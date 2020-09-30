package com.google.android.gms.internal.drive;

import com.google.android.gms.drive.events.OpenFileCallback;

// $FF: synthetic class
final class zzdn implements zzdg {
   private final zzdk zzgl;
   private final zzfh zzgo;

   zzdn(zzdk var1, zzfh var2) {
      this.zzgl = var1;
      this.zzgo = var2;
   }

   public final void accept(Object var1) {
      this.zzgl.zza(this.zzgo, (OpenFileCallback)var1);
   }
}
