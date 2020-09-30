package com.google.android.gms.location;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

public final class zzb implements Creator<ActivityRecognitionResult> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      long var3 = 0L;
      long var5 = var3;
      ArrayList var7 = null;
      Object var8 = var7;
      int var9 = 0;

      while(var1.dataPosition() < var2) {
         int var10 = SafeParcelReader.readHeader(var1);
         int var11 = SafeParcelReader.getFieldId(var10);
         if (var11 != 1) {
            if (var11 != 2) {
               if (var11 != 3) {
                  if (var11 != 4) {
                     if (var11 != 5) {
                        SafeParcelReader.skipUnknownField(var1, var10);
                     } else {
                        var8 = SafeParcelReader.createBundle(var1, var10);
                     }
                  } else {
                     var9 = SafeParcelReader.readInt(var1, var10);
                  }
               } else {
                  var5 = SafeParcelReader.readLong(var1, var10);
               }
            } else {
               var3 = SafeParcelReader.readLong(var1, var10);
            }
         } else {
            var7 = SafeParcelReader.createTypedList(var1, var10, DetectedActivity.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new ActivityRecognitionResult(var7, var3, var5, var9, (Bundle)var8);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new ActivityRecognitionResult[var1];
   }
}
