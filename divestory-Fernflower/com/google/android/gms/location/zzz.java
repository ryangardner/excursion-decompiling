package com.google.android.gms.location;

import android.location.Location;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzz extends com.google.android.gms.internal.location.zza implements zzx {
   zzz(IBinder var1) {
      super(var1, "com.google.android.gms.location.ILocationListener");
   }

   public final void onLocationChanged(Location var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      com.google.android.gms.internal.location.zzc.zza(var2, (Parcelable)var1);
      this.transactOneway(1, var2);
   }
}
