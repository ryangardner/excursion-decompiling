/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 */
package androidx.appcompat.widget;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import androidx.core.view.ViewCompat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {
    private static final String TAG = "ViewUtils";
    private static Method sComputeFitSystemWindowsMethod;

    static {
        if (Build.VERSION.SDK_INT < 18) return;
        try {
            Method method;
            sComputeFitSystemWindowsMethod = method = View.class.getDeclaredMethod("computeFitSystemWindows", Rect.class, Rect.class);
            if (method.isAccessible()) return;
            sComputeFitSystemWindowsMethod.setAccessible(true);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.d((String)TAG, (String)"Could not find method computeFitSystemWindows. Oh well.");
        }
    }

    private ViewUtils() {
    }

    public static void computeFitSystemWindows(View view, Rect rect, Rect rect2) {
        Method method = sComputeFitSystemWindowsMethod;
        if (method == null) return;
        try {
            method.invoke((Object)view, new Object[]{rect, rect2});
            return;
        }
        catch (Exception exception) {
            Log.d((String)TAG, (String)"Could not invoke computeFitSystemWindows", (Throwable)exception);
        }
    }

    public static boolean isLayoutRtl(View view) {
        int n = ViewCompat.getLayoutDirection(view);
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    public static void makeOptionalFitsSystemWindows(View view) {
        if (Build.VERSION.SDK_INT < 16) return;
        try {
            Method method = view.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            method.invoke((Object)view, new Object[0]);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.d((String)TAG, (String)"Could not invoke makeOptionalFitsSystemWindows", (Throwable)illegalAccessException);
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.d((String)TAG, (String)"Could not invoke makeOptionalFitsSystemWindows", (Throwable)invocationTargetException);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.d((String)TAG, (String)"Could not find method makeOptionalFitsSystemWindows. Oh well...");
        }
    }
}

