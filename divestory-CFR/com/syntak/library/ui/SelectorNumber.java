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
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.syntak.library.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.syntak.library.R;

public class SelectorNumber {
    public SelectorNumber(Context context, String string2, final int n, final int n2, final int n3, String string3, String string4, int n4) {
        View view = LayoutInflater.from((Context)context).inflate(R.layout.selector_number, null);
        String string5 = string2;
        if (string2 == null) {
            string5 = context.getResources().getString(R.string.select_quantity);
        }
        string2 = string3;
        if (string3 == null) {
            string2 = context.getResources().getString(R.string.confirm);
        }
        string3 = string4;
        if (string4 == null) {
            string3 = context.getResources().getString(R.string.cancel);
        }
        final TextView textView = (TextView)view.findViewById(R.id.value);
        ImageView imageView = (ImageView)view.findViewById(R.id.up);
        string4 = (ImageView)view.findViewById(R.id.down);
        textView.setText((CharSequence)String.valueOf(n4));
        imageView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                int n = Integer.parseInt(textView.getText().toString());
                int n22 = n2;
                int n32 = n3;
                int n4 = n22;
                if (n < n22 - n32) {
                    n4 = n + n32;
                }
                textView.setText((CharSequence)String.valueOf(n4));
            }
        });
        string4.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                int n5 = Integer.parseInt(textView.getText().toString());
                int n2 = n;
                int n32 = n3;
                int n4 = n2;
                if (n5 > n2 + n32) {
                    n4 = n5 - n32;
                }
                textView.setText((CharSequence)String.valueOf(n4));
            }
        });
        new AlertDialog.Builder(context).setTitle((CharSequence)string5).setView(view).setPositiveButton((CharSequence)string2, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                n = Integer.parseInt(textView.getText().toString());
                SelectorNumber.this.OnConfirmed(n);
            }
        }).setNegativeButton((CharSequence)string3, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                SelectorNumber.this.OnCancelled();
            }
        }).show();
    }

    public void OnCancelled() {
    }

    public void OnConfirmed(int n) {
    }

}

