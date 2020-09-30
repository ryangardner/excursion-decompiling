package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzt implements Creator<UserMetadata> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = null;
      String var4 = var3;
      String var5 = var3;
      String var6 = var3;
      boolean var7 = false;

      while(var1.dataPosition() < var2) {
         int var8 = SafeParcelReader.readHeader(var1);
         int var9 = SafeParcelReader.getFieldId(var8);
         if (var9 != 2) {
            if (var9 != 3) {
               if (var9 != 4) {
                  if (var9 != 5) {
                     if (var9 != 6) {
                        SafeParcelReader.skipUnknownField(var1, var8);
                     } else {
                        var6 = SafeParcelReader.createString(var1, var8);
                     }
                  } else {
                     var7 = SafeParcelReader.readBoolean(var1, var8);
                  }
               } else {
                  var5 = SafeParcelReader.createString(var1, var8);
               }
            } else {
               var4 = SafeParcelReader.createString(var1, var8);
            }
         } else {
            var3 = SafeParcelReader.createString(var1, var8);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new UserMetadata(var3, var4, var5, var7, var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new UserMetadata[var1];
   }
}
