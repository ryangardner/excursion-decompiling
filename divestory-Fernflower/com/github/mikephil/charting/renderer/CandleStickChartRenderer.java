package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.Iterator;
import java.util.List;

public class CandleStickChartRenderer extends LineScatterCandleRadarRenderer {
   private float[] mBodyBuffers = new float[4];
   protected CandleDataProvider mChart;
   private float[] mCloseBuffers = new float[4];
   private float[] mOpenBuffers = new float[4];
   private float[] mRangeBuffers = new float[4];
   private float[] mShadowBuffers = new float[8];

   public CandleStickChartRenderer(CandleDataProvider var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = var1;
   }

   public void drawData(Canvas var1) {
      Iterator var2 = this.mChart.getCandleData().getDataSets().iterator();

      while(var2.hasNext()) {
         ICandleDataSet var3 = (ICandleDataSet)var2.next();
         if (var3.isVisible()) {
            this.drawDataSet(var1, var3);
         }
      }

   }

   protected void drawDataSet(Canvas var1, ICandleDataSet var2) {
      Transformer var3 = this.mChart.getTransformer(var2.getAxisDependency());
      float var4 = this.mAnimator.getPhaseY();
      float var5 = var2.getBarSpace();
      boolean var6 = var2.getShowCandleBar();
      this.mXBounds.set(this.mChart, var2);
      this.mRenderPaint.setStrokeWidth(var2.getShadowWidth());

      for(int var7 = this.mXBounds.min; var7 <= this.mXBounds.range + this.mXBounds.min; ++var7) {
         CandleEntry var8 = (CandleEntry)var2.getEntryForIndex(var7);
         if (var8 != null) {
            float var9 = var8.getX();
            float var10 = var8.getOpen();
            float var11 = var8.getClose();
            float var12 = var8.getHigh();
            float var13 = var8.getLow();
            int var15;
            float[] var17;
            if (var6) {
               var17 = this.mShadowBuffers;
               var17[0] = var9;
               var17[2] = var9;
               var17[4] = var9;
               var17[6] = var9;
               float var19;
               int var14 = (var19 = var10 - var11) == 0.0F ? 0 : (var19 < 0.0F ? -1 : 1);
               if (var14 > 0) {
                  var17[1] = var12 * var4;
                  var17[3] = var10 * var4;
                  var17[5] = var13 * var4;
                  var17[7] = var11 * var4;
               } else if (var10 < var11) {
                  var17[1] = var12 * var4;
                  var17[3] = var11 * var4;
                  var17[5] = var13 * var4;
                  var17[7] = var10 * var4;
               } else {
                  var17[1] = var12 * var4;
                  var17[3] = var10 * var4;
                  var17[5] = var13 * var4;
                  var17[7] = var17[3];
               }

               var3.pointValuesToPixel(this.mShadowBuffers);
               Paint var18;
               if (var2.getShadowColorSameAsCandle()) {
                  if (var14 > 0) {
                     var18 = this.mRenderPaint;
                     if (var2.getDecreasingColor() == 1122867) {
                        var15 = var2.getColor(var7);
                     } else {
                        var15 = var2.getDecreasingColor();
                     }

                     var18.setColor(var15);
                  } else if (var10 < var11) {
                     var18 = this.mRenderPaint;
                     if (var2.getIncreasingColor() == 1122867) {
                        var15 = var2.getColor(var7);
                     } else {
                        var15 = var2.getIncreasingColor();
                     }

                     var18.setColor(var15);
                  } else {
                     var18 = this.mRenderPaint;
                     if (var2.getNeutralColor() == 1122867) {
                        var15 = var2.getColor(var7);
                     } else {
                        var15 = var2.getNeutralColor();
                     }

                     var18.setColor(var15);
                  }
               } else {
                  var18 = this.mRenderPaint;
                  if (var2.getShadowColor() == 1122867) {
                     var15 = var2.getColor(var7);
                  } else {
                     var15 = var2.getShadowColor();
                  }

                  var18.setColor(var15);
               }

               this.mRenderPaint.setStyle(Style.STROKE);
               var1.drawLines(this.mShadowBuffers, this.mRenderPaint);
               var17 = this.mBodyBuffers;
               var17[0] = var9 - 0.5F + var5;
               var17[1] = var11 * var4;
               var17[2] = var9 + 0.5F - var5;
               var17[3] = var10 * var4;
               var3.pointValuesToPixel(var17);
               if (var14 > 0) {
                  if (var2.getDecreasingColor() == 1122867) {
                     this.mRenderPaint.setColor(var2.getColor(var7));
                  } else {
                     this.mRenderPaint.setColor(var2.getDecreasingColor());
                  }

                  this.mRenderPaint.setStyle(var2.getDecreasingPaintStyle());
                  var17 = this.mBodyBuffers;
                  var1.drawRect(var17[0], var17[3], var17[2], var17[1], this.mRenderPaint);
               } else if (var10 < var11) {
                  if (var2.getIncreasingColor() == 1122867) {
                     this.mRenderPaint.setColor(var2.getColor(var7));
                  } else {
                     this.mRenderPaint.setColor(var2.getIncreasingColor());
                  }

                  this.mRenderPaint.setStyle(var2.getIncreasingPaintStyle());
                  var17 = this.mBodyBuffers;
                  var1.drawRect(var17[0], var17[1], var17[2], var17[3], this.mRenderPaint);
               } else {
                  if (var2.getNeutralColor() == 1122867) {
                     this.mRenderPaint.setColor(var2.getColor(var7));
                  } else {
                     this.mRenderPaint.setColor(var2.getNeutralColor());
                  }

                  var17 = this.mBodyBuffers;
                  var1.drawLine(var17[0], var17[1], var17[2], var17[3], this.mRenderPaint);
               }
            } else {
               var17 = this.mRangeBuffers;
               var17[0] = var9;
               var17[1] = var12 * var4;
               var17[2] = var9;
               var17[3] = var13 * var4;
               float[] var16 = this.mOpenBuffers;
               var16[0] = var9 - 0.5F + var5;
               var13 = var10 * var4;
               var16[1] = var13;
               var16[2] = var9;
               var16[3] = var13;
               var16 = this.mCloseBuffers;
               var16[0] = 0.5F + var9 - var5;
               var13 = var11 * var4;
               var16[1] = var13;
               var16[2] = var9;
               var16[3] = var13;
               var3.pointValuesToPixel(var17);
               var3.pointValuesToPixel(this.mOpenBuffers);
               var3.pointValuesToPixel(this.mCloseBuffers);
               if (var10 > var11) {
                  if (var2.getDecreasingColor() == 1122867) {
                     var15 = var2.getColor(var7);
                  } else {
                     var15 = var2.getDecreasingColor();
                  }
               } else if (var10 < var11) {
                  if (var2.getIncreasingColor() == 1122867) {
                     var15 = var2.getColor(var7);
                  } else {
                     var15 = var2.getIncreasingColor();
                  }
               } else if (var2.getNeutralColor() == 1122867) {
                  var15 = var2.getColor(var7);
               } else {
                  var15 = var2.getNeutralColor();
               }

               this.mRenderPaint.setColor(var15);
               var17 = this.mRangeBuffers;
               var1.drawLine(var17[0], var17[1], var17[2], var17[3], this.mRenderPaint);
               var17 = this.mOpenBuffers;
               var1.drawLine(var17[0], var17[1], var17[2], var17[3], this.mRenderPaint);
               var17 = this.mCloseBuffers;
               var1.drawLine(var17[0], var17[1], var17[2], var17[3], this.mRenderPaint);
            }
         }
      }

   }

   public void drawExtras(Canvas var1) {
   }

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      CandleData var3 = this.mChart.getCandleData();
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Highlight var6 = var2[var5];
         ICandleDataSet var7 = (ICandleDataSet)var3.getDataSetByIndex(var6.getDataSetIndex());
         if (var7 != null && var7.isHighlightEnabled()) {
            CandleEntry var8 = (CandleEntry)var7.getEntryForXValue(var6.getX(), var6.getY());
            if (this.isInBoundsX(var8, var7)) {
               float var9 = (var8.getLow() * this.mAnimator.getPhaseY() + var8.getHigh() * this.mAnimator.getPhaseY()) / 2.0F;
               MPPointD var10 = this.mChart.getTransformer(var7.getAxisDependency()).getPixelForValues(var8.getX(), var9);
               var6.setDraw((float)var10.x, (float)var10.y);
               this.drawHighlightLines(var1, (float)var10.x, (float)var10.y, var7);
            }
         }
      }

   }

   public void drawValue(Canvas var1, String var2, float var3, float var4, int var5) {
      this.mValuePaint.setColor(var5);
      var1.drawText(var2, var3, var4, this.mValuePaint);
   }

   public void drawValues(Canvas var1) {
      if (this.isDrawingValuesAllowed(this.mChart)) {
         List var2 = this.mChart.getCandleData().getDataSets();

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            ICandleDataSet var4 = (ICandleDataSet)var2.get(var3);
            if (this.shouldDrawValues(var4) && var4.getEntryCount() >= 1) {
               this.applyValueTextStyle(var4);
               Transformer var5 = this.mChart.getTransformer(var4.getAxisDependency());
               this.mXBounds.set(this.mChart, var4);
               float[] var6 = var5.generateTransformedValuesCandle(var4, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
               float var7 = Utils.convertDpToPixel(5.0F);
               ValueFormatter var8 = var4.getValueFormatter();
               MPPointF var9 = MPPointF.getInstance(var4.getIconsOffset());
               var9.x = Utils.convertDpToPixel(var9.x);
               var9.y = Utils.convertDpToPixel(var9.y);

               for(int var10 = 0; var10 < var6.length; var10 += 2) {
                  float var11 = var6[var10];
                  float var12 = var6[var10 + 1];
                  if (!this.mViewPortHandler.isInBoundsRight(var11)) {
                     break;
                  }

                  if (this.mViewPortHandler.isInBoundsLeft(var11) && this.mViewPortHandler.isInBoundsY(var12)) {
                     int var13 = var10 / 2;
                     CandleEntry var14 = (CandleEntry)var4.getEntryForIndex(this.mXBounds.min + var13);
                     if (var4.isDrawValuesEnabled()) {
                        this.drawValue(var1, var8.getCandleLabel(var14), var11, var12 - var7, var4.getValueTextColor(var13));
                     }

                     if (var14.getIcon() != null && var4.isDrawIconsEnabled()) {
                        Drawable var15 = var14.getIcon();
                        Utils.drawImage(var1, var15, (int)(var11 + var9.x), (int)(var12 + var9.y), var15.getIntrinsicWidth(), var15.getIntrinsicHeight());
                     }
                  }
               }

               MPPointF.recycleInstance(var9);
            }
         }
      }

   }

   public void initBuffers() {
   }
}
