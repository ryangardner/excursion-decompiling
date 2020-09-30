package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzc implements Creator<zzb> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      zze var3 = null;

      while(var1.dataPosition() < var2) {
         int var4 = SafeParcelReader.readHeader(var1);
         if (SafeParcelReader.getFieldId(var4) != 3) {
            SafeParcelReader.skipUnknownField(var1, var4);
         } else {
            var3 = (zze)SafeParcelReader.createParcelable(var1, var4, zze.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzb(var3);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzb[var1];
   }
}
