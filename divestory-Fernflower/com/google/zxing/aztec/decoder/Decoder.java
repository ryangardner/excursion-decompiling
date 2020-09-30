package com.google.zxing.aztec.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Arrays;
import java.util.List;

public final class Decoder {
   private static final String[] DIGIT_TABLE = new String[]{"CTRL_PS", " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ",", ".", "CTRL_UL", "CTRL_US"};
   private static final String[] LOWER_TABLE = new String[]{"CTRL_PS", " ", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
   private static final String[] MIXED_TABLE = new String[]{"CTRL_PS", " ", "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\b", "\t", "\n", "\u000b", "\f", "\r", "\u001b", "\u001c", "\u001d", "\u001e", "\u001f", "@", "\\", "^", "_", "`", "|", "~", "\u007f", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS"};
   private static final String[] PUNCT_TABLE = new String[]{"", "\r", "\r\n", ". ", ", ", ": ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "[", "]", "{", "}", "CTRL_UL"};
   private static final String[] UPPER_TABLE = new String[]{"CTRL_PS", " ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
   private AztecDetectorResult ddata;

   private boolean[] correctBits(boolean[] var1) throws FormatException {
      int var2 = this.ddata.getNbLayers();
      byte var3 = 8;
      GenericGF var4;
      if (var2 <= 2) {
         var3 = 6;
         var4 = GenericGF.AZTEC_DATA_6;
      } else if (this.ddata.getNbLayers() <= 8) {
         var4 = GenericGF.AZTEC_DATA_8;
      } else if (this.ddata.getNbLayers() <= 22) {
         var3 = 10;
         var4 = GenericGF.AZTEC_DATA_10;
      } else {
         var3 = 12;
         var4 = GenericGF.AZTEC_DATA_12;
      }

      int var5 = this.ddata.getNbDatablocks();
      int var6 = var1.length / var3;
      if (var6 < var5) {
         throw FormatException.getFormatInstance();
      } else {
         int var7 = var1.length % var3;
         int[] var8 = new int[var6];

         for(var2 = 0; var2 < var6; var7 += var3) {
            var8[var2] = readCode(var1, var7, var3);
            ++var2;
         }

         try {
            ReedSolomonDecoder var14 = new ReedSolomonDecoder(var4);
            var14.decode(var8, var6 - var5);
         } catch (ReedSolomonException var13) {
            throw FormatException.getFormatInstance(var13);
         }

         int var9 = (1 << var3) - 1;
         var7 = 0;

         int var10;
         for(var6 = 0; var7 < var5; var6 = var2) {
            var10 = var8[var7];
            if (var10 == 0 || var10 == var9) {
               throw FormatException.getFormatInstance();
            }

            label80: {
               if (var10 != 1) {
                  var2 = var6;
                  if (var10 != var9 - 1) {
                     break label80;
                  }
               }

               var2 = var6 + 1;
            }

            ++var7;
         }

         var1 = new boolean[var5 * var3 - var6];
         var6 = 0;

         for(var2 = 0; var6 < var5; var2 = var7) {
            int var11 = var8[var6];
            boolean var12;
            if (var11 != 1 && var11 != var9 - 1) {
               var10 = var3 - 1;

               while(true) {
                  var7 = var2;
                  if (var10 < 0) {
                     break;
                  }

                  if ((1 << var10 & var11) != 0) {
                     var12 = true;
                  } else {
                     var12 = false;
                  }

                  var1[var2] = var12;
                  --var10;
                  ++var2;
               }
            } else {
               if (var11 > 1) {
                  var12 = true;
               } else {
                  var12 = false;
               }

               Arrays.fill(var1, var2, var2 + var3 - 1, var12);
               var7 = var2 + (var3 - 1);
            }

            ++var6;
         }

         return var1;
      }
   }

   private static String getCharacter(Decoder.Table var0, int var1) {
      int var2 = null.$SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[var0.ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var2 == 5) {
                     return DIGIT_TABLE[var1];
                  } else {
                     throw new IllegalStateException("Bad table");
                  }
               } else {
                  return PUNCT_TABLE[var1];
               }
            } else {
               return MIXED_TABLE[var1];
            }
         } else {
            return LOWER_TABLE[var1];
         }
      } else {
         return UPPER_TABLE[var1];
      }
   }

   private static String getEncodedData(boolean[] var0) {
      int var1 = var0.length;
      Decoder.Table var2 = Decoder.Table.UPPER;
      Decoder.Table var3 = Decoder.Table.UPPER;
      StringBuilder var4 = new StringBuilder(20);
      int var5 = 0;

      while(var5 < var1) {
         int var8;
         if (var3 == Decoder.Table.BINARY) {
            if (var1 - var5 < 5) {
               break;
            }

            int var6 = readCode(var0, var5, 5);
            int var7 = var5 + 5;
            var8 = var6;
            var5 = var7;
            if (var6 == 0) {
               if (var1 - var7 < 11) {
                  break;
               }

               var8 = readCode(var0, var7, 11) + 31;
               var5 = var7 + 11;
            }

            var6 = 0;
            var7 = var5;

            while(true) {
               var3 = var2;
               var5 = var7;
               if (var6 >= var8) {
                  break;
               }

               if (var1 - var7 < 8) {
                  var5 = var1;
                  var3 = var2;
                  break;
               }

               var4.append((char)readCode(var0, var7, 8));
               var7 += 8;
               ++var6;
            }
         } else {
            byte var10;
            if (var3 == Decoder.Table.DIGIT) {
               var10 = 4;
            } else {
               var10 = 5;
            }

            if (var1 - var5 < var10) {
               break;
            }

            var8 = readCode(var0, var5, var10);
            var5 += var10;
            String var9 = getCharacter(var3, var8);
            if (var9.startsWith("CTRL_")) {
               var3 = getTable(var9.charAt(5));
               if (var9.charAt(6) != 'L') {
                  continue;
               }
            } else {
               var4.append(var9);
               var3 = var2;
            }
         }

         var2 = var3;
         var3 = var3;
      }

      return var4.toString();
   }

   private static Decoder.Table getTable(char var0) {
      if (var0 != 'B') {
         if (var0 != 'D') {
            if (var0 != 'P') {
               if (var0 != 'L') {
                  return var0 != 'M' ? Decoder.Table.UPPER : Decoder.Table.MIXED;
               } else {
                  return Decoder.Table.LOWER;
               }
            } else {
               return Decoder.Table.PUNCT;
            }
         } else {
            return Decoder.Table.DIGIT;
         }
      } else {
         return Decoder.Table.BINARY;
      }
   }

   public static String highLevelDecode(boolean[] var0) {
      return getEncodedData(var0);
   }

   private static int readCode(boolean[] var0, int var1, int var2) {
      int var3 = 0;

      for(int var4 = var1; var4 < var1 + var2; ++var4) {
         int var5 = var3 << 1;
         var3 = var5;
         if (var0[var4]) {
            var3 = var5 | 1;
         }
      }

      return var3;
   }

   private static int totalBitsInLayer(int var0, boolean var1) {
      byte var2;
      if (var1) {
         var2 = 88;
      } else {
         var2 = 112;
      }

      return (var2 + var0 * 16) * var0;
   }

   public DecoderResult decode(AztecDetectorResult var1) throws FormatException {
      this.ddata = var1;
      return new DecoderResult((byte[])null, getEncodedData(this.correctBits(this.extractBits(var1.getBits()))), (List)null, (String)null);
   }

   boolean[] extractBits(BitMatrix var1) {
      boolean var2 = this.ddata.isCompact();
      int var3 = this.ddata.getNbLayers();
      int var4;
      if (var2) {
         var4 = var3 * 4 + 11;
      } else {
         var4 = var3 * 4 + 14;
      }

      int[] var5 = new int[var4];
      boolean[] var6 = new boolean[totalBitsInLayer(var3, var2)];
      int var7;
      int var8;
      int var9;
      int var10;
      if (var2) {
         for(var7 = 0; var7 < var4; var5[var7] = var7++) {
         }
      } else {
         var8 = var4 / 2;
         var9 = (var4 + 1 + (var8 - 1) / 15 * 2) / 2;

         for(var7 = 0; var7 < var8; ++var7) {
            var10 = var7 / 15 + var7;
            var5[var8 - var7 - 1] = var9 - var10 - 1;
            var5[var8 + var7] = var10 + var9 + 1;
         }
      }

      var7 = 0;

      for(var8 = 0; var7 < var3; ++var7) {
         var9 = (var3 - var7) * 4;
         if (var2) {
            var9 += 9;
         } else {
            var9 += 12;
         }

         int var11 = var7 * 2;
         int var12 = var4 - 1 - var11;

         for(var10 = 0; var10 < var9; ++var10) {
            int var13 = var10 * 2;

            for(int var14 = 0; var14 < 2; ++var14) {
               int var15 = var11 + var14;
               int var16 = var5[var15];
               int var17 = var11 + var10;
               var6[var8 + var13 + var14] = var1.get(var16, var5[var17]);
               var16 = var5[var17];
               var17 = var12 - var14;
               var6[var9 * 2 + var8 + var13 + var14] = var1.get(var16, var5[var17]);
               var16 = var5[var17];
               var17 = var12 - var10;
               var6[var9 * 4 + var8 + var13 + var14] = var1.get(var16, var5[var17]);
               var6[var9 * 6 + var8 + var13 + var14] = var1.get(var5[var17], var5[var15]);
            }
         }

         var8 += var9 * 8;
      }

      return var6;
   }

   private static enum Table {
      BINARY,
      DIGIT,
      LOWER,
      MIXED,
      PUNCT,
      UPPER;

      static {
         Decoder.Table var0 = new Decoder.Table("BINARY", 5);
         BINARY = var0;
      }
   }
}
