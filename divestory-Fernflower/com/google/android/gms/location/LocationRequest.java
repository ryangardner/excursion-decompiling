package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class LocationRequest extends AbstractSafeParcelable implements ReflectedParcelable {
   public static final Creator<LocationRequest> CREATOR = new zzab();
   public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
   public static final int PRIORITY_HIGH_ACCURACY = 100;
   public static final int PRIORITY_LOW_POWER = 104;
   public static final int PRIORITY_NO_POWER = 105;
   private int priority;
   private long zzaf;
   private long zzaw;
   private long zzax;
   private boolean zzay;
   private float zzaz;
   private long zzba;
   private int zzx;

   public LocationRequest() {
      this.priority = 102;
      this.zzaw = 3600000L;
      this.zzax = 600000L;
      this.zzay = false;
      this.zzaf = Long.MAX_VALUE;
      this.zzx = Integer.MAX_VALUE;
      this.zzaz = 0.0F;
      this.zzba = 0L;
   }

   LocationRequest(int var1, long var2, long var4, boolean var6, long var7, int var9, float var10, long var11) {
      this.priority = var1;
      this.zzaw = var2;
      this.zzax = var4;
      this.zzay = var6;
      this.zzaf = var7;
      this.zzx = var9;
      this.zzaz = var10;
      this.zzba = var11;
   }

   public static LocationRequest create() {
      return new LocationRequest();
   }

   private static void zza(long var0) {
      if (var0 < 0L) {
         StringBuilder var2 = new StringBuilder(38);
         var2.append("invalid interval: ");
         var2.append(var0);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof LocationRequest)) {
         return false;
      } else {
         LocationRequest var2 = (LocationRequest)var1;
         return this.priority == var2.priority && this.zzaw == var2.zzaw && this.zzax == var2.zzax && this.zzay == var2.zzay && this.zzaf == var2.zzaf && this.zzx == var2.zzx && this.zzaz == var2.zzaz && this.getMaxWaitTime() == var2.getMaxWaitTime();
      }
   }

   public final long getExpirationTime() {
      return this.zzaf;
   }

   public final long getFastestInterval() {
      return this.zzax;
   }

   public final long getInterval() {
      return this.zzaw;
   }

   public final long getMaxWaitTime() {
      long var1 = this.zzba;
      long var3 = this.zzaw;
      long var5 = var1;
      if (var1 < var3) {
         var5 = var3;
      }

      return var5;
   }

   public final int getNumUpdates() {
      return this.zzx;
   }

   public final int getPriority() {
      return this.priority;
   }

   public final float getSmallestDisplacement() {
      return this.zzaz;
   }

   public final int hashCode() {
      return Objects.hashCode(this.priority, this.zzaw, this.zzaz, this.zzba);
   }

   public final boolean isFastestIntervalExplicitlySet() {
      return this.zzay;
   }

   public final LocationRequest setExpirationDuration(long var1) {
      long var3 = SystemClock.elapsedRealtime();
      if (var1 > Long.MAX_VALUE - var3) {
         this.zzaf = Long.MAX_VALUE;
      } else {
         this.zzaf = var1 + var3;
      }

      if (this.zzaf < 0L) {
         this.zzaf = 0L;
      }

      return this;
   }

   public final LocationRequest setExpirationTime(long var1) {
      this.zzaf = var1;
      if (var1 < 0L) {
         this.zzaf = 0L;
      }

      return this;
   }

   public final LocationRequest setFastestInterval(long var1) {
      zza(var1);
      this.zzay = true;
      this.zzax = var1;
      return this;
   }

   public final LocationRequest setInterval(long var1) {
      zza(var1);
      this.zzaw = var1;
      if (!this.zzay) {
         this.zzax = (long)((double)var1 / 6.0D);
      }

      return this;
   }

   public final LocationRequest setMaxWaitTime(long var1) {
      zza(var1);
      this.zzba = var1;
      return this;
   }

   public final LocationRequest setNumUpdates(int var1) {
      if (var1 > 0) {
         this.zzx = var1;
         return this;
      } else {
         StringBuilder var2 = new StringBuilder(31);
         var2.append("invalid numUpdates: ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public final LocationRequest setPriority(int var1) {
      if (var1 != 100 && var1 != 102 && var1 != 104 && var1 != 105) {
         StringBuilder var2 = new StringBuilder(28);
         var2.append("invalid quality: ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      } else {
         this.priority = var1;
         return this;
      }
   }

   public final LocationRequest setSmallestDisplacement(float var1) {
      if (var1 >= 0.0F) {
         this.zzaz = var1;
         return this;
      } else {
         StringBuilder var2 = new StringBuilder(37);
         var2.append("invalid displacement: ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Request[");
      int var2 = this.priority;
      String var3;
      if (var2 != 100) {
         if (var2 != 102) {
            if (var2 != 104) {
               if (var2 != 105) {
                  var3 = "???";
               } else {
                  var3 = "PRIORITY_NO_POWER";
               }
            } else {
               var3 = "PRIORITY_LOW_POWER";
            }
         } else {
            var3 = "PRIORITY_BALANCED_POWER_ACCURACY";
         }
      } else {
         var3 = "PRIORITY_HIGH_ACCURACY";
      }

      var1.append(var3);
      if (this.priority != 105) {
         var1.append(" requested=");
         var1.append(this.zzaw);
         var1.append("ms");
      }

      var1.append(" fastest=");
      var1.append(this.zzax);
      var1.append("ms");
      if (this.zzba > this.zzaw) {
         var1.append(" maxWait=");
         var1.append(this.zzba);
         var1.append("ms");
      }

      if (this.zzaz > 0.0F) {
         var1.append(" smallestDisplacement=");
         var1.append(this.zzaz);
         var1.append("m");
      }

      long var4 = this.zzaf;
      if (var4 != Long.MAX_VALUE) {
         long var6 = SystemClock.elapsedRealtime();
         var1.append(" expireIn=");
         var1.append(var4 - var6);
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
      SafeParcelWriter.writeInt(var1, 1, this.priority);
      SafeParcelWriter.writeLong(var1, 2, this.zzaw);
      SafeParcelWriter.writeLong(var1, 3, this.zzax);
      SafeParcelWriter.writeBoolean(var1, 4, this.zzay);
      SafeParcelWriter.writeLong(var1, 5, this.zzaf);
      SafeParcelWriter.writeInt(var1, 6, this.zzx);
      SafeParcelWriter.writeFloat(var1, 7, this.zzaz);
      SafeParcelWriter.writeLong(var1, 8, this.zzba);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
