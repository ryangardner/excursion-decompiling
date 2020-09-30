/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class QPEncoderStream
extends FilterOutputStream {
    private static final char[] hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private int bytesPerLine;
    private int count = 0;
    private boolean gotCR = false;
    private boolean gotSpace = false;

    public QPEncoderStream(OutputStream outputStream2) {
        this(outputStream2, 76);
    }

    public QPEncoderStream(OutputStream outputStream2, int n) {
        super(outputStream2);
        this.bytesPerLine = n - 1;
    }

    private void outputCRLF() throws IOException {
        this.out.write(13);
        this.out.write(10);
        this.count = 0;
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    protected void output(int n, boolean bl) throws IOException {
        int n2;
        if (bl) {
            int n3;
            this.count = n3 = this.count + 3;
            if (n3 > this.bytesPerLine) {
                this.out.write(61);
                this.out.write(13);
                this.out.write(10);
                this.count = 3;
            }
            this.out.write(61);
            this.out.write(hex[n >> 4]);
            this.out.write(hex[n & 15]);
            return;
        }
        this.count = n2 = this.count + 1;
        if (n2 > this.bytesPerLine) {
            this.out.write(61);
            this.out.write(13);
            this.out.write(10);
            this.count = 1;
        }
        this.out.write(n);
    }

    @Override
    public void write(int n) throws IOException {
        n &= 255;
        if (this.gotSpace) {
            if (n != 13 && n != 10) {
                this.output(32, false);
            } else {
                this.output(32, true);
            }
            this.gotSpace = false;
        }
        if (n == 13) {
            this.gotCR = true;
            this.outputCRLF();
            return;
        }
        if (n == 10) {
            if (!this.gotCR) {
                this.outputCRLF();
            }
        } else if (n == 32) {
            this.gotSpace = true;
        } else if (n >= 32 && n < 127 && n != 61) {
            this.output(n, false);
        } else {
            this.output(n, true);
        }
        this.gotCR = false;
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        int n3 = 0;
        while (n3 < n2) {
            this.write(arrby[n + n3]);
            ++n3;
        }
        return;
    }
}

