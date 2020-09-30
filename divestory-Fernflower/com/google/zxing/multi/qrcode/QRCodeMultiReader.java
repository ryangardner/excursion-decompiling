package com.google.zxing.multi.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class QRCodeMultiReader extends QRCodeReader implements MultipleBarcodeReader {
   private static final Result[] EMPTY_RESULT_ARRAY = new Result[0];
   private static final ResultPoint[] NO_POINTS = new ResultPoint[0];

   private static List<Result> processStructuredAppend(List<Result> var0) {
      Iterator var1 = var0.iterator();

      boolean var2;
      while(true) {
         if (var1.hasNext()) {
            if (!((Result)var1.next()).getResultMetadata().containsKey(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) {
               continue;
            }

            var2 = true;
            break;
         }

         var2 = false;
         break;
      }

      if (!var2) {
         return var0;
      } else {
         ArrayList var14 = new ArrayList();
         ArrayList var3 = new ArrayList();
         Iterator var4 = var0.iterator();

         Result var12;
         while(var4.hasNext()) {
            var12 = (Result)var4.next();
            var14.add(var12);
            if (var12.getResultMetadata().containsKey(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) {
               var3.add(var12);
            }
         }

         Collections.sort(var3, new QRCodeMultiReader.SAComparator());
         StringBuilder var13 = new StringBuilder();
         var4 = var3.iterator();
         int var5 = 0;
         int var15 = 0;

         while(true) {
            Result var6;
            int var7;
            int var8;
            do {
               if (!var4.hasNext()) {
                  byte[] var19 = new byte[var5];
                  byte[] var17 = new byte[var15];
                  Iterator var16 = var3.iterator();
                  var7 = 0;
                  var8 = 0;

                  while(true) {
                     Result var9;
                     int var10;
                     do {
                        if (!var16.hasNext()) {
                           var12 = new Result(var13.toString(), var19, NO_POINTS, BarcodeFormat.QR_CODE);
                           if (var15 > 0) {
                              var3 = new ArrayList();
                              var3.add(var17);
                              var12.putMetadata(ResultMetadataType.BYTE_SEGMENTS, var3);
                           }

                           var14.add(var12);
                           return var14;
                        }

                        var9 = (Result)var16.next();
                        System.arraycopy(var9.getRawBytes(), 0, var19, var7, var9.getRawBytes().length);
                        var10 = var7 + var9.getRawBytes().length;
                        var7 = var10;
                     } while(!var9.getResultMetadata().containsKey(ResultMetadataType.BYTE_SEGMENTS));

                     Iterator var20 = ((Iterable)var9.getResultMetadata().get(ResultMetadataType.BYTE_SEGMENTS)).iterator();
                     var5 = var8;

                     while(true) {
                        var7 = var10;
                        var8 = var5;
                        if (!var20.hasNext()) {
                           break;
                        }

                        byte[] var11 = (byte[])var20.next();
                        System.arraycopy(var11, 0, var17, var5, var11.length);
                        var5 += var11.length;
                     }
                  }
               }

               var6 = (Result)var4.next();
               var13.append(var6.getText());
               var7 = var5 + var6.getRawBytes().length;
               var5 = var7;
            } while(!var6.getResultMetadata().containsKey(ResultMetadataType.BYTE_SEGMENTS));

            Iterator var18 = ((Iterable)var6.getResultMetadata().get(ResultMetadataType.BYTE_SEGMENTS)).iterator();
            var8 = var15;

            while(true) {
               var5 = var7;
               var15 = var8;
               if (!var18.hasNext()) {
                  break;
               }

               var8 += ((byte[])var18.next()).length;
            }
         }
      }
   }

   public Result[] decodeMultiple(BinaryBitmap var1) throws NotFoundException {
      return this.decodeMultiple(var1, (Map)null);
   }

   public Result[] decodeMultiple(BinaryBitmap var1, Map<DecodeHintType, ?> var2) throws NotFoundException {
      ArrayList var3 = new ArrayList();
      DetectorResult[] var16 = (new MultiDetector(var1.getBlackMatrix())).detectMulti(var2);
      int var4 = var16.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         DetectorResult var6 = var16[var5];

         DecoderResult var7;
         ResultPoint[] var8;
         boolean var10001;
         try {
            var7 = this.getDecoder().decode(var6.getBits(), var2);
            var8 = var6.getPoints();
            if (var7.getOther() instanceof QRCodeDecoderMetaData) {
               ((QRCodeDecoderMetaData)var7.getOther()).applyMirroredCorrection(var8);
            }
         } catch (ReaderException var15) {
            var10001 = false;
            continue;
         }

         Result var18;
         List var19;
         try {
            var18 = new Result(var7.getText(), var7.getRawBytes(), var8, BarcodeFormat.QR_CODE);
            var19 = var7.getByteSegments();
         } catch (ReaderException var14) {
            var10001 = false;
            continue;
         }

         if (var19 != null) {
            try {
               var18.putMetadata(ResultMetadataType.BYTE_SEGMENTS, var19);
            } catch (ReaderException var13) {
               var10001 = false;
               continue;
            }
         }

         String var20;
         try {
            var20 = var7.getECLevel();
         } catch (ReaderException var12) {
            var10001 = false;
            continue;
         }

         if (var20 != null) {
            try {
               var18.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, var20);
            } catch (ReaderException var11) {
               var10001 = false;
               continue;
            }
         }

         try {
            if (var7.hasStructuredAppend()) {
               var18.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, var7.getStructuredAppendSequenceNumber());
               var18.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, var7.getStructuredAppendParity());
            }
         } catch (ReaderException var10) {
            var10001 = false;
            continue;
         }

         try {
            var3.add(var18);
         } catch (ReaderException var9) {
            var10001 = false;
         }
      }

      if (var3.isEmpty()) {
         return EMPTY_RESULT_ARRAY;
      } else {
         List var17 = processStructuredAppend(var3);
         return (Result[])var17.toArray(new Result[var17.size()]);
      }
   }

   private static final class SAComparator implements Comparator<Result>, Serializable {
      private SAComparator() {
      }

      // $FF: synthetic method
      SAComparator(Object var1) {
         this();
      }

      public int compare(Result var1, Result var2) {
         int var3 = (Integer)var1.getResultMetadata().get(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE);
         int var4 = (Integer)var2.getResultMetadata().get(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE);
         if (var3 < var4) {
            return -1;
         } else {
            return var3 > var4 ? 1 : 0;
         }
      }
   }
}
