/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class MultiInputStream
extends InputStream {
    @NullableDecl
    private InputStream in;
    private Iterator<? extends ByteSource> it;

    public MultiInputStream(Iterator<? extends ByteSource> iterator2) throws IOException {
        this.it = Preconditions.checkNotNull(iterator2);
        this.advance();
    }

    private void advance() throws IOException {
        this.close();
        if (!this.it.hasNext()) return;
        this.in = this.it.next().openStream();
    }

    @Override
    public int available() throws IOException {
        InputStream inputStream2 = this.in;
        if (inputStream2 != null) return inputStream2.available();
        return 0;
    }

    @Override
    public void close() throws IOException {
        InputStream inputStream2 = this.in;
        if (inputStream2 == null) return;
        try {
            inputStream2.close();
            return;
        }
        finally {
            this.in = null;
        }
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        InputStream inputStream2;
        while ((inputStream2 = this.in) != null) {
            int n = inputStream2.read();
            if (n != -1) {
                return n;
            }
            this.advance();
        }
        return -1;
    }

    @Override
    public int read(@NullableDecl byte[] arrby, int n, int n2) throws IOException {
        InputStream inputStream2;
        while ((inputStream2 = this.in) != null) {
            int n3 = inputStream2.read(arrby, n, n2);
            if (n3 != -1) {
                return n3;
            }
            this.advance();
        }
        return -1;
    }

    @Override
    public long skip(long l) throws IOException {
        InputStream inputStream2 = this.in;
        if (inputStream2 == null) return 0L;
        if (l <= 0L) {
            return 0L;
        }
        long l2 = inputStream2.skip(l);
        if (l2 != 0L) {
            return l2;
        }
        if (this.read() != -1) return this.in.skip(l - 1L) + 1L;
        return 0L;
    }
}

