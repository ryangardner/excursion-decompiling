package com.google.android.gms.location;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.List;

public final class zzac implements Creator<LocationResult> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      Object var3 = LocationResult.zzbb;

      while(var1.dataPosition() < var2) {
         int var4 = SafeParcelReader.readHeader(var1);
         if (SafeParcelReader.getFieldId(var4) != 1) {
            SafeParcelReader.skipUnknownField(var1, var4);
         } else {
            var3 = SafeParcelReader.createTypedList(var1, var4, Location.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new LocationResult((List)var3);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new LocationResult[var1];
   }
}
