package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zab implements Creator<zaa> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      int var3 = 0;
      StringToIntConverter var4 = null;

      while(var1.dataPosition() < var2) {
         int var5 = SafeParcelReader.readHeader(var1);
         int var6 = SafeParcelReader.getFieldId(var5);
         if (var6 != 1) {
            if (var6 != 2) {
               SafeParcelReader.skipUnknownField(var1, var5);
            } else {
               var4 = (StringToIntConverter)SafeParcelReader.createParcelable(var1, var5, StringToIntConverter.CREATOR);
            }
         } else {
            var3 = SafeParcelReader.readInt(var1, var5);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zaa(var3, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zaa[var1];
   }
}
