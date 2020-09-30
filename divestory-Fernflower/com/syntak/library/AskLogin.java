package com.syntak.library;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class AskLogin {
   public AskLogin(Context var1, String var2) {
      this.showAskDialog(var1, var2);
   }

   public void OnLaterClicked() {
   }

   public void OnLoginClicked() {
   }

   public void OnRegisterClicked() {
   }

   public void showAskDialog(Context var1, String var2) {
      Builder var3 = new Builder(var1);
      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append(" ");
      var4.append(var1.getString(R.string.login_now));
      var3.setTitle(var4.toString());
      var3.setMessage(R.string.desc_login_now);
      var3.setPositiveButton(R.string.login, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            AskLogin.this.OnLoginClicked();
         }
      });
      var3.setNeutralButton(R.string.ask_me_later, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            AskLogin.this.OnLaterClicked();
         }
      });
      var3.setNegativeButton(R.string.register, new OnClickListener() {
         public void onClick(DialogInterface var1, int var2) {
            AskLogin.this.OnRegisterClicked();
         }
      });
      var3.show();
   }
}
