package com.syntak.library.ui;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;

public class AlertConfirm {
   public AlertConfirm(Context var1, String var2, String var3, Drawable var4, String var5, String var6) {
      OnClickListener var7 = new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            if (var2 != -2) {
               if (var2 == -1) {
                  AlertConfirm.this.positive_clicked();
               }
            } else {
               AlertConfirm.this.negative_clicked();
            }

         }
      };
      (new Builder(var1)).setMessage(var2).setTitle(var3).setIcon(var4).setPositiveButton(var5, var7).setNegativeButton(var6, var7).show();
   }

   public void negative_clicked() {
   }

   public void positive_clicked() {
   }
}
