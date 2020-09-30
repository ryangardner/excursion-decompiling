package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;
import java.util.List;

public final class zzf implements Creator<ActivityTransitionRequest> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      ArrayList var3 = null;
      String var4 = null;
      Object var5 = var4;

      while(var1.dataPosition() < var2) {
         int var6 = SafeParcelReader.readHeader(var1);
         int var7 = SafeParcelReader.getFieldId(var6);
         if (var7 != 1) {
            if (var7 != 2) {
               if (var7 != 3) {
                  SafeParcelReader.skipUnknownField(var1, var6);
               } else {
                  var5 = SafeParcelReader.createTypedList(var1, var6, ClientIdentity.CREATOR);
               }
            } else {
               var4 = SafeParcelReader.createString(var1, var6);
            }
         } else {
            var3 = SafeParcelReader.createTypedList(var1, var6, ActivityTransition.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new ActivityTransitionRequest(var3, var4, (List)var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new ActivityTransitionRequest[var1];
   }
}
