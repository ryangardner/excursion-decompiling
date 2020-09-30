package com.sun.mail.handlers;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import myjava.awt.datatransfer.DataFlavor;

public class multipart_mixed implements DataContentHandler {
   private ActivationDataFlavor myDF = new ActivationDataFlavor(MimeMultipart.class, "multipart/mixed", "Multipart");

   public Object getContent(DataSource var1) throws IOException {
      try {
         MimeMultipart var5 = new MimeMultipart(var1);
         return var5;
      } catch (MessagingException var3) {
         IOException var4 = new IOException("Exception while constructing MimeMultipart");
         var4.initCause(var3);
         throw var4;
      }
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws IOException {
      return this.myDF.equals(var1) ? this.getContent(var2) : null;
   }

   public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{this.myDF};
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      if (var1 instanceof MimeMultipart) {
         try {
            ((MimeMultipart)var1).writeTo(var3);
         } catch (MessagingException var4) {
            throw new IOException(var4.toString());
         }
      }

   }
}
