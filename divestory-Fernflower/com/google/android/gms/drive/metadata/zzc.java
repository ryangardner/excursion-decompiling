package com.google.android.gms.drive.metadata;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzc implements Creator<CustomPropertyKey> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = null;
      int var4 = 0;

      while(var1.dataPosition() < var2) {
         int var5 = SafeParcelReader.readHeader(var1);
         int var6 = SafeParcelReader.getFieldId(var5);
         if (var6 != 2) {
            if (var6 != 3) {
               SafeParcelReader.skipUnknownField(var1, var5);
            } else {
               var4 = SafeParcelReader.readInt(var1, var5);
            }
         } else {
            var3 = SafeParcelReader.createString(var1, var5);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new CustomPropertyKey(var3, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new CustomPropertyKey[var1];
   }
}
