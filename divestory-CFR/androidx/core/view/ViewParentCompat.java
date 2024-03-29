/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 */
package androidx.core.view;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;

public final class ViewParentCompat {
    private static final String TAG = "ViewParentCompat";
    private static int[] sTempNestedScrollConsumed;

    private ViewParentCompat() {
    }

    private static int[] getTempNestedScrollConsumed() {
        int[] arrn = sTempNestedScrollConsumed;
        if (arrn == null) {
            sTempNestedScrollConsumed = new int[2];
            return sTempNestedScrollConsumed;
        }
        arrn[0] = 0;
        arrn[1] = 0;
        return sTempNestedScrollConsumed;
    }

    public static void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int n) {
        if (Build.VERSION.SDK_INT < 19) return;
        viewParent.notifySubtreeAccessibilityStateChanged(view, view2, n);
    }

    public static boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean bl) {
        if (Build.VERSION.SDK_INT < 21) {
            if (!(viewParent instanceof NestedScrollingParent)) return false;
            return ((NestedScrollingParent)viewParent).onNestedFling(view, f, f2, bl);
        }
        try {
            return viewParent.onNestedFling(view, f, f2, bl);
        }
        catch (AbstractMethodError abstractMethodError) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewParent ");
            stringBuilder.append((Object)viewParent);
            stringBuilder.append(" does not implement interface method onNestedFling");
            Log.e((String)TAG, (String)stringBuilder.toString(), (Throwable)abstractMethodError);
            return false;
        }
    }

    public static boolean onNestedPreFling(ViewParent viewParent, View object, float f, float f2) {
        if (Build.VERSION.SDK_INT < 21) {
            if (!(viewParent instanceof NestedScrollingParent)) return false;
            return ((NestedScrollingParent)viewParent).onNestedPreFling((View)object, f, f2);
        }
        try {
            return viewParent.onNestedPreFling((View)object, f, f2);
        }
        catch (AbstractMethodError abstractMethodError) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ViewParent ");
            ((StringBuilder)object).append((Object)viewParent);
            ((StringBuilder)object).append(" does not implement interface method onNestedPreFling");
            Log.e((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            return false;
        }
    }

    public static void onNestedPreScroll(ViewParent viewParent, View view, int n, int n2, int[] arrn) {
        ViewParentCompat.onNestedPreScroll(viewParent, view, n, n2, arrn, 0);
    }

    public static void onNestedPreScroll(ViewParent viewParent, View view, int n, int n2, int[] object, int n3) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2)viewParent).onNestedPreScroll(view, n, n2, (int[])object, n3);
            return;
        }
        if (n3 != 0) return;
        if (Build.VERSION.SDK_INT < 21) {
            if (!(viewParent instanceof NestedScrollingParent)) return;
            ((NestedScrollingParent)viewParent).onNestedPreScroll(view, n, n2, (int[])object);
            return;
        }
        try {
            viewParent.onNestedPreScroll(view, n, n2, (int[])object);
            return;
        }
        catch (AbstractMethodError abstractMethodError) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ViewParent ");
            ((StringBuilder)object).append((Object)viewParent);
            ((StringBuilder)object).append(" does not implement interface method onNestedPreScroll");
            Log.e((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            return;
        }
    }

    public static void onNestedScroll(ViewParent viewParent, View view, int n, int n2, int n3, int n4) {
        ViewParentCompat.onNestedScroll(viewParent, view, n, n2, n3, n4, 0, ViewParentCompat.getTempNestedScrollConsumed());
    }

    public static void onNestedScroll(ViewParent viewParent, View view, int n, int n2, int n3, int n4, int n5) {
        ViewParentCompat.onNestedScroll(viewParent, view, n, n2, n3, n4, n5, ViewParentCompat.getTempNestedScrollConsumed());
    }

    public static void onNestedScroll(ViewParent viewParent, View object, int n, int n2, int n3, int n4, int n5, int[] arrn) {
        if (viewParent instanceof NestedScrollingParent3) {
            ((NestedScrollingParent3)viewParent).onNestedScroll((View)object, n, n2, n3, n4, n5, arrn);
            return;
        }
        arrn[0] = arrn[0] + n3;
        arrn[1] = arrn[1] + n4;
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2)viewParent).onNestedScroll((View)object, n, n2, n3, n4, n5);
            return;
        }
        if (n5 != 0) return;
        if (Build.VERSION.SDK_INT < 21) {
            if (!(viewParent instanceof NestedScrollingParent)) return;
            ((NestedScrollingParent)viewParent).onNestedScroll((View)object, n, n2, n3, n4);
            return;
        }
        try {
            viewParent.onNestedScroll((View)object, n, n2, n3, n4);
            return;
        }
        catch (AbstractMethodError abstractMethodError) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ViewParent ");
            ((StringBuilder)object).append((Object)viewParent);
            ((StringBuilder)object).append(" does not implement interface method onNestedScroll");
            Log.e((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            return;
        }
    }

    public static void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int n) {
        ViewParentCompat.onNestedScrollAccepted(viewParent, view, view2, n, 0);
    }

    public static void onNestedScrollAccepted(ViewParent viewParent, View view, View object, int n, int n2) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2)viewParent).onNestedScrollAccepted(view, (View)object, n, n2);
            return;
        }
        if (n2 != 0) return;
        if (Build.VERSION.SDK_INT < 21) {
            if (!(viewParent instanceof NestedScrollingParent)) return;
            ((NestedScrollingParent)viewParent).onNestedScrollAccepted(view, (View)object, n);
            return;
        }
        try {
            viewParent.onNestedScrollAccepted(view, (View)object, n);
            return;
        }
        catch (AbstractMethodError abstractMethodError) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ViewParent ");
            ((StringBuilder)object).append((Object)viewParent);
            ((StringBuilder)object).append(" does not implement interface method onNestedScrollAccepted");
            Log.e((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            return;
        }
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int n) {
        return ViewParentCompat.onStartNestedScroll(viewParent, view, view2, n, 0);
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view, View object, int n, int n2) {
        if (viewParent instanceof NestedScrollingParent2) {
            return ((NestedScrollingParent2)viewParent).onStartNestedScroll(view, (View)object, n, n2);
        }
        if (n2 != 0) return false;
        if (Build.VERSION.SDK_INT < 21) {
            if (!(viewParent instanceof NestedScrollingParent)) return false;
            return ((NestedScrollingParent)viewParent).onStartNestedScroll(view, (View)object, n);
        }
        try {
            return viewParent.onStartNestedScroll(view, (View)object, n);
        }
        catch (AbstractMethodError abstractMethodError) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ViewParent ");
            ((StringBuilder)object).append((Object)viewParent);
            ((StringBuilder)object).append(" does not implement interface method onStartNestedScroll");
            Log.e((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            return false;
        }
    }

    public static void onStopNestedScroll(ViewParent viewParent, View view) {
        ViewParentCompat.onStopNestedScroll(viewParent, view, 0);
    }

    public static void onStopNestedScroll(ViewParent viewParent, View object, int n) {
        if (viewParent instanceof NestedScrollingParent2) {
            ((NestedScrollingParent2)viewParent).onStopNestedScroll((View)object, n);
            return;
        }
        if (n != 0) return;
        if (Build.VERSION.SDK_INT < 21) {
            if (!(viewParent instanceof NestedScrollingParent)) return;
            ((NestedScrollingParent)viewParent).onStopNestedScroll((View)object);
            return;
        }
        try {
            viewParent.onStopNestedScroll((View)object);
            return;
        }
        catch (AbstractMethodError abstractMethodError) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ViewParent ");
            ((StringBuilder)object).append((Object)viewParent);
            ((StringBuilder)object).append(" does not implement interface method onStopNestedScroll");
            Log.e((String)TAG, (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            return;
        }
    }

    @Deprecated
    public static boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
        return viewParent.requestSendAccessibilityEvent(view, accessibilityEvent);
    }
}

