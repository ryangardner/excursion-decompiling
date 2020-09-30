package com.syntak.library.ui;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

   public PickerNumber(Context var1, String var2, int var3, int var4, int var5, String var6, String var7, int var8) {
      byte var9 = 0;
      this.flag_string_array = false;
      this.flag_string_array = false;
      this.context = var1;
      LayoutInflater var10 = LayoutInflater.from(var1);
      this.inflater = var10;
      this.input = var10.inflate(R.layout.picker_number, (ViewGroup)null);
      this.title = var2;
      this.confirm = var6;
      this.cancel = var7;
      if (var2 == null) {
         this.title = var1.getResources().getString(R.string.select_quantity);
      }

      if (var6 == null) {
         this.confirm = var1.getResources().getString(R.string.confirm);
      }

      if (var7 == null) {
         this.cancel = var1.getResources().getString(R.string.cancel);
      }

      this.picker = (NumberPicker)this.input.findViewById(R.id.picker);
      int var11 = var3 / var5 * var5;
      int var12 = var11;
      if (var3 > 0) {
         var12 = var11;
         if (var11 == 0) {
            var12 = var5;
         }
      }

      var11 = var4 / var5 * var5;
      var3 = var11;
      if (var11 < var4) {
         var3 = var11 + var5;
      }

      this.displayed_values = new String[(var3 - var12) / var5 + 1];
      this.picker.setMaxValue(var3 / var5);
      this.picker.setMinValue(var12 / var5);
      this.picker.setValue(var8 / var5);
      var3 = var9;

      while(true) {
         String[] var13 = this.displayed_values;
         if (var3 >= var13.length) {
            this.picker.setDisplayedValues(var13);
            this.start();
            return;
         }

         var13[var3] = String.valueOf(var3 * var5 + var12);
         ++var3;
      }
   }

   public PickerNumber(Context var1, String var2, String[] var3, String var4, String var5, int var6) {
      this.flag_string_array = false;
      this.flag_string_array = true;
      this.context = var1;
      LayoutInflater var7 = LayoutInflater.from(var1);
      this.inflater = var7;
      this.input = var7.inflate(R.layout.picker_number, (ViewGroup)null);
      this.title = var2;
      this.confirm = var4;
      this.cancel = var5;
      if (var2 == null) {
         this.title = var1.getResources().getString(R.string.select_quantity);
      }

      if (var4 == null) {
         this.confirm = var1.getResources().getString(R.string.confirm);
      }

      if (var5 == null) {
         this.cancel = var1.getResources().getString(R.string.cancel);
      }

      NumberPicker var8 = (NumberPicker)this.input.findViewById(R.id.picker);
      this.picker = var8;
      this.displayed_values = var3;
      var8.setMaxValue(var3.length - 1);
      this.picker.setMinValue(0);
      this.picker.setValue(var6);
      this.picker.setDisplayedValues(this.displayed_values);
      this.start();
   }

   private void start() {
      (new Builder(this.context)).setTitle(this.title).setView(this.input).setPositiveButton(this.confirm, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            var2 = PickerNumber.this.picker.getValue();
            if (PickerNumber.this.flag_string_array) {
               PickerNumber.this.OnConfirmed(var2);
            } else {
               PickerNumber var3 = PickerNumber.this;
               var3.OnConfirmed(Integer.parseInt(var3.displayed_values[var2]));
            }

         }
      }).setNegativeButton(this.cancel, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            PickerNumber.this.OnCancelled();
         }
      }).show();
   }

   public void OnCancelled() {
   }

   public void OnConfirmed(int var1) {
   }
}
