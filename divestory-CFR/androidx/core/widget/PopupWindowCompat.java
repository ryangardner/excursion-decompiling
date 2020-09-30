/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 *  android.widget.PopupWindow
 */
package androidx.core.widget;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class PopupWindowCompat {
    private static final String TAG = "PopupWindowCompatApi21";
    private static Method sGetWindowLayoutTypeMethod;
    private static boolean sGetWindowLayoutTypeMethodAttempted;
    private static Field sOverlapAnchorField;
    private static boolean sOverlapAnchorFieldAttempted;
    private static Method sSetWindowLayoutTypeMethod;
    private static boolean sSetWindowLayoutTypeMethodAttempted;

    private PopupWindowCompat() {
    }

    public static boolean getOverlapAnchor(PopupWindow popupWindow) {
        Field field;
        if (Build.VERSION.SDK_INT >= 23) {
            return popupWindow.getOverlapAnchor();
        }
        if (Build.VERSION.SDK_INT < 21) return false;
        if (!sOverlapAnchorFieldAttempted) {
            try {
                sOverlapAnchorField = field = PopupWindow.class.getDeclaredField("mOverlapAnchor");
                field.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.i((String)TAG, (String)"Could not fetch mOverlapAnchor field from PopupWindow", (Throwable)noSuchFieldException);
            }
            sOverlapAnchorFieldAttempted = true;
        }
        if ((field = sOverlapAnchorField) == null) return false;
        try {
            return (Boolean)field.get((Object)popupWindow);
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.i((String)TAG, (String)"Could not get overlap anchor field in PopupWindow", (Throwable)illegalAccessException);
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static int getWindowLayoutType(PopupWindow var0) {
        if (Build.VERSION.SDK_INT >= 23) {
            return var0.getWindowLayoutType();
        }
        if (PopupWindowCompat.sGetWindowLayoutTypeMethodAttempted) ** GOTO lbl-1000
        try {
            PopupWindowCompat.sGetWindowLayoutTypeMethod = var1_2 = PopupWindow.class.getDeclaredMethod("getWindowLayoutType", new Class[0]);
            var1_2.setAccessible(true);
lbl7: // 2 sources:
            do {
                PopupWindowCompat.sGetWindowLayoutTypeMethodAttempted = true;
                break;
            } while (true);
        }
        catch (Exception var1_3) {
            ** continue;
        }
lbl-1000: // 2 sources:
        {
            if ((var1_2 = PopupWindowCompat.sGetWindowLayoutTypeMethod) == null) return 0;
            return (Integer)var1_2.invoke((Object)var0, new Object[0]);
        }
        catch (Exception var0_1) {
            return 0;
        }
    }

    public static void setOverlapAnchor(PopupWindow popupWindow, boolean bl) {
        Field field;
        if (Build.VERSION.SDK_INT >= 23) {
            popupWindow.setOverlapAnchor(bl);
            return;
        }
        if (Build.VERSION.SDK_INT < 21) return;
        if (!sOverlapAnchorFieldAttempted) {
            try {
                sOverlapAnchorField = field = PopupWindow.class.getDeclaredField("mOverlapAnchor");
                field.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.i((String)TAG, (String)"Could not fetch mOverlapAnchor field from PopupWindow", (Throwable)noSuchFieldException);
            }
            sOverlapAnchorFieldAttempted = true;
        }
        if ((field = sOverlapAnchorField) == null) return;
        try {
            field.set((Object)popupWindow, bl);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.i((String)TAG, (String)"Could not set overlap anchor field in PopupWindow", (Throwable)illegalAccessException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static void setWindowLayoutType(PopupWindow var0, int var1_2) {
        if (Build.VERSION.SDK_INT >= 23) {
            var0.setWindowLayoutType(var1_2);
            return;
        }
        if (PopupWindowCompat.sSetWindowLayoutTypeMethodAttempted) ** GOTO lbl-1000
        try {
            PopupWindowCompat.sSetWindowLayoutTypeMethod = var2_3 = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", new Class[]{Integer.TYPE});
            var2_3.setAccessible(true);
lbl8: // 2 sources:
            do {
                PopupWindowCompat.sSetWindowLayoutTypeMethodAttempted = true;
                break;
            } while (true);
        }
        catch (Exception var2_4) {
            ** continue;
        }
lbl-1000: // 2 sources:
        {
            if ((var2_3 = PopupWindowCompat.sSetWindowLayoutTypeMethod) == null) return;
            var2_3.invoke((Object)var0, new Object[]{var1_2});
            return;
        }
        catch (Exception var0_1) {
            return;
        }
    }

    public static void showAsDropDown(PopupWindow popupWindow, View view, int n, int n2, int n3) {
        if (Build.VERSION.SDK_INT >= 19) {
            popupWindow.showAsDropDown(view, n, n2, n3);
            return;
        }
        int n4 = n;
        if ((GravityCompat.getAbsoluteGravity(n3, ViewCompat.getLayoutDirection(view)) & 7) == 5) {
            n4 = n - (popupWindow.getWidth() - view.getWidth());
        }
        popupWindow.showAsDropDown(view, n4, n2);
    }
}

