package com.google.android.gms.internal.drive;

final class zzld extends zzla {
   private zzld() {
      super((zzlb)null);
   }

   // $FF: synthetic method
   zzld(zzlb var1) {
      this();
   }

   private static <E> zzkp<E> zzc(Object var0, long var1) {
      return (zzkp)zznd.zzo(var0, var1);
   }

   final void zza(Object var1, long var2) {
      zzc(var1, var2).zzbp();
   }

   final <E> void zza(Object var1, Object var2, long var3) {
      zzkp var5 = zzc(var1, var3);
      zzkp var6 = zzc(var2, var3);
      int var7 = var5.size();
      int var8 = var6.size();
      zzkp var9 = var5;
      if (var7 > 0) {
         var9 = var5;
         if (var8 > 0) {
            var9 = var5;
            if (!var5.zzbo()) {
               var9 = var5.zzr(var8 + var7);
            }

            var9.addAll(var6);
         }
      }

      var5 = var6;
      if (var7 > 0) {
         var5 = var9;
      }

      zznd.zza(var1, var3, var5);
   }
}
