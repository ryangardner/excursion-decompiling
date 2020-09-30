package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzgg implements Creator<zzgf> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      ParcelFileDescriptor var3 = null;
      IBinder var4 = null;
      Object var5 = var4;

      while(var1.dataPosition() < var2) {
         int var6 = SafeParcelReader.readHeader(var1);
         int var7 = SafeParcelReader.getFieldId(var6);
         if (var7 != 2) {
            if (var7 != 3) {
               if (var7 != 4) {
                  SafeParcelReader.skipUnknownField(var1, var6);
               } else {
                  var5 = SafeParcelReader.createString(var1, var6);
               }
            } else {
               var4 = SafeParcelReader.readIBinder(var1, var6);
            }
         } else {
            var3 = (ParcelFileDescriptor)SafeParcelReader.createParcelable(var1, var6, ParcelFileDescriptor.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzgf(var3, var4, (String)var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzgf[var1];
   }
}
