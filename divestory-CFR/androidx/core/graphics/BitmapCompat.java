/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.graphics;

import android.graphics.Bitmap;
import android.os.Build;

public final class BitmapCompat {
    private BitmapCompat() {
    }

    public static int getAllocationByteCount(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT < 19) return bitmap.getByteCount();
        return bitmap.getAllocationByteCount();
    }

    public static boolean hasMipMap(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT < 18) return false;
        return bitmap.hasMipMap();
    }

    public static void setHasMipMap(Bitmap bitmap, boolean bl) {
        if (Build.VERSION.SDK_INT < 18) return;
        bitmap.setHasMipMap(bl);
    }
}

