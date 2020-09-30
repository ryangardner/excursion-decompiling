package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

public final class zzen implements Creator<zzem> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      ArrayList var3 = null;
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
            var3 = SafeParcelReader.createTypedList(var1, var5, com.google.android.gms.drive.zzr.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzem(var3, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzem[var1];
   }
}
