package com.fasterxml.jackson.core.util;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class BufferRecycler {
   public static final int BYTE_BASE64_CODEC_BUFFER = 3;
   private static final int[] BYTE_BUFFER_LENGTHS = new int[]{8000, 8000, 2000, 2000};
   public static final int BYTE_READ_IO_BUFFER = 0;
   public static final int BYTE_WRITE_CONCAT_BUFFER = 2;
   public static final int BYTE_WRITE_ENCODING_BUFFER = 1;
   private static final int[] CHAR_BUFFER_LENGTHS = new int[]{4000, 4000, 200, 200};
   public static final int CHAR_CONCAT_BUFFER = 1;
   public static final int CHAR_NAME_COPY_BUFFER = 3;
   public static final int CHAR_TEXT_BUFFER = 2;
   public static final int CHAR_TOKEN_BUFFER = 0;
   protected final AtomicReferenceArray<byte[]> _byteBuffers;
   protected final AtomicReferenceArray<char[]> _charBuffers;

   public BufferRecycler() {
      this(4, 4);
   }

   protected BufferRecycler(int var1, int var2) {
      this._byteBuffers = new AtomicReferenceArray(var1);
      this._charBuffers = new AtomicReferenceArray(var2);
   }

   public final byte[] allocByteBuffer(int var1) {
      return this.allocByteBuffer(var1, 0);
   }

   public byte[] allocByteBuffer(int var1, int var2) {
      int var3 = this.byteBufferLength(var1);
      int var4 = var2;
      if (var2 < var3) {
         var4 = var3;
      }

      byte[] var5 = (byte[])this._byteBuffers.getAndSet(var1, (Object)null);
      byte[] var6;
      if (var5 != null) {
         var6 = var5;
         if (var5.length >= var4) {
            return var6;
         }
      }

      var6 = this.balloc(var4);
      return var6;
   }

   public final char[] allocCharBuffer(int var1) {
      return this.allocCharBuffer(var1, 0);
   }

   public char[] allocCharBuffer(int var1, int var2) {
      int var3 = this.charBufferLength(var1);
      int var4 = var2;
      if (var2 < var3) {
         var4 = var3;
      }

      char[] var5 = (char[])this._charBuffers.getAndSet(var1, (Object)null);
      char[] var6;
      if (var5 != null) {
         var6 = var5;
         if (var5.length >= var4) {
            return var6;
         }
      }

      var6 = this.calloc(var4);
      return var6;
   }

   protected byte[] balloc(int var1) {
      return new byte[var1];
   }

   protected int byteBufferLength(int var1) {
      return BYTE_BUFFER_LENGTHS[var1];
   }

   protected char[] calloc(int var1) {
      return new char[var1];
   }

   protected int charBufferLength(int var1) {
      return CHAR_BUFFER_LENGTHS[var1];
   }

   public void releaseByteBuffer(int var1, byte[] var2) {
      this._byteBuffers.set(var1, var2);
   }

   public void releaseCharBuffer(int var1, char[] var2) {
      this._charBuffers.set(var1, var2);
   }
}
