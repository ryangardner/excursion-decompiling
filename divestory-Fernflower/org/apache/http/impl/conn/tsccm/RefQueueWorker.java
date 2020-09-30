package org.apache.http.impl.conn.tsccm;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

@Deprecated
public class RefQueueWorker implements Runnable {
   protected final RefQueueHandler refHandler;
   protected final ReferenceQueue<?> refQueue;
   protected volatile Thread workerThread;

   public RefQueueWorker(ReferenceQueue<?> var1, RefQueueHandler var2) {
      if (var1 != null) {
         if (var2 != null) {
            this.refQueue = var1;
            this.refHandler = var2;
         } else {
            throw new IllegalArgumentException("Handler must not be null.");
         }
      } else {
         throw new IllegalArgumentException("Queue must not be null.");
      }
   }

   public void run() {
      if (this.workerThread == null) {
         this.workerThread = Thread.currentThread();
      }

      while(this.workerThread == Thread.currentThread()) {
         try {
            Reference var1 = this.refQueue.remove();
            this.refHandler.handleReference(var1);
         } catch (InterruptedException var2) {
         }
      }

   }

   public void shutdown() {
      Thread var1 = this.workerThread;
      if (var1 != null) {
         this.workerThread = null;
         var1.interrupt();
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("RefQueueWorker::");
      var1.append(this.workerThread);
      return var1.toString();
   }
}
