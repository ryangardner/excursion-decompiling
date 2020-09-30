/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package androidx.core.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import java.util.List;
import java.util.Map;

public abstract class SharedElementCallback {
    private static final String BUNDLE_SNAPSHOT_BITMAP = "sharedElement:snapshot:bitmap";
    private static final String BUNDLE_SNAPSHOT_IMAGE_MATRIX = "sharedElement:snapshot:imageMatrix";
    private static final String BUNDLE_SNAPSHOT_IMAGE_SCALETYPE = "sharedElement:snapshot:imageScaleType";
    private static final int MAX_IMAGE_SIZE = 1048576;
    private Matrix mTempMatrix;

    private static Bitmap createDrawableBitmap(Drawable drawable2) {
        int n = drawable2.getIntrinsicWidth();
        int n2 = drawable2.getIntrinsicHeight();
        if (n <= 0) return null;
        if (n2 <= 0) {
            return null;
        }
        float f = Math.min(1.0f, 1048576.0f / (float)(n * n2));
        if (drawable2 instanceof BitmapDrawable && f == 1.0f) {
            return ((BitmapDrawable)drawable2).getBitmap();
        }
        n = (int)((float)n * f);
        int n3 = (int)((float)n2 * f);
        Bitmap bitmap = Bitmap.createBitmap((int)n, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Rect rect = drawable2.getBounds();
        int n4 = rect.left;
        n2 = rect.top;
        int n5 = rect.right;
        int n6 = rect.bottom;
        drawable2.setBounds(0, 0, n, n3);
        drawable2.draw(canvas);
        drawable2.setBounds(n4, n2, n5, n6);
        return bitmap;
    }

    public Parcelable onCaptureSharedElementSnapshot(View view, Matrix canvas, RectF rectF) {
        ImageView imageView;
        Drawable drawable2;
        if (view instanceof ImageView) {
            imageView = (ImageView)view;
            drawable2 = imageView.getDrawable();
            Drawable drawable3 = imageView.getBackground();
            if (drawable2 != null && drawable3 == null && (drawable2 = SharedElementCallback.createDrawableBitmap(drawable2)) != null) {
                view = new Bundle();
                view.putParcelable(BUNDLE_SNAPSHOT_BITMAP, (Parcelable)drawable2);
                view.putString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE, imageView.getScaleType().toString());
                if (imageView.getScaleType() != ImageView.ScaleType.MATRIX) return view;
                rectF = imageView.getImageMatrix();
                canvas = new float[9];
                rectF.getValues((float[])canvas);
                view.putFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX, (float[])canvas);
                return view;
            }
        }
        int n = Math.round(rectF.width());
        int n2 = Math.round(rectF.height());
        drawable2 = null;
        imageView = drawable2;
        if (n <= 0) return imageView;
        imageView = drawable2;
        if (n2 <= 0) return imageView;
        float f = Math.min(1.0f, 1048576.0f / (float)(n * n2));
        n = (int)((float)n * f);
        n2 = (int)((float)n2 * f);
        if (this.mTempMatrix == null) {
            this.mTempMatrix = new Matrix();
        }
        this.mTempMatrix.set((Matrix)canvas);
        this.mTempMatrix.postTranslate(-rectF.left, -rectF.top);
        this.mTempMatrix.postScale(f, f);
        imageView = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        canvas = new Canvas((Bitmap)imageView);
        canvas.concat(this.mTempMatrix);
        view.draw(canvas);
        return imageView;
    }

    public View onCreateSnapshotView(Context context, Parcelable parcelable) {
        boolean bl = parcelable instanceof Bundle;
        Context context2 = null;
        if (!bl) {
            if (!(parcelable instanceof Bitmap)) return context2;
            parcelable = (Bitmap)parcelable;
            context2 = new ImageView(context);
            context2.setImageBitmap((Bitmap)parcelable);
            return context2;
        }
        context2 = (Context)(parcelable = (Bundle)parcelable).getParcelable(BUNDLE_SNAPSHOT_BITMAP);
        if (context2 == null) {
            return null;
        }
        context = new ImageView(context);
        context.setImageBitmap((Bitmap)context2);
        context.setScaleType(ImageView.ScaleType.valueOf((String)parcelable.getString(BUNDLE_SNAPSHOT_IMAGE_SCALETYPE)));
        context2 = context;
        if (context.getScaleType() != ImageView.ScaleType.MATRIX) return context2;
        context2 = parcelable.getFloatArray(BUNDLE_SNAPSHOT_IMAGE_MATRIX);
        parcelable = new Matrix();
        parcelable.setValues((float[])context2);
        context.setImageMatrix((Matrix)parcelable);
        return context;
    }

    public void onMapSharedElements(List<String> list, Map<String, View> map) {
    }

    public void onRejectSharedElements(List<View> list) {
    }

    public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
    }

    public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
    }

    public void onSharedElementsArrived(List<String> list, List<View> list2, OnSharedElementsReadyListener onSharedElementsReadyListener) {
        onSharedElementsReadyListener.onSharedElementsReady();
    }

    public static interface OnSharedElementsReadyListener {
        public void onSharedElementsReady();
    }

}

