package com.google.android.gms.internal.location;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzu extends zzab {
   // $FF: synthetic field
   private final Location zzco;

   zzu(zzq var1, GoogleApiClient var2, Location var3) {
      super(var2);
      this.zzco = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zza(this.zzco);
      this.setResult(Status.RESULT_SUCCESS);
   }
}
