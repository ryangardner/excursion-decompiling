package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zar;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zak extends AbstractSafeParcelable {
   public static final Creator<zak> CREATOR = new zaj();
   private final int zaa;
   private final zar zab;

   zak(int var1, zar var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   public zak(zar var1) {
      this(1, var1);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeParcelable(var1, 2, this.zab, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
