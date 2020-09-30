/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class HashingOutputStream
extends FilterOutputStream {
    private final Hasher hasher;

    public HashingOutputStream(HashFunction hashFunction, OutputStream outputStream2) {
        super(Preconditions.checkNotNull(outputStream2));
        this.hasher = Preconditions.checkNotNull(hashFunction.newHasher());
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }

    public HashCode hash() {
        return this.hasher.hash();
    }

    @Override
    public void write(int n) throws IOException {
        this.hasher.putByte((byte)n);
        this.out.write(n);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.hasher.putBytes(arrby, n, n2);
        this.out.write(arrby, n, n2);
    }
}

