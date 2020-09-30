package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzbf extends AbstractSafeParcelable {
   public static final Creator<zzbf> CREATOR = new zzbg();
   private PendingIntent zzbv;
   private int zzcg;
   private zzaj zzcj;
   private zzbd zzdl;
   private com.google.android.gms.location.zzx zzdm;
   private com.google.android.gms.location.zzu zzdn;

   zzbf(int var1, zzbd var2, IBinder var3, PendingIntent var4, IBinder var5, IBinder var6) {
      this.zzcg = var1;
      this.zzdl = var2;
      Object var7 = null;
      com.google.android.gms.location.zzx var8;
      if (var3 == null) {
         var8 = null;
      } else {
         var8 = com.google.android.gms.location.zzy.zzc(var3);
      }

      this.zzdm = var8;
      this.zzbv = var4;
      com.google.android.gms.location.zzu var9;
      if (var5 == null) {
         var9 = null;
      } else {
         var9 = com.google.android.gms.location.zzv.zzb(var5);
      }

      this.zzdn = var9;
      Object var10;
      if (var6 == null) {
         var10 = var7;
      } else if (var6 == null) {
         var10 = var7;
      } else {
         IInterface var11 = var6.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
         if (var11 instanceof zzaj) {
            var10 = (zzaj)var11;
         } else {
            var10 = new zzal(var6);
         }
      }

      this.zzcj = (zzaj)var10;
   }

   public static zzbf zza(com.google.android.gms.location.zzu var0, zzaj var1) {
      IBinder var2 = var0.asBinder();
      IBinder var3;
      if (var1 != null) {
         var3 = var1.asBinder();
      } else {
         var3 = null;
      }

      return new zzbf(2, (zzbd)null, (IBinder)null, (PendingIntent)null, var2, var3);
   }

   public static zzbf zza(com.google.android.gms.location.zzx var0, zzaj var1) {
      IBinder var2 = var0.asBinder();
      IBinder var3;
      if (var1 != null) {
         var3 = var1.asBinder();
      } else {
         var3 = null;
      }

      return new zzbf(2, (zzbd)null, var2, (PendingIntent)null, (IBinder)null, var3);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zzcg);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzdl, var2, false);
      com.google.android.gms.location.zzx var4 = this.zzdm;
      Object var5 = null;
      IBinder var6;
      if (var4 == null) {
         var6 = null;
      } else {
         var6 = var4.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 3, var6, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.zzbv, var2, false);
      com.google.android.gms.location.zzu var7 = this.zzdn;
      if (var7 == null) {
         var6 = null;
      } else {
         var6 = var7.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 5, var6, false);
      zzaj var8 = this.zzcj;
      if (var8 == null) {
         var6 = (IBinder)var5;
      } else {
         var6 = var8.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 6, var6, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
