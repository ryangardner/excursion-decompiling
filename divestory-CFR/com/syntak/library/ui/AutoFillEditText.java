/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.Editable
 *  android.util.AttributeSet
 */
package com.syntak.library.ui;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class AutoFillEditText
extends AppCompatAutoCompleteTextView {
    int mThreshold = 0;

    public AutoFillEditText(Context context) {
        super(context);
    }

    public AutoFillEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AutoFillEditText(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public boolean enoughToFilter() {
        if (this.getText().length() < this.mThreshold) return false;
        return true;
    }

    public int getThreshold() {
        return this.mThreshold;
    }

    public void setThreshold(int n) {
        if (n < 0) return;
        this.mThreshold = n;
    }
}

