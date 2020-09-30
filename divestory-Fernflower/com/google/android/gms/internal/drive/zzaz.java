package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzaz extends zzav {
   // $FF: synthetic field
   private final zzj zzek;

   zzaz(zzaw var1, GoogleApiClient var2, zzj var3) {
      super(var2);
      this.zzek = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzj)this.zzek, (zzes)null, (String)null, new zzgy(this));
   }
}
