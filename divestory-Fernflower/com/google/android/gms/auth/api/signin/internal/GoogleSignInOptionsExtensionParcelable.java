package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class GoogleSignInOptionsExtensionParcelable extends AbstractSafeParcelable {
   public static final Creator<GoogleSignInOptionsExtensionParcelable> CREATOR = new zaa();
   private final int zaa;
   private int zab;
   private Bundle zac;

   GoogleSignInOptionsExtensionParcelable(int var1, int var2, Bundle var3) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
   }

   public GoogleSignInOptionsExtensionParcelable(GoogleSignInOptionsExtension var1) {
      this(1, var1.getExtensionType(), var1.toBundle());
   }

   public int getType() {
      return this.zab;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeInt(var1, 2, this.getType());
      SafeParcelWriter.writeBundle(var1, 3, this.zac, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
