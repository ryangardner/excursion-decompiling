package com.google.common.hash;

import com.google.common.base.Preconditions;

final class FarmHashFingerprint64 extends AbstractNonStreamingHashFunction {
   static final HashFunction FARMHASH_FINGERPRINT_64 = new FarmHashFingerprint64();
   private static final long K0 = -4348849565147123417L;
   private static final long K1 = -5435081209227447693L;
   private static final long K2 = -7286425919675154353L;

   static long fingerprint(byte[] var0, int var1, int var2) {
      if (var2 <= 32) {
         return var2 <= 16 ? hashLength0to16(var0, var1, var2) : hashLength17to32(var0, var1, var2);
      } else {
         return var2 <= 64 ? hashLength33To64(var0, var1, var2) : hashLength65Plus(var0, var1, var2);
      }
   }

   private static long hashLength0to16(byte[] var0, int var1, int var2) {
      long var5;
      long var7;
      if (var2 >= 8) {
         long var3 = (long)(var2 * 2) - 7286425919675154353L;
         var5 = LittleEndianByteArray.load64(var0, var1) - 7286425919675154353L;
         var7 = LittleEndianByteArray.load64(var0, var1 + var2 - 8);
         return hashLength16(Long.rotateRight(var7, 37) * var3 + var5, (Long.rotateRight(var5, 25) + var7) * var3, var3);
      } else if (var2 >= 4) {
         var5 = (long)(var2 * 2);
         var7 = (long)LittleEndianByteArray.load32(var0, var1);
         return hashLength16((long)var2 + ((var7 & 4294967295L) << 3), (long)LittleEndianByteArray.load32(var0, var1 + var2 - 4) & 4294967295L, var5 - 7286425919675154353L);
      } else if (var2 > 0) {
         byte var9 = var0[var1];
         byte var10 = var0[(var2 >> 1) + var1];
         byte var11 = var0[var1 + (var2 - 1)];
         return shiftMix((long)((var9 & 255) + ((var10 & 255) << 8)) * -7286425919675154353L ^ (long)(var2 + ((var11 & 255) << 2)) * -4348849565147123417L) * -7286425919675154353L;
      } else {
         return -7286425919675154353L;
      }
   }

   private static long hashLength16(long var0, long var2, long var4) {
      var0 = (var0 ^ var2) * var4;
      var0 = (var0 ^ var0 >>> 47 ^ var2) * var4;
      return (var0 ^ var0 >>> 47) * var4;
   }

   private static long hashLength17to32(byte[] var0, int var1, int var2) {
      long var3 = (long)(var2 * 2) - 7286425919675154353L;
      long var5 = LittleEndianByteArray.load64(var0, var1) * -5435081209227447693L;
      long var7 = LittleEndianByteArray.load64(var0, var1 + 8);
      var1 += var2;
      long var9 = LittleEndianByteArray.load64(var0, var1 - 8) * var3;
      return hashLength16(LittleEndianByteArray.load64(var0, var1 - 16) * -7286425919675154353L + Long.rotateRight(var5 + var7, 43) + Long.rotateRight(var9, 30), var5 + Long.rotateRight(var7 - 7286425919675154353L, 18) + var9, var3);
   }

   private static long hashLength33To64(byte[] var0, int var1, int var2) {
      long var3 = (long)(var2 * 2) - 7286425919675154353L;
      long var5 = LittleEndianByteArray.load64(var0, var1) * -7286425919675154353L;
      long var7 = LittleEndianByteArray.load64(var0, var1 + 8);
      var2 += var1;
      long var9 = LittleEndianByteArray.load64(var0, var2 - 8) * var3;
      long var11 = LittleEndianByteArray.load64(var0, var2 - 16);
      var11 = Long.rotateRight(var5 + var7, 43) + Long.rotateRight(var9, 30) + var11 * -7286425919675154353L;
      var7 = hashLength16(var11, var9 + Long.rotateRight(var7 - 7286425919675154353L, 18) + var5, var3);
      long var13 = LittleEndianByteArray.load64(var0, var1 + 16) * var3;
      var9 = LittleEndianByteArray.load64(var0, var1 + 24);
      var11 = (var11 + LittleEndianByteArray.load64(var0, var2 - 32)) * var3;
      return hashLength16((var7 + LittleEndianByteArray.load64(var0, var2 - 24)) * var3 + Long.rotateRight(var13 + var9, 43) + Long.rotateRight(var11, 30), var13 + Long.rotateRight(var9 + var5, 18) + var11, var3);
   }

   private static long hashLength65Plus(byte[] var0, int var1, int var2) {
      long var3 = shiftMix(-7956866745689871395L) * -7286425919675154353L;
      long[] var5 = new long[2];
      long[] var6 = new long[2];
      long var7 = 95310865018149119L + LittleEndianByteArray.load64(var0, var1);
      int var9 = var2 - 1;
      var2 = var1 + var9 / 64 * 64;
      int var10 = var9 & 63;
      var9 = var2 + var10 - 63;
      long var11 = 2480279821605975764L;

      while(true) {
         long var13 = Long.rotateRight(var7 + var11 + var5[0] + LittleEndianByteArray.load64(var0, var1 + 8), 37);
         var7 = Long.rotateRight(var11 + var5[1] + LittleEndianByteArray.load64(var0, var1 + 48), 42);
         var11 = var13 * -5435081209227447693L ^ var6[1];
         var7 = var7 * -5435081209227447693L + var5[0] + LittleEndianByteArray.load64(var0, var1 + 40);
         var13 = Long.rotateRight(var3 + var6[0], 33) * -5435081209227447693L;
         weakHashLength32WithSeeds(var0, var1, var5[1] * -5435081209227447693L, var11 + var6[0], var5);
         weakHashLength32WithSeeds(var0, var1 + 32, var13 + var6[1], var7 + LittleEndianByteArray.load64(var0, var1 + 16), var6);
         var1 += 64;
         if (var1 == var2) {
            var3 = ((var11 & 255L) << 1) - 5435081209227447693L;
            var6[0] += (long)var10;
            var5[0] += var6[0];
            var6[0] += var5[0];
            long var15 = Long.rotateRight(var13 + var7 + var5[0] + LittleEndianByteArray.load64(var0, var9 + 8), 37);
            var13 = Long.rotateRight(var7 + var5[1] + LittleEndianByteArray.load64(var0, var9 + 48), 42);
            var7 = var15 * var3 ^ var6[1] * 9L;
            var13 = var13 * var3 + var5[0] * 9L + LittleEndianByteArray.load64(var0, var9 + 40);
            var11 = Long.rotateRight(var11 + var6[0], 33) * var3;
            weakHashLength32WithSeeds(var0, var9, var5[1] * var3, var7 + var6[0], var5);
            weakHashLength32WithSeeds(var0, var9 + 32, var11 + var6[1], LittleEndianByteArray.load64(var0, var9 + 16) + var13, var6);
            return hashLength16(hashLength16(var5[0], var6[0], var3) + shiftMix(var13) * -4348849565147123417L + var7, hashLength16(var5[1], var6[1], var3) + var11, var3);
         }

         var3 = var11;
         var11 = var7;
         var7 = var13;
      }
   }

   private static long shiftMix(long var0) {
      return var0 ^ var0 >>> 47;
   }

   private static void weakHashLength32WithSeeds(byte[] var0, int var1, long var2, long var4, long[] var6) {
      long var7 = LittleEndianByteArray.load64(var0, var1);
      long var9 = LittleEndianByteArray.load64(var0, var1 + 8);
      long var11 = LittleEndianByteArray.load64(var0, var1 + 16);
      long var13 = LittleEndianByteArray.load64(var0, var1 + 24);
      var2 += var7;
      var4 = Long.rotateRight(var4 + var2 + var13, 21);
      var11 += var9 + var2;
      var9 = Long.rotateRight(var11, 44);
      var6[0] = var11 + var13;
      var6[1] = var4 + var9 + var2;
   }

   public int bits() {
      return 64;
   }

   public HashCode hashBytes(byte[] var1, int var2, int var3) {
      Preconditions.checkPositionIndexes(var2, var2 + var3, var1.length);
      return HashCode.fromLong(fingerprint(var1, var2, var3));
   }

   public String toString() {
      return "Hashing.farmHashFingerprint64()";
   }
}
