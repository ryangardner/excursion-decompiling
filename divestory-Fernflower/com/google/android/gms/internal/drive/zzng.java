package com.google.android.gms.internal.drive;

final class zzng {
   private static void zza(byte var0, byte var1, byte var2, byte var3, char[] var4, int var5) throws zzkq {
      if (!zzg(var1) && (var0 << 28) + var1 + 112 >> 30 == 0 && !zzg(var2) && !zzg(var3)) {
         int var6 = (var0 & 7) << 18 | (var1 & 63) << 12 | (var2 & 63) << 6 | var3 & 63;
         var4[var5] = (char)((char)((var6 >>> 10) + 'ퟀ'));
         var4[var5 + 1] = (char)((char)((var6 & 1023) + '\udc00'));
      } else {
         throw zzkq.zzdn();
      }
   }

   private static void zza(byte var0, byte var1, byte var2, char[] var3, int var4) throws zzkq {
      if (!zzg(var1) && (var0 != -32 || var1 >= -96) && (var0 != -19 || var1 < -96) && !zzg(var2)) {
         var3[var4] = (char)((char)((var0 & 15) << 12 | (var1 & 63) << 6 | var2 & 63));
      } else {
         throw zzkq.zzdn();
      }
   }

   private static void zza(byte var0, byte var1, char[] var2, int var3) throws zzkq {
      if (var0 >= -62 && !zzg(var1)) {
         var2[var3] = (char)((char)((var0 & 31) << 6 | var1 & 63));
      } else {
         throw zzkq.zzdn();
      }
   }

   private static void zza(byte var0, char[] var1, int var2) {
      var1[var2] = (char)((char)var0);
   }

   // $FF: synthetic method
   static void zzb(byte var0, byte var1, byte var2, byte var3, char[] var4, int var5) throws zzkq {
      zza(var0, var1, var2, var3, var4, var5);
   }

   // $FF: synthetic method
   static void zzb(byte var0, byte var1, byte var2, char[] var3, int var4) throws zzkq {
      zza(var0, var1, var2, var3, var4);
   }

   // $FF: synthetic method
   static void zzb(byte var0, byte var1, char[] var2, int var3) throws zzkq {
      zza(var0, var1, var2, var3);
   }

   // $FF: synthetic method
   static void zzb(byte var0, char[] var1, int var2) {
      zza(var0, var1, var2);
   }

   private static boolean zzd(byte var0) {
      return var0 >= 0;
   }

   private static boolean zze(byte var0) {
      return var0 < -32;
   }

   private static boolean zzf(byte var0) {
      return var0 < -16;
   }

   private static boolean zzg(byte var0) {
      return var0 > -65;
   }

   // $FF: synthetic method
   static boolean zzh(byte var0) {
      return zzd(var0);
   }

   // $FF: synthetic method
   static boolean zzi(byte var0) {
      return zze(var0);
   }

   // $FF: synthetic method
   static boolean zzj(byte var0) {
      return zzf(var0);
   }
}
