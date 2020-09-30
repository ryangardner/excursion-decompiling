package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzv implements Creator<RootTelemetryConfiguration> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      int var3 = 0;
      boolean var4 = false;
      boolean var5 = false;
      int var6 = 0;
      int var7 = 0;

      while(var1.dataPosition() < var2) {
         int var8 = SafeParcelReader.readHeader(var1);
         int var9 = SafeParcelReader.getFieldId(var8);
         if (var9 != 1) {
            if (var9 != 2) {
               if (var9 != 3) {
                  if (var9 != 4) {
                     if (var9 != 5) {
                        SafeParcelReader.skipUnknownField(var1, var8);
                     } else {
                        var7 = SafeParcelReader.readInt(var1, var8);
                     }
                  } else {
                     var6 = SafeParcelReader.readInt(var1, var8);
                  }
               } else {
                  var5 = SafeParcelReader.readBoolean(var1, var8);
               }
            } else {
               var4 = SafeParcelReader.readBoolean(var1, var8);
            }
         } else {
            var3 = SafeParcelReader.readInt(var1, var8);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new RootTelemetryConfiguration(var3, var4, var5, var6, var7);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new RootTelemetryConfiguration[var1];
   }
}
