package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.Range;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class BarChartRenderer extends BarLineScatterCandleBubbleRenderer {
   protected Paint mBarBorderPaint;
   protected BarBuffer[] mBarBuffers;
   protected RectF mBarRect = new RectF();
   private RectF mBarShadowRectBuffer = new RectF();
   protected BarDataProvider mChart;
   protected Paint mShadowPaint;

   public BarChartRenderer(BarDataProvider var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = var1;
      this.mHighlightPaint = new Paint(1);
      this.mHighlightPaint.setStyle(Style.FILL);
      this.mHighlightPaint.setColor(Color.rgb(0, 0, 0));
      this.mHighlightPaint.setAlpha(120);
      Paint var4 = new Paint(1);
      this.mShadowPaint = var4;
      var4.setStyle(Style.FILL);
      var4 = new Paint(1);
      this.mBarBorderPaint = var4;
      var4.setStyle(Style.STROKE);
   }

   public void drawData(Canvas var1) {
      BarData var2 = this.mChart.getBarData();

      for(int var3 = 0; var3 < var2.getDataSetCount(); ++var3) {
         IBarDataSet var4 = (IBarDataSet)var2.getDataSetByIndex(var3);
         if (var4.isVisible()) {
            this.drawDataSet(var1, var4, var3);
         }
      }

   }

   protected void drawDataSet(Canvas var1, IBarDataSet var2, int var3) {
      Transformer var4 = this.mChart.getTransformer(var2.getAxisDependency());
      this.mBarBorderPaint.setColor(var2.getBarBorderColor());
      this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(var2.getBarBorderWidth()));
      float var5 = var2.getBarBorderWidth();
      byte var6 = 0;
      boolean var7 = true;
      boolean var8;
      if (var5 > 0.0F) {
         var8 = true;
      } else {
         var8 = false;
      }

      float var9 = this.mAnimator.getPhaseX();
      float var10 = this.mAnimator.getPhaseY();
      int var11;
      int var12;
      float var13;
      if (this.mChart.isDrawBarShadowEnabled()) {
         this.mShadowPaint.setColor(var2.getBarShadowColor());
         var5 = this.mChart.getBarData().getBarWidth() / 2.0F;
         var11 = Math.min((int)Math.ceil((double)((float)var2.getEntryCount() * var9)), var2.getEntryCount());

         for(var12 = 0; var12 < var11; ++var12) {
            var13 = ((BarEntry)var2.getEntryForIndex(var12)).getX();
            this.mBarShadowRectBuffer.left = var13 - var5;
            this.mBarShadowRectBuffer.right = var13 + var5;
            var4.rectValueToPixel(this.mBarShadowRectBuffer);
            if (this.mViewPortHandler.isInBoundsLeft(this.mBarShadowRectBuffer.right)) {
               if (!this.mViewPortHandler.isInBoundsRight(this.mBarShadowRectBuffer.left)) {
                  break;
               }

               this.mBarShadowRectBuffer.top = this.mViewPortHandler.contentTop();
               this.mBarShadowRectBuffer.bottom = this.mViewPortHandler.contentBottom();
               var1.drawRect(this.mBarShadowRectBuffer, this.mShadowPaint);
            }
         }
      }

      BarBuffer var14 = this.mBarBuffers[var3];
      var14.setPhases(var9, var10);
      var14.setDataSet(var3);
      var14.setInverted(this.mChart.isInverted(var2.getAxisDependency()));
      var14.setBarWidth(this.mChart.getBarData().getBarWidth());
      var14.feed(var2);
      var4.pointValuesToPixel(var14.buffer);
      boolean var16;
      if (var2.getColors().size() == 1) {
         var16 = var7;
      } else {
         var16 = false;
      }

      var12 = var6;
      if (var16) {
         this.mRenderPaint.setColor(var2.getColor());
         var12 = var6;
      }

      for(; var12 < var14.size(); var12 += 4) {
         ViewPortHandler var17 = this.mViewPortHandler;
         float[] var15 = var14.buffer;
         int var21 = var12 + 2;
         if (var17.isInBoundsLeft(var15[var21])) {
            if (!this.mViewPortHandler.isInBoundsRight(var14.buffer[var12])) {
               break;
            }

            if (!var16) {
               this.mRenderPaint.setColor(var2.getColor(var12 / 4));
            }

            if (var2.getGradientColor() != null) {
               GradientColor var18 = var2.getGradientColor();
               this.mRenderPaint.setShader(new LinearGradient(var14.buffer[var12], var14.buffer[var12 + 3], var14.buffer[var12], var14.buffer[var12 + 1], var18.getStartColor(), var18.getEndColor(), TileMode.MIRROR));
            }

            int var22;
            if (var2.getGradientColors() != null) {
               Paint var19 = this.mRenderPaint;
               var10 = var14.buffer[var12];
               var13 = var14.buffer[var12 + 3];
               var5 = var14.buffer[var12];
               var9 = var14.buffer[var12 + 1];
               var22 = var12 / 4;
               var19.setShader(new LinearGradient(var10, var13, var5, var9, var2.getGradientColor(var22).getStartColor(), var2.getGradientColor(var22).getEndColor(), TileMode.MIRROR));
            }

            var5 = var14.buffer[var12];
            float[] var20 = var14.buffer;
            var22 = var12 + 1;
            var9 = var20[var22];
            var10 = var14.buffer[var21];
            var20 = var14.buffer;
            var11 = var12 + 3;
            var1.drawRect(var5, var9, var10, var20[var11], this.mRenderPaint);
            if (var8) {
               var1.drawRect(var14.buffer[var12], var14.buffer[var22], var14.buffer[var21], var14.buffer[var11], this.mBarBorderPaint);
            }
         }
      }

   }

   public void drawExtras(Canvas var1) {
   }

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      BarData var3 = this.mChart.getBarData();
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Highlight var6 = var2[var5];
         IBarDataSet var7 = (IBarDataSet)var3.getDataSetByIndex(var6.getDataSetIndex());
         if (var7 != null && var7.isHighlightEnabled()) {
            BarEntry var8 = (BarEntry)var7.getEntryForXValue(var6.getX(), var6.getY());
            if (this.isInBoundsX(var8, var7)) {
               Transformer var9 = this.mChart.getTransformer(var7.getAxisDependency());
               this.mHighlightPaint.setColor(var7.getHighLightColor());
               this.mHighlightPaint.setAlpha(var7.getHighLightAlpha());
               boolean var10;
               if (var6.getStackIndex() >= 0 && var8.isStacked()) {
                  var10 = true;
               } else {
                  var10 = false;
               }

               float var11;
               float var12;
               if (var10) {
                  if (this.mChart.isHighlightFullBarEnabled()) {
                     var11 = var8.getPositiveSum();
                     var12 = -var8.getNegativeSum();
                  } else {
                     Range var13 = var8.getRanges()[var6.getStackIndex()];
                     var11 = var13.from;
                     var12 = var13.to;
                  }
               } else {
                  var11 = var8.getY();
                  var12 = 0.0F;
               }

               this.prepareBarHighlight(var8.getX(), var11, var12, var3.getBarWidth() / 2.0F, var9);
               this.setHighlightDrawPos(var6, this.mBarRect);
               var1.drawRect(this.mBarRect, this.mHighlightPaint);
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
         List var2 = this.mChart.getBarData().getDataSets();
         float var3 = Utils.convertDpToPixel(4.5F);
         boolean var4 = this.mChart.isDrawValueAboveBarEnabled();

         float var7;
         for(int var5 = 0; var5 < this.mChart.getBarData().getDataSetCount(); var3 = var7) {
            IBarDataSet var6 = (IBarDataSet)var2.get(var5);
            List var8;
            if (!this.shouldDrawValues(var6)) {
               var7 = var3;
               var8 = var2;
            } else {
               this.applyValueTextStyle(var6);
               boolean var9 = this.mChart.isInverted(var6.getAxisDependency());
               float var10 = (float)Utils.calcTextHeight(this.mValuePaint, "8");
               float var11;
               if (var4) {
                  var11 = -var3;
               } else {
                  var11 = var10 + var3;
               }

               float var12;
               if (var4) {
                  var12 = var10 + var3;
               } else {
                  var12 = -var3;
               }

               float var13 = var11;
               var7 = var12;
               if (var9) {
                  var13 = -var11 - var10;
                  var7 = -var12 - var10;
               }

               BarBuffer var14 = this.mBarBuffers[var5];
               float var15 = this.mAnimator.getPhaseY();
               ValueFormatter var16 = var6.getValueFormatter();
               MPPointF var17 = MPPointF.getInstance(var6.getIconsOffset());
               var17.x = Utils.convertDpToPixel(var17.x);
               var17.y = Utils.convertDpToPixel(var17.y);
               int var18;
               float[] var20;
               int var21;
               int var22;
               String var23;
               float var24;
               MPPointF var40;
               if (!var6.isStacked()) {
                  var18 = 0;
                  var8 = var2;
                  ValueFormatter var19 = var16;

                  MPPointF var33;
                  for(var33 = var17; (float)var18 < (float)var14.buffer.length * this.mAnimator.getPhaseX(); var18 += 4) {
                     var12 = (var14.buffer[var18] + var14.buffer[var18 + 2]) / 2.0F;
                     if (!this.mViewPortHandler.isInBoundsRight(var12)) {
                        break;
                     }

                     ViewPortHandler var36 = this.mViewPortHandler;
                     var20 = var14.buffer;
                     var21 = var18 + 1;
                     if (var36.isInBoundsY(var20[var21]) && this.mViewPortHandler.isInBoundsLeft(var12)) {
                        var22 = var18 / 4;
                        BarEntry var37 = (BarEntry)var6.getEntryForIndex(var22);
                        var10 = var37.getY();
                        if (var6.isDrawValuesEnabled()) {
                           var23 = var19.getBarLabel(var37);
                           var20 = var14.buffer;
                           if (var10 >= 0.0F) {
                              var11 = var20[var21] + var13;
                           } else {
                              var11 = var20[var18 + 3] + var7;
                           }

                           this.drawValue(var1, var23, var12, var11, var6.getValueTextColor(var22));
                        }

                        if (var37.getIcon() != null && var6.isDrawIconsEnabled()) {
                           Drawable var39 = var37.getIcon();
                           if (var10 >= 0.0F) {
                              var11 = var14.buffer[var21] + var13;
                           } else {
                              var11 = var14.buffer[var18 + 3] + var7;
                           }

                           var10 = var33.x;
                           var24 = var33.y;
                           Utils.drawImage(var1, var39, (int)(var12 + var10), (int)(var11 + var24), var39.getIntrinsicWidth(), var39.getIntrinsicHeight());
                        }
                     }
                  }

                  var40 = var33;
                  var9 = var4;
               } else {
                  List var38 = var2;
                  Transformer var34 = this.mChart.getTransformer(var6.getAxisDependency());
                  var18 = 0;
                  var22 = 0;
                  var11 = var3;

                  label176:
                  while(true) {
                     label174:
                     while(true) {
                        var40 = var17;
                        var3 = var11;
                        var9 = var4;
                        var8 = var38;
                        if ((float)var18 >= (float)var6.getEntryCount() * this.mAnimator.getPhaseX()) {
                           break label176;
                        }

                        BarEntry var35 = (BarEntry)var6.getEntryForIndex(var18);
                        var20 = var35.getYVals();
                        var24 = (var14.buffer[var22] + var14.buffer[var22 + 2]) / 2.0F;
                        int var25 = var6.getValueTextColor(var18);
                        float var27;
                        float[] var41;
                        if (var20 == null) {
                           if (!this.mViewPortHandler.isInBoundsRight(var24)) {
                              var40 = var17;
                              var3 = var11;
                              var9 = var4;
                              var8 = var38;
                              break label176;
                           }

                           ViewPortHandler var43 = this.mViewPortHandler;
                           var41 = var14.buffer;
                           var21 = var22 + 1;
                           if (!var43.isInBoundsY(var41[var21]) || !this.mViewPortHandler.isInBoundsLeft(var24)) {
                              continue;
                           }

                           if (var6.isDrawValuesEnabled()) {
                              var23 = var16.getBarLabel(var35);
                              var12 = var14.buffer[var21];
                              if (var35.getY() >= 0.0F) {
                                 var3 = var13;
                              } else {
                                 var3 = var7;
                              }

                              this.drawValue(var1, var23, var24, var12 + var3, var25);
                           }

                           if (var35.getIcon() != null && var6.isDrawIconsEnabled()) {
                              Drawable var42 = var35.getIcon();
                              var12 = var14.buffer[var21];
                              if (var35.getY() >= 0.0F) {
                                 var3 = var13;
                              } else {
                                 var3 = var7;
                              }

                              var10 = var17.x;
                              var27 = var17.y;
                              Utils.drawImage(var1, var42, (int)(var10 + var24), (int)(var12 + var3 + var27), var42.getIntrinsicWidth(), var42.getIntrinsicHeight());
                           }
                           break;
                        }

                        float[] var26 = var20;
                        int var28 = var20.length * 2;
                        var41 = new float[var28];
                        var3 = -var35.getNegativeSum();
                        int var29 = 0;
                        var21 = 0;

                        for(var10 = 0.0F; var29 < var28; ++var21) {
                           var12 = var26[var21];
                           float var45;
                           int var30 = (var45 = var12 - 0.0F) == 0.0F ? 0 : (var45 < 0.0F ? -1 : 1);
                           if (var30 != 0 || var10 != 0.0F && var3 != 0.0F) {
                              if (var30 >= 0) {
                                 var10 += var12;
                                 var12 = var10;
                              } else {
                                 var27 = var3 - var12;
                                 var12 = var3;
                                 var3 = var27;
                              }
                           }

                           var41[var29 + 1] = var12 * var15;
                           var29 += 2;
                        }

                        var34.pointValuesToPixel(var41);
                        var29 = 0;
                        var21 = var28;

                        while(true) {
                           if (var29 >= var21) {
                              break label174;
                           }

                           var27 = var26[var29 / 2];
                           boolean var44;
                           if ((var27 != 0.0F || var3 != 0.0F || var10 <= 0.0F) && var27 >= 0.0F) {
                              var44 = false;
                           } else {
                              var44 = true;
                           }

                           float var31 = var41[var29 + 1];
                           if (var44) {
                              var12 = var7;
                           } else {
                              var12 = var13;
                           }

                           var12 += var31;
                           if (!this.mViewPortHandler.isInBoundsRight(var24)) {
                              break label174;
                           }

                           if (this.mViewPortHandler.isInBoundsY(var12) && this.mViewPortHandler.isInBoundsLeft(var24)) {
                              if (var6.isDrawValuesEnabled()) {
                                 this.drawValue(var1, var16.getBarStackedLabel(var27, var35), var24, var12, var25);
                              }

                              if (var35.getIcon() != null && var6.isDrawIconsEnabled()) {
                                 Drawable var32 = var35.getIcon();
                                 Utils.drawImage(var1, var32, (int)(var24 + var17.x), (int)(var12 + var17.y), var32.getIntrinsicWidth(), var32.getIntrinsicHeight());
                              }
                           }

                           var29 += 2;
                        }
                     }

                     if (var20 == null) {
                        var22 += 4;
                     } else {
                        var22 += var20.length * 4;
                     }

                     ++var18;
                  }
               }

               var7 = var3;
               var4 = var9;
               MPPointF.recycleInstance(var40);
            }

            ++var5;
            var2 = var8;
         }
      }

   }

   public void initBuffers() {
      BarData var1 = this.mChart.getBarData();
      this.mBarBuffers = new BarBuffer[var1.getDataSetCount()];

      for(int var2 = 0; var2 < this.mBarBuffers.length; ++var2) {
         IBarDataSet var3 = (IBarDataSet)var1.getDataSetByIndex(var2);
         BarBuffer[] var4 = this.mBarBuffers;
         int var5 = var3.getEntryCount();
         int var6;
         if (var3.isStacked()) {
            var6 = var3.getStackSize();
         } else {
            var6 = 1;
         }

         var4[var2] = new BarBuffer(var5 * 4 * var6, var1.getDataSetCount(), var3.isStacked());
      }

   }

   protected void prepareBarHighlight(float var1, float var2, float var3, float var4, Transformer var5) {
      this.mBarRect.set(var1 - var4, var2, var1 + var4, var3);
      var5.rectToPixelPhase(this.mBarRect, this.mAnimator.getPhaseY());
   }

   protected void setHighlightDrawPos(Highlight var1, RectF var2) {
      var1.setDraw(var2.centerX(), var2.top);
   }
}
