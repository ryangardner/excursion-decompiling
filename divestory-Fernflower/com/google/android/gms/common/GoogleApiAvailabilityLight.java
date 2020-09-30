package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.wrappers.Wrappers;

public class GoogleApiAvailabilityLight {
   public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
   public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
   public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
   static final String TRACKING_SOURCE_DIALOG = "d";
   static final String TRACKING_SOURCE_NOTIFICATION = "n";
   private static final GoogleApiAvailabilityLight zza;

   static {
      GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
      zza = new GoogleApiAvailabilityLight();
   }

   GoogleApiAvailabilityLight() {
   }

   public static GoogleApiAvailabilityLight getInstance() {
      return zza;
   }

   private static String zza(Context var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("gcore_");
      var2.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
      var2.append("-");
      if (!TextUtils.isEmpty(var1)) {
         var2.append(var1);
      }

      var2.append("-");
      if (var0 != null) {
         var2.append(var0.getPackageName());
      }

      var2.append("-");
      if (var0 != null) {
         try {
            var2.append(Wrappers.packageManager(var0).getPackageInfo(var0.getPackageName(), 0).versionCode);
         } catch (NameNotFoundException var3) {
         }
      }

      return var2.toString();
   }

   public void cancelAvailabilityErrorNotifications(Context var1) {
      GooglePlayServicesUtilLight.cancelAvailabilityErrorNotifications(var1);
   }

   public int getApkVersion(Context var1) {
      return GooglePlayServicesUtilLight.getApkVersion(var1);
   }

   public int getClientVersion(Context var1) {
      return GooglePlayServicesUtilLight.getClientVersion(var1);
   }

   @Deprecated
   public Intent getErrorResolutionIntent(int var1) {
      return this.getErrorResolutionIntent((Context)null, var1, (String)null);
   }

   public Intent getErrorResolutionIntent(Context var1, int var2, String var3) {
      if (var2 != 1 && var2 != 2) {
         return var2 != 3 ? null : com.google.android.gms.common.internal.zzj.zza("com.google.android.gms");
      } else {
         return var1 != null && DeviceProperties.isWearableWithoutPlayStore(var1) ? com.google.android.gms.common.internal.zzj.zza() : com.google.android.gms.common.internal.zzj.zza("com.google.android.gms", zza(var1, var3));
      }
   }

   public PendingIntent getErrorResolutionPendingIntent(Context var1, int var2, int var3) {
      return this.getErrorResolutionPendingIntent(var1, var2, var3, (String)null);
   }

   public PendingIntent getErrorResolutionPendingIntent(Context var1, int var2, int var3, String var4) {
      Intent var5 = this.getErrorResolutionIntent(var1, var2, var4);
      return var5 == null ? null : PendingIntent.getActivity(var1, var3, var5, 134217728);
   }

   public String getErrorString(int var1) {
      return GooglePlayServicesUtilLight.getErrorString(var1);
   }

   public int isGooglePlayServicesAvailable(Context var1) {
      return this.isGooglePlayServicesAvailable(var1, GOOGLE_PLAY_SERVICES_VERSION_CODE);
   }

   public int isGooglePlayServicesAvailable(Context var1, int var2) {
      int var3 = GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(var1, var2);
      var2 = var3;
      if (GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(var1, var3)) {
         var2 = 18;
      }

      return var2;
   }

   public boolean isPlayServicesPossiblyUpdating(Context var1, int var2) {
      return GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(var1, var2);
   }

   public boolean isPlayStorePossiblyUpdating(Context var1, int var2) {
      return GooglePlayServicesUtilLight.isPlayStorePossiblyUpdating(var1, var2);
   }

   public boolean isUninstalledAppPossiblyUpdating(Context var1, String var2) {
      return GooglePlayServicesUtilLight.zza(var1, var2);
   }

   public boolean isUserResolvableError(int var1) {
      return GooglePlayServicesUtilLight.isUserRecoverableError(var1);
   }

   public void verifyGooglePlayServicesIsAvailable(Context var1, int var2) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
      GooglePlayServicesUtilLight.ensurePlayServicesAvailable(var1, var2);
   }
}
