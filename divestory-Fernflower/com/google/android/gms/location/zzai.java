package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzai implements Creator<LocationSettingsStates> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      boolean var8 = false;

      while(var1.dataPosition() < var2) {
         int var9 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var9)) {
         case 1:
            var3 = SafeParcelReader.readBoolean(var1, var9);
            break;
         case 2:
            var4 = SafeParcelReader.readBoolean(var1, var9);
            break;
         case 3:
            var5 = SafeParcelReader.readBoolean(var1, var9);
            break;
         case 4:
            var6 = SafeParcelReader.readBoolean(var1, var9);
            break;
         case 5:
            var7 = SafeParcelReader.readBoolean(var1, var9);
            break;
         case 6:
            var8 = SafeParcelReader.readBoolean(var1, var9);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var9);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new LocationSettingsStates(var3, var4, var5, var6, var7, var8);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new LocationSettingsStates[var1];
   }
}
