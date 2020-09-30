/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.fasterxml.jackson.core.json.JsonGeneratorImpl;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class UTF8JsonGenerator
extends JsonGeneratorImpl {
    private static final byte BYTE_0 = 48;
    private static final byte BYTE_BACKSLASH = 92;
    private static final byte BYTE_COLON = 58;
    private static final byte BYTE_COMMA = 44;
    private static final byte BYTE_LBRACKET = 91;
    private static final byte BYTE_LCURLY = 123;
    private static final byte BYTE_RBRACKET = 93;
    private static final byte BYTE_RCURLY = 125;
    private static final byte BYTE_u = 117;
    private static final byte[] FALSE_BYTES;
    private static final byte[] HEX_CHARS;
    private static final int MAX_BYTES_TO_BUFFER = 512;
    private static final byte[] NULL_BYTES;
    private static final byte[] TRUE_BYTES;
    protected boolean _bufferRecyclable;
    protected char[] _charBuffer;
    protected final int _charBufferLength;
    protected byte[] _entityBuffer;
    protected byte[] _outputBuffer;
    protected final int _outputEnd;
    protected final int _outputMaxContiguous;
    protected final OutputStream _outputStream;
    protected int _outputTail;
    protected byte _quoteChar;

    static {
        HEX_CHARS = CharTypes.copyHexBytes();
        NULL_BYTES = new byte[]{110, 117, 108, 108};
        TRUE_BYTES = new byte[]{116, 114, 117, 101};
        FALSE_BYTES = new byte[]{102, 97, 108, 115, 101};
    }

    @Deprecated
    public UTF8JsonGenerator(IOContext iOContext, int n, ObjectCodec objectCodec, OutputStream outputStream2) {
        this(iOContext, n, objectCodec, outputStream2, '\"');
    }

    public UTF8JsonGenerator(IOContext arrc, int n, ObjectCodec arrby, OutputStream outputStream2, char c) {
        super((IOContext)arrc, n, (ObjectCodec)arrby);
        this._outputStream = outputStream2;
        this._quoteChar = (byte)c;
        if (c != '\"') {
            this._outputEscapes = CharTypes.get7BitOutputEscapes(c);
        }
        this._bufferRecyclable = true;
        arrby = arrc.allocWriteEncodingBuffer();
        this._outputBuffer = arrby;
        this._outputEnd = n = arrby.length;
        this._outputMaxContiguous = n >> 3;
        arrc = arrc.allocConcatBuffer();
        this._charBuffer = arrc;
        this._charBufferLength = arrc.length;
        if (!this.isEnabled(JsonGenerator.Feature.ESCAPE_NON_ASCII)) return;
        this.setHighestNonEscapedChar(127);
    }

    public UTF8JsonGenerator(IOContext arrc, int n, ObjectCodec objectCodec, OutputStream outputStream2, char c, byte[] arrby, int n2, boolean bl) {
        super((IOContext)arrc, n, objectCodec);
        this._outputStream = outputStream2;
        this._quoteChar = (byte)c;
        if (c != '\"') {
            this._outputEscapes = CharTypes.get7BitOutputEscapes(c);
        }
        this._bufferRecyclable = bl;
        this._outputTail = n2;
        this._outputBuffer = arrby;
        this._outputEnd = n = arrby.length;
        this._outputMaxContiguous = n >> 3;
        arrc = arrc.allocConcatBuffer();
        this._charBuffer = arrc;
        this._charBufferLength = arrc.length;
    }

    @Deprecated
    public UTF8JsonGenerator(IOContext iOContext, int n, ObjectCodec objectCodec, OutputStream outputStream2, byte[] arrby, int n2, boolean bl) {
        this(iOContext, n, objectCodec, outputStream2, '\"', arrby, n2, bl);
    }

    private final int _handleLongCustomEscape(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws IOException, JsonGenerationException {
        int n4 = arrby2.length;
        int n5 = n;
        if (n + n4 > n2) {
            this._outputTail = n;
            this._flushBuffer();
            n5 = n = this._outputTail;
            if (n4 > arrby.length) {
                this._outputStream.write(arrby2, 0, n4);
                return n;
            }
        }
        System.arraycopy(arrby2, 0, arrby, n5, n4);
        n = n5 + n4;
        if (n3 * 6 + n <= n2) return n;
        this._outputTail = n;
        this._flushBuffer();
        return this._outputTail;
    }

    private final int _outputMultiByteChar(int n, int n2) throws IOException {
        byte[] arrby = this._outputBuffer;
        if (n >= 55296 && n <= 57343) {
            int n3 = n2 + 1;
            arrby[n2] = (byte)92;
            n2 = n3 + 1;
            arrby[n3] = (byte)117;
            n3 = n2 + 1;
            byte[] arrby2 = HEX_CHARS;
            arrby[n2] = arrby2[n >> 12 & 15];
            n2 = n3 + 1;
            arrby[n3] = arrby2[n >> 8 & 15];
            n3 = n2 + 1;
            arrby[n2] = arrby2[n >> 4 & 15];
            n2 = n3 + 1;
            arrby[n3] = arrby2[n & 15];
            return n2;
        }
        int n4 = n2 + 1;
        arrby[n2] = (byte)(n >> 12 | 224);
        n2 = n4 + 1;
        arrby[n4] = (byte)(n >> 6 & 63 | 128);
        arrby[n2] = (byte)(n & 63 | 128);
        return n2 + 1;
    }

    private final int _outputRawMultiByteChar(int n, char[] arrobject, int n2, int n3) throws IOException {
        if (n >= 55296 && n <= 57343) {
            if (n2 >= n3 || arrobject == null) {
                this._reportError(String.format("Split surrogate on writeRaw() input (last character): first character 0x%4x", n));
            }
            this._outputSurrogates(n, arrobject[n2]);
            return n2 + 1;
        }
        arrobject = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n3 = n4 + 1;
        arrobject[n4] = (char)(n >> 12 | 224);
        this._outputTail = n4 = n3 + 1;
        arrobject[n3] = (char)(n >> 6 & 63 | 128);
        this._outputTail = n4 + 1;
        arrobject[n4] = (char)(n & 63 | 128);
        return n2;
    }

    private final int _readMore(InputStream inputStream2, byte[] arrby, int n, int n2, int n3) throws IOException {
        int n4 = 0;
        int n5 = n;
        n = n4;
        while (n5 < n2) {
            arrby[n] = arrby[n5];
            ++n;
            ++n5;
        }
        n3 = Math.min(n3, arrby.length);
        do {
            if ((n2 = n3 - n) == 0) {
                return n;
            }
            if ((n2 = inputStream2.read(arrby, n, n2)) < 0) {
                return n;
            }
            n = n2 = n + n2;
        } while (n2 < 3);
        return n2;
    }

    private final void _writeBytes(byte[] arrby) throws IOException {
        int n = arrby.length;
        if (this._outputTail + n > this._outputEnd) {
            this._flushBuffer();
            if (n > 512) {
                this._outputStream.write(arrby, 0, n);
                return;
            }
        }
        System.arraycopy(arrby, 0, this._outputBuffer, this._outputTail, n);
        this._outputTail += n;
    }

    private final void _writeBytes(byte[] arrby, int n, int n2) throws IOException {
        if (this._outputTail + n2 > this._outputEnd) {
            this._flushBuffer();
            if (n2 > 512) {
                this._outputStream.write(arrby, n, n2);
                return;
            }
        }
        System.arraycopy(arrby, n, this._outputBuffer, this._outputTail, n2);
        this._outputTail += n2;
    }

    private final int _writeCustomEscape(byte[] arrby, int n, SerializableString arrby2, int n2) throws IOException, JsonGenerationException {
        int n3 = (arrby2 = arrby2.asUnquotedUTF8()).length;
        if (n3 > 6) {
            return this._handleLongCustomEscape(arrby, n, this._outputEnd, arrby2, n2);
        }
        System.arraycopy(arrby2, 0, arrby, n, n3);
        return n + n3;
    }

    private final void _writeCustomStringSegment2(String string2, int n, int n2) throws IOException {
        if (this._outputTail + (n2 - n) * 6 > this._outputEnd) {
            this._flushBuffer();
        }
        int n3 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n4 = this._maximumNonEscapedChar <= 0 ? 65535 : this._maximumNonEscapedChar;
        CharacterEscapes characterEscapes = this._characterEscapes;
        int n5 = n;
        n = n3;
        do {
            Object object;
            if (n5 >= n2) {
                this._outputTail = n;
                return;
            }
            n3 = n5 + 1;
            int n6 = string2.charAt(n5);
            if (n6 <= 127) {
                if (arrn[n6] == 0) {
                    arrby[n] = (byte)n6;
                    n5 = n3;
                    ++n;
                    continue;
                }
                n5 = arrn[n6];
                if (n5 > 0) {
                    n6 = n + 1;
                    arrby[n] = (byte)92;
                    n = n6 + 1;
                    arrby[n6] = (byte)n5;
                } else if (n5 == -2) {
                    SerializableString serializableString = characterEscapes.getEscapeSequence(n6);
                    if (serializableString == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid custom escape definitions; custom escape not found for character code 0x");
                        ((StringBuilder)object).append(Integer.toHexString(n6));
                        ((StringBuilder)object).append(", although was supposed to have one");
                        this._reportError(((StringBuilder)object).toString());
                    }
                    n = this._writeCustomEscape(arrby, n, serializableString, n2 - n3);
                } else {
                    n = this._writeGenericEscape(n6, n);
                }
            } else if (n6 > n4) {
                n = this._writeGenericEscape(n6, n);
            } else {
                object = characterEscapes.getEscapeSequence(n6);
                if (object != null) {
                    n = this._writeCustomEscape(arrby, n, (SerializableString)object, n2 - n3);
                } else if (n6 <= 2047) {
                    n5 = n + 1;
                    arrby[n] = (byte)(n6 >> 6 | 192);
                    n = n5 + 1;
                    arrby[n5] = (byte)(n6 & 63 | 128);
                } else {
                    n = this._outputMultiByteChar(n6, n);
                }
            }
            n5 = n3;
        } while (true);
    }

    private final void _writeCustomStringSegment2(char[] arrc, int n, int n2) throws IOException {
        if (this._outputTail + (n2 - n) * 6 > this._outputEnd) {
            this._flushBuffer();
        }
        int n3 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n4 = this._maximumNonEscapedChar <= 0 ? 65535 : this._maximumNonEscapedChar;
        CharacterEscapes characterEscapes = this._characterEscapes;
        int n5 = n;
        n = n3;
        do {
            Object object;
            int n6;
            if (n5 >= n2) {
                this._outputTail = n;
                return;
            }
            n3 = n5 + 1;
            if ((n5 = arrc[n5]) <= 127) {
                if (arrn[n5] == 0) {
                    arrby[n] = (byte)n5;
                    n5 = n3;
                    ++n;
                    continue;
                }
                n6 = arrn[n5];
                if (n6 > 0) {
                    n5 = n + 1;
                    arrby[n] = (byte)92;
                    n = n5 + 1;
                    arrby[n5] = (byte)n6;
                } else if (n6 == -2) {
                    SerializableString serializableString = characterEscapes.getEscapeSequence(n5);
                    if (serializableString == null) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid custom escape definitions; custom escape not found for character code 0x");
                        ((StringBuilder)object).append(Integer.toHexString(n5));
                        ((StringBuilder)object).append(", although was supposed to have one");
                        this._reportError(((StringBuilder)object).toString());
                    }
                    n = this._writeCustomEscape(arrby, n, serializableString, n2 - n3);
                } else {
                    n = this._writeGenericEscape(n5, n);
                }
            } else if (n5 > n4) {
                n = this._writeGenericEscape(n5, n);
            } else {
                object = characterEscapes.getEscapeSequence(n5);
                if (object != null) {
                    n = this._writeCustomEscape(arrby, n, (SerializableString)object, n2 - n3);
                } else if (n5 <= 2047) {
                    n6 = n + 1;
                    arrby[n] = (byte)(n5 >> 6 | 192);
                    n = n6 + 1;
                    arrby[n6] = (byte)(n5 & 63 | 128);
                } else {
                    n = this._outputMultiByteChar(n5, n);
                }
            }
            n5 = n3;
        } while (true);
    }

    private int _writeGenericEscape(int n, int n2) throws IOException {
        byte[] arrby;
        byte[] arrby2 = this._outputBuffer;
        int n3 = n2 + 1;
        arrby2[n2] = (byte)92;
        n2 = n3 + 1;
        arrby2[n3] = (byte)117;
        if (n > 255) {
            n3 = 255 & n >> 8;
            int n4 = n2 + 1;
            arrby = HEX_CHARS;
            arrby2[n2] = arrby[n3 >> 4];
            n2 = n4 + 1;
            arrby2[n4] = arrby[n3 & 15];
            n &= 255;
        } else {
            n3 = n2 + 1;
            arrby2[n2] = (byte)48;
            n2 = n3 + 1;
            arrby2[n3] = (byte)48;
        }
        n3 = n2 + 1;
        arrby = HEX_CHARS;
        arrby2[n2] = arrby[n >> 4];
        arrby2[n3] = arrby[n & 15];
        return n3 + 1;
    }

    private final void _writeNull() throws IOException {
        if (this._outputTail + 4 >= this._outputEnd) {
            this._flushBuffer();
        }
        System.arraycopy(NULL_BYTES, 0, this._outputBuffer, this._outputTail, 4);
        this._outputTail += 4;
    }

    private final void _writeQuotedInt(int n) throws IOException {
        int n2;
        if (this._outputTail + 13 >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n2 = n3 + 1;
        arrby[n3] = this._quoteChar;
        this._outputTail = n = NumberOutput.outputInt(n, arrby, n2);
        arrby = this._outputBuffer;
        this._outputTail = n + 1;
        arrby[n] = this._quoteChar;
    }

    private final void _writeQuotedLong(long l) throws IOException {
        int n;
        if (this._outputTail + 23 >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n = n2 + 1;
        arrby[n2] = this._quoteChar;
        this._outputTail = n = NumberOutput.outputLong(l, arrby, n);
        arrby = this._outputBuffer;
        this._outputTail = n + 1;
        arrby[n] = this._quoteChar;
    }

    private final void _writeQuotedRaw(String arrby) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby2[n] = this._quoteChar;
        this.writeRaw((String)arrby);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = this._quoteChar;
    }

    private final void _writeQuotedShort(short s) throws IOException {
        int n;
        if (this._outputTail + 8 >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n = n2 + 1;
        arrby[n2] = this._quoteChar;
        s = (short)NumberOutput.outputInt((int)s, arrby, n);
        this._outputTail = s;
        arrby = this._outputBuffer;
        this._outputTail = s + 1;
        arrby[s] = this._quoteChar;
    }

    private void _writeRawSegment(char[] arrc, int n, int n2) throws IOException {
        block0 : do {
            int n3;
            if (n >= n2) return;
            do {
                int n4;
                byte[] arrby;
                if ((n3 = arrc[n]) > 127) {
                    n3 = n + 1;
                    if ((n = arrc[n]) < 2048) {
                        arrby = this._outputBuffer;
                        int n5 = this._outputTail;
                        this._outputTail = n4 = n5 + 1;
                        arrby[n5] = (byte)(n >> 6 | 192);
                        this._outputTail = n4 + 1;
                        arrby[n4] = (byte)(n & 63 | 128);
                        n = n3;
                        continue block0;
                    }
                    n = this._outputRawMultiByteChar(n, arrc, n3, n2);
                    continue block0;
                }
                arrby = this._outputBuffer;
                n4 = this._outputTail;
                this._outputTail = n4 + 1;
                arrby[n4] = (byte)n3;
                n = n3 = n + 1;
            } while (n3 < n2);
            break;
        } while (true);
    }

    private final void _writeSegmentedRaw(char[] arrc, int n, int n2) throws IOException {
        int n3 = this._outputEnd;
        byte[] arrby = this._outputBuffer;
        int n4 = n2 + n;
        block0 : do {
            if (n >= n4) return;
            do {
                int n5;
                if ((n5 = arrc[n]) >= 128) {
                    if (this._outputTail + 3 >= this._outputEnd) {
                        this._flushBuffer();
                    }
                    n2 = n + 1;
                    char c = arrc[n];
                    if (c < '\u0800') {
                        n5 = this._outputTail;
                        this._outputTail = n = n5 + 1;
                        arrby[n5] = (byte)(c >> 6 | 192);
                        this._outputTail = n + 1;
                        arrby[n] = (byte)(c & 63 | 128);
                        n = n2;
                        continue block0;
                    }
                    n = this._outputRawMultiByteChar(c, arrc, n2, n4);
                    continue block0;
                }
                if (this._outputTail >= n3) {
                    this._flushBuffer();
                }
                n2 = this._outputTail;
                this._outputTail = n2 + 1;
                arrby[n2] = (byte)n5;
                n = n2 = n + 1;
            } while (n2 < n4);
            break;
        } while (true);
    }

    private final void _writeStringSegment(String string2, int n, int n2) throws IOException {
        char c;
        int n3 = n2 + n;
        n2 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        while (n < n3 && (c = string2.charAt(n)) <= '' && arrn[c] == 0) {
            arrby[n2] = (byte)c;
            ++n;
            ++n2;
        }
        this._outputTail = n2;
        if (n >= n3) return;
        if (this._characterEscapes != null) {
            this._writeCustomStringSegment2(string2, n, n3);
            return;
        }
        if (this._maximumNonEscapedChar == 0) {
            this._writeStringSegment2(string2, n, n3);
            return;
        }
        this._writeStringSegmentASCII2(string2, n, n3);
    }

    private final void _writeStringSegment(char[] arrc, int n, int n2) throws IOException {
        char c;
        int n3 = n2 + n;
        n2 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        while (n < n3 && (c = arrc[n]) <= '' && arrn[c] == 0) {
            arrby[n2] = (byte)c;
            ++n;
            ++n2;
        }
        this._outputTail = n2;
        if (n >= n3) return;
        if (this._characterEscapes != null) {
            this._writeCustomStringSegment2(arrc, n, n3);
            return;
        }
        if (this._maximumNonEscapedChar == 0) {
            this._writeStringSegment2(arrc, n, n3);
            return;
        }
        this._writeStringSegmentASCII2(arrc, n, n3);
    }

    private final void _writeStringSegment2(String string2, int n, int n2) throws IOException {
        if (this._outputTail + (n2 - n) * 6 > this._outputEnd) {
            this._flushBuffer();
        }
        int n3 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n4 = n;
        n = n3;
        do {
            if (n4 >= n2) {
                this._outputTail = n;
                return;
            }
            n3 = n4 + 1;
            int n5 = string2.charAt(n4);
            if (n5 <= 127) {
                if (arrn[n5] == 0) {
                    arrby[n] = (byte)n5;
                    n4 = n3;
                    ++n;
                    continue;
                }
                n4 = arrn[n5];
                if (n4 > 0) {
                    n5 = n + 1;
                    arrby[n] = (byte)92;
                    n = n5 + 1;
                    arrby[n5] = (byte)n4;
                } else {
                    n = this._writeGenericEscape(n5, n);
                }
            } else if (n5 <= 2047) {
                n4 = n + 1;
                arrby[n] = (byte)(n5 >> 6 | 192);
                n = n4 + 1;
                arrby[n4] = (byte)(n5 & 63 | 128);
            } else {
                n = this._outputMultiByteChar(n5, n);
            }
            n4 = n3;
        } while (true);
    }

    private final void _writeStringSegment2(char[] arrc, int n, int n2) throws IOException {
        if (this._outputTail + (n2 - n) * 6 > this._outputEnd) {
            this._flushBuffer();
        }
        int n3 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n4 = n;
        n = n3;
        do {
            if (n4 >= n2) {
                this._outputTail = n;
                return;
            }
            n3 = n4 + 1;
            int n5 = arrc[n4];
            if (n5 <= 127) {
                if (arrn[n5] == 0) {
                    arrby[n] = (byte)n5;
                    n4 = n3;
                    ++n;
                    continue;
                }
                n4 = arrn[n5];
                if (n4 > 0) {
                    n5 = n + 1;
                    arrby[n] = (byte)92;
                    n = n5 + 1;
                    arrby[n5] = (byte)n4;
                } else {
                    n = this._writeGenericEscape(n5, n);
                }
            } else if (n5 <= 2047) {
                n4 = n + 1;
                arrby[n] = (byte)(n5 >> 6 | 192);
                n = n4 + 1;
                arrby[n4] = (byte)(n5 & 63 | 128);
            } else {
                n = this._outputMultiByteChar(n5, n);
            }
            n4 = n3;
        } while (true);
    }

    private final void _writeStringSegmentASCII2(String string2, int n, int n2) throws IOException {
        if (this._outputTail + (n2 - n) * 6 > this._outputEnd) {
            this._flushBuffer();
        }
        int n3 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n4 = this._maximumNonEscapedChar;
        int n5 = n;
        n = n3;
        do {
            if (n5 >= n2) {
                this._outputTail = n;
                return;
            }
            n3 = n5 + 1;
            int n6 = string2.charAt(n5);
            if (n6 <= 127) {
                if (arrn[n6] == 0) {
                    arrby[n] = (byte)n6;
                    n5 = n3;
                    ++n;
                    continue;
                }
                n5 = arrn[n6];
                if (n5 > 0) {
                    n6 = n + 1;
                    arrby[n] = (byte)92;
                    n = n6 + 1;
                    arrby[n6] = (byte)n5;
                } else {
                    n = this._writeGenericEscape(n6, n);
                }
            } else if (n6 > n4) {
                n = this._writeGenericEscape(n6, n);
            } else if (n6 <= 2047) {
                n5 = n + 1;
                arrby[n] = (byte)(n6 >> 6 | 192);
                n = n5 + 1;
                arrby[n5] = (byte)(n6 & 63 | 128);
            } else {
                n = this._outputMultiByteChar(n6, n);
            }
            n5 = n3;
        } while (true);
    }

    private final void _writeStringSegmentASCII2(char[] arrc, int n, int n2) throws IOException {
        if (this._outputTail + (n2 - n) * 6 > this._outputEnd) {
            this._flushBuffer();
        }
        int n3 = this._outputTail;
        byte[] arrby = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        int n4 = this._maximumNonEscapedChar;
        int n5 = n;
        n = n3;
        do {
            int n6;
            if (n5 >= n2) {
                this._outputTail = n;
                return;
            }
            n3 = n5 + 1;
            if ((n5 = arrc[n5]) <= 127) {
                if (arrn[n5] == 0) {
                    arrby[n] = (byte)n5;
                    n5 = n3;
                    ++n;
                    continue;
                }
                n6 = arrn[n5];
                if (n6 > 0) {
                    n5 = n + 1;
                    arrby[n] = (byte)92;
                    n = n5 + 1;
                    arrby[n5] = (byte)n6;
                } else {
                    n = this._writeGenericEscape(n5, n);
                }
            } else if (n5 > n4) {
                n = this._writeGenericEscape(n5, n);
            } else if (n5 <= 2047) {
                n6 = n + 1;
                arrby[n] = (byte)(n5 >> 6 | 192);
                n = n6 + 1;
                arrby[n6] = (byte)(n5 & 63 | 128);
            } else {
                n = this._outputMultiByteChar(n5, n);
            }
            n5 = n3;
        } while (true);
    }

    private final void _writeStringSegments(String string2, int n, int n2) throws IOException {
        int n3;
        do {
            if (this._outputTail + (n3 = Math.min(this._outputMaxContiguous, n2)) > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(string2, n, n3);
            n += n3;
            n2 = n3 = n2 - n3;
        } while (n3 > 0);
    }

    private final void _writeStringSegments(String arrby, boolean bl) throws IOException {
        int n;
        int n2;
        if (bl) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby2 = this._outputBuffer;
            n = this._outputTail;
            this._outputTail = n + 1;
            arrby2[n] = this._quoteChar;
        }
        n = 0;
        for (int i = arrby.length(); i > 0; n += n2, i -= n2) {
            n2 = Math.min(this._outputMaxContiguous, i);
            if (this._outputTail + n2 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment((String)arrby, n, n2);
        }
        if (!bl) return;
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = this._quoteChar;
    }

    private final void _writeStringSegments(char[] arrc, int n, int n2) throws IOException {
        int n3;
        do {
            if (this._outputTail + (n3 = Math.min(this._outputMaxContiguous, n2)) > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(arrc, n, n3);
            n += n3;
            n2 = n3 = n2 - n3;
        } while (n3 > 0);
    }

    private final void _writeUTF8Segment(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        int[] arrn = this._outputEscapes;
        for (int i = n; i < n + n2; ++i) {
            byte by = arrby[i];
            if (by < 0 || arrn[by] == 0) continue;
            this._writeUTF8Segment2(arrby, n, n2);
            return;
        }
        if (this._outputTail + n2 > this._outputEnd) {
            this._flushBuffer();
        }
        System.arraycopy(arrby, n, this._outputBuffer, this._outputTail, n2);
        this._outputTail += n2;
    }

    private final void _writeUTF8Segment2(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        int n3;
        int n4 = n3 = this._outputTail;
        if (n2 * 6 + n3 > this._outputEnd) {
            this._flushBuffer();
            n4 = this._outputTail;
        }
        byte[] arrby2 = this._outputBuffer;
        int[] arrn = this._outputEscapes;
        n3 = n;
        do {
            int n5;
            if ((n5 = n3) >= n2 + n) {
                this._outputTail = n4;
                return;
            }
            n3 = n5 + 1;
            int n6 = arrby[n5];
            if (n6 >= 0 && arrn[n6] != 0) {
                n5 = arrn[n6];
                if (n5 > 0) {
                    n6 = n4 + 1;
                    arrby2[n4] = (byte)92;
                    n4 = n6 + 1;
                    arrby2[n6] = (byte)n5;
                    continue;
                }
                n4 = this._writeGenericEscape(n6, n4);
                continue;
            }
            arrby2[n4] = (byte)n6;
            ++n4;
        } while (true);
    }

    private final void _writeUTF8Segments(byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        int n3;
        do {
            n3 = Math.min(this._outputMaxContiguous, n2);
            this._writeUTF8Segment(arrby, n, n3);
            n += n3;
            n2 = n3 = n2 - n3;
        } while (n3 > 0);
    }

    private final void _writeUnq(SerializableString serializableString) throws IOException {
        int n = serializableString.appendQuotedUTF8(this._outputBuffer, this._outputTail);
        if (n < 0) {
            this._writeBytes(serializableString.asQuotedUTF8());
            return;
        }
        this._outputTail += n;
    }

    protected final void _flushBuffer() throws IOException {
        int n = this._outputTail;
        if (n <= 0) return;
        this._outputTail = 0;
        this._outputStream.write(this._outputBuffer, 0, n);
    }

    protected final void _outputSurrogates(int n, int n2) throws IOException {
        int n3;
        n = this._decodeSurrogate(n, n2);
        if (this._outputTail + 4 > this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n3 = n2 + 1;
        arrby[n2] = (byte)(n >> 18 | 240);
        this._outputTail = n2 = n3 + 1;
        arrby[n3] = (byte)(n >> 12 & 63 | 128);
        this._outputTail = n3 = n2 + 1;
        arrby[n2] = (byte)(n >> 6 & 63 | 128);
        this._outputTail = n3 + 1;
        arrby[n3] = (byte)(n & 63 | 128);
    }

    @Override
    protected void _releaseBuffers() {
        Object[] arrobject = this._outputBuffer;
        if (arrobject != null && this._bufferRecyclable) {
            this._outputBuffer = null;
            this._ioContext.releaseWriteEncodingBuffer((byte[])arrobject);
        }
        if ((arrobject = this._charBuffer) == null) return;
        this._charBuffer = null;
        this._ioContext.releaseConcatBuffer((char[])arrobject);
    }

    @Override
    protected final void _verifyValueWrite(String arrby) throws IOException {
        int n = this._writeContext.writeValue();
        if (this._cfgPrettyPrinter != null) {
            this._verifyPrettyValueWrite((String)arrby, n);
            return;
        }
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    if (this._rootValueSeparator == null) return;
                    arrby = this._rootValueSeparator.asUnquotedUTF8();
                    if (arrby.length <= 0) return;
                    this._writeBytes(arrby);
                    return;
                }
                if (n != 5) {
                    return;
                }
                this._reportCantWriteValueExpectName((String)arrby);
                return;
            }
            n = 58;
        } else {
            n = 44;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = (byte)n;
    }

    protected final int _writeBinary(Base64Variant base64Variant, InputStream inputStream2, byte[] arrby) throws IOException, JsonGenerationException {
        int n = this._outputEnd - 6;
        int n2 = base64Variant.getMaxLineLength();
        int n3 = 2;
        int n4 = n2 >> 2;
        int n5 = -3;
        int n6 = 0;
        int n7 = 0;
        n2 = 0;
        do {
            int n8;
            int n9;
            int n10 = n5;
            int n11 = n6;
            int n12 = n7;
            if (n6 > n5) {
                n12 = this._readMore(inputStream2, arrby, n6, n7, arrby.length);
                if (n12 < 3) {
                    n4 = n2;
                    if (n12 <= 0) return n4;
                    if (this._outputTail > n) {
                        this._flushBuffer();
                    }
                    n6 = arrby[0] << 16;
                    if (1 < n12) {
                        n6 |= (arrby[1] & 255) << 8;
                        n4 = n3;
                        break;
                    }
                    n4 = 1;
                    break;
                }
                n10 = n12 - 3;
                n11 = 0;
            }
            if (this._outputTail > n) {
                this._flushBuffer();
            }
            n7 = n11 + 1;
            n6 = arrby[n11];
            n5 = n7 + 1;
            n7 = arrby[n7];
            n11 = n5 + 1;
            n5 = arrby[n5];
            int n13 = n2 + 3;
            this._outputTail = n8 = base64Variant.encodeBase64Chunk((n7 & 255 | n6 << 8) << 8 | n5 & 255, this._outputBuffer, this._outputTail);
            n4 = n9 = n4 - 1;
            n5 = n10;
            n6 = n11;
            n7 = n12;
            n2 = n13;
            if (n9 > 0) continue;
            byte[] arrby2 = this._outputBuffer;
            this._outputTail = n2 = n8 + 1;
            arrby2[n8] = (byte)92;
            this._outputTail = n2 + 1;
            arrby2[n2] = (byte)110;
            n4 = base64Variant.getMaxLineLength() >> 2;
            n5 = n10;
            n6 = n11;
            n7 = n12;
            n2 = n13;
        } while (true);
        n2 += n4;
        this._outputTail = base64Variant.encodeBase64Partial(n6, n4, this._outputBuffer, this._outputTail);
        return n2;
    }

    protected final int _writeBinary(Base64Variant base64Variant, InputStream inputStream2, byte[] arrby, int n) throws IOException, JsonGenerationException {
        int n2;
        int n3 = this._outputEnd - 6;
        int n4 = base64Variant.getMaxLineLength();
        int n5 = 2;
        int n6 = n4 >> 2;
        int n7 = -3;
        int n8 = 0;
        int n9 = 0;
        n4 = n;
        n = n8;
        do {
            int n10;
            int n11;
            n8 = n;
            n2 = n9;
            if (n4 <= 2) break;
            n2 = n7;
            int n12 = n;
            n8 = n9;
            if (n > n7) {
                n2 = this._readMore(inputStream2, arrby, n, n9, n4);
                if (n2 < 3) {
                    n8 = 0;
                    break;
                }
                n = n2 - 3;
                n12 = 0;
                n8 = n2;
                n2 = n;
            }
            if (this._outputTail > n3) {
                this._flushBuffer();
            }
            n9 = n12 + 1;
            n = arrby[n12];
            n7 = n9 + 1;
            n9 = arrby[n9];
            n12 = n7 + 1;
            n7 = arrby[n7];
            int n13 = n4 - 3;
            this._outputTail = n10 = base64Variant.encodeBase64Chunk((n9 & 255 | n << 8) << 8 | n7 & 255, this._outputBuffer, this._outputTail);
            n6 = n11 = n6 - 1;
            n7 = n2;
            n = n12;
            n9 = n8;
            n4 = n13;
            if (n11 > 0) continue;
            byte[] arrby2 = this._outputBuffer;
            this._outputTail = n = n10 + 1;
            arrby2[n10] = (byte)92;
            this._outputTail = n + 1;
            arrby2[n] = (byte)110;
            n6 = base64Variant.getMaxLineLength() >> 2;
            n7 = n2;
            n = n12;
            n9 = n8;
            n4 = n13;
        } while (true);
        n = n4;
        if (n4 <= 0) return n;
        n9 = this._readMore(inputStream2, arrby, n8, n2, n4);
        n = n4;
        if (n9 <= 0) return n;
        if (this._outputTail > n3) {
            this._flushBuffer();
        }
        n6 = arrby[0] << 16;
        if (1 < n9) {
            n6 |= (arrby[1] & 255) << 8;
            n = n5;
        } else {
            n = 1;
        }
        this._outputTail = base64Variant.encodeBase64Partial(n6, n, this._outputBuffer, this._outputTail);
        return n4 - n;
    }

    protected final void _writeBinary(Base64Variant base64Variant, byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        int n3 = this._outputEnd - 6;
        int n4 = base64Variant.getMaxLineLength() >> 2;
        int n5 = n;
        n = n4;
        while (n5 <= n2 - 3) {
            if (this._outputTail > n3) {
                this._flushBuffer();
            }
            int n6 = n5 + 1;
            n5 = arrby[n5];
            n4 = n6 + 1;
            this._outputTail = n6 = base64Variant.encodeBase64Chunk((n5 << 8 | arrby[n6] & 255) << 8 | arrby[n4] & 255, this._outputBuffer, this._outputTail);
            n = n5 = n - 1;
            if (n5 <= 0) {
                byte[] arrby2 = this._outputBuffer;
                this._outputTail = n = n6 + 1;
                arrby2[n6] = (byte)92;
                this._outputTail = n + 1;
                arrby2[n] = (byte)110;
                n = base64Variant.getMaxLineLength() >> 2;
            }
            n5 = n4 + 1;
        }
        n4 = n2 - n5;
        if (n4 <= 0) return;
        if (this._outputTail > n3) {
            this._flushBuffer();
        }
        n = n2 = arrby[n5] << 16;
        if (n4 == 2) {
            n = n2 | (arrby[n5 + 1] & 255) << 8;
        }
        this._outputTail = base64Variant.encodeBase64Partial(n, n4, this._outputBuffer, this._outputTail);
    }

    protected final void _writePPFieldName(SerializableString arrby) throws IOException {
        int n;
        int n2 = this._writeContext.writeFieldName(arrby.getValue());
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (n2 == 1) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        n2 = this._cfgUnqNames ^ true;
        if (n2 != 0) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            byte[] arrby2 = this._outputBuffer;
            n = this._outputTail;
            this._outputTail = n + 1;
            arrby2[n] = this._quoteChar;
        }
        if ((n = arrby.appendQuotedUTF8(this._outputBuffer, this._outputTail)) < 0) {
            this._writeBytes(arrby.asQuotedUTF8());
        } else {
            this._outputTail += n;
        }
        if (n2 == 0) return;
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = this._quoteChar;
    }

    protected final void _writePPFieldName(String arrby) throws IOException {
        int n = this._writeContext.writeFieldName((String)arrby);
        if (n == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (n == 1) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (this._cfgUnqNames) {
            this._writeStringSegments((String)arrby, false);
            return;
        }
        n = arrby.length();
        if (n > this._charBufferLength) {
            this._writeStringSegments((String)arrby, true);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby2[n2] = this._quoteChar;
        arrby.getChars(0, n, this._charBuffer, 0);
        if (n <= this._outputMaxContiguous) {
            if (this._outputTail + n > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment(this._charBuffer, 0, n);
        } else {
            this._writeStringSegments(this._charBuffer, 0, n);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = this._quoteChar;
    }

    @Override
    public void close() throws IOException {
        super.close();
        if (this._outputBuffer != null && this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT)) {
            do {
                JsonStreamContext jsonStreamContext;
                if ((jsonStreamContext = this.getOutputContext()).inArray()) {
                    this.writeEndArray();
                    continue;
                }
                if (!jsonStreamContext.inObject()) break;
                this.writeEndObject();
            } while (true);
        }
        this._flushBuffer();
        this._outputTail = 0;
        if (this._outputStream != null) {
            if (!this._ioContext.isResourceManaged() && !this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
                if (this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
                    this._outputStream.flush();
                }
            } else {
                this._outputStream.close();
            }
        }
        this._releaseBuffers();
    }

    @Override
    public void flush() throws IOException {
        this._flushBuffer();
        if (this._outputStream == null) return;
        if (!this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) return;
        this._outputStream.flush();
    }

    @Override
    public int getOutputBuffered() {
        return this._outputTail;
    }

    @Override
    public Object getOutputTarget() {
        return this._outputStream;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public int writeBinary(Base64Variant var1_1, InputStream var2_3, int var3_4) throws IOException, JsonGenerationException {
        block4 : {
            this._verifyValueWrite("write a binary value");
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            var4_5 = this._outputBuffer;
            var5_6 = this._outputTail;
            this._outputTail = var5_6 + 1;
            var4_5[var5_6] = this._quoteChar;
            var4_5 = this._ioContext.allocBase64Buffer();
            if (var3_4 >= 0) ** GOTO lbl13
            try {
                block5 : {
                    var5_6 = this._writeBinary((Base64Variant)var1_1, var2_3, var4_5);
                    break block5;
lbl13: // 1 sources:
                    var6_7 = this._writeBinary((Base64Variant)var1_1, var2_3, var4_5, var3_4);
                    var5_6 = var3_4;
                    if (var6_7 > 0) {
                        var1_1 = new StringBuilder();
                        var1_1.append("Too few bytes available: missing ");
                        var1_1.append(var6_7);
                        var1_1.append(" bytes (out of ");
                        var1_1.append(var3_4);
                        var1_1.append(")");
                        this._reportError(var1_1.toString());
                        var5_6 = var3_4;
                    }
                }
                this._ioContext.releaseBase64Buffer(var4_5);
                if (this._outputTail < this._outputEnd) break block4;
            }
            catch (Throwable var1_2) {
                this._ioContext.releaseBase64Buffer(var4_5);
                throw var1_2;
            }
            this._flushBuffer();
        }
        var1_1 = this._outputBuffer;
        var3_4 = this._outputTail;
        this._outputTail = var3_4 + 1;
        var1_1[var3_4] = (Serializable)((byte)this._quoteChar);
        return var5_6;
    }

    @Override
    public void writeBinary(Base64Variant arrby, byte[] arrby2, int n, int n2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write a binary value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby3 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby3[n3] = this._quoteChar;
        this._writeBinary((Base64Variant)arrby, arrby2, n, n2 + n);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = this._quoteChar;
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        this._verifyValueWrite("write a boolean value");
        if (this._outputTail + 5 >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = bl ? TRUE_BYTES : FALSE_BYTES;
        int n = arrby.length;
        System.arraycopy(arrby, 0, this._outputBuffer, this._outputTail, n);
        this._outputTail += n;
    }

    @Override
    public final void writeEndArray() throws IOException {
        byte[] arrby;
        if (!this._writeContext.inArray()) {
            arrby = new StringBuilder();
            arrby.append("Current context not Array but ");
            arrby.append(this._writeContext.typeDesc());
            this._reportError(arrby.toString());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            arrby = this._outputBuffer;
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrby[n] = (byte)93;
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }

    @Override
    public final void writeEndObject() throws IOException {
        byte[] arrby;
        if (!this._writeContext.inObject()) {
            arrby = new StringBuilder();
            arrby.append("Current context not Object but ");
            arrby.append(this._writeContext.typeDesc());
            this._reportError(arrby.toString());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            arrby = this._outputBuffer;
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrby[n] = (byte)125;
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }

    @Override
    public void writeFieldName(SerializableString arrby) throws IOException {
        int n;
        byte[] arrby2;
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName((SerializableString)arrby);
            return;
        }
        int n2 = this._writeContext.writeFieldName(arrby.getValue());
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (n2 == 1) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            arrby2 = this._outputBuffer;
            n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrby2[n2] = (byte)44;
        }
        if (this._cfgUnqNames) {
            this._writeUnq((SerializableString)arrby);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby2 = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n = n2 + 1;
        arrby2[n2] = this._quoteChar;
        n2 = arrby.appendQuotedUTF8(arrby2, n);
        if (n2 < 0) {
            this._writeBytes(arrby.asQuotedUTF8());
        } else {
            this._outputTail += n2;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = this._quoteChar;
    }

    @Override
    public void writeFieldName(String arrby) throws IOException {
        int n;
        byte[] arrby2;
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName((String)arrby);
            return;
        }
        int n2 = this._writeContext.writeFieldName((String)arrby);
        if (n2 == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        if (n2 == 1) {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            arrby2 = this._outputBuffer;
            n2 = this._outputTail;
            this._outputTail = n2 + 1;
            arrby2[n2] = (byte)44;
        }
        if (this._cfgUnqNames) {
            this._writeStringSegments((String)arrby, false);
            return;
        }
        int n3 = arrby.length();
        if (n3 > this._charBufferLength) {
            this._writeStringSegments((String)arrby, true);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby2 = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n = n2 + 1;
        arrby2[n2] = this._quoteChar;
        if (n3 <= this._outputMaxContiguous) {
            if (n + n3 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment((String)arrby, 0, n3);
        } else {
            this._writeStringSegments((String)arrby, 0, n3);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = this._quoteChar;
    }

    @Override
    public void writeNull() throws IOException {
        this._verifyValueWrite("write a null");
        this._writeNull();
    }

    @Override
    public void writeNumber(double d) throws IOException {
        if (!(this._cfgNumbersAsStrings || NumberOutput.notFinite(d) && JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
            this._verifyValueWrite("write a number");
            this.writeRaw(String.valueOf(d));
            return;
        }
        this.writeString(String.valueOf(d));
    }

    @Override
    public void writeNumber(float f) throws IOException {
        if (!(this._cfgNumbersAsStrings || NumberOutput.notFinite(f) && JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS.enabledIn(this._features))) {
            this._verifyValueWrite("write a number");
            this.writeRaw(String.valueOf(f));
            return;
        }
        this.writeString(String.valueOf(f));
    }

    @Override
    public void writeNumber(int n) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedInt(n);
            return;
        }
        this._outputTail = NumberOutput.outputInt(n, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeNumber(long l) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedLong(l);
            return;
        }
        if (this._outputTail + 21 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputLong(l, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeNumber(String string2) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(string2);
            return;
        }
        this.writeRaw(string2);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        this._verifyValueWrite("write a number");
        if (bigDecimal == null) {
            this._writeNull();
            return;
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(this._asString(bigDecimal));
            return;
        }
        this.writeRaw(this._asString(bigDecimal));
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException {
        this._verifyValueWrite("write a number");
        if (bigInteger == null) {
            this._writeNull();
            return;
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedRaw(bigInteger.toString());
            return;
        }
        this.writeRaw(bigInteger.toString());
    }

    @Override
    public void writeNumber(short s) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedShort(s);
            return;
        }
        this._outputTail = NumberOutput.outputInt((int)s, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeRaw(char c) throws IOException {
        if (this._outputTail + 3 >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        if (c <= '') {
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrby[n] = (byte)c;
            return;
        }
        if (c < '\u0800') {
            int n;
            int n2 = this._outputTail;
            this._outputTail = n = n2 + 1;
            arrby[n2] = (byte)(c >> 6 | 192);
            this._outputTail = n + 1;
            arrby[n] = (byte)(c & 63 | 128);
            return;
        }
        this._outputRawMultiByteChar(c, null, 0, 0);
    }

    @Override
    public void writeRaw(SerializableString serializableString) throws IOException {
        int n = serializableString.appendUnquotedUTF8(this._outputBuffer, this._outputTail);
        if (n < 0) {
            this._writeBytes(serializableString.asUnquotedUTF8());
            return;
        }
        this._outputTail += n;
    }

    @Override
    public void writeRaw(String string2) throws IOException {
        char[] arrc;
        int n = string2.length();
        if (n <= (arrc = this._charBuffer).length) {
            string2.getChars(0, n, arrc, 0);
            this.writeRaw(arrc, 0, n);
            return;
        }
        this.writeRaw(string2, 0, n);
    }

    @Override
    public void writeRaw(String string2, int n, int n2) throws IOException {
        char[] arrc = this._charBuffer;
        int n3 = arrc.length;
        if (n2 <= n3) {
            string2.getChars(n, n + n2, arrc, 0);
            this.writeRaw(arrc, 0, n2);
            return;
        }
        int n4 = this._outputEnd;
        int n5 = Math.min(n3, (n4 >> 2) + (n4 >> 4));
        while (n2 > 0) {
            n3 = Math.min(n5, n2);
            string2.getChars(n, n + n3, arrc, 0);
            if (this._outputTail + n5 * 3 > this._outputEnd) {
                this._flushBuffer();
            }
            n4 = n3;
            if (n3 > 1) {
                char c = arrc[n3 - 1];
                n4 = n3;
                if (c >= '\ud800') {
                    n4 = n3;
                    if (c <= '\udbff') {
                        n4 = n3 - 1;
                    }
                }
            }
            this._writeRawSegment(arrc, 0, n4);
            n += n4;
            n2 -= n4;
        }
    }

    @Override
    public final void writeRaw(char[] arrc, int n, int n2) throws IOException {
        int n3 = this._outputTail;
        int n4 = n2 + n2 + n2;
        int n5 = this._outputEnd;
        if (n3 + n4 > n5) {
            if (n5 < n4) {
                this._writeSegmentedRaw(arrc, n, n2);
                return;
            }
            this._flushBuffer();
        }
        n3 = n2 + n;
        block0 : do {
            if (n >= n3) return;
            do {
                byte[] arrby;
                if ((n5 = arrc[n]) > 127) {
                    n2 = n + 1;
                    n4 = arrc[n];
                    if (n4 < 2048) {
                        arrby = this._outputBuffer;
                        n5 = this._outputTail;
                        this._outputTail = n = n5 + 1;
                        arrby[n5] = (byte)(n4 >> 6 | 192);
                        this._outputTail = n + 1;
                        arrby[n] = (byte)(n4 & 63 | 128);
                        n = n2;
                        continue block0;
                    }
                    n = this._outputRawMultiByteChar(n4, arrc, n2, n3);
                    continue block0;
                }
                arrby = this._outputBuffer;
                n2 = this._outputTail;
                this._outputTail = n2 + 1;
                arrby[n2] = (byte)n5;
                n = n2 = n + 1;
            } while (n2 < n3);
            break;
        } while (true);
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n, int n2) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby2[n3] = this._quoteChar;
        this._writeBytes(arrby, n, n2);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = this._quoteChar;
    }

    @Override
    public void writeRawValue(SerializableString serializableString) throws IOException {
        this._verifyValueWrite("write a raw (unencoded) value");
        int n = serializableString.appendUnquotedUTF8(this._outputBuffer, this._outputTail);
        if (n < 0) {
            this._writeBytes(serializableString.asUnquotedUTF8());
            return;
        }
        this._outputTail += n;
    }

    @Override
    public final void writeStartArray() throws IOException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = (byte)91;
    }

    @Override
    public void writeStartArray(int n) throws IOException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = (byte)91;
    }

    @Override
    public final void writeStartObject() throws IOException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = (byte)123;
    }

    @Override
    public void writeStartObject(Object arrby) throws IOException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext(arrby);
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = (byte)123;
    }

    @Override
    public final void writeString(SerializableString arrby) throws IOException {
        int n;
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n = n2 + 1;
        arrby2[n2] = this._quoteChar;
        if ((n = arrby.appendQuotedUTF8(arrby2, n)) < 0) {
            this._writeBytes(arrby.asQuotedUTF8());
        } else {
            this._outputTail += n;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = this._quoteChar;
    }

    @Override
    public void writeString(Reader arrby, int n) throws IOException {
        this._verifyValueWrite("write a string");
        if (arrby == null) {
            this._reportError("null reader");
        }
        int n2 = n >= 0 ? n : Integer.MAX_VALUE;
        char[] arrc = this._charBuffer;
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby2[n3] = this._quoteChar;
        while (n2 > 0 && (n3 = arrby.read(arrc, 0, Math.min(n2, arrc.length))) > 0) {
            if (this._outputTail + n >= this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegments(arrc, 0, n3);
            n2 -= n3;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby[n3] = this._quoteChar;
        if (n2 <= 0) return;
        if (n < 0) return;
        this._reportError("Didn't read enough from reader");
    }

    @Override
    public void writeString(String arrby) throws IOException {
        this._verifyValueWrite("write a string");
        if (arrby == null) {
            this._writeNull();
            return;
        }
        int n = arrby.length();
        if (n > this._outputMaxContiguous) {
            this._writeStringSegments((String)arrby, true);
            return;
        }
        if (this._outputTail + n >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby2[n2] = this._quoteChar;
        this._writeStringSegment((String)arrby, 0, n);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrby[n2] = this._quoteChar;
    }

    @Override
    public void writeString(char[] arrobject, int n, int n2) throws IOException {
        int n3;
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby = this._outputBuffer;
        int n4 = this._outputTail;
        this._outputTail = n3 = n4 + 1;
        arrby[n4] = this._quoteChar;
        if (n2 <= this._outputMaxContiguous) {
            if (n3 + n2 > this._outputEnd) {
                this._flushBuffer();
            }
            this._writeStringSegment((char[])arrobject, n, n2);
        } else {
            this._writeStringSegments((char[])arrobject, n, n2);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrobject = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrobject[n] = (char)this._quoteChar;
    }

    @Override
    public void writeUTF8String(byte[] arrby, int n, int n2) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        byte[] arrby2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrby2[n3] = this._quoteChar;
        if (n2 <= this._outputMaxContiguous) {
            this._writeUTF8Segment(arrby, n, n2);
        } else {
            this._writeUTF8Segments(arrby, n, n2);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrby = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrby[n] = this._quoteChar;
    }
}

