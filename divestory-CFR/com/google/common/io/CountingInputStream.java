/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class CountingInputStream
extends FilterInputStream {
    private long count;
    private long mark = -1L;

    public CountingInputStream(InputStream inputStream2) {
        super(Preconditions.checkNotNull(inputStream2));
    }

    public long getCount() {
        return this.count;
    }

    @Override
    public void mark(int n) {
        synchronized (this) {
            this.in.mark(n);
            this.mark = this.count;
            return;
        }
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (n == -1) return n;
        ++this.count;
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n = this.in.read(arrby, n, n2)) == -1) return n;
        this.count += (long)n;
        return n;
    }

    @Override
    public void reset() throws IOException {
        synchronized (this) {
            if (!this.in.markSupported()) {
                IOException iOException = new IOException("Mark not supported");
                throw iOException;
            }
            if (this.mark != -1L) {
                this.in.reset();
                this.count = this.mark;
                return;
            }
            IOException iOException = new IOException("Mark not set");
            throw iOException;
        }
    }

    @Override
    public long skip(long l) throws IOException {
        l = this.in.skip(l);
        this.count += l;
        return l;
    }
}

