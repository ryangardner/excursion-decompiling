/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.buffer.HorizontalBarBuffer;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class HorizontalBarChartRenderer
extends BarChartRenderer {
    private RectF mBarShadowRectBuffer = new RectF();

    public HorizontalBarChartRenderer(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(barDataProvider, chartAnimator, viewPortHandler);
        this.mValuePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void drawDataSet(Canvas canvas, IBarDataSet iBarDataSet, int n) {
        int n2;
        int n3;
        float[] arrf = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
        this.mBarBorderPaint.setColor(iBarDataSet.getBarBorderColor());
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(iBarDataSet.getBarBorderWidth()));
        float f = iBarDataSet.getBarBorderWidth();
        int n4 = 0;
        int n5 = 1;
        boolean bl = f > 0.0f;
        float f2 = this.mAnimator.getPhaseX();
        float f3 = this.mAnimator.getPhaseY();
        if (this.mChart.isDrawBarShadowEnabled()) {
            this.mShadowPaint.setColor(iBarDataSet.getBarShadowColor());
            float f4 = this.mChart.getBarData().getBarWidth() / 2.0f;
            n2 = Math.min((int)Math.ceil((float)iBarDataSet.getEntryCount() * f2), iBarDataSet.getEntryCount());
            for (n3 = 0; n3 < n2; ++n3) {
                f = ((BarEntry)iBarDataSet.getEntryForIndex(n3)).getX();
                this.mBarShadowRectBuffer.top = f - f4;
                this.mBarShadowRectBuffer.bottom = f + f4;
                arrf.rectValueToPixel(this.mBarShadowRectBuffer);
                if (!this.mViewPortHandler.isInBoundsTop(this.mBarShadowRectBuffer.bottom)) continue;
                if (!this.mViewPortHandler.isInBoundsBottom(this.mBarShadowRectBuffer.top)) break;
                this.mBarShadowRectBuffer.left = this.mViewPortHandler.contentLeft();
                this.mBarShadowRectBuffer.right = this.mViewPortHandler.contentRight();
                canvas.drawRect(this.mBarShadowRectBuffer, this.mShadowPaint);
            }
        }
        BarBuffer barBuffer = this.mBarBuffers[n];
        barBuffer.setPhases(f2, f3);
        barBuffer.setDataSet(n);
        barBuffer.setInverted(this.mChart.isInverted(iBarDataSet.getAxisDependency()));
        barBuffer.setBarWidth(this.mChart.getBarData().getBarWidth());
        barBuffer.feed(iBarDataSet);
        arrf.pointValuesToPixel(barBuffer.buffer);
        n = iBarDataSet.getColors().size() == 1 ? n5 : 0;
        n3 = n4;
        if (n != 0) {
            this.mRenderPaint.setColor(iBarDataSet.getColor());
            n3 = n4;
        }
        while (n3 < barBuffer.size()) {
            ViewPortHandler viewPortHandler = this.mViewPortHandler;
            arrf = barBuffer.buffer;
            n5 = n3 + 3;
            if (!viewPortHandler.isInBoundsTop(arrf[n5])) {
                return;
            }
            viewPortHandler = this.mViewPortHandler;
            arrf = barBuffer.buffer;
            n2 = n3 + 1;
            if (viewPortHandler.isInBoundsBottom(arrf[n2])) {
                if (n == 0) {
                    this.mRenderPaint.setColor(iBarDataSet.getColor(n3 / 4));
                }
                f = barBuffer.buffer[n3];
                f3 = barBuffer.buffer[n2];
                arrf = barBuffer.buffer;
                n4 = n3 + 2;
                canvas.drawRect(f, f3, arrf[n4], barBuffer.buffer[n5], this.mRenderPaint);
                if (bl) {
                    canvas.drawRect(barBuffer.buffer[n3], barBuffer.buffer[n2], barBuffer.buffer[n4], barBuffer.buffer[n5], this.mBarBorderPaint);
                }
            }
            n3 += 4;
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
        var3_3 = Utils.convertDpToPixel(5.0f);
        var4_4 = this.mChart.isDrawValueAboveBarEnabled();
        var5_5 = 0;
        block0 : do {
            block19 : {
                if (var5_5 >= this.mChart.getBarData().getDataSetCount()) return;
                var6_6 = (IBarDataSet)var2_2.get(var5_5);
                if (!this.shouldDrawValues(var6_6)) ** GOTO lbl122
                var7_7 = this.mChart.isInverted(var6_6.getAxisDependency());
                this.applyValueTextStyle(var6_6);
                var8_8 = (float)Utils.calcTextHeight(this.mValuePaint, "10") / 2.0f;
                var9_9 = var6_6.getValueFormatter();
                var10_10 = this.mBarBuffers[var5_5];
                var11_11 = this.mAnimator.getPhaseY();
                var12_12 = MPPointF.getInstance(var6_6.getIconsOffset());
                var12_12.x = Utils.convertDpToPixel(var12_12.x);
                var12_12.y = Utils.convertDpToPixel(var12_12.y);
                if (var6_6.isStacked()) break block19;
                var13_13 = 0;
                var14_14 = var8_8;
                var15_15 = var2_2;
                var16_16 = var9_9;
                var17_17 = var10_10;
                var2_2 = var12_12;
                while ((float)var13_13 < (float)var17_17.buffer.length * this.mAnimator.getPhaseX()) {
                    var12_12 = var17_17.buffer;
                    var18_18 = var13_13 + 1;
                    var19_19 = (var12_12[var18_18] + var17_17.buffer[var13_13 + 3]) / 2.0f;
                    if (!this.mViewPortHandler.isInBoundsTop(var17_17.buffer[var18_18])) break;
                    if (this.mViewPortHandler.isInBoundsX(var17_17.buffer[var13_13]) && this.mViewPortHandler.isInBoundsBottom(var17_17.buffer[var18_18])) {
                        var10_10 = (BarEntry)var6_6.getEntryForIndex(var13_13 / 4);
                        var8_8 = var10_10.getY();
                        var12_12 = var16_16.getBarLabel((BarEntry)var10_10);
                        var11_11 = Utils.calcTextWidth(this.mValuePaint, (String)var12_12);
                        var20_20 = var4_4 != false ? (Object)var3_3 : (Object)(-(var11_11 + var3_3));
                        var21_21 = var4_4 != false ? -(var11_11 + var3_3) : var3_3;
                        var22_22 /* !! */  = var20_20;
                        var23_23 = var21_21;
                        if (var7_7) {
                            var22_22 /* !! */  = -var20_20 - var11_11;
                            var23_23 = -var21_21 - var11_11;
                        }
                        var20_20 = var22_22 /* !! */ ;
                        if (var6_6.isDrawValuesEnabled()) {
                            var22_22 /* !! */  = var17_17.buffer[var13_13 + 2];
                            var21_21 = var8_8 >= 0.0f ? (float)var20_20 : var23_23;
                            this.drawValue(var1_1, (String)var12_12, (float)(var22_22 /* !! */  + var21_21), var19_19 + var14_14, var6_6.getValueTextColor(var13_13 / 2));
                        }
                        var12_12 = var2_2;
                        if (var10_10.getIcon() != null && var6_6.isDrawIconsEnabled()) {
                            var10_10 = var10_10.getIcon();
                            var21_21 = var17_17.buffer[var13_13 + 2];
                            if (!(var8_8 >= 0.0f)) {
                                var20_20 = var23_23;
                            }
                            var22_22 /* !! */  = var12_12.x;
                            var23_23 = var12_12.y;
                            Utils.drawImage(var1_1, (Drawable)var10_10, (int)(var21_21 + var20_20 + var22_22 /* !! */ ), (int)(var19_19 + var23_23), var10_10.getIntrinsicWidth(), var10_10.getIntrinsicHeight());
                        }
                    }
                    var13_13 += 4;
                }
                var17_17 = var2_2;
                var2_2 = var15_15;
                ** GOTO lbl120
            }
            var15_15 = var2_2;
            var13_13 = var5_5;
            var16_16 = var12_12;
            var24_24 = this.mChart.getTransformer(var6_6.getAxisDependency());
            var18_18 = 0;
            var25_25 = 0;
            do {
                block23 : {
                    block24 : {
                        block20 : {
                            block21 : {
                                block22 : {
                                    var17_17 = var16_16;
                                    var2_2 = var15_15;
                                    var5_5 = var13_13;
                                    if (!((float)var18_18 < (float)var6_6.getEntryCount() * this.mAnimator.getPhaseX())) break block20;
                                    var12_12 = (BarEntry)var6_6.getEntryForIndex(var18_18);
                                    var26_26 = var6_6.getValueTextColor(var18_18);
                                    var2_2 = var12_12.getYVals();
                                    if (var2_2 != null) break block21;
                                    var27_27 = this.mViewPortHandler;
                                    var17_17 = var10_10.buffer;
                                    var5_5 = var25_25 + 1;
                                    if (var27_27.isInBoundsTop(var17_17[var5_5])) break block22;
                                    var17_17 = var16_16;
                                    var2_2 = var15_15;
                                    var5_5 = var13_13;
                                    break block20;
                                }
                                if (!this.mViewPortHandler.isInBoundsX(var10_10.buffer[var25_25]) || !this.mViewPortHandler.isInBoundsBottom(var10_10.buffer[var5_5])) continue;
                                var17_17 = var9_9.getBarLabel((BarEntry)var12_12);
                                var14_14 = Utils.calcTextWidth(this.mValuePaint, (String)var17_17);
                                var20_20 = var4_4 != false ? (Object)var3_3 : (Object)(-(var14_14 + var3_3));
                                var21_21 = var4_4 != false ? -(var14_14 + var3_3) : var3_3;
                                var22_22 /* !! */  = var20_20;
                                var23_23 = var21_21;
                                if (var7_7) {
                                    var22_22 /* !! */  = -var20_20 - var14_14;
                                    var23_23 = -var21_21 - var14_14;
                                }
                                var20_20 = var22_22 /* !! */ ;
                                if (var6_6.isDrawValuesEnabled()) {
                                    var22_22 /* !! */  = var10_10.buffer[var25_25 + 2];
                                    var21_21 = var12_12.getY() >= 0.0f ? (float)var20_20 : var23_23;
                                    this.drawValue(var1_1, (String)var17_17, (float)(var22_22 /* !! */  + var21_21), var10_10.buffer[var5_5] + var8_8, var26_26);
                                }
                                if (var12_12.getIcon() == null || !var6_6.isDrawIconsEnabled()) break block23;
                                var17_17 = var12_12.getIcon();
                                var21_21 = var10_10.buffer[var25_25 + 2];
                                if (!(var12_12.getY() >= 0.0f)) {
                                    var20_20 = var23_23;
                                }
                                var23_23 = var10_10.buffer[var5_5];
                                var14_14 = var16_16.x;
                                var22_22 /* !! */  = var16_16.y;
                                Utils.drawImage(var1_1, (Drawable)var17_17, (int)(var21_21 + var20_20 + var14_14), (int)(var23_23 + var22_22 /* !! */ ), var17_17.getIntrinsicWidth(), var17_17.getIntrinsicHeight());
                                break block23;
                            }
                            var27_27 = var2_2;
                            var28_28 = ((float[])var27_27).length * 2;
                            var17_17 = new float[var28_28];
                            var23_23 = -var12_12.getNegativeSum();
                            var5_5 = 0;
                            var19_19 = 0.0f;
                            break block24;
                        }
                        MPPointF.recycleInstance(var17_17);
lbl122: // 2 sources:
                        ++var5_5;
                        continue block0;
                    }
                    for (var29_29 = 0; var29_29 < var28_28; var29_29 += 2, ++var5_5) {
                        var20_20 = var27_27[var5_5];
                        var30_30 = var20_20 FCMPL 0.0f;
                        if (var30_30 != false || var19_19 != 0.0f && var23_23 != 0.0f) {
                            if (var30_30 >= 0) {
                                var19_19 += var20_20;
                                var20_20 = var19_19;
                            } else {
                                var21_21 = var23_23 - var20_20;
                                var20_20 = var23_23;
                                var23_23 = var21_21;
                            }
                        }
                        var17_17[var29_29] = (float)(var20_20 * var11_11);
                    }
                    var24_24.pointValuesToPixel(var17_17);
                    var5_5 = var28_28;
                    for (var29_29 = 0; var29_29 < var5_5; var29_29 += 2) {
                        var31_31 = var27_27[var29_29 / 2];
                        var32_32 = var9_9.getBarStackedLabel((float)var31_31, (BarEntry)var12_12);
                        var33_33 = Utils.calcTextWidth(this.mValuePaint, var32_32);
                        var21_21 = var4_4 != false ? var3_3 : -(var33_33 + var3_3);
                        var14_14 = var4_4 != false ? -(var33_33 + var3_3) : var3_3;
                        var22_22 /* !! */  = var21_21;
                        var20_20 = var14_14;
                        if (var7_7) {
                            var22_22 /* !! */  = -var21_21 - var33_33;
                            var20_20 = -var14_14 - var33_33;
                        }
                        var28_28 = var31_31 == 0.0f && var23_23 == 0.0f && var19_19 > 0.0f || var31_31 < 0.0f ? 1 : 0;
                        var21_21 = (float)var17_17[var29_29];
                        if (var28_28 != 0) {
                            var22_22 /* !! */  = var20_20;
                        }
                        var20_20 = var21_21 + var22_22 /* !! */ ;
                        var21_21 = (var10_10.buffer[var25_25 + 1] + var10_10.buffer[var25_25 + 3]) / 2.0f;
                        if (!this.mViewPortHandler.isInBoundsTop(var21_21)) break;
                        if (!this.mViewPortHandler.isInBoundsX((float)var20_20) || !this.mViewPortHandler.isInBoundsBottom(var21_21)) continue;
                        if (var6_6.isDrawValuesEnabled()) {
                            this.drawValue(var1_1, var32_32, (float)var20_20, var21_21 + var8_8, var26_26);
                        }
                        if (var12_12.getIcon() == null || !var6_6.isDrawIconsEnabled()) continue;
                        var32_32 = var12_12.getIcon();
                        Utils.drawImage(var1_1, (Drawable)var32_32, (int)(var20_20 + var16_16.x), (int)(var21_21 + var16_16.y), var32_32.getIntrinsicWidth(), var32_32.getIntrinsicHeight());
                    }
                }
                var5_5 = var2_2 == null ? var25_25 + 4 : var25_25 + var2_2.length * 4;
                ++var18_18;
                var25_25 = var5_5;
            } while (true);
            break;
        } while (true);
    }

    @Override
    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new HorizontalBarBuffer[barData.getDataSetCount()];
        int n = 0;
        while (n < this.mBarBuffers.length) {
            IBarDataSet iBarDataSet = (IBarDataSet)barData.getDataSetByIndex(n);
            BarBuffer[] arrbarBuffer = this.mBarBuffers;
            int n2 = iBarDataSet.getEntryCount();
            int n3 = iBarDataSet.isStacked() ? iBarDataSet.getStackSize() : 1;
            arrbarBuffer[n] = new HorizontalBarBuffer(n2 * 4 * n3, barData.getDataSetCount(), iBarDataSet.isStacked());
            ++n;
        }
    }

    @Override
    protected boolean isDrawingValuesAllowed(ChartInterface chartInterface) {
        if (!((float)chartInterface.getData().getEntryCount() < (float)chartInterface.getMaxVisibleCount() * this.mViewPortHandler.getScaleY())) return false;
        return true;
    }

    @Override
    protected void prepareBarHighlight(float f, float f2, float f3, float f4, Transformer transformer) {
        this.mBarRect.set(f2, f - f4, f3, f + f4);
        transformer.rectToPixelPhaseHorizontal(this.mBarRect, this.mAnimator.getPhaseY());
    }

    @Override
    protected void setHighlightDrawPos(Highlight highlight, RectF rectF) {
        highlight.setDraw(rectF.centerY(), rectF.right);
    }
}

