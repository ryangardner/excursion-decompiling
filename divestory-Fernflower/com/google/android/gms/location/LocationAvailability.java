package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;

public final class LocationAvailability extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<LocationAvailability> CREATOR = new zzaa();
   @Deprecated
   private int zzar;
   @Deprecated
   private int zzas;
   private long zzat;
   private int zzau;
   private zzaj[] zzav;

   LocationAvailability(int var1, int var2, int var3, long var4, zzaj[] var6) {
      this.zzau = var1;
      this.zzar = var2;
      this.zzas = var3;
      this.zzat = var4;
      this.zzav = var6;
   }

   public static LocationAvailability extractLocationAvailability(Intent var0) {
      return !hasLocationAvailability(var0) ? null : (LocationAvailability)var0.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
   }

   public static boolean hasLocationAvailability(Intent var0) {
      return var0 == null ? false : var0.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         if (var1 != null && this.getClass() == var1.getClass()) {
            LocationAvailability var2 = (LocationAvailability)var1;
            if (this.zzar == var2.zzar && this.zzas == var2.zzas && this.zzat == var2.zzat && this.zzau == var2.zzau && Arrays.equals(this.zzav, var2.zzav)) {
               return true;
            }
         }

         return false;
      }
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzau, this.zzar, this.zzas, this.zzat, this.zzav);
   }

   public final boolean isLocationAvailable() {
      return this.zzau < 1000;
   }

   public final String toString() {
      boolean var1 = this.isLocationAvailable();
      StringBuilder var2 = new StringBuilder(48);
      var2.append("LocationAvailability[isLocationAvailable: ");
      var2.append(var1);
      var2.append("]");
      return var2.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zzar);
      SafeParcelWriter.writeInt(var1, 2, this.zzas);
      SafeParcelWriter.writeLong(var1, 3, this.zzat);
      SafeParcelWriter.writeInt(var1, 4, this.zzau);
      SafeParcelWriter.writeTypedArray(var1, 5, this.zzav, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
