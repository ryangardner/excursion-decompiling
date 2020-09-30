package com.sun.mail.handlers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import myjava.awt.datatransfer.DataFlavor;

public class text_plain implements DataContentHandler {
   private static ActivationDataFlavor myDF = new ActivationDataFlavor(String.class, "text/plain", "Text String");

   private String getCharset(String param1) {
      // $FF: Couldn't be decompiled
   }

   public Object getContent(DataSource var1) throws IOException {
      String var2 = null;

      String var3;
      boolean var10001;
      try {
         var3 = this.getCharset(var1.getContentType());
      } catch (IllegalArgumentException var59) {
         var10001 = false;
         throw new UnsupportedEncodingException(var2);
      }

      var2 = var3;

      InputStreamReader var64;
      try {
         var64 = new InputStreamReader(var1.getInputStream(), var3);
      } catch (IllegalArgumentException var58) {
         var10001 = false;
         throw new UnsupportedEncodingException(var2);
      }

      String var62;
      label494: {
         Throwable var10000;
         label495: {
            char[] var60;
            try {
               var60 = new char[1024];
            } catch (Throwable var57) {
               var10000 = var57;
               var10001 = false;
               break label495;
            }

            int var4 = 0;

            while(true) {
               int var5;
               try {
                  var5 = var64.read(var60, var4, var60.length - var4);
               } catch (Throwable var54) {
                  var10000 = var54;
                  var10001 = false;
                  break;
               }

               if (var5 == -1) {
                  try {
                     var62 = new String(var60, 0, var4);
                     break label494;
                  } catch (Throwable var53) {
                     var10000 = var53;
                     var10001 = false;
                     break;
                  }
               }

               var5 += var4;
               var4 = var5;

               try {
                  if (var5 < var60.length) {
                     continue;
                  }

                  var4 = var60.length;
               } catch (Throwable var56) {
                  var10000 = var56;
                  var10001 = false;
                  break;
               }

               if (var4 < 262144) {
                  var4 += var4;
               } else {
                  var4 += 262144;
               }

               char[] var63;
               try {
                  var63 = new char[var4];
                  System.arraycopy(var60, 0, var63, 0, var5);
               } catch (Throwable var55) {
                  var10000 = var55;
                  var10001 = false;
                  break;
               }

               var60 = var63;
               var4 = var5;
            }
         }

         Throwable var61 = var10000;

         try {
            var64.close();
         } catch (IOException var51) {
         }

         throw var61;
      }

      try {
         var64.close();
      } catch (IOException var52) {
      }

      return var62;
   }

   protected ActivationDataFlavor getDF() {
      return myDF;
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws IOException {
      return this.getDF().equals(var1) ? this.getContent(var2) : null;
   }

   public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{this.getDF()};
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      if (var1 instanceof String) {
         String var4 = null;

         boolean var10001;
         try {
            var2 = this.getCharset(var2);
         } catch (IllegalArgumentException var6) {
            var10001 = false;
            throw new UnsupportedEncodingException(var4);
         }

         var4 = var2;

         OutputStreamWriter var9;
         try {
            var9 = new OutputStreamWriter(var3, var2);
         } catch (IllegalArgumentException var5) {
            var10001 = false;
            throw new UnsupportedEncodingException(var4);
         }

         String var7 = (String)var1;
         var9.write(var7, 0, var7.length());
         var9.flush();
      } else {
         StringBuilder var8 = new StringBuilder("\"");
         var8.append(this.getDF().getMimeType());
         var8.append("\" DataContentHandler requires String object, ");
         var8.append("was given object of type ");
         var8.append(var1.getClass().toString());
         throw new IOException(var8.toString());
      }
   }
}
