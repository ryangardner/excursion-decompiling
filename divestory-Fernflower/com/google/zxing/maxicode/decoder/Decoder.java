package com.google.zxing.maxicode.decoder;

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
   private static final int ALL = 0;
   private static final int EVEN = 1;
   private static final int ODD = 2;
   private final ReedSolomonDecoder rsDecoder;

   public Decoder() {
      this.rsDecoder = new ReedSolomonDecoder(GenericGF.MAXICODE_FIELD_64);
   }

   private void correctErrors(byte[] var1, int var2, int var3, int var4, int var5) throws ChecksumException {
      int var6 = var3 + var4;
      byte var7;
      if (var5 == 0) {
         var7 = 1;
      } else {
         var7 = 2;
      }

      int[] var8 = new int[var6 / var7];
      byte var9 = 0;

      for(int var10 = 0; var10 < var6; ++var10) {
         if (var5 == 0 || var10 % 2 == var5 - 1) {
            var8[var10 / var7] = var1[var10 + var2] & 255;
         }
      }

      try {
         this.rsDecoder.decode(var8, var4 / var7);
      } catch (ReedSolomonException var11) {
         throw ChecksumException.getChecksumInstance();
      }

      for(var4 = var9; var4 < var3; ++var4) {
         if (var5 == 0 || var4 % 2 == var5 - 1) {
            var1[var4 + var2] = (byte)((byte)var8[var4 / var7]);
         }
      }

   }

   public DecoderResult decode(BitMatrix var1) throws ChecksumException, FormatException {
      return this.decode(var1, (Map)null);
   }

   public DecoderResult decode(BitMatrix var1, Map<DecodeHintType, ?> var2) throws FormatException, ChecksumException {
      byte[] var5 = (new BitMatrixParser(var1)).readCodewords();
      this.correctErrors(var5, 0, 10, 10, 0);
      int var3 = var5[0] & 15;
      byte[] var4;
      if (var3 != 2 && var3 != 3 && var3 != 4) {
         if (var3 != 5) {
            throw FormatException.getFormatInstance();
         }

         this.correctErrors(var5, 20, 68, 56, 1);
         this.correctErrors(var5, 20, 68, 56, 2);
         var4 = new byte[78];
      } else {
         this.correctErrors(var5, 20, 84, 40, 1);
         this.correctErrors(var5, 20, 84, 40, 2);
         var4 = new byte[94];
      }

      System.arraycopy(var5, 0, var4, 0, 10);
      System.arraycopy(var5, 20, var4, 10, var4.length - 10);
      return DecodedBitStreamParser.decode(var4, var3);
   }
}
