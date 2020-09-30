package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzr extends AbstractSafeParcelable implements DriveEvent {
   public static final Creator<zzr> CREATOR = new zzs();
   private final com.google.android.gms.internal.drive.zzh zzcs;

   public zzr(com.google.android.gms.internal.drive.zzh var1) {
      this.zzcs = var1;
   }

   public final boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         if (var1 == this) {
            return true;
         } else {
            zzr var2 = (zzr)var1;
            return Objects.equal(this.zzcs, var2.zzcs);
         }
      } else {
         return false;
      }
   }

   public final int getType() {
      return 8;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzcs);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzcs, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final com.google.android.gms.internal.drive.zzh zzac() {
      return this.zzcs;
   }
}
