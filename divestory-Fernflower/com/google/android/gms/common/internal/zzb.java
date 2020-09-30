package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzb implements Creator<zzc> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      Bundle var3 = null;
      Feature[] var4 = null;
      Object var5 = var4;
      int var6 = 0;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 1) {
            if (var8 != 2) {
               if (var8 != 3) {
                  if (var8 != 4) {
                     SafeParcelReader.skipUnknownField(var1, var7);
                  } else {
                     var5 = (ConnectionTelemetryConfiguration)SafeParcelReader.createParcelable(var1, var7, ConnectionTelemetryConfiguration.CREATOR);
                  }
               } else {
                  var6 = SafeParcelReader.readInt(var1, var7);
               }
            } else {
               var4 = (Feature[])SafeParcelReader.createTypedArray(var1, var7, Feature.CREATOR);
            }
         } else {
            var3 = SafeParcelReader.createBundle(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzc(var3, var4, var6, (ConnectionTelemetryConfiguration)var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzc[var1];
   }
}
