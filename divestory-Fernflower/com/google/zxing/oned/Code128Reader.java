package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.ArrayList;
import java.util.Map;

public final class Code128Reader extends OneDReader {
   private static final int CODE_CODE_A = 101;
   private static final int CODE_CODE_B = 100;
   private static final int CODE_CODE_C = 99;
   private static final int CODE_FNC_1 = 102;
   private static final int CODE_FNC_2 = 97;
   private static final int CODE_FNC_3 = 96;
   private static final int CODE_FNC_4_A = 101;
   private static final int CODE_FNC_4_B = 100;
   static final int[][] CODE_PATTERNS;
   private static final int CODE_SHIFT = 98;
   private static final int CODE_START_A = 103;
   private static final int CODE_START_B = 104;
   private static final int CODE_START_C = 105;
   private static final int CODE_STOP = 106;
   private static final float MAX_AVG_VARIANCE = 0.25F;
   private static final float MAX_INDIVIDUAL_VARIANCE = 0.7F;

   static {
      int[] var0 = new int[]{2, 2, 1, 2, 1, 3};
      int[] var1 = new int[]{2, 2, 1, 3, 1, 2};
      int[] var2 = new int[]{2, 3, 1, 2, 1, 2};
      int[] var3 = new int[]{1, 2, 2, 2, 3, 1};
      int[] var4 = new int[]{1, 1, 3, 2, 2, 2};
      int[] var5 = new int[]{1, 2, 3, 2, 2, 1};
      int[] var6 = new int[]{2, 2, 3, 2, 1, 1};
      int[] var7 = new int[]{2, 2, 1, 1, 3, 2};
      int[] var8 = new int[]{3, 1, 2, 2, 1, 2};
      int[] var9 = new int[]{3, 2, 2, 1, 1, 2};
      int[] var10 = new int[]{2, 1, 2, 3, 2, 1};
      int[] var11 = new int[]{2, 3, 2, 1, 2, 1};
      int[] var12 = new int[]{1, 1, 1, 3, 2, 3};
      int[] var13 = new int[]{1, 1, 2, 3, 1, 3};
      int[] var14 = new int[]{1, 3, 2, 1, 1, 3};
      int[] var15 = new int[]{1, 3, 2, 3, 1, 1};
      int[] var16 = new int[]{2, 1, 1, 3, 1, 3};
      int[] var17 = new int[]{2, 3, 1, 1, 1, 3};
      int[] var18 = new int[]{2, 3, 1, 3, 1, 1};
      int[] var19 = new int[]{1, 1, 2, 1, 3, 3};
      int[] var20 = new int[]{1, 3, 2, 1, 3, 1};
      int[] var21 = new int[]{3, 1, 3, 1, 2, 1};
      int[] var22 = new int[]{2, 1, 3, 1, 1, 3};
      int[] var23 = new int[]{2, 1, 3, 3, 1, 1};
      int[] var24 = new int[]{2, 1, 3, 1, 3, 1};
      int[] var25 = new int[]{3, 1, 1, 3, 2, 1};
      int[] var26 = new int[]{3, 1, 2, 1, 1, 3};
      int[] var27 = new int[]{3, 3, 2, 1, 1, 1};
      int[] var28 = new int[]{3, 1, 4, 1, 1, 1};
      int[] var29 = new int[]{2, 2, 1, 4, 1, 1};
      int[] var30 = new int[]{1, 1, 1, 2, 2, 4};
      int[] var31 = new int[]{1, 1, 1, 4, 2, 2};
      int[] var32 = new int[]{1, 4, 1, 2, 2, 1};
      int[] var33 = new int[]{1, 2, 2, 1, 1, 4};
      int[] var34 = new int[]{1, 2, 2, 4, 1, 1};
      int[] var35 = new int[]{1, 4, 2, 2, 1, 1};
      int[] var36 = new int[]{2, 4, 1, 2, 1, 1};
      int[] var37 = new int[]{4, 1, 3, 1, 1, 1};
      int[] var38 = new int[]{1, 3, 4, 1, 1, 1};
      int[] var39 = new int[]{1, 2, 1, 1, 4, 2};
      int[] var40 = new int[]{1, 1, 4, 2, 1, 2};
      int[] var41 = new int[]{4, 2, 1, 1, 1, 2};
      int[] var42 = new int[]{2, 1, 4, 1, 2, 1};
      int[] var43 = new int[]{1, 1, 1, 3, 4, 1};
      int[] var44 = new int[]{1, 1, 4, 3, 1, 1};
      int[] var45 = new int[]{4, 1, 1, 1, 1, 3};
      int[] var46 = new int[]{4, 1, 1, 3, 1, 1};
      int[] var47 = new int[]{1, 1, 3, 1, 4, 1};
      int[] var48 = new int[]{4, 1, 1, 1, 3, 1};
      int[] var49 = new int[]{2, 1, 1, 2, 3, 2};
      CODE_PATTERNS = new int[][]{{2, 1, 2, 2, 2, 2}, {2, 2, 2, 1, 2, 2}, {2, 2, 2, 2, 2, 1}, {1, 2, 1, 2, 2, 3}, {1, 2, 1, 3, 2, 2}, {1, 3, 1, 2, 2, 2}, {1, 2, 2, 2, 1, 3}, {1, 2, 2, 3, 1, 2}, {1, 3, 2, 2, 1, 2}, var0, var1, var2, {1, 1, 2, 2, 3, 2}, {1, 2, 2, 1, 3, 2}, var3, var4, {1, 2, 3, 1, 2, 2}, var5, var6, var7, {2, 2, 1, 2, 3, 1}, {2, 1, 3, 2, 1, 2}, {2, 2, 3, 1, 1, 2}, {3, 1, 2, 1, 3, 1}, {3, 1, 1, 2, 2, 2}, {3, 2, 1, 1, 2, 2}, {3, 2, 1, 2, 2, 1}, var8, var9, {3, 2, 2, 2, 1, 1}, {2, 1, 2, 1, 2, 3}, var10, var11, var12, {1, 3, 1, 1, 2, 3}, {1, 3, 1, 3, 2, 1}, var13, var14, var15, var16, var17, var18, var19, {1, 1, 2, 3, 3, 1}, var20, {1, 1, 3, 1, 2, 3}, {1, 1, 3, 3, 2, 1}, {1, 3, 3, 1, 2, 1}, var21, {2, 1, 1, 3, 3, 1}, {2, 3, 1, 1, 3, 1}, var22, var23, var24, {3, 1, 1, 1, 2, 3}, var25, {3, 3, 1, 1, 2, 1}, var26, {3, 1, 2, 3, 1, 1}, var27, var28, var29, {4, 3, 1, 1, 1, 1}, var30, var31, {1, 2, 1, 1, 2, 4}, {1, 2, 1, 4, 2, 1}, {1, 4, 1, 1, 2, 2}, var32, {1, 1, 2, 2, 1, 4}, {1, 1, 2, 4, 1, 2}, var33, var34, {1, 4, 2, 1, 1, 2}, var35, var36, {2, 2, 1, 1, 1, 4}, var37, {2, 4, 1, 1, 1, 2}, var38, {1, 1, 1, 2, 4, 2}, var39, {1, 2, 1, 2, 4, 1}, var40, {1, 2, 4, 1, 1, 2}, {1, 2, 4, 2, 1, 1}, {4, 1, 1, 2, 1, 2}, var41, {4, 2, 1, 2, 1, 1}, {2, 1, 2, 1, 4, 1}, var42, {4, 1, 2, 1, 2, 1}, {1, 1, 1, 1, 4, 3}, var43, {1, 3, 1, 1, 4, 1}, {1, 1, 4, 1, 1, 3}, var44, var45, var46, var47, {1, 1, 4, 1, 3, 1}, {3, 1, 1, 1, 4, 1}, var48, {2, 1, 1, 4, 1, 2}, {2, 1, 1, 2, 1, 4}, var49, {2, 3, 3, 1, 1, 1, 2}};
   }

   private static int decodeCode(BitArray var0, int[] var1, int var2) throws NotFoundException {
      recordPattern(var0, var2, var1);
      float var3 = 0.25F;
      int var4 = -1;
      var2 = 0;

      while(true) {
         int[][] var7 = CODE_PATTERNS;
         if (var2 >= var7.length) {
            if (var4 >= 0) {
               return var4;
            }

            throw NotFoundException.getNotFoundInstance();
         }

         float var5 = patternMatchVariance(var1, var7[var2], 0.7F);
         float var6 = var3;
         if (var5 < var3) {
            var4 = var2;
            var6 = var5;
         }

         ++var2;
         var3 = var6;
      }
   }

   private static int[] findStartPattern(BitArray var0) throws NotFoundException {
      int var1 = var0.getSize();
      int var2 = var0.getNextSet(0);
      int[] var3 = new int[6];
      int var4 = var2;
      boolean var5 = false;

      int var7;
      for(int var6 = 0; var2 < var1; var6 = var7) {
         if (var0.get(var2) ^ var5) {
            int var10002 = var3[var6]++;
            var7 = var6;
         } else {
            if (var6 == 5) {
               float var8 = 0.25F;
               var7 = 103;

               int var9;
               float var11;
               for(var9 = -1; var7 <= 105; var8 = var11) {
                  float var10 = patternMatchVariance(var3, CODE_PATTERNS[var7], 0.7F);
                  var11 = var8;
                  if (var10 < var8) {
                     var9 = var7;
                     var11 = var10;
                  }

                  ++var7;
               }

               if (var9 >= 0 && var0.isRange(Math.max(0, var4 - (var2 - var4) / 2), var4, false)) {
                  return new int[]{var4, var2, var9};
               }

               var4 += var3[0] + var3[1];
               System.arraycopy(var3, 2, var3, 0, 4);
               var3[4] = 0;
               var3[5] = 0;
               var7 = var6 - 1;
            } else {
               var7 = var6 + 1;
            }

            var3[var7] = 1;
            var5 ^= true;
         }

         ++var2;
      }

      throw NotFoundException.getNotFoundInstance();
   }

   public Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException, FormatException, ChecksumException {
      boolean var4 = false;
      boolean var5;
      if (var3 != null && var3.containsKey(DecodeHintType.ASSUME_GS1)) {
         var5 = true;
      } else {
         var5 = false;
      }

      int[] var6 = findStartPattern(var2);
      int var7 = var6[2];
      ArrayList var8 = new ArrayList(20);
      var8.add((byte)var7);
      byte var9;
      switch(var7) {
      case 103:
         var9 = 101;
         break;
      case 104:
         var9 = 100;
         break;
      case 105:
         var9 = 99;
         break;
      default:
         throw FormatException.getFormatInstance();
      }

      StringBuilder var29 = new StringBuilder(20);
      int var10 = var6[0];
      int var11 = var6[1];
      int[] var12 = new int[6];
      boolean var13 = false;
      boolean var14 = false;
      boolean var15 = true;
      boolean var16 = false;
      int var17 = 0;
      int var18 = 0;
      int var19 = 0;
      byte var20 = var9;

      int var23;
      for(boolean var34 = var4; !var14; var19 = var23) {
         int var21 = decodeCode(var2, var12, var11);
         var8.add((byte)var21);
         if (var21 != 106) {
            var15 = true;
         }

         int var22 = var7;
         int var31 = var18;
         if (var21 != 106) {
            var31 = var18 + 1;
            var22 = var7 + var31 * var21;
         }

         var7 = var11;

         for(var18 = 0; var18 < 6; ++var18) {
            var7 += var12[var18];
         }

         switch(var21) {
         case 103:
         case 104:
         case 105:
            throw FormatException.getFormatInstance();
         default:
            boolean var39;
            label226: {
               label225: {
                  boolean var40;
                  label224: {
                     label223: {
                        byte var37;
                        boolean var42;
                        label249: {
                           label220: {
                              boolean var35;
                              label219: {
                                 label218: {
                                    label217: {
                                       label216: {
                                          label213:
                                          switch(var20) {
                                          case 99:
                                             if (var21 < 100) {
                                                if (var21 < 10) {
                                                   var29.append('0');
                                                }

                                                var29.append(var21);
                                                var39 = var15;
                                             } else {
                                                if (var21 != 106) {
                                                   var15 = false;
                                                }

                                                if (var21 == 106) {
                                                   var39 = false;
                                                   var14 = true;
                                                   break label226;
                                                }

                                                switch(var21) {
                                                case 100:
                                                   var39 = false;
                                                   var20 = 100;
                                                   break label226;
                                                case 101:
                                                   var39 = false;
                                                   var20 = 101;
                                                   break label226;
                                                case 102:
                                                   var39 = var15;
                                                   if (var5) {
                                                      if (var29.length() == 0) {
                                                         var29.append("]C1");
                                                         var39 = var15;
                                                      } else {
                                                         var29.append('\u001d');
                                                         var39 = var15;
                                                      }
                                                   }
                                                   break label223;
                                                default:
                                                   var39 = var15;
                                                }
                                             }
                                             break label223;
                                          case 100:
                                             if (var21 < 96) {
                                                if (var13 == var34) {
                                                   var29.append((char)(var21 + 32));
                                                } else {
                                                   var29.append((char)(var21 + 32 + 128));
                                                }
                                                break label225;
                                             }

                                             if (var21 != 106) {
                                                var15 = false;
                                             }

                                             var39 = var15;
                                             if (var21 == 106) {
                                                break label216;
                                             }

                                             var35 = var15;
                                             switch(var21) {
                                             case 98:
                                                var39 = true;
                                                break label217;
                                             case 99:
                                                break label218;
                                             case 100:
                                                if (!var34 && var13) {
                                                   var39 = true;
                                                   break label220;
                                                }

                                                var35 = var15;
                                                if (var34) {
                                                   var35 = var15;
                                                   if (var13) {
                                                      var39 = false;
                                                      break label220;
                                                   }
                                                }
                                                break label219;
                                             case 101:
                                                var39 = false;
                                                break label217;
                                             case 102:
                                                var35 = var15;
                                                if (var5) {
                                                   if (var29.length() == 0) {
                                                      var29.append("]C1");
                                                      var35 = var15;
                                                   } else {
                                                      var29.append('\u001d');
                                                      var35 = var15;
                                                   }
                                                }
                                                break label213;
                                             default:
                                                var35 = var15;
                                                break label213;
                                             }
                                          case 101:
                                             if (var21 < 64) {
                                                if (var13 == var34) {
                                                   var29.append((char)(var21 + 32));
                                                } else {
                                                   var29.append((char)(var21 + 32 + 128));
                                                }
                                                break label225;
                                             }

                                             if (var21 < 96) {
                                                if (var13 == var34) {
                                                   var29.append((char)(var21 - 64));
                                                } else {
                                                   var29.append((char)(var21 + 64));
                                                }
                                                break label225;
                                             }

                                             if (var21 != 106) {
                                                var15 = false;
                                             }

                                             var39 = var15;
                                             if (var21 == 106) {
                                                break label216;
                                             }

                                             var35 = var15;
                                             switch(var21) {
                                             case 98:
                                                var39 = true;
                                                break label249;
                                             case 99:
                                                break label218;
                                             case 100:
                                                var39 = false;
                                                break label249;
                                             case 101:
                                                if (!var34 && var13) {
                                                   var39 = true;
                                                   break label220;
                                                }

                                                var35 = var15;
                                                if (var34) {
                                                   var35 = var15;
                                                   if (var13) {
                                                      var39 = false;
                                                      break label220;
                                                   }
                                                }
                                                break label219;
                                             case 102:
                                                var35 = var15;
                                                if (var5) {
                                                   if (var29.length() == 0) {
                                                      var29.append("]C1");
                                                      var35 = var15;
                                                   } else {
                                                      var29.append('\u001d');
                                                      var35 = var15;
                                                   }
                                                }
                                                break label213;
                                             default:
                                                var35 = var15;
                                                break label213;
                                             }
                                          default:
                                             var39 = var15;
                                             break label223;
                                          }

                                          var39 = var34;
                                          var34 = false;
                                          var40 = var13;
                                          var15 = var35;
                                          break label224;
                                       }

                                       var35 = false;
                                       var14 = true;
                                       var15 = var39;
                                       var40 = var13;
                                       var39 = var34;
                                       var34 = var35;
                                       break label224;
                                    }

                                    var42 = var34;
                                    var37 = 101;
                                    var34 = var39;
                                    var39 = var42;
                                    var40 = var13;
                                    var20 = var37;
                                    break label224;
                                 }

                                 var39 = var34;
                                 var34 = false;
                                 var20 = 99;
                                 var40 = var13;
                                 var15 = var35;
                                 break label224;
                              }

                              var39 = var34;
                              var34 = false;
                              var40 = true;
                              var15 = var35;
                              break label224;
                           }

                           var34 = false;
                           var40 = false;
                           break label224;
                        }

                        var42 = var34;
                        var37 = 100;
                        var34 = var39;
                        var39 = var42;
                        var40 = var13;
                        var20 = var37;
                        break label224;
                     }

                     var40 = false;
                     var15 = var39;
                     var39 = var40;
                     break label226;
                  }

                  var13 = var34;
                  var34 = var39;
                  var39 = var13;
                  var13 = var40;
                  break label226;
               }

               var39 = false;
               var13 = false;
            }

            byte var43 = var20;
            if (var16) {
               if (var20 == 101) {
                  var43 = 100;
               } else {
                  var43 = 101;
               }
            }

            var10 = var11;
            var11 = var7;
            var23 = var17;
            var17 = var21;
            var7 = var22;
            var20 = var43;
            var16 = var39;
            var18 = var31;
         }
      }

      int var38 = var2.getNextUnset(var11);
      if (!var2.isRange(var38, Math.min(var2.getSize(), (var38 - var10) / 2 + var38), false)) {
         throw NotFoundException.getNotFoundInstance();
      } else if ((var7 - var18 * var19) % 103 != var19) {
         throw ChecksumException.getChecksumInstance();
      } else {
         var38 = var29.length();
         if (var38 == 0) {
            throw NotFoundException.getNotFoundInstance();
         } else {
            if (var38 > 0 && var15) {
               if (var20 == 99) {
                  var29.delete(var38 - 2, var38);
               } else {
                  var29.delete(var38 - 1, var38);
               }
            }

            float var24 = (float)(var6[1] + var6[0]) / 2.0F;
            float var25 = (float)var10;
            float var26 = (float)(var11 - var10) / 2.0F;
            int var41 = var8.size();
            byte[] var28 = new byte[var41];

            for(var38 = 0; var38 < var41; ++var38) {
               var28[var38] = (Byte)var8.get(var38);
            }

            String var33 = var29.toString();
            float var27 = (float)var1;
            ResultPoint var30 = new ResultPoint(var24, var27);
            ResultPoint var36 = new ResultPoint(var25 + var26, var27);
            BarcodeFormat var32 = BarcodeFormat.CODE_128;
            return new Result(var33, var28, new ResultPoint[]{var30, var36}, var32);
         }
      }
   }
}
