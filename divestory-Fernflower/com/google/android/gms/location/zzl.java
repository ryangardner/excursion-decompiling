package com.google.android.gms.location;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzl extends TaskApiCall<zzaz, Location> {
   zzl(FusedLocationProviderClient var1) {
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1, TaskCompletionSource var2) throws RemoteException {
      var2.setResult(((zzaz)var1).getLastLocation());
   }
}
