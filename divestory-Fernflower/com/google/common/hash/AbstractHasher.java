package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

abstract class AbstractHasher implements Hasher {
   public final Hasher putBoolean(boolean var1) {
      return this.putByte(var1);
   }

   public Hasher putBytes(ByteBuffer var1) {
      if (var1.hasArray()) {
         this.putBytes(var1.array(), var1.arrayOffset() + var1.position(), var1.remaining());
         var1.position(var1.limit());
      } else {
         for(int var2 = var1.remaining(); var2 > 0; --var2) {
            this.putByte(var1.get());
         }
      }

      return this;
   }

   public Hasher putBytes(byte[] var1) {
      return this.putBytes(var1, 0, var1.length);
   }

   public Hasher putBytes(byte[] var1, int var2, int var3) {
      Preconditions.checkPositionIndexes(var2, var2 + var3, var1.length);

      for(int var4 = 0; var4 < var3; ++var4) {
         this.putByte(var1[var2 + var4]);
      }

      return this;
   }

   public Hasher putChar(char var1) {
      this.putByte((byte)var1);
      this.putByte((byte)(var1 >>> 8));
      return this;
   }

   public final Hasher putDouble(double var1) {
      return this.putLong(Double.doubleToRawLongBits(var1));
   }

   public final Hasher putFloat(float var1) {
      return this.putInt(Float.floatToRawIntBits(var1));
   }

   public Hasher putInt(int var1) {
      this.putByte((byte)var1);
      this.putByte((byte)(var1 >>> 8));
      this.putByte((byte)(var1 >>> 16));
      this.putByte((byte)(var1 >>> 24));
      return this;
   }

   public Hasher putLong(long var1) {
      for(int var3 = 0; var3 < 64; var3 += 8) {
         this.putByte((byte)((int)(var1 >>> var3)));
      }

      return this;
   }

   public <T> Hasher putObject(T var1, Funnel<? super T> var2) {
      var2.funnel(var1, this);
      return this;
   }

   public Hasher putShort(short var1) {
      this.putByte((byte)var1);
      this.putByte((byte)(var1 >>> 8));
      return this;
   }

   public Hasher putString(CharSequence var1, Charset var2) {
      return this.putBytes(var1.toString().getBytes(var2));
   }

   public Hasher putUnencodedChars(CharSequence var1) {
      int var2 = var1.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         this.putChar(var1.charAt(var3));
      }

      return this;
   }
}
