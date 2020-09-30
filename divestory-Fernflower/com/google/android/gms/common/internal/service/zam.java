package com.google.android.gms.common.internal.service;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public final class zam extends com.google.android.gms.internal.base.zab implements zak {
   zam(IBinder var1) {
      super(var1, "com.google.android.gms.common.internal.service.ICommonService");
   }

   public final void zaa(zai var1) throws RemoteException {
      Parcel var2 = this.zaa();
      com.google.android.gms.internal.base.zad.zaa(var2, (IInterface)var1);
      this.zac(1, var2);
   }
}
