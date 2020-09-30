package com.google.android.gms.common.api.internal;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

final class zaat implements ResultCallback<Status> {
   // $FF: synthetic field
   private final StatusPendingResult zaa;
   // $FF: synthetic field
   private final boolean zab;
   // $FF: synthetic field
   private final GoogleApiClient zac;
   // $FF: synthetic field
   private final zaap zad;

   zaat(zaap var1, StatusPendingResult var2, boolean var3, GoogleApiClient var4) {
      this.zad = var1;
      this.zaa = var2;
      this.zab = var3;
      this.zac = var4;
   }

   // $FF: synthetic method
   public final void onResult(Result var1) {
      Status var2 = (Status)var1;
      Storage.getInstance(zaap.zac(this.zad)).zaa();
      if (var2.isSuccess() && this.zad.isConnected()) {
         this.zad.reconnect();
      }

      this.zaa.setResult(var2);
      if (this.zab) {
         this.zac.disconnect();
      }

   }
}
