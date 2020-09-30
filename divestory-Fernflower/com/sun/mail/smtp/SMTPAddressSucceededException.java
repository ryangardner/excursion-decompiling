package com.sun.mail.smtp;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public class SMTPAddressSucceededException extends MessagingException {
   private static final long serialVersionUID = -1168335848623096749L;
   protected InternetAddress addr;
   protected String cmd;
   protected int rc;

   public SMTPAddressSucceededException(InternetAddress var1, String var2, int var3, String var4) {
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
