/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.os.Build;
import android.view.ViewGroup;
import androidx.transition.ViewGroupOverlayApi14;
import androidx.transition.ViewGroupOverlayApi18;
import androidx.transition.ViewGroupOverlayImpl;
import androidx.transition.ViewGroupUtilsApi14;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewGroupUtils {
    private static Method sGetChildDrawingOrderMethod;
    private static boolean sGetChildDrawingOrderMethodFetched = false;
    private static boolean sTryHiddenSuppressLayout = true;

    private ViewGroupUtils() {
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    static int getChildDrawingOrder(ViewGroup var0, int var1_2) {
        if (Build.VERSION.SDK_INT >= 29) {
            return var0.getChildDrawingOrder(var1_2);
        }
        if (ViewGroupUtils.sGetChildDrawingOrderMethodFetched) ** GOTO lbl-1000
        try {
            ViewGroupUtils.sGetChildDrawingOrderMethod = var2_3 = ViewGroup.class.getDeclaredMethod("getChildDrawingOrder", new Class[]{Integer.TYPE, Integer.TYPE});
            var2_3.setAccessible(true);
lbl7: // 2 sources:
            do {
                ViewGroupUtils.sGetChildDrawingOrderMethodFetched = true;
                break;
            } while (true);
        }
        catch (NoSuchMethodException var2_4) {
            ** continue;
        }
lbl-1000: // 2 sources:
        {
            if ((var2_3 = ViewGroupUtils.sGetChildDrawingOrderMethod) == null) return var1_2;
            return (Integer)var2_3.invoke((Object)var0, new Object[]{var0.getChildCount(), var1_2});
        }
        catch (IllegalAccessException | InvocationTargetException var0_1) {
            return var1_2;
        }
    }

    static ViewGroupOverlayImpl getOverlay(ViewGroup viewGroup) {
        if (Build.VERSION.SDK_INT < 18) return ViewGroupOverlayApi14.createFrom(viewGroup);
        return new ViewGroupOverlayApi18(viewGroup);
    }

    private static void hiddenSuppressLayout(ViewGroup viewGroup, boolean bl) {
        if (!sTryHiddenSuppressLayout) return;
        try {
            viewGroup.suppressLayout(bl);
            return;
        }
        catch (NoSuchMethodError noSuchMethodError) {
            sTryHiddenSuppressLayout = false;
        }
    }

    static void suppressLayout(ViewGroup viewGroup, boolean bl) {
        if (Build.VERSION.SDK_INT >= 29) {
            viewGroup.suppressLayout(bl);
            return;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            ViewGroupUtils.hiddenSuppressLayout(viewGroup, bl);
            return;
        }
        ViewGroupUtilsApi14.suppressLayout(viewGroup, bl);
    }
}

