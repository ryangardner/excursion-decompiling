/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.mail.internet;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import javax.mail.internet.MimeUtility;

class AsciiOutputStream
extends OutputStream {
    private int ascii;
    private boolean badEOL;
    private boolean breakOnNonAscii;
    private boolean checkEOL;
    private int lastb;
    private int linelen;
    private boolean longLine;
    private int non_ascii;
    private int ret;

    public AsciiOutputStream(boolean bl, boolean bl2) {
        boolean bl3 = false;
        this.ascii = 0;
        this.non_ascii = 0;
        this.linelen = 0;
        this.longLine = false;
        this.badEOL = false;
        this.checkEOL = false;
        this.lastb = 0;
        this.ret = 0;
        this.breakOnNonAscii = bl;
        boolean bl4 = bl3;
        if (bl2) {
            bl4 = bl3;
            if (bl) {
                bl4 = true;
            }
        }
        this.checkEOL = bl4;
    }

    private final void check(int n) throws IOException {
        if (this.checkEOL && (this.lastb == 13 && (n &= 255) != 10 || this.lastb != 13 && n == 10)) {
            this.badEOL = true;
        }
        if (n != 13 && n != 10) {
            int n2;
            this.linelen = n2 = this.linelen + 1;
            if (n2 > 998) {
                this.longLine = true;
            }
        } else {
            this.linelen = 0;
        }
        if (MimeUtility.nonascii(n)) {
            ++this.non_ascii;
            if (this.breakOnNonAscii) {
                this.ret = 3;
                throw new EOFException();
            }
        } else {
            ++this.ascii;
        }
        this.lastb = n;
    }

    public int getAscii() {
        int n = this.ret;
        if (n != 0) {
            return n;
        }
        if (this.badEOL) {
            return 3;
        }
        n = this.non_ascii;
        if (n == 0) {
            if (!this.longLine) return 1;
            return 2;
        }
        if (this.ascii <= n) return 3;
        return 2;
    }

    @Override
    public void write(int n) throws IOException {
        this.check(n);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        int n3 = n;
        while (n3 < n2 + n) {
            this.check(arrby[n3]);
            ++n3;
        }
        return;
    }
}

