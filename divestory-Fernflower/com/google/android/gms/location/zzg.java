package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

public final class zzg implements Creator<ActivityTransitionResult> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      ArrayList var3 = null;

      while(var1.dataPosition() < var2) {
         int var4 = SafeParcelReader.readHeader(var1);
         if (SafeParcelReader.getFieldId(var4) != 1) {
            SafeParcelReader.skipUnknownField(var1, var4);
         } else {
            var3 = SafeParcelReader.createTypedList(var1, var4, ActivityTransitionEvent.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new ActivityTransitionResult(var3);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new ActivityTransitionResult[var1];
   }
}
