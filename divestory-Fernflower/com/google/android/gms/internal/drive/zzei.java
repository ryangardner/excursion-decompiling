package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.FileUploadPreferences;
import com.google.android.gms.drive.TransferPreferences;

@Deprecated
public final class zzei extends AbstractSafeParcelable implements FileUploadPreferences {
   public static final Creator<zzei> CREATOR = new zzej();
   private int zzbn;
   private int zzgy;
   private boolean zzgz;

   public zzei(int var1, int var2, boolean var3) {
      this.zzgy = var1;
      this.zzbn = var2;
      this.zzgz = var3;
   }

   public zzei(TransferPreferences var1) {
      this(var1.getNetworkPreference(), var1.getBatteryUsagePreference(), var1.isRoamingAllowed());
   }

   private static boolean zzh(int var0) {
      return var0 == 1 || var0 == 2;
   }

   private static boolean zzi(int var0) {
      return var0 == 256 || var0 == 257;
   }

   public final int getBatteryUsagePreference() {
      return !zzi(this.zzbn) ? 0 : this.zzbn;
   }

   public final int getNetworkTypePreference() {
      return !zzh(this.zzgy) ? 0 : this.zzgy;
   }

   public final boolean isRoamingAllowed() {
      return this.zzgz;
   }

   public final void setBatteryUsagePreference(int var1) {
      if (zzi(var1)) {
         this.zzbn = var1;
      } else {
         throw new IllegalArgumentException("Invalid battery usage preference value.");
      }
   }

   public final void setNetworkTypePreference(int var1) {
      if (zzh(var1)) {
         this.zzgy = var1;
      } else {
         throw new IllegalArgumentException("Invalid data connection preference value.");
      }
   }

   public final void setRoamingAllowed(boolean var1) {
      this.zzgz = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.zzgy);
      SafeParcelWriter.writeInt(var1, 3, this.zzbn);
      SafeParcelWriter.writeBoolean(var1, 4, this.zzgz);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
