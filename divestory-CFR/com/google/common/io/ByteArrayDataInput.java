/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import java.io.DataInput;

public interface ByteArrayDataInput
extends DataInput {
    @Override
    public boolean readBoolean();

    @Override
    public byte readByte();

    @Override
    public char readChar();

    @Override
    public double readDouble();

    @Override
    public float readFloat();

    @Override
    public void readFully(byte[] var1);

    @Override
    public void readFully(byte[] var1, int var2, int var3);

    @Override
    public int readInt();

    @Override
    public String readLine();

    @Override
    public long readLong();

    @Override
    public short readShort();

    @Override
    public String readUTF();

    @Override
    public int readUnsignedByte();

    @Override
    public int readUnsignedShort();

    @Override
    public int skipBytes(int var1);
}

