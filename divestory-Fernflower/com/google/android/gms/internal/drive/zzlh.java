package com.google.android.gms.internal.drive;

final class zzlh implements zzlp {
   private zzlp[] zztt;

   zzlh(zzlp... var1) {
      this.zztt = var1;
   }

   public final boolean zzb(Class<?> var1) {
      zzlp[] var2 = this.zztt;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         if (var2[var4].zzb(var1)) {
            return true;
         }
      }

      return false;
   }

   public final zzlo zzc(Class<?> var1) {
      zzlp[] var2 = this.zztt;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         zzlp var5 = var2[var4];
         if (var5.zzb(var1)) {
            return var5.zzc(var1);
         }
      }

      String var6 = String.valueOf(var1.getName());
      if (var6.length() != 0) {
         var6 = "No factory is available for message type: ".concat(var6);
      } else {
         var6 = new String("No factory is available for message type: ");
      }

      throw new UnsupportedOperationException(var6);
   }
}
