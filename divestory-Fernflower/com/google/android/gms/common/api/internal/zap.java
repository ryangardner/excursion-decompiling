package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;

public final class zap implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
   public final Api<?> zaa;
   private final boolean zab;
   private zar zac;

   public zap(Api<?> var1, boolean var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   private final zar zaa() {
      Preconditions.checkNotNull(this.zac, "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
      return this.zac;
   }

   public final void onConnected(Bundle var1) {
      this.zaa().onConnected(var1);
   }

   public final void onConnectionFailed(ConnectionResult var1) {
      this.zaa().zaa(var1, this.zaa, this.zab);
   }

   public final void onConnectionSuspended(int var1) {
      this.zaa().onConnectionSuspended(var1);
   }

   public final void zaa(zar var1) {
      this.zac = var1;
   }
}
