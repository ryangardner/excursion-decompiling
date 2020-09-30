package com.google.android.gms.common.util;

public class MurmurHash3 {
   private MurmurHash3() {
   }

   public static int murmurhash3_x86_32(byte[] var0, int var1, int var2, int var3) {
      int var4;
      for(var4 = (var2 & -4) + var1; var1 < var4; var1 += 4) {
         int var5 = (var0[var1] & 255 | (var0[var1 + 1] & 255) << 8 | (var0[var1 + 2] & 255) << 16 | var0[var1 + 3] << 24) * -862048943;
         var3 ^= (var5 << 15 | var5 >>> 17) * 461845907;
         var3 = (var3 >>> 19 | var3 << 13) * 5 - 430675100;
      }

      label21: {
         var1 = 0;
         byte var7 = 0;
         int var6 = var2 & 3;
         if (var6 != 1) {
            var1 = var7;
            if (var6 != 2) {
               if (var6 != 3) {
                  break label21;
               }

               var1 = (var0[var4 + 2] & 255) << 16;
            }

            var1 |= (var0[var4 + 1] & 255) << 8;
         }

         var1 = (var0[var4] & 255 | var1) * -862048943;
         var3 ^= (var1 >>> 17 | var1 << 15) * 461845907;
      }

      var1 = var3 ^ var2;
      var1 = (var1 ^ var1 >>> 16) * -2048144789;
      var1 = (var1 ^ var1 >>> 13) * -1028477387;
      return var1 ^ var1 >>> 16;
   }
}
