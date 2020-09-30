package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;

final class zaar implements GoogleApiClient.ConnectionCallbacks {
   // $FF: synthetic field
   private final AtomicReference zaa;
   // $FF: synthetic field
   private final StatusPendingResult zab;
   // $FF: synthetic field
   private final zaap zac;

   zaar(zaap var1, AtomicReference var2, StatusPendingResult var3) {
      this.zac = var1;
      this.zaa = var2;
      this.zab = var3;
   }

   public final void onConnected(Bundle var1) {
      zaap.zaa(this.zac, (GoogleApiClient)Preconditions.checkNotNull((GoogleApiClient)this.zaa.get()), this.zab, true);
   }

   public final void onConnectionSuspended(int var1) {
   }
}
