package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class XAxisRenderer extends AxisRenderer {
   protected RectF mGridClippingRect = new RectF();
   protected RectF mLimitLineClippingRect = new RectF();
   private Path mLimitLinePath = new Path();
   float[] mLimitLineSegmentsBuffer = new float[4];
   protected float[] mRenderGridLinesBuffer = new float[2];
   protected Path mRenderGridLinesPath = new Path();
   protected float[] mRenderLimitLinesBuffer = new float[2];
   protected XAxis mXAxis;

   public XAxisRenderer(ViewPortHandler var1, XAxis var2, Transformer var3) {
      super(var1, var3, var2);
      this.mXAxis = var2;
      this.mAxisLabelPaint.setColor(-16777216);
      this.mAxisLabelPaint.setTextAlign(Align.CENTER);
      this.mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10.0F));
   }

   public void computeAxis(float var1, float var2, boolean var3) {
      float var4 = var1;
      float var5 = var2;
      if (this.mViewPortHandler.contentWidth() > 10.0F) {
         var4 = var1;
         var5 = var2;
         if (!this.mViewPortHandler.isFullyZoomedOutX()) {
            MPPointD var6 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
            MPPointD var7 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop());
            double var8;
            if (var3) {
               var1 = (float)var7.x;
               var8 = var6.x;
            } else {
               var1 = (float)var6.x;
               var8 = var7.x;
            }

            var5 = (float)var8;
            MPPointD.recycleInstance(var6);
            MPPointD.recycleInstance(var7);
            var4 = var1;
         }
      }

      this.computeAxisValues(var4, var5);
   }

   protected void computeAxisValues(float var1, float var2) {
      super.computeAxisValues(var1, var2);
      this.computeSize();
   }

   protected void computeSize() {
      String var1 = this.mXAxis.getLongestLabel();
      this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
      this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
      FSize var5 = Utils.calcTextSize(this.mAxisLabelPaint, var1);
      float var2 = var5.width;
      float var3 = (float)Utils.calcTextHeight(this.mAxisLabelPaint, "Q");
      FSize var4 = Utils.getSizeOfRotatedRectangleByDegrees(var2, var3, this.mXAxis.getLabelRotationAngle());
      this.mXAxis.mLabelWidth = Math.round(var2);
      this.mXAxis.mLabelHeight = Math.round(var3);
      this.mXAxis.mLabelRotatedWidth = Math.round(var4.width);
      this.mXAxis.mLabelRotatedHeight = Math.round(var4.height);
      FSize.recycleInstance(var4);
      FSize.recycleInstance(var5);
   }

   protected void drawGridLine(Canvas var1, float var2, float var3, Path var4) {
      var4.moveTo(var2, this.mViewPortHandler.contentBottom());
      var4.lineTo(var2, this.mViewPortHandler.contentTop());
      var1.drawPath(var4, this.mGridPaint);
      var4.reset();
   }

   protected void drawLabel(Canvas var1, String var2, float var3, float var4, MPPointF var5, float var6) {
      Utils.drawXAxisValue(var1, var2, var3, var4, this.mAxisLabelPaint, var5, var6);
   }

   protected void drawLabels(Canvas var1, float var2, MPPointF var3) {
      float var4 = this.mXAxis.getLabelRotationAngle();
      boolean var5 = this.mXAxis.isCenterAxisLabelsEnabled();
      int var6 = this.mXAxis.mEntryCount * 2;
      float[] var7 = new float[var6];

      int var8;
      for(var8 = 0; var8 < var6; var8 += 2) {
         if (var5) {
            var7[var8] = this.mXAxis.mCenteredEntries[var8 / 2];
         } else {
            var7[var8] = this.mXAxis.mEntries[var8 / 2];
         }
      }

      this.mTrans.pointValuesToPixel(var7);

      for(var8 = 0; var8 < var6; var8 += 2) {
         float var9 = var7[var8];
         if (this.mViewPortHandler.isInBoundsX(var9)) {
            ValueFormatter var10 = this.mXAxis.getValueFormatter();
            float[] var11 = this.mXAxis.mEntries;
            int var12 = var8 / 2;
            String var15 = var10.getAxisLabel(var11[var12], this.mXAxis);
            float var13 = var9;
            if (this.mXAxis.isAvoidFirstLastClippingEnabled()) {
               if (var12 == this.mXAxis.mEntryCount - 1 && this.mXAxis.mEntryCount > 1) {
                  float var14 = (float)Utils.calcTextWidth(this.mAxisLabelPaint, var15);
                  var13 = var9;
                  if (var14 > this.mViewPortHandler.offsetRight() * 2.0F) {
                     var13 = var9;
                     if (var9 + var14 > this.mViewPortHandler.getChartWidth()) {
                        var13 = var9 - var14 / 2.0F;
                     }
                  }
               } else {
                  var13 = var9;
                  if (var8 == 0) {
                     var13 = var9 + (float)Utils.calcTextWidth(this.mAxisLabelPaint, var15) / 2.0F;
                  }
               }
            }

            this.drawLabel(var1, var15, var13, var2, var3, var4);
         }
      }

   }

   public RectF getGridClippingRect() {
      this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
      this.mGridClippingRect.inset(-this.mAxis.getGridLineWidth(), 0.0F);
      return this.mGridClippingRect;
   }

   public void renderAxisLabels(Canvas var1) {
      if (this.mXAxis.isEnabled() && this.mXAxis.isDrawLabelsEnabled()) {
         float var2 = this.mXAxis.getYOffset();
         this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
         this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
         this.mAxisLabelPaint.setColor(this.mXAxis.getTextColor());
         MPPointF var3 = MPPointF.getInstance(0.0F, 0.0F);
         if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
            var3.x = 0.5F;
            var3.y = 1.0F;
            this.drawLabels(var1, this.mViewPortHandler.contentTop() - var2, var3);
         } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE) {
            var3.x = 0.5F;
            var3.y = 1.0F;
            this.drawLabels(var1, this.mViewPortHandler.contentTop() + var2 + (float)this.mXAxis.mLabelRotatedHeight, var3);
         } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
            var3.x = 0.5F;
            var3.y = 0.0F;
            this.drawLabels(var1, this.mViewPortHandler.contentBottom() + var2, var3);
         } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
            var3.x = 0.5F;
            var3.y = 0.0F;
            this.drawLabels(var1, this.mViewPortHandler.contentBottom() - var2 - (float)this.mXAxis.mLabelRotatedHeight, var3);
         } else {
            var3.x = 0.5F;
            var3.y = 1.0F;
            this.drawLabels(var1, this.mViewPortHandler.contentTop() - var2, var3);
            var3.x = 0.5F;
            var3.y = 0.0F;
            this.drawLabels(var1, this.mViewPortHandler.contentBottom() + var2, var3);
         }

         MPPointF.recycleInstance(var3);
      }

   }

   public void renderAxisLine(Canvas var1) {
      if (this.mXAxis.isDrawAxisLineEnabled() && this.mXAxis.isEnabled()) {
         this.mAxisLinePaint.setColor(this.mXAxis.getAxisLineColor());
         this.mAxisLinePaint.setStrokeWidth(this.mXAxis.getAxisLineWidth());
         this.mAxisLinePaint.setPathEffect(this.mXAxis.getAxisLineDashPathEffect());
         if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP || this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
            var1.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mAxisLinePaint);
         }

         if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
            var1.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
         }
      }

   }

   public void renderGridLines(Canvas var1) {
      if (this.mXAxis.isDrawGridLinesEnabled() && this.mXAxis.isEnabled()) {
         int var2 = var1.save();
         var1.clipRect(this.getGridClippingRect());
         if (this.mRenderGridLinesBuffer.length != this.mAxis.mEntryCount * 2) {
            this.mRenderGridLinesBuffer = new float[this.mXAxis.mEntryCount * 2];
         }

         float[] var3 = this.mRenderGridLinesBuffer;
         byte var4 = 0;

         int var5;
         for(var5 = 0; var5 < var3.length; var5 += 2) {
            float[] var6 = this.mXAxis.mEntries;
            int var7 = var5 / 2;
            var3[var5] = var6[var7];
            var3[var5 + 1] = this.mXAxis.mEntries[var7];
         }

         this.mTrans.pointValuesToPixel(var3);
         this.setupGridPaint();
         Path var8 = this.mRenderGridLinesPath;
         var8.reset();

         for(var5 = var4; var5 < var3.length; var5 += 2) {
            this.drawGridLine(var1, var3[var5], var3[var5 + 1], var8);
         }

         var1.restoreToCount(var2);
      }

   }

   public void renderLimitLineLabel(Canvas var1, LimitLine var2, float[] var3, float var4) {
      String var5 = var2.getLabel();
      if (var5 != null && !var5.equals("")) {
         this.mLimitLinePaint.setStyle(var2.getTextStyle());
         this.mLimitLinePaint.setPathEffect((PathEffect)null);
         this.mLimitLinePaint.setColor(var2.getTextColor());
         this.mLimitLinePaint.setStrokeWidth(0.5F);
         this.mLimitLinePaint.setTextSize(var2.getTextSize());
         float var6 = var2.getLineWidth() + var2.getXOffset();
         LimitLine.LimitLabelPosition var8 = var2.getLabelPosition();
         float var7;
         if (var8 == LimitLine.LimitLabelPosition.RIGHT_TOP) {
            var7 = (float)Utils.calcTextHeight(this.mLimitLinePaint, var5);
            this.mLimitLinePaint.setTextAlign(Align.LEFT);
            var1.drawText(var5, var3[0] + var6, this.mViewPortHandler.contentTop() + var4 + var7, this.mLimitLinePaint);
         } else if (var8 == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
            this.mLimitLinePaint.setTextAlign(Align.LEFT);
            var1.drawText(var5, var3[0] + var6, this.mViewPortHandler.contentBottom() - var4, this.mLimitLinePaint);
         } else if (var8 == LimitLine.LimitLabelPosition.LEFT_TOP) {
            this.mLimitLinePaint.setTextAlign(Align.RIGHT);
            var7 = (float)Utils.calcTextHeight(this.mLimitLinePaint, var5);
            var1.drawText(var5, var3[0] - var6, this.mViewPortHandler.contentTop() + var4 + var7, this.mLimitLinePaint);
         } else {
            this.mLimitLinePaint.setTextAlign(Align.RIGHT);
            var1.drawText(var5, var3[0] - var6, this.mViewPortHandler.contentBottom() - var4, this.mLimitLinePaint);
         }
      }

   }

   public void renderLimitLineLine(Canvas var1, LimitLine var2, float[] var3) {
      float[] var4 = this.mLimitLineSegmentsBuffer;
      var4[0] = var3[0];
      var4[1] = this.mViewPortHandler.contentTop();
      var4 = this.mLimitLineSegmentsBuffer;
      var4[2] = var3[0];
      var4[3] = this.mViewPortHandler.contentBottom();
      this.mLimitLinePath.reset();
      Path var5 = this.mLimitLinePath;
      var4 = this.mLimitLineSegmentsBuffer;
      var5.moveTo(var4[0], var4[1]);
      Path var6 = this.mLimitLinePath;
      var3 = this.mLimitLineSegmentsBuffer;
      var6.lineTo(var3[2], var3[3]);
      this.mLimitLinePaint.setStyle(Style.STROKE);
      this.mLimitLinePaint.setColor(var2.getLineColor());
      this.mLimitLinePaint.setStrokeWidth(var2.getLineWidth());
      this.mLimitLinePaint.setPathEffect(var2.getDashPathEffect());
      var1.drawPath(this.mLimitLinePath, this.mLimitLinePaint);
   }

   public void renderLimitLines(Canvas var1) {
      List var2 = this.mXAxis.getLimitLines();
      if (var2 != null && var2.size() > 0) {
         float[] var3 = this.mRenderLimitLinesBuffer;
         var3[0] = 0.0F;
         var3[1] = 0.0F;

         for(int var4 = 0; var4 < var2.size(); ++var4) {
            LimitLine var5 = (LimitLine)var2.get(var4);
            if (var5.isEnabled()) {
               int var6 = var1.save();
               this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
               this.mLimitLineClippingRect.inset(-var5.getLineWidth(), 0.0F);
               var1.clipRect(this.mLimitLineClippingRect);
               var3[0] = var5.getLimit();
               var3[1] = 0.0F;
               this.mTrans.pointValuesToPixel(var3);
               this.renderLimitLineLine(var1, var5, var3);
               this.renderLimitLineLabel(var1, var5, var3, var5.getYOffset() + 2.0F);
               var1.restoreToCount(var6);
            }
         }
      }

   }

   protected void setupGridPaint() {
      this.mGridPaint.setColor(this.mXAxis.getGridColor());
      this.mGridPaint.setStrokeWidth(this.mXAxis.getGridLineWidth());
      this.mGridPaint.setPathEffect(this.mXAxis.getGridDashPathEffect());
   }
}
