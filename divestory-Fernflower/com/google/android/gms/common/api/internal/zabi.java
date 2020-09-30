package com.google.android.gms.common.api.internal;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.IAccountAccessor;

final class zabi implements Runnable {
   // $FF: synthetic field
   private final ConnectionResult zaa;
   // $FF: synthetic field
   private final GoogleApiManager.zac zab;

   zabi(GoogleApiManager.zac var1, ConnectionResult var2) {
      this.zab = var1;
      this.zaa = var2;
   }

   public final void run() {
      GoogleApiManager.zaa var1 = (GoogleApiManager.zaa)GoogleApiManager.zak(this.zab.zaa).get(GoogleApiManager.zac.zaa(this.zab));
      if (var1 != null) {
         if (this.zaa.isSuccess()) {
            GoogleApiManager.zac.zaa(this.zab, true);
            if (GoogleApiManager.zac.zab(this.zab).requiresSignIn()) {
               GoogleApiManager.zac.zac(this.zab);
            } else {
               try {
                  GoogleApiManager.zac.zab(this.zab).getRemoteService((IAccountAccessor)null, GoogleApiManager.zac.zab(this.zab).getScopesForConnectionlessNonSignIn());
               } catch (SecurityException var3) {
                  Log.e("GoogleApiManager", "Failed to get service from broker. ", var3);
                  GoogleApiManager.zac.zab(this.zab).disconnect("Failed to get service from broker.");
                  var1.onConnectionFailed(new ConnectionResult(10));
               }
            }
         } else {
            var1.onConnectionFailed(this.zaa);
         }
      }
   }
}
