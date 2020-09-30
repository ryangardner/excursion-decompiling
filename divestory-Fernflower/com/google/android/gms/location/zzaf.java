package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzaf implements Creator<zzae> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = "";
      String var4 = "";
      String var5 = var4;

      while(var1.dataPosition() < var2) {
         int var6 = SafeParcelReader.readHeader(var1);
         int var7 = SafeParcelReader.getFieldId(var6);
         if (var7 != 1) {
            if (var7 != 2) {
               if (var7 != 5) {
                  SafeParcelReader.skipUnknownField(var1, var6);
               } else {
                  var3 = SafeParcelReader.createString(var1, var6);
               }
            } else {
               var5 = SafeParcelReader.createString(var1, var6);
            }
         } else {
            var4 = SafeParcelReader.createString(var1, var6);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzae(var3, var4, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzae[var1];
   }
}
