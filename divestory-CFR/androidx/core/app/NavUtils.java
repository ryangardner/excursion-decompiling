/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.Log
 */
package androidx.core.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public final class NavUtils {
    public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
    private static final String TAG = "NavUtils";

    private NavUtils() {
    }

    public static Intent getParentActivityIntent(Activity activity) {
        Object object;
        if (Build.VERSION.SDK_INT >= 16 && (object = activity.getParentActivityIntent()) != null) {
            return object;
        }
        object = NavUtils.getParentActivityName(activity);
        if (object == null) {
            return null;
        }
        ComponentName componentName = new ComponentName((Context)activity, (String)object);
        try {
            if (NavUtils.getParentActivityName((Context)activity, componentName) == null) {
                return Intent.makeMainActivity((ComponentName)componentName);
            }
            activity = new Intent();
            return activity.setComponent(componentName);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getParentActivityIntent: bad parentActivityName '");
            stringBuilder.append((String)object);
            stringBuilder.append("' in manifest");
            Log.e((String)TAG, (String)stringBuilder.toString());
            return null;
        }
    }

    public static Intent getParentActivityIntent(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String string2 = NavUtils.getParentActivityName(context, componentName);
        if (string2 == null) {
            return null;
        }
        if (NavUtils.getParentActivityName(context, componentName = new ComponentName(componentName.getPackageName(), string2)) != null) return new Intent().setComponent(componentName);
        return Intent.makeMainActivity((ComponentName)componentName);
    }

    public static Intent getParentActivityIntent(Context context, Class<?> object) throws PackageManager.NameNotFoundException {
        if ((object = NavUtils.getParentActivityName(context, new ComponentName(context, object))) == null) {
            return null;
        }
        if (NavUtils.getParentActivityName(context, (ComponentName)(object = new ComponentName(context, (String)object))) != null) return new Intent().setComponent((ComponentName)object);
        return Intent.makeMainActivity((ComponentName)object);
    }

    public static String getParentActivityName(Activity object) {
        try {
            return NavUtils.getParentActivityName((Context)object, object.getComponentName());
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new IllegalArgumentException(nameNotFoundException);
        }
    }

    public static String getParentActivityName(Context context, ComponentName object) throws PackageManager.NameNotFoundException {
        Object object2 = context.getPackageManager();
        int n = Build.VERSION.SDK_INT;
        n = 640;
        if (Build.VERSION.SDK_INT >= 29) {
            n = 269222528;
        } else if (Build.VERSION.SDK_INT >= 24) {
            n = 787072;
        }
        object = object2.getActivityInfo((ComponentName)object, n);
        if (Build.VERSION.SDK_INT >= 16 && (object2 = ((ActivityInfo)object).parentActivityName) != null) {
            return object2;
        }
        if (((ActivityInfo)object).metaData == null) {
            return null;
        }
        object2 = ((ActivityInfo)object).metaData.getString(PARENT_ACTIVITY);
        if (object2 == null) {
            return null;
        }
        object = object2;
        if (((String)object2).charAt(0) != '.') return object;
        object = new StringBuilder();
        ((StringBuilder)object).append(context.getPackageName());
        ((StringBuilder)object).append((String)object2);
        return ((StringBuilder)object).toString();
    }

    public static void navigateUpFromSameTask(Activity activity) {
        Object object = NavUtils.getParentActivityIntent(activity);
        if (object != null) {
            NavUtils.navigateUpTo(activity, (Intent)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Activity ");
        ((StringBuilder)object).append(activity.getClass().getSimpleName());
        ((StringBuilder)object).append(" does not have a parent activity name specified. (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data>  element in your manifest?)");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static void navigateUpTo(Activity activity, Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.navigateUpTo(intent);
            return;
        }
        intent.addFlags(67108864);
        activity.startActivity(intent);
        activity.finish();
    }

    public static boolean shouldUpRecreateTask(Activity object, Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return object.shouldUpRecreateTask(intent);
        }
        if ((object = object.getIntent().getAction()) == null) return false;
        if (((String)object).equals("android.intent.action.MAIN")) return false;
        return true;
    }
}

