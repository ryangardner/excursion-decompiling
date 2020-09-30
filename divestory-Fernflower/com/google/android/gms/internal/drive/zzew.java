package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public final class zzew extends zza implements zzeu {
   zzew(IBinder var1) {
      super(var1, "com.google.android.gms.drive.internal.IEventReleaseCallback");
   }

   public final void zza(boolean var1) throws RemoteException {
      Parcel var2 = this.zza();
      zzc.writeBoolean(var2, var1);
      this.zzc(1, var2);
   }
}
