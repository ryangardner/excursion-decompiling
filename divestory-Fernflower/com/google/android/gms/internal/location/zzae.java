package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzae implements Creator<zzad> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      Status var3 = null;

      while(var1.dataPosition() < var2) {
         int var4 = SafeParcelReader.readHeader(var1);
         if (SafeParcelReader.getFieldId(var4) != 1) {
            SafeParcelReader.skipUnknownField(var1, var4);
         } else {
            var3 = (Status)SafeParcelReader.createParcelable(var1, var4, Status.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzad(var3);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzad[var1];
   }
}
