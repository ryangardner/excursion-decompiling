package com.sun.mail.util;

import java.io.IOException;
import java.io.InputStream;

public class QDecoderStream extends QPDecoderStream {
   public QDecoderStream(InputStream var1) {
      super(var1);
   }

   public int read() throws IOException {
      int var1 = this.in.read();
      if (var1 == 95) {
         return 32;
      } else if (var1 == 61) {
         this.ba[0] = (byte)((byte)this.in.read());
         this.ba[1] = (byte)((byte)this.in.read());

         try {
            var1 = ASCIIUtility.parseInt(this.ba, 0, 2, 16);
            return var1;
         } catch (NumberFormatException var4) {
            StringBuilder var3 = new StringBuilder("Error in QP stream ");
            var3.append(var4.getMessage());
            throw new IOException(var3.toString());
         }
      } else {
         return var1;
      }
   }
}
