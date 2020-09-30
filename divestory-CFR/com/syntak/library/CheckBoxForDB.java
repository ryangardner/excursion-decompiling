/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.widget.CheckBox
 */
package com.syntak.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class CheckBoxForDB
extends CheckBox {
    public CheckBoxForDB(Context context) {
        super(context);
    }

    public CheckBoxForDB(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CheckBoxForDB(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    protected void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        if (charSequence.toString().compareTo("") == 0) return;
        boolean bl = charSequence.toString().compareTo("1") == 0;
        this.setChecked(bl);
        this.setText((CharSequence)"");
    }
}

