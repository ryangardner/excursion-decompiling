/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.net.Uri
 */
package com.syntak.library;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import com.syntak.library.R;

public class RateMe {
    private static final String KEY_FIRST_LAUNCHED = "first_launched";
    private static final String KEY_LAUNCHED_TIMES = "launched_times";
    private static final String KEY_NEVER_SHOW = "never_show";

    public static void showRateDialog(final Context context, final SharedPreferences sharedPreferences, String string2, final String string3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getString(R.string.please_rate));
        stringBuilder.append(" ");
        stringBuilder.append(string2);
        builder.setTitle((CharSequence)stringBuilder.toString());
        builder.setMessage(R.string.desc_please_rate);
        builder.setPositiveButton(R.string.go_rating, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                Context context2 = context;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("market://details?id=");
                stringBuilder.append(string3);
                context2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)stringBuilder.toString())));
                dialogInterface.dismiss();
            }
        });
        builder.setNeutralButton(R.string.remind_me_later, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                RateMe.writePref(sharedPreferences, 0, System.currentTimeMillis());
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no_thanks, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                RateMe.writePref(sharedPreferences, true);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void start(Context context, String string2, String string3, long l, int n) {
        long l2;
        SharedPreferences sharedPreferences = context.getSharedPreferences("rateme", 0);
        if (sharedPreferences.getBoolean(KEY_NEVER_SHOW, false)) {
            return;
        }
        int n2 = sharedPreferences.getInt(KEY_LAUNCHED_TIMES, 0) + 1;
        long l3 = l2 = sharedPreferences.getLong(KEY_FIRST_LAUNCHED, 0L);
        if (l2 == 0L) {
            l3 = System.currentTimeMillis();
        }
        RateMe.writePref(sharedPreferences, n2, l3);
        if (n2 < n) return;
        if (System.currentTimeMillis() < l3 + l * 24L * 60L * 60L * 1000L) return;
        RateMe.showRateDialog(context, sharedPreferences, string2, string3);
    }

    private static void writePref(SharedPreferences sharedPreferences, int n, long l) {
        sharedPreferences.edit().putInt(KEY_LAUNCHED_TIMES, n).apply();
        if (l <= 0L) return;
        sharedPreferences.edit().putLong(KEY_FIRST_LAUNCHED, l).apply();
    }

    private static void writePref(SharedPreferences sharedPreferences, boolean bl) {
        sharedPreferences.edit().putBoolean(KEY_NEVER_SHOW, bl).apply();
    }

}

