package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLongArray;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

enum BloomFilterStrategies implements BloomFilter.Strategy {
   MURMUR128_MITZ_32 {
      public <T> boolean mightContain(T var1, Funnel<? super T> var2, int var3, BloomFilterStrategies.LockFreeBitArray var4) {
         long var5 = var4.bitSize();
         long var7 = Hashing.murmur3_128().hashObject(var1, var2).asLong();
         int var9 = (int)var7;
         int var10 = (int)(var7 >>> 32);

         for(int var11 = 1; var11 <= var3; ++var11) {
            int var12 = var11 * var10 + var9;
            int var13 = var12;
            if (var12 < 0) {
               var13 = var12;
            }

            if (!var4.get((long)var13 % var5)) {
               return false;
            }
         }

         return true;
      }

      public <T> boolean put(T var1, Funnel<? super T> var2, int var3, BloomFilterStrategies.LockFreeBitArray var4) {
         long var5 = var4.bitSize();
         long var7 = Hashing.murmur3_128().hashObject(var1, var2).asLong();
         int var9 = (int)var7;
         int var10 = (int)(var7 >>> 32);
         int var11 = 1;

         boolean var12;
         for(var12 = false; var11 <= var3; ++var11) {
            int var13 = var11 * var10 + var9;
            int var14 = var13;
            if (var13 < 0) {
               var14 = var13;
            }

            var12 |= var4.set((long)var14 % var5);
         }

         return var12;
      }
   },
   MURMUR128_MITZ_64;

   static {
      BloomFilterStrategies var0 = new BloomFilterStrategies("MURMUR128_MITZ_64", 1) {
         private long lowerEight(byte[] var1) {
            return Longs.fromBytes(var1[7], var1[6], var1[5], var1[4], var1[3], var1[2], var1[1], var1[0]);
         }

         private long upperEight(byte[] var1) {
            return Longs.fromBytes(var1[15], var1[14], var1[13], var1[12], var1[11], var1[10], var1[9], var1[8]);
         }

         public <T> boolean mightContain(T var1, Funnel<? super T> var2, int var3, BloomFilterStrategies.LockFreeBitArray var4) {
            long var5 = var4.bitSize();
            byte[] var12 = Hashing.murmur3_128().hashObject(var1, var2).getBytesInternal();
            long var7 = this.lowerEight(var12);
            long var9 = this.upperEight(var12);

            for(int var11 = 0; var11 < var3; ++var11) {
               if (!var4.get((Long.MAX_VALUE & var7) % var5)) {
                  return false;
               }

               var7 += var9;
            }

            return true;
         }

         public <T> boolean put(T var1, Funnel<? super T> var2, int var3, BloomFilterStrategies.LockFreeBitArray var4) {
            long var5 = var4.bitSize();
            byte[] var13 = Hashing.murmur3_128().hashObject(var1, var2).getBytesInternal();
            long var7 = this.lowerEight(var13);
            long var9 = this.upperEight(var13);
            int var11 = 0;

            boolean var12;
            for(var12 = false; var11 < var3; ++var11) {
               var12 |= var4.set((Long.MAX_VALUE & var7) % var5);
               var7 += var9;
            }

            return var12;
         }
      };
      MURMUR128_MITZ_64 = var0;
   }

   private BloomFilterStrategies() {
   }

   // $FF: synthetic method
   BloomFilterStrategies(Object var3) {
      this();
   }

   static final class LockFreeBitArray {
      private static final int LONG_ADDRESSABLE_BITS = 6;
      private final LongAddable bitCount;
      final AtomicLongArray data;

      LockFreeBitArray(long var1) {
         boolean var3;
         if (var1 > 0L) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3, "data length is zero!");
         this.data = new AtomicLongArray(Ints.checkedCast(LongMath.divide(var1, 64L, RoundingMode.CEILING)));
         this.bitCount = LongAddables.create();
      }

      LockFreeBitArray(long[] var1) {
         int var2 = var1.length;
         int var3 = 0;
         boolean var4;
         if (var2 > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "data length is zero!");
         this.data = new AtomicLongArray(var1);
         this.bitCount = LongAddables.create();
         long var5 = 0L;

         for(var2 = var1.length; var3 < var2; ++var3) {
            var5 += (long)Long.bitCount(var1[var3]);
         }

         this.bitCount.add(var5);
      }

      public static long[] toPlainArray(AtomicLongArray var0) {
         int var1 = var0.length();
         long[] var2 = new long[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = var0.get(var3);
         }

         return var2;
      }

      long bitCount() {
         return this.bitCount.sum();
      }

      long bitSize() {
         return (long)this.data.length() * 64L;
      }

      BloomFilterStrategies.LockFreeBitArray copy() {
         return new BloomFilterStrategies.LockFreeBitArray(toPlainArray(this.data));
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof BloomFilterStrategies.LockFreeBitArray) {
            BloomFilterStrategies.LockFreeBitArray var2 = (BloomFilterStrategies.LockFreeBitArray)var1;
            return Arrays.equals(toPlainArray(this.data), toPlainArray(var2.data));
         } else {
            return false;
         }
      }

      boolean get(long var1) {
         long var3 = this.data.get((int)(var1 >>> 6));
         boolean var5;
         if ((1L << (int)var1 & var3) != 0L) {
            var5 = true;
         } else {
            var5 = false;
         }

         return var5;
      }

      public int hashCode() {
         return Arrays.hashCode(toPlainArray(this.data));
      }

      void putAll(BloomFilterStrategies.LockFreeBitArray var1) {
         boolean var2;
         if (this.data.length() == var1.data.length()) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2, "BitArrays must be of equal length (%s != %s)", this.data.length(), var1.data.length());

         for(int var3 = 0; var3 < this.data.length(); ++var3) {
            long var4 = var1.data.get(var3);

            long var6;
            long var8;
            boolean var10;
            while(true) {
               var6 = this.data.get(var3);
               var8 = var6 | var4;
               if (var6 == var8) {
                  var10 = false;
                  break;
               }

               if (this.data.compareAndSet(var3, var6, var8)) {
                  var10 = true;
                  break;
               }
            }

            if (var10) {
               int var11 = Long.bitCount(var8);
               int var12 = Long.bitCount(var6);
               this.bitCount.add((long)(var11 - var12));
            }
         }

      }

      boolean set(long var1) {
         if (this.get(var1)) {
            return false;
         } else {
            int var3 = (int)(var1 >>> 6);
            int var4 = (int)var1;

            long var5;
            do {
               var5 = this.data.get(var3);
               var1 = var5 | 1L << var4;
               if (var5 == var1) {
                  return false;
               }
            } while(!this.data.compareAndSet(var3, var5, var1));

            this.bitCount.increment();
            return true;
         }
      }
   }
}
