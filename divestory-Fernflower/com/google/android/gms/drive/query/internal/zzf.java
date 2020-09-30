package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Locale;

public final class zzf extends AbstractSafeParcelable {
   public static final Creator<zzf> CREATOR = new zzg();
   private final String fieldName;
   private final boolean zzmc;

   public zzf(String var1, boolean var2) {
      this.fieldName = var1;
      this.zzmc = var2;
   }

   public final String toString() {
      Locale var1 = Locale.US;
      String var2 = this.fieldName;
      String var3;
      if (this.zzmc) {
         var3 = "ASC";
      } else {
         var3 = "DESC";
      }

      return String.format(var1, "FieldWithSortOrder[%s %s]", var2, var3);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.fieldName, false);
      SafeParcelWriter.writeBoolean(var1, 2, this.zzmc);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
