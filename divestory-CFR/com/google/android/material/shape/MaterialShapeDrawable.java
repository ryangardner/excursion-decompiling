/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Outline
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Region
 *  android.graphics.Region$Op
 *  android.graphics.Xfermode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 */
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
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import androidx.core.graphics.drawable.TintAwareDrawable;
import androidx.core.util.ObjectsCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.android.material.shape.AdjustedCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.ShapePath;
import com.google.android.material.shape.ShapePathModel;
import com.google.android.material.shape.Shapeable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.BitSet;

public class MaterialShapeDrawable
extends Drawable
implements TintAwareDrawable,
Shapeable {
    public static final int SHADOW_COMPAT_MODE_ALWAYS = 2;
    public static final int SHADOW_COMPAT_MODE_DEFAULT = 0;
    public static final int SHADOW_COMPAT_MODE_NEVER = 1;
    private static final float SHADOW_OFFSET_MULTIPLIER = 0.25f;
    private static final float SHADOW_RADIUS_MULTIPLIER = 0.75f;
    private static final String TAG = MaterialShapeDrawable.class.getSimpleName();
    private static final Paint clearPaint = new Paint(1);
    private final BitSet containsIncompatibleShadowOp = new BitSet(8);
    private final ShapePath.ShadowCompatOperation[] cornerShadowOperation = new ShapePath.ShadowCompatOperation[4];
    private MaterialShapeDrawableState drawableState;
    private final ShapePath.ShadowCompatOperation[] edgeShadowOperation = new ShapePath.ShadowCompatOperation[4];
    private final Paint fillPaint = new Paint(1);
    private final RectF insetRectF = new RectF();
    private final Matrix matrix = new Matrix();
    private final Path path = new Path();
    private final RectF pathBounds = new RectF();
    private boolean pathDirty;
    private final Path pathInsetByStroke = new Path();
    private final ShapeAppearancePathProvider pathProvider = new ShapeAppearancePathProvider();
    private final ShapeAppearancePathProvider.PathListener pathShadowListener;
    private final RectF rectF = new RectF();
    private final Region scratchRegion = new Region();
    private boolean shadowBitmapDrawingEnable = true;
    private final ShadowRenderer shadowRenderer = new ShadowRenderer();
    private final Paint strokePaint = new Paint(1);
    private ShapeAppearanceModel strokeShapeAppearance;
    private PorterDuffColorFilter strokeTintFilter;
    private PorterDuffColorFilter tintFilter;
    private final Region transparentRegion = new Region();

    public MaterialShapeDrawable() {
        this(new ShapeAppearanceModel());
    }

    public MaterialShapeDrawable(Context context, AttributeSet attributeSet, int n, int n2) {
        this(ShapeAppearanceModel.builder(context, attributeSet, n, n2).build());
    }

    private MaterialShapeDrawable(MaterialShapeDrawableState materialShapeDrawableState) {
        this.drawableState = materialShapeDrawableState;
        this.strokePaint.setStyle(Paint.Style.STROKE);
        this.fillPaint.setStyle(Paint.Style.FILL);
        clearPaint.setColor(-1);
        clearPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        this.updateTintFilter();
        this.updateColorsForState(this.getState());
        this.pathShadowListener = new ShapeAppearancePathProvider.PathListener(){

            @Override
            public void onCornerPathCreated(ShapePath shapePath, Matrix matrix, int n) {
                MaterialShapeDrawable.this.containsIncompatibleShadowOp.set(n, shapePath.containsIncompatibleShadowOp());
                MaterialShapeDrawable.access$100((MaterialShapeDrawable)MaterialShapeDrawable.this)[n] = shapePath.createShadowCompatOperation(matrix);
            }

            @Override
            public void onEdgePathCreated(ShapePath shapePath, Matrix matrix, int n) {
                MaterialShapeDrawable.this.containsIncompatibleShadowOp.set(n + 4, shapePath.containsIncompatibleShadowOp());
                MaterialShapeDrawable.access$200((MaterialShapeDrawable)MaterialShapeDrawable.this)[n] = shapePath.createShadowCompatOperation(matrix);
            }
        };
    }

    public MaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel) {
        this(new MaterialShapeDrawableState(shapeAppearanceModel, null));
    }

    @Deprecated
    public MaterialShapeDrawable(ShapePathModel shapePathModel) {
        this((ShapeAppearanceModel)shapePathModel);
    }

    static /* synthetic */ ShapePath.ShadowCompatOperation[] access$100(MaterialShapeDrawable materialShapeDrawable) {
        return materialShapeDrawable.cornerShadowOperation;
    }

    static /* synthetic */ ShapePath.ShadowCompatOperation[] access$200(MaterialShapeDrawable materialShapeDrawable) {
        return materialShapeDrawable.edgeShadowOperation;
    }

    private PorterDuffColorFilter calculatePaintColorTintFilter(Paint paint, boolean bl) {
        if (!bl) return null;
        int n = paint.getColor();
        int n2 = this.compositeElevationOverlayIfNeeded(n);
        if (n2 == n) return null;
        return new PorterDuffColorFilter(n2, PorterDuff.Mode.SRC_IN);
    }

    private void calculatePath(RectF rectF, Path path) {
        this.calculatePathForSize(rectF, path);
        if (this.drawableState.scale != 1.0f) {
            this.matrix.reset();
            this.matrix.setScale(this.drawableState.scale, this.drawableState.scale, rectF.width() / 2.0f, rectF.height() / 2.0f);
            path.transform(this.matrix);
        }
        path.computeBounds(this.pathBounds, true);
    }

    private void calculateStrokePath() {
        ShapeAppearanceModel shapeAppearanceModel;
        final float f = -this.getStrokeInsetLength();
        this.strokeShapeAppearance = shapeAppearanceModel = this.getShapeAppearanceModel().withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator(){

            @Override
            public CornerSize apply(CornerSize cornerSize) {
                if (!(cornerSize instanceof RelativeCornerSize)) return new AdjustedCornerSize(f, cornerSize);
                return cornerSize;
            }
        });
        this.pathProvider.calculatePath(shapeAppearanceModel, this.drawableState.interpolation, this.getBoundsInsetByStroke(), this.pathInsetByStroke);
    }

    private PorterDuffColorFilter calculateTintColorTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode, boolean bl) {
        int n;
        int n2 = n = colorStateList.getColorForState(this.getState(), 0);
        if (!bl) return new PorterDuffColorFilter(n2, mode);
        n2 = this.compositeElevationOverlayIfNeeded(n);
        return new PorterDuffColorFilter(n2, mode);
    }

    private PorterDuffColorFilter calculateTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode, Paint paint, boolean bl) {
        if (colorStateList == null) return this.calculatePaintColorTintFilter(paint, bl);
        if (mode == null) return this.calculatePaintColorTintFilter(paint, bl);
        return this.calculateTintColorTintFilter(colorStateList, mode, bl);
    }

    private int compositeElevationOverlayIfNeeded(int n) {
        float f = this.getZ();
        float f2 = this.getParentAbsoluteElevation();
        int n2 = n;
        if (this.drawableState.elevationOverlayProvider == null) return n2;
        return this.drawableState.elevationOverlayProvider.compositeOverlayIfNeeded(n, f + f2);
    }

    public static MaterialShapeDrawable createWithElevationOverlay(Context context) {
        return MaterialShapeDrawable.createWithElevationOverlay(context, 0.0f);
    }

    public static MaterialShapeDrawable createWithElevationOverlay(Context context, float f) {
        int n = MaterialColors.getColor(context, R.attr.colorSurface, MaterialShapeDrawable.class.getSimpleName());
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        materialShapeDrawable.initializeElevationOverlay(context);
        materialShapeDrawable.setFillColor(ColorStateList.valueOf((int)n));
        materialShapeDrawable.setElevation(f);
        return materialShapeDrawable;
    }

    private void drawCompatShadow(Canvas canvas) {
        if (this.containsIncompatibleShadowOp.cardinality() > 0) {
            Log.w((String)TAG, (String)"Compatibility shadow requested but can't be drawn for all operations in this shape.");
        }
        if (this.drawableState.shadowCompatOffset != 0) {
            canvas.drawPath(this.path, this.shadowRenderer.getShadowPaint());
        }
        int n = 0;
        do {
            if (n >= 4) {
                if (!this.shadowBitmapDrawingEnable) return;
                int n2 = this.getShadowOffsetX();
                n = this.getShadowOffsetY();
                canvas.translate((float)(-n2), (float)(-n));
                canvas.drawPath(this.path, clearPaint);
                canvas.translate((float)n2, (float)n);
                return;
            }
            this.cornerShadowOperation[n].draw(this.shadowRenderer, this.drawableState.shadowCompatRadius, canvas);
            this.edgeShadowOperation[n].draw(this.shadowRenderer, this.drawableState.shadowCompatRadius, canvas);
            ++n;
        } while (true);
    }

    private void drawFillShape(Canvas canvas) {
        this.drawShape(canvas, this.fillPaint, this.path, this.drawableState.shapeAppearanceModel, this.getBoundsAsRectF());
    }

    private void drawShape(Canvas canvas, Paint paint, Path path, ShapeAppearanceModel shapeAppearanceModel, RectF rectF) {
        if (shapeAppearanceModel.isRoundRect(rectF)) {
            float f = shapeAppearanceModel.getTopRightCornerSize().getCornerSize(rectF) * this.drawableState.interpolation;
            canvas.drawRoundRect(rectF, f, f, paint);
            return;
        }
        canvas.drawPath(path, paint);
    }

    private void drawStrokeShape(Canvas canvas) {
        this.drawShape(canvas, this.strokePaint, this.pathInsetByStroke, this.strokeShapeAppearance, this.getBoundsInsetByStroke());
    }

    private RectF getBoundsInsetByStroke() {
        this.insetRectF.set(this.getBoundsAsRectF());
        float f = this.getStrokeInsetLength();
        this.insetRectF.inset(f, f);
        return this.insetRectF;
    }

    private float getStrokeInsetLength() {
        if (!this.hasStroke()) return 0.0f;
        return this.strokePaint.getStrokeWidth() / 2.0f;
    }

    private boolean hasCompatShadow() {
        int n = this.drawableState.shadowCompatMode;
        boolean bl = true;
        if (n == 1) return false;
        if (this.drawableState.shadowCompatRadius <= 0) return false;
        boolean bl2 = bl;
        if (this.drawableState.shadowCompatMode == 2) return bl2;
        if (!this.requiresCompatShadow()) return false;
        return bl;
    }

    private boolean hasFill() {
        if (this.drawableState.paintStyle == Paint.Style.FILL_AND_STROKE) return true;
        if (this.drawableState.paintStyle == Paint.Style.FILL) return true;
        return false;
    }

    private boolean hasStroke() {
        if (this.drawableState.paintStyle != Paint.Style.FILL_AND_STROKE) {
            if (this.drawableState.paintStyle != Paint.Style.STROKE) return false;
        }
        if (!(this.strokePaint.getStrokeWidth() > 0.0f)) return false;
        return true;
    }

    private void invalidateSelfIgnoreShape() {
        super.invalidateSelf();
    }

    private void maybeDrawCompatShadow(Canvas canvas) {
        if (!this.hasCompatShadow()) {
            return;
        }
        canvas.save();
        this.prepareCanvasForShadow(canvas);
        if (!this.shadowBitmapDrawingEnable) {
            this.drawCompatShadow(canvas);
            canvas.restore();
            return;
        }
        int n = (int)(this.pathBounds.width() - (float)this.getBounds().width());
        int n2 = (int)(this.pathBounds.height() - (float)this.getBounds().height());
        if (n < 0) throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
        if (n2 < 0) throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
        Bitmap bitmap = Bitmap.createBitmap((int)((int)this.pathBounds.width() + this.drawableState.shadowCompatRadius * 2 + n), (int)((int)this.pathBounds.height() + this.drawableState.shadowCompatRadius * 2 + n2), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        float f = this.getBounds().left - this.drawableState.shadowCompatRadius - n;
        float f2 = this.getBounds().top - this.drawableState.shadowCompatRadius - n2;
        canvas2.translate(-f, -f2);
        this.drawCompatShadow(canvas2);
        canvas.drawBitmap(bitmap, f, f2, null);
        bitmap.recycle();
        canvas.restore();
    }

    private static int modulateAlpha(int n, int n2) {
        return n * (n2 + (n2 >>> 7)) >>> 8;
    }

    private void prepareCanvasForShadow(Canvas canvas) {
        int n = this.getShadowOffsetX();
        int n2 = this.getShadowOffsetY();
        if (Build.VERSION.SDK_INT < 21 && this.shadowBitmapDrawingEnable) {
            Rect rect = canvas.getClipBounds();
            rect.inset(-this.drawableState.shadowCompatRadius, -this.drawableState.shadowCompatRadius);
            rect.offset(n, n2);
            canvas.clipRect(rect, Region.Op.REPLACE);
        }
        canvas.translate((float)n, (float)n2);
    }

    private boolean updateColorsForState(int[] arrn) {
        int n;
        boolean bl;
        int n2;
        ColorStateList colorStateList = this.drawableState.fillColor;
        boolean bl2 = true;
        if (colorStateList != null && (n = this.fillPaint.getColor()) != (n2 = this.drawableState.fillColor.getColorForState(arrn, n))) {
            this.fillPaint.setColor(n2);
            bl = true;
        } else {
            bl = false;
        }
        if (this.drawableState.strokeColor == null) return bl;
        n = this.strokePaint.getColor();
        if (n == (n2 = this.drawableState.strokeColor.getColorForState(arrn, n))) return bl;
        this.strokePaint.setColor(n2);
        return bl2;
    }

    private boolean updateTintFilter() {
        PorterDuffColorFilter porterDuffColorFilter = this.tintFilter;
        PorterDuffColorFilter porterDuffColorFilter2 = this.strokeTintFilter;
        ColorStateList colorStateList = this.drawableState.tintList;
        PorterDuff.Mode mode = this.drawableState.tintMode;
        Paint paint = this.fillPaint;
        boolean bl = true;
        this.tintFilter = this.calculateTintFilter(colorStateList, mode, paint, true);
        this.strokeTintFilter = this.calculateTintFilter(this.drawableState.strokeTintList, this.drawableState.tintMode, this.strokePaint, false);
        if (this.drawableState.useTintColorForShadow) {
            this.shadowRenderer.setShadowColor(this.drawableState.tintList.getColorForState(this.getState(), 0));
        }
        boolean bl2 = bl;
        if (!ObjectsCompat.equals((Object)porterDuffColorFilter, (Object)this.tintFilter)) return bl2;
        if (ObjectsCompat.equals((Object)porterDuffColorFilter2, (Object)this.strokeTintFilter)) return false;
        return bl;
    }

    private void updateZ() {
        float f = this.getZ();
        this.drawableState.shadowCompatRadius = (int)Math.ceil(0.75f * f);
        this.drawableState.shadowCompatOffset = (int)Math.ceil(f * 0.25f);
        this.updateTintFilter();
        this.invalidateSelfIgnoreShape();
    }

    protected final void calculatePathForSize(RectF rectF, Path path) {
        this.pathProvider.calculatePath(this.drawableState.shapeAppearanceModel, this.drawableState.interpolation, rectF, this.pathShadowListener, path);
    }

    public void draw(Canvas canvas) {
        this.fillPaint.setColorFilter((ColorFilter)this.tintFilter);
        int n = this.fillPaint.getAlpha();
        this.fillPaint.setAlpha(MaterialShapeDrawable.modulateAlpha(n, this.drawableState.alpha));
        this.strokePaint.setColorFilter((ColorFilter)this.strokeTintFilter);
        this.strokePaint.setStrokeWidth(this.drawableState.strokeWidth);
        int n2 = this.strokePaint.getAlpha();
        this.strokePaint.setAlpha(MaterialShapeDrawable.modulateAlpha(n2, this.drawableState.alpha));
        if (this.pathDirty) {
            this.calculateStrokePath();
            this.calculatePath(this.getBoundsAsRectF(), this.path);
            this.pathDirty = false;
        }
        this.maybeDrawCompatShadow(canvas);
        if (this.hasFill()) {
            this.drawFillShape(canvas);
        }
        if (this.hasStroke()) {
            this.drawStrokeShape(canvas);
        }
        this.fillPaint.setAlpha(n);
        this.strokePaint.setAlpha(n2);
    }

    protected void drawShape(Canvas canvas, Paint paint, Path path, RectF rectF) {
        this.drawShape(canvas, paint, path, this.drawableState.shapeAppearanceModel, rectF);
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

    public Drawable.ConstantState getConstantState() {
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

    public void getOutline(Outline outline) {
        if (this.drawableState.shadowCompatMode == 2) {
            return;
        }
        if (this.isRoundRect()) {
            float f = this.getTopLeftCornerResolvedSize();
            float f2 = this.drawableState.interpolation;
            outline.setRoundRect(this.getBounds(), f * f2);
            return;
        }
        this.calculatePath(this.getBoundsAsRectF(), this.path);
        if (!this.path.isConvex()) {
            if (Build.VERSION.SDK_INT < 29) return;
        }
        try {
            outline.setConvexPath(this.path);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public boolean getPadding(Rect rect) {
        if (this.drawableState.padding == null) return super.getPadding(rect);
        rect.set(this.drawableState.padding);
        return true;
    }

    public Paint.Style getPaintStyle() {
        return this.drawableState.paintStyle;
    }

    public float getParentAbsoluteElevation() {
        return this.drawableState.parentAbsoluteElevation;
    }

    @Deprecated
    public void getPathForSize(int n, int n2, Path path) {
        this.calculatePathForSize(new RectF(0.0f, 0.0f, (float)n, (float)n2), path);
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
        return (int)((double)this.drawableState.shadowCompatOffset * Math.sin(Math.toRadians(this.drawableState.shadowCompatRotation)));
    }

    public int getShadowOffsetY() {
        return (int)((double)this.drawableState.shadowCompatOffset * Math.cos(Math.toRadians(this.drawableState.shadowCompatRotation)));
    }

    public int getShadowRadius() {
        return this.drawableState.shadowCompatRadius;
    }

    public int getShadowVerticalOffset() {
        return this.drawableState.shadowCompatOffset;
    }

    @Override
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.drawableState.shapeAppearanceModel;
    }

    @Deprecated
    public ShapePathModel getShapedViewModel() {
        ShapeAppearanceModel shapeAppearanceModel = this.getShapeAppearanceModel();
        if (!(shapeAppearanceModel instanceof ShapePathModel)) return null;
        return (ShapePathModel)shapeAppearanceModel;
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
        Rect rect = this.getBounds();
        this.transparentRegion.set(rect);
        this.calculatePath(this.getBoundsAsRectF(), this.path);
        this.scratchRegion.setPath(this.path, this.transparentRegion);
        this.transparentRegion.op(this.scratchRegion, Region.Op.DIFFERENCE);
        return this.transparentRegion;
    }

    public float getZ() {
        return this.getElevation() + this.getTranslationZ();
    }

    public void initializeElevationOverlay(Context context) {
        this.drawableState.elevationOverlayProvider = new ElevationOverlayProvider(context);
        this.updateZ();
    }

    public void invalidateSelf() {
        this.pathDirty = true;
        super.invalidateSelf();
    }

    public boolean isElevationOverlayEnabled() {
        if (this.drawableState.elevationOverlayProvider == null) return false;
        if (!this.drawableState.elevationOverlayProvider.isThemeElevationOverlayEnabled()) return false;
        return true;
    }

    public boolean isElevationOverlayInitialized() {
        if (this.drawableState.elevationOverlayProvider == null) return false;
        return true;
    }

    public boolean isPointInTransparentRegion(int n, int n2) {
        return this.getTransparentRegion().contains(n, n2);
    }

    public boolean isRoundRect() {
        return this.drawableState.shapeAppearanceModel.isRoundRect(this.getBoundsAsRectF());
    }

    @Deprecated
    public boolean isShadowEnabled() {
        if (this.drawableState.shadowCompatMode == 0) return true;
        if (this.drawableState.shadowCompatMode == 2) return true;
        return false;
    }

    public boolean isStateful() {
        if (super.isStateful()) return true;
        if (this.drawableState.tintList != null) {
            if (this.drawableState.tintList.isStateful()) return true;
        }
        if (this.drawableState.strokeTintList != null) {
            if (this.drawableState.strokeTintList.isStateful()) return true;
        }
        if (this.drawableState.strokeColor != null) {
            if (this.drawableState.strokeColor.isStateful()) return true;
        }
        if (this.drawableState.fillColor == null) return false;
        if (!this.drawableState.fillColor.isStateful()) return false;
        return true;
    }

    public Drawable mutate() {
        this.drawableState = new MaterialShapeDrawableState(this.drawableState);
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        this.pathDirty = true;
        super.onBoundsChange(rect);
    }

    protected boolean onStateChange(int[] arrn) {
        boolean bl = this.updateColorsForState(arrn);
        boolean bl2 = this.updateTintFilter();
        bl2 = bl || bl2;
        if (!bl2) return bl2;
        this.invalidateSelf();
        return bl2;
    }

    public boolean requiresCompatShadow() {
        if (Build.VERSION.SDK_INT < 21) return true;
        if (this.isRoundRect()) return false;
        if (this.path.isConvex()) return false;
        if (Build.VERSION.SDK_INT >= 29) return false;
        return true;
    }

    public void setAlpha(int n) {
        if (this.drawableState.alpha == n) return;
        this.drawableState.alpha = n;
        this.invalidateSelfIgnoreShape();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.drawableState.colorFilter = colorFilter;
        this.invalidateSelfIgnoreShape();
    }

    public void setCornerSize(float f) {
        this.setShapeAppearanceModel(this.drawableState.shapeAppearanceModel.withCornerSize(f));
    }

    public void setCornerSize(CornerSize cornerSize) {
        this.setShapeAppearanceModel(this.drawableState.shapeAppearanceModel.withCornerSize(cornerSize));
    }

    public void setEdgeIntersectionCheckEnable(boolean bl) {
        this.pathProvider.setEdgeIntersectionCheckEnable(bl);
    }

    public void setElevation(float f) {
        if (this.drawableState.elevation == f) return;
        this.drawableState.elevation = f;
        this.updateZ();
    }

    public void setFillColor(ColorStateList colorStateList) {
        if (this.drawableState.fillColor == colorStateList) return;
        this.drawableState.fillColor = colorStateList;
        this.onStateChange(this.getState());
    }

    public void setInterpolation(float f) {
        if (this.drawableState.interpolation == f) return;
        this.drawableState.interpolation = f;
        this.pathDirty = true;
        this.invalidateSelf();
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        if (this.drawableState.padding == null) {
            this.drawableState.padding = new Rect();
        }
        this.drawableState.padding.set(n, n2, n3, n4);
        this.invalidateSelf();
    }

    public void setPaintStyle(Paint.Style style2) {
        this.drawableState.paintStyle = style2;
        this.invalidateSelfIgnoreShape();
    }

    public void setParentAbsoluteElevation(float f) {
        if (this.drawableState.parentAbsoluteElevation == f) return;
        this.drawableState.parentAbsoluteElevation = f;
        this.updateZ();
    }

    public void setScale(float f) {
        if (this.drawableState.scale == f) return;
        this.drawableState.scale = f;
        this.invalidateSelf();
    }

    public void setShadowBitmapDrawingEnable(boolean bl) {
        this.shadowBitmapDrawingEnable = bl;
    }

    public void setShadowColor(int n) {
        this.shadowRenderer.setShadowColor(n);
        this.drawableState.useTintColorForShadow = false;
        this.invalidateSelfIgnoreShape();
    }

    public void setShadowCompatRotation(int n) {
        if (this.drawableState.shadowCompatRotation == n) return;
        this.drawableState.shadowCompatRotation = n;
        this.invalidateSelfIgnoreShape();
    }

    public void setShadowCompatibilityMode(int n) {
        if (this.drawableState.shadowCompatMode == n) return;
        this.drawableState.shadowCompatMode = n;
        this.invalidateSelfIgnoreShape();
    }

    @Deprecated
    public void setShadowElevation(int n) {
        this.setElevation(n);
    }

    @Deprecated
    public void setShadowEnabled(boolean bl) {
        this.setShadowCompatibilityMode(bl ^ true);
    }

    @Deprecated
    public void setShadowRadius(int n) {
        this.drawableState.shadowCompatRadius = n;
    }

    public void setShadowVerticalOffset(int n) {
        if (this.drawableState.shadowCompatOffset == n) return;
        this.drawableState.shadowCompatOffset = n;
        this.invalidateSelfIgnoreShape();
    }

    @Override
    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.drawableState.shapeAppearanceModel = shapeAppearanceModel;
        this.invalidateSelf();
    }

    @Deprecated
    public void setShapedViewModel(ShapePathModel shapePathModel) {
        this.setShapeAppearanceModel(shapePathModel);
    }

    public void setStroke(float f, int n) {
        this.setStrokeWidth(f);
        this.setStrokeColor(ColorStateList.valueOf((int)n));
    }

    public void setStroke(float f, ColorStateList colorStateList) {
        this.setStrokeWidth(f);
        this.setStrokeColor(colorStateList);
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        if (this.drawableState.strokeColor == colorStateList) return;
        this.drawableState.strokeColor = colorStateList;
        this.onStateChange(this.getState());
    }

    public void setStrokeTint(int n) {
        this.setStrokeTint(ColorStateList.valueOf((int)n));
    }

    public void setStrokeTint(ColorStateList colorStateList) {
        this.drawableState.strokeTintList = colorStateList;
        this.updateTintFilter();
        this.invalidateSelfIgnoreShape();
    }

    public void setStrokeWidth(float f) {
        this.drawableState.strokeWidth = f;
        this.invalidateSelf();
    }

    @Override
    public void setTint(int n) {
        this.setTintList(ColorStateList.valueOf((int)n));
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        this.drawableState.tintList = colorStateList;
        this.updateTintFilter();
        this.invalidateSelfIgnoreShape();
    }

    @Override
    public void setTintMode(PorterDuff.Mode mode) {
        if (this.drawableState.tintMode == mode) return;
        this.drawableState.tintMode = mode;
        this.updateTintFilter();
        this.invalidateSelfIgnoreShape();
    }

    public void setTranslationZ(float f) {
        if (this.drawableState.translationZ == f) return;
        this.drawableState.translationZ = f;
        this.updateZ();
    }

    public void setUseTintColorForShadow(boolean bl) {
        if (this.drawableState.useTintColorForShadow == bl) return;
        this.drawableState.useTintColorForShadow = bl;
        this.invalidateSelf();
    }

    public void setZ(float f) {
        this.setTranslationZ(f - this.getElevation());
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CompatibilityShadowMode {
    }

    static final class MaterialShapeDrawableState
    extends Drawable.ConstantState {
        public int alpha = 255;
        public ColorFilter colorFilter;
        public float elevation = 0.0f;
        public ElevationOverlayProvider elevationOverlayProvider;
        public ColorStateList fillColor = null;
        public float interpolation = 1.0f;
        public Rect padding = null;
        public Paint.Style paintStyle = Paint.Style.FILL_AND_STROKE;
        public float parentAbsoluteElevation = 0.0f;
        public float scale = 1.0f;
        public int shadowCompatMode = 0;
        public int shadowCompatOffset = 0;
        public int shadowCompatRadius = 0;
        public int shadowCompatRotation = 0;
        public ShapeAppearanceModel shapeAppearanceModel;
        public ColorStateList strokeColor = null;
        public ColorStateList strokeTintList = null;
        public float strokeWidth;
        public ColorStateList tintList = null;
        public PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;
        public float translationZ = 0.0f;
        public boolean useTintColorForShadow = false;

        public MaterialShapeDrawableState(MaterialShapeDrawableState materialShapeDrawableState) {
            this.shapeAppearanceModel = materialShapeDrawableState.shapeAppearanceModel;
            this.elevationOverlayProvider = materialShapeDrawableState.elevationOverlayProvider;
            this.strokeWidth = materialShapeDrawableState.strokeWidth;
            this.colorFilter = materialShapeDrawableState.colorFilter;
            this.fillColor = materialShapeDrawableState.fillColor;
            this.strokeColor = materialShapeDrawableState.strokeColor;
            this.tintMode = materialShapeDrawableState.tintMode;
            this.tintList = materialShapeDrawableState.tintList;
            this.alpha = materialShapeDrawableState.alpha;
            this.scale = materialShapeDrawableState.scale;
            this.shadowCompatOffset = materialShapeDrawableState.shadowCompatOffset;
            this.shadowCompatMode = materialShapeDrawableState.shadowCompatMode;
            this.useTintColorForShadow = materialShapeDrawableState.useTintColorForShadow;
            this.interpolation = materialShapeDrawableState.interpolation;
            this.parentAbsoluteElevation = materialShapeDrawableState.parentAbsoluteElevation;
            this.elevation = materialShapeDrawableState.elevation;
            this.translationZ = materialShapeDrawableState.translationZ;
            this.shadowCompatRadius = materialShapeDrawableState.shadowCompatRadius;
            this.shadowCompatRotation = materialShapeDrawableState.shadowCompatRotation;
            this.strokeTintList = materialShapeDrawableState.strokeTintList;
            this.paintStyle = materialShapeDrawableState.paintStyle;
            if (materialShapeDrawableState.padding == null) return;
            this.padding = new Rect(materialShapeDrawableState.padding);
        }

        public MaterialShapeDrawableState(ShapeAppearanceModel shapeAppearanceModel, ElevationOverlayProvider elevationOverlayProvider) {
            this.shapeAppearanceModel = shapeAppearanceModel;
            this.elevationOverlayProvider = elevationOverlayProvider;
        }

        public int getChangingConfigurations() {
            return 0;
        }

        public Drawable newDrawable() {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(this);
            materialShapeDrawable.pathDirty = true;
            return materialShapeDrawable;
        }
    }

}

