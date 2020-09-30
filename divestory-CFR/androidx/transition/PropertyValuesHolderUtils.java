/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.PropertyValuesHolder
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Property
 */
package androidx.transition;

import android.animation.PropertyValuesHolder;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.Property;
import androidx.transition.PathProperty;

class PropertyValuesHolderUtils {
    private PropertyValuesHolderUtils() {
    }

    static PropertyValuesHolder ofPointF(Property<?, PointF> property, Path path) {
        if (Build.VERSION.SDK_INT < 21) return PropertyValuesHolder.ofFloat(new PathProperty(property, path), (float[])new float[]{0.0f, 1.0f});
        return PropertyValuesHolder.ofObject(property, null, (Path)path);
    }
}

