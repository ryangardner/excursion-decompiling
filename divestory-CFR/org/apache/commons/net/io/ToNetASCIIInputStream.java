/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ToNetASCIIInputStream
extends FilterInputStream {
    private static final int __LAST_WAS_CR = 1;
    private static final int __LAST_WAS_NL = 2;
    private static final int __NOTHING_SPECIAL = 0;
    private int __status = 0;

    public ToNetASCIIInputStream(InputStream inputStream2) {
        super(inputStream2);
    }

    @Override
    public int available() throws IOException {
        int n;
        int n2 = n = this.in.available();
        if (this.__status != 2) return n2;
        return n + 1;
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        if (this.__status == 2) {
            this.__status = 0;
            return 10;
        }
        int n = this.in.read();
        if (n != 10) {
            if (n == 13) {
                this.__status = 1;
                return 13;
            }
        } else if (this.__status != 1) {
            this.__status = 2;
            return 13;
        }
        this.__status = 0;
        return n;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3 = 1;
        if (n2 < 1) {
            return 0;
        }
        int n4 = this.available();
        int n5 = n2;
        if (n2 > n4) {
            n5 = n4;
        }
        n2 = n5 < 1 ? n3 : n5;
        n3 = this.read();
        if (n3 == -1) {
            return -1;
        }
        n5 = n;
        do {
            n4 = n5 + 1;
            arrby[n5] = (byte)n3;
            if (--n2 <= 0) return n4 - n;
            n3 = this.read();
            if (n3 == -1) {
                return n4 - n;
            }
            n5 = n4;
        } while (true);
    }
}

