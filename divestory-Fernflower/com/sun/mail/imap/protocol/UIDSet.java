package com.sun.mail.imap.protocol;

import java.util.Vector;

public class UIDSet {
   public long end;
   public long start;

   public UIDSet() {
   }

   public UIDSet(long var1, long var3) {
      this.start = var1;
      this.end = var3;
   }

   public static UIDSet[] createUIDSets(long[] var0) {
      Vector var1 = new Vector();

      for(int var2 = 0; var2 < var0.length; ++var2) {
         UIDSet var3 = new UIDSet();
         var3.start = var0[var2];

         int var4;
         do {
            var4 = var2 + 1;
            if (var4 >= var0.length) {
               break;
            }

            var2 = var4;
         } while(var0[var4] == var0[var4 - 1] + 1L);

         var2 = var4 - 1;
         var3.end = var0[var2];
         var1.addElement(var3);
      }

      UIDSet[] var5 = new UIDSet[var1.size()];
      var1.copyInto(var5);
      return var5;
   }

   public static long size(UIDSet[] var0) {
      long var1 = 0L;
      if (var0 == null) {
         return 0L;
      } else {
         for(int var3 = 0; var3 < var0.length; ++var3) {
            var1 += var0[var3].size();
         }

         return var1;
      }
   }

   public static String toString(UIDSet[] var0) {
      if (var0 != null && var0.length != 0) {
         int var1 = 0;
         StringBuffer var2 = new StringBuffer();
         int var3 = var0.length;

         while(true) {
            long var4 = var0[var1].start;
            long var6 = var0[var1].end;
            if (var6 > var4) {
               var2.append(var4);
               var2.append(':');
               var2.append(var6);
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

   public long size() {
      return this.end - this.start + 1L;
   }
}
