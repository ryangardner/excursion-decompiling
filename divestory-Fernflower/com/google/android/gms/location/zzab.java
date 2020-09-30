package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzab implements Creator<LocationRequest> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      long var3 = 3600000L;
      long var5 = 600000L;
      long var7 = Long.MAX_VALUE;
      long var9 = 0L;
      int var11 = 102;
      boolean var12 = false;
      int var13 = Integer.MAX_VALUE;
      float var14 = 0.0F;

      while(var1.dataPosition() < var2) {
         int var15 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var15)) {
         case 1:
            var11 = SafeParcelReader.readInt(var1, var15);
            break;
         case 2:
            var3 = SafeParcelReader.readLong(var1, var15);
            break;
         case 3:
            var5 = SafeParcelReader.readLong(var1, var15);
            break;
         case 4:
            var12 = SafeParcelReader.readBoolean(var1, var15);
            break;
         case 5:
            var7 = SafeParcelReader.readLong(var1, var15);
            break;
         case 6:
            var13 = SafeParcelReader.readInt(var1, var15);
            break;
         case 7:
            var14 = SafeParcelReader.readFloat(var1, var15);
            break;
         case 8:
            var9 = SafeParcelReader.readLong(var1, var15);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var15);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new LocationRequest(var11, var3, var5, var12, var7, var13, var14, var9);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new LocationRequest[var1];
   }
}
