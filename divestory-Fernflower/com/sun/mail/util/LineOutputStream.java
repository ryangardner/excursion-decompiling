package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import javax.mail.MessagingException;

public class LineOutputStream extends FilterOutputStream {
   private static byte[] newline;

   static {
      byte[] var0 = new byte[2];
      newline = var0;
      var0[0] = (byte)13;
      var0[1] = (byte)10;
   }

   public LineOutputStream(OutputStream var1) {
      super(var1);
   }

   public void writeln() throws MessagingException {
      try {
         this.out.write(newline);
      } catch (Exception var2) {
         throw new MessagingException("IOException", var2);
      }
   }

   public void writeln(String var1) throws MessagingException {
      try {
         byte[] var3 = ASCIIUtility.getBytes(var1);
         this.out.write(var3);
         this.out.write(newline);
      } catch (Exception var2) {
         throw new MessagingException("IOException", var2);
      }
   }
}
