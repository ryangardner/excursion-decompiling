/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.transition;

import android.graphics.Canvas;
import android.os.Build;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class CanvasUtils {
    private static Method sInorderBarrierMethod;
    private static boolean sOrderMethodsFetched;
    private static Method sReorderBarrierMethod;

    private CanvasUtils() {
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    static void enableZ(Canvas var0, boolean var1_3) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            if (var1_3) {
                var0.enableZ();
                return;
            }
            var0.disableZ();
            return;
        }
        if (Build.VERSION.SDK_INT == 28) throw new IllegalStateException("This method doesn't work on Pie!");
        if (CanvasUtils.sOrderMethodsFetched) ** GOTO lbl-1000
        try {
            CanvasUtils.sReorderBarrierMethod = var2_4 = Canvas.class.getDeclaredMethod("insertReorderBarrier", new Class[0]);
            var2_4.setAccessible(true);
            CanvasUtils.sInorderBarrierMethod = var2_4 = Canvas.class.getDeclaredMethod("insertInorderBarrier", new Class[0]);
            var2_4.setAccessible(true);
lbl16: // 2 sources:
            do {
                CanvasUtils.sOrderMethodsFetched = true;
                break;
            } while (true);
        }
        catch (NoSuchMethodException var2_5) {
            ** continue;
        }
lbl-1000: // 2 sources:
        {
            if (!var1_3) ** GOTO lbl25
            try {
                if (CanvasUtils.sReorderBarrierMethod != null) {
                    CanvasUtils.sReorderBarrierMethod.invoke((Object)var0, new Object[0]);
                }
lbl25: // 4 sources:
                if (var1_3 != false) return;
                if (CanvasUtils.sInorderBarrierMethod == null) return;
                CanvasUtils.sInorderBarrierMethod.invoke((Object)var0, new Object[0]);
                return;
            }
            catch (InvocationTargetException var0_1) {
                throw new RuntimeException(var0_1.getCause());
            }
        }
        catch (IllegalAccessException var0_2) {
            return;
        }
    }
}

