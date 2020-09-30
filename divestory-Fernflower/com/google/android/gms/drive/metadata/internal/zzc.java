package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.CustomPropertyKey;

public final class zzc extends AbstractSafeParcelable {
   public static final Creator<zzc> CREATOR = new zzd();
   final String value;
   final CustomPropertyKey zzje;

   public zzc(CustomPropertyKey var1, String var2) {
      Preconditions.checkNotNull(var1, "key");
      this.zzje = var1;
      this.value = var2;
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         if (var1 != null && var1.getClass() == this.getClass()) {
            zzc var2 = (zzc)var1;
            if (Objects.equal(this.zzje, var2.zzje) && Objects.equal(this.value, var2.value)) {
               return true;
            }
         }

         return false;
      }
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzje, this.value);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzje, var2, false);
      SafeParcelWriter.writeString(var1, 3, this.value, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
