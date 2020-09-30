package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

final class ListenerCallQueue<L> {
   private static final Logger logger = Logger.getLogger(ListenerCallQueue.class.getName());
   private final List<ListenerCallQueue.PerListenerQueue<L>> listeners = Collections.synchronizedList(new ArrayList());

   // $FF: synthetic method
   static Logger access$000() {
      return logger;
   }

   private void enqueueHelper(ListenerCallQueue.Event<L> var1, Object var2) {
      Preconditions.checkNotNull(var1, "event");
      Preconditions.checkNotNull(var2, "label");
      List var3 = this.listeners;
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label198: {
         Iterator var4;
         try {
            var4 = this.listeners.iterator();
         } catch (Throwable var23) {
            var10000 = var23;
            var10001 = false;
            break label198;
         }

         while(true) {
            try {
               if (var4.hasNext()) {
                  ((ListenerCallQueue.PerListenerQueue)var4.next()).add(var1, var2);
                  continue;
               }
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break;
            }

            try {
               return;
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break;
            }
         }
      }

      while(true) {
         Throwable var25 = var10000;

         try {
            throw var25;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            continue;
         }
      }
   }

   public void addListener(L var1, Executor var2) {
      Preconditions.checkNotNull(var1, "listener");
      Preconditions.checkNotNull(var2, "executor");
      this.listeners.add(new ListenerCallQueue.PerListenerQueue(var1, var2));
   }

   public void dispatch() {
      for(int var1 = 0; var1 < this.listeners.size(); ++var1) {
         ((ListenerCallQueue.PerListenerQueue)this.listeners.get(var1)).dispatch();
      }

   }

   public void enqueue(ListenerCallQueue.Event<L> var1) {
      this.enqueueHelper(var1, var1);
   }

   public void enqueue(ListenerCallQueue.Event<L> var1, String var2) {
      this.enqueueHelper(var1, var2);
   }

   interface Event<L> {
      void call(L var1);
   }

   private static final class PerListenerQueue<L> implements Runnable {
      final Executor executor;
      boolean isThreadScheduled;
      final Queue<Object> labelQueue = Queues.newArrayDeque();
      final L listener;
      final Queue<ListenerCallQueue.Event<L>> waitQueue = Queues.newArrayDeque();

      PerListenerQueue(L var1, Executor var2) {
         this.listener = Preconditions.checkNotNull(var1);
         this.executor = (Executor)Preconditions.checkNotNull(var2);
      }

      void add(ListenerCallQueue.Event<L> var1, Object var2) {
         synchronized(this){}

         try {
            this.waitQueue.add(var1);
            this.labelQueue.add(var2);
         } finally {
            ;
         }

      }

      void dispatch() {
         // $FF: Couldn't be decompiled
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }
}
