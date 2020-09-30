package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.R;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat extends ViewGroup {
   private static final String ACCESSIBILITY_CLASS_NAME = "androidx.appcompat.widget.LinearLayoutCompat";
   public static final int HORIZONTAL = 0;
   private static final int INDEX_BOTTOM = 2;
   private static final int INDEX_CENTER_VERTICAL = 0;
   private static final int INDEX_FILL = 3;
   private static final int INDEX_TOP = 1;
   public static final int SHOW_DIVIDER_BEGINNING = 1;
   public static final int SHOW_DIVIDER_END = 4;
   public static final int SHOW_DIVIDER_MIDDLE = 2;
   public static final int SHOW_DIVIDER_NONE = 0;
   public static final int VERTICAL = 1;
   private static final int VERTICAL_GRAVITY_COUNT = 4;
   private boolean mBaselineAligned;
   private int mBaselineAlignedChildIndex;
   private int mBaselineChildTop;
   private Drawable mDivider;
   private int mDividerHeight;
   private int mDividerPadding;
   private int mDividerWidth;
   private int mGravity;
   private int[] mMaxAscent;
   private int[] mMaxDescent;
   private int mOrientation;
   private int mShowDividers;
   private int mTotalLength;
   private boolean mUseLargestChild;
   private float mWeightSum;

   public LinearLayoutCompat(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public LinearLayoutCompat(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public LinearLayoutCompat(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mBaselineAligned = true;
      this.mBaselineAlignedChildIndex = -1;
      this.mBaselineChildTop = 0;
      this.mGravity = 8388659;
      TintTypedArray var4 = TintTypedArray.obtainStyledAttributes(var1, var2, R.styleable.LinearLayoutCompat, var3, 0);
      ViewCompat.saveAttributeDataForStyleable(this, var1, R.styleable.LinearLayoutCompat, var2, var4.getWrappedTypeArray(), var3, 0);
      var3 = var4.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
      if (var3 >= 0) {
         this.setOrientation(var3);
      }

      var3 = var4.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
      if (var3 >= 0) {
         this.setGravity(var3);
      }

      boolean var5 = var4.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
      if (!var5) {
         this.setBaselineAligned(var5);
      }

      this.mWeightSum = var4.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0F);
      this.mBaselineAlignedChildIndex = var4.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
      this.mUseLargestChild = var4.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
      this.setDividerDrawable(var4.getDrawable(R.styleable.LinearLayoutCompat_divider));
      this.mShowDividers = var4.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
      this.mDividerPadding = var4.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
      var4.recycle();
   }

   private void forceUniformHeight(int var1, int var2) {
      int var3 = MeasureSpec.makeMeasureSpec(this.getMeasuredHeight(), 1073741824);

      for(int var4 = 0; var4 < var1; ++var4) {
         View var5 = this.getVirtualChildAt(var4);
         if (var5.getVisibility() != 8) {
            LinearLayoutCompat.LayoutParams var6 = (LinearLayoutCompat.LayoutParams)var5.getLayoutParams();
            if (var6.height == -1) {
               int var7 = var6.width;
               var6.width = var5.getMeasuredWidth();
               this.measureChildWithMargins(var5, var2, 0, var3, 0);
               var6.width = var7;
            }
         }
      }

   }

   private void forceUniformWidth(int var1, int var2) {
      int var3 = MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), 1073741824);

      for(int var4 = 0; var4 < var1; ++var4) {
         View var5 = this.getVirtualChildAt(var4);
         if (var5.getVisibility() != 8) {
            LinearLayoutCompat.LayoutParams var6 = (LinearLayoutCompat.LayoutParams)var5.getLayoutParams();
            if (var6.width == -1) {
               int var7 = var6.height;
               var6.height = var5.getMeasuredHeight();
               this.measureChildWithMargins(var5, var3, 0, var2, 0);
               var6.height = var7;
            }
         }
      }

   }

   private void setChildFrame(View var1, int var2, int var3, int var4, int var5) {
      var1.layout(var2, var3, var4 + var2, var5 + var3);
   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return var1 instanceof LinearLayoutCompat.LayoutParams;
   }

   void drawDividersHorizontal(Canvas var1) {
      int var2 = this.getVirtualChildCount();
      boolean var3 = ViewUtils.isLayoutRtl(this);

      int var4;
      View var5;
      LinearLayoutCompat.LayoutParams var6;
      int var7;
      for(var4 = 0; var4 < var2; ++var4) {
         var5 = this.getVirtualChildAt(var4);
         if (var5 != null && var5.getVisibility() != 8 && this.hasDividerBeforeChildAt(var4)) {
            var6 = (LinearLayoutCompat.LayoutParams)var5.getLayoutParams();
            if (var3) {
               var7 = var5.getRight() + var6.rightMargin;
            } else {
               var7 = var5.getLeft() - var6.leftMargin - this.mDividerWidth;
            }

            this.drawVerticalDivider(var1, var7);
         }
      }

      if (this.hasDividerBeforeChildAt(var2)) {
         label37: {
            var5 = this.getVirtualChildAt(var2 - 1);
            if (var5 == null) {
               if (var3) {
                  var4 = this.getPaddingLeft();
                  break label37;
               }

               var4 = this.getWidth() - this.getPaddingRight();
               var7 = this.mDividerWidth;
            } else {
               var6 = (LinearLayoutCompat.LayoutParams)var5.getLayoutParams();
               if (!var3) {
                  var4 = var5.getRight() + var6.rightMargin;
                  break label37;
               }

               var4 = var5.getLeft() - var6.leftMargin;
               var7 = this.mDividerWidth;
            }

            var4 -= var7;
         }

         this.drawVerticalDivider(var1, var4);
      }

   }

   void drawDividersVertical(Canvas var1) {
      int var2 = this.getVirtualChildCount();

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         View var4 = this.getVirtualChildAt(var3);
         if (var4 != null && var4.getVisibility() != 8 && this.hasDividerBeforeChildAt(var3)) {
            LinearLayoutCompat.LayoutParams var5 = (LinearLayoutCompat.LayoutParams)var4.getLayoutParams();
            this.drawHorizontalDivider(var1, var4.getTop() - var5.topMargin - this.mDividerHeight);
         }
      }

      if (this.hasDividerBeforeChildAt(var2)) {
         View var7 = this.getVirtualChildAt(var2 - 1);
         if (var7 == null) {
            var3 = this.getHeight() - this.getPaddingBottom() - this.mDividerHeight;
         } else {
            LinearLayoutCompat.LayoutParams var6 = (LinearLayoutCompat.LayoutParams)var7.getLayoutParams();
            var3 = var7.getBottom() + var6.bottomMargin;
         }

         this.drawHorizontalDivider(var1, var3);
      }

   }

   void drawHorizontalDivider(Canvas var1, int var2) {
      this.mDivider.setBounds(this.getPaddingLeft() + this.mDividerPadding, var2, this.getWidth() - this.getPaddingRight() - this.mDividerPadding, this.mDividerHeight + var2);
      this.mDivider.draw(var1);
   }

   void drawVerticalDivider(Canvas var1, int var2) {
      this.mDivider.setBounds(var2, this.getPaddingTop() + this.mDividerPadding, this.mDividerWidth + var2, this.getHeight() - this.getPaddingBottom() - this.mDividerPadding);
      this.mDivider.draw(var1);
   }

   protected LinearLayoutCompat.LayoutParams generateDefaultLayoutParams() {
      int var1 = this.mOrientation;
      if (var1 == 0) {
         return new LinearLayoutCompat.LayoutParams(-2, -2);
      } else {
         return var1 == 1 ? new LinearLayoutCompat.LayoutParams(-1, -2) : null;
      }
   }

   public LinearLayoutCompat.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new LinearLayoutCompat.LayoutParams(this.getContext(), var1);
   }

   protected LinearLayoutCompat.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return new LinearLayoutCompat.LayoutParams(var1);
   }

   public int getBaseline() {
      if (this.mBaselineAlignedChildIndex < 0) {
         return super.getBaseline();
      } else {
         int var1 = this.getChildCount();
         int var2 = this.mBaselineAlignedChildIndex;
         if (var1 > var2) {
            View var3 = this.getChildAt(var2);
            int var4 = var3.getBaseline();
            if (var4 == -1) {
               if (this.mBaselineAlignedChildIndex == 0) {
                  return -1;
               } else {
                  throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
               }
            } else {
               var2 = this.mBaselineChildTop;
               var1 = var2;
               if (this.mOrientation == 1) {
                  int var5 = this.mGravity & 112;
                  var1 = var2;
                  if (var5 != 48) {
                     if (var5 != 16) {
                        if (var5 != 80) {
                           var1 = var2;
                        } else {
                           var1 = this.getBottom() - this.getTop() - this.getPaddingBottom() - this.mTotalLength;
                        }
                     } else {
                        var1 = var2 + (this.getBottom() - this.getTop() - this.getPaddingTop() - this.getPaddingBottom() - this.mTotalLength) / 2;
                     }
                  }
               }

               return var1 + ((LinearLayoutCompat.LayoutParams)var3.getLayoutParams()).topMargin + var4;
            }
         } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
         }
      }
   }

   public int getBaselineAlignedChildIndex() {
      return this.mBaselineAlignedChildIndex;
   }

   int getChildrenSkipCount(View var1, int var2) {
      return 0;
   }

   public Drawable getDividerDrawable() {
      return this.mDivider;
   }

   public int getDividerPadding() {
      return this.mDividerPadding;
   }

   public int getDividerWidth() {
      return this.mDividerWidth;
   }

   public int getGravity() {
      return this.mGravity;
   }

   int getLocationOffset(View var1) {
      return 0;
   }

   int getNextLocationOffset(View var1) {
      return 0;
   }

   public int getOrientation() {
      return this.mOrientation;
   }

   public int getShowDividers() {
      return this.mShowDividers;
   }

   View getVirtualChildAt(int var1) {
      return this.getChildAt(var1);
   }

   int getVirtualChildCount() {
      return this.getChildCount();
   }

   public float getWeightSum() {
      return this.mWeightSum;
   }

   protected boolean hasDividerBeforeChildAt(int var1) {
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      if (var1 == 0) {
         if ((this.mShowDividers & 1) != 0) {
            var4 = true;
         }

         return var4;
      } else if (var1 == this.getChildCount()) {
         var4 = var2;
         if ((this.mShowDividers & 4) != 0) {
            var4 = true;
         }

         return var4;
      } else {
         var4 = var3;
         if ((this.mShowDividers & 2) != 0) {
            --var1;

            while(true) {
               var4 = var3;
               if (var1 < 0) {
                  break;
               }

               if (this.getChildAt(var1).getVisibility() != 8) {
                  var4 = true;
                  break;
               }

               --var1;
            }
         }

         return var4;
      }
   }

   public boolean isBaselineAligned() {
      return this.mBaselineAligned;
   }

   public boolean isMeasureWithLargestChildEnabled() {
      return this.mUseLargestChild;
   }

   void layoutHorizontal(int var1, int var2, int var3, int var4) {
      boolean var5 = ViewUtils.isLayoutRtl(this);
      int var6 = this.getPaddingTop();
      int var7 = var4 - var2;
      int var8 = this.getPaddingBottom();
      int var9 = this.getPaddingBottom();
      int var10 = this.getVirtualChildCount();
      var2 = this.mGravity;
      var4 = var2 & 112;
      boolean var11 = this.mBaselineAligned;
      int[] var12 = this.mMaxAscent;
      int[] var13 = this.mMaxDescent;
      var2 = GravityCompat.getAbsoluteGravity(8388615 & var2, ViewCompat.getLayoutDirection(this));
      if (var2 != 1) {
         if (var2 != 5) {
            var2 = this.getPaddingLeft();
         } else {
            var2 = this.getPaddingLeft() + var3 - var1 - this.mTotalLength;
         }
      } else {
         var2 = this.getPaddingLeft() + (var3 - var1 - this.mTotalLength) / 2;
      }

      int var14;
      byte var15;
      if (var5) {
         var14 = var10 - 1;
         var15 = -1;
      } else {
         var14 = 0;
         var15 = 1;
      }

      int var16 = 0;
      var3 = var4;

      for(var4 = var6; var16 < var10; ++var16) {
         int var17 = var14 + var15 * var16;
         View var18 = this.getVirtualChildAt(var17);
         if (var18 == null) {
            var2 += this.measureNullChild(var17);
         } else if (var18.getVisibility() != 8) {
            int var19 = var18.getMeasuredWidth();
            int var20 = var18.getMeasuredHeight();
            LinearLayoutCompat.LayoutParams var21 = (LinearLayoutCompat.LayoutParams)var18.getLayoutParams();
            int var22;
            if (var11 && var21.height != -1) {
               var22 = var18.getBaseline();
            } else {
               var22 = -1;
            }

            int var23 = var21.gravity;
            var1 = var23;
            if (var23 < 0) {
               var1 = var3;
            }

            var1 &= 112;
            if (var1 != 16) {
               if (var1 != 48) {
                  if (var1 != 80) {
                     var1 = var4;
                  } else {
                     var23 = var7 - var8 - var20 - var21.bottomMargin;
                     var1 = var23;
                     if (var22 != -1) {
                        var1 = var18.getMeasuredHeight();
                        var1 = var23 - (var13[2] - (var1 - var22));
                     }
                  }
               } else {
                  var23 = var21.topMargin + var4;
                  var1 = var23;
                  if (var22 != -1) {
                     var1 = var23 + (var12[1] - var22);
                  }
               }
            } else {
               var1 = (var7 - var6 - var9 - var20) / 2 + var4 + var21.topMargin - var21.bottomMargin;
            }

            var22 = var2;
            if (this.hasDividerBeforeChildAt(var17)) {
               var22 = var2 + this.mDividerWidth;
            }

            var2 = var21.leftMargin + var22;
            this.setChildFrame(var18, var2 + this.getLocationOffset(var18), var1, var19, var20);
            var22 = var21.rightMargin;
            var1 = this.getNextLocationOffset(var18);
            var16 += this.getChildrenSkipCount(var18, var17);
            var2 += var19 + var22 + var1;
         }
      }

   }

   void layoutVertical(int var1, int var2, int var3, int var4) {
      int var5 = this.getPaddingLeft();
      int var6 = var3 - var1;
      int var7 = this.getPaddingRight();
      int var8 = this.getPaddingRight();
      int var9 = this.getVirtualChildCount();
      int var10 = this.mGravity;
      var1 = var10 & 112;
      if (var1 != 16) {
         if (var1 != 80) {
            var1 = this.getPaddingTop();
         } else {
            var1 = this.getPaddingTop() + var4 - var2 - this.mTotalLength;
         }
      } else {
         var1 = this.getPaddingTop() + (var4 - var2 - this.mTotalLength) / 2;
      }

      for(var2 = 0; var2 < var9; var1 = var3) {
         View var11 = this.getVirtualChildAt(var2);
         if (var11 == null) {
            var3 = var1 + this.measureNullChild(var2);
            var4 = var2;
         } else {
            var3 = var1;
            var4 = var2;
            if (var11.getVisibility() != 8) {
               int var12 = var11.getMeasuredWidth();
               int var13 = var11.getMeasuredHeight();
               LinearLayoutCompat.LayoutParams var14 = (LinearLayoutCompat.LayoutParams)var11.getLayoutParams();
               var4 = var14.gravity;
               var3 = var4;
               if (var4 < 0) {
                  var3 = var10 & 8388615;
               }

               label41: {
                  var3 = GravityCompat.getAbsoluteGravity(var3, ViewCompat.getLayoutDirection(this)) & 7;
                  if (var3 != 1) {
                     if (var3 != 5) {
                        var3 = var14.leftMargin + var5;
                        break label41;
                     }

                     var4 = var6 - var7 - var12;
                     var3 = var14.rightMargin;
                  } else {
                     var4 = (var6 - var5 - var8 - var12) / 2 + var5 + var14.leftMargin;
                     var3 = var14.rightMargin;
                  }

                  var3 = var4 - var3;
               }

               var4 = var1;
               if (this.hasDividerBeforeChildAt(var2)) {
                  var4 = var1 + this.mDividerHeight;
               }

               var1 = var4 + var14.topMargin;
               this.setChildFrame(var11, var3, var1 + this.getLocationOffset(var11), var12, var13);
               var12 = var14.bottomMargin;
               var3 = this.getNextLocationOffset(var11);
               var4 = var2 + this.getChildrenSkipCount(var11, var2);
               var3 = var1 + var13 + var12 + var3;
            }
         }

         var2 = var4 + 1;
      }

   }

   void measureChildBeforeLayout(View var1, int var2, int var3, int var4, int var5, int var6) {
      this.measureChildWithMargins(var1, var3, var4, var5, var6);
   }

   void measureHorizontal(int var1, int var2) {
      this.mTotalLength = 0;
      int var3 = this.getVirtualChildCount();
      int var4 = MeasureSpec.getMode(var1);
      int var5 = MeasureSpec.getMode(var2);
      if (this.mMaxAscent == null || this.mMaxDescent == null) {
         this.mMaxAscent = new int[4];
         this.mMaxDescent = new int[4];
      }

      int[] var6 = this.mMaxAscent;
      int[] var7 = this.mMaxDescent;
      var6[3] = -1;
      var6[2] = -1;
      var6[1] = -1;
      var6[0] = -1;
      var7[3] = -1;
      var7[2] = -1;
      var7[1] = -1;
      var7[0] = -1;
      boolean var8 = this.mBaselineAligned;
      boolean var9 = this.mUseLargestChild;
      boolean var10;
      if (var4 == 1073741824) {
         var10 = true;
      } else {
         var10 = false;
      }

      float var11 = 0.0F;
      int var12 = 0;
      int var13 = 0;
      int var14 = 0;
      int var15 = 0;
      int var16 = 0;
      boolean var17 = false;
      int var18 = 0;
      boolean var19 = true;

      boolean var20;
      int var23;
      int var24;
      int var25;
      boolean var30;
      for(var20 = false; var12 < var3; ++var12) {
         View var21 = this.getVirtualChildAt(var12);
         if (var21 == null) {
            this.mTotalLength += this.measureNullChild(var12);
         } else if (var21.getVisibility() == 8) {
            var12 += this.getChildrenSkipCount(var21, var12);
         } else {
            if (this.hasDividerBeforeChildAt(var12)) {
               this.mTotalLength += this.mDividerWidth;
            }

            LinearLayoutCompat.LayoutParams var22;
            label362: {
               var22 = (LinearLayoutCompat.LayoutParams)var21.getLayoutParams();
               var11 += var22.weight;
               if (var4 == 1073741824 && var22.width == 0 && var22.weight > 0.0F) {
                  if (var10) {
                     this.mTotalLength += var22.leftMargin + var22.rightMargin;
                  } else {
                     var23 = this.mTotalLength;
                     this.mTotalLength = Math.max(var23, var22.leftMargin + var23 + var22.rightMargin);
                  }

                  if (!var8) {
                     var17 = true;
                     break label362;
                  }

                  var23 = MeasureSpec.makeMeasureSpec(0, 0);
                  var21.measure(var23, var23);
                  var23 = var13;
               } else {
                  if (var22.width == 0 && var22.weight > 0.0F) {
                     var22.width = -2;
                     var23 = 0;
                  } else {
                     var23 = Integer.MIN_VALUE;
                  }

                  if (var11 == 0.0F) {
                     var24 = this.mTotalLength;
                  } else {
                     var24 = 0;
                  }

                  this.measureChildBeforeLayout(var21, var12, var1, var24, var2, 0);
                  if (var23 != Integer.MIN_VALUE) {
                     var22.width = var23;
                  }

                  var24 = var21.getMeasuredWidth();
                  if (var10) {
                     this.mTotalLength += var22.leftMargin + var24 + var22.rightMargin + this.getNextLocationOffset(var21);
                  } else {
                     var23 = this.mTotalLength;
                     this.mTotalLength = Math.max(var23, var23 + var24 + var22.leftMargin + var22.rightMargin + this.getNextLocationOffset(var21));
                  }

                  var23 = var13;
                  if (var9) {
                     var23 = Math.max(var24, var13);
                  }
               }

               var13 = var23;
            }

            var25 = var12;
            if (var5 != 1073741824 && var22.height == -1) {
               var30 = true;
               var20 = true;
            } else {
               var30 = false;
            }

            var23 = var22.topMargin + var22.bottomMargin;
            var24 = var21.getMeasuredHeight() + var23;
            int var26 = View.combineMeasuredStates(var18, var21.getMeasuredState());
            if (var8) {
               int var27 = var21.getBaseline();
               if (var27 != -1) {
                  if (var22.gravity < 0) {
                     var18 = this.mGravity;
                  } else {
                     var18 = var22.gravity;
                  }

                  var18 = ((var18 & 112) >> 4 & -2) >> 1;
                  var6[var18] = Math.max(var6[var18], var27);
                  var7[var18] = Math.max(var7[var18], var24 - var27);
               }
            }

            var14 = Math.max(var14, var24);
            if (var19 && var22.height == -1) {
               var19 = true;
            } else {
               var19 = false;
            }

            if (var22.weight > 0.0F) {
               if (!var30) {
                  var23 = var24;
               }

               var12 = Math.max(var16, var23);
            } else {
               if (!var30) {
                  var23 = var24;
               }

               var15 = Math.max(var15, var23);
               var12 = var16;
            }

            var23 = this.getChildrenSkipCount(var21, var25) + var25;
            var18 = var26;
            var16 = var12;
            var12 = var23;
         }
      }

      if (this.mTotalLength > 0 && this.hasDividerBeforeChildAt(var3)) {
         this.mTotalLength += this.mDividerWidth;
      }

      if (var6[1] == -1 && var6[0] == -1 && var6[2] == -1 && var6[3] == -1) {
         var12 = var14;
      } else {
         var12 = Math.max(var14, Math.max(var6[3], Math.max(var6[0], Math.max(var6[1], var6[2]))) + Math.max(var7[3], Math.max(var7[0], Math.max(var7[1], var7[2]))));
      }

      var14 = var18;
      var23 = var12;
      LinearLayoutCompat.LayoutParams var33;
      View var34;
      if (var9) {
         label336: {
            if (var4 != Integer.MIN_VALUE) {
               var23 = var12;
               if (var4 != 0) {
                  break label336;
               }
            }

            this.mTotalLength = 0;
            var18 = 0;

            while(true) {
               var23 = var12;
               if (var18 >= var3) {
                  break;
               }

               var34 = this.getVirtualChildAt(var18);
               if (var34 == null) {
                  this.mTotalLength += this.measureNullChild(var18);
               } else if (var34.getVisibility() == 8) {
                  var18 += this.getChildrenSkipCount(var34, var18);
               } else {
                  var33 = (LinearLayoutCompat.LayoutParams)var34.getLayoutParams();
                  if (var10) {
                     this.mTotalLength += var33.leftMargin + var13 + var33.rightMargin + this.getNextLocationOffset(var34);
                  } else {
                     var23 = this.mTotalLength;
                     this.mTotalLength = Math.max(var23, var23 + var13 + var33.leftMargin + var33.rightMargin + this.getNextLocationOffset(var34));
                  }
               }

               ++var18;
            }
         }
      }

      var12 = this.mTotalLength + this.getPaddingLeft() + this.getPaddingRight();
      this.mTotalLength = var12;
      var25 = View.resolveSizeAndState(Math.max(var12, this.getSuggestedMinimumWidth()), var1, 0);
      var24 = (16777215 & var25) - this.mTotalLength;
      if (var17 || var24 != 0 && var11 > 0.0F) {
         float var28 = this.mWeightSum;
         if (var28 > 0.0F) {
            var11 = var28;
         }

         var6[3] = -1;
         var6[2] = -1;
         var6[1] = -1;
         var6[0] = -1;
         var7[3] = -1;
         var7[2] = -1;
         var7[1] = -1;
         var7[0] = -1;
         this.mTotalLength = 0;
         var16 = -1;
         var14 = 0;
         var30 = var19;
         var13 = var3;
         int var32 = var18;
         var18 = var15;

         for(var15 = var24; var14 < var13; ++var14) {
            var34 = this.getVirtualChildAt(var14);
            if (var34 != null && var34.getVisibility() != 8) {
               var33 = (LinearLayoutCompat.LayoutParams)var34.getLayoutParams();
               var28 = var33.weight;
               int var31;
               if (var28 > 0.0F) {
                  var23 = (int)((float)var15 * var28 / var11);
                  var24 = getChildMeasureSpec(var2, this.getPaddingTop() + this.getPaddingBottom() + var33.topMargin + var33.bottomMargin, var33.height);
                  if (var33.width == 0 && var4 == 1073741824) {
                     if (var23 > 0) {
                        var31 = var23;
                     } else {
                        var31 = 0;
                     }

                     var34.measure(MeasureSpec.makeMeasureSpec(var31, 1073741824), var24);
                  } else {
                     var3 = var34.getMeasuredWidth() + var23;
                     var31 = var3;
                     if (var3 < 0) {
                        var31 = 0;
                     }

                     var34.measure(MeasureSpec.makeMeasureSpec(var31, 1073741824), var24);
                  }

                  var32 = View.combineMeasuredStates(var32, var34.getMeasuredState() & -16777216);
                  var11 -= var28;
                  var15 -= var23;
               }

               if (var10) {
                  this.mTotalLength += var34.getMeasuredWidth() + var33.leftMargin + var33.rightMargin + this.getNextLocationOffset(var34);
               } else {
                  var31 = this.mTotalLength;
                  this.mTotalLength = Math.max(var31, var34.getMeasuredWidth() + var31 + var33.leftMargin + var33.rightMargin + this.getNextLocationOffset(var34));
               }

               if (var5 != 1073741824 && var33.height == -1) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               var24 = var33.topMargin + var33.bottomMargin;
               var3 = var34.getMeasuredHeight() + var24;
               var23 = Math.max(var16, var3);
               if (var17) {
                  var16 = var24;
               } else {
                  var16 = var3;
               }

               var16 = Math.max(var18, var16);
               if (var30 && var33.height == -1) {
                  var30 = true;
               } else {
                  var30 = false;
               }

               if (var8) {
                  var31 = var34.getBaseline();
                  if (var31 != -1) {
                     if (var33.gravity < 0) {
                        var18 = this.mGravity;
                     } else {
                        var18 = var33.gravity;
                     }

                     var18 = ((var18 & 112) >> 4 & -2) >> 1;
                     var6[var18] = Math.max(var6[var18], var31);
                     var7[var18] = Math.max(var7[var18], var3 - var31);
                  }
               }

               var18 = var16;
               var16 = var23;
            }
         }

         this.mTotalLength += this.getPaddingLeft() + this.getPaddingRight();
         if (var6[1] == -1 && var6[0] == -1 && var6[2] == -1 && var6[3] == -1) {
            var15 = var16;
         } else {
            var15 = Math.max(var16, Math.max(var6[3], Math.max(var6[0], Math.max(var6[1], var6[2]))) + Math.max(var7[3], Math.max(var7[0], Math.max(var7[1], var7[2]))));
         }

         var14 = var32;
         var19 = var30;
         var12 = var13;
         var13 = var15;
         var15 = var18;
      } else {
         var18 = Math.max(var15, var16);
         if (var9 && var4 != 1073741824) {
            for(var15 = 0; var15 < var3; ++var15) {
               View var29 = this.getVirtualChildAt(var15);
               if (var29 != null && var29.getVisibility() != 8 && ((LinearLayoutCompat.LayoutParams)var29.getLayoutParams()).weight > 0.0F) {
                  var29.measure(MeasureSpec.makeMeasureSpec(var13, 1073741824), MeasureSpec.makeMeasureSpec(var29.getMeasuredHeight(), 1073741824));
               }
            }
         }

         var12 = var3;
         var13 = var23;
         var15 = var18;
      }

      if (var19 || var5 == 1073741824) {
         var15 = var13;
      }

      this.setMeasuredDimension(var25 | var14 & -16777216, View.resolveSizeAndState(Math.max(var15 + this.getPaddingTop() + this.getPaddingBottom(), this.getSuggestedMinimumHeight()), var2, var14 << 16));
      if (var20) {
         this.forceUniformHeight(var12, var1);
      }

   }

   int measureNullChild(int var1) {
      return 0;
   }

   void measureVertical(int var1, int var2) {
      this.mTotalLength = 0;
      int var3 = this.getVirtualChildCount();
      int var4 = MeasureSpec.getMode(var1);
      int var5 = MeasureSpec.getMode(var2);
      int var6 = this.mBaselineAlignedChildIndex;
      boolean var7 = this.mUseLargestChild;
      float var8 = 0.0F;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      int var12 = 0;
      int var13 = 0;
      int var14 = 0;
      boolean var15 = false;
      boolean var16 = true;

      boolean var17;
      View var18;
      LinearLayoutCompat.LayoutParams var19;
      int var20;
      int var21;
      int var22;
      int var23;
      int var29;
      for(var17 = false; var14 < var3; ++var14) {
         var18 = this.getVirtualChildAt(var14);
         if (var18 == null) {
            this.mTotalLength += this.measureNullChild(var14);
         } else if (var18.getVisibility() == 8) {
            var14 += this.getChildrenSkipCount(var18, var14);
         } else {
            if (this.hasDividerBeforeChildAt(var14)) {
               this.mTotalLength += this.mDividerHeight;
            }

            var19 = (LinearLayoutCompat.LayoutParams)var18.getLayoutParams();
            var8 += var19.weight;
            if (var5 == 1073741824 && var19.height == 0 && var19.weight > 0.0F) {
               var29 = this.mTotalLength;
               this.mTotalLength = Math.max(var29, var19.topMargin + var29 + var19.bottomMargin);
               var15 = true;
            } else {
               if (var19.height == 0 && var19.weight > 0.0F) {
                  var19.height = -2;
                  var20 = 0;
               } else {
                  var20 = Integer.MIN_VALUE;
               }

               if (var8 == 0.0F) {
                  var21 = this.mTotalLength;
               } else {
                  var21 = 0;
               }

               this.measureChildBeforeLayout(var18, var14, var1, 0, var2, var21);
               if (var20 != Integer.MIN_VALUE) {
                  var19.height = var20;
               }

               var20 = var18.getMeasuredHeight();
               var21 = this.mTotalLength;
               this.mTotalLength = Math.max(var21, var21 + var20 + var19.topMargin + var19.bottomMargin + this.getNextLocationOffset(var18));
               if (var7) {
                  var11 = Math.max(var20, var11);
               }
            }

            var22 = var14;
            if (var6 >= 0 && var6 == var14 + 1) {
               this.mBaselineChildTop = this.mTotalLength;
            }

            if (var14 < var6 && var19.weight > 0.0F) {
               throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
            }

            boolean var28;
            if (var4 != 1073741824 && var19.width == -1) {
               var28 = true;
               var17 = true;
            } else {
               var28 = false;
            }

            var20 = var19.leftMargin + var19.rightMargin;
            var21 = var18.getMeasuredWidth() + var20;
            var10 = Math.max(var10, var21);
            var23 = View.combineMeasuredStates(var9, var18.getMeasuredState());
            boolean var26;
            if (var16 && var19.width == -1) {
               var26 = true;
            } else {
               var26 = false;
            }

            int var30;
            if (var19.weight > 0.0F) {
               if (!var28) {
                  var20 = var21;
               }

               var12 = Math.max(var12, var20);
               var30 = var13;
            } else {
               if (!var28) {
                  var20 = var21;
               }

               var30 = Math.max(var13, var20);
            }

            var14 = this.getChildrenSkipCount(var18, var14);
            var13 = var30;
            var14 += var22;
            var16 = var26;
            var9 = var23;
         }
      }

      if (this.mTotalLength > 0 && this.hasDividerBeforeChildAt(var3)) {
         this.mTotalLength += this.mDividerHeight;
      }

      if (var7 && (var5 == Integer.MIN_VALUE || var5 == 0)) {
         this.mTotalLength = 0;

         for(var14 = 0; var14 < var3; ++var14) {
            var18 = this.getVirtualChildAt(var14);
            if (var18 == null) {
               this.mTotalLength += this.measureNullChild(var14);
            } else if (var18.getVisibility() == 8) {
               var14 += this.getChildrenSkipCount(var18, var14);
            } else {
               var19 = (LinearLayoutCompat.LayoutParams)var18.getLayoutParams();
               var20 = this.mTotalLength;
               this.mTotalLength = Math.max(var20, var20 + var11 + var19.topMargin + var19.bottomMargin + this.getNextLocationOffset(var18));
            }
         }
      }

      var14 = this.mTotalLength + this.getPaddingTop() + this.getPaddingBottom();
      this.mTotalLength = var14;
      var21 = View.resolveSizeAndState(Math.max(var14, this.getSuggestedMinimumHeight()), var2, 0);
      var14 = (16777215 & var21) - this.mTotalLength;
      if (var15 || var14 != 0 && var8 > 0.0F) {
         float var24 = this.mWeightSum;
         if (var24 > 0.0F) {
            var8 = var24;
         }

         this.mTotalLength = 0;
         var12 = var14;
         var14 = 0;

         for(var11 = var10; var14 < var3; ++var14) {
            View var32 = this.getVirtualChildAt(var14);
            if (var32.getVisibility() != 8) {
               LinearLayoutCompat.LayoutParams var31 = (LinearLayoutCompat.LayoutParams)var32.getLayoutParams();
               var24 = var31.weight;
               if (var24 > 0.0F) {
                  var10 = (int)((float)var12 * var24 / var8);
                  int var25 = this.getPaddingLeft();
                  var22 = this.getPaddingRight();
                  var23 = var31.leftMargin;
                  var20 = var31.rightMargin;
                  var6 = var31.width;
                  var29 = var12 - var10;
                  var20 = getChildMeasureSpec(var1, var25 + var22 + var23 + var20, var6);
                  if (var31.height == 0 && var5 == 1073741824) {
                     if (var10 > 0) {
                        var12 = var10;
                     } else {
                        var12 = 0;
                     }

                     var32.measure(var20, MeasureSpec.makeMeasureSpec(var12, 1073741824));
                  } else {
                     var10 += var32.getMeasuredHeight();
                     var12 = var10;
                     if (var10 < 0) {
                        var12 = 0;
                     }

                     var32.measure(var20, MeasureSpec.makeMeasureSpec(var12, 1073741824));
                  }

                  var9 = View.combineMeasuredStates(var9, var32.getMeasuredState() & -256);
                  var8 -= var24;
                  var12 = var29;
               }

               var20 = var31.leftMargin + var31.rightMargin;
               var10 = var32.getMeasuredWidth() + var20;
               var29 = Math.max(var11, var10);
               boolean var27;
               if (var4 != 1073741824 && var31.width == -1) {
                  var27 = true;
               } else {
                  var27 = false;
               }

               if (var27) {
                  var11 = var20;
               } else {
                  var11 = var10;
               }

               var13 = Math.max(var13, var11);
               if (var16 && var31.width == -1) {
                  var16 = true;
               } else {
                  var16 = false;
               }

               var11 = this.mTotalLength;
               this.mTotalLength = Math.max(var11, var32.getMeasuredHeight() + var11 + var31.topMargin + var31.bottomMargin + this.getNextLocationOffset(var32));
               var11 = var29;
            }
         }

         this.mTotalLength += this.getPaddingTop() + this.getPaddingBottom();
         var12 = var9;
         var9 = var13;
      } else {
         var13 = Math.max(var13, var12);
         if (var7 && var5 != 1073741824) {
            for(var12 = 0; var12 < var3; ++var12) {
               var18 = this.getVirtualChildAt(var12);
               if (var18 != null && var18.getVisibility() != 8 && ((LinearLayoutCompat.LayoutParams)var18.getLayoutParams()).weight > 0.0F) {
                  var18.measure(MeasureSpec.makeMeasureSpec(var18.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(var11, 1073741824));
               }
            }
         }

         var12 = var9;
         var9 = var13;
         var11 = var10;
      }

      if (var16 || var4 == 1073741824) {
         var9 = var11;
      }

      this.setMeasuredDimension(View.resolveSizeAndState(Math.max(var9 + this.getPaddingLeft() + this.getPaddingRight(), this.getSuggestedMinimumWidth()), var1, var12), var21);
      if (var17) {
         this.forceUniformWidth(var3, var2);
      }

   }

   protected void onDraw(Canvas var1) {
      if (this.mDivider != null) {
         if (this.mOrientation == 1) {
            this.drawDividersVertical(var1);
         } else {
            this.drawDividersHorizontal(var1);
         }

      }
   }

   public void onInitializeAccessibilityEvent(AccessibilityEvent var1) {
      super.onInitializeAccessibilityEvent(var1);
      var1.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
   }

   public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1) {
      super.onInitializeAccessibilityNodeInfo(var1);
      var1.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      if (this.mOrientation == 1) {
         this.layoutVertical(var2, var3, var4, var5);
      } else {
         this.layoutHorizontal(var2, var3, var4, var5);
      }

   }

   protected void onMeasure(int var1, int var2) {
      if (this.mOrientation == 1) {
         this.measureVertical(var1, var2);
      } else {
         this.measureHorizontal(var1, var2);
      }

   }

   public void setBaselineAligned(boolean var1) {
      this.mBaselineAligned = var1;
   }

   public void setBaselineAlignedChildIndex(int var1) {
      if (var1 >= 0 && var1 < this.getChildCount()) {
         this.mBaselineAlignedChildIndex = var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("base aligned child index out of range (0, ");
         var2.append(this.getChildCount());
         var2.append(")");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void setDividerDrawable(Drawable var1) {
      if (var1 != this.mDivider) {
         this.mDivider = var1;
         boolean var2 = false;
         if (var1 != null) {
            this.mDividerWidth = var1.getIntrinsicWidth();
            this.mDividerHeight = var1.getIntrinsicHeight();
         } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
         }

         if (var1 == null) {
            var2 = true;
         }

         this.setWillNotDraw(var2);
         this.requestLayout();
      }
   }

   public void setDividerPadding(int var1) {
      this.mDividerPadding = var1;
   }

   public void setGravity(int var1) {
      if (this.mGravity != var1) {
         int var2 = var1;
         if ((8388615 & var1) == 0) {
            var2 = var1 | 8388611;
         }

         var1 = var2;
         if ((var2 & 112) == 0) {
            var1 = var2 | 48;
         }

         this.mGravity = var1;
         this.requestLayout();
      }

   }

   public void setHorizontalGravity(int var1) {
      var1 &= 8388615;
      int var2 = this.mGravity;
      if ((8388615 & var2) != var1) {
         this.mGravity = var1 | -8388616 & var2;
         this.requestLayout();
      }

   }

   public void setMeasureWithLargestChildEnabled(boolean var1) {
      this.mUseLargestChild = var1;
   }

   public void setOrientation(int var1) {
      if (this.mOrientation != var1) {
         this.mOrientation = var1;
         this.requestLayout();
      }

   }

   public void setShowDividers(int var1) {
      if (var1 != this.mShowDividers) {
         this.requestLayout();
      }

      this.mShowDividers = var1;
   }

   public void setVerticalGravity(int var1) {
      var1 &= 112;
      int var2 = this.mGravity;
      if ((var2 & 112) != var1) {
         this.mGravity = var1 | var2 & -113;
         this.requestLayout();
      }

   }

   public void setWeightSum(float var1) {
      this.mWeightSum = Math.max(0.0F, var1);
   }

   public boolean shouldDelayChildPressedState() {
      return false;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface DividerMode {
   }

   public static class LayoutParams extends MarginLayoutParams {
      public int gravity = -1;
      public float weight;

      public LayoutParams(int var1, int var2) {
         super(var1, var2);
         this.weight = 0.0F;
      }

      public LayoutParams(int var1, int var2, float var3) {
         super(var1, var2);
         this.weight = var3;
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
         TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.LinearLayoutCompat_Layout);
         this.weight = var3.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0F);
         this.gravity = var3.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
         var3.recycle();
      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(MarginLayoutParams var1) {
         super(var1);
      }

      public LayoutParams(LinearLayoutCompat.LayoutParams var1) {
         super(var1);
         this.weight = var1.weight;
         this.gravity = var1.gravity;
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface OrientationMode {
   }
}
