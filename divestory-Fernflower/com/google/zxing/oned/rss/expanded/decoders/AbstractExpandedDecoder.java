package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

public abstract class AbstractExpandedDecoder {
   private final GeneralAppIdDecoder generalDecoder;
   private final BitArray information;

   AbstractExpandedDecoder(BitArray var1) {
      this.information = var1;
      this.generalDecoder = new GeneralAppIdDecoder(var1);
   }

   public static AbstractExpandedDecoder createDecoder(BitArray var0) {
      if (var0.get(1)) {
         return new AI01AndOtherAIs(var0);
      } else if (!var0.get(2)) {
         return new AnyAIDecoder(var0);
      } else {
         int var1 = GeneralAppIdDecoder.extractNumericValueFromBitArray(var0, 1, 4);
         if (var1 != 4) {
            if (var1 != 5) {
               var1 = GeneralAppIdDecoder.extractNumericValueFromBitArray(var0, 1, 5);
               if (var1 != 12) {
                  if (var1 != 13) {
                     switch(GeneralAppIdDecoder.extractNumericValueFromBitArray(var0, 1, 7)) {
                     case 56:
                        return new AI013x0x1xDecoder(var0, "310", "11");
                     case 57:
                        return new AI013x0x1xDecoder(var0, "320", "11");
                     case 58:
                        return new AI013x0x1xDecoder(var0, "310", "13");
                     case 59:
                        return new AI013x0x1xDecoder(var0, "320", "13");
                     case 60:
                        return new AI013x0x1xDecoder(var0, "310", "15");
                     case 61:
                        return new AI013x0x1xDecoder(var0, "320", "15");
                     case 62:
                        return new AI013x0x1xDecoder(var0, "310", "17");
                     case 63:
                        return new AI013x0x1xDecoder(var0, "320", "17");
                     default:
                        StringBuilder var2 = new StringBuilder();
                        var2.append("unknown decoder: ");
                        var2.append(var0);
                        throw new IllegalStateException(var2.toString());
                     }
                  } else {
                     return new AI01393xDecoder(var0);
                  }
               } else {
                  return new AI01392xDecoder(var0);
               }
            } else {
               return new AI01320xDecoder(var0);
            }
         } else {
            return new AI013103decoder(var0);
         }
      }
   }

   protected final GeneralAppIdDecoder getGeneralDecoder() {
      return this.generalDecoder;
   }

   protected final BitArray getInformation() {
      return this.information;
   }

   public abstract String parseInformation() throws NotFoundException, FormatException;
}
