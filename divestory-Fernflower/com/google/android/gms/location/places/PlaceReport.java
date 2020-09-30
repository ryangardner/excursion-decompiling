package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class PlaceReport extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<PlaceReport> CREATOR = new zza();
   private final String tag;
   private final int versionCode;
   private final String zza;
   private final String zzb;

   PlaceReport(int var1, String var2, String var3, String var4) {
      this.versionCode = var1;
      this.zza = var2;
      this.tag = var3;
      this.zzb = var4;
   }

   public static PlaceReport create(String var0, String var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotEmpty(var1);
      Preconditions.checkNotEmpty("unknown");
      Preconditions.checkArgument(true, "Invalid source");
      return new PlaceReport(1, var0, var1, "unknown");
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof PlaceReport)) {
         return false;
      } else {
         PlaceReport var2 = (PlaceReport)var1;
         return Objects.equal(this.zza, var2.zza) && Objects.equal(this.tag, var2.tag) && Objects.equal(this.zzb, var2.zzb);
      }
   }

   public String getPlaceId() {
      return this.zza;
   }

   public String getTag() {
      return this.tag;
   }

   public int hashCode() {
      return Objects.hashCode(this.zza, this.tag, this.zzb);
   }

   public String toString() {
      Objects.ToStringHelper var1 = Objects.toStringHelper(this);
      var1.add("placeId", this.zza);
      var1.add("tag", this.tag);
      if (!"unknown".equals(this.zzb)) {
         var1.add("source", this.zzb);
      }

      return var1.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.versionCode);
      SafeParcelWriter.writeString(var1, 2, this.getPlaceId(), false);
      SafeParcelWriter.writeString(var1, 3, this.getTag(), false);
      SafeParcelWriter.writeString(var1, 4, this.zzb, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
