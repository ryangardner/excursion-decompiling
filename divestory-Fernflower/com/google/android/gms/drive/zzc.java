package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzc implements Creator<Contents> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      ParcelFileDescriptor var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      int var6 = 0;
      int var7 = 0;
      boolean var8 = false;

      while(var1.dataPosition() < var2) {
         int var9 = SafeParcelReader.readHeader(var1);
         int var10 = SafeParcelReader.getFieldId(var9);
         if (var10 != 2) {
            if (var10 != 3) {
               if (var10 != 4) {
                  if (var10 != 5) {
                     if (var10 != 7) {
                        if (var10 != 8) {
                           SafeParcelReader.skipUnknownField(var1, var9);
                        } else {
                           var5 = SafeParcelReader.createString(var1, var9);
                        }
                     } else {
                        var8 = SafeParcelReader.readBoolean(var1, var9);
                     }
                  } else {
                     var4 = (DriveId)SafeParcelReader.createParcelable(var1, var9, DriveId.CREATOR);
                  }
               } else {
                  var7 = SafeParcelReader.readInt(var1, var9);
               }
            } else {
               var6 = SafeParcelReader.readInt(var1, var9);
            }
         } else {
            var3 = (ParcelFileDescriptor)SafeParcelReader.createParcelable(var1, var9, ParcelFileDescriptor.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new Contents(var3, var6, var7, (DriveId)var4, var8, (String)var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new Contents[var1];
   }
}
