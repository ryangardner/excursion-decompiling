/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.LinearGradient
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.transition.Transition
 *  android.transition.TransitionSet
 *  android.view.View
 *  android.view.ViewParent
 */
package com.google.android.material.transition.platform;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewParent;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

class TransitionUtils {
    private static final RectF transformAlphaRectF = new RectF();

    private TransitionUtils() {
    }

    static float calculateArea(RectF rectF) {
        return rectF.width() * rectF.height();
    }

    static ShapeAppearanceModel convertToRelativeCornerSizes(ShapeAppearanceModel shapeAppearanceModel, final RectF rectF) {
        return shapeAppearanceModel.withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator(){

            @Override
            public CornerSize apply(CornerSize cornerSize) {
                if (!(cornerSize instanceof RelativeCornerSize)) return new RelativeCornerSize(cornerSize.getCornerSize(rectF) / rectF.height());
                return cornerSize;
            }
        });
    }

    static Shader createColorShader(int n) {
        return new LinearGradient(0.0f, 0.0f, 0.0f, 0.0f, n, n, Shader.TileMode.CLAMP);
    }

    static <T> T defaultIfNull(T t, T t2) {
        if (t != null) {
            return t;
        }
        t = t2;
        return t;
    }

    static View findAncestorById(View object, int n) {
        String string2 = object.getResources().getResourceName(n);
        while (object != null) {
            if (object.getId() == n) {
                return object;
            }
            if (!((object = object.getParent()) instanceof View)) break;
            object = (View)object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" is not a valid ancestor");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    static View findDescendantOrAncestorById(View view, int n) {
        View view2 = view.findViewById(n);
        if (view2 == null) return TransitionUtils.findAncestorById(view, n);
        return view2;
    }

    static RectF getLocationOnScreen(View view) {
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        int n = arrn[0];
        int n2 = arrn[1];
        int n3 = view.getWidth();
        int n4 = view.getHeight();
        return new RectF((float)n, (float)n2, (float)(n3 + n), (float)(n4 + n2));
    }

    static RectF getRelativeBounds(View view) {
        return new RectF((float)view.getLeft(), (float)view.getTop(), (float)view.getRight(), (float)view.getBottom());
    }

    static Rect getRelativeBoundsRect(View view) {
        return new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    private static boolean isShapeAppearanceSignificant(ShapeAppearanceModel shapeAppearanceModel, RectF rectF) {
        if (shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(rectF) != 0.0f) return true;
        if (shapeAppearanceModel.getTopRightCornerSize().getCornerSize(rectF) != 0.0f) return true;
        if (shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(rectF) != 0.0f) return true;
        if (shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(rectF) != 0.0f) return true;
        return false;
    }

    static float lerp(float f, float f2, float f3) {
        return f + f3 * (f2 - f);
    }

    static float lerp(float f, float f2, float f3, float f4, float f5) {
        if (f5 < f3) {
            return f;
        }
        if (!(f5 > f4)) return TransitionUtils.lerp(f, f2, (f5 - f3) / (f4 - f3));
        return f2;
    }

    static int lerp(int n, int n2, float f, float f2, float f3) {
        if (f3 < f) {
            return n;
        }
        if (!(f3 > f2)) return (int)TransitionUtils.lerp(n, n2, (f3 - f) / (f2 - f));
        return n2;
    }

    static ShapeAppearanceModel lerp(ShapeAppearanceModel shapeAppearanceModel, ShapeAppearanceModel shapeAppearanceModel2, final RectF rectF, final RectF rectF2, final float f, final float f2, final float f3) {
        if (f3 < f) {
            return shapeAppearanceModel;
        }
        if (!(f3 > f2)) return TransitionUtils.transformCornerSizes(shapeAppearanceModel, shapeAppearanceModel2, rectF, new CornerSizeBinaryOperator(){

            @Override
            public CornerSize apply(CornerSize cornerSize, CornerSize cornerSize2) {
                return new AbsoluteCornerSize(TransitionUtils.lerp(cornerSize.getCornerSize(rectF), cornerSize2.getCornerSize(rectF2), f, f2, f3));
            }
        });
        return shapeAppearanceModel2;
    }

    static void maybeAddTransition(TransitionSet transitionSet, Transition transition) {
        if (transition == null) return;
        transitionSet.addTransition(transition);
    }

    static void maybeRemoveTransition(TransitionSet transitionSet, Transition transition) {
        if (transition == null) return;
        transitionSet.removeTransition(transition);
    }

    private static int saveLayerAlphaCompat(Canvas canvas, Rect rect, int n) {
        transformAlphaRectF.set(rect);
        if (Build.VERSION.SDK_INT < 21) return canvas.saveLayerAlpha(TransitionUtils.transformAlphaRectF.left, TransitionUtils.transformAlphaRectF.top, TransitionUtils.transformAlphaRectF.right, TransitionUtils.transformAlphaRectF.bottom, n, 31);
        return canvas.saveLayerAlpha(transformAlphaRectF, n);
    }

    static void transform(Canvas canvas, Rect rect, float f, float f2, float f3, int n, CanvasOperation canvasOperation) {
        if (n <= 0) {
            return;
        }
        int n2 = canvas.save();
        canvas.translate(f, f2);
        canvas.scale(f3, f3);
        if (n < 255) {
            TransitionUtils.saveLayerAlphaCompat(canvas, rect, n);
        }
        canvasOperation.run(canvas);
        canvas.restoreToCount(n2);
    }

    static ShapeAppearanceModel transformCornerSizes(ShapeAppearanceModel shapeAppearanceModel, ShapeAppearanceModel shapeAppearanceModel2, RectF object, CornerSizeBinaryOperator cornerSizeBinaryOperator) {
        if (TransitionUtils.isShapeAppearanceSignificant(shapeAppearanceModel, (RectF)object)) {
            object = shapeAppearanceModel;
            return ((ShapeAppearanceModel)object).toBuilder().setTopLeftCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getTopLeftCornerSize(), shapeAppearanceModel2.getTopLeftCornerSize())).setTopRightCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getTopRightCornerSize(), shapeAppearanceModel2.getTopRightCornerSize())).setBottomLeftCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getBottomLeftCornerSize(), shapeAppearanceModel2.getBottomLeftCornerSize())).setBottomRightCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getBottomRightCornerSize(), shapeAppearanceModel2.getBottomRightCornerSize())).build();
        }
        object = shapeAppearanceModel2;
        return ((ShapeAppearanceModel)object).toBuilder().setTopLeftCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getTopLeftCornerSize(), shapeAppearanceModel2.getTopLeftCornerSize())).setTopRightCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getTopRightCornerSize(), shapeAppearanceModel2.getTopRightCornerSize())).setBottomLeftCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getBottomLeftCornerSize(), shapeAppearanceModel2.getBottomLeftCornerSize())).setBottomRightCornerSize(cornerSizeBinaryOperator.apply(shapeAppearanceModel.getBottomRightCornerSize(), shapeAppearanceModel2.getBottomRightCornerSize())).build();
    }

    static interface CanvasOperation {
        public void run(Canvas var1);
    }

    static interface CornerSizeBinaryOperator {
        public CornerSize apply(CornerSize var1, CornerSize var2);
    }

}

