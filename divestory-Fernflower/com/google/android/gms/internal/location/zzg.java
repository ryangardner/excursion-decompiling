package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzg extends zzj {
   // $FF: synthetic field
   private final PendingIntent zzbx;

   zzg(zze var1, GoogleApiClient var2, PendingIntent var3) {
      super(var2);
      this.zzbx = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zzb(this.zzbx);
      this.setResult(Status.RESULT_SUCCESS);
   }
}
