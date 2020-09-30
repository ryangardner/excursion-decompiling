package com.google.zxing.qrcode.encoder;

final class MaskUtil {
   private static final int N1 = 3;
   private static final int N2 = 3;
   private static final int N3 = 40;
   private static final int N4 = 10;

   private MaskUtil() {
   }

   static int applyMaskPenaltyRule1(ByteMatrix var0) {
      return applyMaskPenaltyRule1Internal(var0, true) + applyMaskPenaltyRule1Internal(var0, false);
   }

   private static int applyMaskPenaltyRule1Internal(ByteMatrix var0, boolean var1) {
      int var2;
      if (var1) {
         var2 = var0.getHeight();
      } else {
         var2 = var0.getWidth();
      }

      int var3;
      if (var1) {
         var3 = var0.getWidth();
      } else {
         var3 = var0.getHeight();
      }

      byte[][] var10 = var0.getArray();
      int var4 = 0;

      int var5;
      int var13;
      for(var5 = 0; var4 < var2; var5 = var13) {
         byte var6 = -1;
         int var7 = 0;

         int var8;
         for(var8 = 0; var7 < var3; var8 = var13) {
            byte var9;
            if (var1) {
               var9 = var10[var4][var7];
            } else {
               var9 = var10[var7][var4];
            }

            byte var12;
            if (var9 == var6) {
               var13 = var8 + 1;
               var12 = var6;
            } else {
               int var11 = var5;
               if (var8 >= 5) {
                  var11 = var5 + var8 - 5 + 3;
               }

               var12 = var9;
               var13 = 1;
               var5 = var11;
            }

            ++var7;
            var6 = var12;
         }

         var13 = var5;
         if (var8 >= 5) {
            var13 = var5 + var8 - 5 + 3;
         }

         ++var4;
      }

      return var5;
   }

   static int applyMaskPenaltyRule2(ByteMatrix var0) {
      byte[][] var1 = var0.getArray();
      int var2 = var0.getWidth();
      int var3 = var0.getHeight();
      int var4 = 0;

      int var5;
      int var9;
      for(var5 = 0; var4 < var3 - 1; ++var4) {
         for(int var6 = 0; var6 < var2 - 1; var5 = var9) {
            byte var7 = var1[var4][var6];
            byte[] var11 = var1[var4];
            int var8 = var6 + 1;
            var9 = var5;
            if (var7 == var11[var8]) {
               int var10 = var4 + 1;
               var9 = var5;
               if (var7 == var1[var10][var6]) {
                  var9 = var5;
                  if (var7 == var1[var10][var8]) {
                     var9 = var5 + 1;
                  }
               }
            }

            var6 = var8;
         }
      }

      return var5 * 3;
   }

   static int applyMaskPenaltyRule3(ByteMatrix var0) {
      byte[][] var1 = var0.getArray();
      int var2 = var0.getWidth();
      int var3 = var0.getHeight();
      int var4 = 0;

      int var5;
      for(var5 = 0; var4 < var3; ++var4) {
         for(int var6 = 0; var6 < var2; ++var6) {
            byte[] var9 = var1[var4];
            int var7 = var6 + 6;
            int var8 = var5;
            if (var7 < var2) {
               var8 = var5;
               if (var9[var6] == 1) {
                  var8 = var5;
                  if (var9[var6 + 1] == 0) {
                     var8 = var5;
                     if (var9[var6 + 2] == 1) {
                        var8 = var5;
                        if (var9[var6 + 3] == 1) {
                           var8 = var5;
                           if (var9[var6 + 4] == 1) {
                              var8 = var5;
                              if (var9[var6 + 5] == 0) {
                                 var8 = var5;
                                 if (var9[var7] == 1) {
                                    label96: {
                                       if (!isWhiteHorizontal(var9, var6 - 4, var6)) {
                                          var8 = var5;
                                          if (!isWhiteHorizontal(var9, var6 + 7, var6 + 11)) {
                                             break label96;
                                          }
                                       }

                                       var8 = var5 + 1;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            var7 = var4 + 6;
            var5 = var8;
            if (var7 < var3) {
               var5 = var8;
               if (var1[var4][var6] == 1) {
                  var5 = var8;
                  if (var1[var4 + 1][var6] == 0) {
                     var5 = var8;
                     if (var1[var4 + 2][var6] == 1) {
                        var5 = var8;
                        if (var1[var4 + 3][var6] == 1) {
                           var5 = var8;
                           if (var1[var4 + 4][var6] == 1) {
                              var5 = var8;
                              if (var1[var4 + 5][var6] == 0) {
                                 var5 = var8;
                                 if (var1[var7][var6] == 1) {
                                    if (!isWhiteVertical(var1, var6, var4 - 4, var4)) {
                                       var5 = var8;
                                       if (!isWhiteVertical(var1, var6, var4 + 7, var4 + 11)) {
                                          continue;
                                       }
                                    }

                                    var5 = var8 + 1;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var5 * 40;
   }

   static int applyMaskPenaltyRule4(ByteMatrix var0) {
      byte[][] var1 = var0.getArray();
      int var2 = var0.getWidth();
      int var3 = var0.getHeight();
      int var4 = 0;

      int var5;
      for(var5 = 0; var4 < var3; ++var4) {
         byte[] var6 = var1[var4];

         int var8;
         for(int var7 = 0; var7 < var2; var5 = var8) {
            var8 = var5;
            if (var6[var7] == 1) {
               var8 = var5 + 1;
            }

            ++var7;
         }
      }

      var4 = var0.getHeight() * var0.getWidth();
      return Math.abs(var5 * 2 - var4) * 10 / var4 * 10;
   }

   static boolean getDataMaskBit(int var0, int var1, int var2) {
      boolean var3;
      label26: {
         var3 = true;
         int var4 = var1;
         int var5 = var2;
         int var6 = var2;
         switch(var0) {
         case 2:
            var0 = var1 % 3;
            break label26;
         case 3:
            var0 = (var2 + var1) % 3;
            break label26;
         case 4:
            var5 = var2 / 2;
            var4 = var1 / 3;
         case 0:
            var6 = var5 + var4;
         case 1:
            var0 = var6 & 1;
            break label26;
         case 5:
            var0 = var2 * var1;
            var0 = (var0 & 1) + var0 % 3;
            break label26;
         case 6:
            var0 = var2 * var1;
            var0 = (var0 & 1) + var0 % 3;
            break;
         case 7:
            var0 = var2 * var1 % 3 + (var2 + var1 & 1);
            break;
         default:
            StringBuilder var7 = new StringBuilder();
            var7.append("Invalid mask pattern: ");
            var7.append(var0);
            throw new IllegalArgumentException(var7.toString());
         }

         var0 &= 1;
      }

      if (var0 != 0) {
         var3 = false;
      }

      return var3;
   }

   private static boolean isWhiteHorizontal(byte[] var0, int var1, int var2) {
      while(var1 < var2) {
         if (var1 >= 0 && var1 < var0.length && var0[var1] == 1) {
            return false;
         }

         ++var1;
      }

      return true;
   }

   private static boolean isWhiteVertical(byte[][] var0, int var1, int var2, int var3) {
      while(var2 < var3) {
         if (var2 >= 0 && var2 < var0.length && var0[var2][var1] == 1) {
            return false;
         }

         ++var2;
      }

      return true;
   }
}
