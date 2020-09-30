package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.internal.Preconditions;

final class zal implements Runnable {
   // $FF: synthetic field
   final zak zaa;
   private final zam zab;

   zal(zak var1, zam var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   public final void run() {
      if (this.zaa.zaa) {
         ConnectionResult var1 = this.zab.zab();
         if (var1.hasResolution()) {
            this.zaa.mLifecycleFragment.startActivityForResult(GoogleApiActivity.zaa(this.zaa.getActivity(), (PendingIntent)Preconditions.checkNotNull(var1.getResolution()), this.zab.zaa(), false), 1);
         } else if (this.zaa.zac.getErrorResolutionIntent(this.zaa.getActivity(), var1.getErrorCode(), (String)null) != null) {
            this.zaa.zac.zaa(this.zaa.getActivity(), this.zaa.mLifecycleFragment, var1.getErrorCode(), 2, this.zaa);
         } else if (var1.getErrorCode() == 18) {
            Dialog var2 = GoogleApiAvailability.zaa((Activity)this.zaa.getActivity(), (OnCancelListener)this.zaa);
            this.zaa.zac.zaa((Context)this.zaa.getActivity().getApplicationContext(), (zabl)(new zan(this, var2)));
         } else {
            this.zaa.zaa(var1, this.zab.zaa());
         }
      }
   }
}
