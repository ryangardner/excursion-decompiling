package com.google.android.gms.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.common.internal.Preconditions;

public class SupportErrorDialogFragment extends DialogFragment {
   private Dialog zaa;
   private OnCancelListener zab;

   public static SupportErrorDialogFragment newInstance(Dialog var0) {
      return newInstance(var0, (OnCancelListener)null);
   }

   public static SupportErrorDialogFragment newInstance(Dialog var0, OnCancelListener var1) {
      SupportErrorDialogFragment var2 = new SupportErrorDialogFragment();
      var0 = (Dialog)Preconditions.checkNotNull(var0, "Cannot display null dialog");
      var0.setOnCancelListener((OnCancelListener)null);
      var0.setOnDismissListener((OnDismissListener)null);
      var2.zaa = var0;
      if (var1 != null) {
         var2.zab = var1;
      }

      return var2;
   }

   public void onCancel(DialogInterface var1) {
      OnCancelListener var2 = this.zab;
      if (var2 != null) {
         var2.onCancel(var1);
      }

   }

   public Dialog onCreateDialog(Bundle var1) {
      if (this.zaa == null) {
         this.setShowsDialog(false);
      }

      return this.zaa;
   }

   public void show(FragmentManager var1, String var2) {
      super.show(var1, var2);
   }
}
