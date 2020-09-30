package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
   private static final int MAX_DEPTH = 4;
   private static final int MIN_DIMENSION_TO_RECUR = 100;
   private final Reader delegate;

   public GenericMultipleBarcodeReader(Reader var1) {
      this.delegate = var1;
   }

   private void doDecodeMultiple(BinaryBitmap var1, Map<DecodeHintType, ?> var2, List<Result> var3, int var4, int var5, int var6) {
      if (var6 <= 4) {
         Result var7;
         try {
            var7 = this.delegate.decode(var1, var2);
         } catch (ReaderException var22) {
            return;
         }

         Iterator var8 = var3.iterator();

         boolean var9;
         while(true) {
            if (!var8.hasNext()) {
               var9 = false;
               break;
            }

            if (((Result)var8.next()).getText().equals(var7.getText())) {
               var9 = true;
               break;
            }
         }

         if (!var9) {
            var3.add(translateResultPoints(var7, var4, var5));
         }

         ResultPoint[] var24 = var7.getResultPoints();
         if (var24 != null && var24.length != 0) {
            int var10 = var1.getWidth();
            int var11 = var1.getHeight();
            float var12 = (float)var10;
            float var13 = (float)var11;
            int var14 = var24.length;
            float var15 = 0.0F;
            float var16 = 0.0F;

            float var19;
            int var25;
            for(var25 = 0; var25 < var14; var16 = var19) {
               ResultPoint var23 = var24[var25];
               float var17;
               float var18;
               if (var23 == null) {
                  var17 = var12;
                  var18 = var13;
                  var19 = var16;
               } else {
                  var19 = var23.getX();
                  float var20 = var23.getY();
                  float var21 = var12;
                  if (var19 < var12) {
                     var21 = var19;
                  }

                  var12 = var13;
                  if (var20 < var13) {
                     var12 = var20;
                  }

                  var13 = var15;
                  if (var19 > var15) {
                     var13 = var19;
                  }

                  var17 = var21;
                  var15 = var13;
                  var18 = var12;
                  var19 = var16;
                  if (var20 > var16) {
                     var19 = var20;
                     var18 = var12;
                     var15 = var13;
                     var17 = var21;
                  }
               }

               ++var25;
               var12 = var17;
               var13 = var18;
            }

            if (var12 > 100.0F) {
               this.doDecodeMultiple(var1.crop(0, 0, (int)var12, var11), var2, var3, var4, var5, var6 + 1);
            }

            if (var13 > 100.0F) {
               this.doDecodeMultiple(var1.crop(0, 0, var10, (int)var13), var2, var3, var4, var5, var6 + 1);
            }

            if (var15 < (float)(var10 - 100)) {
               var25 = (int)var15;
               this.doDecodeMultiple(var1.crop(var25, 0, var10 - var25, var11), var2, var3, var4 + var25, var5, var6 + 1);
            }

            if (var16 < (float)(var11 - 100)) {
               var25 = (int)var16;
               this.doDecodeMultiple(var1.crop(0, var25, var10, var11 - var25), var2, var3, var4, var5 + var25, var6 + 1);
            }
         }

      }
   }

   private static Result translateResultPoints(Result var0, int var1, int var2) {
      ResultPoint[] var3 = var0.getResultPoints();
      if (var3 == null) {
         return var0;
      } else {
         ResultPoint[] var4 = new ResultPoint[var3.length];

         for(int var5 = 0; var5 < var3.length; ++var5) {
            ResultPoint var6 = var3[var5];
            if (var6 != null) {
               var4[var5] = new ResultPoint(var6.getX() + (float)var1, var6.getY() + (float)var2);
            }
         }

         Result var7 = new Result(var0.getText(), var0.getRawBytes(), var4, var0.getBarcodeFormat());
         var7.putAllMetadata(var0.getResultMetadata());
         return var7;
      }
   }

   public Result[] decodeMultiple(BinaryBitmap var1) throws NotFoundException {
      return this.decodeMultiple(var1, (Map)null);
   }

   public Result[] decodeMultiple(BinaryBitmap var1, Map<DecodeHintType, ?> var2) throws NotFoundException {
      ArrayList var3 = new ArrayList();
      this.doDecodeMultiple(var1, var2, var3, 0, 0, 0);
      if (!var3.isEmpty()) {
         return (Result[])var3.toArray(new Result[var3.size()]);
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }
}
