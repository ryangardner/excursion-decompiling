package com.google.common.hash;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
final class Murmur3_32HashFunction extends AbstractHashFunction implements Serializable {
   private static final int C1 = -862048943;
   private static final int C2 = 461845907;
   private static final int CHUNK_SIZE = 4;
   static final HashFunction GOOD_FAST_HASH_32;
   static final HashFunction MURMUR3_32 = new Murmur3_32HashFunction(0);
   private static final long serialVersionUID = 0L;
   private final int seed;

   static {
      GOOD_FAST_HASH_32 = new Murmur3_32HashFunction(Hashing.GOOD_FAST_HASH_SEED);
   }

   Murmur3_32HashFunction(int var1) {
      this.seed = var1;
   }

   private static long charToThreeUtf8Bytes(char var0) {
      return (long)((var0 & 63 | 128) << 16 | (var0 >>> 12 | 480) & 255 | (var0 >>> 6 & 63 | 128) << 8);
   }

   private static long charToTwoUtf8Bytes(char var0) {
      return (long)((var0 & 63 | 128) << 8 | (var0 >>> 6 | 960) & 255);
   }

   private static long codePointToFourUtf8Bytes(int var0) {
      return ((long)(var0 >>> 18) | 240L) & 255L | ((long)(var0 >>> 12 & 63) | 128L) << 8 | ((long)(var0 >>> 6 & 63) | 128L) << 16 | ((long)(var0 & 63) | 128L) << 24;
   }

   private static HashCode fmix(int var0, int var1) {
      var0 ^= var1;
      var0 = (var0 ^ var0 >>> 16) * -2048144789;
      var0 = (var0 ^ var0 >>> 13) * -1028477387;
      return HashCode.fromInt(var0 ^ var0 >>> 16);
   }

   private static int getIntLittleEndian(byte[] var0, int var1) {
      return Ints.fromBytes(var0[var1 + 3], var0[var1 + 2], var0[var1 + 1], var0[var1]);
   }

   private static int mixH1(int var0, int var1) {
      return Integer.rotateLeft(var0 ^ var1, 13) * 5 - 430675100;
   }

   private static int mixK1(int var0) {
      return Integer.rotateLeft(var0 * -862048943, 15) * 461845907;
   }

   public int bits() {
      return 32;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof Murmur3_32HashFunction;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Murmur3_32HashFunction var5 = (Murmur3_32HashFunction)var1;
         var4 = var3;
         if (this.seed == var5.seed) {
            var4 = true;
         }
      }

      return var4;
   }

   public HashCode hashBytes(byte[] var1, int var2, int var3) {
      Preconditions.checkPositionIndexes(var2, var2 + var3, var1.length);
      int var4 = this.seed;
      int var5 = 0;
      int var6 = 0;

      while(true) {
         int var7 = var6 + 4;
         if (var7 > var3) {
            byte var8 = 0;
            var7 = var6;

            for(var6 = var8; var7 < var3; var6 += 8) {
               var5 ^= UnsignedBytes.toInt(var1[var2 + var7]) << var6;
               ++var7;
            }

            return fmix(mixK1(var5) ^ var4, var3);
         }

         var4 = mixH1(var4, mixK1(getIntLittleEndian(var1, var6 + var2)));
         var6 = var7;
      }
   }

   public int hashCode() {
      return this.getClass().hashCode() ^ this.seed;
   }

   public HashCode hashInt(int var1) {
      var1 = mixK1(var1);
      return fmix(mixH1(this.seed, var1), 4);
   }

   public HashCode hashLong(long var1) {
      int var3 = (int)var1;
      int var4 = (int)(var1 >>> 32);
      var3 = mixK1(var3);
      return fmix(mixH1(mixH1(this.seed, var3), mixK1(var4)), 8);
   }

   public HashCode hashString(CharSequence var1, Charset var2) {
      if (!Charsets.UTF_8.equals(var2)) {
         return this.hashBytes(var1.toString().getBytes(var2));
      } else {
         int var3 = var1.length();
         int var4 = this.seed;
         int var5 = 0;
         int var6 = 0;
         int var7 = 0;

         int var8;
         while(true) {
            var8 = var6 + 4;
            if (var8 > var3) {
               break;
            }

            char var9 = var1.charAt(var6);
            char var10 = var1.charAt(var6 + 1);
            char var11 = var1.charAt(var6 + 2);
            char var12 = var1.charAt(var6 + 3);
            if (var9 >= 128 || var10 >= 128 || var11 >= 128 || var12 >= 128) {
               break;
            }

            var4 = mixH1(var4, mixK1(var10 << 8 | var9 | var11 << 16 | var12 << 24));
            var7 += 4;
            var6 = var8;
         }

         long var13 = 0L;

         int var18;
         for(var8 = var4; var6 < var3; var8 = var18) {
            char var15 = var1.charAt(var6);
            long var16;
            if (var15 < 128) {
               var16 = var13 | (long)var15 << var5;
               var4 = var5 + 8;
               ++var7;
            } else if (var15 < 2048) {
               var16 = var13 | charToTwoUtf8Bytes(var15) << var5;
               var4 = var5 + 16;
               var7 += 2;
            } else if (var15 >= '\ud800' && var15 <= '\udfff') {
               var4 = Character.codePointAt(var1, var6);
               if (var4 == var15) {
                  return this.hashBytes(var1.toString().getBytes(var2));
               }

               ++var6;
               var16 = var13 | codePointToFourUtf8Bytes(var4) << var5;
               var7 += 4;
               var4 = var5;
            } else {
               var16 = var13 | charToThreeUtf8Bytes(var15) << var5;
               var4 = var5 + 24;
               var7 += 3;
            }

            var18 = var8;
            var5 = var4;
            var13 = var16;
            if (var4 >= 32) {
               var18 = mixH1(var8, mixK1((int)var16));
               var13 = var16 >>> 32;
               var5 = var4 - 32;
            }

            ++var6;
         }

         return fmix(mixK1((int)var13) ^ var8, var7);
      }
   }

   public HashCode hashUnencodedChars(CharSequence var1) {
      int var2 = this.seed;

      int var3;
      for(var3 = 1; var3 < var1.length(); var3 += 2) {
         var2 = mixH1(var2, mixK1(var1.charAt(var3 - 1) | var1.charAt(var3) << 16));
      }

      var3 = var2;
      if ((var1.length() & 1) == 1) {
         var3 = var2 ^ mixK1(var1.charAt(var1.length() - 1));
      }

      return fmix(var3, var1.length() * 2);
   }

   public Hasher newHasher() {
      return new Murmur3_32HashFunction.Murmur3_32Hasher(this.seed);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Hashing.murmur3_32(");
      var1.append(this.seed);
      var1.append(")");
      return var1.toString();
   }

   private static final class Murmur3_32Hasher extends AbstractHasher {
      private long buffer;
      private int h1;
      private boolean isDone;
      private int length;
      private int shift;

      Murmur3_32Hasher(int var1) {
         this.h1 = var1;
         this.length = 0;
         this.isDone = false;
      }

      private void update(int var1, long var2) {
         long var4 = this.buffer;
         int var6 = this.shift;
         var2 = (var2 & 4294967295L) << var6 | var4;
         this.buffer = var2;
         var6 += var1 * 8;
         this.shift = var6;
         this.length += var1;
         if (var6 >= 32) {
            this.h1 = Murmur3_32HashFunction.mixH1(this.h1, Murmur3_32HashFunction.mixK1((int)var2));
            this.buffer >>>= 32;
            this.shift -= 32;
         }

      }

      public HashCode hash() {
         Preconditions.checkState(this.isDone ^ true);
         this.isDone = true;
         int var1 = this.h1 ^ Murmur3_32HashFunction.mixK1((int)this.buffer);
         this.h1 = var1;
         return Murmur3_32HashFunction.fmix(var1, this.length);
      }

      public Hasher putByte(byte var1) {
         this.update(1, (long)(var1 & 255));
         return this;
      }

      public Hasher putBytes(ByteBuffer var1) {
         ByteOrder var2 = var1.order();
         var1.order(ByteOrder.LITTLE_ENDIAN);

         while(var1.remaining() >= 4) {
            this.putInt(var1.getInt());
         }

         while(var1.hasRemaining()) {
            this.putByte(var1.get());
         }

         var1.order(var2);
         return this;
      }

      public Hasher putBytes(byte[] var1, int var2, int var3) {
         Preconditions.checkPositionIndexes(var2, var2 + var3, var1.length);
         int var4 = 0;

         while(true) {
            int var5 = var4 + 4;
            int var6 = var4;
            if (var5 > var3) {
               while(var6 < var3) {
                  this.putByte(var1[var2 + var6]);
                  ++var6;
               }

               return this;
            }

            this.update(4, (long)Murmur3_32HashFunction.getIntLittleEndian(var1, var4 + var2));
            var4 = var5;
         }
      }

      public Hasher putChar(char var1) {
         this.update(2, (long)var1);
         return this;
      }

      public Hasher putInt(int var1) {
         this.update(4, (long)var1);
         return this;
      }

      public Hasher putLong(long var1) {
         this.update(4, (long)((int)var1));
         this.update(4, var1 >>> 32);
         return this;
      }

      public Hasher putString(CharSequence var1, Charset var2) {
         if (!Charsets.UTF_8.equals(var2)) {
            return super.putString(var1, var2);
         } else {
            int var3 = var1.length();
            int var4 = 0;

            int var6;
            while(true) {
               int var5 = var4 + 4;
               var6 = var4;
               if (var5 > var3) {
                  break;
               }

               char var7 = var1.charAt(var4);
               char var8 = var1.charAt(var4 + 1);
               char var9 = var1.charAt(var4 + 2);
               char var10 = var1.charAt(var4 + 3);
               var6 = var4;
               if (var7 >= 128) {
                  break;
               }

               var6 = var4;
               if (var8 >= 128) {
                  break;
               }

               var6 = var4;
               if (var9 >= 128) {
                  break;
               }

               var6 = var4;
               if (var10 >= 128) {
                  break;
               }

               this.update(4, (long)(var8 << 8 | var7 | var9 << 16 | var10 << 24));
               var4 = var5;
            }

            for(; var6 < var3; ++var6) {
               char var11 = var1.charAt(var6);
               if (var11 < 128) {
                  this.update(1, (long)var11);
               } else if (var11 < 2048) {
                  this.update(2, Murmur3_32HashFunction.charToTwoUtf8Bytes(var11));
               } else if (var11 >= '\ud800' && var11 <= '\udfff') {
                  var4 = Character.codePointAt(var1, var6);
                  if (var4 == var11) {
                     this.putBytes((byte[])var1.subSequence(var6, var3).toString().getBytes(var2));
                     return this;
                  }

                  ++var6;
                  this.update(4, Murmur3_32HashFunction.codePointToFourUtf8Bytes(var4));
               } else {
                  this.update(3, Murmur3_32HashFunction.charToThreeUtf8Bytes(var11));
               }
            }

            return this;
         }
      }
   }
}
