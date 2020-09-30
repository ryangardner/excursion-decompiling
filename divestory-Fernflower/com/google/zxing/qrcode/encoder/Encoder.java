package com.google.zxing.qrcode.encoder;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class Encoder {
   private static final int[] ALPHANUMERIC_TABLE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};
   static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

   private Encoder() {
   }

   static void append8BitBytes(String var0, BitArray var1, String var2) throws WriterException {
      byte[] var6;
      try {
         var6 = var0.getBytes(var2);
      } catch (UnsupportedEncodingException var5) {
         throw new WriterException(var5);
      }

      int var3 = var6.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         var1.appendBits(var6[var4], 8);
      }

   }

   static void appendAlphanumericBytes(CharSequence var0, BitArray var1) throws WriterException {
      int var2 = var0.length();
      int var3 = 0;

      while(var3 < var2) {
         int var4 = getAlphanumericCode(var0.charAt(var3));
         if (var4 == -1) {
            throw new WriterException();
         }

         int var5 = var3 + 1;
         if (var5 < var2) {
            var5 = getAlphanumericCode(var0.charAt(var5));
            if (var5 == -1) {
               throw new WriterException();
            }

            var1.appendBits(var4 * 45 + var5, 11);
            var3 += 2;
         } else {
            var1.appendBits(var4, 6);
            var3 = var5;
         }
      }

   }

   static void appendBytes(String var0, Mode var1, BitArray var2, String var3) throws WriterException {
      int var4 = null.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[var1.ordinal()];
      if (var4 != 1) {
         if (var4 != 2) {
            if (var4 != 3) {
               if (var4 != 4) {
                  StringBuilder var5 = new StringBuilder();
                  var5.append("Invalid mode: ");
                  var5.append(var1);
                  throw new WriterException(var5.toString());
               }

               appendKanjiBytes(var0, var2);
            } else {
               append8BitBytes(var0, var2, var3);
            }
         } else {
            appendAlphanumericBytes(var0, var2);
         }
      } else {
         appendNumericBytes(var0, var2);
      }

   }

   private static void appendECI(CharacterSetECI var0, BitArray var1) {
      var1.appendBits(Mode.ECI.getBits(), 4);
      var1.appendBits(var0.getValue(), 8);
   }

   static void appendKanjiBytes(String var0, BitArray var1) throws WriterException {
      byte[] var7;
      try {
         var7 = var0.getBytes("Shift_JIS");
      } catch (UnsupportedEncodingException var6) {
         throw new WriterException(var6);
      }

      int var2 = var7.length;

      for(int var3 = 0; var3 < var2; var3 += 2) {
         int var8;
         label44: {
            int var4 = (var7[var3] & 255) << 8 | var7[var3 + 1] & 255;
            char var5 = '腀';
            if (var4 < 33088 || var4 > 40956) {
               if (var4 < 57408 || var4 > 60351) {
                  var8 = -1;
                  break label44;
               }

               var5 = '셀';
            }

            var8 = var4 - var5;
         }

         if (var8 == -1) {
            throw new WriterException("Invalid byte sequence");
         }

         var1.appendBits((var8 >> 8) * 192 + (var8 & 255), 13);
      }

   }

   static void appendLengthInfo(int var0, Version var1, Mode var2, BitArray var3) throws WriterException {
      int var4 = var2.getCharacterCountBits(var1);
      int var5 = 1 << var4;
      if (var0 < var5) {
         var3.appendBits(var0, var4);
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append(var0);
         var6.append(" is bigger than ");
         var6.append(var5 - 1);
         throw new WriterException(var6.toString());
      }
   }

   static void appendModeInfo(Mode var0, BitArray var1) {
      var1.appendBits(var0.getBits(), 4);
   }

   static void appendNumericBytes(CharSequence var0, BitArray var1) {
      int var2 = var0.length();
      int var3 = 0;

      while(var3 < var2) {
         int var4 = var0.charAt(var3) - 48;
         int var5 = var3 + 2;
         if (var5 < var2) {
            var1.appendBits(var4 * 100 + (var0.charAt(var3 + 1) - 48) * 10 + (var0.charAt(var5) - 48), 10);
            var3 += 3;
         } else {
            ++var3;
            if (var3 < var2) {
               var1.appendBits(var4 * 10 + (var0.charAt(var3) - 48), 7);
               var3 = var5;
            } else {
               var1.appendBits(var4, 4);
            }
         }
      }

   }

   private static int calculateMaskPenalty(ByteMatrix var0) {
      return MaskUtil.applyMaskPenaltyRule1(var0) + MaskUtil.applyMaskPenaltyRule2(var0) + MaskUtil.applyMaskPenaltyRule3(var0) + MaskUtil.applyMaskPenaltyRule4(var0);
   }

   private static int chooseMaskPattern(BitArray var0, ErrorCorrectionLevel var1, Version var2, ByteMatrix var3) throws WriterException {
      int var4 = Integer.MAX_VALUE;
      int var5 = -1;

      int var8;
      for(int var6 = 0; var6 < 8; var4 = var8) {
         MatrixUtil.buildMatrix(var0, var1, var2, var6, var3);
         int var7 = calculateMaskPenalty(var3);
         var8 = var4;
         if (var7 < var4) {
            var5 = var6;
            var8 = var7;
         }

         ++var6;
      }

      return var5;
   }

   public static Mode chooseMode(String var0) {
      return chooseMode(var0, (String)null);
   }

   private static Mode chooseMode(String var0, String var1) {
      if ("Shift_JIS".equals(var1)) {
         Mode var6;
         if (isOnlyDoubleByteKanji(var0)) {
            var6 = Mode.KANJI;
         } else {
            var6 = Mode.BYTE;
         }

         return var6;
      } else {
         int var2 = 0;
         boolean var3 = false;

         boolean var4;
         for(var4 = false; var2 < var0.length(); ++var2) {
            char var5 = var0.charAt(var2);
            if (var5 >= '0' && var5 <= '9') {
               var4 = true;
            } else {
               if (getAlphanumericCode(var5) == -1) {
                  return Mode.BYTE;
               }

               var3 = true;
            }
         }

         if (var3) {
            return Mode.ALPHANUMERIC;
         } else if (var4) {
            return Mode.NUMERIC;
         } else {
            return Mode.BYTE;
         }
      }
   }

   private static Version chooseVersion(int var0, ErrorCorrectionLevel var1) throws WriterException {
      for(int var2 = 1; var2 <= 40; ++var2) {
         Version var3 = Version.getVersionForNumber(var2);
         if (var3.getTotalCodewords() - var3.getECBlocksForLevel(var1).getTotalECCodewords() >= (var0 + 7) / 8) {
            return var3;
         }
      }

      throw new WriterException("Data too big");
   }

   public static QRCode encode(String var0, ErrorCorrectionLevel var1) throws WriterException {
      return encode(var0, var1, (Map)null);
   }

   public static QRCode encode(String var0, ErrorCorrectionLevel var1, Map<EncodeHintType, ?> var2) throws WriterException {
      String var10;
      if (var2 == null) {
         var10 = null;
      } else {
         var10 = (String)var2.get(EncodeHintType.CHARACTER_SET);
      }

      String var3 = var10;
      if (var10 == null) {
         var3 = "ISO-8859-1";
      }

      Mode var11 = chooseMode(var0, var3);
      BitArray var4 = new BitArray();
      if (var11 == Mode.BYTE && !"ISO-8859-1".equals(var3)) {
         CharacterSetECI var5 = CharacterSetECI.getCharacterSetECIByName(var3);
         if (var5 != null) {
            appendECI(var5, var4);
         }
      }

      appendModeInfo(var11, var4);
      BitArray var14 = new BitArray();
      appendBytes(var0, var11, var14, var3);
      Version var13 = chooseVersion(var4.getSize() + var11.getCharacterCountBits(Version.getVersionForNumber(1)) + var14.getSize(), var1);
      var13 = chooseVersion(var4.getSize() + var11.getCharacterCountBits(var13) + var14.getSize(), var1);
      BitArray var6 = new BitArray();
      var6.appendBitArray(var4);
      int var7;
      if (var11 == Mode.BYTE) {
         var7 = var14.getSizeInBytes();
      } else {
         var7 = var0.length();
      }

      appendLengthInfo(var7, var13, var11, var6);
      var6.appendBitArray(var14);
      Version.ECBlocks var8 = var13.getECBlocksForLevel(var1);
      var7 = var13.getTotalCodewords() - var8.getTotalECCodewords();
      terminateBits(var7, var6);
      var4 = interleaveWithECBytes(var6, var13.getTotalCodewords(), var7, var8.getNumBlocks());
      QRCode var9 = new QRCode();
      var9.setECLevel(var1);
      var9.setMode(var11);
      var9.setVersion(var13);
      var7 = var13.getDimensionForVersion();
      ByteMatrix var12 = new ByteMatrix(var7, var7);
      var7 = chooseMaskPattern(var4, var1, var13, var12);
      var9.setMaskPattern(var7);
      MatrixUtil.buildMatrix(var4, var1, var13, var7, var12);
      var9.setMatrix(var12);
      return var9;
   }

   static byte[] generateECBytes(byte[] var0, int var1) {
      int var2 = var0.length;
      int[] var3 = new int[var2 + var1];
      byte var4 = 0;

      int var5;
      for(var5 = 0; var5 < var2; ++var5) {
         var3[var5] = var0[var5] & 255;
      }

      (new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256)).encode(var3, var1);
      var0 = new byte[var1];

      for(var5 = var4; var5 < var1; ++var5) {
         var0[var5] = (byte)((byte)var3[var2 + var5]);
      }

      return var0;
   }

   static int getAlphanumericCode(int var0) {
      int[] var1 = ALPHANUMERIC_TABLE;
      return var0 < var1.length ? var1[var0] : -1;
   }

   static void getNumDataBytesAndNumECBytesForBlockID(int var0, int var1, int var2, int var3, int[] var4, int[] var5) throws WriterException {
      if (var3 < var2) {
         int var6 = var0 % var2;
         int var7 = var2 - var6;
         int var8 = var0 / var2;
         var1 /= var2;
         int var9 = var1 + 1;
         int var10 = var8 - var1;
         var8 = var8 + 1 - var9;
         if (var10 == var8) {
            if (var2 == var7 + var6) {
               if (var0 == (var1 + var10) * var7 + (var9 + var8) * var6) {
                  if (var3 < var7) {
                     var4[0] = var1;
                     var5[0] = var10;
                  } else {
                     var4[0] = var9;
                     var5[0] = var8;
                  }

               } else {
                  throw new WriterException("Total bytes mismatch");
               }
            } else {
               throw new WriterException("RS blocks mismatch");
            }
         } else {
            throw new WriterException("EC bytes mismatch");
         }
      } else {
         throw new WriterException("Block ID too large");
      }
   }

   static BitArray interleaveWithECBytes(BitArray var0, int var1, int var2, int var3) throws WriterException {
      if (var0.getSizeInBytes() != var2) {
         throw new WriterException("Number of bits and data bytes does not match");
      } else {
         ArrayList var4 = new ArrayList(var3);
         byte var5 = 0;
         int var6 = 0;
         int var7 = 0;
         int var8 = 0;

         int var9;
         byte[] var13;
         for(var9 = 0; var6 < var3; ++var6) {
            int[] var10 = new int[1];
            int[] var11 = new int[1];
            getNumDataBytesAndNumECBytesForBlockID(var1, var2, var3, var6, var10, var11);
            int var12 = var10[0];
            var13 = new byte[var12];
            var0.toBytes(var7 * 8, var13, 0, var12);
            byte[] var16 = generateECBytes(var13, var11[0]);
            var4.add(new BlockPair(var13, var16));
            var8 = Math.max(var8, var12);
            var9 = Math.max(var9, var16.length);
            var7 += var10[0];
         }

         if (var2 != var7) {
            throw new WriterException("Data bytes does not match offset");
         } else {
            var0 = new BitArray();
            var3 = 0;

            while(true) {
               var2 = var5;
               Iterator var15;
               if (var3 >= var8) {
                  while(var2 < var9) {
                     var15 = var4.iterator();

                     while(var15.hasNext()) {
                        var13 = ((BlockPair)var15.next()).getErrorCorrectionBytes();
                        if (var2 < var13.length) {
                           var0.appendBits(var13[var2], 8);
                        }
                     }

                     ++var2;
                  }

                  if (var1 == var0.getSizeInBytes()) {
                     return var0;
                  }

                  StringBuilder var14 = new StringBuilder();
                  var14.append("Interleaving error: ");
                  var14.append(var1);
                  var14.append(" and ");
                  var14.append(var0.getSizeInBytes());
                  var14.append(" differ.");
                  throw new WriterException(var14.toString());
               }

               var15 = var4.iterator();

               while(var15.hasNext()) {
                  var13 = ((BlockPair)var15.next()).getDataBytes();
                  if (var3 < var13.length) {
                     var0.appendBits(var13[var3], 8);
                  }
               }

               ++var3;
            }
         }
      }
   }

   private static boolean isOnlyDoubleByteKanji(String var0) {
      byte[] var5;
      try {
         var5 = var0.getBytes("Shift_JIS");
      } catch (UnsupportedEncodingException var4) {
         return false;
      }

      int var1 = var5.length;
      if (var1 % 2 != 0) {
         return false;
      } else {
         for(int var2 = 0; var2 < var1; var2 += 2) {
            int var3 = var5[var2] & 255;
            if ((var3 < 129 || var3 > 159) && (var3 < 224 || var3 > 235)) {
               return false;
            }
         }

         return true;
      }
   }

   static void terminateBits(int var0, BitArray var1) throws WriterException {
      int var2 = var0 * 8;
      if (var1.getSize() > var2) {
         StringBuilder var6 = new StringBuilder();
         var6.append("data bits cannot fit in the QR Code");
         var6.append(var1.getSize());
         var6.append(" > ");
         var6.append(var2);
         throw new WriterException(var6.toString());
      } else {
         byte var3 = 0;

         int var4;
         for(var4 = 0; var4 < 4 && var1.getSize() < var2; ++var4) {
            var1.appendBit(false);
         }

         var4 = var1.getSize() & 7;
         if (var4 > 0) {
            while(var4 < 8) {
               var1.appendBit(false);
               ++var4;
            }
         }

         int var5 = var1.getSizeInBytes();

         for(var4 = var3; var4 < var0 - var5; ++var4) {
            short var7;
            if ((var4 & 1) == 0) {
               var7 = 236;
            } else {
               var7 = 17;
            }

            var1.appendBits(var7, 8);
         }

         if (var1.getSize() != var2) {
            throw new WriterException("Bits size does not equal capacity");
         }
      }
   }
}
