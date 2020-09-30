/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.IOContext;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class UTF32Reader
extends Reader {
    protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
    protected static final char NC = '\u0000';
    protected final boolean _bigEndian;
    protected byte[] _buffer;
    protected int _byteCount;
    protected int _charCount;
    protected final IOContext _context;
    protected InputStream _in;
    protected int _length;
    protected final boolean _managedBuffers;
    protected int _ptr;
    protected char _surrogate;
    protected char[] _tmpBuf;

    public UTF32Reader(IOContext iOContext, InputStream inputStream2, byte[] arrby, int n, int n2, boolean bl) {
        boolean bl2 = false;
        this._surrogate = (char)(false ? 1 : 0);
        this._context = iOContext;
        this._in = inputStream2;
        this._buffer = arrby;
        this._ptr = n;
        this._length = n2;
        this._bigEndian = bl;
        bl = bl2;
        if (inputStream2 != null) {
            bl = true;
        }
        this._managedBuffers = bl;
    }

    private void freeBuffers() {
        byte[] arrby = this._buffer;
        if (arrby == null) return;
        this._buffer = null;
        this._context.releaseReadIOBuffer(arrby);
    }

    private boolean loadMore(int n) throws IOException {
        Object object;
        this._byteCount += this._length - n;
        if (n > 0) {
            int n2 = this._ptr;
            if (n2 > 0) {
                object = this._buffer;
                System.arraycopy(object, n2, object, 0, n);
                this._ptr = 0;
            }
            this._length = n;
        } else {
            this._ptr = 0;
            object = this._in;
            n = object == null ? -1 : ((InputStream)object).read(this._buffer);
            if (n < 1) {
                this._length = 0;
                if (n < 0) {
                    if (!this._managedBuffers) return false;
                    this.freeBuffers();
                    return false;
                }
                this.reportStrangeStream();
            }
            this._length = n;
        }
        while ((n = this._length) < 4) {
            object = this._in;
            if (object == null) {
                n = -1;
            } else {
                byte[] arrby = this._buffer;
                n = ((InputStream)object).read(arrby, n, arrby.length - n);
            }
            if (n < 1) {
                if (n < 0) {
                    if (this._managedBuffers) {
                        this.freeBuffers();
                    }
                    this.reportUnexpectedEOF(this._length, 4);
                }
                this.reportStrangeStream();
            }
            this._length += n;
        }
        return true;
    }

    private void reportBounds(char[] arrc, int n, int n2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("read(buf,");
        stringBuilder.append(n);
        stringBuilder.append(",");
        stringBuilder.append(n2);
        stringBuilder.append("), cbuf[");
        stringBuilder.append(arrc.length);
        stringBuilder.append("]");
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    private void reportInvalid(int n, int n2, String string2) throws IOException {
        int n3 = this._byteCount;
        int n4 = this._ptr;
        int n5 = this._charCount;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid UTF-32 character 0x");
        stringBuilder.append(Integer.toHexString(n));
        stringBuilder.append(string2);
        stringBuilder.append(" at char #");
        stringBuilder.append(n5 + n2);
        stringBuilder.append(", byte #");
        stringBuilder.append(n3 + n4 - 1);
        stringBuilder.append(")");
        throw new CharConversionException(stringBuilder.toString());
    }

    private void reportStrangeStream() throws IOException {
        throw new IOException("Strange I/O stream, returned 0 bytes on read");
    }

    private void reportUnexpectedEOF(int n, int n2) throws IOException {
        int n3 = this._byteCount;
        int n4 = this._charCount;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected EOF in the middle of a 4-byte UTF-32 char: got ");
        stringBuilder.append(n);
        stringBuilder.append(", needed ");
        stringBuilder.append(n2);
        stringBuilder.append(", at char #");
        stringBuilder.append(n4);
        stringBuilder.append(", byte #");
        stringBuilder.append(n3 + n);
        stringBuilder.append(")");
        throw new CharConversionException(stringBuilder.toString());
    }

    @Override
    public void close() throws IOException {
        InputStream inputStream2 = this._in;
        if (inputStream2 == null) return;
        this._in = null;
        this.freeBuffers();
        inputStream2.close();
    }

    @Override
    public int read() throws IOException {
        if (this._tmpBuf == null) {
            this._tmpBuf = new char[1];
        }
        if (this.read(this._tmpBuf, 0, 1) >= 1) return this._tmpBuf[0];
        return -1;
    }

    @Override
    public int read(char[] arrc, int n, int n2) throws IOException {
        int n3;
        block13 : {
            if (this._buffer == null) {
                return -1;
            }
            if (n2 < 1) {
                return n2;
            }
            if (n < 0 || n + n2 > arrc.length) {
                this.reportBounds(arrc, n, n2);
            }
            int n4 = n2 + n;
            n3 = this._surrogate;
            if (n3 != 0) {
                n2 = n + 1;
                arrc[n] = (char)n3;
                this._surrogate = (char)(false ? 1 : 0);
            } else {
                n2 = this._length - this._ptr;
                if (n2 < 4 && !this.loadMore(n2)) {
                    if (n2 == 0) {
                        return -1;
                    }
                    this.reportUnexpectedEOF(this._length - this._ptr, 4);
                }
                n2 = n;
            }
            int n5 = this._length;
            do {
                int n6;
                int n7;
                byte[] arrby;
                int n8;
                n3 = n2;
                if (n2 >= n4) break block13;
                n3 = this._ptr;
                if (this._bigEndian) {
                    arrby = this._buffer;
                    n8 = arrby[n3] << 8 | arrby[n3 + 1] & 255;
                    n7 = arrby[n3 + 2];
                    n3 = arrby[n3 + 3] & 255 | (n7 & 255) << 8;
                } else {
                    arrby = this._buffer;
                    n7 = arrby[n3];
                    n6 = arrby[n3 + 1];
                    n8 = arrby[n3 + 2];
                    n8 = arrby[n3 + 3] << 8 | n8 & 255;
                    n3 = n7 & 255 | (n6 & 255) << 8;
                }
                this._ptr += 4;
                n6 = n2;
                n7 = n3;
                if (n8 != 0) {
                    n8 = 65535 & n8;
                    n7 = n3 | n8 - 1 << 16;
                    if (n8 > 16) {
                        this.reportInvalid(n7, n2 - n, String.format(" (above 0x%08x)", 1114111));
                    }
                    n3 = n2 + 1;
                    arrc[n2] = (char)((n7 >> 10) + 55296);
                    if (n3 >= n4) {
                        this._surrogate = (char)n7;
                        n2 = n3;
                        break;
                    }
                    n7 = 56320 | n7 & 1023;
                    n6 = n3;
                }
                n2 = n6 + 1;
                arrc[n6] = (char)n7;
            } while (this._ptr <= n5 - 4);
            n3 = n2;
        }
        n = n3 - n;
        this._charCount += n;
        return n;
    }
}

