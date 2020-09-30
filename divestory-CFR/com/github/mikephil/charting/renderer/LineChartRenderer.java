/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.PathEffect
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.renderer.LineRadarRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LineChartRenderer
extends LineRadarRenderer {
    protected Path cubicFillPath = new Path();
    protected Path cubicPath = new Path();
    protected Canvas mBitmapCanvas;
    protected Bitmap.Config mBitmapConfig = Bitmap.Config.ARGB_8888;
    protected LineDataProvider mChart;
    protected Paint mCirclePaintInner;
    private float[] mCirclesBuffer = new float[2];
    protected WeakReference<Bitmap> mDrawBitmap;
    protected Path mGenerateFilledPathBuffer = new Path();
    private HashMap<IDataSet, DataSetImageCache> mImageCaches = new HashMap();
    private float[] mLineBuffer = new float[4];

    public LineChartRenderer(LineDataProvider lineDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = lineDataProvider;
        lineDataProvider = new Paint(1);
        this.mCirclePaintInner = lineDataProvider;
        lineDataProvider.setStyle(Paint.Style.FILL);
        this.mCirclePaintInner.setColor(-1);
    }

    private void generateFilledPath(ILineDataSet iLineDataSet, int n, int n2, Path path) {
        float f = iLineDataSet.getFillFormatter().getFillLinePosition(iLineDataSet, this.mChart);
        float f2 = this.mAnimator.getPhaseY();
        boolean bl = iLineDataSet.getMode() == LineDataSet.Mode.STEPPED;
        path.reset();
        Object object = iLineDataSet.getEntryForIndex(n);
        path.moveTo(((Entry)object).getX(), f);
        path.lineTo(((Entry)object).getX(), ((BaseEntry)object).getY() * f2);
        Entry entry = null;
        ++n;
        while (n <= n2) {
            entry = (Entry)iLineDataSet.getEntryForIndex(n);
            if (bl) {
                path.lineTo(entry.getX(), ((BaseEntry)object).getY() * f2);
            }
            path.lineTo(entry.getX(), entry.getY() * f2);
            ++n;
            object = entry;
        }
        if (entry != null) {
            path.lineTo(entry.getX(), f);
        }
        path.close();
    }

    protected void drawCircles(Canvas canvas) {
        this.mRenderPaint.setStyle(Paint.Style.FILL);
        float f = this.mAnimator.getPhaseY();
        Object object = this.mCirclesBuffer;
        object[0] = 0.0f;
        object[1] = 0.0f;
        List list = this.mChart.getLineData().getDataSets();
        int n = 0;
        while (n < list.size()) {
            ILineDataSet iLineDataSet = (ILineDataSet)list.get(n);
            if (iLineDataSet.isVisible() && iLineDataSet.isDrawCirclesEnabled() && iLineDataSet.getEntryCount() != 0) {
                Object object2;
                this.mCirclePaintInner.setColor(iLineDataSet.getCircleHoleColor());
                Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
                this.mXBounds.set(this.mChart, iLineDataSet);
                float f2 = iLineDataSet.getCircleRadius();
                float f3 = iLineDataSet.getCircleHoleRadius();
                boolean bl = iLineDataSet.isDrawCircleHoleEnabled() && f3 < f2 && f3 > 0.0f;
                boolean bl2 = bl && iLineDataSet.getCircleHoleColor() == 1122867;
                if (this.mImageCaches.containsKey(iLineDataSet)) {
                    object = this.mImageCaches.get(iLineDataSet);
                } else {
                    object = new DataSetImageCache();
                    this.mImageCaches.put(iLineDataSet, (DataSetImageCache)object);
                }
                if (((DataSetImageCache)object).init(iLineDataSet)) {
                    ((DataSetImageCache)object).fill(iLineDataSet, bl, bl2);
                }
                int n2 = this.mXBounds.range;
                int n3 = this.mXBounds.min;
                for (int i = this.mXBounds.min; i <= n2 + n3 && (object2 = iLineDataSet.getEntryForIndex(i)) != null; ++i) {
                    Bitmap bitmap;
                    this.mCirclesBuffer[0] = ((Entry)object2).getX();
                    this.mCirclesBuffer[1] = ((BaseEntry)object2).getY() * f;
                    transformer.pointValuesToPixel(this.mCirclesBuffer);
                    if (!this.mViewPortHandler.isInBoundsRight(this.mCirclesBuffer[0])) break;
                    if (!this.mViewPortHandler.isInBoundsLeft(this.mCirclesBuffer[0]) || !this.mViewPortHandler.isInBoundsY(this.mCirclesBuffer[1]) || (bitmap = ((DataSetImageCache)object).getBitmap(i)) == null) continue;
                    object2 = this.mCirclesBuffer;
                    canvas.drawBitmap(bitmap, (float)(object2[0] - f2), (float)(object2[1] - f2), null);
                }
            }
            ++n;
        }
    }

    protected void drawCubicBezier(ILineDataSet iLineDataSet) {
        float f = this.mAnimator.getPhaseY();
        Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
        this.mXBounds.set(this.mChart, iLineDataSet);
        float f2 = iLineDataSet.getCubicIntensity();
        this.cubicPath.reset();
        if (this.mXBounds.range >= 1) {
            int n = this.mXBounds.min + 1;
            int n2 = this.mXBounds.min;
            n2 = this.mXBounds.range;
            Object t = iLineDataSet.getEntryForIndex(Math.max(n - 2, 0));
            Object t2 = iLineDataSet.getEntryForIndex(Math.max(n - 1, 0));
            int n3 = -1;
            if (t2 == null) {
                return;
            }
            this.cubicPath.moveTo(((Entry)t2).getX(), ((BaseEntry)t2).getY() * f);
            n = this.mXBounds.min + 1;
            Object t3 = t2;
            while (n <= this.mXBounds.range + this.mXBounds.min) {
                if (n3 != n) {
                    t2 = iLineDataSet.getEntryForIndex(n);
                }
                n2 = n + 1;
                if (n2 < iLineDataSet.getEntryCount()) {
                    n = n2;
                }
                Object t4 = iLineDataSet.getEntryForIndex(n);
                float f3 = ((Entry)t2).getX();
                float f4 = ((Entry)t).getX();
                float f5 = ((BaseEntry)t2).getY();
                float f6 = ((BaseEntry)t).getY();
                float f7 = ((Entry)t4).getX();
                float f8 = ((Entry)t3).getX();
                float f9 = ((BaseEntry)t4).getY();
                float f10 = ((BaseEntry)t3).getY();
                this.cubicPath.cubicTo(((Entry)t3).getX() + (f3 - f4) * f2, (((BaseEntry)t3).getY() + (f5 - f6) * f2) * f, ((Entry)t2).getX() - (f7 - f8) * f2, (((BaseEntry)t2).getY() - (f9 - f10) * f2) * f, ((Entry)t2).getX(), ((BaseEntry)t2).getY() * f);
                t = t3;
                t3 = t2;
                t2 = t4;
                n3 = n;
                n = n2;
            }
        }
        if (iLineDataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            this.drawCubicFill(this.mBitmapCanvas, iLineDataSet, this.cubicFillPath, transformer, this.mXBounds);
        }
        this.mRenderPaint.setColor(iLineDataSet.getColor());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        transformer.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect(null);
    }

    protected void drawCubicFill(Canvas canvas, ILineDataSet iLineDataSet, Path path, Transformer transformer, BarLineScatterCandleBubbleRenderer.XBounds xBounds) {
        float f = iLineDataSet.getFillFormatter().getFillLinePosition(iLineDataSet, this.mChart);
        path.lineTo(((Entry)iLineDataSet.getEntryForIndex(xBounds.min + xBounds.range)).getX(), f);
        path.lineTo(((Entry)iLineDataSet.getEntryForIndex(xBounds.min)).getX(), f);
        path.close();
        transformer.pathValueToPixel(path);
        transformer = iLineDataSet.getFillDrawable();
        if (transformer != null) {
            this.drawFilledPath(canvas, path, (Drawable)transformer);
            return;
        }
        this.drawFilledPath(canvas, path, iLineDataSet.getFillColor(), iLineDataSet.getFillAlpha());
    }

    @Override
    public void drawData(Canvas canvas) {
        Bitmap bitmap;
        Object object;
        block6 : {
            int n;
            int n2;
            block5 : {
                n = (int)this.mViewPortHandler.getChartWidth();
                n2 = (int)this.mViewPortHandler.getChartHeight();
                object = this.mDrawBitmap;
                object = object == null ? null : (Bitmap)((Reference)object).get();
                if (object == null || object.getWidth() != n) break block5;
                bitmap = object;
                if (object.getHeight() == n2) break block6;
            }
            if (n <= 0) return;
            if (n2 <= 0) return;
            bitmap = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)this.mBitmapConfig);
            this.mDrawBitmap = new WeakReference<Bitmap>(bitmap);
            this.mBitmapCanvas = new Canvas(bitmap);
        }
        bitmap.eraseColor(0);
        Iterator iterator2 = this.mChart.getLineData().getDataSets().iterator();
        do {
            if (!iterator2.hasNext()) {
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mRenderPaint);
                return;
            }
            object = (ILineDataSet)iterator2.next();
            if (!object.isVisible()) continue;
            this.drawDataSet(canvas, (ILineDataSet)object);
        } while (true);
    }

    protected void drawDataSet(Canvas canvas, ILineDataSet iLineDataSet) {
        if (iLineDataSet.getEntryCount() < 1) {
            return;
        }
        this.mRenderPaint.setStrokeWidth(iLineDataSet.getLineWidth());
        this.mRenderPaint.setPathEffect((PathEffect)iLineDataSet.getDashPathEffect());
        int n = 1.$SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[iLineDataSet.getMode().ordinal()];
        if (n != 3) {
            if (n != 4) {
                this.drawLinear(canvas, iLineDataSet);
            } else {
                this.drawHorizontalBezier(iLineDataSet);
            }
        } else {
            this.drawCubicBezier(iLineDataSet);
        }
        this.mRenderPaint.setPathEffect(null);
    }

    @Override
    public void drawExtras(Canvas canvas) {
        this.drawCircles(canvas);
    }

    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        LineData lineData = this.mChart.getLineData();
        int n = arrhighlight.length;
        int n2 = 0;
        while (n2 < n) {
            Object object;
            Highlight highlight = arrhighlight[n2];
            ILineDataSet iLineDataSet = (ILineDataSet)lineData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iLineDataSet != null && iLineDataSet.isHighlightEnabled() && this.isInBoundsX((Entry)(object = iLineDataSet.getEntryForXValue(highlight.getX(), highlight.getY())), iLineDataSet)) {
                object = this.mChart.getTransformer(iLineDataSet.getAxisDependency()).getPixelForValues(((Entry)object).getX(), ((BaseEntry)object).getY() * this.mAnimator.getPhaseY());
                highlight.setDraw((float)((MPPointD)object).x, (float)((MPPointD)object).y);
                this.drawHighlightLines(canvas, (float)((MPPointD)object).x, (float)((MPPointD)object).y, iLineDataSet);
            }
            ++n2;
        }
    }

    protected void drawHorizontalBezier(ILineDataSet iLineDataSet) {
        float f = this.mAnimator.getPhaseY();
        Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
        this.mXBounds.set(this.mChart, iLineDataSet);
        this.cubicPath.reset();
        if (this.mXBounds.range >= 1) {
            Object t = iLineDataSet.getEntryForIndex(this.mXBounds.min);
            this.cubicPath.moveTo(((Entry)t).getX(), ((BaseEntry)t).getY() * f);
            for (int i = this.mXBounds.min + 1; i <= this.mXBounds.range + this.mXBounds.min; ++i) {
                Object t2 = iLineDataSet.getEntryForIndex(i);
                float f2 = ((Entry)t).getX() + (((Entry)t2).getX() - ((Entry)t).getX()) / 2.0f;
                this.cubicPath.cubicTo(f2, ((BaseEntry)t).getY() * f, f2, ((BaseEntry)t2).getY() * f, ((Entry)t2).getX(), ((BaseEntry)t2).getY() * f);
                t = t2;
            }
        }
        if (iLineDataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            this.drawCubicFill(this.mBitmapCanvas, iLineDataSet, this.cubicFillPath, transformer, this.mXBounds);
        }
        this.mRenderPaint.setColor(iLineDataSet.getColor());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        transformer.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect(null);
    }

    protected void drawLinear(Canvas object, ILineDataSet iLineDataSet) {
        block15 : {
            int n;
            float f;
            Transformer transformer;
            int n2;
            int n3;
            int n4;
            Object object2;
            block18 : {
                block17 : {
                    block16 : {
                        n3 = iLineDataSet.getEntryCount();
                        n = iLineDataSet.getMode() == LineDataSet.Mode.STEPPED ? 1 : 0;
                        n2 = n != 0 ? 4 : 2;
                        transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
                        f = this.mAnimator.getPhaseY();
                        this.mRenderPaint.setStyle(Paint.Style.STROKE);
                        object2 = iLineDataSet.isDashedLineEnabled() ? this.mBitmapCanvas : object;
                        this.mXBounds.set(this.mChart, iLineDataSet);
                        if (iLineDataSet.isDrawFilledEnabled() && n3 > 0) {
                            this.drawLinearFill((Canvas)object, iLineDataSet, transformer, this.mXBounds);
                        }
                        if (iLineDataSet.getColors().size() <= 1) break block16;
                        int n5 = this.mLineBuffer.length;
                        n3 = n2 * 2;
                        if (n5 <= n3) {
                            this.mLineBuffer = new float[n2 * 4];
                        }
                        break block17;
                    }
                    n4 = this.mLineBuffer.length;
                    if (n4 < Math.max(n3 *= n2, n2) * 2) {
                        this.mLineBuffer = new float[Math.max(n3, n2) * 4];
                    }
                    if (iLineDataSet.getEntryForIndex(this.mXBounds.min) == null) break block15;
                    n4 = 0;
                    break block18;
                }
                for (n2 = this.mXBounds.min; n2 <= this.mXBounds.range + this.mXBounds.min; ++n2) {
                    object = iLineDataSet.getEntryForIndex(n2);
                    if (object == null) continue;
                    this.mLineBuffer[0] = object.getX();
                    this.mLineBuffer[1] = object.getY() * f;
                    if (n2 < this.mXBounds.max) {
                        Object t = iLineDataSet.getEntryForIndex(n2 + 1);
                        if (t == null) break block15;
                        if (n != 0) {
                            this.mLineBuffer[2] = ((Entry)t).getX();
                            object = this.mLineBuffer;
                            object[3] = object[1];
                            object[4] = object[2];
                            object[5] = object[3];
                            object[6] = ((Entry)t).getX();
                            this.mLineBuffer[7] = ((BaseEntry)t).getY() * f;
                        } else {
                            this.mLineBuffer[2] = ((Entry)t).getX();
                            this.mLineBuffer[3] = ((BaseEntry)t).getY() * f;
                        }
                    } else {
                        object = this.mLineBuffer;
                        object[2] = object[0];
                        object[3] = object[1];
                    }
                    transformer.pointValuesToPixel(this.mLineBuffer);
                    if (this.mViewPortHandler.isInBoundsRight(this.mLineBuffer[0])) {
                        if (!this.mViewPortHandler.isInBoundsLeft(this.mLineBuffer[2]) || !this.mViewPortHandler.isInBoundsTop(this.mLineBuffer[1]) && !this.mViewPortHandler.isInBoundsBottom(this.mLineBuffer[3])) continue;
                        this.mRenderPaint.setColor(iLineDataSet.getColor(n2));
                        object2.drawLines(this.mLineBuffer, 0, n3, this.mRenderPaint);
                        continue;
                    }
                    break block15;
                }
                break block15;
            }
            for (n3 = this.mXBounds.min; n3 <= this.mXBounds.range + this.mXBounds.min; ++n3) {
                int n6 = n3 == 0 ? 0 : n3 - 1;
                Object object3 = iLineDataSet.getEntryForIndex(n6);
                object = iLineDataSet.getEntryForIndex(n3);
                n6 = n4;
                if (object3 != null) {
                    if (object == null) {
                        n6 = n4;
                    } else {
                        float[] arrf = this.mLineBuffer;
                        int n7 = n4 + 1;
                        arrf[n4] = ((Entry)object3).getX();
                        arrf = this.mLineBuffer;
                        n6 = n7 + 1;
                        arrf[n7] = ((BaseEntry)object3).getY() * f;
                        n4 = n6;
                        if (n != 0) {
                            arrf = this.mLineBuffer;
                            n4 = n6 + 1;
                            arrf[n6] = object.getX();
                            arrf = this.mLineBuffer;
                            n7 = n4 + 1;
                            arrf[n4] = ((BaseEntry)object3).getY() * f;
                            arrf = this.mLineBuffer;
                            n6 = n7 + 1;
                            arrf[n7] = object.getX();
                            arrf = this.mLineBuffer;
                            n4 = n6 + 1;
                            arrf[n6] = ((BaseEntry)object3).getY() * f;
                        }
                        object3 = this.mLineBuffer;
                        n6 = n4 + 1;
                        object3[n4] = object.getX();
                        this.mLineBuffer[n6] = object.getY() * f;
                        ++n6;
                    }
                }
                n4 = n6;
            }
            if (n4 > 0) {
                transformer.pointValuesToPixel(this.mLineBuffer);
                n = Math.max((this.mXBounds.range + 1) * n2, n2);
                this.mRenderPaint.setColor(iLineDataSet.getColor());
                object2.drawLines(this.mLineBuffer, 0, n * 2, this.mRenderPaint);
            }
        }
        this.mRenderPaint.setPathEffect(null);
    }

    protected void drawLinearFill(Canvas canvas, ILineDataSet iLineDataSet, Transformer transformer, BarLineScatterCandleBubbleRenderer.XBounds xBounds) {
        int n;
        int n2;
        Path path = this.mGenerateFilledPathBuffer;
        int n3 = xBounds.min;
        int n4 = xBounds.range + xBounds.min;
        int n5 = 0;
        do {
            int n6;
            n = n5 * 128 + n3;
            n2 = n6 = n + 128;
            if (n6 > n4) {
                n2 = n4;
            }
            if (n <= n2) {
                this.generateFilledPath(iLineDataSet, n, n2, path);
                transformer.pathValueToPixel(path);
                xBounds = iLineDataSet.getFillDrawable();
                if (xBounds != null) {
                    this.drawFilledPath(canvas, path, (Drawable)xBounds);
                } else {
                    this.drawFilledPath(canvas, path, iLineDataSet.getFillColor(), iLineDataSet.getFillAlpha());
                }
            }
            ++n5;
        } while (n <= n2);
    }

    @Override
    public void drawValue(Canvas canvas, String string2, float f, float f2, int n) {
        this.mValuePaint.setColor(n);
        canvas.drawText(string2, f, f2, this.mValuePaint);
    }

    @Override
    public void drawValues(Canvas canvas) {
        if (!this.isDrawingValuesAllowed(this.mChart)) return;
        List list = this.mChart.getLineData().getDataSets();
        int n = 0;
        while (n < list.size()) {
            ILineDataSet iLineDataSet = (ILineDataSet)list.get(n);
            if (this.shouldDrawValues(iLineDataSet) && iLineDataSet.getEntryCount() >= 1) {
                int n2;
                this.applyValueTextStyle(iLineDataSet);
                Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
                int n3 = n2 = (int)(iLineDataSet.getCircleRadius() * 1.75f);
                if (!iLineDataSet.isDrawCirclesEnabled()) {
                    n3 = n2 / 2;
                }
                this.mXBounds.set(this.mChart, iLineDataSet);
                float[] arrf = transformer.generateTransformedValuesLine(iLineDataSet, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
                ValueFormatter valueFormatter = iLineDataSet.getValueFormatter();
                MPPointF mPPointF = MPPointF.getInstance(iLineDataSet.getIconsOffset());
                mPPointF.x = Utils.convertDpToPixel(mPPointF.x);
                mPPointF.y = Utils.convertDpToPixel(mPPointF.y);
                for (n2 = 0; n2 < arrf.length; n2 += 2) {
                    float f = arrf[n2];
                    float f2 = arrf[n2 + 1];
                    if (!this.mViewPortHandler.isInBoundsRight(f)) break;
                    if (!this.mViewPortHandler.isInBoundsLeft(f) || !this.mViewPortHandler.isInBoundsY(f2)) continue;
                    int n4 = n2 / 2;
                    transformer = iLineDataSet.getEntryForIndex(this.mXBounds.min + n4);
                    if (iLineDataSet.isDrawValuesEnabled()) {
                        this.drawValue(canvas, valueFormatter.getPointLabel((Entry)((Object)transformer)), f, f2 - (float)n3, iLineDataSet.getValueTextColor(n4));
                    }
                    if (((BaseEntry)((Object)transformer)).getIcon() == null || !iLineDataSet.isDrawIconsEnabled()) continue;
                    transformer = ((BaseEntry)((Object)transformer)).getIcon();
                    Utils.drawImage(canvas, (Drawable)transformer, (int)(f + mPPointF.x), (int)(f2 + mPPointF.y), transformer.getIntrinsicWidth(), transformer.getIntrinsicHeight());
                }
                MPPointF.recycleInstance(mPPointF);
            }
            ++n;
        }
    }

    public Bitmap.Config getBitmapConfig() {
        return this.mBitmapConfig;
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

    public void setBitmapConfig(Bitmap.Config config) {
        this.mBitmapConfig = config;
        this.releaseBitmap();
    }

    private class DataSetImageCache {
        private Bitmap[] circleBitmaps;
        private Path mCirclePathBuffer = new Path();

        private DataSetImageCache() {
        }

        protected void fill(ILineDataSet iLineDataSet, boolean bl, boolean bl2) {
            int n = iLineDataSet.getCircleColorCount();
            float f = iLineDataSet.getCircleRadius();
            float f2 = iLineDataSet.getCircleHoleRadius();
            int n2 = 0;
            while (n2 < n) {
                Bitmap.Config config = Bitmap.Config.ARGB_4444;
                int n3 = (int)((double)f * 2.1);
                config = Bitmap.createBitmap((int)n3, (int)n3, (Bitmap.Config)config);
                Canvas canvas = new Canvas((Bitmap)config);
                this.circleBitmaps[n2] = config;
                LineChartRenderer.this.mRenderPaint.setColor(iLineDataSet.getCircleColor(n2));
                if (bl2) {
                    this.mCirclePathBuffer.reset();
                    this.mCirclePathBuffer.addCircle(f, f, f, Path.Direction.CW);
                    this.mCirclePathBuffer.addCircle(f, f, f2, Path.Direction.CCW);
                    canvas.drawPath(this.mCirclePathBuffer, LineChartRenderer.this.mRenderPaint);
                } else {
                    canvas.drawCircle(f, f, f, LineChartRenderer.this.mRenderPaint);
                    if (bl) {
                        canvas.drawCircle(f, f, f2, LineChartRenderer.this.mCirclePaintInner);
                    }
                }
                ++n2;
            }
        }

        protected Bitmap getBitmap(int n) {
            Bitmap[] arrbitmap = this.circleBitmaps;
            return arrbitmap[n % arrbitmap.length];
        }

        protected boolean init(ILineDataSet arrbitmap) {
            int n = arrbitmap.getCircleColorCount();
            arrbitmap = this.circleBitmaps;
            boolean bl = true;
            if (arrbitmap == null) {
                this.circleBitmaps = new Bitmap[n];
                return bl;
            }
            if (arrbitmap.length == n) return false;
            this.circleBitmaps = new Bitmap[n];
            return bl;
        }
    }

}

