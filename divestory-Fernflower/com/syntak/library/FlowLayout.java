package com.syntak.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;

public class FlowLayout extends ViewGroup {
   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private int line_height;

   public FlowLayout(Context var1) {
      super(var1);
   }

   public FlowLayout(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return var1 instanceof FlowLayout.LayoutParams;
   }

   protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
      return new FlowLayout.LayoutParams(1, 1);
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      int var6 = this.getChildCount();
      var5 = this.getPaddingLeft();
      var3 = this.getPaddingTop();

      int var10;
      for(int var7 = 0; var7 < var6; var3 = var10) {
         View var8 = this.getChildAt(var7);
         int var9 = var5;
         var10 = var3;
         if (var8.getVisibility() != 8) {
            int var11 = var8.getMeasuredWidth();
            int var12 = var8.getMeasuredHeight();
            FlowLayout.LayoutParams var13 = (FlowLayout.LayoutParams)var8.getLayoutParams();
            var9 = var5;
            var10 = var3;
            if (var5 + var11 > var4 - var2) {
               var9 = this.getPaddingLeft();
               var10 = var3 + this.line_height;
            }

            var8.layout(var9, var10, var9 + var11, var12 + var10);
            var9 += var11 + var13.horizontal_spacing;
         }

         ++var7;
         var5 = var9;
      }

   }

   protected void onMeasure(int var1, int var2) {
      int var3 = MeasureSpec.getSize(var1) - this.getPaddingLeft() - this.getPaddingRight();
      int var4 = MeasureSpec.getSize(var2) - this.getPaddingTop() - this.getPaddingBottom();
      int var5 = this.getChildCount();
      int var6 = this.getPaddingLeft();
      var1 = this.getPaddingTop();
      int var7 = MeasureSpec.getMode(var2);
      int var8 = 0;
      int var9;
      if (var7 == Integer.MIN_VALUE) {
         var9 = MeasureSpec.makeMeasureSpec(var4, Integer.MIN_VALUE);
      } else {
         var9 = MeasureSpec.makeMeasureSpec(0, 0);
      }

      int var13;
      for(var7 = 0; var8 < var5; var7 = var13) {
         View var10 = this.getChildAt(var8);
         int var11 = var6;
         int var12 = var1;
         var13 = var7;
         if (var10.getVisibility() != 8) {
            FlowLayout.LayoutParams var14 = (FlowLayout.LayoutParams)var10.getLayoutParams();
            var10.measure(MeasureSpec.makeMeasureSpec(var3, Integer.MIN_VALUE), var9);
            var11 = var10.getMeasuredWidth();
            var12 = Math.max(var7, var10.getMeasuredHeight() + var14.vertical_spacing);
            var13 = var6;
            var7 = var1;
            if (var6 + var11 > var3) {
               var13 = this.getPaddingLeft();
               var7 = var1 + var12;
            }

            var11 = var13 + var11 + var14.horizontal_spacing;
            var13 = var12;
            var12 = var7;
         }

         ++var8;
         var6 = var11;
         var1 = var12;
      }

      this.line_height = var7;
      if (MeasureSpec.getMode(var2) == 0) {
         var6 = var1 + var7;
      } else {
         var6 = var4;
         if (MeasureSpec.getMode(var2) == Integer.MIN_VALUE) {
            var1 += var7;
            var6 = var4;
            if (var1 < var4) {
               var6 = var1;
            }
         }
      }

      this.setMeasuredDimension(var3, var6);
   }

   public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
      public final int horizontal_spacing;
      public final int vertical_spacing;

      public LayoutParams(int var1, int var2) {
         super(0, 0);
         this.horizontal_spacing = var1;
         this.vertical_spacing = var2;
      }
   }
}
