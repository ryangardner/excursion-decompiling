package com.google.android.gms.internal.drive;

import android.content.IntentSender;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzep extends zza implements zzeo {
   zzep(IBinder var1) {
      super(var1, "com.google.android.gms.drive.internal.IDriveService");
   }

   public final IntentSender zza(zzgm var1) throws RemoteException {
      Parcel var2 = this.zza();
      zzc.zza(var2, (Parcelable)var1);
      var2 = this.zza(10, var2);
      IntentSender var3 = (IntentSender)zzc.zza(var2, IntentSender.CREATOR);
      var2.recycle();
      return var3;
   }

   public final IntentSender zza(zzu var1) throws RemoteException {
      Parcel var2 = this.zza();
      zzc.zza(var2, (Parcelable)var1);
      var2 = this.zza(11, var2);
      IntentSender var3 = (IntentSender)zzc.zza(var2, IntentSender.CREATOR);
      var2.recycle();
      return var3;
   }

   public final zzec zza(zzgj var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      Parcel var4 = this.zza(7, var3);
      zzec var5 = (zzec)zzc.zza(var4, zzec.CREATOR);
      var4.recycle();
      return var5;
   }

   public final void zza(zzab var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(24, var3);
   }

   public final void zza(zzad var1) throws RemoteException {
      Parcel var2 = this.zza();
      zzc.zza(var2, (Parcelable)var1);
      this.zzb(16, var2);
   }

   public final void zza(zzek var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(1, var3);
   }

   public final void zza(zzeq var1) throws RemoteException {
      Parcel var2 = this.zza();
      zzc.zza(var2, (IInterface)var1);
      this.zzb(9, var2);
   }

   public final void zza(zzex var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(13, var3);
   }

   public final void zza(zzgq var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(2, var3);
   }

   public final void zza(zzgs var1, zzes var2, String var3, zzeq var4) throws RemoteException {
      Parcel var5 = this.zza();
      zzc.zza(var5, (Parcelable)var1);
      zzc.zza(var5, (IInterface)var2);
      var5.writeString((String)null);
      zzc.zza(var5, (IInterface)var4);
      this.zzb(15, var5);
   }

   public final void zza(zzgu var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(36, var3);
   }

   public final void zza(zzgw var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(28, var3);
   }

   public final void zza(zzhb var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(17, var3);
   }

   public final void zza(zzhd var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(38, var3);
   }

   public final void zza(zzhf var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(3, var3);
   }

   public final void zza(zzj var1, zzes var2, String var3, zzeq var4) throws RemoteException {
      Parcel var5 = this.zza();
      zzc.zza(var5, (Parcelable)var1);
      zzc.zza(var5, (IInterface)var2);
      var5.writeString((String)null);
      zzc.zza(var5, (IInterface)var4);
      this.zzb(14, var5);
   }

   public final void zza(zzm var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(18, var3);
   }

   public final void zza(zzo var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(8, var3);
   }

   public final void zza(zzr var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(4, var3);
   }

   public final void zza(zzw var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(5, var3);
   }

   public final void zza(zzy var1, zzeq var2) throws RemoteException {
      Parcel var3 = this.zza();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.zzb(6, var3);
   }

   public final void zzb(zzeq var1) throws RemoteException {
      Parcel var2 = this.zza();
      zzc.zza(var2, (IInterface)var1);
      this.zzb(35, var2);
   }
}
