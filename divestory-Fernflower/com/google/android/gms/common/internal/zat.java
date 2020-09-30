package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zat implements Creator<zar> {
   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      Account var3 = null;
      GoogleSignInAccount var4 = null;
      int var5 = 0;
      int var6 = 0;

      while(var1.dataPosition() < var2) {
         int var7 = SafeParcelReader.readHeader(var1);
         int var8 = SafeParcelReader.getFieldId(var7);
         if (var8 != 1) {
            if (var8 != 2) {
               if (var8 != 3) {
                  if (var8 != 4) {
                     SafeParcelReader.skipUnknownField(var1, var7);
                  } else {
                     var4 = (GoogleSignInAccount)SafeParcelReader.createParcelable(var1, var7, GoogleSignInAccount.CREATOR);
                  }
               } else {
                  var6 = SafeParcelReader.readInt(var1, var7);
               }
            } else {
               var3 = (Account)SafeParcelReader.createParcelable(var1, var7, Account.CREATOR);
            }
         } else {
            var5 = SafeParcelReader.readInt(var1, var7);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zar(var5, var3, var6, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zar[var1];
   }
}
