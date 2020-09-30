package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;

public final class LocationSettingsStates extends AbstractSafeParcelable {
   public static final Creator<LocationSettingsStates> CREATOR = new zzai();
   private final boolean zzbn;
   private final boolean zzbo;
   private final boolean zzbp;
   private final boolean zzbq;
   private final boolean zzbr;
   private final boolean zzbs;

   public LocationSettingsStates(boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      this.zzbn = var1;
      this.zzbo = var2;
      this.zzbp = var3;
      this.zzbq = var4;
      this.zzbr = var5;
      this.zzbs = var6;
   }

   public static LocationSettingsStates fromIntent(Intent var0) {
      return (LocationSettingsStates)SafeParcelableSerializer.deserializeFromIntentExtra(var0, "com.google.android.gms.location.LOCATION_SETTINGS_STATES", CREATOR);
   }

   public final boolean isBlePresent() {
      return this.zzbs;
   }

   public final boolean isBleUsable() {
      return this.zzbp;
   }

   public final boolean isGpsPresent() {
      return this.zzbq;
   }

   public final boolean isGpsUsable() {
      return this.zzbn;
   }

   public final boolean isLocationPresent() {
      return this.zzbq || this.zzbr;
   }

   public final boolean isLocationUsable() {
      return this.zzbn || this.zzbo;
   }

   public final boolean isNetworkLocationPresent() {
      return this.zzbr;
   }

   public final boolean isNetworkLocationUsable() {
      return this.zzbo;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeBoolean(var1, 1, this.isGpsUsable());
      SafeParcelWriter.writeBoolean(var1, 2, this.isNetworkLocationUsable());
      SafeParcelWriter.writeBoolean(var1, 3, this.isBleUsable());
      SafeParcelWriter.writeBoolean(var1, 4, this.isGpsPresent());
      SafeParcelWriter.writeBoolean(var1, 5, this.isNetworkLocationPresent());
      SafeParcelWriter.writeBoolean(var1, 6, this.isBlePresent());
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
