package com.google.common.collect;

import com.google.common.base.Preconditions;

final class CollectPreconditions {
   static void checkEntryNotNull(Object var0, Object var1) {
      if (var0 != null) {
         if (var1 == null) {
            StringBuilder var3 = new StringBuilder();
            var3.append("null value in entry: ");
            var3.append(var0);
            var3.append("=null");
            throw new NullPointerException(var3.toString());
         }
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("null key in entry: null=");
         var2.append(var1);
         throw new NullPointerException(var2.toString());
      }
   }

   static int checkNonnegative(int var0, String var1) {
      if (var0 >= 0) {
         return var0;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append(" cannot be negative but was: ");
         var2.append(var0);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   static long checkNonnegative(long var0, String var2) {
      if (var0 >= 0L) {
         return var0;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append(var2);
         var3.append(" cannot be negative but was: ");
         var3.append(var0);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   static void checkPositive(int var0, String var1) {
      if (var0 <= 0) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append(" must be positive but was: ");
         var2.append(var0);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   static void checkRemove(boolean var0) {
      Preconditions.checkState(var0, "no calls to next() since the last call to remove()");
   }
}
