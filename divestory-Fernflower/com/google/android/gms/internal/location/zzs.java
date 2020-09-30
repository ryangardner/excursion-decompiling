package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.location.LocationCallback;

final class zzs extends zzab {
   // $FF: synthetic field
   private final LocationCallback zzcm;

   zzs(zzq var1, GoogleApiClient var2, LocationCallback var3) {
      super(var2);
      this.zzcm = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zzb(ListenerHolders.createListenerKey(this.zzcm, LocationCallback.class.getSimpleName()), new zzac(this));
   }
}
