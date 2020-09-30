package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class ConnectionResult extends AbstractSafeParcelable {
   public static final int API_UNAVAILABLE = 16;
   public static final int CANCELED = 13;
   public static final Creator<ConnectionResult> CREATOR = new zza();
   public static final int DEVELOPER_ERROR = 10;
   @Deprecated
   public static final int DRIVE_EXTERNAL_STORAGE_REQUIRED = 1500;
   public static final int INTERNAL_ERROR = 8;
   public static final int INTERRUPTED = 15;
   public static final int INVALID_ACCOUNT = 5;
   public static final int LICENSE_CHECK_FAILED = 11;
   public static final int NETWORK_ERROR = 7;
   public static final int RESOLUTION_ACTIVITY_NOT_FOUND = 22;
   public static final int RESOLUTION_REQUIRED = 6;
   public static final int RESTRICTED_PROFILE = 20;
   public static final ConnectionResult RESULT_SUCCESS = new ConnectionResult(0);
   public static final int SERVICE_DISABLED = 3;
   public static final int SERVICE_INVALID = 9;
   public static final int SERVICE_MISSING = 1;
   public static final int SERVICE_MISSING_PERMISSION = 19;
   public static final int SERVICE_UPDATING = 18;
   public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
   public static final int SIGN_IN_FAILED = 17;
   public static final int SIGN_IN_REQUIRED = 4;
   public static final int SUCCESS = 0;
   public static final int TIMEOUT = 14;
   public static final int UNKNOWN = -1;
   private final int zza;
   private final int zzb;
   private final PendingIntent zzc;
   private final String zzd;

   public ConnectionResult(int var1) {
      this(var1, (PendingIntent)null, (String)null);
   }

   ConnectionResult(int var1, int var2, PendingIntent var3, String var4) {
      this.zza = var1;
      this.zzb = var2;
      this.zzc = var3;
      this.zzd = var4;
   }

   public ConnectionResult(int var1, PendingIntent var2) {
      this(var1, var2, (String)null);
   }

   public ConnectionResult(int var1, PendingIntent var2, String var3) {
      this(1, var1, var2, var3);
   }

   static String zza(int var0) {
      if (var0 != 99) {
         if (var0 != 1500) {
            switch(var0) {
            case -1:
               return "UNKNOWN";
            case 0:
               return "SUCCESS";
            case 1:
               return "SERVICE_MISSING";
            case 2:
               return "SERVICE_VERSION_UPDATE_REQUIRED";
            case 3:
               return "SERVICE_DISABLED";
            case 4:
               return "SIGN_IN_REQUIRED";
            case 5:
               return "INVALID_ACCOUNT";
            case 6:
               return "RESOLUTION_REQUIRED";
            case 7:
               return "NETWORK_ERROR";
            case 8:
               return "INTERNAL_ERROR";
            case 9:
               return "SERVICE_INVALID";
            case 10:
               return "DEVELOPER_ERROR";
            case 11:
               return "LICENSE_CHECK_FAILED";
            default:
               switch(var0) {
               case 13:
                  return "CANCELED";
               case 14:
                  return "TIMEOUT";
               case 15:
                  return "INTERRUPTED";
               case 16:
                  return "API_UNAVAILABLE";
               case 17:
                  return "SIGN_IN_FAILED";
               case 18:
                  return "SERVICE_UPDATING";
               case 19:
                  return "SERVICE_MISSING_PERMISSION";
               case 20:
                  return "RESTRICTED_PROFILE";
               case 21:
                  return "API_VERSION_UPDATE_REQUIRED";
               case 22:
                  return "RESOLUTION_ACTIVITY_NOT_FOUND";
               default:
                  StringBuilder var1 = new StringBuilder(31);
                  var1.append("UNKNOWN_ERROR_CODE(");
                  var1.append(var0);
                  var1.append(")");
                  return var1.toString();
               }
            }
         } else {
            return "DRIVE_EXTERNAL_STORAGE_REQUIRED";
         }
      } else {
         return "UNFINISHED";
      }
   }

   public final boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ConnectionResult)) {
         return false;
      } else {
         ConnectionResult var2 = (ConnectionResult)var1;
         return this.zzb == var2.zzb && Objects.equal(this.zzc, var2.zzc) && Objects.equal(this.zzd, var2.zzd);
      }
   }

   public final int getErrorCode() {
      return this.zzb;
   }

   public final String getErrorMessage() {
      return this.zzd;
   }

   public final PendingIntent getResolution() {
      return this.zzc;
   }

   public final boolean hasResolution() {
      return this.zzb != 0 && this.zzc != null;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzb, this.zzc, this.zzd);
   }

   public final boolean isSuccess() {
      return this.zzb == 0;
   }

   public final void startResolutionForResult(Activity var1, int var2) throws SendIntentException {
      if (this.hasResolution()) {
         var1.startIntentSenderForResult(((PendingIntent)Preconditions.checkNotNull(this.zzc)).getIntentSender(), var2, (Intent)null, 0, 0, 0);
      }
   }

   public final String toString() {
      return Objects.toStringHelper(this).add("statusCode", zza(this.zzb)).add("resolution", this.zzc).add("message", this.zzd).toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zza);
      SafeParcelWriter.writeInt(var1, 2, this.getErrorCode());
      SafeParcelWriter.writeParcelable(var1, 3, this.getResolution(), var2, false);
      SafeParcelWriter.writeString(var1, 4, this.getErrorMessage(), false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
