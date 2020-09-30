package com.google.android.gms.internal.location;

import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

final class zzx extends zzab {
   // $FF: synthetic field
   private final LocationRequest zzck;
   // $FF: synthetic field
   private final LocationCallback zzcm;
   // $FF: synthetic field
   private final Looper zzcp;

   zzx(zzq var1, GoogleApiClient var2, LocationRequest var3, LocationCallback var4, Looper var5) {
      super(var2);
      this.zzck = var3;
      this.zzcm = var4;
      this.zzcp = var5;
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      zzaz var2 = (zzaz)var1;
      zzac var3 = new zzac(this);
      var2.zza((zzbd)zzbd.zza(this.zzck), (ListenerHolder)ListenerHolders.createListenerHolder(this.zzcm, zzbm.zza(this.zzcp), LocationCallback.class.getSimpleName()), (zzaj)var3);
   }
}
