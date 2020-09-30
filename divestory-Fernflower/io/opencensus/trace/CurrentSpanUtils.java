package io.opencensus.trace;

import io.grpc.Context;
import io.opencensus.common.Scope;
import io.opencensus.trace.unsafe.ContextUtils;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

final class CurrentSpanUtils {
   private CurrentSpanUtils() {
   }

   @Nullable
   static Span getCurrentSpan() {
      return ContextUtils.getValue(Context.current());
   }

   private static void setErrorStatus(Span var0, Throwable var1) {
      Status var2 = Status.UNKNOWN;
      String var3;
      if (var1.getMessage() == null) {
         var3 = var1.getClass().getSimpleName();
      } else {
         var3 = var1.getMessage();
      }

      var0.setStatus(var2.withDescription(var3));
   }

   static Scope withSpan(Span var0, boolean var1) {
      return new CurrentSpanUtils.ScopeInSpan(var0, var1);
   }

   static Runnable withSpan(Span var0, boolean var1, Runnable var2) {
      return new CurrentSpanUtils.RunnableInSpan(var0, var2, var1);
   }

   static <C> Callable<C> withSpan(Span var0, boolean var1, Callable<C> var2) {
      return new CurrentSpanUtils.CallableInSpan(var0, var2, var1);
   }

   private static final class CallableInSpan<V> implements Callable<V> {
      private final Callable<V> callable;
      private final boolean endSpan;
      private final Span span;

      private CallableInSpan(Span var1, Callable<V> var2, boolean var3) {
         this.span = var1;
         this.callable = var2;
         this.endSpan = var3;
      }

      // $FF: synthetic method
      CallableInSpan(Span var1, Callable var2, boolean var3, Object var4) {
         this(var1, var2, var3);
      }

      public V call() throws Exception {
         Context var1 = ContextUtils.withValue(Context.current(), this.span).attach();

         Object var31;
         label240: {
            Throwable var10000;
            label235: {
               boolean var10001;
               Exception var29;
               try {
                  try {
                     var31 = this.callable.call();
                     break label240;
                  } catch (Exception var27) {
                     var29 = var27;
                  }
               } catch (Throwable var28) {
                  Throwable var3 = var28;

                  try {
                     CurrentSpanUtils.setErrorStatus(this.span, var3);
                     if (var3 instanceof Error) {
                        throw (Error)var3;
                     }
                  } catch (Throwable var26) {
                     var10000 = var26;
                     var10001 = false;
                     break label235;
                  }

                  try {
                     RuntimeException var2 = new RuntimeException("unexpected", var3);
                     throw var2;
                  } catch (Throwable var24) {
                     var10000 = var24;
                     var10001 = false;
                     break label235;
                  }
               }

               label223:
               try {
                  CurrentSpanUtils.setErrorStatus(this.span, var29);
                  throw var29;
               } catch (Throwable var25) {
                  var10000 = var25;
                  var10001 = false;
                  break label223;
               }
            }

            Throwable var30 = var10000;
            Context.current().detach(var1);
            if (this.endSpan) {
               this.span.end();
            }

            throw var30;
         }

         Context.current().detach(var1);
         if (this.endSpan) {
            this.span.end();
         }

         return var31;
      }
   }

   private static final class RunnableInSpan implements Runnable {
      private final boolean endSpan;
      private final Runnable runnable;
      private final Span span;

      private RunnableInSpan(Span var1, Runnable var2, boolean var3) {
         this.span = var1;
         this.runnable = var2;
         this.endSpan = var3;
      }

      // $FF: synthetic method
      RunnableInSpan(Span var1, Runnable var2, boolean var3, Object var4) {
         this(var1, var2, var3);
      }

      public void run() {
         Context var1 = ContextUtils.withValue(Context.current(), this.span).attach();

         try {
            this.runnable.run();
         } catch (Throwable var9) {
            Throwable var2 = var9;

            try {
               CurrentSpanUtils.setErrorStatus(this.span, var2);
               if (!(var2 instanceof RuntimeException)) {
                  if (var2 instanceof Error) {
                     throw (Error)var2;
                  }

                  RuntimeException var3 = new RuntimeException("unexpected", var2);
                  throw var3;
               }

               throw (RuntimeException)var2;
            } finally {
               Context.current().detach(var1);
               if (this.endSpan) {
                  this.span.end();
               }

            }
         }

         Context.current().detach(var1);
         if (this.endSpan) {
            this.span.end();
         }

      }
   }

   private static final class ScopeInSpan implements Scope {
      private final boolean endSpan;
      private final Context origContext;
      private final Span span;

      private ScopeInSpan(Span var1, boolean var2) {
         this.span = var1;
         this.endSpan = var2;
         this.origContext = ContextUtils.withValue(Context.current(), var1).attach();
      }

      // $FF: synthetic method
      ScopeInSpan(Span var1, boolean var2, Object var3) {
         this(var1, var2);
      }

      public void close() {
         Context.current().detach(this.origContext);
         if (this.endSpan) {
            this.span.end();
         }

      }
   }
}
