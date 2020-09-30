/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import com.fasterxml.jackson.core.io.UTF32Reader;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.core.json.UTF8StreamJsonParser;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import java.io.ByteArrayInputStream;
import java.io.CharConversionException;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class ByteSourceJsonBootstrapper {
    public static final byte UTF8_BOM_1 = -17;
    public static final byte UTF8_BOM_2 = -69;
    public static final byte UTF8_BOM_3 = -65;
    private boolean _bigEndian = true;
    private final boolean _bufferRecyclable;
    private int _bytesPerChar;
    private final IOContext _context;
    private final InputStream _in;
    private final byte[] _inputBuffer;
    private int _inputEnd;
    private int _inputPtr;

    public ByteSourceJsonBootstrapper(IOContext iOContext, InputStream inputStream2) {
        this._context = iOContext;
        this._in = inputStream2;
        this._inputBuffer = iOContext.allocReadIOBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._bufferRecyclable = true;
    }

    public ByteSourceJsonBootstrapper(IOContext iOContext, byte[] arrby, int n, int n2) {
        this._context = iOContext;
        this._in = null;
        this._inputBuffer = arrby;
        this._inputPtr = n;
        this._inputEnd = n + n2;
        this._bufferRecyclable = false;
    }

    private boolean checkUTF16(int n) {
        if ((65280 & n) == 0) {
            this._bigEndian = true;
        } else {
            if ((n & 255) != 0) return false;
            this._bigEndian = false;
        }
        this._bytesPerChar = 2;
        return true;
    }

    private boolean checkUTF32(int n) throws IOException {
        if (n >> 8 == 0) {
            this._bigEndian = true;
        } else if ((16777215 & n) == 0) {
            this._bigEndian = false;
        } else if ((-16711681 & n) == 0) {
            this.reportWeirdUCS4("3412");
        } else {
            if ((n & -65281) != 0) return false;
            this.reportWeirdUCS4("2143");
        }
        this._bytesPerChar = 4;
        return true;
    }

    private boolean handleBOM(int n) throws IOException {
        if (n != -16842752) {
            if (n == -131072) {
                this._inputPtr += 4;
                this._bytesPerChar = 4;
                this._bigEndian = false;
                return true;
            }
            if (n == 65279) {
                this._bigEndian = true;
                this._inputPtr += 4;
                this._bytesPerChar = 4;
                return true;
            }
            if (n == 65534) {
                this.reportWeirdUCS4("2143");
            }
        } else {
            this.reportWeirdUCS4("3412");
        }
        int n2 = n >>> 16;
        if (n2 == 65279) {
            this._inputPtr += 2;
            this._bytesPerChar = 2;
            this._bigEndian = true;
            return true;
        }
        if (n2 == 65534) {
            this._inputPtr += 2;
            this._bytesPerChar = 2;
            this._bigEndian = false;
            return true;
        }
        if (n >>> 8 != 15711167) return false;
        this._inputPtr += 3;
        this._bytesPerChar = 1;
        this._bigEndian = true;
        return true;
    }

    public static MatchStrength hasJSONFormat(InputAccessor object) throws IOException {
        int n;
        if (!object.hasMoreBytes()) {
            return MatchStrength.INCONCLUSIVE;
        }
        int n2 = n = object.nextByte();
        if (n == -17) {
            if (!object.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (object.nextByte() != -69) {
                return MatchStrength.NO_MATCH;
            }
            if (!object.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (object.nextByte() != -65) {
                return MatchStrength.NO_MATCH;
            }
            if (!object.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            n2 = n = (int)object.nextByte();
        }
        if ((n = ByteSourceJsonBootstrapper.skipSpace(object, (byte)n2)) < 0) {
            return MatchStrength.INCONCLUSIVE;
        }
        if (n == 123) {
            n = ByteSourceJsonBootstrapper.skipSpace(object);
            if (n < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (n == 34) return MatchStrength.SOLID_MATCH;
            if (n != 125) return MatchStrength.NO_MATCH;
            return MatchStrength.SOLID_MATCH;
        }
        if (n == 91) {
            n = ByteSourceJsonBootstrapper.skipSpace(object);
            if (n < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (n == 93) return MatchStrength.SOLID_MATCH;
            if (n != 91) return MatchStrength.SOLID_MATCH;
            return MatchStrength.SOLID_MATCH;
        }
        MatchStrength matchStrength = MatchStrength.WEAK_MATCH;
        if (n == 34) {
            return matchStrength;
        }
        if (n <= 57 && n >= 48) {
            return matchStrength;
        }
        if (n == 45) {
            n = ByteSourceJsonBootstrapper.skipSpace(object);
            if (n < 0) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (n > 57) return MatchStrength.NO_MATCH;
            if (n < 48) return MatchStrength.NO_MATCH;
            return matchStrength;
        }
        if (n == 110) {
            return ByteSourceJsonBootstrapper.tryMatch(object, "ull", matchStrength);
        }
        if (n == 116) {
            return ByteSourceJsonBootstrapper.tryMatch(object, "rue", matchStrength);
        }
        if (n != 102) return MatchStrength.NO_MATCH;
        return ByteSourceJsonBootstrapper.tryMatch(object, "alse", matchStrength);
    }

    private void reportWeirdUCS4(String string2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported UCS-4 endianness (");
        stringBuilder.append(string2);
        stringBuilder.append(") detected");
        throw new CharConversionException(stringBuilder.toString());
    }

    private static int skipSpace(InputAccessor inputAccessor) throws IOException {
        if (inputAccessor.hasMoreBytes()) return ByteSourceJsonBootstrapper.skipSpace(inputAccessor, inputAccessor.nextByte());
        return -1;
    }

    private static int skipSpace(InputAccessor inputAccessor, byte by) throws IOException {
        while ((by = (byte)(by & 255)) == 32 || by == 13 || by == 10 || by == 9) {
            if (!inputAccessor.hasMoreBytes()) {
                return -1;
            }
            by = inputAccessor.nextByte();
        }
        return by;
    }

    public static int skipUTF8BOM(DataInput object) throws IOException {
        int n = object.readUnsignedByte();
        if (n != 239) {
            return n;
        }
        n = object.readUnsignedByte();
        if (n != 187) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected byte 0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            ((StringBuilder)object).append(" following 0xEF; should get 0xBB as part of UTF-8 BOM");
            throw new IOException(((StringBuilder)object).toString());
        }
        n = object.readUnsignedByte();
        if (n == 191) {
            return object.readUnsignedByte();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected byte 0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        ((StringBuilder)object).append(" following 0xEF 0xBB; should get 0xBF as part of UTF-8 BOM");
        throw new IOException(((StringBuilder)object).toString());
    }

    private static MatchStrength tryMatch(InputAccessor inputAccessor, String string2, MatchStrength matchStrength) throws IOException {
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            if (!inputAccessor.hasMoreBytes()) {
                return MatchStrength.INCONCLUSIVE;
            }
            if (inputAccessor.nextByte() != string2.charAt(n2)) {
                return MatchStrength.NO_MATCH;
            }
            ++n2;
        }
        return matchStrength;
    }

    public JsonParser constructParser(int n, ObjectCodec objectCodec, ByteQuadsCanonicalizer byteQuadsCanonicalizer, CharsToNameCanonicalizer charsToNameCanonicalizer, int n2) throws IOException {
        int n3 = this._inputPtr;
        JsonEncoding jsonEncoding = this.detectEncoding();
        int n4 = this._inputPtr;
        if (jsonEncoding != JsonEncoding.UTF8) return new ReaderBasedJsonParser(this._context, n, this.constructReader(), objectCodec, charsToNameCanonicalizer.makeChild(n2));
        if (!JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(n2)) return new ReaderBasedJsonParser(this._context, n, this.constructReader(), objectCodec, charsToNameCanonicalizer.makeChild(n2));
        byteQuadsCanonicalizer = byteQuadsCanonicalizer.makeChild(n2);
        return new UTF8StreamJsonParser(this._context, n, this._in, objectCodec, byteQuadsCanonicalizer, this._inputBuffer, this._inputPtr, this._inputEnd, n4 - n3, this._bufferRecyclable);
    }

    public Reader constructReader() throws IOException {
        InputStream inputStream2;
        JsonEncoding jsonEncoding = this._context.getEncoding();
        int n = jsonEncoding.bits();
        if (n != 8 && n != 16) {
            if (n != 32) throw new RuntimeException("Internal error");
            IOContext iOContext = this._context;
            return new UTF32Reader(iOContext, this._in, this._inputBuffer, this._inputPtr, this._inputEnd, iOContext.getEncoding().isBigEndian());
        }
        InputStream inputStream3 = this._in;
        if (inputStream3 == null) {
            inputStream2 = new ByteArrayInputStream(this._inputBuffer, this._inputPtr, this._inputEnd);
            return new InputStreamReader(inputStream2, jsonEncoding.getJavaName());
        }
        inputStream2 = inputStream3;
        if (this._inputPtr >= this._inputEnd) return new InputStreamReader(inputStream2, jsonEncoding.getJavaName());
        inputStream2 = new MergedStream(this._context, inputStream3, this._inputBuffer, this._inputPtr, this._inputEnd);
        return new InputStreamReader(inputStream2, jsonEncoding.getJavaName());
    }

    public JsonEncoding detectEncoding() throws IOException {
        Object object;
        int n;
        block12 : {
            block11 : {
                int n2;
                block10 : {
                    boolean bl = this.ensureLoaded(4);
                    n2 = 0;
                    if (!bl) break block10;
                    object = this._inputBuffer;
                    int n3 = this._inputPtr;
                    byte by = object[n3];
                    int n4 = object[n3 + 1];
                    n = object[n3 + 2];
                    if (this.handleBOM(n4 = object[n3 + 3] & 255 | (by << 24 | (n4 & 255) << 16 | (n & 255) << 8)) || this.checkUTF32(n4)) break block11;
                    n = n2;
                    if (!this.checkUTF16(n4 >>> 16)) break block12;
                    break block11;
                }
                n = n2;
                if (!this.ensureLoaded(2)) break block12;
                object = this._inputBuffer;
                int n5 = this._inputPtr;
                byte by = object[n5];
                n = n2;
                if (!this.checkUTF16(object[n5 + 1] & 255 | (by & 255) << 8)) break block12;
            }
            n = 1;
        }
        if (n == 0) {
            object = JsonEncoding.UTF8;
        } else {
            n = this._bytesPerChar;
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) throw new RuntimeException("Internal error");
                    object = this._bigEndian ? JsonEncoding.UTF32_BE : JsonEncoding.UTF32_LE;
                } else {
                    object = this._bigEndian ? JsonEncoding.UTF16_BE : JsonEncoding.UTF16_LE;
                }
            } else {
                object = JsonEncoding.UTF8;
            }
        }
        this._context.setEncoding((JsonEncoding)((Object)object));
        return object;
    }

    protected boolean ensureLoaded(int n) throws IOException {
        int n2 = this._inputEnd - this._inputPtr;
        while (n2 < n) {
            int n3;
            InputStream inputStream2 = this._in;
            if (inputStream2 == null) {
                n3 = -1;
            } else {
                byte[] arrby = this._inputBuffer;
                n3 = this._inputEnd;
                n3 = inputStream2.read(arrby, n3, arrby.length - n3);
            }
            if (n3 < 1) {
                return false;
            }
            this._inputEnd += n3;
            n2 += n3;
        }
        return true;
    }
}

