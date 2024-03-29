/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package androidx.core.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AppLaunchChecker {
    private static final String KEY_STARTED_FROM_LAUNCHER = "startedFromLauncher";
    private static final String SHARED_PREFS_NAME = "android.support.AppLaunchChecker";

    public static boolean hasStartedFromLauncher(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, 0).getBoolean(KEY_STARTED_FROM_LAUNCHER, false);
    }

    public static void onActivityCreate(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFS_NAME, 0);
        if (sharedPreferences.getBoolean(KEY_STARTED_FROM_LAUNCHER, false)) {
            return;
        }
        if ((activity = activity.getIntent()) == null) {
            return;
        }
        if (!"android.intent.action.MAIN".equals(activity.getAction())) return;
        if (!activity.hasCategory("android.intent.category.LAUNCHER")) {
            if (!activity.hasCategory("android.intent.category.LEANBACK_LAUNCHER")) return;
        }
        sharedPreferences.edit().putBoolean(KEY_STARTED_FROM_LAUNCHER, true).apply();
    }
}

