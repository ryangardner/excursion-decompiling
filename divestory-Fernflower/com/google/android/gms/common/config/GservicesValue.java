package com.google.android.gms.common.config;

import android.content.Context;
import java.util.Set;

public abstract class GservicesValue<T> {
   private static final Object zzc = new Object();
   private static GservicesValue.zza zzd = null;
   private static int zze = 0;
   private static Context zzf;
   private static Set<String> zzg;
   protected final String zza;
   protected final T zzb;
   private T zzh = null;

   protected GservicesValue(String var1, T var2) {
      this.zza = var1;
      this.zzb = var2;
   }

   public static boolean isInitialized() {
      // $FF: Couldn't be decompiled
   }

   public static GservicesValue<Float> value(String var0, Float var1) {
      return new zzc(var0, var1);
   }

   public static GservicesValue<Integer> value(String var0, Integer var1) {
      return new zzd(var0, var1);
   }

   public static GservicesValue<Long> value(String var0, Long var1) {
      return new com.google.android.gms.common.config.zza(var0, var1);
   }

   public static GservicesValue<String> value(String var0, String var1) {
      return new zze(var0, var1);
   }

   public static GservicesValue<Boolean> value(String var0, boolean var1) {
      return new zzb(var0, var1);
   }

   private static boolean zza() {
      // $FF: Couldn't be decompiled
   }

   public final T get() {
      // $FF: Couldn't be decompiled
   }

   @Deprecated
   public final T getBinderSafe() {
      return this.get();
   }

   public void override(T param1) {
      // $FF: Couldn't be decompiled
   }

   public void resetOverride() {
      this.zzh = null;
   }

   protected abstract T zza(String var1);

   private interface zza {
      Boolean zza(String var1, Boolean var2);

      Float zza(String var1, Float var2);

      Integer zza(String var1, Integer var2);

      Long zza(String var1, Long var2);

      String zza(String var1, String var2);
   }
}
