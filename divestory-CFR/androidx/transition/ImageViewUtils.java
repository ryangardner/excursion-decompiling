/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.widget.ImageView
 */
package androidx.transition;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;
import java.lang.reflect.Field;

class ImageViewUtils {
    private static Field sDrawMatrixField;
    private static boolean sDrawMatrixFieldFetched = false;
    private static boolean sTryHiddenAnimateTransform = true;

    private ImageViewUtils() {
    }

    static void animateTransform(ImageView imageView, Matrix matrix) {
        Object object;
        block11 : {
            if (Build.VERSION.SDK_INT >= 29) {
                imageView.animateTransform(matrix);
                return;
            }
            if (matrix == null) {
                matrix = imageView.getDrawable();
                if (matrix == null) return;
                matrix.setBounds(0, 0, imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight(), imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom());
                imageView.invalidate();
                return;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                ImageViewUtils.hiddenAnimateTransform(imageView, matrix);
                return;
            }
            object = imageView.getDrawable();
            if (object == null) return;
            object.setBounds(0, 0, object.getIntrinsicWidth(), object.getIntrinsicHeight());
            object = null;
            Matrix matrix2 = null;
            ImageViewUtils.fetchDrawMatrixField();
            Field field = sDrawMatrixField;
            if (field != null) {
                block10 : {
                    object = matrix2;
                    field = (Matrix)field.get((Object)imageView);
                    if (field != null) break block10;
                    try {
                        matrix2 = new Matrix();
                        object = matrix2;
                    }
                    catch (IllegalAccessException illegalAccessException) {}
                    try {
                        sDrawMatrixField.set((Object)imageView, (Object)matrix2);
                        object = matrix2;
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        // empty catch block
                    }
                    break block11;
                }
                object = field;
            }
        }
        if (object != null) {
            object.set(matrix);
        }
        imageView.invalidate();
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private static void fetchDrawMatrixField() {
        if (ImageViewUtils.sDrawMatrixFieldFetched != false) return;
        try {
            ImageViewUtils.sDrawMatrixField = var0 = ImageView.class.getDeclaredField("mDrawMatrix");
            var0.setAccessible(true);
lbl5: // 2 sources:
            do {
                ImageViewUtils.sDrawMatrixFieldFetched = true;
                return;
                break;
            } while (true);
        }
        catch (NoSuchFieldException var0_1) {
            ** continue;
        }
    }

    private static void hiddenAnimateTransform(ImageView imageView, Matrix matrix) {
        if (!sTryHiddenAnimateTransform) return;
        try {
            imageView.animateTransform(matrix);
            return;
        }
        catch (NoSuchMethodError noSuchMethodError) {
            sTryHiddenAnimateTransform = false;
        }
    }
}

