package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzcd extends zzav {
   // $FF: synthetic field
   private final zzei zzfl;

   zzcd(zzcb var1, GoogleApiClient var2, zzei var3) {
      super(var2);
      this.zzfl = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgu)(new zzgu(this.zzfl)), new zzgy(this));
   }
}
