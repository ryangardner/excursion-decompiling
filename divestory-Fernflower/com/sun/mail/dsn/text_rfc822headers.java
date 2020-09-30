package com.sun.mail.dsn;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import myjava.awt.datatransfer.DataFlavor;

public class text_rfc822headers implements DataContentHandler {
   private static ActivationDataFlavor myDF = new ActivationDataFlavor(MessageHeaders.class, "text/rfc822-headers", "RFC822 headers");
   private static ActivationDataFlavor myDFs = new ActivationDataFlavor(String.class, "text/rfc822-headers", "RFC822 headers");

   private String getCharset(String param1) {
      // $FF: Couldn't be decompiled
   }

   private Object getStringContent(DataSource var1) throws IOException {
      String var2 = null;

      boolean var10001;
      String var3;
      try {
         var3 = this.getCharset(var1.getContentType());
      } catch (IllegalArgumentException var7) {
         var10001 = false;
         throw new UnsupportedEncodingException(var2);
      }

      var2 = var3;

      InputStreamReader var10;
      try {
         var10 = new InputStreamReader(var1.getInputStream(), var3);
      } catch (IllegalArgumentException var6) {
         var10001 = false;
         throw new UnsupportedEncodingException(var2);
      }

      char[] var8 = new char[1024];
      int var4 = 0;

      while(true) {
         int var5 = var10.read(var8, var4, var8.length - var4);
         if (var5 == -1) {
            return new String(var8, 0, var4);
         }

         var5 += var4;
         var4 = var5;
         if (var5 >= var8.length) {
            var4 = var8.length;
            if (var4 < 262144) {
               var4 += var4;
            } else {
               var4 += 262144;
            }

            char[] var9 = new char[var4];
            System.arraycopy(var8, 0, var9, 0, var5);
            var8 = var9;
            var4 = var5;
         }
      }
   }

   public Object getContent(DataSource var1) throws IOException {
      try {
         MessageHeaders var4 = new MessageHeaders(var1.getInputStream());
         return var4;
      } catch (MessagingException var3) {
         StringBuilder var2 = new StringBuilder("Exception creating MessageHeaders: ");
         var2.append(var3);
         throw new IOException(var2.toString());
      }
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws IOException {
      if (myDF.equals(var1)) {
         return this.getContent(var2);
      } else {
         return myDFs.equals(var1) ? this.getStringContent(var2) : null;
      }
   }

   public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{myDF, myDFs};
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      StringBuilder var10;
      if (var1 instanceof MessageHeaders) {
         MessageHeaders var9 = (MessageHeaders)var1;

         try {
            var9.writeTo(var3);
         } catch (MessagingException var5) {
            Exception var12 = var5.getNextException();
            if (var12 instanceof IOException) {
               throw (IOException)var12;
            } else {
               var10 = new StringBuilder("Exception writing headers: ");
               var10.append(var5);
               throw new IOException(var10.toString());
            }
         }
      } else if (var1 instanceof String) {
         String var4 = null;

         boolean var10001;
         try {
            var2 = this.getCharset(var2);
         } catch (IllegalArgumentException var7) {
            var10001 = false;
            throw new UnsupportedEncodingException(var4);
         }

         var4 = var2;

         OutputStreamWriter var11;
         try {
            var11 = new OutputStreamWriter(var3, var2);
         } catch (IllegalArgumentException var6) {
            var10001 = false;
            throw new UnsupportedEncodingException(var4);
         }

         String var8 = (String)var1;
         var11.write(var8, 0, var8.length());
         var11.flush();
      } else {
         var10 = new StringBuilder("\"");
         var10.append(myDFs.getMimeType());
         var10.append("\" DataContentHandler requires String object, ");
         var10.append("was given object of type ");
         var10.append(var1.getClass().toString());
         throw new IOException(var10.toString());
      }
   }
}
