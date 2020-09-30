/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.api.client.extensions.android;

import android.os.Build;
import com.google.api.client.util.Preconditions;

public class AndroidUtils {
    private AndroidUtils() {
    }

    public static void checkMinimumSdkLevel(int n) {
        Preconditions.checkArgument(AndroidUtils.isMinimumSdkLevel(n), "running on Android SDK level %s but requires minimum %s", Build.VERSION.SDK_INT, n);
    }

    public static boolean isMinimumSdkLevel(int n) {
        if (Build.VERSION.SDK_INT < n) return false;
        return true;
    }
}

