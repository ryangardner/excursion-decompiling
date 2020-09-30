/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractHasher;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class AbstractStreamingHasher
extends AbstractHasher {
    private final ByteBuffer buffer;
    private final int bufferSize;
    private final int chunkSize;

    protected AbstractStreamingHasher(int n) {
        this(n, n);
    }

    protected AbstractStreamingHasher(int n, int n2) {
        boolean bl = n2 % n == 0;
        Preconditions.checkArgument(bl);
        this.buffer = ByteBuffer.allocate(n2 + 7).order(ByteOrder.LITTLE_ENDIAN);
        this.bufferSize = n2;
        this.chunkSize = n;
    }

    private void munch() {
        this.buffer.flip();
        do {
            if (this.buffer.remaining() < this.chunkSize) {
                this.buffer.compact();
                return;
            }
            this.process(this.buffer);
        } while (true);
    }

    private void munchIfFull() {
        if (this.buffer.remaining() >= 8) return;
        this.munch();
    }

    private Hasher putBytesInternal(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() <= this.buffer.remaining()) {
            this.buffer.put(byteBuffer);
            this.munchIfFull();
            return this;
        }
        int n = this.bufferSize;
        int n2 = this.buffer.position();
        for (int i = 0; i < n - n2; ++i) {
            this.buffer.put(byteBuffer.get());
        }
        this.munch();
        do {
            if (byteBuffer.remaining() < this.chunkSize) {
                this.buffer.put(byteBuffer);
                return this;
            }
            this.process(byteBuffer);
        } while (true);
    }

    @Override
    public final HashCode hash() {
        this.munch();
        this.buffer.flip();
        if (this.buffer.remaining() <= 0) return this.makeHash();
        this.processRemaining(this.buffer);
        ByteBuffer byteBuffer = this.buffer;
        byteBuffer.position(byteBuffer.limit());
        return this.makeHash();
    }

    protected abstract HashCode makeHash();

    protected abstract void process(ByteBuffer var1);

    protected void processRemaining(ByteBuffer byteBuffer) {
        byteBuffer.position(byteBuffer.limit());
        byteBuffer.limit(this.chunkSize + 7);
        do {
            int n;
            int n2;
            if ((n = byteBuffer.position()) >= (n2 = this.chunkSize)) {
                byteBuffer.limit(n2);
                byteBuffer.flip();
                this.process(byteBuffer);
                return;
            }
            byteBuffer.putLong(0L);
        } while (true);
    }

    @Override
    public final Hasher putByte(byte by) {
        this.buffer.put(by);
        this.munchIfFull();
        return this;
    }

    @Override
    public final Hasher putBytes(ByteBuffer byteBuffer) {
        ByteOrder byteOrder = byteBuffer.order();
        try {
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            Hasher hasher = this.putBytesInternal(byteBuffer);
            return hasher;
        }
        finally {
            byteBuffer.order(byteOrder);
        }
    }

    @Override
    public final Hasher putBytes(byte[] arrby, int n, int n2) {
        return this.putBytesInternal(ByteBuffer.wrap(arrby, n, n2).order(ByteOrder.LITTLE_ENDIAN));
    }

    @Override
    public final Hasher putChar(char c) {
        this.buffer.putChar(c);
        this.munchIfFull();
        return this;
    }

    @Override
    public final Hasher putInt(int n) {
        this.buffer.putInt(n);
        this.munchIfFull();
        return this;
    }

    @Override
    public final Hasher putLong(long l) {
        this.buffer.putLong(l);
        this.munchIfFull();
        return this;
    }

    @Override
    public final Hasher putShort(short s) {
        this.buffer.putShort(s);
        this.munchIfFull();
        return this;
    }
}

