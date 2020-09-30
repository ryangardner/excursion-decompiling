package com.sun.mail.dsn;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import myjava.awt.datatransfer.DataFlavor;

public class multipart_report implements DataContentHandler {
   private ActivationDataFlavor myDF = new ActivationDataFlavor(MultipartReport.class, "multipart/report", "Multipart Report");

   public Object getContent(DataSource var1) throws IOException {
      try {
         MultipartReport var5 = new MultipartReport(var1);
         return var5;
      } catch (MessagingException var3) {
         IOException var4 = new IOException("Exception while constructing MultipartReport");
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
      if (var1 instanceof MultipartReport) {
         try {
            ((MultipartReport)var1).writeTo(var3);
         } catch (MessagingException var4) {
            throw new IOException(var4.toString());
         }
      }

   }
}
