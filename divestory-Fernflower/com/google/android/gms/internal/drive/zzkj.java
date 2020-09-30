package com.google.android.gms.internal.drive;

final class zzkj implements zzlp {
   private static final zzkj zzrp = new zzkj();

   private zzkj() {
   }

   public static zzkj zzcv() {
      return zzrp;
   }

   public final boolean zzb(Class<?> var1) {
      return zzkk.class.isAssignableFrom(var1);
   }

   public final zzlo zzc(Class<?> var1) {
      String var4;
      if (!zzkk.class.isAssignableFrom(var1)) {
         var4 = String.valueOf(var1.getName());
         if (var4.length() != 0) {
            var4 = "Unsupported message type: ".concat(var4);
         } else {
            var4 = new String("Unsupported message type: ");
         }

         throw new IllegalArgumentException(var4);
      } else {
         try {
            zzlo var2 = (zzlo)zzkk.zzd(var1.asSubclass(zzkk.class)).zza(zzkk.zze.zzrz, (Object)null, (Object)null);
            return var2;
         } catch (Exception var3) {
            var4 = String.valueOf(var1.getName());
            if (var4.length() != 0) {
               var4 = "Unable to get message info for ".concat(var4);
            } else {
               var4 = new String("Unable to get message info for ");
            }

            throw new RuntimeException(var4, var3);
         }
      }
   }
}
