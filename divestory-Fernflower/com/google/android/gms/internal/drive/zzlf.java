package com.google.android.gms.internal.drive;

final class zzlf implements zzmg {
   private static final zzlp zzts = new zzlg();
   private final zzlp zztr;

   public zzlf() {
      this(new zzlh(new zzlp[]{zzkj.zzcv(), zzdv()}));
   }

   private zzlf(zzlp var1) {
      this.zztr = (zzlp)zzkm.zza(var1, (String)"messageInfoFactory");
   }

   private static boolean zza(zzlo var0) {
      return var0.zzec() == zzkk.zze.zzsf;
   }

   private static zzlp zzdv() {
      try {
         zzlp var0 = (zzlp)Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance").invoke((Object)null);
         return var0;
      } catch (Exception var1) {
         return zzts;
      }
   }

   public final <T> zzmf<T> zze(Class<T> var1) {
      zzmh.zzg(var1);
      zzlo var2 = this.zztr.zzc(var1);
      if (var2.zzed()) {
         return zzkk.class.isAssignableFrom(var1) ? zzlw.zza(zzmh.zzeo(), zzka.zzcl(), var2.zzee()) : zzlw.zza(zzmh.zzem(), zzka.zzcm(), var2.zzee());
      } else if (zzkk.class.isAssignableFrom(var1)) {
         return zza(var2) ? zzlu.zza(var1, var2, zzma.zzeh(), zzla.zzdu(), zzmh.zzeo(), zzka.zzcl(), zzln.zzea()) : zzlu.zza(var1, var2, zzma.zzeh(), zzla.zzdu(), zzmh.zzeo(), (zzjy)null, zzln.zzea());
      } else {
         return zza(var2) ? zzlu.zza(var1, var2, zzma.zzeg(), zzla.zzdt(), zzmh.zzem(), zzka.zzcm(), zzln.zzdz()) : zzlu.zza(var1, var2, zzma.zzeg(), zzla.zzdt(), zzmh.zzen(), (zzjy)null, zzln.zzdz());
      }
   }
}
