package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class Status extends AbstractSafeParcelable implements Result, ReflectedParcelable {
   public static final Creator<Status> CREATOR = new zzb();
   public static final Status RESULT_CANCELED = new Status(16);
   public static final Status RESULT_DEAD_CLIENT = new Status(18);
   public static final Status RESULT_INTERNAL_ERROR = new Status(8);
   public static final Status RESULT_INTERRUPTED = new Status(14);
   public static final Status RESULT_SUCCESS = new Status(0);
   public static final Status RESULT_TIMEOUT = new Status(15);
   private static final Status zza = new Status(17);
   private final int zzb;
   private final int zzc;
   private final String zzd;
   private final PendingIntent zze;
   private final ConnectionResult zzf;

   public Status(int var1) {
      this(var1, (String)null);
   }

   Status(int var1, int var2, String var3, PendingIntent var4) {
      this(var1, var2, var3, var4, (ConnectionResult)null);
   }

   Status(int var1, int var2, String var3, PendingIntent var4, ConnectionResult var5) {
      this.zzb = var1;
      this.zzc = var2;
      this.zzd = var3;
      this.zze = var4;
      this.zzf = var5;
   }

   public Status(int var1, String var2) {
      this(1, var1, var2, (PendingIntent)null);
   }

   public Status(int var1, String var2, PendingIntent var3) {
      this(1, var1, var2, var3);
   }

   public Status(ConnectionResult var1, String var2) {
      this(var1, var2, 17);
   }

   @Deprecated
   public Status(ConnectionResult var1, String var2, int var3) {
      this(1, var3, var2, var1.getResolution(), var1);
   }

   public final boolean equals(Object var1) {
      if (!(var1 instanceof Status)) {
         return false;
      } else {
         Status var2 = (Status)var1;
         return this.zzb == var2.zzb && this.zzc == var2.zzc && Objects.equal(this.zzd, var2.zzd) && Objects.equal(this.zze, var2.zze) && Objects.equal(this.zzf, var2.zzf);
      }
   }

   public final ConnectionResult getConnectionResult() {
      return this.zzf;
   }

   public final PendingIntent getResolution() {
      return this.zze;
   }

   public final Status getStatus() {
      return this;
   }

   public final int getStatusCode() {
      return this.zzc;
   }

   public final String getStatusMessage() {
      return this.zzd;
   }

   public final boolean hasResolution() {
      return this.zze != null;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzb, this.zzc, this.zzd, this.zze, this.zzf);
   }

   public final boolean isCanceled() {
      return this.zzc == 16;
   }

   public final boolean isInterrupted() {
      return this.zzc == 14;
   }

   public final boolean isSuccess() {
      return this.zzc <= 0;
   }

   public final void startResolutionForResult(Activity var1, int var2) throws SendIntentException {
      if (this.hasResolution()) {
         var1.startIntentSenderForResult(((PendingIntent)Preconditions.checkNotNull(this.zze)).getIntentSender(), var2, (Intent)null, 0, 0, 0);
      }
   }

   public final String toString() {
      return Objects.toStringHelper(this).add("statusCode", this.zza()).add("resolution", this.zze).toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.getStatusCode());
      SafeParcelWriter.writeString(var1, 2, this.getStatusMessage(), false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zze, var2, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.getConnectionResult(), var2, false);
      SafeParcelWriter.writeInt(var1, 1000, this.zzb);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final String zza() {
      String var1 = this.zzd;
      return var1 != null ? var1 : CommonStatusCodes.getStatusCodeString(this.zzc);
   }
}
