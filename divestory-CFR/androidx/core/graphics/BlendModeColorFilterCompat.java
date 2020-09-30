/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.BlendMode
 *  android.graphics.BlendModeColorFilter
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import androidx.core.graphics.BlendModeCompat;
import androidx.core.graphics.BlendModeUtils;

public class BlendModeColorFilterCompat {
    private BlendModeColorFilterCompat() {
    }

    public static ColorFilter createBlendModeColorFilterCompat(int n, BlendModeCompat blendModeCompat) {
        int n2 = Build.VERSION.SDK_INT;
        BlendMode blendMode = null;
        PorterDuff.Mode mode = null;
        if (n2 >= 29) {
            blendMode = BlendModeUtils.obtainBlendModeFromCompat(blendModeCompat);
            blendModeCompat = mode;
            if (blendMode == null) return blendModeCompat;
            return new BlendModeColorFilter(n, blendMode);
        }
        mode = BlendModeUtils.obtainPorterDuffFromCompat(blendModeCompat);
        blendModeCompat = blendMode;
        if (mode == null) return blendModeCompat;
        return new PorterDuffColorFilter(n, mode);
    }
}

