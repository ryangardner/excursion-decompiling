/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.BitmapShader
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.AnimationDrawable
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.ClipDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.graphics.drawable.ShapeDrawable
 *  android.graphics.drawable.shapes.RoundRectShape
 *  android.graphics.drawable.shapes.Shape
 *  android.util.AttributeSet
 *  android.widget.ProgressBar
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.drawable.WrappedDrawable;

class AppCompatProgressBarHelper {
    private static final int[] TINT_ATTRS = new int[]{16843067, 16843068};
    private Bitmap mSampleTile;
    private final ProgressBar mView;

    AppCompatProgressBarHelper(ProgressBar progressBar) {
        this.mView = progressBar;
    }

    private Shape getDrawableShape() {
        return new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    private Drawable tileify(Drawable drawable2, boolean bl) {
        Drawable[] arrdrawable;
        LayerDrawable layerDrawable;
        int n;
        int n2;
        int n3;
        if (drawable2 instanceof WrappedDrawable) {
            WrappedDrawable wrappedDrawable = (WrappedDrawable)drawable2;
            Drawable drawable3 = wrappedDrawable.getWrappedDrawable();
            if (drawable3 == null) return drawable2;
            wrappedDrawable.setWrappedDrawable(this.tileify(drawable3, bl));
            return drawable2;
        }
        if (drawable2 instanceof LayerDrawable) {
            drawable2 = (LayerDrawable)drawable2;
            n = drawable2.getNumberOfLayers();
            arrdrawable = new Drawable[n];
            n2 = 0;
        } else {
            if (!(drawable2 instanceof BitmapDrawable)) return drawable2;
            drawable2 = (BitmapDrawable)drawable2;
            Bitmap bitmap = drawable2.getBitmap();
            if (this.mSampleTile == null) {
                this.mSampleTile = bitmap;
            }
            ShapeDrawable shapeDrawable = new ShapeDrawable(this.getDrawableShape());
            bitmap = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
            shapeDrawable.getPaint().setShader((Shader)bitmap);
            shapeDrawable.getPaint().setColorFilter(drawable2.getPaint().getColorFilter());
            drawable2 = shapeDrawable;
            if (!bl) return drawable2;
            return new ClipDrawable((Drawable)shapeDrawable, 3, 1);
        }
        for (n3 = 0; n3 < n; ++n3) {
            int n4 = drawable2.getId(n3);
            layerDrawable = drawable2.getDrawable(n3);
            bl = n4 == 16908301 || n4 == 16908303;
            arrdrawable[n3] = this.tileify((Drawable)layerDrawable, bl);
        }
        layerDrawable = new LayerDrawable(arrdrawable);
        n3 = n2;
        while (n3 < n) {
            layerDrawable.setId(n3, drawable2.getId(n3));
            ++n3;
        }
        return layerDrawable;
    }

    private Drawable tileifyIndeterminate(Drawable drawable2) {
        Drawable drawable3 = drawable2;
        if (!(drawable2 instanceof AnimationDrawable)) return drawable3;
        drawable2 = (AnimationDrawable)drawable2;
        int n = drawable2.getNumberOfFrames();
        drawable3 = new AnimationDrawable();
        drawable3.setOneShot(drawable2.isOneShot());
        int n2 = 0;
        do {
            if (n2 >= n) {
                drawable3.setLevel(10000);
                return drawable3;
            }
            Drawable drawable4 = this.tileify(drawable2.getFrame(n2), true);
            drawable4.setLevel(10000);
            drawable3.addFrame(drawable4, drawable2.getDuration(n2));
            ++n2;
        } while (true);
    }

    Bitmap getSampleTile() {
        return this.mSampleTile;
    }

    void loadFromAttributes(AttributeSet object, int n) {
        object = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), (AttributeSet)object, TINT_ATTRS, n, 0);
        Drawable drawable2 = ((TintTypedArray)object).getDrawableIfKnown(0);
        if (drawable2 != null) {
            this.mView.setIndeterminateDrawable(this.tileifyIndeterminate(drawable2));
        }
        if ((drawable2 = ((TintTypedArray)object).getDrawableIfKnown(1)) != null) {
            this.mView.setProgressDrawable(this.tileify(drawable2, false));
        }
        ((TintTypedArray)object).recycle();
    }
}

