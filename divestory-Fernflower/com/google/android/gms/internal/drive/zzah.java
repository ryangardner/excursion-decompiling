package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzah extends zzam {
   // $FF: synthetic field
   private final int zzdv;

   zzah(zzaf var1, GoogleApiClient var2, int var3) {
      this.zzdv = 536870912;
      super(var2);
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzr)(new zzr(this.zzdv)), new zzak(this));
   }
}
