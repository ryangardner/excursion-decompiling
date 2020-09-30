package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzr extends AbstractSafeParcelable {
   public static final Creator<zzr> CREATOR = new zzs();
   private final int mode;

   public zzr(int var1) {
      boolean var2;
      if (var1 != 536870912 && var1 != 805306368) {
         var2 = false;
      } else {
         var2 = true;
      }

      Preconditions.checkArgument(var2, "Cannot create a new read-only contents!");
      this.mode = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.mode);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
