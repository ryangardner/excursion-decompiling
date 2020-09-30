package com.google.common.eventbus;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventBus {
   private static final Logger logger = Logger.getLogger(EventBus.class.getName());
   private final Dispatcher dispatcher;
   private final SubscriberExceptionHandler exceptionHandler;
   private final Executor executor;
   private final String identifier;
   private final SubscriberRegistry subscribers;

   public EventBus() {
      this("default");
   }

   public EventBus(SubscriberExceptionHandler var1) {
      this("default", MoreExecutors.directExecutor(), Dispatcher.perThreadDispatchQueue(), var1);
   }

   public EventBus(String var1) {
      this(var1, MoreExecutors.directExecutor(), Dispatcher.perThreadDispatchQueue(), EventBus.LoggingHandler.INSTANCE);
   }

   EventBus(String var1, Executor var2, Dispatcher var3, SubscriberExceptionHandler var4) {
      this.subscribers = new SubscriberRegistry(this);
      this.identifier = (String)Preconditions.checkNotNull(var1);
      this.executor = (Executor)Preconditions.checkNotNull(var2);
      this.dispatcher = (Dispatcher)Preconditions.checkNotNull(var3);
      this.exceptionHandler = (SubscriberExceptionHandler)Preconditions.checkNotNull(var4);
   }

   final Executor executor() {
      return this.executor;
   }

   void handleSubscriberException(Throwable var1, SubscriberExceptionContext var2) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);

      try {
         this.exceptionHandler.handleException(var1, var2);
      } catch (Throwable var4) {
         logger.log(Level.SEVERE, String.format(Locale.ROOT, "Exception %s thrown while handling exception: %s", var4, var1), var4);
         return;
      }

   }

   public final String identifier() {
      return this.identifier;
   }

   public void post(Object var1) {
      Iterator var2 = this.subscribers.getSubscribers(var1);
      if (var2.hasNext()) {
         this.dispatcher.dispatch(var1, var2);
      } else if (!(var1 instanceof DeadEvent)) {
         this.post(new DeadEvent(this, var1));
      }

   }

   public void register(Object var1) {
      this.subscribers.register(var1);
   }

   public String toString() {
      return MoreObjects.toStringHelper((Object)this).addValue(this.identifier).toString();
   }

   public void unregister(Object var1) {
      this.subscribers.unregister(var1);
   }

   static final class LoggingHandler implements SubscriberExceptionHandler {
      static final EventBus.LoggingHandler INSTANCE = new EventBus.LoggingHandler();

      private static Logger logger(SubscriberExceptionContext var0) {
         StringBuilder var1 = new StringBuilder();
         var1.append(EventBus.class.getName());
         var1.append(".");
         var1.append(var0.getEventBus().identifier());
         return Logger.getLogger(var1.toString());
      }

      private static String message(SubscriberExceptionContext var0) {
         Method var1 = var0.getSubscriberMethod();
         StringBuilder var2 = new StringBuilder();
         var2.append("Exception thrown by subscriber method ");
         var2.append(var1.getName());
         var2.append('(');
         var2.append(var1.getParameterTypes()[0].getName());
         var2.append(')');
         var2.append(" on subscriber ");
         var2.append(var0.getSubscriber());
         var2.append(" when dispatching event: ");
         var2.append(var0.getEvent());
         return var2.toString();
      }

      public void handleException(Throwable var1, SubscriberExceptionContext var2) {
         Logger var3 = logger(var2);
         if (var3.isLoggable(Level.SEVERE)) {
            var3.log(Level.SEVERE, message(var2), var1);
         }

      }
   }
}
