package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.RemoteException;

public abstract class zzak extends zzb implements zzaj {
   public zzak() {
      super("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
   }

   protected final boolean dispatchTransaction(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if (var1 == 1) {
         this.zza((zzad)com.google.android.gms.internal.location.zzc.zza(var2, zzad.CREATOR));
         return true;
      } else {
         return false;
      }
   }
}
