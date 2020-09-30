package com.google.android.gms.internal.location;

import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

final class zzw extends zzab {
   // $FF: synthetic field
   private final LocationRequest zzck;
   // $FF: synthetic field
   private final LocationListener zzcl;
   // $FF: synthetic field
   private final Looper zzcp;

   zzw(zzq var1, GoogleApiClient var2, LocationRequest var3, LocationListener var4, Looper var5) {
      super(var2);
      this.zzck = var3;
      this.zzcl = var4;
      this.zzcp = var5;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      zzaz var3 = (zzaz)var1;
      zzac var2 = new zzac(this);
      var3.zza((LocationRequest)this.zzck, (ListenerHolder)ListenerHolders.createListenerHolder(this.zzcl, zzbm.zza(this.zzcp), LocationListener.class.getSimpleName()), (zzaj)var2);
   }
}
