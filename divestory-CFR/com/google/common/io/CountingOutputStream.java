/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class CountingOutputStream
extends FilterOutputStream {
    private long count;

    public CountingOutputStream(OutputStream outputStream2) {
        super(Preconditions.checkNotNull(outputStream2));
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }

    public long getCount() {
        return this.count;
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
        ++this.count;
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.out.write(arrby, n, n2);
        this.count += (long)n2;
    }
}

