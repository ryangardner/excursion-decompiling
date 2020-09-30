package com.google.android.gms.internal.drive;

import com.google.android.gms.drive.events.OpenFileCallback;

// $FF: synthetic class
final class zzdm implements zzdg {
   private final zzfl zzgn;

   zzdm(zzfl var1) {
      this.zzgn = var1;
   }

   public final void accept(Object var1) {
      zzfl var2 = this.zzgn;
      ((OpenFileCallback)var1).onProgress(var2.zzhy, var2.zzhz);
   }
}
