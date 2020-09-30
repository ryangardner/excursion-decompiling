package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.location.ActivityTransitionRequest;

final class zzh extends zzj {
   // $FF: synthetic field
   private final ActivityTransitionRequest zzby;
   // $FF: synthetic field
   private final PendingIntent zzbz;

   zzh(zze var1, GoogleApiClient var2, ActivityTransitionRequest var3, PendingIntent var4) {
      super(var2);
      this.zzby = var3;
      this.zzbz = var4;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zza((ActivityTransitionRequest)this.zzby, (PendingIntent)this.zzbz, (BaseImplementation.ResultHolder)this);
   }
}
