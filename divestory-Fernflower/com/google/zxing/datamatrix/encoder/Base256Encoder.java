package com.google.zxing.datamatrix.encoder;

final class Base256Encoder implements Encoder {
   private static char randomize255State(char var0, int var1) {
      int var2 = var0 + var1 * 149 % 255 + 1;
      return var2 <= 255 ? (char)var2 : (char)(var2 - 256);
   }

   public void encode(EncoderContext var1) {
      StringBuilder var2 = new StringBuilder();
      byte var3 = 0;
      var2.append('\u0000');

      int var4;
      while(var1.hasMoreCharacters()) {
         var2.append(var1.getCurrentChar());
         ++var1.pos;
         var4 = HighLevelEncoder.lookAheadTest(var1.getMessage(), var1.pos, this.getEncodingMode());
         if (var4 != this.getEncodingMode()) {
            var1.signalEncoderChange(var4);
            break;
         }
      }

      int var5 = var2.length() - 1;
      var4 = var1.getCodewordCount() + var5 + 1;
      var1.updateSymbolInfo(var4);
      boolean var7;
      if (var1.getSymbolInfo().getDataCapacity() - var4 > 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var1.hasMoreCharacters() || var7) {
         if (var5 <= 249) {
            var2.setCharAt(0, (char)var5);
         } else {
            if (var5 <= 249 || var5 > 1555) {
               StringBuilder var6 = new StringBuilder();
               var6.append("Message length not in valid ranges: ");
               var6.append(var5);
               throw new IllegalStateException(var6.toString());
            }

            var2.setCharAt(0, (char)(var5 / 250 + 249));
            var2.insert(1, (char)(var5 % 250));
         }
      }

      var5 = var2.length();

      for(var4 = var3; var4 < var5; ++var4) {
         var1.writeCodeword(randomize255State(var2.charAt(var4), var1.getCodewordCount() + 1));
      }

   }

   public int getEncodingMode() {
      return 5;
   }
}
