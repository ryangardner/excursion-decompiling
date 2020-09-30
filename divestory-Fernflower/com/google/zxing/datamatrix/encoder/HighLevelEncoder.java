package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import java.util.Arrays;

public final class HighLevelEncoder {
   static final int ASCII_ENCODATION = 0;
   static final int BASE256_ENCODATION = 5;
   static final int C40_ENCODATION = 1;
   static final char C40_UNLATCH = 'þ';
   static final int EDIFACT_ENCODATION = 4;
   static final char LATCH_TO_ANSIX12 = 'î';
   static final char LATCH_TO_BASE256 = 'ç';
   static final char LATCH_TO_C40 = 'æ';
   static final char LATCH_TO_EDIFACT = 'ð';
   static final char LATCH_TO_TEXT = 'ï';
   private static final char MACRO_05 = 'ì';
   private static final String MACRO_05_HEADER = "[)>\u001e05\u001d";
   private static final char MACRO_06 = 'í';
   private static final String MACRO_06_HEADER = "[)>\u001e06\u001d";
   private static final String MACRO_TRAILER = "\u001e\u0004";
   private static final char PAD = '\u0081';
   static final int TEXT_ENCODATION = 2;
   static final char UPPER_SHIFT = 'ë';
   static final int X12_ENCODATION = 3;
   static final char X12_UNLATCH = 'þ';

   private HighLevelEncoder() {
   }

   public static int determineConsecutiveDigitCount(CharSequence var0, int var1) {
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

   public static String encodeHighLevel(String var0) {
      return encodeHighLevel(var0, SymbolShapeHint.FORCE_NONE, (Dimension)null, (Dimension)null);
   }

   public static String encodeHighLevel(String var0, SymbolShapeHint var1, Dimension var2, Dimension var3) {
      ASCIIEncoder var4 = new ASCIIEncoder();
      byte var5 = 0;
      C40Encoder var6 = new C40Encoder();
      TextEncoder var7 = new TextEncoder();
      X12Encoder var8 = new X12Encoder();
      EdifactEncoder var9 = new EdifactEncoder();
      Base256Encoder var10 = new Base256Encoder();
      EncoderContext var11 = new EncoderContext(var0);
      var11.setSymbolShape(var1);
      var11.setSizeConstraints(var2, var3);
      int var12;
      if (var0.startsWith("[)>\u001e05\u001d") && var0.endsWith("\u001e\u0004")) {
         var11.writeCodeword('ì');
         var11.setSkipAtEnd(2);
         var11.pos += 7;
         var12 = var5;
      } else {
         var12 = var5;
         if (var0.startsWith("[)>\u001e06\u001d")) {
            var12 = var5;
            if (var0.endsWith("\u001e\u0004")) {
               var11.writeCodeword('í');
               var11.setSkipAtEnd(2);
               var11.pos += 7;
               var12 = var5;
            }
         }
      }

      while(var11.hasMoreCharacters()) {
         (new Encoder[]{var4, var6, var7, var8, var9, var10})[var12].encode(var11);
         if (var11.getNewEncoding() >= 0) {
            var12 = var11.getNewEncoding();
            var11.resetEncoderSignal();
         }
      }

      int var13 = var11.getCodewordCount();
      var11.updateSymbolInfo();
      int var15 = var11.getSymbolInfo().getDataCapacity();
      if (var13 < var15 && var12 != 0 && var12 != 5) {
         var11.writeCodeword('þ');
      }

      StringBuilder var14 = var11.getCodewords();
      if (var14.length() < var15) {
         var14.append('\u0081');
      }

      while(var14.length() < var15) {
         var14.append(randomize253State('\u0081', var14.length() + 1));
      }

      return var11.getCodewords().toString();
   }

   private static int findMinimums(float[] var0, int[] var1, int var2, byte[] var3) {
      Arrays.fill(var3, (byte)0);

      int var6;
      for(int var4 = 0; var4 < 6; var2 = var6) {
         var1[var4] = (int)Math.ceil((double)var0[var4]);
         int var5 = var1[var4];
         var6 = var2;
         if (var2 > var5) {
            Arrays.fill(var3, (byte)0);
            var6 = var5;
         }

         if (var6 == var5) {
            var3[var4] = (byte)((byte)(var3[var4] + 1));
         }

         ++var4;
      }

      return var2;
   }

   private static int getMinimumCount(byte[] var0) {
      int var1 = 0;

      int var2;
      for(var2 = 0; var1 < 6; ++var1) {
         var2 += var0[var1];
      }

      return var2;
   }

   static void illegalCharacter(char var0) {
      String var1 = Integer.toHexString(var0);
      StringBuilder var2 = new StringBuilder();
      var2.append("0000".substring(0, 4 - var1.length()));
      var2.append(var1);
      String var4 = var2.toString();
      StringBuilder var3 = new StringBuilder();
      var3.append("Illegal character: ");
      var3.append(var0);
      var3.append(" (0x");
      var3.append(var4);
      var3.append(')');
      throw new IllegalArgumentException(var3.toString());
   }

   static boolean isDigit(char var0) {
      boolean var1;
      if (var0 >= '0' && var0 <= '9') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   static boolean isExtendedASCII(char var0) {
      boolean var1;
      if (var0 >= 128 && var0 <= 255) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isNativeC40(char var0) {
      boolean var1;
      if (var0 == ' ' || var0 >= '0' && var0 <= '9' || var0 >= 'A' && var0 <= 'Z') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isNativeEDIFACT(char var0) {
      boolean var1;
      if (var0 >= ' ' && var0 <= '^') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isNativeText(char var0) {
      boolean var1;
      if (var0 == ' ' || var0 >= '0' && var0 <= '9' || var0 >= 'a' && var0 <= 'z') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isNativeX12(char var0) {
      boolean var1;
      if (isX12TermSep(var0) || var0 == ' ' || var0 >= '0' && var0 <= '9' || var0 >= 'A' && var0 <= 'Z') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isSpecialB256(char var0) {
      return false;
   }

   private static boolean isX12TermSep(char var0) {
      boolean var1;
      if (var0 != '\r' && var0 != '*' && var0 != '>') {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   static int lookAheadTest(CharSequence var0, int var1, int var2) {
      if (var1 >= var0.length()) {
         return var2;
      } else {
         float[] var3;
         if (var2 == 0) {
            var3 = new float[]{0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.25F};
         } else {
            var3 = new float[]{1.0F, 2.0F, 2.0F, 2.0F, 2.0F, 2.25F};
            var3[var2] = 0.0F;
         }

         var2 = 0;

         while(true) {
            int var4 = var1 + var2;
            if (var4 == var0.length()) {
               byte[] var9 = new byte[6];
               int[] var8 = new int[6];
               var1 = findMinimums(var3, var8, Integer.MAX_VALUE, var9);
               var2 = getMinimumCount(var9);
               if (var8[0] == var1) {
                  return 0;
               }

               if (var2 == 1 && var9[5] > 0) {
                  return 5;
               }

               if (var2 == 1 && var9[4] > 0) {
                  return 4;
               }

               if (var2 == 1 && var9[2] > 0) {
                  return 2;
               }

               if (var2 == 1 && var9[3] > 0) {
                  return 3;
               }

               return 1;
            }

            char var6 = var0.charAt(var4);
            var4 = var2 + 1;
            int var10002;
            if (isDigit(var6)) {
               var3[0] = (float)((double)var3[0] + 0.5D);
            } else if (isExtendedASCII(var6)) {
               var3[0] = (float)((int)Math.ceil((double)var3[0]));
               var3[0] += 2.0F;
            } else {
               var3[0] = (float)((int)Math.ceil((double)var3[0]));
               var10002 = var3[0]++;
            }

            if (isNativeC40(var6)) {
               var3[1] += 0.6666667F;
            } else if (isExtendedASCII(var6)) {
               var3[1] += 2.6666667F;
            } else {
               var10002 = var3[1]++;
            }

            if (isNativeText(var6)) {
               var3[2] += 0.6666667F;
            } else if (isExtendedASCII(var6)) {
               var3[2] += 2.6666667F;
            } else {
               var10002 = var3[2]++;
            }

            if (isNativeX12(var6)) {
               var3[3] += 0.6666667F;
            } else if (isExtendedASCII(var6)) {
               var3[3] += 4.3333335F;
            } else {
               var3[3] += 3.3333333F;
            }

            if (isNativeEDIFACT(var6)) {
               var3[4] += 0.75F;
            } else if (isExtendedASCII(var6)) {
               var3[4] += 4.25F;
            } else {
               var3[4] += 3.25F;
            }

            if (isSpecialB256(var6)) {
               var3[5] += 4.0F;
            } else {
               var10002 = var3[5]++;
            }

            var2 = var4;
            if (var4 >= 4) {
               int[] var5 = new int[6];
               byte[] var7 = new byte[6];
               findMinimums(var3, var5, Integer.MAX_VALUE, var7);
               var2 = getMinimumCount(var7);
               if (var5[0] < var5[5] && var5[0] < var5[1] && var5[0] < var5[2] && var5[0] < var5[3] && var5[0] < var5[4]) {
                  return 0;
               }

               if (var5[5] < var5[0] || var7[1] + var7[2] + var7[3] + var7[4] == 0) {
                  return 5;
               }

               if (var2 == 1 && var7[4] > 0) {
                  return 4;
               }

               if (var2 == 1 && var7[2] > 0) {
                  return 2;
               }

               if (var2 == 1 && var7[3] > 0) {
                  return 3;
               }

               var2 = var4;
               if (var5[1] + 1 < var5[0]) {
                  var2 = var4;
                  if (var5[1] + 1 < var5[5]) {
                     var2 = var4;
                     if (var5[1] + 1 < var5[4]) {
                        var2 = var4;
                        if (var5[1] + 1 < var5[2]) {
                           if (var5[1] < var5[3]) {
                              return 1;
                           }

                           var2 = var4;
                           if (var5[1] == var5[3]) {
                              for(var1 = var1 + var4 + 1; var1 < var0.length(); ++var1) {
                                 var6 = var0.charAt(var1);
                                 if (isX12TermSep(var6)) {
                                    return 3;
                                 }

                                 if (!isNativeX12(var6)) {
                                    break;
                                 }
                              }

                              return 1;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static char randomize253State(char var0, int var1) {
      int var2 = var0 + var1 * 149 % 253 + 1;
      if (var2 > 254) {
         var2 -= 254;
      }

      return (char)var2;
   }
}
