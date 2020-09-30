package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.appcompat.R;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

class AppCompatSeekBarHelper extends AppCompatProgressBarHelper {
   private boolean mHasTickMarkTint = false;
   private boolean mHasTickMarkTintMode = false;
   private Drawable mTickMark;
   private ColorStateList mTickMarkTintList = null;
   private Mode mTickMarkTintMode = null;
   private final SeekBar mView;

   AppCompatSeekBarHelper(SeekBar var1) {
      super(var1);
      this.mView = var1;
   }

   private void applyTickMarkTint() {
      if (this.mTickMark != null && (this.mHasTickMarkTint || this.mHasTickMarkTintMode)) {
         Drawable var1 = DrawableCompat.wrap(this.mTickMark.mutate());
         this.mTickMark = var1;
         if (this.mHasTickMarkTint) {
            DrawableCompat.setTintList(var1, this.mTickMarkTintList);
         }

         if (this.mHasTickMarkTintMode) {
            DrawableCompat.setTintMode(this.mTickMark, this.mTickMarkTintMode);
         }

         if (this.mTickMark.isStateful()) {
            this.mTickMark.setState(this.mView.getDrawableState());
         }
      }

   }

   void drawTickMarks(Canvas var1) {
      if (this.mTickMark != null) {
         int var2 = this.mView.getMax();
         int var3 = 1;
         if (var2 > 1) {
            int var4 = this.mTickMark.getIntrinsicWidth();
            int var5 = this.mTickMark.getIntrinsicHeight();
            if (var4 >= 0) {
               var4 /= 2;
            } else {
               var4 = 1;
            }

            if (var5 >= 0) {
               var3 = var5 / 2;
            }

            this.mTickMark.setBounds(-var4, -var3, var4, var3);
            float var6 = (float)(this.mView.getWidth() - this.mView.getPaddingLeft() - this.mView.getPaddingRight()) / (float)var2;
            var3 = var1.save();
            var1.translate((float)this.mView.getPaddingLeft(), (float)(this.mView.getHeight() / 2));

            for(var4 = 0; var4 <= var2; ++var4) {
               this.mTickMark.draw(var1);
               var1.translate(var6, 0.0F);
            }

            var1.restoreToCount(var3);
         }
      }

   }

   void drawableStateChanged() {
      Drawable var1 = this.mTickMark;
      if (var1 != null && var1.isStateful() && var1.setState(this.mView.getDrawableState())) {
         this.mView.invalidateDrawable(var1);
      }

   }

   Drawable getTickMark() {
      return this.mTickMark;
   }

   ColorStateList getTickMarkTintList() {
      return this.mTickMarkTintList;
   }

   Mode getTickMarkTintMode() {
      return this.mTickMarkTintMode;
   }

   void jumpDrawablesToCurrentState() {
      Drawable var1 = this.mTickMark;
      if (var1 != null) {
         var1.jumpToCurrentState();
      }

   }

   void loadFromAttributes(AttributeSet var1, int var2) {
      super.loadFromAttributes(var1, var2);
      TintTypedArray var3 = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), var1, R.styleable.AppCompatSeekBar, var2, 0);
      SeekBar var4 = this.mView;
      ViewCompat.saveAttributeDataForStyleable(var4, var4.getContext(), R.styleable.AppCompatSeekBar, var1, var3.getWrappedTypeArray(), var2, 0);
      Drawable var5 = var3.getDrawableIfKnown(R.styleable.AppCompatSeekBar_android_thumb);
      if (var5 != null) {
         this.mView.setThumb(var5);
      }

      this.setTickMark(var3.getDrawable(R.styleable.AppCompatSeekBar_tickMark));
      if (var3.hasValue(R.styleable.AppCompatSeekBar_tickMarkTintMode)) {
         this.mTickMarkTintMode = DrawableUtils.parseTintMode(var3.getInt(R.styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.mTickMarkTintMode);
         this.mHasTickMarkTintMode = true;
      }

      if (var3.hasValue(R.styleable.AppCompatSeekBar_tickMarkTint)) {
         this.mTickMarkTintList = var3.getColorStateList(R.styleable.AppCompatSeekBar_tickMarkTint);
         this.mHasTickMarkTint = true;
      }

      var3.recycle();
      this.applyTickMarkTint();
   }

   void setTickMark(Drawable var1) {
      Drawable var2 = this.mTickMark;
      if (var2 != null) {
         var2.setCallback((Callback)null);
      }

      this.mTickMark = var1;
      if (var1 != null) {
         var1.setCallback(this.mView);
         DrawableCompat.setLayoutDirection(var1, ViewCompat.getLayoutDirection(this.mView));
         if (var1.isStateful()) {
            var1.setState(this.mView.getDrawableState());
         }

         this.applyTickMarkTint();
      }

      this.mView.invalidate();
   }

   void setTickMarkTintList(ColorStateList var1) {
      this.mTickMarkTintList = var1;
      this.mHasTickMarkTint = true;
      this.applyTickMarkTint();
   }

   void setTickMarkTintMode(Mode var1) {
      this.mTickMarkTintMode = var1;
      this.mHasTickMarkTintMode = true;
      this.applyTickMarkTint();
   }
}