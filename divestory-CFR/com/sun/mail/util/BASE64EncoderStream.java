/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BASE64EncoderStream
extends FilterOutputStream {
    private static byte[] newline = new byte[]{13, 10};
    private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private byte[] buffer;
    private int bufsize;
    private int bytesPerLine;
    private int count;
    private int lineLimit;
    private boolean noCRLF;
    private byte[] outbuf;

    public BASE64EncoderStream(OutputStream outputStream2) {
        this(outputStream2, 76);
    }

    public BASE64EncoderStream(OutputStream arrby, int n) {
        int n2;
        block5 : {
            block4 : {
                super((OutputStream)arrby);
                this.bufsize = 0;
                this.count = 0;
                this.noCRLF = false;
                this.buffer = new byte[3];
                if (n == Integer.MAX_VALUE) break block4;
                n2 = n;
                if (n >= 4) break block5;
            }
            this.noCRLF = true;
            n2 = 76;
        }
        this.bytesPerLine = n = n2 / 4 * 4;
        this.lineLimit = n / 4 * 3;
        if (this.noCRLF) {
            this.outbuf = new byte[n];
            return;
        }
        arrby = new byte[n + 2];
        this.outbuf = arrby;
        arrby[n] = (byte)13;
        arrby[n + 1] = (byte)10;
    }

    private void encode() throws IOException {
        int n = BASE64EncoderStream.encodedSize(this.bufsize);
        this.out.write(BASE64EncoderStream.encode(this.buffer, 0, this.bufsize, this.outbuf), 0, n);
        this.count = n = this.count + n;
        if (n < this.bytesPerLine) return;
        if (!this.noCRLF) {
            this.out.write(newline);
        }
        this.count = 0;
    }

    public static byte[] encode(byte[] arrby) {
        if (arrby.length != 0) return BASE64EncoderStream.encode(arrby, 0, arrby.length, null);
        return arrby;
    }

    private static byte[] encode(byte[] arrobject, int n, int n2, byte[] arrobject2) {
        byte[] arrby = arrobject2;
        if (arrobject2 == null) {
            arrby = new byte[BASE64EncoderStream.encodedSize(n2)];
        }
        int n3 = 0;
        int n4 = n;
        n = n3;
        do {
            if (n2 < 3) {
                if (n2 != 1) break;
                n2 = (arrobject[n4] & 255) << 4;
                arrby[n + 3] = (byte)61;
                arrby[n + 2] = (byte)61;
                arrobject = pem_array;
                arrby[n + 1] = arrobject[n2 & 63];
                arrby[n + 0] = arrobject[n2 >> 6 & 63];
                return arrby;
            }
            n3 = n4 + 1;
            byte by = arrobject[n4];
            n4 = n3 + 1;
            n3 = ((by & 255) << 8 | arrobject[n3] & 255) << 8 | arrobject[n4] & 255;
            arrobject2 = pem_array;
            arrby[n + 3] = arrobject2[n3 & 63];
            arrby[n + 2] = arrobject2[(n3 >>= 6) & 63];
            arrby[n + 1] = arrobject2[(n3 >>= 6) & 63];
            arrby[n + 0] = arrobject2[n3 >> 6 & 63];
            n2 -= 3;
            n += 4;
            ++n4;
        } while (true);
        if (n2 != 2) return arrby;
        n2 = arrobject[n4];
        n2 = (arrobject[n4 + 1] & 255 | (n2 & 255) << 8) << 2;
        arrby[n + 3] = (byte)61;
        arrobject = pem_array;
        arrby[n + 2] = arrobject[n2 & 63];
        arrby[n + 1] = arrobject[(n2 >>= 6) & 63];
        arrby[n + 0] = arrobject[n2 >> 6 & 63];
        return arrby;
    }

    private static int encodedSize(int n) {
        return (n + 2) / 3 * 4;
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            this.flush();
            if (this.count > 0 && !this.noCRLF) {
                this.out.write(newline);
                this.out.flush();
            }
            this.out.close();
            return;
        }
    }

    @Override
    public void flush() throws IOException {
        synchronized (this) {
            if (this.bufsize > 0) {
                this.encode();
                this.bufsize = 0;
            }
            this.out.flush();
            return;
        }
    }

    @Override
    public void write(int n) throws IOException {
        synchronized (this) {
            int n2;
            byte[] arrby = this.buffer;
            int n3 = this.bufsize;
            this.bufsize = n2 = n3 + 1;
            arrby[n3] = (byte)n;
            if (n2 != 3) return;
            this.encode();
            this.bufsize = 0;
            return;
        }
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            int n3;
            int n4;
            void var4_4 = n4 + n3;
            for (n4 = n3; this.bufsize != 0 && n4 < var4_4; ++n4) {
                this.write(arrby[n4]);
            }
            int n5 = (this.bytesPerLine - this.count) / 4 * 3;
            int n6 = n4 + n5;
            n3 = n4;
            if (n6 < var4_4) {
                int n7;
                n3 = n7 = BASE64EncoderStream.encodedSize(n5);
                if (!this.noCRLF) {
                    byte[] arrby2 = this.outbuf;
                    n3 = n7 + 1;
                    arrby2[n7] = (byte)13;
                    this.outbuf[n3] = (byte)10;
                    ++n3;
                }
                this.out.write(BASE64EncoderStream.encode(arrby, n4, n5, this.outbuf), 0, n3);
                this.count = 0;
                n3 = n6;
            }
            do {
                if (this.lineLimit + n3 >= var4_4) {
                    n4 = n3;
                    if (n3 + 3 >= var4_4) break;
                    n4 = (var4_4 - n3) / 3 * 3;
                    n6 = BASE64EncoderStream.encodedSize(n4);
                    this.out.write(BASE64EncoderStream.encode(arrby, n3, n4, this.outbuf), 0, n6);
                    n4 = n3 + n4;
                    this.count += n6;
                    break;
                }
                this.out.write(BASE64EncoderStream.encode(arrby, n3, this.lineLimit, this.outbuf));
                n4 = this.lineLimit;
                n3 += n4;
            } while (true);
            while (n4 < var4_4) {
                this.write(arrby[n4]);
                ++n4;
            }
            return;
        }
    }
}

