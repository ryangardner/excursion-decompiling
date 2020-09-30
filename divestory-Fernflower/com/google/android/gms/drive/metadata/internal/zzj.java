package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzj implements Creator<MetadataBundle> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      Bundle var3 = null;

      while(var1.dataPosition() < var2) {
         int var4 = SafeParcelReader.readHeader(var1);
         if (SafeParcelReader.getFieldId(var4) != 2) {
            SafeParcelReader.skipUnknownField(var1, var4);
         } else {
            var3 = SafeParcelReader.createBundle(var1, var4);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new MetadataBundle(var3);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new MetadataBundle[var1];
   }
}
