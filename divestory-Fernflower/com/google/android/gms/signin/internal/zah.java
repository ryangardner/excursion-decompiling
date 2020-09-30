package com.google.android.gms.signin.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IAccountAccessor;

public final class zah extends com.google.android.gms.internal.base.zab implements zae {
   zah(IBinder var1) {
      super(var1, "com.google.android.gms.signin.internal.ISignInService");
   }

   public final void zaa(int var1) throws RemoteException {
      Parcel var2 = this.zaa();
      var2.writeInt(var1);
      this.zab(7, var2);
   }

   public final void zaa(IAccountAccessor var1, int var2, boolean var3) throws RemoteException {
      Parcel var4 = this.zaa();
      com.google.android.gms.internal.base.zad.zaa(var4, (IInterface)var1);
      var4.writeInt(var2);
      com.google.android.gms.internal.base.zad.zaa(var4, var3);
      this.zab(9, var4);
   }

   public final void zaa(zak var1, zac var2) throws RemoteException {
      Parcel var3 = this.zaa();
      com.google.android.gms.internal.base.zad.zaa(var3, (Parcelable)var1);
      com.google.android.gms.internal.base.zad.zaa(var3, (IInterface)var2);
      this.zab(12, var3);
   }
}
