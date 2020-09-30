package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class Feature extends AbstractSafeParcelable {
   public static final Creator<Feature> CREATOR = new zzb();
   private final String zza;
   @Deprecated
   private final int zzb;
   private final long zzc;

   public Feature(String var1, int var2, long var3) {
      this.zza = var1;
      this.zzb = var2;
      this.zzc = var3;
   }

   public Feature(String var1, long var2) {
      this.zza = var1;
      this.zzc = var2;
      this.zzb = -1;
   }

   public boolean equals(Object var1) {
      if (var1 instanceof Feature) {
         Feature var2 = (Feature)var1;
         if ((this.getName() != null && this.getName().equals(var2.getName()) || this.getName() == null && var2.getName() == null) && this.getVersion() == var2.getVersion()) {
            return true;
         }
      }

      return false;
   }

   public String getName() {
      return this.zza;
   }

   public long getVersion() {
      long var1 = this.zzc;
      long var3 = var1;
      if (var1 == -1L) {
         var3 = (long)this.zzb;
      }

      return var3;
   }

   public int hashCode() {
      return Objects.hashCode(this.getName(), this.getVersion());
   }

   public String toString() {
      return Objects.toStringHelper(this).add("name", this.getName()).add("version", this.getVersion()).toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.getName(), false);
      SafeParcelWriter.writeInt(var1, 2, this.zzb);
      SafeParcelWriter.writeLong(var1, 3, this.getVersion());
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
