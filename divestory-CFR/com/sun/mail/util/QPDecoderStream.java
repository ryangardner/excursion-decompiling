/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import com.sun.mail.util.ASCIIUtility;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class QPDecoderStream
extends FilterInputStream {
    protected byte[] ba = new byte[2];
    protected int spaces = 0;

    public QPDecoderStream(InputStream inputStream2) {
        super(new PushbackInputStream(inputStream2, 2));
    }

    @Override
    public int available() throws IOException {
        return this.in.available();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        int n = this.spaces;
        int n2 = 32;
        if (n > 0) {
            this.spaces = n - 1;
            return 32;
        }
        n = this.in.read();
        if (n != 32) {
            if (n != 61) return n;
            n2 = this.in.read();
            if (n2 == 10) {
                return this.read();
            }
            if (n2 == 13) {
                n2 = this.in.read();
                if (n2 == 10) return this.read();
                ((PushbackInputStream)this.in).unread(n2);
                return this.read();
            }
            if (n2 == -1) {
                return -1;
            }
            byte[] arrby = this.ba;
            arrby[0] = (byte)n2;
            arrby[1] = (byte)this.in.read();
            try {
                return ASCIIUtility.parseInt(this.ba, 0, 2, 16);
            }
            catch (NumberFormatException numberFormatException) {
                ((PushbackInputStream)this.in).unread(this.ba);
            }
            return n;
        }
        do {
            if ((n = this.in.read()) != 32) {
                if (n != 13 && n != 10 && n != -1) {
                    ((PushbackInputStream)this.in).unread(n);
                    return n2;
                }
                this.spaces = 0;
                return n;
            }
            ++this.spaces;
        } while (true);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3 = 0;
        while (n3 < n2) {
            int n4 = this.read();
            if (n4 == -1) {
                n = n3;
                if (n3 != 0) return n;
                return -1;
            }
            arrby[n + n3] = (byte)n4;
            ++n3;
        }
        return n3;
    }
}

