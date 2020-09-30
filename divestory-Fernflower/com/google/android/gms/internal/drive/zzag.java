package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.query.Query;

final class zzag extends zzar {
   // $FF: synthetic field
   private final Query zzdu;

   zzag(zzaf var1, GoogleApiClient var2, Query var3) {
      super(var2);
      this.zzdu = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgq)(new zzgq(this.zzdu)), new zzas(this));
   }
}
