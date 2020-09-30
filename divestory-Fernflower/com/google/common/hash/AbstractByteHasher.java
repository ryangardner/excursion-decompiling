package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class AbstractByteHasher extends AbstractHasher {
   private final ByteBuffer scratch;

   AbstractByteHasher() {
      this.scratch = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
   }

   private Hasher update(int var1) {
      try {
         this.update(this.scratch.array(), 0, var1);
      } finally {
         this.scratch.clear();
      }

      return this;
   }

   public Hasher putByte(byte var1) {
      this.update(var1);
      return this;
   }

   public Hasher putBytes(ByteBuffer var1) {
      this.update(var1);
      return this;
   }

   public Hasher putBytes(byte[] var1) {
      Preconditions.checkNotNull(var1);
      this.update(var1);
      return this;
   }

   public Hasher putBytes(byte[] var1, int var2, int var3) {
      Preconditions.checkPositionIndexes(var2, var2 + var3, var1.length);
      this.update(var1, var2, var3);
      return this;
   }

   public Hasher putChar(char var1) {
      this.scratch.putChar(var1);
      return this.update((int)2);
   }

   public Hasher putInt(int var1) {
      this.scratch.putInt(var1);
      return this.update((int)4);
   }

   public Hasher putLong(long var1) {
      this.scratch.putLong(var1);
      return this.update((int)8);
   }

   public Hasher putShort(short var1) {
      this.scratch.putShort(var1);
      return this.update((int)2);
   }

   protected abstract void update(byte var1);

   protected void update(ByteBuffer var1) {
      if (var1.hasArray()) {
         this.update(var1.array(), var1.arrayOffset() + var1.position(), var1.remaining());
         var1.position(var1.limit());
      } else {
         for(int var2 = var1.remaining(); var2 > 0; --var2) {
            this.update(var1.get());
         }
      }

   }

   protected void update(byte[] var1) {
      this.update(var1, 0, var1.length);
   }

   protected void update(byte[] var1, int var2, int var3) {
      for(int var4 = var2; var4 < var2 + var3; ++var4) {
         this.update(var1[var4]);
      }

   }
}
