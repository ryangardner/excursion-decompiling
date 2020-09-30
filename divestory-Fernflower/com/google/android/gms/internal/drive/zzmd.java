package com.google.android.gms.internal.drive;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzmd {
   private static final zzmd zzuw = new zzmd();
   private final zzmg zzux = new zzlf();
   private final ConcurrentMap<Class<?>, zzmf<?>> zzuy = new ConcurrentHashMap();

   private zzmd() {
   }

   public static zzmd zzej() {
      return zzuw;
   }

   public final <T> zzmf<T> zzf(Class<T> var1) {
      zzkm.zza(var1, (String)"messageType");
      zzmf var2 = (zzmf)this.zzuy.get(var1);
      zzmf var3 = var2;
      if (var2 == null) {
         var3 = this.zzux.zze(var1);
         zzkm.zza(var1, (String)"messageType");
         zzkm.zza(var3, (String)"schema");
         zzmf var4 = (zzmf)this.zzuy.putIfAbsent(var1, var3);
         if (var4 != null) {
            var3 = var4;
         }
      }

      return var3;
   }

   public final <T> zzmf<T> zzq(T var1) {
      return this.zzf(var1.getClass());
   }
}
