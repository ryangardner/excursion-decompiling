package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.location.LocationListener;

final class zzz extends zzab {
   // $FF: synthetic field
   private final LocationListener zzcl;

   zzz(zzq var1, GoogleApiClient var2, LocationListener var3) {
      super(var2);
      this.zzcl = var3;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zzaz)var1).zza((ListenerHolder.ListenerKey)ListenerHolders.createListenerKey(this.zzcl, LocationListener.class.getSimpleName()), (zzaj)(new zzac(this)));
   }
}
