package com.sun.mail.smtp;

import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;

public class SMTPAddressFailedException extends SendFailedException {
   private static final long serialVersionUID = 804831199768630097L;
   protected InternetAddress addr;
   protected String cmd;
   protected int rc;

   public SMTPAddressFailedException(InternetAddress var1, String var2, int var3, String var4) {
      super(var4);
      this.addr = var1;
      this.cmd = var2;
      this.rc = var3;
   }

   public InternetAddress getAddress() {
      return this.addr;
   }

   public String getCommand() {
      return this.cmd;
   }

   public int getReturnCode() {
      return this.rc;
   }
}
