/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.RelativeLayout
 */
package com.syntak.library;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.syntak.library.R;

public class ClearableEditText
extends RelativeLayout {
    Button btn_clear;
    EditText edit_text;
    LayoutInflater inflater = null;

    public ClearableEditText(Context context) {
        super(context);
        this.initViews();
    }

    public ClearableEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initViews();
    }

    public ClearableEditText(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initViews();
    }

    void clearText() {
        this.btn_clear.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ClearableEditText.this.edit_text.setText((CharSequence)"");
            }
        });
    }

    public Editable getText() {
        return this.edit_text.getText();
    }

    public float getTextSize() {
        return this.edit_text.getTextSize();
    }

    void initViews() {
        LayoutInflater layoutInflater;
        this.inflater = layoutInflater = (LayoutInflater)this.getContext().getSystemService("layout_inflater");
        layoutInflater.inflate(R.layout.clearable_edittext, (ViewGroup)this, true);
        this.edit_text = (EditText)this.findViewById(R.id.clearable_edit);
        layoutInflater = (Button)this.findViewById(R.id.clearable_button_clear);
        this.btn_clear = layoutInflater;
        layoutInflater.setVisibility(4);
        this.clearText();
        this.showHideClearButton();
    }

    public void setNumericOnly() {
        this.edit_text.setInputType(2);
    }

    public void setSelectAllOnFocus(boolean bl) {
        this.edit_text.setSelectAllOnFocus(true);
    }

    public void setText(String string2) {
        this.edit_text.setText((CharSequence)string2);
    }

    void showHideClearButton() {
        this.edit_text.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                if (charSequence.length() > 0) {
                    ClearableEditText.this.btn_clear.setVisibility(0);
                    return;
                }
                ClearableEditText.this.btn_clear.setVisibility(4);
            }
        });
    }

}

