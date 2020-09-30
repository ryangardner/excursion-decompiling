package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class AxisRenderer extends Renderer {
   protected AxisBase mAxis;
   protected Paint mAxisLabelPaint;
   protected Paint mAxisLinePaint;
   protected Paint mGridPaint;
   protected Paint mLimitLinePaint;
   protected Transformer mTrans;

   public AxisRenderer(ViewPortHandler var1, Transformer var2, AxisBase var3) {
      super(var1);
      this.mTrans = var2;
      this.mAxis = var3;
      if (this.mViewPortHandler != null) {
         this.mAxisLabelPaint = new Paint(1);
         Paint var4 = new Paint();
         this.mGridPaint = var4;
         var4.setColor(-7829368);
         this.mGridPaint.setStrokeWidth(1.0F);
         this.mGridPaint.setStyle(Style.STROKE);
         this.mGridPaint.setAlpha(90);
         var4 = new Paint();
         this.mAxisLinePaint = var4;
         var4.setColor(-16777216);
         this.mAxisLinePaint.setStrokeWidth(1.0F);
         this.mAxisLinePaint.setStyle(Style.STROKE);
         var4 = new Paint(1);
         this.mLimitLinePaint = var4;
         var4.setStyle(Style.STROKE);
      }

   }

   public void computeAxis(float var1, float var2, boolean var3) {
      float var4 = var1;
      float var5 = var2;
      if (this.mViewPortHandler != null) {
         var4 = var1;
         var5 = var2;
         if (this.mViewPortHandler.contentWidth() > 10.0F) {
            var4 = var1;
            var5 = var2;
            if (!this.mViewPortHandler.isFullyZoomedOutY()) {
               MPPointD var6 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
               MPPointD var7 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom());
               double var8;
               if (!var3) {
                  var1 = (float)var7.y;
                  var8 = var6.y;
               } else {
                  var1 = (float)var6.y;
                  var8 = var7.y;
               }

               var5 = (float)var8;
               MPPointD.recycleInstance(var6);
               MPPointD.recycleInstance(var7);
               var4 = var1;
            }
         }
      }

      this.computeAxisValues(var4, var5);
   }

   protected void computeAxisValues(float var1, float var2) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.mergeProviderType(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.mergeTypeToSubRef(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Unknown Source)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Unknown Source)\n\tat the.bytecode.club.bytecodeviewer.util.Dex2Jar.dex2Jar(Dex2Jar.java:54)\n\tat the.bytecode.club.bytecodeviewer.BytecodeViewer$8.run(BytecodeViewer.java:957)\n");
   }

   public Paint getPaintAxisLabels() {
      return this.mAxisLabelPaint;
   }

   public Paint getPaintAxisLine() {
      return this.mAxisLinePaint;
   }

   public Paint getPaintGrid() {
      return this.mGridPaint;
   }

   public Transformer getTransformer() {
      return this.mTrans;
   }

   public abstract void renderAxisLabels(Canvas var1);

   public abstract void renderAxisLine(Canvas var1);

   public abstract void renderGridLines(Canvas var1);

   public abstract void renderLimitLines(Canvas var1);
}
