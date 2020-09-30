package com.google.android.gms.internal.drive;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

final class zzlm implements zzll {
   public final int zzb(int var1, Object var2, Object var3) {
      zzlk var4 = (zzlk)var2;
      if (var4.isEmpty()) {
         return 0;
      } else {
         Iterator var5 = var4.entrySet().iterator();
         if (!var5.hasNext()) {
            return 0;
         } else {
            Entry var6 = (Entry)var5.next();
            var6.getKey();
            var6.getValue();
            throw new NoSuchMethodError();
         }
      }
   }

   public final Object zzb(Object var1, Object var2) {
      zzlk var3 = (zzlk)var1;
      zzlk var5 = (zzlk)var2;
      zzlk var4 = var3;
      if (!var5.isEmpty()) {
         var4 = var3;
         if (!var3.isMutable()) {
            var4 = var3.zzdx();
         }

         var4.zza(var5);
      }

      return var4;
   }

   public final Map<?, ?> zzh(Object var1) {
      return (zzlk)var1;
   }

   public final Map<?, ?> zzi(Object var1) {
      return (zzlk)var1;
   }

   public final boolean zzj(Object var1) {
      return !((zzlk)var1).isMutable();
   }

   public final Object zzk(Object var1) {
      ((zzlk)var1).zzbp();
      return var1;
   }

   public final Object zzl(Object var1) {
      return zzlk.zzdw().zzdx();
   }

   public final zzlj<?, ?> zzm(Object var1) {
      throw new NoSuchMethodError();
   }
}
