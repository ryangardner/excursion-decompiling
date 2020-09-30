package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zau extends AbstractSafeParcelable {
   public static final Creator<zau> CREATOR = new zax();
   private final int zaa;
   private final int zab;
   private final int zac;
   @Deprecated
   private final Scope[] zad;

   zau(int var1, int var2, int var3, Scope[] var4) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
      this.zad = var4;
   }

   public zau(int var1, int var2, Scope[] var3) {
      this(1, var1, var2, (Scope[])null);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeInt(var1, 2, this.zab);
      SafeParcelWriter.writeInt(var1, 3, this.zac);
      SafeParcelWriter.writeTypedArray(var1, 4, this.zad, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
