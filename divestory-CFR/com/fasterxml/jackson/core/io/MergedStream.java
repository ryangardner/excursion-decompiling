/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.io.IOContext;
import java.io.IOException;
import java.io.InputStream;

public final class MergedStream
extends InputStream {
    private byte[] _b;
    private final IOContext _ctxt;
    private final int _end;
    private final InputStream _in;
    private int _ptr;

    public MergedStream(IOContext iOContext, InputStream inputStream2, byte[] arrby, int n, int n2) {
        this._ctxt = iOContext;
        this._in = inputStream2;
        this._b = arrby;
        this._ptr = n;
        this._end = n2;
    }

    private void _free() {
        byte[] arrby = this._b;
        if (arrby == null) return;
        this._b = null;
        IOContext iOContext = this._ctxt;
        if (iOContext == null) return;
        iOContext.releaseReadIOBuffer(arrby);
    }

    @Override
    public int available() throws IOException {
        if (this._b == null) return this._in.available();
        return this._end - this._ptr;
    }

    @Override
    public void close() throws IOException {
        this._free();
        this._in.close();
    }

    @Override
    public void mark(int n) {
        if (this._b != null) return;
        this._in.mark(n);
    }

    @Override
    public boolean markSupported() {
        if (this._b != null) return false;
        if (!this._in.markSupported()) return false;
        return true;
    }

    @Override
    public int read() throws IOException {
        int n;
        byte[] arrby = this._b;
        if (arrby == null) return this._in.read();
        int n2 = this._ptr;
        this._ptr = n = n2 + 1;
        n2 = arrby[n2];
        if (n < this._end) return n2 & 255;
        this._free();
        return n2 & 255;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (this._b == null) return this._in.read(arrby, n, n2);
        int n3 = this._end - this._ptr;
        int n4 = n2;
        if (n2 > n3) {
            n4 = n3;
        }
        System.arraycopy(this._b, this._ptr, arrby, n, n4);
        this._ptr = n = this._ptr + n4;
        if (n < this._end) return n4;
        this._free();
        return n4;
    }

    @Override
    public void reset() throws IOException {
        if (this._b != null) return;
        this._in.reset();
    }

    @Override
    public long skip(long l) throws IOException {
        long l2;
        long l3;
        if (this._b != null) {
            int n = this._end;
            int n2 = this._ptr;
            l2 = n - n2;
            if (l2 > l) {
                this._ptr = n2 + (int)l;
                return l;
            }
            this._free();
            l3 = l2 + 0L;
            l2 = l - l2;
            l = l3;
            l3 = l2;
        } else {
            l2 = 0L;
            l3 = l;
            l = l2;
        }
        l2 = l;
        if (l3 <= 0L) return l2;
        return l + this._in.skip(l3);
    }
}

