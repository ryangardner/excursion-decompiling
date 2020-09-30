package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zal extends com.google.android.gms.internal.base.zab implements zam {
   zal(IBinder var1) {
      super(var1, "com.google.android.gms.common.internal.ISignInButtonCreator");
   }

   public final IObjectWrapper zaa(IObjectWrapper var1, zau var2) throws RemoteException {
      Parcel var3 = this.zaa();
      com.google.android.gms.internal.base.zad.zaa(var3, (IInterface)var1);
      com.google.android.gms.internal.base.zad.zaa(var3, (Parcelable)var2);
      Parcel var4 = this.zaa(2, var3);
      IObjectWrapper var5 = IObjectWrapper.Stub.asInterface(var4.readStrongBinder());
      var4.recycle();
      return var5;
   }
}
