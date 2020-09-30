package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R;

public class Layer extends ConstraintHelper {
   private static final String TAG = "Layer";
   private boolean mApplyElevationOnAttach;
   private boolean mApplyVisibilityOnAttach;
   protected float mComputedCenterX = Float.NaN;
   protected float mComputedCenterY = Float.NaN;
   protected float mComputedMaxX = Float.NaN;
   protected float mComputedMaxY = Float.NaN;
   protected float mComputedMinX = Float.NaN;
   protected float mComputedMinY = Float.NaN;
   ConstraintLayout mContainer;
   private float mGroupRotateAngle = Float.NaN;
   boolean mNeedBounds = true;
   private float mRotationCenterX = Float.NaN;
   private float mRotationCenterY = Float.NaN;
   private float mScaleX = 1.0F;
   private float mScaleY = 1.0F;
   private float mShiftX = 0.0F;
   private float mShiftY = 0.0F;
   View[] mViews = null;

   public Layer(Context var1) {
      super(var1);
   }

   public Layer(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public Layer(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   private void reCacheViews() {
      if (this.mContainer != null) {
         if (this.mCount != 0) {
            View[] var1 = this.mViews;
            if (var1 == null || var1.length != this.mCount) {
               this.mViews = new View[this.mCount];
            }

            for(int var2 = 0; var2 < this.mCount; ++var2) {
               int var3 = this.mIds[var2];
               this.mViews[var2] = this.mContainer.getViewById(var3);
            }

         }
      }
   }

   private void transform() {
      if (this.mContainer != null) {
         if (this.mViews == null) {
            this.reCacheViews();
         }

         this.calcCenters();
         double var1 = Math.toRadians((double)this.mGroupRotateAngle);
         float var3 = (float)Math.sin(var1);
         float var4 = (float)Math.cos(var1);
         float var5 = this.mScaleX;
         float var6 = this.mScaleY;
         float var7 = -var6;

         for(int var8 = 0; var8 < this.mCount; ++var8) {
            View var9 = this.mViews[var8];
            int var10 = (var9.getLeft() + var9.getRight()) / 2;
            int var11 = (var9.getTop() + var9.getBottom()) / 2;
            float var12 = (float)var10 - this.mComputedCenterX;
            float var13 = (float)var11 - this.mComputedCenterY;
            float var14 = this.mShiftX;
            float var15 = this.mShiftY;
            var9.setTranslationX(var5 * var4 * var12 + var7 * var3 * var13 - var12 + var14);
            var9.setTranslationY(var12 * var5 * var3 + var6 * var4 * var13 - var13 + var15);
            var9.setScaleY(this.mScaleY);
            var9.setScaleX(this.mScaleX);
            var9.setRotation(this.mGroupRotateAngle);
         }

      }
   }

   protected void calcCenters() {
      if (this.mContainer != null) {
         if (this.mNeedBounds || Float.isNaN(this.mComputedCenterX) || Float.isNaN(this.mComputedCenterY)) {
            if (!Float.isNaN(this.mRotationCenterX) && !Float.isNaN(this.mRotationCenterY)) {
               this.mComputedCenterY = this.mRotationCenterY;
               this.mComputedCenterX = this.mRotationCenterX;
            } else {
               View[] var1 = this.getViews(this.mContainer);
               int var2 = 0;
               int var3 = var1[0].getLeft();
               int var4 = var1[0].getTop();
               int var5 = var1[0].getRight();

               int var6;
               for(var6 = var1[0].getBottom(); var2 < this.mCount; ++var2) {
                  View var7 = var1[var2];
                  var3 = Math.min(var3, var7.getLeft());
                  var4 = Math.min(var4, var7.getTop());
                  var5 = Math.max(var5, var7.getRight());
                  var6 = Math.max(var6, var7.getBottom());
               }

               this.mComputedMaxX = (float)var5;
               this.mComputedMaxY = (float)var6;
               this.mComputedMinX = (float)var3;
               this.mComputedMinY = (float)var4;
               if (Float.isNaN(this.mRotationCenterX)) {
                  this.mComputedCenterX = (float)((var3 + var5) / 2);
               } else {
                  this.mComputedCenterX = this.mRotationCenterX;
               }

               if (Float.isNaN(this.mRotationCenterY)) {
                  this.mComputedCenterY = (float)((var4 + var6) / 2);
               } else {
                  this.mComputedCenterY = this.mRotationCenterY;
               }
            }

         }
      }
   }

   protected void init(AttributeSet var1) {
      super.init(var1);
      int var2 = 0;
      this.mUseViewMeasure = false;
      if (var1 != null) {
         TypedArray var5 = this.getContext().obtainStyledAttributes(var1, R.styleable.ConstraintLayout_Layout);

         for(int var3 = var5.getIndexCount(); var2 < var3; ++var2) {
            int var4 = var5.getIndex(var2);
            if (var4 == R.styleable.ConstraintLayout_Layout_android_visibility) {
               this.mApplyVisibilityOnAttach = true;
            } else if (var4 == R.styleable.ConstraintLayout_Layout_android_elevation) {
               this.mApplyElevationOnAttach = true;
            }
         }
      }

   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.mContainer = (ConstraintLayout)this.getParent();
      if (this.mApplyVisibilityOnAttach || this.mApplyElevationOnAttach) {
         int var1 = this.getVisibility();
         float var2;
         if (VERSION.SDK_INT >= 21) {
            var2 = this.getElevation();
         } else {
            var2 = 0.0F;
         }

         for(int var3 = 0; var3 < this.mCount; ++var3) {
            int var4 = this.mIds[var3];
            View var5 = this.mContainer.getViewById(var4);
            if (var5 != null) {
               if (this.mApplyVisibilityOnAttach) {
                  var5.setVisibility(var1);
               }

               if (this.mApplyElevationOnAttach && var2 > 0.0F && VERSION.SDK_INT >= 21) {
                  var5.setTranslationZ(var5.getTranslationZ() + var2);
               }
            }
         }
      }

   }

   public void setElevation(float var1) {
      super.setElevation(var1);
      this.applyLayoutFeatures();
   }

   public void setPivotX(float var1) {
      this.mRotationCenterX = var1;
      this.transform();
   }

   public void setPivotY(float var1) {
      this.mRotationCenterY = var1;
      this.transform();
   }

   public void setRotation(float var1) {
      this.mGroupRotateAngle = var1;
      this.transform();
   }

   public void setScaleX(float var1) {
      this.mScaleX = var1;
      this.transform();
   }

   public void setScaleY(float var1) {
      this.mScaleY = var1;
      this.transform();
   }

   public void setTranslationX(float var1) {
      this.mShiftX = var1;
      this.transform();
   }

   public void setTranslationY(float var1) {
      this.mShiftY = var1;
      this.transform();
   }

   public void setVisibility(int var1) {
      super.setVisibility(var1);
      this.applyLayoutFeatures();
   }

   public void updatePostLayout(ConstraintLayout var1) {
      this.reCacheViews();
      this.mComputedCenterX = Float.NaN;
      this.mComputedCenterY = Float.NaN;
      ConstraintWidget var2 = ((ConstraintLayout.LayoutParams)this.getLayoutParams()).getConstraintWidget();
      var2.setWidth(0);
      var2.setHeight(0);
      this.calcCenters();
      this.layout((int)this.mComputedMinX - this.getPaddingLeft(), (int)this.mComputedMinY - this.getPaddingTop(), (int)this.mComputedMaxX + this.getPaddingRight(), (int)this.mComputedMaxY + this.getPaddingBottom());
      if (!Float.isNaN(this.mGroupRotateAngle)) {
         this.transform();
      }

   }

   public void updatePreDraw(ConstraintLayout var1) {
      this.mContainer = var1;
      float var2 = this.getRotation();
      if (var2 == 0.0F) {
         if (!Float.isNaN(this.mGroupRotateAngle)) {
            this.mGroupRotateAngle = var2;
         }
      } else {
         this.mGroupRotateAngle = var2;
      }

   }
}
