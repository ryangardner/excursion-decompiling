package com.sun.mail.smtp;

import javax.mail.Address;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;

public class SMTPSendFailedException extends SendFailedException {
   private static final long serialVersionUID = 8049122628728932894L;
   protected InternetAddress addr;
   protected String cmd;
   protected int rc;

   public SMTPSendFailedException(String var1, int var2, String var3, Exception var4, Address[] var5, Address[] var6, Address[] var7) {
      super(var3, var4, var5, var6, var7);
      this.cmd = var1;
      this.rc = var2;
   }

   public String getCommand() {
      return this.cmd;
   }

   public int getReturnCode() {
      return this.rc;
   }
}
