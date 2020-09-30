package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

final class zzy extends zzab {
   // $FF: synthetic field
   private final PendingIntent zzbx;
   // $FF: synthetic field
   private final LocationRequest zzck;

   zzy(zzq var1, GoogleApiClient var2, LocationRequest var3, PendingIntent var4) {
      super(var2);
      this.zzck = var3;
      this.zzbx = var4;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      zzaz var3 = (zzaz)var1;
      zzac var2 = new zzac(this);
      var3.zza((LocationRequest)this.zzck, (PendingIntent)this.zzbx, (zzaj)var2);
   }
}
