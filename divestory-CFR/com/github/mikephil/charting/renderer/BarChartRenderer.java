/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.LinearGradient
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class BarChartRenderer
extends BarLineScatterCandleBubbleRenderer {
    protected Paint mBarBorderPaint;
    protected BarBuffer[] mBarBuffers;
    protected RectF mBarRect = new RectF();
    private RectF mBarShadowRectBuffer = new RectF();
    protected BarDataProvider mChart;
    protected Paint mShadowPaint;

    public BarChartRenderer(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = barDataProvider;
        this.mHighlightPaint = new Paint(1);
        this.mHighlightPaint.setStyle(Paint.Style.FILL);
        this.mHighlightPaint.setColor(Color.rgb((int)0, (int)0, (int)0));
        this.mHighlightPaint.setAlpha(120);
        barDataProvider = new Paint(1);
        this.mShadowPaint = barDataProvider;
        barDataProvider.setStyle(Paint.Style.FILL);
        barDataProvider = new Paint(1);
        this.mBarBorderPaint = barDataProvider;
        barDataProvider.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void drawData(Canvas canvas) {
        BarData barData = this.mChart.getBarData();
        int n = 0;
        while (n < barData.getDataSetCount()) {
            IBarDataSet iBarDataSet = (IBarDataSet)barData.getDataSetByIndex(n);
            if (iBarDataSet.isVisible()) {
                this.drawDataSet(canvas, iBarDataSet, n);
            }
            ++n;
        }
    }

    protected void drawDataSet(Canvas canvas, IBarDataSet iBarDataSet, int n) {
        int n2;
        float f;
        int n3;
        Object object = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
        this.mBarBorderPaint.setColor(iBarDataSet.getBarBorderColor());
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(iBarDataSet.getBarBorderWidth()));
        float f2 = iBarDataSet.getBarBorderWidth();
        int n4 = 0;
        int n5 = 1;
        boolean bl = f2 > 0.0f;
        float f3 = this.mAnimator.getPhaseX();
        float f4 = this.mAnimator.getPhaseY();
        if (this.mChart.isDrawBarShadowEnabled()) {
            this.mShadowPaint.setColor(iBarDataSet.getBarShadowColor());
            f2 = this.mChart.getBarData().getBarWidth() / 2.0f;
            n3 = Math.min((int)Math.ceil((float)iBarDataSet.getEntryCount() * f3), iBarDataSet.getEntryCount());
            for (n2 = 0; n2 < n3; ++n2) {
                f = ((BarEntry)iBarDataSet.getEntryForIndex(n2)).getX();
                this.mBarShadowRectBuffer.left = f - f2;
                this.mBarShadowRectBuffer.right = f + f2;
                object.rectValueToPixel(this.mBarShadowRectBuffer);
                if (!this.mViewPortHandler.isInBoundsLeft(this.mBarShadowRectBuffer.right)) continue;
                if (!this.mViewPortHandler.isInBoundsRight(this.mBarShadowRectBuffer.left)) break;
                this.mBarShadowRectBuffer.top = this.mViewPortHandler.contentTop();
                this.mBarShadowRectBuffer.bottom = this.mViewPortHandler.contentBottom();
                canvas.drawRect(this.mBarShadowRectBuffer, this.mShadowPaint);
            }
        }
        BarBuffer barBuffer = this.mBarBuffers[n];
        barBuffer.setPhases(f3, f4);
        barBuffer.setDataSet(n);
        barBuffer.setInverted(this.mChart.isInverted(iBarDataSet.getAxisDependency()));
        barBuffer.setBarWidth(this.mChart.getBarData().getBarWidth());
        barBuffer.feed(iBarDataSet);
        object.pointValuesToPixel(barBuffer.buffer);
        n = iBarDataSet.getColors().size() == 1 ? n5 : 0;
        n2 = n4;
        if (n != 0) {
            this.mRenderPaint.setColor(iBarDataSet.getColor());
            n2 = n4;
        }
        while (n2 < barBuffer.size()) {
            object = this.mViewPortHandler;
            float[] arrf = barBuffer.buffer;
            n4 = n2 + 2;
            if (object.isInBoundsLeft(arrf[n4])) {
                if (!this.mViewPortHandler.isInBoundsRight(barBuffer.buffer[n2])) {
                    return;
                }
                if (n == 0) {
                    this.mRenderPaint.setColor(iBarDataSet.getColor(n2 / 4));
                }
                if (iBarDataSet.getGradientColor() != null) {
                    object = iBarDataSet.getGradientColor();
                    this.mRenderPaint.setShader((Shader)new LinearGradient(barBuffer.buffer[n2], barBuffer.buffer[n2 + 3], barBuffer.buffer[n2], barBuffer.buffer[n2 + 1], object.getStartColor(), object.getEndColor(), Shader.TileMode.MIRROR));
                }
                if (iBarDataSet.getGradientColors() != null) {
                    object = this.mRenderPaint;
                    f4 = barBuffer.buffer[n2];
                    f = barBuffer.buffer[n2 + 3];
                    f2 = barBuffer.buffer[n2];
                    f3 = barBuffer.buffer[n2 + 1];
                    n5 = n2 / 4;
                    object.setShader((Shader)new LinearGradient(f4, f, f2, f3, iBarDataSet.getGradientColor(n5).getStartColor(), iBarDataSet.getGradientColor(n5).getEndColor(), Shader.TileMode.MIRROR));
                }
                f2 = barBuffer.buffer[n2];
                object = barBuffer.buffer;
                n5 = n2 + 1;
                f3 = object[n5];
                f4 = barBuffer.buffer[n4];
                object = barBuffer.buffer;
                n3 = n2 + 3;
                canvas.drawRect(f2, f3, f4, object[n3], this.mRenderPaint);
                if (bl) {
                    canvas.drawRect(barBuffer.buffer[n2], barBuffer.buffer[n5], barBuffer.buffer[n4], barBuffer.buffer[n3], this.mBarBorderPaint);
                }
            }
            n2 += 4;
        }
    }

    @Override
    public void drawExtras(Canvas canvas) {
    }

    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        BarData barData = this.mChart.getBarData();
        int n = arrhighlight.length;
        int n2 = 0;
        while (n2 < n) {
            BarEntry barEntry;
            Highlight highlight = arrhighlight[n2];
            Object object = (IBarDataSet)barData.getDataSetByIndex(highlight.getDataSetIndex());
            if (object != null && object.isHighlightEnabled() && this.isInBoundsX(barEntry = (BarEntry)object.getEntryForXValue(highlight.getX(), highlight.getY()), (IBarLineScatterCandleBubbleDataSet)object)) {
                float f;
                float f2;
                Transformer transformer = this.mChart.getTransformer(object.getAxisDependency());
                this.mHighlightPaint.setColor(object.getHighLightColor());
                this.mHighlightPaint.setAlpha(object.getHighLightAlpha());
                boolean bl = highlight.getStackIndex() >= 0 && barEntry.isStacked();
                if (bl) {
                    if (this.mChart.isHighlightFullBarEnabled()) {
                        f = barEntry.getPositiveSum();
                        f2 = -barEntry.getNegativeSum();
                    } else {
                        object = barEntry.getRanges()[highlight.getStackIndex()];
                        f = ((Range)object).from;
                        f2 = ((Range)object).to;
                    }
                } else {
                    f = barEntry.getY();
                    f2 = 0.0f;
                }
                this.prepareBarHighlight(barEntry.getX(), f, f2, barData.getBarWidth() / 2.0f, transformer);
                this.setHighlightDrawPos(highlight, this.mBarRect);
                canvas.drawRect(this.mBarRect, this.mHighlightPaint);
            }
            ++n2;
        }
    }

    @Override
    public void drawValue(Canvas canvas, String string2, float f, float f2, int n) {
        this.mValuePaint.setColor(n);
        canvas.drawText(string2, f, f2, this.mValuePaint);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void drawValues(Canvas var1_1) {
        if (this.isDrawingValuesAllowed(this.mChart) == false) return;
        var2_2 = this.mChart.getBarData().getDataSets();
        var3_3 = Utils.convertDpToPixel(4.5f);
        var4_4 = this.mChart.isDrawValueAboveBarEnabled();
        var5_5 = 0;
        block0 : do {
            block15 : {
                block14 : {
                    if (var5_5 >= this.mChart.getBarData().getDataSetCount()) return;
                    var6_6 = (IBarDataSet)var2_2.get(var5_5);
                    if (this.shouldDrawValues(var6_6)) break block14;
                    var7_7 = var3_3;
                    var8_8 = var2_2;
                    ** GOTO lbl114
                }
                this.applyValueTextStyle(var6_6);
                var9_9 = this.mChart.isInverted(var6_6.getAxisDependency());
                var10_10 = Utils.calcTextHeight(this.mValuePaint, "8");
                var11_11 = var4_4 != false ? -var3_3 : var10_10 + var3_3;
                var12_12 = var4_4 != false ? var10_10 + var3_3 : -var3_3;
                var13_13 = var11_11;
                var7_7 = var12_12;
                if (var9_9) {
                    var13_13 = -var11_11 - var10_10;
                    var7_7 = -var12_12 - var10_10;
                }
                var14_14 = this.mBarBuffers[var5_5];
                var15_15 = this.mAnimator.getPhaseY();
                var16_16 = var6_6.getValueFormatter();
                var17_17 = MPPointF.getInstance(var6_6.getIconsOffset());
                var17_17.x = Utils.convertDpToPixel(var17_17.x);
                var17_17.y = Utils.convertDpToPixel(var17_17.y);
                if (var6_6.isStacked()) break block15;
                var18_18 = 0;
                var8_8 = var2_2;
                var19_19 = var16_16;
                var2_2 = var17_17;
                while ((float)var18_18 < (float)var14_14.buffer.length * this.mAnimator.getPhaseX() && this.mViewPortHandler.isInBoundsRight(var12_12 = (var14_14.buffer[var18_18] + var14_14.buffer[var18_18 + 2]) / 2.0f)) {
                    var17_17 = this.mViewPortHandler;
                    var20_20 = var14_14.buffer;
                    var21_21 = var18_18 + 1;
                    if (var17_17.isInBoundsY(var20_20[var21_21]) && this.mViewPortHandler.isInBoundsLeft(var12_12)) {
                        var22_22 = var18_18 / 4;
                        var17_17 = (BarEntry)var6_6.getEntryForIndex(var22_22);
                        var10_10 = var17_17.getY();
                        if (var6_6.isDrawValuesEnabled()) {
                            var23_23 = var19_19.getBarLabel((BarEntry)var17_17);
                            var20_20 = var14_14.buffer;
                            var11_11 = var10_10 >= 0.0f ? (float)(var20_20[var21_21] + var13_13) : (float)(var20_20[var18_18 + 3] + var7_7);
                            this.drawValue(var1_1, (String)var23_23, var12_12, var11_11, var6_6.getValueTextColor(var22_22));
                        }
                        var20_20 = var17_17;
                        var17_17 = var2_2;
                        if (var20_20.getIcon() != null && var6_6.isDrawIconsEnabled()) {
                            var20_20 = var20_20.getIcon();
                            var11_11 = var10_10 >= 0.0f ? var14_14.buffer[var21_21] + var13_13 : var14_14.buffer[var18_18 + 3] + var7_7;
                            var10_10 = var17_17.x;
                            var24_24 = var17_17.y;
                            Utils.drawImage(var1_1, (Drawable)var20_20, (int)(var12_12 + var10_10), (int)(var11_11 + var24_24), var20_20.getIntrinsicWidth(), var20_20.getIntrinsicHeight());
                        }
                    }
                    var18_18 += 4;
                }
                var20_20 = var2_2;
                var9_9 = var4_4;
                ** GOTO lbl110
            }
            var19_19 = var2_2;
            var2_2 = this.mChart.getTransformer(var6_6.getAxisDependency());
            var18_18 = 0;
            var22_22 = 0;
            var11_11 = var3_3;
            do {
                block19 : {
                    block20 : {
                        block16 : {
                            block17 : {
                                block18 : {
                                    var20_20 = var17_17;
                                    var3_3 = var11_11;
                                    var9_9 = var4_4;
                                    var8_8 = var19_19;
                                    if (!((float)var18_18 < (float)var6_6.getEntryCount() * this.mAnimator.getPhaseX())) break block16;
                                    var8_8 = (BarEntry)var6_6.getEntryForIndex(var18_18);
                                    var20_20 = var8_8.getYVals();
                                    var24_24 = (var14_14.buffer[var22_22] + var14_14.buffer[var22_22 + 2]) / 2.0f;
                                    var25_25 = var6_6.getValueTextColor(var18_18);
                                    if (var20_20 != null) break block17;
                                    if (this.mViewPortHandler.isInBoundsRight(var24_24)) break block18;
                                    var20_20 = var17_17;
                                    var3_3 = var11_11;
                                    var9_9 = var4_4;
                                    var8_8 = var19_19;
                                    break block16;
                                }
                                var26_26 = this.mViewPortHandler;
                                var23_23 = var14_14.buffer;
                                var21_21 = var22_22 + 1;
                                if (!var26_26.isInBoundsY(var23_23[var21_21]) || !this.mViewPortHandler.isInBoundsLeft(var24_24)) continue;
                                if (var6_6.isDrawValuesEnabled()) {
                                    var23_23 = var16_16.getBarLabel((BarEntry)var8_8);
                                    var12_12 = var14_14.buffer[var21_21];
                                    var3_3 = var8_8.getY() >= 0.0f ? var13_13 : var7_7;
                                    this.drawValue(var1_1, (String)var23_23, var24_24, var12_12 + var3_3, var25_25);
                                }
                                if (var8_8.getIcon() == null || !var6_6.isDrawIconsEnabled()) break block19;
                                var23_23 = var8_8.getIcon();
                                var12_12 = var14_14.buffer[var21_21];
                                var3_3 = var8_8.getY() >= 0.0f ? var13_13 : var7_7;
                                var10_10 = var17_17.x;
                                var27_27 = var17_17.y;
                                Utils.drawImage(var1_1, (Drawable)var23_23, (int)(var10_10 + var24_24), (int)(var12_12 + var3_3 + var27_27), var23_23.getIntrinsicWidth(), var23_23.getIntrinsicHeight());
                                break block19;
                            }
                            var26_26 = var20_20;
                            var28_28 = ((float[])var26_26).length * 2;
                            var23_23 = new float[var28_28];
                            var3_3 = -var8_8.getNegativeSum();
                            var21_21 = 0;
                            var10_10 = 0.0f;
                            break block20;
                        }
                        var7_7 = var3_3;
                        var4_4 = var9_9;
                        MPPointF.recycleInstance((MPPointF)var20_20);
lbl114: // 2 sources:
                        ++var5_5;
                        var2_2 = var8_8;
                        var3_3 = var7_7;
                        continue block0;
                    }
                    for (var29_29 = 0; var29_29 < var28_28; var29_29 += 2, ++var21_21) {
                        var12_12 = (float)var26_26[var21_21];
                        var30_30 = var12_12 FCMPL 0.0f;
                        if (var30_30 != false || var10_10 != 0.0f && var3_3 != 0.0f) {
                            if (var30_30 >= 0) {
                                var10_10 += var12_12;
                                var12_12 = var10_10;
                            } else {
                                var27_27 = var3_3 - var12_12;
                                var12_12 = var3_3;
                                var3_3 = var27_27;
                            }
                        }
                        var23_23[var29_29 + 1] = (Drawable)(var12_12 * var15_15);
                    }
                    var2_2.pointValuesToPixel(var23_23);
                    var21_21 = var28_28;
                    for (var29_29 = 0; var29_29 < var21_21; var29_29 += 2) {
                        var27_27 = (float)var26_26[var29_29 / 2];
                        var28_28 = var27_27 == 0.0f && var3_3 == 0.0f && var10_10 > 0.0f || var27_27 < 0.0f ? 1 : 0;
                        var31_31 = var23_23[var29_29 + 1];
                        var12_12 = var28_28 != 0 ? var7_7 : var13_13;
                        var12_12 = (float)(var31_31 + var12_12);
                        if (!this.mViewPortHandler.isInBoundsRight(var24_24)) break;
                        if (!this.mViewPortHandler.isInBoundsY(var12_12) || !this.mViewPortHandler.isInBoundsLeft(var24_24)) continue;
                        if (var6_6.isDrawValuesEnabled()) {
                            this.drawValue(var1_1, var16_16.getBarStackedLabel(var27_27, (BarEntry)var8_8), var24_24, var12_12, var25_25);
                        }
                        if ((var32_32 = var8_8).getIcon() == null || !var6_6.isDrawIconsEnabled()) continue;
                        var32_32 = var32_32.getIcon();
                        Utils.drawImage(var1_1, (Drawable)var32_32, (int)(var24_24 + var17_17.x), (int)(var12_12 + var17_17.y), var32_32.getIntrinsicWidth(), var32_32.getIntrinsicHeight());
                    }
                }
                var22_22 = var20_20 == null ? (var22_22 += 4) : (var22_22 += ((Object)var20_20).length * 4);
                ++var18_18;
            } while (true);
            break;
        } while (true);
    }

    @Override
    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new BarBuffer[barData.getDataSetCount()];
        int n = 0;
        while (n < this.mBarBuffers.length) {
            IBarDataSet iBarDataSet = (IBarDataSet)barData.getDataSetByIndex(n);
            BarBuffer[] arrbarBuffer = this.mBarBuffers;
            int n2 = iBarDataSet.getEntryCount();
            int n3 = iBarDataSet.isStacked() ? iBarDataSet.getStackSize() : 1;
            arrbarBuffer[n] = new BarBuffer(n2 * 4 * n3, barData.getDataSetCount(), iBarDataSet.isStacked());
            ++n;
        }
    }

    protected void prepareBarHighlight(float f, float f2, float f3, float f4, Transformer transformer) {
        this.mBarRect.set(f - f4, f2, f + f4, f3);
        transformer.rectToPixelPhase(this.mBarRect, this.mAnimator.getPhaseY());
    }

    protected void setHighlightDrawPos(Highlight highlight, RectF rectF) {
        highlight.setDraw(rectF.centerX(), rectF.top);
    }
}

