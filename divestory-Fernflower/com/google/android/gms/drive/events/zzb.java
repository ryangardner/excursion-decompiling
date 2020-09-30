package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Locale;

public final class zzb extends AbstractSafeParcelable implements DriveEvent {
   public static final Creator<zzb> CREATOR = new zzc();
   private final zze zzbv;

   public zzb(zze var1) {
      this.zzbv = var1;
   }

   public final boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         if (var1 == this) {
            return true;
         } else {
            zzb var2 = (zzb)var1;
            return Objects.equal(this.zzbv, var2.zzbv);
         }
      } else {
         return false;
      }
   }

   public final int getType() {
      return 4;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzbv);
   }

   public final String toString() {
      return String.format(Locale.US, "ChangesAvailableEvent [changesAvailableOptions=%s]", this.zzbv);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 3, this.zzbv, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
