package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zzn extends com.google.android.gms.internal.common.zzb implements zzm {
   zzn(IBinder var1) {
      super(var1, "com.google.android.gms.common.internal.ICertData");
   }

   public final IObjectWrapper zzb() throws RemoteException {
      Parcel var1 = this.zza(1, this.a_());
      IObjectWrapper var2 = IObjectWrapper.Stub.asInterface(var1.readStrongBinder());
      var1.recycle();
      return var2;
   }

   public final int zzc() throws RemoteException {
      Parcel var1 = this.zza(2, this.a_());
      int var2 = var1.readInt();
      var1.recycle();
      return var2;
   }
}
