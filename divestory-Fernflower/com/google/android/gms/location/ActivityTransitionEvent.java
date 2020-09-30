package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class ActivityTransitionEvent extends AbstractSafeParcelable {
   public static final Creator<ActivityTransitionEvent> CREATOR = new zzd();
   private final int zzi;
   private final int zzj;
   private final long zzk;

   public ActivityTransitionEvent(int var1, int var2, long var3) {
      DetectedActivity.zzb(var1);
      ActivityTransition.zza(var2);
      this.zzi = var1;
      this.zzj = var2;
      this.zzk = var3;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ActivityTransitionEvent)) {
         return false;
      } else {
         ActivityTransitionEvent var2 = (ActivityTransitionEvent)var1;
         return this.zzi == var2.zzi && this.zzj == var2.zzj && this.zzk == var2.zzk;
      }
   }

   public int getActivityType() {
      return this.zzi;
   }

   public long getElapsedRealTimeNanos() {
      return this.zzk;
   }

   public int getTransitionType() {
      return this.zzj;
   }

   public int hashCode() {
      return Objects.hashCode(this.zzi, this.zzj, this.zzk);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      int var2 = this.zzi;
      StringBuilder var3 = new StringBuilder(24);
      var3.append("ActivityType ");
      var3.append(var2);
      var1.append(var3.toString());
      var1.append(" ");
      var2 = this.zzj;
      var3 = new StringBuilder(26);
      var3.append("TransitionType ");
      var3.append(var2);
      var1.append(var3.toString());
      var1.append(" ");
      long var4 = this.zzk;
      var3 = new StringBuilder(41);
      var3.append("ElapsedRealTimeNanos ");
      var3.append(var4);
      var1.append(var3.toString());
      return var1.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.getActivityType());
      SafeParcelWriter.writeInt(var1, 2, this.getTransitionType());
      SafeParcelWriter.writeLong(var1, 3, this.getElapsedRealTimeNanos());
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
