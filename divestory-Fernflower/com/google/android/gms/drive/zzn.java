package com.google.android.gms.drive;

@Deprecated
public final class zzn extends ExecutionOptions {
   private boolean zzat;

   private zzn(String var1, boolean var2, int var3, boolean var4) {
      super(var1, var2, var3);
      this.zzat = var4;
   }

   // $FF: synthetic method
   zzn(String var1, boolean var2, int var3, boolean var4, zzo var5) {
      this(var1, var2, var3, var4);
   }

   public static zzn zza(ExecutionOptions var0) {
      zzp var1 = new zzp();
      if (var0 != null) {
         var1.setConflictStrategy(var0.zzn());
         var1.setNotifyOnCompletion(var0.zzm());
         String var2 = var0.zzl();
         if (var2 != null) {
            var1.setTrackingTag(var2);
         }
      }

      return (zzn)var1.build();
   }

   public final boolean zzp() {
      return this.zzat;
   }
}
