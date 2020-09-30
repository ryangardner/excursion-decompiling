package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;

final class zzav implements ListenerHolder.Notifier<LocationCallback> {
   // $FF: synthetic field
   private final LocationAvailability zzdc;

   zzav(zzat var1, LocationAvailability var2) {
      this.zzdc = var2;
   }

   // $FF: synthetic method
   public final void notifyListener(Object var1) {
      ((LocationCallback)var1).onLocationAvailability(this.zzdc);
   }

   public final void onNotifyListenerFailed() {
   }
}
