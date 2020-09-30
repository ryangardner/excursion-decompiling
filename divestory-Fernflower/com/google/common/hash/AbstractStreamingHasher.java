package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class AbstractStreamingHasher extends AbstractHasher {
   private final ByteBuffer buffer;
   private final int bufferSize;
   private final int chunkSize;

   protected AbstractStreamingHasher(int var1) {
      this(var1, var1);
   }

   protected AbstractStreamingHasher(int var1, int var2) {
      boolean var3;
      if (var2 % var1 == 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      this.buffer = ByteBuffer.allocate(var2 + 7).order(ByteOrder.LITTLE_ENDIAN);
      this.bufferSize = var2;
      this.chunkSize = var1;
   }

   private void munch() {
      this.buffer.flip();

      while(this.buffer.remaining() >= this.chunkSize) {
         this.process(this.buffer);
      }

      this.buffer.compact();
   }

   private void munchIfFull() {
      if (this.buffer.remaining() < 8) {
         this.munch();
      }

   }

   private Hasher putBytesInternal(ByteBuffer var1) {
      if (var1.remaining() <= this.buffer.remaining()) {
         this.buffer.put(var1);
         this.munchIfFull();
         return this;
      } else {
         int var2 = this.bufferSize;
         int var3 = this.buffer.position();

         for(int var4 = 0; var4 < var2 - var3; ++var4) {
            this.buffer.put(var1.get());
         }

         this.munch();

         while(var1.remaining() >= this.chunkSize) {
            this.process(var1);
         }

         this.buffer.put(var1);
         return this;
      }
   }

   public final HashCode hash() {
      this.munch();
      this.buffer.flip();
      if (this.buffer.remaining() > 0) {
         this.processRemaining(this.buffer);
         ByteBuffer var1 = this.buffer;
         var1.position(var1.limit());
      }

      return this.makeHash();
   }

   protected abstract HashCode makeHash();

   protected abstract void process(ByteBuffer var1);

   protected void processRemaining(ByteBuffer var1) {
      var1.position(var1.limit());
      var1.limit(this.chunkSize + 7);

      while(true) {
         int var2 = var1.position();
         int var3 = this.chunkSize;
         if (var2 >= var3) {
            var1.limit(var3);
            var1.flip();
            this.process(var1);
            return;
         }

         var1.putLong(0L);
      }
   }

   public final Hasher putByte(byte var1) {
      this.buffer.put(var1);
      this.munchIfFull();
      return this;
   }

   public final Hasher putBytes(ByteBuffer var1) {
      ByteOrder var2 = var1.order();

      Hasher var3;
      try {
         var1.order(ByteOrder.LITTLE_ENDIAN);
         var3 = this.putBytesInternal(var1);
      } finally {
         var1.order(var2);
      }

      return var3;
   }

   public final Hasher putBytes(byte[] var1, int var2, int var3) {
      return this.putBytesInternal(ByteBuffer.wrap(var1, var2, var3).order(ByteOrder.LITTLE_ENDIAN));
   }

   public final Hasher putChar(char var1) {
      this.buffer.putChar(var1);
      this.munchIfFull();
      return this;
   }

   public final Hasher putInt(int var1) {
      this.buffer.putInt(var1);
      this.munchIfFull();
      return this;
   }

   public final Hasher putLong(long var1) {
      this.buffer.putLong(var1);
      this.munchIfFull();
      return this;
   }

   public final Hasher putShort(short var1) {
      this.buffer.putShort(var1);
      this.munchIfFull();
      return this;
   }
}
