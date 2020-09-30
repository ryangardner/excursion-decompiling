package com.google.android.gms.common.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;

final class zai implements BaseGmsClient.BaseOnConnectionFailedListener {
   // $FF: synthetic field
   private final OnConnectionFailedListener zaa;

   zai(OnConnectionFailedListener var1) {
      this.zaa = var1;
   }

   public final void onConnectionFailed(ConnectionResult var1) {
      this.zaa.onConnectionFailed(var1);
   }
}
