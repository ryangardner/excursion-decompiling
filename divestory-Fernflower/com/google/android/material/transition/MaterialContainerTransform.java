package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class MaterialContainerTransform extends Transition {
   private static final MaterialContainerTransform.ProgressThresholdsGroup DEFAULT_ENTER_THRESHOLDS = new MaterialContainerTransform.ProgressThresholdsGroup(new MaterialContainerTransform.ProgressThresholds(0.0F, 0.25F), new MaterialContainerTransform.ProgressThresholds(0.0F, 1.0F), new MaterialContainerTransform.ProgressThresholds(0.0F, 1.0F), new MaterialContainerTransform.ProgressThresholds(0.0F, 0.75F));
   private static final MaterialContainerTransform.ProgressThresholdsGroup DEFAULT_ENTER_THRESHOLDS_ARC = new MaterialContainerTransform.ProgressThresholdsGroup(new MaterialContainerTransform.ProgressThresholds(0.1F, 0.4F), new MaterialContainerTransform.ProgressThresholds(0.1F, 1.0F), new MaterialContainerTransform.ProgressThresholds(0.1F, 1.0F), new MaterialContainerTransform.ProgressThresholds(0.1F, 0.9F));
   private static final MaterialContainerTransform.ProgressThresholdsGroup DEFAULT_RETURN_THRESHOLDS = new MaterialContainerTransform.ProgressThresholdsGroup(new MaterialContainerTransform.ProgressThresholds(0.6F, 0.9F), new MaterialContainerTransform.ProgressThresholds(0.0F, 1.0F), new MaterialContainerTransform.ProgressThresholds(0.0F, 0.9F), new MaterialContainerTransform.ProgressThresholds(0.3F, 0.9F));
   private static final MaterialContainerTransform.ProgressThresholdsGroup DEFAULT_RETURN_THRESHOLDS_ARC = new MaterialContainerTransform.ProgressThresholdsGroup(new MaterialContainerTransform.ProgressThresholds(0.6F, 0.9F), new MaterialContainerTransform.ProgressThresholds(0.0F, 0.9F), new MaterialContainerTransform.ProgressThresholds(0.0F, 0.9F), new MaterialContainerTransform.ProgressThresholds(0.2F, 0.9F));
   private static final float ELEVATION_NOT_SET = -1.0F;
   public static final int FADE_MODE_CROSS = 2;
   public static final int FADE_MODE_IN = 0;
   public static final int FADE_MODE_OUT = 1;
   public static final int FADE_MODE_THROUGH = 3;
   public static final int FIT_MODE_AUTO = 0;
   public static final int FIT_MODE_HEIGHT = 2;
   public static final int FIT_MODE_WIDTH = 1;
   private static final String PROP_BOUNDS = "materialContainerTransition:bounds";
   private static final String PROP_SHAPE_APPEARANCE = "materialContainerTransition:shapeAppearance";
   private static final String TAG = MaterialContainerTransform.class.getSimpleName();
   public static final int TRANSITION_DIRECTION_AUTO = 0;
   public static final int TRANSITION_DIRECTION_ENTER = 1;
   public static final int TRANSITION_DIRECTION_RETURN = 2;
   private static final String[] TRANSITION_PROPS = new String[]{"materialContainerTransition:bounds", "materialContainerTransition:shapeAppearance"};
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
   private MaterialContainerTransform.ProgressThresholds fadeProgressThresholds;
   private int fitMode;
   private boolean holdAtEndEnabled;
   private MaterialContainerTransform.ProgressThresholds scaleMaskProgressThresholds;
   private MaterialContainerTransform.ProgressThresholds scaleProgressThresholds;
   private int scrimColor;
   private MaterialContainerTransform.ProgressThresholds shapeMaskProgressThresholds;
   private int startContainerColor;
   private float startElevation;
   private ShapeAppearanceModel startShapeAppearanceModel;
   private View startView;
   private int startViewId;
   private int transitionDirection;

   public MaterialContainerTransform() {
      boolean var1 = false;
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
      if (VERSION.SDK_INT >= 28) {
         var1 = true;
      }

      this.elevationShadowEnabled = var1;
      this.startElevation = -1.0F;
      this.endElevation = -1.0F;
      this.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
   }

   private MaterialContainerTransform.ProgressThresholdsGroup buildThresholdsGroup(boolean var1) {
      PathMotion var2 = this.getPathMotion();
      return !(var2 instanceof ArcMotion) && !(var2 instanceof MaterialArcMotion) ? this.getThresholdsOrDefault(var1, DEFAULT_ENTER_THRESHOLDS, DEFAULT_RETURN_THRESHOLDS) : this.getThresholdsOrDefault(var1, DEFAULT_ENTER_THRESHOLDS_ARC, DEFAULT_RETURN_THRESHOLDS_ARC);
   }

   private static RectF calculateDrawableBounds(View var0, View var1, float var2, float var3) {
      if (var1 != null) {
         RectF var4 = TransitionUtils.getLocationOnScreen(var1);
         var4.offset(var2, var3);
         return var4;
      } else {
         return new RectF(0.0F, 0.0F, (float)var0.getWidth(), (float)var0.getHeight());
      }
   }

   private static ShapeAppearanceModel captureShapeAppearance(View var0, RectF var1, ShapeAppearanceModel var2) {
      return TransitionUtils.convertToRelativeCornerSizes(getShapeAppearance(var0, var2), var1);
   }

   private static void captureValues(TransitionValues var0, View var1, int var2, ShapeAppearanceModel var3) {
      if (var2 != -1) {
         var0.view = TransitionUtils.findDescendantOrAncestorById(var0.view, var2);
      } else if (var1 != null) {
         var0.view = var1;
      } else if (var0.view.getTag(R.id.mtrl_motion_snapshot_view) instanceof View) {
         var1 = (View)var0.view.getTag(R.id.mtrl_motion_snapshot_view);
         var0.view.setTag(R.id.mtrl_motion_snapshot_view, (Object)null);
         var0.view = var1;
      }

      View var4 = var0.view;
      if (ViewCompat.isLaidOut(var4) || var4.getWidth() != 0 || var4.getHeight() != 0) {
         RectF var5;
         if (var4.getParent() == null) {
            var5 = TransitionUtils.getRelativeBounds(var4);
         } else {
            var5 = TransitionUtils.getLocationOnScreen(var4);
         }

         var0.values.put("materialContainerTransition:bounds", var5);
         var0.values.put("materialContainerTransition:shapeAppearance", captureShapeAppearance(var4, var5, var3));
      }

   }

   private static float getElevationOrDefault(float var0, View var1) {
      if (var0 == -1.0F) {
         var0 = ViewCompat.getElevation(var1);
      }

      return var0;
   }

   private static ShapeAppearanceModel getShapeAppearance(View var0, ShapeAppearanceModel var1) {
      if (var1 != null) {
         return var1;
      } else if (var0.getTag(R.id.mtrl_motion_snapshot_view) instanceof ShapeAppearanceModel) {
         return (ShapeAppearanceModel)var0.getTag(R.id.mtrl_motion_snapshot_view);
      } else {
         Context var3 = var0.getContext();
         int var2 = getTransitionShapeAppearanceResId(var3);
         if (var2 != -1) {
            return ShapeAppearanceModel.builder(var3, var2, 0).build();
         } else {
            return var0 instanceof Shapeable ? ((Shapeable)var0).getShapeAppearanceModel() : ShapeAppearanceModel.builder().build();
         }
      }
   }

   private MaterialContainerTransform.ProgressThresholdsGroup getThresholdsOrDefault(boolean var1, MaterialContainerTransform.ProgressThresholdsGroup var2, MaterialContainerTransform.ProgressThresholdsGroup var3) {
      if (!var1) {
         var2 = var3;
      }

      return new MaterialContainerTransform.ProgressThresholdsGroup((MaterialContainerTransform.ProgressThresholds)TransitionUtils.defaultIfNull(this.fadeProgressThresholds, var2.fade), (MaterialContainerTransform.ProgressThresholds)TransitionUtils.defaultIfNull(this.scaleProgressThresholds, var2.scale), (MaterialContainerTransform.ProgressThresholds)TransitionUtils.defaultIfNull(this.scaleMaskProgressThresholds, var2.scaleMask), (MaterialContainerTransform.ProgressThresholds)TransitionUtils.defaultIfNull(this.shapeMaskProgressThresholds, var2.shapeMask));
   }

   private static int getTransitionShapeAppearanceResId(Context var0) {
      TypedArray var2 = var0.obtainStyledAttributes(new int[]{R.attr.transitionShapeAppearance});
      int var1 = var2.getResourceId(0, -1);
      var2.recycle();
      return var1;
   }

   private boolean isEntering(RectF var1, RectF var2) {
      int var3 = this.transitionDirection;
      boolean var4 = false;
      if (var3 != 0) {
         if (var3 != 1) {
            if (var3 == 2) {
               return false;
            } else {
               StringBuilder var5 = new StringBuilder();
               var5.append("Invalid transition direction: ");
               var5.append(this.transitionDirection);
               throw new IllegalArgumentException(var5.toString());
            }
         } else {
            return true;
         }
      } else {
         if (TransitionUtils.calculateArea(var2) > TransitionUtils.calculateArea(var1)) {
            var4 = true;
         }

         return var4;
      }
   }

   public void captureEndValues(TransitionValues var1) {
      captureValues(var1, this.endView, this.endViewId, this.endShapeAppearanceModel);
   }

   public void captureStartValues(TransitionValues var1) {
      captureValues(var1, this.startView, this.startViewId, this.startShapeAppearanceModel);
   }

   public Animator createAnimator(ViewGroup var1, TransitionValues var2, TransitionValues var3) {
      if (var2 != null && var3 != null) {
         RectF var4 = (RectF)var2.values.get("materialContainerTransition:bounds");
         ShapeAppearanceModel var5 = (ShapeAppearanceModel)var2.values.get("materialContainerTransition:shapeAppearance");
         if (var4 != null && var5 != null) {
            RectF var6 = (RectF)var3.values.get("materialContainerTransition:bounds");
            ShapeAppearanceModel var7 = (ShapeAppearanceModel)var3.values.get("materialContainerTransition:shapeAppearance");
            if (var6 != null && var7 != null) {
               final View var8 = var2.view;
               final View var15 = var3.view;
               final View var13;
               if (var15.getParent() != null) {
                  var13 = var15;
               } else {
                  var13 = var8;
               }

               View var14;
               if (this.drawingViewId == var13.getId()) {
                  View var9 = (View)var13.getParent();
                  var14 = var13;
                  var13 = var9;
               } else {
                  var13 = TransitionUtils.findAncestorById(var13, this.drawingViewId);
                  var14 = null;
               }

               RectF var18 = TransitionUtils.getLocationOnScreen(var13);
               float var10 = -var18.left;
               float var11 = -var18.top;
               RectF var16 = calculateDrawableBounds(var13, var14, var10, var11);
               var4.offset(var10, var11);
               var6.offset(var10, var11);
               boolean var12 = this.isEntering(var4, var6);
               final MaterialContainerTransform.TransitionDrawable var19 = new MaterialContainerTransform.TransitionDrawable(this.getPathMotion(), var8, var4, var5, getElevationOrDefault(this.startElevation, var8), var15, var6, var7, getElevationOrDefault(this.endElevation, var15), this.containerColor, this.startContainerColor, this.endContainerColor, this.scrimColor, var12, this.elevationShadowEnabled, FadeModeEvaluators.get(this.fadeMode, var12), FitModeEvaluators.get(this.fitMode, var12, var4, var6), this.buildThresholdsGroup(var12), this.drawDebugEnabled);
               var19.setBounds(Math.round(var16.left), Math.round(var16.top), Math.round(var16.right), Math.round(var16.bottom));
               ValueAnimator var17 = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F});
               var17.addUpdateListener(new AnimatorUpdateListener() {
                  public void onAnimationUpdate(ValueAnimator var1) {
                     var19.setProgress(var1.getAnimatedFraction());
                  }
               });
               this.addListener(new TransitionListenerAdapter() {
                  public void onTransitionEnd(Transition var1) {
                     MaterialContainerTransform.this.removeListener(this);
                     if (!MaterialContainerTransform.this.holdAtEndEnabled) {
                        var8.setAlpha(1.0F);
                        var15.setAlpha(1.0F);
                        ViewUtils.getOverlay(var13).remove(var19);
                     }
                  }

                  public void onTransitionStart(Transition var1) {
                     ViewUtils.getOverlay(var13).add(var19);
                     var8.setAlpha(0.0F);
                     var15.setAlpha(0.0F);
                  }
               });
               return var17;
            }

            Log.w(TAG, "Skipping due to null end bounds. Ensure end view is laid out and measured.");
            return null;
         }

         Log.w(TAG, "Skipping due to null start bounds. Ensure start view is laid out and measured.");
      }

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

   public MaterialContainerTransform.ProgressThresholds getFadeProgressThresholds() {
      return this.fadeProgressThresholds;
   }

   public int getFitMode() {
      return this.fitMode;
   }

   public MaterialContainerTransform.ProgressThresholds getScaleMaskProgressThresholds() {
      return this.scaleMaskProgressThresholds;
   }

   public MaterialContainerTransform.ProgressThresholds getScaleProgressThresholds() {
      return this.scaleProgressThresholds;
   }

   public int getScrimColor() {
      return this.scrimColor;
   }

   public MaterialContainerTransform.ProgressThresholds getShapeMaskProgressThresholds() {
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

   public void setAllContainerColors(int var1) {
      this.containerColor = var1;
      this.startContainerColor = var1;
      this.endContainerColor = var1;
   }

   public void setContainerColor(int var1) {
      this.containerColor = var1;
   }

   public void setDrawDebugEnabled(boolean var1) {
      this.drawDebugEnabled = var1;
   }

   public void setDrawingViewId(int var1) {
      this.drawingViewId = var1;
   }

   public void setElevationShadowEnabled(boolean var1) {
      this.elevationShadowEnabled = var1;
   }

   public void setEndContainerColor(int var1) {
      this.endContainerColor = var1;
   }

   public void setEndElevation(float var1) {
      this.endElevation = var1;
   }

   public void setEndShapeAppearanceModel(ShapeAppearanceModel var1) {
      this.endShapeAppearanceModel = var1;
   }

   public void setEndView(View var1) {
      this.endView = var1;
   }

   public void setEndViewId(int var1) {
      this.endViewId = var1;
   }

   public void setFadeMode(int var1) {
      this.fadeMode = var1;
   }

   public void setFadeProgressThresholds(MaterialContainerTransform.ProgressThresholds var1) {
      this.fadeProgressThresholds = var1;
   }

   public void setFitMode(int var1) {
      this.fitMode = var1;
   }

   public void setHoldAtEndEnabled(boolean var1) {
      this.holdAtEndEnabled = var1;
   }

   public void setScaleMaskProgressThresholds(MaterialContainerTransform.ProgressThresholds var1) {
      this.scaleMaskProgressThresholds = var1;
   }

   public void setScaleProgressThresholds(MaterialContainerTransform.ProgressThresholds var1) {
      this.scaleProgressThresholds = var1;
   }

   public void setScrimColor(int var1) {
      this.scrimColor = var1;
   }

   public void setShapeMaskProgressThresholds(MaterialContainerTransform.ProgressThresholds var1) {
      this.shapeMaskProgressThresholds = var1;
   }

   public void setStartContainerColor(int var1) {
      this.startContainerColor = var1;
   }

   public void setStartElevation(float var1) {
      this.startElevation = var1;
   }

   public void setStartShapeAppearanceModel(ShapeAppearanceModel var1) {
      this.startShapeAppearanceModel = var1;
   }

   public void setStartView(View var1) {
      this.startView = var1;
   }

   public void setStartViewId(int var1) {
      this.startViewId = var1;
   }

   public void setTransitionDirection(int var1) {
      this.transitionDirection = var1;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface FadeMode {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface FitMode {
   }

   public static class ProgressThresholds {
      private final float end;
      private final float start;

      public ProgressThresholds(float var1, float var2) {
         this.start = var1;
         this.end = var2;
      }

      public float getEnd() {
         return this.end;
      }

      public float getStart() {
         return this.start;
      }
   }

   private static class ProgressThresholdsGroup {
      private final MaterialContainerTransform.ProgressThresholds fade;
      private final MaterialContainerTransform.ProgressThresholds scale;
      private final MaterialContainerTransform.ProgressThresholds scaleMask;
      private final MaterialContainerTransform.ProgressThresholds shapeMask;

      private ProgressThresholdsGroup(MaterialContainerTransform.ProgressThresholds var1, MaterialContainerTransform.ProgressThresholds var2, MaterialContainerTransform.ProgressThresholds var3, MaterialContainerTransform.ProgressThresholds var4) {
         this.fade = var1;
         this.scale = var2;
         this.scaleMask = var3;
         this.shapeMask = var4;
      }

      // $FF: synthetic method
      ProgressThresholdsGroup(MaterialContainerTransform.ProgressThresholds var1, MaterialContainerTransform.ProgressThresholds var2, MaterialContainerTransform.ProgressThresholds var3, MaterialContainerTransform.ProgressThresholds var4, Object var5) {
         this(var1, var2, var3, var4);
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface TransitionDirection {
   }

   private static final class TransitionDrawable extends Drawable {
      private static final int COMPAT_SHADOW_COLOR = -7829368;
      private static final int SHADOW_COLOR = 754974720;
      private static final float SHADOW_DX_MULTIPLIER_ADJUSTMENT = 0.3F;
      private static final float SHADOW_DY_MULTIPLIER_ADJUSTMENT = 1.5F;
      private final MaterialShapeDrawable compatShadowDrawable;
      private final Paint containerPaint;
      private float currentElevation;
      private float currentElevationDy;
      private final RectF currentEndBounds;
      private final RectF currentEndBoundsMasked;
      private RectF currentMaskBounds;
      private final RectF currentStartBounds;
      private final RectF currentStartBoundsMasked;
      private final Paint debugPaint;
      private final Path debugPath;
      private final float displayHeight;
      private final float displayWidth;
      private final boolean drawDebugEnabled;
      private final boolean elevationShadowEnabled;
      private final RectF endBounds;
      private final Paint endContainerPaint;
      private final float endElevation;
      private final ShapeAppearanceModel endShapeAppearanceModel;
      private final View endView;
      private final boolean entering;
      private final FadeModeEvaluator fadeModeEvaluator;
      private FadeModeResult fadeModeResult;
      private final FitModeEvaluator fitModeEvaluator;
      private FitModeResult fitModeResult;
      private final MaskEvaluator maskEvaluator;
      private final float motionPathLength;
      private final PathMeasure motionPathMeasure;
      private final float[] motionPathPosition;
      private float progress;
      private final MaterialContainerTransform.ProgressThresholdsGroup progressThresholds;
      private final Paint scrimPaint;
      private final Paint shadowPaint;
      private final RectF startBounds;
      private final Paint startContainerPaint;
      private final float startElevation;
      private final ShapeAppearanceModel startShapeAppearanceModel;
      private final View startView;

      private TransitionDrawable(PathMotion var1, View var2, RectF var3, ShapeAppearanceModel var4, float var5, View var6, RectF var7, ShapeAppearanceModel var8, float var9, int var10, int var11, int var12, int var13, boolean var14, boolean var15, FadeModeEvaluator var16, FitModeEvaluator var17, MaterialContainerTransform.ProgressThresholdsGroup var18, boolean var19) {
         this.containerPaint = new Paint();
         this.startContainerPaint = new Paint();
         this.endContainerPaint = new Paint();
         this.shadowPaint = new Paint();
         this.scrimPaint = new Paint();
         this.maskEvaluator = new MaskEvaluator();
         this.motionPathPosition = new float[2];
         this.compatShadowDrawable = new MaterialShapeDrawable();
         this.debugPaint = new Paint();
         this.debugPath = new Path();
         this.startView = var2;
         this.startBounds = var3;
         this.startShapeAppearanceModel = var4;
         this.startElevation = var5;
         this.endView = var6;
         this.endBounds = var7;
         this.endShapeAppearanceModel = var8;
         this.endElevation = var9;
         this.entering = var14;
         this.elevationShadowEnabled = var15;
         this.fadeModeEvaluator = var16;
         this.fitModeEvaluator = var17;
         this.progressThresholds = var18;
         this.drawDebugEnabled = var19;
         WindowManager var21 = (WindowManager)var2.getContext().getSystemService("window");
         DisplayMetrics var23 = new DisplayMetrics();
         var21.getDefaultDisplay().getMetrics(var23);
         this.displayWidth = (float)var23.widthPixels;
         this.displayHeight = (float)var23.heightPixels;
         this.containerPaint.setColor(var10);
         this.startContainerPaint.setColor(var11);
         this.endContainerPaint.setColor(var12);
         this.compatShadowDrawable.setFillColor(ColorStateList.valueOf(0));
         this.compatShadowDrawable.setShadowCompatibilityMode(2);
         this.compatShadowDrawable.setShadowBitmapDrawingEnable(false);
         this.compatShadowDrawable.setShadowColor(-7829368);
         this.currentStartBounds = new RectF(var3);
         this.currentStartBoundsMasked = new RectF(this.currentStartBounds);
         this.currentEndBounds = new RectF(this.currentStartBounds);
         this.currentEndBoundsMasked = new RectF(this.currentEndBounds);
         PointF var22 = getMotionPathPoint(var3);
         PointF var24 = getMotionPathPoint(var7);
         PathMeasure var20 = new PathMeasure(var1.getPath(var22.x, var22.y, var24.x, var24.y), false);
         this.motionPathMeasure = var20;
         this.motionPathLength = var20.getLength();
         this.motionPathPosition[0] = var3.centerX();
         this.motionPathPosition[1] = var3.top;
         this.scrimPaint.setStyle(Style.FILL);
         this.scrimPaint.setShader(TransitionUtils.createColorShader(var13));
         this.debugPaint.setStyle(Style.STROKE);
         this.debugPaint.setStrokeWidth(10.0F);
         this.updateProgress(0.0F);
      }

      // $FF: synthetic method
      TransitionDrawable(PathMotion var1, View var2, RectF var3, ShapeAppearanceModel var4, float var5, View var6, RectF var7, ShapeAppearanceModel var8, float var9, int var10, int var11, int var12, int var13, boolean var14, boolean var15, FadeModeEvaluator var16, FitModeEvaluator var17, MaterialContainerTransform.ProgressThresholdsGroup var18, boolean var19, Object var20) {
         this(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19);
      }

      private static float calculateElevationDxMultiplier(RectF var0, float var1) {
         return (var0.centerX() / (var1 / 2.0F) - 1.0F) * 0.3F;
      }

      private static float calculateElevationDyMultiplier(RectF var0, float var1) {
         return var0.centerY() / var1 * 1.5F;
      }

      private void drawDebugCumulativePath(Canvas var1, RectF var2, Path var3, int var4) {
         PointF var5 = getMotionPathPoint(var2);
         if (this.progress == 0.0F) {
            var3.reset();
            var3.moveTo(var5.x, var5.y);
         } else {
            var3.lineTo(var5.x, var5.y);
            this.debugPaint.setColor(var4);
            var1.drawPath(var3, this.debugPaint);
         }

      }

      private void drawDebugRect(Canvas var1, RectF var2, int var3) {
         this.debugPaint.setColor(var3);
         var1.drawRect(var2, this.debugPaint);
      }

      private void drawElevationShadow(Canvas var1) {
         var1.save();
         var1.clipPath(this.maskEvaluator.getPath(), Op.DIFFERENCE);
         if (VERSION.SDK_INT > 28) {
            this.drawElevationShadowWithPaintShadowLayer(var1);
         } else {
            this.drawElevationShadowWithMaterialShapeDrawable(var1);
         }

         var1.restore();
      }

      private void drawElevationShadowWithMaterialShapeDrawable(Canvas var1) {
         this.compatShadowDrawable.setBounds((int)this.currentMaskBounds.left, (int)this.currentMaskBounds.top, (int)this.currentMaskBounds.right, (int)this.currentMaskBounds.bottom);
         this.compatShadowDrawable.setElevation(this.currentElevation);
         this.compatShadowDrawable.setShadowVerticalOffset((int)this.currentElevationDy);
         this.compatShadowDrawable.setShapeAppearanceModel(this.maskEvaluator.getCurrentShapeAppearanceModel());
         this.compatShadowDrawable.draw(var1);
      }

      private void drawElevationShadowWithPaintShadowLayer(Canvas var1) {
         ShapeAppearanceModel var2 = this.maskEvaluator.getCurrentShapeAppearanceModel();
         if (var2.isRoundRect(this.currentMaskBounds)) {
            float var3 = var2.getTopLeftCornerSize().getCornerSize(this.currentMaskBounds);
            var1.drawRoundRect(this.currentMaskBounds, var3, var3, this.shadowPaint);
         } else {
            var1.drawPath(this.maskEvaluator.getPath(), this.shadowPaint);
         }

      }

      private void drawEndView(Canvas var1) {
         this.maybeDrawContainerColor(var1, this.endContainerPaint);
         TransitionUtils.transform(var1, this.getBounds(), this.currentEndBounds.left, this.currentEndBounds.top, this.fitModeResult.endScale, this.fadeModeResult.endAlpha, new TransitionUtils.CanvasOperation() {
            public void run(Canvas var1) {
               TransitionDrawable.this.endView.draw(var1);
            }
         });
      }

      private void drawStartView(Canvas var1) {
         this.maybeDrawContainerColor(var1, this.startContainerPaint);
         TransitionUtils.transform(var1, this.getBounds(), this.currentStartBounds.left, this.currentStartBounds.top, this.fitModeResult.startScale, this.fadeModeResult.startAlpha, new TransitionUtils.CanvasOperation() {
            public void run(Canvas var1) {
               TransitionDrawable.this.startView.draw(var1);
            }
         });
      }

      private static PointF getMotionPathPoint(RectF var0) {
         return new PointF(var0.centerX(), var0.top);
      }

      private void maybeDrawContainerColor(Canvas var1, Paint var2) {
         if (var2.getColor() != 0 && var2.getAlpha() > 0) {
            var1.drawRect(this.getBounds(), var2);
         }

      }

      private void setProgress(float var1) {
         if (this.progress != var1) {
            this.updateProgress(var1);
         }

      }

      private void updateProgress(float var1) {
         this.progress = var1;
         Paint var2 = this.scrimPaint;
         float var3;
         if (this.entering) {
            var3 = TransitionUtils.lerp(0.0F, 255.0F, var1);
         } else {
            var3 = TransitionUtils.lerp(255.0F, 0.0F, var1);
         }

         var2.setAlpha((int)var3);
         this.motionPathMeasure.getPosTan(this.motionPathLength * var1, this.motionPathPosition, (float[])null);
         float[] var8 = this.motionPathPosition;
         var3 = var8[0];
         float var4 = var8[1];
         float var5 = (Float)Preconditions.checkNotNull(this.progressThresholds.scale.start);
         float var6 = (Float)Preconditions.checkNotNull(this.progressThresholds.scale.end);
         FitModeResult var9 = this.fitModeEvaluator.evaluate(var1, var5, var6, this.startBounds.width(), this.startBounds.height(), this.endBounds.width(), this.endBounds.height());
         this.fitModeResult = var9;
         this.currentStartBounds.set(var3 - var9.currentStartWidth / 2.0F, var4, this.fitModeResult.currentStartWidth / 2.0F + var3, this.fitModeResult.currentStartHeight + var4);
         this.currentEndBounds.set(var3 - this.fitModeResult.currentEndWidth / 2.0F, var4, var3 + this.fitModeResult.currentEndWidth / 2.0F, this.fitModeResult.currentEndHeight + var4);
         this.currentStartBoundsMasked.set(this.currentStartBounds);
         this.currentEndBoundsMasked.set(this.currentEndBounds);
         var3 = (Float)Preconditions.checkNotNull(this.progressThresholds.scaleMask.start);
         var6 = (Float)Preconditions.checkNotNull(this.progressThresholds.scaleMask.end);
         boolean var7 = this.fitModeEvaluator.shouldMaskStartBounds(this.fitModeResult);
         RectF var10;
         if (var7) {
            var10 = this.currentStartBoundsMasked;
         } else {
            var10 = this.currentEndBoundsMasked;
         }

         var3 = TransitionUtils.lerp(0.0F, 1.0F, var3, var6, var1);
         if (!var7) {
            var3 = 1.0F - var3;
         }

         this.fitModeEvaluator.applyMask(var10, var3, this.fitModeResult);
         this.currentMaskBounds = new RectF(Math.min(this.currentStartBoundsMasked.left, this.currentEndBoundsMasked.left), Math.min(this.currentStartBoundsMasked.top, this.currentEndBoundsMasked.top), Math.max(this.currentStartBoundsMasked.right, this.currentEndBoundsMasked.right), Math.max(this.currentStartBoundsMasked.bottom, this.currentEndBoundsMasked.bottom));
         this.maskEvaluator.evaluate(var1, this.startShapeAppearanceModel, this.endShapeAppearanceModel, this.currentStartBounds, this.currentStartBoundsMasked, this.currentEndBoundsMasked, this.progressThresholds.shapeMask);
         this.currentElevation = TransitionUtils.lerp(this.startElevation, this.endElevation, var1);
         var5 = calculateElevationDxMultiplier(this.currentMaskBounds, this.displayWidth);
         var6 = calculateElevationDyMultiplier(this.currentMaskBounds, this.displayHeight);
         var3 = this.currentElevation;
         var5 = (float)((int)(var5 * var3));
         var6 = (float)((int)(var6 * var3));
         this.currentElevationDy = var6;
         this.shadowPaint.setShadowLayer(var3, var5, var6, 754974720);
         var3 = (Float)Preconditions.checkNotNull(this.progressThresholds.fade.start);
         var6 = (Float)Preconditions.checkNotNull(this.progressThresholds.fade.end);
         this.fadeModeResult = this.fadeModeEvaluator.evaluate(var1, var3, var6);
         if (this.startContainerPaint.getColor() != 0) {
            this.startContainerPaint.setAlpha(this.fadeModeResult.startAlpha);
         }

         if (this.endContainerPaint.getColor() != 0) {
            this.endContainerPaint.setAlpha(this.fadeModeResult.endAlpha);
         }

         this.invalidateSelf();
      }

      public void draw(Canvas var1) {
         if (this.scrimPaint.getAlpha() > 0) {
            var1.drawRect(this.getBounds(), this.scrimPaint);
         }

         int var2;
         if (this.drawDebugEnabled) {
            var2 = var1.save();
         } else {
            var2 = -1;
         }

         if (this.elevationShadowEnabled && this.currentElevation > 0.0F) {
            this.drawElevationShadow(var1);
         }

         this.maskEvaluator.clip(var1);
         this.maybeDrawContainerColor(var1, this.containerPaint);
         if (this.fadeModeResult.endOnTop) {
            this.drawStartView(var1);
            this.drawEndView(var1);
         } else {
            this.drawEndView(var1);
            this.drawStartView(var1);
         }

         if (this.drawDebugEnabled) {
            var1.restoreToCount(var2);
            this.drawDebugCumulativePath(var1, this.currentStartBounds, this.debugPath, -65281);
            this.drawDebugRect(var1, this.currentStartBoundsMasked, -256);
            this.drawDebugRect(var1, this.currentStartBounds, -16711936);
            this.drawDebugRect(var1, this.currentEndBoundsMasked, -16711681);
            this.drawDebugRect(var1, this.currentEndBounds, -16776961);
         }

      }

      public int getOpacity() {
         return -3;
      }

      public void setAlpha(int var1) {
         throw new UnsupportedOperationException("Setting alpha on is not supported");
      }

      public void setColorFilter(ColorFilter var1) {
         throw new UnsupportedOperationException("Setting a color filter is not supported");
      }
   }
}
