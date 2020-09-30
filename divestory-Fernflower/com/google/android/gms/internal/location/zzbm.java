package com.google.android.gms.internal.location;

import android.os.Looper;
import com.google.android.gms.common.internal.Preconditions;

public final class zzbm {
   public static Looper zza(Looper var0) {
      return var0 != null ? var0 : zzc();
   }

   public static Looper zzc() {
      boolean var0;
      if (Looper.myLooper() != null) {
         var0 = true;
      } else {
         var0 = false;
      }

      Preconditions.checkState(var0, "Can't create handler inside thread that has not called Looper.prepare()");
      return Looper.myLooper();
   }
}
