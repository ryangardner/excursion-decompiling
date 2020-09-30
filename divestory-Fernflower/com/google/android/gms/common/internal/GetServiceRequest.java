package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class GetServiceRequest extends AbstractSafeParcelable {
   public static final Creator<GetServiceRequest> CREATOR = new zze();
   String zza;
   IBinder zzb;
   Scope[] zzc;
   Bundle zzd;
   Account zze;
   Feature[] zzf;
   Feature[] zzg;
   boolean zzh;
   private final int zzi;
   private final int zzj;
   private int zzk;
   private boolean zzl;
   private int zzm;

   public GetServiceRequest(int var1) {
      this.zzi = 5;
      this.zzk = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
      this.zzj = var1;
      this.zzl = true;
   }

   GetServiceRequest(int var1, int var2, int var3, String var4, IBinder var5, Scope[] var6, Bundle var7, Account var8, Feature[] var9, Feature[] var10, boolean var11, int var12, boolean var13) {
      this.zzi = var1;
      this.zzj = var2;
      this.zzk = var3;
      if ("com.google.android.gms".equals(var4)) {
         this.zza = "com.google.android.gms";
      } else {
         this.zza = var4;
      }

      if (var1 < 2) {
         Account var14 = null;
         if (var5 != null) {
            var14 = AccountAccessor.getAccountBinderSafe(IAccountAccessor.Stub.asInterface(var5));
         }

         this.zze = var14;
      } else {
         this.zzb = var5;
         this.zze = var8;
      }

      this.zzc = var6;
      this.zzd = var7;
      this.zzf = var9;
      this.zzg = var10;
      this.zzl = var11;
      this.zzm = var12;
      this.zzh = var13;
   }

   public Bundle getExtraArgs() {
      return this.zzd;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zzi);
      SafeParcelWriter.writeInt(var1, 2, this.zzj);
      SafeParcelWriter.writeInt(var1, 3, this.zzk);
      SafeParcelWriter.writeString(var1, 4, this.zza, false);
      SafeParcelWriter.writeIBinder(var1, 5, this.zzb, false);
      SafeParcelWriter.writeTypedArray(var1, 6, this.zzc, var2, false);
      SafeParcelWriter.writeBundle(var1, 7, this.zzd, false);
      SafeParcelWriter.writeParcelable(var1, 8, this.zze, var2, false);
      SafeParcelWriter.writeTypedArray(var1, 10, this.zzf, var2, false);
      SafeParcelWriter.writeTypedArray(var1, 11, this.zzg, var2, false);
      SafeParcelWriter.writeBoolean(var1, 12, this.zzl);
      SafeParcelWriter.writeInt(var1, 13, this.zzm);
      SafeParcelWriter.writeBoolean(var1, 14, this.zzh);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
