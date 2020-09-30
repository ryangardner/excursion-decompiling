package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class EAN8Writer extends UPCEANWriter {
   private static final int CODE_WIDTH = 67;

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException {
      if (var2 == BarcodeFormat.EAN_8) {
         return super.encode(var1, var2, var3, var4, var5);
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Can only encode EAN_8, but got ");
         var6.append(var2);
         throw new IllegalArgumentException(var6.toString());
      }
   }

   public boolean[] encode(String var1) {
      if (var1.length() != 8) {
         StringBuilder var6 = new StringBuilder();
         var6.append("Requested contents should be 8 digits long, but got ");
         var6.append(var1.length());
         throw new IllegalArgumentException(var6.toString());
      } else {
         boolean[] var2 = new boolean[67];
         int var3 = appendPattern(var2, 0, UPCEANReader.START_END_PATTERN, true) + 0;

         int var4;
         int var5;
         for(var4 = 0; var4 <= 3; var4 = var5) {
            var5 = var4 + 1;
            var4 = Integer.parseInt(var1.substring(var4, var5));
            var3 += appendPattern(var2, var3, UPCEANReader.L_PATTERNS[var4], false);
         }

         var3 += appendPattern(var2, var3, UPCEANReader.MIDDLE_PATTERN, false);

         for(var4 = 4; var4 <= 7; var4 = var5) {
            var5 = var4 + 1;
            var4 = Integer.parseInt(var1.substring(var4, var5));
            var3 += appendPattern(var2, var3, UPCEANReader.L_PATTERNS[var4], true);
         }

         appendPattern(var2, var3, UPCEANReader.START_END_PATTERN, true);
         return var2;
      }
   }
}
