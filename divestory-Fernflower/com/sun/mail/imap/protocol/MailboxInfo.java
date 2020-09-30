package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import javax.mail.Flags;

public class MailboxInfo {
   public Flags availableFlags = null;
   public int first = -1;
   public int mode;
   public Flags permanentFlags = null;
   public int recent = -1;
   public int total = -1;
   public long uidnext = -1L;
   public long uidvalidity = -1L;

   public MailboxInfo(Response[] var1) throws ParsingException {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2] != null && var1[var2] instanceof IMAPResponse) {
            IMAPResponse var3 = (IMAPResponse)var1[var2];
            if (var3.keyEquals("EXISTS")) {
               this.total = var3.getNumber();
               var1[var2] = null;
            } else if (var3.keyEquals("RECENT")) {
               this.recent = var3.getNumber();
               var1[var2] = null;
            } else if (var3.keyEquals("FLAGS")) {
               this.availableFlags = new FLAGS(var3);
               var1[var2] = null;
            } else if (var3.isUnTagged() && var3.isOK()) {
               var3.skipSpaces();
               if (var3.readByte() != 91) {
                  var3.reset();
               } else {
                  boolean var5;
                  label60: {
                     String var4 = var3.readAtom();
                     if (var4.equalsIgnoreCase("UNSEEN")) {
                        this.first = var3.readNumber();
                     } else if (var4.equalsIgnoreCase("UIDVALIDITY")) {
                        this.uidvalidity = var3.readLong();
                     } else if (var4.equalsIgnoreCase("PERMANENTFLAGS")) {
                        this.permanentFlags = new FLAGS(var3);
                     } else {
                        if (!var4.equalsIgnoreCase("UIDNEXT")) {
                           var5 = false;
                           break label60;
                        }

                        this.uidnext = var3.readLong();
                     }

                     var5 = true;
                  }

                  if (var5) {
                     var1[var2] = null;
                  } else {
                     var3.reset();
                  }
               }
            }
         }
      }

      if (this.permanentFlags == null) {
         if (this.availableFlags != null) {
            this.permanentFlags = new Flags(this.availableFlags);
         } else {
            this.permanentFlags = new Flags();
         }
      }

   }
}
