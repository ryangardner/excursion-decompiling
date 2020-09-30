package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zar extends AbstractSafeParcelable {
   public static final Creator<zar> CREATOR = new zat();
   private final int zaa;
   private final Account zab;
   private final int zac;
   private final GoogleSignInAccount zad;

   zar(int var1, Account var2, int var3, GoogleSignInAccount var4) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
      this.zad = var4;
   }

   public zar(Account var1, int var2, GoogleSignInAccount var3) {
      this(2, var1, var2, var3);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeParcelable(var1, 2, this.zab, var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.zac);
      SafeParcelWriter.writeParcelable(var1, 4, this.zad, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
