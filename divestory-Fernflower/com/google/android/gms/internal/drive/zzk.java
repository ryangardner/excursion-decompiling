package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;

public final class zzk implements Creator<zzj> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      DriveId var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      Object var6 = var3;
      int var7 = 0;

      while(var1.dataPosition() < var2) {
         int var8 = SafeParcelReader.readHeader(var1);
         int var9 = SafeParcelReader.getFieldId(var8);
         if (var9 != 2) {
            if (var9 != 3) {
               if (var9 != 4) {
                  if (var9 != 5) {
                     if (var9 != 6) {
                        SafeParcelReader.skipUnknownField(var1, var8);
                     } else {
                        var6 = (com.google.android.gms.drive.events.zzt)SafeParcelReader.createParcelable(var1, var8, com.google.android.gms.drive.events.zzt.CREATOR);
                     }
                  } else {
                     var5 = (com.google.android.gms.drive.events.zzx)SafeParcelReader.createParcelable(var1, var8, com.google.android.gms.drive.events.zzx.CREATOR);
                  }
               } else {
                  var4 = (com.google.android.gms.drive.events.zze)SafeParcelReader.createParcelable(var1, var8, com.google.android.gms.drive.events.zze.CREATOR);
               }
            } else {
               var7 = SafeParcelReader.readInt(var1, var8);
            }
         } else {
            var3 = (DriveId)SafeParcelReader.createParcelable(var1, var8, DriveId.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzj(var3, var7, (com.google.android.gms.drive.events.zze)var4, (com.google.android.gms.drive.events.zzx)var5, (com.google.android.gms.drive.events.zzt)var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzj[var1];
   }
}
