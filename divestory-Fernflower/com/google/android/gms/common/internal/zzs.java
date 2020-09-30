package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zzs extends com.google.android.gms.internal.common.zzb implements zzr {
   zzs(IBinder var1) {
      super(var1, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
   }

   public final boolean zza(com.google.android.gms.common.zzj var1, IObjectWrapper var2) throws RemoteException {
      Parcel var3 = this.a_();
      com.google.android.gms.internal.common.zzd.zza(var3, (Parcelable)var1);
      com.google.android.gms.internal.common.zzd.zza(var3, (IInterface)var2);
      Parcel var5 = this.zza(5, var3);
      boolean var4 = com.google.android.gms.internal.common.zzd.zza(var5);
      var5.recycle();
      return var4;
   }
}
