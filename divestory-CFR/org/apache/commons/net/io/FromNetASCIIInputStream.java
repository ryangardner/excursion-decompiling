/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;

public final class FromNetASCIIInputStream
extends PushbackInputStream {
    static final String _lineSeparator;
    static final byte[] _lineSeparatorBytes;
    static final boolean _noConversionRequired;
    private int __length = 0;

    static {
        String string2;
        _lineSeparator = string2 = System.getProperty("line.separator");
        _noConversionRequired = string2.equals("\r\n");
        try {
            _lineSeparatorBytes = _lineSeparator.getBytes("US-ASCII");
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("Broken JVM - cannot find US-ASCII charset!", unsupportedEncodingException);
        }
    }

    public FromNetASCIIInputStream(InputStream inputStream2) {
        super(inputStream2, _lineSeparatorBytes.length + 1);
    }

    private int __read() throws IOException {
        int n;
        int n2 = n = super.read();
        if (n != 13) return n2;
        n2 = super.read();
        if (n2 == 10) {
            this.unread(_lineSeparatorBytes);
            n2 = super.read();
            --this.__length;
            return n2;
        }
        if (n2 == -1) return 13;
        this.unread(n2);
        return 13;
    }

    public static final boolean isConversionRequired() {
        return _noConversionRequired ^ true;
    }

    @Override
    public int available() throws IOException {
        if (this.in == null) throw new IOException("Stream closed");
        return this.buf.length - this.pos + this.in.available();
    }

    @Override
    public int read() throws IOException {
        if (!_noConversionRequired) return this.__read();
        return super.read();
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (_noConversionRequired) {
            return super.read(arrby, n, n2);
        }
        if (n2 < 1) {
            return 0;
        }
        int n3 = this.available();
        int n4 = n2;
        if (n2 > n3) {
            n4 = n3;
        }
        this.__length = n4;
        if (n4 < 1) {
            this.__length = 1;
        }
        if ((n2 = this.__read()) == -1) {
            return -1;
        }
        n4 = n;
        do {
            n3 = n4 + 1;
            arrby[n4] = (byte)n2;
            this.__length = n2 = this.__length - 1;
            if (n2 <= 0) return n3 - n;
            n2 = this.__read();
            if (n2 == -1) {
                return n3 - n;
            }
            n4 = n3;
        } while (true);
    }
}

