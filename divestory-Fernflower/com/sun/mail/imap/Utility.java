package com.sun.mail.imap;

import com.sun.mail.imap.protocol.MessageSet;
import com.sun.mail.imap.protocol.UIDSet;
import java.util.Vector;
import javax.mail.Message;

public final class Utility {
   private Utility() {
   }

   public static MessageSet[] toMessageSet(Message[] var0, Utility.Condition var1) {
      Vector var2 = new Vector(1);

      for(int var3 = 0; var3 < var0.length; ++var3) {
         IMAPMessage var4 = (IMAPMessage)var0[var3];
         if (!var4.isExpunged()) {
            int var5 = var4.getSequenceNumber();
            if (var1 == null || var1.test(var4)) {
               MessageSet var9 = new MessageSet();
               var9.start = var5;

               while(true) {
                  ++var3;
                  if (var3 >= var0.length) {
                     break;
                  }

                  IMAPMessage var6 = (IMAPMessage)var0[var3];
                  if (!var6.isExpunged()) {
                     int var7 = var6.getSequenceNumber();
                     if (var1 == null || var1.test(var6)) {
                        if (var7 != var5 + 1) {
                           --var3;
                           break;
                        }

                        var5 = var7;
                     }
                  }
               }

               var9.end = var5;
               var2.addElement(var9);
            }
         }
      }

      if (var2.isEmpty()) {
         return null;
      } else {
         MessageSet[] var8 = new MessageSet[var2.size()];
         var2.copyInto(var8);
         return var8;
      }
   }

   public static UIDSet[] toUIDSet(Message[] var0) {
      Vector var1 = new Vector(1);

      for(int var2 = 0; var2 < var0.length; ++var2) {
         IMAPMessage var3 = (IMAPMessage)var0[var2];
         if (!var3.isExpunged()) {
            long var4 = var3.getUID();
            UIDSet var6 = new UIDSet();
            var6.start = var4;

            while(true) {
               ++var2;
               if (var2 >= var0.length) {
                  break;
               }

               var3 = (IMAPMessage)var0[var2];
               if (!var3.isExpunged()) {
                  long var7 = var3.getUID();
                  if (var7 != 1L + var4) {
                     --var2;
                     break;
                  }

                  var4 = var7;
               }
            }

            var6.end = var4;
            var1.addElement(var6);
         }
      }

      if (var1.isEmpty()) {
         return null;
      } else {
         UIDSet[] var9 = new UIDSet[var1.size()];
         var1.copyInto(var9);
         return var9;
      }
   }

   public interface Condition {
      boolean test(IMAPMessage var1);
   }
}
