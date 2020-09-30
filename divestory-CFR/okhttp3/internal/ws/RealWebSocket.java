/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.ws.RealWebSocket$connect
 *  okhttp3.internal.ws.RealWebSocket$initReaderAndWriter$
 *  okhttp3.internal.ws.RealWebSocket$initReaderAndWriter$$inlined
 *  okhttp3.internal.ws.RealWebSocket$initReaderAndWriter$$inlined$synchronized
 *  okhttp3.internal.ws.RealWebSocket$initReaderAndWriter$$inlined$synchronized$lambda
 *  okhttp3.internal.ws.RealWebSocket$writeOneFrame$
 *  okhttp3.internal.ws.RealWebSocket$writeOneFrame$$inlined
 *  okhttp3.internal.ws.RealWebSocket$writeOneFrame$$inlined$synchronized
 *  okhttp3.internal.ws.RealWebSocket$writeOneFrame$$inlined$synchronized$lambda
 */
package okhttp3.internal.ws;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.ws.RealWebSocket;
import okhttp3.internal.ws.RealWebSocket$initReaderAndWriter$;
import okhttp3.internal.ws.RealWebSocket$writeOneFrame$;
import okhttp3.internal.ws.WebSocketExtensions;
import okhttp3.internal.ws.WebSocketProtocol;
import okhttp3.internal.ws.WebSocketReader;
import okhttp3.internal.ws.WebSocketWriter;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u00b6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001c\u0018\u0000 `2\u00020\u00012\u00020\u0002:\u0005_`abcB?\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\u0006\u0010\u000f\u001a\u00020\f\u00a2\u0006\u0002\u0010\u0010J\u0016\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\f2\u0006\u00105\u001a\u000206J\b\u00107\u001a\u000203H\u0016J\u001f\u00108\u001a\u0002032\u0006\u00109\u001a\u00020:2\b\u0010;\u001a\u0004\u0018\u00010<H\u0000\u00a2\u0006\u0002\b=J\u001a\u0010>\u001a\u00020\u00122\u0006\u0010?\u001a\u00020%2\b\u0010@\u001a\u0004\u0018\u00010\u0018H\u0016J \u0010>\u001a\u00020\u00122\u0006\u0010?\u001a\u00020%2\b\u0010@\u001a\u0004\u0018\u00010\u00182\u0006\u0010A\u001a\u00020\fJ\u000e\u0010B\u001a\u0002032\u0006\u0010C\u001a\u00020DJ\u001c\u0010E\u001a\u0002032\n\u0010F\u001a\u00060Gj\u0002`H2\b\u00109\u001a\u0004\u0018\u00010:J\u0016\u0010I\u001a\u0002032\u0006\u0010\u001e\u001a\u00020\u00182\u0006\u0010*\u001a\u00020+J\u0006\u0010J\u001a\u000203J\u0018\u0010K\u001a\u0002032\u0006\u0010?\u001a\u00020%2\u0006\u0010@\u001a\u00020\u0018H\u0016J\u0010\u0010L\u001a\u0002032\u0006\u0010M\u001a\u00020\u0018H\u0016J\u0010\u0010L\u001a\u0002032\u0006\u0010N\u001a\u00020 H\u0016J\u0010\u0010O\u001a\u0002032\u0006\u0010P\u001a\u00020 H\u0016J\u0010\u0010Q\u001a\u0002032\u0006\u0010P\u001a\u00020 H\u0016J\u000e\u0010R\u001a\u00020\u00122\u0006\u0010P\u001a\u00020 J\u0006\u0010S\u001a\u00020\u0012J\b\u0010!\u001a\u00020\fH\u0016J\u0006\u0010'\u001a\u00020%J\u0006\u0010(\u001a\u00020%J\b\u0010T\u001a\u00020\u0006H\u0016J\b\u0010U\u001a\u000203H\u0002J\u0010\u0010V\u001a\u00020\u00122\u0006\u0010M\u001a\u00020\u0018H\u0016J\u0010\u0010V\u001a\u00020\u00122\u0006\u0010N\u001a\u00020 H\u0016J\u0018\u0010V\u001a\u00020\u00122\u0006\u0010W\u001a\u00020 2\u0006\u0010X\u001a\u00020%H\u0002J\u0006\u0010)\u001a\u00020%J\u0006\u0010Y\u001a\u000203J\r\u0010Z\u001a\u00020\u0012H\u0000\u00a2\u0006\u0002\b[J\r\u0010\\\u001a\u000203H\u0000\u00a2\u0006\u0002\b]J\f\u0010^\u001a\u00020\u0012*\u00020\u000eH\u0002R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010/X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00100\u001a\u0004\u0018\u000101X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006d"}, d2={"Lokhttp3/internal/ws/RealWebSocket;", "Lokhttp3/WebSocket;", "Lokhttp3/internal/ws/WebSocketReader$FrameCallback;", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "originalRequest", "Lokhttp3/Request;", "listener", "Lokhttp3/WebSocketListener;", "random", "Ljava/util/Random;", "pingIntervalMillis", "", "extensions", "Lokhttp3/internal/ws/WebSocketExtensions;", "minimumDeflateSize", "(Lokhttp3/internal/concurrent/TaskRunner;Lokhttp3/Request;Lokhttp3/WebSocketListener;Ljava/util/Random;JLokhttp3/internal/ws/WebSocketExtensions;J)V", "awaitingPong", "", "call", "Lokhttp3/Call;", "enqueuedClose", "failed", "key", "", "getListener$okhttp", "()Lokhttp3/WebSocketListener;", "messageAndCloseQueue", "Ljava/util/ArrayDeque;", "", "name", "pongQueue", "Lokio/ByteString;", "queueSize", "reader", "Lokhttp3/internal/ws/WebSocketReader;", "receivedCloseCode", "", "receivedCloseReason", "receivedPingCount", "receivedPongCount", "sentPingCount", "streams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "taskQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "writer", "Lokhttp3/internal/ws/WebSocketWriter;", "writerTask", "Lokhttp3/internal/concurrent/Task;", "awaitTermination", "", "timeout", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "cancel", "checkUpgradeSuccess", "response", "Lokhttp3/Response;", "exchange", "Lokhttp3/internal/connection/Exchange;", "checkUpgradeSuccess$okhttp", "close", "code", "reason", "cancelAfterCloseMillis", "connect", "client", "Lokhttp3/OkHttpClient;", "failWebSocket", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "initReaderAndWriter", "loopReader", "onReadClose", "onReadMessage", "text", "bytes", "onReadPing", "payload", "onReadPong", "pong", "processNextFrame", "request", "runWriter", "send", "data", "formatOpcode", "tearDown", "writeOneFrame", "writeOneFrame$okhttp", "writePingFrame", "writePingFrame$okhttp", "isValid", "Close", "Companion", "Message", "Streams", "WriterTask", "okhttp"}, k=1, mv={1, 1, 16})
public final class RealWebSocket
implements WebSocket,
WebSocketReader.FrameCallback {
    private static final long CANCEL_AFTER_CLOSE_MILLIS = 60000L;
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_MINIMUM_DEFLATE_SIZE = 1024L;
    private static final long MAX_QUEUE_SIZE = 0x1000000L;
    private static final List<Protocol> ONLY_HTTP1 = CollectionsKt.listOf(Protocol.HTTP_1_1);
    private boolean awaitingPong;
    private Call call;
    private boolean enqueuedClose;
    private WebSocketExtensions extensions;
    private boolean failed;
    private final String key;
    private final WebSocketListener listener;
    private final ArrayDeque<Object> messageAndCloseQueue;
    private long minimumDeflateSize;
    private String name;
    private final Request originalRequest;
    private final long pingIntervalMillis;
    private final ArrayDeque<ByteString> pongQueue;
    private long queueSize;
    private final Random random;
    private WebSocketReader reader;
    private int receivedCloseCode;
    private String receivedCloseReason;
    private int receivedPingCount;
    private int receivedPongCount;
    private int sentPingCount;
    private Streams streams;
    private TaskQueue taskQueue;
    private WebSocketWriter writer;
    private Task writerTask;

    public RealWebSocket(TaskRunner object, Request arrby, WebSocketListener webSocketListener, Random random, long l, WebSocketExtensions webSocketExtensions, long l2) {
        Intrinsics.checkParameterIsNotNull(object, "taskRunner");
        Intrinsics.checkParameterIsNotNull(arrby, "originalRequest");
        Intrinsics.checkParameterIsNotNull(webSocketListener, "listener");
        Intrinsics.checkParameterIsNotNull(random, "random");
        this.originalRequest = arrby;
        this.listener = webSocketListener;
        this.random = random;
        this.pingIntervalMillis = l;
        this.extensions = webSocketExtensions;
        this.minimumDeflateSize = l2;
        this.taskQueue = ((TaskRunner)object).newQueue();
        this.pongQueue = new ArrayDeque();
        this.messageAndCloseQueue = new ArrayDeque();
        this.receivedCloseCode = -1;
        if (Intrinsics.areEqual("GET", this.originalRequest.method())) {
            object = ByteString.Companion;
            arrby = new byte[16];
            this.random.nextBytes(arrby);
            this.key = ByteString.Companion.of$default((ByteString.Companion)object, arrby, 0, 0, 3, null).base64();
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Request must be GET: ");
        ((StringBuilder)object).append(this.originalRequest.method());
        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }

    public static final /* synthetic */ WebSocketExtensions access$getExtensions$p(RealWebSocket realWebSocket) {
        return realWebSocket.extensions;
    }

    public static final /* synthetic */ ArrayDeque access$getMessageAndCloseQueue$p(RealWebSocket realWebSocket) {
        return realWebSocket.messageAndCloseQueue;
    }

    public static final /* synthetic */ boolean access$isValid(RealWebSocket realWebSocket, WebSocketExtensions webSocketExtensions) {
        return realWebSocket.isValid(webSocketExtensions);
    }

    public static final /* synthetic */ void access$setExtensions$p(RealWebSocket realWebSocket, WebSocketExtensions webSocketExtensions) {
        realWebSocket.extensions = webSocketExtensions;
    }

    public static final /* synthetic */ void access$setName$p(RealWebSocket realWebSocket, String string2) {
        realWebSocket.name = string2;
    }

    private final boolean isValid(WebSocketExtensions webSocketExtensions) {
        if (webSocketExtensions.unknownValues) {
            return false;
        }
        if (webSocketExtensions.clientMaxWindowBits != null) {
            return false;
        }
        if (webSocketExtensions.serverMaxWindowBits == null) return true;
        int n = webSocketExtensions.serverMaxWindowBits;
        if (8 > n) return false;
        if (15 >= n) return true;
        return false;
    }

    private final void runWriter() {
        if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            Thread thread2 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
            stringBuilder.append(thread2.getName());
            stringBuilder.append(" MUST hold lock on ");
            stringBuilder.append(this);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        Task task = this.writerTask;
        if (task == null) return;
        TaskQueue.schedule$default(this.taskQueue, task, 0L, 2, null);
    }

    private final boolean send(ByteString byteString, int n) {
        synchronized (this) {
            if (this.failed) return false;
            if (this.enqueuedClose) {
                return false;
            }
            if (this.queueSize + (long)byteString.size() > 0x1000000L) {
                this.close(1001, null);
                return false;
            }
            this.queueSize += (long)byteString.size();
            ArrayDeque<Object> arrayDeque = this.messageAndCloseQueue;
            Message message = new Message(n, byteString);
            arrayDeque.add(message);
            this.runWriter();
            return true;
        }
    }

    public final void awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "timeUnit");
        this.taskQueue.idleLatch().await(l, timeUnit);
    }

    @Override
    public void cancel() {
        Call call = this.call;
        if (call == null) {
            Intrinsics.throwNpe();
        }
        call.cancel();
    }

    public final void checkUpgradeSuccess$okhttp(Response object, Exchange object2) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "response");
        if (((Response)object).code() != 101) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Expected HTTP 101 response but was '");
            ((StringBuilder)object2).append(((Response)object).code());
            ((StringBuilder)object2).append(' ');
            ((StringBuilder)object2).append(((Response)object).message());
            ((StringBuilder)object2).append('\'');
            throw (Throwable)new ProtocolException(((StringBuilder)object2).toString());
        }
        CharSequence charSequence = Response.header$default((Response)object, "Connection", null, 2, null);
        if (!StringsKt.equals("Upgrade", (String)charSequence, true)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Expected 'Connection' header value 'Upgrade' but was '");
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append('\'');
            throw (Throwable)new ProtocolException(((StringBuilder)object).toString());
        }
        charSequence = Response.header$default((Response)object, "Upgrade", null, 2, null);
        if (!StringsKt.equals("websocket", (String)charSequence, true)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Expected 'Upgrade' header value 'websocket' but was '");
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append('\'');
            throw (Throwable)new ProtocolException(((StringBuilder)object).toString());
        }
        object = Response.header$default((Response)object, "Sec-WebSocket-Accept", null, 2, null);
        ByteString.Companion companion = ByteString.Companion;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.key);
        ((StringBuilder)charSequence).append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
        charSequence = companion.encodeUtf8(((StringBuilder)charSequence).toString()).sha1().base64();
        if (!(Intrinsics.areEqual(charSequence, object) ^ true)) {
            if (object2 == null) throw (Throwable)new ProtocolException("Web Socket exchange missing: bad interceptor?");
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Expected 'Sec-WebSocket-Accept' header value '");
        ((StringBuilder)object2).append((String)charSequence);
        ((StringBuilder)object2).append("' but was '");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append('\'');
        throw (Throwable)new ProtocolException(((StringBuilder)object2).toString());
    }

    @Override
    public boolean close(int n, String string2) {
        return this.close(n, string2, 60000L);
    }

    public final boolean close(int n, String object, long l) {
        synchronized (this) {
            boolean bl;
            WebSocketProtocol.INSTANCE.validateCloseCode(n);
            Object object2 = null;
            if (object != null && !(bl = (long)((ByteString)(object2 = ByteString.Companion.encodeUtf8((String)object))).size() <= 123L)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("reason.size() > 123: ");
                ((StringBuilder)object2).append((String)object);
                object2 = ((StringBuilder)object2).toString();
                object = new IllegalArgumentException(object2.toString());
                throw (Throwable)object;
            }
            if (this.failed) return false;
            if (this.enqueuedClose) {
                return false;
            }
            this.enqueuedClose = true;
            ArrayDeque<Object> arrayDeque = this.messageAndCloseQueue;
            object = new Close(n, (ByteString)object2, l);
            arrayDeque.add(object);
            this.runWriter();
            return true;
        }
    }

    public final void connect(OkHttpClient object) {
        Intrinsics.checkParameterIsNotNull(object, "client");
        if (this.originalRequest.header("Sec-WebSocket-Extensions") != null) {
            this.failWebSocket(new ProtocolException("Request header not permitted: 'Sec-WebSocket-Extensions'"), null);
            return;
        }
        Cloneable cloneable = ((OkHttpClient)object).newBuilder().eventListener(EventListener.NONE).protocols(ONLY_HTTP1).build();
        object = this.originalRequest.newBuilder().header("Upgrade", "websocket").header("Connection", "Upgrade").header("Sec-WebSocket-Key", this.key).header("Sec-WebSocket-Version", "13").header("Sec-WebSocket-Extensions", "permessage-deflate").build();
        cloneable = new RealCall((OkHttpClient)cloneable, (Request)object, true);
        this.call = cloneable;
        if (cloneable == null) {
            Intrinsics.throwNpe();
        }
        cloneable.enqueue(new Callback(this, (Request)object){
            final /* synthetic */ Request $request;
            final /* synthetic */ RealWebSocket this$0;
            {
                this.this$0 = realWebSocket;
                this.$request = request;
            }

            public void onFailure(Call call, IOException iOException) {
                Intrinsics.checkParameterIsNotNull(call, "call");
                Intrinsics.checkParameterIsNotNull(iOException, "e");
                this.this$0.failWebSocket(iOException, null);
            }

            public void onResponse(Call object, Response response) {
                Intrinsics.checkParameterIsNotNull(object, "call");
                Intrinsics.checkParameterIsNotNull(response, "response");
                Object object2 = response.exchange();
                try {
                    this.this$0.checkUpgradeSuccess$okhttp(response, (Exchange)object2);
                    if (object2 == null) {
                        Intrinsics.throwNpe();
                    }
                    object = ((Exchange)object2).newWebSocketStreams();
                }
                catch (IOException iOException) {
                    if (object2 != null) {
                        ((Exchange)object2).webSocketUpgradeFailed();
                    }
                    this.this$0.failWebSocket(iOException, response);
                    Util.closeQuietly(response);
                    return;
                }
                object2 = WebSocketExtensions.Companion.parse(response.headers());
                RealWebSocket.access$setExtensions$p(this.this$0, (WebSocketExtensions)object2);
                if (!RealWebSocket.access$isValid(this.this$0, (WebSocketExtensions)object2)) {
                    object2 = this.this$0;
                    synchronized (object2) {
                        RealWebSocket.access$getMessageAndCloseQueue$p(this.this$0).clear();
                        this.this$0.close(1010, "unexpected Sec-WebSocket-Extensions in response header");
                    }
                }
                try {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(Util.okHttpName);
                    ((StringBuilder)object2).append(" WebSocket ");
                    ((StringBuilder)object2).append(this.$request.url().redact());
                    object2 = ((StringBuilder)object2).toString();
                    this.this$0.initReaderAndWriter((String)object2, (Streams)object);
                    this.this$0.getListener$okhttp().onOpen(this.this$0, response);
                    this.this$0.loopReader();
                    return;
                }
                catch (Exception exception) {
                    this.this$0.failWebSocket(exception, null);
                }
            }
        });
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public final void failWebSocket(Exception exception, Response response) {
        WebSocketWriter webSocketWriter;
        Streams streams;
        WebSocketReader webSocketReader;
        Intrinsics.checkParameterIsNotNull(exception, "e");
        synchronized (this) {
            boolean bl = this.failed;
            if (bl) {
                return;
            }
            this.failed = true;
            streams = this.streams;
            this.streams = null;
            webSocketReader = this.reader;
            this.reader = null;
            webSocketWriter = this.writer;
            this.writer = null;
            this.taskQueue.shutdown();
            Unit unit = Unit.INSTANCE;
        }
        try {
            void var2_3;
            this.listener.onFailure(this, exception, (Response)var2_3);
            return;
        }
        finally {
            if (streams != null) {
                Util.closeQuietly(streams);
            }
            if (webSocketReader != null) {
                Util.closeQuietly(webSocketReader);
            }
            if (webSocketWriter != null) {
                Util.closeQuietly(webSocketWriter);
            }
        }
    }

    public final WebSocketListener getListener$okhttp() {
        return this.listener;
    }

    public final void initReaderAndWriter(String object, Streams streams) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "name");
        Intrinsics.checkParameterIsNotNull(streams, "streams");
        WebSocketExtensions webSocketExtensions = this.extensions;
        if (webSocketExtensions == null) {
            Intrinsics.throwNpe();
        }
        synchronized (this) {
            this.name = object;
            this.streams = streams;
            Object object2 = new WebSocketWriter(streams.getClient(), streams.getSink(), this.random, webSocketExtensions.perMessageDeflate, webSocketExtensions.noContextTakeover(streams.getClient()), this.minimumDeflateSize);
            this.writer = object2;
            object2 = new WriterTask();
            this.writerTask = (Task)object2;
            if (this.pingIntervalMillis != 0L) {
                long l = TimeUnit.MILLISECONDS.toNanos(this.pingIntervalMillis);
                object2 = this.taskQueue;
                CharSequence charSequence = new StringBuilder();
                charSequence.append((String)object);
                charSequence.append(" ping");
                charSequence = charSequence.toString();
                Task task = new Task((String)charSequence, (String)charSequence, l, this, (String)object, streams, webSocketExtensions){
                    final /* synthetic */ WebSocketExtensions $extensions$inlined;
                    final /* synthetic */ String $name;
                    final /* synthetic */ String $name$inlined;
                    final /* synthetic */ long $pingIntervalNanos$inlined;
                    final /* synthetic */ Streams $streams$inlined;
                    final /* synthetic */ RealWebSocket this$0;
                    {
                        this.$name = string2;
                        this.$pingIntervalNanos$inlined = l;
                        this.this$0 = realWebSocket;
                        this.$name$inlined = string4;
                        this.$streams$inlined = streams;
                        this.$extensions$inlined = webSocketExtensions;
                        super(string3, false, 2, null);
                    }

                    public long runOnce() {
                        this.this$0.writePingFrame$okhttp();
                        return this.$pingIntervalNanos$inlined;
                    }
                };
                ((TaskQueue)object2).schedule(task, l);
            }
            if (((Collection)this.messageAndCloseQueue).isEmpty() ^ true) {
                this.runWriter();
            }
            object = Unit.INSTANCE;
            // MONITOREXIT [0, 1] lbl28 : MonitorExitStatement: MONITOREXIT : this
            this.reader = new WebSocketReader(streams.getClient(), streams.getSource(), this, webSocketExtensions.perMessageDeflate, webSocketExtensions.noContextTakeover(streams.getClient() ^ true));
            return;
        }
    }

    public final void loopReader() throws IOException {
        while (this.receivedCloseCode == -1) {
            WebSocketReader webSocketReader = this.reader;
            if (webSocketReader == null) {
                Intrinsics.throwNpe();
            }
            webSocketReader.processNextFrame();
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public void onReadClose(int n, String object) {
        WebSocketWriter webSocketWriter;
        Streams streams;
        WebSocketReader webSocketReader;
        Intrinsics.checkParameterIsNotNull(object, "reason");
        boolean bl = true;
        boolean bl2 = n != -1;
        if (!bl2) throw (Throwable)new IllegalArgumentException("Failed requirement.".toString());
        Object object2 = null;
        WebSocketReader webSocketReader2 = null;
        WebSocketWriter webSocketWriter2 = null;
        synchronized (this) {
            bl2 = this.receivedCloseCode == -1 ? bl : false;
            if (!bl2) {
                object = new IllegalStateException("already closed".toString());
                throw (Throwable)object;
            }
            this.receivedCloseCode = n;
            this.receivedCloseReason = object;
            streams = object2;
            webSocketReader = webSocketReader2;
            webSocketWriter = webSocketWriter2;
            if (this.enqueuedClose) {
                streams = object2;
                webSocketReader = webSocketReader2;
                webSocketWriter = webSocketWriter2;
                if (this.messageAndCloseQueue.isEmpty()) {
                    streams = this.streams;
                    this.streams = null;
                    webSocketReader = this.reader;
                    this.reader = null;
                    webSocketWriter = this.writer;
                    this.writer = null;
                    this.taskQueue.shutdown();
                }
            }
            object2 = Unit.INSTANCE;
        }
        try {
            this.listener.onClosing(this, n, (String)object);
            if (streams == null) return;
            this.listener.onClosed(this, n, (String)object);
            return;
        }
        finally {
            if (streams != null) {
                Util.closeQuietly(streams);
            }
            if (webSocketReader != null) {
                Util.closeQuietly(webSocketReader);
            }
            if (webSocketWriter != null) {
                Util.closeQuietly(webSocketWriter);
            }
        }
    }

    @Override
    public void onReadMessage(String string2) throws IOException {
        Intrinsics.checkParameterIsNotNull(string2, "text");
        this.listener.onMessage((WebSocket)this, string2);
    }

    @Override
    public void onReadMessage(ByteString byteString) throws IOException {
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        this.listener.onMessage((WebSocket)this, byteString);
    }

    @Override
    public void onReadPing(ByteString byteString) {
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull(byteString, "payload");
            if (this.failed) return;
            if (this.enqueuedClose && this.messageAndCloseQueue.isEmpty()) {
                return;
            }
            this.pongQueue.add(byteString);
            this.runWriter();
            ++this.receivedPingCount;
            return;
        }
    }

    @Override
    public void onReadPong(ByteString byteString) {
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull(byteString, "payload");
            ++this.receivedPongCount;
            this.awaitingPong = false;
            return;
        }
    }

    public final boolean pong(ByteString byteString) {
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull(byteString, "payload");
            if (this.failed) return false;
            if (this.enqueuedClose && this.messageAndCloseQueue.isEmpty()) {
                return false;
            }
            this.pongQueue.add(byteString);
            this.runWriter();
            return true;
        }
    }

    public final boolean processNextFrame() throws IOException {
        boolean bl = false;
        try {
            WebSocketReader webSocketReader = this.reader;
            if (webSocketReader == null) {
                Intrinsics.throwNpe();
            }
            webSocketReader.processNextFrame();
            int n = this.receivedCloseCode;
            if (n != -1) return bl;
            return true;
        }
        catch (Exception exception) {
            this.failWebSocket(exception, null);
        }
        return bl;
    }

    @Override
    public long queueSize() {
        synchronized (this) {
            return this.queueSize;
        }
    }

    public final int receivedPingCount() {
        synchronized (this) {
            return this.receivedPingCount;
        }
    }

    public final int receivedPongCount() {
        synchronized (this) {
            return this.receivedPongCount;
        }
    }

    @Override
    public Request request() {
        return this.originalRequest;
    }

    @Override
    public boolean send(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "text");
        return this.send(ByteString.Companion.encodeUtf8(string2), 1);
    }

    @Override
    public boolean send(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "bytes");
        return this.send(byteString, 2);
    }

    public final int sentPingCount() {
        synchronized (this) {
            return this.sentPingCount;
        }
    }

    public final void tearDown() throws InterruptedException {
        this.taskQueue.shutdown();
        this.taskQueue.idleLatch().await(10L, TimeUnit.SECONDS);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public final boolean writeOneFrame$okhttp() throws IOException {
        block30 : {
            block29 : {
                block28 : {
                    var1_1 = new Ref.ObjectRef();
                    var1_1.element = null;
                    var2_2 = new Ref.IntRef();
                    var2_2.element = -1;
                    var3_3 = new Ref.ObjectRef();
                    var3_3.element = null;
                    var4_4 = new Ref.ObjectRef();
                    var4_4.element = null;
                    var5_5 = new Ref.ObjectRef();
                    var5_5.element = null;
                    var6_6 = new Ref.ObjectRef();
                    var6_6.element = null;
                    // MONITORENTER : this
                    var7_7 = this.failed;
                    if (var7_7) {
                        // MONITOREXIT : this
                        return false;
                    }
                    var8_8 = this.writer;
                    var9_9 = this.pongQueue.poll();
                    if (var9_9 == null) {
                        var1_1.element = this.messageAndCloseQueue.poll();
                        if (var1_1.element instanceof Close) {
                            var2_2.element = this.receivedCloseCode;
                            var3_3.element = this.receivedCloseReason;
                            if (var2_2.element != -1) {
                                var4_4.element = this.streams;
                                this.streams = null;
                                var5_5.element = this.reader;
                                this.reader = null;
                                var6_6.element = this.writer;
                                this.writer = null;
                                this.taskQueue.shutdown();
                            } else {
                                var10_14 = var1_1.element;
                                if (var10_14 == null) {
                                    var9_9 = new TypeCastException("null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Close");
                                    throw var9_9;
                                }
                                var11_15 = ((Close)var10_14).getCancelAfterCloseMillis();
                                var10_14 = this.taskQueue;
                                var13_16 /* !! */  = new StringBuilder();
                                var13_16 /* !! */ .append(this.name);
                                var13_16 /* !! */ .append(" cancel");
                                var13_16 /* !! */  = var13_16 /* !! */ .toString();
                                var11_15 = TimeUnit.MILLISECONDS.toNanos(var11_15);
                                var14_17 = new Task((String)var13_16 /* !! */ , true, (String)var13_16 /* !! */ , true, this, (WebSocketWriter)var8_8, (ByteString)var9_9, var1_1, var2_2, (Ref.ObjectRef)var3_3, (Ref.ObjectRef)var4_4, (Ref.ObjectRef)var5_5, (Ref.ObjectRef)var6_6){
                                    final /* synthetic */ boolean $cancelable;
                                    final /* synthetic */ Ref.ObjectRef $messageOrClose$inlined;
                                    final /* synthetic */ String $name;
                                    final /* synthetic */ ByteString $pong$inlined;
                                    final /* synthetic */ Ref.ObjectRef $readerToClose$inlined;
                                    final /* synthetic */ Ref.IntRef $receivedCloseCode$inlined;
                                    final /* synthetic */ Ref.ObjectRef $receivedCloseReason$inlined;
                                    final /* synthetic */ Ref.ObjectRef $streamsToClose$inlined;
                                    final /* synthetic */ WebSocketWriter $writer$inlined;
                                    final /* synthetic */ Ref.ObjectRef $writerToClose$inlined;
                                    final /* synthetic */ RealWebSocket this$0;
                                    {
                                        this.$name = string2;
                                        this.$cancelable = bl;
                                        this.this$0 = realWebSocket;
                                        this.$writer$inlined = webSocketWriter;
                                        this.$pong$inlined = byteString;
                                        this.$messageOrClose$inlined = objectRef;
                                        this.$receivedCloseCode$inlined = intRef;
                                        this.$receivedCloseReason$inlined = objectRef2;
                                        this.$streamsToClose$inlined = objectRef3;
                                        this.$readerToClose$inlined = objectRef4;
                                        this.$writerToClose$inlined = objectRef5;
                                        super(string3, bl2);
                                    }

                                    public long runOnce() {
                                        this.this$0.cancel();
                                        return -1L;
                                    }
                                };
                                var10_14.schedule(var14_17, var11_15);
                            }
                        } else {
                            var10_14 = var1_1.element;
                            if (var10_14 == null) {
                                // MONITOREXIT : this
                                return false;
                            }
                        }
                    }
                    var10_14 = Unit.INSTANCE;
                    // MONITOREXIT : this
                    if (var9_9 == null) ** GOTO lbl62
                    if (var8_8 != null) ** GOTO lbl60
                    Intrinsics.throwNpe();
lbl60: // 2 sources:
                    var8_8.writePong((ByteString)var9_9);
                    break block28;
lbl62: // 1 sources:
                    if (!(var1_1.element instanceof Message)) ** GOTO lbl76
                    var9_9 = var1_1.element;
                    if (var9_9 == null) ** GOTO lbl-1000
                    var9_9 = (Message)var9_9;
                    if (var8_8 == null) {
                        Intrinsics.throwNpe();
                    }
                    var8_8.writeMessageFrame(var9_9.getFormatOpcode(), var9_9.getData());
                    // MONITORENTER : this
                    this.queueSize -= (long)var9_9.getData().size();
                    var9_9 = Unit.INSTANCE;
                    // MONITOREXIT : this
                    break block28;
lbl-1000: // 1 sources:
                    {
                        var9_9 = new TypeCastException("null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Message");
                        throw var9_9;
lbl76: // 1 sources:
                        if (!(var1_1.element instanceof Close)) ** GOTO lbl107
                        var9_9 = var1_1.element;
                        if (var9_9 == null) break block29;
                        var9_9 = (Close)var9_9;
                        if (var8_8 == null) {
                            Intrinsics.throwNpe();
                        }
                        var8_8.writeClose(var9_9.getCode(), var9_9.getReason());
                    }
                    try {
                        if ((Streams)var4_4.element == null) break block28;
                        var8_8 = this.listener;
                        var9_9 = this;
                        var15_18 = var2_2.element;
                        var3_3 = (String)var3_3.element;
                        if (var3_3 == null) {
                            Intrinsics.throwNpe();
                        }
                        var8_8.onClosed((WebSocket)var9_9, var15_18, (String)var3_3);
                    }
                    catch (Throwable var9_10) {}
                }
                var9_9 = (Streams)var4_4.element;
                if (var9_9 != null) {
                    Util.closeQuietly((Closeable)var9_9);
                }
                if ((var9_9 = (WebSocketReader)var5_5.element) != null) {
                    Util.closeQuietly((Closeable)var9_9);
                }
                if ((var9_9 = (WebSocketWriter)var6_6.element) == null) return true;
                Util.closeQuietly((Closeable)var9_9);
                return true;
                break block30;
            }
            try {
                var9_9 = new TypeCastException("null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Close");
                throw var9_9;
lbl107: // 1 sources:
                var9_9 = new AssertionError();
                throw (Throwable)var9_9;
            }
            catch (Throwable var9_11) {}
            break block30;
            catch (Throwable var9_12) {
                // empty catch block
            }
        }
        var4_4 = (Streams)var4_4.element;
        if (var4_4 != null) {
            Util.closeQuietly((Closeable)var4_4);
        }
        if ((var5_5 = (WebSocketReader)var5_5.element) != null) {
            Util.closeQuietly((Closeable)var5_5);
        }
        if ((var6_6 = (WebSocketWriter)var6_6.element) == null) throw var9_13;
        Util.closeQuietly((Closeable)var6_6);
        throw var9_13;
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public final void writePingFrame$okhttp() {
        // MONITORENTER : this
        boolean bl = this.failed;
        if (bl) {
            // MONITOREXIT : this
            return;
        }
        WebSocketWriter webSocketWriter = this.writer;
        if (webSocketWriter == null) {
            // MONITOREXIT : this
            return;
        }
        int n = this.awaitingPong ? this.sentPingCount : -1;
        ++this.sentPingCount;
        this.awaitingPong = true;
        Object object = Unit.INSTANCE;
        // MONITOREXIT : this
        if (n != -1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("sent ping but didn't receive pong within ");
            ((StringBuilder)object).append(this.pingIntervalMillis);
            ((StringBuilder)object).append("ms (after ");
            ((StringBuilder)object).append(n - 1);
            ((StringBuilder)object).append(" successful ping/pongs)");
            this.failWebSocket(new SocketTimeoutException(((StringBuilder)object).toString()), null);
            return;
        }
        try {
            webSocketWriter.writePing(ByteString.EMPTY);
            return;
        }
        catch (IOException iOException) {
            this.failWebSocket(iOException, null);
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\b\b\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lokhttp3/internal/ws/RealWebSocket$Close;", "", "code", "", "reason", "Lokio/ByteString;", "cancelAfterCloseMillis", "", "(ILokio/ByteString;J)V", "getCancelAfterCloseMillis", "()J", "getCode", "()I", "getReason", "()Lokio/ByteString;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Close {
        private final long cancelAfterCloseMillis;
        private final int code;
        private final ByteString reason;

        public Close(int n, ByteString byteString, long l) {
            this.code = n;
            this.reason = byteString;
            this.cancelAfterCloseMillis = l;
        }

        public final long getCancelAfterCloseMillis() {
            return this.cancelAfterCloseMillis;
        }

        public final int getCode() {
            return this.code;
        }

        public final ByteString getReason() {
            return this.reason;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lokhttp3/internal/ws/RealWebSocket$Companion;", "", "()V", "CANCEL_AFTER_CLOSE_MILLIS", "", "DEFAULT_MINIMUM_DEFLATE_SIZE", "MAX_QUEUE_SIZE", "ONLY_HTTP1", "", "Lokhttp3/Protocol;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2={"Lokhttp3/internal/ws/RealWebSocket$Message;", "", "formatOpcode", "", "data", "Lokio/ByteString;", "(ILokio/ByteString;)V", "getData", "()Lokio/ByteString;", "getFormatOpcode", "()I", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Message {
        private final ByteString data;
        private final int formatOpcode;

        public Message(int n, ByteString byteString) {
            Intrinsics.checkParameterIsNotNull(byteString, "data");
            this.formatOpcode = n;
            this.data = byteString;
        }

        public final ByteString getData() {
            return this.data;
        }

        public final int getFormatOpcode() {
            return this.formatOpcode;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lokhttp3/internal/ws/RealWebSocket$Streams;", "Ljava/io/Closeable;", "client", "", "source", "Lokio/BufferedSource;", "sink", "Lokio/BufferedSink;", "(ZLokio/BufferedSource;Lokio/BufferedSink;)V", "getClient", "()Z", "getSink", "()Lokio/BufferedSink;", "getSource", "()Lokio/BufferedSource;", "okhttp"}, k=1, mv={1, 1, 16})
    public static abstract class Streams
    implements Closeable {
        private final boolean client;
        private final BufferedSink sink;
        private final BufferedSource source;

        public Streams(boolean bl, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            Intrinsics.checkParameterIsNotNull(bufferedSource, "source");
            Intrinsics.checkParameterIsNotNull(bufferedSink, "sink");
            this.client = bl;
            this.source = bufferedSource;
            this.sink = bufferedSink;
        }

        public final boolean getClient() {
            return this.client;
        }

        public final BufferedSink getSink() {
            return this.sink;
        }

        public final BufferedSource getSource() {
            return this.source;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016\u00a8\u0006\u0005"}, d2={"Lokhttp3/internal/ws/RealWebSocket$WriterTask;", "Lokhttp3/internal/concurrent/Task;", "(Lokhttp3/internal/ws/RealWebSocket;)V", "runOnce", "", "okhttp"}, k=1, mv={1, 1, 16})
    private final class WriterTask
    extends Task {
        public WriterTask() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(RealWebSocket.this.name);
            stringBuilder.append(" writer");
            super(stringBuilder.toString(), false, 2, null);
        }

        @Override
        public long runOnce() {
            try {
                boolean bl = RealWebSocket.this.writeOneFrame$okhttp();
                if (!bl) return -1L;
                return 0L;
            }
            catch (IOException iOException) {
                RealWebSocket.this.failWebSocket(iOException, null);
            }
            return -1L;
        }
    }

}

