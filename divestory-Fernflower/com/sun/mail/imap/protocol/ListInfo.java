package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import java.util.Vector;

public class ListInfo {
   public static final int CHANGED = 1;
   public static final int INDETERMINATE = 3;
   public static final int UNCHANGED = 2;
   public String[] attrs;
   public boolean canOpen = true;
   public int changeState = 3;
   public boolean hasInferiors = true;
   public String name = null;
   public char separator = (char)47;

   public ListInfo(IMAPResponse var1) throws ParsingException {
      String[] var2 = var1.readSimpleList();
      Vector var3 = new Vector();
      if (var2 != null) {
         for(int var4 = 0; var4 < var2.length; ++var4) {
            if (var2[var4].equalsIgnoreCase("\\Marked")) {
               this.changeState = 1;
            } else if (var2[var4].equalsIgnoreCase("\\Unmarked")) {
               this.changeState = 2;
            } else if (var2[var4].equalsIgnoreCase("\\Noselect")) {
               this.canOpen = false;
            } else if (var2[var4].equalsIgnoreCase("\\Noinferiors")) {
               this.hasInferiors = false;
            }

            var3.addElement(var2[var4]);
         }
      }

      var2 = new String[var3.size()];
      this.attrs = var2;
      var3.copyInto(var2);
      var1.skipSpaces();
      if (var1.readByte() == 34) {
         char var6 = (char)var1.readByte();
         this.separator = (char)var6;
         if (var6 == '\\') {
            this.separator = (char)((char)var1.readByte());
         }

         var1.skip(1);
      } else {
         var1.skip(2);
      }

      var1.skipSpaces();
      String var5 = var1.readAtomString();
      this.name = var5;
      this.name = BASE64MailboxDecoder.decode(var5);
   }
}
