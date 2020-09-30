package com.google.common.eventbus;

import com.google.common.base.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class Subscriber {
   private EventBus bus;
   private final Executor executor;
   private final Method method;
   final Object target;

   private Subscriber(EventBus var1, Object var2, Method var3) {
      this.bus = var1;
      this.target = Preconditions.checkNotNull(var2);
      this.method = var3;
      var3.setAccessible(true);
      this.executor = var1.executor();
   }

   // $FF: synthetic method
   Subscriber(EventBus var1, Object var2, Method var3, Object var4) {
      this(var1, var2, var3);
   }

   private SubscriberExceptionContext context(Object var1) {
      return new SubscriberExceptionContext(this.bus, var1, this.target, this.method);
   }

   static Subscriber create(EventBus var0, Object var1, Method var2) {
      Object var3;
      if (isDeclaredThreadSafe(var2)) {
         var3 = new Subscriber(var0, var1, var2);
      } else {
         var3 = new Subscriber.SynchronizedSubscriber(var0, var1, var2);
      }

      return (Subscriber)var3;
   }

   private static boolean isDeclaredThreadSafe(Method var0) {
      boolean var1;
      if (var0.getAnnotation(AllowConcurrentEvents.class) != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   final void dispatchEvent(final Object var1) {
      this.executor.execute(new Runnable() {
         public void run() {
            try {
               Subscriber.this.invokeSubscriberMethod(var1);
            } catch (InvocationTargetException var2) {
               Subscriber.this.bus.handleSubscriberException(var2.getCause(), Subscriber.this.context(var1));
            }

         }
      });
   }

   public final boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof Subscriber;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Subscriber var5 = (Subscriber)var1;
         var4 = var3;
         if (this.target == var5.target) {
            var4 = var3;
            if (this.method.equals(var5.method)) {
               var4 = true;
            }
         }
      }

      return var4;
   }

   public final int hashCode() {
      return (this.method.hashCode() + 31) * 31 + System.identityHashCode(this.target);
   }

   void invokeSubscriberMethod(Object var1) throws InvocationTargetException {
      try {
         this.method.invoke(this.target, Preconditions.checkNotNull(var1));
      } catch (IllegalArgumentException var4) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Method rejected target/argument: ");
         var2.append(var1);
         throw new Error(var2.toString(), var4);
      } catch (IllegalAccessException var5) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Method became inaccessible: ");
         var3.append(var1);
         throw new Error(var3.toString(), var5);
      } catch (InvocationTargetException var6) {
         if (var6.getCause() instanceof Error) {
            throw (Error)var6.getCause();
         } else {
            throw var6;
         }
      }
   }

   static final class SynchronizedSubscriber extends Subscriber {
      private SynchronizedSubscriber(EventBus var1, Object var2, Method var3) {
         super(var1, var2, var3, null);
      }

      // $FF: synthetic method
      SynchronizedSubscriber(EventBus var1, Object var2, Method var3, Object var4) {
         this(var1, var2, var3);
      }

      void invokeSubscriberMethod(Object param1) throws InvocationTargetException {
         // $FF: Couldn't be decompiled
      }
   }
}
