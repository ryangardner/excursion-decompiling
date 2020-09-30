package com.google.common.util.concurrent;

import com.google.errorprone.annotations.DoNotMock;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@DoNotMock("Create an AbstractIdleService")
public interface Service {
   void addListener(Service.Listener var1, Executor var2);

   void awaitRunning();

   void awaitRunning(long var1, TimeUnit var3) throws TimeoutException;

   void awaitTerminated();

   void awaitTerminated(long var1, TimeUnit var3) throws TimeoutException;

   Throwable failureCause();

   boolean isRunning();

   Service startAsync();

   Service.State state();

   Service stopAsync();

   public abstract static class Listener {
      public void failed(Service.State var1, Throwable var2) {
      }

      public void running() {
      }

      public void starting() {
      }

      public void stopping(Service.State var1) {
      }

      public void terminated(Service.State var1) {
      }
   }

   public static enum State {
      FAILED,
      NEW {
         boolean isTerminal() {
            return false;
         }
      },
      RUNNING {
         boolean isTerminal() {
            return false;
         }
      },
      STARTING {
         boolean isTerminal() {
            return false;
         }
      },
      STOPPING {
         boolean isTerminal() {
            return false;
         }
      },
      TERMINATED {
         boolean isTerminal() {
            return true;
         }
      };

      static {
         Service.State var0 = new Service.State("FAILED", 5) {
            boolean isTerminal() {
               return true;
            }
         };
         FAILED = var0;
      }

      private State() {
      }

      // $FF: synthetic method
      State(Object var3) {
         this();
      }

      abstract boolean isTerminal();
   }
}
