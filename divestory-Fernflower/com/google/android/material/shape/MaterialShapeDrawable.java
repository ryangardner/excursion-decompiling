package com.google.android.material.shape;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.util.ObjectsCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.shadow.ShadowRenderer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.BitSet;

public class MaterialShapeDrawable extends Drawable implements TintAwareDrawable, Shapeable {
   public static final int SHADOW_COMPAT_MODE_ALWAYS = 2;
   public static final int SHADOW_COMPAT_MODE_DEFAULT = 0;
   public static final int SHADOW_COMPAT_MODE_NEVER = 1;
   private static final float SHADOW_OFFSET_MULTIPLIER = 0.25F;
   private static final float SHADOW_RADIUS_MULTIPLIER = 0.75F;
   private static final String TAG = MaterialShapeDrawable.class.getSimpleName();
   private static final Paint clearPaint = new Paint(1);
   private final BitSet containsIncompatibleShadowOp;
   private final ShapePath.ShadowCompatOperation[] cornerShadowOperation;
   private MaterialShapeDrawable.MaterialShapeDrawableState drawableState;
   private final ShapePath.ShadowCompatOperation[] edgeShadowOperation;
   private final Paint fillPaint;
   private final RectF insetRectF;
   private final Matrix matrix;
   private final Path path;
   private final RectF pathBounds;
   private boolean pathDirty;
   private final Path pathInsetByStroke;
   private final ShapeAppearancePathProvider pathProvider;
   private final ShapeAppearancePathProvider.PathListener pathShadowListener;
   private final RectF rectF;
   private final Region scratchRegion;
   private boolean shadowBitmapDrawingEnable;
   private final ShadowRenderer shadowRenderer;
   private final Paint strokePaint;
   private ShapeAppearanceModel strokeShapeAppearance;
   private PorterDuffColorFilter strokeTintFilter;
   private PorterDuffColorFilter tintFilter;
   private final Region transparentRegion;

   public MaterialShapeDrawable() {
      this(new ShapeAppearanceModel());
   }

   public MaterialShapeDrawable(Context var1, AttributeSet var2, int var3, int var4) {
      this(ShapeAppearanceModel.builder(var1, var2, var3, var4).build());
   }

   private MaterialShapeDrawable(MaterialShapeDrawable.MaterialShapeDrawableState var1) {
      this.cornerShadowOperation = new ShapePath.ShadowCompatOperation[4];
      this.edgeShadowOperation = new ShapePath.ShadowCompatOperation[4];
      this.containsIncompatibleShadowOp = new BitSet(8);
      this.matrix = new Matrix();
      this.path = new Path();
      this.pathInsetByStroke = new Path();
      this.rectF = new RectF();
      this.insetRectF = new RectF();
      this.transparentRegion = new Region();
      this.scratchRegion = new Region();
      this.fillPaint = new Paint(1);
      this.strokePaint = new Paint(1);
      this.shadowRenderer = new ShadowRenderer();
      this.pathProvider = new ShapeAppearancePathProvider();
      this.pathBounds = new RectF();
      this.shadowBitmapDrawingEnable = true;
      this.drawableState = var1;
      this.strokePaint.setStyle(Style.STROKE);
      this.fillPaint.setStyle(Style.FILL);
      clearPaint.setColor(-1);
      clearPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
      this.updateTintFilter();
      this.updateColorsForState(this.getState());
      this.pathShadowListener = new ShapeAppearancePathProvider.PathListener() {
         public void onCornerPathCreated(ShapePath var1, Matrix var2, int var3) {
            MaterialShapeDrawable.this.containsIncompatibleShadowOp.set(var3, var1.containsIncompatibleShadowOp());
            MaterialShapeDrawable.this.cornerShadowOperation[var3] = var1.createShadowCompatOperation(var2);
         }

         public void onEdgePathCreated(ShapePath var1, Matrix var2, int var3) {
            MaterialShapeDrawable.this.containsIncompatibleShadowOp.set(var3 + 4, var1.containsIncompatibleShadowOp());
            MaterialShapeDrawable.this.edgeShadowOperation[var3] = var1.createShadowCompatOperation(var2);
         }
      };
   }

   // $FF: synthetic method
   MaterialShapeDrawable(MaterialShapeDrawable.MaterialShapeDrawableState var1, Object var2) {
      this(var1);
   }

   public MaterialShapeDrawable(ShapeAppearanceModel var1) {
      this(new MaterialShapeDrawable.MaterialShapeDrawableState(var1, (ElevationOverlayProvider)null));
   }

   @Deprecated
   public MaterialShapeDrawable(ShapePathModel var1) {
      this((ShapeAppearanceModel)var1);
   }

   private PorterDuffColorFilter calculatePaintColorTintFilter(Paint var1, boolean var2) {
      if (var2) {
         int var3 = var1.getColor();
         int var4 = this.compositeElevationOverlayIfNeeded(var3);
         if (var4 != var3) {
            return new PorterDuffColorFilter(var4, Mode.SRC_IN);
         }
      }

      return null;
   }

   private void calculatePath(RectF var1, Path var2) {
      this.calculatePathForSize(var1, var2);
      if (this.drawableState.scale != 1.0F) {
         this.matrix.reset();
         this.matrix.setScale(this.drawableState.scale, this.drawableState.scale, var1.width() / 2.0F, var1.height() / 2.0F);
         var2.transform(this.matrix);
      }

      var2.computeBounds(this.pathBounds, true);
   }

   private void calculateStrokePath() {
      final float var1 = -this.getStrokeInsetLength();
      ShapeAppearanceModel var2 = this.getShapeAppearanceModel().withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator() {
         public CornerSize apply(CornerSize var1x) {
            if (!(var1x instanceof RelativeCornerSize)) {
               var1x = new AdjustedCornerSize(var1, (CornerSize)var1x);
            }

            return (CornerSize)var1x;
         }
      });
      this.strokeShapeAppearance = var2;
      this.pathProvider.calculatePath(var2, this.drawableState.interpolation, this.getBoundsInsetByStroke(), this.pathInsetByStroke);
   }

   private PorterDuffColorFilter calculateTintColorTintFilter(ColorStateList var1, Mode var2, boolean var3) {
      int var4 = var1.getColorForState(this.getState(), 0);
      int var5 = var4;
      if (var3) {
         var5 = this.compositeElevationOverlayIfNeeded(var4);
      }

      return new PorterDuffColorFilter(var5, var2);
   }

   private PorterDuffColorFilter calculateTintFilter(ColorStateList var1, Mode var2, Paint var3, boolean var4) {
      PorterDuffColorFilter var5;
      if (var1 != null && var2 != null) {
         var5 = this.calculateTintColorTintFilter(var1, var2, var4);
      } else {
         var5 = this.calculatePaintColorTintFilter(var3, var4);
      }

      return var5;
   }

   private int compositeElevationOverlayIfNeeded(int var1) {
      float var2 = this.getZ();
      float var3 = this.getParentAbsoluteElevation();
      int var4 = var1;
      if (this.drawableState.elevationOverlayProvider != null) {
         var4 = this.drawableState.elevationOverlayProvider.compositeOverlayIfNeeded(var1, var2 + var3);
      }

      return var4;
   }

   public static MaterialShapeDrawable createWithElevationOverlay(Context var0) {
      return createWithElevationOverlay(var0, 0.0F);
   }

   public static MaterialShapeDrawable createWithElevationOverlay(Context var0, float var1) {
      int var2 = MaterialColors.getColor(var0, R.attr.colorSurface, MaterialShapeDrawable.class.getSimpleName());
      MaterialShapeDrawable var3 = new MaterialShapeDrawable();
      var3.initializeElevationOverlay(var0);
      var3.setFillColor(ColorStateList.valueOf(var2));
      var3.setElevation(var1);
      return var3;
   }

   private void drawCompatShadow(Canvas var1) {
      if (this.containsIncompatibleShadowOp.cardinality() > 0) {
         Log.w(TAG, "Compatibility shadow requested but can't be drawn for all operations in this shape.");
      }

      if (this.drawableState.shadowCompatOffset != 0) {
         var1.drawPath(this.path, this.shadowRenderer.getShadowPaint());
      }

      int var2;
      for(var2 = 0; var2 < 4; ++var2) {
         this.cornerShadowOperation[var2].draw(this.shadowRenderer, this.drawableState.shadowCompatRadius, var1);
         this.edgeShadowOperation[var2].draw(this.shadowRenderer, this.drawableState.shadowCompatRadius, var1);
      }

      if (this.shadowBitmapDrawingEnable) {
         int var3 = this.getShadowOffsetX();
         var2 = this.getShadowOffsetY();
         var1.translate((float)(-var3), (float)(-var2));
         var1.drawPath(this.path, clearPaint);
         var1.translate((float)var3, (float)var2);
      }

   }

   private void drawFillShape(Canvas var1) {
      this.drawShape(var1, this.fillPaint, this.path, this.drawableState.shapeAppearanceModel, this.getBoundsAsRectF());
   }

   private void drawShape(Canvas var1, Paint var2, Path var3, ShapeAppearanceModel var4, RectF var5) {
      if (var4.isRoundRect(var5)) {
         float var6 = var4.getTopRightCornerSize().getCornerSize(var5) * this.drawableState.interpolation;
         var1.drawRoundRect(var5, var6, var6, var2);
      } else {
         var1.drawPath(var3, var2);
      }

   }

   private void drawStrokeShape(Canvas var1) {
      this.drawShape(var1, this.strokePaint, this.pathInsetByStroke, this.strokeShapeAppearance, this.getBoundsInsetByStroke());
   }

   private RectF getBoundsInsetByStroke() {
      this.insetRectF.set(this.getBoundsAsRectF());
      float var1 = this.getStrokeInsetLength();
      this.insetRectF.inset(var1, var1);
      return this.insetRectF;
   }

   private float getStrokeInsetLength() {
      return this.hasStroke() ? this.strokePaint.getStrokeWidth() / 2.0F : 0.0F;
   }

   private boolean hasCompatShadow() {
      int var1 = this.drawableState.shadowCompatMode;
      boolean var2 = true;
      boolean var3;
      if (var1 != 1 && this.drawableState.shadowCompatRadius > 0) {
         var3 = var2;
         if (this.drawableState.shadowCompatMode == 2) {
            return var3;
         }

         if (this.requiresCompatShadow()) {
            var3 = var2;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   private boolean hasFill() {
      boolean var1;
      if (this.drawableState.paintStyle != Style.FILL_AND_STROKE && this.drawableState.paintStyle != Style.FILL) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private boolean hasStroke() {
      boolean var1;
      if ((this.drawableState.paintStyle == Style.FILL_AND_STROKE || this.drawableState.paintStyle == Style.STROKE) && this.strokePaint.getStrokeWidth() > 0.0F) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void invalidateSelfIgnoreShape() {
      super.invalidateSelf();
   }

   private void maybeDrawCompatShadow(Canvas var1) {
      if (this.hasCompatShadow()) {
         var1.save();
         this.prepareCanvasForShadow(var1);
         if (!this.shadowBitmapDrawingEnable) {
            this.drawCompatShadow(var1);
            var1.restore();
         } else {
            int var2 = (int)(this.pathBounds.width() - (float)this.getBounds().width());
            int var3 = (int)(this.pathBounds.height() - (float)this.getBounds().height());
            if (var2 >= 0 && var3 >= 0) {
               Bitmap var4 = Bitmap.createBitmap((int)this.pathBounds.width() + this.drawableState.shadowCompatRadius * 2 + var2, (int)this.pathBounds.height() + this.drawableState.shadowCompatRadius * 2 + var3, Config.ARGB_8888);
               Canvas var5 = new Canvas(var4);
               float var6 = (float)(this.getBounds().left - this.drawableState.shadowCompatRadius - var2);
               float var7 = (float)(this.getBounds().top - this.drawableState.shadowCompatRadius - var3);
               var5.translate(-var6, -var7);
               this.drawCompatShadow(var5);
               var1.drawBitmap(var4, var6, var7, (Paint)null);
               var4.recycle();
               var1.restore();
            } else {
               throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
            }
         }
      }
   }

   private static int modulateAlpha(int var0, int var1) {
      return var0 * (var1 + (var1 >>> 7)) >>> 8;
   }

   private void prepareCanvasForShadow(Canvas var1) {
      int var2 = this.getShadowOffsetX();
      int var3 = this.getShadowOffsetY();
      if (VERSION.SDK_INT < 21 && this.shadowBitmapDrawingEnable) {
         Rect var4 = var1.getClipBounds();
         var4.inset(-this.drawableState.shadowCompatRadius, -this.drawableState.shadowCompatRadius);
         var4.offset(var2, var3);
         var1.clipRect(var4, Op.REPLACE);
      }

      var1.translate((float)var2, (float)var3);
   }

   private boolean updateColorsForState(int[] var1) {
      boolean var3;
      int var4;
      int var5;
      boolean var6;
      label18: {
         ColorStateList var2 = this.drawableState.fillColor;
         var3 = true;
         if (var2 != null) {
            var4 = this.fillPaint.getColor();
            var5 = this.drawableState.fillColor.getColorForState(var1, var4);
            if (var4 != var5) {
               this.fillPaint.setColor(var5);
               var6 = true;
               break label18;
            }
         }

         var6 = false;
      }

      if (this.drawableState.strokeColor != null) {
         var4 = this.strokePaint.getColor();
         var5 = this.drawableState.strokeColor.getColorForState(var1, var4);
         if (var4 != var5) {
            this.strokePaint.setColor(var5);
            var6 = var3;
         }
      }

      return var6;
   }

   private boolean updateTintFilter() {
      PorterDuffColorFilter var1 = this.tintFilter;
      PorterDuffColorFilter var2 = this.strokeTintFilter;
      ColorStateList var3 = this.drawableState.tintList;
      Mode var4 = this.drawableState.tintMode;
      Paint var5 = this.fillPaint;
      boolean var6 = true;
      this.tintFilter = this.calculateTintFilter(var3, var4, var5, true);
      this.strokeTintFilter = this.calculateTintFilter(this.drawableState.strokeTintList, this.drawableState.tintMode, this.strokePaint, false);
      if (this.drawableState.useTintColorForShadow) {
         this.shadowRenderer.setShadowColor(this.drawableState.tintList.getColorForState(this.getState(), 0));
      }

      boolean var7 = var6;
      if (ObjectsCompat.equals(var1, this.tintFilter)) {
         if (!ObjectsCompat.equals(var2, this.strokeTintFilter)) {
            var7 = var6;
         } else {
            var7 = false;
         }
      }

      return var7;
   }

   private void updateZ() {
      float var1 = this.getZ();
      this.drawableState.shadowCompatRadius = (int)Math.ceil((double)(0.75F * var1));
      this.drawableState.shadowCompatOffset = (int)Math.ceil((double)(var1 * 0.25F));
      this.updateTintFilter();
      this.invalidateSelfIgnoreShape();
   }

   protected final void calculatePathForSize(RectF var1, Path var2) {
      this.pathProvider.calculatePath(this.drawableState.shapeAppearanceModel, this.drawableState.interpolation, var1, this.pathShadowListener, var2);
   }

   public void draw(Canvas var1) {
      this.fillPaint.setColorFilter(this.tintFilter);
      int var2 = this.fillPaint.getAlpha();
      this.fillPaint.setAlpha(modulateAlpha(var2, this.drawableState.alpha));
      this.strokePaint.setColorFilter(this.strokeTintFilter);
      this.strokePaint.setStrokeWidth(this.drawableState.strokeWidth);
      int var3 = this.strokePaint.getAlpha();
      this.strokePaint.setAlpha(modulateAlpha(var3, this.drawableState.alpha));
      if (this.pathDirty) {
         this.calculateStrokePath();
         this.calculatePath(this.getBoundsAsRectF(), this.path);
         this.pathDirty = false;
      }

      this.maybeDrawCompatShadow(var1);
      if (this.hasFill()) {
         this.drawFillShape(var1);
      }

      if (this.hasStroke()) {
         this.drawStrokeShape(var1);
      }

      this.fillPaint.setAlpha(var2);
      this.strokePaint.setAlpha(var3);
   }

   protected void drawShape(Canvas var1, Paint var2, Path var3, RectF var4) {
      this.drawShape(var1, var2, var3, this.drawableState.shapeAppearanceModel, var4);
   }

   public float getBottomLeftCornerResolvedSize() {
      return this.drawableState.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(this.getBoundsAsRectF());
   }

   public float getBottomRightCornerResolvedSize() {
      return this.drawableState.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(this.getBoundsAsRectF());
   }

   protected RectF getBoundsAsRectF() {
      this.rectF.set(this.getBounds());
      return this.rectF;
   }

   public ConstantState getConstantState() {
      return this.drawableState;
   }

   public float getElevation() {
      return this.drawableState.elevation;
   }

   public ColorStateList getFillColor() {
      return this.drawableState.fillColor;
   }

   public float getInterpolation() {
      return this.drawableState.interpolation;
   }

   public int getOpacity() {
      return -3;
   }

   public void getOutline(Outline var1) {
      if (this.drawableState.shadowCompatMode != 2) {
         if (this.isRoundRect()) {
            float var2 = this.getTopLeftCornerResolvedSize();
            float var3 = this.drawableState.interpolation;
            var1.setRoundRect(this.getBounds(), var2 * var3);
         } else {
            this.calculatePath(this.getBoundsAsRectF(), this.path);
            if (this.path.isConvex() || VERSION.SDK_INT >= 29) {
               try {
                  var1.setConvexPath(this.path);
               } catch (IllegalArgumentException var4) {
               }
            }

         }
      }
   }

   public boolean getPadding(Rect var1) {
      if (this.drawableState.padding != null) {
         var1.set(this.drawableState.padding);
         return true;
      } else {
         return super.getPadding(var1);
      }
   }

   public Style getPaintStyle() {
      return this.drawableState.paintStyle;
   }

   public float getParentAbsoluteElevation() {
      return this.drawableState.parentAbsoluteElevation;
   }

   @Deprecated
   public void getPathForSize(int var1, int var2, Path var3) {
      this.calculatePathForSize(new RectF(0.0F, 0.0F, (float)var1, (float)var2), var3);
   }

   public float getScale() {
      return this.drawableState.scale;
   }

   public int getShadowCompatRotation() {
      return this.drawableState.shadowCompatRotation;
   }

   public int getShadowCompatibilityMode() {
      return this.drawableState.shadowCompatMode;
   }

   @Deprecated
   public int getShadowElevation() {
      return (int)this.getElevation();
   }

   public int getShadowOffsetX() {
      return (int)((double)this.drawableState.shadowCompatOffset * Math.sin(Math.toRadians((double)this.drawableState.shadowCompatRotation)));
   }

   public int getShadowOffsetY() {
      return (int)((double)this.drawableState.shadowCompatOffset * Math.cos(Math.toRadians((double)this.drawableState.shadowCompatRotation)));
   }

   public int getShadowRadius() {
      return this.drawableState.shadowCompatRadius;
   }

   public int getShadowVerticalOffset() {
      return this.drawableState.shadowCompatOffset;
   }

   public ShapeAppearanceModel getShapeAppearanceModel() {
      return this.drawableState.shapeAppearanceModel;
   }

   @Deprecated
   public ShapePathModel getShapedViewModel() {
      ShapeAppearanceModel var1 = this.getShapeAppearanceModel();
      ShapePathModel var2;
      if (var1 instanceof ShapePathModel) {
         var2 = (ShapePathModel)var1;
      } else {
         var2 = null;
      }

      return var2;
   }

   public ColorStateList getStrokeColor() {
      return this.drawableState.strokeColor;
   }

   public ColorStateList getStrokeTintList() {
      return this.drawableState.strokeTintList;
   }

   public float getStrokeWidth() {
      return this.drawableState.strokeWidth;
   }

   public ColorStateList getTintList() {
      return this.drawableState.tintList;
   }

   public float getTopLeftCornerResolvedSize() {
      return this.drawableState.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.getBoundsAsRectF());
   }

   public float getTopRightCornerResolvedSize() {
      return this.drawableState.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(this.getBoundsAsRectF());
   }

   public float getTranslationZ() {
      return this.drawableState.translationZ;
   }

   public Region getTransparentRegion() {
      Rect var1 = this.getBounds();
      this.transparentRegion.set(var1);
      this.calculatePath(this.getBoundsAsRectF(), this.path);
      this.scratchRegion.setPath(this.path, this.transparentRegion);
      this.transparentRegion.op(this.scratchRegion, Op.DIFFERENCE);
      return this.transparentRegion;
   }

   public float getZ() {
      return this.getElevation() + this.getTranslationZ();
   }

   public void initializeElevationOverlay(Context var1) {
      this.drawableState.elevationOverlayProvider = new ElevationOverlayProvider(var1);
      this.updateZ();
   }

   public void invalidateSelf() {
      this.pathDirty = true;
      super.invalidateSelf();
   }

   public boolean isElevationOverlayEnabled() {
      boolean var1;
      if (this.drawableState.elevationOverlayProvider != null && this.drawableState.elevationOverlayProvider.isThemeElevationOverlayEnabled()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isElevationOverlayInitialized() {
      boolean var1;
      if (this.drawableState.elevationOverlayProvider != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isPointInTransparentRegion(int var1, int var2) {
      return this.getTransparentRegion().contains(var1, var2);
   }

   public boolean isRoundRect() {
      return this.drawableState.shapeAppearanceModel.isRoundRect(this.getBoundsAsRectF());
   }

   @Deprecated
   public boolean isShadowEnabled() {
      boolean var1;
      if (this.drawableState.shadowCompatMode != 0 && this.drawableState.shadowCompatMode != 2) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isStateful() {
      boolean var1;
      if (super.isStateful() || this.drawableState.tintList != null && this.drawableState.tintList.isStateful() || this.drawableState.strokeTintList != null && this.drawableState.strokeTintList.isStateful() || this.drawableState.strokeColor != null && this.drawableState.strokeColor.isStateful() || this.drawableState.fillColor != null && this.drawableState.fillColor.isStateful()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Drawable mutate() {
      this.drawableState = new MaterialShapeDrawable.MaterialShapeDrawableState(this.drawableState);
      return this;
   }

   protected void onBoundsChange(Rect var1) {
      this.pathDirty = true;
      super.onBoundsChange(var1);
   }

   protected boolean onStateChange(int[] var1) {
      boolean var2 = this.updateColorsForState(var1);
      boolean var3 = this.updateTintFilter();
      if (!var2 && !var3) {
         var3 = false;
      } else {
         var3 = true;
      }

      if (var3) {
         this.invalidateSelf();
      }

      return var3;
   }

   public boolean requiresCompatShadow() {
      boolean var1;
      if (VERSION.SDK_INT < 21 || !this.isRoundRect() && !this.path.isConvex() && VERSION.SDK_INT < 29) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setAlpha(int var1) {
      if (this.drawableState.alpha != var1) {
         this.drawableState.alpha = var1;
         this.invalidateSelfIgnoreShape();
      }

   }

   public void setColorFilter(ColorFilter var1) {
      this.drawableState.colorFilter = var1;
      this.invalidateSelfIgnoreShape();
   }

   public void setCornerSize(float var1) {
      this.setShapeAppearanceModel(this.drawableState.shapeAppearanceModel.withCornerSize(var1));
   }

   public void setCornerSize(CornerSize var1) {
      this.setShapeAppearanceModel(this.drawableState.shapeAppearanceModel.withCornerSize(var1));
   }

   public void setEdgeIntersectionCheckEnable(boolean var1) {
      this.pathProvider.setEdgeIntersectionCheckEnable(var1);
   }

   public void setElevation(float var1) {
      if (this.drawableState.elevation != var1) {
         this.drawableState.elevation = var1;
         this.updateZ();
      }

   }

   public void setFillColor(ColorStateList var1) {
      if (this.drawableState.fillColor != var1) {
         this.drawableState.fillColor = var1;
         this.onStateChange(this.getState());
      }

   }

   public void setInterpolation(float var1) {
      if (this.drawableState.interpolation != var1) {
         this.drawableState.interpolation = var1;
         this.pathDirty = true;
         this.invalidateSelf();
      }

   }

   public void setPadding(int var1, int var2, int var3, int var4) {
      if (this.drawableState.padding == null) {
         this.drawableState.padding = new Rect();
      }

      this.drawableState.padding.set(var1, var2, var3, var4);
      this.invalidateSelf();
   }

   public void setPaintStyle(Style var1) {
      this.drawableState.paintStyle = var1;
      this.invalidateSelfIgnoreShape();
   }

   public void setParentAbsoluteElevation(float var1) {
      if (this.drawableState.parentAbsoluteElevation != var1) {
         this.drawableState.parentAbsoluteElevation = var1;
         this.updateZ();
      }

   }

   public void setScale(float var1) {
      if (this.drawableState.scale != var1) {
         this.drawableState.scale = var1;
         this.invalidateSelf();
      }

   }

   public void setShadowBitmapDrawingEnable(boolean var1) {
      this.shadowBitmapDrawingEnable = var1;
   }

   public void setShadowColor(int var1) {
      this.shadowRenderer.setShadowColor(var1);
      this.drawableState.useTintColorForShadow = false;
      this.invalidateSelfIgnoreShape();
   }

   public void setShadowCompatRotation(int var1) {
      if (this.drawableState.shadowCompatRotation != var1) {
         this.drawableState.shadowCompatRotation = var1;
         this.invalidateSelfIgnoreShape();
      }

   }

   public void setShadowCompatibilityMode(int var1) {
      if (this.drawableState.shadowCompatMode != var1) {
         this.drawableState.shadowCompatMode = var1;
         this.invalidateSelfIgnoreShape();
      }

   }

   @Deprecated
   public void setShadowElevation(int var1) {
      this.setElevation((float)var1);
   }

   @Deprecated
   public void setShadowEnabled(boolean var1) {
      this.setShadowCompatibilityMode(var1 ^ 1);
   }

   @Deprecated
   public void setShadowRadius(int var1) {
      this.drawableState.shadowCompatRadius = var1;
   }

   public void setShadowVerticalOffset(int var1) {
      if (this.drawableState.shadowCompatOffset != var1) {
         this.drawableState.shadowCompatOffset = var1;
         this.invalidateSelfIgnoreShape();
      }

   }

   public void setShapeAppearanceModel(ShapeAppearanceModel var1) {
      this.drawableState.shapeAppearanceModel = var1;
      this.invalidateSelf();
   }

   @Deprecated
   public void setShapedViewModel(ShapePathModel var1) {
      this.setShapeAppearanceModel(var1);
   }

   public void setStroke(float var1, int var2) {
      this.setStrokeWidth(var1);
      this.setStrokeColor(ColorStateList.valueOf(var2));
   }

   public void setStroke(float var1, ColorStateList var2) {
      this.setStrokeWidth(var1);
      this.setStrokeColor(var2);
   }

   public void setStrokeColor(ColorStateList var1) {
      if (this.drawableState.strokeColor != var1) {
         this.drawableState.strokeColor = var1;
         this.onStateChange(this.getState());
      }

   }

   public void setStrokeTint(int var1) {
      this.setStrokeTint(ColorStateList.valueOf(var1));
   }

   public void setStrokeTint(ColorStateList var1) {
      this.drawableState.strokeTintList = var1;
      this.updateTintFilter();
      this.invalidateSelfIgnoreShape();
   }

   public void setStrokeWidth(float var1) {
      this.drawableState.strokeWidth = var1;
      this.invalidateSelf();
   }

   public void setTint(int var1) {
      this.setTintList(ColorStateList.valueOf(var1));
   }

   public void setTintList(ColorStateList var1) {
      this.drawableState.tintList = var1;
      this.updateTintFilter();
      this.invalidateSelfIgnoreShape();
   }

   public void setTintMode(Mode var1) {
      if (this.drawableState.tintMode != var1) {
         this.drawableState.tintMode = var1;
         this.updateTintFilter();
         this.invalidateSelfIgnoreShape();
      }

   }

   public void setTranslationZ(float var1) {
      if (this.drawableState.translationZ != var1) {
         this.drawableState.translationZ = var1;
         this.updateZ();
      }

   }

   public void setUseTintColorForShadow(boolean var1) {
      if (this.drawableState.useTintColorForShadow != var1) {
         this.drawableState.useTintColorForShadow = var1;
         this.invalidateSelf();
      }

   }

   public void setZ(float var1) {
      this.setTranslationZ(var1 - this.getElevation());
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface CompatibilityShadowMode {
   }

   static final class MaterialShapeDrawableState extends ConstantState {
      public int alpha;
      public ColorFilter colorFilter;
      public float elevation;
      public ElevationOverlayProvider elevationOverlayProvider;
      public ColorStateList fillColor = null;
      public float interpolation;
      public Rect padding;
      public Style paintStyle;
      public float parentAbsoluteElevation;
      public float scale;
      public int shadowCompatMode;
      public int shadowCompatOffset;
      public int shadowCompatRadius;
      public int shadowCompatRotation;
      public ShapeAppearanceModel shapeAppearanceModel;
      public ColorStateList strokeColor = null;
      public ColorStateList strokeTintList = null;
      public float strokeWidth;
      public ColorStateList tintList = null;
      public Mode tintMode;
      public float translationZ;
      public boolean useTintColorForShadow;

      public MaterialShapeDrawableState(MaterialShapeDrawable.MaterialShapeDrawableState var1) {
         this.tintMode = Mode.SRC_IN;
         this.padding = null;
         this.scale = 1.0F;
         this.interpolation = 1.0F;
         this.alpha = 255;
         this.parentAbsoluteElevation = 0.0F;
         this.elevation = 0.0F;
         this.translationZ = 0.0F;
         this.shadowCompatMode = 0;
         this.shadowCompatRadius = 0;
         this.shadowCompatOffset = 0;
         this.shadowCompatRotation = 0;
         this.useTintColorForShadow = false;
         this.paintStyle = Style.FILL_AND_STROKE;
         this.shapeAppearanceModel = var1.shapeAppearanceModel;
         this.elevationOverlayProvider = var1.elevationOverlayProvider;
         this.strokeWidth = var1.strokeWidth;
         this.colorFilter = var1.colorFilter;
         this.fillColor = var1.fillColor;
         this.strokeColor = var1.strokeColor;
         this.tintMode = var1.tintMode;
         this.tintList = var1.tintList;
         this.alpha = var1.alpha;
         this.scale = var1.scale;
         this.shadowCompatOffset = var1.shadowCompatOffset;
         this.shadowCompatMode = var1.shadowCompatMode;
         this.useTintColorForShadow = var1.useTintColorForShadow;
         this.interpolation = var1.interpolation;
         this.parentAbsoluteElevation = var1.parentAbsoluteElevation;
         this.elevation = var1.elevation;
         this.translationZ = var1.translationZ;
         this.shadowCompatRadius = var1.shadowCompatRadius;
         this.shadowCompatRotation = var1.shadowCompatRotation;
         this.strokeTintList = var1.strokeTintList;
         this.paintStyle = var1.paintStyle;
         if (var1.padding != null) {
            this.padding = new Rect(var1.padding);
         }

      }

      public MaterialShapeDrawableState(ShapeAppearanceModel var1, ElevationOverlayProvider var2) {
         this.tintMode = Mode.SRC_IN;
         this.padding = null;
         this.scale = 1.0F;
         this.interpolation = 1.0F;
         this.alpha = 255;
         this.parentAbsoluteElevation = 0.0F;
         this.elevation = 0.0F;
         this.translationZ = 0.0F;
         this.shadowCompatMode = 0;
         this.shadowCompatRadius = 0;
         this.shadowCompatOffset = 0;
         this.shadowCompatRotation = 0;
         this.useTintColorForShadow = false;
         this.paintStyle = Style.FILL_AND_STROKE;
         this.shapeAppearanceModel = var1;
         this.elevationOverlayProvider = var2;
      }

      public int getChangingConfigurations() {
         return 0;
      }

      public Drawable newDrawable() {
         MaterialShapeDrawable var1 = new MaterialShapeDrawable(this);
         var1.pathDirty = true;
         return var1;
      }
   }
}
