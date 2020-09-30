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
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

public class WriterBasedJsonGenerator
extends JsonGeneratorImpl {
    protected static final char[] HEX_CHARS = CharTypes.copyHexChars();
    protected static final int SHORT_WRITE = 32;
    protected char[] _copyBuffer;
    protected SerializableString _currentEscape;
    protected char[] _entityBuffer;
    protected char[] _outputBuffer;
    protected int _outputEnd;
    protected int _outputHead;
    protected int _outputTail;
    protected char _quoteChar;
    protected final Writer _writer;

    @Deprecated
    public WriterBasedJsonGenerator(IOContext iOContext, int n, ObjectCodec objectCodec, Writer writer) {
        this(iOContext, n, objectCodec, writer, '\"');
    }

    public WriterBasedJsonGenerator(IOContext arrc, int n, ObjectCodec objectCodec, Writer writer, char c) {
        super((IOContext)arrc, n, objectCodec);
        this._writer = writer;
        arrc = arrc.allocConcatBuffer();
        this._outputBuffer = arrc;
        this._outputEnd = arrc.length;
        this._quoteChar = c;
        if (c == '\"') return;
        this._outputEscapes = CharTypes.get7BitOutputEscapes(c);
    }

    private char[] _allocateCopyBuffer() {
        if (this._copyBuffer != null) return this._copyBuffer;
        this._copyBuffer = this._ioContext.allocNameCopyBuffer(2000);
        return this._copyBuffer;
    }

    private char[] _allocateEntityBuffer() {
        char[] arrc = new char[14];
        arrc[0] = (char)92;
        arrc[2] = (char)92;
        arrc[3] = (char)117;
        arrc[4] = (char)48;
        arrc[5] = (char)48;
        arrc[8] = (char)92;
        arrc[9] = (char)117;
        this._entityBuffer = arrc;
        return arrc;
    }

    private void _appendCharacterEscape(char c, int n) throws IOException, JsonGenerationException {
        if (n >= 0) {
            int n2;
            if (this._outputTail + 2 > this._outputEnd) {
                this._flushBuffer();
            }
            char[] arrc = this._outputBuffer;
            c = (char)this._outputTail;
            this._outputTail = n2 = c + '\u0001';
            arrc[c] = (char)92;
            this._outputTail = n2 + 1;
            arrc[n2] = (char)n;
            return;
        }
        if (n != -2) {
            char[] arrc;
            if (this._outputTail + 5 >= this._outputEnd) {
                this._flushBuffer();
            }
            n = this._outputTail;
            char[] arrc2 = this._outputBuffer;
            int n3 = n + 1;
            arrc2[n] = (char)92;
            n = n3 + 1;
            arrc2[n3] = (char)117;
            if (c > '\u00ff') {
                int n4 = 255 & c >> 8;
                n3 = n + 1;
                arrc = HEX_CHARS;
                arrc2[n] = arrc[n4 >> 4];
                n = n3 + 1;
                arrc2[n3] = arrc[n4 & 15];
                c = (char)(c & 255);
            } else {
                n3 = n + 1;
                arrc2[n] = (char)48;
                n = n3 + 1;
                arrc2[n3] = (char)48;
            }
            n3 = n + 1;
            arrc = HEX_CHARS;
            arrc2[n] = arrc[c >> 4];
            arrc2[n3] = arrc[c & 15];
            this._outputTail = n3 + 1;
            return;
        }
        Object object = this._currentEscape;
        if (object == null) {
            object = this._characterEscapes.getEscapeSequence(c).getValue();
        } else {
            object = object.getValue();
            this._currentEscape = null;
        }
        c = (char)((String)object).length();
        if (this._outputTail + c > this._outputEnd) {
            this._flushBuffer();
            if (c > this._outputEnd) {
                this._writer.write((String)object);
                return;
            }
        }
        ((String)object).getChars(0, c, this._outputBuffer, this._outputTail);
        this._outputTail += c;
    }

    private int _prependOrWriteCharacterEscape(char[] arrc, int n, int n2, char c, int n3) throws IOException, JsonGenerationException {
        if (n3 >= 0) {
            if (n > 1 && n < n2) {
                arrc[n -= 2] = (char)92;
                arrc[n + 1] = (char)n3;
                return n;
            }
            char[] arrc2 = this._entityBuffer;
            arrc = arrc2;
            if (arrc2 == null) {
                arrc = this._allocateEntityBuffer();
            }
            arrc[1] = (char)n3;
            this._writer.write(arrc, 0, 2);
            return n;
        }
        if (n3 != -2) {
            if (n > 5 && n < n2) {
                char[] arrc3;
                n2 = (n -= 6) + 1;
                arrc[n] = (char)92;
                n = n2 + 1;
                arrc[n2] = (char)117;
                if (c > '\u00ff') {
                    n3 = c >> 8 & 255;
                    n2 = n + 1;
                    arrc3 = HEX_CHARS;
                    arrc[n] = arrc3[n3 >> 4];
                    n = n2 + 1;
                    arrc[n2] = arrc3[n3 & 15];
                    c = (char)(c & 255);
                } else {
                    n2 = n + 1;
                    arrc[n] = (char)48;
                    n = n2 + 1;
                    arrc[n2] = (char)48;
                }
                n2 = n + 1;
                arrc3 = HEX_CHARS;
                arrc[n] = arrc3[c >> 4];
                arrc[n2] = arrc3[c & 15];
                return n2 - 5;
            }
            char[] arrc4 = this._entityBuffer;
            arrc = arrc4;
            if (arrc4 == null) {
                arrc = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            if (c > '\u00ff') {
                n2 = c >> 8 & 255;
                c = (char)(c & 255);
                arrc4 = HEX_CHARS;
                arrc[10] = arrc4[n2 >> 4];
                arrc[11] = arrc4[n2 & 15];
                arrc[12] = arrc4[c >> 4];
                arrc[13] = arrc4[c & 15];
                this._writer.write(arrc, 8, 6);
                return n;
            }
            arrc4 = HEX_CHARS;
            arrc[6] = arrc4[c >> 4];
            arrc[7] = arrc4[c & 15];
            this._writer.write(arrc, 2, 6);
            return n;
        }
        Object object = this._currentEscape;
        if (object == null) {
            object = this._characterEscapes.getEscapeSequence(c).getValue();
        } else {
            object = object.getValue();
            this._currentEscape = null;
        }
        c = (char)((String)object).length();
        if (n >= c && n < n2) {
            ((String)object).getChars(0, c, arrc, n -= c);
            return n;
        }
        this._writer.write((String)object);
        return n;
    }

    private void _prependOrWriteCharacterEscape(char c, int n) throws IOException, JsonGenerationException {
        if (n >= 0) {
            char[] arrc;
            c = (char)this._outputTail;
            if (c >= '\u0002') {
                c = (char)(c - 2);
                this._outputHead = c;
                char[] arrc2 = this._outputBuffer;
                arrc2[c] = (char)92;
                arrc2[c + '\u0001'] = (char)n;
                return;
            }
            char[] arrc3 = arrc = this._entityBuffer;
            if (arrc == null) {
                arrc3 = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            arrc3[1] = (char)n;
            this._writer.write(arrc3, 0, 2);
            return;
        }
        if (n != -2) {
            char[] arrc;
            n = this._outputTail;
            if (n >= 6) {
                char[] arrc4;
                char[] arrc5 = this._outputBuffer;
                this._outputHead = n++;
                arrc5[n -= 6] = (char)92;
                arrc5[n] = (char)117;
                if (c > '\u00ff') {
                    int n2 = c >> 8 & 255;
                    arrc4 = HEX_CHARS;
                    arrc5[++n] = arrc4[n2 >> 4];
                    arrc5[++n] = arrc4[n2 & 15];
                    c = (char)(c & 255);
                } else {
                    arrc5[++n] = (char)48;
                    arrc5[++n] = (char)48;
                }
                arrc4 = HEX_CHARS;
                arrc5[++n] = arrc4[c >> 4];
                arrc5[n + 1] = arrc4[c & 15];
                return;
            }
            char[] arrc6 = arrc = this._entityBuffer;
            if (arrc == null) {
                arrc6 = this._allocateEntityBuffer();
            }
            this._outputHead = this._outputTail;
            if (c > '\u00ff') {
                n = c >> 8 & 255;
                c = (char)(c & 255);
                arrc = HEX_CHARS;
                arrc6[10] = arrc[n >> 4];
                arrc6[11] = arrc[n & 15];
                arrc6[12] = arrc[c >> 4];
                arrc6[13] = arrc[c & 15];
                this._writer.write(arrc6, 8, 6);
                return;
            }
            arrc = HEX_CHARS;
            arrc6[6] = arrc[c >> 4];
            arrc6[7] = arrc[c & 15];
            this._writer.write(arrc6, 2, 6);
            return;
        }
        Object object = this._currentEscape;
        if (object == null) {
            object = this._characterEscapes.getEscapeSequence(c).getValue();
        } else {
            object = object.getValue();
            this._currentEscape = null;
        }
        c = (char)((String)object).length();
        n = this._outputTail;
        if (n >= c) {
            this._outputHead = n -= c;
            ((String)object).getChars(0, c, this._outputBuffer, n);
            return;
        }
        this._outputHead = n;
        this._writer.write((String)object);
    }

    private int _readMore(InputStream inputStream2, byte[] arrby, int n, int n2, int n3) throws IOException {
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

    private final void _writeFieldNameTail(SerializableString arrc) throws IOException {
        arrc = arrc.asQuotedChars();
        this.writeRaw(arrc, 0, arrc.length);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    private void _writeLongString(String string2) throws IOException {
        this._flushBuffer();
        int n = string2.length();
        int n2 = 0;
        do {
            int n3;
            int n4 = n3 = this._outputEnd;
            if (n2 + n3 > n) {
                n4 = n - n2;
            }
            n3 = n2 + n4;
            string2.getChars(n2, n3, this._outputBuffer, 0);
            if (this._characterEscapes != null) {
                this._writeSegmentCustom(n4);
            } else if (this._maximumNonEscapedChar != 0) {
                this._writeSegmentASCII(n4, this._maximumNonEscapedChar);
            } else {
                this._writeSegment(n4);
            }
            if (n3 >= n) {
                return;
            }
            n2 = n3;
        } while (true);
    }

    private final void _writeNull() throws IOException {
        if (this._outputTail + 4 >= this._outputEnd) {
            this._flushBuffer();
        }
        int n = this._outputTail;
        char[] arrc = this._outputBuffer;
        arrc[n] = (char)110;
        arrc[++n] = (char)117;
        arrc[++n] = (char)108;
        arrc[++n] = (char)108;
        this._outputTail = n + 1;
    }

    private void _writeQuotedInt(int n) throws IOException {
        int n2;
        if (this._outputTail + 13 >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n2 = n3 + 1;
        arrc[n3] = this._quoteChar;
        this._outputTail = n = NumberOutput.outputInt(n, arrc, n2);
        arrc = this._outputBuffer;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    private void _writeQuotedLong(long l) throws IOException {
        int n;
        if (this._outputTail + 23 >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n = n2 + 1;
        arrc[n2] = this._quoteChar;
        this._outputTail = n = NumberOutput.outputLong(l, arrc, n);
        arrc = this._outputBuffer;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    private void _writeQuotedRaw(String arrc) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc2[n] = this._quoteChar;
        this.writeRaw((String)arrc);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    private void _writeQuotedShort(short s) throws IOException {
        int n;
        if (this._outputTail + 8 >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n = n2 + 1;
        arrc[n2] = this._quoteChar;
        s = (short)NumberOutput.outputInt((int)s, arrc, n);
        this._outputTail = s;
        arrc = this._outputBuffer;
        this._outputTail = s + 1;
        arrc[s] = this._quoteChar;
    }

    private void _writeSegment(int n) throws IOException {
        int[] arrn = this._outputEscapes;
        int n2 = arrn.length;
        int n3 = 0;
        int n4 = 0;
        while (n3 < n) {
            int n5;
            char c;
            while ((c = this._outputBuffer[n3]) >= n2 || arrn[c] == 0) {
                n3 = n5 = n3 + 1;
                if (n5 < n) continue;
                n3 = n5;
                break;
            }
            n5 = n3 - n4;
            if (n5 > 0) {
                this._writer.write(this._outputBuffer, n4, n5);
                if (n3 >= n) {
                    return;
                }
            }
            n4 = this._prependOrWriteCharacterEscape(this._outputBuffer, ++n3, n, c, arrn[c]);
        }
    }

    private void _writeSegmentASCII(int n, int n2) throws IOException, JsonGenerationException {
        int[] arrn = this._outputEscapes;
        int n3 = Math.min(arrn.length, n2 + 1);
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        while (n4 < n) {
            int n7;
            char c;
            block8 : {
                int n8 = n6;
                n6 = n4;
                do {
                    if ((c = this._outputBuffer[n6]) < n3) {
                        n4 = n8 = arrn[c];
                        if (n8 != 0) {
                            n7 = n6;
                            n6 = n8;
                            break block8;
                        }
                    } else {
                        n4 = n8;
                        if (c > n2) {
                            n4 = -1;
                            n7 = n6;
                            n6 = n4;
                            break block8;
                        }
                    }
                    n6 = n7 = n6 + 1;
                    n8 = n4;
                } while (n7 < n);
                n6 = n4;
            }
            n4 = n7 - n5;
            if (n4 > 0) {
                this._writer.write(this._outputBuffer, n5, n4);
                if (n7 >= n) {
                    return;
                }
            }
            n4 = n7 + 1;
            n5 = this._prependOrWriteCharacterEscape(this._outputBuffer, n4, n, c, n6);
        }
    }

    private void _writeSegmentCustom(int n) throws IOException, JsonGenerationException {
        int[] arrn = this._outputEscapes;
        char c = this._maximumNonEscapedChar < 1 ? (char)'\uffff' : this._maximumNonEscapedChar;
        int n2 = Math.min(arrn.length, c + 1);
        CharacterEscapes characterEscapes = this._characterEscapes;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (n3 < n) {
            char c2;
            int n6;
            block9 : {
                int n7;
                n6 = n5;
                n5 = n3;
                do {
                    if ((c2 = this._outputBuffer[n5]) < n2) {
                        n3 = n6 = arrn[c2];
                        if (n6 != 0) {
                            n3 = n5;
                            n5 = n6;
                            break block9;
                        }
                    } else {
                        SerializableString serializableString;
                        if (c2 > c) {
                            n6 = -1;
                            n3 = n5;
                            n5 = n6;
                            break block9;
                        }
                        this._currentEscape = serializableString = characterEscapes.getEscapeSequence(c2);
                        n3 = n6;
                        if (serializableString != null) {
                            n6 = -2;
                            n3 = n5;
                            n5 = n6;
                            break block9;
                        }
                    }
                    n5 = n7 = n5 + 1;
                    n6 = n3;
                } while (n7 < n);
                n5 = n3;
                n3 = n7;
            }
            n6 = n3 - n4;
            if (n6 > 0) {
                this._writer.write(this._outputBuffer, n4, n6);
                if (n3 >= n) {
                    return;
                }
            }
            n4 = this._prependOrWriteCharacterEscape(this._outputBuffer, ++n3, n, c2, n5);
        }
    }

    private void _writeString(String string2) throws IOException {
        int n;
        int n2 = string2.length();
        if (n2 > (n = this._outputEnd)) {
            this._writeLongString(string2);
            return;
        }
        if (this._outputTail + n2 > n) {
            this._flushBuffer();
        }
        string2.getChars(0, n2, this._outputBuffer, this._outputTail);
        if (this._characterEscapes != null) {
            this._writeStringCustom(n2);
            return;
        }
        if (this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(n2, this._maximumNonEscapedChar);
            return;
        }
        this._writeString2(n2);
    }

    private void _writeString(char[] arrc, int n, int n2) throws IOException {
        if (this._characterEscapes != null) {
            this._writeStringCustom(arrc, n, n2);
            return;
        }
        if (this._maximumNonEscapedChar != 0) {
            this._writeStringASCII(arrc, n, n2, this._maximumNonEscapedChar);
            return;
        }
        int n3 = n2 + n;
        int[] arrn = this._outputEscapes;
        int n4 = arrn.length;
        while (n < n3) {
            int n5;
            n2 = n;
            while ((n5 = arrc[n2]) >= n4 || arrn[n5] == 0) {
                n2 = n5 = n2 + 1;
                if (n5 < n3) continue;
                n2 = n5;
                break;
            }
            n5 = n2 - n;
            if (n5 < 32) {
                if (this._outputTail + n5 > this._outputEnd) {
                    this._flushBuffer();
                }
                if (n5 > 0) {
                    System.arraycopy(arrc, n, this._outputBuffer, this._outputTail, n5);
                    this._outputTail += n5;
                }
            } else {
                this._flushBuffer();
                this._writer.write(arrc, n, n5);
            }
            if (n2 >= n3) {
                return;
            }
            n = n2 + 1;
            char c = arrc[n2];
            this._appendCharacterEscape(c, arrn[c]);
        }
    }

    private void _writeString2(int n) throws IOException {
        int n2 = this._outputTail + n;
        int[] arrn = this._outputEscapes;
        n = arrn.length;
        block0 : do {
            int n3;
            if (this._outputTail >= n2) return;
            do {
                char[] arrc;
                int n4;
                if ((n4 = (arrc = this._outputBuffer)[n3 = this._outputTail]) < n && arrn[n4] != 0) {
                    n4 = this._outputHead;
                    if ((n3 -= n4) > 0) {
                        this._writer.write(arrc, n4, n3);
                    }
                    arrc = this._outputBuffer;
                    n3 = this._outputTail;
                    this._outputTail = n3 + 1;
                    char c = arrc[n3];
                    this._prependOrWriteCharacterEscape(c, arrn[c]);
                    continue block0;
                }
                this._outputTail = n3 = this._outputTail + 1;
            } while (n3 < n2);
            break;
        } while (true);
    }

    private void _writeString2(SerializableString arrc) throws IOException {
        int n = (arrc = arrc.asQuotedChars()).length;
        if (n < 32) {
            if (n > this._outputEnd - this._outputTail) {
                this._flushBuffer();
            }
            System.arraycopy(arrc, 0, this._outputBuffer, this._outputTail, n);
            this._outputTail += n;
        } else {
            this._flushBuffer();
            this._writer.write(arrc, 0, n);
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    private void _writeStringASCII(int n, int n2) throws IOException, JsonGenerationException {
        int n3 = this._outputTail + n;
        int[] arrn = this._outputEscapes;
        int n4 = Math.min(arrn.length, n2 + 1);
        block0 : do {
            if (this._outputTail >= n3) return;
            do {
                block7 : {
                    char c;
                    block8 : {
                        block6 : {
                            if ((c = this._outputBuffer[this._outputTail]) >= n4) break block6;
                            n = arrn[c];
                            if (n == 0) break block7;
                            break block8;
                        }
                        if (c <= n2) break block7;
                        n = -1;
                    }
                    int n5 = this._outputTail;
                    int n6 = this._outputHead;
                    if ((n5 -= n6) > 0) {
                        this._writer.write(this._outputBuffer, n6, n5);
                    }
                    ++this._outputTail;
                    this._prependOrWriteCharacterEscape(c, n);
                    continue block0;
                }
                this._outputTail = n = this._outputTail + 1;
            } while (n < n3);
            break;
        } while (true);
    }

    private void _writeStringASCII(char[] arrc, int n, int n2, int n3) throws IOException, JsonGenerationException {
        int n4 = n2 + n;
        int[] arrn = this._outputEscapes;
        int n5 = Math.min(arrn.length, n3 + 1);
        int n6 = 0;
        n2 = n;
        n = n6;
        while (n2 < n4) {
            char c;
            int n7;
            block11 : {
                n6 = n2;
                int n8 = n;
                do {
                    if ((c = arrc[n6]) < n5) {
                        n = n7 = arrn[c];
                        if (n7 != 0) {
                            n = n7;
                            break block11;
                        }
                    } else {
                        n = n8;
                        if (c > n3) {
                            n = -1;
                            break block11;
                        }
                    }
                    n7 = n6 + 1;
                    n8 = n;
                    n6 = n7;
                } while (n7 < n4);
                n6 = n7;
            }
            n7 = n6 - n2;
            if (n7 < 32) {
                if (this._outputTail + n7 > this._outputEnd) {
                    this._flushBuffer();
                }
                if (n7 > 0) {
                    System.arraycopy(arrc, n2, this._outputBuffer, this._outputTail, n7);
                    this._outputTail += n7;
                }
            } else {
                this._flushBuffer();
                this._writer.write(arrc, n2, n7);
            }
            if (n6 >= n4) {
                return;
            }
            n2 = n6 + 1;
            this._appendCharacterEscape(c, n);
        }
    }

    private void _writeStringCustom(int n) throws IOException, JsonGenerationException {
        int n2 = this._outputTail + n;
        int[] arrn = this._outputEscapes;
        char c = this._maximumNonEscapedChar < 1 ? (char)'\uffff' : this._maximumNonEscapedChar;
        int n3 = Math.min(arrn.length, c + 1);
        CharacterEscapes characterEscapes = this._characterEscapes;
        block0 : do {
            if (this._outputTail >= n2) return;
            do {
                block7 : {
                    char c2;
                    block8 : {
                        SerializableString serializableString;
                        block9 : {
                            block6 : {
                                if ((c2 = this._outputBuffer[this._outputTail]) >= n3) break block6;
                                n = arrn[c2];
                                if (n == 0) break block7;
                                break block8;
                            }
                            if (c2 <= c) break block9;
                            n = -1;
                            break block8;
                        }
                        this._currentEscape = serializableString = characterEscapes.getEscapeSequence(c2);
                        if (serializableString == null) break block7;
                        n = -2;
                    }
                    int n4 = this._outputTail;
                    int n5 = this._outputHead;
                    if ((n4 -= n5) > 0) {
                        this._writer.write(this._outputBuffer, n5, n4);
                    }
                    ++this._outputTail;
                    this._prependOrWriteCharacterEscape(c2, n);
                    continue block0;
                }
                this._outputTail = n = this._outputTail + 1;
            } while (n < n2);
            break;
        } while (true);
    }

    private void _writeStringCustom(char[] arrc, int n, int n2) throws IOException, JsonGenerationException {
        int n3 = n2 + n;
        int[] arrn = this._outputEscapes;
        char c = this._maximumNonEscapedChar < 1 ? (char)'\uffff' : this._maximumNonEscapedChar;
        int n4 = Math.min(arrn.length, c + 1);
        CharacterEscapes characterEscapes = this._characterEscapes;
        int n5 = 0;
        n2 = n;
        n = n5;
        while (n2 < n3) {
            int n6;
            char c2;
            block12 : {
                n5 = n2;
                int n7 = n;
                do {
                    if ((c2 = arrc[n5]) < n4) {
                        n = n6 = arrn[c2];
                        if (n6 != 0) {
                            n = n6;
                            break block12;
                        }
                    } else {
                        SerializableString serializableString;
                        if (c2 > c) {
                            n = -1;
                            break block12;
                        }
                        this._currentEscape = serializableString = characterEscapes.getEscapeSequence(c2);
                        n = n7;
                        if (serializableString != null) {
                            n = -2;
                            break block12;
                        }
                    }
                    n6 = n5 + 1;
                    n7 = n;
                    n5 = n6;
                } while (n6 < n3);
                n5 = n6;
            }
            n6 = n5 - n2;
            if (n6 < 32) {
                if (this._outputTail + n6 > this._outputEnd) {
                    this._flushBuffer();
                }
                if (n6 > 0) {
                    System.arraycopy(arrc, n2, this._outputBuffer, this._outputTail, n6);
                    this._outputTail += n6;
                }
            } else {
                this._flushBuffer();
                this._writer.write(arrc, n2, n6);
            }
            if (n5 >= n3) {
                return;
            }
            n2 = n5 + 1;
            this._appendCharacterEscape(c2, n);
        }
    }

    private void writeRawLong(String string2) throws IOException {
        int n = this._outputEnd;
        int n2 = this._outputTail;
        string2.getChars(0, n -= n2, this._outputBuffer, n2);
        this._outputTail += n;
        this._flushBuffer();
        n2 = string2.length() - n;
        do {
            int n3;
            if (n2 <= (n3 = this._outputEnd)) {
                string2.getChars(n, n + n2, this._outputBuffer, 0);
                this._outputHead = 0;
                this._outputTail = n2;
                return;
            }
            int n4 = n + n3;
            string2.getChars(n, n4, this._outputBuffer, 0);
            this._outputHead = 0;
            this._outputTail = n3;
            this._flushBuffer();
            n2 -= n3;
            n = n4;
        } while (true);
    }

    protected void _flushBuffer() throws IOException {
        int n = this._outputTail;
        int n2 = this._outputHead;
        if ((n -= n2) <= 0) return;
        this._outputHead = 0;
        this._outputTail = 0;
        this._writer.write(this._outputBuffer, n2, n);
    }

    @Override
    protected void _releaseBuffers() {
        char[] arrc = this._outputBuffer;
        if (arrc != null) {
            this._outputBuffer = null;
            this._ioContext.releaseConcatBuffer(arrc);
        }
        if ((arrc = this._copyBuffer) == null) return;
        this._copyBuffer = null;
        this._ioContext.releaseNameCopyBuffer(arrc);
    }

    @Override
    protected final void _verifyValueWrite(String arrc) throws IOException {
        int n = this._writeContext.writeValue();
        if (this._cfgPrettyPrinter != null) {
            this._verifyPrettyValueWrite((String)arrc, n);
            return;
        }
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    if (this._rootValueSeparator == null) return;
                    this.writeRaw(this._rootValueSeparator.getValue());
                    return;
                }
                if (n != 5) {
                    return;
                }
                this._reportCantWriteValueExpectName((String)arrc);
                return;
            }
            n = 58;
        } else {
            n = 44;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = (char)n;
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
            char[] arrc = this._outputBuffer;
            this._outputTail = n2 = n8 + 1;
            arrc[n8] = (char)92;
            this._outputTail = n2 + 1;
            arrc[n2] = (char)110;
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
            char[] arrc = this._outputBuffer;
            this._outputTail = n = n10 + 1;
            arrc[n10] = (char)92;
            this._outputTail = n + 1;
            arrc[n] = (char)110;
            n6 = base64Variant.getMaxLineLength() >> 2;
            n7 = n2;
            n = n12;
            n9 = n8;
            n4 = n13;
        } while (true);
        n = n4;
        if (n4 <= 0) return n;
        n6 = this._readMore(inputStream2, arrby, n8, n2, n4);
        n = n4;
        if (n6 <= 0) return n;
        if (this._outputTail > n3) {
            this._flushBuffer();
        }
        n = arrby[0] << 16;
        if (1 < n6) {
            n |= (arrby[1] & 255) << 8;
            n6 = n5;
        } else {
            n6 = 1;
        }
        this._outputTail = base64Variant.encodeBase64Partial(n, n6, this._outputBuffer, this._outputTail);
        return n4 - n6;
    }

    protected final void _writeBinary(Base64Variant base64Variant, byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        int n3;
        int n4 = this._outputEnd - 6;
        int n5 = base64Variant.getMaxLineLength() >> 2;
        while (n <= n2 - 3) {
            if (this._outputTail > n4) {
                this._flushBuffer();
            }
            int n6 = n + 1;
            n = arrby[n];
            n3 = n6 + 1;
            this._outputTail = n6 = base64Variant.encodeBase64Chunk((n << 8 | arrby[n6] & 255) << 8 | arrby[n3] & 255, this._outputBuffer, this._outputTail);
            n = --n5;
            if (n5 <= 0) {
                char[] arrc = this._outputBuffer;
                this._outputTail = n = n6 + 1;
                arrc[n6] = (char)92;
                this._outputTail = n + 1;
                arrc[n] = (char)110;
                n = base64Variant.getMaxLineLength() >> 2;
            }
            n5 = n;
            n = ++n3;
        }
        n3 = n2 - n;
        if (n3 <= 0) return;
        if (this._outputTail > n4) {
            this._flushBuffer();
        }
        n2 = n5 = arrby[n] << 16;
        if (n3 == 2) {
            n2 = n5 | (arrby[n + 1] & 255) << 8;
        }
        this._outputTail = base64Variant.encodeBase64Partial(n2, n3, this._outputBuffer, this._outputTail);
    }

    protected final void _writeFieldName(SerializableString arrc, boolean bl) throws IOException {
        int n;
        char[] arrc2;
        int n2;
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName((SerializableString)arrc, bl);
            return;
        }
        if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (bl) {
            arrc2 = this._outputBuffer;
            n = this._outputTail;
            this._outputTail = n + 1;
            arrc2[n] = (char)44;
        }
        if (this._cfgUnqNames) {
            arrc = arrc.asQuotedChars();
            this.writeRaw(arrc, 0, arrc.length);
            return;
        }
        arrc2 = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n2 = n + 1;
        arrc2[n] = this._quoteChar;
        n = arrc.appendQuoted(arrc2, n2);
        if (n < 0) {
            this._writeFieldNameTail((SerializableString)arrc);
            return;
        }
        this._outputTail = n = this._outputTail + n;
        if (n >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    protected final void _writeFieldName(String arrc, boolean bl) throws IOException {
        int n;
        char[] arrc2;
        if (this._cfgPrettyPrinter != null) {
            this._writePPFieldName((String)arrc, bl);
            return;
        }
        if (this._outputTail + 1 >= this._outputEnd) {
            this._flushBuffer();
        }
        if (bl) {
            arrc2 = this._outputBuffer;
            n = this._outputTail;
            this._outputTail = n + 1;
            arrc2[n] = (char)44;
        }
        if (this._cfgUnqNames) {
            this._writeString((String)arrc);
            return;
        }
        arrc2 = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc2[n] = this._quoteChar;
        this._writeString((String)arrc);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    protected final void _writePPFieldName(SerializableString arrc, boolean bl) throws IOException {
        if (bl) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        arrc = arrc.asQuotedChars();
        if (this._cfgUnqNames) {
            this.writeRaw(arrc, 0, arrc.length);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc2[n] = this._quoteChar;
        this.writeRaw(arrc, 0, arrc.length);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    protected final void _writePPFieldName(String arrc, boolean bl) throws IOException {
        if (bl) {
            this._cfgPrettyPrinter.writeObjectEntrySeparator(this);
        } else {
            this._cfgPrettyPrinter.beforeObjectEntries(this);
        }
        if (this._cfgUnqNames) {
            this._writeString((String)arrc);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc2[n] = this._quoteChar;
        this._writeString((String)arrc);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    @Override
    public boolean canWriteFormattedNumbers() {
        return true;
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
        this._outputHead = 0;
        this._outputTail = 0;
        if (this._writer != null) {
            if (!this._ioContext.isResourceManaged() && !this.isEnabled(JsonGenerator.Feature.AUTO_CLOSE_TARGET)) {
                if (this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) {
                    this._writer.flush();
                }
            } else {
                this._writer.close();
            }
        }
        this._releaseBuffers();
    }

    @Override
    public void flush() throws IOException {
        this._flushBuffer();
        if (this._writer == null) return;
        if (!this.isEnabled(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM)) return;
        this._writer.flush();
    }

    @Override
    public int getOutputBuffered() {
        return Math.max(0, this._outputTail - this._outputHead);
    }

    @Override
    public Object getOutputTarget() {
        return this._writer;
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
            var5_7 = this._outputTail;
            this._outputTail = var5_7 + 1;
            var4_5[var5_7] = this._quoteChar;
            var4_6 = this._ioContext.allocBase64Buffer();
            if (var3_4 >= 0) ** GOTO lbl13
            try {
                block5 : {
                    var5_7 = this._writeBinary((Base64Variant)var1_1, var2_3, var4_6);
                    break block5;
lbl13: // 1 sources:
                    var6_8 = this._writeBinary((Base64Variant)var1_1, var2_3, var4_6, var3_4);
                    var5_7 = var3_4;
                    if (var6_8 > 0) {
                        var1_1 = new StringBuilder();
                        var1_1.append("Too few bytes available: missing ");
                        var1_1.append(var6_8);
                        var1_1.append(" bytes (out of ");
                        var1_1.append(var3_4);
                        var1_1.append(")");
                        this._reportError(var1_1.toString());
                        var5_7 = var3_4;
                    }
                }
                this._ioContext.releaseBase64Buffer(var4_6);
                if (this._outputTail < this._outputEnd) break block4;
            }
            catch (Throwable var1_2) {
                this._ioContext.releaseBase64Buffer(var4_6);
                throw var1_2;
            }
            this._flushBuffer();
        }
        var1_1 = this._outputBuffer;
        var3_4 = this._outputTail;
        this._outputTail = var3_4 + 1;
        var1_1[var3_4] = (Serializable)((char)this._quoteChar);
        return var5_7;
    }

    @Override
    public void writeBinary(Base64Variant arrc, byte[] arrby, int n, int n2) throws IOException, JsonGenerationException {
        this._verifyValueWrite("write a binary value");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrc2[n3] = this._quoteChar;
        this._writeBinary((Base64Variant)arrc, arrby, n, n2 + n);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        this._verifyValueWrite("write a boolean value");
        if (this._outputTail + 5 >= this._outputEnd) {
            this._flushBuffer();
        }
        int n = this._outputTail;
        char[] arrc = this._outputBuffer;
        if (bl) {
            arrc[n] = (char)116;
            arrc[++n] = (char)114;
            arrc[++n] = (char)117;
            arrc[++n] = (char)101;
        } else {
            arrc[n] = (char)102;
            arrc[++n] = (char)97;
            arrc[++n] = (char)108;
            arrc[++n] = (char)115;
            arrc[++n] = (char)101;
        }
        this._outputTail = n + 1;
    }

    @Override
    public void writeEndArray() throws IOException {
        char[] arrc;
        if (!this._writeContext.inArray()) {
            arrc = new StringBuilder();
            arrc.append("Current context not Array but ");
            arrc.append(this._writeContext.typeDesc());
            this._reportError(arrc.toString());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndArray(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            arrc = this._outputBuffer;
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrc[n] = (char)93;
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }

    @Override
    public void writeEndObject() throws IOException {
        char[] arrc;
        if (!this._writeContext.inObject()) {
            arrc = new StringBuilder();
            arrc.append("Current context not Object but ");
            arrc.append(this._writeContext.typeDesc());
            this._reportError(arrc.toString());
        }
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeEndObject(this, this._writeContext.getEntryCount());
        } else {
            if (this._outputTail >= this._outputEnd) {
                this._flushBuffer();
            }
            arrc = this._outputBuffer;
            int n = this._outputTail;
            this._outputTail = n + 1;
            arrc[n] = (char)125;
        }
        this._writeContext = this._writeContext.clearAndGetParent();
    }

    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException {
        int n = this._writeContext.writeFieldName(serializableString.getValue());
        if (n == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this._writeFieldName(serializableString, bl);
    }

    @Override
    public void writeFieldName(String string2) throws IOException {
        int n = this._writeContext.writeFieldName(string2);
        if (n == 4) {
            this._reportError("Can not write a field name, expecting a value");
        }
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this._writeFieldName(string2, bl);
    }

    @Override
    public void writeNull() throws IOException {
        this._verifyValueWrite("write a null");
        this._writeNull();
    }

    @Override
    public void writeNumber(double d) throws IOException {
        if (!(this._cfgNumbersAsStrings || NumberOutput.notFinite(d) && this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))) {
            this._verifyValueWrite("write a number");
            this.writeRaw(String.valueOf(d));
            return;
        }
        this.writeString(String.valueOf(d));
    }

    @Override
    public void writeNumber(float f) throws IOException {
        if (!(this._cfgNumbersAsStrings || NumberOutput.notFinite(f) && this.isEnabled(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS))) {
            this._verifyValueWrite("write a number");
            this.writeRaw(String.valueOf(f));
            return;
        }
        this.writeString(String.valueOf(f));
    }

    @Override
    public void writeNumber(int n) throws IOException {
        this._verifyValueWrite("write a number");
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedInt(n);
            return;
        }
        if (this._outputTail + 11 >= this._outputEnd) {
            this._flushBuffer();
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
        if (this._cfgNumbersAsStrings) {
            this._writeQuotedShort(s);
            return;
        }
        if (this._outputTail + 6 >= this._outputEnd) {
            this._flushBuffer();
        }
        this._outputTail = NumberOutput.outputInt((int)s, this._outputBuffer, this._outputTail);
    }

    @Override
    public void writeRaw(char c) throws IOException {
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = c;
    }

    @Override
    public void writeRaw(SerializableString serializableString) throws IOException {
        int n = serializableString.appendUnquoted(this._outputBuffer, this._outputTail);
        if (n < 0) {
            this.writeRaw(serializableString.getValue());
            return;
        }
        this._outputTail += n;
    }

    @Override
    public void writeRaw(String string2) throws IOException {
        int n;
        int n2 = string2.length();
        int n3 = n = this._outputEnd - this._outputTail;
        if (n == 0) {
            this._flushBuffer();
            n3 = this._outputEnd - this._outputTail;
        }
        if (n3 >= n2) {
            string2.getChars(0, n2, this._outputBuffer, this._outputTail);
            this._outputTail += n2;
            return;
        }
        this.writeRawLong(string2);
    }

    @Override
    public void writeRaw(String string2, int n, int n2) throws IOException {
        int n3;
        int n4 = n3 = this._outputEnd - this._outputTail;
        if (n3 < n2) {
            this._flushBuffer();
            n4 = this._outputEnd - this._outputTail;
        }
        if (n4 >= n2) {
            string2.getChars(n, n + n2, this._outputBuffer, this._outputTail);
            this._outputTail += n2;
            return;
        }
        this.writeRawLong(string2.substring(n, n2 + n));
    }

    @Override
    public void writeRaw(char[] arrc, int n, int n2) throws IOException {
        if (n2 >= 32) {
            this._flushBuffer();
            this._writer.write(arrc, n, n2);
            return;
        }
        if (n2 > this._outputEnd - this._outputTail) {
            this._flushBuffer();
        }
        System.arraycopy(arrc, n, this._outputBuffer, this._outputTail, n2);
        this._outputTail += n2;
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n, int n2) throws IOException {
        this._reportUnsupportedOperation();
    }

    @Override
    public void writeStartArray() throws IOException {
        this._verifyValueWrite("start an array");
        this._writeContext = this._writeContext.createChildArrayContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartArray(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = (char)91;
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
        char[] arrc = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = (char)91;
    }

    @Override
    public void writeStartObject() throws IOException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext();
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = (char)123;
    }

    @Override
    public void writeStartObject(Object arrc) throws IOException {
        this._verifyValueWrite("start an object");
        this._writeContext = this._writeContext.createChildObjectContext(arrc);
        if (this._cfgPrettyPrinter != null) {
            this._cfgPrettyPrinter.writeStartObject(this);
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = (char)123;
    }

    @Override
    public void writeString(SerializableString arrc) throws IOException {
        int n;
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n2 = this._outputTail;
        this._outputTail = n = n2 + 1;
        arrc2[n2] = this._quoteChar;
        n2 = arrc.appendQuoted(arrc2, n);
        if (n2 < 0) {
            this._writeString2((SerializableString)arrc);
            return;
        }
        this._outputTail = n2 = this._outputTail + n2;
        if (n2 >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n2 = this._outputTail;
        this._outputTail = n2 + 1;
        arrc[n2] = this._quoteChar;
    }

    @Override
    public void writeString(Reader arrc, int n) throws IOException {
        this._verifyValueWrite("write a string");
        if (arrc == null) {
            this._reportError("null reader");
        }
        int n2 = n >= 0 ? n : Integer.MAX_VALUE;
        if (this._outputTail + n >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrc2[n3] = this._quoteChar;
        arrc2 = this._allocateCopyBuffer();
        while (n2 > 0 && (n3 = arrc.read(arrc2, 0, Math.min(n2, arrc2.length))) > 0) {
            if (this._outputTail + n >= this._outputEnd) {
                this._flushBuffer();
            }
            this._writeString(arrc2, 0, n3);
            n2 -= n3;
        }
        if (this._outputTail + n >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrc[n3] = this._quoteChar;
        if (n2 <= 0) return;
        if (n < 0) return;
        this._reportError("Didn't read enough from reader");
    }

    @Override
    public void writeString(String arrc) throws IOException {
        this._verifyValueWrite("write a string");
        if (arrc == null) {
            this._writeNull();
            return;
        }
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n = this._outputTail;
        this._outputTail = n + 1;
        arrc2[n] = this._quoteChar;
        this._writeString((String)arrc);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    @Override
    public void writeString(char[] arrc, int n, int n2) throws IOException {
        this._verifyValueWrite("write a string");
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        char[] arrc2 = this._outputBuffer;
        int n3 = this._outputTail;
        this._outputTail = n3 + 1;
        arrc2[n3] = this._quoteChar;
        this._writeString(arrc, n, n2);
        if (this._outputTail >= this._outputEnd) {
            this._flushBuffer();
        }
        arrc = this._outputBuffer;
        n = this._outputTail;
        this._outputTail = n + 1;
        arrc[n] = this._quoteChar;
    }

    @Override
    public void writeUTF8String(byte[] arrby, int n, int n2) throws IOException {
        this._reportUnsupportedOperation();
    }
}

