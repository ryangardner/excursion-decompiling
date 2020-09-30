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
 */
package com.syntak.library.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import com.syntak.library.R;
import java.util.HashMap;

public class ConfirmDialog {
    public static String KEY_CANCEL = "cancel";
    public static String KEY_CONFIRM = "confirm";
    public static String KEY_MESSAGE = "message";
    public static String KEY_TITLE = "title";

    public ConfirmDialog(Context context, String string2) {
        String string3 = context.getResources().getString(R.string.confirm);
        context.getResources().getString(R.string.cancel);
        new AlertDialog.Builder(context).setMessage((CharSequence)string2).setPositiveButton((CharSequence)string3, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                ConfirmDialog.this.OnConfirmed();
            }
        }).show();
    }

    public ConfirmDialog(Context context, String string2, String string3) {
        String string4 = string3;
        if (string3 == null) {
            string4 = context.getResources().getString(R.string.confirm);
        }
        new AlertDialog.Builder(context).setTitle((CharSequence)string2).setPositiveButton((CharSequence)string4, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                ConfirmDialog.this.OnConfirmed();
            }
        }).show();
    }

    public ConfirmDialog(Context context, String string2, String string3, String string4) {
        String string5 = string3;
        if (string3 == null) {
            string5 = context.getResources().getString(R.string.confirm);
        }
        string3 = string4;
        if (string4 == null) {
            string3 = context.getResources().getString(R.string.cancel);
        }
        new AlertDialog.Builder(context).setTitle((CharSequence)string2).setPositiveButton((CharSequence)string5, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                ConfirmDialog.this.OnConfirmed();
            }
        }).setNegativeButton((CharSequence)string3, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                ConfirmDialog.this.OnCancelled();
            }
        }).show();
    }

    public ConfirmDialog(Context context, String string2, String string3, String string4, String string5) {
        String string6 = string4;
        if (string4 == null) {
            string6 = context.getResources().getString(R.string.confirm);
        }
        string4 = string5;
        if (string5 == null) {
            string4 = context.getResources().getString(R.string.cancel);
        }
        new AlertDialog.Builder(context).setTitle((CharSequence)string2).setMessage((CharSequence)string3).setPositiveButton((CharSequence)string6, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                ConfirmDialog.this.OnConfirmed();
            }
        }).setNegativeButton((CharSequence)string4, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                ConfirmDialog.this.OnCancelled();
            }
        }).show();
    }

    public ConfirmDialog(Context context, HashMap<String, String> hashMap) {
        boolean bl = hashMap.containsKey(KEY_TITLE);
        String string2 = null;
        String string3 = bl ? hashMap.get(KEY_TITLE) : null;
        String string4 = hashMap.containsKey(KEY_MESSAGE) ? hashMap.get(KEY_MESSAGE) : null;
        String string5 = hashMap.containsKey(KEY_CONFIRM) ? hashMap.get(KEY_CONFIRM) : null;
        if (hashMap.containsKey(KEY_CANCEL)) {
            string2 = hashMap.get(KEY_CANCEL);
        }
        context = new AlertDialog.Builder(context);
        if (string3 != null) {
            context.setTitle((CharSequence)string3);
        }
        if (string4 != null) {
            context.setMessage((CharSequence)string4);
        }
        if (string5 != null) {
            context.setPositiveButton((CharSequence)string5, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    ConfirmDialog.this.OnConfirmed();
                }
            });
        }
        if (string2 != null) {
            context.setNegativeButton((CharSequence)string2, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    ConfirmDialog.this.OnCancelled();
                }
            });
        }
        context.show();
    }

    public void OnCancelled() {
    }

    public void OnConfirmed() {
    }

}

