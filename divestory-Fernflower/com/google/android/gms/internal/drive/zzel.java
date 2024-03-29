package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;

public final class zzel implements Creator<zzek> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      DriveId var3 = null;
      boolean var4 = false;

      while(var1.dataPosition() < var2) {
         int var5 = SafeParcelReader.readHeader(var1);
         int var6 = SafeParcelReader.getFieldId(var5);
         if (var6 != 2) {
            if (var6 != 3) {
               SafeParcelReader.skipUnknownField(var1, var5);
            } else {
               var4 = SafeParcelReader.readBoolean(var1, var5);
            }
         } else {
            var3 = (DriveId)SafeParcelReader.createParcelable(var1, var5, DriveId.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzek(var3, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzek[var1];
   }
}
