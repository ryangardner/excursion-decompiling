package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGmsCallbacks extends IInterface {
   void onPostInitComplete(int var1, IBinder var2, Bundle var3) throws RemoteException;

   void zza(int var1, Bundle var2) throws RemoteException;

   void zza(int var1, IBinder var2, zzc var3) throws RemoteException;

   public abstract static class zza extends com.google.android.gms.internal.common.zza implements IGmsCallbacks {
      public zza() {
         super("com.google.android.gms.common.internal.IGmsCallbacks");
      }

      protected final boolean zza(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         if (var1 != 1) {
            if (var1 != 2) {
               if (var1 != 3) {
                  return false;
               }

               this.zza(var2.readInt(), var2.readStrongBinder(), (zzc)com.google.android.gms.internal.common.zzd.zza(var2, zzc.CREATOR));
            } else {
               this.zza(var2.readInt(), (Bundle)com.google.android.gms.internal.common.zzd.zza(var2, Bundle.CREATOR));
            }
         } else {
            this.onPostInitComplete(var2.readInt(), var2.readStrongBinder(), (Bundle)com.google.android.gms.internal.common.zzd.zza(var2, Bundle.CREATOR));
         }

         var3.writeNoException();
         return true;
      }
   }
}
