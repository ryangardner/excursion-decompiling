package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzah implements Creator<LocationSettingsResult> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      Status var3 = null;
      LocationSettingsStates var4 = null;

      while(var1.dataPosition() < var2) {
         int var5 = SafeParcelReader.readHeader(var1);
         int var6 = SafeParcelReader.getFieldId(var5);
         if (var6 != 1) {
            if (var6 != 2) {
               SafeParcelReader.skipUnknownField(var1, var5);
            } else {
               var4 = (LocationSettingsStates)SafeParcelReader.createParcelable(var1, var5, LocationSettingsStates.CREATOR);
            }
         } else {
            var3 = (Status)SafeParcelReader.createParcelable(var1, var5, Status.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new LocationSettingsResult(var3, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new LocationSettingsResult[var1];
   }
}
