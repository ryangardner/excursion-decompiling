package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LineChartRenderer extends LineRadarRenderer {
   protected Path cubicFillPath;
   protected Path cubicPath;
   protected Canvas mBitmapCanvas;
   protected Config mBitmapConfig;
   protected LineDataProvider mChart;
   protected Paint mCirclePaintInner;
   private float[] mCirclesBuffer;
   protected WeakReference<Bitmap> mDrawBitmap;
   protected Path mGenerateFilledPathBuffer;
   private HashMap<IDataSet, LineChartRenderer.DataSetImageCache> mImageCaches;
   private float[] mLineBuffer;

   public LineChartRenderer(LineDataProvider var1, ChartAnimator var2, ViewPortHandler var3) {
      super(var2, var3);
      this.mBitmapConfig = Config.ARGB_8888;
      this.cubicPath = new Path();
      this.cubicFillPath = new Path();
      this.mLineBuffer = new float[4];
      this.mGenerateFilledPathBuffer = new Path();
      this.mImageCaches = new HashMap();
      this.mCirclesBuffer = new float[2];
      this.mChart = var1;
      Paint var4 = new Paint(1);
      this.mCirclePaintInner = var4;
      var4.setStyle(Style.FILL);
      this.mCirclePaintInner.setColor(-1);
   }

   private void generateFilledPath(ILineDataSet var1, int var2, int var3, Path var4) {
      float var5 = var1.getFillFormatter().getFillLinePosition(var1, this.mChart);
      float var6 = this.mAnimator.getPhaseY();
      boolean var7;
      if (var1.getMode() == LineDataSet.Mode.STEPPED) {
         var7 = true;
      } else {
         var7 = false;
      }

      var4.reset();
      Entry var8 = var1.getEntryForIndex(var2);
      var4.moveTo(var8.getX(), var5);
      var4.lineTo(var8.getX(), var8.getY() * var6);
      Entry var9 = null;
      ++var2;

      while(var2 <= var3) {
         var9 = var1.getEntryForIndex(var2);
         if (var7) {
            var4.lineTo(var9.getX(), var8.getY() * var6);
         }

         var4.lineTo(var9.getX(), var9.getY() * var6);
         ++var2;
         var8 = var9;
      }

      if (var9 != null) {
         var4.lineTo(var9.getX(), var5);
      }

      var4.close();
   }

   protected void drawCircles(Canvas var1) {
      this.mRenderPaint.setStyle(Style.FILL);
      float var2 = this.mAnimator.getPhaseY();
      float[] var3 = this.mCirclesBuffer;
      var3[0] = 0.0F;
      var3[1] = 0.0F;
      List var4 = this.mChart.getLineData().getDataSets();

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         ILineDataSet var6 = (ILineDataSet)var4.get(var5);
         if (var6.isVisible() && var6.isDrawCirclesEnabled() && var6.getEntryCount() != 0) {
            this.mCirclePaintInner.setColor(var6.getCircleHoleColor());
            Transformer var7 = this.mChart.getTransformer(var6.getAxisDependency());
            this.mXBounds.set(this.mChart, var6);
            float var8 = var6.getCircleRadius();
            float var9 = var6.getCircleHoleRadius();
            boolean var10;
            if (var6.isDrawCircleHoleEnabled() && var9 < var8 && var9 > 0.0F) {
               var10 = true;
            } else {
               var10 = false;
            }

            boolean var11;
            if (var10 && var6.getCircleHoleColor() == 1122867) {
               var11 = true;
            } else {
               var11 = false;
            }

            LineChartRenderer.DataSetImageCache var17;
            if (this.mImageCaches.containsKey(var6)) {
               var17 = (LineChartRenderer.DataSetImageCache)this.mImageCaches.get(var6);
            } else {
               var17 = new LineChartRenderer.DataSetImageCache();
               this.mImageCaches.put(var6, var17);
            }

            if (var17.init(var6)) {
               var17.fill(var6, var10, var11);
            }

            int var12 = this.mXBounds.range;
            int var13 = this.mXBounds.min;

            for(int var14 = this.mXBounds.min; var14 <= var12 + var13; ++var14) {
               Entry var15 = var6.getEntryForIndex(var14);
               if (var15 == null) {
                  break;
               }

               this.mCirclesBuffer[0] = var15.getX();
               this.mCirclesBuffer[1] = var15.getY() * var2;
               var7.pointValuesToPixel(this.mCirclesBuffer);
               if (!this.mViewPortHandler.isInBoundsRight(this.mCirclesBuffer[0])) {
                  break;
               }

               if (this.mViewPortHandler.isInBoundsLeft(this.mCirclesBuffer[0]) && this.mViewPortHandler.isInBoundsY(this.mCirclesBuffer[1])) {
                  Bitmap var16 = var17.getBitmap(var14);
                  if (var16 != null) {
                     float[] var18 = this.mCirclesBuffer;
                     var1.drawBitmap(var16, var18[0] - var8, var18[1] - var8, (Paint)null);
                  }
               }
            }
         }
      }

   }

   protected void drawCubicBezier(ILineDataSet var1) {
      float var2 = this.mAnimator.getPhaseY();
      Transformer var3 = this.mChart.getTransformer(var1.getAxisDependency());
      this.mXBounds.set(this.mChart, var1);
      float var4 = var1.getCubicIntensity();
      this.cubicPath.reset();
      if (this.mXBounds.range >= 1) {
         int var5 = this.mXBounds.min + 1;
         int var6 = this.mXBounds.min;
         var6 = this.mXBounds.range;
         Entry var7 = var1.getEntryForIndex(Math.max(var5 - 2, 0));
         Entry var8 = var1.getEntryForIndex(Math.max(var5 - 1, 0));
         int var9 = -1;
         if (var8 == null) {
            return;
         }

         this.cubicPath.moveTo(var8.getX(), var8.getY() * var2);
         var5 = this.mXBounds.min + 1;

         for(Entry var10 = var8; var5 <= this.mXBounds.range + this.mXBounds.min; var5 = var6) {
            if (var9 != var5) {
               var8 = var1.getEntryForIndex(var5);
            }

            var6 = var5 + 1;
            if (var6 < var1.getEntryCount()) {
               var5 = var6;
            }

            Entry var11 = var1.getEntryForIndex(var5);
            float var12 = var8.getX();
            float var13 = var7.getX();
            float var14 = var8.getY();
            float var15 = var7.getY();
            float var16 = var11.getX();
            float var17 = var10.getX();
            float var18 = var11.getY();
            float var19 = var10.getY();
            this.cubicPath.cubicTo(var10.getX() + (var12 - var13) * var4, (var10.getY() + (var14 - var15) * var4) * var2, var8.getX() - (var16 - var17) * var4, (var8.getY() - (var18 - var19) * var4) * var2, var8.getX(), var8.getY() * var2);
            var7 = var10;
            var10 = var8;
            var8 = var11;
            var9 = var5;
         }
      }

      if (var1.isDrawFilledEnabled()) {
         this.cubicFillPath.reset();
         this.cubicFillPath.addPath(this.cubicPath);
         this.drawCubicFill(this.mBitmapCanvas, var1, this.cubicFillPath, var3, this.mXBounds);
      }

      this.mRenderPaint.setColor(var1.getColor());
      this.mRenderPaint.setStyle(Style.STROKE);
      var3.pathValueToPixel(this.cubicPath);
      this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
      this.mRenderPaint.setPathEffect((PathEffect)null);
   }

   protected void drawCubicFill(Canvas var1, ILineDataSet var2, Path var3, Transformer var4, BarLineScatterCandleBubbleRenderer.XBounds var5) {
      float var6 = var2.getFillFormatter().getFillLinePosition(var2, this.mChart);
      var3.lineTo(var2.getEntryForIndex(var5.min + var5.range).getX(), var6);
      var3.lineTo(var2.getEntryForIndex(var5.min).getX(), var6);
      var3.close();
      var4.pathValueToPixel(var3);
      Drawable var7 = var2.getFillDrawable();
      if (var7 != null) {
         this.drawFilledPath(var1, var3, var7);
      } else {
         this.drawFilledPath(var1, var3, var2.getFillColor(), var2.getFillAlpha());
      }

   }

   public void drawData(Canvas var1) {
      int var2 = (int)this.mViewPortHandler.getChartWidth();
      int var3 = (int)this.mViewPortHandler.getChartHeight();
      WeakReference var4 = this.mDrawBitmap;
      Bitmap var7;
      if (var4 == null) {
         var7 = null;
      } else {
         var7 = (Bitmap)var4.get();
      }

      Bitmap var5;
      label42: {
         if (var7 != null && var7.getWidth() == var2) {
            var5 = var7;
            if (var7.getHeight() == var3) {
               break label42;
            }
         }

         if (var2 <= 0 || var3 <= 0) {
            return;
         }

         var5 = Bitmap.createBitmap(var2, var3, this.mBitmapConfig);
         this.mDrawBitmap = new WeakReference(var5);
         this.mBitmapCanvas = new Canvas(var5);
      }

      var5.eraseColor(0);
      Iterator var6 = this.mChart.getLineData().getDataSets().iterator();

      while(var6.hasNext()) {
         ILineDataSet var8 = (ILineDataSet)var6.next();
         if (var8.isVisible()) {
            this.drawDataSet(var1, var8);
         }
      }

      var1.drawBitmap(var5, 0.0F, 0.0F, this.mRenderPaint);
   }

   protected void drawDataSet(Canvas var1, ILineDataSet var2) {
      if (var2.getEntryCount() >= 1) {
         this.mRenderPaint.setStrokeWidth(var2.getLineWidth());
         this.mRenderPaint.setPathEffect(var2.getDashPathEffect());
         int var3 = null.$SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[var2.getMode().ordinal()];
         if (var3 != 3) {
            if (var3 != 4) {
               this.drawLinear(var1, var2);
            } else {
               this.drawHorizontalBezier(var2);
            }
         } else {
            this.drawCubicBezier(var2);
         }

         this.mRenderPaint.setPathEffect((PathEffect)null);
      }
   }

   public void drawExtras(Canvas var1) {
      this.drawCircles(var1);
   }

   public void drawHighlighted(Canvas var1, Highlight[] var2) {
      LineData var3 = this.mChart.getLineData();
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Highlight var6 = var2[var5];
         ILineDataSet var7 = (ILineDataSet)var3.getDataSetByIndex(var6.getDataSetIndex());
         if (var7 != null && var7.isHighlightEnabled()) {
            Entry var8 = var7.getEntryForXValue(var6.getX(), var6.getY());
            if (this.isInBoundsX(var8, var7)) {
               MPPointD var9 = this.mChart.getTransformer(var7.getAxisDependency()).getPixelForValues(var8.getX(), var8.getY() * this.mAnimator.getPhaseY());
               var6.setDraw((float)var9.x, (float)var9.y);
               this.drawHighlightLines(var1, (float)var9.x, (float)var9.y, var7);
            }
         }
      }

   }

   protected void drawHorizontalBezier(ILineDataSet var1) {
      float var2 = this.mAnimator.getPhaseY();
      Transformer var3 = this.mChart.getTransformer(var1.getAxisDependency());
      this.mXBounds.set(this.mChart, var1);
      this.cubicPath.reset();
      if (this.mXBounds.range >= 1) {
         Entry var4 = var1.getEntryForIndex(this.mXBounds.min);
         this.cubicPath.moveTo(var4.getX(), var4.getY() * var2);

         Entry var6;
         for(int var5 = this.mXBounds.min + 1; var5 <= this.mXBounds.range + this.mXBounds.min; var4 = var6) {
            var6 = var1.getEntryForIndex(var5);
            float var7 = var4.getX() + (var6.getX() - var4.getX()) / 2.0F;
            this.cubicPath.cubicTo(var7, var4.getY() * var2, var7, var6.getY() * var2, var6.getX(), var6.getY() * var2);
            ++var5;
         }
      }

      if (var1.isDrawFilledEnabled()) {
         this.cubicFillPath.reset();
         this.cubicFillPath.addPath(this.cubicPath);
         this.drawCubicFill(this.mBitmapCanvas, var1, this.cubicFillPath, var3, this.mXBounds);
      }

      this.mRenderPaint.setColor(var1.getColor());
      this.mRenderPaint.setStyle(Style.STROKE);
      var3.pathValueToPixel(this.cubicPath);
      this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
      this.mRenderPaint.setPathEffect((PathEffect)null);
   }

   protected void drawLinear(Canvas var1, ILineDataSet var2) {
      int var3 = var2.getEntryCount();
      boolean var4;
      if (var2.getMode() == LineDataSet.Mode.STEPPED) {
         var4 = true;
      } else {
         var4 = false;
      }

      byte var5;
      if (var4) {
         var5 = 4;
      } else {
         var5 = 2;
      }

      Transformer var6 = this.mChart.getTransformer(var2.getAxisDependency());
      float var7 = this.mAnimator.getPhaseY();
      this.mRenderPaint.setStyle(Style.STROKE);
      Canvas var8;
      if (var2.isDashedLineEnabled()) {
         var8 = this.mBitmapCanvas;
      } else {
         var8 = var1;
      }

      this.mXBounds.set(this.mChart, var2);
      if (var2.isDrawFilledEnabled() && var3 > 0) {
         this.drawLinearFill(var1, var2, var6, this.mXBounds);
      }

      int var9;
      Entry var10;
      Entry var14;
      if (var2.getColors().size() > 1) {
         var9 = this.mLineBuffer.length;
         var3 = var5 * 2;
         if (var9 <= var3) {
            this.mLineBuffer = new float[var5 * 4];
         }

         for(int var17 = this.mXBounds.min; var17 <= this.mXBounds.range + this.mXBounds.min; ++var17) {
            var14 = var2.getEntryForIndex(var17);
            if (var14 != null) {
               this.mLineBuffer[0] = var14.getX();
               this.mLineBuffer[1] = var14.getY() * var7;
               float[] var15;
               if (var17 < this.mXBounds.max) {
                  var10 = var2.getEntryForIndex(var17 + 1);
                  if (var10 == null) {
                     break;
                  }

                  if (var4) {
                     this.mLineBuffer[2] = var10.getX();
                     var15 = this.mLineBuffer;
                     var15[3] = var15[1];
                     var15[4] = var15[2];
                     var15[5] = var15[3];
                     var15[6] = var10.getX();
                     this.mLineBuffer[7] = var10.getY() * var7;
                  } else {
                     this.mLineBuffer[2] = var10.getX();
                     this.mLineBuffer[3] = var10.getY() * var7;
                  }
               } else {
                  var15 = this.mLineBuffer;
                  var15[2] = var15[0];
                  var15[3] = var15[1];
               }

               var6.pointValuesToPixel(this.mLineBuffer);
               if (!this.mViewPortHandler.isInBoundsRight(this.mLineBuffer[0])) {
                  break;
               }

               if (this.mViewPortHandler.isInBoundsLeft(this.mLineBuffer[2]) && (this.mViewPortHandler.isInBoundsTop(this.mLineBuffer[1]) || this.mViewPortHandler.isInBoundsBottom(this.mLineBuffer[3]))) {
                  this.mRenderPaint.setColor(var2.getColor(var17));
                  var8.drawLines(this.mLineBuffer, 0, var3, this.mRenderPaint);
               }
            }
         }
      } else {
         var9 = this.mLineBuffer.length;
         var3 *= var5;
         if (var9 < Math.max(var3, var5) * 2) {
            this.mLineBuffer = new float[Math.max(var3, var5) * 4];
         }

         if (var2.getEntryForIndex(this.mXBounds.min) != null) {
            var3 = this.mXBounds.min;

            int var11;
            for(var9 = 0; var3 <= this.mXBounds.range + this.mXBounds.min; var9 = var11) {
               if (var3 == 0) {
                  var11 = 0;
               } else {
                  var11 = var3 - 1;
               }

               var10 = var2.getEntryForIndex(var11);
               var14 = var2.getEntryForIndex(var3);
               var11 = var9;
               if (var10 != null) {
                  if (var14 == null) {
                     var11 = var9;
                  } else {
                     float[] var12 = this.mLineBuffer;
                     int var13 = var9 + 1;
                     var12[var9] = var10.getX();
                     var12 = this.mLineBuffer;
                     var11 = var13 + 1;
                     var12[var13] = var10.getY() * var7;
                     var9 = var11;
                     if (var4) {
                        var12 = this.mLineBuffer;
                        var9 = var11 + 1;
                        var12[var11] = var14.getX();
                        var12 = this.mLineBuffer;
                        var13 = var9 + 1;
                        var12[var9] = var10.getY() * var7;
                        var12 = this.mLineBuffer;
                        var11 = var13 + 1;
                        var12[var13] = var14.getX();
                        var12 = this.mLineBuffer;
                        var9 = var11 + 1;
                        var12[var11] = var10.getY() * var7;
                     }

                     float[] var18 = this.mLineBuffer;
                     var11 = var9 + 1;
                     var18[var9] = var14.getX();
                     this.mLineBuffer[var11] = var14.getY() * var7;
                     ++var11;
                  }
               }

               ++var3;
            }

            if (var9 > 0) {
               var6.pointValuesToPixel(this.mLineBuffer);
               int var16 = Math.max((this.mXBounds.range + 1) * var5, var5);
               this.mRenderPaint.setColor(var2.getColor());
               var8.drawLines(this.mLineBuffer, 0, var16 * 2, this.mRenderPaint);
            }
         }
      }

      this.mRenderPaint.setPathEffect((PathEffect)null);
   }

   protected void drawLinearFill(Canvas var1, ILineDataSet var2, Transformer var3, BarLineScatterCandleBubbleRenderer.XBounds var4) {
      Path var5 = this.mGenerateFilledPathBuffer;
      int var6 = var4.min;
      int var7 = var4.range + var4.min;
      int var8 = 0;

      int var9;
      int var11;
      do {
         var9 = var8 * 128 + var6;
         int var10 = var9 + 128;
         var11 = var10;
         if (var10 > var7) {
            var11 = var7;
         }

         if (var9 <= var11) {
            this.generateFilledPath(var2, var9, var11, var5);
            var3.pathValueToPixel(var5);
            Drawable var12 = var2.getFillDrawable();
            if (var12 != null) {
               this.drawFilledPath(var1, var5, var12);
            } else {
               this.drawFilledPath(var1, var5, var2.getFillColor(), var2.getFillAlpha());
            }
         }

         ++var8;
      } while(var9 <= var11);

   }

   public void drawValue(Canvas var1, String var2, float var3, float var4, int var5) {
      this.mValuePaint.setColor(var5);
      var1.drawText(var2, var3, var4, this.mValuePaint);
   }

   public void drawValues(Canvas var1) {
      if (this.isDrawingValuesAllowed(this.mChart)) {
         List var2 = this.mChart.getLineData().getDataSets();

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            ILineDataSet var4 = (ILineDataSet)var2.get(var3);
            if (this.shouldDrawValues(var4) && var4.getEntryCount() >= 1) {
               this.applyValueTextStyle(var4);
               Transformer var5 = this.mChart.getTransformer(var4.getAxisDependency());
               int var6 = (int)(var4.getCircleRadius() * 1.75F);
               int var7 = var6;
               if (!var4.isDrawCirclesEnabled()) {
                  var7 = var6 / 2;
               }

               this.mXBounds.set(this.mChart, var4);
               float[] var8 = var5.generateTransformedValuesLine(var4, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
               ValueFormatter var9 = var4.getValueFormatter();
               MPPointF var10 = MPPointF.getInstance(var4.getIconsOffset());
               var10.x = Utils.convertDpToPixel(var10.x);
               var10.y = Utils.convertDpToPixel(var10.y);

               for(var6 = 0; var6 < var8.length; var6 += 2) {
                  float var11 = var8[var6];
                  float var12 = var8[var6 + 1];
                  if (!this.mViewPortHandler.isInBoundsRight(var11)) {
                     break;
                  }

                  if (this.mViewPortHandler.isInBoundsLeft(var11) && this.mViewPortHandler.isInBoundsY(var12)) {
                     int var13 = var6 / 2;
                     Entry var14 = var4.getEntryForIndex(this.mXBounds.min + var13);
                     if (var4.isDrawValuesEnabled()) {
                        this.drawValue(var1, var9.getPointLabel(var14), var11, var12 - (float)var7, var4.getValueTextColor(var13));
                     }

                     if (var14.getIcon() != null && var4.isDrawIconsEnabled()) {
                        Drawable var15 = var14.getIcon();
                        Utils.drawImage(var1, var15, (int)(var11 + var10.x), (int)(var12 + var10.y), var15.getIntrinsicWidth(), var15.getIntrinsicHeight());
                     }
                  }
               }

               MPPointF.recycleInstance(var10);
            }
         }
      }

   }

   public Config getBitmapConfig() {
      return this.mBitmapConfig;
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

   public void setBitmapConfig(Config var1) {
      this.mBitmapConfig = var1;
      this.releaseBitmap();
   }

   private class DataSetImageCache {
      private Bitmap[] circleBitmaps;
      private Path mCirclePathBuffer;

      private DataSetImageCache() {
         this.mCirclePathBuffer = new Path();
      }

      // $FF: synthetic method
      DataSetImageCache(Object var2) {
         this();
      }

      protected void fill(ILineDataSet var1, boolean var2, boolean var3) {
         int var4 = var1.getCircleColorCount();
         float var5 = var1.getCircleRadius();
         float var6 = var1.getCircleHoleRadius();

         for(int var7 = 0; var7 < var4; ++var7) {
            Config var8 = Config.ARGB_4444;
            int var9 = (int)((double)var5 * 2.1D);
            Bitmap var11 = Bitmap.createBitmap(var9, var9, var8);
            Canvas var10 = new Canvas(var11);
            this.circleBitmaps[var7] = var11;
            LineChartRenderer.this.mRenderPaint.setColor(var1.getCircleColor(var7));
            if (var3) {
               this.mCirclePathBuffer.reset();
               this.mCirclePathBuffer.addCircle(var5, var5, var5, Direction.CW);
               this.mCirclePathBuffer.addCircle(var5, var5, var6, Direction.CCW);
               var10.drawPath(this.mCirclePathBuffer, LineChartRenderer.this.mRenderPaint);
            } else {
               var10.drawCircle(var5, var5, var5, LineChartRenderer.this.mRenderPaint);
               if (var2) {
                  var10.drawCircle(var5, var5, var6, LineChartRenderer.this.mCirclePaintInner);
               }
            }
         }

      }

      protected Bitmap getBitmap(int var1) {
         Bitmap[] var2 = this.circleBitmaps;
         return var2[var1 % var2.length];
      }

      protected boolean init(ILineDataSet var1) {
         int var2 = var1.getCircleColorCount();
         Bitmap[] var4 = this.circleBitmaps;
         boolean var3 = true;
         if (var4 == null) {
            this.circleBitmaps = new Bitmap[var2];
         } else if (var4.length != var2) {
            this.circleBitmaps = new Bitmap[var2];
         } else {
            var3 = false;
         }

         return var3;
      }
   }
}
