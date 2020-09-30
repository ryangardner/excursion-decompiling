package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzd implements Creator<ConnectionTelemetryConfiguration> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      RootTelemetryConfiguration var3 = null;
      Object var4 = var3;
      boolean var5 = false;
      boolean var6 = false;
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
                     var4 = SafeParcelReader.createIntArray(var1, var8);
                  }
               } else {
                  var6 = SafeParcelReader.readBoolean(var1, var8);
               }
            } else {
               var5 = SafeParcelReader.readBoolean(var1, var8);
            }
         } else {
            var3 = (RootTelemetryConfiguration)SafeParcelReader.createParcelable(var1, var8, RootTelemetryConfiguration.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new ConnectionTelemetryConfiguration(var3, var5, var6, (int[])var4, var7);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new ConnectionTelemetryConfiguration[var1];
   }
}
