package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zzl extends com.google.android.gms.internal.common.zzb implements zzm {
   zzl(IBinder var1) {
      super(var1, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
   }

   public final IObjectWrapper zza(IObjectWrapper var1, String var2, int var3, IObjectWrapper var4) throws RemoteException {
      Parcel var5 = this.a_();
      com.google.android.gms.internal.common.zzd.zza(var5, (IInterface)var1);
      var5.writeString(var2);
      var5.writeInt(var3);
      com.google.android.gms.internal.common.zzd.zza(var5, (IInterface)var4);
      Parcel var6 = this.zza(2, var5);
      IObjectWrapper var7 = IObjectWrapper.Stub.asInterface(var6.readStrongBinder());
      var6.recycle();
      return var7;
   }

   public final IObjectWrapper zzb(IObjectWrapper var1, String var2, int var3, IObjectWrapper var4) throws RemoteException {
      Parcel var5 = this.a_();
      com.google.android.gms.internal.common.zzd.zza(var5, (IInterface)var1);
      var5.writeString(var2);
      var5.writeInt(var3);
      com.google.android.gms.internal.common.zzd.zza(var5, (IInterface)var4);
      Parcel var6 = this.zza(3, var5);
      IObjectWrapper var7 = IObjectWrapper.Stub.asInterface(var6.readStrongBinder());
      var6.recycle();
      return var7;
   }
}
