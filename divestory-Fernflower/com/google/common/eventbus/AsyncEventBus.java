package com.google.common.eventbus;

import java.util.concurrent.Executor;

public class AsyncEventBus extends EventBus {
   public AsyncEventBus(String var1, Executor var2) {
      super(var1, var2, Dispatcher.legacyAsync(), EventBus.LoggingHandler.INSTANCE);
   }

   public AsyncEventBus(Executor var1) {
      super("default", var1, Dispatcher.legacyAsync(), EventBus.LoggingHandler.INSTANCE);
   }

   public AsyncEventBus(Executor var1, SubscriberExceptionHandler var2) {
      super("default", var1, Dispatcher.legacyAsync(), var2);
   }
}
