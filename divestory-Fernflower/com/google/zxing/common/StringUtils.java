package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.nio.charset.Charset;
import java.util.Map;

public final class StringUtils {
   private static final boolean ASSUME_SHIFT_JIS;
   private static final String EUC_JP = "EUC_JP";
   public static final String GB2312 = "GB2312";
   private static final String ISO88591 = "ISO8859_1";
   private static final String PLATFORM_DEFAULT_ENCODING;
   public static final String SHIFT_JIS = "SJIS";
   private static final String UTF8 = "UTF8";

   static {
      String var0 = Charset.defaultCharset().name();
      PLATFORM_DEFAULT_ENCODING = var0;
      boolean var1;
      if (!"SJIS".equalsIgnoreCase(var0) && !"EUC_JP".equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING)) {
         var1 = false;
      } else {
         var1 = true;
      }

      ASSUME_SHIFT_JIS = var1;
   }

   private StringUtils() {
   }

   public static String guessEncoding(byte[] var0, Map<DecodeHintType, ?> var1) {
      String var31;
      if (var1 != null) {
         var31 = (String)var1.get(DecodeHintType.CHARACTER_SET);
         if (var31 != null) {
            return var31;
         }
      }

      int var3 = var0.length;
      int var4 = var0.length;
      boolean var5 = true;
      int var6 = 0;
      boolean var7;
      if (var4 > 3 && var0[0] == -17 && var0[1] == -69 && var0[2] == -65) {
         var7 = true;
      } else {
         var7 = false;
      }

      int var8 = 0;
      boolean var9 = true;
      boolean var10 = true;
      int var11 = 0;
      int var12 = 0;
      int var13 = 0;
      int var14 = 0;
      int var15 = 0;
      int var16 = 0;
      int var17 = 0;
      int var18 = 0;
      int var19 = 0;

      int var29;
      for(int var20 = 0; var11 < var3 && (var5 || var9 || var10); var20 = var29) {
         int var21 = var0[var11] & 255;
         boolean var22 = var10;
         var4 = var12;
         int var23 = var14;
         int var24 = var15;
         int var25 = var16;
         int var26;
         if (var10) {
            label186: {
               if (var12 > 0) {
                  if ((var21 & 128) != 0) {
                     var4 = var12 - 1;
                     var22 = var10;
                     var23 = var14;
                     var24 = var15;
                     var25 = var16;
                     break label186;
                  }

                  var4 = var12;
               } else {
                  var22 = var10;
                  var4 = var12;
                  var23 = var14;
                  var24 = var15;
                  var25 = var16;
                  if ((var21 & 128) == 0) {
                     break label186;
                  }

                  if ((var21 & 64) == 0) {
                     var4 = var12;
                  } else {
                     var4 = var12 + 1;
                     if ((var21 & 32) == 0) {
                        var23 = var14 + 1;
                        var22 = var10;
                        var24 = var15;
                        var25 = var16;
                        break label186;
                     }

                     ++var4;
                     if ((var21 & 16) == 0) {
                        var24 = var15 + 1;
                        var22 = var10;
                        var23 = var14;
                        var25 = var16;
                        break label186;
                     }

                     var26 = var4 + 1;
                     var4 = var26;
                     if ((var21 & 8) == 0) {
                        var25 = var16 + 1;
                        var24 = var15;
                        var23 = var14;
                        var4 = var26;
                        var22 = var10;
                        break label186;
                     }
                  }
               }

               var22 = false;
               var23 = var14;
               var24 = var15;
               var25 = var16;
            }
         }

         boolean var34 = var5;
         int var27 = var18;
         if (var5) {
            if (var21 > 127 && var21 < 160) {
               var34 = false;
               var27 = var18;
            } else {
               var34 = var5;
               var27 = var18;
               if (var21 > 159) {
                  label166: {
                     if (var21 >= 192 && var21 != 215) {
                        var34 = var5;
                        var27 = var18;
                        if (var21 != 247) {
                           break label166;
                        }
                     }

                     var27 = var18 + 1;
                     var34 = var5;
                  }
               }
            }
         }

         var16 = var8;
         var14 = var6;
         boolean var35 = var9;
         var18 = var13;
         int var28 = var17;
         var26 = var19;
         var29 = var20;
         if (var9) {
            label159: {
               if (var13 > 0) {
                  if (var21 >= 64 && var21 != 127 && var21 <= 252) {
                     var18 = var13 - 1;
                     var16 = var8;
                     var14 = var6;
                     var35 = var9;
                     var28 = var17;
                     var26 = var19;
                     var29 = var20;
                     break label159;
                  }
               } else if (var21 != 128 && var21 != 160 && var21 <= 239) {
                  if (var21 > 160 && var21 < 224) {
                     var16 = var8 + 1;
                     var29 = var20 + 1;
                     if (var29 > var17) {
                        var17 = var29;
                     }

                     var26 = 0;
                     var14 = var6;
                     var35 = var9;
                     var18 = var13;
                     var28 = var17;
                  } else {
                     int var33;
                     if (var21 > 127) {
                        ++var13;
                        var33 = var19 + 1;
                        if (var33 > var6) {
                           var6 = var33;
                           var33 = var33;
                        }
                     } else {
                        var33 = 0;
                     }

                     var29 = 0;
                     var16 = var8;
                     var14 = var6;
                     var35 = var9;
                     var18 = var13;
                     var28 = var17;
                     var26 = var33;
                  }
                  break label159;
               }

               var35 = false;
               var29 = var20;
               var26 = var19;
               var28 = var17;
               var18 = var13;
               var14 = var6;
               var16 = var8;
            }
         }

         ++var11;
         var8 = var16;
         var5 = var34;
         var6 = var14;
         var9 = var35;
         var10 = var22;
         var12 = var4;
         var13 = var18;
         var14 = var23;
         var15 = var24;
         var16 = var25;
         var17 = var28;
         var18 = var27;
         var19 = var26;
      }

      boolean var32 = var10;
      if (var10) {
         var32 = var10;
         if (var12 > 0) {
            var32 = false;
         }
      }

      var10 = var9;
      if (var9) {
         var10 = var9;
         if (var13 > 0) {
            var10 = false;
         }
      }

      if (var32 && (var7 || var14 + var15 + var16 > 0)) {
         return "UTF8";
      } else {
         var31 = "SJIS";
         if (var10 && (ASSUME_SHIFT_JIS || var17 >= 3 || var6 >= 3)) {
            return "SJIS";
         } else if (var5 && var10) {
            String var30;
            if (var17 == 2) {
               var30 = var31;
               if (var8 == 2) {
                  return var30;
               }
            }

            if (var18 * 10 >= var3) {
               var30 = var31;
            } else {
               var30 = "ISO8859_1";
            }

            return var30;
         } else if (var5) {
            return "ISO8859_1";
         } else if (var10) {
            return "SJIS";
         } else {
            return var32 ? "UTF8" : PLATFORM_DEFAULT_ENCODING;
         }
      }
   }
}
