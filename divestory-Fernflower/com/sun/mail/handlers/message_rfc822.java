package com.sun.mail.handlers;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import myjava.awt.datatransfer.DataFlavor;

public class message_rfc822 implements DataContentHandler {
   ActivationDataFlavor ourDataFlavor = new ActivationDataFlavor(Message.class, "message/rfc822", "Message");

   public Object getContent(DataSource param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws IOException {
      return this.ourDataFlavor.equals(var1) ? this.getContent(var2) : null;
   }

   public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{this.ourDataFlavor};
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      if (var1 instanceof Message) {
         Message var5 = (Message)var1;

         try {
            var5.writeTo(var3);
         } catch (MessagingException var4) {
            throw new IOException(var4.toString());
         }
      } else {
         throw new IOException("unsupported object");
      }
   }
}
