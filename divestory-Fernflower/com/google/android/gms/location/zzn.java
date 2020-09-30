package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.internal.location.zzbd;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzn extends RegisterListenerMethod<zzaz, LocationCallback> {
   // $FF: synthetic field
   private final zzbd zzy;
   // $FF: synthetic field
   private final ListenerHolder zzz;

   zzn(FusedLocationProviderClient var1, ListenerHolder var2, zzbd var3, ListenerHolder var4) {
      super(var2);
      this.zzy = var3;
      this.zzz = var4;
   }

   // $FF: synthetic method
   protected final void registerListener(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      zzaz var3 = (zzaz)var1;
      FusedLocationProviderClient.zza var4 = new FusedLocationProviderClient.zza(var2);
      var3.zza((zzbd)this.zzy, (ListenerHolder)this.zzz, (com.google.android.gms.internal.location.zzaj)var4);
   }
}
