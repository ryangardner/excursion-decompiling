package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzs implements Creator<zzr> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = null;
      String var4 = var3;
      String var5 = var3;
      int var6 = 0;
      int var7 = 0;
      boolean var8 = false;

      while(var1.dataPosition() < var2) {
         int var9 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var9)) {
         case 2:
            var3 = SafeParcelReader.createString(var1, var9);
            break;
         case 3:
            var6 = SafeParcelReader.readInt(var1, var9);
            break;
         case 4:
            var4 = SafeParcelReader.createString(var1, var9);
            break;
         case 5:
            var5 = SafeParcelReader.createString(var1, var9);
            break;
         case 6:
            var7 = SafeParcelReader.readInt(var1, var9);
            break;
         case 7:
            var8 = SafeParcelReader.readBoolean(var1, var9);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var9);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzr(var3, var6, var4, var5, var7, var8);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzr[var1];
   }
}
