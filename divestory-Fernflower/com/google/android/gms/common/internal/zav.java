package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zav implements Creator<zas> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      IBinder var3 = null;
      Object var4 = var3;
      int var5 = 0;
      boolean var6 = false;
      boolean var7 = false;

      while(var1.dataPosition() < var2) {
         int var8 = SafeParcelReader.readHeader(var1);
         int var9 = SafeParcelReader.getFieldId(var8);
         if (var9 != 1) {
            if (var9 != 2) {
               if (var9 != 3) {
                  if (var9 != 4) {
                     if (var9 != 5) {
                        SafeParcelReader.skipUnknownField(var1, var8);
                     } else {
                        var7 = SafeParcelReader.readBoolean(var1, var8);
                     }
                  } else {
                     var6 = SafeParcelReader.readBoolean(var1, var8);
                  }
               } else {
                  var4 = (ConnectionResult)SafeParcelReader.createParcelable(var1, var8, ConnectionResult.CREATOR);
               }
            } else {
               var3 = SafeParcelReader.readIBinder(var1, var8);
            }
         } else {
            var5 = SafeParcelReader.readInt(var1, var8);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zas(var5, var3, (ConnectionResult)var4, var6, var7);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zas[var1];
   }
}
