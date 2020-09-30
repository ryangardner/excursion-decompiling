package okhttp3.internal.http2;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okio.Sink;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 (2\u00020\u0001:\u0001(B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0014H\u0016J\b\u0010\u001c\u001a\u00020\u0014H\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0012\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\fH\u0016J\u0010\u0010$\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 H\u0016J\b\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006)"},
   d2 = {"Lokhttp3/internal/http2/Http2ExchangeCodec;", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "connection", "Lokhttp3/internal/connection/RealConnection;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "http2Connection", "Lokhttp3/internal/http2/Http2Connection;", "(Lokhttp3/OkHttpClient;Lokhttp3/internal/connection/RealConnection;Lokhttp3/internal/http/RealInterceptorChain;Lokhttp3/internal/http2/Http2Connection;)V", "canceled", "", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "protocol", "Lokhttp3/Protocol;", "stream", "Lokhttp3/internal/http2/Http2Stream;", "cancel", "", "createRequestBody", "Lokio/Sink;", "request", "Lokhttp3/Request;", "contentLength", "", "finishRequest", "flushRequest", "openResponseBodySource", "Lokio/Source;", "response", "Lokhttp3/Response;", "readResponseHeaders", "Lokhttp3/Response$Builder;", "expectContinue", "reportedContentLength", "trailers", "Lokhttp3/Headers;", "writeRequestHeaders", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Http2ExchangeCodec implements ExchangeCodec {
   private static final String CONNECTION = "connection";
   public static final Http2ExchangeCodec.Companion Companion = new Http2ExchangeCodec.Companion((DefaultConstructorMarker)null);
   private static final String ENCODING = "encoding";
   private static final String HOST = "host";
   private static final List<String> HTTP_2_SKIPPED_REQUEST_HEADERS = Util.immutableListOf("connection", "host", "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade", ":method", ":path", ":scheme", ":authority");
   private static final List<String> HTTP_2_SKIPPED_RESPONSE_HEADERS = Util.immutableListOf("connection", "host", "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade");
   private static final String KEEP_ALIVE = "keep-alive";
   private static final String PROXY_CONNECTION = "proxy-connection";
   private static final String TE = "te";
   private static final String TRANSFER_ENCODING = "transfer-encoding";
   private static final String UPGRADE = "upgrade";
   private volatile boolean canceled;
   private final RealInterceptorChain chain;
   private final RealConnection connection;
   private final Http2Connection http2Connection;
   private final Protocol protocol;
   private volatile Http2Stream stream;

   public Http2ExchangeCodec(OkHttpClient var1, RealConnection var2, RealInterceptorChain var3, Http2Connection var4) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "connection");
      Intrinsics.checkParameterIsNotNull(var3, "chain");
      Intrinsics.checkParameterIsNotNull(var4, "http2Connection");
      super();
      this.connection = var2;
      this.chain = var3;
      this.http2Connection = var4;
      Protocol var5;
      if (var1.protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
         var5 = Protocol.H2_PRIOR_KNOWLEDGE;
      } else {
         var5 = Protocol.HTTP_2;
      }

      this.protocol = var5;
   }

   public void cancel() {
      this.canceled = true;
      Http2Stream var1 = this.stream;
      if (var1 != null) {
         var1.closeLater(ErrorCode.CANCEL);
      }

   }

   public Sink createRequestBody(Request var1, long var2) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      Http2Stream var4 = this.stream;
      if (var4 == null) {
         Intrinsics.throwNpe();
      }

      return var4.getSink();
   }

   public void finishRequest() {
      Http2Stream var1 = this.stream;
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      var1.getSink().close();
   }

   public void flushRequest() {
      this.http2Connection.flush();
   }

   public RealConnection getConnection() {
      return this.connection;
   }

   public Source openResponseBodySource(Response var1) {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      Http2Stream var2 = this.stream;
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      return (Source)var2.getSource$okhttp();
   }

   public Response.Builder readResponseHeaders(boolean var1) {
      Http2Stream var2 = this.stream;
      if (var2 == null) {
         Intrinsics.throwNpe();
      }

      Headers var4 = var2.takeHeaders();
      Response.Builder var3 = Companion.readHttp2HeadersList(var4, this.protocol);
      Response.Builder var5 = var3;
      if (var1) {
         var5 = var3;
         if (var3.getCode$okhttp() == 100) {
            var5 = null;
         }
      }

      return var5;
   }

   public long reportedContentLength(Response var1) {
      Intrinsics.checkParameterIsNotNull(var1, "response");
      long var2;
      if (!HttpHeaders.promisesBody(var1)) {
         var2 = 0L;
      } else {
         var2 = Util.headersContentLength(var1);
      }

      return var2;
   }

   public Headers trailers() {
      Http2Stream var1 = this.stream;
      if (var1 == null) {
         Intrinsics.throwNpe();
      }

      return var1.trailers();
   }

   public void writeRequestHeaders(Request var1) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      if (this.stream == null) {
         boolean var2;
         if (var1.body() != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         List var3 = Companion.http2HeadersList(var1);
         this.stream = this.http2Connection.newStream(var3, var2);
         Http2Stream var4;
         if (this.canceled) {
            var4 = this.stream;
            if (var4 == null) {
               Intrinsics.throwNpe();
            }

            var4.closeLater(ErrorCode.CANCEL);
            throw (Throwable)(new IOException("Canceled"));
         } else {
            var4 = this.stream;
            if (var4 == null) {
               Intrinsics.throwNpe();
            }

            var4.readTimeout().timeout((long)this.chain.getReadTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
            var4 = this.stream;
            if (var4 == null) {
               Intrinsics.throwNpe();
            }

            var4.writeTimeout().timeout((long)this.chain.getWriteTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\b2\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0019"},
      d2 = {"Lokhttp3/internal/http2/Http2ExchangeCodec$Companion;", "", "()V", "CONNECTION", "", "ENCODING", "HOST", "HTTP_2_SKIPPED_REQUEST_HEADERS", "", "HTTP_2_SKIPPED_RESPONSE_HEADERS", "KEEP_ALIVE", "PROXY_CONNECTION", "TE", "TRANSFER_ENCODING", "UPGRADE", "http2HeadersList", "Lokhttp3/internal/http2/Header;", "request", "Lokhttp3/Request;", "readHttp2HeadersList", "Lokhttp3/Response$Builder;", "headerBlock", "Lokhttp3/Headers;", "protocol", "Lokhttp3/Protocol;", "okhttp"},
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

      public final List<Header> http2HeadersList(Request var1) {
         Intrinsics.checkParameterIsNotNull(var1, "request");
         Headers var2 = var1.headers();
         ArrayList var3 = new ArrayList(var2.size() + 4);
         var3.add(new Header(Header.TARGET_METHOD, var1.method()));
         var3.add(new Header(Header.TARGET_PATH, RequestLine.INSTANCE.requestPath(var1.url())));
         String var4 = var1.header("Host");
         if (var4 != null) {
            var3.add(new Header(Header.TARGET_AUTHORITY, var4));
         }

         var3.add(new Header(Header.TARGET_SCHEME, var1.url().scheme()));
         int var5 = 0;

         for(int var6 = var2.size(); var5 < var6; ++var5) {
            String var7 = var2.name(var5);
            Locale var8 = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(var8, "Locale.US");
            if (var7 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var7 = var7.toLowerCase(var8);
            Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.String).toLowerCase(locale)");
            if (!Http2ExchangeCodec.HTTP_2_SKIPPED_REQUEST_HEADERS.contains(var7) || Intrinsics.areEqual((Object)var7, (Object)"te") && Intrinsics.areEqual((Object)var2.value(var5), (Object)"trailers")) {
               var3.add(new Header(var7, var2.value(var5)));
            }
         }

         return (List)var3;
      }

      public final Response.Builder readHttp2HeadersList(Headers var1, Protocol var2) {
         Intrinsics.checkParameterIsNotNull(var1, "headerBlock");
         Intrinsics.checkParameterIsNotNull(var2, "protocol");
         StatusLine var3 = (StatusLine)null;
         Headers.Builder var4 = new Headers.Builder();
         int var5 = var1.size();

         StatusLine var11;
         for(int var6 = 0; var6 < var5; var3 = var11) {
            String var7 = var1.name(var6);
            String var8 = var1.value(var6);
            if (Intrinsics.areEqual((Object)var7, (Object)":status")) {
               StatusLine.Companion var10 = StatusLine.Companion;
               StringBuilder var9 = new StringBuilder();
               var9.append("HTTP/1.1 ");
               var9.append(var8);
               var11 = var10.parse(var9.toString());
            } else {
               var11 = var3;
               if (!Http2ExchangeCodec.HTTP_2_SKIPPED_RESPONSE_HEADERS.contains(var7)) {
                  var4.addLenient$okhttp(var7, var8);
                  var11 = var3;
               }
            }

            ++var6;
         }

         if (var3 != null) {
            return (new Response.Builder()).protocol(var2).code(var3.code).message(var3.message).headers(var4.build());
         } else {
            throw (Throwable)(new ProtocolException("Expected ':status' header not present"));
         }
      }
   }
}
