package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;

public class Status {
   static final String[] standardItems = new String[]{"MESSAGES", "RECENT", "UNSEEN", "UIDNEXT", "UIDVALIDITY"};
   public String mbox = null;
   public int recent = -1;
   public int total = -1;
   public long uidnext = -1L;
   public long uidvalidity = -1L;
   public int unseen = -1;

   public Status(Response var1) throws ParsingException {
      this.mbox = var1.readAtomString();
      var1.skipSpaces();
      if (var1.readByte() == 40) {
         do {
            String var2 = var1.readAtom();
            if (var2.equalsIgnoreCase("MESSAGES")) {
               this.total = var1.readNumber();
            } else if (var2.equalsIgnoreCase("RECENT")) {
               this.recent = var1.readNumber();
            } else if (var2.equalsIgnoreCase("UIDNEXT")) {
               this.uidnext = var1.readLong();
            } else if (var2.equalsIgnoreCase("UIDVALIDITY")) {
               this.uidvalidity = var1.readLong();
            } else if (var2.equalsIgnoreCase("UNSEEN")) {
               this.unseen = var1.readNumber();
            }
         } while(var1.readByte() != 41);

      } else {
         throw new ParsingException("parse error in STATUS");
      }
   }

   public static void add(Status var0, Status var1) {
      int var2 = var1.total;
      if (var2 != -1) {
         var0.total = var2;
      }

      var2 = var1.recent;
      if (var2 != -1) {
         var0.recent = var2;
      }

      long var3 = var1.uidnext;
      if (var3 != -1L) {
         var0.uidnext = var3;
      }

      var3 = var1.uidvalidity;
      if (var3 != -1L) {
         var0.uidvalidity = var3;
      }

      var2 = var1.unseen;
      if (var2 != -1) {
         var0.unseen = var2;
      }

   }
}
