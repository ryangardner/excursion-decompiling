package com.google.android.gms.internal.location;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzal extends zza implements zzaj {
   zzal(IBinder var1) {
      super(var1, "com.google.android.gms.location.internal.IFusedLocationProviderCallback");
   }

   public final void zza(zzad var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var2, (Parcelable)var1);
      this.transactOneway(1, var2);
   }
}
