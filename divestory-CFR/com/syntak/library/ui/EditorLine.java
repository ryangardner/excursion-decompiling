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
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.EditText
 */
package com.syntak.library.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.syntak.library.R;
import com.syntak.library.StringOp;

public class EditorLine {
    public EditorLine(Context context, String string2, String string3, String string4, String string5, String string6) {
        View view = LayoutInflater.from((Context)context).inflate(R.layout.editor_line, null);
        String string7 = string2;
        if (string2 == null) {
            string7 = context.getResources().getString(R.string.enter_text);
        }
        string2 = string4;
        if (string4 == null) {
            string2 = context.getResources().getString(R.string.confirm);
        }
        string4 = string5;
        if (string5 == null) {
            string4 = context.getResources().getString(R.string.cancel);
        }
        string5 = (EditText)view.findViewById(R.id.edit);
        if (string3 != null && string3.equals(string6)) {
            string5.setText((CharSequence)"");
        } else {
            string5.setText((CharSequence)string6);
        }
        new AlertDialog.Builder(context).setTitle((CharSequence)string7).setView(view).setPositiveButton((CharSequence)string2, new DialogInterface.OnClickListener((EditText)string5, string3){
            final /* synthetic */ EditText val$edit;
            final /* synthetic */ String val$hint;
            {
                this.val$edit = editText;
                this.val$hint = string2;
            }

            public void onClick(DialogInterface object, int n) {
                String string2 = this.val$edit.getText().toString();
                object = string2;
                if (StringOp.strlen(string2) == 0) {
                    object = this.val$hint;
                }
                EditorLine.this.OnConfirmed((String)object);
            }
        }).setNegativeButton((CharSequence)string4, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                EditorLine.this.OnCancelled();
            }
        }).show();
    }

    public void OnCancelled() {
    }

    public void OnConfirmed(String string2) {
    }

}

