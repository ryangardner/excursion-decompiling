package com.google.android.gms.internal.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

final class zan implements zam {
   private zan() {
   }

   // $FF: synthetic method
   zan(zao var1) {
      this();
   }

   public final ExecutorService zaa(int var1, int var2) {
      return this.zaa(4, Executors.defaultThreadFactory(), var2);
   }

   public final ExecutorService zaa(int var1, ThreadFactory var2, int var3) {
      ThreadPoolExecutor var4 = new ThreadPoolExecutor(var1, var1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), var2);
      var4.allowCoreThreadTimeOut(true);
      return Executors.unconfigurableExecutorService(var4);
   }

   public final ExecutorService zaa(ThreadFactory var1, int var2) {
      return this.zaa(1, var1, var2);
   }
}
