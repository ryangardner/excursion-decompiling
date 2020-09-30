package com.google.android.gms.internal.location;

import android.location.Location;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationListener;

final class zzay implements ListenerHolder.Notifier<LocationListener> {
   // $FF: synthetic field
   private final Location zzdd;

   zzay(zzax var1, Location var2) {
      this.zzdd = var2;
   }

   // $FF: synthetic method
   public final void notifyListener(Object var1) {
      ((LocationListener)var1).onLocationChanged(this.zzdd);
   }

   public final void onNotifyListenerFailed() {
   }
}
