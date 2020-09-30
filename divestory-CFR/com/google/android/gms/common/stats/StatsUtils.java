/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.os.Process
 *  android.text.TextUtils
 */
package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;

@Deprecated
public class StatsUtils {
    public static String getEventKey(Context context, Intent intent) {
        long l = System.identityHashCode((Object)context);
        return String.valueOf((long)System.identityHashCode((Object)intent) | l << 32);
    }

    public static String getEventKey(PowerManager.WakeLock object, String string2) {
        String string3 = String.valueOf(String.valueOf((long)Process.myPid() << 32 | (long)System.identityHashCode(object)));
        object = string2;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            object = "";
        }
        if (((String)(object = String.valueOf(object))).length() == 0) return new String(string3);
        return string3.concat((String)object);
    }
}

