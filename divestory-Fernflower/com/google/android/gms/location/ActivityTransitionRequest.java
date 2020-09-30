package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class ActivityTransitionRequest extends AbstractSafeParcelable {
   public static final Creator<ActivityTransitionRequest> CREATOR = new zzf();
   public static final Comparator<ActivityTransition> IS_SAME_TRANSITION = new zze();
   private final String tag;
   private final List<ActivityTransition> zzl;
   private final List<ClientIdentity> zzm;

   public ActivityTransitionRequest(List<ActivityTransition> var1) {
      this(var1, (String)null, (List)null);
   }

   public ActivityTransitionRequest(List<ActivityTransition> var1, String var2, List<ClientIdentity> var3) {
      Preconditions.checkNotNull(var1, "transitions can't be null");
      boolean var4;
      if (var1.size() > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "transitions can't be empty.");
      TreeSet var5 = new TreeSet(IS_SAME_TRANSITION);
      Iterator var6 = var1.iterator();

      while(var6.hasNext()) {
         ActivityTransition var7 = (ActivityTransition)var6.next();
         Preconditions.checkArgument(var5.add(var7), String.format("Found duplicated transition: %s.", var7));
      }

      this.zzl = Collections.unmodifiableList(var1);
      this.tag = var2;
      if (var3 == null) {
         var1 = Collections.emptyList();
      } else {
         var1 = Collections.unmodifiableList(var3);
      }

      this.zzm = var1;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         if (var1 != null && this.getClass() == var1.getClass()) {
            ActivityTransitionRequest var2 = (ActivityTransitionRequest)var1;
            if (Objects.equal(this.zzl, var2.zzl) && Objects.equal(this.tag, var2.tag) && Objects.equal(this.zzm, var2.zzm)) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      int var1 = this.zzl.hashCode();
      String var2 = this.tag;
      int var3 = 0;
      int var4;
      if (var2 != null) {
         var4 = var2.hashCode();
      } else {
         var4 = 0;
      }

      List var5 = this.zzm;
      if (var5 != null) {
         var3 = var5.hashCode();
      }

      return (var1 * 31 + var4) * 31 + var3;
   }

   public void serializeToIntentExtra(Intent var1) {
      SafeParcelableSerializer.serializeToIntentExtra(this, var1, "com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_REQUEST");
   }

   public String toString() {
      String var1 = String.valueOf(this.zzl);
      String var2 = this.tag;
      String var3 = String.valueOf(this.zzm);
      StringBuilder var4 = new StringBuilder(String.valueOf(var1).length() + 61 + String.valueOf(var2).length() + String.valueOf(var3).length());
      var4.append("ActivityTransitionRequest [mTransitions=");
      var4.append(var1);
      var4.append(", mTag='");
      var4.append(var2);
      var4.append('\'');
      var4.append(", mClients=");
      var4.append(var3);
      var4.append(']');
      return var4.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.zzl, false);
      SafeParcelWriter.writeString(var1, 2, this.tag, false);
      SafeParcelWriter.writeTypedList(var1, 3, this.zzm, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
