package com.syntak.library.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import com.syntak.library.R;

public class SmartRatingBar extends View {
   private int mGapSize;
   private boolean mIndicator = true;
   private int mMaxStarNum = 5;
   private SmartRatingBar.OnRatingBarChangeListener mOnRatingBarChangeListener;
   private int mOrientation = 0;
   private Drawable mRatingBackgroundDrawable;
   private Drawable mRatingDrawable;
   private float mRatingNum = 3.3F;
   private float mRatingStepSize = 0.1F;

   public SmartRatingBar(Context var1) {
      super(var1);
      this.init(var1, (AttributeSet)null);
   }

   public SmartRatingBar(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init(var1, var2);
   }

   private void init(Context var1, AttributeSet var2) {
      if (var2 != null) {
         TypedArray var3 = var1.obtainStyledAttributes(var2, R.styleable.SmartRatingBar);
         this.mRatingNum = var3.getFloat(R.styleable.SmartRatingBar_rating, 2.5F);
         this.mGapSize = var3.getDimensionPixelSize(R.styleable.SmartRatingBar_gap, 0);
         this.mMaxStarNum = var3.getInt(R.styleable.SmartRatingBar_maxRating, 5);
         this.mIndicator = var3.getBoolean(R.styleable.SmartRatingBar_indicator, true);
         this.mOrientation = var3.getInt(R.styleable.SmartRatingBar_orientation, 0);
         this.mRatingDrawable = var3.getDrawable(R.styleable.SmartRatingBar_ratingDrawable);
         this.mRatingBackgroundDrawable = var3.getDrawable(R.styleable.SmartRatingBar_backgroundDrawable);
         var3.recycle();
      }

      if (this.mRatingDrawable == null && this.mRatingBackgroundDrawable == null) {
         this.mRatingDrawable = var1.getResources().getDrawable(R.drawable.smart_ratingbar_rating);
         this.mRatingBackgroundDrawable = var1.getResources().getDrawable(R.drawable.smart_ratingbar_background);
      }

   }

   private int measureHeight(int var1) {
      int var2 = MeasureSpec.getMode(var1);
      int var3 = MeasureSpec.getSize(var1);
      if (var2 == 1073741824) {
         return var3;
      } else {
         int var4 = this.mRatingDrawable.getBounds().height();
         var1 = var4;
         if (var4 == 0) {
            var1 = this.mRatingDrawable.getIntrinsicHeight();
         }

         int var5 = this.getPaddingBottom() + this.getPaddingTop();
         if (this.mOrientation == 0) {
            var1 += var5;
         } else {
            var4 = this.mMaxStarNum;
            var1 = var5 + (var4 - 1) * this.mGapSize + var4 * var1;
         }

         return var2 == Integer.MIN_VALUE ? Math.min(var3, var1) : var1;
      }
   }

   private int measureWidth(int var1) {
      int var2 = MeasureSpec.getMode(var1);
      int var3 = MeasureSpec.getSize(var1);
      if (var2 == 1073741824) {
         return var3;
      } else {
         int var4 = this.mRatingDrawable.getBounds().width();
         var1 = var4;
         if (var4 == 0) {
            var1 = this.mRatingDrawable.getIntrinsicWidth();
         }

         var4 = this.getPaddingLeft() + this.getPaddingRight();
         if (this.mOrientation == 0) {
            int var5 = this.mMaxStarNum;
            var1 = var4 + (var5 - 1) * this.mGapSize + var5 * var1;
         } else {
            var1 += var4;
         }

         return var2 == Integer.MIN_VALUE ? Math.min(var1, var3) : var1;
      }
   }

   private void translateCanvas(Canvas var1, Rect var2) {
      if (this.mOrientation == 0) {
         var1.translate((float)(this.mGapSize + var2.width()), 0.0F);
      } else {
         var1.translate(0.0F, (float)(this.mGapSize + var2.height()));
      }

   }

   public boolean dispatchTouchEvent(MotionEvent var1) {
      if (this.mIndicator) {
         return true;
      } else if (var1.getAction() != 0) {
         return this.mIndicator;
      } else {
         int var2;
         float var3;
         int var4;
         int var5;
         if (this.mOrientation == 0) {
            var2 = this.mRatingBackgroundDrawable.getBounds().width();
            var3 = var1.getX() - (float)this.getPaddingLeft();
            var4 = this.mGapSize;
            var5 = (int)(var3 / (float)(var2 + var4));
            var3 = (float)((int)(var3 % (float)(var4 + var2) / (float)var2 / this.mRatingStepSize)) / 10.0F;
            this.setRatingNum((float)var5 + var3);
         } else {
            var2 = this.mRatingBackgroundDrawable.getBounds().height();
            var3 = var1.getY() - (float)this.getPaddingTop();
            var4 = this.mGapSize;
            var5 = (int)(var3 / (float)(var2 + var4));
            var3 = (float)((int)(var3 % (float)(var4 + var2) / (float)var2 / this.mRatingStepSize)) / 10.0F;
            this.setRatingNum((float)var5 + var3);
         }

         return true;
      }
   }

   public int getGapSize() {
      return this.mGapSize;
   }

   public int getMaxStarNum() {
      return this.mMaxStarNum;
   }

   public Drawable getRatingBackgroundDrawable() {
      return this.mRatingBackgroundDrawable;
   }

   public Drawable getRatingDrawable() {
      return this.mRatingDrawable;
   }

   public float getRatingNum() {
      return this.mRatingNum;
   }

   public boolean isIndicator() {
      return this.mIndicator;
   }

   protected void onDraw(Canvas var1) {
      super.onDraw(var1);
      Drawable var2 = this.mRatingDrawable;
      Drawable var3 = this.mRatingBackgroundDrawable;
      float var4 = this.mRatingNum;
      Rect var5 = var2.getBounds();
      int var6 = (int)Math.floor((double)var4);
      var1.translate((float)this.getPaddingLeft(), (float)this.getPaddingTop());

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         var2.draw(var1);
         this.translateCanvas(var1, var5);
      }

      var4 -= (float)var6;
      int var8;
      if (this.mOrientation == 0) {
         var6 = (int)((float)var5.width() * var4);
         var8 = var5.height();
      } else {
         var6 = var5.width();
         var8 = (int)((float)var5.height() * var4);
      }

      var1.save();
      var1.clipRect(0, 0, var6, var8);
      var2.draw(var1);
      var1.restore();
      var1.save();
      if (this.mOrientation == 0) {
         var1.clipRect(var6, 0, var5.right, var5.bottom);
      } else {
         var1.clipRect(0, var8, var5.right, var5.bottom);
      }

      var3.draw(var1);
      var1.restore();
      this.translateCanvas(var1, var5);

      while(true) {
         ++var7;
         if (var7 >= this.mMaxStarNum) {
            return;
         }

         var3.draw(var1);
         this.translateCanvas(var1, var5);
      }
   }

   protected void onMeasure(int var1, int var2) {
      super.onMeasure(var1, var2);
      Drawable var3;
      if (this.mOrientation == 0) {
         var2 = this.measureHeight(var2);
         if (this.mRatingDrawable.getIntrinsicHeight() > var2) {
            this.mRatingDrawable.setBounds(0, 0, var2, var2);
            this.mRatingBackgroundDrawable.setBounds(0, 0, var2, var2);
         } else {
            var3 = this.mRatingDrawable;
            var3.setBounds(0, 0, var3.getIntrinsicWidth(), this.mRatingDrawable.getIntrinsicHeight());
            this.mRatingBackgroundDrawable.setBounds(0, 0, this.mRatingDrawable.getIntrinsicWidth(), this.mRatingDrawable.getIntrinsicHeight());
         }

         var1 = this.measureWidth(var1);
      } else {
         var1 = this.measureWidth(var1);
         if (this.mRatingDrawable.getIntrinsicWidth() > var1) {
            this.mRatingDrawable.setBounds(0, 0, var1, var1);
            this.mRatingBackgroundDrawable.setBounds(0, 0, var1, var1);
         } else {
            var3 = this.mRatingDrawable;
            var3.setBounds(0, 0, var3.getIntrinsicWidth(), this.mRatingDrawable.getIntrinsicHeight());
            this.mRatingBackgroundDrawable.setBounds(0, 0, this.mRatingDrawable.getIntrinsicWidth(), this.mRatingDrawable.getIntrinsicHeight());
         }

         var2 = this.measureHeight(var2);
      }

      this.setMeasuredDimension(var1, var2);
   }

   protected void onRestoreInstanceState(Parcelable var1) {
      SmartRatingBar.SavedState var2 = (SmartRatingBar.SavedState)var1;
      super.onRestoreInstanceState(var2.getSuperState());
      this.setRatingNum(var2.getRating());
      this.setMaxStarNum(var2.getStarNum());
   }

   protected Parcelable onSaveInstanceState() {
      SmartRatingBar.SavedState var1 = new SmartRatingBar.SavedState(super.onSaveInstanceState());
      var1.mRating = this.getRatingNum();
      var1.mStarNum = this.getMaxStarNum();
      return var1;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      if (this.mIndicator) {
         return true;
      } else if (var1.getAction() != 0) {
         return super.onTouchEvent(var1);
      } else {
         int var2;
         float var3;
         int var4;
         int var5;
         if (this.mOrientation == 0) {
            var2 = this.mRatingBackgroundDrawable.getBounds().width();
            var3 = var1.getX() - (float)this.getPaddingLeft();
            var4 = this.mGapSize;
            var5 = (int)(var3 / (float)(var2 + var4));
            var3 = (float)((int)(var3 % (float)(var4 + var2) / (float)var2 / this.mRatingStepSize)) / 10.0F;
            this.setRatingNum((float)var5 + var3);
         } else {
            var2 = this.mRatingBackgroundDrawable.getBounds().height();
            var3 = var1.getY() - (float)this.getPaddingTop();
            var4 = this.mGapSize;
            var5 = (int)(var3 / (float)(var2 + var4));
            var3 = (float)((int)(var3 % (float)(var4 + var2) / (float)var2 / this.mRatingStepSize)) / 10.0F;
            this.setRatingNum((float)var5 + var3);
         }

         return true;
      }
   }

   public void setGapSize(int var1) {
      this.mGapSize = var1;
      this.postInvalidate();
   }

   public void setIndicator(boolean var1) {
      this.mIndicator = var1;
   }

   public void setMaxStarNum(int var1) {
      this.mMaxStarNum = var1;
      this.postInvalidate();
   }

   public void setOnRatingBarChangeListener(SmartRatingBar.OnRatingBarChangeListener var1) {
      this.mOnRatingBarChangeListener = var1;
   }

   public void setRatingBackgroundDrawable(Drawable var1) {
      this.mRatingBackgroundDrawable = var1;
   }

   public void setRatingDrawable(Drawable var1) {
      this.mRatingDrawable = var1;
      this.postInvalidate();
   }

   public void setRatingNum(float var1) {
      this.mRatingNum = var1;
      this.postInvalidate();
      SmartRatingBar.OnRatingBarChangeListener var2 = this.mOnRatingBarChangeListener;
      if (var2 != null) {
         var2.onRatingChanged(this, var1);
      }

   }

   public interface OnRatingBarChangeListener {
      void onRatingChanged(SmartRatingBar var1, float var2);
   }

   static class SavedState extends BaseSavedState {
      public static final Creator<SmartRatingBar.SavedState> CREATOR = new Creator<SmartRatingBar.SavedState>() {
         public SmartRatingBar.SavedState createFromParcel(Parcel var1) {
            return new SmartRatingBar.SavedState(var1);
         }

         public SmartRatingBar.SavedState[] newArray(int var1) {
            return new SmartRatingBar.SavedState[var1];
         }
      };
      float mRating;
      int mStarNum;

      public SavedState(Parcel var1) {
         super(var1);
         this.mRating = var1.readFloat();
         this.mStarNum = var1.readInt();
      }

      public SavedState(Parcelable var1) {
         super(var1);
      }

      public float getRating() {
         return this.mRating;
      }

      public int getStarNum() {
         return this.mStarNum;
      }

      public void setRating(float var1) {
         this.mRating = var1;
      }

      public void setStarNum(int var1) {
         this.mStarNum = var1;
      }

      public void writeToParcel(Parcel var1, int var2) {
         super.writeToParcel(var1, var2);
         var1.writeFloat(this.mRating);
         var1.writeInt(this.mStarNum);
      }
   }
}
