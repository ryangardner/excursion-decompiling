package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.List;

public final class zab implements Creator<GoogleSignInAccount> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var3 = null;
      String var4 = var3;
      String var5 = var3;
      String var6 = var3;
      Object var7 = var3;
      String var8 = var3;
      String var9 = var3;
      Object var10 = var3;
      String var11 = var3;
      String var12 = var3;
      long var13 = 0L;
      int var15 = 0;

      while(var1.dataPosition() < var2) {
         int var16 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var16)) {
         case 1:
            var15 = SafeParcelReader.readInt(var1, var16);
            break;
         case 2:
            var3 = SafeParcelReader.createString(var1, var16);
            break;
         case 3:
            var4 = SafeParcelReader.createString(var1, var16);
            break;
         case 4:
            var5 = SafeParcelReader.createString(var1, var16);
            break;
         case 5:
            var6 = SafeParcelReader.createString(var1, var16);
            break;
         case 6:
            var7 = (Uri)SafeParcelReader.createParcelable(var1, var16, Uri.CREATOR);
            break;
         case 7:
            var8 = SafeParcelReader.createString(var1, var16);
            break;
         case 8:
            var13 = SafeParcelReader.readLong(var1, var16);
            break;
         case 9:
            var9 = SafeParcelReader.createString(var1, var16);
            break;
         case 10:
            var10 = SafeParcelReader.createTypedList(var1, var16, Scope.CREATOR);
            break;
         case 11:
            var11 = SafeParcelReader.createString(var1, var16);
            break;
         case 12:
            var12 = SafeParcelReader.createString(var1, var16);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var16);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new GoogleSignInAccount(var15, var3, var4, var5, var6, (Uri)var7, var8, var13, var9, (List)var10, var11, var12);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new GoogleSignInAccount[var1];
   }
}
