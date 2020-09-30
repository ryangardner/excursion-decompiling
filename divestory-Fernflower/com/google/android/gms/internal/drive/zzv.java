package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzv implements Creator<zzu> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      MetadataBundle var3 = null;
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
                        var6 = SafeParcelReader.readIntegerObject(var1, var8);
                     }
                  } else {
                     var5 = (DriveId)SafeParcelReader.createParcelable(var1, var8, DriveId.CREATOR);
                  }
               } else {
                  var4 = SafeParcelReader.createString(var1, var8);
               }
            } else {
               var7 = SafeParcelReader.readInt(var1, var8);
            }
         } else {
            var3 = (MetadataBundle)SafeParcelReader.createParcelable(var1, var8, MetadataBundle.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzu(var3, var7, (String)var4, (DriveId)var5, (Integer)var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzu[var1];
   }
}
