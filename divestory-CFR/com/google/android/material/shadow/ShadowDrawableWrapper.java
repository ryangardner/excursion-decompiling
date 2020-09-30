/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.LinearGradient
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$FillType
 *  android.graphics.RadialGradient
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 */
package com.google.android.material.shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;

@Deprecated
public class ShadowDrawableWrapper
extends DrawableWrapper {
    static final double COS_45 = Math.cos(Math.toRadians(45.0));
    static final float SHADOW_BOTTOM_SCALE = 1.0f;
    static final float SHADOW_HORIZ_SCALE = 0.5f;
    static final float SHADOW_MULTIPLIER = 1.5f;
    static final float SHADOW_TOP_SCALE = 0.25f;
    private boolean addPaddingForCorners = true;
    final RectF contentBounds;
    float cornerRadius;
    final Paint cornerShadowPaint;
    Path cornerShadowPath;
    private boolean dirty = true;
    final Paint edgeShadowPaint;
    float maxShadowSize;
    private boolean printedShadowClipWarning = false;
    float rawMaxShadowSize;
    float rawShadowSize;
    private float rotation;
    private final int shadowEndColor;
    private final int shadowMiddleColor;
    float shadowSize;
    private final int shadowStartColor;

    public ShadowDrawableWrapper(Context context, Drawable drawable2, float f, float f2, float f3) {
        super(drawable2);
        this.shadowStartColor = ContextCompat.getColor(context, R.color.design_fab_shadow_start_color);
        this.shadowMiddleColor = ContextCompat.getColor(context, R.color.design_fab_shadow_mid_color);
        this.shadowEndColor = ContextCompat.getColor(context, R.color.design_fab_shadow_end_color);
        context = new Paint(5);
        this.cornerShadowPaint = context;
        context.setStyle(Paint.Style.FILL);
        this.cornerRadius = Math.round(f);
        this.contentBounds = new RectF();
        context = new Paint(this.cornerShadowPaint);
        this.edgeShadowPaint = context;
        context.setAntiAlias(false);
        this.setShadowSize(f2, f3);
    }

    private void buildComponents(Rect rect) {
        float f = this.rawMaxShadowSize * 1.5f;
        this.contentBounds.set((float)rect.left + this.rawMaxShadowSize, (float)rect.top + f, (float)rect.right - this.rawMaxShadowSize, (float)rect.bottom - f);
        this.getWrappedDrawable().setBounds((int)this.contentBounds.left, (int)this.contentBounds.top, (int)this.contentBounds.right, (int)this.contentBounds.bottom);
        this.buildShadowCorners();
    }

    private void buildShadowCorners() {
        int n;
        int n2;
        float f;
        int n3;
        float f2 = this.cornerRadius;
        RectF rectF = new RectF(-f2, -f2, f2, f2);
        RectF rectF2 = new RectF(rectF);
        f2 = this.shadowSize;
        rectF2.inset(-f2, -f2);
        Path path = this.cornerShadowPath;
        if (path == null) {
            this.cornerShadowPath = new Path();
        } else {
            path.reset();
        }
        this.cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        this.cornerShadowPath.moveTo(-this.cornerRadius, 0.0f);
        this.cornerShadowPath.rLineTo(-this.shadowSize, 0.0f);
        this.cornerShadowPath.arcTo(rectF2, 180.0f, 90.0f, false);
        this.cornerShadowPath.arcTo(rectF, 270.0f, -90.0f, false);
        this.cornerShadowPath.close();
        f2 = -rectF2.top;
        if (f2 > 0.0f) {
            f = this.cornerRadius / f2;
            float f3 = (1.0f - f) / 2.0f;
            Paint paint = this.cornerShadowPaint;
            n3 = this.shadowStartColor;
            n2 = this.shadowMiddleColor;
            n = this.shadowEndColor;
            path = Shader.TileMode.CLAMP;
            paint.setShader((Shader)new RadialGradient(0.0f, 0.0f, f2, new int[]{0, n3, n2, n}, new float[]{0.0f, f, f3 + f, 1.0f}, (Shader.TileMode)path));
        }
        path = this.edgeShadowPaint;
        f = rectF.top;
        f2 = rectF2.top;
        n2 = this.shadowStartColor;
        n3 = this.shadowMiddleColor;
        n = this.shadowEndColor;
        rectF = Shader.TileMode.CLAMP;
        path.setShader((Shader)new LinearGradient(0.0f, f, 0.0f, f2, new int[]{n2, n3, n}, new float[]{0.0f, 0.5f, 1.0f}, (Shader.TileMode)rectF));
        this.edgeShadowPaint.setAntiAlias(false);
    }

    public static float calculateHorizontalPadding(float f, float f2, boolean bl) {
        float f3 = f;
        if (!bl) return f3;
        return (float)((double)f + (1.0 - COS_45) * (double)f2);
    }

    public static float calculateVerticalPadding(float f, float f2, boolean bl) {
        if (!bl) return f * 1.5f;
        return (float)((double)(f * 1.5f) + (1.0 - COS_45) * (double)f2);
    }

    private void drawShadow(Canvas canvas) {
        int n = canvas.save();
        canvas.rotate(this.rotation, this.contentBounds.centerX(), this.contentBounds.centerY());
        float f = this.cornerRadius;
        float f2 = -f - this.shadowSize;
        float f3 = this.contentBounds.width();
        float f4 = f * 2.0f;
        int n2 = f3 - f4 > 0.0f ? 1 : 0;
        boolean bl = this.contentBounds.height() - f4 > 0.0f;
        f3 = this.rawShadowSize;
        float f5 = f / (f3 - 0.5f * f3 + f);
        float f6 = f / (f3 - 0.25f * f3 + f);
        f3 = f / (f3 - f3 * 1.0f + f);
        int n3 = canvas.save();
        canvas.translate(this.contentBounds.left + f, this.contentBounds.top + f);
        canvas.scale(f5, f6);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (n2 != 0) {
            canvas.scale(1.0f / f5, 1.0f);
            canvas.drawRect(0.0f, f2, this.contentBounds.width() - f4, -this.cornerRadius, this.edgeShadowPaint);
        }
        canvas.restoreToCount(n3);
        n3 = canvas.save();
        canvas.translate(this.contentBounds.right - f, this.contentBounds.bottom - f);
        canvas.scale(f5, f3);
        canvas.rotate(180.0f);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (n2 != 0) {
            canvas.scale(1.0f / f5, 1.0f);
            canvas.drawRect(0.0f, f2, this.contentBounds.width() - f4, -this.cornerRadius + this.shadowSize, this.edgeShadowPaint);
        }
        canvas.restoreToCount(n3);
        n2 = canvas.save();
        canvas.translate(this.contentBounds.left + f, this.contentBounds.bottom - f);
        canvas.scale(f5, f3);
        canvas.rotate(270.0f);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (bl) {
            canvas.scale(1.0f / f3, 1.0f);
            canvas.drawRect(0.0f, f2, this.contentBounds.height() - f4, -this.cornerRadius, this.edgeShadowPaint);
        }
        canvas.restoreToCount(n2);
        n2 = canvas.save();
        canvas.translate(this.contentBounds.right - f, this.contentBounds.top + f);
        canvas.scale(f5, f6);
        canvas.rotate(90.0f);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (bl) {
            canvas.scale(1.0f / f6, 1.0f);
            canvas.drawRect(0.0f, f2, this.contentBounds.height() - f4, -this.cornerRadius, this.edgeShadowPaint);
        }
        canvas.restoreToCount(n2);
        canvas.restoreToCount(n);
    }

    private static int toEven(float f) {
        int n;
        int n2 = n = Math.round(f);
        if (n % 2 != 1) return n2;
        return n - 1;
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.dirty) {
            this.buildComponents(this.getBounds());
            this.dirty = false;
        }
        this.drawShadow(canvas);
        super.draw(canvas);
    }

    public float getCornerRadius() {
        return this.cornerRadius;
    }

    public float getMaxShadowSize() {
        return this.rawMaxShadowSize;
    }

    public float getMinHeight() {
        float f = this.rawMaxShadowSize;
        return Math.max(f, this.cornerRadius + f * 1.5f / 2.0f) * 2.0f + this.rawMaxShadowSize * 1.5f * 2.0f;
    }

    public float getMinWidth() {
        float f = this.rawMaxShadowSize;
        return Math.max(f, this.cornerRadius + f / 2.0f) * 2.0f + this.rawMaxShadowSize * 2.0f;
    }

    @Override
    public int getOpacity() {
        return -3;
    }

    @Override
    public boolean getPadding(Rect rect) {
        int n = (int)Math.ceil(ShadowDrawableWrapper.calculateVerticalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
        int n2 = (int)Math.ceil(ShadowDrawableWrapper.calculateHorizontalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
        rect.set(n2, n, n2, n);
        return true;
    }

    public float getShadowSize() {
        return this.rawShadowSize;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        this.dirty = true;
    }

    public void setAddPaddingForCorners(boolean bl) {
        this.addPaddingForCorners = bl;
        this.invalidateSelf();
    }

    @Override
    public void setAlpha(int n) {
        super.setAlpha(n);
        this.cornerShadowPaint.setAlpha(n);
        this.edgeShadowPaint.setAlpha(n);
    }

    public void setCornerRadius(float f) {
        if (this.cornerRadius == (f = (float)Math.round(f))) {
            return;
        }
        this.cornerRadius = f;
        this.dirty = true;
        this.invalidateSelf();
    }

    public void setMaxShadowSize(float f) {
        this.setShadowSize(this.rawShadowSize, f);
    }

    public final void setRotation(float f) {
        if (this.rotation == f) return;
        this.rotation = f;
        this.invalidateSelf();
    }

    public void setShadowSize(float f) {
        this.setShadowSize(f, this.rawMaxShadowSize);
    }

    public void setShadowSize(float f, float f2) {
        if (f < 0.0f) throw new IllegalArgumentException("invalid shadow size");
        if (f2 < 0.0f) throw new IllegalArgumentException("invalid shadow size");
        float f3 = ShadowDrawableWrapper.toEven(f);
        f2 = ShadowDrawableWrapper.toEven(f2);
        f = f3;
        if (f3 > f2) {
            if (!this.printedShadowClipWarning) {
                this.printedShadowClipWarning = true;
            }
            f = f2;
        }
        if (this.rawShadowSize == f && this.rawMaxShadowSize == f2) {
            return;
        }
        this.rawShadowSize = f;
        this.rawMaxShadowSize = f2;
        this.shadowSize = Math.round(f * 1.5f);
        this.maxShadowSize = f2;
        this.dirty = true;
        this.invalidateSelf();
    }
}

