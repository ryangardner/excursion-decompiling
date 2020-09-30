package com.google.android.gms.common.util.concurrent;

import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberedThreadFactory implements ThreadFactory {
   private final String zza;
   private final int zzb;
   private final AtomicInteger zzc;
   private final ThreadFactory zzd;

   public NumberedThreadFactory(String var1) {
      this(var1, 0);
   }

   private NumberedThreadFactory(String var1, int var2) {
      this.zzc = new AtomicInteger();
      this.zzd = Executors.defaultThreadFactory();
      this.zza = (String)Preconditions.checkNotNull(var1, "Name must not be null");
      this.zzb = 0;
   }

   public Thread newThread(Runnable var1) {
      Thread var5 = this.zzd.newThread(new zza(var1, 0));
      String var2 = this.zza;
      int var3 = this.zzc.getAndIncrement();
      StringBuilder var4 = new StringBuilder(String.valueOf(var2).length() + 13);
      var4.append(var2);
      var4.append("[");
      var4.append(var3);
      var4.append("]");
      var5.setName(var4.toString());
      return var5;
   }
}
