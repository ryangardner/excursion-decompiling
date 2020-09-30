/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap;

import java.io.IOException;
import java.io.OutputStream;

class LengthCounter
extends OutputStream {
    private byte[] buf = new byte[8192];
    private int maxsize;
    private int size = 0;

    public LengthCounter(int n) {
        this.maxsize = n;
    }

    public byte[] getBytes() {
        return this.buf;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public void write(int n) {
        int n2 = this.size + 1;
        if (this.buf != null) {
            int n3 = this.maxsize;
            if (n2 > n3 && n3 >= 0) {
                this.buf = null;
            } else {
                byte[] arrby = this.buf;
                if (n2 > arrby.length) {
                    arrby = new byte[Math.max(arrby.length << 1, n2)];
                    System.arraycopy(this.buf, 0, arrby, 0, this.size);
                    this.buf = arrby;
                    arrby[this.size] = (byte)n;
                } else {
                    arrby[this.size] = (byte)n;
                }
            }
        }
        this.size = n2;
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) {
        if (n < 0) throw new IndexOutOfBoundsException();
        if (n > arrby.length) throw new IndexOutOfBoundsException();
        if (n2 < 0) throw new IndexOutOfBoundsException();
        int n3 = n + n2;
        if (n3 > arrby.length) throw new IndexOutOfBoundsException();
        if (n3 < 0) throw new IndexOutOfBoundsException();
        if (n2 == 0) {
            return;
        }
        n3 = this.size + n2;
        if (this.buf != null) {
            int n4 = this.maxsize;
            if (n3 > n4 && n4 >= 0) {
                this.buf = null;
            } else {
                byte[] arrby2 = this.buf;
                if (n3 > arrby2.length) {
                    arrby2 = new byte[Math.max(arrby2.length << 1, n3)];
                    System.arraycopy(this.buf, 0, arrby2, 0, this.size);
                    this.buf = arrby2;
                    System.arraycopy(arrby, n, arrby2, this.size, n2);
                } else {
                    System.arraycopy(arrby, n, arrby2, this.size, n2);
                }
            }
        }
        this.size = n3;
    }
}

