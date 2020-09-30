package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zab extends AbstractSafeParcelable implements Result {
   public static final Creator<zab> CREATOR = new zaa();
   private final int zaa;
   private int zab;
   private Intent zac;

   public zab() {
      this(0, (Intent)null);
   }

   zab(int var1, int var2, Intent var3) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
   }

   private zab(int var1, Intent var2) {
      this(2, 0, (Intent)null);
   }

   public final Status getStatus() {
      return this.zab == 0 ? Status.RESULT_SUCCESS : Status.RESULT_CANCELED;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zaa);
      SafeParcelWriter.writeInt(var1, 2, this.zab);
      SafeParcelWriter.writeParcelable(var1, 3, this.zac, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
