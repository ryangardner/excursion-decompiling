package com.sun.mail.imap;

import com.sun.mail.iap.Literal;
import com.sun.mail.util.CRLFOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.mail.Message;
import javax.mail.MessagingException;

class MessageLiteral implements Literal {
   private byte[] buf;
   private Message msg;
   private int msgSize = -1;

   public MessageLiteral(Message var1, int var2) throws MessagingException, IOException {
      this.msg = var1;
      LengthCounter var3 = new LengthCounter(var2);
      CRLFOutputStream var4 = new CRLFOutputStream(var3);
      var1.writeTo(var4);
      var4.flush();
      this.msgSize = var3.getSize();
      this.buf = var3.getBytes();
   }

   public int size() {
      return this.msgSize;
   }

   public void writeTo(OutputStream var1) throws IOException {
      try {
         if (this.buf != null) {
            var1.write(this.buf, 0, this.msgSize);
         } else {
            CRLFOutputStream var4 = new CRLFOutputStream(var1);
            this.msg.writeTo(var4);
         }

      } catch (MessagingException var3) {
         StringBuilder var2 = new StringBuilder("MessagingException while appending message: ");
         var2.append(var3);
         throw new IOException(var2.toString());
      }
   }
}
