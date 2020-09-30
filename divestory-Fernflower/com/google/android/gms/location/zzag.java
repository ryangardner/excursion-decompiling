package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

public final class zzag implements Creator<LocationSettingsRequest> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      boolean var3 = false;
      ArrayList var4 = null;
      Object var5 = var4;
      boolean var6 = false;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 1) {
            if (var8 != 2) {
               if (var8 != 3) {
                  if (var8 != 5) {
                     SafeParcelReader.skipUnknownField(var1, var7);
                  } else {
                     var5 = (zzae)SafeParcelReader.createParcelable(var1, var7, zzae.CREATOR);
                  }
               } else {
                  var6 = SafeParcelReader.readBoolean(var1, var7);
               }
            } else {
               var3 = SafeParcelReader.readBoolean(var1, var7);
            }
         } else {
            var4 = SafeParcelReader.createTypedList(var1, var7, LocationRequest.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new LocationSettingsRequest(var4, var3, var6, (zzae)var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new LocationSettingsRequest[var1];
   }
}
