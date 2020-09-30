package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LegendRenderer extends Renderer {
   protected List<LegendEntry> computedEntries = new ArrayList(16);
   protected FontMetrics legendFontMetrics = new FontMetrics();
   protected Legend mLegend;
   protected Paint mLegendFormPaint;
   protected Paint mLegendLabelPaint;
   private Path mLineFormPath = new Path();

   public LegendRenderer(ViewPortHandler var1, Legend var2) {
      super(var1);
      this.mLegend = var2;
      Paint var3 = new Paint(1);
      this.mLegendLabelPaint = var3;
      var3.setTextSize(Utils.convertDpToPixel(9.0F));
      this.mLegendLabelPaint.setTextAlign(Align.LEFT);
      var3 = new Paint(1);
      this.mLegendFormPaint = var3;
      var3.setStyle(Style.FILL);
   }

   public void computeLegend(ChartData<?> var1) {
      ChartData var2 = var1;
      if (!this.mLegend.isLegendCustom()) {
         this.computedEntries.clear();

         for(int var3 = 0; var3 < var1.getDataSetCount(); ++var3) {
            IDataSet var4 = var2.getDataSetByIndex(var3);
            List var5 = var4.getColors();
            int var6 = var4.getEntryCount();
            int var9;
            if (var4 instanceof IBarDataSet) {
               IBarDataSet var7 = (IBarDataSet)var4;
               if (var7.isStacked()) {
                  String[] var8 = var7.getStackLabels();

                  for(var9 = 0; var9 < var5.size() && var9 < var7.getStackSize(); ++var9) {
                     this.computedEntries.add(new LegendEntry(var8[var9 % var8.length], var4.getForm(), var4.getFormSize(), var4.getFormLineWidth(), var4.getFormLineDashEffect(), (Integer)var5.get(var9)));
                  }

                  if (var7.getLabel() != null) {
                     this.computedEntries.add(new LegendEntry(var4.getLabel(), Legend.LegendForm.NONE, Float.NaN, Float.NaN, (DashPathEffect)null, 1122867));
                  }
                  continue;
               }
            }

            if (var4 instanceof IPieDataSet) {
               IPieDataSet var11 = (IPieDataSet)var4;

               for(var9 = 0; var9 < var5.size() && var9 < var6; ++var9) {
                  this.computedEntries.add(new LegendEntry(((PieEntry)var11.getEntryForIndex(var9)).getLabel(), var4.getForm(), var4.getFormSize(), var4.getFormLineWidth(), var4.getFormLineDashEffect(), (Integer)var5.get(var9)));
               }

               if (var11.getLabel() != null) {
                  this.computedEntries.add(new LegendEntry(var4.getLabel(), Legend.LegendForm.NONE, Float.NaN, Float.NaN, (DashPathEffect)null, 1122867));
               }
            } else {
               label102: {
                  if (var4 instanceof ICandleDataSet) {
                     ICandleDataSet var12 = (ICandleDataSet)var4;
                     if (var12.getDecreasingColor() != 1122867) {
                        var9 = var12.getDecreasingColor();
                        var6 = var12.getIncreasingColor();
                        this.computedEntries.add(new LegendEntry((String)null, var4.getForm(), var4.getFormSize(), var4.getFormLineWidth(), var4.getFormLineDashEffect(), var9));
                        this.computedEntries.add(new LegendEntry(var4.getLabel(), var4.getForm(), var4.getFormSize(), var4.getFormLineWidth(), var4.getFormLineDashEffect(), var6));
                        break label102;
                     }
                  }

                  for(var9 = 0; var9 < var5.size() && var9 < var6; ++var9) {
                     String var13;
                     if (var9 < var5.size() - 1 && var9 < var6 - 1) {
                        var13 = null;
                     } else {
                        var13 = var1.getDataSetByIndex(var3).getLabel();
                     }

                     this.computedEntries.add(new LegendEntry(var13, var4.getForm(), var4.getFormSize(), var4.getFormLineWidth(), var4.getFormLineDashEffect(), (Integer)var5.get(var9)));
                  }
               }
            }

            var2 = var1;
         }

         if (this.mLegend.getExtraEntries() != null) {
            Collections.addAll(this.computedEntries, this.mLegend.getExtraEntries());
         }

         this.mLegend.setEntries(this.computedEntries);
      }

      Typeface var10 = this.mLegend.getTypeface();
      if (var10 != null) {
         this.mLegendLabelPaint.setTypeface(var10);
      }

      this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
      this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
      this.mLegend.calculateDimensions(this.mLegendLabelPaint, this.mViewPortHandler);
   }

   protected void drawForm(Canvas var1, float var2, float var3, LegendEntry var4, Legend var5) {
      if (var4.formColor != 1122868 && var4.formColor != 1122867 && var4.formColor != 0) {
         int var6 = var1.save();
         Legend.LegendForm var7 = var4.form;
         Legend.LegendForm var8 = var7;
         if (var7 == Legend.LegendForm.DEFAULT) {
            var8 = var5.getForm();
         }

         this.mLegendFormPaint.setColor(var4.formColor);
         float var9;
         if (Float.isNaN(var4.formSize)) {
            var9 = var5.getFormSize();
         } else {
            var9 = var4.formSize;
         }

         float var10 = Utils.convertDpToPixel(var9);
         var9 = var10 / 2.0F;
         int var11 = null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[var8.ordinal()];
         if (var11 != 3 && var11 != 4) {
            if (var11 != 5) {
               if (var11 == 6) {
                  if (Float.isNaN(var4.formLineWidth)) {
                     var9 = var5.getFormLineWidth();
                  } else {
                     var9 = var4.formLineWidth;
                  }

                  var9 = Utils.convertDpToPixel(var9);
                  DashPathEffect var12;
                  if (var4.formLineDashEffect == null) {
                     var12 = var5.getFormLineDashEffect();
                  } else {
                     var12 = var4.formLineDashEffect;
                  }

                  this.mLegendFormPaint.setStyle(Style.STROKE);
                  this.mLegendFormPaint.setStrokeWidth(var9);
                  this.mLegendFormPaint.setPathEffect(var12);
                  this.mLineFormPath.reset();
                  this.mLineFormPath.moveTo(var2, var3);
                  this.mLineFormPath.lineTo(var2 + var10, var3);
                  var1.drawPath(this.mLineFormPath, this.mLegendFormPaint);
               }
            } else {
               this.mLegendFormPaint.setStyle(Style.FILL);
               var1.drawRect(var2, var3 - var9, var2 + var10, var3 + var9, this.mLegendFormPaint);
            }
         } else {
            this.mLegendFormPaint.setStyle(Style.FILL);
            var1.drawCircle(var2 + var9, var3, var9, this.mLegendFormPaint);
         }

         var1.restoreToCount(var6);
      }

   }

   protected void drawLabel(Canvas var1, float var2, float var3, String var4) {
      var1.drawText(var4, var2, var3, this.mLegendLabelPaint);
   }

   public Paint getFormPaint() {
      return this.mLegendFormPaint;
   }

   public Paint getLabelPaint() {
      return this.mLegendLabelPaint;
   }

   public void renderLegend(Canvas var1) {
      if (this.mLegend.isEnabled()) {
         Typeface var2 = this.mLegend.getTypeface();
         if (var2 != null) {
            this.mLegendLabelPaint.setTypeface(var2);
         }

         this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
         this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
         float var3 = Utils.getLineHeight(this.mLegendLabelPaint, this.legendFontMetrics);
         float var4 = Utils.getLineSpacing(this.mLegendLabelPaint, this.legendFontMetrics) + Utils.convertDpToPixel(this.mLegend.getYEntrySpace());
         float var5 = var3 - (float)Utils.calcTextHeight(this.mLegendLabelPaint, "ABC") / 2.0F;
         LegendEntry[] var6 = this.mLegend.getEntries();
         float var7 = Utils.convertDpToPixel(this.mLegend.getFormToTextSpace());
         float var8 = Utils.convertDpToPixel(this.mLegend.getXEntrySpace());
         Legend.LegendOrientation var30 = this.mLegend.getOrientation();
         Legend.LegendHorizontalAlignment var9 = this.mLegend.getHorizontalAlignment();
         Legend.LegendVerticalAlignment var10 = this.mLegend.getVerticalAlignment();
         Legend.LegendDirection var11 = this.mLegend.getDirection();
         float var12 = Utils.convertDpToPixel(this.mLegend.getFormSize());
         float var13 = Utils.convertDpToPixel(this.mLegend.getStackSpace());
         float var14 = this.mLegend.getYOffset();
         float var15 = this.mLegend.getXOffset();
         int var16 = null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[var9.ordinal()];
         float var17;
         float var18;
         if (var16 != 1) {
            if (var16 != 2) {
               if (var16 != 3) {
                  var15 = 0.0F;
               } else {
                  if (var30 == Legend.LegendOrientation.VERTICAL) {
                     var17 = this.mViewPortHandler.getChartWidth() / 2.0F;
                  } else {
                     var17 = this.mViewPortHandler.contentLeft() + this.mViewPortHandler.contentWidth() / 2.0F;
                  }

                  if (var11 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                     var18 = var15;
                  } else {
                     var18 = -var15;
                  }

                  var17 += var18;
                  if (var30 == Legend.LegendOrientation.VERTICAL) {
                     double var19 = (double)var17;
                     double var21;
                     if (var11 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                        var21 = (double)(-this.mLegend.mNeededWidth) / 2.0D + (double)var15;
                     } else {
                        var21 = (double)this.mLegend.mNeededWidth / 2.0D - (double)var15;
                     }

                     var15 = (float)(var19 + var21);
                  } else {
                     var15 = var17;
                  }
               }
            } else {
               if (var30 == Legend.LegendOrientation.VERTICAL) {
                  var17 = this.mViewPortHandler.getChartWidth();
               } else {
                  var17 = this.mViewPortHandler.contentRight();
               }

               var17 -= var15;
               var15 = var17;
               if (var11 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                  var15 = var17 - this.mLegend.mNeededWidth;
               }
            }
         } else {
            if (var30 != Legend.LegendOrientation.VERTICAL) {
               var15 += this.mViewPortHandler.contentLeft();
            }

            if (var11 == Legend.LegendDirection.RIGHT_TO_LEFT) {
               var15 += this.mLegend.mNeededWidth;
            }
         }

         var16 = null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[var30.ordinal()];
         float var24;
         if (var16 != 1) {
            if (var16 == 2) {
               var16 = null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[var10.ordinal()];
               if (var16 != 1) {
                  if (var16 != 2) {
                     if (var16 != 3) {
                        var17 = 0.0F;
                     } else {
                        var17 = this.mViewPortHandler.getChartHeight() / 2.0F - this.mLegend.mNeededHeight / 2.0F + this.mLegend.getYOffset();
                     }
                  } else {
                     if (var9 == Legend.LegendHorizontalAlignment.CENTER) {
                        var17 = this.mViewPortHandler.getChartHeight();
                     } else {
                        var17 = this.mViewPortHandler.contentBottom();
                     }

                     var17 -= this.mLegend.mNeededHeight + var14;
                  }
               } else {
                  if (var9 == Legend.LegendHorizontalAlignment.CENTER) {
                     var17 = 0.0F;
                  } else {
                     var17 = this.mViewPortHandler.contentTop();
                  }

                  var17 += var14;
               }

               var8 = var17;
               boolean var23 = false;
               var16 = 0;
               var24 = 0.0F;
               var17 = var13;
               var18 = var5;
               var13 = var8;

               for(Legend.LegendDirection var31 = var11; var16 < var6.length; var24 = var8) {
                  LegendEntry var25 = var6[var16];
                  boolean var26;
                  if (var25.form != Legend.LegendForm.NONE) {
                     var26 = true;
                  } else {
                     var26 = false;
                  }

                  if (Float.isNaN(var25.formSize)) {
                     var5 = var12;
                  } else {
                     var5 = Utils.convertDpToPixel(var25.formSize);
                  }

                  if (var26) {
                     if (var31 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                        var8 = var15 + var24;
                     } else {
                        var8 = var15 - (var5 - var24);
                     }

                     var14 = var8;
                     this.drawForm(var1, var8, var13 + var18, var25, this.mLegend);
                     var8 = var8;
                     if (var31 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                        var8 = var14 + var5;
                     }
                  } else {
                     var8 = var15;
                  }

                  if (var25.label == null) {
                     var8 = var24 + var5 + var17;
                     var23 = true;
                  } else {
                     if (var26 && !var23) {
                        if (var31 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                           var17 = var7;
                        } else {
                           var17 = -var7;
                        }

                        var17 += var8;
                     } else {
                        var17 = var8;
                        if (var23) {
                           var17 = var15;
                        }
                     }

                     var8 = var17;
                     if (var31 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        var8 = var17 - (float)Utils.calcTextWidth(this.mLegendLabelPaint, var25.label);
                     }

                     if (!var23) {
                        this.drawLabel(var1, var8, var13 + var3, var25.label);
                     } else {
                        var13 += var3 + var4;
                        this.drawLabel(var1, var8, var13 + var3, var25.label);
                     }

                     var13 += var3 + var4;
                     var8 = 0.0F;
                  }

                  ++var16;
                  var17 = var17;
               }
            }
         } else {
            List var32 = this.mLegend.getCalculatedLineSizes();
            List var27 = this.mLegend.getCalculatedLabelSizes();
            List var35 = this.mLegend.getCalculatedLabelBreakPoints();
            var16 = null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[var10.ordinal()];
            var17 = var14;
            if (var16 != 1) {
               if (var16 != 2) {
                  if (var16 != 3) {
                     var17 = 0.0F;
                  } else {
                     var17 = var14 + (this.mViewPortHandler.getChartHeight() - this.mLegend.mNeededHeight) / 2.0F;
                  }
               } else {
                  var17 = this.mViewPortHandler.getChartHeight() - var14 - this.mLegend.mNeededHeight;
               }
            }

            int var36 = var6.length;
            var14 = var15;
            var16 = 0;
            int var34 = 0;
            var18 = var13;

            for(var13 = var15; var16 < var36; var14 = var15) {
               LegendEntry var33 = var6[var16];
               boolean var28;
               if (var33.form != Legend.LegendForm.NONE) {
                  var28 = true;
               } else {
                  var28 = false;
               }

               if (Float.isNaN(var33.formSize)) {
                  var24 = var12;
               } else {
                  var24 = Utils.convertDpToPixel(var33.formSize);
               }

               if (var16 < var35.size() && (Boolean)var35.get(var16)) {
                  var17 += var3 + var4;
                  var15 = var13;
               } else {
                  var15 = var14;
               }

               if (var15 == var13 && var9 == Legend.LegendHorizontalAlignment.CENTER && var34 < var32.size()) {
                  if (var11 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var14 = ((FSize)var32.get(var34)).width;
                  } else {
                     var14 = -((FSize)var32.get(var34)).width;
                  }

                  var15 += var14 / 2.0F;
                  ++var34;
               }

               boolean var29;
               if (var33.label == null) {
                  var29 = true;
               } else {
                  var29 = false;
               }

               if (var28) {
                  var14 = var15;
                  if (var11 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var14 = var15 - var24;
                  }

                  this.drawForm(var1, var14, var17 + var5, var33, this.mLegend);
                  if (var11 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                     var15 = var14 + var24;
                  } else {
                     var15 = var14;
                  }
               }

               if (!var29) {
                  var14 = var15;
                  if (var28) {
                     if (var11 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        var14 = -var7;
                     } else {
                        var14 = var7;
                     }

                     var14 += var15;
                  }

                  var15 = var14;
                  if (var11 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var15 = var14 - ((FSize)var27.get(var16)).width;
                  }

                  this.drawLabel(var1, var15, var17 + var3, var33.label);
                  var14 = var15;
                  if (var11 == Legend.LegendDirection.LEFT_TO_RIGHT) {
                     var14 = var15 + ((FSize)var27.get(var16)).width;
                  }

                  if (var11 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var15 = -var8;
                  } else {
                     var15 = var8;
                  }

                  var15 += var14;
               } else {
                  if (var11 == Legend.LegendDirection.RIGHT_TO_LEFT) {
                     var14 = -var18;
                  } else {
                     var14 = var18;
                  }

                  var15 += var14;
               }

               ++var16;
            }
         }

      }
   }
}
