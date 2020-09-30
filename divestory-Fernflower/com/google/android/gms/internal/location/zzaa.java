package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzaa extends zzab {
   // $FF: synthetic field
   private final PendingIntent zzbx;

   zzaa(zzq var1, GoogleApiClient var2, PendingIntent var3) {
      super(var2);
      this.zzbx = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      zzaz var3 = (zzaz)var1;
      zzac var2 = new zzac(this);
      var3.zza((PendingIntent)this.zzbx, (zzaj)var2);
   }
}
