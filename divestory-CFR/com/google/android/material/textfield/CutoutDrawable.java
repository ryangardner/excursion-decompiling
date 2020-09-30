/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.RectF
 *  android.graphics.Xfermode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 */
package com.google.android.material.textfield;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

class CutoutDrawable
extends MaterialShapeDrawable {
    private final RectF cutoutBounds;
    private final Paint cutoutPaint;
    private int savedLayer;

    CutoutDrawable() {
        this(null);
    }

    CutoutDrawable(ShapeAppearanceModel shapeAppearanceModel) {
        if (shapeAppearanceModel == null) {
            shapeAppearanceModel = new ShapeAppearanceModel();
        }
        super(shapeAppearanceModel);
        this.cutoutPaint = new Paint(1);
        this.setPaintStyles();
        this.cutoutBounds = new RectF();
    }

    private void postDraw(Canvas canvas) {
        if (this.useHardwareLayer(this.getCallback())) return;
        canvas.restoreToCount(this.savedLayer);
    }

    private void preDraw(Canvas canvas) {
        Drawable.Callback callback = this.getCallback();
        if (this.useHardwareLayer(callback)) {
            canvas = (View)callback;
            if (canvas.getLayerType() == 2) return;
            canvas.setLayerType(2, null);
            return;
        }
        this.saveCanvasLayer(canvas);
    }

    private void saveCanvasLayer(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.savedLayer = canvas.saveLayer(0.0f, 0.0f, (float)canvas.getWidth(), (float)canvas.getHeight(), null);
            return;
        }
        this.savedLayer = canvas.saveLayer(0.0f, 0.0f, (float)canvas.getWidth(), (float)canvas.getHeight(), null, 31);
    }

    private void setPaintStyles() {
        this.cutoutPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.cutoutPaint.setColor(-1);
        this.cutoutPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    private boolean useHardwareLayer(Drawable.Callback callback) {
        return callback instanceof View;
    }

    @Override
    public void draw(Canvas canvas) {
        this.preDraw(canvas);
        super.draw(canvas);
        canvas.drawRect(this.cutoutBounds, this.cutoutPaint);
        this.postDraw(canvas);
    }

    boolean hasCutout() {
        return this.cutoutBounds.isEmpty() ^ true;
    }

    void removeCutout() {
        this.setCutout(0.0f, 0.0f, 0.0f, 0.0f);
    }

    void setCutout(float f, float f2, float f3, float f4) {
        if (f == this.cutoutBounds.left && f2 == this.cutoutBounds.top && f3 == this.cutoutBounds.right) {
            if (f4 == this.cutoutBounds.bottom) return;
        }
        this.cutoutBounds.set(f, f2, f3, f4);
        this.invalidateSelf();
    }

    void setCutout(RectF rectF) {
        this.setCutout(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }
}

