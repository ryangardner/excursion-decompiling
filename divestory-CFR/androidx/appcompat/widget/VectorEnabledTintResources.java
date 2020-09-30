/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import androidx.appcompat.widget.ResourceManagerInternal;
import java.lang.ref.WeakReference;

public class VectorEnabledTintResources
extends Resources {
    public static final int MAX_SDK_WHERE_REQUIRED = 20;
    private static boolean sCompatVectorFromResourcesEnabled = false;
    private final WeakReference<Context> mContextRef;

    public VectorEnabledTintResources(Context context, Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.mContextRef = new WeakReference<Context>(context);
    }

    public static boolean isCompatVectorFromResourcesEnabled() {
        return sCompatVectorFromResourcesEnabled;
    }

    public static void setCompatVectorFromResourcesEnabled(boolean bl) {
        sCompatVectorFromResourcesEnabled = bl;
    }

    public static boolean shouldBeUsed() {
        if (!VectorEnabledTintResources.isCompatVectorFromResourcesEnabled()) return false;
        if (Build.VERSION.SDK_INT > 20) return false;
        return true;
    }

    public Drawable getDrawable(int n) throws Resources.NotFoundException {
        Context context = (Context)this.mContextRef.get();
        if (context == null) return super.getDrawable(n);
        return ResourceManagerInternal.get().onDrawableLoadedFromResources(context, this, n);
    }

    final Drawable superGetDrawable(int n) {
        return super.getDrawable(n);
    }
}

