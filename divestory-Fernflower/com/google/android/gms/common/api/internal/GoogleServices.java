package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.common.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.common.internal.zzt;

@Deprecated
public final class GoogleServices {
   private static final Object zza = new Object();
   private static GoogleServices zzb;
   private final String zzc;
   private final Status zzd;
   private final boolean zze;
   private final boolean zzf;

   GoogleServices(Context var1) {
      Resources var2 = var1.getResources();
      int var3 = var2.getIdentifier("google_app_measurement_enable", "integer", var2.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
      boolean var4 = false;
      boolean var5 = true;
      if (var3 != 0) {
         if (var2.getInteger(var3) != 0) {
            var4 = true;
         }

         this.zzf = var4 ^ true;
      } else {
         this.zzf = false;
         var4 = var5;
      }

      this.zze = var4;
      String var6 = zzt.zza(var1);
      String var7 = var6;
      if (var6 == null) {
         var7 = (new StringResourceValueReader(var1)).getString("google_app_id");
      }

      if (TextUtils.isEmpty(var7)) {
         this.zzd = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
         this.zzc = null;
      } else {
         this.zzc = var7;
         this.zzd = Status.RESULT_SUCCESS;
      }
   }

   GoogleServices(String var1, boolean var2) {
      this.zzc = var1;
      this.zzd = Status.RESULT_SUCCESS;
      this.zze = var2;
      this.zzf = var2 ^ true;
   }

   private static GoogleServices checkInitialized(String var0) {
      Object var1 = zza;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (zzb != null) {
               GoogleServices var18 = zzb;
               return var18;
            }
         } catch (Throwable var16) {
            var10000 = var16;
            var10001 = false;
            break label122;
         }

         label116:
         try {
            int var3 = String.valueOf(var0).length();
            StringBuilder var4 = new StringBuilder(var3 + 34);
            var4.append("Initialize must be called before ");
            var4.append(var0);
            var4.append(".");
            IllegalStateException var2 = new IllegalStateException(var4.toString());
            throw var2;
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label116;
         }
      }

      while(true) {
         Throwable var17 = var10000;

         try {
            throw var17;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            continue;
         }
      }
   }

   static void clearInstanceForTest() {
      // $FF: Couldn't be decompiled
   }

   public static String getGoogleAppId() {
      return checkInitialized("getGoogleAppId").zzc;
   }

   public static Status initialize(Context var0) {
      Preconditions.checkNotNull(var0, "Context must not be null.");
      Object var1 = zza;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (zzb == null) {
               GoogleServices var2 = new GoogleServices(var0);
               zzb = var2;
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            Status var16 = zzb.zzd;
            return var16;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var15 = var10000;

         try {
            throw var15;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   public static Status initialize(Context var0, String var1, boolean var2) {
      Preconditions.checkNotNull(var0, "Context must not be null.");
      Preconditions.checkNotEmpty(var1, "App ID must be nonempty.");
      Object var16 = zza;
      synchronized(var16){}

      Throwable var10000;
      boolean var10001;
      label123: {
         Status var18;
         try {
            if (zzb != null) {
               var18 = zzb.checkGoogleAppId(var1);
               return var18;
            }
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label123;
         }

         label117:
         try {
            GoogleServices var3 = new GoogleServices(var1, var2);
            zzb = var3;
            var18 = var3.zzd;
            return var18;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label117;
         }
      }

      while(true) {
         Throwable var17 = var10000;

         try {
            throw var17;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            continue;
         }
      }
   }

   public static boolean isMeasurementEnabled() {
      GoogleServices var0 = checkInitialized("isMeasurementEnabled");
      return var0.zzd.isSuccess() && var0.zze;
   }

   public static boolean isMeasurementExplicitlyDisabled() {
      return checkInitialized("isMeasurementExplicitlyDisabled").zzf;
   }

   final Status checkGoogleAppId(String var1) {
      String var2 = this.zzc;
      if (var2 != null && !var2.equals(var1)) {
         var1 = this.zzc;
         StringBuilder var3 = new StringBuilder(String.valueOf(var1).length() + 97);
         var3.append("Initialize was called with two different Google App IDs.  Only the first app ID will be used: '");
         var3.append(var1);
         var3.append("'.");
         return new Status(10, var3.toString());
      } else {
         return Status.RESULT_SUCCESS;
      }
   }
}
