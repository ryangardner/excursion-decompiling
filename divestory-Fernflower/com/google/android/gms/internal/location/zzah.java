package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.BaseImplementation;

final class zzah extends zzai {
   // $FF: synthetic field
   private final com.google.android.gms.location.zzal zzct;

   zzah(zzaf var1, GoogleApiClient var2, com.google.android.gms.location.zzal var3) {
      super(var2);
      this.zzct = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zza((com.google.android.gms.location.zzal)this.zzct, (BaseImplementation.ResultHolder)this);
   }
}
