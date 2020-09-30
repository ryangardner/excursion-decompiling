package okhttp3.internal.http1;

import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Proxy.Type;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\t\u0018\u0000 ?2\u00020\u0001:\u0007<=>?@ABB'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\u001cH\u0016J\b\u0010&\u001a\u00020\u001cH\u0016J\b\u0010'\u001a\u00020\u001eH\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020)2\u0006\u0010-\u001a\u00020!H\u0002J\b\u0010.\u001a\u00020\u001eH\u0002J\b\u0010/\u001a\u00020)H\u0002J\u0010\u00100\u001a\u00020)2\u0006\u00101\u001a\u00020\u0019H\u0016J\u0012\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u00020\u0010H\u0016J\u0010\u00105\u001a\u00020!2\u0006\u00101\u001a\u00020\u0019H\u0016J\u000e\u00106\u001a\u00020\u001c2\u0006\u00101\u001a\u00020\u0019J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0016\u00107\u001a\u00020\u001c2\u0006\u00108\u001a\u00020\u00152\u0006\u00109\u001a\u00020:J\u0010\u0010;\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u0017H\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0016\u001a\u00020\u0010*\u00020\u00178BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0018\u0010\u0016\u001a\u00020\u0010*\u00020\u00198BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u001a¨\u0006C"},
   d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec;", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "connection", "Lokhttp3/internal/connection/RealConnection;", "source", "Lokio/BufferedSource;", "sink", "Lokio/BufferedSink;", "(Lokhttp3/OkHttpClient;Lokhttp3/internal/connection/RealConnection;Lokio/BufferedSource;Lokio/BufferedSink;)V", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "headersReader", "Lokhttp3/internal/http1/HeadersReader;", "isClosed", "", "()Z", "state", "", "trailers", "Lokhttp3/Headers;", "isChunked", "Lokhttp3/Request;", "(Lokhttp3/Request;)Z", "Lokhttp3/Response;", "(Lokhttp3/Response;)Z", "cancel", "", "createRequestBody", "Lokio/Sink;", "request", "contentLength", "", "detachTimeout", "timeout", "Lokio/ForwardingTimeout;", "finishRequest", "flushRequest", "newChunkedSink", "newChunkedSource", "Lokio/Source;", "url", "Lokhttp3/HttpUrl;", "newFixedLengthSource", "length", "newKnownLengthSink", "newUnknownLengthSource", "openResponseBodySource", "response", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "reportedContentLength", "skipConnectBody", "writeRequest", "headers", "requestLine", "", "writeRequestHeaders", "AbstractSource", "ChunkedSink", "ChunkedSource", "Companion", "FixedLengthSource", "KnownLengthSink", "UnknownLengthSource", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Http1ExchangeCodec implements ExchangeCodec {
   public static final Http1ExchangeCodec.Companion Companion = new Http1ExchangeCodec.Companion((DefaultConstructorMarker)null);
   private static final long NO_CHUNK_YET = -1L;
   private static final int STATE_CLOSED = 6;
   private static final int STATE_IDLE = 0;
   private static final int STATE_OPEN_REQUEST_BODY = 1;
   private static final int STATE_OPEN_RESPONSE_BODY = 4;
   private static final int STATE_READING_RESPONSE_BODY = 5;
   private static final int STATE_READ_RESPONSE_HEADERS = 3;
   private static final int STATE_WRITING_REQUEST_BODY = 2;
   private final OkHttpClient client;
   private final RealConnection connection;
   private final HeadersReader headersReader;
   private final BufferedSink sink;
   private final BufferedSource source;
   private int state;
   private Headers trailers;

   public Http1ExchangeCodec(OkHttpClient var1, RealConnection var2, BufferedSource var3, BufferedSink var4) {
      Intrinsics.checkParameterIsNotNull(var2, "connection");
      Intrinsics.checkParameterIsNotNull(var3, "source");
      Intrinsics.checkParameterIsNotNull(var4, "sink");
      super();
      this.client = var1;
      this.connection = var2;
      this.source = var3;
      this.sink = var4;
      this.headersReader = new HeadersReader(this.source);
   }

   private final void detachTimeout(ForwardingTimeout var1) {
      Timeout var2 = var1.delegate();
      var1.setDelegate(Timeout.NONE);
      var2.clearDeadline();
      var2.clearTimeout();
   }

   private final boolean isChunked(Request var1) {
      return StringsKt.equals("chunked", var1.header("Transfer-Encoding"), true);
   }

   private final boolean isChunked(Response var1) {
      return StringsKt.equals("chunked", Response.header$default(var1, "Transfer-Encoding", (String)null, 2, (Object)null), true);
   }

   private final Sink newChunkedSink() {
      int var1 = this.state;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      if (var2) {
         this.state = 2;
         return (Sink)(new Http1ExchangeCodec.ChunkedSink());
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("state: ");
         var3.append(this.state);
         throw (Throwable)(new IllegalStateException(var3.toString().toString()));
      }
   }

   private final Source newChunkedSource(HttpUrl var1) {
      boolean var2;
      if (this.state == 4) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         this.state = 5;
         return (Source)(new Http1ExchangeCodec.ChunkedSource(var1));
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("state: ");
         var3.append(this.state);
         throw (Throwable)(new IllegalStateException(var3.toString().toString()));
      }
   }

   private final Source newFixedLengthSource(long var1) {
      boolean var3;
      if (this.state == 4) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         this.state = 5;
         return (Source)(new Http1ExchangeCodec.FixedLengthSource(var1));
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("state: ");
         var4.append(this.state);
         throw (Throwable)(new IllegalStateException(var4.toString().toString()));
      }
   }

   private final Sink newKnownLengthSink() {
      int var1 = this.state;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      if (var2) {
         this.state = 2;
         return (Sink)(new Http1ExchangeCodec.KnownLengthSink());
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("state: ");
         var3.append(this.state);
         throw (Throwable)(new IllegalStateException(var3.toString().toString()));
      }
   }

   private final Source newUnknownLengthSource() {
      boolean var1;
      if (this.state == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         this.state = 5;
         this.getConnection().noNewExchanges$okhttp();
         return (Source)(new Http1ExchangeCodec.UnknownLengthSource());
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("state: ");
         var2.append(this.state);
         throw (Throwable)(new IllegalStateException(var2.toString().toString()));
      }
   }

   public void cancel() {
      this.getConnection().cancel();
   }

   public Sink createRequestBody(Request var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      if (var1.body() != null && var1.body().isDuplex()) {
         throw (Throwable)(new ProtocolException("Duplex connections are not supported for HTTP/1"));
      } else {
         Sink var4;
         if (this.isChunked(var1)) {
            var4 = this.newChunkedSink();
         } else {
            if (var2 == -1L) {
               throw (Throwable)(new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!"));
            }

            var4 = this.newKnownLengthSink();
         }

         return var4;
      }
   }

   public void finishRequest() {
      this.sink.flush();
   }

   public void flushRequest() {
      this.sink.flush();
   }

   public RealConnection getConnection() {
      return this.connection;
   }

   public final boolean isClosed() {
      boolean var1;
      if (this.state == 6) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Source openResponseBodySource(Response var1) {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      Source var4;
      if (!HttpHeaders.promisesBody(var1)) {
         var4 = this.newFixedLengthSource(0L);
      } else if (this.isChunked(var1)) {
         var4 = this.newChunkedSource(var1.request().url());
      } else {
         long var2 = Util.headersContentLength(var1);
         if (var2 != -1L) {
            var4 = this.newFixedLengthSource(var2);
         } else {
            var4 = this.newUnknownLengthSource();
         }
      }

      return var4;
   }

   public Response.Builder readResponseHeaders(boolean var1) {
      int var2 = this.state;
      boolean var3 = true;
      boolean var4 = var3;
      if (var2 != 1) {
         if (var2 == 3) {
            var4 = var3;
         } else {
            var4 = false;
         }
      }

      if (var4) {
         EOFException var10000;
         label60: {
            StatusLine var5;
            Response.Builder var13;
            boolean var10001;
            try {
               var5 = StatusLine.Companion.parse(this.headersReader.readLine());
               var13 = new Response.Builder();
               var13 = var13.protocol(var5.protocol).code(var5.code).message(var5.message).headers(this.headersReader.readHeaders());
            } catch (EOFException var9) {
               var10000 = var9;
               var10001 = false;
               break label60;
            }

            if (var1) {
               label70: {
                  try {
                     if (var5.code != 100) {
                        break label70;
                     }
                  } catch (EOFException var11) {
                     var10000 = var11;
                     var10001 = false;
                     break label60;
                  }

                  var13 = null;
                  return var13;
               }
            }

            try {
               if (var5.code == 100) {
                  this.state = 3;
                  return var13;
               }
            } catch (EOFException var10) {
               var10000 = var10;
               var10001 = false;
               break label60;
            }

            try {
               this.state = 4;
               return var13;
            } catch (EOFException var8) {
               var10000 = var8;
               var10001 = false;
            }
         }

         EOFException var7 = var10000;
         String var14 = this.getConnection().route().address().url().redact();
         StringBuilder var12 = new StringBuilder();
         var12.append("unexpected end of stream on ");
         var12.append(var14);
         throw (Throwable)(new IOException(var12.toString(), (Throwable)var7));
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("state: ");
         var6.append(this.state);
         throw (Throwable)(new IllegalStateException(var6.toString().toString()));
      }
   }

   public long reportedContentLength(Response var1) {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      long var2;
      if (!HttpHeaders.promisesBody(var1)) {
         var2 = 0L;
      } else if (this.isChunked(var1)) {
         var2 = -1L;
      } else {
         var2 = Util.headersContentLength(var1);
      }

      return var2;
   }

   public final void skipConnectBody(Response var1) {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      long var2 = Util.headersContentLength(var1);
      if (var2 != -1L) {
         Source var4 = this.newFixedLengthSource(var2);
         Util.skipAll(var4, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
         var4.close();
      }
   }

   public Headers trailers() {
      boolean var1;
      if (this.state == 6) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         Headers var2 = this.trailers;
         if (var2 == null) {
            var2 = Util.EMPTY_HEADERS;
         }

         return var2;
      } else {
         throw (Throwable)(new IllegalStateException("too early; can't read the trailers yet".toString()));
      }
   }

   public final void writeRequest(Headers var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "headers");
      Intrinsics.checkParameterIsNotNull(var2, "requestLine");
      int var3 = this.state;
      byte var4 = 0;
      boolean var7;
      if (var3 == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (!var7) {
         StringBuilder var6 = new StringBuilder();
         var6.append("state: ");
         var6.append(this.state);
         throw (Throwable)(new IllegalStateException(var6.toString().toString()));
      } else {
         this.sink.writeUtf8(var2).writeUtf8("\r\n");
         int var5 = var1.size();

         for(var3 = var4; var3 < var5; ++var3) {
            this.sink.writeUtf8(var1.name(var3)).writeUtf8(": ").writeUtf8(var1.value(var3)).writeUtf8("\r\n");
         }

         this.sink.writeUtf8("\r\n");
         this.state = 1;
      }
   }

   public void writeRequestHeaders(Request var1) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      RequestLine var2 = RequestLine.INSTANCE;
      Type var3 = this.getConnection().route().proxy().type();
      Intrinsics.checkExpressionValueIsNotNull(var3, "connection.route().proxy.type()");
      String var4 = var2.get(var1, var3);
      this.writeRequest(var1.headers(), var4);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b¢\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000eH\u0016J\r\u0010\u0012\u001a\u00020\u0013H\u0000¢\u0006\u0002\b\u0014J\b\u0010\t\u001a\u00020\u0015H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016"},
      d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokio/Source;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "closed", "", "getClosed", "()Z", "setClosed", "(Z)V", "timeout", "Lokio/ForwardingTimeout;", "getTimeout", "()Lokio/ForwardingTimeout;", "read", "", "sink", "Lokio/Buffer;", "byteCount", "responseBodyComplete", "", "responseBodyComplete$okhttp", "Lokio/Timeout;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private abstract class AbstractSource implements Source {
      private boolean closed;
      private final ForwardingTimeout timeout;

      public AbstractSource() {
         this.timeout = new ForwardingTimeout(Http1ExchangeCodec.this.source.timeout());
      }

      protected final boolean getClosed() {
         return this.closed;
      }

      protected final ForwardingTimeout getTimeout() {
         return this.timeout;
      }

      public long read(Buffer var1, long var2) {
         Intrinsics.checkParameterIsNotNull(var1, "sink");

         try {
            var2 = Http1ExchangeCodec.this.source.read(var1, var2);
            return var2;
         } catch (IOException var4) {
            Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
            this.responseBodyComplete$okhttp();
            throw (Throwable)var4;
         }
      }

      public final void responseBodyComplete$okhttp() {
         if (Http1ExchangeCodec.this.state != 6) {
            if (Http1ExchangeCodec.this.state == 5) {
               Http1ExchangeCodec.this.detachTimeout(this.timeout);
               Http1ExchangeCodec.this.state = 6;
            } else {
               StringBuilder var1 = new StringBuilder();
               var1.append("state: ");
               var1.append(Http1ExchangeCodec.this.state);
               throw (Throwable)(new IllegalStateException(var1.toString()));
            }
         }
      }

      protected final void setClosed(boolean var1) {
         this.closed = var1;
      }

      public Timeout timeout() {
         return (Timeout)this.timeout;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\b\u0010\u0005\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
      d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$ChunkedSink;", "Lokio/Sink;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "closed", "", "timeout", "Lokio/ForwardingTimeout;", "close", "", "flush", "Lokio/Timeout;", "write", "source", "Lokio/Buffer;", "byteCount", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private final class ChunkedSink implements Sink {
      private boolean closed;
      private final ForwardingTimeout timeout;

      public ChunkedSink() {
         this.timeout = new ForwardingTimeout(Http1ExchangeCodec.this.sink.timeout());
      }

      public void close() {
         synchronized(this){}

         Throwable var10000;
         label78: {
            boolean var1;
            boolean var10001;
            try {
               var1 = this.closed;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label78;
            }

            if (var1) {
               return;
            }

            try {
               this.closed = true;
               Http1ExchangeCodec.this.sink.writeUtf8("0\r\n\r\n");
               Http1ExchangeCodec.this.detachTimeout(this.timeout);
               Http1ExchangeCodec.this.state = 3;
            } catch (Throwable var7) {
               var10000 = var7;
               var10001 = false;
               break label78;
            }

            return;
         }

         Throwable var2 = var10000;
         throw var2;
      }

      public void flush() {
         synchronized(this){}

         Throwable var10000;
         label78: {
            boolean var1;
            boolean var10001;
            try {
               var1 = this.closed;
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label78;
            }

            if (var1) {
               return;
            }

            try {
               Http1ExchangeCodec.this.sink.flush();
            } catch (Throwable var7) {
               var10000 = var7;
               var10001 = false;
               break label78;
            }

            return;
         }

         Throwable var2 = var10000;
         throw var2;
      }

      public Timeout timeout() {
         return (Timeout)this.timeout;
      }

      public void write(Buffer var1, long var2) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         if (this.closed ^ true) {
            if (var2 != 0L) {
               Http1ExchangeCodec.this.sink.writeHexadecimalUnsignedLong(var2);
               Http1ExchangeCodec.this.sink.writeUtf8("\r\n");
               Http1ExchangeCodec.this.sink.write(var1, var2);
               Http1ExchangeCodec.this.sink.writeUtf8("\r\n");
            }
         } else {
            throw (Throwable)(new IllegalStateException("closed".toString()));
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H\u0016J\b\u0010\u0010\u001a\u00020\u000bH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
      d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$ChunkedSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "url", "Lokhttp3/HttpUrl;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;Lokhttp3/HttpUrl;)V", "bytesRemainingInChunk", "", "hasMoreChunks", "", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "readChunkSize", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private final class ChunkedSource extends Http1ExchangeCodec.AbstractSource {
      private long bytesRemainingInChunk;
      private boolean hasMoreChunks;
      private final HttpUrl url;

      public ChunkedSource(HttpUrl var2) {
         Intrinsics.checkParameterIsNotNull(var2, "url");
         super();
         this.url = var2;
         this.bytesRemainingInChunk = -1L;
         this.hasMoreChunks = true;
      }

      private final void readChunkSize() {
         if (this.bytesRemainingInChunk != -1L) {
            Http1ExchangeCodec.this.source.readUtf8LineStrict();
         }

         label70: {
            NumberFormatException var10000;
            label75: {
               String var1;
               boolean var10001;
               try {
                  this.bytesRemainingInChunk = Http1ExchangeCodec.this.source.readHexadecimalUnsignedLong();
                  var1 = Http1ExchangeCodec.this.source.readUtf8LineStrict();
               } catch (NumberFormatException var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label75;
               }

               if (var1 == null) {
                  try {
                     TypeCastException var15 = new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                     throw var15;
                  } catch (NumberFormatException var7) {
                     var10000 = var7;
                     var10001 = false;
                  }
               } else {
                  label78: {
                     label76: {
                        boolean var2;
                        label66: {
                           label65: {
                              try {
                                 var1 = StringsKt.trim((CharSequence)var1).toString();
                                 if (this.bytesRemainingInChunk < 0L) {
                                    break label76;
                                 }

                                 if (((CharSequence)var1).length() > 0) {
                                    break label65;
                                 }
                              } catch (NumberFormatException var10) {
                                 var10000 = var10;
                                 var10001 = false;
                                 break label78;
                              }

                              var2 = false;
                              break label66;
                           }

                           var2 = true;
                        }

                        if (!var2) {
                           break label70;
                        }

                        boolean var3;
                        try {
                           var3 = StringsKt.startsWith$default(var1, ";", false, 2, (Object)null);
                        } catch (NumberFormatException var8) {
                           var10000 = var8;
                           var10001 = false;
                           break label78;
                        }

                        if (var3) {
                           break label70;
                        }
                     }

                     try {
                        StringBuilder var17 = new StringBuilder();
                        var17.append("expected chunk size and optional extensions");
                        var17.append(" but was \"");
                        var17.append(this.bytesRemainingInChunk);
                        var17.append(var1);
                        var17.append('"');
                        ProtocolException var16 = new ProtocolException(var17.toString());
                        throw (Throwable)var16;
                     } catch (NumberFormatException var6) {
                        var10000 = var6;
                        var10001 = false;
                     }
                  }
               }
            }

            NumberFormatException var14 = var10000;
            throw (Throwable)(new ProtocolException(var14.getMessage()));
         }

         if (this.bytesRemainingInChunk == 0L) {
            this.hasMoreChunks = false;
            Http1ExchangeCodec var11 = Http1ExchangeCodec.this;
            var11.trailers = var11.headersReader.readHeaders();
            OkHttpClient var12 = Http1ExchangeCodec.this.client;
            if (var12 == null) {
               Intrinsics.throwNpe();
            }

            CookieJar var4 = var12.cookieJar();
            HttpUrl var5 = this.url;
            Headers var13 = Http1ExchangeCodec.this.trailers;
            if (var13 == null) {
               Intrinsics.throwNpe();
            }

            HttpHeaders.receiveHeaders(var4, var5, var13);
            this.responseBodyComplete$okhttp();
         }

      }

      public void close() {
         if (!this.getClosed()) {
            if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
               Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
               this.responseBodyComplete$okhttp();
            }

            this.setClosed(true);
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

         if (!var4) {
            StringBuilder var8 = new StringBuilder();
            var8.append("byteCount < 0: ");
            var8.append(var2);
            throw (Throwable)(new IllegalArgumentException(var8.toString().toString()));
         } else if (true ^ this.getClosed()) {
            if (!this.hasMoreChunks) {
               return -1L;
            } else {
               long var5 = this.bytesRemainingInChunk;
               if (var5 == 0L || var5 == -1L) {
                  this.readChunkSize();
                  if (!this.hasMoreChunks) {
                     return -1L;
                  }
               }

               var2 = super.read(var1, Math.min(var2, this.bytesRemainingInChunk));
               if (var2 != -1L) {
                  this.bytesRemainingInChunk -= var2;
                  return var2;
               } else {
                  Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
                  ProtocolException var7 = new ProtocolException("unexpected end of stream");
                  this.responseBodyComplete$okhttp();
                  throw (Throwable)var7;
               }
            }
         } else {
            throw (Throwable)(new IllegalStateException("closed".toString()));
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"},
      d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$Companion;", "", "()V", "NO_CHUNK_YET", "", "STATE_CLOSED", "", "STATE_IDLE", "STATE_OPEN_REQUEST_BODY", "STATE_OPEN_RESPONSE_BODY", "STATE_READING_RESPONSE_BODY", "STATE_READ_RESPONSE_HEADERS", "STATE_WRITING_REQUEST_BODY", "okhttp"},
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
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"},
      d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$FixedLengthSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "bytesRemaining", "", "(Lokhttp3/internal/http1/Http1ExchangeCodec;J)V", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private final class FixedLengthSource extends Http1ExchangeCodec.AbstractSource {
      private long bytesRemaining;

      public FixedLengthSource(long var2) {
         super();
         this.bytesRemaining = var2;
         if (var2 == 0L) {
            this.responseBodyComplete$okhttp();
         }

      }

      public void close() {
         if (!this.getClosed()) {
            if (this.bytesRemaining != 0L && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
               Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
               this.responseBodyComplete$okhttp();
            }

            this.setClosed(true);
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
            if (true ^ this.getClosed()) {
               long var5 = this.bytesRemaining;
               if (var5 == 0L) {
                  return -1L;
               } else {
                  var2 = super.read(var1, Math.min(var5, var2));
                  if (var2 != -1L) {
                     var5 = this.bytesRemaining - var2;
                     this.bytesRemaining = var5;
                     if (var5 == 0L) {
                        this.responseBodyComplete$okhttp();
                     }

                     return var2;
                  } else {
                     Http1ExchangeCodec.this.getConnection().noNewExchanges$okhttp();
                     ProtocolException var8 = new ProtocolException("unexpected end of stream");
                     this.responseBodyComplete$okhttp();
                     throw (Throwable)var8;
                  }
               }
            } else {
               throw (Throwable)(new IllegalStateException("closed".toString()));
            }
         } else {
            StringBuilder var7 = new StringBuilder();
            var7.append("byteCount < 0: ");
            var7.append(var2);
            throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\b\u0010\u0005\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
      d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$KnownLengthSink;", "Lokio/Sink;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "closed", "", "timeout", "Lokio/ForwardingTimeout;", "close", "", "flush", "Lokio/Timeout;", "write", "source", "Lokio/Buffer;", "byteCount", "", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private final class KnownLengthSink implements Sink {
      private boolean closed;
      private final ForwardingTimeout timeout;

      public KnownLengthSink() {
         this.timeout = new ForwardingTimeout(Http1ExchangeCodec.this.sink.timeout());
      }

      public void close() {
         if (!this.closed) {
            this.closed = true;
            Http1ExchangeCodec.this.detachTimeout(this.timeout);
            Http1ExchangeCodec.this.state = 3;
         }
      }

      public void flush() {
         if (!this.closed) {
            Http1ExchangeCodec.this.sink.flush();
         }
      }

      public Timeout timeout() {
         return (Timeout)this.timeout;
      }

      public void write(Buffer var1, long var2) {
         Intrinsics.checkParameterIsNotNull(var1, "source");
         if (this.closed ^ true) {
            Util.checkOffsetAndCount(var1.size(), 0L, var2);
            Http1ExchangeCodec.this.sink.write(var1, var2);
         } else {
            throw (Throwable)(new IllegalStateException("closed".toString()));
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"},
      d2 = {"Lokhttp3/internal/http1/Http1ExchangeCodec$UnknownLengthSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec$AbstractSource;", "Lokhttp3/internal/http1/Http1ExchangeCodec;", "(Lokhttp3/internal/http1/Http1ExchangeCodec;)V", "inputExhausted", "", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private final class UnknownLengthSource extends Http1ExchangeCodec.AbstractSource {
      private boolean inputExhausted;

      public UnknownLengthSource() {
         super();
      }

      public void close() {
         if (!this.getClosed()) {
            if (!this.inputExhausted) {
               this.responseBodyComplete$okhttp();
            }

            this.setClosed(true);
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
            if (this.getClosed() ^ true) {
               if (this.inputExhausted) {
                  return -1L;
               } else {
                  var2 = super.read(var1, var2);
                  if (var2 == -1L) {
                     this.inputExhausted = true;
                     this.responseBodyComplete$okhttp();
                     return -1L;
                  } else {
                     return var2;
                  }
               }
            } else {
               throw (Throwable)(new IllegalStateException("closed".toString()));
            }
         } else {
            StringBuilder var5 = new StringBuilder();
            var5.append("byteCount < 0: ");
            var5.append(var2);
            throw (Throwable)(new IllegalArgumentException(var5.toString().toString()));
         }
      }
   }
}
