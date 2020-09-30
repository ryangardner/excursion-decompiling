package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import androidx.appcompat.content.res.AppCompatResources;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;

class ClearTextEndIconDelegate extends EndIconDelegate {
   private static final int ANIMATION_FADE_DURATION = 100;
   private static final int ANIMATION_SCALE_DURATION = 150;
   private static final float ANIMATION_SCALE_FROM_VALUE = 0.8F;
   private final TextWatcher clearTextEndIconTextWatcher = new TextWatcher() {
      public void afterTextChanged(Editable var1) {
         if (ClearTextEndIconDelegate.this.textInputLayout.getSuffixText() == null) {
            ClearTextEndIconDelegate.this.animateIcon(ClearTextEndIconDelegate.hasText(var1));
         }
      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
      }

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
      }
   };
   private final TextInputLayout.OnEditTextAttachedListener clearTextOnEditTextAttachedListener = new TextInputLayout.OnEditTextAttachedListener() {
      public void onEditTextAttached(TextInputLayout var1) {
         EditText var2 = var1.getEditText();
         boolean var3;
         if (var2.hasFocus() && ClearTextEndIconDelegate.hasText(var2.getText())) {
            var3 = true;
         } else {
            var3 = false;
         }

         var1.setEndIconVisible(var3);
         var1.setEndIconCheckable(false);
         var2.setOnFocusChangeListener(ClearTextEndIconDelegate.this.onFocusChangeListener);
         var2.removeTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
         var2.addTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
      }
   };
   private final TextInputLayout.OnEndIconChangedListener endIconChangedListener = new TextInputLayout.OnEndIconChangedListener() {
      public void onEndIconChanged(TextInputLayout var1, int var2) {
         EditText var3 = var1.getEditText();
         if (var3 != null && var2 == 2) {
            var3.removeTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
            if (var3.getOnFocusChangeListener() == ClearTextEndIconDelegate.this.onFocusChangeListener) {
               var3.setOnFocusChangeListener((OnFocusChangeListener)null);
            }
         }

      }
   };
   private AnimatorSet iconInAnim;
   private ValueAnimator iconOutAnim;
   private final OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
      public void onFocusChange(View var1, boolean var2) {
         boolean var3 = TextUtils.isEmpty(((EditText)var1).getText());
         boolean var4 = true;
         ClearTextEndIconDelegate var5 = ClearTextEndIconDelegate.this;
         if (var3 ^ true && var2) {
            var2 = var4;
         } else {
            var2 = false;
         }

         var5.animateIcon(var2);
      }
   };

   ClearTextEndIconDelegate(TextInputLayout var1) {
      super(var1);
   }

   private void animateIcon(boolean var1) {
      boolean var2;
      if (this.textInputLayout.isEndIconVisible() == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var1) {
         this.iconOutAnim.cancel();
         this.iconInAnim.start();
         if (var2) {
            this.iconInAnim.end();
         }
      } else {
         this.iconInAnim.cancel();
         this.iconOutAnim.start();
         if (var2) {
            this.iconOutAnim.end();
         }
      }

   }

   private ValueAnimator getAlphaAnimator(float... var1) {
      ValueAnimator var2 = ValueAnimator.ofFloat(var1);
      var2.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
      var2.setDuration(100L);
      var2.addUpdateListener(new AnimatorUpdateListener() {
         public void onAnimationUpdate(ValueAnimator var1) {
            float var2 = (Float)var1.getAnimatedValue();
            ClearTextEndIconDelegate.this.endIconView.setAlpha(var2);
         }
      });
      return var2;
   }

   private ValueAnimator getScaleAnimator() {
      ValueAnimator var1 = ValueAnimator.ofFloat(new float[]{0.8F, 1.0F});
      var1.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
      var1.setDuration(150L);
      var1.addUpdateListener(new AnimatorUpdateListener() {
         public void onAnimationUpdate(ValueAnimator var1) {
            float var2 = (Float)var1.getAnimatedValue();
            ClearTextEndIconDelegate.this.endIconView.setScaleX(var2);
            ClearTextEndIconDelegate.this.endIconView.setScaleY(var2);
         }
      });
      return var1;
   }

   private static boolean hasText(Editable var0) {
      boolean var1;
      if (var0.length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void initAnimators() {
      ValueAnimator var1 = this.getScaleAnimator();
      ValueAnimator var2 = this.getAlphaAnimator(0.0F, 1.0F);
      AnimatorSet var3 = new AnimatorSet();
      this.iconInAnim = var3;
      var3.playTogether(new Animator[]{var1, var2});
      this.iconInAnim.addListener(new AnimatorListenerAdapter() {
         public void onAnimationStart(Animator var1) {
            ClearTextEndIconDelegate.this.textInputLayout.setEndIconVisible(true);
         }
      });
      var2 = this.getAlphaAnimator(1.0F, 0.0F);
      this.iconOutAnim = var2;
      var2.addListener(new AnimatorListenerAdapter() {
         public void onAnimationEnd(Animator var1) {
            ClearTextEndIconDelegate.this.textInputLayout.setEndIconVisible(false);
         }
      });
   }

   void initialize() {
      this.textInputLayout.setEndIconDrawable(AppCompatResources.getDrawable(this.context, R.drawable.mtrl_ic_cancel));
      this.textInputLayout.setEndIconContentDescription(this.textInputLayout.getResources().getText(R.string.clear_text_end_icon_content_description));
      this.textInputLayout.setEndIconOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            Editable var2 = ClearTextEndIconDelegate.this.textInputLayout.getEditText().getText();
            if (var2 != null) {
               var2.clear();
            }

         }
      });
      this.textInputLayout.addOnEditTextAttachedListener(this.clearTextOnEditTextAttachedListener);
      this.textInputLayout.addOnEndIconChangedListener(this.endIconChangedListener);
      this.initAnimators();
   }

   void onSuffixVisibilityChanged(boolean var1) {
      if (this.textInputLayout.getSuffixText() != null) {
         this.animateIcon(var1);
      }
   }
}
