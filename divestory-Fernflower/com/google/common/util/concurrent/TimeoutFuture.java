package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class TimeoutFuture<V> extends FluentFuture.TrustedFuture<V> {
   @NullableDecl
   private ListenableFuture<V> delegateRef;
   @NullableDecl
   private ScheduledFuture<?> timer;

   private TimeoutFuture(ListenableFuture<V> var1) {
      this.delegateRef = (ListenableFuture)Preconditions.checkNotNull(var1);
   }

   static <V> ListenableFuture<V> create(ListenableFuture<V> var0, long var1, TimeUnit var3, ScheduledExecutorService var4) {
      TimeoutFuture var5 = new TimeoutFuture(var0);
      TimeoutFuture.Fire var6 = new TimeoutFuture.Fire(var5);
      var5.timer = var4.schedule(var6, var1, var3);
      var0.addListener(var6, MoreExecutors.directExecutor());
      return var5;
   }

   protected void afterDone() {
      this.maybePropagateCancellationTo(this.delegateRef);
      ScheduledFuture var1 = this.timer;
      if (var1 != null) {
         var1.cancel(false);
      }

      this.delegateRef = null;
      this.timer = null;
   }

   protected String pendingToString() {
      ListenableFuture var1 = this.delegateRef;
      ScheduledFuture var2 = this.timer;
      if (var1 != null) {
         StringBuilder var3 = new StringBuilder();
         var3.append("inputFuture=[");
         var3.append(var1);
         var3.append("]");
         String var6 = var3.toString();
         String var7 = var6;
         if (var2 != null) {
            long var4 = var2.getDelay(TimeUnit.MILLISECONDS);
            var7 = var6;
            if (var4 > 0L) {
               var3 = new StringBuilder();
               var3.append(var6);
               var3.append(", remaining delay=[");
               var3.append(var4);
               var3.append(" ms]");
               var7 = var3.toString();
            }
         }

         return var7;
      } else {
         return null;
      }
   }

   private static final class Fire<V> implements Runnable {
      @NullableDecl
      TimeoutFuture<V> timeoutFutureRef;

      Fire(TimeoutFuture<V> var1) {
         this.timeoutFutureRef = var1;
      }

      public void run() {
         TimeoutFuture var1 = this.timeoutFutureRef;
         if (var1 != null) {
            ListenableFuture var2 = var1.delegateRef;
            if (var2 != null) {
               this.timeoutFutureRef = null;
               if (var2.isDone()) {
                  var1.setFuture(var2);
               } else {
                  label2578: {
                     Throwable var10000;
                     Throwable var318;
                     label2588: {
                        ScheduledFuture var3;
                        boolean var10001;
                        try {
                           var3 = var1.timer;
                           var1.timer = null;
                        } catch (Throwable var314) {
                           var10000 = var314;
                           var10001 = false;
                           break label2588;
                        }

                        String var5;
                        label2589: {
                           String var6;
                           label2590: {
                              String var4 = "Timed out";
                              var5 = var4;
                              if (var3 != null) {
                                 var6 = var4;

                                 long var7;
                                 try {
                                    var7 = Math.abs(var3.getDelay(TimeUnit.MILLISECONDS));
                                 } catch (Throwable var313) {
                                    var10000 = var313;
                                    var10001 = false;
                                    break label2590;
                                 }

                                 var5 = var4;
                                 if (var7 > 10L) {
                                    var6 = var4;

                                    StringBuilder var317;
                                    try {
                                       var317 = new StringBuilder;
                                    } catch (Throwable var312) {
                                       var10000 = var312;
                                       var10001 = false;
                                       break label2590;
                                    }

                                    var6 = var4;

                                    try {
                                       var317.<init>();
                                    } catch (Throwable var311) {
                                       var10000 = var311;
                                       var10001 = false;
                                       break label2590;
                                    }

                                    var6 = var4;

                                    try {
                                       var317.append("Timed out");
                                    } catch (Throwable var310) {
                                       var10000 = var310;
                                       var10001 = false;
                                       break label2590;
                                    }

                                    var6 = var4;

                                    try {
                                       var317.append(" (timeout delayed by ");
                                    } catch (Throwable var309) {
                                       var10000 = var309;
                                       var10001 = false;
                                       break label2590;
                                    }

                                    var6 = var4;

                                    try {
                                       var317.append(var7);
                                    } catch (Throwable var308) {
                                       var10000 = var308;
                                       var10001 = false;
                                       break label2590;
                                    }

                                    var6 = var4;

                                    try {
                                       var317.append(" ms after scheduled time)");
                                    } catch (Throwable var307) {
                                       var10000 = var307;
                                       var10001 = false;
                                       break label2590;
                                    }

                                    var6 = var4;

                                    try {
                                       var5 = var317.toString();
                                    } catch (Throwable var306) {
                                       var10000 = var306;
                                       var10001 = false;
                                       break label2590;
                                    }
                                 }
                              }

                              var6 = var5;

                              StringBuilder var315;
                              try {
                                 var315 = new StringBuilder;
                              } catch (Throwable var305) {
                                 var10000 = var305;
                                 var10001 = false;
                                 break label2590;
                              }

                              var6 = var5;

                              try {
                                 var315.<init>();
                              } catch (Throwable var304) {
                                 var10000 = var304;
                                 var10001 = false;
                                 break label2590;
                              }

                              var6 = var5;

                              try {
                                 var315.append(var5);
                              } catch (Throwable var303) {
                                 var10000 = var303;
                                 var10001 = false;
                                 break label2590;
                              }

                              var6 = var5;

                              try {
                                 var315.append(": ");
                              } catch (Throwable var302) {
                                 var10000 = var302;
                                 var10001 = false;
                                 break label2590;
                              }

                              var6 = var5;

                              try {
                                 var315.append(var2);
                              } catch (Throwable var301) {
                                 var10000 = var301;
                                 var10001 = false;
                                 break label2590;
                              }

                              var6 = var5;

                              label2524:
                              try {
                                 var5 = var315.toString();
                                 break label2589;
                              } catch (Throwable var300) {
                                 var10000 = var300;
                                 var10001 = false;
                                 break label2524;
                              }
                           }

                           var318 = var10000;

                           try {
                              TimeoutFuture.TimeoutFutureException var316 = new TimeoutFuture.TimeoutFutureException(var6);
                              var1.setException(var316);
                              throw var318;
                           } catch (Throwable var298) {
                              var10000 = var298;
                              var10001 = false;
                              break label2588;
                           }
                        }

                        label2520:
                        try {
                           TimeoutFuture.TimeoutFutureException var319 = new TimeoutFuture.TimeoutFutureException(var5);
                           var1.setException(var319);
                           break label2578;
                        } catch (Throwable var299) {
                           var10000 = var299;
                           var10001 = false;
                           break label2520;
                        }
                     }

                     var318 = var10000;
                     var2.cancel(true);
                     throw var318;
                  }

                  var2.cancel(true);
               }

            }
         }
      }
   }

   private static final class TimeoutFutureException extends TimeoutException {
      private TimeoutFutureException(String var1) {
         super(var1);
      }

      // $FF: synthetic method
      TimeoutFutureException(String var1, Object var2) {
         this(var1);
      }

      public Throwable fillInStackTrace() {
         synchronized(this){}

         try {
            this.setStackTrace(new StackTraceElement[0]);
         } finally {
            ;
         }

         return this;
      }
   }
}
