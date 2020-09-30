package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.RemoteException;

public abstract class zzet extends zzb implements zzes {
   public zzet() {
      super("com.google.android.gms.drive.internal.IEventCallback");
   }

   protected final boolean dispatchTransaction(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if (var1 == 1) {
         this.zzc((zzfp)com.google.android.gms.internal.drive.zzc.zza(var2, zzfp.CREATOR));
         var3.writeNoException();
         return true;
      } else {
         return false;
      }
   }
}
