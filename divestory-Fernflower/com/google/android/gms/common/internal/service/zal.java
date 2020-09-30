package com.google.android.gms.common.internal.service;

import android.os.Parcel;
import android.os.RemoteException;

public abstract class zal extends com.google.android.gms.internal.base.zaa implements zai {
   public zal() {
      super("com.google.android.gms.common.internal.service.ICommonCallbacks");
   }

   protected final boolean zaa(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if (var1 == 1) {
         this.zaa(var2.readInt());
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
