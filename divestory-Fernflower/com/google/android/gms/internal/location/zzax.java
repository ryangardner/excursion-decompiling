package com.google.android.gms.internal.location;

import android.location.Location;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.location.LocationListener;

final class zzax extends com.google.android.gms.location.zzy {
   private final ListenerHolder<LocationListener> zzda;

   zzax(ListenerHolder<LocationListener> var1) {
      this.zzda = var1;
   }

   public final void onLocationChanged(Location var1) {
      synchronized(this){}

      try {
         ListenerHolder var2 = this.zzda;
         zzay var3 = new zzay(this, var1);
         var2.notifyListener(var3);
      } finally {
         ;
      }

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
