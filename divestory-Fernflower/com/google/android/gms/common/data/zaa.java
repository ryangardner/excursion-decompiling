package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zaa implements Creator<BitmapTeleporter> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      int var3 = 0;
      ParcelFileDescriptor var4 = null;
      int var5 = 0;

      while(var1.dataPosition() < var2) {
         int var6 = SafeParcelReader.readHeader(var1);
         int var7 = SafeParcelReader.getFieldId(var6);
         if (var7 != 1) {
            if (var7 != 2) {
               if (var7 != 3) {
                  SafeParcelReader.skipUnknownField(var1, var6);
               } else {
                  var5 = SafeParcelReader.readInt(var1, var6);
               }
            } else {
               var4 = (ParcelFileDescriptor)SafeParcelReader.createParcelable(var1, var6, ParcelFileDescriptor.CREATOR);
            }
         } else {
            var3 = SafeParcelReader.readInt(var1, var6);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new BitmapTeleporter(var3, var4, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new BitmapTeleporter[var1];
   }
}
