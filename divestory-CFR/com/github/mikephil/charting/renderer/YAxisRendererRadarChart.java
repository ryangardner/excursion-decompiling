/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRendererRadarChart
extends YAxisRenderer {
    private RadarChart mChart;
    private Path mRenderLimitLinesPathBuffer = new Path();

    public YAxisRendererRadarChart(ViewPortHandler viewPortHandler, YAxis yAxis, RadarChart radarChart) {
        super(viewPortHandler, yAxis, null);
        this.mChart = radarChart;
    }

    @Override
    protected void computeAxisValues(float f, float f2) {
        throw new RuntimeException("d2j fail translate: java.lang.RuntimeException\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.mergeProviderType(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.mergeTypeToSubRef(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Unknown Source)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Unknown Source)\n\tat the.bytecode.club.bytecodeviewer.util.Dex2Jar.dex2Jar(Dex2Jar.java:54)\n\tat the.bytecode.club.bytecodeviewer.BytecodeViewer$8.run(BytecodeViewer.java:957)\n");
    }

    @Override
    public void renderAxisLabels(Canvas canvas) {
        if (!this.mYAxis.isEnabled()) return;
        if (!this.mYAxis.isDrawLabelsEnabled()) {
            return;
        }
        this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
        MPPointF mPPointF = this.mChart.getCenterOffsets();
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        float f = this.mChart.getFactor();
        int n = this.mYAxis.isDrawBottomYLabelEntryEnabled() ^ true;
        int n2 = this.mYAxis.isDrawTopYLabelEntryEnabled() ? this.mYAxis.mEntryCount : this.mYAxis.mEntryCount - 1;
        do {
            if (n >= n2) {
                MPPointF.recycleInstance(mPPointF);
                MPPointF.recycleInstance(mPPointF2);
                return;
            }
            Utils.getPosition(mPPointF, (this.mYAxis.mEntries[n] - this.mYAxis.mAxisMinimum) * f, this.mChart.getRotationAngle(), mPPointF2);
            canvas.drawText(this.mYAxis.getFormattedLabel(n), mPPointF2.x + 10.0f, mPPointF2.y, this.mAxisLabelPaint);
            ++n;
        } while (true);
    }

    @Override
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> list = this.mYAxis.getLimitLines();
        if (list == null) {
            return;
        }
        float f = this.mChart.getSliceAngle();
        float f2 = this.mChart.getFactor();
        MPPointF mPPointF = this.mChart.getCenterOffsets();
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        int n = 0;
        do {
            if (n >= list.size()) {
                MPPointF.recycleInstance(mPPointF);
                MPPointF.recycleInstance(mPPointF2);
                return;
            }
            LimitLine limitLine = list.get(n);
            if (limitLine.isEnabled()) {
                this.mLimitLinePaint.setColor(limitLine.getLineColor());
                this.mLimitLinePaint.setPathEffect((PathEffect)limitLine.getDashPathEffect());
                this.mLimitLinePaint.setStrokeWidth(limitLine.getLineWidth());
                float f3 = limitLine.getLimit();
                float f4 = this.mChart.getYChartMin();
                limitLine = this.mRenderLimitLinesPathBuffer;
                limitLine.reset();
                for (int i = 0; i < ((IRadarDataSet)((RadarData)this.mChart.getData()).getMaxEntryCountSet()).getEntryCount(); ++i) {
                    Utils.getPosition(mPPointF, (f3 - f4) * f2, (float)i * f + this.mChart.getRotationAngle(), mPPointF2);
                    if (i == 0) {
                        limitLine.moveTo(mPPointF2.x, mPPointF2.y);
                        continue;
                    }
                    limitLine.lineTo(mPPointF2.x, mPPointF2.y);
                }
                limitLine.close();
                canvas.drawPath((Path)limitLine, this.mLimitLinePaint);
            }
            ++n;
        } while (true);
    }
}

