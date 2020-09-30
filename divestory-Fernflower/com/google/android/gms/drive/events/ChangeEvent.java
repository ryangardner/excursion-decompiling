package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import java.util.Locale;

public final class ChangeEvent extends AbstractSafeParcelable implements ResourceEvent {
   public static final Creator<ChangeEvent> CREATOR = new zza();
   private final int zzbu;
   private final DriveId zzk;

   public ChangeEvent(DriveId var1, int var2) {
      this.zzk = var1;
      this.zzbu = var2;
   }

   public final DriveId getDriveId() {
      return this.zzk;
   }

   public final int getType() {
      return 1;
   }

   public final boolean hasBeenDeleted() {
      return (this.zzbu & 4) != 0;
   }

   public final boolean hasContentChanged() {
      return (this.zzbu & 2) != 0;
   }

   public final boolean hasMetadataChanged() {
      return (this.zzbu & 1) != 0;
   }

   public final String toString() {
      return String.format(Locale.US, "ChangeEvent [id=%s,changeFlags=%x]", this.zzk, this.zzbu);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 2, this.zzk, var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.zzbu);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
