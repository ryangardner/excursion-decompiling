/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.PieRadarHighlighter;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

public class RadarHighlighter
extends PieRadarHighlighter<RadarChart> {
    public RadarHighlighter(RadarChart radarChart) {
        super(radarChart);
    }

    @Override
    protected Highlight getClosestHighlight(int n, float f, float f2) {
        List<Highlight> list = this.getHighlightsAtIndex(n);
        float f3 = ((RadarChart)this.mChart).distanceToCenter(f, f2) / ((RadarChart)this.mChart).getFactor();
        Highlight highlight = null;
        f2 = Float.MAX_VALUE;
        n = 0;
        while (n < list.size()) {
            Highlight highlight2 = list.get(n);
            float f4 = Math.abs(highlight2.getY() - f3);
            f = f2;
            if (f4 < f2) {
                highlight = highlight2;
                f = f4;
            }
            ++n;
            f2 = f;
        }
        return highlight;
    }

    protected List<Highlight> getHighlightsAtIndex(int n) {
        this.mHighlightBuffer.clear();
        float f = ((RadarChart)this.mChart).getAnimator().getPhaseX();
        float f2 = ((RadarChart)this.mChart).getAnimator().getPhaseY();
        float f3 = ((RadarChart)this.mChart).getSliceAngle();
        float f4 = ((RadarChart)this.mChart).getFactor();
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        int n2 = 0;
        do {
            int n3 = n;
            if (n2 >= ((RadarData)((RadarChart)this.mChart).getData()).getDataSetCount()) return this.mHighlightBuffer;
            Object t = ((RadarData)((RadarChart)this.mChart).getData()).getDataSetByIndex(n2);
            Object t2 = t.getEntryForIndex(n3);
            float f5 = ((BaseEntry)t2).getY();
            float f6 = ((RadarChart)this.mChart).getYChartMin();
            MPPointF mPPointF2 = ((RadarChart)this.mChart).getCenterOffsets();
            float f7 = n3;
            Utils.getPosition(mPPointF2, (f5 - f6) * f4 * f2, f3 * f7 * f + ((RadarChart)this.mChart).getRotationAngle(), mPPointF);
            this.mHighlightBuffer.add(new Highlight(f7, ((BaseEntry)t2).getY(), mPPointF.x, mPPointF.y, n2, t.getAxisDependency()));
            ++n2;
        } while (true);
    }
}

