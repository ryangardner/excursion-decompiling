/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.util.Log
 */
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.CombinedHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CombinedDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.renderer.CombinedChartRenderer;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CombinedChart
extends BarLineChartBase<CombinedData>
implements CombinedDataProvider {
    private boolean mDrawBarShadow = false;
    protected DrawOrder[] mDrawOrder;
    private boolean mDrawValueAboveBar = true;
    protected boolean mHighlightFullBarEnabled = false;

    public CombinedChart(Context context) {
        super(context);
    }

    public CombinedChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CombinedChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        if (this.mMarker == null) return;
        if (!this.isDrawMarkersEnabled()) return;
        if (!this.valuesToHighlight()) {
            return;
        }
        int n = 0;
        while (n < this.mIndicesToHighlight.length) {
            Highlight highlight = this.mIndicesToHighlight[n];
            float[] arrf = ((CombinedData)this.mData).getDataSetByHighlight(highlight);
            Entry entry = ((CombinedData)this.mData).getEntryForHighlight(highlight);
            if (entry != null && !((float)arrf.getEntryIndex((Entry)entry) > (float)arrf.getEntryCount() * this.mAnimator.getPhaseX()) && this.mViewPortHandler.isInBounds((arrf = this.getMarkerPosition(highlight))[0], arrf[1])) {
                this.mMarker.refreshContent(entry, highlight);
                this.mMarker.draw(canvas, arrf[0], arrf[1]);
            }
            ++n;
        }
    }

    @Override
    public BarData getBarData() {
        if (this.mData != null) return ((CombinedData)this.mData).getBarData();
        return null;
    }

    @Override
    public BubbleData getBubbleData() {
        if (this.mData != null) return ((CombinedData)this.mData).getBubbleData();
        return null;
    }

    @Override
    public CandleData getCandleData() {
        if (this.mData != null) return ((CombinedData)this.mData).getCandleData();
        return null;
    }

    @Override
    public CombinedData getCombinedData() {
        return (CombinedData)this.mData;
    }

    public DrawOrder[] getDrawOrder() {
        return this.mDrawOrder;
    }

    @Override
    public Highlight getHighlightByTouchPoint(float f, float f2) {
        if (this.mData == null) {
            Log.e((String)"MPAndroidChart", (String)"Can't select by touch. No data set.");
            return null;
        }
        Highlight highlight = this.getHighlighter().getHighlight(f, f2);
        if (highlight == null) return highlight;
        if (this.isHighlightFullBarEnabled()) return new Highlight(highlight.getX(), highlight.getY(), highlight.getXPx(), highlight.getYPx(), highlight.getDataSetIndex(), -1, highlight.getAxis());
        return highlight;
    }

    @Override
    public LineData getLineData() {
        if (this.mData != null) return ((CombinedData)this.mData).getLineData();
        return null;
    }

    @Override
    public ScatterData getScatterData() {
        if (this.mData != null) return ((CombinedData)this.mData).getScatterData();
        return null;
    }

    @Override
    protected void init() {
        super.init();
        this.mDrawOrder = new DrawOrder[]{DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.LINE, DrawOrder.CANDLE, DrawOrder.SCATTER};
        this.setHighlighter(new CombinedHighlighter(this, this));
        this.setHighlightFullBarEnabled(true);
        this.mRenderer = new CombinedChartRenderer(this, this.mAnimator, this.mViewPortHandler);
    }

    @Override
    public boolean isDrawBarShadowEnabled() {
        return this.mDrawBarShadow;
    }

    @Override
    public boolean isDrawValueAboveBarEnabled() {
        return this.mDrawValueAboveBar;
    }

    @Override
    public boolean isHighlightFullBarEnabled() {
        return this.mHighlightFullBarEnabled;
    }

    @Override
    public void setData(CombinedData combinedData) {
        super.setData(combinedData);
        this.setHighlighter(new CombinedHighlighter(this, this));
        ((CombinedChartRenderer)this.mRenderer).createRenderers();
        this.mRenderer.initBuffers();
    }

    public void setDrawBarShadow(boolean bl) {
        this.mDrawBarShadow = bl;
    }

    public void setDrawOrder(DrawOrder[] arrdrawOrder) {
        if (arrdrawOrder == null) return;
        if (arrdrawOrder.length <= 0) {
            return;
        }
        this.mDrawOrder = arrdrawOrder;
    }

    public void setDrawValueAboveBar(boolean bl) {
        this.mDrawValueAboveBar = bl;
    }

    public void setHighlightFullBarEnabled(boolean bl) {
        this.mHighlightFullBarEnabled = bl;
    }

    public static final class DrawOrder
    extends Enum<DrawOrder> {
        private static final /* synthetic */ DrawOrder[] $VALUES;
        public static final /* enum */ DrawOrder BAR;
        public static final /* enum */ DrawOrder BUBBLE;
        public static final /* enum */ DrawOrder CANDLE;
        public static final /* enum */ DrawOrder LINE;
        public static final /* enum */ DrawOrder SCATTER;

        static {
            DrawOrder drawOrder;
            BAR = new DrawOrder();
            BUBBLE = new DrawOrder();
            LINE = new DrawOrder();
            CANDLE = new DrawOrder();
            SCATTER = drawOrder = new DrawOrder();
            $VALUES = new DrawOrder[]{BAR, BUBBLE, LINE, CANDLE, drawOrder};
        }

        public static DrawOrder valueOf(String string2) {
            return Enum.valueOf(DrawOrder.class, string2);
        }

        public static DrawOrder[] values() {
            return (DrawOrder[])$VALUES.clone();
        }
    }

}

