package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

public final class zzam implements Creator<zzal> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      ArrayList var3 = null;
      String var4 = "";
      PendingIntent var5 = null;

      while(var1.dataPosition() < var2) {
         int var6 = SafeParcelReader.readHeader(var1);
         int var7 = SafeParcelReader.getFieldId(var6);
         if (var7 != 1) {
            if (var7 != 2) {
               if (var7 != 3) {
                  SafeParcelReader.skipUnknownField(var1, var6);
               } else {
                  var4 = SafeParcelReader.createString(var1, var6);
               }
            } else {
               var5 = (PendingIntent)SafeParcelReader.createParcelable(var1, var6, PendingIntent.CREATOR);
            }
         } else {
            var3 = SafeParcelReader.createStringList(var1, var6);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzal(var3, var5, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzal[var1];
   }
}
