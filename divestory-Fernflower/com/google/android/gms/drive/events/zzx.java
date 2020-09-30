package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveSpace;
import java.util.List;
import java.util.Locale;

public final class zzx extends AbstractSafeParcelable {
   public static final Creator<zzx> CREATOR = new zzy();
   private final List<DriveSpace> zzby;

   zzx(List<DriveSpace> var1) {
      this.zzby = var1;
   }

   public final boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         if (var1 == this) {
            return true;
         } else {
            zzx var2 = (zzx)var1;
            return Objects.equal(this.zzby, var2.zzby);
         }
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzby);
   }

   public final String toString() {
      return String.format(Locale.US, "TransferStateOptions[Spaces=%s]", this.zzby);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 2, this.zzby, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
