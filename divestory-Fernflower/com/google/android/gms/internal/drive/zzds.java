package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.List;

final class zzds extends zzav {
   // $FF: synthetic field
   private final List zzgb;
   // $FF: synthetic field
   private final zzdp zzgq;

   zzds(zzdp var1, GoogleApiClient var2, List var3) {
      super(var2);
      this.zzgq = var1;
      this.zzgb = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzeo)((zzaw)var1).getService()).zza((zzgw)(new zzgw(this.zzgq.zzk, this.zzgb)), new zzgy(this));
   }
}
