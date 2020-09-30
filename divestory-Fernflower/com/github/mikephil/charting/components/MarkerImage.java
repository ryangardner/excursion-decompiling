package com.github.mikephil.charting.components;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;
import java.lang.ref.WeakReference;

public class MarkerImage implements IMarker {
   private Context mContext;
   private Drawable mDrawable;
   private Rect mDrawableBoundsCache = new Rect();
   private MPPointF mOffset = new MPPointF();
   private MPPointF mOffset2 = new MPPointF();
   private FSize mSize = new FSize();
   private WeakReference<Chart> mWeakChart;

   public MarkerImage(Context var1, int var2) {
      this.mContext = var1;
      if (VERSION.SDK_INT >= 21) {
         this.mDrawable = this.mContext.getResources().getDrawable(var2, (Theme)null);
      } else {
         this.mDrawable = this.mContext.getResources().getDrawable(var2);
      }

   }

   public void draw(Canvas var1, float var2, float var3) {
      if (this.mDrawable != null) {
         MPPointF var4 = this.getOffsetForDrawingAtPoint(var2, var3);
         float var5 = this.mSize.width;
         float var6 = this.mSize.height;
         float var7 = var5;
         if (var5 == 0.0F) {
            var7 = (float)this.mDrawable.getIntrinsicWidth();
         }

         var5 = var6;
         if (var6 == 0.0F) {
            var5 = (float)this.mDrawable.getIntrinsicHeight();
         }

         this.mDrawable.copyBounds(this.mDrawableBoundsCache);
         this.mDrawable.setBounds(this.mDrawableBoundsCache.left, this.mDrawableBoundsCache.top, this.mDrawableBoundsCache.left + (int)var7, this.mDrawableBoundsCache.top + (int)var5);
         int var8 = var1.save();
         var1.translate(var2 + var4.x, var3 + var4.y);
         this.mDrawable.draw(var1);
         var1.restoreToCount(var8);
         this.mDrawable.setBounds(this.mDrawableBoundsCache);
      }
   }

   public Chart getChartView() {
      WeakReference var1 = this.mWeakChart;
      Chart var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = (Chart)var1.get();
      }

      return var2;
   }

   public MPPointF getOffset() {
      return this.mOffset;
   }

   public MPPointF getOffsetForDrawingAtPoint(float var1, float var2) {
      MPPointF var3 = this.getOffset();
      this.mOffset2.x = var3.x;
      this.mOffset2.y = var3.y;
      Chart var8 = this.getChartView();
      float var4 = this.mSize.width;
      float var5 = this.mSize.height;
      float var6 = var4;
      Drawable var7;
      if (var4 == 0.0F) {
         var7 = this.mDrawable;
         var6 = var4;
         if (var7 != null) {
            var6 = (float)var7.getIntrinsicWidth();
         }
      }

      var4 = var5;
      if (var5 == 0.0F) {
         var7 = this.mDrawable;
         var4 = var5;
         if (var7 != null) {
            var4 = (float)var7.getIntrinsicHeight();
         }
      }

      if (this.mOffset2.x + var1 < 0.0F) {
         this.mOffset2.x = -var1;
      } else if (var8 != null && var1 + var6 + this.mOffset2.x > (float)var8.getWidth()) {
         this.mOffset2.x = (float)var8.getWidth() - var1 - var6;
      }

      if (this.mOffset2.y + var2 < 0.0F) {
         this.mOffset2.y = -var2;
      } else if (var8 != null && var2 + var4 + this.mOffset2.y > (float)var8.getHeight()) {
         this.mOffset2.y = (float)var8.getHeight() - var2 - var4;
      }

      return this.mOffset2;
   }

   public FSize getSize() {
      return this.mSize;
   }

   public void refreshContent(Entry var1, Highlight var2) {
   }

   public void setChartView(Chart var1) {
      this.mWeakChart = new WeakReference(var1);
   }

   public void setOffset(float var1, float var2) {
      this.mOffset.x = var1;
      this.mOffset.y = var2;
   }

   public void setOffset(MPPointF var1) {
      this.mOffset = var1;
      if (var1 == null) {
         this.mOffset = new MPPointF();
      }

   }

   public void setSize(FSize var1) {
      this.mSize = var1;
      if (var1 == null) {
         this.mSize = new FSize();
      }

   }
}
