package com.google.android.gms.internal.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzo extends AbstractSafeParcelable {
   public static final Creator<zzo> CREATOR = new zzp();
   private int zzcg;
   private zzm zzch;
   private com.google.android.gms.location.zzr zzci;
   private zzaj zzcj;

   zzo(int var1, zzm var2, IBinder var3, IBinder var4) {
      this.zzcg = var1;
      this.zzch = var2;
      Object var5 = null;
      com.google.android.gms.location.zzr var6;
      if (var3 == null) {
         var6 = null;
      } else {
         var6 = com.google.android.gms.location.zzs.zza(var3);
      }

      this.zzci = var6;
      Object var7;
      if (var4 == null) {
         var7 = var5;
      } else if (var4 == null) {
         var7 = var5;
      } else {
         IInterface var8 = var4.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
         if (var8 instanceof zzaj) {
            var7 = (zzaj)var8;
         } else {
            var7 = new zzal(var4);
         }
      }

      this.zzcj = (zzaj)var7;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zzcg);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzch, var2, false);
      com.google.android.gms.location.zzr var4 = this.zzci;
      Object var5 = null;
      IBinder var6;
      if (var4 == null) {
         var6 = null;
      } else {
         var6 = var4.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 3, var6, false);
      zzaj var7 = this.zzcj;
      if (var7 == null) {
         var6 = (IBinder)var5;
      } else {
         var6 = var7.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 4, var6, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
