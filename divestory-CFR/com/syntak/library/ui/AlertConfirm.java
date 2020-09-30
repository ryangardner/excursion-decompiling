/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.graphics.drawable.Drawable
 */
package com.syntak.library.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

public class AlertConfirm {
    public AlertConfirm(Context context, String string2, String string3, Drawable drawable2, String string4, String string5) {
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                if (n == -2) {
                    AlertConfirm.this.negative_clicked();
                    return;
                }
                if (n != -1) {
                    return;
                }
                AlertConfirm.this.positive_clicked();
            }
        };
        new AlertDialog.Builder(context).setMessage((CharSequence)string2).setTitle((CharSequence)string3).setIcon(drawable2).setPositiveButton((CharSequence)string4, onClickListener).setNegativeButton((CharSequence)string5, onClickListener).show();
    }

    public void negative_clicked() {
    }

    public void positive_clicked() {
    }

}

