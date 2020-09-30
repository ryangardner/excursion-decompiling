/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.Layout
 *  android.text.Layout$Alignment
 *  android.text.StaticLayout
 *  android.text.TextPaint
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

public class PieChartRenderer
extends DataRenderer {
    protected Canvas mBitmapCanvas;
    private RectF mCenterTextLastBounds = new RectF();
    private CharSequence mCenterTextLastValue;
    private StaticLayout mCenterTextLayout;
    private TextPaint mCenterTextPaint;
    protected PieChart mChart;
    protected WeakReference<Bitmap> mDrawBitmap;
    protected Path mDrawCenterTextPathBuffer = new Path();
    protected RectF mDrawHighlightedRectF = new RectF();
    private Paint mEntryLabelsPaint;
    private Path mHoleCirclePath = new Path();
    protected Paint mHolePaint;
    private RectF mInnerRectBuffer = new RectF();
    private Path mPathBuffer = new Path();
    private RectF[] mRectBuffer = new RectF[]{new RectF(), new RectF(), new RectF()};
    protected Paint mTransparentCirclePaint;
    protected Paint mValueLinePaint;

    public PieChartRenderer(PieChart pieChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = pieChart;
        pieChart = new Paint(1);
        this.mHolePaint = pieChart;
        pieChart.setColor(-1);
        this.mHolePaint.setStyle(Paint.Style.FILL);
        pieChart = new Paint(1);
        this.mTransparentCirclePaint = pieChart;
        pieChart.setColor(-1);
        this.mTransparentCirclePaint.setStyle(Paint.Style.FILL);
        this.mTransparentCirclePaint.setAlpha(105);
        pieChart = new TextPaint(1);
        this.mCenterTextPaint = pieChart;
        pieChart.setColor(-16777216);
        this.mCenterTextPaint.setTextSize(Utils.convertDpToPixel(12.0f));
        this.mValuePaint.setTextSize(Utils.convertDpToPixel(13.0f));
        this.mValuePaint.setColor(-1);
        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
        pieChart = new Paint(1);
        this.mEntryLabelsPaint = pieChart;
        pieChart.setColor(-1);
        this.mEntryLabelsPaint.setTextAlign(Paint.Align.CENTER);
        this.mEntryLabelsPaint.setTextSize(Utils.convertDpToPixel(13.0f));
        pieChart = new Paint(1);
        this.mValueLinePaint = pieChart;
        pieChart.setStyle(Paint.Style.STROKE);
    }

    protected float calculateMinimumRadiusForSpacedSlice(MPPointF mPPointF, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f6 / 2.0f;
        float f8 = mPPointF.x;
        double d = (f5 + f6) * 0.017453292f;
        f8 += (float)Math.cos(d) * f;
        f6 = mPPointF.y + (float)Math.sin(d) * f;
        float f9 = mPPointF.x;
        d = (f5 + f7) * 0.017453292f;
        float f10 = (float)Math.cos(d);
        f7 = mPPointF.y;
        f5 = (float)Math.sin(d);
        return (float)((double)(f - (float)(Math.sqrt(Math.pow(f8 - f3, 2.0) + Math.pow(f6 - f4, 2.0)) / 2.0 * Math.tan((180.0 - (double)f2) / 2.0 * 0.017453292519943295))) - Math.sqrt(Math.pow(f9 + f10 * f - (f8 + f3) / 2.0f, 2.0) + Math.pow(f7 + f5 * f - (f6 + f4) / 2.0f, 2.0)));
    }

    protected void drawCenterText(Canvas canvas) {
        CharSequence charSequence = this.mChart.getCenterText();
        if (!this.mChart.isDrawCenterTextEnabled()) return;
        if (charSequence == null) return;
        MPPointF mPPointF = this.mChart.getCenterCircleBox();
        MPPointF mPPointF2 = this.mChart.getCenterTextOffset();
        float f = mPPointF.x + mPPointF2.x;
        float f2 = mPPointF.y + mPPointF2.y;
        float f3 = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled() ? this.mChart.getRadius() * (this.mChart.getHoleRadius() / 100.0f) : this.mChart.getRadius();
        RectF rectF = this.mRectBuffer[0];
        rectF.left = f - f3;
        rectF.top = f2 - f3;
        rectF.right = f + f3;
        rectF.bottom = f2 + f3;
        RectF rectF2 = this.mRectBuffer[1];
        rectF2.set(rectF);
        f3 = this.mChart.getCenterTextRadiusPercent() / 100.0f;
        if ((double)f3 > 0.0) {
            rectF2.inset((rectF2.width() - rectF2.width() * f3) / 2.0f, (rectF2.height() - rectF2.height() * f3) / 2.0f);
        }
        if (!charSequence.equals(this.mCenterTextLastValue) || !rectF2.equals((Object)this.mCenterTextLastBounds)) {
            this.mCenterTextLastBounds.set(rectF2);
            this.mCenterTextLastValue = charSequence;
            f3 = this.mCenterTextLastBounds.width();
            this.mCenterTextLayout = new StaticLayout(charSequence, 0, charSequence.length(), this.mCenterTextPaint, (int)Math.max(Math.ceil(f3), 1.0), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        }
        f3 = this.mCenterTextLayout.getHeight();
        canvas.save();
        if (Build.VERSION.SDK_INT >= 18) {
            charSequence = this.mDrawCenterTextPathBuffer;
            charSequence.reset();
            charSequence.addOval(rectF, Path.Direction.CW);
            canvas.clipPath((Path)charSequence);
        }
        canvas.translate(rectF2.left, rectF2.top + (rectF2.height() - f3) / 2.0f);
        this.mCenterTextLayout.draw(canvas);
        canvas.restore();
        MPPointF.recycleInstance(mPPointF);
        MPPointF.recycleInstance(mPPointF2);
    }

    @Override
    public void drawData(Canvas canvas) {
        Object object;
        Object object2;
        block4 : {
            int n;
            int n2;
            block3 : {
                n = (int)this.mViewPortHandler.getChartWidth();
                n2 = (int)this.mViewPortHandler.getChartHeight();
                object = this.mDrawBitmap;
                object = object == null ? null : (Bitmap)((Reference)object).get();
                if (object == null || object.getWidth() != n) break block3;
                object2 = object;
                if (object.getHeight() == n2) break block4;
            }
            if (n <= 0) return;
            if (n2 <= 0) return;
            object2 = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_4444);
            this.mDrawBitmap = new WeakReference<Bitmap>((Bitmap)object2);
            this.mBitmapCanvas = new Canvas((Bitmap)object2);
        }
        object2.eraseColor(0);
        object = ((PieData)this.mChart.getData()).getDataSets().iterator();
        while (object.hasNext()) {
            object2 = (IPieDataSet)object.next();
            if (!object2.isVisible() || object2.getEntryCount() <= 0) continue;
            this.drawDataSet(canvas, (IPieDataSet)object2);
        }
    }

    /*
     * Unable to fully structure code
     */
    protected void drawDataSet(Canvas var1_1, IPieDataSet var2_2) {
        var3_14 = this;
        var4_15 = var2_13;
        var5_16 = var3_14.mChart.getRotationAngle();
        var6_17 = var3_14.mAnimator.getPhaseX();
        var7_18 = var3_14.mAnimator.getPhaseY();
        var8_19 = var3_14.mChart.getCircleBox();
        var9_20 = var2_13.getEntryCount();
        var10_21 = var3_14.mChart.getDrawAngles();
        var1_2 = var3_14.mChart.getCenterCircleBox();
        var11_22 = var3_14.mChart.getRadius();
        var12_23 = var3_14.mChart.isDrawHoleEnabled() != false && var3_14.mChart.isDrawSlicesUnderHoleEnabled() == false;
        var13_24 = var12_23 != false ? var3_14.mChart.getHoleRadius() / 100.0f * var11_22 : 0.0f;
        var14_25 = (var11_22 - var3_14.mChart.getHoleRadius() * var11_22 / 100.0f) / 2.0f;
        var15_26 = new RectF();
        var16_35 = var12_23 != false && var3_14.mChart.isDrawRoundedSlicesEnabled() != false;
        var18_37 = 0;
        for (var17_36 = 0; var17_36 < var9_20; ++var17_36) {
            var19_38 = var18_37;
            if (Math.abs(((PieEntry)var4_15.getEntryForIndex(var17_36)).getY()) > Utils.FLOAT_EPSILON) {
                var19_38 = var18_37 + 1;
            }
            var18_37 = var19_38;
        }
        var20_39 = var18_37 <= 1 ? 0.0f : var3_14.getSliceSpace((IPieDataSet)var4_15);
        var19_38 = 0;
        var21_40 = 0.0f;
        var17_36 = var9_20;
        do {
            block22 : {
                block23 : {
                    block21 : {
                        var4_15 = var2_13;
                        if (var19_38 >= var17_36) {
                            MPPointF.recycleInstance((MPPointF)var1_3);
                            return;
                        }
                        var22_41 = var10_21[var19_38];
                        if (Math.abs(var4_15.getEntryForIndex(var19_38).getY()) > Utils.FLOAT_EPSILON && (!var3_14.mChart.needsHighlight(var19_38) || var16_35)) break block21;
                        var23_42 = var21_40 + var22_41 * var6_17;
                        var4_15 = var15_27;
                        var15_28 = var1_3;
                        var1_4 = var4_15;
                        break block22;
                    }
                    var9_20 = var20_39 > 0.0f && var22_41 <= 180.0f ? 1 : 0;
                    var3_14.mRenderPaint.setColor(var4_15.getColor(var19_38));
                    var23_42 = var18_37 == 1 ? 0.0f : var20_39 / (var11_22 * 0.017453292f);
                    var24_43 = var5_16 + (var21_40 + var23_42 / 2.0f) * var7_18;
                    var23_42 = var25_44 = (var22_41 - var23_42) * var7_18;
                    if (var25_44 < 0.0f) {
                        var23_42 = 0.0f;
                    }
                    var3_14.mPathBuffer.reset();
                    if (var16_35) {
                        var25_44 = var1_3.x;
                        var26_45 = var11_22 - var14_25;
                        var27_46 = var24_43 * 0.017453292f;
                        var25_44 += (float)Math.cos(var27_46) * var26_45;
                        var26_45 = var1_3.y + var26_45 * (float)Math.sin(var27_46);
                        var15_27.set(var25_44 - var14_25, var26_45 - var14_25, var25_44 + var14_25, var26_45 + var14_25);
                    }
                    var25_44 = var1_3.x;
                    var27_46 = var24_43 * 0.017453292f;
                    var29_47 = var25_44 + (float)Math.cos(var27_46) * var11_22;
                    var26_45 = var1_3.y + (float)Math.sin(var27_46) * var11_22;
                    var30_48 = var23_42 FCMPL 360.0f;
                    if (var30_48 >= 0 && var23_42 % 360.0f <= Utils.FLOAT_EPSILON) {
                        var3_14.mPathBuffer.addCircle(var1_3.x, var1_3.y, var11_22, Path.Direction.CW);
                    } else {
                        if (var16_35) {
                            var3_14.mPathBuffer.arcTo((RectF)var15_27, var24_43 + 180.0f, -180.0f);
                        }
                        var3_14.mPathBuffer.arcTo(var8_19, var24_43, var23_42);
                    }
                    var3_14.mInnerRectBuffer.set(var1_3.x - var13_24, var1_3.y - var13_24, var1_3.x + var13_24, var1_3.y + var13_24);
                    if (!var12_23 || !(var13_24 > 0.0f) && var9_20 == 0) break block23;
                    if (var9_20 != 0) {
                        var25_44 = var24_43 = this.calculateMinimumRadiusForSpacedSlice((MPPointF)var1_3, var11_22, var22_41 * var7_18, var29_47, var26_45, var24_43, var23_42);
                        if (var24_43 < 0.0f) {
                            var25_44 = -var24_43;
                        }
                        var25_44 = Math.max(var13_24, var25_44);
                    } else {
                        var25_44 = var13_24;
                    }
                    var24_43 = var25_44;
                    var25_44 = var18_37 != 1 && var24_43 != 0.0f ? var20_39 / (var24_43 * 0.017453292f) : 0.0f;
                    var29_47 = var25_44 / 2.0f;
                    var25_44 = var26_45 = (var22_41 - var25_44) * var7_18;
                    if (var26_45 < 0.0f) {
                        var25_44 = 0.0f;
                    }
                    var26_45 = var5_16 + (var21_40 + var29_47) * var7_18 + var25_44;
                    if (var30_48 >= 0 && var23_42 % 360.0f <= Utils.FLOAT_EPSILON) {
                        this.mPathBuffer.addCircle(var1_3.x, var1_3.y, var24_43, Path.Direction.CCW);
                    } else {
                        var3_14 = this;
                        if (var16_35) {
                            var24_43 = var1_3.x;
                            var23_42 = var11_22 - var14_25;
                            var27_46 = var26_45 * 0.017453292f;
                            var24_43 += (float)Math.cos(var27_46) * var23_42;
                            var23_42 = var1_3.y + var23_42 * (float)Math.sin(var27_46);
                            var4_15 = var15_27;
                            var4_15.set(var24_43 - var14_25, var23_42 - var14_25, var24_43 + var14_25, var23_42 + var14_25);
                            var3_14.mPathBuffer.arcTo((RectF)var4_15, var26_45, 180.0f);
                        } else {
                            var4_15 = var3_14.mPathBuffer;
                            var23_42 = var1_3.x;
                            var27_46 = var26_45 * 0.017453292f;
                            var4_15.lineTo(var23_42 + (float)Math.cos(var27_46) * var24_43, var1_3.y + var24_43 * (float)Math.sin(var27_46));
                        }
                        var3_14.mPathBuffer.arcTo(var3_14.mInnerRectBuffer, var26_45, -var25_44);
                    }
                    var31_49 = this;
                    var32_50 = var1_3;
                    var1_5 = var15_27;
                    ** GOTO lbl-1000
                }
                var4_15 = var1_3;
                var1_6 = var15_27;
                var31_49 = var3_14;
                var32_50 = var4_15;
                if (var23_42 % 360.0f > Utils.FLOAT_EPSILON) {
                    if (var9_20 != 0) {
                        var25_44 = var23_42 / 2.0f;
                        var1_7 = var15_27;
                        var23_42 = this.calculateMinimumRadiusForSpacedSlice((MPPointF)var4_15, var11_22, var22_41 * var7_18, var29_47, var26_45, var24_43, var23_42);
                        var26_45 = var4_15.x;
                        var27_46 = (var24_43 + var25_44) * 0.017453292f;
                        var24_43 = (float)Math.cos(var27_46);
                        var29_47 = var4_15.y;
                        var25_44 = (float)Math.sin(var27_46);
                        var3_14.mPathBuffer.lineTo(var26_45 + var24_43 * var23_42, var29_47 + var23_42 * var25_44);
                        var15_29 = var4_15;
                    } else {
                        var1_8 = var15_27;
                        var3_14.mPathBuffer.lineTo(var4_15.x, var4_15.y);
                        var15_30 = var4_15;
                    }
                } else lbl-1000: // 2 sources:
                {
                    var15_31 = var32_50;
                    var3_14 = var31_49;
                }
                var3_14.mPathBuffer.close();
                var3_14.mBitmapCanvas.drawPath(var3_14.mPathBuffer, var3_14.mRenderPaint);
                var23_42 = var21_40 + var22_41 * var6_17;
            }
            ++var19_38;
            var4_15 = var15_33;
            var15_34 = var1_11;
            var1_12 = var4_15;
            var21_40 = var23_42;
        } while (true);
    }

    protected void drawEntryLabel(Canvas canvas, String string2, float f, float f2) {
        canvas.drawText(string2, f, f2, this.mEntryLabelsPaint);
    }

    @Override
    public void drawExtras(Canvas canvas) {
        this.drawHole(canvas);
        canvas.drawBitmap((Bitmap)this.mDrawBitmap.get(), 0.0f, 0.0f, null);
        this.drawCenterText(canvas);
    }

    @Override
    public void drawHighlighted(Canvas arrf, Highlight[] arrhighlight) {
        boolean bl = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled();
        if (bl && this.mChart.isDrawRoundedSlicesEnabled()) {
            return;
        }
        float f = this.mAnimator.getPhaseX();
        float f2 = this.mAnimator.getPhaseY();
        float f3 = this.mChart.getRotationAngle();
        arrf = this.mChart.getDrawAngles();
        float[] arrf2 = this.mChart.getAbsoluteAngles();
        MPPointF mPPointF = this.mChart.getCenterCircleBox();
        float f4 = this.mChart.getRadius();
        float f5 = bl ? this.mChart.getHoleRadius() / 100.0f * f4 : 0.0f;
        RectF rectF = this.mDrawHighlightedRectF;
        rectF.set(0.0f, 0.0f, 0.0f, 0.0f);
        int n = 0;
        do {
            Object object;
            if (n >= ((Highlight[])(object = arrhighlight)).length) {
                MPPointF.recycleInstance(mPPointF);
                return;
            }
            int n2 = (int)object[n].getX();
            if (n2 < arrf.length && (object = ((PieData)this.mChart.getData()).getDataSetByIndex(object[n].getDataSetIndex())) != null && object.isHighlightEnabled()) {
                float f6;
                int n3;
                double d;
                int n4;
                int n5 = object.getEntryCount();
                int n6 = 0;
                for (n4 = 0; n4 < n5; ++n4) {
                    n3 = n6;
                    if (Math.abs(((PieEntry)object.getEntryForIndex(n4)).getY()) > Utils.FLOAT_EPSILON) {
                        n3 = n6 + 1;
                    }
                    n6 = n3;
                }
                float f7 = n2 == 0 ? 0.0f : arrf2[n2 - 1] * f;
                float f8 = n6 <= 1 ? 0.0f : object.getSliceSpace();
                float f9 = arrf[n2];
                float f10 = object.getSelectionShift();
                float f11 = f4 + f10;
                rectF.set(this.mChart.getCircleBox());
                f10 = -f10;
                rectF.inset(f10, f10);
                n4 = f8 > 0.0f && f9 <= 180.0f ? 1 : 0;
                this.mRenderPaint.setColor(object.getColor(n2));
                float f12 = n6 == 1 ? 0.0f : f8 / (f4 * 0.017453292f);
                f10 = n6 == 1 ? 0.0f : f8 / (f11 * 0.017453292f);
                float f13 = f3 + (f12 / 2.0f + f7) * f2;
                f12 = (f9 - f12) * f2;
                if (f12 < 0.0f) {
                    f12 = 0.0f;
                }
                float f14 = (f10 / 2.0f + f7) * f2 + f3;
                f10 = f6 = (f9 - f10) * f2;
                if (f6 < 0.0f) {
                    f10 = 0.0f;
                }
                this.mPathBuffer.reset();
                n3 = (int)(f12 FCMPL 360.0f);
                if (n3 >= 0 && f12 % 360.0f <= Utils.FLOAT_EPSILON) {
                    this.mPathBuffer.addCircle(mPPointF.x, mPPointF.y, f11, Path.Direction.CW);
                } else {
                    object = this.mPathBuffer;
                    f6 = mPPointF.x;
                    d = f14 * 0.017453292f;
                    object.moveTo(f6 + (float)Math.cos(d) * f11, mPPointF.y + f11 * (float)Math.sin(d));
                    this.mPathBuffer.arcTo(rectF, f14, f10);
                }
                if (n4 != 0) {
                    f10 = mPPointF.x;
                    d = f13 * 0.017453292f;
                    f10 = this.calculateMinimumRadiusForSpacedSlice(mPPointF, f4, f9 * f2, (float)Math.cos(d) * f4 + f10, mPPointF.y + (float)Math.sin(d) * f4, f13, f12);
                } else {
                    f10 = 0.0f;
                }
                object = mPPointF;
                f6 = f5;
                this.mInnerRectBuffer.set(((MPPointF)object).x - f6, ((MPPointF)object).y - f6, ((MPPointF)object).x + f6, ((MPPointF)object).y + f6);
                if (bl && (f6 > 0.0f || n4 != 0)) {
                    if (n4 != 0) {
                        f13 = f10;
                        if (f10 < 0.0f) {
                            f13 = -f10;
                        }
                        f10 = Math.max(f6, f13);
                    } else {
                        f10 = f6;
                    }
                    f8 = n6 != 1 && f10 != 0.0f ? (f8 /= f10 * 0.017453292f) : 0.0f;
                    f13 = f8 / 2.0f;
                    f8 = f6 = (f9 - f8) * f2;
                    if (f6 < 0.0f) {
                        f8 = 0.0f;
                    }
                    f7 = (f7 + f13) * f2 + f3 + f8;
                    if (n3 >= 0 && f12 % 360.0f <= Utils.FLOAT_EPSILON) {
                        this.mPathBuffer.addCircle(((MPPointF)object).x, ((MPPointF)object).y, f10, Path.Direction.CCW);
                    } else {
                        Path path = this.mPathBuffer;
                        f12 = ((MPPointF)object).x;
                        d = f7 * 0.017453292f;
                        path.lineTo(f12 + (float)Math.cos(d) * f10, ((MPPointF)object).y + f10 * (float)Math.sin(d));
                        this.mPathBuffer.arcTo(this.mInnerRectBuffer, f7, -f8);
                    }
                } else if (f12 % 360.0f > Utils.FLOAT_EPSILON) {
                    if (n4 != 0) {
                        f8 = f12 / 2.0f;
                        f7 = ((MPPointF)object).x;
                        d = (f13 + f8) * 0.017453292f;
                        f12 = (float)Math.cos(d);
                        f8 = ((MPPointF)object).y;
                        f6 = (float)Math.sin(d);
                        this.mPathBuffer.lineTo(f7 + f12 * f10, f8 + f10 * f6);
                    } else {
                        this.mPathBuffer.lineTo(((MPPointF)object).x, ((MPPointF)object).y);
                    }
                }
                this.mPathBuffer.close();
                this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
            }
            ++n;
        } while (true);
    }

    protected void drawHole(Canvas object) {
        if (!this.mChart.isDrawHoleEnabled()) return;
        if (this.mBitmapCanvas == null) return;
        float f = this.mChart.getRadius();
        float f2 = this.mChart.getHoleRadius() / 100.0f * f;
        object = this.mChart.getCenterCircleBox();
        if (Color.alpha((int)this.mHolePaint.getColor()) > 0) {
            this.mBitmapCanvas.drawCircle(object.x, object.y, f2, this.mHolePaint);
        }
        if (Color.alpha((int)this.mTransparentCirclePaint.getColor()) > 0 && this.mChart.getTransparentCircleRadius() > this.mChart.getHoleRadius()) {
            int n = this.mTransparentCirclePaint.getAlpha();
            float f3 = this.mChart.getTransparentCircleRadius() / 100.0f;
            this.mTransparentCirclePaint.setAlpha((int)((float)n * this.mAnimator.getPhaseX() * this.mAnimator.getPhaseY()));
            this.mHoleCirclePath.reset();
            this.mHoleCirclePath.addCircle(object.x, object.y, f * f3, Path.Direction.CW);
            this.mHoleCirclePath.addCircle(object.x, object.y, f2, Path.Direction.CCW);
            this.mBitmapCanvas.drawPath(this.mHoleCirclePath, this.mTransparentCirclePaint);
            this.mTransparentCirclePaint.setAlpha(n);
        }
        MPPointF.recycleInstance((MPPointF)object);
    }

    protected void drawRoundedSlices(Canvas arrf) {
        if (!this.mChart.isDrawRoundedSlicesEnabled()) {
            return;
        }
        IPieDataSet iPieDataSet = ((PieData)this.mChart.getData()).getDataSet();
        if (!iPieDataSet.isVisible()) {
            return;
        }
        float f = this.mAnimator.getPhaseX();
        float f2 = this.mAnimator.getPhaseY();
        MPPointF mPPointF = this.mChart.getCenterCircleBox();
        float f3 = this.mChart.getRadius();
        float f4 = (f3 - this.mChart.getHoleRadius() * f3 / 100.0f) / 2.0f;
        arrf = this.mChart.getDrawAngles();
        float f5 = this.mChart.getRotationAngle();
        int n = 0;
        do {
            if (n >= iPieDataSet.getEntryCount()) {
                MPPointF.recycleInstance(mPPointF);
                return;
            }
            float f6 = arrf[n];
            if (Math.abs(((BaseEntry)iPieDataSet.getEntryForIndex(n)).getY()) > Utils.FLOAT_EPSILON) {
                double d = f3 - f4;
                double d2 = (f5 + f6) * f2;
                double d3 = Math.cos(Math.toRadians(d2));
                float f7 = (float)((double)mPPointF.x + d3 * d);
                float f8 = (float)(d * Math.sin(Math.toRadians(d2)) + (double)mPPointF.y);
                this.mRenderPaint.setColor(iPieDataSet.getColor(n));
                this.mBitmapCanvas.drawCircle(f7, f8, f4, this.mRenderPaint);
            }
            f5 += f6 * f;
            ++n;
        } while (true);
    }

    @Override
    public void drawValue(Canvas canvas, String string2, float f, float f2, int n) {
        this.mValuePaint.setColor(n);
        canvas.drawText(string2, f, f2, this.mValuePaint);
    }

    @Override
    public void drawValues(Canvas arrf) {
        float f;
        float[] arrf2 = arrf;
        float[] arrf3 = this.mChart.getCenterCircleBox();
        float f2 = this.mChart.getRadius();
        float f3 = this.mChart.getRotationAngle();
        Object[] arrobject = this.mChart.getDrawAngles();
        Object object = this.mChart.getAbsoluteAngles();
        float f4 = this.mAnimator.getPhaseX();
        float f5 = this.mAnimator.getPhaseY();
        float f6 = (f2 - this.mChart.getHoleRadius() * f2 / 100.0f) / 2.0f;
        float f7 = this.mChart.getHoleRadius() / 100.0f;
        float f8 = f2 / 10.0f * 3.6f;
        float f9 = f3;
        if (this.mChart.isDrawHoleEnabled()) {
            f = (f2 - f2 * f7) / 2.0f;
            f9 = f3;
            f8 = f;
            if (!this.mChart.isDrawSlicesUnderHoleEnabled()) {
                f9 = f3;
                f8 = f;
                if (this.mChart.isDrawRoundedSlicesEnabled()) {
                    f9 = (float)((double)f3 + (double)(f6 * 360.0f) / ((double)f2 * 6.283185307179586));
                    f8 = f;
                }
            }
        }
        f = f9;
        f9 = f2 - f8;
        PieData pieData = (PieData)this.mChart.getData();
        List list = pieData.getDataSets();
        float f10 = pieData.getYValueSum();
        boolean bl = this.mChart.isDrawEntryLabelsEnabled();
        arrf.save();
        float f11 = Utils.convertDpToPixel(5.0f);
        int n = 0;
        int n2 = 0;
        f8 = f5;
        do {
            Object object2;
            Object object3;
            if (n2 >= list.size()) {
                MPPointF.recycleInstance((MPPointF)arrf3);
                arrf.restore();
                return;
            }
            Object object4 = (IPieDataSet)list.get(n2);
            boolean bl2 = object4.isDrawValuesEnabled();
            if (!bl2 && !bl) {
                f5 = f2;
                object2 = arrobject;
                object3 = object;
                f2 = f4;
                f3 = f8;
                f8 = f;
                arrobject = arrf3;
                object = object2;
                f4 = f9;
                arrf3 = object3;
                f = f3;
                f9 = f5;
            } else {
                PieDataSet.ValuePosition valuePosition = object4.getXValuePosition();
                PieDataSet.ValuePosition valuePosition2 = object4.getYValuePosition();
                this.applyValueTextStyle((IDataSet)object4);
                float f12 = (float)Utils.calcTextHeight(this.mValuePaint, "Q") + Utils.convertDpToPixel(4.0f);
                object2 = object4.getValueFormatter();
                int n3 = object4.getEntryCount();
                this.mValueLinePaint.setColor(object4.getValueLineColor());
                this.mValueLinePaint.setStrokeWidth(Utils.convertDpToPixel(object4.getValueLineWidth()));
                f5 = this.getSliceSpace((IPieDataSet)object4);
                object3 = MPPointF.getInstance(object4.getIconsOffset());
                object3.x = Utils.convertDpToPixel(object3.x);
                object3.y = Utils.convertDpToPixel(object3.y);
                arrf2 = object;
                object = object4;
                for (int i = 0; i < n3; ++n, ++i) {
                    float[] arrf4;
                    float f13;
                    Object object5;
                    float f14;
                    PieEntry pieEntry = (PieEntry)object.getEntryForIndex(i);
                    f3 = n == 0 ? 0.0f : arrf2[n - 1] * f4;
                    float f15 = f + (f3 + (arrobject[n] - f5 / (f9 * 0.017453292f) / 2.0f) / 2.0f) * f8;
                    f3 = this.mChart.isUsePercentValuesEnabled() ? pieEntry.getY() / f10 * 100.0f : pieEntry.getY();
                    String string2 = ((ValueFormatter)object2).getPieLabel(f3, pieEntry);
                    object4 = pieEntry.getLabel();
                    double d = f15 * 0.017453292f;
                    float f16 = (float)Math.cos(d);
                    float f17 = (float)Math.sin(d);
                    boolean bl3 = bl && valuePosition == PieDataSet.ValuePosition.OUTSIDE_SLICE;
                    boolean bl4 = bl2 && valuePosition2 == PieDataSet.ValuePosition.OUTSIDE_SLICE;
                    boolean bl5 = bl && valuePosition == PieDataSet.ValuePosition.INSIDE_SLICE;
                    boolean bl6 = bl2 && valuePosition2 == PieDataSet.ValuePosition.INSIDE_SLICE;
                    if (bl3 || bl4) {
                        float f18 = object.getValueLinePart1Length();
                        f6 = object.getValueLinePart2Length();
                        f3 = object.getValueLinePart1OffsetPercentage() / 100.0f;
                        if (this.mChart.isDrawHoleEnabled()) {
                            f13 = f2 * f7;
                            f3 = (f2 - f13) * f3 + f13;
                        } else {
                            f3 = f2 * f3;
                        }
                        f6 = object.isValueLineVariableLength() ? f6 * f9 * (float)Math.abs(Math.sin(d)) : (f6 *= f9);
                        f14 = arrf3.x;
                        f13 = arrf3.y;
                        float f19 = (f18 + 1.0f) * f9;
                        f18 = f19 * f16 + arrf3.x;
                        f19 = arrf3.y + f19 * f17;
                        d = (double)f15 % 360.0;
                        if (d >= 90.0 && d <= 270.0) {
                            f15 = f18 - f6;
                            this.mValuePaint.setTextAlign(Paint.Align.RIGHT);
                            if (bl3) {
                                this.mEntryLabelsPaint.setTextAlign(Paint.Align.RIGHT);
                            }
                            f6 = f15;
                            f15 -= f11;
                        } else {
                            f6 = f18 + f6;
                            this.mValuePaint.setTextAlign(Paint.Align.LEFT);
                            if (bl3) {
                                this.mEntryLabelsPaint.setTextAlign(Paint.Align.LEFT);
                            }
                            f15 = f6 + f11;
                        }
                        if (object.getValueLineColor() != 1122867) {
                            if (object.isUsingSliceColorAsValueLineColor()) {
                                this.mValueLinePaint.setColor(object.getColor(i));
                            }
                            arrf.drawLine(f3 * f16 + f14, f3 * f17 + f13, f18, f19, this.mValueLinePaint);
                            arrf.drawLine(f18, f19, f6, f19, this.mValueLinePaint);
                        }
                        object5 = object;
                        if (bl3 && bl4) {
                            this.drawValue((Canvas)arrf, string2, f15, f19, object5.getValueTextColor(i));
                            if (i < pieData.getEntryCount() && object4 != null) {
                                this.drawEntryLabel((Canvas)arrf, (String)object4, f15, f19 + f12);
                            }
                        } else {
                            arrf4 = object4;
                            if (bl3) {
                                if (i < pieData.getEntryCount() && arrf4 != null) {
                                    this.drawEntryLabel((Canvas)arrf, (String)arrf4, f15, f19 + f12 / 2.0f);
                                }
                            } else if (bl4) {
                                this.drawValue((Canvas)arrf, string2, f15, f19 + f12 / 2.0f, object5.getValueTextColor(i));
                            }
                        }
                    }
                    object5 = arrf;
                    if (bl5 || bl6) {
                        arrf4 = arrf3;
                        f3 = f9 * f16 + arrf4.x;
                        f6 = f9 * f17 + arrf4.y;
                        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
                        if (bl5 && bl6) {
                            this.drawValue((Canvas)arrf, string2, f3, f6, object.getValueTextColor(i));
                            if (i < pieData.getEntryCount() && object4 != null) {
                                this.drawEntryLabel((Canvas)object5, (String)object4, f3, f6 + f12);
                            }
                        } else if (bl5) {
                            if (i < pieData.getEntryCount() && object4 != null) {
                                this.drawEntryLabel((Canvas)object5, (String)object4, f3, f6 + f12 / 2.0f);
                            }
                        } else if (bl6) {
                            this.drawValue((Canvas)arrf, string2, f3, f6 + f12 / 2.0f, object.getValueTextColor(i));
                        }
                    }
                    if (pieEntry.getIcon() == null || !object.isDrawIconsEnabled()) continue;
                    pieEntry = pieEntry.getIcon();
                    object4 = object3;
                    f6 = object4.y;
                    f13 = arrf3.x;
                    f15 = object4.y;
                    f3 = arrf3.y;
                    f14 = object4.x;
                    Utils.drawImage((Canvas)arrf, (Drawable)pieEntry, (int)((f9 + f6) * f16 + f13), (int)((f9 + f15) * f17 + f3 + f14), pieEntry.getIntrinsicWidth(), pieEntry.getIntrinsicHeight());
                }
                f5 = f2;
                object = arrobject;
                object2 = arrf2;
                f2 = f4;
                f3 = f8;
                f8 = f;
                f4 = f9;
                arrobject = arrf3;
                arrf2 = arrf;
                MPPointF.recycleInstance((MPPointF)object3);
                f9 = f5;
                f = f3;
                arrf3 = object2;
            }
            ++n2;
            object3 = object;
            f5 = f4;
            object = arrf3;
            f4 = f2;
            f3 = f;
            f = f8;
            arrf3 = arrobject;
            f2 = f9;
            arrobject = object3;
            f8 = f3;
            f9 = f5;
        } while (true);
    }

    public TextPaint getPaintCenterText() {
        return this.mCenterTextPaint;
    }

    public Paint getPaintEntryLabels() {
        return this.mEntryLabelsPaint;
    }

    public Paint getPaintHole() {
        return this.mHolePaint;
    }

    public Paint getPaintTransparentCircle() {
        return this.mTransparentCirclePaint;
    }

    protected float getSliceSpace(IPieDataSet iPieDataSet) {
        if (!iPieDataSet.isAutomaticallyDisableSliceSpacingEnabled()) {
            return iPieDataSet.getSliceSpace();
        }
        if (!(iPieDataSet.getSliceSpace() / this.mViewPortHandler.getSmallestContentExtension() > iPieDataSet.getYMin() / ((PieData)this.mChart.getData()).getYValueSum() * 2.0f)) return iPieDataSet.getSliceSpace();
        return 0.0f;
    }

    @Override
    public void initBuffers() {
    }

    public void releaseBitmap() {
        Object object = this.mBitmapCanvas;
        if (object != null) {
            object.setBitmap(null);
            this.mBitmapCanvas = null;
        }
        if ((object = this.mDrawBitmap) == null) return;
        if ((object = (Bitmap)((Reference)object).get()) != null) {
            object.recycle();
        }
        this.mDrawBitmap.clear();
        this.mDrawBitmap = null;
    }
}

