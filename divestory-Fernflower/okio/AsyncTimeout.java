package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 \u001b2\u00020\u0001:\u0002\u001b\u001cB\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0001J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\u0004J\u0012\u0010\u000e\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0014J\u0010\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007H\u0002J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u00020\fH\u0014J\"\u0010\u0016\u001a\u0002H\u0017\"\u0004\b\u0000\u0010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0019H\u0086\b¢\u0006\u0002\u0010\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lokio/AsyncTimeout;", "Lokio/Timeout;", "()V", "inQueue", "", "next", "timeoutAt", "", "access$newTimeoutException", "Ljava/io/IOException;", "cause", "enter", "", "exit", "newTimeoutException", "remainingNanos", "now", "sink", "Lokio/Sink;", "source", "Lokio/Source;", "timedOut", "withTimeout", "T", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "Companion", "Watchdog", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public class AsyncTimeout extends Timeout {
   public static final AsyncTimeout.Companion Companion = new AsyncTimeout.Companion((DefaultConstructorMarker)null);
   private static final long IDLE_TIMEOUT_MILLIS;
   private static final long IDLE_TIMEOUT_NANOS;
   private static final int TIMEOUT_WRITE_SIZE = 65536;
   private static AsyncTimeout head;
   private boolean inQueue;
   private AsyncTimeout next;
   private long timeoutAt;

   static {
      IDLE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60L);
      IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(IDLE_TIMEOUT_MILLIS);
   }

   // $FF: synthetic method
   public static final long access$getTimeoutAt$p(AsyncTimeout var0) {
      return var0.timeoutAt;
   }

   private final long remainingNanos(long var1) {
      return this.timeoutAt - var1;
   }

   public final IOException access$newTimeoutException(IOException var1) {
      return this.newTimeoutException(var1);
   }

   public final void enter() {
      if (this.inQueue ^ true) {
         long var1 = this.timeoutNanos();
         boolean var3 = this.hasDeadline();
         if (var1 != 0L || var3) {
            this.inQueue = true;
            Companion.scheduleTimeout(this, var1, var3);
         }
      } else {
         throw (Throwable)(new IllegalStateException("Unbalanced enter/exit".toString()));
      }
   }

   public final boolean exit() {
      if (!this.inQueue) {
         return false;
      } else {
         this.inQueue = false;
         return Companion.cancelScheduledTimeout(this);
      }
   }

   protected IOException newTimeoutException(IOException var1) {
      InterruptedIOException var2 = new InterruptedIOException("timeout");
      if (var1 != null) {
         var2.initCause((Throwable)var1);
      }

      return (IOException)var2;
   }

   public final Sink sink(final Sink var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      return (Sink)(new Sink() {
         public void close() {
            AsyncTimeout var1x = AsyncTimeout.this;
            var1x.enter();

            label297: {
               Throwable var39;
               Throwable var10000;
               label301: {
                  IOException var2;
                  boolean var10001;
                  try {
                     try {
                        var1.close();
                        Unit var40 = Unit.INSTANCE;
                        break label297;
                     } catch (IOException var37) {
                        var2 = var37;
                     }
                  } catch (Throwable var38) {
                     var10000 = var38;
                     var10001 = false;
                     break label301;
                  }

                  label302: {
                     try {
                        if (!var1x.exit()) {
                           break label302;
                        }
                     } catch (Throwable var36) {
                        var10000 = var36;
                        var10001 = false;
                        break label301;
                     }

                     try {
                        var2 = var1x.access$newTimeoutException(var2);
                     } catch (Throwable var35) {
                        var10000 = var35;
                        var10001 = false;
                        break label301;
                     }
                  }

                  try {
                     var39 = (Throwable)var2;
                  } catch (Throwable var34) {
                     var10000 = var34;
                     var10001 = false;
                     break label301;
                  }

                  label279:
                  try {
                     throw var39;
                  } catch (Throwable var33) {
                     var10000 = var33;
                     var10001 = false;
                     break label279;
                  }
               }

               var39 = var10000;
               var1x.exit();
               throw var39;
            }

            if (var1x.exit()) {
               throw (Throwable)var1x.access$newTimeoutException((IOException)null);
            }
         }

         public void flush() {
            AsyncTimeout var1x = AsyncTimeout.this;
            var1x.enter();

            label297: {
               Throwable var39;
               Throwable var10000;
               label301: {
                  IOException var2;
                  boolean var10001;
                  try {
                     try {
                        var1.flush();
                        Unit var40 = Unit.INSTANCE;
                        break label297;
                     } catch (IOException var37) {
                        var2 = var37;
                     }
                  } catch (Throwable var38) {
                     var10000 = var38;
                     var10001 = false;
                     break label301;
                  }

                  label302: {
                     try {
                        if (!var1x.exit()) {
                           break label302;
                        }
                     } catch (Throwable var36) {
                        var10000 = var36;
                        var10001 = false;
                        break label301;
                     }

                     try {
                        var2 = var1x.access$newTimeoutException(var2);
                     } catch (Throwable var35) {
                        var10000 = var35;
                        var10001 = false;
                        break label301;
                     }
                  }

                  try {
                     var39 = (Throwable)var2;
                  } catch (Throwable var34) {
                     var10000 = var34;
                     var10001 = false;
                     break label301;
                  }

                  label279:
                  try {
                     throw var39;
                  } catch (Throwable var33) {
                     var10000 = var33;
                     var10001 = false;
                     break label279;
                  }
               }

               var39 = var10000;
               var1x.exit();
               throw var39;
            }

            if (var1x.exit()) {
               throw (Throwable)var1x.access$newTimeoutException((IOException)null);
            }
         }

         public AsyncTimeout timeout() {
            return AsyncTimeout.this;
         }

         public String toString() {
            StringBuilder var1x = new StringBuilder();
            var1x.append("AsyncTimeout.sink(");
            var1x.append(var1);
            var1x.append(')');
            return var1x.toString();
         }

         public void write(Buffer var1x, long var2) {
            Intrinsics.checkParameterIsNotNull(var1x, "source");
            _Util.checkOffsetAndCount(var1x.size(), 0L, var2);

            while(true) {
               long var4 = 0L;
               if (var2 <= 0L) {
                  return;
               }

               Segment var6 = var1x.head;
               long var7 = var4;
               Segment var9 = var6;
               if (var6 == null) {
                  Intrinsics.throwNpe();
                  var9 = var6;
                  var7 = var4;
               }

               while(true) {
                  var4 = var7;
                  if (var7 >= (long)65536) {
                     break;
                  }

                  var4 = var7 + (long)(var9.limit - var9.pos);
                  if (var4 >= var2) {
                     var4 = var2;
                     break;
                  }

                  var6 = var9.next;
                  var7 = var4;
                  var9 = var6;
                  if (var6 == null) {
                     Intrinsics.throwNpe();
                     var7 = var4;
                     var9 = var6;
                  }
               }

               AsyncTimeout var49 = AsyncTimeout.this;
               var49.enter();

               label450: {
                  Throwable var47;
                  Throwable var10000;
                  label457: {
                     IOException var46;
                     boolean var10001;
                     try {
                        try {
                           var1.write(var1x, var4);
                           Unit var48 = Unit.INSTANCE;
                           break label450;
                        } catch (IOException var44) {
                           var46 = var44;
                        }
                     } catch (Throwable var45) {
                        var10000 = var45;
                        var10001 = false;
                        break label457;
                     }

                     label458: {
                        try {
                           if (!var49.exit()) {
                              break label458;
                           }
                        } catch (Throwable var43) {
                           var10000 = var43;
                           var10001 = false;
                           break label457;
                        }

                        try {
                           var46 = var49.access$newTimeoutException(var46);
                        } catch (Throwable var42) {
                           var10000 = var42;
                           var10001 = false;
                           break label457;
                        }
                     }

                     try {
                        var47 = (Throwable)var46;
                     } catch (Throwable var41) {
                        var10000 = var41;
                        var10001 = false;
                        break label457;
                     }

                     label432:
                     try {
                        throw var47;
                     } catch (Throwable var40) {
                        var10000 = var40;
                        var10001 = false;
                        break label432;
                     }
                  }

                  var47 = var10000;
                  var49.exit();
                  throw var47;
               }

               if (var49.exit()) {
                  throw (Throwable)var49.access$newTimeoutException((IOException)null);
               }

               var2 -= var4;
            }
         }
      });
   }

   public final Source source(final Source var1) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      return (Source)(new Source() {
         public void close() {
            AsyncTimeout var1x = AsyncTimeout.this;
            var1x.enter();

            label297: {
               Throwable var39;
               Throwable var10000;
               label301: {
                  IOException var2;
                  boolean var10001;
                  try {
                     try {
                        var1.close();
                        Unit var40 = Unit.INSTANCE;
                        break label297;
                     } catch (IOException var37) {
                        var2 = var37;
                     }
                  } catch (Throwable var38) {
                     var10000 = var38;
                     var10001 = false;
                     break label301;
                  }

                  label302: {
                     try {
                        if (!var1x.exit()) {
                           break label302;
                        }
                     } catch (Throwable var36) {
                        var10000 = var36;
                        var10001 = false;
                        break label301;
                     }

                     try {
                        var2 = var1x.access$newTimeoutException(var2);
                     } catch (Throwable var35) {
                        var10000 = var35;
                        var10001 = false;
                        break label301;
                     }
                  }

                  try {
                     var39 = (Throwable)var2;
                  } catch (Throwable var34) {
                     var10000 = var34;
                     var10001 = false;
                     break label301;
                  }

                  label279:
                  try {
                     throw var39;
                  } catch (Throwable var33) {
                     var10000 = var33;
                     var10001 = false;
                     break label279;
                  }
               }

               var39 = var10000;
               var1x.exit();
               throw var39;
            }

            if (var1x.exit()) {
               throw (Throwable)var1x.access$newTimeoutException((IOException)null);
            }
         }

         public long read(Buffer var1x, long var2) {
            Intrinsics.checkParameterIsNotNull(var1x, "sink");
            AsyncTimeout var4 = AsyncTimeout.this;
            var4.enter();

            label297: {
               Throwable var42;
               Throwable var10000;
               label301: {
                  IOException var41;
                  boolean var10001;
                  try {
                     try {
                        var2 = var1.read(var1x, var2);
                        break label297;
                     } catch (IOException var39) {
                        var41 = var39;
                     }
                  } catch (Throwable var40) {
                     var10000 = var40;
                     var10001 = false;
                     break label301;
                  }

                  label302: {
                     try {
                        if (!var4.exit()) {
                           break label302;
                        }
                     } catch (Throwable var38) {
                        var10000 = var38;
                        var10001 = false;
                        break label301;
                     }

                     try {
                        var41 = var4.access$newTimeoutException(var41);
                     } catch (Throwable var37) {
                        var10000 = var37;
                        var10001 = false;
                        break label301;
                     }
                  }

                  try {
                     var42 = (Throwable)var41;
                  } catch (Throwable var36) {
                     var10000 = var36;
                     var10001 = false;
                     break label301;
                  }

                  label279:
                  try {
                     throw var42;
                  } catch (Throwable var35) {
                     var10000 = var35;
                     var10001 = false;
                     break label279;
                  }
               }

               var42 = var10000;
               var4.exit();
               throw var42;
            }

            if (!var4.exit()) {
               return var2;
            } else {
               throw (Throwable)var4.access$newTimeoutException((IOException)null);
            }
         }

         public AsyncTimeout timeout() {
            return AsyncTimeout.this;
         }

         public String toString() {
            StringBuilder var1x = new StringBuilder();
            var1x.append("AsyncTimeout.source(");
            var1x.append(var1);
            var1x.append(')');
            return var1x.toString();
         }
      });
   }

   protected void timedOut() {
   }

   public final <T> T withTimeout(Function0<? extends T> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "block");
      this.enter();

      Object var40;
      label297: {
         Throwable var39;
         Throwable var10000;
         label301: {
            IOException var38;
            boolean var10001;
            try {
               try {
                  var40 = var1.invoke();
                  break label297;
               } catch (IOException var36) {
                  var38 = var36;
               }
            } catch (Throwable var37) {
               var10000 = var37;
               var10001 = false;
               break label301;
            }

            label302: {
               try {
                  if (!this.exit()) {
                     break label302;
                  }
               } catch (Throwable var35) {
                  var10000 = var35;
                  var10001 = false;
                  break label301;
               }

               try {
                  var38 = this.access$newTimeoutException(var38);
               } catch (Throwable var34) {
                  var10000 = var34;
                  var10001 = false;
                  break label301;
               }
            }

            try {
               var39 = (Throwable)var38;
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               break label301;
            }

            label279:
            try {
               throw var39;
            } catch (Throwable var32) {
               var10000 = var32;
               var10001 = false;
               break label279;
            }
         }

         var39 = var10000;
         InlineMarker.finallyStart(1);
         this.exit();
         InlineMarker.finallyEnd(1);
         throw var39;
      }

      InlineMarker.finallyStart(1);
      if (!this.exit()) {
         InlineMarker.finallyEnd(1);
         return var40;
      } else {
         throw (Throwable)this.access$newTimeoutException((IOException)null);
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000f\u0010\n\u001a\u0004\u0018\u00010\tH\u0000¢\u0006\u0002\b\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\u0002J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"},
      d2 = {"Lokio/AsyncTimeout$Companion;", "", "()V", "IDLE_TIMEOUT_MILLIS", "", "IDLE_TIMEOUT_NANOS", "TIMEOUT_WRITE_SIZE", "", "head", "Lokio/AsyncTimeout;", "awaitTimeout", "awaitTimeout$okio", "cancelScheduledTimeout", "", "node", "scheduleTimeout", "", "timeoutNanos", "hasDeadline", "okio"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      private final boolean cancelScheduledTimeout(AsyncTimeout var1) {
         synchronized(AsyncTimeout.class){}

         label141: {
            Throwable var10000;
            label140: {
               boolean var10001;
               AsyncTimeout var2;
               try {
                  var2 = AsyncTimeout.head;
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label140;
               }

               while(true) {
                  if (var2 == null) {
                     return true;
                  }

                  try {
                     if (var2.next == var1) {
                        var2.next = var1.next;
                        var1.next = (AsyncTimeout)null;
                        break label141;
                     }
                  } catch (Throwable var13) {
                     var10000 = var13;
                     var10001 = false;
                     break;
                  }

                  try {
                     var2 = var2.next;
                  } catch (Throwable var12) {
                     var10000 = var12;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var15 = var10000;
            throw var15;
         }

         return false;
      }

      private final void scheduleTimeout(AsyncTimeout var1, long var2, boolean var4) {
         synchronized(AsyncTimeout.class){}

         Throwable var10000;
         label1945: {
            AsyncTimeout var5;
            boolean var10001;
            try {
               if (AsyncTimeout.head == null) {
                  var5 = new AsyncTimeout();
                  AsyncTimeout.head = var5;
                  AsyncTimeout.Watchdog var253 = new AsyncTimeout.Watchdog();
                  var253.start();
               }
            } catch (Throwable var249) {
               var10000 = var249;
               var10001 = false;
               break label1945;
            }

            long var6;
            try {
               var6 = System.nanoTime();
            } catch (Throwable var248) {
               var10000 = var248;
               var10001 = false;
               break label1945;
            }

            long var254;
            int var8 = (var254 = var2 - 0L) == 0L ? 0 : (var254 < 0L ? -1 : 1);
            if (var8 != 0 && var4) {
               try {
                  var1.timeoutAt = Math.min(var2, var1.deadlineNanoTime() - var6) + var6;
               } catch (Throwable var247) {
                  var10000 = var247;
                  var10001 = false;
                  break label1945;
               }
            } else if (var8 != 0) {
               try {
                  var1.timeoutAt = var2 + var6;
               } catch (Throwable var246) {
                  var10000 = var246;
                  var10001 = false;
                  break label1945;
               }
            } else {
               if (!var4) {
                  try {
                     AssertionError var250 = new AssertionError();
                     throw (Throwable)var250;
                  } catch (Throwable var244) {
                     var10000 = var244;
                     var10001 = false;
                     break label1945;
                  }
               }

               try {
                  var1.timeoutAt = var1.deadlineNanoTime();
               } catch (Throwable var245) {
                  var10000 = var245;
                  var10001 = false;
                  break label1945;
               }
            }

            AsyncTimeout var9;
            try {
               var2 = var1.remainingNanos(var6);
               var9 = AsyncTimeout.head;
            } catch (Throwable var243) {
               var10000 = var243;
               var10001 = false;
               break label1945;
            }

            var5 = var9;
            if (var9 == null) {
               try {
                  Intrinsics.throwNpe();
               } catch (Throwable var240) {
                  var10000 = var240;
                  var10001 = false;
                  break label1945;
               }

               var5 = var9;
            }

            while(true) {
               try {
                  if (var5.next == null) {
                     break;
                  }

                  var9 = var5.next;
               } catch (Throwable var241) {
                  var10000 = var241;
                  var10001 = false;
                  break label1945;
               }

               if (var9 == null) {
                  try {
                     Intrinsics.throwNpe();
                  } catch (Throwable var239) {
                     var10000 = var239;
                     var10001 = false;
                     break label1945;
                  }
               }

               label1908:
               try {
                  if (var2 >= var9.remainingNanos(var6)) {
                     break label1908;
                  }
                  break;
               } catch (Throwable var242) {
                  var10000 = var242;
                  var10001 = false;
                  break label1945;
               }

               try {
                  var9 = var5.next;
               } catch (Throwable var238) {
                  var10000 = var238;
                  var10001 = false;
                  break label1945;
               }

               var5 = var9;
               if (var9 == null) {
                  try {
                     Intrinsics.throwNpe();
                  } catch (Throwable var237) {
                     var10000 = var237;
                     var10001 = false;
                     break label1945;
                  }

                  var5 = var9;
               }
            }

            try {
               var1.next = var5.next;
               var5.next = var1;
               if (var5 == AsyncTimeout.head) {
                  ((Object)AsyncTimeout.class).notify();
               }
            } catch (Throwable var236) {
               var10000 = var236;
               var10001 = false;
               break label1945;
            }

            try {
               Unit var252 = Unit.INSTANCE;
            } catch (Throwable var235) {
               var10000 = var235;
               var10001 = false;
               break label1945;
            }

            return;
         }

         Throwable var251 = var10000;
         throw var251;
      }

      public final AsyncTimeout awaitTimeout$okio() throws InterruptedException {
         AsyncTimeout var1 = AsyncTimeout.head;
         if (var1 == null) {
            Intrinsics.throwNpe();
         }

         var1 = var1.next;
         AsyncTimeout var2 = null;
         long var3;
         if (var1 == null) {
            var3 = System.nanoTime();
            ((Object)AsyncTimeout.class).wait(AsyncTimeout.IDLE_TIMEOUT_MILLIS);
            AsyncTimeout var5 = AsyncTimeout.head;
            if (var5 == null) {
               Intrinsics.throwNpe();
            }

            var1 = var2;
            if (var5.next == null) {
               var1 = var2;
               if (System.nanoTime() - var3 >= AsyncTimeout.IDLE_TIMEOUT_NANOS) {
                  var1 = AsyncTimeout.head;
               }
            }

            return var1;
         } else {
            long var6 = var1.remainingNanos(System.nanoTime());
            if (var6 > 0L) {
               var3 = var6 / 1000000L;
               ((Object)AsyncTimeout.class).wait(var3, (int)(var6 - 1000000L * var3));
               return null;
            } else {
               var2 = AsyncTimeout.head;
               if (var2 == null) {
                  Intrinsics.throwNpe();
               }

               var2.next = var1.next;
               var1.next = (AsyncTimeout)null;
               return var1;
            }
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0000¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"},
      d2 = {"Lokio/AsyncTimeout$Watchdog;", "Ljava/lang/Thread;", "()V", "run", "", "okio"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class Watchdog extends Thread {
      public Watchdog() {
         super("Okio Watchdog");
         this.setDaemon(true);
      }

      public void run() {
         // $FF: Couldn't be decompiled
      }
   }
}
