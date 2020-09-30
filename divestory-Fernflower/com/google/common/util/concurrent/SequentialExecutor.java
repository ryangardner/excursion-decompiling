package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Logger;

final class SequentialExecutor implements Executor {
   private static final Logger log = Logger.getLogger(SequentialExecutor.class.getName());
   private final Executor executor;
   private final Deque<Runnable> queue = new ArrayDeque();
   private final SequentialExecutor.QueueWorker worker;
   private long workerRunCount;
   private SequentialExecutor.WorkerRunningState workerRunningState;

   SequentialExecutor(Executor var1) {
      this.workerRunningState = SequentialExecutor.WorkerRunningState.IDLE;
      this.workerRunCount = 0L;
      this.worker = new SequentialExecutor.QueueWorker();
      this.executor = (Executor)Preconditions.checkNotNull(var1);
   }

   // $FF: synthetic method
   static Deque access$100(SequentialExecutor var0) {
      return var0.queue;
   }

   // $FF: synthetic method
   static SequentialExecutor.WorkerRunningState access$200(SequentialExecutor var0) {
      return var0.workerRunningState;
   }

   // $FF: synthetic method
   static SequentialExecutor.WorkerRunningState access$202(SequentialExecutor var0, SequentialExecutor.WorkerRunningState var1) {
      var0.workerRunningState = var1;
      return var1;
   }

   // $FF: synthetic method
   static long access$308(SequentialExecutor var0) {
      long var1 = (long)(var0.workerRunCount++);
      return var1;
   }

   // $FF: synthetic method
   static Logger access$400() {
      return log;
   }

   public void execute(final Runnable var1) {
      Preconditions.checkNotNull(var1);
      Deque var2 = this.queue;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      Throwable var218;
      label1992: {
         label1995: {
            try {
               if (this.workerRunningState != SequentialExecutor.WorkerRunningState.RUNNING && this.workerRunningState != SequentialExecutor.WorkerRunningState.QUEUED) {
                  break label1995;
               }
            } catch (Throwable var217) {
               var10000 = var217;
               var10001 = false;
               break label1992;
            }

            try {
               this.queue.add(var1);
               return;
            } catch (Throwable var216) {
               var10000 = var216;
               var10001 = false;
               break label1992;
            }
         }

         long var3;
         Runnable var5;
         try {
            var3 = this.workerRunCount;
            var5 = new Runnable() {
               public void run() {
                  var1.run();
               }
            };
            this.queue.add(var5);
            this.workerRunningState = SequentialExecutor.WorkerRunningState.QUEUING;
         } catch (Throwable var215) {
            var10000 = var215;
            var10001 = false;
            break label1992;
         }

         boolean var6 = true;
         boolean var7 = true;

         label1993: {
            Object var219;
            try {
               this.executor.execute(this.worker);
               break label1993;
            } catch (RuntimeException var213) {
               var219 = var213;
            } catch (Error var214) {
               var219 = var214;
            }

            var2 = this.queue;
            synchronized(var2){}

            label1994: {
               label1945: {
                  label1944: {
                     label1943: {
                        try {
                           if (this.workerRunningState != SequentialExecutor.WorkerRunningState.IDLE && this.workerRunningState != SequentialExecutor.WorkerRunningState.QUEUING) {
                              break label1943;
                           }
                        } catch (Throwable var209) {
                           var10000 = var209;
                           var10001 = false;
                           break label1994;
                        }

                        try {
                           if (this.queue.removeLastOccurrence(var5)) {
                              break label1944;
                           }
                        } catch (Throwable var208) {
                           var10000 = var208;
                           var10001 = false;
                           break label1994;
                        }
                     }

                     var7 = false;
                     break label1945;
                  }

                  var7 = var6;
               }

               label1930: {
                  try {
                     if (!(var219 instanceof RejectedExecutionException)) {
                        break label1930;
                     }
                  } catch (Throwable var207) {
                     var10000 = var207;
                     var10001 = false;
                     break label1994;
                  }

                  if (!var7) {
                     try {
                        return;
                     } catch (Throwable var205) {
                        var10000 = var205;
                        var10001 = false;
                        break label1994;
                     }
                  }
               }

               label1924:
               try {
                  throw var219;
               } catch (Throwable var206) {
                  var10000 = var206;
                  var10001 = false;
                  break label1924;
               }
            }

            while(true) {
               var218 = var10000;

               try {
                  throw var218;
               } catch (Throwable var204) {
                  var10000 = var204;
                  var10001 = false;
                  continue;
               }
            }
         }

         if (this.workerRunningState == SequentialExecutor.WorkerRunningState.QUEUING) {
            var7 = false;
         }

         if (var7) {
            return;
         }

         Deque var220 = this.queue;
         synchronized(var220){}

         label1962: {
            try {
               if (this.workerRunCount == var3 && this.workerRunningState == SequentialExecutor.WorkerRunningState.QUEUING) {
                  this.workerRunningState = SequentialExecutor.WorkerRunningState.QUEUED;
               }
            } catch (Throwable var212) {
               var10000 = var212;
               var10001 = false;
               break label1962;
            }

            label1959:
            try {
               return;
            } catch (Throwable var211) {
               var10000 = var211;
               var10001 = false;
               break label1959;
            }
         }

         while(true) {
            var218 = var10000;

            try {
               throw var218;
            } catch (Throwable var210) {
               var10000 = var210;
               var10001 = false;
               continue;
            }
         }
      }

      while(true) {
         var218 = var10000;

         try {
            throw var218;
         } catch (Throwable var203) {
            var10000 = var203;
            var10001 = false;
            continue;
         }
      }
   }

   private final class QueueWorker implements Runnable {
      private QueueWorker() {
      }

      // $FF: synthetic method
      QueueWorker(Object var2) {
         this();
      }

      private void workOnQueue() {
         // $FF: Couldn't be decompiled
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }

   static enum WorkerRunningState {
      IDLE,
      QUEUED,
      QUEUING,
      RUNNING;

      static {
         SequentialExecutor.WorkerRunningState var0 = new SequentialExecutor.WorkerRunningState("RUNNING", 3);
         RUNNING = var0;
      }
   }
}
