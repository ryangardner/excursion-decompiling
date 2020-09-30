/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$Style
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.Renderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class DataRenderer
extends Renderer {
    protected ChartAnimator mAnimator;
    protected Paint mDrawPaint;
    protected Paint mHighlightPaint;
    protected Paint mRenderPaint;
    protected Paint mValuePaint;

    public DataRenderer(ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(viewPortHandler);
        this.mAnimator = chartAnimator;
        chartAnimator = new Paint(1);
        this.mRenderPaint = chartAnimator;
        chartAnimator.setStyle(Paint.Style.FILL);
        this.mDrawPaint = new Paint(4);
        chartAnimator = new Paint(1);
        this.mValuePaint = chartAnimator;
        chartAnimator.setColor(Color.rgb((int)63, (int)63, (int)63));
        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
        this.mValuePaint.setTextSize(Utils.convertDpToPixel(9.0f));
        chartAnimator = new Paint(1);
        this.mHighlightPaint = chartAnimator;
        chartAnimator.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(2.0f);
        this.mHighlightPaint.setColor(Color.rgb((int)255, (int)187, (int)115));
    }

    protected void applyValueTextStyle(IDataSet iDataSet) {
        this.mValuePaint.setTypeface(iDataSet.getValueTypeface());
        this.mValuePaint.setTextSize(iDataSet.getValueTextSize());
    }

    public abstract void drawData(Canvas var1);

    public abstract void drawExtras(Canvas var1);

    public abstract void drawHighlighted(Canvas var1, Highlight[] var2);

    public abstract void drawValue(Canvas var1, String var2, float var3, float var4, int var5);

    public abstract void drawValues(Canvas var1);

    public Paint getPaintHighlight() {
        return this.mHighlightPaint;
    }

    public Paint getPaintRender() {
        return this.mRenderPaint;
    }

    public Paint getPaintValues() {
        return this.mValuePaint;
    }

    public abstract void initBuffers();

    protected boolean isDrawingValuesAllowed(ChartInterface chartInterface) {
        if (!((float)chartInterface.getData().getEntryCount() < (float)chartInterface.getMaxVisibleCount() * this.mViewPortHandler.getScaleX())) return false;
        return true;
    }
}

