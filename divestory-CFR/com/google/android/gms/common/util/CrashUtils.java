/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.DropBoxManager
 *  android.util.Log
 */
package com.google.android.gms.common.util;

import android.content.Context;
import android.os.DropBoxManager;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;

public final class CrashUtils {
    private static final String[] zza = new String[]{"android.", "com.android.", "dalvik.", "java.", "javax."};
    private static DropBoxManager zzb = null;
    private static boolean zzc = false;
    private static int zzd = -1;
    private static int zze = 0;
    private static int zzf = 0;

    public static boolean addDynamiteErrorToDropBox(Context context, Throwable throwable) {
        return CrashUtils.zza(context, throwable, 536870912);
    }

    private static boolean zza(Context context, Throwable throwable, int n) {
        try {
            Preconditions.checkNotNull(context);
            Preconditions.checkNotNull(throwable);
            return false;
        }
        catch (Exception exception) {
            Log.e((String)"CrashUtils", (String)"Error adding exception to DropBox!", (Throwable)exception);
            return false;
        }
    }
}

