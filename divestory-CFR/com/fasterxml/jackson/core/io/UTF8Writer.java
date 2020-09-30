/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.IOContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public final class UTF8Writer
extends Writer {
    static final int SURR1_FIRST = 55296;
    static final int SURR1_LAST = 56319;
    static final int SURR2_FIRST = 56320;
    static final int SURR2_LAST = 57343;
    private final IOContext _context;
    private OutputStream _out;
    private byte[] _outBuffer;
    private final int _outBufferEnd;
    private int _outPtr;
    private int _surrogate;

    public UTF8Writer(IOContext arrby, OutputStream outputStream2) {
        this._context = arrby;
        this._out = outputStream2;
        arrby = arrby.allocWriteEncodingBuffer();
        this._outBuffer = arrby;
        this._outBufferEnd = arrby.length - 4;
        this._outPtr = 0;
    }

    protected static void illegalSurrogate(int n) throws IOException {
        throw new IOException(UTF8Writer.illegalSurrogateDesc(n));
    }

    protected static String illegalSurrogateDesc(int n) {
        if (n > 1114111) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal character point (0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(") to output; max is 0x10FFFF as per RFC 4627");
            return stringBuilder.toString();
        }
        if (n < 55296) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal character point (0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(") to output");
            return stringBuilder.toString();
        }
        if (n <= 56319) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unmatched first part of surrogate pair (0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unmatched second part of surrogate pair (0x");
        stringBuilder.append(Integer.toHexString(n));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public Writer append(char c) throws IOException {
        this.write(c);
        return this;
    }

    @Override
    public void close() throws IOException {
        byte[] arrby = this._out;
        if (arrby == null) return;
        int n = this._outPtr;
        if (n > 0) {
            arrby.write(this._outBuffer, 0, n);
            this._outPtr = 0;
        }
        OutputStream outputStream2 = this._out;
        this._out = null;
        arrby = this._outBuffer;
        if (arrby != null) {
            this._outBuffer = null;
            this._context.releaseWriteEncodingBuffer(arrby);
        }
        outputStream2.close();
        n = this._surrogate;
        this._surrogate = 0;
        if (n <= 0) return;
        UTF8Writer.illegalSurrogate(n);
    }

    protected int convertSurrogate(int n) throws IOException {
        int n2 = this._surrogate;
        this._surrogate = 0;
        if (n >= 56320 && n <= 57343) {
            return (n2 - 55296 << 10) + 65536 + (n - 56320);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Broken surrogate pair: first char 0x");
        stringBuilder.append(Integer.toHexString(n2));
        stringBuilder.append(", second 0x");
        stringBuilder.append(Integer.toHexString(n));
        stringBuilder.append("; illegal combination");
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public void flush() throws IOException {
        OutputStream outputStream2 = this._out;
        if (outputStream2 == null) return;
        int n = this._outPtr;
        if (n > 0) {
            outputStream2.write(this._outBuffer, 0, n);
            this._outPtr = 0;
        }
        this._out.flush();
    }

    @Override
    public void write(int n) throws IOException {
        int n2;
        if (this._surrogate > 0) {
            n2 = this.convertSurrogate(n);
        } else {
            n2 = n;
            if (n >= 55296) {
                n2 = n;
                if (n <= 57343) {
                    if (n > 56319) {
                        UTF8Writer.illegalSurrogate(n);
                    }
                    this._surrogate = n;
                    return;
                }
            }
        }
        n = this._outPtr;
        if (n >= this._outBufferEnd) {
            this._out.write(this._outBuffer, 0, n);
            this._outPtr = 0;
        }
        if (n2 < 128) {
            byte[] arrby = this._outBuffer;
            n = this._outPtr;
            this._outPtr = n + 1;
            arrby[n] = (byte)n2;
            return;
        }
        n = this._outPtr;
        if (n2 < 2048) {
            byte[] arrby = this._outBuffer;
            int n3 = n + 1;
            arrby[n] = (byte)(n2 >> 6 | 192);
            n = n3 + 1;
            arrby[n3] = (byte)(n2 & 63 | 128);
        } else if (n2 <= 65535) {
            byte[] arrby = this._outBuffer;
            int n4 = n + 1;
            arrby[n] = (byte)(n2 >> 12 | 224);
            n = n4 + 1;
            arrby[n4] = (byte)(n2 >> 6 & 63 | 128);
            arrby[n] = (byte)(n2 & 63 | 128);
            ++n;
        } else {
            if (n2 > 1114111) {
                UTF8Writer.illegalSurrogate(n2);
            }
            byte[] arrby = this._outBuffer;
            int n5 = n + 1;
            arrby[n] = (byte)(n2 >> 18 | 240);
            n = n5 + 1;
            arrby[n5] = (byte)(n2 >> 12 & 63 | 128);
            n5 = n + 1;
            arrby[n] = (byte)(n2 >> 6 & 63 | 128);
            n = n5 + 1;
            arrby[n5] = (byte)(n2 & 63 | 128);
        }
        this._outPtr = n;
    }

    @Override
    public void write(String string2) throws IOException {
        this.write(string2, 0, string2.length());
    }

    @Override
    public void write(String string2, int n, int n2) throws IOException {
        if (n2 < 2) {
            if (n2 != 1) return;
            this.write(string2.charAt(n));
            return;
        }
        int n3 = n;
        int n4 = n2;
        if (this._surrogate > 0) {
            n3 = string2.charAt(n);
            n4 = n2 - 1;
            this.write(this.convertSurrogate(n3));
            n3 = n + 1;
        }
        n = this._outPtr;
        byte[] arrby = this._outBuffer;
        int n5 = this._outBufferEnd;
        int n6 = n4 + n3;
        n2 = n3;
        block0 : do {
            n3 = n;
            if (n2 >= n6) break;
            n3 = n;
            if (n >= n5) {
                this._out.write(arrby, 0, n);
                n3 = 0;
            }
            n4 = n2 + 1;
            int n7 = string2.charAt(n2);
            n2 = n3;
            n = n4;
            int n8 = n7;
            if (n7 < 128) {
                n = n3 + 1;
                arrby[n3] = (byte)n7;
                n8 = n6 - n4;
                n2 = n5 - n;
                n3 = n8;
                if (n8 > n2) {
                    n3 = n2;
                }
                n2 = n4;
                do {
                    if (n2 >= n3 + n4) continue block0;
                    n8 = n2 + 1;
                    n7 = string2.charAt(n2);
                    if (n7 >= 128) {
                        n2 = n;
                        n = n8;
                        n8 = n7;
                        break;
                    }
                    int n9 = n + 1;
                    arrby[n] = (byte)n7;
                    n2 = n8;
                    n = n9;
                } while (true);
            }
            if (n8 < 2048) {
                n4 = n2 + 1;
                arrby[n2] = (byte)(n8 >> 6 | 192);
                n3 = n4 + 1;
                arrby[n4] = (byte)(n8 & 63 | 128);
                n2 = n;
                n = n3;
                continue;
            }
            if (n8 >= 55296 && n8 <= 57343) {
                if (n8 > 56319) {
                    this._outPtr = n2;
                    UTF8Writer.illegalSurrogate(n8);
                }
                this._surrogate = n8;
                if (n >= n6) {
                    n3 = n2;
                    break;
                }
                n3 = n + 1;
                n4 = this.convertSurrogate(string2.charAt(n));
                if (n4 > 1114111) {
                    this._outPtr = n2;
                    UTF8Writer.illegalSurrogate(n4);
                }
                n8 = n2 + 1;
                arrby[n2] = (byte)(n4 >> 18 | 240);
                n = n8 + 1;
                arrby[n8] = (byte)(n4 >> 12 & 63 | 128);
                n2 = n + 1;
                arrby[n] = (byte)(n4 >> 6 & 63 | 128);
                n = n2 + 1;
                arrby[n2] = (byte)(n4 & 63 | 128);
                n2 = n3;
                continue;
            }
            n4 = n2 + 1;
            arrby[n2] = (byte)(n8 >> 12 | 224);
            n3 = n4 + 1;
            arrby[n4] = (byte)(n8 >> 6 & 63 | 128);
            arrby[n3] = (byte)(n8 & 63 | 128);
            n2 = n;
            n = n3 + 1;
        } while (true);
        this._outPtr = n3;
    }

    @Override
    public void write(char[] arrc) throws IOException {
        this.write(arrc, 0, arrc.length);
    }

    @Override
    public void write(char[] arrc, int n, int n2) throws IOException {
        if (n2 < 2) {
            if (n2 != 1) return;
            this.write(arrc[n]);
            return;
        }
        int n3 = n;
        int n4 = n2;
        if (this._surrogate > 0) {
            n3 = arrc[n];
            n4 = n2 - 1;
            this.write(this.convertSurrogate(n3));
            n3 = n + 1;
        }
        n = this._outPtr;
        byte[] arrby = this._outBuffer;
        int n5 = this._outBufferEnd;
        int n6 = n4 + n3;
        n2 = n3;
        block0 : do {
            n3 = n;
            if (n2 >= n6) break;
            n3 = n;
            if (n >= n5) {
                this._out.write(arrby, 0, n);
                n3 = 0;
            }
            n4 = n2 + 1;
            int n7 = arrc[n2];
            n2 = n3;
            n = n4;
            int n8 = n7;
            if (n7 < 128) {
                n = n3 + 1;
                arrby[n3] = (byte)n7;
                n2 = n6 - n4;
                n8 = n5 - n;
                n3 = n2;
                if (n2 > n8) {
                    n3 = n8;
                }
                n2 = n4;
                do {
                    if (n2 >= n3 + n4) continue block0;
                    n8 = n2 + 1;
                    n7 = arrc[n2];
                    if (n7 >= 128) {
                        n2 = n;
                        n = n8;
                        n8 = n7;
                        break;
                    }
                    int n9 = n + 1;
                    arrby[n] = (byte)n7;
                    n2 = n8;
                    n = n9;
                } while (true);
            }
            if (n8 < 2048) {
                n4 = n2 + 1;
                arrby[n2] = (byte)(n8 >> 6 | 192);
                n3 = n4 + 1;
                arrby[n4] = (byte)(n8 & 63 | 128);
                n2 = n;
                n = n3;
                continue;
            }
            if (n8 >= 55296 && n8 <= 57343) {
                if (n8 > 56319) {
                    this._outPtr = n2;
                    UTF8Writer.illegalSurrogate(n8);
                }
                this._surrogate = n8;
                if (n >= n6) {
                    n3 = n2;
                    break;
                }
                n3 = n + 1;
                n4 = this.convertSurrogate(arrc[n]);
                if (n4 > 1114111) {
                    this._outPtr = n2;
                    UTF8Writer.illegalSurrogate(n4);
                }
                n = n2 + 1;
                arrby[n2] = (byte)(n4 >> 18 | 240);
                n8 = n + 1;
                arrby[n] = (byte)(n4 >> 12 & 63 | 128);
                n2 = n8 + 1;
                arrby[n8] = (byte)(n4 >> 6 & 63 | 128);
                n = n2 + 1;
                arrby[n2] = (byte)(n4 & 63 | 128);
                n2 = n3;
                continue;
            }
            n4 = n2 + 1;
            arrby[n2] = (byte)(n8 >> 12 | 224);
            n3 = n4 + 1;
            arrby[n4] = (byte)(n8 >> 6 & 63 | 128);
            arrby[n3] = (byte)(n8 & 63 | 128);
            n2 = n;
            n = n3 + 1;
        } while (true);
        this._outPtr = n3;
    }
}

