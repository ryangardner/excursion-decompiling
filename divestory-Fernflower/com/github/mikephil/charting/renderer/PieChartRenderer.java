package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;

public class PieChartRenderer extends DataRenderer {
   protected Canvas mBitmapCanvas;
   private RectF mCenterTextLastBounds = new RectF();
   private CharSequence mCenterTextLastValue;
   private StaticLayout mCenterTextLayout;
   private TextPaint mCenterTextPaint;
   protected PieChart mChart;
   protected WeakReference<Bitmap> mDrawBitmap;
   protected Path mDrawCenterTextPathBuffer = new Path();
   protected RectF mDrawHighlightedRectF = new RectF();
   private Paint mEntryLabelsPaint;
   private Path mHoleCirclePath = new Path();
   protected Paint mHolePaint;
   private RectF mInnerRectBuffer = new RectF();
   private Path mPathBuffer = new Path();
   private RectF[] mRectBuffer = new RectF[]{new RectF(), new RectF(), new RectF()};
   protected Paint mTransparentCirclePaint;
   protected Paint mValueLinePaint;

   public PieChartRenderer(PieChart var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mChart = var1;
      Paint var4 = new Paint(1);
      this.mHolePaint = var4;
      var4.setColor(-1);
      this.mHolePaint.setStyle(Style.FILL);
      var4 = new Paint(1);
      this.mTransparentCirclePaint = var4;
      var4.setColor(-1);
      this.mTransparentCirclePaint.setStyle(Style.FILL);
      this.mTransparentCirclePaint.setAlpha(105);
      TextPaint var5 = new TextPaint(1);
      this.mCenterTextPaint = var5;
      var5.setColor(-16777216);
      this.mCenterTextPaint.setTextSize(Utils.convertDpToPixel(12.0F));
      this.mValuePaint.setTextSize(Utils.convertDpToPixel(13.0F));
      this.mValuePaint.setColor(-1);
      this.mValuePaint.setTextAlign(Align.CENTER);
      var4 = new Paint(1);
      this.mEntryLabelsPaint = var4;
      var4.setColor(-1);
      this.mEntryLabelsPaint.setTextAlign(Align.CENTER);
      this.mEntryLabelsPaint.setTextSize(Utils.convertDpToPixel(13.0F));
      var4 = new Paint(1);
      this.mValueLinePaint = var4;
      var4.setStyle(Style.STROKE);
   }

   protected float calculateMinimumRadiusForSpacedSlice(MPPointF var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var7 / 2.0F;
      float var9 = var1.x;
      double var10 = (double)((var6 + var7) * 0.017453292F);
      var9 += (float)Math.cos(var10) * var2;
      var7 = var1.y + (float)Math.sin(var10) * var2;
      float var12 = var1.x;
      var10 = (double)((var6 + var8) * 0.017453292F);
      float var13 = (float)Math.cos(var10);
      var8 = var1.y;
      var6 = (float)Math.sin(var10);
      return (float)((double)(var2 - (float)(Math.sqrt(Math.pow((double)(var9 - var4), 2.0D) + Math.pow((double)(var7 - var5), 2.0D)) / 2.0D * Math.tan((180.0D - (double)var3) / 2.0D * 0.017453292519943295D))) - Math.sqrt(Math.pow((double)(var12 + var13 * var2 - (var9 + var4) / 2.0F), 2.0D) + Math.pow((double)(var8 + var6 * var2 - (var7 + var5) / 2.0F), 2.0D)));
   }

   protected void drawCenterText(Canvas var1) {
      CharSequence var2 = this.mChart.getCenterText();
      if (this.mChart.isDrawCenterTextEnabled() && var2 != null) {
         MPPointF var3 = this.mChart.getCenterCircleBox();
         MPPointF var4 = this.mChart.getCenterTextOffset();
         float var5 = var3.x + var4.x;
         float var6 = var3.y + var4.y;
         float var7;
         if (this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled()) {
            var7 = this.mChart.getRadius() * (this.mChart.getHoleRadius() / 100.0F);
         } else {
            var7 = this.mChart.getRadius();
         }

         RectF var8 = this.mRectBuffer[0];
         var8.left = var5 - var7;
         var8.top = var6 - var7;
         var8.right = var5 + var7;
         var8.bottom = var6 + var7;
         RectF var9 = this.mRectBuffer[1];
         var9.set(var8);
         var7 = this.mChart.getCenterTextRadiusPercent() / 100.0F;
         if ((double)var7 > 0.0D) {
            var9.inset((var9.width() - var9.width() * var7) / 2.0F, (var9.height() - var9.height() * var7) / 2.0F);
         }

         if (!var2.equals(this.mCenterTextLastValue) || !var9.equals(this.mCenterTextLastBounds)) {
            this.mCenterTextLastBounds.set(var9);
            this.mCenterTextLastValue = var2;
            var7 = this.mCenterTextLastBounds.width();
            this.mCenterTextLayout = new StaticLayout(var2, 0, var2.length(), this.mCenterTextPaint, (int)Math.max(Math.ceil((double)var7), 1.0D), Alignment.ALIGN_CENTER, 1.0F, 0.0F, false);
         }

         var7 = (float)this.mCenterTextLayout.getHeight();
         var1.save();
         if (VERSION.SDK_INT >= 18) {
            Path var10 = this.mDrawCenterTextPathBuffer;
            var10.reset();
            var10.addOval(var8, Direction.CW);
            var1.clipPath(var10);
         }

         var1.translate(var9.left, var9.top + (var9.height() - var7) / 2.0F);
         this.mCenterTextLayout.draw(var1);
         var1.restore();
         MPPointF.recycleInstance(var3);
         MPPointF.recycleInstance(var4);
      }

   }

   public void drawData(Canvas var1) {
      int var2 = (int)this.mViewPortHandler.getChartWidth();
      int var3 = (int)this.mViewPortHandler.getChartHeight();
      WeakReference var4 = this.mDrawBitmap;
      Bitmap var6;
      if (var4 == null) {
         var6 = null;
      } else {
         var6 = (Bitmap)var4.get();
      }

      Bitmap var5;
      label42: {
         if (var6 != null && var6.getWidth() == var2) {
            var5 = var6;
            if (var6.getHeight() == var3) {
               break label42;
            }
         }

         if (var2 <= 0 || var3 <= 0) {
            return;
         }

         var5 = Bitmap.createBitmap(var2, var3, Config.ARGB_4444);
         this.mDrawBitmap = new WeakReference(var5);
         this.mBitmapCanvas = new Canvas(var5);
      }

      var5.eraseColor(0);
      Iterator var7 = ((PieData)this.mChart.getData()).getDataSets().iterator();

      while(var7.hasNext()) {
         IPieDataSet var8 = (IPieDataSet)var7.next();
         if (var8.isVisible() && var8.getEntryCount() > 0) {
            this.drawDataSet(var1, var8);
         }
      }

   }

   protected void drawDataSet(Canvas var1, IPieDataSet var2) {
      PieChartRenderer var3 = this;
      IPieDataSet var4 = var2;
      float var5 = this.mChart.getRotationAngle();
      float var6 = this.mAnimator.getPhaseX();
      float var7 = this.mAnimator.getPhaseY();
      RectF var8 = this.mChart.getCircleBox();
      int var9 = var2.getEntryCount();
      float[] var10 = this.mChart.getDrawAngles();
      MPPointF var33 = this.mChart.getCenterCircleBox();
      float var11 = this.mChart.getRadius();
      boolean var12;
      if (this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled()) {
         var12 = true;
      } else {
         var12 = false;
      }

      float var13;
      if (var12) {
         var13 = this.mChart.getHoleRadius() / 100.0F * var11;
      } else {
         var13 = 0.0F;
      }

      float var14 = (var11 - this.mChart.getHoleRadius() * var11 / 100.0F) / 2.0F;
      RectF var15 = new RectF();
      boolean var16;
      if (var12 && this.mChart.isDrawRoundedSlicesEnabled()) {
         var16 = true;
      } else {
         var16 = false;
      }

      int var17 = 0;

      int var18;
      int var19;
      for(var18 = 0; var17 < var9; var18 = var19) {
         var19 = var18;
         if (Math.abs(((PieEntry)var4.getEntryForIndex(var17)).getY()) > Utils.FLOAT_EPSILON) {
            var19 = var18 + 1;
         }

         ++var17;
      }

      float var20;
      if (var18 <= 1) {
         var20 = 0.0F;
      } else {
         var20 = this.getSliceSpace(var4);
      }

      var19 = 0;
      float var21 = 0.0F;

      float var23;
      for(var17 = var9; var19 < var17; var21 = var23) {
         float var22 = var10[var19];
         RectF var34;
         MPPointF var39;
         if (Math.abs(var2.getEntryForIndex(var19).getY()) > Utils.FLOAT_EPSILON && (!var3.mChart.needsHighlight(var19) || var16)) {
            boolean var38;
            if (var20 > 0.0F && var22 <= 180.0F) {
               var38 = true;
            } else {
               var38 = false;
            }

            var3.mRenderPaint.setColor(var2.getColor(var19));
            if (var18 == 1) {
               var23 = 0.0F;
            } else {
               var23 = var20 / (var11 * 0.017453292F);
            }

            float var24 = var5 + (var21 + var23 / 2.0F) * var7;
            float var25 = (var22 - var23) * var7;
            var23 = var25;
            if (var25 < 0.0F) {
               var23 = 0.0F;
            }

            var3.mPathBuffer.reset();
            float var26;
            double var27;
            if (var16) {
               var25 = var33.x;
               var26 = var11 - var14;
               var27 = (double)(var24 * 0.017453292F);
               var25 += (float)Math.cos(var27) * var26;
               var26 = var33.y + var26 * (float)Math.sin(var27);
               var15.set(var25 - var14, var26 - var14, var25 + var14, var26 + var14);
            }

            var25 = var33.x;
            var27 = (double)(var24 * 0.017453292F);
            float var29 = var25 + (float)Math.cos(var27) * var11;
            var26 = var33.y + (float)Math.sin(var27) * var11;
            float var40;
            int var30 = (var40 = var23 - 360.0F) == 0.0F ? 0 : (var40 < 0.0F ? -1 : 1);
            if (var30 >= 0 && var23 % 360.0F <= Utils.FLOAT_EPSILON) {
               var3.mPathBuffer.addCircle(var33.x, var33.y, var11, Direction.CW);
            } else {
               if (var16) {
                  var3.mPathBuffer.arcTo(var15, var24 + 180.0F, -180.0F);
               }

               var3.mPathBuffer.arcTo(var8, var24, var23);
            }

            label185: {
               var3.mInnerRectBuffer.set(var33.x - var13, var33.y - var13, var33.x + var13, var33.y + var13);
               PieChartRenderer var31;
               MPPointF var32;
               if (var12 && (var13 > 0.0F || var38)) {
                  if (var38) {
                     var24 = this.calculateMinimumRadiusForSpacedSlice(var33, var11, var22 * var7, var29, var26, var24, var23);
                     var25 = var24;
                     if (var24 < 0.0F) {
                        var25 = -var24;
                     }

                     var25 = Math.max(var13, var25);
                  } else {
                     var25 = var13;
                  }

                  var24 = var25;
                  if (var18 != 1 && var25 != 0.0F) {
                     var25 = var20 / (var25 * 0.017453292F);
                  } else {
                     var25 = 0.0F;
                  }

                  var29 = var25 / 2.0F;
                  var26 = (var22 - var25) * var7;
                  var25 = var26;
                  if (var26 < 0.0F) {
                     var25 = 0.0F;
                  }

                  var26 = var5 + (var21 + var29) * var7 + var25;
                  if (var30 >= 0 && var23 % 360.0F <= Utils.FLOAT_EPSILON) {
                     this.mPathBuffer.addCircle(var33.x, var33.y, var24, Direction.CCW);
                  } else {
                     if (var16) {
                        var24 = var33.x;
                        var23 = var11 - var14;
                        var27 = (double)(var26 * 0.017453292F);
                        var24 += (float)Math.cos(var27) * var23;
                        var23 = var33.y + var23 * (float)Math.sin(var27);
                        var15.set(var24 - var14, var23 - var14, var24 + var14, var23 + var14);
                        this.mPathBuffer.arcTo(var15, var26, 180.0F);
                     } else {
                        Path var37 = this.mPathBuffer;
                        var23 = var33.x;
                        var27 = (double)(var26 * 0.017453292F);
                        var37.lineTo(var23 + (float)Math.cos(var27) * var24, var33.y + var24 * (float)Math.sin(var27));
                     }

                     this.mPathBuffer.arcTo(this.mInnerRectBuffer, var26, -var25);
                  }

                  var31 = this;
                  var32 = var33;
                  var34 = var15;
               } else {
                  var34 = var15;
                  var31 = var3;
                  var32 = var33;
                  if (var23 % 360.0F > Utils.FLOAT_EPSILON) {
                     if (var38) {
                        var25 = var23 / 2.0F;
                        var34 = var15;
                        var23 = this.calculateMinimumRadiusForSpacedSlice(var33, var11, var22 * var7, var29, var26, var24, var23);
                        var26 = var33.x;
                        var27 = (double)((var24 + var25) * 0.017453292F);
                        var24 = (float)Math.cos(var27);
                        var29 = var33.y;
                        var25 = (float)Math.sin(var27);
                        var3.mPathBuffer.lineTo(var26 + var24 * var23, var29 + var23 * var25);
                        var39 = var33;
                     } else {
                        var34 = var15;
                        var3.mPathBuffer.lineTo(var33.x, var33.y);
                        var39 = var33;
                     }
                     break label185;
                  }
               }

               var39 = var32;
               var3 = var31;
            }

            var3.mPathBuffer.close();
            var3.mBitmapCanvas.drawPath(var3.mPathBuffer, var3.mRenderPaint);
            var23 = var21 + var22 * var6;
         } else {
            var23 = var21 + var22 * var6;
            var39 = var33;
            var34 = var15;
         }

         ++var19;
         var15 = var34;
         var33 = var39;
      }

      MPPointF.recycleInstance(var33);
   }

   protected void drawEntryLabel(Canvas var1, String var2, float var3, float var4) {
      var1.drawText(var2, var3, var4, this.mEntryLabelsPaint);
   }

   public void drawExtras(Canvas var1) {
      this.drawHole(var1);
      var1.drawBitmap((Bitmap)this.mDrawBitmap.get(), 0.0F, 0.0F, (Paint)null);
      this.drawCenterText(var1);
   }

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      boolean var3;
      if (this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled()) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3 || !this.mChart.isDrawRoundedSlicesEnabled()) {
         float var4 = this.mAnimator.getPhaseX();
         float var5 = this.mAnimator.getPhaseY();
         float var6 = this.mChart.getRotationAngle();
         float[] var31 = this.mChart.getDrawAngles();
         float[] var7 = this.mChart.getAbsoluteAngles();
         MPPointF var8 = this.mChart.getCenterCircleBox();
         float var9 = this.mChart.getRadius();
         float var10;
         if (var3) {
            var10 = this.mChart.getHoleRadius() / 100.0F * var9;
         } else {
            var10 = 0.0F;
         }

         RectF var11 = this.mDrawHighlightedRectF;
         var11.set(0.0F, 0.0F, 0.0F, 0.0F);

         for(int var12 = 0; var12 < var2.length; ++var12) {
            int var14 = (int)var2[var12].getX();
            if (var14 < var31.length) {
               IPieDataSet var13 = ((PieData)this.mChart.getData()).getDataSetByIndex(var2[var12].getDataSetIndex());
               if (var13 != null && var13.isHighlightEnabled()) {
                  int var15 = var13.getEntryCount();
                  int var16 = 0;

                  int var17;
                  int var18;
                  for(var17 = 0; var16 < var15; var17 = var18) {
                     var18 = var17;
                     if (Math.abs(((PieEntry)var13.getEntryForIndex(var16)).getY()) > Utils.FLOAT_EPSILON) {
                        var18 = var17 + 1;
                     }

                     ++var16;
                  }

                  float var19;
                  if (var14 == 0) {
                     var19 = 0.0F;
                  } else {
                     var19 = var7[var14 - 1] * var4;
                  }

                  float var20;
                  if (var17 <= 1) {
                     var20 = 0.0F;
                  } else {
                     var20 = var13.getSliceSpace();
                  }

                  float var21 = var31[var14];
                  float var22 = var13.getSelectionShift();
                  float var23 = var9 + var22;
                  var11.set(this.mChart.getCircleBox());
                  var22 = -var22;
                  var11.inset(var22, var22);
                  boolean var33;
                  if (var20 > 0.0F && var21 <= 180.0F) {
                     var33 = true;
                  } else {
                     var33 = false;
                  }

                  this.mRenderPaint.setColor(var13.getColor(var14));
                  float var24;
                  if (var17 == 1) {
                     var24 = 0.0F;
                  } else {
                     var24 = var20 / (var9 * 0.017453292F);
                  }

                  if (var17 == 1) {
                     var22 = 0.0F;
                  } else {
                     var22 = var20 / (var23 * 0.017453292F);
                  }

                  float var25 = var6 + (var24 / 2.0F + var19) * var5;
                  var24 = (var21 - var24) * var5;
                  if (var24 < 0.0F) {
                     var24 = 0.0F;
                  }

                  float var26 = (var22 / 2.0F + var19) * var5 + var6;
                  float var27 = (var21 - var22) * var5;
                  var22 = var27;
                  if (var27 < 0.0F) {
                     var22 = 0.0F;
                  }

                  this.mPathBuffer.reset();
                  float var34;
                  var18 = (var34 = var24 - 360.0F) == 0.0F ? 0 : (var34 < 0.0F ? -1 : 1);
                  double var28;
                  if (var18 >= 0 && var24 % 360.0F <= Utils.FLOAT_EPSILON) {
                     this.mPathBuffer.addCircle(var8.x, var8.y, var23, Direction.CW);
                  } else {
                     Path var32 = this.mPathBuffer;
                     var27 = var8.x;
                     var28 = (double)(var26 * 0.017453292F);
                     var32.moveTo(var27 + (float)Math.cos(var28) * var23, var8.y + var23 * (float)Math.sin(var28));
                     this.mPathBuffer.arcTo(var11, var26, var22);
                  }

                  if (var33) {
                     var22 = var8.x;
                     var28 = (double)(var25 * 0.017453292F);
                     var22 = this.calculateMinimumRadiusForSpacedSlice(var8, var9, var21 * var5, (float)Math.cos(var28) * var9 + var22, var8.y + (float)Math.sin(var28) * var9, var25, var24);
                  } else {
                     var22 = 0.0F;
                  }

                  this.mInnerRectBuffer.set(var8.x - var10, var8.y - var10, var8.x + var10, var8.y + var10);
                  if (var3 && (var10 > 0.0F || var33)) {
                     if (var33) {
                        var25 = var22;
                        if (var22 < 0.0F) {
                           var25 = -var22;
                        }

                        var22 = Math.max(var10, var25);
                     } else {
                        var22 = var10;
                     }

                     if (var17 != 1 && var22 != 0.0F) {
                        var20 /= var22 * 0.017453292F;
                     } else {
                        var20 = 0.0F;
                     }

                     var25 = var20 / 2.0F;
                     var27 = (var21 - var20) * var5;
                     var20 = var27;
                     if (var27 < 0.0F) {
                        var20 = 0.0F;
                     }

                     var19 = (var19 + var25) * var5 + var6 + var20;
                     if (var18 >= 0 && var24 % 360.0F <= Utils.FLOAT_EPSILON) {
                        this.mPathBuffer.addCircle(var8.x, var8.y, var22, Direction.CCW);
                     } else {
                        Path var30 = this.mPathBuffer;
                        var24 = var8.x;
                        var28 = (double)(var19 * 0.017453292F);
                        var30.lineTo(var24 + (float)Math.cos(var28) * var22, var8.y + var22 * (float)Math.sin(var28));
                        this.mPathBuffer.arcTo(this.mInnerRectBuffer, var19, -var20);
                     }
                  } else if (var24 % 360.0F > Utils.FLOAT_EPSILON) {
                     if (var33) {
                        var20 = var24 / 2.0F;
                        var19 = var8.x;
                        var28 = (double)((var25 + var20) * 0.017453292F);
                        var24 = (float)Math.cos(var28);
                        var20 = var8.y;
                        var27 = (float)Math.sin(var28);
                        this.mPathBuffer.lineTo(var19 + var24 * var22, var20 + var22 * var27);
                     } else {
                        this.mPathBuffer.lineTo(var8.x, var8.y);
                     }
                  }

                  this.mPathBuffer.close();
                  this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
               }
            }
         }

         MPPointF.recycleInstance(var8);
      }
   }

   protected void drawHole(Canvas var1) {
      if (this.mChart.isDrawHoleEnabled() && this.mBitmapCanvas != null) {
         float var2 = this.mChart.getRadius();
         float var3 = this.mChart.getHoleRadius() / 100.0F * var2;
         MPPointF var6 = this.mChart.getCenterCircleBox();
         if (Color.alpha(this.mHolePaint.getColor()) > 0) {
            this.mBitmapCanvas.drawCircle(var6.x, var6.y, var3, this.mHolePaint);
         }

         if (Color.alpha(this.mTransparentCirclePaint.getColor()) > 0 && this.mChart.getTransparentCircleRadius() > this.mChart.getHoleRadius()) {
            int var4 = this.mTransparentCirclePaint.getAlpha();
            float var5 = this.mChart.getTransparentCircleRadius() / 100.0F;
            this.mTransparentCirclePaint.setAlpha((int)((float)var4 * this.mAnimator.getPhaseX() * this.mAnimator.getPhaseY()));
            this.mHoleCirclePath.reset();
            this.mHoleCirclePath.addCircle(var6.x, var6.y, var2 * var5, Direction.CW);
            this.mHoleCirclePath.addCircle(var6.x, var6.y, var3, Direction.CCW);
            this.mBitmapCanvas.drawPath(this.mHoleCirclePath, this.mTransparentCirclePaint);
            this.mTransparentCirclePaint.setAlpha(var4);
         }

         MPPointF.recycleInstance(var6);
      }

   }

   protected void drawRoundedSlices(Canvas var1) {
      if (this.mChart.isDrawRoundedSlicesEnabled()) {
         IPieDataSet var2 = ((PieData)this.mChart.getData()).getDataSet();
         if (var2.isVisible()) {
            float var3 = this.mAnimator.getPhaseX();
            float var4 = this.mAnimator.getPhaseY();
            MPPointF var5 = this.mChart.getCenterCircleBox();
            float var6 = this.mChart.getRadius();
            float var7 = (var6 - this.mChart.getHoleRadius() * var6 / 100.0F) / 2.0F;
            float[] var19 = this.mChart.getDrawAngles();
            float var8 = this.mChart.getRotationAngle();

            for(int var9 = 0; var9 < var2.getEntryCount(); ++var9) {
               float var10 = var19[var9];
               if (Math.abs(var2.getEntryForIndex(var9).getY()) > Utils.FLOAT_EPSILON) {
                  double var11 = (double)(var6 - var7);
                  double var13 = (double)((var8 + var10) * var4);
                  double var15 = Math.cos(Math.toRadians(var13));
                  float var17 = (float)((double)var5.x + var15 * var11);
                  float var18 = (float)(var11 * Math.sin(Math.toRadians(var13)) + (double)var5.y);
                  this.mRenderPaint.setColor(var2.getColor(var9));
                  this.mBitmapCanvas.drawCircle(var17, var18, var7, this.mRenderPaint);
               }

               var8 += var10 * var3;
            }

            MPPointF.recycleInstance(var5);
         }
      }
   }

   public void drawValue(Canvas var1, String var2, float var3, float var4, int var5) {
      this.mValuePaint.setColor(var5);
      var1.drawText(var2, var3, var4, this.mValuePaint);
   }

   public void drawValues(Canvas var1) {
      MPPointF var3 = this.mChart.getCenterCircleBox();
      float var4 = this.mChart.getRadius();
      float var5 = this.mChart.getRotationAngle();
      float[] var6 = this.mChart.getDrawAngles();
      float[] var7 = this.mChart.getAbsoluteAngles();
      float var8 = this.mAnimator.getPhaseX();
      float var9 = this.mAnimator.getPhaseY();
      float var10 = (var4 - this.mChart.getHoleRadius() * var4 / 100.0F) / 2.0F;
      float var11 = this.mChart.getHoleRadius() / 100.0F;
      float var12 = var4 / 10.0F * 3.6F;
      float var13 = var5;
      float var14;
      if (this.mChart.isDrawHoleEnabled()) {
         var14 = (var4 - var4 * var11) / 2.0F;
         var13 = var5;
         var12 = var14;
         if (!this.mChart.isDrawSlicesUnderHoleEnabled()) {
            var13 = var5;
            var12 = var14;
            if (this.mChart.isDrawRoundedSlicesEnabled()) {
               var13 = (float)((double)var5 + (double)(var10 * 360.0F) / ((double)var4 * 6.283185307179586D));
               var12 = var14;
            }
         }
      }

      var14 = var13;
      var13 = var4 - var12;
      PieData var15 = (PieData)this.mChart.getData();
      List var16 = var15.getDataSets();
      float var17 = var15.getYValueSum();
      boolean var18 = this.mChart.isDrawEntryLabelsEnabled();
      var1.save();
      float var19 = Utils.convertDpToPixel(5.0F);
      int var20 = 0;
      int var21 = 0;

      for(var12 = var9; var21 < var16.size(); var13 = var9) {
         IPieDataSet var22 = (IPieDataSet)var16.get(var21);
         boolean var23 = var22.isDrawValuesEnabled();
         float[] var48;
         MPPointF var49;
         float[] var53;
         if (!var23 && !var18) {
            var9 = var4;
            var53 = var7;
            var4 = var8;
            var5 = var12;
            var12 = var14;
            var49 = var3;
            var7 = var6;
            var8 = var13;
            var48 = var53;
            var14 = var5;
            var13 = var9;
         } else {
            PieDataSet.ValuePosition var26 = var22.getXValuePosition();
            PieDataSet.ValuePosition var27 = var22.getYValuePosition();
            this.applyValueTextStyle(var22);
            float var28 = (float)Utils.calcTextHeight(this.mValuePaint, "Q") + Utils.convertDpToPixel(4.0F);
            ValueFormatter var24 = var22.getValueFormatter();
            int var29 = var22.getEntryCount();
            this.mValueLinePaint.setColor(var22.getValueLineColor());
            this.mValueLinePaint.setStrokeWidth(Utils.convertDpToPixel(var22.getValueLineWidth()));
            var9 = this.getSliceSpace(var22);
            MPPointF var25 = MPPointF.getInstance(var22.getIconsOffset());
            var25.x = Utils.convertDpToPixel(var25.x);
            var25.y = Utils.convertDpToPixel(var25.y);
            int var30 = 0;
            float[] var2 = var7;

            for(IPieDataSet var50 = var22; var30 < var29; ++var30) {
               PieEntry var31 = (PieEntry)var50.getEntryForIndex(var30);
               if (var20 == 0) {
                  var5 = 0.0F;
               } else {
                  var5 = var2[var20 - 1] * var8;
               }

               float var32 = var14 + (var5 + (var6[var20] - var9 / (var13 * 0.017453292F) / 2.0F) / 2.0F) * var12;
               if (this.mChart.isUsePercentValuesEnabled()) {
                  var5 = var31.getY() / var17 * 100.0F;
               } else {
                  var5 = var31.getY();
               }

               String var33 = var24.getPieLabel(var5, var31);
               String var51 = var31.getLabel();
               double var34 = (double)(var32 * 0.017453292F);
               float var36 = (float)Math.cos(var34);
               float var37 = (float)Math.sin(var34);
               boolean var38;
               if (var18 && var26 == PieDataSet.ValuePosition.OUTSIDE_SLICE) {
                  var38 = true;
               } else {
                  var38 = false;
               }

               boolean var39;
               if (var23 && var27 == PieDataSet.ValuePosition.OUTSIDE_SLICE) {
                  var39 = true;
               } else {
                  var39 = false;
               }

               boolean var40;
               if (var18 && var26 == PieDataSet.ValuePosition.INSIDE_SLICE) {
                  var40 = true;
               } else {
                  var40 = false;
               }

               boolean var41;
               if (var23 && var27 == PieDataSet.ValuePosition.INSIDE_SLICE) {
                  var41 = true;
               } else {
                  var41 = false;
               }

               float var43;
               float var44;
               if (var38 || var39) {
                  float var42 = var50.getValueLinePart1Length();
                  var10 = var50.getValueLinePart2Length();
                  var5 = var50.getValueLinePart1OffsetPercentage() / 100.0F;
                  if (this.mChart.isDrawHoleEnabled()) {
                     var43 = var4 * var11;
                     var5 = (var4 - var43) * var5 + var43;
                  } else {
                     var5 = var4 * var5;
                  }

                  if (var50.isValueLineVariableLength()) {
                     var10 = var10 * var13 * (float)Math.abs(Math.sin(var34));
                  } else {
                     var10 *= var13;
                  }

                  var44 = var3.x;
                  var43 = var3.y;
                  float var45 = (var42 + 1.0F) * var13;
                  var42 = var45 * var36 + var3.x;
                  var45 = var3.y + var45 * var37;
                  var34 = (double)var32 % 360.0D;
                  if (var34 >= 90.0D && var34 <= 270.0D) {
                     var32 = var42 - var10;
                     this.mValuePaint.setTextAlign(Align.RIGHT);
                     if (var38) {
                        this.mEntryLabelsPaint.setTextAlign(Align.RIGHT);
                     }

                     var10 = var32;
                     var32 -= var19;
                  } else {
                     var10 += var42;
                     this.mValuePaint.setTextAlign(Align.LEFT);
                     if (var38) {
                        this.mEntryLabelsPaint.setTextAlign(Align.LEFT);
                     }

                     var32 = var10 + var19;
                  }

                  if (var50.getValueLineColor() != 1122867) {
                     if (var50.isUsingSliceColorAsValueLineColor()) {
                        this.mValueLinePaint.setColor(var50.getColor(var30));
                     }

                     var1.drawLine(var5 * var36 + var44, var5 * var37 + var43, var42, var45, this.mValueLinePaint);
                     var1.drawLine(var42, var45, var10, var45, this.mValueLinePaint);
                  }

                  if (var38 && var39) {
                     this.drawValue(var1, var33, var32, var45, var50.getValueTextColor(var30));
                     if (var30 < var15.getEntryCount() && var51 != null) {
                        this.drawEntryLabel(var1, var51, var32, var45 + var28);
                     }
                  } else if (var38) {
                     if (var30 < var15.getEntryCount() && var51 != null) {
                        this.drawEntryLabel(var1, var51, var32, var45 + var28 / 2.0F);
                     }
                  } else if (var39) {
                     this.drawValue(var1, var33, var32, var45 + var28 / 2.0F, var50.getValueTextColor(var30));
                  }
               }

               if (var40 || var41) {
                  var5 = var13 * var36 + var3.x;
                  var10 = var13 * var37 + var3.y;
                  this.mValuePaint.setTextAlign(Align.CENTER);
                  if (var40 && var41) {
                     this.drawValue(var1, var33, var5, var10, var50.getValueTextColor(var30));
                     if (var30 < var15.getEntryCount() && var51 != null) {
                        this.drawEntryLabel(var1, var51, var5, var10 + var28);
                     }
                  } else if (var40) {
                     if (var30 < var15.getEntryCount() && var51 != null) {
                        this.drawEntryLabel(var1, var51, var5, var10 + var28 / 2.0F);
                     }
                  } else if (var41) {
                     this.drawValue(var1, var33, var5, var10 + var28 / 2.0F, var50.getValueTextColor(var30));
                  }
               }

               if (var31.getIcon() != null && var50.isDrawIconsEnabled()) {
                  Drawable var54 = var31.getIcon();
                  var10 = var25.y;
                  var43 = var3.x;
                  var32 = var25.y;
                  var5 = var3.y;
                  var44 = var25.x;
                  Utils.drawImage(var1, var54, (int)((var13 + var10) * var36 + var43), (int)((var13 + var32) * var37 + var5 + var44), var54.getIntrinsicWidth(), var54.getIntrinsicHeight());
               }

               ++var20;
            }

            var9 = var4;
            var7 = var6;
            var4 = var8;
            var5 = var12;
            var12 = var14;
            var8 = var13;
            var49 = var3;
            MPPointF.recycleInstance(var25);
            var13 = var9;
            var14 = var5;
            var48 = var2;
         }

         ++var21;
         var53 = var7;
         var9 = var8;
         var7 = var48;
         var8 = var4;
         var5 = var14;
         var14 = var12;
         var3 = var49;
         var4 = var13;
         var6 = var53;
         var12 = var5;
      }

      MPPointF.recycleInstance(var3);
      var1.restore();
   }

   public TextPaint getPaintCenterText() {
      return this.mCenterTextPaint;
   }

   public Paint getPaintEntryLabels() {
      return this.mEntryLabelsPaint;
   }

   public Paint getPaintHole() {
      return this.mHolePaint;
   }

   public Paint getPaintTransparentCircle() {
      return this.mTransparentCirclePaint;
   }

   protected float getSliceSpace(IPieDataSet var1) {
      if (!var1.isAutomaticallyDisableSliceSpacingEnabled()) {
         return var1.getSliceSpace();
      } else {
         float var2;
         if (var1.getSliceSpace() / this.mViewPortHandler.getSmallestContentExtension() > var1.getYMin() / ((PieData)this.mChart.getData()).getYValueSum() * 2.0F) {
            var2 = 0.0F;
         } else {
            var2 = var1.getSliceSpace();
         }

         return var2;
      }
   }

   public void initBuffers() {
   }

   public void releaseBitmap() {
      Canvas var1 = this.mBitmapCanvas;
      if (var1 != null) {
         var1.setBitmap((Bitmap)null);
         this.mBitmapCanvas = null;
      }

      WeakReference var2 = this.mDrawBitmap;
      if (var2 != null) {
         Bitmap var3 = (Bitmap)var2.get();
         if (var3 != null) {
            var3.recycle();
         }

         this.mDrawBitmap.clear();
         this.mDrawBitmap = null;
      }

   }
}
