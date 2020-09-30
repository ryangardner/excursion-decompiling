/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.GhostView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class GhostViewPlatform
implements GhostView {
    private static final String TAG = "GhostViewApi21";
    private static Method sAddGhostMethod;
    private static boolean sAddGhostMethodFetched;
    private static Class<?> sGhostViewClass;
    private static boolean sGhostViewClassFetched;
    private static Method sRemoveGhostMethod;
    private static boolean sRemoveGhostMethodFetched;
    private final View mGhostView;

    private GhostViewPlatform(View view) {
        this.mGhostView = view;
    }

    static GhostView addGhost(View object, ViewGroup viewGroup, Matrix matrix) {
        GhostViewPlatform.fetchAddGhostMethod();
        if (sAddGhostMethod == null) return null;
        try {
            return new GhostViewPlatform((View)sAddGhostMethod.invoke(null, new Object[]{object, viewGroup, matrix}));
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
    }

    private static void fetchAddGhostMethod() {
        if (sAddGhostMethodFetched) return;
        try {
            Method method;
            GhostViewPlatform.fetchGhostViewClass();
            sAddGhostMethod = method = sGhostViewClass.getDeclaredMethod("addGhost", View.class, ViewGroup.class, Matrix.class);
            method.setAccessible(true);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.i((String)TAG, (String)"Failed to retrieve addGhost method", (Throwable)noSuchMethodException);
        }
        sAddGhostMethodFetched = true;
    }

    private static void fetchGhostViewClass() {
        if (sGhostViewClassFetched) return;
        try {
            sGhostViewClass = Class.forName("android.view.GhostView");
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.i((String)TAG, (String)"Failed to retrieve GhostView class", (Throwable)classNotFoundException);
        }
        sGhostViewClassFetched = true;
    }

    private static void fetchRemoveGhostMethod() {
        if (sRemoveGhostMethodFetched) return;
        try {
            Method method;
            GhostViewPlatform.fetchGhostViewClass();
            sRemoveGhostMethod = method = sGhostViewClass.getDeclaredMethod("removeGhost", View.class);
            method.setAccessible(true);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.i((String)TAG, (String)"Failed to retrieve removeGhost method", (Throwable)noSuchMethodException);
        }
        sRemoveGhostMethodFetched = true;
    }

    static void removeGhost(View view) {
        GhostViewPlatform.fetchRemoveGhostMethod();
        Method method = sRemoveGhostMethod;
        if (method == null) return;
        try {
            method.invoke(null, new Object[]{view});
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
    }

    @Override
    public void reserveEndViewTransition(ViewGroup viewGroup, View view) {
    }

    @Override
    public void setVisibility(int n) {
        this.mGhostView.setVisibility(n);
    }
}

