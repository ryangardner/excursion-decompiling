package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class LittleEndianDataInputStream extends FilterInputStream implements DataInput {
   public LittleEndianDataInputStream(InputStream var1) {
      super((InputStream)Preconditions.checkNotNull(var1));
   }

   private byte readAndCheckByte() throws IOException, EOFException {
      int var1 = this.in.read();
      if (-1 != var1) {
         return (byte)var1;
      } else {
         throw new EOFException();
      }
   }

   public boolean readBoolean() throws IOException {
      boolean var1;
      if (this.readUnsignedByte() != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public byte readByte() throws IOException {
      return (byte)this.readUnsignedByte();
   }

   public char readChar() throws IOException {
      return (char)this.readUnsignedShort();
   }

   public double readDouble() throws IOException {
      return Double.longBitsToDouble(this.readLong());
   }

   public float readFloat() throws IOException {
      return Float.intBitsToFloat(this.readInt());
   }

   public void readFully(byte[] var1) throws IOException {
      ByteStreams.readFully(this, var1);
   }

   public void readFully(byte[] var1, int var2, int var3) throws IOException {
      ByteStreams.readFully(this, var1, var2, var3);
   }

   public int readInt() throws IOException {
      byte var1 = this.readAndCheckByte();
      byte var2 = this.readAndCheckByte();
      byte var3 = this.readAndCheckByte();
      return Ints.fromBytes(this.readAndCheckByte(), var3, var2, var1);
   }

   public String readLine() {
      throw new UnsupportedOperationException("readLine is not supported");
   }

   public long readLong() throws IOException {
      byte var1 = this.readAndCheckByte();
      byte var2 = this.readAndCheckByte();
      byte var3 = this.readAndCheckByte();
      byte var4 = this.readAndCheckByte();
      byte var5 = this.readAndCheckByte();
      byte var6 = this.readAndCheckByte();
      byte var7 = this.readAndCheckByte();
      return Longs.fromBytes(this.readAndCheckByte(), var7, var6, var5, var4, var3, var2, var1);
   }

   public short readShort() throws IOException {
      return (short)this.readUnsignedShort();
   }

   public String readUTF() throws IOException {
      return (new DataInputStream(this.in)).readUTF();
   }

   public int readUnsignedByte() throws IOException {
      int var1 = this.in.read();
      if (var1 >= 0) {
         return var1;
      } else {
         throw new EOFException();
      }
   }

   public int readUnsignedShort() throws IOException {
      byte var1 = this.readAndCheckByte();
      return Ints.fromBytes((byte)0, (byte)0, this.readAndCheckByte(), var1);
   }

   public int skipBytes(int var1) throws IOException {
      return (int)this.in.skip((long)var1);
   }
}
