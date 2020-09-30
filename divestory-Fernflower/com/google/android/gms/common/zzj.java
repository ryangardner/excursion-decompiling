package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import javax.annotation.Nullable;

public final class zzj extends AbstractSafeParcelable {
   public static final Creator<zzj> CREATOR = new zzm();
   private final String zza;
   @Nullable
   private final zzd zzb;
   private final boolean zzc;
   private final boolean zzd;

   zzj(String var1, @Nullable IBinder var2, boolean var3, boolean var4) {
      this.zza = var1;
      this.zzb = zza(var2);
      this.zzc = var3;
      this.zzd = var4;
   }

   zzj(String var1, @Nullable zzd var2, boolean var3, boolean var4) {
      this.zza = var1;
      this.zzb = var2;
      this.zzc = var3;
      this.zzd = var4;
   }

   @Nullable
   private static zzd zza(@Nullable IBinder var0) {
      Object var1 = null;
      if (var0 == null) {
         return null;
      } else {
         IObjectWrapper var3;
         try {
            var3 = com.google.android.gms.common.internal.zzo.zza(var0).zzb();
         } catch (RemoteException var2) {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", var2);
            return null;
         }

         byte[] var4;
         if (var3 == null) {
            var4 = null;
         } else {
            var4 = (byte[])ObjectWrapper.unwrap(var3);
         }

         zzg var5;
         if (var4 != null) {
            var5 = new zzg(var4);
         } else {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
            var5 = (zzg)var1;
         }

         return var5;
      }
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.zza, false);
      zzd var3 = this.zzb;
      IBinder var4;
      if (var3 == null) {
         Log.w("GoogleCertificatesQuery", "certificate binder is null");
         var4 = null;
      } else {
         var4 = var3.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 2, var4, false);
      SafeParcelWriter.writeBoolean(var1, 3, this.zzc);
      SafeParcelWriter.writeBoolean(var1, 4, this.zzd);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
