package com.google.android.gms.common.util;

public final class HexDumpUtils {
   public static String dump(byte[] var0, int var1, int var2, boolean var3) {
      if (var0 != null && var0.length != 0 && var1 >= 0 && var2 > 0 && var1 + var2 <= var0.length) {
         byte var4 = 57;
         if (var3) {
            var4 = 75;
         }

         StringBuilder var5 = new StringBuilder(var4 * ((var2 + 16 - 1) / 16));
         int var6 = var2;
         int var11 = 0;

         int var8;
         for(int var7 = 0; var6 > 0; var7 = var8) {
            if (var11 == 0) {
               if (var2 < 65536) {
                  var5.append(String.format("%04X:", var1));
               } else {
                  var5.append(String.format("%08X:", var1));
               }

               var8 = var1;
            } else {
               var8 = var7;
               if (var11 == 8) {
                  var5.append(" -");
                  var8 = var7;
               }
            }

            var5.append(String.format(" %02X", var0[var1] & 255));
            --var6;
            var7 = var11 + 1;
            if (var3 && (var7 == 16 || var6 == 0)) {
               int var9 = 16 - var7;
               if (var9 > 0) {
                  for(var11 = 0; var11 < var9; ++var11) {
                     var5.append("   ");
                  }
               }

               if (var9 >= 8) {
                  var5.append("  ");
               }

               var5.append("  ");

               for(var11 = 0; var11 < var7; ++var11) {
                  char var12 = (char)var0[var8 + var11];
                  char var10;
                  if (var12 >= ' ' && var12 <= '~') {
                     var10 = var12;
                  } else {
                     byte var13 = 46;
                     var10 = (char)var13;
                  }

                  var5.append(var10);
               }
            }

            label79: {
               if (var7 != 16) {
                  var11 = var7;
                  if (var6 != 0) {
                     break label79;
                  }
               }

               var5.append('\n');
               var11 = 0;
            }

            ++var1;
         }

         return var5.toString();
      } else {
         return null;
      }
   }
}
