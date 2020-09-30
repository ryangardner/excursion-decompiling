package com.fasterxml.jackson.core.util;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;

public final class ByteArrayBuilder extends OutputStream {
   static final int DEFAULT_BLOCK_ARRAY_SIZE = 40;
   private static final int INITIAL_BLOCK_SIZE = 500;
   private static final int MAX_BLOCK_SIZE = 131072;
   public static final byte[] NO_BYTES = new byte[0];
   private final BufferRecycler _bufferRecycler;
   private byte[] _currBlock;
   private int _currBlockPtr;
   private final LinkedList<byte[]> _pastBlocks;
   private int _pastLen;

   public ByteArrayBuilder() {
      this((BufferRecycler)null);
   }

   public ByteArrayBuilder(int var1) {
      this((BufferRecycler)null, var1);
   }

   public ByteArrayBuilder(BufferRecycler var1) {
      this(var1, 500);
   }

   public ByteArrayBuilder(BufferRecycler var1, int var2) {
      this._pastBlocks = new LinkedList();
      this._bufferRecycler = var1;
      byte[] var3;
      if (var1 == null) {
         var3 = new byte[var2];
      } else {
         var3 = var1.allocByteBuffer(2);
      }

      this._currBlock = var3;
   }

   private ByteArrayBuilder(BufferRecycler var1, byte[] var2, int var3) {
      this._pastBlocks = new LinkedList();
      this._bufferRecycler = null;
      this._currBlock = var2;
      this._currBlockPtr = var3;
   }

   private void _allocMore() {
      int var1 = this._pastLen + this._currBlock.length;
      if (var1 >= 0) {
         this._pastLen = var1;
         int var2 = Math.max(var1 >> 1, 1000);
         var1 = var2;
         if (var2 > 131072) {
            var1 = 131072;
         }

         this._pastBlocks.add(this._currBlock);
         this._currBlock = new byte[var1];
         this._currBlockPtr = 0;
      } else {
         throw new IllegalStateException("Maximum Java array size (2GB) exceeded by `ByteArrayBuilder`");
      }
   }

   public static ByteArrayBuilder fromInitial(byte[] var0, int var1) {
      return new ByteArrayBuilder((BufferRecycler)null, var0, var1);
   }

   public void append(int var1) {
      if (this._currBlockPtr >= this._currBlock.length) {
         this._allocMore();
      }

      byte[] var2 = this._currBlock;
      int var3 = this._currBlockPtr++;
      var2[var3] = (byte)((byte)var1);
   }

   public void appendFourBytes(int var1) {
      int var2 = this._currBlockPtr;
      byte[] var3 = this._currBlock;
      if (var2 + 3 < var3.length) {
         int var4 = var2 + 1;
         this._currBlockPtr = var4;
         var3[var2] = (byte)((byte)(var1 >> 24));
         var2 = var4 + 1;
         this._currBlockPtr = var2;
         var3[var4] = (byte)((byte)(var1 >> 16));
         var4 = var2 + 1;
         this._currBlockPtr = var4;
         var3[var2] = (byte)((byte)(var1 >> 8));
         this._currBlockPtr = var4 + 1;
         var3[var4] = (byte)((byte)var1);
      } else {
         this.append(var1 >> 24);
         this.append(var1 >> 16);
         this.append(var1 >> 8);
         this.append(var1);
      }

   }

   public void appendThreeBytes(int var1) {
      int var2 = this._currBlockPtr;
      byte[] var3 = this._currBlock;
      if (var2 + 2 < var3.length) {
         int var4 = var2 + 1;
         this._currBlockPtr = var4;
         var3[var2] = (byte)((byte)(var1 >> 16));
         var2 = var4 + 1;
         this._currBlockPtr = var2;
         var3[var4] = (byte)((byte)(var1 >> 8));
         this._currBlockPtr = var2 + 1;
         var3[var2] = (byte)((byte)var1);
      } else {
         this.append(var1 >> 16);
         this.append(var1 >> 8);
         this.append(var1);
      }

   }

   public void appendTwoBytes(int var1) {
      int var2 = this._currBlockPtr;
      byte[] var3 = this._currBlock;
      if (var2 + 1 < var3.length) {
         int var4 = var2 + 1;
         this._currBlockPtr = var4;
         var3[var2] = (byte)((byte)(var1 >> 8));
         this._currBlockPtr = var4 + 1;
         var3[var4] = (byte)((byte)var1);
      } else {
         this.append(var1 >> 8);
         this.append(var1);
      }

   }

   public void close() {
   }

   public byte[] completeAndCoalesce(int var1) {
      this._currBlockPtr = var1;
      return this.toByteArray();
   }

   public byte[] finishCurrentSegment() {
      this._allocMore();
      return this._currBlock;
   }

   public void flush() {
   }

   public byte[] getCurrentSegment() {
      return this._currBlock;
   }

   public int getCurrentSegmentLength() {
      return this._currBlockPtr;
   }

   public void release() {
      this.reset();
      BufferRecycler var1 = this._bufferRecycler;
      if (var1 != null) {
         byte[] var2 = this._currBlock;
         if (var2 != null) {
            var1.releaseByteBuffer(2, var2);
            this._currBlock = null;
         }
      }

   }

   public void reset() {
      this._pastLen = 0;
      this._currBlockPtr = 0;
      if (!this._pastBlocks.isEmpty()) {
         this._pastBlocks.clear();
      }

   }

   public byte[] resetAndGetFirstSegment() {
      this.reset();
      return this._currBlock;
   }

   public void setCurrentSegmentLength(int var1) {
      this._currBlockPtr = var1;
   }

   public int size() {
      return this._pastLen + this._currBlockPtr;
   }

   public byte[] toByteArray() {
      int var1 = this._pastLen + this._currBlockPtr;
      if (var1 == 0) {
         return NO_BYTES;
      } else {
         byte[] var2 = new byte[var1];
         Iterator var3 = this._pastBlocks.iterator();

         int var4;
         int var6;
         for(var4 = 0; var3.hasNext(); var4 += var6) {
            byte[] var5 = (byte[])var3.next();
            var6 = var5.length;
            System.arraycopy(var5, 0, var2, var4, var6);
         }

         System.arraycopy(this._currBlock, 0, var2, var4, this._currBlockPtr);
         var4 += this._currBlockPtr;
         if (var4 == var1) {
            if (!this._pastBlocks.isEmpty()) {
               this.reset();
            }

            return var2;
         } else {
            StringBuilder var7 = new StringBuilder();
            var7.append("Internal error: total len assumed to be ");
            var7.append(var1);
            var7.append(", copied ");
            var7.append(var4);
            var7.append(" bytes");
            throw new RuntimeException(var7.toString());
         }
      }
   }

   public void write(int var1) {
      this.append(var1);
   }

   public void write(byte[] var1) {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) {
      int var4 = var2;

      while(true) {
         int var5 = Math.min(this._currBlock.length - this._currBlockPtr, var3);
         int var6 = var4;
         var2 = var3;
         if (var5 > 0) {
            System.arraycopy(var1, var4, this._currBlock, this._currBlockPtr, var5);
            var6 = var4 + var5;
            this._currBlockPtr += var5;
            var2 = var3 - var5;
         }

         if (var2 <= 0) {
            return;
         }

         this._allocMore();
         var4 = var6;
         var3 = var2;
      }
   }
}
