/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class BufferRecycler {
    public static final int BYTE_BASE64_CODEC_BUFFER = 3;
    private static final int[] BYTE_BUFFER_LENGTHS = new int[]{8000, 8000, 2000, 2000};
    public static final int BYTE_READ_IO_BUFFER = 0;
    public static final int BYTE_WRITE_CONCAT_BUFFER = 2;
    public static final int BYTE_WRITE_ENCODING_BUFFER = 1;
    private static final int[] CHAR_BUFFER_LENGTHS = new int[]{4000, 4000, 200, 200};
    public static final int CHAR_CONCAT_BUFFER = 1;
    public static final int CHAR_NAME_COPY_BUFFER = 3;
    public static final int CHAR_TEXT_BUFFER = 2;
    public static final int CHAR_TOKEN_BUFFER = 0;
    protected final AtomicReferenceArray<byte[]> _byteBuffers;
    protected final AtomicReferenceArray<char[]> _charBuffers;

    public BufferRecycler() {
        this(4, 4);
    }

    protected BufferRecycler(int n, int n2) {
        this._byteBuffers = new AtomicReferenceArray(n);
        this._charBuffers = new AtomicReferenceArray(n2);
    }

    public final byte[] allocByteBuffer(int n) {
        return this.allocByteBuffer(n, 0);
    }

    public byte[] allocByteBuffer(int n, int n2) {
        byte[] arrby;
        int n3 = this.byteBufferLength(n);
        int n4 = n2;
        if (n2 < n3) {
            n4 = n3;
        }
        if ((arrby = (byte[])this._byteBuffers.getAndSet(n, null)) == null) return this.balloc(n4);
        byte[] arrby2 = arrby;
        if (arrby.length >= n4) return arrby2;
        return this.balloc(n4);
    }

    public final char[] allocCharBuffer(int n) {
        return this.allocCharBuffer(n, 0);
    }

    public char[] allocCharBuffer(int n, int n2) {
        char[] arrc;
        int n3 = this.charBufferLength(n);
        int n4 = n2;
        if (n2 < n3) {
            n4 = n3;
        }
        if ((arrc = (char[])this._charBuffers.getAndSet(n, null)) == null) return this.calloc(n4);
        char[] arrc2 = arrc;
        if (arrc.length >= n4) return arrc2;
        return this.calloc(n4);
    }

    protected byte[] balloc(int n) {
        return new byte[n];
    }

    protected int byteBufferLength(int n) {
        return BYTE_BUFFER_LENGTHS[n];
    }

    protected char[] calloc(int n) {
        return new char[n];
    }

    protected int charBufferLength(int n) {
        return CHAR_BUFFER_LENGTHS[n];
    }

    public void releaseByteBuffer(int n, byte[] arrby) {
        this._byteBuffers.set(n, arrby);
    }

    public void releaseCharBuffer(int n, char[] arrc) {
        this._charBuffers.set(n, arrc);
    }
}

