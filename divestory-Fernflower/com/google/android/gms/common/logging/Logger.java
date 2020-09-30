package com.google.android.gms.common.logging;

import android.util.Log;
import com.google.android.gms.common.internal.GmsLogger;
import java.util.Locale;

public class Logger {
   private final String zza;
   private final String zzb;
   private final GmsLogger zzc;
   private final int zzd;

   private Logger(String var1, String var2) {
      this.zzb = var2;
      this.zza = var1;
      this.zzc = new GmsLogger(var1);

      int var3;
      for(var3 = 2; 7 >= var3 && !Log.isLoggable(this.zza, var3); ++var3) {
      }

      this.zzd = var3;
   }

   public Logger(String var1, String... var2) {
      String var7;
      if (var2.length == 0) {
         var7 = "";
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append('[');
         int var4 = var2.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var2[var5];
            if (var3.length() > 1) {
               var3.append(",");
            }

            var3.append(var6);
         }

         var3.append(']');
         var3.append(' ');
         var7 = var3.toString();
      }

      this(var1, var7);
   }

   public void d(String var1, Throwable var2, Object... var3) {
      if (this.isLoggable(3)) {
         Log.d(this.zza, this.format(var1, var3), var2);
      }

   }

   public void d(String var1, Object... var2) {
      if (this.isLoggable(3)) {
         Log.d(this.zza, this.format(var1, var2));
      }

   }

   public void e(String var1, Throwable var2, Object... var3) {
      Log.e(this.zza, this.format(var1, var3), var2);
   }

   public void e(String var1, Object... var2) {
      Log.e(this.zza, this.format(var1, var2));
   }

   protected String format(String var1, Object... var2) {
      String var3 = var1;
      if (var2 != null) {
         var3 = var1;
         if (var2.length > 0) {
            var3 = String.format(Locale.US, var1, var2);
         }
      }

      return this.zzb.concat(var3);
   }

   public String getTag() {
      return this.zza;
   }

   public void i(String var1, Object... var2) {
      Log.i(this.zza, this.format(var1, var2));
   }

   public boolean isLoggable(int var1) {
      return this.zzd <= var1;
   }

   public void v(String var1, Throwable var2, Object... var3) {
      if (this.isLoggable(2)) {
         Log.v(this.zza, this.format(var1, var3), var2);
      }

   }

   public void v(String var1, Object... var2) {
      if (this.isLoggable(2)) {
         Log.v(this.zza, this.format(var1, var2));
      }

   }

   public void w(String var1, Object... var2) {
      Log.w(this.zza, this.format(var1, var2));
   }

   public void wtf(String var1, Throwable var2, Object... var3) {
      Log.wtf(this.zza, this.format(var1, var3), var2);
   }

   public void wtf(Throwable var1) {
      Log.wtf(this.zza, var1);
   }
}
