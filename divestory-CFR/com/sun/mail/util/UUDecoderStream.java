/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import com.sun.mail.util.LineInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UUDecoderStream
extends FilterInputStream {
    private byte[] buffer;
    private int bufsize = 0;
    private boolean gotEnd = false;
    private boolean gotPrefix = false;
    private int index = 0;
    private LineInputStream lin;
    private int mode;
    private String name;

    public UUDecoderStream(InputStream inputStream2) {
        super(inputStream2);
        this.lin = new LineInputStream(inputStream2);
        this.buffer = new byte[45];
    }

    private boolean decode() throws IOException {
        String string2;
        if (this.gotEnd) {
            return false;
        }
        this.bufsize = 0;
        do {
            if ((string2 = this.lin.readLine()) == null) throw new IOException("Missing End");
            if (!string2.regionMatches(true, 0, "end", 0, 3)) continue;
            this.gotEnd = true;
            return false;
        } while (string2.length() == 0);
        int n = string2.charAt(0);
        if (n < 32) throw new IOException("Buffer format error");
        int n2 = n - 32 & 63;
        if (n2 == 0) {
            string2 = this.lin.readLine();
            if (string2 == null) throw new IOException("Missing End");
            if (!string2.regionMatches(true, 0, "end", 0, 3)) throw new IOException("Missing End");
            this.gotEnd = true;
            return false;
        }
        n = (n2 * 8 + 5) / 6;
        if (string2.length() < n + 1) throw new IOException("Short buffer error");
        int n3 = 1;
        while (this.bufsize < n2) {
            int n4;
            int n5 = n3 + 1;
            n3 = (byte)(string2.charAt(n3) - 32 & 63);
            n = n5 + 1;
            n5 = (byte)(string2.charAt(n5) - 32 & 63);
            byte[] arrby = this.buffer;
            int n6 = this.bufsize;
            this.bufsize = n4 = n6 + 1;
            arrby[n6] = (byte)(n3 << 2 & 252 | n5 >>> 4 & 3);
            if (n4 < n2) {
                n3 = n + 1;
                n = (byte)(string2.charAt(n) - 32 & 63);
                arrby = this.buffer;
                n6 = this.bufsize;
                this.bufsize = n6 + 1;
                arrby[n6] = (byte)(n5 << 4 & 240 | n >>> 2 & 15);
                n5 = n;
                n = n3;
            }
            n3 = n;
            if (this.bufsize >= n2) continue;
            n3 = (byte)(string2.charAt(n) - 32 & 63);
            arrby = this.buffer;
            n6 = this.bufsize;
            this.bufsize = n6 + 1;
            arrby[n6] = (byte)(n3 & 63 | n5 << 6 & 192);
            n3 = n + 1;
        }
        return true;
    }

    private void readPrefix() throws IOException {
        CharSequence charSequence;
        if (this.gotPrefix) {
            return;
        }
        do {
            if ((charSequence = this.lin.readLine()) == null) throw new IOException("UUDecoder error: No Begin");
        } while (!((String)charSequence).regionMatches(true, 0, "begin", 0, 5));
        try {
            this.mode = Integer.parseInt(((String)charSequence).substring(6, 9));
        }
        catch (NumberFormatException numberFormatException) {
            charSequence = new StringBuilder("UUDecoder error: ");
            ((StringBuilder)charSequence).append(numberFormatException.toString());
            throw new IOException(((StringBuilder)charSequence).toString());
        }
        this.name = ((String)charSequence).substring(10);
        this.gotPrefix = true;
    }

    @Override
    public int available() throws IOException {
        return this.in.available() * 3 / 4 + (this.bufsize - this.index);
    }

    public int getMode() throws IOException {
        this.readPrefix();
        return this.mode;
    }

    public String getName() throws IOException {
        this.readPrefix();
        return this.name;
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        if (this.index >= this.bufsize) {
            this.readPrefix();
            if (!this.decode()) {
                return -1;
            }
            this.index = 0;
        }
        byte[] arrby = this.buffer;
        int n = this.index;
        this.index = n + 1;
        return arrby[n] & 255;
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

