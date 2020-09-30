package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

final class zzau implements ListenerHolder.Notifier<LocationCallback> {
   // $FF: synthetic field
   private final LocationResult zzdb;

   zzau(zzat var1, LocationResult var2) {
      this.zzdb = var2;
   }

   // $FF: synthetic method
   public final void notifyListener(Object var1) {
      ((LocationCallback)var1).onLocationResult(this.zzdb);
   }

   public final void onNotifyListenerFailed() {
   }
}
