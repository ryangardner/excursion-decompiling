/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.util.BufferRecycler;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;

public final class ByteArrayBuilder
extends OutputStream {
    static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
    private static final int INITIAL_BLOCK_SIZE = 500;
    private static final int MAX_BLOCK_SIZE = 131072;
    public static final byte[] NO_BYTES = new byte[0];
    private final BufferRecycler _bufferRecycler;
    private byte[] _currBlock;
    private int _currBlockPtr;
    private final LinkedList<byte[]> _pastBlocks = new LinkedList();
    private int _pastLen;

    public ByteArrayBuilder() {
        this(null);
    }

    public ByteArrayBuilder(int n) {
        this(null, n);
    }

    public ByteArrayBuilder(BufferRecycler bufferRecycler) {
        this(bufferRecycler, 500);
    }

    public ByteArrayBuilder(BufferRecycler arrby, int n) {
        this._bufferRecycler = arrby;
        arrby = arrby == null ? new byte[n] : arrby.allocByteBuffer(2);
        this._currBlock = arrby;
    }

    private ByteArrayBuilder(BufferRecycler bufferRecycler, byte[] arrby, int n) {
        this._bufferRecycler = null;
        this._currBlock = arrby;
        this._currBlockPtr = n;
    }

    private void _allocMore() {
        int n;
        int n2 = this._pastLen + this._currBlock.length;
        if (n2 < 0) throw new IllegalStateException("Maximum Java array size (2GB) exceeded by `ByteArrayBuilder`");
        this._pastLen = n2;
        n2 = n = Math.max(n2 >> 1, 1000);
        if (n > 131072) {
            n2 = 131072;
        }
        this._pastBlocks.add(this._currBlock);
        this._currBlock = new byte[n2];
        this._currBlockPtr = 0;
    }

    public static ByteArrayBuilder fromInitial(byte[] arrby, int n) {
        return new ByteArrayBuilder(null, arrby, n);
    }

    public void append(int n) {
        if (this._currBlockPtr >= this._currBlock.length) {
            this._allocMore();
        }
        byte[] arrby = this._currBlock;
        int n2 = this._currBlockPtr;
        this._currBlockPtr = n2 + 1;
        arrby[n2] = (byte)n;
    }

    public void appendFourBytes(int n) {
        int n2 = this._currBlockPtr;
        byte[] arrby = this._currBlock;
        if (n2 + 3 < arrby.length) {
            int n3;
            this._currBlockPtr = n3 = n2 + 1;
            arrby[n2] = (byte)(n >> 24);
            this._currBlockPtr = n2 = n3 + 1;
            arrby[n3] = (byte)(n >> 16);
            this._currBlockPtr = n3 = n2 + 1;
            arrby[n2] = (byte)(n >> 8);
            this._currBlockPtr = n3 + 1;
            arrby[n3] = (byte)n;
            return;
        }
        this.append(n >> 24);
        this.append(n >> 16);
        this.append(n >> 8);
        this.append(n);
    }

    public void appendThreeBytes(int n) {
        int n2 = this._currBlockPtr;
        byte[] arrby = this._currBlock;
        if (n2 + 2 < arrby.length) {
            int n3;
            this._currBlockPtr = n3 = n2 + 1;
            arrby[n2] = (byte)(n >> 16);
            this._currBlockPtr = n2 = n3 + 1;
            arrby[n3] = (byte)(n >> 8);
            this._currBlockPtr = n2 + 1;
            arrby[n2] = (byte)n;
            return;
        }
        this.append(n >> 16);
        this.append(n >> 8);
        this.append(n);
    }

    public void appendTwoBytes(int n) {
        int n2 = this._currBlockPtr;
        byte[] arrby = this._currBlock;
        if (n2 + 1 < arrby.length) {
            int n3;
            this._currBlockPtr = n3 = n2 + 1;
            arrby[n2] = (byte)(n >> 8);
            this._currBlockPtr = n3 + 1;
            arrby[n3] = (byte)n;
            return;
        }
        this.append(n >> 8);
        this.append(n);
    }

    @Override
    public void close() {
    }

    public byte[] completeAndCoalesce(int n) {
        this._currBlockPtr = n;
        return this.toByteArray();
    }

    public byte[] finishCurrentSegment() {
        this._allocMore();
        return this._currBlock;
    }

    @Override
    public void flush() {
    }

    public byte[] getCurrentSegment() {
        return this._currBlock;
    }

    public int getCurrentSegmentLength() {
        return this._currBlockPtr;
    }

    public void release() {
        this.reset();
        BufferRecycler bufferRecycler = this._bufferRecycler;
        if (bufferRecycler == null) return;
        byte[] arrby = this._currBlock;
        if (arrby == null) return;
        bufferRecycler.releaseByteBuffer(2, arrby);
        this._currBlock = null;
    }

    public void reset() {
        this._pastLen = 0;
        this._currBlockPtr = 0;
        if (this._pastBlocks.isEmpty()) return;
        this._pastBlocks.clear();
    }

    public byte[] resetAndGetFirstSegment() {
        this.reset();
        return this._currBlock;
    }

    public void setCurrentSegmentLength(int n) {
        this._currBlockPtr = n;
    }

    public int size() {
        return this._pastLen + this._currBlockPtr;
    }

    public byte[] toByteArray() {
        Object object;
        int n = this._pastLen + this._currBlockPtr;
        if (n == 0) {
            return NO_BYTES;
        }
        byte[] arrby = new byte[n];
        Iterator iterator2 = this._pastBlocks.iterator();
        int n2 = 0;
        while (iterator2.hasNext()) {
            object = (byte[])iterator2.next();
            int n3 = ((byte[])object).length;
            System.arraycopy(object, 0, arrby, n2, n3);
            n2 += n3;
        }
        System.arraycopy(this._currBlock, 0, arrby, n2, this._currBlockPtr);
        if ((n2 += this._currBlockPtr) == n) {
            if (this._pastBlocks.isEmpty()) return arrby;
            this.reset();
            return arrby;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Internal error: total len assumed to be ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", copied ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" bytes");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Override
    public void write(int n) {
        this.append(n);
    }

    @Override
    public void write(byte[] arrby) {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) {
        int n3 = n;
        do {
            int n4 = Math.min(this._currBlock.length - this._currBlockPtr, n2);
            int n5 = n3;
            n = n2;
            if (n4 > 0) {
                System.arraycopy(arrby, n3, this._currBlock, this._currBlockPtr, n4);
                n5 = n3 + n4;
                this._currBlockPtr += n4;
                n = n2 - n4;
            }
            if (n <= 0) {
                return;
            }
            this._allocMore();
            n3 = n5;
            n2 = n;
        } while (true);
    }
}

