package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.util.Collections;
import java.util.List;

public class ActivityTransitionResult extends AbstractSafeParcelable {
   public static final Creator<ActivityTransitionResult> CREATOR = new zzg();
   private final List<ActivityTransitionEvent> zzn;

   public ActivityTransitionResult(List<ActivityTransitionEvent> var1) {
      Preconditions.checkNotNull(var1, "transitionEvents list can't be null.");
      if (!var1.isEmpty()) {
         for(int var2 = 1; var2 < var1.size(); ++var2) {
            boolean var3;
            if (((ActivityTransitionEvent)var1.get(var2)).getElapsedRealTimeNanos() >= ((ActivityTransitionEvent)var1.get(var2 - 1)).getElapsedRealTimeNanos()) {
               var3 = true;
            } else {
               var3 = false;
            }

            Preconditions.checkArgument(var3);
         }
      }

      this.zzn = Collections.unmodifiableList(var1);
   }

   public static ActivityTransitionResult extractResult(Intent var0) {
      return !hasResult(var0) ? null : (ActivityTransitionResult)SafeParcelableSerializer.deserializeFromIntentExtra(var0, "com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_RESULT", CREATOR);
   }

   public static boolean hasResult(Intent var0) {
      return var0 == null ? false : var0.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_RESULT");
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return var1 != null && this.getClass() == var1.getClass() ? this.zzn.equals(((ActivityTransitionResult)var1).zzn) : false;
      }
   }

   public List<ActivityTransitionEvent> getTransitionEvents() {
      return this.zzn;
   }

   public int hashCode() {
      return this.zzn.hashCode();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.getTransitionEvents(), false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
