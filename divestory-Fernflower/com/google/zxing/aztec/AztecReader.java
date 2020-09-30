package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.aztec.decoder.Decoder;
import com.google.zxing.aztec.detector.Detector;
import com.google.zxing.common.DecoderResult;
import java.util.List;
import java.util.Map;

public final class AztecReader implements Reader {
   public Result decode(BinaryBitmap var1) throws NotFoundException, FormatException {
      return this.decode(var1, (Map)null);
   }

   public Result decode(BinaryBitmap var1, Map<DecodeHintType, ?> var2) throws NotFoundException, FormatException {
      Detector var3 = new Detector(var1.getBlackMatrix());
      int var4 = 0;
      DecoderResult var5 = null;

      FormatException var7;
      ResultPoint[] var16;
      Object var23;
      label73: {
         DecoderResult var26;
         label72: {
            FormatException var6;
            label71: {
               label70: {
                  AztecDetectorResult var24;
                  try {
                     var24 = var3.detect(false);
                     var16 = var24.getPoints();
                  } catch (NotFoundException var14) {
                     var23 = var14;
                     var16 = null;
                     break label70;
                  } catch (FormatException var15) {
                     var6 = var15;
                     var16 = null;
                     break label71;
                  }

                  try {
                     Decoder var25 = new Decoder();
                     var26 = var25.decode(var24);
                     break label72;
                  } catch (NotFoundException var12) {
                     var23 = var12;
                  } catch (FormatException var13) {
                     var6 = var13;
                     break label71;
                  }
               }

               var7 = null;
               break label73;
            }

            var7 = var6;
            var23 = null;
            break label73;
         }

         var7 = null;
         var5 = var26;
         var23 = var7;
      }

      DecoderResult var8 = var5;
      if (var5 == null) {
         label78: {
            Object var17;
            try {
               AztecDetectorResult var27 = var3.detect(true);
               var16 = var27.getPoints();
               Decoder var22 = new Decoder();
               var8 = var22.decode(var27);
               break label78;
            } catch (NotFoundException var10) {
               var17 = var10;
            } catch (FormatException var11) {
               var17 = var11;
            }

            if (var23 == null) {
               if (var7 != null) {
                  throw var7;
               }

               throw var17;
            }

            throw var23;
         }
      }

      if (var2 != null) {
         ResultPointCallback var18 = (ResultPointCallback)var2.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
         if (var18 != null) {
            for(int var9 = var16.length; var4 < var9; ++var4) {
               var18.foundPossibleResultPoint(var16[var4]);
            }
         }
      }

      Result var21 = new Result(var8.getText(), var8.getRawBytes(), var16, BarcodeFormat.AZTEC);
      List var19 = var8.getByteSegments();
      if (var19 != null) {
         var21.putMetadata(ResultMetadataType.BYTE_SEGMENTS, var19);
      }

      String var20 = var8.getECLevel();
      if (var20 != null) {
         var21.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, var20);
      }

      return var21;
   }

   public void reset() {
   }
}
