package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.location.LocationRequest;
import java.util.List;

public final class zzbe implements Creator<zzbd> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      Object var3 = zzbd.zzcd;
      LocationRequest var4 = null;
      Object var5 = var4;
      Object var6 = var4;
      boolean var7 = false;
      boolean var8 = false;
      boolean var9 = false;

      while(var1.dataPosition() < var2) {
         int var10 = SafeParcelReader.readHeader(var1);
         int var11 = SafeParcelReader.getFieldId(var10);
         if (var11 != 1) {
            switch(var11) {
            case 5:
               var3 = SafeParcelReader.createTypedList(var1, var10, ClientIdentity.CREATOR);
               break;
            case 6:
               var5 = SafeParcelReader.createString(var1, var10);
               break;
            case 7:
               var7 = SafeParcelReader.readBoolean(var1, var10);
               break;
            case 8:
               var8 = SafeParcelReader.readBoolean(var1, var10);
               break;
            case 9:
               var9 = SafeParcelReader.readBoolean(var1, var10);
               break;
            case 10:
               var6 = SafeParcelReader.createString(var1, var10);
               break;
            default:
               SafeParcelReader.skipUnknownField(var1, var10);
            }
         } else {
            var4 = (LocationRequest)SafeParcelReader.createParcelable(var1, var10, LocationRequest.CREATOR);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzbd(var4, (List)var3, (String)var5, var7, var8, var9, (String)var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzbd[var1];
   }
}
