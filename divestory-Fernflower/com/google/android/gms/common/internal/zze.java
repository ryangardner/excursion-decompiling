package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zze implements Creator<GetServiceRequest> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      Object var6 = var3;
      Object var7 = var3;
      Object var8 = var3;
      Object var9 = var3;
      int var10 = 0;
      int var11 = 0;
      int var12 = 0;
      boolean var13 = false;
      int var14 = 0;
      boolean var15 = false;

      while(var1.dataPosition() < var2) {
         int var16 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var16)) {
         case 1:
            var10 = SafeParcelReader.readInt(var1, var16);
            break;
         case 2:
            var11 = SafeParcelReader.readInt(var1, var16);
            break;
         case 3:
            var12 = SafeParcelReader.readInt(var1, var16);
            break;
         case 4:
            var3 = SafeParcelReader.createString(var1, var16);
            break;
         case 5:
            var4 = SafeParcelReader.readIBinder(var1, var16);
            break;
         case 6:
            var5 = (Scope[])SafeParcelReader.createTypedArray(var1, var16, Scope.CREATOR);
            break;
         case 7:
            var6 = SafeParcelReader.createBundle(var1, var16);
            break;
         case 8:
            var7 = (Account)SafeParcelReader.createParcelable(var1, var16, Account.CREATOR);
            break;
         case 9:
         default:
            SafeParcelReader.skipUnknownField(var1, var16);
            break;
         case 10:
            var8 = (Feature[])SafeParcelReader.createTypedArray(var1, var16, Feature.CREATOR);
            break;
         case 11:
            var9 = (Feature[])SafeParcelReader.createTypedArray(var1, var16, Feature.CREATOR);
            break;
         case 12:
            var13 = SafeParcelReader.readBoolean(var1, var16);
            break;
         case 13:
            var14 = SafeParcelReader.readInt(var1, var16);
            break;
         case 14:
            var15 = SafeParcelReader.readBoolean(var1, var16);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new GetServiceRequest(var10, var11, var12, var3, (IBinder)var4, (Scope[])var5, (Bundle)var6, (Account)var7, (Feature[])var8, (Feature[])var9, var13, var14, var15);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new GetServiceRequest[var1];
   }
}
