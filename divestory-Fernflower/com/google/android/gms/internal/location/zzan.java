package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class zzan extends zzb implements zzam {
   public zzan() {
      super("com.google.android.gms.location.internal.IGeofencerCallbacks");
   }

   protected final boolean dispatchTransaction(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 3) {
               return false;
            }

            this.zza(var2.readInt(), (PendingIntent)com.google.android.gms.internal.location.zzc.zza(var2, PendingIntent.CREATOR));
         } else {
            this.zzb(var2.readInt(), var2.createStringArray());
         }
      } else {
         this.zza(var2.readInt(), var2.createStringArray());
      }

      return true;
   }
}
