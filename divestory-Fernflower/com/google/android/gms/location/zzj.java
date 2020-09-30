package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzj extends AbstractSafeParcelable {
   public static final Creator<zzj> CREATOR = new zzk();
   private boolean zzt;
   private long zzu;
   private float zzv;
   private long zzw;
   private int zzx;

   public zzj() {
      this(true, 50L, 0.0F, Long.MAX_VALUE, Integer.MAX_VALUE);
   }

   zzj(boolean var1, long var2, float var4, long var5, int var7) {
      this.zzt = var1;
      this.zzu = var2;
      this.zzv = var4;
      this.zzw = var5;
      this.zzx = var7;
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof zzj)) {
         return false;
      } else {
         zzj var2 = (zzj)var1;
         return this.zzt == var2.zzt && this.zzu == var2.zzu && Float.compare(this.zzv, var2.zzv) == 0 && this.zzw == var2.zzw && this.zzx == var2.zzx;
      }
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzt, this.zzu, this.zzv, this.zzw, this.zzx);
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("DeviceOrientationRequest[mShouldUseMag=");
      var1.append(this.zzt);
      var1.append(" mMinimumSamplingPeriodMs=");
      var1.append(this.zzu);
      var1.append(" mSmallestAngleChangeRadians=");
      var1.append(this.zzv);
      long var2 = this.zzw;
      if (var2 != Long.MAX_VALUE) {
         long var4 = SystemClock.elapsedRealtime();
         var1.append(" expireIn=");
         var1.append(var2 - var4);
         var1.append("ms");
      }

      if (this.zzx != Integer.MAX_VALUE) {
         var1.append(" num=");
         var1.append(this.zzx);
      }

      var1.append(']');
      return var1.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeBoolean(var1, 1, this.zzt);
      SafeParcelWriter.writeLong(var1, 2, this.zzu);
      SafeParcelWriter.writeFloat(var1, 3, this.zzv);
      SafeParcelWriter.writeLong(var1, 4, this.zzw);
      SafeParcelWriter.writeInt(var1, 5, this.zzx);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
