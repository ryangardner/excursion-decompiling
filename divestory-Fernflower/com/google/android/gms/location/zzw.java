package com.google.android.gms.location;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzw extends com.google.android.gms.internal.location.zza implements zzu {
   zzw(IBinder var1) {
      super(var1, "com.google.android.gms.location.ILocationCallback");
   }

   public final void onLocationAvailability(LocationAvailability var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      com.google.android.gms.internal.location.zzc.zza(var2, (Parcelable)var1);
      this.transactOneway(2, var2);
   }

   public final void onLocationResult(LocationResult var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      com.google.android.gms.internal.location.zzc.zza(var2, (Parcelable)var1);
      this.transactOneway(1, var2);
   }
}
