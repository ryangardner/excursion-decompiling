package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import javax.mail.Flags;

public class FLAGS extends Flags implements Item {
   static final char[] name = new char[]{'F', 'L', 'A', 'G', 'S'};
   private static final long serialVersionUID = 439049847053756670L;
   public int msgno;

   public FLAGS(IMAPResponse var1) throws ParsingException {
      this.msgno = var1.getNumber();
      var1.skipSpaces();
      String[] var5 = var1.readSimpleList();
      if (var5 != null) {
         for(int var2 = 0; var2 < var5.length; ++var2) {
            String var3 = var5[var2];
            if (var3.length() >= 2 && var3.charAt(0) == '\\') {
               char var4 = Character.toUpperCase(var3.charAt(1));
               if (var4 != '*') {
                  if (var4 != 'A') {
                     if (var4 != 'D') {
                        if (var4 != 'F') {
                           if (var4 != 'R') {
                              if (var4 != 'S') {
                                 this.add(var3);
                              } else {
                                 this.add(Flags.Flag.SEEN);
                              }
                           } else {
                              this.add(Flags.Flag.RECENT);
                           }
                        } else {
                           this.add(Flags.Flag.FLAGGED);
                        }
                     } else if (var3.length() >= 3) {
                        var4 = var3.charAt(2);
                        if (var4 != 'e' && var4 != 'E') {
                           if (var4 == 'r' || var4 == 'R') {
                              this.add(Flags.Flag.DRAFT);
                           }
                        } else {
                           this.add(Flags.Flag.DELETED);
                        }
                     } else {
                        this.add(var3);
                     }
                  } else {
                     this.add(Flags.Flag.ANSWERED);
                  }
               } else {
                  this.add(Flags.Flag.USER);
               }
            } else {
               this.add(var3);
            }
         }
      }

   }
}
