/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.imap.protocol;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

public class BASE64MailboxEncoder {
    private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', ','};
    protected byte[] buffer = new byte[4];
    protected int bufsize = 0;
    protected Writer out = null;
    protected boolean started = false;

    public BASE64MailboxEncoder(Writer writer) {
        this.out = writer;
    }

    public static String encode(String string2) {
        char[] arrc = string2.toCharArray();
        int n = arrc.length;
        CharArrayWriter charArrayWriter = new CharArrayWriter(n);
        int n2 = 0;
        BASE64MailboxEncoder bASE64MailboxEncoder = null;
        boolean bl = false;
        do {
            BASE64MailboxEncoder bASE64MailboxEncoder2;
            if (n2 >= n) {
                if (bASE64MailboxEncoder != null) {
                    bASE64MailboxEncoder.flush();
                }
                if (!bl) return string2;
                return charArrayWriter.toString();
            }
            char c = arrc[n2];
            if (c >= ' ' && c <= '~') {
                if (bASE64MailboxEncoder != null) {
                    bASE64MailboxEncoder.flush();
                }
                if (c == '&') {
                    charArrayWriter.write(38);
                    charArrayWriter.write(45);
                    bl = true;
                    bASE64MailboxEncoder2 = bASE64MailboxEncoder;
                } else {
                    charArrayWriter.write(c);
                    bASE64MailboxEncoder2 = bASE64MailboxEncoder;
                }
            } else {
                bASE64MailboxEncoder2 = bASE64MailboxEncoder;
                if (bASE64MailboxEncoder == null) {
                    bASE64MailboxEncoder2 = new BASE64MailboxEncoder(charArrayWriter);
                    bl = true;
                }
                bASE64MailboxEncoder2.write(c);
            }
            ++n2;
            bASE64MailboxEncoder = bASE64MailboxEncoder2;
        } while (true);
    }

    protected void encode() throws IOException {
        int n = this.bufsize;
        if (n == 1) {
            n = this.buffer[0];
            this.out.write(pem_array[n >>> 2 & 63]);
            this.out.write(pem_array[(n << 4 & 48) + 0]);
            return;
        }
        if (n == 2) {
            byte[] arrby = this.buffer;
            n = arrby[0];
            byte by = arrby[1];
            this.out.write(pem_array[n >>> 2 & 63]);
            this.out.write(pem_array[(n << 4 & 48) + (by >>> 4 & 15)]);
            this.out.write(pem_array[(by << 2 & 60) + 0]);
            return;
        }
        byte[] arrby = this.buffer;
        byte by = arrby[0];
        n = arrby[1];
        byte by2 = arrby[2];
        this.out.write(pem_array[by >>> 2 & 63]);
        this.out.write(pem_array[(by << 4 & 48) + (n >>> 4 & 15)]);
        this.out.write(pem_array[(n << 2 & 60) + (by2 >>> 6 & 3)]);
        this.out.write(pem_array[by2 & 63]);
        if (this.bufsize != 4) return;
        arrby = this.buffer;
        arrby[0] = arrby[3];
    }

    public void flush() {
        try {
            if (this.bufsize > 0) {
                this.encode();
                this.bufsize = 0;
            }
            if (!this.started) return;
            this.out.write(45);
            this.started = false;
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public void write(int n) {
        byte[] arrby;
        int n2;
        int n3;
        try {
            if (!this.started) {
                this.started = true;
                this.out.write(38);
            }
            arrby = this.buffer;
            n3 = this.bufsize;
            this.bufsize = n2 = n3 + 1;
        }
        catch (IOException iOException) {
            return;
        }
        arrby[n3] = (byte)(n >> 8);
        arrby = this.buffer;
        this.bufsize = n3 = n2 + 1;
        arrby[n2] = (byte)(n & 255);
        if (n3 < 3) return;
        this.encode();
        this.bufsize -= 3;
    }
}

