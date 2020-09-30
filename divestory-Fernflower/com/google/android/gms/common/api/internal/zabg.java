package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;

final class zabg implements Runnable {
   // $FF: synthetic field
   private final ConnectionResult zaa;
   // $FF: synthetic field
   private final GoogleApiManager.zaa zab;

   zabg(GoogleApiManager.zaa var1, ConnectionResult var2) {
      this.zab = var1;
      this.zaa = var2;
   }

   public final void run() {
      this.zab.onConnectionFailed(this.zaa);
   }
}
