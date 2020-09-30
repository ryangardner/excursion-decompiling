/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class LineInputStream
extends FilterInputStream {
    private char[] lineBuffer = null;

    public LineInputStream(InputStream inputStream2) {
        super(inputStream2);
    }

    public String readLine() throws IOException {
        Object object;
        int n;
        InputStream inputStream2 = this.in;
        char[] arrc = object = this.lineBuffer;
        if (object == null) {
            this.lineBuffer = arrc = new char[128];
        }
        int n2 = arrc.length;
        int n3 = 0;
        while ((n = inputStream2.read()) != -1 && n != 10) {
            int n4;
            if (n == 13) {
                n2 = n4 = inputStream2.read();
                if (n4 == 13) {
                    n2 = inputStream2.read();
                }
                if (n2 == 10) break;
                object = inputStream2;
                if (!(inputStream2 instanceof PushbackInputStream)) {
                    this.in = object = new PushbackInputStream(inputStream2);
                }
                ((PushbackInputStream)object).unread(n2);
                break;
            }
            n2 = n4 = n2 - 1;
            if (n4 < 0) {
                n2 = n3 + 128;
                arrc = new char[n2];
                System.arraycopy(this.lineBuffer, 0, arrc, 0, n3);
                this.lineBuffer = arrc;
                n2 = n2 - n3 - 1;
            }
            arrc[n3] = (char)n;
            ++n3;
        }
        if (n != -1) return String.copyValueOf(arrc, 0, n3);
        if (n3 != 0) return String.copyValueOf(arrc, 0, n3);
        return null;
    }
}

