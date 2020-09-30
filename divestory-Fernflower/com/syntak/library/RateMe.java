package com.syntak.library;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;

public class RateMe {
   private static final String KEY_FIRST_LAUNCHED = "first_launched";
   private static final String KEY_LAUNCHED_TIMES = "launched_times";
   private static final String KEY_NEVER_SHOW = "never_show";

   public static void showRateDialog(final Context var0, final SharedPreferences var1, String var2, final String var3) {
      Builder var4 = new Builder(var0);
      StringBuilder var5 = new StringBuilder();
      var5.append(var0.getString(R.string.please_rate));
      var5.append(" ");
      var5.append(var2);
      var4.setTitle(var5.toString());
      var4.setMessage(R.string.desc_please_rate);
      var4.setPositiveButton(R.string.go_rating, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            Context var3x = var0;
            StringBuilder var4 = new StringBuilder();
            var4.append("market://details?id=");
            var4.append(var3);
            var3x.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(var4.toString())));
            var1.dismiss();
         }
      });
      var4.setNeutralButton(R.string.remind_me_later, new OnClickListener() {
         public void onClick(DialogInterface var1x, int var2) {
            RateMe.writePref(var1, 0, System.currentTimeMillis());
            var1x.dismiss();
         }
      });
      var4.setNegativeButton(R.string.no_thanks, new OnClickListener() {
         public void onClick(DialogInterface var1x, int var2) {
            RateMe.writePref(var1, true);
            var1x.dismiss();
         }
      });
      var4.show();
   }

   public static void start(Context var0, String var1, String var2, long var3, int var5) {
      SharedPreferences var6 = var0.getSharedPreferences("rateme", 0);
      if (!var6.getBoolean("never_show", false)) {
         int var7 = var6.getInt("launched_times", 0) + 1;
         long var8 = var6.getLong("first_launched", 0L);
         long var10 = var8;
         if (var8 == 0L) {
            var10 = System.currentTimeMillis();
         }

         writePref(var6, var7, var10);
         if (var7 >= var5 && System.currentTimeMillis() >= var10 + var3 * 24L * 60L * 60L * 1000L) {
            showRateDialog(var0, var6, var1, var2);
         }

      }
   }

   private static void writePref(SharedPreferences var0, int var1, long var2) {
      var0.edit().putInt("launched_times", var1).apply();
      if (var2 > 0L) {
         var0.edit().putLong("first_launched", var2).apply();
      }

   }

   private static void writePref(SharedPreferences var0, boolean var1) {
      var0.edit().putBoolean("never_show", var1).apply();
   }
}
