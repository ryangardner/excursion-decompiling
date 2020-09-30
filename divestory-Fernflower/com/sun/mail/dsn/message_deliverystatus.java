package com.sun.mail.dsn;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import myjava.awt.datatransfer.DataFlavor;

public class message_deliverystatus implements DataContentHandler {
   ActivationDataFlavor ourDataFlavor = new ActivationDataFlavor(DeliveryStatus.class, "message/delivery-status", "Delivery Status");

   public Object getContent(DataSource var1) throws IOException {
      try {
         DeliveryStatus var5 = new DeliveryStatus(var1.getInputStream());
         return var5;
      } catch (MessagingException var3) {
         StringBuilder var4 = new StringBuilder("Exception creating DeliveryStatus in message/devliery-status DataContentHandler: ");
         var4.append(var3.toString());
         throw new IOException(var4.toString());
      }
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws IOException {
      return this.ourDataFlavor.equals(var1) ? this.getContent(var2) : null;
   }

   public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{this.ourDataFlavor};
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      if (var1 instanceof DeliveryStatus) {
         DeliveryStatus var5 = (DeliveryStatus)var1;

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
