package com.google.common.base;

public final class Utf8 {
   private Utf8() {
   }

   public static int encodedLength(CharSequence var0) {
      int var1 = var0.length();

      int var2;
      for(var2 = 0; var2 < var1 && var0.charAt(var2) < 128; ++var2) {
      }

      int var3 = var1;

      int var4;
      while(true) {
         var4 = var3;
         if (var2 >= var1) {
            break;
         }

         char var6 = var0.charAt(var2);
         if (var6 >= 2048) {
            var4 = var3 + encodedLengthGeneral(var0, var2);
            break;
         }

         var3 += 127 - var6 >>> 31;
         ++var2;
      }

      if (var4 >= var1) {
         return var4;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("UTF-8 length does not fit in int: ");
         var5.append((long)var4 + 4294967296L);
         throw new IllegalArgumentException(var5.toString());
      }
   }

   private static int encodedLengthGeneral(CharSequence var0, int var1) {
      int var2 = var0.length();

      int var3;
      int var5;
      for(var3 = 0; var1 < var2; var1 = var5 + 1) {
         char var4 = var0.charAt(var1);
         if (var4 < 2048) {
            var3 += 127 - var4 >>> 31;
            var5 = var1;
         } else {
            int var6 = var3 + 2;
            var3 = var6;
            var5 = var1;
            if ('\ud800' <= var4) {
               var3 = var6;
               var5 = var1;
               if (var4 <= '\udfff') {
                  if (Character.codePointAt(var0, var1) == var4) {
                     throw new IllegalArgumentException(unpairedSurrogateMsg(var1));
                  }

                  var5 = var1 + 1;
                  var3 = var6;
               }
            }
         }
      }

      return var3;
   }

   public static boolean isWellFormed(byte[] var0) {
      return isWellFormed(var0, 0, var0.length);
   }

   public static boolean isWellFormed(byte[] var0, int var1, int var2) {
      var2 += var1;
      Preconditions.checkPositionIndexes(var1, var2, var0.length);

      while(var1 < var2) {
         if (var0[var1] < 0) {
            return isWellFormedSlowPath(var0, var1, var2);
         }

         ++var1;
      }

      return true;
   }

   private static boolean isWellFormedSlowPath(byte[] var0, int var1, int var2) {
      while(var1 < var2) {
         int var3 = var1 + 1;
         byte var4 = var0[var1];
         var1 = var3;
         if (var4 < 0) {
            if (var4 < -32) {
               if (var3 == var2) {
                  return false;
               }

               if (var4 >= -62) {
                  var1 = var3 + 1;
                  if (var0[var3] <= -65) {
                     continue;
                  }
               }

               return false;
            } else if (var4 < -16) {
               int var5 = var3 + 1;
               if (var5 >= var2) {
                  return false;
               }

               byte var6 = var0[var3];
               if (var6 <= -65 && (var4 != -32 || var6 >= -96) && (var4 != -19 || -96 > var6)) {
                  var1 = var5 + 1;
                  if (var0[var5] <= -65) {
                     continue;
                  }
               }

               return false;
            } else {
               if (var3 + 2 >= var2) {
                  return false;
               }

               var1 = var3 + 1;
               byte var7 = var0[var3];
               if (var7 <= -65 && (var4 << 28) + var7 + 112 >> 30 == 0) {
                  var3 = var1 + 1;
                  if (var0[var1] <= -65) {
                     var1 = var3 + 1;
                     if (var0[var3] <= -65) {
                        continue;
                     }
                  }
               }

               return false;
            }
         }
      }

      return true;
   }

   private static String unpairedSurrogateMsg(int var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("Unpaired surrogate at index ");
      var1.append(var0);
      return var1.toString();
   }
}
