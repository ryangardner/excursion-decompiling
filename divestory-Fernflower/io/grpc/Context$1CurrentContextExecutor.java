package io.grpc;

import java.util.concurrent.Executor;

final class Context$1CurrentContextExecutor implements Executor {
   // $FF: synthetic field
   final Executor val$e;

   Context$1CurrentContextExecutor(Executor var1) {
      this.val$e = var1;
   }

   public void execute(Runnable var1) {
      this.val$e.execute(Context.current().wrap(var1));
   }
}
