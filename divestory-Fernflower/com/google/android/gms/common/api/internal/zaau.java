package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zaau implements GoogleApiClient.OnConnectionFailedListener {
   // $FF: synthetic field
   private final StatusPendingResult zaa;

   zaau(zaap var1, StatusPendingResult var2) {
      this.zaa = var2;
   }

   public final void onConnectionFailed(ConnectionResult var1) {
      this.zaa.setResult(new Status(8));
   }
}
