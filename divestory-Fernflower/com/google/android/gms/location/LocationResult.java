package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationResult extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<LocationResult> CREATOR = new zzac();
   static final List<Location> zzbb = Collections.emptyList();
   private final List<Location> zzbc;

   LocationResult(List<Location> var1) {
      this.zzbc = var1;
   }

   public static LocationResult create(List<Location> var0) {
      List var1 = var0;
      if (var0 == null) {
         var1 = zzbb;
      }

      return new LocationResult(var1);
   }

   public static LocationResult extractResult(Intent var0) {
      return !hasResult(var0) ? null : (LocationResult)var0.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
   }

   public static boolean hasResult(Intent var0) {
      return var0 == null ? false : var0.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
   }

   public final boolean equals(Object var1) {
      if (!(var1 instanceof LocationResult)) {
         return false;
      } else {
         LocationResult var5 = (LocationResult)var1;
         if (var5.zzbc.size() != this.zzbc.size()) {
            return false;
         } else {
            Iterator var6 = var5.zzbc.iterator();
            Iterator var2 = this.zzbc.iterator();

            Location var3;
            Location var4;
            do {
               if (!var6.hasNext()) {
                  return true;
               }

               var3 = (Location)var2.next();
               var4 = (Location)var6.next();
            } while(var3.getTime() == var4.getTime());

            return false;
         }
      }
   }

   public final Location getLastLocation() {
      int var1 = this.zzbc.size();
      return var1 == 0 ? null : (Location)this.zzbc.get(var1 - 1);
   }

   public final List<Location> getLocations() {
      return this.zzbc;
   }

   public final int hashCode() {
      Iterator var1 = this.zzbc.iterator();

      int var2;
      long var3;
      for(var2 = 17; var1.hasNext(); var2 = var2 * 31 + (int)(var3 ^ var3 >>> 32)) {
         var3 = ((Location)var1.next()).getTime();
      }

      return var2;
   }

   public final String toString() {
      String var1 = String.valueOf(this.zzbc);
      StringBuilder var2 = new StringBuilder(String.valueOf(var1).length() + 27);
      var2.append("LocationResult[locations: ");
      var2.append(var1);
      var2.append("]");
      return var2.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.getLocations(), false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
