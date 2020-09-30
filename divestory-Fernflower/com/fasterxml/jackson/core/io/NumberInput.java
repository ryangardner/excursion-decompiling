package com.fasterxml.jackson.core.io;

import java.math.BigDecimal;

public final class NumberInput {
   static final long L_BILLION = 1000000000L;
   static final String MAX_LONG_STR = String.valueOf(Long.MAX_VALUE);
   static final String MIN_LONG_STR_NO_SIGN = String.valueOf(Long.MIN_VALUE).substring(1);
   public static final String NASTY_SMALL_DOUBLE = "2.2250738585072012e-308";

   private static NumberFormatException _badBD(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("Value \"");
      var1.append(var0);
      var1.append("\" can not be represented as BigDecimal");
      return new NumberFormatException(var1.toString());
   }

   public static boolean inLongRange(String var0, boolean var1) {
      String var2;
      if (var1) {
         var2 = MIN_LONG_STR_NO_SIGN;
      } else {
         var2 = MAX_LONG_STR;
      }

      int var3 = var2.length();
      int var4 = var0.length();
      var1 = true;
      if (var4 < var3) {
         return true;
      } else if (var4 > var3) {
         return false;
      } else {
         for(var4 = 0; var4 < var3; ++var4) {
            int var5 = var0.charAt(var4) - var2.charAt(var4);
            if (var5 != 0) {
               if (var5 >= 0) {
                  var1 = false;
               }

               return var1;
            }
         }

         return true;
      }
   }

   public static boolean inLongRange(char[] var0, int var1, int var2, boolean var3) {
      String var4;
      if (var3) {
         var4 = MIN_LONG_STR_NO_SIGN;
      } else {
         var4 = MAX_LONG_STR;
      }

      int var5 = var4.length();
      var3 = true;
      if (var2 < var5) {
         return true;
      } else if (var2 > var5) {
         return false;
      } else {
         for(var2 = 0; var2 < var5; ++var2) {
            int var6 = var0[var1 + var2] - var4.charAt(var2);
            if (var6 != 0) {
               if (var6 >= 0) {
                  var3 = false;
               }

               return var3;
            }
         }

         return true;
      }
   }

   public static double parseAsDouble(String var0, double var1) {
      if (var0 == null) {
         return var1;
      } else {
         var0 = var0.trim();
         if (var0.length() == 0) {
            return var1;
         } else {
            try {
               double var3 = parseDouble(var0);
               return var3;
            } catch (NumberFormatException var5) {
               return var1;
            }
         }
      }
   }

   public static int parseAsInt(String var0, int var1) {
      if (var0 == null) {
         return var1;
      } else {
         String var2 = var0.trim();
         int var3 = var2.length();
         if (var3 == 0) {
            return var1;
         } else {
            byte var4 = 0;
            int var5 = var3;
            int var6 = var4;
            var0 = var2;
            if (var3 > 0) {
               char var7 = var2.charAt(0);
               if (var7 == '+') {
                  var0 = var2.substring(1);
                  var5 = var0.length();
                  var6 = var4;
               } else {
                  var5 = var3;
                  var6 = var4;
                  var0 = var2;
                  if (var7 == '-') {
                     var6 = 1;
                     var0 = var2;
                     var5 = var3;
                  }
               }
            }

            while(var6 < var5) {
               char var12 = var0.charAt(var6);
               if (var12 > '9' || var12 < '0') {
                  double var8;
                  try {
                     var8 = parseDouble(var0);
                  } catch (NumberFormatException var10) {
                     return var1;
                  }

                  return (int)var8;
               }

               ++var6;
            }

            try {
               var6 = Integer.parseInt(var0);
               return var6;
            } catch (NumberFormatException var11) {
               return var1;
            }
         }
      }
   }

   public static long parseAsLong(String var0, long var1) {
      if (var0 == null) {
         return var1;
      } else {
         String var3 = var0.trim();
         int var4 = var3.length();
         if (var4 == 0) {
            return var1;
         } else {
            byte var5 = 0;
            int var6 = var4;
            int var7 = var5;
            var0 = var3;
            if (var4 > 0) {
               char var8 = var3.charAt(0);
               if (var8 == '+') {
                  var0 = var3.substring(1);
                  var6 = var0.length();
                  var7 = var5;
               } else {
                  var6 = var4;
                  var7 = var5;
                  var0 = var3;
                  if (var8 == '-') {
                     var7 = 1;
                     var0 = var3;
                     var6 = var4;
                  }
               }
            }

            while(var7 < var6) {
               char var15 = var0.charAt(var7);
               if (var15 > '9' || var15 < '0') {
                  double var9;
                  try {
                     var9 = parseDouble(var0);
                  } catch (NumberFormatException var13) {
                     return var1;
                  }

                  return (long)var9;
               }

               ++var7;
            }

            try {
               long var11 = Long.parseLong(var0);
               return var11;
            } catch (NumberFormatException var14) {
               return var1;
            }
         }
      }
   }

   public static BigDecimal parseBigDecimal(String var0) throws NumberFormatException {
      try {
         BigDecimal var1 = new BigDecimal(var0);
         return var1;
      } catch (NumberFormatException var2) {
         throw _badBD(var0);
      }
   }

   public static BigDecimal parseBigDecimal(char[] var0) throws NumberFormatException {
      return parseBigDecimal(var0, 0, var0.length);
   }

   public static BigDecimal parseBigDecimal(char[] var0, int var1, int var2) throws NumberFormatException {
      try {
         BigDecimal var3 = new BigDecimal(var0, var1, var2);
         return var3;
      } catch (NumberFormatException var4) {
         throw _badBD(new String(var0, var1, var2));
      }
   }

   public static double parseDouble(String var0) throws NumberFormatException {
      return "2.2250738585072012e-308".equals(var0) ? Double.MIN_VALUE : Double.parseDouble(var0);
   }

   public static int parseInt(String var0) {
      boolean var1 = false;
      char var2 = var0.charAt(0);
      int var3 = var0.length();
      byte var4 = 1;
      if (var2 == '-') {
         var1 = true;
      }

      if (var1) {
         if (var3 == 1 || var3 > 10) {
            return Integer.parseInt(var0);
         }

         var2 = var0.charAt(1);
         var4 = 2;
      } else if (var3 > 9) {
         return Integer.parseInt(var0);
      }

      if (var2 <= '9' && var2 >= '0') {
         int var5 = var2 - 48;
         int var7 = var5;
         int var8;
         if (var4 < var3) {
            int var6 = var4 + 1;
            var2 = var0.charAt(var4);
            if (var2 > '9' || var2 < '0') {
               return Integer.parseInt(var0);
            }

            var5 = var5 * 10 + (var2 - 48);
            var7 = var5;
            if (var6 < var3) {
               label93: {
                  var8 = var6 + 1;
                  var2 = var0.charAt(var6);
                  if (var2 <= '9' && var2 >= '0') {
                     var5 = var5 * 10 + (var2 - 48);
                     var7 = var5;
                     if (var8 >= var3) {
                        break label93;
                     }

                     var7 = var5;

                     while(true) {
                        var5 = var8 + 1;
                        char var9 = var0.charAt(var8);
                        if (var9 > '9' || var9 < '0') {
                           return Integer.parseInt(var0);
                        }

                        var7 = var7 * 10 + (var9 - 48);
                        if (var5 >= var3) {
                           break label93;
                        }

                        var8 = var5;
                     }
                  }

                  return Integer.parseInt(var0);
               }
            }
         }

         var8 = var7;
         if (var1) {
            var8 = -var7;
         }

         return var8;
      } else {
         return Integer.parseInt(var0);
      }
   }

   public static int parseInt(char[] var0, int var1, int var2) {
      int var3 = var0[var1 + var2 - 1] - 48;
      int var4 = var3;
      int var5 = var1;
      int var6 = var3;
      int var7 = var1;
      int var8 = var3;
      int var9 = var1;
      int var10 = var3;
      int var11 = var1;
      int var12 = var3;
      int var13 = var1;
      int var14 = var3;
      int var15 = var1;
      int var16 = var3;
      int var17 = var1;
      switch(var2) {
      case 9:
         var4 = var3 + (var0[var1] - 48) * 100000000;
         var5 = var1 + 1;
      case 8:
         var6 = var4 + (var0[var5] - 48) * 10000000;
         var7 = var5 + 1;
      case 7:
         var8 = var6 + (var0[var7] - 48) * 1000000;
         var9 = var7 + 1;
      case 6:
         var10 = var8 + (var0[var9] - 48) * 100000;
         var11 = var9 + 1;
      case 5:
         var12 = var10 + (var0[var11] - 48) * 10000;
         var13 = var11 + 1;
      case 4:
         var14 = var12 + (var0[var13] - 48) * 1000;
         var15 = var13 + 1;
      case 3:
         var16 = var14 + (var0[var15] - 48) * 100;
         var17 = var15 + 1;
      case 2:
         var3 = var16 + (var0[var17] - 48) * 10;
      default:
         return var3;
      }
   }

   public static long parseLong(String var0) {
      return var0.length() <= 9 ? (long)parseInt(var0) : Long.parseLong(var0);
   }

   public static long parseLong(char[] var0, int var1, int var2) {
      var2 -= 9;
      return (long)parseInt(var0, var1, var2) * 1000000000L + (long)parseInt(var0, var1 + var2, 9);
   }
}
