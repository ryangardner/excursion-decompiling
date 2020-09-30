package com.sun.mail.smtp;

import java.io.InputStream;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class SMTPMessage extends MimeMessage {
   public static final int NOTIFY_DELAY = 4;
   public static final int NOTIFY_FAILURE = 2;
   public static final int NOTIFY_NEVER = -1;
   public static final int NOTIFY_SUCCESS = 1;
   public static final int RETURN_FULL = 1;
   public static final int RETURN_HDRS = 2;
   private static final String[] returnOptionString;
   private boolean allow8bitMIME = false;
   private String envelopeFrom;
   private String extension = null;
   private int notifyOptions = 0;
   private int returnOption = 0;
   private boolean sendPartial = false;
   private String submitter = null;

   static {
      String[] var0 = new String[]{null, "FULL", "HDRS"};
      returnOptionString = var0;
   }

   public SMTPMessage(Session var1) {
      super(var1);
   }

   public SMTPMessage(Session var1, InputStream var2) throws MessagingException {
      super(var1, var2);
   }

   public SMTPMessage(MimeMessage var1) throws MessagingException {
      super(var1);
   }

   public boolean getAllow8bitMIME() {
      return this.allow8bitMIME;
   }

   String getDSNNotify() {
      int var1 = this.notifyOptions;
      if (var1 == 0) {
         return null;
      } else if (var1 == -1) {
         return "NEVER";
      } else {
         StringBuffer var2 = new StringBuffer();
         if ((this.notifyOptions & 1) != 0) {
            var2.append("SUCCESS");
         }

         if ((this.notifyOptions & 2) != 0) {
            if (var2.length() != 0) {
               var2.append(',');
            }

            var2.append("FAILURE");
         }

         if ((this.notifyOptions & 4) != 0) {
            if (var2.length() != 0) {
               var2.append(',');
            }

            var2.append("DELAY");
         }

         return var2.toString();
      }
   }

   String getDSNRet() {
      return returnOptionString[this.returnOption];
   }

   public String getEnvelopeFrom() {
      return this.envelopeFrom;
   }

   public String getMailExtension() {
      return this.extension;
   }

   public int getNotifyOptions() {
      return this.notifyOptions;
   }

   public int getReturnOption() {
      return this.returnOption;
   }

   public boolean getSendPartial() {
      return this.sendPartial;
   }

   public String getSubmitter() {
      return this.submitter;
   }

   public void setAllow8bitMIME(boolean var1) {
      this.allow8bitMIME = var1;
   }

   public void setEnvelopeFrom(String var1) {
      this.envelopeFrom = var1;
   }

   public void setMailExtension(String var1) {
      this.extension = var1;
   }

   public void setNotifyOptions(int var1) {
      if (var1 >= -1 && var1 < 8) {
         this.notifyOptions = var1;
      } else {
         throw new IllegalArgumentException("Bad return option");
      }
   }

   public void setReturnOption(int var1) {
      if (var1 >= 0 && var1 <= 2) {
         this.returnOption = var1;
      } else {
         throw new IllegalArgumentException("Bad return option");
      }
   }

   public void setSendPartial(boolean var1) {
      this.sendPartial = var1;
   }

   public void setSubmitter(String var1) {
      this.submitter = var1;
   }
}
