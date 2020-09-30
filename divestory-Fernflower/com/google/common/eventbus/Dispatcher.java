package com.google.common.eventbus;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

abstract class Dispatcher {
   static Dispatcher immediate() {
      return Dispatcher.ImmediateDispatcher.INSTANCE;
   }

   static Dispatcher legacyAsync() {
      return new Dispatcher.LegacyAsyncDispatcher();
   }

   static Dispatcher perThreadDispatchQueue() {
      return new Dispatcher.PerThreadQueuedDispatcher();
   }

   abstract void dispatch(Object var1, Iterator<Subscriber> var2);

   private static final class ImmediateDispatcher extends Dispatcher {
      private static final Dispatcher.ImmediateDispatcher INSTANCE = new Dispatcher.ImmediateDispatcher();

      void dispatch(Object var1, Iterator<Subscriber> var2) {
         Preconditions.checkNotNull(var1);

         while(var2.hasNext()) {
            ((Subscriber)var2.next()).dispatchEvent(var1);
         }

      }
   }

   private static final class LegacyAsyncDispatcher extends Dispatcher {
      private final ConcurrentLinkedQueue<Dispatcher.LegacyAsyncDispatcher.EventWithSubscriber> queue;

      private LegacyAsyncDispatcher() {
         this.queue = Queues.newConcurrentLinkedQueue();
      }

      // $FF: synthetic method
      LegacyAsyncDispatcher(Object var1) {
         this();
      }

      void dispatch(Object var1, Iterator<Subscriber> var2) {
         Preconditions.checkNotNull(var1);

         while(var2.hasNext()) {
            this.queue.add(new Dispatcher.LegacyAsyncDispatcher.EventWithSubscriber(var1, (Subscriber)var2.next()));
         }

         while(true) {
            Dispatcher.LegacyAsyncDispatcher.EventWithSubscriber var3 = (Dispatcher.LegacyAsyncDispatcher.EventWithSubscriber)this.queue.poll();
            if (var3 == null) {
               return;
            }

            var3.subscriber.dispatchEvent(var3.event);
         }
      }

      private static final class EventWithSubscriber {
         private final Object event;
         private final Subscriber subscriber;

         private EventWithSubscriber(Object var1, Subscriber var2) {
            this.event = var1;
            this.subscriber = var2;
         }

         // $FF: synthetic method
         EventWithSubscriber(Object var1, Subscriber var2, Object var3) {
            this(var1, var2);
         }
      }
   }

   private static final class PerThreadQueuedDispatcher extends Dispatcher {
      private final ThreadLocal<Boolean> dispatching;
      private final ThreadLocal<Queue<Dispatcher.PerThreadQueuedDispatcher.Event>> queue;

      private PerThreadQueuedDispatcher() {
         this.queue = new ThreadLocal<Queue<Dispatcher.PerThreadQueuedDispatcher.Event>>() {
            protected Queue<Dispatcher.PerThreadQueuedDispatcher.Event> initialValue() {
               return Queues.newArrayDeque();
            }
         };
         this.dispatching = new ThreadLocal<Boolean>() {
            protected Boolean initialValue() {
               return false;
            }
         };
      }

      // $FF: synthetic method
      PerThreadQueuedDispatcher(Object var1) {
         this();
      }

      void dispatch(Object var1, Iterator<Subscriber> var2) {
         Preconditions.checkNotNull(var1);
         Preconditions.checkNotNull(var2);
         Queue var3 = (Queue)this.queue.get();
         var3.offer(new Dispatcher.PerThreadQueuedDispatcher.Event(var1, var2));
         if (!(Boolean)this.dispatching.get()) {
            this.dispatching.set(true);

            Throwable var10000;
            label106:
            while(true) {
               boolean var10001;
               Dispatcher.PerThreadQueuedDispatcher.Event var10;
               try {
                  var10 = (Dispatcher.PerThreadQueuedDispatcher.Event)var3.poll();
               } catch (Throwable var9) {
                  var10000 = var9;
                  var10001 = false;
                  break;
               }

               if (var10 == null) {
                  this.dispatching.remove();
                  this.queue.remove();
                  return;
               }

               while(true) {
                  try {
                     if (!var10.subscribers.hasNext()) {
                        break;
                     }

                     ((Subscriber)var10.subscribers.next()).dispatchEvent(var10.event);
                  } catch (Throwable var8) {
                     var10000 = var8;
                     var10001 = false;
                     break label106;
                  }
               }
            }

            Throwable var11 = var10000;
            this.dispatching.remove();
            this.queue.remove();
            throw var11;
         }
      }

      private static final class Event {
         private final Object event;
         private final Iterator<Subscriber> subscribers;

         private Event(Object var1, Iterator<Subscriber> var2) {
            this.event = var1;
            this.subscribers = var2;
         }

         // $FF: synthetic method
         Event(Object var1, Iterator var2, Object var3) {
            this(var1, var2);
         }
      }
   }
}
