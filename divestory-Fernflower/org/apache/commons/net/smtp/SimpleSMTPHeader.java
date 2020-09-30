package org.apache.commons.net.smtp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SimpleSMTPHeader {
   private StringBuffer __cc;
   private final String __from;
   private final StringBuffer __headerFields;
   private final String __subject;
   private final String __to;
   private boolean hasHeaderDate;

   public SimpleSMTPHeader(String var1, String var2, String var3) {
      if (var1 != null) {
         this.__to = var2;
         this.__from = var1;
         this.__subject = var3;
         this.__headerFields = new StringBuffer();
         this.__cc = null;
      } else {
         throw new IllegalArgumentException("From cannot be null");
      }
   }

   public void addCC(String var1) {
      StringBuffer var2 = this.__cc;
      if (var2 == null) {
         this.__cc = new StringBuffer();
      } else {
         var2.append(", ");
      }

      this.__cc.append(var1);
   }

   public void addHeaderField(String var1, String var2) {
      if (!this.hasHeaderDate && "Date".equals(var1)) {
         this.hasHeaderDate = true;
      }

      this.__headerFields.append(var1);
      this.__headerFields.append(": ");
      this.__headerFields.append(var2);
      this.__headerFields.append('\n');
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      SimpleDateFormat var2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
      if (!this.hasHeaderDate) {
         this.addHeaderField("Date", var2.format(new Date()));
      }

      if (this.__headerFields.length() > 0) {
         var1.append(this.__headerFields.toString());
      }

      var1.append("From: ");
      var1.append(this.__from);
      var1.append("\n");
      if (this.__to != null) {
         var1.append("To: ");
         var1.append(this.__to);
         var1.append("\n");
      }

      if (this.__cc != null) {
         var1.append("Cc: ");
         var1.append(this.__cc.toString());
         var1.append("\n");
      }

      if (this.__subject != null) {
         var1.append("Subject: ");
         var1.append(this.__subject);
         var1.append("\n");
      }

      var1.append('\n');
      return var1.toString();
   }
}
