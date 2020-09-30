package com.google.android.gms.internal.location;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzp implements Creator<zzo> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      zzm var3 = null;
      IBinder var4 = null;
      IBinder var5 = var4;
      int var6 = 1;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 1) {
            if (var8 != 2) {
               if (var8 != 3) {
                  if (var8 != 4) {
                     SafeParcelReader.skipUnknownField(var1, var7);
                  } else {
                     var5 = SafeParcelReader.readIBinder(var1, var7);
                  }
               } else {
                  var4 = SafeParcelReader.readIBinder(var1, var7);
               }
            } else {
               var3 = (zzm)SafeParcelReader.createParcelable(var1, var7, zzm.CREATOR);
            }
         } else {
            var6 = SafeParcelReader.readInt(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzo(var6, var3, var4, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzo[var1];
   }
}
