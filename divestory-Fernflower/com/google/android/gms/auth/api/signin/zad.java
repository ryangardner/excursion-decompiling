package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

public final class zad implements Creator<GoogleSignInOptions> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      ArrayList var3 = null;
      Object var4 = var3;
      Object var5 = var3;
      Object var6 = var3;
      ArrayList var7 = var3;
      Object var8 = var3;
      int var9 = 0;
      boolean var10 = false;
      boolean var11 = false;
      boolean var12 = false;

      while(var1.dataPosition() < var2) {
         int var13 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var13)) {
         case 1:
            var9 = SafeParcelReader.readInt(var1, var13);
            break;
         case 2:
            var3 = SafeParcelReader.createTypedList(var1, var13, Scope.CREATOR);
            break;
         case 3:
            var4 = (Account)SafeParcelReader.createParcelable(var1, var13, Account.CREATOR);
            break;
         case 4:
            var10 = SafeParcelReader.readBoolean(var1, var13);
            break;
         case 5:
            var11 = SafeParcelReader.readBoolean(var1, var13);
            break;
         case 6:
            var12 = SafeParcelReader.readBoolean(var1, var13);
            break;
         case 7:
            var5 = SafeParcelReader.createString(var1, var13);
            break;
         case 8:
            var6 = SafeParcelReader.createString(var1, var13);
            break;
         case 9:
            var7 = SafeParcelReader.createTypedList(var1, var13, GoogleSignInOptionsExtensionParcelable.CREATOR);
            break;
         case 10:
            var8 = SafeParcelReader.createString(var1, var13);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var13);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new GoogleSignInOptions(var9, var3, (Account)var4, var10, var11, var12, (String)var5, (String)var6, var7, (String)var8);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new GoogleSignInOptions[var1];
   }
}
