package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.List;

public final class zzn implements Creator<zzm> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      com.google.android.gms.location.zzj var3 = zzm.zzce;
      Object var4 = zzm.zzcd;
      String var5 = null;

      while(var1.dataPosition() < var2) {
         int var6 = SafeParcelReader.readHeader(var1);
         int var7 = SafeParcelReader.getFieldId(var6);
         if (var7 != 1) {
            if (var7 != 2) {
               if (var7 != 3) {
                  SafeParcelReader.skipUnknownField(var1, var6);
               } else {
                  var5 = SafeParcelReader.createString(var1, var6);
               }
            } else {
               var4 = SafeParcelReader.createTypedList(var1, var6, ClientIdentity.CREATOR);
            }
         } else {
            var3 = (com.google.android.gms.location.zzj)SafeParcelReader.createParcelable(var1, var6, com.google.android.gms.location.zzj.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzm(var3, (List)var4, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzm[var1];
   }
}
