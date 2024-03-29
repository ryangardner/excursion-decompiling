/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import androidx.core.graphics.BitmapCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.core.view.GravityCompat;
import java.io.InputStream;

public final class RoundedBitmapDrawableFactory {
    private static final String TAG = "RoundedBitmapDrawableFa";

    private RoundedBitmapDrawableFactory() {
    }

    public static RoundedBitmapDrawable create(Resources resources, Bitmap bitmap) {
        if (Build.VERSION.SDK_INT < 21) return new DefaultRoundedBitmapDrawable(resources, bitmap);
        return new RoundedBitmapDrawable21(resources, bitmap);
    }

    public static RoundedBitmapDrawable create(Resources object, InputStream inputStream2) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create((Resources)object, BitmapFactory.decodeStream((InputStream)inputStream2));
        if (roundedBitmapDrawable.getBitmap() != null) return roundedBitmapDrawable;
        object = new StringBuilder();
        ((StringBuilder)object).append("RoundedBitmapDrawable cannot decode ");
        ((StringBuilder)object).append(inputStream2);
        Log.w((String)TAG, (String)((StringBuilder)object).toString());
        return roundedBitmapDrawable;
    }

    public static RoundedBitmapDrawable create(Resources object, String string2) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create((Resources)object, BitmapFactory.decodeFile((String)string2));
        if (roundedBitmapDrawable.getBitmap() != null) return roundedBitmapDrawable;
        object = new StringBuilder();
        ((StringBuilder)object).append("RoundedBitmapDrawable cannot decode ");
        ((StringBuilder)object).append(string2);
        Log.w((String)TAG, (String)((StringBuilder)object).toString());
        return roundedBitmapDrawable;
    }

    private static class DefaultRoundedBitmapDrawable
    extends RoundedBitmapDrawable {
        DefaultRoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
            super(resources, bitmap);
        }

        @Override
        void gravityCompatApply(int n, int n2, int n3, Rect rect, Rect rect2) {
            GravityCompat.apply(n, n2, n3, rect, rect2, 0);
        }

        @Override
        public boolean hasMipMap() {
            if (this.mBitmap == null) return false;
            if (!BitmapCompat.hasMipMap(this.mBitmap)) return false;
            return true;
        }

        @Override
        public void setMipMap(boolean bl) {
            if (this.mBitmap == null) return;
            BitmapCompat.setHasMipMap(this.mBitmap, bl);
            this.invalidateSelf();
        }
    }

}

