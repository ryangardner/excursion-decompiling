/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.material.textfield;

import android.content.Context;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;

abstract class EndIconDelegate {
    Context context;
    CheckableImageButton endIconView;
    TextInputLayout textInputLayout;

    EndIconDelegate(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
        this.context = textInputLayout.getContext();
        this.endIconView = textInputLayout.getEndIconView();
    }

    abstract void initialize();

    boolean isBoxBackgroundModeSupported(int n) {
        return true;
    }

    void onSuffixVisibilityChanged(boolean bl) {
    }

    boolean shouldTintIconOnError() {
        return false;
    }
}

