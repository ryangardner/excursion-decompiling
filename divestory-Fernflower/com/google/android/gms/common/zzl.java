package com.google.android.gms.common;

import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;
import java.security.MessageDigest;
import java.util.concurrent.Callable;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
class zzl {
   private static final zzl zzb = new zzl(true, (String)null, (Throwable)null);
   final boolean zza;
   @Nullable
   private final String zzc;
   @Nullable
   private final Throwable zzd;

   zzl(boolean var1, @Nullable String var2, @Nullable Throwable var3) {
      this.zza = var1;
      this.zzc = var2;
      this.zzd = var3;
   }

   static zzl zza() {
      return zzb;
   }

   static zzl zza(String var0) {
      return new zzl(false, var0, (Throwable)null);
   }

   static zzl zza(String var0, Throwable var1) {
      return new zzl(false, var0, var1);
   }

   static zzl zza(Callable<String> var0) {
      return new zzn(var0, (zzo)null);
   }

   static String zza(String var0, zzd var1, boolean var2, boolean var3) {
      String var4;
      if (var3) {
         var4 = "debug cert rejected";
      } else {
         var4 = "not allowed";
      }

      String var5 = Hex.bytesToStringLowercase(((MessageDigest)Preconditions.checkNotNull(AndroidUtilsLight.zza("SHA-1"))).digest(var1.zza()));
      StringBuilder var6 = new StringBuilder(14);
      var6.append("12451009.false");
      return String.format("%s: pkg=%s, sha1=%s, atk=%s, ver=%s", var4, var0, var5, var2, var6.toString());
   }

   @Nullable
   String zzb() {
      return this.zzc;
   }

   final void zzc() {
      if (!this.zza && Log.isLoggable("GoogleCertificatesRslt", 3)) {
         if (this.zzd != null) {
            Log.d("GoogleCertificatesRslt", this.zzb(), this.zzd);
            return;
         }

         Log.d("GoogleCertificatesRslt", this.zzb());
      }

   }
}
