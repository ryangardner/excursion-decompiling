package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import java.util.Vector;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

class IMAPAddress extends InternetAddress {
   private static final long serialVersionUID = -3835822029483122232L;
   private boolean group = false;
   private InternetAddress[] grouplist;
   private String groupname;

   IMAPAddress(Response var1) throws ParsingException {
      var1.skipSpaces();
      if (var1.readByte() == 40) {
         this.encodedPersonal = var1.readString();
         var1.readString();
         String var2 = var1.readString();
         String var3 = var1.readString();
         if (var1.readByte() != 41) {
            throw new ParsingException("ADDRESS parse error");
         } else {
            if (var3 == null) {
               this.group = true;
               this.groupname = var2;
               if (var2 == null) {
                  return;
               }

               StringBuffer var7 = new StringBuffer();
               var7.append(this.groupname);
               var7.append(':');
               Vector var8 = new Vector();

               while(var1.peekByte() != 41) {
                  IMAPAddress var4 = new IMAPAddress(var1);
                  if (var4.isEndOfGroup()) {
                     break;
                  }

                  if (var8.size() != 0) {
                     var7.append(',');
                  }

                  var7.append(var4.toString());
                  var8.addElement(var4);
               }

               var7.append(';');
               this.address = var7.toString();
               IMAPAddress[] var5 = new IMAPAddress[var8.size()];
               this.grouplist = var5;
               var8.copyInto(var5);
            } else if (var2 != null && var2.length() != 0) {
               if (var3.length() == 0) {
                  this.address = var2;
               } else {
                  StringBuilder var6 = new StringBuilder(String.valueOf(var2));
                  var6.append("@");
                  var6.append(var3);
                  this.address = var6.toString();
               }
            } else {
               this.address = var3;
            }

         }
      } else {
         throw new ParsingException("ADDRESS parse error");
      }
   }

   public InternetAddress[] getGroup(boolean var1) throws AddressException {
      InternetAddress[] var2 = this.grouplist;
      return var2 == null ? null : (InternetAddress[])var2.clone();
   }

   boolean isEndOfGroup() {
      return this.group && this.groupname == null;
   }

   public boolean isGroup() {
      return this.group;
   }
}
