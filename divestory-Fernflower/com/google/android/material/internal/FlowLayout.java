package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;

public class FlowLayout extends ViewGroup {
   private int itemSpacing;
   private int lineSpacing;
   private int rowCount;
   private boolean singleLine;

   public FlowLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public FlowLayout(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public FlowLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.singleLine = false;
      this.loadFromAttributes(var1, var2);
   }

   public FlowLayout(Context var1, AttributeSet var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.singleLine = false;
      this.loadFromAttributes(var1, var2);
   }

   private static int getMeasuredDimension(int var0, int var1, int var2) {
      if (var1 != Integer.MIN_VALUE) {
         return var1 != 1073741824 ? var2 : var0;
      } else {
         return Math.min(var2, var0);
      }
   }

   private void loadFromAttributes(Context var1, AttributeSet var2) {
      TypedArray var3 = var1.getTheme().obtainStyledAttributes(var2, R.styleable.FlowLayout, 0, 0);
      this.lineSpacing = var3.getDimensionPixelSize(R.styleable.FlowLayout_lineSpacing, 0);
      this.itemSpacing = var3.getDimensionPixelSize(R.styleable.FlowLayout_itemSpacing, 0);
      var3.recycle();
   }

   protected int getItemSpacing() {
      return this.itemSpacing;
   }

   protected int getLineSpacing() {
      return this.lineSpacing;
   }

   protected int getRowCount() {
      return this.rowCount;
   }

   public int getRowIndex(View var1) {
      Object var2 = var1.getTag(R.id.row_index_key);
      return !(var2 instanceof Integer) ? -1 : (Integer)var2;
   }

   public boolean isSingleLine() {
      return this.singleLine;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      if (this.getChildCount() == 0) {
         this.rowCount = 0;
      } else {
         this.rowCount = 1;
         boolean var6;
         if (ViewCompat.getLayoutDirection(this) == 1) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var6) {
            var3 = this.getPaddingRight();
         } else {
            var3 = this.getPaddingLeft();
         }

         if (var6) {
            var5 = this.getPaddingLeft();
         } else {
            var5 = this.getPaddingRight();
         }

         int var7 = this.getPaddingTop();
         int var8 = var4 - var2 - var5;
         var4 = var3;
         var2 = var7;

         for(int var9 = 0; var9 < this.getChildCount(); ++var9) {
            View var10 = this.getChildAt(var9);
            if (var10.getVisibility() == 8) {
               var10.setTag(R.id.row_index_key, -1);
            } else {
               LayoutParams var11 = var10.getLayoutParams();
               int var12;
               int var13;
               if (var11 instanceof MarginLayoutParams) {
                  MarginLayoutParams var16 = (MarginLayoutParams)var11;
                  var12 = MarginLayoutParamsCompat.getMarginStart(var16);
                  var13 = MarginLayoutParamsCompat.getMarginEnd(var16);
               } else {
                  var13 = 0;
                  var12 = 0;
               }

               int var14 = var10.getMeasuredWidth();
               int var15 = var4;
               var5 = var2;
               if (!this.singleLine) {
                  var15 = var4;
                  var5 = var2;
                  if (var4 + var12 + var14 > var8) {
                     var5 = this.lineSpacing + var7;
                     ++this.rowCount;
                     var15 = var3;
                  }
               }

               var10.setTag(R.id.row_index_key, this.rowCount - 1);
               var2 = var15 + var12;
               var4 = var10.getMeasuredWidth() + var2;
               var7 = var10.getMeasuredHeight() + var5;
               if (var6) {
                  var10.layout(var8 - var4, var5, var8 - var15 - var12, var7);
               } else {
                  var10.layout(var2, var5, var4, var7);
               }

               var4 = var15 + var12 + var13 + var10.getMeasuredWidth() + this.itemSpacing;
               var2 = var5;
            }
         }

      }
   }

   protected void onMeasure(int var1, int var2) {
      int var3 = MeasureSpec.getSize(var1);
      int var4 = MeasureSpec.getMode(var1);
      int var5 = MeasureSpec.getSize(var2);
      int var6 = MeasureSpec.getMode(var2);
      int var7;
      if (var4 != Integer.MIN_VALUE && var4 != 1073741824) {
         var7 = Integer.MAX_VALUE;
      } else {
         var7 = var3;
      }

      int var8 = this.getPaddingLeft();
      int var9 = this.getPaddingTop();
      int var10 = this.getPaddingRight();
      int var11 = var9;
      int var12 = 0;

      int var13;
      for(var13 = 0; var12 < this.getChildCount(); ++var12) {
         View var14 = this.getChildAt(var12);
         if (var14.getVisibility() != 8) {
            this.measureChild(var14, var1, var2);
            LayoutParams var15 = var14.getLayoutParams();
            int var16;
            int var17;
            if (var15 instanceof MarginLayoutParams) {
               MarginLayoutParams var20 = (MarginLayoutParams)var15;
               var16 = var20.leftMargin + 0;
               var17 = var20.rightMargin + 0;
            } else {
               var16 = 0;
               var17 = 0;
            }

            if (var8 + var16 + var14.getMeasuredWidth() > var7 - var10 && !this.isSingleLine()) {
               var11 = this.getPaddingLeft();
               var8 = this.lineSpacing + var9;
               var9 = var11;
               var11 = var8;
            } else {
               var9 = var8;
            }

            int var18 = var9 + var16 + var14.getMeasuredWidth();
            int var19 = var14.getMeasuredHeight();
            var8 = var13;
            if (var18 > var13) {
               var8 = var18;
            }

            var16 = var9 + var16 + var17 + var14.getMeasuredWidth() + this.itemSpacing;
            var13 = var8;
            if (var12 == this.getChildCount() - 1) {
               var13 = var8 + var17;
            }

            var9 = var11 + var19;
            var8 = var16;
         }
      }

      var2 = this.getPaddingRight();
      var1 = this.getPaddingBottom();
      this.setMeasuredDimension(getMeasuredDimension(var3, var4, var13 + var2), getMeasuredDimension(var5, var6, var9 + var1));
   }

   protected void setItemSpacing(int var1) {
      this.itemSpacing = var1;
   }

   protected void setLineSpacing(int var1) {
      this.lineSpacing = var1;
   }

   public void setSingleLine(boolean var1) {
      this.singleLine = var1;
   }
}
