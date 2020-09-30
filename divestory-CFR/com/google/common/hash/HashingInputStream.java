/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class HashingInputStream
extends FilterInputStream {
    private final Hasher hasher;

    public HashingInputStream(HashFunction hashFunction, InputStream inputStream2) {
        super(Preconditions.checkNotNull(inputStream2));
        this.hasher = Preconditions.checkNotNull(hashFunction.newHasher());
    }

    public HashCode hash() {
        return this.hasher.hash();
    }

    @Override
    public void mark(int n) {
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (n == -1) return n;
        this.hasher.putByte((byte)n);
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n2 = this.in.read(arrby, n, n2)) == -1) return n2;
        this.hasher.putBytes(arrby, n, n2);
        return n2;
    }

    @Override
    public void reset() throws IOException {
        throw new IOException("reset not supported");
    }
}

