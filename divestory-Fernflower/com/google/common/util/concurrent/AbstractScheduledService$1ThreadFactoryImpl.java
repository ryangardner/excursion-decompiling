package com.google.common.util.concurrent;

import java.util.concurrent.ThreadFactory;

class AbstractScheduledService$1ThreadFactoryImpl implements ThreadFactory {
   // $FF: synthetic field
   final AbstractScheduledService this$0;

   AbstractScheduledService$1ThreadFactoryImpl(AbstractScheduledService var1) {
      this.this$0 = var1;
   }

   public Thread newThread(Runnable var1) {
      return MoreExecutors.newThread(this.this$0.serviceName(), var1);
   }
}
