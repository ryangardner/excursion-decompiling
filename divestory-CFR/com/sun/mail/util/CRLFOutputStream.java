/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CRLFOutputStream
extends FilterOutputStream {
    private static final byte[] newline = new byte[]{13, 10};
    protected boolean atBOL = true;
    protected int lastb = -1;

    public CRLFOutputStream(OutputStream outputStream2) {
        super(outputStream2);
    }

    @Override
    public void write(int n) throws IOException {
        if (n == 13) {
            this.writeln();
        } else if (n == 10) {
            if (this.lastb != 13) {
                this.writeln();
            }
        } else {
            this.out.write(n);
            this.atBOL = false;
        }
        this.lastb = n;
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        int n3 = n2 + n;
        n2 = n;
        do {
            int n4;
            block8 : {
                block7 : {
                    block6 : {
                        if (n >= n3) {
                            n = n3 - n2;
                            if (n <= 0) return;
                            this.out.write(arrby, n2, n);
                            this.atBOL = false;
                            return;
                        }
                        if (arrby[n] != 13) break block6;
                        this.out.write(arrby, n2, n - n2);
                        this.writeln();
                        break block7;
                    }
                    n4 = n2;
                    if (arrby[n] != 10) break block8;
                    if (this.lastb != 13) {
                        this.out.write(arrby, n2, n - n2);
                        this.writeln();
                    }
                }
                n4 = n + 1;
            }
            this.lastb = arrby[n];
            ++n;
            n2 = n4;
        } while (true);
    }

    public void writeln() throws IOException {
        this.out.write(newline);
        this.atBOL = true;
    }
}

