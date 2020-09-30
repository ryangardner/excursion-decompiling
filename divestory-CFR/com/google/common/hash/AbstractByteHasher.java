/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractHasher;
import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class AbstractByteHasher
extends AbstractHasher {
    private final ByteBuffer scratch = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);

    AbstractByteHasher() {
    }

    private Hasher update(int n) {
        try {
            this.update(this.scratch.array(), 0, n);
            return this;
        }
        finally {
            this.scratch.clear();
        }
    }

    @Override
    public Hasher putByte(byte by) {
        this.update(by);
        return this;
    }

    @Override
    public Hasher putBytes(ByteBuffer byteBuffer) {
        this.update(byteBuffer);
        return this;
    }

    @Override
    public Hasher putBytes(byte[] arrby) {
        Preconditions.checkNotNull(arrby);
        this.update(arrby);
        return this;
    }

    @Override
    public Hasher putBytes(byte[] arrby, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
        this.update(arrby, n, n2);
        return this;
    }

    @Override
    public Hasher putChar(char c) {
        this.scratch.putChar(c);
        return this.update(2);
    }

    @Override
    public Hasher putInt(int n) {
        this.scratch.putInt(n);
        return this.update(4);
    }

    @Override
    public Hasher putLong(long l) {
        this.scratch.putLong(l);
        return this.update(8);
    }

    @Override
    public Hasher putShort(short s) {
        this.scratch.putShort(s);
        return this.update(2);
    }

    protected abstract void update(byte var1);

    protected void update(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            this.update(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            byteBuffer.position(byteBuffer.limit());
            return;
        }
        int n = byteBuffer.remaining();
        while (n > 0) {
            this.update(byteBuffer.get());
            --n;
        }
    }

    protected void update(byte[] arrby) {
        this.update(arrby, 0, arrby.length);
    }

    protected void update(byte[] arrby, int n, int n2) {
        int n3 = n;
        while (n3 < n + n2) {
            this.update(arrby[n3]);
            ++n3;
        }
    }
}

