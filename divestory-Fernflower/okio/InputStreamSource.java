package okio;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nH\u0016J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
   d2 = {"Lokio/InputStreamSource;", "Lokio/Source;", "input", "Ljava/io/InputStream;", "timeout", "Lokio/Timeout;", "(Ljava/io/InputStream;Lokio/Timeout;)V", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "toString", "", "okio"},
   k = 1,
   mv = {1, 1, 16}
)
final class InputStreamSource implements Source {
   private final InputStream input;
   private final Timeout timeout;

   public InputStreamSource(InputStream var1, Timeout var2) {
      Intrinsics.checkParameterIsNotNull(var1, "input");
      Intrinsics.checkParameterIsNotNull(var2, "timeout");
      super();
      this.input = var1;
      this.timeout = var2;
   }

   public void close() {
      this.input.close();
   }

   public long read(Buffer var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sink");
      long var15;
      int var4 = (var15 = var2 - 0L) == 0L ? 0 : (var15 < 0L ? -1 : 1);
      if (var4 == 0) {
         return 0L;
      } else {
         boolean var14;
         if (var4 >= 0) {
            var14 = true;
         } else {
            var14 = false;
         }

         if (var14) {
            AssertionError var10000;
            label49: {
               Segment var5;
               boolean var10001;
               try {
                  this.timeout.throwIfReached();
                  var5 = var1.writableSegment$okio(1);
                  var4 = (int)Math.min(var2, (long)(8192 - var5.limit));
                  var4 = this.input.read(var5.data, var5.limit, var4);
               } catch (AssertionError var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label49;
               }

               if (var4 == -1) {
                  try {
                     if (var5.pos == var5.limit) {
                        var1.head = var5.pop();
                        SegmentPool.recycle(var5);
                     }

                     return -1L;
                  } catch (AssertionError var8) {
                     var10000 = var8;
                     var10001 = false;
                  }
               } else {
                  label58: {
                     try {
                        var5.limit += var4;
                        var2 = var1.size();
                     } catch (AssertionError var10) {
                        var10000 = var10;
                        var10001 = false;
                        break label58;
                     }

                     long var6 = (long)var4;

                     try {
                        var1.setSize$okio(var2 + var6);
                        return var6;
                     } catch (AssertionError var9) {
                        var10000 = var9;
                        var10001 = false;
                     }
                  }
               }
            }

            AssertionError var13 = var10000;
            if (Okio.isAndroidGetsocknameError(var13)) {
               throw (Throwable)(new IOException((Throwable)var13));
            } else {
               throw (Throwable)var13;
            }
         } else {
            StringBuilder var12 = new StringBuilder();
            var12.append("byteCount < 0: ");
            var12.append(var2);
            throw (Throwable)(new IllegalArgumentException(var12.toString().toString()));
         }
      }
   }

   public Timeout timeout() {
      return this.timeout;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("source(");
      var1.append(this.input);
      var1.append(')');
      return var1.toString();
   }
}
