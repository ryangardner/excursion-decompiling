package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zax implements Creator<zau> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      int var3 = 0;
      Scope[] var4 = null;
      int var5 = 0;
      int var6 = 0;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 1) {
            if (var8 != 2) {
               if (var8 != 3) {
                  if (var8 != 4) {
                     SafeParcelReader.skipUnknownField(var1, var7);
                  } else {
                     var4 = (Scope[])SafeParcelReader.createTypedArray(var1, var7, Scope.CREATOR);
                  }
               } else {
                  var6 = SafeParcelReader.readInt(var1, var7);
               }
            } else {
               var5 = SafeParcelReader.readInt(var1, var7);
            }
         } else {
            var3 = SafeParcelReader.readInt(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zau(var3, var5, var6, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zau[var1];
   }
}
