package com.syntak.library.ui;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.syntak.library.R;

public class SelectorNumber {
   public SelectorNumber(Context var1, String var2, final int var3, final int var4, final int var5, String var6, String var7, int var8) {
      View var9 = LayoutInflater.from(var1).inflate(R.layout.selector_number, (ViewGroup)null);
      String var10 = var2;
      if (var2 == null) {
         var10 = var1.getResources().getString(R.string.select_quantity);
      }

      var2 = var6;
      if (var6 == null) {
         var2 = var1.getResources().getString(R.string.confirm);
      }

      var6 = var7;
      if (var7 == null) {
         var6 = var1.getResources().getString(R.string.cancel);
      }

      final TextView var11 = (TextView)var9.findViewById(R.id.value);
      ImageView var12 = (ImageView)var9.findViewById(R.id.up);
      ImageView var13 = (ImageView)var9.findViewById(R.id.down);
      var11.setText(String.valueOf(var8));
      var12.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            int var2 = Integer.parseInt(var11.getText().toString());
            int var3 = var4;
            int var4x = var5;
            int var5x = var3;
            if (var2 < var3 - var4x) {
               var5x = var2 + var4x;
            }

            var11.setText(String.valueOf(var5x));
         }
      });
      var13.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            int var2 = Integer.parseInt(var11.getText().toString());
            int var3x = var3;
            int var4 = var5;
            int var5x = var3x;
            if (var2 > var3x + var4) {
               var5x = var2 - var4;
            }

            var11.setText(String.valueOf(var5x));
         }
      });
      (new Builder(var1)).setTitle(var10).setView(var9).setPositiveButton(var2, new android.content.DialogInterface.OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            var2 = Integer.parseInt(var11.getText().toString());
            SelectorNumber.this.OnConfirmed(var2);
         }
      }).setNegativeButton(var6, new android.content.DialogInterface.OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            SelectorNumber.this.OnCancelled();
         }
      }).show();
   }

   public void OnCancelled() {
   }

   public void OnConfirmed(int var1) {
   }
}
