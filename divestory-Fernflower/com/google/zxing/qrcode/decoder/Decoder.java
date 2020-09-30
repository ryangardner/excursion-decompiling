package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Map;

public final class Decoder {
   private final ReedSolomonDecoder rsDecoder;

   public Decoder() {
      this.rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);
   }

   private void correctErrors(byte[] var1, int var2) throws ChecksumException {
      int var3 = var1.length;
      int[] var4 = new int[var3];
      byte var5 = 0;

      int var6;
      for(var6 = 0; var6 < var3; ++var6) {
         var4[var6] = var1[var6] & 255;
      }

      var6 = var1.length;

      try {
         this.rsDecoder.decode(var4, var6 - var2);
      } catch (ReedSolomonException var7) {
         throw ChecksumException.getChecksumInstance();
      }

      for(var6 = var5; var6 < var2; ++var6) {
         var1[var6] = (byte)((byte)var4[var6]);
      }

   }

   private DecoderResult decode(BitMatrixParser var1, Map<DecodeHintType, ?> var2) throws FormatException, ChecksumException {
      Version var3 = var1.readVersion();
      ErrorCorrectionLevel var4 = var1.readFormatInformation().getErrorCorrectionLevel();
      DataBlock[] var13 = DataBlock.getDataBlocks(var1.readCodewords(), var3, var4);
      int var5 = var13.length;
      int var6 = 0;

      int var7;
      for(var7 = 0; var6 < var5; ++var6) {
         var7 += var13[var6].getNumDataCodewords();
      }

      byte[] var8 = new byte[var7];
      int var9 = var13.length;
      var7 = 0;

      for(var6 = 0; var7 < var9; ++var7) {
         DataBlock var10 = var13[var7];
         byte[] var11 = var10.getCodewords();
         int var12 = var10.getNumDataCodewords();
         this.correctErrors(var11, var12);

         for(var5 = 0; var5 < var12; ++var6) {
            var8[var6] = (byte)var11[var5];
            ++var5;
         }
      }

      return DecodedBitStreamParser.decode(var8, var3, var4, var2);
   }

   public DecoderResult decode(BitMatrix var1) throws ChecksumException, FormatException {
      return this.decode((BitMatrix)var1, (Map)null);
   }

   public DecoderResult decode(BitMatrix var1, Map<DecodeHintType, ?> var2) throws FormatException, ChecksumException {
      BitMatrixParser var3 = new BitMatrixParser(var1);
      FormatException var4 = null;

      ChecksumException var9;
      try {
         DecoderResult var10 = this.decode(var3, var2);
         return var10;
      } catch (FormatException var7) {
         var4 = var7;
         var9 = null;
      } catch (ChecksumException var8) {
         var9 = var8;
      }

      Object var11;
      try {
         var3.remask();
         var3.setMirror(true);
         var3.readVersion();
         var3.readFormatInformation();
         var3.mirror();
         DecoderResult var12 = this.decode(var3, var2);
         QRCodeDecoderMetaData var13 = new QRCodeDecoderMetaData(true);
         var12.setOther(var13);
         return var12;
      } catch (FormatException var5) {
         var11 = var5;
      } catch (ChecksumException var6) {
         var11 = var6;
      }

      if (var4 == null) {
         if (var9 != null) {
            throw var9;
         } else {
            throw var11;
         }
      } else {
         throw var4;
      }
   }

   public DecoderResult decode(boolean[][] var1) throws ChecksumException, FormatException {
      return this.decode((boolean[][])var1, (Map)null);
   }

   public DecoderResult decode(boolean[][] var1, Map<DecodeHintType, ?> var2) throws ChecksumException, FormatException {
      int var3 = var1.length;
      BitMatrix var4 = new BitMatrix(var3);

      for(int var5 = 0; var5 < var3; ++var5) {
         for(int var6 = 0; var6 < var3; ++var6) {
            if (var1[var5][var6]) {
               var4.set(var6, var5);
            }
         }
      }

      return this.decode(var4, var2);
   }
}
