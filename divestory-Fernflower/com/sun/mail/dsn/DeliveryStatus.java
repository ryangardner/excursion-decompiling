package com.sun.mail.dsn;

import com.sun.mail.util.LineOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;

public class DeliveryStatus {
   private static boolean debug;
   protected InternetHeaders messageDSN;
   protected InternetHeaders[] recipientDSN;

   static {
      label36: {
         String var0;
         boolean var10001;
         try {
            var0 = System.getProperty("mail.dsn.debug");
         } catch (SecurityException var4) {
            var10001 = false;
            break label36;
         }

         boolean var1;
         label29: {
            label28: {
               if (var0 != null) {
                  try {
                     if (!var0.equalsIgnoreCase("false")) {
                        break label28;
                     }
                  } catch (SecurityException var3) {
                     var10001 = false;
                     break label36;
                  }
               }

               var1 = false;
               break label29;
            }

            var1 = true;
         }

         try {
            debug = var1;
         } catch (SecurityException var2) {
            var10001 = false;
         }
      }

   }

   public DeliveryStatus() throws MessagingException {
      this.messageDSN = new InternetHeaders();
      this.recipientDSN = new InternetHeaders[0];
   }

   public DeliveryStatus(InputStream var1) throws MessagingException, IOException {
      this.messageDSN = new InternetHeaders(var1);
      if (debug) {
         System.out.println("DSN: got messageDSN");
      }

      Vector var2 = new Vector();

      while(true) {
         try {
            if (var1.available() <= 0) {
               break;
            }

            InternetHeaders var3 = new InternetHeaders(var1);
            if (debug) {
               System.out.println("DSN: got recipientDSN");
            }

            var2.addElement(var3);
         } catch (EOFException var4) {
            if (debug) {
               System.out.println("DSN: got EOFException");
            }
            break;
         }
      }

      if (debug) {
         PrintStream var5 = System.out;
         StringBuilder var7 = new StringBuilder("DSN: recipientDSN size ");
         var7.append(var2.size());
         var5.println(var7.toString());
      }

      InternetHeaders[] var6 = new InternetHeaders[var2.size()];
      this.recipientDSN = var6;
      var2.copyInto(var6);
   }

   private static void writeInternetHeaders(InternetHeaders var0, LineOutputStream var1) throws IOException {
      Enumeration var3 = var0.getAllHeaderLines();

      while(true) {
         try {
            if (!var3.hasMoreElements()) {
               return;
            }

            var1.writeln((String)var3.nextElement());
         } catch (MessagingException var2) {
            Exception var4 = var2.getNextException();
            if (var4 instanceof IOException) {
               throw (IOException)var4;
            }

            StringBuilder var5 = new StringBuilder("Exception writing headers: ");
            var5.append(var2);
            throw new IOException(var5.toString());
         }
      }
   }

   public void addRecipientDSN(InternetHeaders var1) {
      InternetHeaders[] var2 = this.recipientDSN;
      InternetHeaders[] var3 = new InternetHeaders[var2.length + 1];
      System.arraycopy(var2, 0, var3, 0, var2.length);
      this.recipientDSN = var3;
      var3[var3.length - 1] = var1;
   }

   public InternetHeaders getMessageDSN() {
      return this.messageDSN;
   }

   public InternetHeaders getRecipientDSN(int var1) {
      return this.recipientDSN[var1];
   }

   public int getRecipientDSNCount() {
      return this.recipientDSN.length;
   }

   public void setMessageDSN(InternetHeaders var1) {
      this.messageDSN = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder("DeliveryStatus: Reporting-MTA=");
      var1.append(this.messageDSN.getHeader("Reporting-MTA", (String)null));
      var1.append(", #Recipients=");
      var1.append(this.recipientDSN.length);
      return var1.toString();
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      LineOutputStream var4;
      if (var1 instanceof LineOutputStream) {
         var4 = (LineOutputStream)var1;
      } else {
         var4 = new LineOutputStream(var1);
      }

      writeInternetHeaders(this.messageDSN, var4);
      var4.writeln();
      int var2 = 0;

      while(true) {
         InternetHeaders[] var3 = this.recipientDSN;
         if (var2 >= var3.length) {
            return;
         }

         writeInternetHeaders(var3[var2], var4);
         var4.writeln();
         ++var2;
      }
   }
}
