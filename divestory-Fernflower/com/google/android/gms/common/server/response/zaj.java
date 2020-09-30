package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zaj implements Creator<FastJsonResponse.Field> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = null;
      String var4 = var3;
      Object var5 = var3;
      int var6 = 0;
      int var7 = 0;
      boolean var8 = false;
      int var9 = 0;
      boolean var10 = false;
      int var11 = 0;

      while(var1.dataPosition() < var2) {
         int var12 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var12)) {
         case 1:
            var6 = SafeParcelReader.readInt(var1, var12);
            break;
         case 2:
            var7 = SafeParcelReader.readInt(var1, var12);
            break;
         case 3:
            var8 = SafeParcelReader.readBoolean(var1, var12);
            break;
         case 4:
            var9 = SafeParcelReader.readInt(var1, var12);
            break;
         case 5:
            var10 = SafeParcelReader.readBoolean(var1, var12);
            break;
         case 6:
            var3 = SafeParcelReader.createString(var1, var12);
            break;
         case 7:
            var11 = SafeParcelReader.readInt(var1, var12);
            break;
         case 8:
            var4 = SafeParcelReader.createString(var1, var12);
            break;
         case 9:
            var5 = (com.google.android.gms.common.server.converter.zaa)SafeParcelReader.createParcelable(var1, var12, com.google.android.gms.common.server.converter.zaa.CREATOR);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var12);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new FastJsonResponse.Field(var6, var7, var8, var9, var10, var3, var11, var4, (com.google.android.gms.common.server.converter.zaa)var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new FastJsonResponse.Field[var1];
   }
}
