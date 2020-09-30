package com.google.android.gms.common;

import java.util.concurrent.Callable;

final class zzn extends zzl {
   private final Callable<String> zzb;

   private zzn(Callable<String> var1) {
      super(false, (String)null, (Throwable)null);
      this.zzb = var1;
   }

   // $FF: synthetic method
   zzn(Callable var1, zzo var2) {
      this(var1);
   }

   final String zzb() {
      try {
         String var1 = (String)this.zzb.call();
         return var1;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }
}
