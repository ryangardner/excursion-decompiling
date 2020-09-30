package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzaj extends AbstractSafeParcelable {
   public static final Creator<zzaj> CREATOR = new zzak();
   private final int zzar;
   private final int zzas;
   private final long zzat;
   private final long zzbt;

   zzaj(int var1, int var2, long var3, long var5) {
      this.zzas = var1;
      this.zzar = var2;
      this.zzbt = var3;
      this.zzat = var5;
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         if (var1 != null && this.getClass() == var1.getClass()) {
            zzaj var2 = (zzaj)var1;
            if (this.zzas == var2.zzas && this.zzar == var2.zzar && this.zzbt == var2.zzbt && this.zzat == var2.zzat) {
               return true;
            }
         }

         return false;
      }
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzar, this.zzas, this.zzat, this.zzbt);
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder("NetworkLocationStatus:");
      var1.append(" Wifi status: ");
      var1.append(this.zzas);
      var1.append(" Cell status: ");
      var1.append(this.zzar);
      var1.append(" elapsed time NS: ");
      var1.append(this.zzat);
      var1.append(" system time ms: ");
      var1.append(this.zzbt);
      return var1.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zzas);
      SafeParcelWriter.writeInt(var1, 2, this.zzar);
      SafeParcelWriter.writeLong(var1, 3, this.zzbt);
      SafeParcelWriter.writeLong(var1, 4, this.zzat);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
