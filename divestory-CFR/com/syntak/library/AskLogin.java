/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 */
package com.syntak.library;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.syntak.library.R;

public class AskLogin {
    public AskLogin(Context context, String string2) {
        this.showAskDialog(context, string2);
    }

    public void OnLaterClicked() {
    }

    public void OnLoginClicked() {
    }

    public void OnRegisterClicked() {
    }

    public void showAskDialog(Context context, String string2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(context.getString(R.string.login_now));
        builder.setTitle((CharSequence)stringBuilder.toString());
        builder.setMessage(R.string.desc_login_now);
        builder.setPositiveButton(R.string.login, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                AskLogin.this.OnLoginClicked();
            }
        });
        builder.setNeutralButton(R.string.ask_me_later, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                AskLogin.this.OnLaterClicked();
            }
        });
        builder.setNegativeButton(R.string.register, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                AskLogin.this.OnRegisterClicked();
            }
        });
        builder.show();
    }

}

