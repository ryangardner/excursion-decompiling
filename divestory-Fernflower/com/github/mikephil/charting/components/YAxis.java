package com.github.mikephil.charting.components;

import android.graphics.Paint;
import com.github.mikephil.charting.utils.Utils;

public class YAxis extends AxisBase {
   private YAxis.AxisDependency mAxisDependency;
   private boolean mDrawBottomYLabelEntry = true;
   private boolean mDrawTopYLabelEntry = true;
   protected boolean mDrawZeroLine = false;
   protected boolean mInverted = false;
   protected float mMaxWidth;
   protected float mMinWidth;
   private YAxis.YAxisLabelPosition mPosition;
   protected float mSpacePercentBottom = 10.0F;
   protected float mSpacePercentTop = 10.0F;
   private boolean mUseAutoScaleRestrictionMax = false;
   private boolean mUseAutoScaleRestrictionMin = false;
   protected int mZeroLineColor = -7829368;
   protected float mZeroLineWidth = 1.0F;

   public YAxis() {
      this.mPosition = YAxis.YAxisLabelPosition.OUTSIDE_CHART;
      this.mMinWidth = 0.0F;
      this.mMaxWidth = Float.POSITIVE_INFINITY;
      this.mAxisDependency = YAxis.AxisDependency.LEFT;
      this.mYOffset = 0.0F;
   }

   public YAxis(YAxis.AxisDependency var1) {
      this.mPosition = YAxis.YAxisLabelPosition.OUTSIDE_CHART;
      this.mMinWidth = 0.0F;
      this.mMaxWidth = Float.POSITIVE_INFINITY;
      this.mAxisDependency = var1;
      this.mYOffset = 0.0F;
   }

   public void calculate(float var1, float var2) {
      float var3 = var1;
      float var4 = var2;
      if (Math.abs(var2 - var1) == 0.0F) {
         var4 = var2 + 1.0F;
         var3 = var1 - 1.0F;
      }

      var2 = Math.abs(var4 - var3);
      if (this.mCustomAxisMin) {
         var1 = this.mAxisMinimum;
      } else {
         var1 = var3 - var2 / 100.0F * this.getSpaceBottom();
      }

      this.mAxisMinimum = var1;
      if (this.mCustomAxisMax) {
         var1 = this.mAxisMaximum;
      } else {
         var1 = var4 + var2 / 100.0F * this.getSpaceTop();
      }

      this.mAxisMaximum = var1;
      this.mAxisRange = Math.abs(this.mAxisMinimum - this.mAxisMaximum);
   }

   public YAxis.AxisDependency getAxisDependency() {
      return this.mAxisDependency;
   }

   public YAxis.YAxisLabelPosition getLabelPosition() {
      return this.mPosition;
   }

   public float getMaxWidth() {
      return this.mMaxWidth;
   }

   public float getMinWidth() {
      return this.mMinWidth;
   }

   public float getRequiredHeightSpace(Paint var1) {
      var1.setTextSize(this.mTextSize);
      return (float)Utils.calcTextHeight(var1, this.getLongestLabel()) + this.getYOffset() * 2.0F;
   }

   public float getRequiredWidthSpace(Paint var1) {
      var1.setTextSize(this.mTextSize);
      float var2 = (float)Utils.calcTextWidth(var1, this.getLongestLabel()) + this.getXOffset() * 2.0F;
      float var3 = this.getMinWidth();
      float var4 = this.getMaxWidth();
      float var5 = var3;
      if (var3 > 0.0F) {
         var5 = Utils.convertDpToPixel(var3);
      }

      var3 = var4;
      if (var4 > 0.0F) {
         var3 = var4;
         if (var4 != Float.POSITIVE_INFINITY) {
            var3 = Utils.convertDpToPixel(var4);
         }
      }

      if ((double)var3 <= 0.0D) {
         var3 = var2;
      }

      return Math.max(var5, Math.min(var2, var3));
   }

   public float getSpaceBottom() {
      return this.mSpacePercentBottom;
   }

   public float getSpaceTop() {
      return this.mSpacePercentTop;
   }

   public int getZeroLineColor() {
      return this.mZeroLineColor;
   }

   public float getZeroLineWidth() {
      return this.mZeroLineWidth;
   }

   public boolean isDrawBottomYLabelEntryEnabled() {
      return this.mDrawBottomYLabelEntry;
   }

   public boolean isDrawTopYLabelEntryEnabled() {
      return this.mDrawTopYLabelEntry;
   }

   public boolean isDrawZeroLineEnabled() {
      return this.mDrawZeroLine;
   }

   public boolean isInverted() {
      return this.mInverted;
   }

   @Deprecated
   public boolean isUseAutoScaleMaxRestriction() {
      return this.mUseAutoScaleRestrictionMax;
   }

   @Deprecated
   public boolean isUseAutoScaleMinRestriction() {
      return this.mUseAutoScaleRestrictionMin;
   }

   public boolean needsOffset() {
      return this.isEnabled() && this.isDrawLabelsEnabled() && this.getLabelPosition() == YAxis.YAxisLabelPosition.OUTSIDE_CHART;
   }

   public void setDrawTopYLabelEntry(boolean var1) {
      this.mDrawTopYLabelEntry = var1;
   }

   public void setDrawZeroLine(boolean var1) {
      this.mDrawZeroLine = var1;
   }

   public void setInverted(boolean var1) {
      this.mInverted = var1;
   }

   public void setMaxWidth(float var1) {
      this.mMaxWidth = var1;
   }

   public void setMinWidth(float var1) {
      this.mMinWidth = var1;
   }

   public void setPosition(YAxis.YAxisLabelPosition var1) {
      this.mPosition = var1;
   }

   public void setSpaceBottom(float var1) {
      this.mSpacePercentBottom = var1;
   }

   public void setSpaceTop(float var1) {
      this.mSpacePercentTop = var1;
   }

   @Deprecated
   public void setStartAtZero(boolean var1) {
      if (var1) {
         this.setAxisMinimum(0.0F);
      } else {
         this.resetAxisMinimum();
      }

   }

   @Deprecated
   public void setUseAutoScaleMaxRestriction(boolean var1) {
      this.mUseAutoScaleRestrictionMax = var1;
   }

   @Deprecated
   public void setUseAutoScaleMinRestriction(boolean var1) {
      this.mUseAutoScaleRestrictionMin = var1;
   }

   public void setZeroLineColor(int var1) {
      this.mZeroLineColor = var1;
   }

   public void setZeroLineWidth(float var1) {
      this.mZeroLineWidth = Utils.convertDpToPixel(var1);
   }

   public static enum AxisDependency {
      LEFT,
      RIGHT;

      static {
         YAxis.AxisDependency var0 = new YAxis.AxisDependency("RIGHT", 1);
         RIGHT = var0;
      }
   }

   public static enum YAxisLabelPosition {
      INSIDE_CHART,
      OUTSIDE_CHART;

      static {
         YAxis.YAxisLabelPosition var0 = new YAxis.YAxisLabelPosition("INSIDE_CHART", 1);
         INSIDE_CHART = var0;
      }
   }
}
