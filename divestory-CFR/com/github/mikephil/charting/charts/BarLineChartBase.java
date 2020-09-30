/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 */
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.jobs.AnimatedMoveViewJob;
import com.github.mikephil.charting.jobs.AnimatedZoomJob;
import com.github.mikephil.charting.jobs.MoveViewJob;
import com.github.mikephil.charting.jobs.ZoomJob;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnDrawListener;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class BarLineChartBase<T extends BarLineScatterCandleBubbleData<? extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>>
extends Chart<T>
implements BarLineScatterCandleBubbleDataProvider {
    private long drawCycles = 0L;
    protected boolean mAutoScaleMinMaxEnabled = false;
    protected YAxis mAxisLeft;
    protected YAxisRenderer mAxisRendererLeft;
    protected YAxisRenderer mAxisRendererRight;
    protected YAxis mAxisRight;
    protected Paint mBorderPaint;
    protected boolean mClipValuesToContent = false;
    private boolean mCustomViewPortEnabled = false;
    protected boolean mDoubleTapToZoomEnabled = true;
    private boolean mDragXEnabled = true;
    private boolean mDragYEnabled = true;
    protected boolean mDrawBorders = false;
    protected boolean mDrawGridBackground = false;
    protected OnDrawListener mDrawListener;
    protected Matrix mFitScreenMatrixBuffer = new Matrix();
    protected float[] mGetPositionBuffer = new float[2];
    protected Paint mGridBackgroundPaint;
    protected boolean mHighlightPerDragEnabled = true;
    protected boolean mKeepPositionOnRotation = false;
    protected Transformer mLeftAxisTransformer;
    protected int mMaxVisibleCount = 100;
    protected float mMinOffset = 15.0f;
    private RectF mOffsetsBuffer = new RectF();
    protected float[] mOnSizeChangedBuffer = new float[2];
    protected boolean mPinchZoomEnabled = false;
    protected Transformer mRightAxisTransformer;
    private boolean mScaleXEnabled = true;
    private boolean mScaleYEnabled = true;
    protected XAxisRenderer mXAxisRenderer;
    protected Matrix mZoomMatrixBuffer = new Matrix();
    protected MPPointD posForGetHighestVisibleX = MPPointD.getInstance(0.0, 0.0);
    protected MPPointD posForGetLowestVisibleX = MPPointD.getInstance(0.0, 0.0);
    private long totalTime = 0L;

    public BarLineChartBase(Context context) {
        super(context);
    }

    public BarLineChartBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BarLineChartBase(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    protected void autoScale() {
        float f = this.getLowestVisibleX();
        float f2 = this.getHighestVisibleX();
        ((BarLineScatterCandleBubbleData)this.mData).calcMinMaxY(f, f2);
        this.mXAxis.calculate(((BarLineScatterCandleBubbleData)this.mData).getXMin(), ((BarLineScatterCandleBubbleData)this.mData).getXMax());
        if (this.mAxisLeft.isEnabled()) {
            this.mAxisLeft.calculate(((BarLineScatterCandleBubbleData)this.mData).getYMin(YAxis.AxisDependency.LEFT), ((BarLineScatterCandleBubbleData)this.mData).getYMax(YAxis.AxisDependency.LEFT));
        }
        if (this.mAxisRight.isEnabled()) {
            this.mAxisRight.calculate(((BarLineScatterCandleBubbleData)this.mData).getYMin(YAxis.AxisDependency.RIGHT), ((BarLineScatterCandleBubbleData)this.mData).getYMax(YAxis.AxisDependency.RIGHT));
        }
        this.calculateOffsets();
    }

    @Override
    protected void calcMinMax() {
        this.mXAxis.calculate(((BarLineScatterCandleBubbleData)this.mData).getXMin(), ((BarLineScatterCandleBubbleData)this.mData).getXMax());
        this.mAxisLeft.calculate(((BarLineScatterCandleBubbleData)this.mData).getYMin(YAxis.AxisDependency.LEFT), ((BarLineScatterCandleBubbleData)this.mData).getYMax(YAxis.AxisDependency.LEFT));
        this.mAxisRight.calculate(((BarLineScatterCandleBubbleData)this.mData).getYMin(YAxis.AxisDependency.RIGHT), ((BarLineScatterCandleBubbleData)this.mData).getYMax(YAxis.AxisDependency.RIGHT));
    }

    protected void calculateLegendOffsets(RectF rectF) {
        rectF.left = 0.0f;
        rectF.right = 0.0f;
        rectF.top = 0.0f;
        rectF.bottom = 0.0f;
        if (this.mLegend == null) return;
        if (!this.mLegend.isEnabled()) return;
        if (this.mLegend.isDrawInsideEnabled()) return;
        int n = 2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mLegend.getOrientation().ordinal()];
        if (n != 1) {
            if (n != 2) {
                return;
            }
            n = 2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()];
            if (n == 1) {
                rectF.top += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
                return;
            }
            if (n != 2) {
                return;
            }
            rectF.bottom += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
            return;
        }
        n = 2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[this.mLegend.getHorizontalAlignment().ordinal()];
        if (n == 1) {
            rectF.left += Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent()) + this.mLegend.getXOffset();
            return;
        }
        if (n == 2) {
            rectF.right += Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent()) + this.mLegend.getXOffset();
            return;
        }
        if (n != 3) {
            return;
        }
        n = 2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()];
        if (n == 1) {
            rectF.top += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
            return;
        }
        if (n != 2) {
            return;
        }
        rectF.bottom += Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent()) + this.mLegend.getYOffset();
    }

    @Override
    public void calculateOffsets() {
        block6 : {
            float f;
            float f2;
            float f3;
            float f4;
            float f5;
            block7 : {
                float f6;
                float f7;
                block10 : {
                    block9 : {
                        block8 : {
                            if (this.mCustomViewPortEnabled) break block6;
                            this.calculateLegendOffsets(this.mOffsetsBuffer);
                            f2 = this.mOffsetsBuffer.left + 0.0f;
                            f6 = this.mOffsetsBuffer.top + 0.0f;
                            f5 = this.mOffsetsBuffer.right + 0.0f;
                            f3 = this.mOffsetsBuffer.bottom + 0.0f;
                            f4 = f2;
                            if (this.mAxisLeft.needsOffset()) {
                                f4 = f2 + this.mAxisLeft.getRequiredWidthSpace(this.mAxisRendererLeft.getPaintAxisLabels());
                            }
                            f2 = f5;
                            if (this.mAxisRight.needsOffset()) {
                                f2 = f5 + this.mAxisRight.getRequiredWidthSpace(this.mAxisRendererRight.getPaintAxisLabels());
                            }
                            f = f6;
                            f5 = f3;
                            if (!this.mXAxis.isEnabled()) break block7;
                            f = f6;
                            f5 = f3;
                            if (!this.mXAxis.isDrawLabelsEnabled()) break block7;
                            f7 = (float)this.mXAxis.mLabelRotatedHeight + this.mXAxis.getYOffset();
                            if (this.mXAxis.getPosition() != XAxis.XAxisPosition.BOTTOM) break block8;
                            f5 = f3 + f7;
                            f = f6;
                            break block7;
                        }
                        if (this.mXAxis.getPosition() != XAxis.XAxisPosition.TOP) break block9;
                        f5 = f3;
                        break block10;
                    }
                    f = f6;
                    f5 = f3;
                    if (this.mXAxis.getPosition() != XAxis.XAxisPosition.BOTH_SIDED) break block7;
                    f5 = f3 + f7;
                }
                f = f6 + f7;
            }
            f3 = f + this.getExtraTopOffset();
            f = f4 + this.getExtraLeftOffset();
            f4 = Utils.convertDpToPixel(this.mMinOffset);
            this.mViewPortHandler.restrainViewPort(Math.max(f4, f), Math.max(f4, f3), Math.max(f4, f2 += this.getExtraRightOffset()), Math.max(f4, f5 += this.getExtraBottomOffset()));
            if (this.mLogEnabled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("offsetLeft: ");
                stringBuilder.append(f);
                stringBuilder.append(", offsetTop: ");
                stringBuilder.append(f3);
                stringBuilder.append(", offsetRight: ");
                stringBuilder.append(f2);
                stringBuilder.append(", offsetBottom: ");
                stringBuilder.append(f5);
                Log.i((String)"MPAndroidChart", (String)stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("Content: ");
                stringBuilder.append(this.mViewPortHandler.getContentRect().toString());
                Log.i((String)"MPAndroidChart", (String)stringBuilder.toString());
            }
        }
        this.prepareOffsetMatrix();
        this.prepareValuePxMatrix();
    }

    public void centerViewTo(float f, float f2, YAxis.AxisDependency axisDependency) {
        float f3 = this.getAxisRange(axisDependency) / this.mViewPortHandler.getScaleY();
        float f4 = this.getXAxis().mAxisRange / this.mViewPortHandler.getScaleX();
        this.addViewportJob(MoveViewJob.getInstance(this.mViewPortHandler, f - f4 / 2.0f, f2 + f3 / 2.0f, this.getTransformer(axisDependency), (View)this));
    }

    public void centerViewToAnimated(float f, float f2, YAxis.AxisDependency axisDependency, long l) {
        MPPointD mPPointD = this.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), axisDependency);
        float f3 = this.getAxisRange(axisDependency) / this.mViewPortHandler.getScaleY();
        float f4 = this.getXAxis().mAxisRange / this.mViewPortHandler.getScaleX();
        this.addViewportJob(AnimatedMoveViewJob.getInstance(this.mViewPortHandler, f - f4 / 2.0f, f2 + f3 / 2.0f, this.getTransformer(axisDependency), (View)this, (float)mPPointD.x, (float)mPPointD.y, l));
        MPPointD.recycleInstance(mPPointD);
    }

    public void centerViewToY(float f, YAxis.AxisDependency axisDependency) {
        float f2 = this.getAxisRange(axisDependency) / this.mViewPortHandler.getScaleY();
        this.addViewportJob(MoveViewJob.getInstance(this.mViewPortHandler, 0.0f, f + f2 / 2.0f, this.getTransformer(axisDependency), (View)this));
    }

    public void computeScroll() {
        if (!(this.mChartTouchListener instanceof BarLineChartTouchListener)) return;
        ((BarLineChartTouchListener)this.mChartTouchListener).computeScroll();
    }

    protected void drawGridBackground(Canvas canvas) {
        if (this.mDrawGridBackground) {
            canvas.drawRect(this.mViewPortHandler.getContentRect(), this.mGridBackgroundPaint);
        }
        if (!this.mDrawBorders) return;
        canvas.drawRect(this.mViewPortHandler.getContentRect(), this.mBorderPaint);
    }

    public void fitScreen() {
        Matrix matrix = this.mFitScreenMatrixBuffer;
        this.mViewPortHandler.fitScreen(matrix);
        this.mViewPortHandler.refresh(matrix, (View)this, false);
        this.calculateOffsets();
        this.postInvalidate();
    }

    public YAxis getAxis(YAxis.AxisDependency axisDependency) {
        if (axisDependency != YAxis.AxisDependency.LEFT) return this.mAxisRight;
        return this.mAxisLeft;
    }

    public YAxis getAxisLeft() {
        return this.mAxisLeft;
    }

    protected float getAxisRange(YAxis.AxisDependency axisDependency) {
        if (axisDependency != YAxis.AxisDependency.LEFT) return this.mAxisRight.mAxisRange;
        return this.mAxisLeft.mAxisRange;
    }

    public YAxis getAxisRight() {
        return this.mAxisRight;
    }

    public IBarLineScatterCandleBubbleDataSet getDataSetByTouchPoint(float f, float f2) {
        Highlight highlight = this.getHighlightByTouchPoint(f, f2);
        if (highlight == null) return null;
        return (IBarLineScatterCandleBubbleDataSet)((BarLineScatterCandleBubbleData)this.mData).getDataSetByIndex(highlight.getDataSetIndex());
    }

    public OnDrawListener getDrawListener() {
        return this.mDrawListener;
    }

    public Entry getEntryByTouchPoint(float f, float f2) {
        Highlight highlight = this.getHighlightByTouchPoint(f, f2);
        if (highlight == null) return null;
        return ((BarLineScatterCandleBubbleData)this.mData).getEntryForHighlight(highlight);
    }

    @Override
    public float getHighestVisibleX() {
        this.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.posForGetHighestVisibleX);
        return (float)Math.min((double)this.mXAxis.mAxisMaximum, this.posForGetHighestVisibleX.x);
    }

    @Override
    public float getLowestVisibleX() {
        this.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.posForGetLowestVisibleX);
        return (float)Math.max((double)this.mXAxis.mAxisMinimum, this.posForGetLowestVisibleX.x);
    }

    @Override
    public int getMaxVisibleCount() {
        return this.mMaxVisibleCount;
    }

    public float getMinOffset() {
        return this.mMinOffset;
    }

    @Override
    public Paint getPaint(int n) {
        Paint paint = super.getPaint(n);
        if (paint != null) {
            return paint;
        }
        if (n == 4) return this.mGridBackgroundPaint;
        return null;
    }

    public MPPointD getPixelForValues(float f, float f2, YAxis.AxisDependency axisDependency) {
        return this.getTransformer(axisDependency).getPixelForValues(f, f2);
    }

    public MPPointF getPosition(Entry arrf, YAxis.AxisDependency axisDependency) {
        if (arrf == null) {
            return null;
        }
        this.mGetPositionBuffer[0] = arrf.getX();
        this.mGetPositionBuffer[1] = arrf.getY();
        this.getTransformer(axisDependency).pointValuesToPixel(this.mGetPositionBuffer);
        arrf = this.mGetPositionBuffer;
        return MPPointF.getInstance(arrf[0], arrf[1]);
    }

    public YAxisRenderer getRendererLeftYAxis() {
        return this.mAxisRendererLeft;
    }

    public YAxisRenderer getRendererRightYAxis() {
        return this.mAxisRendererRight;
    }

    public XAxisRenderer getRendererXAxis() {
        return this.mXAxisRenderer;
    }

    public float getScaleX() {
        if (this.mViewPortHandler != null) return this.mViewPortHandler.getScaleX();
        return 1.0f;
    }

    public float getScaleY() {
        if (this.mViewPortHandler != null) return this.mViewPortHandler.getScaleY();
        return 1.0f;
    }

    @Override
    public Transformer getTransformer(YAxis.AxisDependency axisDependency) {
        if (axisDependency != YAxis.AxisDependency.LEFT) return this.mRightAxisTransformer;
        return this.mLeftAxisTransformer;
    }

    public MPPointD getValuesByTouchPoint(float f, float f2, YAxis.AxisDependency axisDependency) {
        MPPointD mPPointD = MPPointD.getInstance(0.0, 0.0);
        this.getValuesByTouchPoint(f, f2, axisDependency, mPPointD);
        return mPPointD;
    }

    public void getValuesByTouchPoint(float f, float f2, YAxis.AxisDependency axisDependency, MPPointD mPPointD) {
        this.getTransformer(axisDependency).getValuesByTouchPoint(f, f2, mPPointD);
    }

    public float getVisibleXRange() {
        return Math.abs(this.getHighestVisibleX() - this.getLowestVisibleX());
    }

    @Override
    public float getYChartMax() {
        return Math.max(this.mAxisLeft.mAxisMaximum, this.mAxisRight.mAxisMaximum);
    }

    @Override
    public float getYChartMin() {
        return Math.min(this.mAxisLeft.mAxisMinimum, this.mAxisRight.mAxisMinimum);
    }

    public boolean hasNoDragOffset() {
        return this.mViewPortHandler.hasNoDragOffset();
    }

    @Override
    protected void init() {
        Paint paint;
        super.init();
        this.mAxisLeft = new YAxis(YAxis.AxisDependency.LEFT);
        this.mAxisRight = new YAxis(YAxis.AxisDependency.RIGHT);
        this.mLeftAxisTransformer = new Transformer(this.mViewPortHandler);
        this.mRightAxisTransformer = new Transformer(this.mViewPortHandler);
        this.mAxisRendererLeft = new YAxisRenderer(this.mViewPortHandler, this.mAxisLeft, this.mLeftAxisTransformer);
        this.mAxisRendererRight = new YAxisRenderer(this.mViewPortHandler, this.mAxisRight, this.mRightAxisTransformer);
        this.mXAxisRenderer = new XAxisRenderer(this.mViewPortHandler, this.mXAxis, this.mLeftAxisTransformer);
        this.setHighlighter(new ChartHighlighter<BarLineChartBase>(this));
        this.mChartTouchListener = new BarLineChartTouchListener(this, this.mViewPortHandler.getMatrixTouch(), 3.0f);
        this.mGridBackgroundPaint = paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        this.mGridBackgroundPaint.setColor(Color.rgb((int)240, (int)240, (int)240));
        this.mBorderPaint = paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setColor(-16777216);
        this.mBorderPaint.setStrokeWidth(Utils.convertDpToPixel(1.0f));
    }

    public boolean isAnyAxisInverted() {
        if (this.mAxisLeft.isInverted()) {
            return true;
        }
        if (!this.mAxisRight.isInverted()) return false;
        return true;
    }

    public boolean isAutoScaleMinMaxEnabled() {
        return this.mAutoScaleMinMaxEnabled;
    }

    public boolean isClipValuesToContentEnabled() {
        return this.mClipValuesToContent;
    }

    public boolean isDoubleTapToZoomEnabled() {
        return this.mDoubleTapToZoomEnabled;
    }

    public boolean isDragEnabled() {
        if (this.mDragXEnabled) return true;
        if (this.mDragYEnabled) return true;
        return false;
    }

    public boolean isDragXEnabled() {
        return this.mDragXEnabled;
    }

    public boolean isDragYEnabled() {
        return this.mDragYEnabled;
    }

    public boolean isDrawBordersEnabled() {
        return this.mDrawBorders;
    }

    public boolean isFullyZoomedOut() {
        return this.mViewPortHandler.isFullyZoomedOut();
    }

    public boolean isHighlightPerDragEnabled() {
        return this.mHighlightPerDragEnabled;
    }

    @Override
    public boolean isInverted(YAxis.AxisDependency axisDependency) {
        return this.getAxis(axisDependency).isInverted();
    }

    public boolean isKeepPositionOnRotation() {
        return this.mKeepPositionOnRotation;
    }

    public boolean isPinchZoomEnabled() {
        return this.mPinchZoomEnabled;
    }

    public boolean isScaleXEnabled() {
        return this.mScaleXEnabled;
    }

    public boolean isScaleYEnabled() {
        return this.mScaleYEnabled;
    }

    public void moveViewTo(float f, float f2, YAxis.AxisDependency axisDependency) {
        float f3 = this.getAxisRange(axisDependency) / this.mViewPortHandler.getScaleY();
        this.addViewportJob(MoveViewJob.getInstance(this.mViewPortHandler, f, f2 + f3 / 2.0f, this.getTransformer(axisDependency), (View)this));
    }

    public void moveViewToAnimated(float f, float f2, YAxis.AxisDependency axisDependency, long l) {
        MPPointD mPPointD = this.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), axisDependency);
        float f3 = this.getAxisRange(axisDependency) / this.mViewPortHandler.getScaleY();
        this.addViewportJob(AnimatedMoveViewJob.getInstance(this.mViewPortHandler, f, f2 + f3 / 2.0f, this.getTransformer(axisDependency), (View)this, (float)mPPointD.x, (float)mPPointD.y, l));
        MPPointD.recycleInstance(mPPointD);
    }

    public void moveViewToX(float f) {
        this.addViewportJob(MoveViewJob.getInstance(this.mViewPortHandler, f, 0.0f, this.getTransformer(YAxis.AxisDependency.LEFT), (View)this));
    }

    @Override
    public void notifyDataSetChanged() {
        if (this.mData == null) {
            if (!this.mLogEnabled) return;
            Log.i((String)"MPAndroidChart", (String)"Preparing... DATA NOT SET.");
            return;
        }
        if (this.mLogEnabled) {
            Log.i((String)"MPAndroidChart", (String)"Preparing...");
        }
        if (this.mRenderer != null) {
            this.mRenderer.initBuffers();
        }
        this.calcMinMax();
        this.mAxisRendererLeft.computeAxis(this.mAxisLeft.mAxisMinimum, this.mAxisLeft.mAxisMaximum, this.mAxisLeft.isInverted());
        this.mAxisRendererRight.computeAxis(this.mAxisRight.mAxisMinimum, this.mAxisRight.mAxisMaximum, this.mAxisRight.isInverted());
        this.mXAxisRenderer.computeAxis(this.mXAxis.mAxisMinimum, this.mXAxis.mAxisMaximum, false);
        if (this.mLegend != null) {
            this.mLegendRenderer.computeLegend(this.mData);
        }
        this.calculateOffsets();
    }

    @Override
    protected void onDraw(Canvas object) {
        long l;
        long l2;
        super.onDraw((Canvas)object);
        if (this.mData == null) {
            return;
        }
        long l3 = System.currentTimeMillis();
        this.drawGridBackground((Canvas)object);
        if (this.mAutoScaleMinMaxEnabled) {
            this.autoScale();
        }
        if (this.mAxisLeft.isEnabled()) {
            this.mAxisRendererLeft.computeAxis(this.mAxisLeft.mAxisMinimum, this.mAxisLeft.mAxisMaximum, this.mAxisLeft.isInverted());
        }
        if (this.mAxisRight.isEnabled()) {
            this.mAxisRendererRight.computeAxis(this.mAxisRight.mAxisMinimum, this.mAxisRight.mAxisMaximum, this.mAxisRight.isInverted());
        }
        if (this.mXAxis.isEnabled()) {
            this.mXAxisRenderer.computeAxis(this.mXAxis.mAxisMinimum, this.mXAxis.mAxisMaximum, false);
        }
        this.mXAxisRenderer.renderAxisLine((Canvas)object);
        this.mAxisRendererLeft.renderAxisLine((Canvas)object);
        this.mAxisRendererRight.renderAxisLine((Canvas)object);
        if (this.mXAxis.isDrawGridLinesBehindDataEnabled()) {
            this.mXAxisRenderer.renderGridLines((Canvas)object);
        }
        if (this.mAxisLeft.isDrawGridLinesBehindDataEnabled()) {
            this.mAxisRendererLeft.renderGridLines((Canvas)object);
        }
        if (this.mAxisRight.isDrawGridLinesBehindDataEnabled()) {
            this.mAxisRendererRight.renderGridLines((Canvas)object);
        }
        if (this.mXAxis.isEnabled() && this.mXAxis.isDrawLimitLinesBehindDataEnabled()) {
            this.mXAxisRenderer.renderLimitLines((Canvas)object);
        }
        if (this.mAxisLeft.isEnabled() && this.mAxisLeft.isDrawLimitLinesBehindDataEnabled()) {
            this.mAxisRendererLeft.renderLimitLines((Canvas)object);
        }
        if (this.mAxisRight.isEnabled() && this.mAxisRight.isDrawLimitLinesBehindDataEnabled()) {
            this.mAxisRendererRight.renderLimitLines((Canvas)object);
        }
        int n = object.save();
        object.clipRect(this.mViewPortHandler.getContentRect());
        this.mRenderer.drawData((Canvas)object);
        if (!this.mXAxis.isDrawGridLinesBehindDataEnabled()) {
            this.mXAxisRenderer.renderGridLines((Canvas)object);
        }
        if (!this.mAxisLeft.isDrawGridLinesBehindDataEnabled()) {
            this.mAxisRendererLeft.renderGridLines((Canvas)object);
        }
        if (!this.mAxisRight.isDrawGridLinesBehindDataEnabled()) {
            this.mAxisRendererRight.renderGridLines((Canvas)object);
        }
        if (this.valuesToHighlight()) {
            this.mRenderer.drawHighlighted((Canvas)object, this.mIndicesToHighlight);
        }
        object.restoreToCount(n);
        this.mRenderer.drawExtras((Canvas)object);
        if (this.mXAxis.isEnabled() && !this.mXAxis.isDrawLimitLinesBehindDataEnabled()) {
            this.mXAxisRenderer.renderLimitLines((Canvas)object);
        }
        if (this.mAxisLeft.isEnabled() && !this.mAxisLeft.isDrawLimitLinesBehindDataEnabled()) {
            this.mAxisRendererLeft.renderLimitLines((Canvas)object);
        }
        if (this.mAxisRight.isEnabled() && !this.mAxisRight.isDrawLimitLinesBehindDataEnabled()) {
            this.mAxisRendererRight.renderLimitLines((Canvas)object);
        }
        this.mXAxisRenderer.renderAxisLabels((Canvas)object);
        this.mAxisRendererLeft.renderAxisLabels((Canvas)object);
        this.mAxisRendererRight.renderAxisLabels((Canvas)object);
        if (this.isClipValuesToContentEnabled()) {
            n = object.save();
            object.clipRect(this.mViewPortHandler.getContentRect());
            this.mRenderer.drawValues((Canvas)object);
            object.restoreToCount(n);
        } else {
            this.mRenderer.drawValues((Canvas)object);
        }
        this.mLegendRenderer.renderLegend((Canvas)object);
        this.drawDescription((Canvas)object);
        this.drawMarkers((Canvas)object);
        if (!this.mLogEnabled) return;
        l3 = System.currentTimeMillis() - l3;
        this.totalTime = l = this.totalTime + l3;
        this.drawCycles = l2 = this.drawCycles + 1L;
        l2 = l / l2;
        object = new StringBuilder();
        ((StringBuilder)object).append("Drawtime: ");
        ((StringBuilder)object).append(l3);
        ((StringBuilder)object).append(" ms, average: ");
        ((StringBuilder)object).append(l2);
        ((StringBuilder)object).append(" ms, cycles: ");
        ((StringBuilder)object).append(this.drawCycles);
        Log.i((String)"MPAndroidChart", (String)((StringBuilder)object).toString());
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        float[] arrf = this.mOnSizeChangedBuffer;
        arrf[1] = 0.0f;
        arrf[0] = 0.0f;
        if (this.mKeepPositionOnRotation) {
            arrf[0] = this.mViewPortHandler.contentLeft();
            this.mOnSizeChangedBuffer[1] = this.mViewPortHandler.contentTop();
            this.getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(this.mOnSizeChangedBuffer);
        }
        super.onSizeChanged(n, n2, n3, n4);
        if (this.mKeepPositionOnRotation) {
            this.getTransformer(YAxis.AxisDependency.LEFT).pointValuesToPixel(this.mOnSizeChangedBuffer);
            this.mViewPortHandler.centerViewPort(this.mOnSizeChangedBuffer, (View)this);
            return;
        }
        this.mViewPortHandler.refresh(this.mViewPortHandler.getMatrixTouch(), (View)this, true);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (this.mChartTouchListener == null) return false;
        if (this.mData == null) {
            return false;
        }
        if (this.mTouchEnabled) return this.mChartTouchListener.onTouch((View)this, motionEvent);
        return false;
    }

    protected void prepareOffsetMatrix() {
        this.mRightAxisTransformer.prepareMatrixOffset(this.mAxisRight.isInverted());
        this.mLeftAxisTransformer.prepareMatrixOffset(this.mAxisLeft.isInverted());
    }

    protected void prepareValuePxMatrix() {
        if (this.mLogEnabled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Preparing Value-Px Matrix, xmin: ");
            stringBuilder.append(this.mXAxis.mAxisMinimum);
            stringBuilder.append(", xmax: ");
            stringBuilder.append(this.mXAxis.mAxisMaximum);
            stringBuilder.append(", xdelta: ");
            stringBuilder.append(this.mXAxis.mAxisRange);
            Log.i((String)"MPAndroidChart", (String)stringBuilder.toString());
        }
        this.mRightAxisTransformer.prepareMatrixValuePx(this.mXAxis.mAxisMinimum, this.mXAxis.mAxisRange, this.mAxisRight.mAxisRange, this.mAxisRight.mAxisMinimum);
        this.mLeftAxisTransformer.prepareMatrixValuePx(this.mXAxis.mAxisMinimum, this.mXAxis.mAxisRange, this.mAxisLeft.mAxisRange, this.mAxisLeft.mAxisMinimum);
    }

    public void resetTracking() {
        this.totalTime = 0L;
        this.drawCycles = 0L;
    }

    public void resetViewPortOffsets() {
        this.mCustomViewPortEnabled = false;
        this.calculateOffsets();
    }

    public void resetZoom() {
        this.mViewPortHandler.resetZoom(this.mZoomMatrixBuffer);
        this.mViewPortHandler.refresh(this.mZoomMatrixBuffer, (View)this, false);
        this.calculateOffsets();
        this.postInvalidate();
    }

    public void setAutoScaleMinMaxEnabled(boolean bl) {
        this.mAutoScaleMinMaxEnabled = bl;
    }

    public void setBorderColor(int n) {
        this.mBorderPaint.setColor(n);
    }

    public void setBorderWidth(float f) {
        this.mBorderPaint.setStrokeWidth(Utils.convertDpToPixel(f));
    }

    public void setClipValuesToContent(boolean bl) {
        this.mClipValuesToContent = bl;
    }

    public void setDoubleTapToZoomEnabled(boolean bl) {
        this.mDoubleTapToZoomEnabled = bl;
    }

    public void setDragEnabled(boolean bl) {
        this.mDragXEnabled = bl;
        this.mDragYEnabled = bl;
    }

    public void setDragOffsetX(float f) {
        this.mViewPortHandler.setDragOffsetX(f);
    }

    public void setDragOffsetY(float f) {
        this.mViewPortHandler.setDragOffsetY(f);
    }

    public void setDragXEnabled(boolean bl) {
        this.mDragXEnabled = bl;
    }

    public void setDragYEnabled(boolean bl) {
        this.mDragYEnabled = bl;
    }

    public void setDrawBorders(boolean bl) {
        this.mDrawBorders = bl;
    }

    public void setDrawGridBackground(boolean bl) {
        this.mDrawGridBackground = bl;
    }

    public void setGridBackgroundColor(int n) {
        this.mGridBackgroundPaint.setColor(n);
    }

    public void setHighlightPerDragEnabled(boolean bl) {
        this.mHighlightPerDragEnabled = bl;
    }

    public void setKeepPositionOnRotation(boolean bl) {
        this.mKeepPositionOnRotation = bl;
    }

    public void setMaxVisibleValueCount(int n) {
        this.mMaxVisibleCount = n;
    }

    public void setMinOffset(float f) {
        this.mMinOffset = f;
    }

    public void setOnDrawListener(OnDrawListener onDrawListener) {
        this.mDrawListener = onDrawListener;
    }

    @Override
    public void setPaint(Paint paint, int n) {
        super.setPaint(paint, n);
        if (n != 4) {
            return;
        }
        this.mGridBackgroundPaint = paint;
    }

    public void setPinchZoom(boolean bl) {
        this.mPinchZoomEnabled = bl;
    }

    public void setRendererLeftYAxis(YAxisRenderer yAxisRenderer) {
        this.mAxisRendererLeft = yAxisRenderer;
    }

    public void setRendererRightYAxis(YAxisRenderer yAxisRenderer) {
        this.mAxisRendererRight = yAxisRenderer;
    }

    public void setScaleEnabled(boolean bl) {
        this.mScaleXEnabled = bl;
        this.mScaleYEnabled = bl;
    }

    public void setScaleMinima(float f, float f2) {
        this.mViewPortHandler.setMinimumScaleX(f);
        this.mViewPortHandler.setMinimumScaleY(f2);
    }

    public void setScaleXEnabled(boolean bl) {
        this.mScaleXEnabled = bl;
    }

    public void setScaleYEnabled(boolean bl) {
        this.mScaleYEnabled = bl;
    }

    public void setViewPortOffsets(final float f, final float f2, final float f3, final float f4) {
        this.mCustomViewPortEnabled = true;
        this.post(new Runnable(){

            @Override
            public void run() {
                BarLineChartBase.this.mViewPortHandler.restrainViewPort(f, f2, f3, f4);
                BarLineChartBase.this.prepareOffsetMatrix();
                BarLineChartBase.this.prepareValuePxMatrix();
            }
        });
    }

    public void setVisibleXRange(float f, float f2) {
        f = this.mXAxis.mAxisRange / f;
        f2 = this.mXAxis.mAxisRange / f2;
        this.mViewPortHandler.setMinMaxScaleX(f, f2);
    }

    public void setVisibleXRangeMaximum(float f) {
        f = this.mXAxis.mAxisRange / f;
        this.mViewPortHandler.setMinimumScaleX(f);
    }

    public void setVisibleXRangeMinimum(float f) {
        f = this.mXAxis.mAxisRange / f;
        this.mViewPortHandler.setMaximumScaleX(f);
    }

    public void setVisibleYRange(float f, float f2, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        f2 = this.getAxisRange(axisDependency) / f2;
        this.mViewPortHandler.setMinMaxScaleY(f, f2);
    }

    public void setVisibleYRangeMaximum(float f, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        this.mViewPortHandler.setMinimumScaleY(f);
    }

    public void setVisibleYRangeMinimum(float f, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        this.mViewPortHandler.setMaximumScaleY(f);
    }

    public void setXAxisRenderer(XAxisRenderer xAxisRenderer) {
        this.mXAxisRenderer = xAxisRenderer;
    }

    public void zoom(float f, float f2, float f3, float f4) {
        this.mViewPortHandler.zoom(f, f2, f3, -f4, this.mZoomMatrixBuffer);
        this.mViewPortHandler.refresh(this.mZoomMatrixBuffer, (View)this, false);
        this.calculateOffsets();
        this.postInvalidate();
    }

    public void zoom(float f, float f2, float f3, float f4, YAxis.AxisDependency axisDependency) {
        this.addViewportJob(ZoomJob.getInstance(this.mViewPortHandler, f, f2, f3, f4, this.getTransformer(axisDependency), axisDependency, (View)this));
    }

    public void zoomAndCenterAnimated(float f, float f2, float f3, float f4, YAxis.AxisDependency axisDependency, long l) {
        MPPointD mPPointD = this.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), axisDependency);
        this.addViewportJob(AnimatedZoomJob.getInstance(this.mViewPortHandler, (View)this, this.getTransformer(axisDependency), this.getAxis(axisDependency), this.mXAxis.mAxisRange, f, f2, this.mViewPortHandler.getScaleX(), this.mViewPortHandler.getScaleY(), f3, f4, (float)mPPointD.x, (float)mPPointD.y, l));
        MPPointD.recycleInstance(mPPointD);
    }

    public void zoomIn() {
        MPPointF mPPointF = this.mViewPortHandler.getContentCenter();
        this.mViewPortHandler.zoomIn(mPPointF.x, -mPPointF.y, this.mZoomMatrixBuffer);
        this.mViewPortHandler.refresh(this.mZoomMatrixBuffer, (View)this, false);
        MPPointF.recycleInstance(mPPointF);
        this.calculateOffsets();
        this.postInvalidate();
    }

    public void zoomOut() {
        MPPointF mPPointF = this.mViewPortHandler.getContentCenter();
        this.mViewPortHandler.zoomOut(mPPointF.x, -mPPointF.y, this.mZoomMatrixBuffer);
        this.mViewPortHandler.refresh(this.mZoomMatrixBuffer, (View)this, false);
        MPPointF.recycleInstance(mPPointF);
        this.calculateOffsets();
        this.postInvalidate();
    }

    public void zoomToCenter(float f, float f2) {
        MPPointF mPPointF = this.getCenterOffsets();
        Matrix matrix = this.mZoomMatrixBuffer;
        this.mViewPortHandler.zoom(f, f2, mPPointF.x, -mPPointF.y, matrix);
        this.mViewPortHandler.refresh(matrix, (View)this, false);
    }

}

