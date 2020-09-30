package com.google.android.gms.internal.drive;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

// $FF: synthetic class
final class zzcj implements Continuation {
   private final zzg zzfp;

   zzcj(zzg var1) {
      this.zzfp = var1;
   }

   public final Object then(Task var1) {
      return zzch.zza(this.zzfp, var1);
   }
}
