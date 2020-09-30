package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.query.internal.FilterHolder;

public final class zzgn implements Creator<zzgm> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = null;
      String[] var4 = null;
      Object var5 = var4;
      Object var6 = var4;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 2) {
            if (var8 != 3) {
               if (var8 != 4) {
                  if (var8 != 5) {
                     SafeParcelReader.skipUnknownField(var1, var7);
                  } else {
                     var6 = (FilterHolder)SafeParcelReader.createParcelable(var1, var7, FilterHolder.CREATOR);
                  }
               } else {
                  var5 = (DriveId)SafeParcelReader.createParcelable(var1, var7, DriveId.CREATOR);
               }
            } else {
               var4 = SafeParcelReader.createStringArray(var1, var7);
            }
         } else {
            var3 = SafeParcelReader.createString(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzgm(var3, var4, (DriveId)var5, (FilterHolder)var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzgm[var1];
   }
}
