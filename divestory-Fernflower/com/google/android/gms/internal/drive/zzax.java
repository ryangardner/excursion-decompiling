package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzax extends zzav {
   // $FF: synthetic field
   private final zzj zzek;
   // $FF: synthetic field
   private final zzee zzel;

   zzax(zzaw var1, GoogleApiClient var2, zzj var3, zzee var4) {
      super(var2);
      this.zzek = var3;
      this.zzel = var4;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzj)this.zzek, this.zzel, (String)null, new zzgy(this));
   }
}
