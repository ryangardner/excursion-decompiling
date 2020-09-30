/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathMeasure
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Region
 *  android.graphics.Region$Op
 *  android.graphics.Shader
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.Display
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.WindowManager
 */
package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.transition.ArcMotion;
import androidx.transition.PathMotion;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.transition.FadeModeEvaluator;
import com.google.android.material.transition.FadeModeEvaluators;
import com.google.android.material.transition.FadeModeResult;
import com.google.android.material.transition.FitModeEvaluator;
import com.google.android.material.transition.FitModeEvaluators;
import com.google.android.material.transition.FitModeResult;
import com.google.android.material.transition.MaskEvaluator;
import com.google.android.material.transition.MaterialArcMotion;
import com.google.android.material.transition.TransitionListenerAdapter;
import com.google.android.material.transition.TransitionUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public final class MaterialContainerTransform
extends Transition {
    private static final ProgressThresholdsGroup DEFAULT_ENTER_THRESHOLDS;
    private static final ProgressThresholdsGroup DEFAULT_ENTER_THRESHOLDS_ARC;
    private static final ProgressThresholdsGroup DEFAULT_RETURN_THRESHOLDS;
    private static final ProgressThresholdsGroup DEFAULT_RETURN_THRESHOLDS_ARC;
    private static final float ELEVATION_NOT_SET = -1.0f;
    public static final int FADE_MODE_CROSS = 2;
    public static final int FADE_MODE_IN = 0;
    public static final int FADE_MODE_OUT = 1;
    public static final int FADE_MODE_THROUGH = 3;
    public static final int FIT_MODE_AUTO = 0;
    public static final int FIT_MODE_HEIGHT = 2;
    public static final int FIT_MODE_WIDTH = 1;
    private static final String PROP_BOUNDS = "materialContainerTransition:bounds";
    private static final String PROP_SHAPE_APPEARANCE = "materialContainerTransition:shapeAppearance";
    private static final String TAG;
    public static final int TRANSITION_DIRECTION_AUTO = 0;
    public static final int TRANSITION_DIRECTION_ENTER = 1;
    public static final int TRANSITION_DIRECTION_RETURN = 2;
    private static final String[] TRANSITION_PROPS;
    private int containerColor;
    private boolean drawDebugEnabled;
    private int drawingViewId;
    private boolean elevationShadowEnabled;
    private int endContainerColor;
    private float endElevation;
    private ShapeAppearanceModel endShapeAppearanceModel;
    private View endView;
    private int endViewId;
    private int fadeMode;
    private ProgressThresholds fadeProgressThresholds;
    private int fitMode;
    private boolean holdAtEndEnabled;
    private ProgressThresholds scaleMaskProgressThresholds;
    private ProgressThresholds scaleProgressThresholds;
    private int scrimColor;
    private ProgressThresholds shapeMaskProgressThresholds;
    private int startContainerColor;
    private float startElevation;
    private ShapeAppearanceModel startShapeAppearanceModel;
    private View startView;
    private int startViewId;
    private int transitionDirection;

    static {
        TAG = MaterialContainerTransform.class.getSimpleName();
        TRANSITION_PROPS = new String[]{PROP_BOUNDS, PROP_SHAPE_APPEARANCE};
        DEFAULT_ENTER_THRESHOLDS = new ProgressThresholdsGroup(new ProgressThresholds(0.0f, 0.25f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 0.75f));
        DEFAULT_RETURN_THRESHOLDS = new ProgressThresholdsGroup(new ProgressThresholds(0.6f, 0.9f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.3f, 0.9f));
        DEFAULT_ENTER_THRESHOLDS_ARC = new ProgressThresholdsGroup(new ProgressThresholds(0.1f, 0.4f), new ProgressThresholds(0.1f, 1.0f), new ProgressThresholds(0.1f, 1.0f), new ProgressThresholds(0.1f, 0.9f));
        DEFAULT_RETURN_THRESHOLDS_ARC = new ProgressThresholdsGroup(new ProgressThresholds(0.6f, 0.9f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.2f, 0.9f));
    }

    public MaterialContainerTransform() {
        boolean bl = false;
        this.drawDebugEnabled = false;
        this.holdAtEndEnabled = false;
        this.drawingViewId = 16908290;
        this.startViewId = -1;
        this.endViewId = -1;
        this.containerColor = 0;
        this.startContainerColor = 0;
        this.endContainerColor = 0;
        this.scrimColor = 1375731712;
        this.transitionDirection = 0;
        this.fadeMode = 0;
        this.fitMode = 0;
        if (Build.VERSION.SDK_INT >= 28) {
            bl = true;
        }
        this.elevationShadowEnabled = bl;
        this.startElevation = -1.0f;
        this.endElevation = -1.0f;
        this.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    }

    private ProgressThresholdsGroup buildThresholdsGroup(boolean bl) {
        PathMotion pathMotion = this.getPathMotion();
        if (pathMotion instanceof ArcMotion) return this.getThresholdsOrDefault(bl, DEFAULT_ENTER_THRESHOLDS_ARC, DEFAULT_RETURN_THRESHOLDS_ARC);
        if (!(pathMotion instanceof MaterialArcMotion)) return this.getThresholdsOrDefault(bl, DEFAULT_ENTER_THRESHOLDS, DEFAULT_RETURN_THRESHOLDS);
        return this.getThresholdsOrDefault(bl, DEFAULT_ENTER_THRESHOLDS_ARC, DEFAULT_RETURN_THRESHOLDS_ARC);
    }

    private static RectF calculateDrawableBounds(View view, View view2, float f, float f2) {
        if (view2 == null) return new RectF(0.0f, 0.0f, (float)view.getWidth(), (float)view.getHeight());
        view = TransitionUtils.getLocationOnScreen(view2);
        view.offset(f, f2);
        return view;
    }

    private static ShapeAppearanceModel captureShapeAppearance(View view, RectF rectF, ShapeAppearanceModel shapeAppearanceModel) {
        return TransitionUtils.convertToRelativeCornerSizes(MaterialContainerTransform.getShapeAppearance(view, shapeAppearanceModel), rectF);
    }

    private static void captureValues(TransitionValues transitionValues, View view, int n, ShapeAppearanceModel shapeAppearanceModel) {
        if (n != -1) {
            transitionValues.view = TransitionUtils.findDescendantOrAncestorById(transitionValues.view, n);
        } else if (view != null) {
            transitionValues.view = view;
        } else if (transitionValues.view.getTag(R.id.mtrl_motion_snapshot_view) instanceof View) {
            view = (View)transitionValues.view.getTag(R.id.mtrl_motion_snapshot_view);
            transitionValues.view.setTag(R.id.mtrl_motion_snapshot_view, null);
            transitionValues.view = view;
        }
        View view2 = transitionValues.view;
        if (!ViewCompat.isLaidOut(view2) && view2.getWidth() == 0) {
            if (view2.getHeight() == 0) return;
        }
        view = view2.getParent() == null ? TransitionUtils.getRelativeBounds(view2) : TransitionUtils.getLocationOnScreen(view2);
        transitionValues.values.put(PROP_BOUNDS, (Object)view);
        transitionValues.values.put(PROP_SHAPE_APPEARANCE, MaterialContainerTransform.captureShapeAppearance(view2, (RectF)view, shapeAppearanceModel));
    }

    private static float getElevationOrDefault(float f, View view) {
        if (f == -1.0f) return ViewCompat.getElevation(view);
        return f;
    }

    private static ShapeAppearanceModel getShapeAppearance(View view, ShapeAppearanceModel shapeAppearanceModel) {
        if (shapeAppearanceModel != null) {
            return shapeAppearanceModel;
        }
        if (view.getTag(R.id.mtrl_motion_snapshot_view) instanceof ShapeAppearanceModel) {
            return (ShapeAppearanceModel)view.getTag(R.id.mtrl_motion_snapshot_view);
        }
        shapeAppearanceModel = view.getContext();
        int n = MaterialContainerTransform.getTransitionShapeAppearanceResId((Context)shapeAppearanceModel);
        if (n != -1) {
            return ShapeAppearanceModel.builder((Context)shapeAppearanceModel, n, 0).build();
        }
        if (!(view instanceof Shapeable)) return ShapeAppearanceModel.builder().build();
        return ((Shapeable)view).getShapeAppearanceModel();
    }

    private ProgressThresholdsGroup getThresholdsOrDefault(boolean bl, ProgressThresholdsGroup progressThresholdsGroup, ProgressThresholdsGroup progressThresholdsGroup2) {
        if (bl) {
            return new ProgressThresholdsGroup(TransitionUtils.defaultIfNull(this.fadeProgressThresholds, progressThresholdsGroup.fade), TransitionUtils.defaultIfNull(this.scaleProgressThresholds, progressThresholdsGroup.scale), TransitionUtils.defaultIfNull(this.scaleMaskProgressThresholds, progressThresholdsGroup.scaleMask), TransitionUtils.defaultIfNull(this.shapeMaskProgressThresholds, progressThresholdsGroup.shapeMask));
        }
        progressThresholdsGroup = progressThresholdsGroup2;
        return new ProgressThresholdsGroup(TransitionUtils.defaultIfNull(this.fadeProgressThresholds, progressThresholdsGroup.fade), TransitionUtils.defaultIfNull(this.scaleProgressThresholds, progressThresholdsGroup.scale), TransitionUtils.defaultIfNull(this.scaleMaskProgressThresholds, progressThresholdsGroup.scaleMask), TransitionUtils.defaultIfNull(this.shapeMaskProgressThresholds, progressThresholdsGroup.shapeMask));
    }

    private static int getTransitionShapeAppearanceResId(Context context) {
        context = context.obtainStyledAttributes(new int[]{R.attr.transitionShapeAppearance});
        int n = context.getResourceId(0, -1);
        context.recycle();
        return n;
    }

    private boolean isEntering(RectF object, RectF rectF) {
        int n = this.transitionDirection;
        boolean bl = false;
        if (n == 0) {
            if (!(TransitionUtils.calculateArea(rectF) > TransitionUtils.calculateArea((RectF)object))) return bl;
            return true;
        }
        if (n == 1) return true;
        if (n == 2) {
            return false;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid transition direction: ");
        ((StringBuilder)object).append(this.transitionDirection);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        MaterialContainerTransform.captureValues(transitionValues, this.endView, this.endViewId, this.endShapeAppearanceModel);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        MaterialContainerTransform.captureValues(transitionValues, this.startView, this.startViewId, this.startShapeAppearanceModel);
    }

    @Override
    public Animator createAnimator(ViewGroup object, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null) return null;
        if (transitionValues2 == null) {
            return null;
        }
        RectF rectF = (RectF)transitionValues.values.get(PROP_BOUNDS);
        ShapeAppearanceModel shapeAppearanceModel = (ShapeAppearanceModel)transitionValues.values.get(PROP_SHAPE_APPEARANCE);
        if (rectF != null && shapeAppearanceModel != null) {
            RectF rectF2 = (RectF)transitionValues2.values.get(PROP_BOUNDS);
            ShapeAppearanceModel shapeAppearanceModel2 = (ShapeAppearanceModel)transitionValues2.values.get(PROP_SHAPE_APPEARANCE);
            if (rectF2 != null && shapeAppearanceModel2 != null) {
                Object object2;
                View view = transitionValues.view;
                transitionValues2 = transitionValues2.view;
                object = transitionValues2.getParent() != null ? transitionValues2 : view;
                if (this.drawingViewId == object.getId()) {
                    object2 = (View)object.getParent();
                    transitionValues = object;
                    object = object2;
                } else {
                    object = TransitionUtils.findAncestorById((View)object, this.drawingViewId);
                    transitionValues = null;
                }
                object2 = TransitionUtils.getLocationOnScreen((View)object);
                float f = -object2.left;
                float f2 = -object2.top;
                transitionValues = MaterialContainerTransform.calculateDrawableBounds((View)object, (View)transitionValues, f, f2);
                rectF.offset(f, f2);
                rectF2.offset(f, f2);
                boolean bl = this.isEntering(rectF, rectF2);
                object2 = new TransitionDrawable(this.getPathMotion(), view, rectF, shapeAppearanceModel, MaterialContainerTransform.getElevationOrDefault(this.startElevation, view), (View)transitionValues2, rectF2, shapeAppearanceModel2, MaterialContainerTransform.getElevationOrDefault(this.endElevation, (View)transitionValues2), this.containerColor, this.startContainerColor, this.endContainerColor, this.scrimColor, bl, this.elevationShadowEnabled, FadeModeEvaluators.get(this.fadeMode, bl), FitModeEvaluators.get(this.fitMode, bl, rectF, rectF2), this.buildThresholdsGroup(bl), this.drawDebugEnabled);
                object2.setBounds(Math.round(((RectF)transitionValues).left), Math.round(((RectF)transitionValues).top), Math.round(((RectF)transitionValues).right), Math.round(((RectF)transitionValues).bottom));
                transitionValues = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
                transitionValues.addUpdateListener(new ValueAnimator.AnimatorUpdateListener((TransitionDrawable)((Object)object2)){
                    final /* synthetic */ TransitionDrawable val$transitionDrawable;
                    {
                        this.val$transitionDrawable = transitionDrawable;
                    }

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.val$transitionDrawable.setProgress(valueAnimator.getAnimatedFraction());
                    }
                });
                this.addListener(new TransitionListenerAdapter((View)object, (TransitionDrawable)((Object)object2), view, (View)transitionValues2){
                    final /* synthetic */ View val$drawingView;
                    final /* synthetic */ View val$endView;
                    final /* synthetic */ View val$startView;
                    final /* synthetic */ TransitionDrawable val$transitionDrawable;
                    {
                        this.val$drawingView = view;
                        this.val$transitionDrawable = transitionDrawable;
                        this.val$startView = view2;
                        this.val$endView = view3;
                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        MaterialContainerTransform.this.removeListener(this);
                        if (MaterialContainerTransform.this.holdAtEndEnabled) {
                            return;
                        }
                        this.val$startView.setAlpha(1.0f);
                        this.val$endView.setAlpha(1.0f);
                        ViewUtils.getOverlay(this.val$drawingView).remove(this.val$transitionDrawable);
                    }

                    @Override
                    public void onTransitionStart(Transition transition) {
                        ViewUtils.getOverlay(this.val$drawingView).add(this.val$transitionDrawable);
                        this.val$startView.setAlpha(0.0f);
                        this.val$endView.setAlpha(0.0f);
                    }
                });
                return transitionValues;
            }
            Log.w((String)TAG, (String)"Skipping due to null end bounds. Ensure end view is laid out and measured.");
            return null;
        }
        Log.w((String)TAG, (String)"Skipping due to null start bounds. Ensure start view is laid out and measured.");
        return null;
    }

    public int getContainerColor() {
        return this.containerColor;
    }

    public int getDrawingViewId() {
        return this.drawingViewId;
    }

    public int getEndContainerColor() {
        return this.endContainerColor;
    }

    public float getEndElevation() {
        return this.endElevation;
    }

    public ShapeAppearanceModel getEndShapeAppearanceModel() {
        return this.endShapeAppearanceModel;
    }

    public View getEndView() {
        return this.endView;
    }

    public int getEndViewId() {
        return this.endViewId;
    }

    public int getFadeMode() {
        return this.fadeMode;
    }

    public ProgressThresholds getFadeProgressThresholds() {
        return this.fadeProgressThresholds;
    }

    public int getFitMode() {
        return this.fitMode;
    }

    public ProgressThresholds getScaleMaskProgressThresholds() {
        return this.scaleMaskProgressThresholds;
    }

    public ProgressThresholds getScaleProgressThresholds() {
        return this.scaleProgressThresholds;
    }

    public int getScrimColor() {
        return this.scrimColor;
    }

    public ProgressThresholds getShapeMaskProgressThresholds() {
        return this.shapeMaskProgressThresholds;
    }

    public int getStartContainerColor() {
        return this.startContainerColor;
    }

    public float getStartElevation() {
        return this.startElevation;
    }

    public ShapeAppearanceModel getStartShapeAppearanceModel() {
        return this.startShapeAppearanceModel;
    }

    public View getStartView() {
        return this.startView;
    }

    public int getStartViewId() {
        return this.startViewId;
    }

    public int getTransitionDirection() {
        return this.transitionDirection;
    }

    @Override
    public String[] getTransitionProperties() {
        return TRANSITION_PROPS;
    }

    public boolean isDrawDebugEnabled() {
        return this.drawDebugEnabled;
    }

    public boolean isElevationShadowEnabled() {
        return this.elevationShadowEnabled;
    }

    public boolean isHoldAtEndEnabled() {
        return this.holdAtEndEnabled;
    }

    public void setAllContainerColors(int n) {
        this.containerColor = n;
        this.startContainerColor = n;
        this.endContainerColor = n;
    }

    public void setContainerColor(int n) {
        this.containerColor = n;
    }

    public void setDrawDebugEnabled(boolean bl) {
        this.drawDebugEnabled = bl;
    }

    public void setDrawingViewId(int n) {
        this.drawingViewId = n;
    }

    public void setElevationShadowEnabled(boolean bl) {
        this.elevationShadowEnabled = bl;
    }

    public void setEndContainerColor(int n) {
        this.endContainerColor = n;
    }

    public void setEndElevation(float f) {
        this.endElevation = f;
    }

    public void setEndShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.endShapeAppearanceModel = shapeAppearanceModel;
    }

    public void setEndView(View view) {
        this.endView = view;
    }

    public void setEndViewId(int n) {
        this.endViewId = n;
    }

    public void setFadeMode(int n) {
        this.fadeMode = n;
    }

    public void setFadeProgressThresholds(ProgressThresholds progressThresholds) {
        this.fadeProgressThresholds = progressThresholds;
    }

    public void setFitMode(int n) {
        this.fitMode = n;
    }

    public void setHoldAtEndEnabled(boolean bl) {
        this.holdAtEndEnabled = bl;
    }

    public void setScaleMaskProgressThresholds(ProgressThresholds progressThresholds) {
        this.scaleMaskProgressThresholds = progressThresholds;
    }

    public void setScaleProgressThresholds(ProgressThresholds progressThresholds) {
        this.scaleProgressThresholds = progressThresholds;
    }

    public void setScrimColor(int n) {
        this.scrimColor = n;
    }

    public void setShapeMaskProgressThresholds(ProgressThresholds progressThresholds) {
        this.shapeMaskProgressThresholds = progressThresholds;
    }

    public void setStartContainerColor(int n) {
        this.startContainerColor = n;
    }

    public void setStartElevation(float f) {
        this.startElevation = f;
    }

    public void setStartShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.startShapeAppearanceModel = shapeAppearanceModel;
    }

    public void setStartView(View view) {
        this.startView = view;
    }

    public void setStartViewId(int n) {
        this.startViewId = n;
    }

    public void setTransitionDirection(int n) {
        this.transitionDirection = n;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FadeMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FitMode {
    }

    public static class ProgressThresholds {
        private final float end;
        private final float start;

        public ProgressThresholds(float f, float f2) {
            this.start = f;
            this.end = f2;
        }

        public float getEnd() {
            return this.end;
        }

        public float getStart() {
            return this.start;
        }
    }

    private static class ProgressThresholdsGroup {
        private final ProgressThresholds fade;
        private final ProgressThresholds scale;
        private final ProgressThresholds scaleMask;
        private final ProgressThresholds shapeMask;

        private ProgressThresholdsGroup(ProgressThresholds progressThresholds, ProgressThresholds progressThresholds2, ProgressThresholds progressThresholds3, ProgressThresholds progressThresholds4) {
            this.fade = progressThresholds;
            this.scale = progressThresholds2;
            this.scaleMask = progressThresholds3;
            this.shapeMask = progressThresholds4;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TransitionDirection {
    }

    private static final class TransitionDrawable
    extends Drawable {
        private static final int COMPAT_SHADOW_COLOR = -7829368;
        private static final int SHADOW_COLOR = 754974720;
        private static final float SHADOW_DX_MULTIPLIER_ADJUSTMENT = 0.3f;
        private static final float SHADOW_DY_MULTIPLIER_ADJUSTMENT = 1.5f;
        private final MaterialShapeDrawable compatShadowDrawable = new MaterialShapeDrawable();
        private final Paint containerPaint = new Paint();
        private float currentElevation;
        private float currentElevationDy;
        private final RectF currentEndBounds;
        private final RectF currentEndBoundsMasked;
        private RectF currentMaskBounds;
        private final RectF currentStartBounds;
        private final RectF currentStartBoundsMasked;
        private final Paint debugPaint = new Paint();
        private final Path debugPath = new Path();
        private final float displayHeight;
        private final float displayWidth;
        private final boolean drawDebugEnabled;
        private final boolean elevationShadowEnabled;
        private final RectF endBounds;
        private final Paint endContainerPaint = new Paint();
        private final float endElevation;
        private final ShapeAppearanceModel endShapeAppearanceModel;
        private final View endView;
        private final boolean entering;
        private final FadeModeEvaluator fadeModeEvaluator;
        private FadeModeResult fadeModeResult;
        private final FitModeEvaluator fitModeEvaluator;
        private FitModeResult fitModeResult;
        private final MaskEvaluator maskEvaluator = new MaskEvaluator();
        private final float motionPathLength;
        private final PathMeasure motionPathMeasure;
        private final float[] motionPathPosition = new float[2];
        private float progress;
        private final ProgressThresholdsGroup progressThresholds;
        private final Paint scrimPaint = new Paint();
        private final Paint shadowPaint = new Paint();
        private final RectF startBounds;
        private final Paint startContainerPaint = new Paint();
        private final float startElevation;
        private final ShapeAppearanceModel startShapeAppearanceModel;
        private final View startView;

        private TransitionDrawable(PathMotion pathMotion, View view, RectF rectF, ShapeAppearanceModel shapeAppearanceModel, float f, View view2, RectF rectF2, ShapeAppearanceModel shapeAppearanceModel2, float f2, int n, int n2, int n3, int n4, boolean bl, boolean bl2, FadeModeEvaluator fadeModeEvaluator, FitModeEvaluator fitModeEvaluator, ProgressThresholdsGroup progressThresholdsGroup, boolean bl3) {
            this.startView = view;
            this.startBounds = rectF;
            this.startShapeAppearanceModel = shapeAppearanceModel;
            this.startElevation = f;
            this.endView = view2;
            this.endBounds = rectF2;
            this.endShapeAppearanceModel = shapeAppearanceModel2;
            this.endElevation = f2;
            this.entering = bl;
            this.elevationShadowEnabled = bl2;
            this.fadeModeEvaluator = fadeModeEvaluator;
            this.fitModeEvaluator = fitModeEvaluator;
            this.progressThresholds = progressThresholdsGroup;
            this.drawDebugEnabled = bl3;
            view = (WindowManager)view.getContext().getSystemService("window");
            shapeAppearanceModel = new DisplayMetrics();
            view.getDefaultDisplay().getMetrics((DisplayMetrics)shapeAppearanceModel);
            this.displayWidth = ((DisplayMetrics)shapeAppearanceModel).widthPixels;
            this.displayHeight = ((DisplayMetrics)shapeAppearanceModel).heightPixels;
            this.containerPaint.setColor(n);
            this.startContainerPaint.setColor(n2);
            this.endContainerPaint.setColor(n3);
            this.compatShadowDrawable.setFillColor(ColorStateList.valueOf((int)0));
            this.compatShadowDrawable.setShadowCompatibilityMode(2);
            this.compatShadowDrawable.setShadowBitmapDrawingEnable(false);
            this.compatShadowDrawable.setShadowColor(-7829368);
            this.currentStartBounds = new RectF(rectF);
            this.currentStartBoundsMasked = new RectF(this.currentStartBounds);
            this.currentEndBounds = new RectF(this.currentStartBounds);
            this.currentEndBoundsMasked = new RectF(this.currentEndBounds);
            view = TransitionDrawable.getMotionPathPoint(rectF);
            shapeAppearanceModel = TransitionDrawable.getMotionPathPoint(rectF2);
            pathMotion = new PathMeasure(pathMotion.getPath(view.x, view.y, ((PointF)shapeAppearanceModel).x, ((PointF)shapeAppearanceModel).y), false);
            this.motionPathMeasure = pathMotion;
            this.motionPathLength = pathMotion.getLength();
            this.motionPathPosition[0] = rectF.centerX();
            this.motionPathPosition[1] = rectF.top;
            this.scrimPaint.setStyle(Paint.Style.FILL);
            this.scrimPaint.setShader(TransitionUtils.createColorShader(n4));
            this.debugPaint.setStyle(Paint.Style.STROKE);
            this.debugPaint.setStrokeWidth(10.0f);
            this.updateProgress(0.0f);
        }

        private static float calculateElevationDxMultiplier(RectF rectF, float f) {
            return (rectF.centerX() / (f / 2.0f) - 1.0f) * 0.3f;
        }

        private static float calculateElevationDyMultiplier(RectF rectF, float f) {
            return rectF.centerY() / f * 1.5f;
        }

        private void drawDebugCumulativePath(Canvas canvas, RectF rectF, Path path, int n) {
            rectF = TransitionDrawable.getMotionPathPoint(rectF);
            if (this.progress == 0.0f) {
                path.reset();
                path.moveTo(rectF.x, rectF.y);
                return;
            }
            path.lineTo(rectF.x, rectF.y);
            this.debugPaint.setColor(n);
            canvas.drawPath(path, this.debugPaint);
        }

        private void drawDebugRect(Canvas canvas, RectF rectF, int n) {
            this.debugPaint.setColor(n);
            canvas.drawRect(rectF, this.debugPaint);
        }

        private void drawElevationShadow(Canvas canvas) {
            canvas.save();
            canvas.clipPath(this.maskEvaluator.getPath(), Region.Op.DIFFERENCE);
            if (Build.VERSION.SDK_INT > 28) {
                this.drawElevationShadowWithPaintShadowLayer(canvas);
            } else {
                this.drawElevationShadowWithMaterialShapeDrawable(canvas);
            }
            canvas.restore();
        }

        private void drawElevationShadowWithMaterialShapeDrawable(Canvas canvas) {
            this.compatShadowDrawable.setBounds((int)this.currentMaskBounds.left, (int)this.currentMaskBounds.top, (int)this.currentMaskBounds.right, (int)this.currentMaskBounds.bottom);
            this.compatShadowDrawable.setElevation(this.currentElevation);
            this.compatShadowDrawable.setShadowVerticalOffset((int)this.currentElevationDy);
            this.compatShadowDrawable.setShapeAppearanceModel(this.maskEvaluator.getCurrentShapeAppearanceModel());
            this.compatShadowDrawable.draw(canvas);
        }

        private void drawElevationShadowWithPaintShadowLayer(Canvas canvas) {
            ShapeAppearanceModel shapeAppearanceModel = this.maskEvaluator.getCurrentShapeAppearanceModel();
            if (shapeAppearanceModel.isRoundRect(this.currentMaskBounds)) {
                float f = shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.currentMaskBounds);
                canvas.drawRoundRect(this.currentMaskBounds, f, f, this.shadowPaint);
                return;
            }
            canvas.drawPath(this.maskEvaluator.getPath(), this.shadowPaint);
        }

        private void drawEndView(Canvas canvas) {
            this.maybeDrawContainerColor(canvas, this.endContainerPaint);
            TransitionUtils.transform(canvas, this.getBounds(), this.currentEndBounds.left, this.currentEndBounds.top, this.fitModeResult.endScale, this.fadeModeResult.endAlpha, new TransitionUtils.CanvasOperation(){

                @Override
                public void run(Canvas canvas) {
                    TransitionDrawable.this.endView.draw(canvas);
                }
            });
        }

        private void drawStartView(Canvas canvas) {
            this.maybeDrawContainerColor(canvas, this.startContainerPaint);
            TransitionUtils.transform(canvas, this.getBounds(), this.currentStartBounds.left, this.currentStartBounds.top, this.fitModeResult.startScale, this.fadeModeResult.startAlpha, new TransitionUtils.CanvasOperation(){

                @Override
                public void run(Canvas canvas) {
                    TransitionDrawable.this.startView.draw(canvas);
                }
            });
        }

        private static PointF getMotionPathPoint(RectF rectF) {
            return new PointF(rectF.centerX(), rectF.top);
        }

        private void maybeDrawContainerColor(Canvas canvas, Paint paint) {
            if (paint.getColor() == 0) return;
            if (paint.getAlpha() <= 0) return;
            canvas.drawRect(this.getBounds(), paint);
        }

        private void setProgress(float f) {
            if (this.progress == f) return;
            this.updateProgress(f);
        }

        private void updateProgress(float f) {
            this.progress = f;
            RectF rectF = this.scrimPaint;
            float f2 = this.entering ? TransitionUtils.lerp(0.0f, 255.0f, f) : TransitionUtils.lerp(255.0f, 0.0f, f);
            rectF.setAlpha((int)f2);
            this.motionPathMeasure.getPosTan(this.motionPathLength * f, this.motionPathPosition, null);
            rectF = this.motionPathPosition;
            f2 = rectF[0];
            float f3 = rectF[1];
            float f4 = Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.scale.start)).floatValue();
            float f5 = Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.scale.end)).floatValue();
            rectF = this.fitModeEvaluator.evaluate(f, f4, f5, this.startBounds.width(), this.startBounds.height(), this.endBounds.width(), this.endBounds.height());
            this.fitModeResult = rectF;
            this.currentStartBounds.set(f2 - rectF.currentStartWidth / 2.0f, f3, this.fitModeResult.currentStartWidth / 2.0f + f2, this.fitModeResult.currentStartHeight + f3);
            this.currentEndBounds.set(f2 - this.fitModeResult.currentEndWidth / 2.0f, f3, f2 + this.fitModeResult.currentEndWidth / 2.0f, this.fitModeResult.currentEndHeight + f3);
            this.currentStartBoundsMasked.set(this.currentStartBounds);
            this.currentEndBoundsMasked.set(this.currentEndBounds);
            f2 = Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.scaleMask.start)).floatValue();
            f5 = Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.scaleMask.end)).floatValue();
            boolean bl = this.fitModeEvaluator.shouldMaskStartBounds(this.fitModeResult);
            rectF = bl ? this.currentStartBoundsMasked : this.currentEndBoundsMasked;
            f2 = TransitionUtils.lerp(0.0f, 1.0f, f2, f5, f);
            if (!bl) {
                f2 = 1.0f - f2;
            }
            this.fitModeEvaluator.applyMask(rectF, f2, this.fitModeResult);
            this.currentMaskBounds = new RectF(Math.min(this.currentStartBoundsMasked.left, this.currentEndBoundsMasked.left), Math.min(this.currentStartBoundsMasked.top, this.currentEndBoundsMasked.top), Math.max(this.currentStartBoundsMasked.right, this.currentEndBoundsMasked.right), Math.max(this.currentStartBoundsMasked.bottom, this.currentEndBoundsMasked.bottom));
            this.maskEvaluator.evaluate(f, this.startShapeAppearanceModel, this.endShapeAppearanceModel, this.currentStartBounds, this.currentStartBoundsMasked, this.currentEndBoundsMasked, this.progressThresholds.shapeMask);
            this.currentElevation = TransitionUtils.lerp(this.startElevation, this.endElevation, f);
            f4 = TransitionDrawable.calculateElevationDxMultiplier(this.currentMaskBounds, this.displayWidth);
            f5 = TransitionDrawable.calculateElevationDyMultiplier(this.currentMaskBounds, this.displayHeight);
            f2 = this.currentElevation;
            f4 = (int)(f4 * f2);
            this.currentElevationDy = f5 = (float)((int)(f5 * f2));
            this.shadowPaint.setShadowLayer(f2, f4, f5, 754974720);
            f2 = Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.fade.start)).floatValue();
            f5 = Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.fade.end)).floatValue();
            this.fadeModeResult = this.fadeModeEvaluator.evaluate(f, f2, f5);
            if (this.startContainerPaint.getColor() != 0) {
                this.startContainerPaint.setAlpha(this.fadeModeResult.startAlpha);
            }
            if (this.endContainerPaint.getColor() != 0) {
                this.endContainerPaint.setAlpha(this.fadeModeResult.endAlpha);
            }
            this.invalidateSelf();
        }

        public void draw(Canvas canvas) {
            if (this.scrimPaint.getAlpha() > 0) {
                canvas.drawRect(this.getBounds(), this.scrimPaint);
            }
            int n = this.drawDebugEnabled ? canvas.save() : -1;
            if (this.elevationShadowEnabled && this.currentElevation > 0.0f) {
                this.drawElevationShadow(canvas);
            }
            this.maskEvaluator.clip(canvas);
            this.maybeDrawContainerColor(canvas, this.containerPaint);
            if (this.fadeModeResult.endOnTop) {
                this.drawStartView(canvas);
                this.drawEndView(canvas);
            } else {
                this.drawEndView(canvas);
                this.drawStartView(canvas);
            }
            if (!this.drawDebugEnabled) return;
            canvas.restoreToCount(n);
            this.drawDebugCumulativePath(canvas, this.currentStartBounds, this.debugPath, -65281);
            this.drawDebugRect(canvas, this.currentStartBoundsMasked, -256);
            this.drawDebugRect(canvas, this.currentStartBounds, -16711936);
            this.drawDebugRect(canvas, this.currentEndBoundsMasked, -16711681);
            this.drawDebugRect(canvas, this.currentEndBounds, -16776961);
        }

        public int getOpacity() {
            return -3;
        }

        public void setAlpha(int n) {
            throw new UnsupportedOperationException("Setting alpha on is not supported");
        }

        public void setColorFilter(ColorFilter colorFilter) {
            throw new UnsupportedOperationException("Setting a color filter is not supported");
        }

    }

}

