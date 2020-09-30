/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class ToNetASCIIOutputStream
extends FilterOutputStream {
    private boolean __lastWasCR = false;

    public ToNetASCIIOutputStream(OutputStream outputStream2) {
        super(outputStream2);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public void write(int n) throws IOException {
        synchronized (this) {
            if (n != 10) {
                if (n == 13) {
                    this.__lastWasCR = true;
                    this.out.write(13);
                    return;
                }
            } else if (!this.__lastWasCR) {
                this.out.write(13);
            }
            this.__lastWasCR = false;
            this.out.write(n);
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
            while (n2 > 0) {
                this.write(arrby[n]);
                ++n;
                --n2;
            }
            return;
        }
    }
}

