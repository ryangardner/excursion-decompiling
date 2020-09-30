package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzp extends com.google.android.gms.internal.common.zzb implements IGmsCallbacks {
   zzp(IBinder var1) {
      super(var1, "com.google.android.gms.common.internal.IGmsCallbacks");
   }

   public final void onPostInitComplete(int var1, IBinder var2, Bundle var3) throws RemoteException {
      Parcel var4 = this.a_();
      var4.writeInt(var1);
      var4.writeStrongBinder(var2);
      com.google.android.gms.internal.common.zzd.zza(var4, (Parcelable)var3);
      this.zzb(1, var4);
   }

   public final void zza(int var1, Bundle var2) throws RemoteException {
      Parcel var3 = this.a_();
      var3.writeInt(var1);
      com.google.android.gms.internal.common.zzd.zza(var3, (Parcelable)var2);
      this.zzb(2, var3);
   }

   public final void zza(int var1, IBinder var2, zzc var3) throws RemoteException {
      Parcel var4 = this.a_();
      var4.writeInt(var1);
      var4.writeStrongBinder(var2);
      com.google.android.gms.internal.common.zzd.zza(var4, (Parcelable)var3);
      this.zzb(3, var4);
   }
}
