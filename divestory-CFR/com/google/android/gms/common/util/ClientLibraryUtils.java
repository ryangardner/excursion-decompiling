/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 */
package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.gms.common.wrappers.Wrappers;

public class ClientLibraryUtils {
    private ClientLibraryUtils() {
    }

    public static int getClientVersion(Context context, String string2) {
        if ((context = ClientLibraryUtils.zzb(context, string2)) == null) return -1;
        if (context.applicationInfo == null) {
            return -1;
        }
        context = context.applicationInfo.metaData;
        if (context != null) return context.getInt("com.google.android.gms.version", -1);
        return -1;
    }

    public static boolean isPackageSide() {
        return false;
    }

    public static boolean zza(Context context, String string2) {
        "com.google.android.gms".equals(string2);
        try {
            int n = Wrappers.packageManager((Context)context).getApplicationInfo((String)string2, (int)0).flags;
            if ((n & 2097152) == 0) return false;
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    private static PackageInfo zzb(Context context, String string2) {
        try {
            return Wrappers.packageManager(context).getPackageInfo(string2, 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }
}

