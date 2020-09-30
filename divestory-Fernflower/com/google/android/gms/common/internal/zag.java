package com.google.android.gms.common.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;

final class zag implements BaseGmsClient.BaseConnectionCallbacks {
   // $FF: synthetic field
   private final ConnectionCallbacks zaa;

   zag(ConnectionCallbacks var1) {
      this.zaa = var1;
   }

   public final void onConnected(Bundle var1) {
      this.zaa.onConnected(var1);
   }

   public final void onConnectionSuspended(int var1) {
      this.zaa.onConnectionSuspended(var1);
   }
}
