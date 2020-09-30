/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.Resources
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.NumberPicker
 */
package com.syntak.library.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import com.syntak.library.R;

public class PickerNumber {
    String cancel;
    String confirm;
    Context context;
    String[] displayed_values = null;
    boolean flag_string_array;
    LayoutInflater inflater;
    final View input;
    NumberPicker picker;
    String title;

    public PickerNumber(Context arrstring, String string2, int n, int n2, int n3, String string3, String string4, int n4) {
        LayoutInflater layoutInflater;
        int n5;
        int n6 = 0;
        this.flag_string_array = false;
        this.flag_string_array = false;
        this.context = arrstring;
        this.inflater = layoutInflater = LayoutInflater.from((Context)arrstring);
        this.input = layoutInflater.inflate(R.layout.picker_number, null);
        this.title = string2;
        this.confirm = string3;
        this.cancel = string4;
        if (string2 == null) {
            this.title = arrstring.getResources().getString(R.string.select_quantity);
        }
        if (string3 == null) {
            this.confirm = arrstring.getResources().getString(R.string.confirm);
        }
        if (string4 == null) {
            this.cancel = arrstring.getResources().getString(R.string.cancel);
        }
        this.picker = (NumberPicker)this.input.findViewById(R.id.picker);
        int n7 = n5 = n / n3 * n3;
        if (n > 0) {
            n7 = n5;
            if (n5 == 0) {
                n7 = n3;
            }
        }
        n = n5 = n2 / n3 * n3;
        if (n5 < n2) {
            n = n5 + n3;
        }
        this.displayed_values = new String[(n - n7) / n3 + 1];
        this.picker.setMaxValue(n / n3);
        this.picker.setMinValue(n7 / n3);
        this.picker.setValue(n4 / n3);
        n = n6;
        do {
            if (n >= (arrstring = this.displayed_values).length) {
                this.picker.setDisplayedValues(arrstring);
                this.start();
                return;
            }
            arrstring[n] = String.valueOf(n * n3 + n7);
            ++n;
        } while (true);
    }

    public PickerNumber(Context context, String string2, String[] arrstring, String string3, String string4, int n) {
        LayoutInflater layoutInflater;
        this.flag_string_array = false;
        this.flag_string_array = true;
        this.context = context;
        this.inflater = layoutInflater = LayoutInflater.from((Context)context);
        this.input = layoutInflater.inflate(R.layout.picker_number, null);
        this.title = string2;
        this.confirm = string3;
        this.cancel = string4;
        if (string2 == null) {
            this.title = context.getResources().getString(R.string.select_quantity);
        }
        if (string3 == null) {
            this.confirm = context.getResources().getString(R.string.confirm);
        }
        if (string4 == null) {
            this.cancel = context.getResources().getString(R.string.cancel);
        }
        context = (NumberPicker)this.input.findViewById(R.id.picker);
        this.picker = context;
        this.displayed_values = arrstring;
        context.setMaxValue(arrstring.length - 1);
        this.picker.setMinValue(0);
        this.picker.setValue(n);
        this.picker.setDisplayedValues(this.displayed_values);
        this.start();
    }

    private void start() {
        new AlertDialog.Builder(this.context).setTitle((CharSequence)this.title).setView(this.input).setPositiveButton((CharSequence)this.confirm, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface object, int n) {
                n = PickerNumber.this.picker.getValue();
                if (PickerNumber.this.flag_string_array) {
                    PickerNumber.this.OnConfirmed(n);
                    return;
                }
                object = PickerNumber.this;
                ((PickerNumber)object).OnConfirmed(Integer.parseInt(((PickerNumber)object).displayed_values[n]));
            }
        }).setNegativeButton((CharSequence)this.cancel, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                PickerNumber.this.OnCancelled();
            }
        }).show();
    }

    public void OnCancelled() {
    }

    public void OnConfirmed(int n) {
    }

}

