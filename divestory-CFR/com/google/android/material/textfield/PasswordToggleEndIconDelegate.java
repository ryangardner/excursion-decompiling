/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.text.method.PasswordTransformationMethod
 *  android.text.method.TransformationMethod
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.EditText
 */
package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.content.res.AppCompatResources;
import com.google.android.material.R;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.EndIconDelegate;
import com.google.android.material.textfield.TextInputLayout;

class PasswordToggleEndIconDelegate
extends EndIconDelegate {
    private final TextInputLayout.OnEditTextAttachedListener onEditTextAttachedListener = new TextInputLayout.OnEditTextAttachedListener(){

        @Override
        public void onEditTextAttached(TextInputLayout textInputLayout) {
            EditText editText = textInputLayout.getEditText();
            textInputLayout.setEndIconVisible(true);
            textInputLayout.setEndIconCheckable(true);
            PasswordToggleEndIconDelegate.this.endIconView.setChecked(true ^ PasswordToggleEndIconDelegate.this.hasPasswordTransformation());
            editText.removeTextChangedListener(PasswordToggleEndIconDelegate.this.textWatcher);
            editText.addTextChangedListener(PasswordToggleEndIconDelegate.this.textWatcher);
        }
    };
    private final TextInputLayout.OnEndIconChangedListener onEndIconChangedListener = new TextInputLayout.OnEndIconChangedListener(){

        @Override
        public void onEndIconChanged(TextInputLayout textInputLayout, int n) {
            if ((textInputLayout = textInputLayout.getEditText()) == null) return;
            if (n != 1) return;
            textInputLayout.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
            textInputLayout.removeTextChangedListener(PasswordToggleEndIconDelegate.this.textWatcher);
        }
    };
    private final TextWatcher textWatcher = new TextWatcher(){

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            PasswordToggleEndIconDelegate.this.endIconView.setChecked(PasswordToggleEndIconDelegate.this.hasPasswordTransformation() ^ true);
        }

        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }
    };

    PasswordToggleEndIconDelegate(TextInputLayout textInputLayout) {
        super(textInputLayout);
    }

    private boolean hasPasswordTransformation() {
        EditText editText = this.textInputLayout.getEditText();
        if (editText == null) return false;
        if (!(editText.getTransformationMethod() instanceof PasswordTransformationMethod)) return false;
        return true;
    }

    private static boolean isInputTypePassword(EditText editText) {
        if (editText == null) return false;
        if (editText.getInputType() == 16) return true;
        if (editText.getInputType() == 128) return true;
        if (editText.getInputType() == 144) return true;
        if (editText.getInputType() != 224) return false;
        return true;
    }

    @Override
    void initialize() {
        this.textInputLayout.setEndIconDrawable(AppCompatResources.getDrawable(this.context, R.drawable.design_password_eye));
        this.textInputLayout.setEndIconContentDescription(this.textInputLayout.getResources().getText(R.string.password_toggle_content_description));
        this.textInputLayout.setEndIconOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                view = PasswordToggleEndIconDelegate.this.textInputLayout.getEditText();
                if (view == null) {
                    return;
                }
                int n = view.getSelectionEnd();
                if (PasswordToggleEndIconDelegate.this.hasPasswordTransformation()) {
                    view.setTransformationMethod(null);
                } else {
                    view.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
                }
                if (n < 0) return;
                view.setSelection(n);
            }
        });
        this.textInputLayout.addOnEditTextAttachedListener(this.onEditTextAttachedListener);
        this.textInputLayout.addOnEndIconChangedListener(this.onEndIconChangedListener);
        EditText editText = this.textInputLayout.getEditText();
        if (!PasswordToggleEndIconDelegate.isInputTypePassword(editText)) return;
        editText.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
    }

}

