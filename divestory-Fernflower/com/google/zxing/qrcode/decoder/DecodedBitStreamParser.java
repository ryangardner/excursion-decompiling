package com.google.zxing.qrcode.decoder;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

final class DecodedBitStreamParser {
   private static final char[] ALPHANUMERIC_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '$', '%', '*', '+', '-', '.', '/', ':'};
   private static final int GB2312_SUBSET = 1;

   private DecodedBitStreamParser() {
   }

   static DecoderResult decode(byte[] var0, Version var1, ErrorCorrectionLevel var2, Map<DecodeHintType, ?> var3) throws FormatException {
      BitSource var4 = new BitSource(var0);
      StringBuilder var5 = new StringBuilder(50);
      ArrayList var6 = new ArrayList(1);
      CharacterSetECI var7 = null;
      boolean var8 = false;
      int var9 = -1;
      int var10 = -1;

      while(true) {
         Mode var11;
         boolean var10001;
         label183: {
            try {
               if (var4.available() < 4) {
                  var11 = Mode.TERMINATOR;
                  break label183;
               }
            } catch (IllegalArgumentException var31) {
               var10001 = false;
               break;
            }

            try {
               var11 = Mode.forBits(var4.readBits(4));
            } catch (IllegalArgumentException var30) {
               var10001 = false;
               break;
            }
         }

         int var12 = var9;
         int var13 = var10;
         CharacterSetECI var14 = var7;

         label190: {
            label172: {
               label191: {
                  label170: {
                     try {
                        if (var11 == Mode.TERMINATOR) {
                           break label191;
                        }

                        if (var11 != Mode.FNC1_FIRST_POSITION && var11 != Mode.FNC1_SECOND_POSITION) {
                           break label170;
                        }
                     } catch (IllegalArgumentException var29) {
                        var10001 = false;
                        break;
                     }

                     var8 = true;
                     break label172;
                  }

                  label161: {
                     label160: {
                        try {
                           if (var11 != Mode.STRUCTURED_APPEND) {
                              break label161;
                           }

                           if (var4.available() < 16) {
                              break label160;
                           }

                           var12 = var4.readBits(8);
                           var13 = var4.readBits(8);
                        } catch (IllegalArgumentException var28) {
                           var10001 = false;
                           break;
                        }

                        var14 = var7;
                        break label191;
                     }

                     try {
                        throw FormatException.getFormatInstance();
                     } catch (IllegalArgumentException var17) {
                        var10001 = false;
                        break;
                     }
                  }

                  label194: {
                     try {
                        if (var11 != Mode.ECI) {
                           break label194;
                        }

                        var14 = CharacterSetECI.getCharacterSetECIByValue(parseECIValue(var4));
                     } catch (IllegalArgumentException var27) {
                        var10001 = false;
                        break;
                     }

                     if (var14 == null) {
                        try {
                           throw FormatException.getFormatInstance();
                        } catch (IllegalArgumentException var18) {
                           var10001 = false;
                           break;
                        }
                     }

                     var12 = var9;
                     var13 = var10;
                     break label191;
                  }

                  label143: {
                     int var15;
                     int var16;
                     try {
                        if (var11 != Mode.HANZI) {
                           break label143;
                        }

                        var15 = var4.readBits(4);
                        var16 = var4.readBits(var11.getCharacterCountBits(var1));
                     } catch (IllegalArgumentException var26) {
                        var10001 = false;
                        break;
                     }

                     var12 = var9;
                     var13 = var10;
                     var14 = var7;
                     if (var15 == 1) {
                        try {
                           decodeHanziSegment(var4, var5, var16);
                        } catch (IllegalArgumentException var21) {
                           var10001 = false;
                           break;
                        }

                        var12 = var9;
                        var13 = var10;
                        var14 = var7;
                     }
                     break label191;
                  }

                  label136: {
                     try {
                        var13 = var4.readBits(var11.getCharacterCountBits(var1));
                        if (var11 != Mode.NUMERIC) {
                           break label136;
                        }

                        decodeNumericSegment(var4, var5, var13);
                     } catch (IllegalArgumentException var25) {
                        var10001 = false;
                        break;
                     }

                     var12 = var9;
                     var13 = var10;
                     var14 = var7;
                     break label191;
                  }

                  label197: {
                     try {
                        if (var11 == Mode.ALPHANUMERIC) {
                           decodeAlphanumericSegment(var4, var5, var13, var8);
                           break label197;
                        }
                     } catch (IllegalArgumentException var24) {
                        var10001 = false;
                        break;
                     }

                     try {
                        if (var11 == Mode.BYTE) {
                           decodeByteSegment(var4, var5, var13, var7, var6, var3);
                           break label172;
                        }
                     } catch (IllegalArgumentException var23) {
                        var10001 = false;
                        break;
                     }

                     try {
                        if (var11 != Mode.KANJI) {
                           break label190;
                        }

                        decodeKanjiSegment(var4, var5, var13);
                        break label172;
                     } catch (IllegalArgumentException var22) {
                        var10001 = false;
                        break;
                     }
                  }

                  var12 = var9;
                  var13 = var10;
                  var14 = var7;
               }

               var9 = var12;
               var10 = var13;
               var7 = var14;
            }

            Mode var35;
            try {
               var35 = Mode.TERMINATOR;
            } catch (IllegalArgumentException var20) {
               var10001 = false;
               break;
            }

            if (var11 == var35) {
               String var34 = var5.toString();
               ArrayList var32;
               if (var6.isEmpty()) {
                  var32 = null;
               } else {
                  var32 = var6;
               }

               String var33;
               if (var2 == null) {
                  var33 = null;
               } else {
                  var33 = var2.toString();
               }

               return new DecoderResult(var0, var34, var32, var33, var9, var10);
            }
            continue;
         }

         try {
            throw FormatException.getFormatInstance();
         } catch (IllegalArgumentException var19) {
            var10001 = false;
            break;
         }
      }

      throw FormatException.getFormatInstance();
   }

   private static void decodeAlphanumericSegment(BitSource var0, StringBuilder var1, int var2, boolean var3) throws FormatException {
      int var4;
      for(var4 = var1.length(); var2 > 1; var2 -= 2) {
         if (var0.available() < 11) {
            throw FormatException.getFormatInstance();
         }

         int var5 = var0.readBits(11);
         var1.append(toAlphaNumericChar(var5 / 45));
         var1.append(toAlphaNumericChar(var5 % 45));
      }

      if (var2 == 1) {
         if (var0.available() < 6) {
            throw FormatException.getFormatInstance();
         }

         var1.append(toAlphaNumericChar(var0.readBits(6)));
      }

      if (var3) {
         for(var2 = var4; var2 < var1.length(); ++var2) {
            if (var1.charAt(var2) == '%') {
               if (var2 < var1.length() - 1) {
                  var4 = var2 + 1;
                  if (var1.charAt(var4) == '%') {
                     var1.deleteCharAt(var4);
                     continue;
                  }
               }

               var1.setCharAt(var2, '\u001d');
            }
         }
      }

   }

   private static void decodeByteSegment(BitSource var0, StringBuilder var1, int var2, CharacterSetECI var3, Collection<byte[]> var4, Map<DecodeHintType, ?> var5) throws FormatException {
      if (var2 * 8 > var0.available()) {
         throw FormatException.getFormatInstance();
      } else {
         byte[] var6 = new byte[var2];

         for(int var7 = 0; var7 < var2; ++var7) {
            var6[var7] = (byte)((byte)var0.readBits(8));
         }

         String var9;
         if (var3 == null) {
            var9 = StringUtils.guessEncoding(var6, var5);
         } else {
            var9 = var3.name();
         }

         try {
            String var10 = new String(var6, var9);
            var1.append(var10);
         } catch (UnsupportedEncodingException var8) {
            throw FormatException.getFormatInstance();
         }

         var4.add(var6);
      }
   }

   private static void decodeHanziSegment(BitSource var0, StringBuilder var1, int var2) throws FormatException {
      if (var2 * 13 <= var0.available()) {
         byte[] var3 = new byte[var2 * 2];

         for(int var4 = 0; var2 > 0; --var2) {
            int var5 = var0.readBits(13);
            int var6 = var5 % 96 | var5 / 96 << 8;
            char var9;
            if (var6 < 959) {
               var9 = 'ꆡ';
            } else {
               var9 = 'ꚡ';
            }

            var5 = var6 + var9;
            var3[var4] = (byte)((byte)(var5 >> 8 & 255));
            var3[var4 + 1] = (byte)((byte)(var5 & 255));
            var4 += 2;
         }

         try {
            String var8 = new String(var3, "GB2312");
            var1.append(var8);
         } catch (UnsupportedEncodingException var7) {
            throw FormatException.getFormatInstance();
         }
      } else {
         throw FormatException.getFormatInstance();
      }
   }

   private static void decodeKanjiSegment(BitSource var0, StringBuilder var1, int var2) throws FormatException {
      if (var2 * 13 <= var0.available()) {
         byte[] var3 = new byte[var2 * 2];

         for(int var4 = 0; var2 > 0; --var2) {
            int var5 = var0.readBits(13);
            int var6 = var5 % 192 | var5 / 192 << 8;
            char var9;
            if (var6 < 7936) {
               var9 = '腀';
            } else {
               var9 = '셀';
            }

            var5 = var6 + var9;
            var3[var4] = (byte)((byte)(var5 >> 8));
            var3[var4 + 1] = (byte)((byte)var5);
            var4 += 2;
         }

         try {
            String var8 = new String(var3, "SJIS");
            var1.append(var8);
         } catch (UnsupportedEncodingException var7) {
            throw FormatException.getFormatInstance();
         }
      } else {
         throw FormatException.getFormatInstance();
      }
   }

   private static void decodeNumericSegment(BitSource var0, StringBuilder var1, int var2) throws FormatException {
      while(true) {
         if (var2 >= 3) {
            if (var0.available() >= 10) {
               int var3 = var0.readBits(10);
               if (var3 < 1000) {
                  var1.append(toAlphaNumericChar(var3 / 100));
                  var1.append(toAlphaNumericChar(var3 / 10 % 10));
                  var1.append(toAlphaNumericChar(var3 % 10));
                  var2 -= 3;
                  continue;
               }

               throw FormatException.getFormatInstance();
            }

            throw FormatException.getFormatInstance();
         }

         if (var2 == 2) {
            if (var0.available() < 7) {
               throw FormatException.getFormatInstance();
            }

            var2 = var0.readBits(7);
            if (var2 >= 100) {
               throw FormatException.getFormatInstance();
            }

            var1.append(toAlphaNumericChar(var2 / 10));
            var1.append(toAlphaNumericChar(var2 % 10));
         } else if (var2 == 1) {
            if (var0.available() < 4) {
               throw FormatException.getFormatInstance();
            }

            var2 = var0.readBits(4);
            if (var2 >= 10) {
               throw FormatException.getFormatInstance();
            }

            var1.append(toAlphaNumericChar(var2));
         }

         return;
      }
   }

   private static int parseECIValue(BitSource var0) throws FormatException {
      int var1 = var0.readBits(8);
      if ((var1 & 128) == 0) {
         return var1 & 127;
      } else if ((var1 & 192) == 128) {
         return var0.readBits(8) | (var1 & 63) << 8;
      } else if ((var1 & 224) == 192) {
         return var0.readBits(16) | (var1 & 31) << 16;
      } else {
         throw FormatException.getFormatInstance();
      }
   }

   private static char toAlphaNumericChar(int var0) throws FormatException {
      char[] var1 = ALPHANUMERIC_CHARS;
      if (var0 < var1.length) {
         return var1[var0];
      } else {
         throw FormatException.getFormatInstance();
      }
   }
}
