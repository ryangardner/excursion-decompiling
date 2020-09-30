/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.util.Log
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.util.Log;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ScatterDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.renderer.BubbleChartRenderer;
import com.github.mikephil.charting.renderer.CandleStickChartRenderer;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.renderer.ScatterChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CombinedChartRenderer
extends DataRenderer {
    protected WeakReference<Chart> mChart;
    protected List<Highlight> mHighlightBuffer = new ArrayList<Highlight>();
    protected List<DataRenderer> mRenderers = new ArrayList<DataRenderer>(5);

    public CombinedChartRenderer(CombinedChart combinedChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = new WeakReference<CombinedChart>(combinedChart);
        this.createRenderers();
    }

    public void createRenderers() {
        this.mRenderers.clear();
        CombinedChart combinedChart = (CombinedChart)this.mChart.get();
        if (combinedChart == null) {
            return;
        }
        CombinedChart.DrawOrder[] arrdrawOrder = combinedChart.getDrawOrder();
        int n = arrdrawOrder.length;
        int n2 = 0;
        while (n2 < n) {
            CombinedChart.DrawOrder drawOrder = arrdrawOrder[n2];
            int n3 = 1.$SwitchMap$com$github$mikephil$charting$charts$CombinedChart$DrawOrder[drawOrder.ordinal()];
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 != 4) {
                            if (n3 == 5 && combinedChart.getScatterData() != null) {
                                this.mRenderers.add(new ScatterChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                            }
                        } else if (combinedChart.getCandleData() != null) {
                            this.mRenderers.add(new CandleStickChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                        }
                    } else if (combinedChart.getLineData() != null) {
                        this.mRenderers.add(new LineChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                    }
                } else if (combinedChart.getBubbleData() != null) {
                    this.mRenderers.add(new BubbleChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
                }
            } else if (combinedChart.getBarData() != null) {
                this.mRenderers.add(new BarChartRenderer(combinedChart, this.mAnimator, this.mViewPortHandler));
            }
            ++n2;
        }
    }

    @Override
    public void drawData(Canvas canvas) {
        Iterator<DataRenderer> iterator2 = this.mRenderers.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().drawData(canvas);
        }
    }

    @Override
    public void drawExtras(Canvas canvas) {
        Iterator<DataRenderer> iterator2 = this.mRenderers.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().drawExtras(canvas);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        Chart chart = (Chart)this.mChart.get();
        if (chart == null) {
            return;
        }
        Iterator<DataRenderer> iterator2 = this.mRenderers.iterator();
        while (iterator2.hasNext()) {
            void var6_13;
            DataRenderer dataRenderer = iterator2.next();
            Object var6_7 = null;
            if (dataRenderer instanceof BarChartRenderer) {
                BarData barData = ((BarChartRenderer)dataRenderer).mChart.getBarData();
            } else if (dataRenderer instanceof LineChartRenderer) {
                LineData lineData = ((LineChartRenderer)dataRenderer).mChart.getLineData();
            } else if (dataRenderer instanceof CandleStickChartRenderer) {
                CandleData candleData = ((CandleStickChartRenderer)dataRenderer).mChart.getCandleData();
            } else if (dataRenderer instanceof ScatterChartRenderer) {
                ScatterData scatterData = ((ScatterChartRenderer)dataRenderer).mChart.getScatterData();
            } else if (dataRenderer instanceof BubbleChartRenderer) {
                BubbleData bubbleData = ((BubbleChartRenderer)dataRenderer).mChart.getBubbleData();
            }
            int n = var6_13 == null ? -1 : ((CombinedData)chart.getData()).getAllData().indexOf(var6_13);
            this.mHighlightBuffer.clear();
            for (Highlight highlight : arrhighlight) {
                if (highlight.getDataIndex() != n && highlight.getDataIndex() != -1) continue;
                this.mHighlightBuffer.add(highlight);
            }
            List<Highlight> object2 = this.mHighlightBuffer;
            dataRenderer.drawHighlighted(canvas, object2.toArray(new Highlight[object2.size()]));
        }
    }

    @Override
    public void drawValue(Canvas canvas, String string2, float f, float f2, int n) {
        Log.e((String)"MPAndroidChart", (String)"Erroneous call to drawValue() in CombinedChartRenderer!");
    }

    @Override
    public void drawValues(Canvas canvas) {
        Iterator<DataRenderer> iterator2 = this.mRenderers.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().drawValues(canvas);
        }
    }

    public DataRenderer getSubRenderer(int n) {
        if (n >= this.mRenderers.size()) return null;
        if (n >= 0) return this.mRenderers.get(n);
        return null;
    }

    public List<DataRenderer> getSubRenderers() {
        return this.mRenderers;
    }

    @Override
    public void initBuffers() {
        Iterator<DataRenderer> iterator2 = this.mRenderers.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().initBuffers();
        }
    }

    public void setSubRenderers(List<DataRenderer> list) {
        this.mRenderers = list;
    }

}

