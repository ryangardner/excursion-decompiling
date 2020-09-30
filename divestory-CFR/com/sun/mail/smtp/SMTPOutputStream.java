/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.smtp;

import com.sun.mail.util.CRLFOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SMTPOutputStream
extends CRLFOutputStream {
    public SMTPOutputStream(OutputStream outputStream2) {
        super(outputStream2);
    }

    public void ensureAtBOL() throws IOException {
        if (this.atBOL) return;
        super.writeln();
    }

    @Override
    public void flush() {
    }

    @Override
    public void write(int n) throws IOException {
        if ((this.lastb == 10 || this.lastb == 13 || this.lastb == -1) && n == 46) {
            this.out.write(46);
        }
        super.write(n);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        int n3 = this.lastb == -1 ? 10 : this.lastb;
        int n4 = n2 + n;
        n2 = n;
        do {
            int n5;
            block7 : {
                block6 : {
                    if (n >= n4) {
                        n = n4 - n2;
                        if (n <= 0) return;
                        super.write(arrby, n2, n);
                        return;
                    }
                    if (n3 == 10) break block6;
                    n5 = n2;
                    if (n3 != 13) break block7;
                }
                n5 = n2;
                if (arrby[n] == 46) {
                    super.write(arrby, n2, n - n2);
                    this.out.write(46);
                    n5 = n;
                }
            }
            n3 = arrby[n];
            ++n;
            n2 = n5;
        } while (true);
    }
}

