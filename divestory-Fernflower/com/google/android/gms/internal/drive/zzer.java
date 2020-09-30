package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public abstract class zzer extends zzb implements zzeq {
   public zzer() {
      super("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
   }

   protected final boolean dispatchTransaction(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      switch(var1) {
      case 1:
         this.zza((zzfl)com.google.android.gms.internal.drive.zzc.zza(var2, zzfl.CREATOR));
         break;
      case 2:
         this.zza((zzft)com.google.android.gms.internal.drive.zzc.zza(var2, zzft.CREATOR));
         break;
      case 3:
         this.zza((zzfn)com.google.android.gms.internal.drive.zzc.zza(var2, zzfn.CREATOR));
         break;
      case 4:
         this.zza((zzfy)com.google.android.gms.internal.drive.zzc.zza(var2, zzfy.CREATOR));
         break;
      case 5:
         this.zza((zzfh)com.google.android.gms.internal.drive.zzc.zza(var2, zzfh.CREATOR));
         break;
      case 6:
         this.zza((Status)com.google.android.gms.internal.drive.zzc.zza(var2, Status.CREATOR));
         break;
      case 7:
         this.onSuccess();
         break;
      case 8:
         this.zza((zzfv)com.google.android.gms.internal.drive.zzc.zza(var2, zzfv.CREATOR));
         break;
      case 9:
         this.zza((zzgh)com.google.android.gms.internal.drive.zzc.zza(var2, zzgh.CREATOR));
         break;
      case 10:
      case 19:
      default:
         return false;
      case 11:
         this.zza((zzfx)com.google.android.gms.internal.drive.zzc.zza(var2, zzfx.CREATOR), zzip.zzb(var2.readStrongBinder()));
         break;
      case 12:
         this.zza((zzgd)com.google.android.gms.internal.drive.zzc.zza(var2, zzgd.CREATOR));
         break;
      case 13:
         this.zza((zzga)com.google.android.gms.internal.drive.zzc.zza(var2, zzga.CREATOR));
         break;
      case 14:
         this.zza((zzfj)com.google.android.gms.internal.drive.zzc.zza(var2, zzfj.CREATOR));
         break;
      case 15:
         this.zzb(com.google.android.gms.internal.drive.zzc.zza(var2));
         break;
      case 16:
         this.zza((zzfr)com.google.android.gms.internal.drive.zzc.zza(var2, zzfr.CREATOR));
         break;
      case 17:
         this.zza((com.google.android.gms.drive.zza)com.google.android.gms.internal.drive.zzc.zza(var2, com.google.android.gms.drive.zza.CREATOR));
         break;
      case 18:
         this.zza((zzff)com.google.android.gms.internal.drive.zzc.zza(var2, zzff.CREATOR));
         break;
      case 20:
         this.zza((zzem)com.google.android.gms.internal.drive.zzc.zza(var2, zzem.CREATOR));
         break;
      case 21:
         this.zza((zzgz)com.google.android.gms.internal.drive.zzc.zza(var2, zzgz.CREATOR));
         break;
      case 22:
         this.zza((zzgf)com.google.android.gms.internal.drive.zzc.zza(var2, zzgf.CREATOR));
      }

      var3.writeNoException();
      return true;
   }
}
