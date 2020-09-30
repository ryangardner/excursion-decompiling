package okhttp3.internal.connection;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.ws.RealWebSocket;
import okio.Buffer;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002ABB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ7\u0010\u001c\u001a\u0002H\u001d\"\n\b\u0000\u0010\u001d*\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u0002H\u001d¢\u0006\u0002\u0010$J\u0006\u0010%\u001a\u00020&J\u0016\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0016J\u0006\u0010,\u001a\u00020&J\u0006\u0010-\u001a\u00020&J\u0006\u0010.\u001a\u00020&J\u0006\u0010/\u001a\u000200J\u0006\u00101\u001a\u00020&J\u0006\u00102\u001a\u00020&J\u000e\u00103\u001a\u0002042\u0006\u00105\u001a\u000206J\u0010\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020\u0016J\u000e\u0010:\u001a\u00020&2\u0006\u00105\u001a\u000206J\u0006\u0010;\u001a\u00020&J\u0010\u0010<\u001a\u00020&2\u0006\u0010#\u001a\u00020\u001eH\u0002J\u0006\u0010=\u001a\u00020>J\u0006\u0010?\u001a\u00020&J\u000e\u0010@\u001a\u00020&2\u0006\u0010)\u001a\u00020*R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000eX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0006\u001a\u00020\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0016@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018¨\u0006C"},
   d2 = {"Lokhttp3/internal/connection/Exchange;", "", "call", "Lokhttp3/internal/connection/RealCall;", "eventListener", "Lokhttp3/EventListener;", "finder", "Lokhttp3/internal/connection/ExchangeFinder;", "codec", "Lokhttp3/internal/http/ExchangeCodec;", "(Lokhttp3/internal/connection/RealCall;Lokhttp3/EventListener;Lokhttp3/internal/connection/ExchangeFinder;Lokhttp3/internal/http/ExchangeCodec;)V", "getCall$okhttp", "()Lokhttp3/internal/connection/RealCall;", "connection", "Lokhttp3/internal/connection/RealConnection;", "getConnection$okhttp", "()Lokhttp3/internal/connection/RealConnection;", "getEventListener$okhttp", "()Lokhttp3/EventListener;", "getFinder$okhttp", "()Lokhttp3/internal/connection/ExchangeFinder;", "isCoalescedConnection", "", "isCoalescedConnection$okhttp", "()Z", "<set-?>", "isDuplex", "isDuplex$okhttp", "bodyComplete", "E", "Ljava/io/IOException;", "bytesRead", "", "responseDone", "requestDone", "e", "(JZZLjava/io/IOException;)Ljava/io/IOException;", "cancel", "", "createRequestBody", "Lokio/Sink;", "request", "Lokhttp3/Request;", "duplex", "detachWithViolence", "finishRequest", "flushRequest", "newWebSocketStreams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "noNewExchangesOnConnection", "noRequestBody", "openResponseBody", "Lokhttp3/ResponseBody;", "response", "Lokhttp3/Response;", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "responseHeadersEnd", "responseHeadersStart", "trackFailure", "trailers", "Lokhttp3/Headers;", "webSocketUpgradeFailed", "writeRequestHeaders", "RequestBodySink", "ResponseBodySource", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Exchange {
   private final RealCall call;
   private final ExchangeCodec codec;
   private final RealConnection connection;
   private final EventListener eventListener;
   private final ExchangeFinder finder;
   private boolean isDuplex;

   public Exchange(RealCall var1, EventListener var2, ExchangeFinder var3, ExchangeCodec var4) {
      Intrinsics.checkParameterIsNotNull(var1, "call");
      Intrinsics.checkParameterIsNotNull(var2, "eventListener");
      Intrinsics.checkParameterIsNotNull(var3, "finder");
      Intrinsics.checkParameterIsNotNull(var4, "codec");
      super();
      this.call = var1;
      this.eventListener = var2;
      this.finder = var3;
      this.codec = var4;
      this.connection = var4.getConnection();
   }

   private final void trackFailure(IOException var1) {
      this.finder.trackFailure(var1);
      this.codec.getConnection().trackFailure$okhttp(this.call, var1);
   }

   public final <E extends IOException> E bodyComplete(long var1, boolean var3, boolean var4, E var5) {
      if (var5 != null) {
         this.trackFailure(var5);
      }

      if (var4) {
         if (var5 != null) {
            this.eventListener.requestFailed((Call)this.call, var5);
         } else {
            this.eventListener.requestBodyEnd((Call)this.call, var1);
         }
      }

      if (var3) {
         if (var5 != null) {
            this.eventListener.responseFailed((Call)this.call, var5);
         } else {
            this.eventListener.responseBodyEnd((Call)this.call, var1);
         }
      }

      return this.call.messageDone$okhttp(this, var4, var3, var5);
   }

   public final void cancel() {
      this.codec.cancel();
   }

   public final Sink createRequestBody(Request var1, boolean var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      this.isDuplex = var2;
      RequestBody var3 = var1.body();
      if (var3 == null) {
         Intrinsics.throwNpe();
      }

      long var4 = var3.contentLength();
      this.eventListener.requestBodyStart((Call)this.call);
      return (Sink)(new Exchange.RequestBodySink(this.codec.createRequestBody(var1, var4), var4));
   }

   public final void detachWithViolence() {
      this.codec.cancel();
      this.call.messageDone$okhttp(this, true, true, (IOException)null);
   }

   public final void finishRequest() throws IOException {
      try {
         this.codec.finishRequest();
      } catch (IOException var2) {
         this.eventListener.requestFailed((Call)this.call, var2);
         this.trackFailure(var2);
         throw (Throwable)var2;
      }
   }

   public final void flushRequest() throws IOException {
      try {
         this.codec.flushRequest();
      } catch (IOException var2) {
         this.eventListener.requestFailed((Call)this.call, var2);
         this.trackFailure(var2);
         throw (Throwable)var2;
      }
   }

   public final RealCall getCall$okhttp() {
      return this.call;
   }

   public final RealConnection getConnection$okhttp() {
      return this.connection;
   }

   public final EventListener getEventListener$okhttp() {
      return this.eventListener;
   }

   public final ExchangeFinder getFinder$okhttp() {
      return this.finder;
   }

   public final boolean isCoalescedConnection$okhttp() {
      return Intrinsics.areEqual((Object)this.finder.getAddress$okhttp().url().host(), (Object)this.connection.route().address().url().host()) ^ true;
   }

   public final boolean isDuplex$okhttp() {
      return this.isDuplex;
   }

   public final RealWebSocket.Streams newWebSocketStreams() throws SocketException {
      this.call.timeoutEarlyExit();
      return this.codec.getConnection().newWebSocketStreams$okhttp(this);
   }

   public final void noNewExchangesOnConnection() {
      this.codec.getConnection().noNewExchanges$okhttp();
   }

   public final void noRequestBody() {
      this.call.messageDone$okhttp(this, true, false, (IOException)null);
   }

   public final ResponseBody openResponseBody(Response var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "response");

      try {
         String var2 = Response.header$default(var1, "Content-Type", (String)null, 2, (Object)null);
         long var3 = this.codec.reportedContentLength(var1);
         Source var5 = this.codec.openResponseBodySource(var1);
         Exchange.ResponseBodySource var8 = new Exchange.ResponseBodySource(var5, var3);
         RealResponseBody var7 = new RealResponseBody(var2, var3, Okio.buffer((Source)var8));
         ResponseBody var9 = (ResponseBody)var7;
         return var9;
      } catch (IOException var6) {
         this.eventListener.responseFailed((Call)this.call, var6);
         this.trackFailure(var6);
         throw (Throwable)var6;
      }
   }

   public final Response.Builder readResponseHeaders(boolean param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public final void responseHeadersEnd(Response var1) {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      this.eventListener.responseHeadersEnd((Call)this.call, var1);
   }

   public final void responseHeadersStart() {
      this.eventListener.responseHeadersStart((Call)this.call);
   }

   public final Headers trailers() throws IOException {
      return this.codec.trailers();
   }

   public final void webSocketUpgradeFailed() {
      this.bodyComplete(-1L, true, true, (IOException)null);
   }

   public final void writeRequestHeaders(Request var1) throws IOException {
      Intrinsics.checkParameterIsNotNull(var1, "request");

      try {
         this.eventListener.requestHeadersStart((Call)this.call);
         this.codec.writeRequestHeaders(var1);
         this.eventListener.requestHeadersEnd((Call)this.call, var1);
      } catch (IOException var2) {
         this.eventListener.requestFailed((Call)this.call, var2);
         this.trackFailure(var2);
         throw (Throwable)var2;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J!\u0010\r\u001a\u0002H\u000e\"\n\b\u0000\u0010\u000e*\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u0002H\u000eH\u0002¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\fH\u0016J\u0018\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0005H\u0016R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"},
      d2 = {"Lokhttp3/internal/connection/Exchange$RequestBodySink;", "Lokio/ForwardingSink;", "delegate", "Lokio/Sink;", "contentLength", "", "(Lokhttp3/internal/connection/Exchange;Lokio/Sink;J)V", "bytesReceived", "closed", "", "completed", "close", "", "complete", "E", "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "flush", "write", "source", "Lokio/Buffer;", "byteCount", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private final class RequestBodySink extends ForwardingSink {
      private long bytesReceived;
      private boolean closed;
      private boolean completed;
      private final long contentLength;

      public RequestBodySink(Sink var2, long var3) {
         Intrinsics.checkParameterIsNotNull(var2, "delegate");
         super(var2);
         this.contentLength = var3;
      }

      private final <E extends IOException> E complete(E var1) {
         if (this.completed) {
            return var1;
         } else {
            this.completed = true;
            return Exchange.this.bodyComplete(this.bytesReceived, false, true, var1);
         }
      }

      public void close() throws IOException {
         if (!this.closed) {
            this.closed = true;
            long var1 = this.contentLength;
            if (var1 != -1L && this.bytesReceived != var1) {
               throw (Throwable)(new ProtocolException("unexpected end of stream"));
            } else {
               try {
                  super.close();
                  this.complete((IOException)null);
               } catch (IOException var4) {
                  throw (Throwable)this.complete(var4);
               }
            }
         }
      }

      public void flush() throws IOException {
         try {
            super.flush();
         } catch (IOException var2) {
            throw (Throwable)this.complete(var2);
         }
      }

      public void write(Buffer var1, long var2) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         if (this.closed ^ true) {
            long var4 = this.contentLength;
            if (var4 != -1L && this.bytesReceived + var2 > var4) {
               StringBuilder var7 = new StringBuilder();
               var7.append("expected ");
               var7.append(this.contentLength);
               var7.append(" bytes but received ");
               var7.append(this.bytesReceived + var2);
               throw (Throwable)(new ProtocolException(var7.toString()));
            } else {
               try {
                  super.write(var1, var2);
                  this.bytesReceived += var2;
               } catch (IOException var6) {
                  throw (Throwable)this.complete(var6);
               }
            }
         } else {
            throw (Throwable)(new IllegalStateException("closed".toString()));
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\f\u001a\u00020\rH\u0016J\u001f\u0010\u000e\u001a\u0002H\u000f\"\n\b\u0000\u0010\u000f*\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u0002H\u000f¢\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0005H\u0016R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"},
      d2 = {"Lokhttp3/internal/connection/Exchange$ResponseBodySource;", "Lokio/ForwardingSource;", "delegate", "Lokio/Source;", "contentLength", "", "(Lokhttp3/internal/connection/Exchange;Lokio/Source;J)V", "bytesReceived", "closed", "", "completed", "invokeStartEvent", "close", "", "complete", "E", "Ljava/io/IOException;", "e", "(Ljava/io/IOException;)Ljava/io/IOException;", "read", "sink", "Lokio/Buffer;", "byteCount", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public final class ResponseBodySource extends ForwardingSource {
      private long bytesReceived;
      private boolean closed;
      private boolean completed;
      private final long contentLength;
      private boolean invokeStartEvent;

      public ResponseBodySource(Source var2, long var3) {
         Intrinsics.checkParameterIsNotNull(var2, "delegate");
         super(var2);
         this.contentLength = var3;
         this.invokeStartEvent = true;
         if (var3 == 0L) {
            this.complete((IOException)null);
         }

      }

      public void close() throws IOException {
         if (!this.closed) {
            this.closed = true;

            try {
               super.close();
               this.complete((IOException)null);
            } catch (IOException var2) {
               throw (Throwable)this.complete(var2);
            }
         }
      }

      public final <E extends IOException> E complete(E var1) {
         if (this.completed) {
            return var1;
         } else {
            this.completed = true;
            if (var1 == null && this.invokeStartEvent) {
               this.invokeStartEvent = false;
               Exchange.this.getEventListener$okhttp().responseBodyStart((Call)Exchange.this.getCall$okhttp());
            }

            return Exchange.this.bodyComplete(this.bytesReceived, true, false, var1);
         }
      }

      public long read(Buffer var1, long var2) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "sink");
         if (this.closed ^ true) {
            IOException var10000;
            label53: {
               boolean var10001;
               try {
                  var2 = this.delegate().read(var1, var2);
                  if (this.invokeStartEvent) {
                     this.invokeStartEvent = false;
                     Exchange.this.getEventListener$okhttp().responseBodyStart((Call)Exchange.this.getCall$okhttp());
                  }
               } catch (IOException var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label53;
               }

               if (var2 == -1L) {
                  try {
                     this.complete((IOException)null);
                     return -1L;
                  } catch (IOException var7) {
                     var10000 = var7;
                     var10001 = false;
                  }
               } else {
                  label49: {
                     long var4;
                     label57: {
                        try {
                           var4 = this.bytesReceived + var2;
                           if (this.contentLength != -1L && var4 > this.contentLength) {
                              break label57;
                           }
                        } catch (IOException var10) {
                           var10000 = var10;
                           var10001 = false;
                           break label49;
                        }

                        try {
                           this.bytesReceived = var4;
                           if (var4 == this.contentLength) {
                              this.complete((IOException)null);
                           }

                           return var2;
                        } catch (IOException var9) {
                           var10000 = var9;
                           var10001 = false;
                           break label49;
                        }
                     }

                     try {
                        StringBuilder var13 = new StringBuilder();
                        var13.append("expected ");
                        var13.append(this.contentLength);
                        var13.append(" bytes but received ");
                        var13.append(var4);
                        ProtocolException var6 = new ProtocolException(var13.toString());
                        throw (Throwable)var6;
                     } catch (IOException var8) {
                        var10000 = var8;
                        var10001 = false;
                     }
                  }
               }
            }

            IOException var12 = var10000;
            throw (Throwable)this.complete(var12);
         } else {
            throw (Throwable)(new IllegalStateException("closed".toString()));
         }
      }
   }
}
