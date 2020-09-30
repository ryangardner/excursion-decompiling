package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

// $FF: synthetic class
final class zzci implements Continuation {
   private final ListenerHolder zzfo;

   zzci(ListenerHolder var1) {
      this.zzfo = var1;
   }

   public final Object then(Task var1) {
      return zzch.zza(this.zzfo, var1);
   }
}
