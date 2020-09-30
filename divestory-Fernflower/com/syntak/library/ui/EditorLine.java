package com.syntak.library.ui;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.syntak.library.R;
import com.syntak.library.StringOp;

public class EditorLine {
   public EditorLine(Context var1, String var2, final String var3, String var4, String var5, String var6) {
      View var7 = LayoutInflater.from(var1).inflate(R.layout.editor_line, (ViewGroup)null);
      String var8 = var2;
      if (var2 == null) {
         var8 = var1.getResources().getString(R.string.enter_text);
      }

      var2 = var4;
      if (var4 == null) {
         var2 = var1.getResources().getString(R.string.confirm);
      }

      var4 = var5;
      if (var5 == null) {
         var4 = var1.getResources().getString(R.string.cancel);
      }

      final EditText var9 = (EditText)var7.findViewById(R.id.edit);
      if (var3 != null && var3.equals(var6)) {
         var9.setText("");
      } else {
         var9.setText(var6);
      }

      (new Builder(var1)).setTitle(var8).setView(var7).setPositiveButton(var2, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            String var3x = var9.getText().toString();
            String var4 = var3x;
            if (StringOp.strlen(var3x) == 0) {
               var4 = var3;
            }

            EditorLine.this.OnConfirmed(var4);
         }
      }).setNegativeButton(var4, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            EditorLine.this.OnCancelled();
         }
      }).show();
   }

   public void OnCancelled() {
   }

   public void OnConfirmed(String var1) {
   }
}
