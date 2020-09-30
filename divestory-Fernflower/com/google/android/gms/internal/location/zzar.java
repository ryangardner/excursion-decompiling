package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.location.LocationSettingsResult;

public abstract class zzar extends zzb implements zzaq {
   public zzar() {
      super("com.google.android.gms.location.internal.ISettingsCallbacks");
   }

   protected final boolean dispatchTransaction(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if (var1 == 1) {
         this.zza((LocationSettingsResult)com.google.android.gms.internal.location.zzc.zza(var2, LocationSettingsResult.CREATOR));
         return true;
      } else {
         return false;
      }
   }
}
