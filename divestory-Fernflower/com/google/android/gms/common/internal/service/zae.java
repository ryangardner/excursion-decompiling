package com.google.android.gms.common.internal.service;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;

final class zae extends zaa {
   private final BaseImplementation.ResultHolder<Status> zaa;

   public zae(BaseImplementation.ResultHolder<Status> var1) {
      this.zaa = var1;
   }

   public final void zaa(int var1) throws RemoteException {
      this.zaa.setResult(new Status(var1));
   }
}
