/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Binder
 *  android.os.Process
 */
package com.google.android.gms.common.wrappers;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Process;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.wrappers.InstantApps;

public class PackageManagerWrapper {
    private final Context zza;

    public PackageManagerWrapper(Context context) {
        this.zza = context;
    }

    public int checkCallingOrSelfPermission(String string2) {
        return this.zza.checkCallingOrSelfPermission(string2);
    }

    public int checkPermission(String string2, String string3) {
        return this.zza.getPackageManager().checkPermission(string2, string3);
    }

    public ApplicationInfo getApplicationInfo(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.zza.getPackageManager().getApplicationInfo(string2, n);
    }

    public CharSequence getApplicationLabel(String string2) throws PackageManager.NameNotFoundException {
        return this.zza.getPackageManager().getApplicationLabel(this.zza.getPackageManager().getApplicationInfo(string2, 0));
    }

    public PackageInfo getPackageInfo(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.zza.getPackageManager().getPackageInfo(string2, n);
    }

    public boolean isCallerInstantApp() {
        if (Binder.getCallingUid() == Process.myUid()) {
            return InstantApps.isInstantApp(this.zza);
        }
        if (!PlatformVersion.isAtLeastO()) return false;
        String string2 = this.zza.getPackageManager().getNameForUid(Binder.getCallingUid());
        if (string2 == null) return false;
        return this.zza.getPackageManager().isInstantApp(string2);
    }

    public final boolean zza(int n, String object) {
        if (PlatformVersion.isAtLeastKitKat()) {
            try {
                AppOpsManager appOpsManager = (AppOpsManager)this.zza.getSystemService("appops");
                if (appOpsManager != null) {
                    appOpsManager.checkPackage(n, (String)object);
                    return true;
                }
                object = new NullPointerException("context.getSystemService(Context.APP_OPS_SERVICE) is null");
                throw object;
            }
            catch (SecurityException securityException) {
                return false;
            }
        }
        String[] arrstring = this.zza.getPackageManager().getPackagesForUid(n);
        if (object == null) return false;
        if (arrstring == null) return false;
        n = 0;
        while (n < arrstring.length) {
            if (((String)object).equals(arrstring[n])) {
                return true;
            }
            ++n;
        }
        return false;
    }
}

