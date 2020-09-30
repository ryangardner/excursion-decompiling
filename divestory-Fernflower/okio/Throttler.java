package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001d\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\fJ$\u0010\u0006\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u0004H\u0007J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u0011J\u0015\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\u0013J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0004H\u0002J\f\u0010\u0016\u001a\u00020\u0004*\u00020\u0004H\u0002J\f\u0010\u0017\u001a\u00020\u0004*\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lokio/Throttler;", "", "()V", "allocatedUntil", "", "(J)V", "bytesPerSecond", "maxByteCount", "waitByteCount", "byteCountOrWaitNanos", "now", "byteCount", "byteCountOrWaitNanos$okio", "", "sink", "Lokio/Sink;", "source", "Lokio/Source;", "take", "take$okio", "waitNanos", "nanosToWait", "bytesToNanos", "nanosToBytes", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Throttler {
   private long allocatedUntil;
   private long bytesPerSecond;
   private long maxByteCount;
   private long waitByteCount;

   public Throttler() {
      this(System.nanoTime());
   }

   public Throttler(long var1) {
      this.allocatedUntil = var1;
      this.waitByteCount = 8192L;
      this.maxByteCount = 262144L;
   }

   // $FF: synthetic method
   public static void bytesPerSecond$default(Throttler var0, long var1, long var3, long var5, int var7, Object var8) {
      if ((var7 & 2) != 0) {
         var3 = var0.waitByteCount;
      }

      if ((var7 & 4) != 0) {
         var5 = var0.maxByteCount;
      }

      var0.bytesPerSecond(var1, var3, var5);
   }

   private final long bytesToNanos(long var1) {
      return var1 * 1000000000L / this.bytesPerSecond;
   }

   private final long nanosToBytes(long var1) {
      return var1 * this.bytesPerSecond / 1000000000L;
   }

   private final void waitNanos(long var1) {
      long var3 = var1 / 1000000L;
      ((Object)this).wait(var3, (int)(var1 - 1000000L * var3));
   }

   public final long byteCountOrWaitNanos$okio(long var1, long var3) {
      if (this.bytesPerSecond == 0L) {
         return var3;
      } else {
         long var5 = Math.max(this.allocatedUntil - var1, 0L);
         long var7 = this.maxByteCount - this.nanosToBytes(var5);
         if (var7 >= var3) {
            this.allocatedUntil = var1 + var5 + this.bytesToNanos(var3);
            return var3;
         } else {
            long var9 = this.waitByteCount;
            if (var7 >= var9) {
               this.allocatedUntil = var1 + this.bytesToNanos(this.maxByteCount);
               return var7;
            } else {
               var3 = Math.min(var9, var3);
               var5 += this.bytesToNanos(var3 - this.maxByteCount);
               if (var5 == 0L) {
                  this.allocatedUntil = var1 + this.bytesToNanos(this.maxByteCount);
                  return var3;
               } else {
                  return -var5;
               }
            }
         }
      }
   }

   public final void bytesPerSecond(long var1) {
      bytesPerSecond$default(this, var1, 0L, 0L, 6, (Object)null);
   }

   public final void bytesPerSecond(long var1, long var3) {
      bytesPerSecond$default(this, var1, var3, 0L, 4, (Object)null);
   }

   public final void bytesPerSecond(long var1, long var3, long var5) {
      synchronized(this){}
      boolean var7 = true;
      boolean var8;
      if (var1 >= 0L) {
         var8 = true;
      } else {
         var8 = false;
      }

      Throwable var10000;
      boolean var10001;
      IllegalArgumentException var30;
      if (var8) {
         if (var3 > 0L) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            if (var5 >= var3) {
               var8 = var7;
            } else {
               var8 = false;
            }

            if (var8) {
               label221: {
                  try {
                     this.bytesPerSecond = var1;
                     this.waitByteCount = var3;
                     this.maxByteCount = var5;
                     ((Object)this).notifyAll();
                     Unit var9 = Unit.INSTANCE;
                  } catch (Throwable var26) {
                     var10000 = var26;
                     var10001 = false;
                     break label221;
                  }

                  return;
               }
            } else {
               label223:
               try {
                  var30 = new IllegalArgumentException("Failed requirement.".toString());
                  throw (Throwable)var30;
               } catch (Throwable var27) {
                  var10000 = var27;
                  var10001 = false;
                  break label223;
               }
            }
         } else {
            label227:
            try {
               var30 = new IllegalArgumentException("Failed requirement.".toString());
               throw (Throwable)var30;
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               break label227;
            }
         }
      } else {
         label231:
         try {
            var30 = new IllegalArgumentException("Failed requirement.".toString());
            throw (Throwable)var30;
         } catch (Throwable var29) {
            var10000 = var29;
            var10001 = false;
            break label231;
         }
      }

      Throwable var31 = var10000;
      throw var31;
   }

   public final Sink sink(final Sink var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      return (Sink)(new ForwardingSink(var1) {
         public void write(Buffer var1x, long var2) throws IOException {
            Intrinsics.checkParameterIsNotNull(var1x, "source");

            long var4;
            for(; var2 > 0L; var2 -= var4) {
               try {
                  var4 = Throttler.this.take$okio(var2);
                  super.write(var1x, var4);
               } catch (InterruptedException var6) {
                  Thread.currentThread().interrupt();
                  throw (Throwable)(new InterruptedIOException("interrupted"));
               }
            }

         }
      });
   }

   public final Source source(final Source var1) {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      return (Source)(new ForwardingSource(var1) {
         public long read(Buffer var1x, long var2) {
            Intrinsics.checkParameterIsNotNull(var1x, "sink");

            try {
               var2 = super.read(var1x, Throttler.this.take$okio(var2));
               return var2;
            } catch (InterruptedException var4) {
               Thread.currentThread().interrupt();
               throw (Throwable)(new InterruptedIOException("interrupted"));
            }
         }
      });
   }

   public final long take$okio(long var1) {
      boolean var3;
      if (var1 > 0L) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         synchronized(this){}

         while(true) {
            Throwable var10000;
            label123: {
               boolean var10001;
               long var4;
               try {
                  var4 = this.byteCountOrWaitNanos$okio(System.nanoTime(), var1);
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label123;
               }

               if (var4 >= 0L) {
                  return var4;
               }

               var4 = -var4;

               label108:
               try {
                  this.waitNanos(var4);
                  continue;
               } catch (Throwable var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label108;
               }
            }

            Throwable var6 = var10000;
            throw var6;
         }
      } else {
         throw (Throwable)(new IllegalArgumentException("Failed requirement.".toString()));
      }
   }
}
