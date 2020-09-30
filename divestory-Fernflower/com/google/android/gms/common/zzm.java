package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzm implements Creator<zzj> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      boolean var3 = false;
      String var4 = null;
      Object var5 = var4;
      boolean var6 = false;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 1) {
            if (var8 != 2) {
               if (var8 != 3) {
                  if (var8 != 4) {
                     SafeParcelReader.skipUnknownField(var1, var7);
                  } else {
                     var6 = SafeParcelReader.readBoolean(var1, var7);
                  }
               } else {
                  var3 = SafeParcelReader.readBoolean(var1, var7);
               }
            } else {
               var5 = SafeParcelReader.readIBinder(var1, var7);
            }
         } else {
            var4 = SafeParcelReader.createString(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzj(var4, (IBinder)var5, var3, var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzj[var1];
   }
}
