package com.google.android.gms.common.util.concurrent;

import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {
   private final String zza;
   private final int zzb;
   private final ThreadFactory zzc;

   public NamedThreadFactory(String var1) {
      this(var1, 0);
   }

   private NamedThreadFactory(String var1, int var2) {
      this.zzc = Executors.defaultThreadFactory();
      this.zza = (String)Preconditions.checkNotNull(var1, "Name must not be null");
      this.zzb = 0;
   }

   public Thread newThread(Runnable var1) {
      Thread var2 = this.zzc.newThread(new zza(var1, 0));
      var2.setName(this.zza);
      return var2;
   }
}
