package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public final class ByteStreams {
   private static final int BUFFER_SIZE = 8192;
   private static final int MAX_ARRAY_LEN = 2147483639;
   private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream() {
      public String toString() {
         return "ByteStreams.nullOutputStream()";
      }

      public void write(int var1) {
      }

      public void write(byte[] var1) {
         Preconditions.checkNotNull(var1);
      }

      public void write(byte[] var1, int var2, int var3) {
         Preconditions.checkNotNull(var1);
      }
   };
   private static final int TO_BYTE_ARRAY_DEQUE_SIZE = 20;
   private static final int ZERO_COPY_CHUNK_SIZE = 524288;

   private ByteStreams() {
   }

   private static byte[] combineBuffers(Deque<byte[]> var0, int var1) {
      byte[] var2 = new byte[var1];

      int var5;
      for(int var3 = var1; var3 > 0; var3 -= var5) {
         byte[] var4 = (byte[])var0.removeFirst();
         var5 = Math.min(var3, var4.length);
         System.arraycopy(var4, 0, var2, var1 - var3, var5);
      }

      return var2;
   }

   public static long copy(InputStream var0, OutputStream var1) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      byte[] var2 = createBuffer();
      long var3 = 0L;

      while(true) {
         int var5 = var0.read(var2);
         if (var5 == -1) {
            return var3;
         }

         var1.write(var2, 0, var5);
         var3 += (long)var5;
      }
   }

   public static long copy(ReadableByteChannel var0, WritableByteChannel var1) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      boolean var2 = var0 instanceof FileChannel;
      long var3 = 0L;
      if (var2) {
         FileChannel var12 = (FileChannel)var0;
         long var5 = var12.position();
         var3 = var5;

         long var9;
         do {
            long var7;
            do {
               var7 = var12.transferTo(var3, 524288L, var1);
               var9 = var3 + var7;
               var12.position(var9);
               var3 = var9;
            } while(var7 > 0L);

            var3 = var9;
         } while(var9 < var12.size());

         return var9 - var5;
      } else {
         ByteBuffer var11 = ByteBuffer.wrap(createBuffer());

         while(var0.read(var11) != -1) {
            var11.flip();

            while(var11.hasRemaining()) {
               var3 += (long)var1.write(var11);
            }

            var11.clear();
         }

         return var3;
      }
   }

   static byte[] createBuffer() {
      return new byte[8192];
   }

   public static long exhaust(InputStream var0) throws IOException {
      byte[] var1 = createBuffer();
      long var2 = 0L;

      while(true) {
         long var4 = (long)var0.read(var1);
         if (var4 == -1L) {
            return var2;
         }

         var2 += var4;
      }
   }

   public static InputStream limit(InputStream var0, long var1) {
      return new ByteStreams.LimitedInputStream(var0, var1);
   }

   public static ByteArrayDataInput newDataInput(ByteArrayInputStream var0) {
      return new ByteStreams.ByteArrayDataInputStream((ByteArrayInputStream)Preconditions.checkNotNull(var0));
   }

   public static ByteArrayDataInput newDataInput(byte[] var0) {
      return newDataInput(new ByteArrayInputStream(var0));
   }

   public static ByteArrayDataInput newDataInput(byte[] var0, int var1) {
      Preconditions.checkPositionIndex(var1, var0.length);
      return newDataInput(new ByteArrayInputStream(var0, var1, var0.length - var1));
   }

   public static ByteArrayDataOutput newDataOutput() {
      return newDataOutput(new ByteArrayOutputStream());
   }

   public static ByteArrayDataOutput newDataOutput(int var0) {
      if (var0 >= 0) {
         return newDataOutput(new ByteArrayOutputStream(var0));
      } else {
         throw new IllegalArgumentException(String.format("Invalid size: %s", var0));
      }
   }

   public static ByteArrayDataOutput newDataOutput(ByteArrayOutputStream var0) {
      return new ByteStreams.ByteArrayDataOutputStream((ByteArrayOutputStream)Preconditions.checkNotNull(var0));
   }

   public static OutputStream nullOutputStream() {
      return NULL_OUTPUT_STREAM;
   }

   public static int read(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      int var4 = 0;
      if (var3 < 0) {
         throw new IndexOutOfBoundsException(String.format("len (%s) cannot be negative", var3));
      } else {
         Preconditions.checkPositionIndexes(var2, var2 + var3, var1.length);

         while(var4 < var3) {
            int var5 = var0.read(var1, var2 + var4, var3 - var4);
            if (var5 == -1) {
               break;
            }

            var4 += var5;
         }

         return var4;
      }
   }

   public static <T> T readBytes(InputStream var0, ByteProcessor<T> var1) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      byte[] var2 = createBuffer();

      int var3;
      do {
         var3 = var0.read(var2);
      } while(var3 != -1 && var1.processBytes(var2, 0, var3));

      return var1.getResult();
   }

   public static void readFully(InputStream var0, byte[] var1) throws IOException {
      readFully(var0, var1, 0, var1.length);
   }

   public static void readFully(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      var2 = read(var0, var1, var2, var3);
      if (var2 != var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append("reached end of stream after reading ");
         var4.append(var2);
         var4.append(" bytes; ");
         var4.append(var3);
         var4.append(" bytes expected");
         throw new EOFException(var4.toString());
      }
   }

   public static void skipFully(InputStream var0, long var1) throws IOException {
      long var3 = skipUpTo(var0, var1);
      if (var3 < var1) {
         StringBuilder var5 = new StringBuilder();
         var5.append("reached end of stream after skipping ");
         var5.append(var3);
         var5.append(" bytes; ");
         var5.append(var1);
         var5.append(" bytes expected");
         throw new EOFException(var5.toString());
      }
   }

   private static long skipSafely(InputStream var0, long var1) throws IOException {
      int var3 = var0.available();
      if (var3 == 0) {
         var1 = 0L;
      } else {
         var1 = var0.skip(Math.min((long)var3, var1));
      }

      return var1;
   }

   static long skipUpTo(InputStream var0, long var1) throws IOException {
      byte[] var3 = null;

      long var4;
      byte[] var10;
      for(var4 = 0L; var4 < var1; var3 = var10) {
         long var6 = var1 - var4;
         long var8 = skipSafely(var0, var6);
         var10 = var3;
         long var11 = var8;
         if (var8 == 0L) {
            int var13 = (int)Math.min(var6, 8192L);
            var10 = var3;
            if (var3 == null) {
               var10 = new byte[var13];
            }

            var8 = (long)var0.read(var10, 0, var13);
            var11 = var8;
            if (var8 == -1L) {
               break;
            }
         }

         var4 += var11;
      }

      return var4;
   }

   public static byte[] toByteArray(InputStream var0) throws IOException {
      Preconditions.checkNotNull(var0);
      return toByteArrayInternal(var0, new ArrayDeque(20), 0);
   }

   static byte[] toByteArray(InputStream var0, long var1) throws IOException {
      boolean var3;
      if (var1 >= 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "expectedSize (%s) must be non-negative", var1);
      if (var1 <= 2147483639L) {
         int var4 = (int)var1;
         byte[] var5 = new byte[var4];

         int var6;
         int var8;
         for(var6 = var4; var6 > 0; var6 -= var8) {
            int var7 = var4 - var6;
            var8 = var0.read(var5, var7, var6);
            if (var8 == -1) {
               return Arrays.copyOf(var5, var7);
            }
         }

         var6 = var0.read();
         if (var6 == -1) {
            return var5;
         } else {
            ArrayDeque var9 = new ArrayDeque(22);
            var9.add(var5);
            var9.add(new byte[]{(byte)var6});
            return toByteArrayInternal(var0, var9, var4 + 1);
         }
      } else {
         StringBuilder var10 = new StringBuilder();
         var10.append(var1);
         var10.append(" bytes is too large to fit in a byte array");
         throw new OutOfMemoryError(var10.toString());
      }
   }

   private static byte[] toByteArrayInternal(InputStream var0, Deque<byte[]> var1, int var2) throws IOException {
      short var3 = 8192;
      int var4 = var2;

      for(var2 = var3; var4 < 2147483639; var2 = IntMath.saturatedMultiply(var2, 2)) {
         int var5 = Math.min(var2, 2147483639 - var4);
         byte[] var6 = new byte[var5];
         var1.add(var6);

         int var7;
         for(int var8 = 0; var8 < var5; var4 += var7) {
            var7 = var0.read(var6, var8, var5 - var8);
            if (var7 == -1) {
               return combineBuffers(var1, var4);
            }

            var8 += var7;
         }
      }

      if (var0.read() == -1) {
         return combineBuffers(var1, 2147483639);
      } else {
         throw new OutOfMemoryError("input is too large to fit in a byte array");
      }
   }

   private static class ByteArrayDataInputStream implements ByteArrayDataInput {
      final DataInput input;

      ByteArrayDataInputStream(ByteArrayInputStream var1) {
         this.input = new DataInputStream(var1);
      }

      public boolean readBoolean() {
         try {
            boolean var1 = this.input.readBoolean();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public byte readByte() {
         try {
            byte var1 = this.input.readByte();
            return var1;
         } catch (EOFException var3) {
            throw new IllegalStateException(var3);
         } catch (IOException var4) {
            throw new AssertionError(var4);
         }
      }

      public char readChar() {
         try {
            char var1 = this.input.readChar();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public double readDouble() {
         try {
            double var1 = this.input.readDouble();
            return var1;
         } catch (IOException var4) {
            throw new IllegalStateException(var4);
         }
      }

      public float readFloat() {
         try {
            float var1 = this.input.readFloat();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public void readFully(byte[] var1) {
         try {
            this.input.readFully(var1);
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      public void readFully(byte[] var1, int var2, int var3) {
         try {
            this.input.readFully(var1, var2, var3);
         } catch (IOException var4) {
            throw new IllegalStateException(var4);
         }
      }

      public int readInt() {
         try {
            int var1 = this.input.readInt();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public String readLine() {
         try {
            String var1 = this.input.readLine();
            return var1;
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      public long readLong() {
         try {
            long var1 = this.input.readLong();
            return var1;
         } catch (IOException var4) {
            throw new IllegalStateException(var4);
         }
      }

      public short readShort() {
         try {
            short var1 = this.input.readShort();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public String readUTF() {
         try {
            String var1 = this.input.readUTF();
            return var1;
         } catch (IOException var2) {
            throw new IllegalStateException(var2);
         }
      }

      public int readUnsignedByte() {
         try {
            int var1 = this.input.readUnsignedByte();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public int readUnsignedShort() {
         try {
            int var1 = this.input.readUnsignedShort();
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }

      public int skipBytes(int var1) {
         try {
            var1 = this.input.skipBytes(var1);
            return var1;
         } catch (IOException var3) {
            throw new IllegalStateException(var3);
         }
      }
   }

   private static class ByteArrayDataOutputStream implements ByteArrayDataOutput {
      final ByteArrayOutputStream byteArrayOutputStream;
      final DataOutput output;

      ByteArrayDataOutputStream(ByteArrayOutputStream var1) {
         this.byteArrayOutputStream = var1;
         this.output = new DataOutputStream(var1);
      }

      public byte[] toByteArray() {
         return this.byteArrayOutputStream.toByteArray();
      }

      public void write(int var1) {
         try {
            this.output.write(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void write(byte[] var1) {
         try {
            this.output.write(var1);
         } catch (IOException var2) {
            throw new AssertionError(var2);
         }
      }

      public void write(byte[] var1, int var2, int var3) {
         try {
            this.output.write(var1, var2, var3);
         } catch (IOException var4) {
            throw new AssertionError(var4);
         }
      }

      public void writeBoolean(boolean var1) {
         try {
            this.output.writeBoolean(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeByte(int var1) {
         try {
            this.output.writeByte(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeBytes(String var1) {
         try {
            this.output.writeBytes(var1);
         } catch (IOException var2) {
            throw new AssertionError(var2);
         }
      }

      public void writeChar(int var1) {
         try {
            this.output.writeChar(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeChars(String var1) {
         try {
            this.output.writeChars(var1);
         } catch (IOException var2) {
            throw new AssertionError(var2);
         }
      }

      public void writeDouble(double var1) {
         try {
            this.output.writeDouble(var1);
         } catch (IOException var4) {
            throw new AssertionError(var4);
         }
      }

      public void writeFloat(float var1) {
         try {
            this.output.writeFloat(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeInt(int var1) {
         try {
            this.output.writeInt(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeLong(long var1) {
         try {
            this.output.writeLong(var1);
         } catch (IOException var4) {
            throw new AssertionError(var4);
         }
      }

      public void writeShort(int var1) {
         try {
            this.output.writeShort(var1);
         } catch (IOException var3) {
            throw new AssertionError(var3);
         }
      }

      public void writeUTF(String var1) {
         try {
            this.output.writeUTF(var1);
         } catch (IOException var2) {
            throw new AssertionError(var2);
         }
      }
   }

   private static final class LimitedInputStream extends FilterInputStream {
      private long left;
      private long mark = -1L;

      LimitedInputStream(InputStream var1, long var2) {
         super(var1);
         Preconditions.checkNotNull(var1);
         boolean var4;
         if (var2 >= 0L) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "limit must be non-negative");
         this.left = var2;
      }

      public int available() throws IOException {
         return (int)Math.min((long)this.in.available(), this.left);
      }

      public void mark(int var1) {
         synchronized(this){}

         try {
            this.in.mark(var1);
            this.mark = this.left;
         } finally {
            ;
         }

      }

      public int read() throws IOException {
         if (this.left == 0L) {
            return -1;
         } else {
            int var1 = this.in.read();
            if (var1 != -1) {
               --this.left;
            }

            return var1;
         }
      }

      public int read(byte[] var1, int var2, int var3) throws IOException {
         long var4 = this.left;
         if (var4 == 0L) {
            return -1;
         } else {
            var3 = (int)Math.min((long)var3, var4);
            var2 = this.in.read(var1, var2, var3);
            if (var2 != -1) {
               this.left -= (long)var2;
            }

            return var2;
         }
      }

      public void reset() throws IOException {
         synchronized(this){}

         try {
            IOException var1;
            if (!this.in.markSupported()) {
               var1 = new IOException("Mark not supported");
               throw var1;
            }

            if (this.mark == -1L) {
               var1 = new IOException("Mark not set");
               throw var1;
            }

            this.in.reset();
            this.left = this.mark;
         } finally {
            ;
         }

      }

      public long skip(long var1) throws IOException {
         var1 = Math.min(var1, this.left);
         var1 = this.in.skip(var1);
         this.left -= var1;
         return var1;
      }
   }
}
