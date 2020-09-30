package com.google.android.gms.internal.drive;

import com.google.android.gms.common.internal.Objects;
import java.util.Locale;

public final class zze implements com.google.android.gms.drive.events.zzk {
   private final com.google.android.gms.drive.events.zzm zzcv;
   private final long zzcw;
   private final long zzcx;

   public zze(zzh var1) {
      this.zzcv = new zzf(var1);
      this.zzcw = var1.zzcw;
      this.zzcx = var1.zzcx;
   }

   public final boolean equals(Object var1) {
      if (var1 != null && var1.getClass() == this.getClass()) {
         if (var1 == this) {
            return true;
         }

         zze var2 = (zze)var1;
         if (Objects.equal(this.zzcv, var2.zzcv) && this.zzcw == var2.zzcw && this.zzcx == var2.zzcx) {
            return true;
         }
      }

      return false;
   }

   public final int hashCode() {
      return Objects.hashCode(this.zzcx, this.zzcw, this.zzcx);
   }

   public final String toString() {
      return String.format(Locale.US, "FileTransferProgress[FileTransferState: %s, BytesTransferred: %d, TotalBytes: %d]", this.zzcv.toString(), this.zzcw, this.zzcx);
   }
}
