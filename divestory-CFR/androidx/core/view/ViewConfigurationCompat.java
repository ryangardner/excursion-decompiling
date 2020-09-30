/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.ViewConfiguration
 */
package androidx.core.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewConfiguration;
import java.lang.reflect.Method;

public final class ViewConfigurationCompat {
    private static final String TAG = "ViewConfigCompat";
    private static Method sGetScaledScrollFactorMethod;

    static {
        if (Build.VERSION.SDK_INT != 25) return;
        try {
            sGetScaledScrollFactorMethod = ViewConfiguration.class.getDeclaredMethod("getScaledScrollFactor", new Class[0]);
            return;
        }
        catch (Exception exception) {
            Log.i((String)TAG, (String)"Could not find method getScaledScrollFactor() on ViewConfiguration");
        }
    }

    private ViewConfigurationCompat() {
    }

    private static float getLegacyScrollFactor(ViewConfiguration object, Context context) {
        Method method;
        if (Build.VERSION.SDK_INT >= 25 && (method = sGetScaledScrollFactorMethod) != null) {
            int n;
            try {
                n = (Integer)method.invoke(object, new Object[0]);
            }
            catch (Exception exception) {
                Log.i((String)TAG, (String)"Could not find method getScaledScrollFactor() on ViewConfiguration");
            }
            return n;
        }
        object = new TypedValue();
        if (!context.getTheme().resolveAttribute(16842829, (TypedValue)object, true)) return 0.0f;
        return object.getDimension(context.getResources().getDisplayMetrics());
    }

    public static float getScaledHorizontalScrollFactor(ViewConfiguration viewConfiguration, Context context) {
        if (Build.VERSION.SDK_INT < 26) return ViewConfigurationCompat.getLegacyScrollFactor(viewConfiguration, context);
        return viewConfiguration.getScaledHorizontalScrollFactor();
    }

    public static int getScaledHoverSlop(ViewConfiguration viewConfiguration) {
        if (Build.VERSION.SDK_INT < 28) return viewConfiguration.getScaledTouchSlop() / 2;
        return viewConfiguration.getScaledHoverSlop();
    }

    @Deprecated
    public static int getScaledPagingTouchSlop(ViewConfiguration viewConfiguration) {
        return viewConfiguration.getScaledPagingTouchSlop();
    }

    public static float getScaledVerticalScrollFactor(ViewConfiguration viewConfiguration, Context context) {
        if (Build.VERSION.SDK_INT < 26) return ViewConfigurationCompat.getLegacyScrollFactor(viewConfiguration, context);
        return viewConfiguration.getScaledVerticalScrollFactor();
    }

    @Deprecated
    public static boolean hasPermanentMenuKey(ViewConfiguration viewConfiguration) {
        return viewConfiguration.hasPermanentMenuKey();
    }

    public static boolean shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration viewConfiguration, Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return viewConfiguration.shouldShowMenuShortcutsWhenKeyboardPresent();
        }
        viewConfiguration = context.getResources();
        int n = viewConfiguration.getIdentifier("config_showMenuShortcutsWhenKeyboardPresent", "bool", "android");
        if (n == 0) return false;
        if (!viewConfiguration.getBoolean(n)) return false;
        return true;
    }
}

