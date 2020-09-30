/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class LittleEndianDataInputStream
extends FilterInputStream
implements DataInput {
    public LittleEndianDataInputStream(InputStream inputStream2) {
        super(Preconditions.checkNotNull(inputStream2));
    }

    private byte readAndCheckByte() throws IOException, EOFException {
        int n = this.in.read();
        if (-1 == n) throw new EOFException();
        return (byte)n;
    }

    @Override
    public boolean readBoolean() throws IOException {
        if (this.readUnsignedByte() == 0) return false;
        return true;
    }

    @Override
    public byte readByte() throws IOException {
        return (byte)this.readUnsignedByte();
    }

    @Override
    public char readChar() throws IOException {
        return (char)this.readUnsignedShort();
    }

    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }

    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }

    @Override
    public void readFully(byte[] arrby) throws IOException {
        ByteStreams.readFully(this, arrby);
    }

    @Override
    public void readFully(byte[] arrby, int n, int n2) throws IOException {
        ByteStreams.readFully(this, arrby, n, n2);
    }

    @Override
    public int readInt() throws IOException {
        byte by = this.readAndCheckByte();
        byte by2 = this.readAndCheckByte();
        byte by3 = this.readAndCheckByte();
        return Ints.fromBytes(this.readAndCheckByte(), by3, by2, by);
    }

    @Override
    public String readLine() {
        throw new UnsupportedOperationException("readLine is not supported");
    }

    @Override
    public long readLong() throws IOException {
        byte by = this.readAndCheckByte();
        byte by2 = this.readAndCheckByte();
        byte by3 = this.readAndCheckByte();
        byte by4 = this.readAndCheckByte();
        byte by5 = this.readAndCheckByte();
        byte by6 = this.readAndCheckByte();
        byte by7 = this.readAndCheckByte();
        return Longs.fromBytes(this.readAndCheckByte(), by7, by6, by5, by4, by3, by2, by);
    }

    @Override
    public short readShort() throws IOException {
        return (short)this.readUnsignedShort();
    }

    @Override
    public String readUTF() throws IOException {
        return new DataInputStream(this.in).readUTF();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        int n = this.in.read();
        if (n < 0) throw new EOFException();
        return n;
    }

    @Override
    public int readUnsignedShort() throws IOException {
        byte by = this.readAndCheckByte();
        return Ints.fromBytes((byte)0, (byte)0, this.readAndCheckByte(), by);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        return (int)this.in.skip(n);
    }
}

