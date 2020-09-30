package com.google.android.gms.common.internal.service;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zaf extends zag {
   zaf(zac var1, GoogleApiClient var2) {
      super(var2);
   }

   // $FF: synthetic method
   protected final void doExecute(Api.AnyClient var1) throws RemoteException {
      ((zak)((zaj)var1).getService()).zaa(new zae(this));
   }
}
