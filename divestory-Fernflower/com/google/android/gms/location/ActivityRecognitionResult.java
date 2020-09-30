package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ActivityRecognitionResult extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<ActivityRecognitionResult> CREATOR = new zzb();
   private Bundle extras;
   private List<DetectedActivity> zze;
   private long zzf;
   private long zzg;
   private int zzh;

   public ActivityRecognitionResult(DetectedActivity var1, long var2, long var4) {
      this((DetectedActivity)var1, var2, var4, 0, (Bundle)null);
   }

   private ActivityRecognitionResult(DetectedActivity var1, long var2, long var4, int var6, Bundle var7) {
      this((List)Collections.singletonList(var1), var2, var4, 0, (Bundle)null);
   }

   public ActivityRecognitionResult(List<DetectedActivity> var1, long var2, long var4) {
      this((List)var1, var2, var4, 0, (Bundle)null);
   }

   public ActivityRecognitionResult(List<DetectedActivity> var1, long var2, long var4, int var6, Bundle var7) {
      boolean var8 = true;
      boolean var9;
      if (var1 != null && var1.size() > 0) {
         var9 = true;
      } else {
         var9 = false;
      }

      Preconditions.checkArgument(var9, "Must have at least 1 detected activity");
      if (var2 > 0L && var4 > 0L) {
         var9 = var8;
      } else {
         var9 = false;
      }

      Preconditions.checkArgument(var9, "Must set times");
      this.zze = var1;
      this.zzf = var2;
      this.zzg = var4;
      this.zzh = var6;
      this.extras = var7;
   }

   public static ActivityRecognitionResult extractResult(Intent var0) {
      ActivityRecognitionResult var3;
      label30: {
         Object var1;
         label29: {
            if (hasResult(var0)) {
               var1 = var0.getExtras().get("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
               if (var1 instanceof byte[]) {
                  var1 = SafeParcelableSerializer.deserializeFromBytes((byte[])var1, CREATOR);
                  break label29;
               }

               if (var1 instanceof ActivityRecognitionResult) {
                  break label29;
               }
            }

            var3 = null;
            break label30;
         }

         var3 = (ActivityRecognitionResult)var1;
      }

      if (var3 != null) {
         return var3;
      } else {
         List var2 = zza(var0);
         return var2 != null && !var2.isEmpty() ? (ActivityRecognitionResult)var2.get(var2.size() - 1) : null;
      }
   }

   public static boolean hasResult(Intent var0) {
      if (var0 == null) {
         return false;
      } else {
         boolean var1;
         if (var0 == null) {
            var1 = false;
         } else {
            var1 = var0.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT");
         }

         if (var1) {
            return true;
         } else {
            List var2 = zza(var0);
            return var2 != null && !var2.isEmpty();
         }
      }
   }

   private static List<ActivityRecognitionResult> zza(Intent var0) {
      boolean var1;
      if (var0 == null) {
         var1 = false;
      } else {
         var1 = var0.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST");
      }

      return !var1 ? null : SafeParcelableSerializer.deserializeIterableFromIntentExtra(var0, "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT_LIST", CREATOR);
   }

   private static boolean zza(Bundle var0, Bundle var1) {
      if (var0 == null && var1 == null) {
         return true;
      } else if (var0 == null && var1 != null || var0 != null && var1 == null) {
         return false;
      } else if (var0.size() != var1.size()) {
         return false;
      } else {
         Iterator var2 = var0.keySet().iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            if (!var1.containsKey(var3)) {
               return false;
            }

            if (var0.get(var3) == null) {
               if (var1.get(var3) != null) {
                  return false;
               }
            } else if (var0.get(var3) instanceof Bundle) {
               if (!zza(var0.getBundle(var3), var1.getBundle(var3))) {
                  return false;
               }
            } else if (!var0.get(var3).equals(var1.get(var3))) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         if (var1 != null && this.getClass() == var1.getClass()) {
            ActivityRecognitionResult var2 = (ActivityRecognitionResult)var1;
            if (this.zzf == var2.zzf && this.zzg == var2.zzg && this.zzh == var2.zzh && Objects.equal(this.zze, var2.zze) && zza(this.extras, var2.extras)) {
               return true;
            }
         }

         return false;
      }
   }

   public int getActivityConfidence(int var1) {
      Iterator var2 = this.zze.iterator();

      DetectedActivity var3;
      do {
         if (!var2.hasNext()) {
            return 0;
         }

         var3 = (DetectedActivity)var2.next();
      } while(var3.getType() != var1);

      return var3.getConfidence();
   }

   public long getElapsedRealtimeMillis() {
      return this.zzg;
   }

   public DetectedActivity getMostProbableActivity() {
      return (DetectedActivity)this.zze.get(0);
   }

   public List<DetectedActivity> getProbableActivities() {
      return this.zze;
   }

   public long getTime() {
      return this.zzf;
   }

   public int hashCode() {
      return Objects.hashCode(this.zzf, this.zzg, this.zzh, this.zze, this.extras);
   }

   public String toString() {
      String var1 = String.valueOf(this.zze);
      long var2 = this.zzf;
      long var4 = this.zzg;
      StringBuilder var6 = new StringBuilder(String.valueOf(var1).length() + 124);
      var6.append("ActivityRecognitionResult [probableActivities=");
      var6.append(var1);
      var6.append(", timeMillis=");
      var6.append(var2);
      var6.append(", elapsedRealtimeMillis=");
      var6.append(var4);
      var6.append("]");
      return var6.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.zze, false);
      SafeParcelWriter.writeLong(var1, 2, this.zzf);
      SafeParcelWriter.writeLong(var1, 3, this.zzg);
      SafeParcelWriter.writeInt(var1, 4, this.zzh);
      SafeParcelWriter.writeBundle(var1, 5, this.extras, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
