/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.widget.EditText
 */
package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.content.res.AppCompatResources;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.EndIconDelegate;
import com.google.android.material.textfield.TextInputLayout;

class ClearTextEndIconDelegate
extends EndIconDelegate {
    private static final int ANIMATION_FADE_DURATION = 100;
    private static final int ANIMATION_SCALE_DURATION = 150;
    private static final float ANIMATION_SCALE_FROM_VALUE = 0.8f;
    private final TextWatcher clearTextEndIconTextWatcher = new TextWatcher(){

        public void afterTextChanged(Editable editable) {
            if (ClearTextEndIconDelegate.this.textInputLayout.getSuffixText() != null) {
                return;
            }
            ClearTextEndIconDelegate.this.animateIcon(ClearTextEndIconDelegate.hasText(editable));
        }

        public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }

        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }
    };
    private final TextInputLayout.OnEditTextAttachedListener clearTextOnEditTextAttachedListener = new TextInputLayout.OnEditTextAttachedListener(){

        @Override
        public void onEditTextAttached(TextInputLayout textInputLayout) {
            EditText editText = textInputLayout.getEditText();
            boolean bl = editText.hasFocus() && ClearTextEndIconDelegate.hasText(editText.getText());
            textInputLayout.setEndIconVisible(bl);
            textInputLayout.setEndIconCheckable(false);
            editText.setOnFocusChangeListener(ClearTextEndIconDelegate.this.onFocusChangeListener);
            editText.removeTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
            editText.addTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
        }
    };
    private final TextInputLayout.OnEndIconChangedListener endIconChangedListener = new TextInputLayout.OnEndIconChangedListener(){

        @Override
        public void onEndIconChanged(TextInputLayout textInputLayout, int n) {
            if ((textInputLayout = textInputLayout.getEditText()) == null) return;
            if (n != 2) return;
            textInputLayout.removeTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
            if (textInputLayout.getOnFocusChangeListener() != ClearTextEndIconDelegate.this.onFocusChangeListener) return;
            textInputLayout.setOnFocusChangeListener(null);
        }
    };
    private AnimatorSet iconInAnim;
    private ValueAnimator iconOutAnim;
    private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener(){

        public void onFocusChange(View object, boolean bl) {
            boolean bl2 = TextUtils.isEmpty((CharSequence)((EditText)object).getText());
            boolean bl3 = true;
            object = ClearTextEndIconDelegate.this;
            bl = bl2 ^ true && bl ? bl3 : false;
            ((ClearTextEndIconDelegate)object).animateIcon(bl);
        }
    };

    ClearTextEndIconDelegate(TextInputLayout textInputLayout) {
        super(textInputLayout);
    }

    private void animateIcon(boolean bl) {
        boolean bl2 = this.textInputLayout.isEndIconVisible() == bl;
        if (bl) {
            this.iconOutAnim.cancel();
            this.iconInAnim.start();
            if (!bl2) return;
            this.iconInAnim.end();
            return;
        }
        this.iconInAnim.cancel();
        this.iconOutAnim.start();
        if (!bl2) return;
        this.iconOutAnim.end();
    }

    private ValueAnimator getAlphaAnimator(float ... valueAnimator) {
        valueAnimator = ValueAnimator.ofFloat((float[])valueAnimator);
        valueAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
        valueAnimator.setDuration(100L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                ClearTextEndIconDelegate.this.endIconView.setAlpha(f);
            }
        });
        return valueAnimator;
    }

    private ValueAnimator getScaleAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat((float[])new float[]{0.8f, 1.0f});
        valueAnimator.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        valueAnimator.setDuration(150L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float f = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                ClearTextEndIconDelegate.this.endIconView.setScaleX(f);
                ClearTextEndIconDelegate.this.endIconView.setScaleY(f);
            }
        });
        return valueAnimator;
    }

    private static boolean hasText(Editable editable) {
        if (editable.length() <= 0) return false;
        return true;
    }

    private void initAnimators() {
        AnimatorSet animatorSet;
        ValueAnimator valueAnimator = this.getScaleAnimator();
        ValueAnimator valueAnimator2 = this.getAlphaAnimator(0.0f, 1.0f);
        this.iconInAnim = animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{valueAnimator, valueAnimator2});
        this.iconInAnim.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationStart(Animator animator2) {
                ClearTextEndIconDelegate.this.textInputLayout.setEndIconVisible(true);
            }
        });
        this.iconOutAnim = valueAnimator2 = this.getAlphaAnimator(1.0f, 0.0f);
        valueAnimator2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                ClearTextEndIconDelegate.this.textInputLayout.setEndIconVisible(false);
            }
        });
    }

    @Override
    void initialize() {
        this.textInputLayout.setEndIconDrawable(AppCompatResources.getDrawable(this.context, R.drawable.mtrl_ic_cancel));
        this.textInputLayout.setEndIconContentDescription(this.textInputLayout.getResources().getText(R.string.clear_text_end_icon_content_description));
        this.textInputLayout.setEndIconOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                view = ClearTextEndIconDelegate.this.textInputLayout.getEditText().getText();
                if (view == null) return;
                view.clear();
            }
        });
        this.textInputLayout.addOnEditTextAttachedListener(this.clearTextOnEditTextAttachedListener);
        this.textInputLayout.addOnEndIconChangedListener(this.endIconChangedListener);
        this.initAnimators();
    }

    @Override
    void onSuffixVisibilityChanged(boolean bl) {
        if (this.textInputLayout.getSuffixText() == null) {
            return;
        }
        this.animateIcon(bl);
    }

}

