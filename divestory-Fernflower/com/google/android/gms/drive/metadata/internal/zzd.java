package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.metadata.CustomPropertyKey;

public final class zzd implements Creator<zzc> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      CustomPropertyKey var3 = null;
      String var4 = null;

      while(var1.dataPosition() < var2) {
         int var5 = SafeParcelReader.readHeader(var1);
         int var6 = SafeParcelReader.getFieldId(var5);
         if (var6 != 2) {
            if (var6 != 3) {
               SafeParcelReader.skipUnknownField(var1, var5);
            } else {
               var4 = SafeParcelReader.createString(var1, var5);
            }
         } else {
            var3 = (CustomPropertyKey)SafeParcelReader.createParcelable(var1, var5, CustomPropertyKey.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzc(var3, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzc[var1];
   }
}
