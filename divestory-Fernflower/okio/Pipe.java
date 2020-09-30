package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\"2\u0006\u0010\u0017\u001a\u00020\u0010J\r\u0010\u0017\u001a\u00020\u0010H\u0007¢\u0006\u0002\b$J\r\u0010\u001b\u001a\u00020\u001cH\u0007¢\u0006\u0002\b%J&\u0010&\u001a\u00020\"*\u00020\u00102\u0017\u0010'\u001a\u0013\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\"0(¢\u0006\u0002\b)H\u0082\bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0017\u001a\u00020\u00108G¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u001a\u0010\u0018\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u0013\u0010\u001b\u001a\u00020\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\f\"\u0004\b \u0010\u000e¨\u0006*"},
   d2 = {"Lokio/Pipe;", "", "maxBufferSize", "", "(J)V", "buffer", "Lokio/Buffer;", "getBuffer$okio", "()Lokio/Buffer;", "canceled", "", "getCanceled$okio", "()Z", "setCanceled$okio", "(Z)V", "foldedSink", "Lokio/Sink;", "getFoldedSink$okio", "()Lokio/Sink;", "setFoldedSink$okio", "(Lokio/Sink;)V", "getMaxBufferSize$okio", "()J", "sink", "sinkClosed", "getSinkClosed$okio", "setSinkClosed$okio", "source", "Lokio/Source;", "()Lokio/Source;", "sourceClosed", "getSourceClosed$okio", "setSourceClosed$okio", "cancel", "", "fold", "-deprecated_sink", "-deprecated_source", "forward", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Pipe {
   private final Buffer buffer;
   private boolean canceled;
   private Sink foldedSink;
   private final long maxBufferSize;
   private final Sink sink;
   private boolean sinkClosed;
   private final Source source;
   private boolean sourceClosed;

   public Pipe(long var1) {
      this.maxBufferSize = var1;
      this.buffer = new Buffer();
      boolean var3;
      if (this.maxBufferSize >= 1L) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         this.sink = (Sink)(new Sink() {
            private final Timeout timeout = new Timeout();

            public void close() {
               Sink var1 = (Sink)null;
               Buffer var2 = Pipe.this.getBuffer$okio();
               synchronized(var2){}

               Throwable var10000;
               label1136: {
                  boolean var3;
                  boolean var10001;
                  try {
                     var3 = Pipe.this.getSinkClosed$okio();
                  } catch (Throwable var118) {
                     var10000 = var118;
                     var10001 = false;
                     break label1136;
                  }

                  if (var3) {
                     return;
                  }

                  Sink var4;
                  try {
                     var4 = Pipe.this.getFoldedSink$okio();
                  } catch (Throwable var117) {
                     var10000 = var117;
                     var10001 = false;
                     break label1136;
                  }

                  label1140: {
                     if (var4 != null) {
                        var1 = var4;
                     } else {
                        try {
                           if (Pipe.this.getSourceClosed$okio() && Pipe.this.getBuffer$okio().size() > 0L) {
                              break label1140;
                           }
                        } catch (Throwable var116) {
                           var10000 = var116;
                           var10001 = false;
                           break label1136;
                        }

                        Buffer var124;
                        try {
                           Pipe.this.setSinkClosed$okio(true);
                           var124 = Pipe.this.getBuffer$okio();
                        } catch (Throwable var115) {
                           var10000 = var115;
                           var10001 = false;
                           break label1136;
                        }

                        if (var124 == null) {
                           try {
                              TypeCastException var119 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                              throw var119;
                           } catch (Throwable var113) {
                              var10000 = var113;
                              var10001 = false;
                              break label1136;
                           }
                        }

                        try {
                           ((Object)var124).notifyAll();
                        } catch (Throwable var114) {
                           var10000 = var114;
                           var10001 = false;
                           break label1136;
                        }
                     }

                     try {
                        Unit var125 = Unit.INSTANCE;
                     } catch (Throwable var112) {
                        var10000 = var112;
                        var10001 = false;
                        break label1136;
                     }

                     if (var1 != null) {
                        Pipe var122 = Pipe.this;
                        Timeout var126 = var1.timeout();
                        Timeout var123 = var122.sink().timeout();
                        long var5 = var126.timeoutNanos();
                        var126.timeout(Timeout.Companion.minTimeout(var123.timeoutNanos(), var126.timeoutNanos()), TimeUnit.NANOSECONDS);
                        if (var126.hasDeadline()) {
                           long var7 = var126.deadlineNanoTime();
                           if (var123.hasDeadline()) {
                              var126.deadlineNanoTime(Math.min(var126.deadlineNanoTime(), var123.deadlineNanoTime()));
                           }

                           try {
                              var1.close();
                           } finally {
                              var126.timeout(var5, TimeUnit.NANOSECONDS);
                              if (var123.hasDeadline()) {
                                 var126.deadlineNanoTime(var7);
                              }

                           }
                        } else {
                           if (var123.hasDeadline()) {
                              var126.deadlineNanoTime(var123.deadlineNanoTime());
                           }

                           try {
                              var1.close();
                           } finally {
                              var126.timeout(var5, TimeUnit.NANOSECONDS);
                              if (var123.hasDeadline()) {
                                 var126.clearDeadline();
                              }

                           }
                        }
                     }

                     return;
                  }

                  label1100:
                  try {
                     IOException var120 = new IOException("source is closed");
                     throw (Throwable)var120;
                  } catch (Throwable var111) {
                     var10000 = var111;
                     var10001 = false;
                     break label1100;
                  }
               }

               Throwable var121 = var10000;
               throw var121;
            }

            public void flush() {
               Sink var1 = (Sink)null;
               Buffer var2 = Pipe.this.getBuffer$okio();
               synchronized(var2){}

               Throwable var10000;
               label910: {
                  boolean var10001;
                  IOException var84;
                  label909: {
                     label918: {
                        Sink var3;
                        try {
                           if (!(Pipe.this.getSinkClosed$okio() ^ true)) {
                              break label918;
                           }

                           if (Pipe.this.getCanceled$okio()) {
                              break label909;
                           }

                           var3 = Pipe.this.getFoldedSink$okio();
                        } catch (Throwable var79) {
                           var10000 = var79;
                           var10001 = false;
                           break label910;
                        }

                        if (var3 == null) {
                           label917: {
                              var3 = var1;

                              label892: {
                                 try {
                                    if (!Pipe.this.getSourceClosed$okio()) {
                                       break label917;
                                    }

                                    if (Pipe.this.getBuffer$okio().size() <= 0L) {
                                       break label892;
                                    }
                                 } catch (Throwable var76) {
                                    var10000 = var76;
                                    var10001 = false;
                                    break label910;
                                 }

                                 try {
                                    var84 = new IOException("source is closed");
                                    throw (Throwable)var84;
                                 } catch (Throwable var74) {
                                    var10000 = var74;
                                    var10001 = false;
                                    break label910;
                                 }
                              }

                              var3 = var1;
                           }
                        }

                        try {
                           Unit var80 = Unit.INSTANCE;
                        } catch (Throwable var75) {
                           var10000 = var75;
                           var10001 = false;
                           break label910;
                        }

                        if (var3 != null) {
                           Pipe var82 = Pipe.this;
                           Timeout var81 = var3.timeout();
                           Timeout var83 = var82.sink().timeout();
                           long var4 = var81.timeoutNanos();
                           var81.timeout(Timeout.Companion.minTimeout(var83.timeoutNanos(), var81.timeoutNanos()), TimeUnit.NANOSECONDS);
                           if (var81.hasDeadline()) {
                              long var6 = var81.deadlineNanoTime();
                              if (var83.hasDeadline()) {
                                 var81.deadlineNanoTime(Math.min(var81.deadlineNanoTime(), var83.deadlineNanoTime()));
                              }

                              try {
                                 var3.flush();
                              } finally {
                                 var81.timeout(var4, TimeUnit.NANOSECONDS);
                                 if (var83.hasDeadline()) {
                                    var81.deadlineNanoTime(var6);
                                 }

                              }
                           } else {
                              if (var83.hasDeadline()) {
                                 var81.deadlineNanoTime(var83.deadlineNanoTime());
                              }

                              try {
                                 var3.flush();
                              } finally {
                                 var81.timeout(var4, TimeUnit.NANOSECONDS);
                                 if (var83.hasDeadline()) {
                                    var81.clearDeadline();
                                 }

                              }
                           }
                        }

                        return;
                     }

                     try {
                        IllegalStateException var86 = new IllegalStateException("closed".toString());
                        throw (Throwable)var86;
                     } catch (Throwable var78) {
                        var10000 = var78;
                        var10001 = false;
                        break label910;
                     }
                  }

                  label897:
                  try {
                     var84 = new IOException("canceled");
                     throw (Throwable)var84;
                  } catch (Throwable var77) {
                     var10000 = var77;
                     var10001 = false;
                     break label897;
                  }
               }

               Throwable var85 = var10000;
               throw var85;
            }

            public Timeout timeout() {
               return this.timeout;
            }

            public void write(Buffer var1, long var2) {
               Intrinsics.checkParameterIsNotNull(var1, "source");
               Sink var4 = (Sink)null;
               Buffer var5 = Pipe.this.getBuffer$okio();
               synchronized(var5){}

               Throwable var10000;
               label2076: {
                  boolean var10001;
                  IOException var252;
                  label2075: {
                     label2074: {
                        try {
                           if (Pipe.this.getSinkClosed$okio() ^ true) {
                              if (!Pipe.this.getCanceled$okio()) {
                                 break label2075;
                              }
                              break label2074;
                           }
                        } catch (Throwable var250) {
                           var10000 = var250;
                           var10001 = false;
                           break label2076;
                        }

                        try {
                           IllegalStateException var251 = new IllegalStateException("closed".toString());
                           throw (Throwable)var251;
                        } catch (Throwable var243) {
                           var10000 = var243;
                           var10001 = false;
                           break label2076;
                        }
                     }

                     try {
                        var252 = new IOException("canceled");
                        throw (Throwable)var252;
                     } catch (Throwable var242) {
                        var10000 = var242;
                        var10001 = false;
                        break label2076;
                     }
                  }

                  while(true) {
                     Sink var6 = var4;
                     long var7;
                     if (var2 > 0L) {
                        try {
                           var6 = Pipe.this.getFoldedSink$okio();
                        } catch (Throwable var247) {
                           var10000 = var247;
                           var10001 = false;
                           break;
                        }

                        if (var6 == null) {
                           label2060: {
                              try {
                                 if (!Pipe.this.getSourceClosed$okio()) {
                                    var7 = Pipe.this.getMaxBufferSize$okio() - Pipe.this.getBuffer$okio().size();
                                    break label2060;
                                 }
                              } catch (Throwable var249) {
                                 var10000 = var249;
                                 var10001 = false;
                                 break;
                              }

                              try {
                                 var252 = new IOException("source is closed");
                                 throw (Throwable)var252;
                              } catch (Throwable var240) {
                                 var10000 = var240;
                                 var10001 = false;
                                 break;
                              }
                           }

                           if (var7 == 0L) {
                              try {
                                 this.timeout.waitUntilNotified(Pipe.this.getBuffer$okio());
                                 if (!Pipe.this.getCanceled$okio()) {
                                    continue;
                                 }
                              } catch (Throwable var248) {
                                 var10000 = var248;
                                 var10001 = false;
                                 break;
                              }

                              try {
                                 var252 = new IOException("canceled");
                                 throw (Throwable)var252;
                              } catch (Throwable var238) {
                                 var10000 = var238;
                                 var10001 = false;
                                 break;
                              }
                           }

                           try {
                              var7 = Math.min(var7, var2);
                              Pipe.this.getBuffer$okio().write(var1, var7);
                           } catch (Throwable var246) {
                              var10000 = var246;
                              var10001 = false;
                              break;
                           }

                           var2 -= var7;

                           Buffer var259;
                           try {
                              var259 = Pipe.this.getBuffer$okio();
                           } catch (Throwable var245) {
                              var10000 = var245;
                              var10001 = false;
                              break;
                           }

                           if (var259 == null) {
                              try {
                                 TypeCastException var253 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                                 throw var253;
                              } catch (Throwable var239) {
                                 var10000 = var239;
                                 var10001 = false;
                                 break;
                              }
                           }

                           try {
                              ((Object)var259).notifyAll();
                              continue;
                           } catch (Throwable var244) {
                              var10000 = var244;
                              var10001 = false;
                              break;
                           }
                        }
                     }

                     try {
                        Unit var255 = Unit.INSTANCE;
                     } catch (Throwable var241) {
                        var10000 = var241;
                        var10001 = false;
                        break;
                     }

                     if (var6 != null) {
                        Pipe var257 = Pipe.this;
                        Timeout var256 = var6.timeout();
                        Timeout var258 = var257.sink().timeout();
                        var7 = var256.timeoutNanos();
                        var256.timeout(Timeout.Companion.minTimeout(var258.timeoutNanos(), var256.timeoutNanos()), TimeUnit.NANOSECONDS);
                        if (var256.hasDeadline()) {
                           long var9 = var256.deadlineNanoTime();
                           if (var258.hasDeadline()) {
                              var256.deadlineNanoTime(Math.min(var256.deadlineNanoTime(), var258.deadlineNanoTime()));
                           }

                           try {
                              var6.write(var1, var2);
                           } finally {
                              var256.timeout(var7, TimeUnit.NANOSECONDS);
                              if (var258.hasDeadline()) {
                                 var256.deadlineNanoTime(var9);
                              }

                           }
                        } else {
                           if (var258.hasDeadline()) {
                              var256.deadlineNanoTime(var258.deadlineNanoTime());
                           }

                           try {
                              var6.write(var1, var2);
                           } finally {
                              var256.timeout(var7, TimeUnit.NANOSECONDS);
                              if (var258.hasDeadline()) {
                                 var256.clearDeadline();
                              }

                           }
                        }
                     }

                     return;
                  }
               }

               Throwable var254 = var10000;
               throw var254;
            }
         });
         this.source = (Source)(new Source() {
            private final Timeout timeout = new Timeout();

            public void close() {
               Buffer var1 = Pipe.this.getBuffer$okio();
               synchronized(var1){}

               Throwable var10000;
               label105: {
                  boolean var10001;
                  Buffer var2;
                  try {
                     Pipe.this.setSourceClosed$okio(true);
                     var2 = Pipe.this.getBuffer$okio();
                  } catch (Throwable var14) {
                     var10000 = var14;
                     var10001 = false;
                     break label105;
                  }

                  if (var2 != null) {
                     label99: {
                        try {
                           ((Object)var2).notifyAll();
                           Unit var15 = Unit.INSTANCE;
                        } catch (Throwable var12) {
                           var10000 = var12;
                           var10001 = false;
                           break label99;
                        }

                        return;
                     }
                  } else {
                     label101:
                     try {
                        TypeCastException var17 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                        throw var17;
                     } catch (Throwable var13) {
                        var10000 = var13;
                        var10001 = false;
                        break label101;
                     }
                  }
               }

               Throwable var16 = var10000;
               throw var16;
            }

            public long read(Buffer var1, long var2) {
               Intrinsics.checkParameterIsNotNull(var1, "sink");
               Buffer var4 = Pipe.this.getBuffer$okio();
               synchronized(var4){}

               Throwable var10000;
               label783: {
                  IOException var96;
                  boolean var10001;
                  label782: {
                     label787: {
                        try {
                           if (!(Pipe.this.getSourceClosed$okio() ^ true)) {
                              break label787;
                           }

                           if (Pipe.this.getCanceled$okio()) {
                              break label782;
                           }
                        } catch (Throwable var95) {
                           var10000 = var95;
                           var10001 = false;
                           break label783;
                        }

                        while(true) {
                           boolean var5;
                           try {
                              if (Pipe.this.getBuffer$okio().size() != 0L) {
                                 break;
                              }

                              var5 = Pipe.this.getSinkClosed$okio();
                           } catch (Throwable var94) {
                              var10000 = var94;
                              var10001 = false;
                              break label783;
                           }

                           if (var5) {
                              return -1L;
                           }

                           label764:
                           try {
                              this.timeout.waitUntilNotified(Pipe.this.getBuffer$okio());
                              if (Pipe.this.getCanceled$okio()) {
                                 break label764;
                              }
                              continue;
                           } catch (Throwable var93) {
                              var10000 = var93;
                              var10001 = false;
                              break label783;
                           }

                           try {
                              var96 = new IOException("canceled");
                              throw (Throwable)var96;
                           } catch (Throwable var87) {
                              var10000 = var87;
                              var10001 = false;
                              break label783;
                           }
                        }

                        try {
                           var2 = Pipe.this.getBuffer$okio().read(var1, var2);
                           var1 = Pipe.this.getBuffer$okio();
                        } catch (Throwable var90) {
                           var10000 = var90;
                           var10001 = false;
                           break label783;
                        }

                        if (var1 != null) {
                           try {
                              ((Object)var1).notifyAll();
                           } catch (Throwable var88) {
                              var10000 = var88;
                              var10001 = false;
                              break label783;
                           }

                           return var2;
                        } else {
                           try {
                              TypeCastException var97 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                              throw var97;
                           } catch (Throwable var89) {
                              var10000 = var89;
                              var10001 = false;
                              break label783;
                           }
                        }
                     }

                     try {
                        IllegalStateException var99 = new IllegalStateException("closed".toString());
                        throw (Throwable)var99;
                     } catch (Throwable var92) {
                        var10000 = var92;
                        var10001 = false;
                        break label783;
                     }
                  }

                  label756:
                  try {
                     var96 = new IOException("canceled");
                     throw (Throwable)var96;
                  } catch (Throwable var91) {
                     var10000 = var91;
                     var10001 = false;
                     break label756;
                  }
               }

               Throwable var98 = var10000;
               throw var98;
            }

            public Timeout timeout() {
               return this.timeout;
            }
         });
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("maxBufferSize < 1: ");
         var4.append(this.maxBufferSize);
         throw (Throwable)(new IllegalArgumentException(var4.toString().toString()));
      }
   }

   // $FF: synthetic method
   public static final void access$forward(Pipe var0, Sink var1, Function1 var2) {
      var0.forward(var1, var2);
   }

   private final void forward(Sink var1, Function1<? super Sink, Unit> var2) {
      Timeout var3 = var1.timeout();
      Timeout var4 = this.sink().timeout();
      long var5 = var3.timeoutNanos();
      var3.timeout(Timeout.Companion.minTimeout(var4.timeoutNanos(), var3.timeoutNanos()), TimeUnit.NANOSECONDS);
      if (var3.hasDeadline()) {
         long var7 = var3.deadlineNanoTime();
         if (var4.hasDeadline()) {
            var3.deadlineNanoTime(Math.min(var3.deadlineNanoTime(), var4.deadlineNanoTime()));
         }

         try {
            var2.invoke(var1);
         } finally {
            InlineMarker.finallyStart(1);
            var3.timeout(var5, TimeUnit.NANOSECONDS);
            if (var4.hasDeadline()) {
               var3.deadlineNanoTime(var7);
            }

            InlineMarker.finallyEnd(1);
         }
      } else {
         if (var4.hasDeadline()) {
            var3.deadlineNanoTime(var4.deadlineNanoTime());
         }

         try {
            var2.invoke(var1);
         } finally {
            InlineMarker.finallyStart(1);
            var3.timeout(var5, TimeUnit.NANOSECONDS);
            if (var4.hasDeadline()) {
               var3.clearDeadline();
            }

            InlineMarker.finallyEnd(1);
         }
      }

   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "sink",
   imports = {}
)
   )
   public final Sink _deprecated_sink/* $FF was: -deprecated_sink*/() {
      return this.sink;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "source",
   imports = {}
)
   )
   public final Source _deprecated_source/* $FF was: -deprecated_source*/() {
      return this.source;
   }

   public final void cancel() {
      Buffer var1 = this.buffer;
      synchronized(var1){}

      Throwable var10000;
      label105: {
         boolean var10001;
         Buffer var2;
         try {
            this.canceled = true;
            this.buffer.clear();
            var2 = this.buffer;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label105;
         }

         if (var2 != null) {
            label99: {
               try {
                  ((Object)var2).notifyAll();
                  Unit var15 = Unit.INSTANCE;
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label99;
               }

               return;
            }
         } else {
            label101:
            try {
               TypeCastException var17 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
               throw var17;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label101;
            }
         }
      }

      Throwable var16 = var10000;
      throw var16;
   }

   public final void fold(Sink var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "sink");

      while(true) {
         Buffer var2 = this.buffer;
         synchronized(var2){}

         Throwable var10000;
         label1575: {
            boolean var3;
            boolean var10001;
            label1574: {
               label1573: {
                  try {
                     if (this.foldedSink == null) {
                        break label1573;
                     }
                  } catch (Throwable var188) {
                     var10000 = var188;
                     var10001 = false;
                     break label1575;
                  }

                  var3 = false;
                  break label1574;
               }

               var3 = true;
            }

            if (var3) {
               label1566: {
                  label1582: {
                     try {
                        if (this.canceled) {
                           break label1582;
                        }

                        if (this.buffer.exhausted()) {
                           this.sourceClosed = true;
                           this.foldedSink = var1;
                           return;
                        }
                     } catch (Throwable var187) {
                        var10000 = var187;
                        var10001 = false;
                        break label1566;
                     }

                     boolean var4;
                     Buffer var5;
                     Buffer var6;
                     try {
                        var4 = this.sinkClosed;
                        var5 = new Buffer();
                        var5.write(this.buffer, this.buffer.size());
                        var6 = this.buffer;
                     } catch (Throwable var184) {
                        var10000 = var184;
                        var10001 = false;
                        break label1566;
                     }

                     if (var6 != null) {
                        try {
                           ((Object)var6).notifyAll();
                           Unit var197 = Unit.INSTANCE;
                        } catch (Throwable var182) {
                           var10000 = var182;
                           var10001 = false;
                           break label1566;
                        }

                        label1539: {
                           try {
                              var1.write(var5, var5.size());
                           } catch (Throwable var181) {
                              var10000 = var181;
                              var10001 = false;
                              break label1539;
                           }

                           if (var4) {
                              label1533:
                              try {
                                 var1.close();
                              } catch (Throwable var179) {
                                 var10000 = var179;
                                 var10001 = false;
                                 break label1533;
                              }
                           } else {
                              label1535:
                              try {
                                 var1.flush();
                              } catch (Throwable var180) {
                                 var10000 = var180;
                                 var10001 = false;
                                 break label1535;
                              }
                           }
                           continue;
                        }

                        Throwable var193 = var10000;
                        Buffer var189 = this.buffer;
                        synchronized(var189){}

                        label1527: {
                           try {
                              this.sourceClosed = true;
                              var5 = this.buffer;
                           } catch (Throwable var178) {
                              var10000 = var178;
                              var10001 = false;
                              break label1527;
                           }

                           if (var5 == null) {
                              label1520:
                              try {
                                 TypeCastException var195 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                                 throw var195;
                              } catch (Throwable var176) {
                                 var10000 = var176;
                                 var10001 = false;
                                 break label1520;
                              }
                           } else {
                              label1523: {
                                 try {
                                    ((Object)var5).notifyAll();
                                    Unit var196 = Unit.INSTANCE;
                                 } catch (Throwable var177) {
                                    var10000 = var177;
                                    var10001 = false;
                                    break label1523;
                                 }

                                 throw var193;
                              }
                           }
                        }

                        var193 = var10000;
                        throw var193;
                     } else {
                        try {
                           TypeCastException var190 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                           throw var190;
                        } catch (Throwable var183) {
                           var10000 = var183;
                           var10001 = false;
                           break label1566;
                        }
                     }
                  }

                  label1553:
                  try {
                     this.foldedSink = var1;
                     IOException var191 = new IOException("canceled");
                     throw (Throwable)var191;
                  } catch (Throwable var185) {
                     var10000 = var185;
                     var10001 = false;
                     break label1553;
                  }
               }
            } else {
               label1555:
               try {
                  IllegalStateException var194 = new IllegalStateException("sink already folded".toString());
                  throw (Throwable)var194;
               } catch (Throwable var186) {
                  var10000 = var186;
                  var10001 = false;
                  break label1555;
               }
            }
         }

         Throwable var192 = var10000;
         throw var192;
      }
   }

   public final Buffer getBuffer$okio() {
      return this.buffer;
   }

   public final boolean getCanceled$okio() {
      return this.canceled;
   }

   public final Sink getFoldedSink$okio() {
      return this.foldedSink;
   }

   public final long getMaxBufferSize$okio() {
      return this.maxBufferSize;
   }

   public final boolean getSinkClosed$okio() {
      return this.sinkClosed;
   }

   public final boolean getSourceClosed$okio() {
      return this.sourceClosed;
   }

   public final void setCanceled$okio(boolean var1) {
      this.canceled = var1;
   }

   public final void setFoldedSink$okio(Sink var1) {
      this.foldedSink = var1;
   }

   public final void setSinkClosed$okio(boolean var1) {
      this.sinkClosed = var1;
   }

   public final void setSourceClosed$okio(boolean var1) {
      this.sourceClosed = var1;
   }

   public final Sink sink() {
      return this.sink;
   }

   public final Source source() {
      return this.source;
   }
}
