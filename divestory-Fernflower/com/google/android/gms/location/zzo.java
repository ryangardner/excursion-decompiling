package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzo extends UnregisterListenerMethod<zzaz, LocationCallback> {
   // $FF: synthetic field
   private final FusedLocationProviderClient zzaa;

   zzo(FusedLocationProviderClient var1, ListenerHolder.ListenerKey var2) {
      super(var2);
      this.zzaa = var1;
   }

   // $FF: synthetic method
   protected final void unregisterListener(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      zzaz var3 = (zzaz)var1;
      com.google.android.gms.internal.location.zzaj var5 = FusedLocationProviderClient.zza(this.zzaa, var2);

      try {
         var3.zzb(this.getListenerKey(), var5);
      } catch (RuntimeException var4) {
         var2.trySetException(var4);
      }
   }
}
