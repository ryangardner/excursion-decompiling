package com.google.android.gms.internal.drive;

final class zzme implements zzlo {
   private final int flags;
   private final String info;
   private final Object[] zzue;
   private final zzlq zzuh;

   zzme(zzlq var1, String var2, Object[] var3) {
      this.zzuh = var1;
      this.info = var2;
      this.zzue = var3;
      char var4 = var2.charAt(0);
      if (var4 < '\ud800') {
         this.flags = var4;
      } else {
         int var5 = var4 & 8191;
         int var6 = 13;
         int var8 = 1;

         while(true) {
            char var7 = var2.charAt(var8);
            if (var7 < '\ud800') {
               this.flags = var5 | var7 << var6;
               return;
            }

            var5 |= (var7 & 8191) << var6;
            var6 += 13;
            ++var8;
         }
      }
   }

   public final int zzec() {
      return (this.flags & 1) == 1 ? zzkk.zze.zzsf : zzkk.zze.zzsg;
   }

   public final boolean zzed() {
      return (this.flags & 2) == 2;
   }

   public final zzlq zzee() {
      return this.zzuh;
   }

   final String zzek() {
      return this.info;
   }

   final Object[] zzel() {
      return this.zzue;
   }
}
