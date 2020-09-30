package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Path;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRendererRadarChart extends YAxisRenderer {
   private RadarChart mChart;
   private Path mRenderLimitLinesPathBuffer = new Path();

   public YAxisRendererRadarChart(ViewPortHandler var1, YAxis var2, RadarChart var3) {
      super(var1, var2, (Transformer)null);
      this.mChart = var3;
   }

   protected void computeAxisValues(float var1, float var2) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.mergeProviderType(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.mergeTypeToSubRef(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Unknown Source)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Unknown Source)\n\tat the.bytecode.club.bytecodeviewer.util.Dex2Jar.dex2Jar(Dex2Jar.java:54)\n\tat the.bytecode.club.bytecodeviewer.BytecodeViewer$8.run(BytecodeViewer.java:957)\n");
   }

   public void renderAxisLabels(Canvas var1) {
      if (this.mYAxis.isEnabled() && this.mYAxis.isDrawLabelsEnabled()) {
         this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
         this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
         this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
         MPPointF var2 = this.mChart.getCenterOffsets();
         MPPointF var3 = MPPointF.getInstance(0.0F, 0.0F);
         float var4 = this.mChart.getFactor();
         int var5 = this.mYAxis.isDrawBottomYLabelEntryEnabled() ^ 1;
         int var6;
         if (this.mYAxis.isDrawTopYLabelEntryEnabled()) {
            var6 = this.mYAxis.mEntryCount;
         } else {
            var6 = this.mYAxis.mEntryCount - 1;
         }

         while(var5 < var6) {
            Utils.getPosition(var2, (this.mYAxis.mEntries[var5] - this.mYAxis.mAxisMinimum) * var4, this.mChart.getRotationAngle(), var3);
            var1.drawText(this.mYAxis.getFormattedLabel(var5), var3.x + 10.0F, var3.y, this.mAxisLabelPaint);
            ++var5;
         }

         MPPointF.recycleInstance(var2);
         MPPointF.recycleInstance(var3);
      }

   }

   public void renderLimitLines(Canvas var1) {
      List var2 = this.mYAxis.getLimitLines();
      if (var2 != null) {
         float var3 = this.mChart.getSliceAngle();
         float var4 = this.mChart.getFactor();
         MPPointF var5 = this.mChart.getCenterOffsets();
         MPPointF var6 = MPPointF.getInstance(0.0F, 0.0F);

         for(int var7 = 0; var7 < var2.size(); ++var7) {
            LimitLine var8 = (LimitLine)var2.get(var7);
            if (var8.isEnabled()) {
               this.mLimitLinePaint.setColor(var8.getLineColor());
               this.mLimitLinePaint.setPathEffect(var8.getDashPathEffect());
               this.mLimitLinePaint.setStrokeWidth(var8.getLineWidth());
               float var9 = var8.getLimit();
               float var10 = this.mChart.getYChartMin();
               Path var12 = this.mRenderLimitLinesPathBuffer;
               var12.reset();

               for(int var11 = 0; var11 < ((IRadarDataSet)((RadarData)this.mChart.getData()).getMaxEntryCountSet()).getEntryCount(); ++var11) {
                  Utils.getPosition(var5, (var9 - var10) * var4, (float)var11 * var3 + this.mChart.getRotationAngle(), var6);
                  if (var11 == 0) {
                     var12.moveTo(var6.x, var6.y);
                  } else {
                     var12.lineTo(var6.x, var6.y);
                  }
               }

               var12.close();
               var1.drawPath(var12, this.mLimitLinePaint);
            }
         }

         MPPointF.recycleInstance(var5);
         MPPointF.recycleInstance(var6);
      }
   }
}
