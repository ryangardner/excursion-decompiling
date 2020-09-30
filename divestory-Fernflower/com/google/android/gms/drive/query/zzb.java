package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.DriveSpace;
import com.google.android.gms.drive.query.internal.zzr;
import java.util.List;

public final class zzb implements Creator<Query> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      zzr var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      Object var6 = var3;
      Object var7 = var3;
      boolean var8 = false;
      boolean var9 = false;

      while(var1.dataPosition() < var2) {
         int var10 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var10)) {
         case 1:
            var3 = (zzr)SafeParcelReader.createParcelable(var1, var10, zzr.CREATOR);
            break;
         case 2:
         default:
            SafeParcelReader.skipUnknownField(var1, var10);
            break;
         case 3:
            var4 = SafeParcelReader.createString(var1, var10);
            break;
         case 4:
            var5 = (SortOrder)SafeParcelReader.createParcelable(var1, var10, SortOrder.CREATOR);
            break;
         case 5:
            var6 = SafeParcelReader.createStringList(var1, var10);
            break;
         case 6:
            var8 = SafeParcelReader.readBoolean(var1, var10);
            break;
         case 7:
            var7 = SafeParcelReader.createTypedList(var1, var10, DriveSpace.CREATOR);
            break;
         case 8:
            var9 = SafeParcelReader.readBoolean(var1, var10);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new Query(var3, (String)var4, (SortOrder)var5, (List)var6, var8, (List)var7, var9);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new Query[var1];
   }
}
