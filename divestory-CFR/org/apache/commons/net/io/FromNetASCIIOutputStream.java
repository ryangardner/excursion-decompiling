/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.io.FromNetASCIIInputStream;

public final class FromNetASCIIOutputStream
extends FilterOutputStream {
    private boolean __lastWasCR = false;

    public FromNetASCIIOutputStream(OutputStream outputStream2) {
        super(outputStream2);
    }

    private void __write(int n) throws IOException {
        if (n != 10) {
            if (n == 13) {
                this.__lastWasCR = true;
                return;
            }
            if (this.__lastWasCR) {
                this.out.write(13);
                this.__lastWasCR = false;
            }
            this.out.write(n);
            return;
        }
        if (this.__lastWasCR) {
            this.out.write(FromNetASCIIInputStream._lineSeparatorBytes);
            this.__lastWasCR = false;
            return;
        }
        this.__lastWasCR = false;
        this.out.write(10);
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            if (FromNetASCIIInputStream._noConversionRequired) {
                super.close();
                return;
            }
            if (this.__lastWasCR) {
                this.out.write(13);
            }
            super.close();
            return;
        }
    }

    @Override
    public void write(int n) throws IOException {
        synchronized (this) {
            if (FromNetASCIIInputStream._noConversionRequired) {
                this.out.write(n);
                return;
            }
            this.__write(n);
            return;
        }
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        synchronized (this) {
            this.write(arrby, 0, arrby.length);
            return;
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            int n3 = n;
            int n4 = n2;
            if (FromNetASCIIInputStream._noConversionRequired) {
                this.out.write(arrby, n, n2);
                return;
            }
            while (n4 > 0) {
                this.__write(arrby[n3]);
                ++n3;
                --n4;
            }
            return;
        }
    }
}

