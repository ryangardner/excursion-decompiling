package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzd implements Creator<ActivityTransitionEvent> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      int var3 = 0;
      long var4 = 0L;
      int var6 = 0;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 1) {
            if (var8 != 2) {
               if (var8 != 3) {
                  SafeParcelReader.skipUnknownField(var1, var7);
               } else {
                  var4 = SafeParcelReader.readLong(var1, var7);
               }
            } else {
               var6 = SafeParcelReader.readInt(var1, var7);
            }
         } else {
            var3 = SafeParcelReader.readInt(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new ActivityTransitionEvent(var3, var6, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new ActivityTransitionEvent[var1];
   }
}
