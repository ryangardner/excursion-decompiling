/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.renderer.LineScatterCandleRadarRenderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class LineRadarRenderer
extends LineScatterCandleRadarRenderer {
    public LineRadarRenderer(ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
    }

    private boolean clipPathSupported() {
        if (Utils.getSDKInt() < 18) return false;
        return true;
    }

    protected void drawFilledPath(Canvas canvas, Path path, int n, int n2) {
        n = n & 16777215 | n2 << 24;
        if (this.clipPathSupported()) {
            n2 = canvas.save();
            canvas.clipPath(path);
            canvas.drawColor(n);
            canvas.restoreToCount(n2);
            return;
        }
        Paint.Style style2 = this.mRenderPaint.getStyle();
        n2 = this.mRenderPaint.getColor();
        this.mRenderPaint.setStyle(Paint.Style.FILL);
        this.mRenderPaint.setColor(n);
        canvas.drawPath(path, this.mRenderPaint);
        this.mRenderPaint.setColor(n2);
        this.mRenderPaint.setStyle(style2);
    }

    protected void drawFilledPath(Canvas object, Path path, Drawable drawable2) {
        if (this.clipPathSupported()) {
            int n = object.save();
            object.clipPath(path);
            drawable2.setBounds((int)this.mViewPortHandler.contentLeft(), (int)this.mViewPortHandler.contentTop(), (int)this.mViewPortHandler.contentRight(), (int)this.mViewPortHandler.contentBottom());
            drawable2.draw((Canvas)object);
            object.restoreToCount(n);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Fill-drawables not (yet) supported below API level 18, this code was run on API level ");
        ((StringBuilder)object).append(Utils.getSDKInt());
        ((StringBuilder)object).append(".");
        throw new RuntimeException(((StringBuilder)object).toString());
    }
}

