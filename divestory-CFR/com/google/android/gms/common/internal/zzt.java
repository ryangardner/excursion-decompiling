/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.wrappers.Wrappers;

public final class zzt {
    private static Object zza = new Object();
    private static boolean zzb;
    private static String zzc;
    private static int zzd;

    public static String zza(Context context) {
        zzt.zzc(context);
        return zzc;
    }

    public static int zzb(Context context) {
        zzt.zzc(context);
        return zzd;
    }

    private static void zzc(Context object) {
        Object object2 = zza;
        synchronized (object2) {
            block7 : {
                if (zzb) {
                    return;
                }
                zzb = true;
                String string2 = object.getPackageName();
                object = Wrappers.packageManager((Context)object);
                object = object.getApplicationInfo((String)string2, (int)128).metaData;
                if (object != null) break block7;
                return;
            }
            try {
                zzc = object.getString("com.google.app.id");
                zzd = object.getInt("com.google.android.gms.version");
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.wtf((String)"MetadataValueReader", (String)"This should never happen.", (Throwable)nameNotFoundException);
            }
            return;
        }
    }
}

