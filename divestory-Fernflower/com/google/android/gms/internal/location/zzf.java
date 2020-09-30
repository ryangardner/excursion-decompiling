package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzf extends zzj {
   // $FF: synthetic field
   private final long zzbw;
   // $FF: synthetic field
   private final PendingIntent zzbx;

   zzf(zze var1, GoogleApiClient var2, long var3, PendingIntent var5) {
      super(var2);
      this.zzbw = var3;
      this.zzbx = var5;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zza(this.zzbw, this.zzbx);
      this.setResult(Status.RESULT_SUCCESS);
   }
}
