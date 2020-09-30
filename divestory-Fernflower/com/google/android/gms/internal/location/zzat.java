package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

final class zzat extends com.google.android.gms.location.zzv {
   private final ListenerHolder<LocationCallback> zzda;

   zzat(ListenerHolder<LocationCallback> var1) {
      this.zzda = var1;
   }

   public final void onLocationAvailability(LocationAvailability var1) {
      this.zzda.notifyListener(new zzav(this, var1));
   }

   public final void onLocationResult(LocationResult var1) {
      this.zzda.notifyListener(new zzau(this, var1));
   }

   public final void release() {
      synchronized(this){}

      try {
         this.zzda.clear();
      } finally {
         ;
      }

   }
}
