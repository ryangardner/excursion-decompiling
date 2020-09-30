package io.grpc;

import java.util.concurrent.Executor;

final class Context$1FixedContextExecutor implements Executor {
   // $FF: synthetic field
   final Context this$0;
   // $FF: synthetic field
   final Executor val$e;

   Context$1FixedContextExecutor(Context var1, Executor var2) {
      this.this$0 = var1;
      this.val$e = var2;
   }

   public void execute(Runnable var1) {
      this.val$e.execute(this.this$0.wrap(var1));
   }
}
