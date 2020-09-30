package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.List;

public final class zzv extends AbstractSafeParcelable implements DriveEvent {
   public static final Creator<zzv> CREATOR = new zzw();
   private final List<com.google.android.gms.internal.drive.zzh> zzcu;

   public zzv(List<com.google.android.gms.internal.drive.zzh> var1) {
      this.zzcu = var1;
   }

   public final boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         if (var1 == this) {
            return true;
         } else {
            zzv var2 = (zzv)var1;
            return Objects.equal(this.zzcu, var2.zzcu);
         }
      } else {
         return false;
      }
   }

   public final int getType() {
      return 7;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzcu);
   }

   public final String toString() {
      return String.format("TransferStateEvent[%s]", TextUtils.join("','", this.zzcu));
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 3, this.zzcu, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
