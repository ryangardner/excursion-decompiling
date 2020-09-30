package com.github.mikephil.charting.data;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.interfaces.datasets.ILineRadarDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

public abstract class LineRadarDataSet<T extends Entry> extends LineScatterCandleRadarDataSet<T> implements ILineRadarDataSet<T> {
   private boolean mDrawFilled = false;
   private int mFillAlpha = 85;
   private int mFillColor = Color.rgb(140, 234, 255);
   protected Drawable mFillDrawable;
   private float mLineWidth = 2.5F;

   public LineRadarDataSet(List<T> var1, String var2) {
      super(var1, var2);
   }

   protected void copy(LineRadarDataSet var1) {
      super.copy(var1);
      var1.mDrawFilled = this.mDrawFilled;
      var1.mFillAlpha = this.mFillAlpha;
      var1.mFillColor = this.mFillColor;
      var1.mFillDrawable = this.mFillDrawable;
      var1.mLineWidth = this.mLineWidth;
   }

   public int getFillAlpha() {
      return this.mFillAlpha;
   }

   public int getFillColor() {
      return this.mFillColor;
   }

   public Drawable getFillDrawable() {
      return this.mFillDrawable;
   }

   public float getLineWidth() {
      return this.mLineWidth;
   }

   public boolean isDrawFilledEnabled() {
      return this.mDrawFilled;
   }

   public void setDrawFilled(boolean var1) {
      this.mDrawFilled = var1;
   }

   public void setFillAlpha(int var1) {
      this.mFillAlpha = var1;
   }

   public void setFillColor(int var1) {
      this.mFillColor = var1;
      this.mFillDrawable = null;
   }

   public void setFillDrawable(Drawable var1) {
      this.mFillDrawable = var1;
   }

   public void setLineWidth(float var1) {
      float var2 = var1;
      if (var1 < 0.0F) {
         var2 = 0.0F;
      }

      var1 = var2;
      if (var2 > 10.0F) {
         var1 = 10.0F;
      }

      this.mLineWidth = Utils.convertDpToPixel(var1);
   }
}