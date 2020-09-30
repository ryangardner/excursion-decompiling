package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.BaseImplementation;

final class zzi extends zzj {
   // $FF: synthetic field
   private final PendingIntent zzbz;

   zzi(zze var1, GoogleApiClient var2, PendingIntent var3) {
      super(var2);
      this.zzbz = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zza((PendingIntent)this.zzbz, (BaseImplementation.ResultHolder)this);
   }
}
