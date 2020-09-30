package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedInts;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class HashCode {
   private static final char[] hexDigits = "0123456789abcdef".toCharArray();

   HashCode() {
   }

   private static int decode(char var0) {
      if (var0 >= '0' && var0 <= '9') {
         return var0 - 48;
      } else if (var0 >= 'a' && var0 <= 'f') {
         return var0 - 97 + 10;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("Illegal hexadecimal character: ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      }
   }

   public static HashCode fromBytes(byte[] var0) {
      int var1 = var0.length;
      boolean var2 = true;
      if (var1 < 1) {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "A HashCode must contain at least 1 byte.");
      return fromBytesNoCopy((byte[])var0.clone());
   }

   static HashCode fromBytesNoCopy(byte[] var0) {
      return new HashCode.BytesHashCode(var0);
   }

   public static HashCode fromInt(int var0) {
      return new HashCode.IntHashCode(var0);
   }

   public static HashCode fromLong(long var0) {
      return new HashCode.LongHashCode(var0);
   }

   public static HashCode fromString(String var0) {
      int var1 = var0.length();
      boolean var2 = true;
      int var3 = 0;
      boolean var4;
      if (var1 >= 2) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "input string (%s) must have at least 2 characters", (Object)var0);
      if (var0.length() % 2 == 0) {
         var4 = var2;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "input string (%s) must have an even number of characters", (Object)var0);

      byte[] var5;
      for(var5 = new byte[var0.length() / 2]; var3 < var0.length(); var3 += 2) {
         int var6 = decode(var0.charAt(var3));
         var1 = decode(var0.charAt(var3 + 1));
         var5[var3 / 2] = (byte)((byte)((var6 << 4) + var1));
      }

      return fromBytesNoCopy(var5);
   }

   public abstract byte[] asBytes();

   public abstract int asInt();

   public abstract long asLong();

   public abstract int bits();

   public final boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof HashCode;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         HashCode var5 = (HashCode)var1;
         var4 = var3;
         if (this.bits() == var5.bits()) {
            var4 = var3;
            if (this.equalsSameBits(var5)) {
               var4 = true;
            }
         }
      }

      return var4;
   }

   abstract boolean equalsSameBits(HashCode var1);

   byte[] getBytesInternal() {
      return this.asBytes();
   }

   public final int hashCode() {
      if (this.bits() >= 32) {
         return this.asInt();
      } else {
         byte[] var1 = this.getBytesInternal();
         int var2 = var1[0] & 255;

         for(int var3 = 1; var3 < var1.length; ++var3) {
            var2 |= (var1[var3] & 255) << var3 * 8;
         }

         return var2;
      }
   }

   public abstract long padToLong();

   public final String toString() {
      byte[] var1 = this.getBytesInternal();
      StringBuilder var2 = new StringBuilder(var1.length * 2);
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte var5 = var1[var4];
         var2.append(hexDigits[var5 >> 4 & 15]);
         var2.append(hexDigits[var5 & 15]);
      }

      return var2.toString();
   }

   public int writeBytesTo(byte[] var1, int var2, int var3) {
      var3 = Ints.min(var3, this.bits() / 8);
      Preconditions.checkPositionIndexes(var2, var2 + var3, var1.length);
      this.writeBytesToImpl(var1, var2, var3);
      return var3;
   }

   abstract void writeBytesToImpl(byte[] var1, int var2, int var3);

   private static final class BytesHashCode extends HashCode implements Serializable {
      private static final long serialVersionUID = 0L;
      final byte[] bytes;

      BytesHashCode(byte[] var1) {
         this.bytes = (byte[])Preconditions.checkNotNull(var1);
      }

      public byte[] asBytes() {
         return (byte[])this.bytes.clone();
      }

      public int asInt() {
         boolean var1;
         if (this.bytes.length >= 4) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkState(var1, "HashCode#asInt() requires >= 4 bytes (it only has %s bytes).", this.bytes.length);
         byte[] var2 = this.bytes;
         byte var3 = var2[0];
         byte var4 = var2[1];
         byte var5 = var2[2];
         return (var2[3] & 255) << 24 | (var4 & 255) << 8 | var3 & 255 | (var5 & 255) << 16;
      }

      public long asLong() {
         boolean var1;
         if (this.bytes.length >= 8) {
            var1 = true;
         } else {
            var1 = false;
         }

         Preconditions.checkState(var1, "HashCode#asLong() requires >= 8 bytes (it only has %s bytes).", this.bytes.length);
         return this.padToLong();
      }

      public int bits() {
         return this.bytes.length * 8;
      }

      boolean equalsSameBits(HashCode var1) {
         if (this.bytes.length != var1.getBytesInternal().length) {
            return false;
         } else {
            int var2 = 0;
            boolean var3 = true;

            while(true) {
               byte[] var4 = this.bytes;
               if (var2 >= var4.length) {
                  return var3;
               }

               boolean var5;
               if (var4[var2] == var1.getBytesInternal()[var2]) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               var3 &= var5;
               ++var2;
            }
         }
      }

      byte[] getBytesInternal() {
         return this.bytes;
      }

      public long padToLong() {
         long var1 = (long)(this.bytes[0] & 255);

         for(int var3 = 1; var3 < Math.min(this.bytes.length, 8); ++var3) {
            var1 |= ((long)this.bytes[var3] & 255L) << var3 * 8;
         }

         return var1;
      }

      void writeBytesToImpl(byte[] var1, int var2, int var3) {
         System.arraycopy(this.bytes, 0, var1, var2, var3);
      }
   }

   private static final class IntHashCode extends HashCode implements Serializable {
      private static final long serialVersionUID = 0L;
      final int hash;

      IntHashCode(int var1) {
         this.hash = var1;
      }

      public byte[] asBytes() {
         int var1 = this.hash;
         return new byte[]{(byte)var1, (byte)(var1 >> 8), (byte)(var1 >> 16), (byte)(var1 >> 24)};
      }

      public int asInt() {
         return this.hash;
      }

      public long asLong() {
         throw new IllegalStateException("this HashCode only has 32 bits; cannot create a long");
      }

      public int bits() {
         return 32;
      }

      boolean equalsSameBits(HashCode var1) {
         boolean var2;
         if (this.hash == var1.asInt()) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public long padToLong() {
         return UnsignedInts.toLong(this.hash);
      }

      void writeBytesToImpl(byte[] var1, int var2, int var3) {
         for(int var4 = 0; var4 < var3; ++var4) {
            var1[var2 + var4] = (byte)((byte)(this.hash >> var4 * 8));
         }

      }
   }

   private static final class LongHashCode extends HashCode implements Serializable {
      private static final long serialVersionUID = 0L;
      final long hash;

      LongHashCode(long var1) {
         this.hash = var1;
      }

      public byte[] asBytes() {
         long var1 = this.hash;
         return new byte[]{(byte)((int)var1), (byte)((int)(var1 >> 8)), (byte)((int)(var1 >> 16)), (byte)((int)(var1 >> 24)), (byte)((int)(var1 >> 32)), (byte)((int)(var1 >> 40)), (byte)((int)(var1 >> 48)), (byte)((int)(var1 >> 56))};
      }

      public int asInt() {
         return (int)this.hash;
      }

      public long asLong() {
         return this.hash;
      }

      public int bits() {
         return 64;
      }

      boolean equalsSameBits(HashCode var1) {
         boolean var2;
         if (this.hash == var1.asLong()) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public long padToLong() {
         return this.hash;
      }

      void writeBytesToImpl(byte[] var1, int var2, int var3) {
         for(int var4 = 0; var4 < var3; ++var4) {
            var1[var2 + var4] = (byte)((byte)((int)(this.hash >> var4 * 8)));
         }

      }
   }
}
