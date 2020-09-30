package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.location.GeofencingRequest;

final class zzag extends zzai {
   // $FF: synthetic field
   private final PendingIntent zzbz;
   // $FF: synthetic field
   private final GeofencingRequest zzcs;

   zzag(zzaf var1, GoogleApiClient var2, GeofencingRequest var3, PendingIntent var4) {
      super(var2);
      this.zzcs = var3;
      this.zzbz = var4;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zza((GeofencingRequest)this.zzcs, (PendingIntent)this.zzbz, (BaseImplementation.ResultHolder)this);
   }
}
