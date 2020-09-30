package com.google.android.gms.common;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageInstaller.SessionInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.UidVerifier;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GooglePlayServicesUtilLight {
   static final int GMS_AVAILABILITY_NOTIFICATION_ID = 10436;
   static final int GMS_GENERAL_ERROR_NOTIFICATION_ID = 39789;
   public static final String GOOGLE_PLAY_GAMES_PACKAGE = "com.google.android.play.games";
   @Deprecated
   public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
   @Deprecated
   public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 12451000;
   public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
   static final AtomicBoolean sCanceledAvailabilityNotification = new AtomicBoolean();
   private static boolean zza;
   private static boolean zzb;
   private static boolean zzc;
   private static boolean zzd;
   private static final AtomicBoolean zze = new AtomicBoolean();

   GooglePlayServicesUtilLight() {
   }

   @Deprecated
   public static void cancelAvailabilityErrorNotifications(Context var0) {
      if (!sCanceledAvailabilityNotification.getAndSet(true)) {
         boolean var10001;
         NotificationManager var3;
         try {
            var3 = (NotificationManager)var0.getSystemService("notification");
         } catch (SecurityException var2) {
            var10001 = false;
            return;
         }

         if (var3 != null) {
            try {
               var3.cancel(10436);
            } catch (SecurityException var1) {
               var10001 = false;
            }
         }

      }
   }

   public static void enableUsingApkIndependentContext() {
      zze.set(true);
   }

   @Deprecated
   public static void ensurePlayServicesAvailable(Context var0, int var1) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
      var1 = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(var0, var1);
      if (var1 != 0) {
         Intent var3 = GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(var0, var1, "e");
         StringBuilder var2 = new StringBuilder(57);
         var2.append("GooglePlayServices not available due to error ");
         var2.append(var1);
         Log.e("GooglePlayServicesUtil", var2.toString());
         if (var3 == null) {
            throw new GooglePlayServicesNotAvailableException(var1);
         } else {
            throw new GooglePlayServicesRepairableException(var1, "Google Play Services not available", var3);
         }
      }
   }

   @Deprecated
   public static int getApkVersion(Context var0) {
      PackageInfo var2;
      try {
         var2 = var0.getPackageManager().getPackageInfo("com.google.android.gms", 0);
      } catch (NameNotFoundException var1) {
         Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
         return 0;
      }

      return var2.versionCode;
   }

   @Deprecated
   public static int getClientVersion(Context var0) {
      Preconditions.checkState(true);
      return ClientLibraryUtils.getClientVersion(var0, var0.getPackageName());
   }

   @Deprecated
   public static PendingIntent getErrorPendingIntent(int var0, Context var1, int var2) {
      return GoogleApiAvailabilityLight.getInstance().getErrorResolutionPendingIntent(var1, var0, var2);
   }

   @Deprecated
   public static String getErrorString(int var0) {
      return ConnectionResult.zza(var0);
   }

   @Deprecated
   public static Intent getGooglePlayServicesAvailabilityRecoveryIntent(int var0) {
      return GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent((Context)null, var0, (String)null);
   }

   public static Context getRemoteContext(Context var0) {
      try {
         var0 = var0.createPackageContext("com.google.android.gms", 3);
         return var0;
      } catch (NameNotFoundException var1) {
         return null;
      }
   }

   public static Resources getRemoteResource(Context var0) {
      try {
         Resources var2 = var0.getPackageManager().getResourcesForApplication("com.google.android.gms");
         return var2;
      } catch (NameNotFoundException var1) {
         return null;
      }
   }

   public static boolean honorsDebugCertificates(Context param0) {
      // $FF: Couldn't be decompiled
   }

   @Deprecated
   public static int isGooglePlayServicesAvailable(Context var0) {
      return isGooglePlayServicesAvailable(var0, GOOGLE_PLAY_SERVICES_VERSION_CODE);
   }

   @Deprecated
   public static int isGooglePlayServicesAvailable(Context var0, int var1) {
      boolean var6 = false;

      label65:
      try {
         var6 = true;
         var0.getResources().getString(R.string.common_google_play_services_unknown_issue);
         var6 = false;
      } finally {
         if (var6) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
            break label65;
         }
      }

      if (!"com.google.android.gms".equals(var0.getPackageName()) && !zze.get()) {
         int var3 = zzt.zzb(var0);
         if (var3 == 0) {
            throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
         }

         if (var3 != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
            var1 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
            StringBuilder var8 = new StringBuilder(320);
            var8.append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ");
            var8.append(var1);
            var8.append(" but found ");
            var8.append(var3);
            var8.append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            throw new IllegalStateException(var8.toString());
         }
      }

      boolean var4;
      if (!DeviceProperties.isWearableWithoutPlayStore(var0) && !DeviceProperties.zza(var0)) {
         var4 = true;
      } else {
         var4 = false;
      }

      return zza(var0, var4, var1);
   }

   @Deprecated
   public static boolean isGooglePlayServicesUid(Context var0, int var1) {
      return UidVerifier.isGooglePlayServicesUid(var0, var1);
   }

   @Deprecated
   public static boolean isPlayServicesPossiblyUpdating(Context var0, int var1) {
      if (var1 == 18) {
         return true;
      } else {
         return var1 == 1 ? zza(var0, "com.google.android.gms") : false;
      }
   }

   @Deprecated
   public static boolean isPlayStorePossiblyUpdating(Context var0, int var1) {
      return var1 == 9 ? zza(var0, "com.android.vending") : false;
   }

   public static boolean isRestrictedUserProfile(Context var0) {
      if (PlatformVersion.isAtLeastJellyBeanMR2()) {
         Bundle var1 = ((UserManager)Preconditions.checkNotNull(var0.getSystemService("user"))).getApplicationRestrictions(var0.getPackageName());
         if (var1 != null && "true".equals(var1.getString("restricted_profile"))) {
            return true;
         }
      }

      return false;
   }

   @Deprecated
   public static boolean isSidewinderDevice(Context var0) {
      return DeviceProperties.isSidewinder(var0);
   }

   @Deprecated
   public static boolean isUserRecoverableError(int var0) {
      return var0 == 1 || var0 == 2 || var0 == 3 || var0 == 9;
   }

   @Deprecated
   public static boolean uidHasPackageName(Context var0, int var1, String var2) {
      return UidVerifier.uidHasPackageName(var0, var1, var2);
   }

   private static int zza(Context var0, boolean var1, int var2) {
      boolean var3;
      if (var2 >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);
      String var4 = var0.getPackageName();
      PackageManager var5 = var0.getPackageManager();
      PackageInfo var6 = null;
      if (var1) {
         try {
            var6 = var5.getPackageInfo("com.android.vending", 8256);
         } catch (NameNotFoundException var11) {
            Log.w("GooglePlayServicesUtil", String.valueOf(var4).concat(" requires the Google Play Store, but it is missing."));
            return 9;
         }
      }

      PackageInfo var7;
      try {
         var7 = var5.getPackageInfo("com.google.android.gms", 64);
      } catch (NameNotFoundException var10) {
         Log.w("GooglePlayServicesUtil", String.valueOf(var4).concat(" requires Google Play services, but they are missing."));
         return 1;
      }

      GoogleSignatureVerifier.getInstance(var0);
      if (!GoogleSignatureVerifier.zza(var7, true)) {
         Log.w("GooglePlayServicesUtil", String.valueOf(var4).concat(" requires Google Play services, but their signature is invalid."));
         return 9;
      } else if (var1 && (!GoogleSignatureVerifier.zza((PackageInfo)Preconditions.checkNotNull(var6), true) || !var6.signatures[0].equals(var7.signatures[0]))) {
         Log.w("GooglePlayServicesUtil", String.valueOf(var4).concat(" requires Google Play Store, but its signature is invalid."));
         return 9;
      } else if (com.google.android.gms.common.util.zza.zza(var7.versionCode) < com.google.android.gms.common.util.zza.zza(var2)) {
         int var8 = var7.versionCode;
         StringBuilder var13 = new StringBuilder(String.valueOf(var4).length() + 82);
         var13.append("Google Play services out of date for ");
         var13.append(var4);
         var13.append(".  Requires ");
         var13.append(var2);
         var13.append(" but found ");
         var13.append(var8);
         Log.w("GooglePlayServicesUtil", var13.toString());
         return 2;
      } else {
         ApplicationInfo var14 = var7.applicationInfo;
         ApplicationInfo var12 = var14;
         if (var14 == null) {
            try {
               var12 = var5.getApplicationInfo("com.google.android.gms", 0);
            } catch (NameNotFoundException var9) {
               Log.wtf("GooglePlayServicesUtil", String.valueOf(var4).concat(" requires Google Play services, but they're missing when getting application info."), var9);
               return 1;
            }
         }

         return !var12.enabled ? 3 : 0;
      }
   }

   static boolean zza(Context var0, String var1) {
      boolean var2 = var1.equals("com.google.android.gms");
      if (PlatformVersion.isAtLeastLollipop()) {
         List var3;
         try {
            var3 = var0.getPackageManager().getPackageInstaller().getAllSessions();
         } catch (Exception var4) {
            return false;
         }

         Iterator var9 = var3.iterator();

         while(var9.hasNext()) {
            if (var1.equals(((SessionInfo)var9.next()).getAppPackageName())) {
               return true;
            }
         }
      }

      PackageManager var10 = var0.getPackageManager();

      boolean var10001;
      ApplicationInfo var8;
      try {
         var8 = var10.getApplicationInfo(var1, 8192);
      } catch (NameNotFoundException var7) {
         var10001 = false;
         return false;
      }

      if (var2) {
         try {
            return var8.enabled;
         } catch (NameNotFoundException var5) {
            var10001 = false;
         }
      } else {
         try {
            if (!var8.enabled) {
               return false;
            }

            var2 = isRestrictedUserProfile(var0);
         } catch (NameNotFoundException var6) {
            var10001 = false;
            return false;
         }

         if (!var2) {
            return true;
         }
      }

      return false;
   }
}
