package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import okio.Source;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001:\u0001FB{\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0000\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0000\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017¢\u0006\u0002\u0010\u0018J\u000f\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007¢\u0006\u0002\b+J\r\u0010\u001a\u001a\u00020\u001bH\u0007¢\u0006\u0002\b,J\u000f\u0010\u0011\u001a\u0004\u0018\u00010\u0000H\u0007¢\u0006\u0002\b-J\f\u0010.\u001a\b\u0012\u0004\u0012\u0002000/J\b\u00101\u001a\u000202H\u0016J\r\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\b3J\u000f\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0007¢\u0006\u0002\b4J\u001e\u00105\u001a\u0004\u0018\u00010\u00072\u0006\u00106\u001a\u00020\u00072\n\b\u0002\u00107\u001a\u0004\u0018\u00010\u0007H\u0007J\r\u0010\f\u001a\u00020\rH\u0007¢\u0006\u0002\b8J\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070/2\u0006\u00106\u001a\u00020\u0007J\r\u0010\u0006\u001a\u00020\u0007H\u0007¢\u0006\u0002\b9J\u000f\u0010\u0010\u001a\u0004\u0018\u00010\u0000H\u0007¢\u0006\u0002\b:J\u0006\u0010;\u001a\u00020<J\u000e\u0010=\u001a\u00020\u000f2\u0006\u0010>\u001a\u00020\u0014J\u000f\u0010\u0012\u001a\u0004\u0018\u00010\u0000H\u0007¢\u0006\u0002\b?J\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b@J\r\u0010\u0015\u001a\u00020\u0014H\u0007¢\u0006\u0002\bAJ\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\bBJ\r\u0010\u0013\u001a\u00020\u0014H\u0007¢\u0006\u0002\bCJ\b\u0010D\u001a\u00020\u0007H\u0016J\u0006\u0010E\u001a\u00020\rR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u001b8G¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001cR\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u00008\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u001dR\u0013\u0010\b\u001a\u00020\t8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u001eR\u0018\u0010\u0016\u001a\u0004\u0018\u00010\u00178\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u001fR\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010 R\u0013\u0010\f\u001a\u00020\r8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010!R\u0011\u0010\"\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\b\"\u0010$R\u0011\u0010%\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\b%\u0010$R\u0010\u0010&\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u0006\u001a\u00020\u00078\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010'R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u00008\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001dR\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u00008\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u001dR\u0013\u0010\u0004\u001a\u00020\u00058\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010(R\u0013\u0010\u0015\u001a\u00020\u00148\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010)R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010*R\u0013\u0010\u0013\u001a\u00020\u00148\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010)¨\u0006G"},
   d2 = {"Lokhttp3/Response;", "Ljava/io/Closeable;", "request", "Lokhttp3/Request;", "protocol", "Lokhttp3/Protocol;", "message", "", "code", "", "handshake", "Lokhttp3/Handshake;", "headers", "Lokhttp3/Headers;", "body", "Lokhttp3/ResponseBody;", "networkResponse", "cacheResponse", "priorResponse", "sentRequestAtMillis", "", "receivedResponseAtMillis", "exchange", "Lokhttp3/internal/connection/Exchange;", "(Lokhttp3/Request;Lokhttp3/Protocol;Ljava/lang/String;ILokhttp3/Handshake;Lokhttp3/Headers;Lokhttp3/ResponseBody;Lokhttp3/Response;Lokhttp3/Response;Lokhttp3/Response;JJLokhttp3/internal/connection/Exchange;)V", "()Lokhttp3/ResponseBody;", "cacheControl", "Lokhttp3/CacheControl;", "()Lokhttp3/CacheControl;", "()Lokhttp3/Response;", "()I", "()Lokhttp3/internal/connection/Exchange;", "()Lokhttp3/Handshake;", "()Lokhttp3/Headers;", "isRedirect", "", "()Z", "isSuccessful", "lazyCacheControl", "()Ljava/lang/String;", "()Lokhttp3/Protocol;", "()J", "()Lokhttp3/Request;", "-deprecated_body", "-deprecated_cacheControl", "-deprecated_cacheResponse", "challenges", "", "Lokhttp3/Challenge;", "close", "", "-deprecated_code", "-deprecated_handshake", "header", "name", "defaultValue", "-deprecated_headers", "-deprecated_message", "-deprecated_networkResponse", "newBuilder", "Lokhttp3/Response$Builder;", "peekBody", "byteCount", "-deprecated_priorResponse", "-deprecated_protocol", "-deprecated_receivedResponseAtMillis", "-deprecated_request", "-deprecated_sentRequestAtMillis", "toString", "trailers", "Builder", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Response implements Closeable {
   private final ResponseBody body;
   private final Response cacheResponse;
   private final int code;
   private final Exchange exchange;
   private final Handshake handshake;
   private final Headers headers;
   private CacheControl lazyCacheControl;
   private final String message;
   private final Response networkResponse;
   private final Response priorResponse;
   private final Protocol protocol;
   private final long receivedResponseAtMillis;
   private final Request request;
   private final long sentRequestAtMillis;

   public Response(Request var1, Protocol var2, String var3, int var4, Handshake var5, Headers var6, ResponseBody var7, Response var8, Response var9, Response var10, long var11, long var13, Exchange var15) {
      Intrinsics.checkParameterIsNotNull(var1, "request");
      Intrinsics.checkParameterIsNotNull(var2, "protocol");
      Intrinsics.checkParameterIsNotNull(var3, "message");
      Intrinsics.checkParameterIsNotNull(var6, "headers");
      super();
      this.request = var1;
      this.protocol = var2;
      this.message = var3;
      this.code = var4;
      this.handshake = var5;
      this.headers = var6;
      this.body = var7;
      this.networkResponse = var8;
      this.cacheResponse = var9;
      this.priorResponse = var10;
      this.sentRequestAtMillis = var11;
      this.receivedResponseAtMillis = var13;
      this.exchange = var15;
   }

   // $FF: synthetic method
   public static String header$default(Response var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = (String)null;
      }

      return var0.header(var1, var2);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "body",
   imports = {}
)
   )
   public final ResponseBody _deprecated_body/* $FF was: -deprecated_body*/() {
      return this.body;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "cacheControl",
   imports = {}
)
   )
   public final CacheControl _deprecated_cacheControl/* $FF was: -deprecated_cacheControl*/() {
      return this.cacheControl();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "cacheResponse",
   imports = {}
)
   )
   public final Response _deprecated_cacheResponse/* $FF was: -deprecated_cacheResponse*/() {
      return this.cacheResponse;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "code",
   imports = {}
)
   )
   public final int _deprecated_code/* $FF was: -deprecated_code*/() {
      return this.code;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "handshake",
   imports = {}
)
   )
   public final Handshake _deprecated_handshake/* $FF was: -deprecated_handshake*/() {
      return this.handshake;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "headers",
   imports = {}
)
   )
   public final Headers _deprecated_headers/* $FF was: -deprecated_headers*/() {
      return this.headers;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "message",
   imports = {}
)
   )
   public final String _deprecated_message/* $FF was: -deprecated_message*/() {
      return this.message;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "networkResponse",
   imports = {}
)
   )
   public final Response _deprecated_networkResponse/* $FF was: -deprecated_networkResponse*/() {
      return this.networkResponse;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "priorResponse",
   imports = {}
)
   )
   public final Response _deprecated_priorResponse/* $FF was: -deprecated_priorResponse*/() {
      return this.priorResponse;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "protocol",
   imports = {}
)
   )
   public final Protocol _deprecated_protocol/* $FF was: -deprecated_protocol*/() {
      return this.protocol;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "receivedResponseAtMillis",
   imports = {}
)
   )
   public final long _deprecated_receivedResponseAtMillis/* $FF was: -deprecated_receivedResponseAtMillis*/() {
      return this.receivedResponseAtMillis;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "request",
   imports = {}
)
   )
   public final Request _deprecated_request/* $FF was: -deprecated_request*/() {
      return this.request;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "sentRequestAtMillis",
   imports = {}
)
   )
   public final long _deprecated_sentRequestAtMillis/* $FF was: -deprecated_sentRequestAtMillis*/() {
      return this.sentRequestAtMillis;
   }

   public final ResponseBody body() {
      return this.body;
   }

   public final CacheControl cacheControl() {
      CacheControl var1 = this.lazyCacheControl;
      CacheControl var2 = var1;
      if (var1 == null) {
         var2 = CacheControl.Companion.parse(this.headers);
         this.lazyCacheControl = var2;
      }

      return var2;
   }

   public final Response cacheResponse() {
      return this.cacheResponse;
   }

   public final List<Challenge> challenges() {
      Headers var1 = this.headers;
      int var2 = this.code;
      String var3;
      if (var2 != 401) {
         if (var2 != 407) {
            return CollectionsKt.emptyList();
         }

         var3 = "Proxy-Authenticate";
      } else {
         var3 = "WWW-Authenticate";
      }

      return HttpHeaders.parseChallenges(var1, var3);
   }

   public void close() {
      ResponseBody var1 = this.body;
      if (var1 != null) {
         var1.close();
      } else {
         throw (Throwable)(new IllegalStateException("response is not eligible for a body and must not be closed".toString()));
      }
   }

   public final int code() {
      return this.code;
   }

   public final Exchange exchange() {
      return this.exchange;
   }

   public final Handshake handshake() {
      return this.handshake;
   }

   public final String header(String var1) {
      return header$default(this, var1, (String)null, 2, (Object)null);
   }

   public final String header(String var1, String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      var1 = this.headers.get(var1);
      if (var1 != null) {
         var2 = var1;
      }

      return var2;
   }

   public final List<String> headers(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      return this.headers.values(var1);
   }

   public final Headers headers() {
      return this.headers;
   }

   public final boolean isRedirect() {
      int var1 = this.code;
      boolean var2;
      if (var1 != 307 && var1 != 308) {
         switch(var1) {
         case 300:
         case 301:
         case 302:
         case 303:
            break;
         default:
            var2 = false;
            return var2;
         }
      }

      var2 = true;
      return var2;
   }

   public final boolean isSuccessful() {
      int var1 = this.code;
      boolean var2;
      if (200 <= var1 && 299 >= var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final String message() {
      return this.message;
   }

   public final Response networkResponse() {
      return this.networkResponse;
   }

   public final Response.Builder newBuilder() {
      return new Response.Builder(this);
   }

   public final ResponseBody peekBody(long var1) throws IOException {
      ResponseBody var3 = this.body;
      if (var3 == null) {
         Intrinsics.throwNpe();
      }

      BufferedSource var5 = var3.source().peek();
      Buffer var4 = new Buffer();
      var5.request(var1);
      var4.write((Source)var5, Math.min(var1, var5.getBuffer().size()));
      return ResponseBody.Companion.create((BufferedSource)var4, this.body.contentType(), var4.size());
   }

   public final Response priorResponse() {
      return this.priorResponse;
   }

   public final Protocol protocol() {
      return this.protocol;
   }

   public final long receivedResponseAtMillis() {
      return this.receivedResponseAtMillis;
   }

   public final Request request() {
      return this.request;
   }

   public final long sentRequestAtMillis() {
      return this.sentRequestAtMillis;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Response{protocol=");
      var1.append(this.protocol);
      var1.append(", code=");
      var1.append(this.code);
      var1.append(", message=");
      var1.append(this.message);
      var1.append(", url=");
      var1.append(this.request.url());
      var1.append('}');
      return var1.toString();
   }

   public final Headers trailers() throws IOException {
      Exchange var1 = this.exchange;
      if (var1 != null) {
         return var1.trailers();
      } else {
         throw (Throwable)(new IllegalStateException("trailers not available".toString()));
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u000f\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010I\u001a\u00020\u00002\u0006\u0010J\u001a\u00020)2\u0006\u0010K\u001a\u00020)H\u0016J\u0012\u0010\u0006\u001a\u00020\u00002\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\b\u0010L\u001a\u00020\u0004H\u0016J\u0012\u0010\f\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0004H\u0016J\u0012\u0010M\u001a\u00020N2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0002J\u001a\u0010O\u001a\u00020N2\u0006\u0010J\u001a\u00020)2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0002J\u0010\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u001c\u001a\u00020\u00002\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u0018\u0010P\u001a\u00020\u00002\u0006\u0010J\u001a\u00020)2\u0006\u0010K\u001a\u00020)H\u0016J\u0010\u0010\"\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020QH\u0016J\u0015\u0010R\u001a\u00020N2\u0006\u0010S\u001a\u00020\u0017H\u0000¢\u0006\u0002\bTJ\u0010\u0010(\u001a\u00020\u00002\u0006\u0010(\u001a\u00020)H\u0016J\u0012\u0010.\u001a\u00020\u00002\b\u0010.\u001a\u0004\u0018\u00010\u0004H\u0016J\u0012\u00101\u001a\u00020\u00002\b\u00101\u001a\u0004\u0018\u00010\u0004H\u0016J\u0010\u00104\u001a\u00020\u00002\u0006\u00104\u001a\u000205H\u0016J\u0010\u0010:\u001a\u00020\u00002\u0006\u0010:\u001a\u00020;H\u0016J\u0010\u0010U\u001a\u00020\u00002\u0006\u0010J\u001a\u00020)H\u0016J\u0010\u0010@\u001a\u00020\u00002\u0006\u0010@\u001a\u00020AH\u0016J\u0010\u0010F\u001a\u00020\u00002\u0006\u0010F\u001a\u00020;H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0005R\u001a\u0010\u0010\u001a\u00020\u0011X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020#X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001c\u0010(\u001a\u0004\u0018\u00010)X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001c\u0010.\u001a\u0004\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u000e\"\u0004\b0\u0010\u0005R\u001c\u00101\u001a\u0004\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u000e\"\u0004\b3\u0010\u0005R\u001c\u00104\u001a\u0004\u0018\u000105X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010:\u001a\u00020;X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001c\u0010@\u001a\u0004\u0018\u00010AX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u00020;X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010=\"\u0004\bH\u0010?¨\u0006V"},
      d2 = {"Lokhttp3/Response$Builder;", "", "()V", "response", "Lokhttp3/Response;", "(Lokhttp3/Response;)V", "body", "Lokhttp3/ResponseBody;", "getBody$okhttp", "()Lokhttp3/ResponseBody;", "setBody$okhttp", "(Lokhttp3/ResponseBody;)V", "cacheResponse", "getCacheResponse$okhttp", "()Lokhttp3/Response;", "setCacheResponse$okhttp", "code", "", "getCode$okhttp", "()I", "setCode$okhttp", "(I)V", "exchange", "Lokhttp3/internal/connection/Exchange;", "getExchange$okhttp", "()Lokhttp3/internal/connection/Exchange;", "setExchange$okhttp", "(Lokhttp3/internal/connection/Exchange;)V", "handshake", "Lokhttp3/Handshake;", "getHandshake$okhttp", "()Lokhttp3/Handshake;", "setHandshake$okhttp", "(Lokhttp3/Handshake;)V", "headers", "Lokhttp3/Headers$Builder;", "getHeaders$okhttp", "()Lokhttp3/Headers$Builder;", "setHeaders$okhttp", "(Lokhttp3/Headers$Builder;)V", "message", "", "getMessage$okhttp", "()Ljava/lang/String;", "setMessage$okhttp", "(Ljava/lang/String;)V", "networkResponse", "getNetworkResponse$okhttp", "setNetworkResponse$okhttp", "priorResponse", "getPriorResponse$okhttp", "setPriorResponse$okhttp", "protocol", "Lokhttp3/Protocol;", "getProtocol$okhttp", "()Lokhttp3/Protocol;", "setProtocol$okhttp", "(Lokhttp3/Protocol;)V", "receivedResponseAtMillis", "", "getReceivedResponseAtMillis$okhttp", "()J", "setReceivedResponseAtMillis$okhttp", "(J)V", "request", "Lokhttp3/Request;", "getRequest$okhttp", "()Lokhttp3/Request;", "setRequest$okhttp", "(Lokhttp3/Request;)V", "sentRequestAtMillis", "getSentRequestAtMillis$okhttp", "setSentRequestAtMillis$okhttp", "addHeader", "name", "value", "build", "checkPriorResponse", "", "checkSupportResponse", "header", "Lokhttp3/Headers;", "initExchange", "deferredTrailers", "initExchange$okhttp", "removeHeader", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static class Builder {
      private ResponseBody body;
      private Response cacheResponse;
      private int code;
      private Exchange exchange;
      private Handshake handshake;
      private Headers.Builder headers;
      private String message;
      private Response networkResponse;
      private Response priorResponse;
      private Protocol protocol;
      private long receivedResponseAtMillis;
      private Request request;
      private long sentRequestAtMillis;

      public Builder() {
         this.code = -1;
         this.headers = new Headers.Builder();
      }

      public Builder(Response var1) {
         Intrinsics.checkParameterIsNotNull(var1, "response");
         super();
         this.code = -1;
         this.request = var1.request();
         this.protocol = var1.protocol();
         this.code = var1.code();
         this.message = var1.message();
         this.handshake = var1.handshake();
         this.headers = var1.headers().newBuilder();
         this.body = var1.body();
         this.networkResponse = var1.networkResponse();
         this.cacheResponse = var1.cacheResponse();
         this.priorResponse = var1.priorResponse();
         this.sentRequestAtMillis = var1.sentRequestAtMillis();
         this.receivedResponseAtMillis = var1.receivedResponseAtMillis();
         this.exchange = var1.exchange();
      }

      private final void checkPriorResponse(Response var1) {
         if (var1 != null) {
            boolean var2;
            if (var1.body() == null) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (!var2) {
               throw (Throwable)(new IllegalArgumentException("priorResponse.body != null".toString()));
            }
         }

      }

      private final void checkSupportResponse(String var1, Response var2) {
         if (var2 != null) {
            ResponseBody var3 = var2.body();
            boolean var4 = true;
            boolean var5;
            if (var3 == null) {
               var5 = true;
            } else {
               var5 = false;
            }

            StringBuilder var6;
            if (!var5) {
               var6 = new StringBuilder();
               var6.append(var1);
               var6.append(".body != null");
               throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
            }

            if (var2.networkResponse() == null) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (!var5) {
               var6 = new StringBuilder();
               var6.append(var1);
               var6.append(".networkResponse != null");
               throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
            }

            if (var2.cacheResponse() == null) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (!var5) {
               var6 = new StringBuilder();
               var6.append(var1);
               var6.append(".cacheResponse != null");
               throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
            }

            if (var2.priorResponse() == null) {
               var5 = var4;
            } else {
               var5 = false;
            }

            if (!var5) {
               var6 = new StringBuilder();
               var6.append(var1);
               var6.append(".priorResponse != null");
               throw (Throwable)(new IllegalArgumentException(var6.toString().toString()));
            }
         }

      }

      public Response.Builder addHeader(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Response.Builder var3 = (Response.Builder)this;
         var3.headers.add(var1, var2);
         return var3;
      }

      public Response.Builder body(ResponseBody var1) {
         Response.Builder var2 = (Response.Builder)this;
         var2.body = var1;
         return var2;
      }

      public Response build() {
         boolean var1;
         if (this.code >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            Request var2 = this.request;
            if (var2 != null) {
               Protocol var5 = this.protocol;
               if (var5 != null) {
                  String var4 = this.message;
                  if (var4 != null) {
                     return new Response(var2, var5, var4, this.code, this.handshake, this.headers.build(), this.body, this.networkResponse, this.cacheResponse, this.priorResponse, this.sentRequestAtMillis, this.receivedResponseAtMillis, this.exchange);
                  } else {
                     throw (Throwable)(new IllegalStateException("message == null".toString()));
                  }
               } else {
                  throw (Throwable)(new IllegalStateException("protocol == null".toString()));
               }
            } else {
               throw (Throwable)(new IllegalStateException("request == null".toString()));
            }
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("code < 0: ");
            var3.append(this.code);
            throw (Throwable)(new IllegalStateException(var3.toString().toString()));
         }
      }

      public Response.Builder cacheResponse(Response var1) {
         Response.Builder var2 = (Response.Builder)this;
         var2.checkSupportResponse("cacheResponse", var1);
         var2.cacheResponse = var1;
         return var2;
      }

      public Response.Builder code(int var1) {
         Response.Builder var2 = (Response.Builder)this;
         var2.code = var1;
         return var2;
      }

      public final ResponseBody getBody$okhttp() {
         return this.body;
      }

      public final Response getCacheResponse$okhttp() {
         return this.cacheResponse;
      }

      public final int getCode$okhttp() {
         return this.code;
      }

      public final Exchange getExchange$okhttp() {
         return this.exchange;
      }

      public final Handshake getHandshake$okhttp() {
         return this.handshake;
      }

      public final Headers.Builder getHeaders$okhttp() {
         return this.headers;
      }

      public final String getMessage$okhttp() {
         return this.message;
      }

      public final Response getNetworkResponse$okhttp() {
         return this.networkResponse;
      }

      public final Response getPriorResponse$okhttp() {
         return this.priorResponse;
      }

      public final Protocol getProtocol$okhttp() {
         return this.protocol;
      }

      public final long getReceivedResponseAtMillis$okhttp() {
         return this.receivedResponseAtMillis;
      }

      public final Request getRequest$okhttp() {
         return this.request;
      }

      public final long getSentRequestAtMillis$okhttp() {
         return this.sentRequestAtMillis;
      }

      public Response.Builder handshake(Handshake var1) {
         Response.Builder var2 = (Response.Builder)this;
         var2.handshake = var1;
         return var2;
      }

      public Response.Builder header(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Intrinsics.checkParameterIsNotNull(var2, "value");
         Response.Builder var3 = (Response.Builder)this;
         var3.headers.set(var1, var2);
         return var3;
      }

      public Response.Builder headers(Headers var1) {
         Intrinsics.checkParameterIsNotNull(var1, "headers");
         Response.Builder var2 = (Response.Builder)this;
         var2.headers = var1.newBuilder();
         return var2;
      }

      public final void initExchange$okhttp(Exchange var1) {
         Intrinsics.checkParameterIsNotNull(var1, "deferredTrailers");
         this.exchange = var1;
      }

      public Response.Builder message(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "message");
         Response.Builder var2 = (Response.Builder)this;
         var2.message = var1;
         return var2;
      }

      public Response.Builder networkResponse(Response var1) {
         Response.Builder var2 = (Response.Builder)this;
         var2.checkSupportResponse("networkResponse", var1);
         var2.networkResponse = var1;
         return var2;
      }

      public Response.Builder priorResponse(Response var1) {
         Response.Builder var2 = (Response.Builder)this;
         var2.checkPriorResponse(var1);
         var2.priorResponse = var1;
         return var2;
      }

      public Response.Builder protocol(Protocol var1) {
         Intrinsics.checkParameterIsNotNull(var1, "protocol");
         Response.Builder var2 = (Response.Builder)this;
         var2.protocol = var1;
         return var2;
      }

      public Response.Builder receivedResponseAtMillis(long var1) {
         Response.Builder var3 = (Response.Builder)this;
         var3.receivedResponseAtMillis = var1;
         return var3;
      }

      public Response.Builder removeHeader(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "name");
         Response.Builder var2 = (Response.Builder)this;
         var2.headers.removeAll(var1);
         return var2;
      }

      public Response.Builder request(Request var1) {
         Intrinsics.checkParameterIsNotNull(var1, "request");
         Response.Builder var2 = (Response.Builder)this;
         var2.request = var1;
         return var2;
      }

      public Response.Builder sentRequestAtMillis(long var1) {
         Response.Builder var3 = (Response.Builder)this;
         var3.sentRequestAtMillis = var1;
         return var3;
      }

      public final void setBody$okhttp(ResponseBody var1) {
         this.body = var1;
      }

      public final void setCacheResponse$okhttp(Response var1) {
         this.cacheResponse = var1;
      }

      public final void setCode$okhttp(int var1) {
         this.code = var1;
      }

      public final void setExchange$okhttp(Exchange var1) {
         this.exchange = var1;
      }

      public final void setHandshake$okhttp(Handshake var1) {
         this.handshake = var1;
      }

      public final void setHeaders$okhttp(Headers.Builder var1) {
         Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
         this.headers = var1;
      }

      public final void setMessage$okhttp(String var1) {
         this.message = var1;
      }

      public final void setNetworkResponse$okhttp(Response var1) {
         this.networkResponse = var1;
      }

      public final void setPriorResponse$okhttp(Response var1) {
         this.priorResponse = var1;
      }

      public final void setProtocol$okhttp(Protocol var1) {
         this.protocol = var1;
      }

      public final void setReceivedResponseAtMillis$okhttp(long var1) {
         this.receivedResponseAtMillis = var1;
      }

      public final void setRequest$okhttp(Request var1) {
         this.request = var1;
      }

      public final void setSentRequestAtMillis$okhttp(long var1) {
         this.sentRequestAtMillis = var1;
      }
   }
}
