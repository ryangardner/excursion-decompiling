package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzt extends zzab {
   // $FF: synthetic field
   private final boolean zzcn;

   zzt(zzq var1, GoogleApiClient var2, boolean var3) {
      super(var2);
      this.zzcn = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zza(this.zzcn);
      this.setResult(Status.RESULT_SUCCESS);
   }
}
