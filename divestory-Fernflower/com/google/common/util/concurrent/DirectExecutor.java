package com.google.common.util.concurrent;

import java.util.concurrent.Executor;

enum DirectExecutor implements Executor {
   INSTANCE;

   static {
      DirectExecutor var0 = new DirectExecutor("INSTANCE", 0);
      INSTANCE = var0;
   }

   public void execute(Runnable var1) {
      var1.run();
   }

   public String toString() {
      return "MoreExecutors.directExecutor()";
   }
}
