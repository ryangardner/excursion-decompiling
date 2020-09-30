package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.common.zzb;
import com.google.android.gms.internal.common.zzd;

public interface IFragmentWrapper extends IInterface {
   IObjectWrapper zza() throws RemoteException;

   void zza(Intent var1) throws RemoteException;

   void zza(Intent var1, int var2) throws RemoteException;

   void zza(IObjectWrapper var1) throws RemoteException;

   void zza(boolean var1) throws RemoteException;

   Bundle zzb() throws RemoteException;

   void zzb(IObjectWrapper var1) throws RemoteException;

   void zzb(boolean var1) throws RemoteException;

   int zzc() throws RemoteException;

   void zzc(boolean var1) throws RemoteException;

   IFragmentWrapper zzd() throws RemoteException;

   void zzd(boolean var1) throws RemoteException;

   IObjectWrapper zze() throws RemoteException;

   boolean zzf() throws RemoteException;

   String zzg() throws RemoteException;

   IFragmentWrapper zzh() throws RemoteException;

   int zzi() throws RemoteException;

   boolean zzj() throws RemoteException;

   IObjectWrapper zzk() throws RemoteException;

   boolean zzl() throws RemoteException;

   boolean zzm() throws RemoteException;

   boolean zzn() throws RemoteException;

   boolean zzo() throws RemoteException;

   boolean zzp() throws RemoteException;

   boolean zzq() throws RemoteException;

   boolean zzr() throws RemoteException;

   public abstract static class Stub extends com.google.android.gms.internal.common.zza implements IFragmentWrapper {
      public Stub() {
         super("com.google.android.gms.dynamic.IFragmentWrapper");
      }

      public static IFragmentWrapper asInterface(IBinder var0) {
         if (var0 == null) {
            return null;
         } else {
            IInterface var1 = var0.queryLocalInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            return (IFragmentWrapper)(var1 instanceof IFragmentWrapper ? (IFragmentWrapper)var1 : new IFragmentWrapper.Stub.zza(var0));
         }
      }

      protected final boolean zza(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         boolean var5;
         IObjectWrapper var6;
         IFragmentWrapper var7;
         switch(var1) {
         case 2:
            var6 = this.zza();
            var3.writeNoException();
            zzd.zza(var3, (IInterface)var6);
            break;
         case 3:
            Bundle var9 = this.zzb();
            var3.writeNoException();
            zzd.zzb(var3, var9);
            break;
         case 4:
            var1 = this.zzc();
            var3.writeNoException();
            var3.writeInt(var1);
            break;
         case 5:
            var7 = this.zzd();
            var3.writeNoException();
            zzd.zza(var3, (IInterface)var7);
            break;
         case 6:
            var6 = this.zze();
            var3.writeNoException();
            zzd.zza(var3, (IInterface)var6);
            break;
         case 7:
            var5 = this.zzf();
            var3.writeNoException();
            zzd.zza(var3, var5);
            break;
         case 8:
            String var8 = this.zzg();
            var3.writeNoException();
            var3.writeString(var8);
            break;
         case 9:
            var7 = this.zzh();
            var3.writeNoException();
            zzd.zza(var3, (IInterface)var7);
            break;
         case 10:
            var1 = this.zzi();
            var3.writeNoException();
            var3.writeInt(var1);
            break;
         case 11:
            var5 = this.zzj();
            var3.writeNoException();
            zzd.zza(var3, var5);
            break;
         case 12:
            var6 = this.zzk();
            var3.writeNoException();
            zzd.zza(var3, (IInterface)var6);
            break;
         case 13:
            var5 = this.zzl();
            var3.writeNoException();
            zzd.zza(var3, var5);
            break;
         case 14:
            var5 = this.zzm();
            var3.writeNoException();
            zzd.zza(var3, var5);
            break;
         case 15:
            var5 = this.zzn();
            var3.writeNoException();
            zzd.zza(var3, var5);
            break;
         case 16:
            var5 = this.zzo();
            var3.writeNoException();
            zzd.zza(var3, var5);
            break;
         case 17:
            var5 = this.zzp();
            var3.writeNoException();
            zzd.zza(var3, var5);
            break;
         case 18:
            var5 = this.zzq();
            var3.writeNoException();
            zzd.zza(var3, var5);
            break;
         case 19:
            var5 = this.zzr();
            var3.writeNoException();
            zzd.zza(var3, var5);
            break;
         case 20:
            this.zza(IObjectWrapper.Stub.asInterface(var2.readStrongBinder()));
            var3.writeNoException();
            break;
         case 21:
            this.zza(zzd.zza(var2));
            var3.writeNoException();
            break;
         case 22:
            this.zzb(zzd.zza(var2));
            var3.writeNoException();
            break;
         case 23:
            this.zzc(zzd.zza(var2));
            var3.writeNoException();
            break;
         case 24:
            this.zzd(zzd.zza(var2));
            var3.writeNoException();
            break;
         case 25:
            this.zza((Intent)zzd.zza(var2, Intent.CREATOR));
            var3.writeNoException();
            break;
         case 26:
            this.zza((Intent)zzd.zza(var2, Intent.CREATOR), var2.readInt());
            var3.writeNoException();
            break;
         case 27:
            this.zzb(IObjectWrapper.Stub.asInterface(var2.readStrongBinder()));
            var3.writeNoException();
            break;
         default:
            return false;
         }

         return true;
      }

      public static final class zza extends zzb implements IFragmentWrapper {
         zza(IBinder var1) {
            super(var1, "com.google.android.gms.dynamic.IFragmentWrapper");
         }

         public final IObjectWrapper zza() throws RemoteException {
            Parcel var1 = this.zza(2, this.a_());
            IObjectWrapper var2 = IObjectWrapper.Stub.asInterface(var1.readStrongBinder());
            var1.recycle();
            return var2;
         }

         public final void zza(Intent var1) throws RemoteException {
            Parcel var2 = this.a_();
            zzd.zza(var2, (Parcelable)var1);
            this.zzb(25, var2);
         }

         public final void zza(Intent var1, int var2) throws RemoteException {
            Parcel var3 = this.a_();
            zzd.zza(var3, (Parcelable)var1);
            var3.writeInt(var2);
            this.zzb(26, var3);
         }

         public final void zza(IObjectWrapper var1) throws RemoteException {
            Parcel var2 = this.a_();
            zzd.zza(var2, (IInterface)var1);
            this.zzb(20, var2);
         }

         public final void zza(boolean var1) throws RemoteException {
            Parcel var2 = this.a_();
            zzd.zza(var2, var1);
            this.zzb(21, var2);
         }

         public final Bundle zzb() throws RemoteException {
            Parcel var1 = this.zza(3, this.a_());
            Bundle var2 = (Bundle)zzd.zza(var1, Bundle.CREATOR);
            var1.recycle();
            return var2;
         }

         public final void zzb(IObjectWrapper var1) throws RemoteException {
            Parcel var2 = this.a_();
            zzd.zza(var2, (IInterface)var1);
            this.zzb(27, var2);
         }

         public final void zzb(boolean var1) throws RemoteException {
            Parcel var2 = this.a_();
            zzd.zza(var2, var1);
            this.zzb(22, var2);
         }

         public final int zzc() throws RemoteException {
            Parcel var1 = this.zza(4, this.a_());
            int var2 = var1.readInt();
            var1.recycle();
            return var2;
         }

         public final void zzc(boolean var1) throws RemoteException {
            Parcel var2 = this.a_();
            zzd.zza(var2, var1);
            this.zzb(23, var2);
         }

         public final IFragmentWrapper zzd() throws RemoteException {
            Parcel var1 = this.zza(5, this.a_());
            IFragmentWrapper var2 = IFragmentWrapper.Stub.asInterface(var1.readStrongBinder());
            var1.recycle();
            return var2;
         }

         public final void zzd(boolean var1) throws RemoteException {
            Parcel var2 = this.a_();
            zzd.zza(var2, var1);
            this.zzb(24, var2);
         }

         public final IObjectWrapper zze() throws RemoteException {
            Parcel var1 = this.zza(6, this.a_());
            IObjectWrapper var2 = IObjectWrapper.Stub.asInterface(var1.readStrongBinder());
            var1.recycle();
            return var2;
         }

         public final boolean zzf() throws RemoteException {
            Parcel var1 = this.zza(7, this.a_());
            boolean var2 = zzd.zza(var1);
            var1.recycle();
            return var2;
         }

         public final String zzg() throws RemoteException {
            Parcel var1 = this.zza(8, this.a_());
            String var2 = var1.readString();
            var1.recycle();
            return var2;
         }

         public final IFragmentWrapper zzh() throws RemoteException {
            Parcel var1 = this.zza(9, this.a_());
            IFragmentWrapper var2 = IFragmentWrapper.Stub.asInterface(var1.readStrongBinder());
            var1.recycle();
            return var2;
         }

         public final int zzi() throws RemoteException {
            Parcel var1 = this.zza(10, this.a_());
            int var2 = var1.readInt();
            var1.recycle();
            return var2;
         }

         public final boolean zzj() throws RemoteException {
            Parcel var1 = this.zza(11, this.a_());
            boolean var2 = zzd.zza(var1);
            var1.recycle();
            return var2;
         }

         public final IObjectWrapper zzk() throws RemoteException {
            Parcel var1 = this.zza(12, this.a_());
            IObjectWrapper var2 = IObjectWrapper.Stub.asInterface(var1.readStrongBinder());
            var1.recycle();
            return var2;
         }

         public final boolean zzl() throws RemoteException {
            Parcel var1 = this.zza(13, this.a_());
            boolean var2 = zzd.zza(var1);
            var1.recycle();
            return var2;
         }

         public final boolean zzm() throws RemoteException {
            Parcel var1 = this.zza(14, this.a_());
            boolean var2 = zzd.zza(var1);
            var1.recycle();
            return var2;
         }

         public final boolean zzn() throws RemoteException {
            Parcel var1 = this.zza(15, this.a_());
            boolean var2 = zzd.zza(var1);
            var1.recycle();
            return var2;
         }

         public final boolean zzo() throws RemoteException {
            Parcel var1 = this.zza(16, this.a_());
            boolean var2 = zzd.zza(var1);
            var1.recycle();
            return var2;
         }

         public final boolean zzp() throws RemoteException {
            Parcel var1 = this.zza(17, this.a_());
            boolean var2 = zzd.zza(var1);
            var1.recycle();
            return var2;
         }

         public final boolean zzq() throws RemoteException {
            Parcel var1 = this.zza(18, this.a_());
            boolean var2 = zzd.zza(var1);
            var1.recycle();
            return var2;
         }

         public final boolean zzr() throws RemoteException {
            Parcel var1 = this.zza(19, this.a_());
            boolean var2 = zzd.zza(var1);
            var1.recycle();
            return var2;
         }
      }
   }
}
