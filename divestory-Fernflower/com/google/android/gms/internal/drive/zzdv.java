package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzdv extends zzav {
   // $FF: synthetic field
   private final zzdp zzgq;

   zzdv(zzdp var1, GoogleApiClient var2) {
      super(var2);
      this.zzgq = var1;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzhb)(new zzhb(this.zzgq.zzk)), new zzgy(this));
   }
}
