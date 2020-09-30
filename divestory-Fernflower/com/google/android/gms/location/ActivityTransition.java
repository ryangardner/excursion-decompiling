package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ActivityTransition extends AbstractSafeParcelable {
   public static final int ACTIVITY_TRANSITION_ENTER = 0;
   public static final int ACTIVITY_TRANSITION_EXIT = 1;
   public static final Creator<ActivityTransition> CREATOR = new zzc();
   private final int zzi;
   private final int zzj;

   ActivityTransition(int var1, int var2) {
      this.zzi = var1;
      this.zzj = var2;
   }

   public static void zza(int var0) {
      boolean var1 = true;
      if (var0 < 0 || var0 > 1) {
         var1 = false;
      }

      StringBuilder var2 = new StringBuilder(41);
      var2.append("Transition type ");
      var2.append(var0);
      var2.append(" is not valid.");
      Preconditions.checkArgument(var1, var2.toString());
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ActivityTransition)) {
         return false;
      } else {
         ActivityTransition var2 = (ActivityTransition)var1;
         return this.zzi == var2.zzi && this.zzj == var2.zzj;
      }
   }

   public int getActivityType() {
      return this.zzi;
   }

   public int getTransitionType() {
      return this.zzj;
   }

   public int hashCode() {
      return Objects.hashCode(this.zzi, this.zzj);
   }

   public String toString() {
      int var1 = this.zzi;
      int var2 = this.zzj;
      StringBuilder var3 = new StringBuilder(75);
      var3.append("ActivityTransition [mActivityType=");
      var3.append(var1);
      var3.append(", mTransitionType=");
      var3.append(var2);
      var3.append(']');
      return var3.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.getActivityType());
      SafeParcelWriter.writeInt(var1, 2, this.getTransitionType());
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   public static class Builder {
      private int zzi = -1;
      private int zzj = -1;

      public ActivityTransition build() {
         int var1 = this.zzi;
         boolean var2 = true;
         boolean var3;
         if (var1 != -1) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkState(var3, "Activity type not set.");
         if (this.zzj != -1) {
            var3 = var2;
         } else {
            var3 = false;
         }

         Preconditions.checkState(var3, "Activity transition type not set.");
         return new ActivityTransition(this.zzi, this.zzj);
      }

      public ActivityTransition.Builder setActivityTransition(int var1) {
         ActivityTransition.zza(var1);
         this.zzj = var1;
         return this;
      }

      public ActivityTransition.Builder setActivityType(int var1) {
         DetectedActivity.zzb(var1);
         this.zzi = var1;
         return this;
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface SupportedActivityTransition {
   }
}
