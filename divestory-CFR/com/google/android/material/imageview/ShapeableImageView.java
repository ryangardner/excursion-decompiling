/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Outline
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Xfermode
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewOutlineProvider
 */
package com.google.android.material.imageview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class ShapeableImageView
extends AppCompatImageView
implements Shapeable {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ShapeableImageView;
    private final Paint borderPaint;
    private final Paint clearPaint;
    private final RectF destination;
    private Path maskPath;
    private final RectF maskRect;
    private final Path path = new Path();
    private final ShapeAppearancePathProvider pathProvider = new ShapeAppearancePathProvider();
    private final MaterialShapeDrawable shadowDrawable;
    private ShapeAppearanceModel shapeAppearanceModel;
    private ColorStateList strokeColor;
    private float strokeWidth;

    public ShapeableImageView(Context context) {
        this(context, null, 0);
    }

    public ShapeableImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShapeableImageView(Context context, AttributeSet attributeSet, int n) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, n, DEF_STYLE_RES), attributeSet, n);
        Paint paint;
        context = this.getContext();
        this.clearPaint = paint = new Paint();
        paint.setAntiAlias(true);
        this.clearPaint.setColor(-1);
        this.clearPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        this.destination = new RectF();
        this.maskRect = new RectF();
        this.maskPath = new Path();
        paint = context.obtainStyledAttributes(attributeSet, R.styleable.ShapeableImageView, n, DEF_STYLE_RES);
        this.strokeColor = MaterialResources.getColorStateList(context, (TypedArray)paint, R.styleable.ShapeableImageView_strokeColor);
        this.strokeWidth = paint.getDimensionPixelSize(R.styleable.ShapeableImageView_strokeWidth, 0);
        this.borderPaint = paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setAntiAlias(true);
        this.shapeAppearanceModel = ShapeAppearanceModel.builder(context, attributeSet, n, DEF_STYLE_RES).build();
        this.shadowDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
        if (Build.VERSION.SDK_INT < 21) return;
        this.setOutlineProvider((ViewOutlineProvider)new OutlineProvider());
    }

    private void drawStroke(Canvas canvas) {
        if (this.strokeColor == null) {
            return;
        }
        this.borderPaint.setStrokeWidth(this.strokeWidth);
        int n = this.strokeColor.getColorForState(this.getDrawableState(), this.strokeColor.getDefaultColor());
        if (!(this.strokeWidth > 0.0f)) return;
        if (n == 0) return;
        this.borderPaint.setColor(n);
        canvas.drawPath(this.path, this.borderPaint);
    }

    private void updateShapeMask(int n, int n2) {
        this.destination.set((float)this.getPaddingLeft(), (float)this.getPaddingTop(), (float)(n - this.getPaddingRight()), (float)(n2 - this.getPaddingBottom()));
        this.pathProvider.calculatePath(this.shapeAppearanceModel, 1.0f, this.destination, this.path);
        this.maskPath.rewind();
        this.maskPath.addPath(this.path);
        this.maskRect.set(0.0f, 0.0f, (float)n, (float)n2);
        this.maskPath.addRect(this.maskRect, Path.Direction.CCW);
    }

    @Override
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    public ColorStateList getStrokeColor() {
        return this.strokeColor;
    }

    public float getStrokeWidth() {
        return this.strokeWidth;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setLayerType(2, null);
    }

    protected void onDetachedFromWindow() {
        this.setLayerType(0, null);
        super.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.maskPath, this.clearPaint);
        this.drawStroke(canvas);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.updateShapeMask(n, n2);
    }

    @Override
    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        this.shadowDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        this.updateShapeMask(this.getWidth(), this.getHeight());
        this.invalidate();
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        this.strokeColor = colorStateList;
        this.invalidate();
    }

    public void setStrokeColorResource(int n) {
        this.setStrokeColor(AppCompatResources.getColorStateList(this.getContext(), n));
    }

    public void setStrokeWidth(float f) {
        if (this.strokeWidth == f) return;
        this.strokeWidth = f;
        this.invalidate();
    }

    public void setStrokeWidthResource(int n) {
        this.setStrokeWidth(this.getResources().getDimensionPixelSize(n));
    }

    class OutlineProvider
    extends ViewOutlineProvider {
        private final Rect rect = new Rect();

        OutlineProvider() {
        }

        public void getOutline(View view, Outline outline) {
            if (ShapeableImageView.this.shapeAppearanceModel == null) {
                return;
            }
            ShapeableImageView.this.destination.round(this.rect);
            ShapeableImageView.this.shadowDrawable.setBounds(this.rect);
            ShapeableImageView.this.shadowDrawable.getOutline(outline);
        }
    }

}

