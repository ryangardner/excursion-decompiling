package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.location.zzbh;
import java.util.ArrayList;

public final class zzq implements Creator<GeofencingRequest> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      ArrayList var3 = null;
      int var4 = 0;
      String var5 = "";

      while(var1.dataPosition() < var2) {
         int var6 = SafeParcelReader.readHeader(var1);
         int var7 = SafeParcelReader.getFieldId(var6);
         if (var7 != 1) {
            if (var7 != 2) {
               if (var7 != 3) {
                  SafeParcelReader.skipUnknownField(var1, var6);
               } else {
                  var5 = SafeParcelReader.createString(var1, var6);
               }
            } else {
               var4 = SafeParcelReader.readInt(var1, var6);
            }
         } else {
            var3 = SafeParcelReader.createTypedList(var1, var6, zzbh.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new GeofencingRequest(var3, var4, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new GeofencingRequest[var1];
   }
}
