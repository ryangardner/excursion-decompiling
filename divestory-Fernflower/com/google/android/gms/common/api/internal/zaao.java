package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;

final class zaao implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
   // $FF: synthetic field
   private final zaad zaa;

   private zaao(zaad var1) {
      this.zaa = var1;
   }

   // $FF: synthetic method
   zaao(zaad var1, zaag var2) {
      this(var1);
   }

   public final void onConnected(Bundle var1) {
      ClientSettings var2 = (ClientSettings)Preconditions.checkNotNull(zaad.zai(this.zaa));
      ((com.google.android.gms.signin.zad)Preconditions.checkNotNull(zaad.zaf(this.zaa))).zaa(new zaam(this.zaa));
   }

   public final void onConnectionFailed(ConnectionResult var1) {
      zaad.zac(this.zaa).lock();

      try {
         if (zaad.zab(this.zaa, var1)) {
            zaad.zaj(this.zaa);
            zaad.zak(this.zaa);
         } else {
            zaad.zaa(this.zaa, var1);
         }
      } finally {
         zaad.zac(this.zaa).unlock();
      }

   }

   public final void onConnectionSuspended(int var1) {
   }
}
