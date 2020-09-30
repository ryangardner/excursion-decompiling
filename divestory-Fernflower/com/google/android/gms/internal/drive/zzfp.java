package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.DriveEvent;

public final class zzfp extends AbstractSafeParcelable {
   public static final Creator<zzfp> CREATOR = new zzfq();
   private final int zzda;
   private final ChangeEvent zzib;
   private final CompletionEvent zzic;
   private final com.google.android.gms.drive.events.zzo zzid;
   private final com.google.android.gms.drive.events.zzb zzie;
   private final com.google.android.gms.drive.events.zzv zzif;
   private final com.google.android.gms.drive.events.zzr zzig;

   zzfp(int var1, ChangeEvent var2, CompletionEvent var3, com.google.android.gms.drive.events.zzo var4, com.google.android.gms.drive.events.zzb var5, com.google.android.gms.drive.events.zzv var6, com.google.android.gms.drive.events.zzr var7) {
      this.zzda = var1;
      this.zzib = var2;
      this.zzic = var3;
      this.zzid = var4;
      this.zzie = var5;
      this.zzif = var6;
      this.zzig = var7;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.zzda);
      SafeParcelWriter.writeParcelable(var1, 3, this.zzib, var2, false);
      SafeParcelWriter.writeParcelable(var1, 5, this.zzic, var2, false);
      SafeParcelWriter.writeParcelable(var1, 6, this.zzid, var2, false);
      SafeParcelWriter.writeParcelable(var1, 7, this.zzie, var2, false);
      SafeParcelWriter.writeParcelable(var1, 9, this.zzif, var2, false);
      SafeParcelWriter.writeParcelable(var1, 10, this.zzig, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public final DriveEvent zzat() {
      int var1 = this.zzda;
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 3) {
               if (var1 != 4) {
                  if (var1 != 7) {
                     if (var1 == 8) {
                        return this.zzig;
                     } else {
                        var1 = this.zzda;
                        StringBuilder var2 = new StringBuilder(33);
                        var2.append("Unexpected event type ");
                        var2.append(var1);
                        throw new IllegalStateException(var2.toString());
                     }
                  } else {
                     return this.zzif;
                  }
               } else {
                  return this.zzie;
               }
            } else {
               return this.zzid;
            }
         } else {
            return this.zzic;
         }
      } else {
         return this.zzib;
      }
   }
}
