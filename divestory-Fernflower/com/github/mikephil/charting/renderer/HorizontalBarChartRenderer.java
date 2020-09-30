package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.buffer.HorizontalBarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class HorizontalBarChartRenderer extends BarChartRenderer {
   private RectF mBarShadowRectBuffer = new RectF();

   public HorizontalBarChartRenderer(BarDataProvider var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var1, var2, var3);
      this.mValuePaint.setTextAlign(Align.LEFT);
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
      int var12;
      int var13;
      if (this.mChart.isDrawBarShadowEnabled()) {
         this.mShadowPaint.setColor(var2.getBarShadowColor());
         float var11 = this.mChart.getBarData().getBarWidth() / 2.0F;
         var12 = Math.min((int)Math.ceil((double)((float)var2.getEntryCount() * var9)), var2.getEntryCount());

         for(var13 = 0; var13 < var12; ++var13) {
            var5 = ((BarEntry)var2.getEntryForIndex(var13)).getX();
            this.mBarShadowRectBuffer.top = var5 - var11;
            this.mBarShadowRectBuffer.bottom = var5 + var11;
            var4.rectValueToPixel(this.mBarShadowRectBuffer);
            if (this.mViewPortHandler.isInBoundsTop(this.mBarShadowRectBuffer.bottom)) {
               if (!this.mViewPortHandler.isInBoundsBottom(this.mBarShadowRectBuffer.top)) {
                  break;
               }

               this.mBarShadowRectBuffer.left = this.mViewPortHandler.contentLeft();
               this.mBarShadowRectBuffer.right = this.mViewPortHandler.contentRight();
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

      var13 = var6;
      if (var16) {
         this.mRenderPaint.setColor(var2.getColor());
         var13 = var6;
      }

      for(; var13 < var14.size(); var13 += 4) {
         ViewPortHandler var15 = this.mViewPortHandler;
         float[] var17 = var14.buffer;
         int var19 = var13 + 3;
         if (!var15.isInBoundsTop(var17[var19])) {
            break;
         }

         var15 = this.mViewPortHandler;
         var17 = var14.buffer;
         var12 = var13 + 1;
         if (var15.isInBoundsBottom(var17[var12])) {
            if (!var16) {
               this.mRenderPaint.setColor(var2.getColor(var13 / 4));
            }

            var5 = var14.buffer[var13];
            var10 = var14.buffer[var12];
            var17 = var14.buffer;
            int var18 = var13 + 2;
            var1.drawRect(var5, var10, var17[var18], var14.buffer[var19], this.mRenderPaint);
            if (var8) {
               var1.drawRect(var14.buffer[var13], var14.buffer[var12], var14.buffer[var18], var14.buffer[var19], this.mBarBorderPaint);
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
         float var3 = Utils.convertDpToPixel(5.0F);
         boolean var4 = this.mChart.isDrawValueAboveBarEnabled();

         for(int var5 = 0; var5 < this.mChart.getBarData().getDataSetCount(); ++var5) {
            IBarDataSet var6 = (IBarDataSet)var2.get(var5);
            if (this.shouldDrawValues(var6)) {
               boolean var7 = this.mChart.isInverted(var6.getAxisDependency());
               this.applyValueTextStyle(var6);
               float var8 = (float)Utils.calcTextHeight(this.mValuePaint, "10") / 2.0F;
               ValueFormatter var9 = var6.getValueFormatter();
               BarBuffer var10 = this.mBarBuffers[var5];
               float var11 = this.mAnimator.getPhaseY();
               MPPointF var12 = MPPointF.getInstance(var6.getIconsOffset());
               var12.x = Utils.convertDpToPixel(var12.x);
               var12.y = Utils.convertDpToPixel(var12.y);
               int var13;
               float var14;
               int var18;
               float var19;
               float var20;
               float var21;
               float var22;
               float var23;
               MPPointF var42;
               if (!var6.isStacked()) {
                  var13 = 0;
                  var14 = var8;
                  ValueFormatter var16 = var9;
                  BarBuffer var17 = var10;

                  MPPointF var34;
                  for(var34 = var12; (float)var13 < (float)var17.buffer.length * this.mAnimator.getPhaseX(); var13 += 4) {
                     float[] var38 = var17.buffer;
                     var18 = var13 + 1;
                     var19 = (var38[var18] + var17.buffer[var13 + 3]) / 2.0F;
                     if (!this.mViewPortHandler.isInBoundsTop(var17.buffer[var18])) {
                        break;
                     }

                     if (this.mViewPortHandler.isInBoundsX(var17.buffer[var13]) && this.mViewPortHandler.isInBoundsBottom(var17.buffer[var18])) {
                        BarEntry var36 = (BarEntry)var6.getEntryForIndex(var13 / 4);
                        var8 = var36.getY();
                        String var39 = var16.getBarLabel(var36);
                        var11 = (float)Utils.calcTextWidth(this.mValuePaint, var39);
                        if (var4) {
                           var20 = var3;
                        } else {
                           var20 = -(var11 + var3);
                        }

                        if (var4) {
                           var21 = -(var11 + var3);
                        } else {
                           var21 = var3;
                        }

                        var22 = var20;
                        var23 = var21;
                        if (var7) {
                           var22 = -var20 - var11;
                           var23 = -var21 - var11;
                        }

                        var20 = var22;
                        if (var6.isDrawValuesEnabled()) {
                           var22 = var17.buffer[var13 + 2];
                           if (var8 >= 0.0F) {
                              var21 = var20;
                           } else {
                              var21 = var23;
                           }

                           this.drawValue(var1, var39, var22 + var21, var19 + var14, var6.getValueTextColor(var13 / 2));
                        }

                        if (var36.getIcon() != null && var6.isDrawIconsEnabled()) {
                           Drawable var37 = var36.getIcon();
                           var21 = var17.buffer[var13 + 2];
                           if (var8 < 0.0F) {
                              var20 = var23;
                           }

                           var22 = var34.x;
                           var23 = var34.y;
                           Utils.drawImage(var1, var37, (int)(var21 + var20 + var22), (int)(var19 + var23), var37.getIntrinsicWidth(), var37.getIntrinsicHeight());
                        }
                     }
                  }

                  var42 = var34;
                  var2 = var2;
               } else {
                  List var15 = var2;
                  var13 = var5;
                  MPPointF var41 = var12;
                  Transformer var24 = this.mChart.getTransformer(var6.getAxisDependency());
                  var18 = 0;
                  int var25 = 0;

                  label208:
                  while(true) {
                     float[] var35;
                     label206:
                     while(true) {
                        var42 = var41;
                        var2 = var15;
                        var5 = var13;
                        if ((float)var18 >= (float)var6.getEntryCount() * this.mAnimator.getPhaseX()) {
                           break label208;
                        }

                        BarEntry var40 = (BarEntry)var6.getEntryForIndex(var18);
                        int var26 = var6.getValueTextColor(var18);
                        var35 = var40.getYVals();
                        float[] var43;
                        if (var35 == null) {
                           ViewPortHandler var46 = this.mViewPortHandler;
                           var43 = var10.buffer;
                           var5 = var25 + 1;
                           if (!var46.isInBoundsTop(var43[var5])) {
                              var42 = var41;
                              var2 = var15;
                              var5 = var13;
                              break label208;
                           }

                           if (!this.mViewPortHandler.isInBoundsX(var10.buffer[var25]) || !this.mViewPortHandler.isInBoundsBottom(var10.buffer[var5])) {
                              continue;
                           }

                           String var44 = var9.getBarLabel(var40);
                           var14 = (float)Utils.calcTextWidth(this.mValuePaint, var44);
                           if (var4) {
                              var20 = var3;
                           } else {
                              var20 = -(var14 + var3);
                           }

                           if (var4) {
                              var21 = -(var14 + var3);
                           } else {
                              var21 = var3;
                           }

                           var22 = var20;
                           var23 = var21;
                           if (var7) {
                              var22 = -var20 - var14;
                              var23 = -var21 - var14;
                           }

                           var20 = var22;
                           if (var6.isDrawValuesEnabled()) {
                              var22 = var10.buffer[var25 + 2];
                              if (var40.getY() >= 0.0F) {
                                 var21 = var20;
                              } else {
                                 var21 = var23;
                              }

                              this.drawValue(var1, var44, var22 + var21, var10.buffer[var5] + var8, var26);
                           }

                           if (var40.getIcon() != null && var6.isDrawIconsEnabled()) {
                              Drawable var45 = var40.getIcon();
                              var21 = var10.buffer[var25 + 2];
                              if (var40.getY() < 0.0F) {
                                 var20 = var23;
                              }

                              var23 = var10.buffer[var5];
                              var14 = var41.x;
                              var22 = var41.y;
                              Utils.drawImage(var1, var45, (int)(var21 + var20 + var14), (int)(var23 + var22), var45.getIntrinsicWidth(), var45.getIntrinsicHeight());
                           }
                           break;
                        }

                        float[] var27 = var35;
                        int var28 = var35.length * 2;
                        var43 = new float[var28];
                        var23 = -var40.getNegativeSum();
                        int var29 = 0;
                        var5 = 0;

                        for(var19 = 0.0F; var29 < var28; ++var5) {
                           var20 = var27[var5];
                           float var49;
                           int var30 = (var49 = var20 - 0.0F) == 0.0F ? 0 : (var49 < 0.0F ? -1 : 1);
                           if (var30 != 0 || var19 != 0.0F && var23 != 0.0F) {
                              if (var30 >= 0) {
                                 var19 += var20;
                                 var20 = var19;
                              } else {
                                 var21 = var23 - var20;
                                 var20 = var23;
                                 var23 = var21;
                              }
                           }

                           var43[var29] = var20 * var11;
                           var29 += 2;
                        }

                        var24.pointValuesToPixel(var43);
                        var29 = 0;
                        var5 = var28;

                        while(true) {
                           if (var29 >= var5) {
                              break label206;
                           }

                           float var31 = var27[var29 / 2];
                           String var32 = var9.getBarStackedLabel(var31, var40);
                           float var33 = (float)Utils.calcTextWidth(this.mValuePaint, var32);
                           if (var4) {
                              var21 = var3;
                           } else {
                              var21 = -(var33 + var3);
                           }

                           if (var4) {
                              var14 = -(var33 + var3);
                           } else {
                              var14 = var3;
                           }

                           var22 = var21;
                           var20 = var14;
                           if (var7) {
                              var22 = -var21 - var33;
                              var20 = -var14 - var33;
                           }

                           boolean var47;
                           if ((var31 != 0.0F || var23 != 0.0F || var19 <= 0.0F) && var31 >= 0.0F) {
                              var47 = false;
                           } else {
                              var47 = true;
                           }

                           var21 = var43[var29];
                           if (var47) {
                              var22 = var20;
                           }

                           var20 = var21 + var22;
                           var21 = (var10.buffer[var25 + 1] + var10.buffer[var25 + 3]) / 2.0F;
                           if (!this.mViewPortHandler.isInBoundsTop(var21)) {
                              break label206;
                           }

                           if (this.mViewPortHandler.isInBoundsX(var20) && this.mViewPortHandler.isInBoundsBottom(var21)) {
                              if (var6.isDrawValuesEnabled()) {
                                 this.drawValue(var1, var32, var20, var21 + var8, var26);
                              }

                              if (var40.getIcon() != null && var6.isDrawIconsEnabled()) {
                                 Drawable var48 = var40.getIcon();
                                 Utils.drawImage(var1, var48, (int)(var20 + var41.x), (int)(var21 + var41.y), var48.getIntrinsicWidth(), var48.getIntrinsicHeight());
                              }
                           }

                           var29 += 2;
                        }
                     }

                     if (var35 == null) {
                        var5 = var25 + 4;
                     } else {
                        var5 = var25 + var35.length * 4;
                     }

                     ++var18;
                     var25 = var5;
                  }
               }

               MPPointF.recycleInstance(var42);
            }
         }
      }

   }

   public void initBuffers() {
      BarData var1 = this.mChart.getBarData();
      this.mBarBuffers = new HorizontalBarBuffer[var1.getDataSetCount()];

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

         var4[var2] = new HorizontalBarBuffer(var5 * 4 * var6, var1.getDataSetCount(), var3.isStacked());
      }

   }

   protected boolean isDrawingValuesAllowed(ChartInterface var1) {
      boolean var2;
      if ((float)var1.getData().getEntryCount() < (float)var1.getMaxVisibleCount() * this.mViewPortHandler.getScaleY()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected void prepareBarHighlight(float var1, float var2, float var3, float var4, Transformer var5) {
      this.mBarRect.set(var2, var1 - var4, var3, var1 + var4);
      var5.rectToPixelPhaseHorizontal(this.mBarRect, this.mAnimator.getPhaseY());
   }

   protected void setHighlightDrawPos(Highlight var1, RectF var2) {
      var1.setDraw(var2.centerY(), var2.right);
   }
}
