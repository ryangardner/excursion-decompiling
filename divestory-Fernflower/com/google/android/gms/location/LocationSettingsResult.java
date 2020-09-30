package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class LocationSettingsResult extends AbstractSafeParcelable implements Result {
   public static final Creator<LocationSettingsResult> CREATOR = new zzah();
   private final Status zzbl;
   private final LocationSettingsStates zzbm;

   public LocationSettingsResult(Status var1) {
      this(var1, (LocationSettingsStates)null);
   }

   public LocationSettingsResult(Status var1, LocationSettingsStates var2) {
      this.zzbl = var1;
      this.zzbm = var2;
   }

   public final LocationSettingsStates getLocationSettingsStates() {
      return this.zzbm;
   }

   public final Status getStatus() {
      return this.zzbl;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.getStatus(), var2, false);
      SafeParcelWriter.writeParcelable(var1, 2, this.getLocationSettingsStates(), var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
