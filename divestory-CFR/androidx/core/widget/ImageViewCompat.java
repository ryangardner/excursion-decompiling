/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.widget.ImageView
 */
package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;
import androidx.core.widget.TintableImageSourceView;

public class ImageViewCompat {
    private ImageViewCompat() {
    }

    public static ColorStateList getImageTintList(ImageView imageView) {
        if (Build.VERSION.SDK_INT >= 21) {
            return imageView.getImageTintList();
        }
        if (!(imageView instanceof TintableImageSourceView)) return null;
        return ((TintableImageSourceView)imageView).getSupportImageTintList();
    }

    public static PorterDuff.Mode getImageTintMode(ImageView imageView) {
        if (Build.VERSION.SDK_INT >= 21) {
            return imageView.getImageTintMode();
        }
        if (!(imageView instanceof TintableImageSourceView)) return null;
        return ((TintableImageSourceView)imageView).getSupportImageTintMode();
    }

    public static void setImageTintList(ImageView imageView, ColorStateList colorStateList) {
        if (Build.VERSION.SDK_INT < 21) {
            if (!(imageView instanceof TintableImageSourceView)) return;
            ((TintableImageSourceView)imageView).setSupportImageTintList(colorStateList);
            return;
        }
        imageView.setImageTintList(colorStateList);
        if (Build.VERSION.SDK_INT != 21) return;
        colorStateList = imageView.getDrawable();
        if (colorStateList == null) return;
        if (imageView.getImageTintList() == null) return;
        if (colorStateList.isStateful()) {
            colorStateList.setState(imageView.getDrawableState());
        }
        imageView.setImageDrawable((Drawable)colorStateList);
    }

    public static void setImageTintMode(ImageView imageView, PorterDuff.Mode mode) {
        if (Build.VERSION.SDK_INT < 21) {
            if (!(imageView instanceof TintableImageSourceView)) return;
            ((TintableImageSourceView)imageView).setSupportImageTintMode(mode);
            return;
        }
        imageView.setImageTintMode(mode);
        if (Build.VERSION.SDK_INT != 21) return;
        mode = imageView.getDrawable();
        if (mode == null) return;
        if (imageView.getImageTintList() == null) return;
        if (mode.isStateful()) {
            mode.setState(imageView.getDrawableState());
        }
        imageView.setImageDrawable((Drawable)mode);
    }
}

