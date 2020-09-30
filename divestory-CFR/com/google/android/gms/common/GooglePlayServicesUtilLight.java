/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageInstaller
 *  android.content.pm.PackageInstaller$SessionInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.UserManager
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.R;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.common.util.zza;
import com.google.android.gms.common.wrappers.Wrappers;
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
    private static boolean zza = false;
    private static boolean zzb = false;
    private static boolean zzc = false;
    private static boolean zzd = false;
    private static final AtomicBoolean zze = new AtomicBoolean();

    GooglePlayServicesUtilLight() {
    }

    @Deprecated
    public static void cancelAvailabilityErrorNotifications(Context context) {
        if (sCanceledAvailabilityNotification.getAndSet(true)) {
            return;
        }
        try {
            context = (NotificationManager)context.getSystemService("notification");
            if (context == null) return;
            context.cancel(10436);
            return;
        }
        catch (SecurityException securityException) {
            return;
        }
    }

    public static void enableUsingApkIndependentContext() {
        zze.set(true);
    }

    @Deprecated
    public static void ensurePlayServicesAvailable(Context context, int n) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        n = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context, n);
        if (n == 0) return;
        context = GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(context, n, "e");
        StringBuilder stringBuilder = new StringBuilder(57);
        stringBuilder.append("GooglePlayServices not available due to error ");
        stringBuilder.append(n);
        Log.e((String)"GooglePlayServicesUtil", (String)stringBuilder.toString());
        if (context != null) throw new GooglePlayServicesRepairableException(n, "Google Play Services not available", (Intent)context);
        throw new GooglePlayServicesNotAvailableException(n);
    }

    @Deprecated
    public static int getApkVersion(Context context) {
        try {
            context = context.getPackageManager().getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 0);
            return context.versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services is missing.");
            return 0;
        }
    }

    @Deprecated
    public static int getClientVersion(Context context) {
        Preconditions.checkState(true);
        return ClientLibraryUtils.getClientVersion(context, context.getPackageName());
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int n, Context context, int n2) {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionPendingIntent(context, n, n2);
    }

    @Deprecated
    public static String getErrorString(int n) {
        return ConnectionResult.zza(n);
    }

    @Deprecated
    public static Intent getGooglePlayServicesAvailabilityRecoveryIntent(int n) {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(null, n, null);
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext(GOOGLE_PLAY_SERVICES_PACKAGE, 3);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication(GOOGLE_PLAY_SERVICES_PACKAGE);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static boolean honorsDebugCertificates(Context context) {
        block5 : {
            if (!zzd) {
                Throwable throwable2222;
                PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 64);
                GoogleSignatureVerifier.getInstance(context);
                zzc = packageInfo != null && !GoogleSignatureVerifier.zza(packageInfo, false) && GoogleSignatureVerifier.zza(packageInfo, true);
                zzd = true;
                break block5;
                {
                    catch (Throwable throwable2222) {
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {}
                    {
                        Log.w((String)"GooglePlayServicesUtil", (String)"Cannot find Google Play services package name.", (Throwable)nameNotFoundException);
                        zzd = true;
                        break block5;
                    }
                }
                zzd = true;
                throw throwable2222;
            }
        }
        if (zzc) return true;
        if (DeviceProperties.isUserBuild()) return false;
        return true;
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(context, GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context object, int n) {
        boolean bl;
        try {
            object.getResources().getString(R.string.common_google_play_services_unknown_issue);
        }
        catch (Throwable throwable) {
            Log.e((String)"GooglePlayServicesUtil", (String)"The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (!GOOGLE_PLAY_SERVICES_PACKAGE.equals(object.getPackageName()) && !zze.get()) {
            int n2 = zzt.zzb((Context)object);
            if (n2 == 0) throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            if (n2 != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                n = GOOGLE_PLAY_SERVICES_VERSION_CODE;
                object = new StringBuilder(320);
                ((StringBuilder)object).append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" but found ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
        }
        if (!DeviceProperties.isWearableWithoutPlayStore((Context)object) && !DeviceProperties.zza((Context)object)) {
            bl = true;
            return GooglePlayServicesUtilLight.zza((Context)object, bl, n);
        }
        bl = false;
        return GooglePlayServicesUtilLight.zza((Context)object, bl, n);
    }

    @Deprecated
    public static boolean isGooglePlayServicesUid(Context context, int n) {
        return UidVerifier.isGooglePlayServicesUid(context, n);
    }

    @Deprecated
    public static boolean isPlayServicesPossiblyUpdating(Context context, int n) {
        if (n == 18) {
            return true;
        }
        if (n != 1) return false;
        return GooglePlayServicesUtilLight.zza(context, GOOGLE_PLAY_SERVICES_PACKAGE);
    }

    @Deprecated
    public static boolean isPlayStorePossiblyUpdating(Context context, int n) {
        if (n != 9) return false;
        return GooglePlayServicesUtilLight.zza(context, GOOGLE_PLAY_STORE_PACKAGE);
    }

    public static boolean isRestrictedUserProfile(Context context) {
        if (!PlatformVersion.isAtLeastJellyBeanMR2()) return false;
        if ((context = ((UserManager)Preconditions.checkNotNull(context.getSystemService("user"))).getApplicationRestrictions(context.getPackageName())) == null) return false;
        if (!"true".equals(context.getString("restricted_profile"))) return false;
        return true;
    }

    @Deprecated
    public static boolean isSidewinderDevice(Context context) {
        return DeviceProperties.isSidewinder(context);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int n) {
        if (n == 1) return true;
        if (n == 2) return true;
        if (n == 3) return true;
        if (n == 9) return true;
        return false;
    }

    @Deprecated
    public static boolean uidHasPackageName(Context context, int n, String string2) {
        return UidVerifier.uidHasPackageName(context, n, string2);
    }

    private static int zza(Context object, boolean bl, int n) {
        PackageInfo packageInfo;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2);
        String string2 = object.getPackageName();
        PackageManager packageManager = object.getPackageManager();
        PackageInfo packageInfo2 = null;
        if (bl) {
            try {
                packageInfo2 = packageManager.getPackageInfo(GOOGLE_PLAY_STORE_PACKAGE, 8256);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.w((String)"GooglePlayServicesUtil", (String)String.valueOf(string2).concat(" requires the Google Play Store, but it is missing."));
                return 9;
            }
        }
        try {
            packageInfo = packageManager.getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 64);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.w((String)"GooglePlayServicesUtil", (String)String.valueOf(string2).concat(" requires Google Play services, but they are missing."));
            return 1;
        }
        GoogleSignatureVerifier.getInstance((Context)object);
        if (!GoogleSignatureVerifier.zza(packageInfo, true)) {
            Log.w((String)"GooglePlayServicesUtil", (String)String.valueOf(string2).concat(" requires Google Play services, but their signature is invalid."));
            return 9;
        }
        if (!(!bl || GoogleSignatureVerifier.zza(Preconditions.checkNotNull(packageInfo2), true) && packageInfo2.signatures[0].equals((Object)packageInfo.signatures[0]))) {
            Log.w((String)"GooglePlayServicesUtil", (String)String.valueOf(string2).concat(" requires Google Play Store, but its signature is invalid."));
            return 9;
        }
        if (zza.zza(packageInfo.versionCode) < zza.zza(n)) {
            int n2 = packageInfo.versionCode;
            object = new StringBuilder(String.valueOf(string2).length() + 82);
            ((StringBuilder)object).append("Google Play services out of date for ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(".  Requires ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" but found ");
            ((StringBuilder)object).append(n2);
            Log.w((String)"GooglePlayServicesUtil", (String)((StringBuilder)object).toString());
            return 2;
        }
        packageInfo2 = packageInfo.applicationInfo;
        object = packageInfo2;
        if (packageInfo2 == null) {
            try {
                object = packageManager.getApplicationInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 0);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.wtf((String)"GooglePlayServicesUtil", (String)String.valueOf(string2).concat(" requires Google Play services, but they're missing when getting application info."), (Throwable)nameNotFoundException);
                return 1;
            }
        }
        if (((ApplicationInfo)object).enabled) return 0;
        return 3;
    }

    static boolean zza(Context context, String string2) {
        Object object;
        boolean bl = string2.equals(GOOGLE_PLAY_SERVICES_PACKAGE);
        if (PlatformVersion.isAtLeastLollipop()) {
            try {
                object = context.getPackageManager().getPackageInstaller().getAllSessions();
                object = object.iterator();
            }
            catch (Exception exception) {
                return false;
            }
            while (object.hasNext()) {
                if (!string2.equals(((PackageInstaller.SessionInfo)object.next()).getAppPackageName())) continue;
                return true;
            }
        }
        object = context.getPackageManager();
        try {
            string2 = object.getApplicationInfo(string2, 8192);
            if (bl) {
                return ((ApplicationInfo)string2).enabled;
            }
            if (!((ApplicationInfo)string2).enabled) return false;
            bl = GooglePlayServicesUtilLight.isRestrictedUserProfile(context);
            if (bl) return false;
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }
}

