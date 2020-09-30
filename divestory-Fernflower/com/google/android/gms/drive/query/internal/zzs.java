package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

public final class zzs implements Creator<zzr> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      zzx var3 = null;
      ArrayList var4 = null;

      while(var1.dataPosition() < var2) {
         int var5 = SafeParcelReader.readHeader(var1);
         int var6 = SafeParcelReader.getFieldId(var5);
         if (var6 != 1) {
            if (var6 != 2) {
               SafeParcelReader.skipUnknownField(var1, var5);
            } else {
               var4 = SafeParcelReader.createTypedList(var1, var5, FilterHolder.CREATOR);
            }
         } else {
            var3 = (zzx)SafeParcelReader.createParcelable(var1, var5, zzx.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzr(var3, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzr[var1];
   }
}
