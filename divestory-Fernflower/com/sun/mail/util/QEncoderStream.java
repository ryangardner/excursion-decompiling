package com.sun.mail.util;

import java.io.IOException;
import java.io.OutputStream;

public class QEncoderStream extends QPEncoderStream {
   private static String TEXT_SPECIALS;
   private static String WORD_SPECIALS;
   private String specials;

   public QEncoderStream(OutputStream var1, boolean var2) {
      super(var1, Integer.MAX_VALUE);
      String var3;
      if (var2) {
         var3 = WORD_SPECIALS;
      } else {
         var3 = TEXT_SPECIALS;
      }

      this.specials = var3;
   }

   public static int encodedLength(byte[] var0, boolean var1) {
      String var2;
      if (var1) {
         var2 = WORD_SPECIALS;
      } else {
         var2 = TEXT_SPECIALS;
      }

      int var3 = 0;

      int var4;
      for(var4 = 0; var3 < var0.length; ++var3) {
         int var5 = var0[var3] & 255;
         if (var5 >= 32 && var5 < 127 && var2.indexOf(var5) < 0) {
            ++var4;
         } else {
            var4 += 3;
         }
      }

      return var4;
   }

   public void write(int var1) throws IOException {
      var1 &= 255;
      if (var1 == 32) {
         this.output(95, false);
      } else if (var1 >= 32 && var1 < 127 && this.specials.indexOf(var1) < 0) {
         this.output(var1, false);
      } else {
         this.output(var1, true);
      }

   }
}
