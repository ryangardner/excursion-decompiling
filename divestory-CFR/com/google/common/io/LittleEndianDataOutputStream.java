/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class LittleEndianDataOutputStream
extends FilterOutputStream
implements DataOutput {
    public LittleEndianDataOutputStream(OutputStream outputStream2) {
        super(new DataOutputStream(Preconditions.checkNotNull(outputStream2)));
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        this.out.write(arrby, n, n2);
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        ((DataOutputStream)this.out).writeBoolean(bl);
    }

    @Override
    public void writeByte(int n) throws IOException {
        ((DataOutputStream)this.out).writeByte(n);
    }

    @Deprecated
    @Override
    public void writeBytes(String string2) throws IOException {
        ((DataOutputStream)this.out).writeBytes(string2);
    }

    @Override
    public void writeChar(int n) throws IOException {
        this.writeShort(n);
    }

    @Override
    public void writeChars(String string2) throws IOException {
        int n = 0;
        while (n < string2.length()) {
            this.writeChar(string2.charAt(n));
            ++n;
        }
    }

    @Override
    public void writeDouble(double d) throws IOException {
        this.writeLong(Double.doubleToLongBits(d));
    }

    @Override
    public void writeFloat(float f) throws IOException {
        this.writeInt(Float.floatToIntBits(f));
    }

    @Override
    public void writeInt(int n) throws IOException {
        this.out.write(n & 255);
        this.out.write(n >> 8 & 255);
        this.out.write(n >> 16 & 255);
        this.out.write(n >> 24 & 255);
    }

    @Override
    public void writeLong(long l) throws IOException {
        byte[] arrby = Longs.toByteArray(Long.reverseBytes(l));
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void writeShort(int n) throws IOException {
        this.out.write(n & 255);
        this.out.write(n >> 8 & 255);
    }

    @Override
    public void writeUTF(String string2) throws IOException {
        ((DataOutputStream)this.out).writeUTF(string2);
    }
}

