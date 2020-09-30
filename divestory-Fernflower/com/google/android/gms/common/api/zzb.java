package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzb implements Creator<Status> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      int var6 = 0;
      int var7 = 0;

      while(var1.dataPosition() < var2) {
         int var8 = SafeParcelReader.readHeader(var1);
         int var9 = SafeParcelReader.getFieldId(var8);
         if (var9 != 1) {
            if (var9 != 2) {
               if (var9 != 3) {
                  if (var9 != 4) {
                     if (var9 != 1000) {
                        SafeParcelReader.skipUnknownField(var1, var8);
                     } else {
                        var6 = SafeParcelReader.readInt(var1, var8);
                     }
                  } else {
                     var5 = (ConnectionResult)SafeParcelReader.createParcelable(var1, var8, ConnectionResult.CREATOR);
                  }
               } else {
                  var4 = (PendingIntent)SafeParcelReader.createParcelable(var1, var8, PendingIntent.CREATOR);
               }
            } else {
               var3 = SafeParcelReader.createString(var1, var8);
            }
         } else {
            var7 = SafeParcelReader.readInt(var1, var8);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new Status(var6, var7, var3, (PendingIntent)var4, (ConnectionResult)var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new Status[var1];
   }
}
