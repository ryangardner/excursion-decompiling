/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class UUEncoderStream
extends FilterOutputStream {
    private byte[] buffer;
    private int bufsize = 0;
    protected int mode;
    protected String name;
    private boolean wrotePrefix = false;

    public UUEncoderStream(OutputStream outputStream2) {
        this(outputStream2, "encoder.buf", 644);
    }

    public UUEncoderStream(OutputStream outputStream2, String string2) {
        this(outputStream2, string2, 644);
    }

    public UUEncoderStream(OutputStream outputStream2, String string2, int n) {
        super(outputStream2);
        this.name = string2;
        this.mode = n;
        this.buffer = new byte[45];
    }

    private void encode() throws IOException {
        this.out.write((this.bufsize & 63) + 32);
        int n = 0;
        do {
            int n2;
            int n3;
            byte by;
            block4 : {
                block5 : {
                    block2 : {
                        block3 : {
                            int n4;
                            if (n >= (n4 = this.bufsize)) {
                                this.out.write(10);
                                return;
                            }
                            byte[] arrby = this.buffer;
                            n2 = n + 1;
                            by = arrby[n];
                            n3 = 1;
                            if (n2 >= n4) break block2;
                            n3 = n2 + 1;
                            n = arrby[n2];
                            if (n3 >= n4) break block3;
                            n4 = n3 + 1;
                            n3 = arrby[n3];
                            n2 = n;
                            n = n4;
                            break block4;
                        }
                        n2 = n;
                        n = n3;
                        break block5;
                    }
                    n = n2;
                    n2 = n3;
                }
                n3 = 1;
            }
            this.out.write((by >>> 2 & 63) + 32);
            this.out.write((by << 4 & 48 | n2 >>> 4 & 15) + 32);
            this.out.write((n2 << 2 & 60 | n3 >>> 6 & 3) + 32);
            this.out.write((n3 & 63) + 32);
        } while (true);
    }

    private void writePrefix() throws IOException {
        if (this.wrotePrefix) return;
        PrintStream printStream = new PrintStream(this.out);
        StringBuilder stringBuilder = new StringBuilder("begin ");
        stringBuilder.append(this.mode);
        stringBuilder.append(" ");
        stringBuilder.append(this.name);
        printStream.println(stringBuilder.toString());
        printStream.flush();
        this.wrotePrefix = true;
    }

    private void writeSuffix() throws IOException {
        PrintStream printStream = new PrintStream(this.out);
        printStream.println(" \nend");
        printStream.flush();
    }

    @Override
    public void close() throws IOException {
        this.flush();
        this.out.close();
    }

    @Override
    public void flush() throws IOException {
        if (this.bufsize > 0) {
            this.writePrefix();
            this.encode();
        }
        this.writeSuffix();
        this.out.flush();
    }

    public void setNameMode(String string2, int n) {
        this.name = string2;
        this.mode = n;
    }

    @Override
    public void write(int n) throws IOException {
        int n2;
        byte[] arrby = this.buffer;
        int n3 = this.bufsize;
        this.bufsize = n2 = n3 + 1;
        arrby[n3] = (byte)n;
        if (n2 != 45) return;
        this.writePrefix();
        this.encode();
        this.bufsize = 0;
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        int n3 = 0;
        while (n3 < n2) {
            this.write(arrby[n + n3]);
            ++n3;
        }
        return;
    }
}

