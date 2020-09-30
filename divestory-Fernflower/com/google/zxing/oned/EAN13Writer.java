package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class EAN13Writer extends UPCEANWriter {
   private static final int CODE_WIDTH = 95;

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException {
      if (var2 == BarcodeFormat.EAN_13) {
         return super.encode(var1, var2, var3, var4, var5);
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Can only encode EAN_13, but got ");
         var6.append(var2);
         throw new IllegalArgumentException(var6.toString());
      }
   }

   public boolean[] encode(String var1) {
      if (var1.length() == 13) {
         boolean var10001;
         boolean var2;
         try {
            var2 = UPCEANReader.checkStandardUPCEANChecksum(var1);
         } catch (FormatException var11) {
            var10001 = false;
            throw new IllegalArgumentException("Illegal contents");
         }

         if (var2) {
            int var3 = Integer.parseInt(var1.substring(0, 1));
            int var4 = EAN13Reader.FIRST_DIGIT_ENCODINGS[var3];
            boolean[] var13 = new boolean[95];
            var3 = appendPattern(var13, 0, UPCEANReader.START_END_PATTERN, true) + 0;

            int var6;
            int var7;
            int var9;
            for(var6 = 1; var6 <= 6; var6 = var7) {
               var7 = var6 + 1;
               int var8 = Integer.parseInt(var1.substring(var6, var7));
               var9 = var8;
               if ((var4 >> 6 - var6 & 1) == 1) {
                  var9 = var8 + 10;
               }

               var3 += appendPattern(var13, var3, UPCEANReader.L_AND_G_PATTERNS[var9], false);
            }

            var3 += appendPattern(var13, var3, UPCEANReader.MIDDLE_PATTERN, false);

            for(var6 = 7; var6 <= 12; var6 = var9) {
               var9 = var6 + 1;
               var6 = Integer.parseInt(var1.substring(var6, var9));
               var3 += appendPattern(var13, var3, UPCEANReader.L_PATTERNS[var6], true);
            }

            appendPattern(var13, var3, UPCEANReader.START_END_PATTERN, true);
            return var13;
         } else {
            try {
               IllegalArgumentException var12 = new IllegalArgumentException("Contents do not pass checksum");
               throw var12;
            } catch (FormatException var10) {
               var10001 = false;
               throw new IllegalArgumentException("Illegal contents");
            }
         }
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Requested contents should be 13 digits long, but got ");
         var5.append(var1.length());
         throw new IllegalArgumentException(var5.toString());
      }
   }
}
