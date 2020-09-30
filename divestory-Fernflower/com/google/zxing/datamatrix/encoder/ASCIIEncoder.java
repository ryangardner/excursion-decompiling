package com.google.zxing.datamatrix.encoder;

final class ASCIIEncoder implements Encoder {
   private static char encodeASCIIDigits(char var0, char var1) {
      if (HighLevelEncoder.isDigit(var0) && HighLevelEncoder.isDigit(var1)) {
         return (char)((var0 - 48) * 10 + (var1 - 48) + 130);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("not digits: ");
         var2.append(var0);
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void encode(EncoderContext var1) {
      if (HighLevelEncoder.determineConsecutiveDigitCount(var1.getMessage(), var1.pos) >= 2) {
         var1.writeCodeword(encodeASCIIDigits(var1.getMessage().charAt(var1.pos), var1.getMessage().charAt(var1.pos + 1)));
         var1.pos += 2;
      } else {
         char var2 = var1.getCurrentChar();
         int var3 = HighLevelEncoder.lookAheadTest(var1.getMessage(), var1.pos, this.getEncodingMode());
         if (var3 != this.getEncodingMode()) {
            if (var3 == 1) {
               var1.writeCodeword('æ');
               var1.signalEncoderChange(1);
               return;
            }

            if (var3 != 2) {
               if (var3 != 3) {
                  if (var3 != 4) {
                     if (var3 == 5) {
                        var1.writeCodeword('ç');
                        var1.signalEncoderChange(5);
                        return;
                     }

                     StringBuilder var4 = new StringBuilder();
                     var4.append("Illegal mode: ");
                     var4.append(var3);
                     throw new IllegalStateException(var4.toString());
                  }

                  var1.writeCodeword('ð');
                  var1.signalEncoderChange(4);
               } else {
                  var1.writeCodeword('î');
                  var1.signalEncoderChange(3);
               }
            } else {
               var1.writeCodeword('ï');
               var1.signalEncoderChange(2);
            }
         } else if (HighLevelEncoder.isExtendedASCII(var2)) {
            var1.writeCodeword('ë');
            var1.writeCodeword((char)(var2 - 128 + 1));
            ++var1.pos;
         } else {
            var1.writeCodeword((char)(var2 + 1));
            ++var1.pos;
         }
      }

   }

   public int getEncodingMode() {
      return 0;
   }
}
