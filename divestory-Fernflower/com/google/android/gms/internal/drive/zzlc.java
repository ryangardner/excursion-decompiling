package com.google.android.gms.internal.drive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

final class zzlc extends zzla {
   private static final Class<?> zzto = Collections.unmodifiableList(Collections.emptyList()).getClass();

   private zzlc() {
      super((zzlb)null);
   }

   // $FF: synthetic method
   zzlc(zzlb var1) {
      this();
   }

   private static <E> List<E> zzb(Object var0, long var1) {
      return (List)zznd.zzo(var0, var1);
   }

   final void zza(Object var1, long var2) {
      List var4 = (List)zznd.zzo(var1, var2);
      Object var6;
      if (var4 instanceof zzkz) {
         var6 = ((zzkz)var4).zzds();
      } else {
         if (zzto.isAssignableFrom(var4.getClass())) {
            return;
         }

         if (var4 instanceof zzmc && var4 instanceof zzkp) {
            zzkp var5 = (zzkp)var4;
            if (var5.zzbo()) {
               var5.zzbp();
            }

            return;
         }

         var6 = Collections.unmodifiableList(var4);
      }

      zznd.zza(var1, var2, var6);
   }

   final <E> void zza(Object var1, Object var2, long var3) {
      Object var5 = zzb(var2, var3);
      int var6 = ((List)var5).size();
      List var7 = zzb(var1, var3);
      if (var7.isEmpty()) {
         if (var7 instanceof zzkz) {
            var2 = new zzky(var6);
         } else if (var7 instanceof zzmc && var7 instanceof zzkp) {
            var2 = ((zzkp)var7).zzr(var6);
         } else {
            var2 = new ArrayList(var6);
         }

         zznd.zza(var1, var3, var2);
      } else if (zzto.isAssignableFrom(var7.getClass())) {
         var2 = new ArrayList(var7.size() + var6);
         ((ArrayList)var2).addAll(var7);
         zznd.zza(var1, var3, var2);
      } else if (var7 instanceof zzna) {
         var2 = new zzky(var7.size() + var6);
         ((zziw)var2).addAll((zzna)var7);
         zznd.zza(var1, var3, var2);
      } else {
         var2 = var7;
         if (var7 instanceof zzmc) {
            var2 = var7;
            if (var7 instanceof zzkp) {
               zzkp var8 = (zzkp)var7;
               var2 = var7;
               if (!var8.zzbo()) {
                  var2 = var8.zzr(var7.size() + var6);
                  zznd.zza(var1, var3, var2);
               }
            }
         }
      }

      int var9 = ((List)var2).size();
      var6 = ((List)var5).size();
      if (var9 > 0 && var6 > 0) {
         ((List)var2).addAll((Collection)var5);
      }

      if (var9 > 0) {
         var5 = var2;
      }

      zznd.zza(var1, var3, var5);
   }
}
