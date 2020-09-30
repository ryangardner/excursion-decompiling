package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.query.internal.zzf;
import java.util.ArrayList;

public final class zzc implements Creator<SortOrder> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      ArrayList var3 = null;
      boolean var4 = false;

      while(var1.dataPosition() < var2) {
         int var5 = SafeParcelReader.readHeader(var1);
         int var6 = SafeParcelReader.getFieldId(var5);
         if (var6 != 1) {
            if (var6 != 2) {
               SafeParcelReader.skipUnknownField(var1, var5);
            } else {
               var4 = SafeParcelReader.readBoolean(var1, var5);
            }
         } else {
            var3 = SafeParcelReader.createTypedList(var1, var5, zzf.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new SortOrder(var3, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new SortOrder[var1];
   }
}
