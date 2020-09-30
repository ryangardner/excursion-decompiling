package com.syntak.library.ui;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.syntak.library.R;
import java.util.HashMap;

public class ConfirmDialog {
   public static String KEY_CANCEL;
   public static String KEY_CONFIRM;
   public static String KEY_MESSAGE;
   public static String KEY_TITLE;

   public ConfirmDialog(Context var1, String var2) {
      String var3 = var1.getResources().getString(R.string.confirm);
      var1.getResources().getString(R.string.cancel);
      (new Builder(var1)).setMessage(var2).setPositiveButton(var3, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            ConfirmDialog.this.OnConfirmed();
         }
      }).show();
   }

   public ConfirmDialog(Context var1, String var2, String var3) {
      String var4 = var3;
      if (var3 == null) {
         var4 = var1.getResources().getString(R.string.confirm);
      }

      (new Builder(var1)).setTitle(var2).setPositiveButton(var4, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            ConfirmDialog.this.OnConfirmed();
         }
      }).show();
   }

   public ConfirmDialog(Context var1, String var2, String var3, String var4) {
      String var5 = var3;
      if (var3 == null) {
         var5 = var1.getResources().getString(R.string.confirm);
      }

      var3 = var4;
      if (var4 == null) {
         var3 = var1.getResources().getString(R.string.cancel);
      }

      (new Builder(var1)).setTitle(var2).setPositiveButton(var5, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            ConfirmDialog.this.OnConfirmed();
         }
      }).setNegativeButton(var3, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            ConfirmDialog.this.OnCancelled();
         }
      }).show();
   }

   public ConfirmDialog(Context var1, String var2, String var3, String var4, String var5) {
      String var6 = var4;
      if (var4 == null) {
         var6 = var1.getResources().getString(R.string.confirm);
      }

      var4 = var5;
      if (var5 == null) {
         var4 = var1.getResources().getString(R.string.cancel);
      }

      (new Builder(var1)).setTitle(var2).setMessage(var3).setPositiveButton(var6, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            ConfirmDialog.this.OnConfirmed();
         }
      }).setNegativeButton(var4, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            ConfirmDialog.this.OnCancelled();
         }
      }).show();
   }

   public ConfirmDialog(Context var1, HashMap<String, String> var2) {
      boolean var3 = var2.containsKey(KEY_TITLE);
      String var4 = null;
      String var5;
      if (var3) {
         var5 = (String)var2.get(KEY_TITLE);
      } else {
         var5 = null;
      }

      String var6;
      if (var2.containsKey(KEY_MESSAGE)) {
         var6 = (String)var2.get(KEY_MESSAGE);
      } else {
         var6 = null;
      }

      String var7;
      if (var2.containsKey(KEY_CONFIRM)) {
         var7 = (String)var2.get(KEY_CONFIRM);
      } else {
         var7 = null;
      }

      if (var2.containsKey(KEY_CANCEL)) {
         var4 = (String)var2.get(KEY_CANCEL);
      }

      Builder var8 = new Builder(var1);
      if (var5 != null) {
         var8.setTitle(var5);
      }

      if (var6 != null) {
         var8.setMessage(var6);
      }

      if (var7 != null) {
         var8.setPositiveButton(var7, new OnClickListener() {
            public void onClick(DialogInterface var1, int var2) {
               ConfirmDialog.this.OnConfirmed();
            }
         });
      }

      if (var4 != null) {
         var8.setNegativeButton(var4, new OnClickListener() {
            public void onClick(DialogInterface var1, int var2) {
               ConfirmDialog.this.OnCancelled();
            }
         });
      }

      var8.show();
   }

   public void OnCancelled() {
   }

   public void OnConfirmed() {
   }
}
