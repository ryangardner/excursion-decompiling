package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;

public class ContentFrameLayout extends FrameLayout {
   private ContentFrameLayout.OnAttachListener mAttachListener;
   private final Rect mDecorPadding;
   private TypedValue mFixedHeightMajor;
   private TypedValue mFixedHeightMinor;
   private TypedValue mFixedWidthMajor;
   private TypedValue mFixedWidthMinor;
   private TypedValue mMinWidthMajor;
   private TypedValue mMinWidthMinor;

   public ContentFrameLayout(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public ContentFrameLayout(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public ContentFrameLayout(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mDecorPadding = new Rect();
   }

   public void dispatchFitSystemWindows(Rect var1) {
      this.fitSystemWindows(var1);
   }

   public TypedValue getFixedHeightMajor() {
      if (this.mFixedHeightMajor == null) {
         this.mFixedHeightMajor = new TypedValue();
      }

      return this.mFixedHeightMajor;
   }

   public TypedValue getFixedHeightMinor() {
      if (this.mFixedHeightMinor == null) {
         this.mFixedHeightMinor = new TypedValue();
      }

      return this.mFixedHeightMinor;
   }

   public TypedValue getFixedWidthMajor() {
      if (this.mFixedWidthMajor == null) {
         this.mFixedWidthMajor = new TypedValue();
      }

      return this.mFixedWidthMajor;
   }

   public TypedValue getFixedWidthMinor() {
      if (this.mFixedWidthMinor == null) {
         this.mFixedWidthMinor = new TypedValue();
      }

      return this.mFixedWidthMinor;
   }

   public TypedValue getMinWidthMajor() {
      if (this.mMinWidthMajor == null) {
         this.mMinWidthMajor = new TypedValue();
      }

      return this.mMinWidthMajor;
   }

   public TypedValue getMinWidthMinor() {
      if (this.mMinWidthMinor == null) {
         this.mMinWidthMinor = new TypedValue();
      }

      return this.mMinWidthMinor;
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      ContentFrameLayout.OnAttachListener var1 = this.mAttachListener;
      if (var1 != null) {
         var1.onAttachedFromWindow();
      }

   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      ContentFrameLayout.OnAttachListener var1 = this.mAttachListener;
      if (var1 != null) {
         var1.onDetachedFromWindow();
      }

   }

   protected void onMeasure(int var1, int var2) {
      DisplayMetrics var3 = this.getContext().getResources().getDisplayMetrics();
      int var4 = var3.widthPixels;
      int var5 = var3.heightPixels;
      boolean var6 = true;
      boolean var14;
      if (var4 < var5) {
         var14 = true;
      } else {
         var14 = false;
      }

      int var7;
      int var8;
      TypedValue var9;
      float var10;
      int var11;
      boolean var12;
      label104: {
         var7 = MeasureSpec.getMode(var1);
         var8 = MeasureSpec.getMode(var2);
         if (var7 == Integer.MIN_VALUE) {
            if (var14) {
               var9 = this.mFixedWidthMinor;
            } else {
               var9 = this.mFixedWidthMajor;
            }

            if (var9 != null && var9.type != 0) {
               label98: {
                  if (var9.type == 5) {
                     var10 = var9.getDimension(var3);
                  } else {
                     if (var9.type != 6) {
                        var4 = 0;
                        break label98;
                     }

                     var10 = var9.getFraction((float)var3.widthPixels, (float)var3.widthPixels);
                  }

                  var4 = (int)var10;
               }

               if (var4 > 0) {
                  var11 = MeasureSpec.makeMeasureSpec(Math.min(var4 - (this.mDecorPadding.left + this.mDecorPadding.right), MeasureSpec.getSize(var1)), 1073741824);
                  var12 = true;
                  break label104;
               }
            }
         }

         boolean var15 = false;
         var11 = var1;
         var12 = var15;
      }

      var4 = var2;
      if (var8 == Integer.MIN_VALUE) {
         if (var14) {
            var9 = this.mFixedHeightMajor;
         } else {
            var9 = this.mFixedHeightMinor;
         }

         var4 = var2;
         if (var9 != null) {
            var4 = var2;
            if (var9.type != 0) {
               label85: {
                  if (var9.type == 5) {
                     var10 = var9.getDimension(var3);
                  } else {
                     if (var9.type != 6) {
                        var8 = 0;
                        break label85;
                     }

                     var10 = var9.getFraction((float)var3.heightPixels, (float)var3.heightPixels);
                  }

                  var8 = (int)var10;
               }

               var4 = var2;
               if (var8 > 0) {
                  var4 = MeasureSpec.makeMeasureSpec(Math.min(var8 - (this.mDecorPadding.top + this.mDecorPadding.bottom), MeasureSpec.getSize(var2)), 1073741824);
               }
            }
         }
      }

      boolean var13;
      label79: {
         super.onMeasure(var11, var4);
         var8 = this.getMeasuredWidth();
         var11 = MeasureSpec.makeMeasureSpec(var8, 1073741824);
         if (!var12 && var7 == Integer.MIN_VALUE) {
            if (var14) {
               var9 = this.mMinWidthMinor;
            } else {
               var9 = this.mMinWidthMajor;
            }

            if (var9 != null && var9.type != 0) {
               label72: {
                  if (var9.type == 5) {
                     var10 = var9.getDimension(var3);
                  } else {
                     if (var9.type != 6) {
                        var1 = 0;
                        break label72;
                     }

                     var10 = var9.getFraction((float)var3.widthPixels, (float)var3.widthPixels);
                  }

                  var1 = (int)var10;
               }

               var2 = var1;
               if (var1 > 0) {
                  var2 = var1 - (this.mDecorPadding.left + this.mDecorPadding.right);
               }

               if (var8 < var2) {
                  var1 = MeasureSpec.makeMeasureSpec(var2, 1073741824);
                  var13 = var6;
                  break label79;
               }
            }
         }

         var13 = false;
         var1 = var11;
      }

      if (var13) {
         super.onMeasure(var1, var4);
      }

   }

   public void setAttachListener(ContentFrameLayout.OnAttachListener var1) {
      this.mAttachListener = var1;
   }

   public void setDecorPadding(int var1, int var2, int var3, int var4) {
      this.mDecorPadding.set(var1, var2, var3, var4);
      if (ViewCompat.isLaidOut(this)) {
         this.requestLayout();
      }

   }

   public interface OnAttachListener {
      void onAttachedFromWindow();

      void onDetachedFromWindow();
   }
}