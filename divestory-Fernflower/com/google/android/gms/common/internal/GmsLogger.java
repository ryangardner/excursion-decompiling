package com.google.android.gms.common.internal;

import android.util.Log;

public final class GmsLogger {
   private static final int zza = 15;
   private static final String zzb;
   private final String zzc;
   private final String zzd;

   public GmsLogger(String var1) {
      this(var1, (String)null);
   }

   public GmsLogger(String var1, String var2) {
      Preconditions.checkNotNull(var1, "log tag cannot be null");
      boolean var3;
      if (var1.length() <= 23) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "tag \"%s\" is longer than the %d character maximum", var1, 23);
      this.zzc = var1;
      if (var2 != null && var2.length() > 0) {
         this.zzd = var2;
      } else {
         this.zzd = null;
      }
   }

   private final String zza(String var1) {
      String var2 = this.zzd;
      return var2 == null ? var1 : var2.concat(var1);
   }

   private final String zza(String var1, Object... var2) {
      String var3 = String.format(var1, var2);
      var1 = this.zzd;
      return var1 == null ? var3 : var1.concat(var3);
   }

   public final boolean canLog(int var1) {
      return Log.isLoggable(this.zzc, var1);
   }

   public final boolean canLogPii() {
      return false;
   }

   public final void d(String var1, String var2) {
      if (this.canLog(3)) {
         Log.d(var1, this.zza(var2));
      }

   }

   public final void d(String var1, String var2, Throwable var3) {
      if (this.canLog(3)) {
         Log.d(var1, this.zza(var2), var3);
      }

   }

   public final void e(String var1, String var2) {
      if (this.canLog(6)) {
         Log.e(var1, this.zza(var2));
      }

   }

   public final void e(String var1, String var2, Throwable var3) {
      if (this.canLog(6)) {
         Log.e(var1, this.zza(var2), var3);
      }

   }

   public final void efmt(String var1, String var2, Object... var3) {
      if (this.canLog(6)) {
         Log.e(var1, this.zza(var2, var3));
      }

   }

   public final void i(String var1, String var2) {
      if (this.canLog(4)) {
         Log.i(var1, this.zza(var2));
      }

   }

   public final void i(String var1, String var2, Throwable var3) {
      if (this.canLog(4)) {
         Log.i(var1, this.zza(var2), var3);
      }

   }

   public final void pii(String var1, String var2) {
      if (this.canLogPii()) {
         var1 = String.valueOf(var1);
         if (" PII_LOG".length() != 0) {
            var1 = var1.concat(" PII_LOG");
         } else {
            var1 = new String(var1);
         }

         Log.i(var1, this.zza(var2));
      }

   }

   public final void pii(String var1, String var2, Throwable var3) {
      if (this.canLogPii()) {
         var1 = String.valueOf(var1);
         if (" PII_LOG".length() != 0) {
            var1 = var1.concat(" PII_LOG");
         } else {
            var1 = new String(var1);
         }

         Log.i(var1, this.zza(var2), var3);
      }

   }

   public final void v(String var1, String var2) {
      if (this.canLog(2)) {
         Log.v(var1, this.zza(var2));
      }

   }

   public final void v(String var1, String var2, Throwable var3) {
      if (this.canLog(2)) {
         Log.v(var1, this.zza(var2), var3);
      }

   }

   public final void w(String var1, String var2) {
      if (this.canLog(5)) {
         Log.w(var1, this.zza(var2));
      }

   }

   public final void w(String var1, String var2, Throwable var3) {
      if (this.canLog(5)) {
         Log.w(var1, this.zza(var2), var3);
      }

   }

   public final void wfmt(String var1, String var2, Object... var3) {
      if (this.canLog(5)) {
         Log.w(this.zzc, this.zza(var2, var3));
      }

   }

   public final void wtf(String var1, String var2, Throwable var3) {
      if (this.canLog(7)) {
         Log.e(var1, this.zza(var2), var3);
         Log.wtf(var1, this.zza(var2), var3);
      }

   }
}
