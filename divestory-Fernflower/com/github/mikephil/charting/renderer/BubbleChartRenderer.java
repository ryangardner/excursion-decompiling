package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.Iterator;
import java.util.List;

public class BubbleChartRenderer extends BarLineScatterCandleBubbleRenderer {
   private float[] _hsvBuffer = new float[3];
   protected BubbleDataProvider mChart;
   private float[] pointBuffer = new float[2];
   private float[] sizeBuffer = new float[4];

   public BubbleChartRenderer(BubbleDataProvider var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = var1;
      this.mRenderPaint.setStyle(Style.FILL);
      this.mHighlightPaint.setStyle(Style.STROKE);
      this.mHighlightPaint.setStrokeWidth(Utils.convertDpToPixel(1.5F));
   }

   public void drawData(Canvas var1) {
      Iterator var2 = this.mChart.getBubbleData().getDataSets().iterator();

      while(var2.hasNext()) {
         IBubbleDataSet var3 = (IBubbleDataSet)var2.next();
         if (var3.isVisible()) {
            this.drawDataSet(var1, var3);
         }
      }

   }

   protected void drawDataSet(Canvas var1, IBubbleDataSet var2) {
      if (var2.getEntryCount() >= 1) {
         Transformer var3 = this.mChart.getTransformer(var2.getAxisDependency());
         float var4 = this.mAnimator.getPhaseY();
         this.mXBounds.set(this.mChart, var2);
         float[] var5 = this.sizeBuffer;
         var5[0] = 0.0F;
         var5[2] = 1.0F;
         var3.pointValuesToPixel(var5);
         boolean var6 = var2.isNormalizeSizeEnabled();
         var5 = this.sizeBuffer;
         float var7 = Math.abs(var5[2] - var5[0]);
         float var8 = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), var7);

         for(int var9 = this.mXBounds.min; var9 <= this.mXBounds.range + this.mXBounds.min; ++var9) {
            BubbleEntry var11 = (BubbleEntry)var2.getEntryForIndex(var9);
            this.pointBuffer[0] = var11.getX();
            this.pointBuffer[1] = var11.getY() * var4;
            var3.pointValuesToPixel(this.pointBuffer);
            var7 = this.getShapeSize(var11.getSize(), var2.getMaxSize(), var8, var6) / 2.0F;
            if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + var7) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - var7) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + var7)) {
               if (!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - var7)) {
                  break;
               }

               int var10 = var2.getColor((int)var11.getX());
               this.mRenderPaint.setColor(var10);
               var5 = this.pointBuffer;
               var1.drawCircle(var5[0], var5[1], var7, this.mRenderPaint);
            }
         }

      }
   }

   public void drawExtras(Canvas var1) {
   }

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      BubbleData var3 = this.mChart.getBubbleData();
      float var4 = this.mAnimator.getPhaseY();
      int var5 = var2.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Highlight var7 = var2[var6];
         IBubbleDataSet var8 = (IBubbleDataSet)var3.getDataSetByIndex(var7.getDataSetIndex());
         if (var8 != null && var8.isHighlightEnabled()) {
            BubbleEntry var9 = (BubbleEntry)var8.getEntryForXValue(var7.getX(), var7.getY());
            if (var9.getY() == var7.getY() && this.isInBoundsX(var9, var8)) {
               Transformer var10 = this.mChart.getTransformer(var8.getAxisDependency());
               float[] var11 = this.sizeBuffer;
               var11[0] = 0.0F;
               var11[2] = 1.0F;
               var10.pointValuesToPixel(var11);
               boolean var12 = var8.isNormalizeSizeEnabled();
               var11 = this.sizeBuffer;
               float var13 = Math.abs(var11[2] - var11[0]);
               var13 = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), var13);
               this.pointBuffer[0] = var9.getX();
               this.pointBuffer[1] = var9.getY() * var4;
               var10.pointValuesToPixel(this.pointBuffer);
               float[] var17 = this.pointBuffer;
               var7.setDraw(var17[0], var17[1]);
               var13 = this.getShapeSize(var9.getSize(), var8.getMaxSize(), var13, var12) / 2.0F;
               if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + var13) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - var13) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + var13)) {
                  if (!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - var13)) {
                     break;
                  }

                  int var14 = var8.getColor((int)var9.getX());
                  Color.RGBToHSV(Color.red(var14), Color.green(var14), Color.blue(var14), this._hsvBuffer);
                  float[] var15 = this._hsvBuffer;
                  var15[2] *= 0.5F;
                  var14 = Color.HSVToColor(Color.alpha(var14), this._hsvBuffer);
                  this.mHighlightPaint.setColor(var14);
                  this.mHighlightPaint.setStrokeWidth(var8.getHighlightCircleWidth());
                  float[] var16 = this.pointBuffer;
                  var1.drawCircle(var16[0], var16[1], var13, this.mHighlightPaint);
               }
            }
         }
      }

   }

   public void drawValue(Canvas var1, String var2, float var3, float var4, int var5) {
      this.mValuePaint.setColor(var5);
      var1.drawText(var2, var3, var4, this.mValuePaint);
   }

   public void drawValues(Canvas var1) {
      BubbleData var2 = this.mChart.getBubbleData();
      if (var2 != null) {
         if (this.isDrawingValuesAllowed(this.mChart)) {
            List var3 = var2.getDataSets();
            float var4 = (float)Utils.calcTextHeight(this.mValuePaint, "1");

            for(int var5 = 0; var5 < var3.size(); ++var5) {
               IBubbleDataSet var6 = (IBubbleDataSet)var3.get(var5);
               if (this.shouldDrawValues(var6) && var6.getEntryCount() >= 1) {
                  this.applyValueTextStyle(var6);
                  float var7 = Math.max(0.0F, Math.min(1.0F, this.mAnimator.getPhaseX()));
                  float var8 = this.mAnimator.getPhaseY();
                  this.mXBounds.set(this.mChart, var6);
                  float[] var9 = this.mChart.getTransformer(var6.getAxisDependency()).generateTransformedValuesBubble(var6, var8, this.mXBounds.min, this.mXBounds.max);
                  if (var7 != 1.0F) {
                     var8 = var7;
                  }

                  ValueFormatter var10 = var6.getValueFormatter();
                  MPPointF var11 = MPPointF.getInstance(var6.getIconsOffset());
                  var11.x = Utils.convertDpToPixel(var11.x);
                  var11.y = Utils.convertDpToPixel(var11.y);

                  for(int var12 = 0; var12 < var9.length; var12 += 2) {
                     int var13 = var12 / 2;
                     int var14 = var6.getValueTextColor(this.mXBounds.min + var13);
                     var14 = Color.argb(Math.round(255.0F * var8), Color.red(var14), Color.green(var14), Color.blue(var14));
                     var7 = var9[var12];
                     float var15 = var9[var12 + 1];
                     if (!this.mViewPortHandler.isInBoundsRight(var7)) {
                        break;
                     }

                     if (this.mViewPortHandler.isInBoundsLeft(var7) && this.mViewPortHandler.isInBoundsY(var15)) {
                        BubbleEntry var16 = (BubbleEntry)var6.getEntryForIndex(var13 + this.mXBounds.min);
                        if (var6.isDrawValuesEnabled()) {
                           this.drawValue(var1, var10.getBubbleLabel(var16), var7, var15 + 0.5F * var4, var14);
                        }

                        if (var16.getIcon() != null && var6.isDrawIconsEnabled()) {
                           Drawable var17 = var16.getIcon();
                           Utils.drawImage(var1, var17, (int)(var7 + var11.x), (int)(var15 + var11.y), var17.getIntrinsicWidth(), var17.getIntrinsicHeight());
                        }
                     }
                  }

                  MPPointF.recycleInstance(var11);
               }
            }
         }

      }
   }

   protected float getShapeSize(float var1, float var2, float var3, boolean var4) {
      float var5 = var1;
      if (var4) {
         if (var2 == 0.0F) {
            var5 = 1.0F;
         } else {
            var5 = (float)Math.sqrt((double)(var1 / var2));
         }
      }

      return var3 * var5;
   }

   public void initBuffers() {
   }
}
