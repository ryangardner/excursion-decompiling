package com.sun.mail.imap.protocol;

import java.util.Vector;

public class MessageSet {
   public int end;
   public int start;

   public MessageSet() {
   }

   public MessageSet(int var1, int var2) {
      this.start = var1;
      this.end = var2;
   }

   public static MessageSet[] createMessageSets(int[] var0) {
      Vector var1 = new Vector();

      for(int var2 = 0; var2 < var0.length; ++var2) {
         MessageSet var3 = new MessageSet();
         var3.start = var0[var2];

         int var4;
         do {
            var4 = var2 + 1;
            if (var4 >= var0.length) {
               break;
            }

            var2 = var4;
         } while(var0[var4] == var0[var4 - 1] + 1);

         var2 = var4 - 1;
         var3.end = var0[var2];
         var1.addElement(var3);
      }

      MessageSet[] var5 = new MessageSet[var1.size()];
      var1.copyInto(var5);
      return var5;
   }

   public static int size(MessageSet[] var0) {
      int var1 = 0;
      if (var0 == null) {
         return 0;
      } else {
         int var2;
         for(var2 = 0; var1 < var0.length; ++var1) {
            var2 += var0[var1].size();
         }

         return var2;
      }
   }

   public static String toString(MessageSet[] var0) {
      if (var0 != null && var0.length != 0) {
         int var1 = 0;
         StringBuffer var2 = new StringBuffer();
         int var3 = var0.length;

         while(true) {
            int var4 = var0[var1].start;
            int var5 = var0[var1].end;
            if (var5 > var4) {
               var2.append(var4);
               var2.append(':');
               var2.append(var5);
            } else {
               var2.append(var4);
            }

            ++var1;
            if (var1 >= var3) {
               return var2.toString();
            }

            var2.append(',');
         }
      } else {
         return null;
      }
   }

   public int size() {
      return this.end - this.start + 1;
   }
}
