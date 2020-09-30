package com.sun.mail.imap;

public class AppendUID {
   public long uid = -1L;
   public long uidvalidity = -1L;

   public AppendUID(long var1, long var3) {
      this.uidvalidity = var1;
      this.uid = var3;
   }
}
