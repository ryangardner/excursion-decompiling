package com.google.android.gms.drive;

import android.os.Parcel;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public abstract class zzu extends AbstractSafeParcelable {
   private transient volatile boolean zzbt = false;

   public void writeToParcel(Parcel var1, int var2) {
      Preconditions.checkState(this.zzbt ^ true);
      this.zzbt = true;
      this.zza(var1, var2);
   }

   protected abstract void zza(Parcel var1, int var2);
}
