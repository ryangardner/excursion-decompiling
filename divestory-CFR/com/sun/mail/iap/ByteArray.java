/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.iap;

import java.io.ByteArrayInputStream;

public class ByteArray {
    private byte[] bytes;
    private int count;
    private int start;

    public ByteArray(int n) {
        this(new byte[n], 0, n);
    }

    public ByteArray(byte[] arrby, int n, int n2) {
        this.bytes = arrby;
        this.start = n;
        this.count = n2;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int getCount() {
        return this.count;
    }

    public byte[] getNewBytes() {
        int n = this.count;
        byte[] arrby = new byte[n];
        System.arraycopy(this.bytes, this.start, arrby, 0, n);
        return arrby;
    }

    public int getStart() {
        return this.start;
    }

    public void grow(int n) {
        byte[] arrby = this.bytes;
        byte[] arrby2 = new byte[arrby.length + n];
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        this.bytes = arrby2;
    }

    public void setCount(int n) {
        this.count = n;
    }

    public ByteArrayInputStream toByteArrayInputStream() {
        return new ByteArrayInputStream(this.bytes, this.start, this.count);
    }
}

