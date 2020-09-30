package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzcc extends zzcg {
   // $FF: synthetic field
   private final zzcb zzfk;

   zzcc(zzcb var1, GoogleApiClient var2) {
      super(var1, var2);
      this.zzfk = var1;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zzb(new zzce(this.zzfk, this, (zzcc)null));
   }
}
