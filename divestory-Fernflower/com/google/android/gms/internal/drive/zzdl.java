package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.events.OpenFileCallback;

// $FF: synthetic class
final class zzdl implements zzdg {
   private final zzdk zzgl;
   private final Status zzgm;

   zzdl(zzdk var1, Status var2) {
      this.zzgl = var1;
      this.zzgm = var2;
   }

   public final void accept(Object var1) {
      this.zzgl.zza(this.zzgm, (OpenFileCallback)var1);
   }
}
