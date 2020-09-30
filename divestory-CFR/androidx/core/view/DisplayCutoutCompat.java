/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.DisplayCutout
 */
package androidx.core.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import java.util.List;

public final class DisplayCutoutCompat {
    private final Object mDisplayCutout;

    public DisplayCutoutCompat(Rect object, List<Rect> list) {
        object = Build.VERSION.SDK_INT >= 28 ? new DisplayCutout(object, list) : null;
        this(object);
    }

    private DisplayCutoutCompat(Object object) {
        this.mDisplayCutout = object;
    }

    static DisplayCutoutCompat wrap(Object object) {
        if (object != null) return new DisplayCutoutCompat(object);
        return null;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        DisplayCutoutCompat displayCutoutCompat = (DisplayCutoutCompat)object;
        object = this.mDisplayCutout;
        if (object != null) {
            return object.equals(displayCutoutCompat.mDisplayCutout);
        }
        if (displayCutoutCompat.mDisplayCutout != null) return false;
        return bl;
    }

    public List<Rect> getBoundingRects() {
        if (Build.VERSION.SDK_INT < 28) return null;
        return ((DisplayCutout)this.mDisplayCutout).getBoundingRects();
    }

    public int getSafeInsetBottom() {
        if (Build.VERSION.SDK_INT < 28) return 0;
        return ((DisplayCutout)this.mDisplayCutout).getSafeInsetBottom();
    }

    public int getSafeInsetLeft() {
        if (Build.VERSION.SDK_INT < 28) return 0;
        return ((DisplayCutout)this.mDisplayCutout).getSafeInsetLeft();
    }

    public int getSafeInsetRight() {
        if (Build.VERSION.SDK_INT < 28) return 0;
        return ((DisplayCutout)this.mDisplayCutout).getSafeInsetRight();
    }

    public int getSafeInsetTop() {
        if (Build.VERSION.SDK_INT < 28) return 0;
        return ((DisplayCutout)this.mDisplayCutout).getSafeInsetTop();
    }

    public int hashCode() {
        Object object = this.mDisplayCutout;
        if (object != null) return object.hashCode();
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DisplayCutoutCompat{");
        stringBuilder.append(this.mDisplayCutout);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    DisplayCutout unwrap() {
        return (DisplayCutout)this.mDisplayCutout;
    }
}

