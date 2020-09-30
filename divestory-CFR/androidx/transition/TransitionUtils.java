/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorSet
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Picture
 *  android.graphics.RectF
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroupOverlay
 *  android.view.ViewParent
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewParent;
import android.widget.ImageView;
import androidx.transition.ViewUtils;

class TransitionUtils {
    private static final boolean HAS_IS_ATTACHED_TO_WINDOW;
    private static final boolean HAS_OVERLAY;
    private static final boolean HAS_PICTURE_BITMAP;
    private static final int MAX_IMAGE_SIZE = 1048576;

    static {
        int n = Build.VERSION.SDK_INT;
        boolean bl = true;
        boolean bl2 = n >= 19;
        HAS_IS_ATTACHED_TO_WINDOW = bl2;
        bl2 = Build.VERSION.SDK_INT >= 18;
        HAS_OVERLAY = bl2;
        bl2 = Build.VERSION.SDK_INT >= 28 ? bl : false;
        HAS_PICTURE_BITMAP = bl2;
    }

    private TransitionUtils() {
    }

    static View copyViewImage(ViewGroup viewGroup, View view, View view2) {
        Matrix matrix = new Matrix();
        matrix.setTranslate((float)(-view2.getScrollX()), (float)(-view2.getScrollY()));
        ViewUtils.transformMatrixToGlobal(view, matrix);
        ViewUtils.transformMatrixToLocal((View)viewGroup, matrix);
        RectF rectF = new RectF(0.0f, 0.0f, (float)view.getWidth(), (float)view.getHeight());
        matrix.mapRect(rectF);
        int n = Math.round(rectF.left);
        int n2 = Math.round(rectF.top);
        int n3 = Math.round(rectF.right);
        int n4 = Math.round(rectF.bottom);
        view2 = new ImageView(view.getContext());
        view2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewGroup = TransitionUtils.createViewBitmap(view, matrix, rectF, viewGroup);
        if (viewGroup != null) {
            view2.setImageBitmap((Bitmap)viewGroup);
        }
        view2.measure(View.MeasureSpec.makeMeasureSpec((int)(n3 - n), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(n4 - n2), (int)1073741824));
        view2.layout(n, n2, n3, n4);
        return view2;
    }

    private static Bitmap createViewBitmap(View view, Matrix matrix, RectF rectF, ViewGroup viewGroup) {
        boolean bl;
        int n;
        ViewGroup viewGroup2;
        boolean bl2;
        block9 : {
            block8 : {
                block7 : {
                    if (!HAS_IS_ATTACHED_TO_WINDOW) break block7;
                    bl = view.isAttachedToWindow() ^ true;
                    if (viewGroup == null) break block8;
                    bl2 = viewGroup.isAttachedToWindow();
                    break block9;
                }
                bl = false;
            }
            bl2 = false;
        }
        boolean bl3 = HAS_OVERLAY;
        Bitmap bitmap = null;
        if (bl3 && bl) {
            if (!bl2) {
                return null;
            }
            viewGroup2 = (ViewGroup)view.getParent();
            n = viewGroup2.indexOfChild(view);
            viewGroup.getOverlay().add(view);
        } else {
            viewGroup2 = null;
            n = 0;
        }
        int n2 = Math.round(rectF.width());
        int n3 = Math.round(rectF.height());
        Bitmap bitmap2 = bitmap;
        if (n2 > 0) {
            bitmap2 = bitmap;
            if (n3 > 0) {
                float f = Math.min(1.0f, 1048576.0f / (float)(n2 * n3));
                n2 = Math.round((float)n2 * f);
                n3 = Math.round((float)n3 * f);
                matrix.postTranslate(-rectF.left, -rectF.top);
                matrix.postScale(f, f);
                if (HAS_PICTURE_BITMAP) {
                    rectF = new Picture();
                    bitmap2 = rectF.beginRecording(n2, n3);
                    bitmap2.concat(matrix);
                    view.draw((Canvas)bitmap2);
                    rectF.endRecording();
                    bitmap2 = Bitmap.createBitmap((Picture)rectF);
                } else {
                    bitmap2 = Bitmap.createBitmap((int)n2, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
                    rectF = new Canvas(bitmap2);
                    rectF.concat(matrix);
                    view.draw((Canvas)rectF);
                }
            }
        }
        if (!HAS_OVERLAY) return bitmap2;
        if (!bl) return bitmap2;
        viewGroup.getOverlay().remove(view);
        viewGroup2.addView(view, n);
        return bitmap2;
    }

    static Animator mergeAnimators(Animator animator2, Animator animator3) {
        if (animator2 == null) {
            return animator3;
        }
        if (animator3 == null) {
            return animator2;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{animator2, animator3});
        return animatorSet;
    }

    static class MatrixEvaluator
    implements TypeEvaluator<Matrix> {
        final float[] mTempEndValues = new float[9];
        final Matrix mTempMatrix = new Matrix();
        final float[] mTempStartValues = new float[9];

        MatrixEvaluator() {
        }

        public Matrix evaluate(float f, Matrix arrf, Matrix arrf2) {
            arrf.getValues(this.mTempStartValues);
            arrf2.getValues(this.mTempEndValues);
            int n = 0;
            do {
                if (n >= 9) {
                    this.mTempMatrix.setValues(this.mTempEndValues);
                    return this.mTempMatrix;
                }
                arrf = this.mTempEndValues;
                float f2 = arrf[n];
                arrf2 = this.mTempStartValues;
                float f3 = arrf2[n];
                arrf[n] = arrf2[n] + (f2 - f3) * f;
                ++n;
            } while (true);
        }
    }

}

