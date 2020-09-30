package com.google.android.gms.auth.api.signin.internal;

public class HashAccumulator {
   private static int zaa;
   private int zab = 1;

   public HashAccumulator addObject(Object var1) {
      int var2 = zaa;
      int var3 = this.zab;
      int var4;
      if (var1 == null) {
         var4 = 0;
      } else {
         var4 = var1.hashCode();
      }

      this.zab = var2 * var3 + var4;
      return this;
   }

   public int hash() {
      return this.zab;
   }

   public final HashAccumulator zaa(boolean var1) {
      this.zab = zaa * this.zab + var1;
      return this;
   }
}
