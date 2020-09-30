package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.http1.HeadersReader;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.Source;
import okio.Timeout;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u001c2\u00020\u0001:\u0003\u001c\u001d\u001eB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u0002J\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bR\u0013\u0010\u0007\u001a\u00020\b8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0018\u00010\u0010R\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"},
   d2 = {"Lokhttp3/MultipartReader;", "Ljava/io/Closeable;", "response", "Lokhttp3/ResponseBody;", "(Lokhttp3/ResponseBody;)V", "source", "Lokio/BufferedSource;", "boundary", "", "(Lokio/BufferedSource;Ljava/lang/String;)V", "()Ljava/lang/String;", "closed", "", "crlfDashDashBoundary", "Lokio/ByteString;", "currentPart", "Lokhttp3/MultipartReader$PartSource;", "dashDashBoundary", "noMoreParts", "partCount", "", "close", "", "currentPartBytesRemaining", "", "maxResult", "nextPart", "Lokhttp3/MultipartReader$Part;", "Companion", "Part", "PartSource", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class MultipartReader implements Closeable {
   public static final MultipartReader.Companion Companion = new MultipartReader.Companion((DefaultConstructorMarker)null);
   private static final Options afterBoundaryOptions;
   private final String boundary;
   private boolean closed;
   private final ByteString crlfDashDashBoundary;
   private MultipartReader.PartSource currentPart;
   private final ByteString dashDashBoundary;
   private boolean noMoreParts;
   private int partCount;
   private final BufferedSource source;

   static {
      afterBoundaryOptions = Options.Companion.of(ByteString.Companion.encodeUtf8("\r\n"), ByteString.Companion.encodeUtf8("--"), ByteString.Companion.encodeUtf8(" "), ByteString.Companion.encodeUtf8("\t"));
   }

   public MultipartReader(ResponseBody var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      BufferedSource var2 = var1.source();
      MediaType var3 = var1.contentType();
      if (var3 != null) {
         String var4 = var3.parameter("boundary");
         if (var4 != null) {
            this(var2, var4);
            return;
         }
      }

      throw (Throwable)(new ProtocolException("expected the Content-Type to have a boundary parameter"));
   }

   public MultipartReader(BufferedSource var1, String var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "source");
      Intrinsics.checkParameterIsNotNull(var2, "boundary");
      super();
      this.source = var1;
      this.boundary = var2;
      this.dashDashBoundary = (new Buffer()).writeUtf8("--").writeUtf8(this.boundary).readByteString();
      this.crlfDashDashBoundary = (new Buffer()).writeUtf8("\r\n--").writeUtf8(this.boundary).readByteString();
   }

   private final long currentPartBytesRemaining(long var1) {
      this.source.require((long)this.crlfDashDashBoundary.size());
      long var3 = this.source.getBuffer().indexOf(this.crlfDashDashBoundary);
      if (var3 == -1L) {
         var1 = Math.min(var1, this.source.getBuffer().size() - (long)this.crlfDashDashBoundary.size() + 1L);
      } else {
         var1 = Math.min(var1, var3);
      }

      return var1;
   }

   public final String boundary() {
      return this.boundary;
   }

   public void close() throws IOException {
      if (!this.closed) {
         this.closed = true;
         this.currentPart = (MultipartReader.PartSource)null;
         this.source.close();
      }
   }

   public final MultipartReader.Part nextPart() throws IOException {
      if (this.closed ^ true) {
         if (this.noMoreParts) {
            return null;
         } else {
            if (this.partCount == 0 && this.source.rangeEquals(0L, this.dashDashBoundary)) {
               this.source.skip((long)this.dashDashBoundary.size());
            } else {
               while(true) {
                  long var1 = this.currentPartBytesRemaining(8192L);
                  if (var1 == 0L) {
                     this.source.skip((long)this.crlfDashDashBoundary.size());
                     break;
                  }

                  this.source.skip(var1);
               }
            }

            boolean var3 = false;

            while(true) {
               int var4;
               do {
                  var4 = this.source.select(afterBoundaryOptions);
                  if (var4 == -1) {
                     throw (Throwable)(new ProtocolException("unexpected characters after boundary"));
                  }

                  if (var4 == 0) {
                     ++this.partCount;
                     Headers var5 = (new HeadersReader(this.source)).readHeaders();
                     MultipartReader.PartSource var6 = new MultipartReader.PartSource();
                     this.currentPart = var6;
                     return new MultipartReader.Part(var5, Okio.buffer((Source)var6));
                  }

                  if (var4 == 1) {
                     if (!var3) {
                        if (this.partCount != 0) {
                           this.noMoreParts = true;
                           return null;
                        }

                        throw (Throwable)(new ProtocolException("expected at least 1 part"));
                     }

                     throw (Throwable)(new ProtocolException("unexpected characters after boundary"));
                  }
               } while(var4 != 2 && var4 != 3);

               var3 = true;
            }
         }
      } else {
         throw (Throwable)(new IllegalStateException("closed".toString()));
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
      d2 = {"Lokhttp3/MultipartReader$Companion;", "", "()V", "afterBoundaryOptions", "Lokio/Options;", "getAfterBoundaryOptions", "()Lokio/Options;", "okhttp"},
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

      public final Options getAfterBoundaryOptions() {
         return MultipartReader.afterBoundaryOptions;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\t\u001a\u00020\nH\u0096\u0001R\u0013\u0010\u0004\u001a\u00020\u00058\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\b¨\u0006\u000b"},
      d2 = {"Lokhttp3/MultipartReader$Part;", "Ljava/io/Closeable;", "headers", "Lokhttp3/Headers;", "body", "Lokio/BufferedSource;", "(Lokhttp3/Headers;Lokio/BufferedSource;)V", "()Lokio/BufferedSource;", "()Lokhttp3/Headers;", "close", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Part implements Closeable {
      private final BufferedSource body;
      private final Headers headers;

      public Part(Headers var1, BufferedSource var2) {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         Intrinsics.checkParameterIsNotNull(var2, "body");
         super();
         this.headers = var1;
         this.body = var2;
      }

      public final BufferedSource body() {
         return this.body;
      }

      public void close() {
         this.body.close();
      }

      public final Headers headers() {
         return this.headers;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0016J\b\u0010\u0003\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"},
      d2 = {"Lokhttp3/MultipartReader$PartSource;", "Lokio/Source;", "(Lokhttp3/MultipartReader;)V", "timeout", "Lokio/Timeout;", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private final class PartSource implements Source {
      private final Timeout timeout = new Timeout();

      public PartSource() {
      }

      public void close() {
         if (Intrinsics.areEqual((Object)MultipartReader.this.currentPart, (Object)((MultipartReader.PartSource)this))) {
            MultipartReader.this.currentPart = (MultipartReader.PartSource)null;
         }

      }

      public long read(Buffer var1, long var2) {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         boolean var4;
         if (var2 >= 0L) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            if (!Intrinsics.areEqual((Object)MultipartReader.this.currentPart, (Object)((MultipartReader.PartSource)this))) {
               throw (Throwable)(new IllegalStateException("closed".toString()));
            } else {
               Timeout var5 = MultipartReader.this.source.timeout();
               Timeout var6 = this.timeout;
               long var7 = var5.timeoutNanos();
               var5.timeout(Timeout.Companion.minTimeout(var6.timeoutNanos(), var5.timeoutNanos()), TimeUnit.NANOSECONDS);
               Throwable var32;
               Throwable var10000;
               boolean var10001;
               if (var5.hasDeadline()) {
                  long var9 = var5.deadlineNanoTime();
                  if (var6.hasDeadline()) {
                     var5.deadlineNanoTime(Math.min(var5.deadlineNanoTime(), var6.deadlineNanoTime()));
                  }

                  label432: {
                     label433: {
                        try {
                           var2 = MultipartReader.this.currentPartBytesRemaining(var2);
                        } catch (Throwable var28) {
                           var10000 = var28;
                           var10001 = false;
                           break label433;
                        }

                        if (var2 == 0L) {
                           var2 = -1L;
                           break label432;
                        }

                        label401:
                        try {
                           var2 = MultipartReader.this.source.read(var1, var2);
                           break label432;
                        } catch (Throwable var27) {
                           var10000 = var27;
                           var10001 = false;
                           break label401;
                        }
                     }

                     var32 = var10000;
                     var5.timeout(var7, TimeUnit.NANOSECONDS);
                     if (var6.hasDeadline()) {
                        var5.deadlineNanoTime(var9);
                     }

                     throw var32;
                  }

                  var5.timeout(var7, TimeUnit.NANOSECONDS);
                  if (var6.hasDeadline()) {
                     var5.deadlineNanoTime(var9);
                  }

                  return var2;
               } else {
                  if (var6.hasDeadline()) {
                     var5.deadlineNanoTime(var6.deadlineNanoTime());
                  }

                  label434: {
                     label435: {
                        try {
                           var2 = MultipartReader.this.currentPartBytesRemaining(var2);
                        } catch (Throwable var30) {
                           var10000 = var30;
                           var10001 = false;
                           break label435;
                        }

                        if (var2 == 0L) {
                           var2 = -1L;
                           break label434;
                        }

                        label415:
                        try {
                           var2 = MultipartReader.this.source.read(var1, var2);
                           break label434;
                        } catch (Throwable var29) {
                           var10000 = var29;
                           var10001 = false;
                           break label415;
                        }
                     }

                     var32 = var10000;
                     var5.timeout(var7, TimeUnit.NANOSECONDS);
                     if (var6.hasDeadline()) {
                        var5.clearDeadline();
                     }

                     throw var32;
                  }

                  var5.timeout(var7, TimeUnit.NANOSECONDS);
                  if (var6.hasDeadline()) {
                     var5.clearDeadline();
                  }

                  return var2;
               }
            }
         } else {
            StringBuilder var31 = new StringBuilder();
            var31.append("byteCount < 0: ");
            var31.append(var2);
            throw (Throwable)(new IllegalArgumentException(var31.toString().toString()));
         }
      }

      public Timeout timeout() {
         return this.timeout;
      }
   }
}
