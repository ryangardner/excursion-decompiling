package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzae implements Creator<zzad> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);

      while(var1.dataPosition() < var2) {
         int var3 = SafeParcelReader.readHeader(var1);
         SafeParcelReader.getFieldId(var3);
         SafeParcelReader.skipUnknownField(var1, var3);
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzad();
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzad[var1];
   }
}
