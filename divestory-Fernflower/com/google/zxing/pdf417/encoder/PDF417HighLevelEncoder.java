package com.google.zxing.pdf417.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

final class PDF417HighLevelEncoder {
   private static final int BYTE_COMPACTION = 1;
   private static final Charset DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
   private static final int ECI_CHARSET = 927;
   private static final int ECI_GENERAL_PURPOSE = 926;
   private static final int ECI_USER_DEFINED = 925;
   private static final int LATCH_TO_BYTE = 924;
   private static final int LATCH_TO_BYTE_PADDED = 901;
   private static final int LATCH_TO_NUMERIC = 902;
   private static final int LATCH_TO_TEXT = 900;
   private static final byte[] MIXED = new byte[128];
   private static final int NUMERIC_COMPACTION = 2;
   private static final byte[] PUNCTUATION = new byte[128];
   private static final int SHIFT_TO_BYTE = 913;
   private static final int SUBMODE_ALPHA = 0;
   private static final int SUBMODE_LOWER = 1;
   private static final int SUBMODE_MIXED = 2;
   private static final int SUBMODE_PUNCTUATION = 3;
   private static final int TEXT_COMPACTION = 0;
   private static final byte[] TEXT_MIXED_RAW = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, 61, 94, 0, 32, 0, 0, 0};
   private static final byte[] TEXT_PUNCTUATION_RAW = new byte[]{59, 60, 62, 64, 91, 92, 93, 95, 96, 126, 33, 13, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39, 0};

   static {
      Arrays.fill(MIXED, (byte)-1);
      byte var0 = 0;
      byte var1 = 0;

      while(true) {
         byte[] var2 = TEXT_MIXED_RAW;
         if (var1 >= var2.length) {
            Arrays.fill(PUNCTUATION, (byte)-1);
            var1 = var0;

            while(true) {
               var2 = TEXT_PUNCTUATION_RAW;
               if (var1 >= var2.length) {
                  return;
               }

               byte var4 = var2[var1];
               if (var4 > 0) {
                  PUNCTUATION[var4] = (byte)var1;
               }

               ++var1;
            }
         }

         byte var3 = var2[var1];
         if (var3 > 0) {
            MIXED[var3] = (byte)var1;
         }

         ++var1;
      }
   }

   private PDF417HighLevelEncoder() {
   }

   private static int determineConsecutiveBinaryCount(String var0, int var1, Charset var2) throws WriterException {
      CharsetEncoder var9 = var2.newEncoder();
      int var3 = var0.length();

      int var4;
      for(var4 = var1; var4 < var3; ++var4) {
         char var5 = var0.charAt(var4);
         int var6 = 0;
         char var7 = var5;

         int var10;
         while(true) {
            var10 = var6;
            if (var6 >= 13) {
               break;
            }

            var10 = var6;
            if (!isDigit(var7)) {
               break;
            }

            ++var6;
            var10 = var4 + var6;
            if (var10 >= var3) {
               var10 = var6;
               break;
            }

            var5 = var0.charAt(var10);
            var7 = var5;
         }

         if (var10 >= 13) {
            return var4 - var1;
         }

         var7 = var0.charAt(var4);
         if (!var9.canEncode(var7)) {
            StringBuilder var8 = new StringBuilder();
            var8.append("Non-encodable character detected: ");
            var8.append(var7);
            var8.append(" (Unicode: ");
            var8.append(var7);
            var8.append(')');
            throw new WriterException(var8.toString());
         }
      }

      return var4 - var1;
   }

   private static int determineConsecutiveDigitCount(CharSequence var0, int var1) {
      int var2 = var0.length();
      int var3 = 0;
      byte var4 = 0;
      if (var1 < var2) {
         char var7 = var0.charAt(var1);
         int var5 = var1;
         char var6 = var7;
         var1 = var4;

         while(true) {
            var3 = var1;
            if (!isDigit(var6)) {
               break;
            }

            var3 = var1;
            if (var5 >= var2) {
               break;
            }

            var3 = var1 + 1;
            int var8 = var5 + 1;
            var1 = var3;
            var5 = var8;
            if (var8 < var2) {
               char var9 = var0.charAt(var8);
               var1 = var3;
               var6 = var9;
               var5 = var8;
            }
         }
      }

      return var3;
   }

   private static int determineConsecutiveTextCount(CharSequence var0, int var1) {
      int var2 = var0.length();
      int var3 = var1;

      int var4;
      while(true) {
         var4 = var3;
         if (var3 >= var2) {
            break;
         }

         char var5 = var0.charAt(var3);
         var4 = 0;
         char var6 = var5;

         while(var4 < 13 && isDigit(var6) && var3 < var2) {
            int var7 = var4 + 1;
            int var9 = var3 + 1;
            var3 = var9;
            var4 = var7;
            if (var9 < var2) {
               char var8 = var0.charAt(var9);
               var3 = var9;
               var6 = var8;
               var4 = var7;
            }
         }

         if (var4 >= 13) {
            return var3 - var1 - var4;
         }

         if (var4 <= 0) {
            if (!isText(var0.charAt(var3))) {
               var4 = var3;
               break;
            }

            ++var3;
         }
      }

      return var4 - var1;
   }

   private static void encodeBinary(byte[] var0, int var1, int var2, int var3, StringBuilder var4) {
      boolean var5 = true;
      if (var2 == 1 && var3 == 0) {
         var4.append('Α');
      } else {
         boolean var9;
         if (var2 % 6 == 0) {
            var9 = var5;
         } else {
            var9 = false;
         }

         if (var9) {
            var4.append('Μ');
         } else {
            var4.append('΅');
         }
      }

      int var10;
      if (var2 >= 6) {
         char[] var6 = new char[5];
         var3 = var1;

         while(true) {
            var10 = var3;
            if (var1 + var2 - var3 < 6) {
               break;
            }

            long var7 = 0L;

            for(var10 = 0; var10 < 6; ++var10) {
               var7 = (var7 << 8) + (long)(var0[var3 + var10] & 255);
            }

            for(var10 = 0; var10 < 5; ++var10) {
               var6[var10] = (char)((char)((int)(var7 % 900L)));
               var7 /= 900L;
            }

            for(var10 = 4; var10 >= 0; --var10) {
               var4.append(var6[var10]);
            }

            var3 += 6;
         }
      } else {
         var10 = var1;
      }

      while(var10 < var1 + var2) {
         var4.append((char)(var0[var10] & 255));
         ++var10;
      }

   }

   static String encodeHighLevel(String var0, Compaction var1, Charset var2) throws WriterException {
      StringBuilder var3 = new StringBuilder(var0.length());
      Charset var4;
      if (var2 == null) {
         var4 = DEFAULT_ENCODING;
      } else {
         var4 = var2;
         if (!DEFAULT_ENCODING.equals(var2)) {
            CharacterSetECI var5 = CharacterSetECI.getCharacterSetECIByName(var2.name());
            var4 = var2;
            if (var5 != null) {
               encodingECI(var5.getValue(), var3);
               var4 = var2;
            }
         }
      }

      int var6 = var0.length();
      if (var1 == Compaction.TEXT) {
         encodeText(var0, 0, var6, var3, 0);
      } else if (var1 == Compaction.BYTE) {
         byte[] var12 = var0.getBytes(var4);
         encodeBinary(var12, 0, var12.length, 1, var3);
      } else if (var1 == Compaction.NUMERIC) {
         var3.append('Ά');
         encodeNumeric(var0, 0, var6, var3);
      } else {
         int var7 = 0;
         int var8 = 0;
         byte var9 = 0;

         while(true) {
            while(var7 < var6) {
               int var10 = determineConsecutiveDigitCount(var0, var7);
               if (var10 >= 13) {
                  var3.append('Ά');
                  encodeNumeric(var0, var7, var10, var3);
                  var7 += var10;
                  var8 = 0;
                  var9 = 2;
               } else {
                  int var11 = determineConsecutiveTextCount(var0, var7);
                  if (var11 < 5 && var10 != var6) {
                     var11 = determineConsecutiveBinaryCount(var0, var7, var4);
                     var10 = var11;
                     if (var11 == 0) {
                        var10 = 1;
                     }

                     var10 += var7;
                     byte[] var13 = var0.substring(var7, var10).getBytes(var4);
                     if (var13.length == 1 && var9 == 0) {
                        encodeBinary(var13, 0, 1, 0, var3);
                     } else {
                        encodeBinary(var13, 0, var13.length, var9, var3);
                        var8 = 0;
                        var9 = 1;
                     }

                     var7 = var10;
                  } else {
                     byte var14 = var9;
                     if (var9 != 0) {
                        var3.append('΄');
                        var8 = 0;
                        var14 = 0;
                     }

                     var8 = encodeText(var0, var7, var11, var3, var8);
                     var7 += var11;
                     var9 = var14;
                  }
               }
            }

            return var3.toString();
         }
      }

      return var3.toString();
   }

   private static void encodeNumeric(String var0, int var1, int var2, StringBuilder var3) {
      StringBuilder var4 = new StringBuilder(var2 / 3 + 1);
      BigInteger var5 = BigInteger.valueOf(900L);
      BigInteger var6 = BigInteger.valueOf(0L);

      int var8;
      for(int var7 = 0; var7 < var2; var7 += var8) {
         var4.setLength(0);
         var8 = Math.min(44, var2 - var7);
         StringBuilder var9 = new StringBuilder();
         var9.append('1');
         int var10 = var1 + var7;
         var9.append(var0.substring(var10, var10 + var8));
         BigInteger var12 = new BigInteger(var9.toString());

         BigInteger var11;
         do {
            var4.append((char)var12.mod(var5).intValue());
            var11 = var12.divide(var5);
            var12 = var11;
         } while(!var11.equals(var6));

         for(var10 = var4.length() - 1; var10 >= 0; --var10) {
            var3.append(var4.charAt(var10));
         }
      }

   }

   private static int encodeText(CharSequence var0, int var1, int var2, StringBuilder var3, int var4) {
      StringBuilder var5 = new StringBuilder(var2);
      int var6 = 0;

      while(true) {
         label104:
         while(true) {
            int var7;
            char var8;
            label102:
            while(true) {
               while(true) {
                  var7 = var1 + var6;
                  var8 = var0.charAt(var7);
                  if (var4 != 0) {
                     if (var4 != 1) {
                        if (var4 != 2) {
                           if (!isPunctuation(var8)) {
                              var5.append('\u001d');
                              break label104;
                           }

                           var5.append((char)PUNCTUATION[var8]);
                           break label102;
                        }

                        if (isMixed(var8)) {
                           var5.append((char)MIXED[var8]);
                           break label102;
                        }

                        if (isAlphaUpper(var8)) {
                           var5.append('\u001c');
                           break label104;
                        }

                        if (!isAlphaLower(var8)) {
                           ++var7;
                           if (var7 < var2 && isPunctuation(var0.charAt(var7))) {
                              var4 = 3;
                              var5.append('\u0019');
                              continue;
                           }

                           var5.append('\u001d');
                           var5.append((char)PUNCTUATION[var8]);
                           break label102;
                        }

                        var5.append('\u001b');
                        break;
                     }

                     if (isAlphaLower(var8)) {
                        if (var8 == ' ') {
                           var5.append('\u001a');
                        } else {
                           var5.append((char)(var8 - 97));
                        }
                        break label102;
                     }

                     if (isAlphaUpper(var8)) {
                        var5.append('\u001b');
                        var5.append((char)(var8 - 65));
                        break label102;
                     }

                     if (!isMixed(var8)) {
                        var5.append('\u001d');
                        var5.append((char)PUNCTUATION[var8]);
                        break label102;
                     }

                     var5.append('\u001c');
                  } else {
                     if (isAlphaUpper(var8)) {
                        if (var8 == ' ') {
                           var5.append('\u001a');
                        } else {
                           var5.append((char)(var8 - 65));
                        }
                        break label102;
                     }

                     if (isAlphaLower(var8)) {
                        var5.append('\u001b');
                        break;
                     }

                     if (!isMixed(var8)) {
                        var5.append('\u001d');
                        var5.append((char)PUNCTUATION[var8]);
                        break label102;
                     }

                     var5.append('\u001c');
                  }

                  var4 = 2;
               }

               var4 = 1;
            }

            var7 = var6 + 1;
            var6 = var7;
            if (var7 >= var2) {
               var7 = var5.length();
               var1 = 0;

               char var9;
               for(var9 = 0; var1 < var7; ++var1) {
                  boolean var10;
                  if (var1 % 2 != 0) {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  if (var10) {
                     var8 = (char)(var9 * 30 + var5.charAt(var1));
                     var3.append(var8);
                     var9 = var8;
                  } else {
                     var9 = var5.charAt(var1);
                  }
               }

               if (var7 % 2 != 0) {
                  var3.append((char)(var9 * 30 + 29));
               }

               return var4;
            }
         }

         var4 = 0;
      }
   }

   private static void encodingECI(int var0, StringBuilder var1) throws WriterException {
      if (var0 >= 0 && var0 < 900) {
         var1.append('Ο');
         var1.append((char)var0);
      } else if (var0 < 810900) {
         var1.append('Ξ');
         var1.append((char)(var0 / 900 - 1));
         var1.append((char)(var0 % 900));
      } else {
         if (var0 >= 811800) {
            var1 = new StringBuilder();
            var1.append("ECI number not in valid range from 0..811799, but was ");
            var1.append(var0);
            throw new WriterException(var1.toString());
         }

         var1.append('Ν');
         var1.append((char)(810900 - var0));
      }

   }

   private static boolean isAlphaLower(char var0) {
      boolean var1;
      if (var0 == ' ' || var0 >= 'a' && var0 <= 'z') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isAlphaUpper(char var0) {
      boolean var1;
      if (var0 == ' ' || var0 >= 'A' && var0 <= 'Z') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isDigit(char var0) {
      boolean var1;
      if (var0 >= '0' && var0 <= '9') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isMixed(char var0) {
      boolean var1;
      if (MIXED[var0] != -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isPunctuation(char var0) {
      boolean var1;
      if (PUNCTUATION[var0] != -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isText(char var0) {
      boolean var1;
      if (var0 == '\t' || var0 == '\n' || var0 == '\r' || var0 >= ' ' && var0 <= '~') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
