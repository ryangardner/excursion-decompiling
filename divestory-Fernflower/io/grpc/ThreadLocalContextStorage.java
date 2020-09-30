package io.grpc;

import java.util.logging.Level;
import java.util.logging.Logger;

final class ThreadLocalContextStorage extends Context.Storage {
   static final ThreadLocal<Context> localContext = new ThreadLocal();
   private static final Logger log = Logger.getLogger(ThreadLocalContextStorage.class.getName());

   public Context current() {
      Context var1 = (Context)localContext.get();
      Context var2 = var1;
      if (var1 == null) {
         var2 = Context.ROOT;
      }

      return var2;
   }

   public void detach(Context var1, Context var2) {
      if (this.current() != var1) {
         log.log(Level.SEVERE, "Context was not attached when detaching", (new Throwable()).fillInStackTrace());
      }

      if (var2 != Context.ROOT) {
         localContext.set(var2);
      } else {
         localContext.set((Object)null);
      }

   }

   public Context doAttach(Context var1) {
      Context var2 = this.current();
      localContext.set(var1);
      return var2;
   }
}
