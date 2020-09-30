package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzbi implements Creator<zzbh> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      double var3 = 0.0D;
      double var5 = var3;
      String var7 = null;
      long var8 = 0L;
      int var10 = 0;
      byte var11 = 0;
      float var12 = 0.0F;
      int var13 = 0;
      int var14 = -1;
      short var15 = var11;

      while(var1.dataPosition() < var2) {
         int var16 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var16)) {
         case 1:
            var7 = SafeParcelReader.createString(var1, var16);
            break;
         case 2:
            var8 = SafeParcelReader.readLong(var1, var16);
            break;
         case 3:
            short var17 = SafeParcelReader.readShort(var1, var16);
            var15 = var17;
            break;
         case 4:
            var3 = SafeParcelReader.readDouble(var1, var16);
            break;
         case 5:
            var5 = SafeParcelReader.readDouble(var1, var16);
            break;
         case 6:
            var12 = SafeParcelReader.readFloat(var1, var16);
            break;
         case 7:
            var10 = SafeParcelReader.readInt(var1, var16);
            break;
         case 8:
            var13 = SafeParcelReader.readInt(var1, var16);
            break;
         case 9:
            var14 = SafeParcelReader.readInt(var1, var16);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var16);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzbh(var7, var10, var15, var3, var5, var12, var8, var13, var14);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzbh[var1];
   }
}
