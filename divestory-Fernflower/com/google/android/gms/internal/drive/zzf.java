package com.google.android.gms.internal.drive;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.drive.DriveId;

public final class zzf {
   private final int status;
   private final int zzct;
   private final DriveId zzk;

   public zzf(zzh var1) {
      this.zzk = var1.zzk;
      this.zzct = var1.zzct;
      this.status = var1.status;
   }

   public final boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         if (var1 == this) {
            return true;
         }

         zzf var2 = (zzf)var1;
         if (Objects.equal(this.zzk, var2.zzk) && this.zzct == var2.zzct && this.status == var2.status) {
            return true;
         }
      }

      return false;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzk, this.zzct, this.status);
   }

   public final String toString() {
      return String.format("FileTransferState[TransferType: %d, DriveId: %s, status: %d]", this.zzct, this.zzk, this.status);
   }
}
