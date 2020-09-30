package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zzj extends com.google.android.gms.internal.common.zzb implements zzk {
   zzj(IBinder var1) {
      super(var1, "com.google.android.gms.dynamite.IDynamiteLoader");
   }

   public final int zza(IObjectWrapper var1, String var2, boolean var3) throws RemoteException {
      Parcel var4 = this.a_();
      com.google.android.gms.internal.common.zzd.zza(var4, (IInterface)var1);
      var4.writeString(var2);
      com.google.android.gms.internal.common.zzd.zza(var4, var3);
      Parcel var6 = this.zza(3, var4);
      int var5 = var6.readInt();
      var6.recycle();
      return var5;
   }

   public final IObjectWrapper zza(IObjectWrapper var1, String var2, int var3) throws RemoteException {
      Parcel var4 = this.a_();
      com.google.android.gms.internal.common.zzd.zza(var4, (IInterface)var1);
      var4.writeString(var2);
      var4.writeInt(var3);
      Parcel var5 = this.zza(2, var4);
      var1 = IObjectWrapper.Stub.asInterface(var5.readStrongBinder());
      var5.recycle();
      return var1;
   }

   public final IObjectWrapper zza(IObjectWrapper var1, String var2, int var3, IObjectWrapper var4) throws RemoteException {
      Parcel var5 = this.a_();
      com.google.android.gms.internal.common.zzd.zza(var5, (IInterface)var1);
      var5.writeString(var2);
      var5.writeInt(var3);
      com.google.android.gms.internal.common.zzd.zza(var5, (IInterface)var4);
      Parcel var6 = this.zza(8, var5);
      var1 = IObjectWrapper.Stub.asInterface(var6.readStrongBinder());
      var6.recycle();
      return var1;
   }

   public final int zzb() throws RemoteException {
      Parcel var1 = this.zza(6, this.a_());
      int var2 = var1.readInt();
      var1.recycle();
      return var2;
   }

   public final int zzb(IObjectWrapper var1, String var2, boolean var3) throws RemoteException {
      Parcel var4 = this.a_();
      com.google.android.gms.internal.common.zzd.zza(var4, (IInterface)var1);
      var4.writeString(var2);
      com.google.android.gms.internal.common.zzd.zza(var4, var3);
      Parcel var6 = this.zza(5, var4);
      int var5 = var6.readInt();
      var6.recycle();
      return var5;
   }

   public final IObjectWrapper zzb(IObjectWrapper var1, String var2, int var3) throws RemoteException {
      Parcel var4 = this.a_();
      com.google.android.gms.internal.common.zzd.zza(var4, (IInterface)var1);
      var4.writeString(var2);
      var4.writeInt(var3);
      Parcel var5 = this.zza(4, var4);
      IObjectWrapper var6 = IObjectWrapper.Stub.asInterface(var5.readStrongBinder());
      var5.recycle();
      return var6;
   }

   public final IObjectWrapper zzc(IObjectWrapper var1, String var2, boolean var3) throws RemoteException {
      Parcel var4 = this.a_();
      com.google.android.gms.internal.common.zzd.zza(var4, (IInterface)var1);
      var4.writeString(var2);
      com.google.android.gms.internal.common.zzd.zza(var4, var3);
      Parcel var5 = this.zza(7, var4);
      var1 = IObjectWrapper.Stub.asInterface(var5.readStrongBinder());
      var5.recycle();
      return var1;
   }
}
