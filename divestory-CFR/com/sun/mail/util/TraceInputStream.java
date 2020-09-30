/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TraceInputStream
extends FilterInputStream {
    private boolean quote = false;
    private boolean trace = false;
    private OutputStream traceOut;

    public TraceInputStream(InputStream inputStream2, OutputStream outputStream2) {
        super(inputStream2);
        this.traceOut = outputStream2;
    }

    private final void writeByte(int n) throws IOException {
        int n2;
        n = n2 = n & 255;
        if (n2 > 127) {
            this.traceOut.write(77);
            this.traceOut.write(45);
            n = n2 & 127;
        }
        if (n == 13) {
            this.traceOut.write(92);
            this.traceOut.write(114);
            return;
        }
        if (n == 10) {
            this.traceOut.write(92);
            this.traceOut.write(110);
            this.traceOut.write(10);
            return;
        }
        if (n == 9) {
            this.traceOut.write(92);
            this.traceOut.write(116);
            return;
        }
        if (n < 32) {
            this.traceOut.write(94);
            this.traceOut.write(n + 64);
            return;
        }
        this.traceOut.write(n);
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (!this.trace) return n;
        if (n == -1) return n;
        if (this.quote) {
            this.writeByte(n);
            return n;
        }
        this.traceOut.write(n);
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3 = this.in.read(arrby, n, n2);
        if (!this.trace) return n3;
        if (n3 == -1) return n3;
        if (!this.quote) {
            this.traceOut.write(arrby, n, n3);
            return n3;
        }
        n2 = 0;
        while (n2 < n3) {
            this.writeByte(arrby[n + n2]);
            ++n2;
        }
        return n3;
    }

    public void setQuote(boolean bl) {
        this.quote = bl;
    }

    public void setTrace(boolean bl) {
        this.trace = bl;
    }
}

