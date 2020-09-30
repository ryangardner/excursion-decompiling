/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.concurrent.TaskQueue$execute
 *  okhttp3.internal.http2.Http2Connection$$special$
 *  okhttp3.internal.http2.Http2Connection$$special$$inlined
 *  okhttp3.internal.http2.Http2Connection$$special$$inlined$schedule
 *  okhttp3.internal.http2.Http2Connection$Listener$Companion$REFUSE_INCOMING_STREAMS
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$applyAndAckSettings$
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$applyAndAckSettings$$inlined
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$applyAndAckSettings$$inlined$synchronized
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$applyAndAckSettings$$inlined$synchronized$lambda
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$headers$
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$headers$$inlined
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$headers$$inlined$synchronized
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$headers$$inlined$synchronized$lambda
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$ping$
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$ping$$inlined
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$ping$$inlined$execute
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$settings$
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$settings$$inlined
 *  okhttp3.internal.http2.Http2Connection$ReaderRunnable$settings$$inlined$execute
 *  okhttp3.internal.http2.Http2Connection$pushDataLater$
 *  okhttp3.internal.http2.Http2Connection$pushDataLater$$inlined
 *  okhttp3.internal.http2.Http2Connection$pushDataLater$$inlined$execute
 *  okhttp3.internal.http2.Http2Connection$pushHeadersLater$
 *  okhttp3.internal.http2.Http2Connection$pushHeadersLater$$inlined
 *  okhttp3.internal.http2.Http2Connection$pushHeadersLater$$inlined$execute
 *  okhttp3.internal.http2.Http2Connection$pushRequestLater$
 *  okhttp3.internal.http2.Http2Connection$pushRequestLater$$inlined
 *  okhttp3.internal.http2.Http2Connection$pushRequestLater$$inlined$execute
 *  okhttp3.internal.http2.Http2Connection$pushResetLater$
 *  okhttp3.internal.http2.Http2Connection$pushResetLater$$inlined
 *  okhttp3.internal.http2.Http2Connection$pushResetLater$$inlined$execute
 *  okhttp3.internal.http2.Http2Connection$sendDegradedPingLater$
 *  okhttp3.internal.http2.Http2Connection$sendDegradedPingLater$$inlined
 *  okhttp3.internal.http2.Http2Connection$sendDegradedPingLater$$inlined$execute
 *  okhttp3.internal.http2.Http2Connection$writeSynResetLater$
 *  okhttp3.internal.http2.Http2Connection$writeSynResetLater$$inlined
 *  okhttp3.internal.http2.Http2Connection$writeSynResetLater$$inlined$execute
 *  okhttp3.internal.http2.Http2Connection$writeWindowUpdateLater$
 *  okhttp3.internal.http2.Http2Connection$writeWindowUpdateLater$$inlined
 *  okhttp3.internal.http2.Http2Connection$writeWindowUpdateLater$$inlined$execute
 */
package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Connection$$special$;
import okhttp3.internal.http2.Http2Connection$ReaderRunnable$applyAndAckSettings$;
import okhttp3.internal.http2.Http2Connection$ReaderRunnable$headers$;
import okhttp3.internal.http2.Http2Connection$ReaderRunnable$ping$;
import okhttp3.internal.http2.Http2Connection$ReaderRunnable$settings$;
import okhttp3.internal.http2.Http2Connection$pushDataLater$;
import okhttp3.internal.http2.Http2Connection$pushHeadersLater$;
import okhttp3.internal.http2.Http2Connection$pushRequestLater$;
import okhttp3.internal.http2.Http2Connection$pushResetLater$;
import okhttp3.internal.http2.Http2Connection$sendDegradedPingLater$;
import okhttp3.internal.http2.Http2Connection$writeSynResetLater$;
import okhttp3.internal.http2.Http2Connection$writeWindowUpdateLater$;
import okhttp3.internal.http2.Http2Reader;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.http2.Http2Writer;
import okhttp3.internal.http2.PushObserver;
import okhttp3.internal.http2.Settings;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u00b4\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u0000 \u0099\u00012\u00020\u0001:\b\u0098\u0001\u0099\u0001\u009a\u0001\u009b\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010P\u001a\u00020QJ\b\u0010R\u001a\u00020QH\u0016J'\u0010R\u001a\u00020Q2\u0006\u0010S\u001a\u00020T2\u0006\u0010U\u001a\u00020T2\b\u0010V\u001a\u0004\u0018\u00010WH\u0000\u00a2\u0006\u0002\bXJ\u0012\u0010Y\u001a\u00020Q2\b\u0010Z\u001a\u0004\u0018\u00010WH\u0002J\u0006\u0010[\u001a\u00020QJ\u0010\u0010\\\u001a\u0004\u0018\u00010B2\u0006\u0010]\u001a\u00020\u0012J\u000e\u0010^\u001a\u00020\t2\u0006\u0010_\u001a\u00020\u0006J&\u0010`\u001a\u00020B2\u0006\u0010a\u001a\u00020\u00122\f\u0010b\u001a\b\u0012\u0004\u0012\u00020d0c2\u0006\u0010e\u001a\u00020\tH\u0002J\u001c\u0010`\u001a\u00020B2\f\u0010b\u001a\b\u0012\u0004\u0012\u00020d0c2\u0006\u0010e\u001a\u00020\tJ\u0006\u0010f\u001a\u00020\u0012J-\u0010g\u001a\u00020Q2\u0006\u0010h\u001a\u00020\u00122\u0006\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020\u00122\u0006\u0010l\u001a\u00020\tH\u0000\u00a2\u0006\u0002\bmJ+\u0010n\u001a\u00020Q2\u0006\u0010h\u001a\u00020\u00122\f\u0010b\u001a\b\u0012\u0004\u0012\u00020d0c2\u0006\u0010l\u001a\u00020\tH\u0000\u00a2\u0006\u0002\boJ#\u0010p\u001a\u00020Q2\u0006\u0010h\u001a\u00020\u00122\f\u0010b\u001a\b\u0012\u0004\u0012\u00020d0cH\u0000\u00a2\u0006\u0002\bqJ\u001d\u0010r\u001a\u00020Q2\u0006\u0010h\u001a\u00020\u00122\u0006\u0010s\u001a\u00020TH\u0000\u00a2\u0006\u0002\btJ$\u0010u\u001a\u00020B2\u0006\u0010a\u001a\u00020\u00122\f\u0010b\u001a\b\u0012\u0004\u0012\u00020d0c2\u0006\u0010e\u001a\u00020\tJ\u0015\u0010v\u001a\u00020\t2\u0006\u0010h\u001a\u00020\u0012H\u0000\u00a2\u0006\u0002\bwJ\u0017\u0010x\u001a\u0004\u0018\u00010B2\u0006\u0010h\u001a\u00020\u0012H\u0000\u00a2\u0006\u0002\byJ\r\u0010z\u001a\u00020QH\u0000\u00a2\u0006\u0002\b{J\u000e\u0010|\u001a\u00020Q2\u0006\u0010}\u001a\u00020&J\u000e\u0010~\u001a\u00020Q2\u0006\u0010\u001a\u00020TJ\u001e\u0010\u0001\u001a\u00020Q2\t\b\u0002\u0010\u0081\u0001\u001a\u00020\t2\b\b\u0002\u0010E\u001a\u00020FH\u0007J\u0018\u0010\u0082\u0001\u001a\u00020Q2\u0007\u0010\u0083\u0001\u001a\u00020\u0006H\u0000\u00a2\u0006\u0003\b\u0084\u0001J,\u0010\u0085\u0001\u001a\u00020Q2\u0006\u0010h\u001a\u00020\u00122\u0007\u0010\u0086\u0001\u001a\u00020\t2\n\u0010\u0087\u0001\u001a\u0005\u0018\u00010\u0088\u00012\u0006\u0010k\u001a\u00020\u0006J/\u0010\u0089\u0001\u001a\u00020Q2\u0006\u0010h\u001a\u00020\u00122\u0007\u0010\u0086\u0001\u001a\u00020\t2\r\u0010\u008a\u0001\u001a\b\u0012\u0004\u0012\u00020d0cH\u0000\u00a2\u0006\u0003\b\u008b\u0001J\u0007\u0010\u008c\u0001\u001a\u00020QJ\"\u0010\u008c\u0001\u001a\u00020Q2\u0007\u0010\u008d\u0001\u001a\u00020\t2\u0007\u0010\u008e\u0001\u001a\u00020\u00122\u0007\u0010\u008f\u0001\u001a\u00020\u0012J\u0007\u0010\u0090\u0001\u001a\u00020QJ\u001f\u0010\u0091\u0001\u001a\u00020Q2\u0006\u0010h\u001a\u00020\u00122\u0006\u0010\u001a\u00020TH\u0000\u00a2\u0006\u0003\b\u0092\u0001J\u001f\u0010\u0093\u0001\u001a\u00020Q2\u0006\u0010h\u001a\u00020\u00122\u0006\u0010s\u001a\u00020TH\u0000\u00a2\u0006\u0003\b\u0094\u0001J \u0010\u0095\u0001\u001a\u00020Q2\u0006\u0010h\u001a\u00020\u00122\u0007\u0010\u0096\u0001\u001a\u00020\u0006H\u0000\u00a2\u0006\u0003\b\u0097\u0001R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\tX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\rX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u0012X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001fX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020\u0012X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001b\"\u0004\b$\u0010\u001dR\u0011\u0010%\u001a\u00020&\u00a2\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020&X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010(\"\u0004\b+\u0010,R\u000e\u0010-\u001a\u00020.X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u00102\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u001e\u00105\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u00104R\u0015\u00107\u001a\u000608R\u00020\u0000\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u000e\u0010;\u001a\u000200X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010<\u001a\u00020=X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010?R \u0010@\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020B0AX\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u000e\u0010E\u001a\u00020FX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010G\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u00104R\u001e\u0010I\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u00104R\u0011\u0010K\u001a\u00020L\u00a2\u0006\b\n\u0000\u001a\u0004\bM\u0010NR\u000e\u0010O\u001a\u000200X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u009c\u0001"}, d2={"Lokhttp3/internal/http2/Http2Connection;", "Ljava/io/Closeable;", "builder", "Lokhttp3/internal/http2/Http2Connection$Builder;", "(Lokhttp3/internal/http2/Http2Connection$Builder;)V", "awaitPingsSent", "", "awaitPongsReceived", "client", "", "getClient$okhttp", "()Z", "connectionName", "", "getConnectionName$okhttp", "()Ljava/lang/String;", "currentPushRequests", "", "", "degradedPingsSent", "degradedPongDeadlineNs", "degradedPongsReceived", "intervalPingsSent", "intervalPongsReceived", "isShutdown", "lastGoodStreamId", "getLastGoodStreamId$okhttp", "()I", "setLastGoodStreamId$okhttp", "(I)V", "listener", "Lokhttp3/internal/http2/Http2Connection$Listener;", "getListener$okhttp", "()Lokhttp3/internal/http2/Http2Connection$Listener;", "nextStreamId", "getNextStreamId$okhttp", "setNextStreamId$okhttp", "okHttpSettings", "Lokhttp3/internal/http2/Settings;", "getOkHttpSettings", "()Lokhttp3/internal/http2/Settings;", "peerSettings", "getPeerSettings", "setPeerSettings", "(Lokhttp3/internal/http2/Settings;)V", "pushObserver", "Lokhttp3/internal/http2/PushObserver;", "pushQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "<set-?>", "readBytesAcknowledged", "getReadBytesAcknowledged", "()J", "readBytesTotal", "getReadBytesTotal", "readerRunnable", "Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "getReaderRunnable", "()Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "settingsListenerQueue", "socket", "Ljava/net/Socket;", "getSocket$okhttp", "()Ljava/net/Socket;", "streams", "", "Lokhttp3/internal/http2/Http2Stream;", "getStreams$okhttp", "()Ljava/util/Map;", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "writeBytesMaximum", "getWriteBytesMaximum", "writeBytesTotal", "getWriteBytesTotal", "writer", "Lokhttp3/internal/http2/Http2Writer;", "getWriter", "()Lokhttp3/internal/http2/Http2Writer;", "writerQueue", "awaitPong", "", "close", "connectionCode", "Lokhttp3/internal/http2/ErrorCode;", "streamCode", "cause", "Ljava/io/IOException;", "close$okhttp", "failConnection", "e", "flush", "getStream", "id", "isHealthy", "nowNs", "newStream", "associatedStreamId", "requestHeaders", "", "Lokhttp3/internal/http2/Header;", "out", "openStreamCount", "pushDataLater", "streamId", "source", "Lokio/BufferedSource;", "byteCount", "inFinished", "pushDataLater$okhttp", "pushHeadersLater", "pushHeadersLater$okhttp", "pushRequestLater", "pushRequestLater$okhttp", "pushResetLater", "errorCode", "pushResetLater$okhttp", "pushStream", "pushedStream", "pushedStream$okhttp", "removeStream", "removeStream$okhttp", "sendDegradedPingLater", "sendDegradedPingLater$okhttp", "setSettings", "settings", "shutdown", "statusCode", "start", "sendConnectionPreface", "updateConnectionFlowControl", "read", "updateConnectionFlowControl$okhttp", "writeData", "outFinished", "buffer", "Lokio/Buffer;", "writeHeaders", "alternating", "writeHeaders$okhttp", "writePing", "reply", "payload1", "payload2", "writePingAndAwaitPong", "writeSynReset", "writeSynReset$okhttp", "writeSynResetLater", "writeSynResetLater$okhttp", "writeWindowUpdateLater", "unacknowledgedBytesRead", "writeWindowUpdateLater$okhttp", "Builder", "Companion", "Listener", "ReaderRunnable", "okhttp"}, k=1, mv={1, 1, 16})
public final class Http2Connection
implements Closeable {
    public static final int AWAIT_PING = 3;
    public static final Companion Companion = new Companion(null);
    private static final Settings DEFAULT_SETTINGS;
    public static final int DEGRADED_PING = 2;
    public static final int DEGRADED_PONG_TIMEOUT_NS = 1000000000;
    public static final int INTERVAL_PING = 1;
    public static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    private long awaitPingsSent;
    private long awaitPongsReceived;
    private final boolean client;
    private final String connectionName;
    private final Set<Integer> currentPushRequests;
    private long degradedPingsSent;
    private long degradedPongDeadlineNs;
    private long degradedPongsReceived;
    private long intervalPingsSent;
    private long intervalPongsReceived;
    private boolean isShutdown;
    private int lastGoodStreamId;
    private final Listener listener;
    private int nextStreamId;
    private final Settings okHttpSettings;
    private Settings peerSettings;
    private final PushObserver pushObserver;
    private final TaskQueue pushQueue;
    private long readBytesAcknowledged;
    private long readBytesTotal;
    private final ReaderRunnable readerRunnable;
    private final TaskQueue settingsListenerQueue;
    private final Socket socket;
    private final Map<Integer, Http2Stream> streams;
    private final TaskRunner taskRunner;
    private long writeBytesMaximum;
    private long writeBytesTotal;
    private final Http2Writer writer;
    private final TaskQueue writerQueue;

    static {
        Settings settings = new Settings();
        settings.set(7, 65535);
        settings.set(5, 16384);
        DEFAULT_SETTINGS = settings;
    }

    public Http2Connection(Builder object) {
        Intrinsics.checkParameterIsNotNull(object, "builder");
        this.client = ((Builder)object).getClient$okhttp();
        this.listener = ((Builder)object).getListener$okhttp();
        this.streams = new LinkedHashMap();
        this.connectionName = ((Builder)object).getConnectionName$okhttp();
        int n = ((Builder)object).getClient$okhttp() ? 3 : 2;
        this.nextStreamId = n;
        Object object2 = ((Builder)object).getTaskRunner$okhttp();
        this.taskRunner = object2;
        this.writerQueue = ((TaskRunner)object2).newQueue();
        this.pushQueue = this.taskRunner.newQueue();
        this.settingsListenerQueue = this.taskRunner.newQueue();
        this.pushObserver = ((Builder)object).getPushObserver$okhttp();
        object2 = new Settings();
        if (((Builder)object).getClient$okhttp()) {
            ((Settings)object2).set(7, 16777216);
        }
        this.okHttpSettings = object2;
        this.peerSettings = object2 = DEFAULT_SETTINGS;
        this.writeBytesMaximum = ((Settings)object2).getInitialWindowSize();
        this.socket = ((Builder)object).getSocket$okhttp();
        this.writer = new Http2Writer(((Builder)object).getSink$okhttp(), this.client);
        this.readerRunnable = new ReaderRunnable(new Http2Reader(((Builder)object).getSource$okhttp(), this.client));
        this.currentPushRequests = new LinkedHashSet();
        if (((Builder)object).getPingIntervalMillis$okhttp() == 0) return;
        long l = TimeUnit.MILLISECONDS.toNanos(((Builder)object).getPingIntervalMillis$okhttp());
        object = this.writerQueue;
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(this.connectionName);
        ((StringBuilder)object2).append(" ping");
        object2 = ((StringBuilder)object2).toString();
        ((TaskQueue)object).schedule(new Task((String)object2, (String)object2, this, l){
            final /* synthetic */ String $name;
            final /* synthetic */ long $pingIntervalNanos$inlined;
            final /* synthetic */ Http2Connection this$0;
            {
                this.$name = string2;
                this.this$0 = http2Connection;
                this.$pingIntervalNanos$inlined = l;
                super(string3, false, 2, null);
            }

            public long runOnce() {
                Http2Connection http2Connection = this.this$0;
                synchronized (http2Connection) {
                    block6 : {
                        boolean bl;
                        if (Http2Connection.access$getIntervalPongsReceived$p(this.this$0) < Http2Connection.access$getIntervalPingsSent$p(this.this$0)) {
                            bl = true;
                        } else {
                            Http2Connection http2Connection2 = this.this$0;
                            Http2Connection.access$setIntervalPingsSent$p(http2Connection2, Http2Connection.access$getIntervalPingsSent$p(http2Connection2) + 1L);
                            bl = false;
                        }
                        if (!bl) break block6;
                        Http2Connection.access$failConnection(this.this$0, null);
                        return -1L;
                    }
                    this.this$0.writePing(false, 1, 0);
                    return this.$pingIntervalNanos$inlined;
                    finally {
                    }
                }
            }
        }, l);
    }

    public static final /* synthetic */ Set access$getCurrentPushRequests$p(Http2Connection http2Connection) {
        return http2Connection.currentPushRequests;
    }

    public static final /* synthetic */ long access$getIntervalPingsSent$p(Http2Connection http2Connection) {
        return http2Connection.intervalPingsSent;
    }

    public static final /* synthetic */ PushObserver access$getPushObserver$p(Http2Connection http2Connection) {
        return http2Connection.pushObserver;
    }

    public static final /* synthetic */ long access$getWriteBytesMaximum$p(Http2Connection http2Connection) {
        return http2Connection.writeBytesMaximum;
    }

    public static final /* synthetic */ void access$setAwaitPongsReceived$p(Http2Connection http2Connection, long l) {
        http2Connection.awaitPongsReceived = l;
    }

    public static final /* synthetic */ void access$setDegradedPongsReceived$p(Http2Connection http2Connection, long l) {
        http2Connection.degradedPongsReceived = l;
    }

    public static final /* synthetic */ void access$setIntervalPingsSent$p(Http2Connection http2Connection, long l) {
        http2Connection.intervalPingsSent = l;
    }

    public static final /* synthetic */ void access$setIntervalPongsReceived$p(Http2Connection http2Connection, long l) {
        http2Connection.intervalPongsReceived = l;
    }

    public static final /* synthetic */ void access$setShutdown$p(Http2Connection http2Connection, boolean bl) {
        http2Connection.isShutdown = bl;
    }

    public static final /* synthetic */ void access$setWriteBytesMaximum$p(Http2Connection http2Connection, long l) {
        http2Connection.writeBytesMaximum = l;
    }

    private final void failConnection(IOException iOException) {
        this.close$okhttp(ErrorCode.PROTOCOL_ERROR, ErrorCode.PROTOCOL_ERROR, iOException);
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private final Http2Stream newStream(int n, List<Header> object, boolean bl) throws IOException {
        void var3_3;
        boolean bl2 = var3_3 ^ true;
        Http2Writer http2Writer = this.writer;
        // MONITORENTER : http2Writer
        // MONITORENTER : this
        if (this.nextStreamId > 1073741823) {
            this.shutdown(ErrorCode.REFUSED_STREAM);
        }
        if (this.isShutdown) {
            object = new ConnectionShutdownException();
            throw (Throwable)object;
        }
        int n2 = this.nextStreamId;
        this.nextStreamId += 2;
        Http2Stream http2Stream = new Http2Stream(n2, this, bl2, false, null);
        boolean bl3 = var3_3 == false || this.writeBytesTotal >= this.writeBytesMaximum || http2Stream.getWriteBytesTotal() >= http2Stream.getWriteBytesMaximum();
        if (http2Stream.isOpen()) {
            this.streams.put(n2, http2Stream);
        }
        Unit unit = Unit.INSTANCE;
        // MONITOREXIT : this
        if (n == 0) {
            this.writer.headers(bl2, n2, (List<Header>)object);
        } else {
            if (!(true ^ this.client)) {
                object = new IllegalArgumentException("client streams shouldn't have associated stream IDs".toString());
                throw (Throwable)object;
            }
            this.writer.pushPromise(n, n2, (List<Header>)object);
        }
        object = Unit.INSTANCE;
        // MONITOREXIT : http2Writer
        if (!bl3) return http2Stream;
        this.writer.flush();
        return http2Stream;
    }

    public static /* synthetic */ void start$default(Http2Connection http2Connection, boolean bl, TaskRunner taskRunner, int n, Object object) throws IOException {
        if ((n & 1) != 0) {
            bl = true;
        }
        if ((n & 2) != 0) {
            taskRunner = TaskRunner.INSTANCE;
        }
        http2Connection.start(bl, taskRunner);
    }

    public final void awaitPong() throws InterruptedException {
        synchronized (this) {
            while (this.awaitPongsReceived < this.awaitPingsSent) {
                ((Object)this).wait();
            }
            return;
        }
    }

    @Override
    public void close() {
        this.close$okhttp(ErrorCode.NO_ERROR, ErrorCode.CANCEL, null);
    }

    /*
     * Exception decompiling
     */
    public final void close$okhttp(ErrorCode var1_1, ErrorCode var2_6, IOException var3_7) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 5[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    public final void flush() throws IOException {
        this.writer.flush();
    }

    public final boolean getClient$okhttp() {
        return this.client;
    }

    public final String getConnectionName$okhttp() {
        return this.connectionName;
    }

    public final int getLastGoodStreamId$okhttp() {
        return this.lastGoodStreamId;
    }

    public final Listener getListener$okhttp() {
        return this.listener;
    }

    public final int getNextStreamId$okhttp() {
        return this.nextStreamId;
    }

    public final Settings getOkHttpSettings() {
        return this.okHttpSettings;
    }

    public final Settings getPeerSettings() {
        return this.peerSettings;
    }

    public final long getReadBytesAcknowledged() {
        return this.readBytesAcknowledged;
    }

    public final long getReadBytesTotal() {
        return this.readBytesTotal;
    }

    public final ReaderRunnable getReaderRunnable() {
        return this.readerRunnable;
    }

    public final Socket getSocket$okhttp() {
        return this.socket;
    }

    public final Http2Stream getStream(int n) {
        synchronized (this) {
            return this.streams.get(n);
        }
    }

    public final Map<Integer, Http2Stream> getStreams$okhttp() {
        return this.streams;
    }

    public final long getWriteBytesMaximum() {
        return this.writeBytesMaximum;
    }

    public final long getWriteBytesTotal() {
        return this.writeBytesTotal;
    }

    public final Http2Writer getWriter() {
        return this.writer;
    }

    public final boolean isHealthy(long l) {
        synchronized (this) {
            boolean bl = this.isShutdown;
            if (bl) {
                return false;
            }
            if (this.degradedPongsReceived >= this.degradedPingsSent) return true;
            long l2 = this.degradedPongDeadlineNs;
            if (l < l2) return true;
            return false;
        }
    }

    public final Http2Stream newStream(List<Header> list, boolean bl) throws IOException {
        Intrinsics.checkParameterIsNotNull(list, "requestHeaders");
        return this.newStream(0, list, bl);
    }

    public final int openStreamCount() {
        synchronized (this) {
            return this.streams.size();
        }
    }

    public final void pushDataLater$okhttp(int n, BufferedSource object, int n2, boolean bl) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "source");
        Buffer buffer = new Buffer();
        long l = n2;
        object.require(l);
        object.read(buffer, l);
        object = this.pushQueue;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.connectionName);
        charSequence.append('[');
        charSequence.append(n);
        charSequence.append("] onData");
        charSequence = charSequence.toString();
        ((TaskQueue)object).schedule(new Task((String)charSequence, true, (String)charSequence, true, this, n, buffer, n2, bl){
            final /* synthetic */ Buffer $buffer$inlined;
            final /* synthetic */ int $byteCount$inlined;
            final /* synthetic */ boolean $cancelable;
            final /* synthetic */ boolean $inFinished$inlined;
            final /* synthetic */ String $name;
            final /* synthetic */ int $streamId$inlined;
            final /* synthetic */ Http2Connection this$0;
            {
                this.$name = string2;
                this.$cancelable = bl;
                this.this$0 = http2Connection;
                this.$streamId$inlined = n;
                this.$buffer$inlined = buffer;
                this.$byteCount$inlined = n2;
                this.$inFinished$inlined = bl3;
                super(string3, bl2);
            }

            /*
             * Enabled unnecessary exception pruning
             * Converted monitor instructions to comments
             */
            public long runOnce() {
                try {
                    boolean bl = Http2Connection.access$getPushObserver$p(this.this$0).onData(this.$streamId$inlined, this.$buffer$inlined, this.$byteCount$inlined, this.$inFinished$inlined);
                    if (bl) {
                        this.this$0.getWriter().rstStream(this.$streamId$inlined, ErrorCode.CANCEL);
                    }
                    if (!bl) {
                        if (!this.$inFinished$inlined) return -1L;
                    }
                    Http2Connection http2Connection = this.this$0;
                    // MONITORENTER : http2Connection
                    Http2Connection.access$getCurrentPushRequests$p(this.this$0).remove(this.$streamId$inlined);
                }
                catch (IOException iOException) {
                    return -1L;
                }
                return -1L;
            }
        }, 0L);
    }

    public final void pushHeadersLater$okhttp(int n, List<Header> list, boolean bl) {
        Intrinsics.checkParameterIsNotNull(list, "requestHeaders");
        TaskQueue taskQueue = this.pushQueue;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.connectionName);
        charSequence.append('[');
        charSequence.append(n);
        charSequence.append("] onHeaders");
        charSequence = charSequence.toString();
        taskQueue.schedule(new Task((String)charSequence, true, (String)charSequence, true, this, n, list, bl){
            final /* synthetic */ boolean $cancelable;
            final /* synthetic */ boolean $inFinished$inlined;
            final /* synthetic */ String $name;
            final /* synthetic */ List $requestHeaders$inlined;
            final /* synthetic */ int $streamId$inlined;
            final /* synthetic */ Http2Connection this$0;
            {
                this.$name = string2;
                this.$cancelable = bl;
                this.this$0 = http2Connection;
                this.$streamId$inlined = n;
                this.$requestHeaders$inlined = list;
                this.$inFinished$inlined = bl3;
                super(string3, bl2);
            }

            /*
             * Unable to fully structure code
             * Enabled unnecessary exception pruning
             * Converted monitor instructions to comments
             */
            public long runOnce() {
                var1_1 = Http2Connection.access$getPushObserver$p(this.this$0).onHeaders(this.$streamId$inlined, this.$requestHeaders$inlined, this.$inFinished$inlined);
                if (!var1_1) ** GOTO lbl5
                try {
                    this.this$0.getWriter().rstStream(this.$streamId$inlined, ErrorCode.CANCEL);
lbl5: // 2 sources:
                    if (!var1_1) {
                        if (this.$inFinished$inlined == false) return -1L;
                    }
                    var2_2 = this.this$0;
                    // MONITORENTER : var2_2
                    Http2Connection.access$getCurrentPushRequests$p(this.this$0).remove(this.$streamId$inlined);
                }
                catch (IOException var3_3) {
                    return -1L;
                }
                return -1L;
            }
        }, 0L);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void pushRequestLater$okhttp(int n, List<Header> list) {
        Intrinsics.checkParameterIsNotNull(list, "requestHeaders");
        synchronized (this) {
            if (this.currentPushRequests.contains(n)) {
                this.writeSynResetLater$okhttp(n, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.currentPushRequests.add(n);
        }
        TaskQueue taskQueue = this.pushQueue;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.connectionName);
        charSequence.append('[');
        charSequence.append(n);
        charSequence.append("] onRequest");
        charSequence = charSequence.toString();
        taskQueue.schedule(new Task((String)charSequence, true, (String)charSequence, true, this, n, list){
            final /* synthetic */ boolean $cancelable;
            final /* synthetic */ String $name;
            final /* synthetic */ List $requestHeaders$inlined;
            final /* synthetic */ int $streamId$inlined;
            final /* synthetic */ Http2Connection this$0;
            {
                this.$name = string2;
                this.$cancelable = bl;
                this.this$0 = http2Connection;
                this.$streamId$inlined = n;
                this.$requestHeaders$inlined = list;
                super(string3, bl2);
            }

            /*
             * Enabled unnecessary exception pruning
             * Converted monitor instructions to comments
             */
            public long runOnce() {
                if (!Http2Connection.access$getPushObserver$p(this.this$0).onRequest(this.$streamId$inlined, this.$requestHeaders$inlined)) return -1L;
                try {
                    this.this$0.getWriter().rstStream(this.$streamId$inlined, ErrorCode.CANCEL);
                    Http2Connection http2Connection = this.this$0;
                    // MONITORENTER : http2Connection
                    Http2Connection.access$getCurrentPushRequests$p(this.this$0).remove(this.$streamId$inlined);
                }
                catch (IOException iOException) {
                    return -1L;
                }
                return -1L;
            }
        }, 0L);
    }

    public final void pushResetLater$okhttp(int n, ErrorCode errorCode) {
        Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
        TaskQueue taskQueue = this.pushQueue;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.connectionName);
        charSequence.append('[');
        charSequence.append(n);
        charSequence.append("] onReset");
        charSequence = charSequence.toString();
        taskQueue.schedule(new Task((String)charSequence, true, (String)charSequence, true, this, n, errorCode){
            final /* synthetic */ boolean $cancelable;
            final /* synthetic */ ErrorCode $errorCode$inlined;
            final /* synthetic */ String $name;
            final /* synthetic */ int $streamId$inlined;
            final /* synthetic */ Http2Connection this$0;
            {
                this.$name = string2;
                this.$cancelable = bl;
                this.this$0 = http2Connection;
                this.$streamId$inlined = n;
                this.$errorCode$inlined = errorCode;
                super(string3, bl2);
            }

            public long runOnce() {
                Http2Connection.access$getPushObserver$p(this.this$0).onReset(this.$streamId$inlined, this.$errorCode$inlined);
                Http2Connection http2Connection = this.this$0;
                synchronized (http2Connection) {
                    Http2Connection.access$getCurrentPushRequests$p(this.this$0).remove(this.$streamId$inlined);
                    return -1L;
                }
            }
        }, 0L);
    }

    public final Http2Stream pushStream(int n, List<Header> list, boolean bl) throws IOException {
        Intrinsics.checkParameterIsNotNull(list, "requestHeaders");
        if (!(this.client ^ true)) throw (Throwable)new IllegalStateException("Client cannot push requests.".toString());
        return this.newStream(n, list, bl);
    }

    public final boolean pushedStream$okhttp(int n) {
        boolean bl = true;
        if (n == 0) return false;
        if ((n & 1) != 0) return false;
        return bl;
    }

    public final Http2Stream removeStream$okhttp(int n) {
        synchronized (this) {
            Http2Stream http2Stream = this.streams.remove(n);
            ((Object)this).notifyAll();
            return http2Stream;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void sendDegradedPingLater$okhttp() {
        Object object;
        synchronized (this) {
            long l = this.degradedPongsReceived;
            long l2 = this.degradedPingsSent++;
            if (l < l2) {
                return;
            }
            this.degradedPongDeadlineNs = System.nanoTime() + (long)1000000000;
            object = Unit.INSTANCE;
        }
        object = this.writerQueue;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.connectionName);
        charSequence.append(" ping");
        charSequence = charSequence.toString();
        ((TaskQueue)object).schedule(new Task((String)charSequence, true, (String)charSequence, true, this){
            final /* synthetic */ boolean $cancelable;
            final /* synthetic */ String $name;
            final /* synthetic */ Http2Connection this$0;
            {
                this.$name = string2;
                this.$cancelable = bl;
                this.this$0 = http2Connection;
                super(string3, bl2);
            }

            public long runOnce() {
                this.this$0.writePing(false, 2, 0);
                return -1L;
            }
        }, 0L);
    }

    public final void setLastGoodStreamId$okhttp(int n) {
        this.lastGoodStreamId = n;
    }

    public final void setNextStreamId$okhttp(int n) {
        this.nextStreamId = n;
    }

    public final void setPeerSettings(Settings settings) {
        Intrinsics.checkParameterIsNotNull(settings, "<set-?>");
        this.peerSettings = settings;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void setSettings(Settings object) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "settings");
        Http2Writer http2Writer = this.writer;
        synchronized (http2Writer) {
            synchronized (this) {
                if (!this.isShutdown) {
                    this.okHttpSettings.merge((Settings)object);
                    Unit unit = Unit.INSTANCE;
                    // MONITOREXIT [4, 5, 6] lbl8 : MonitorExitStatement: MONITOREXIT : this
                    this.writer.settings((Settings)object);
                    object = Unit.INSTANCE;
                    return;
                }
                object = new ConnectionShutdownException();
                throw (Throwable)object;
            }
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void shutdown(ErrorCode object) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "statusCode");
        Http2Writer http2Writer = this.writer;
        synchronized (http2Writer) {
            int n;
            synchronized (this) {
                boolean bl = this.isShutdown;
                if (bl) {
                    return;
                }
                this.isShutdown = true;
                n = this.lastGoodStreamId;
                Unit unit = Unit.INSTANCE;
            }
            this.writer.goAway(n, (ErrorCode)((Object)object), Util.EMPTY_BYTE_ARRAY);
            Unit unit = Unit.INSTANCE;
            return;
        }
    }

    public final void start() throws IOException {
        Http2Connection.start$default(this, false, null, 3, null);
    }

    public final void start(boolean bl) throws IOException {
        Http2Connection.start$default(this, bl, null, 2, null);
    }

    public final void start(boolean bl, TaskRunner object) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "taskRunner");
        if (bl) {
            this.writer.connectionPreface();
            this.writer.settings(this.okHttpSettings);
            int n = this.okHttpSettings.getInitialWindowSize();
            if (n != 65535) {
                this.writer.windowUpdate(0, n - 65535);
            }
        }
        object = ((TaskRunner)object).newQueue();
        String string2 = this.connectionName;
        ((TaskQueue)object).schedule(new Task(this.readerRunnable, string2, true, string2, true){
            final /* synthetic */ Function0 $block;
            final /* synthetic */ boolean $cancelable;
            final /* synthetic */ String $name;
            {
                this.$block = function0;
                this.$name = string2;
                this.$cancelable = bl;
                super(string3, bl2);
            }

            public long runOnce() {
                this.$block.invoke();
                return -1L;
            }
        }, 0L);
    }

    public final void updateConnectionFlowControl$okhttp(long l) {
        synchronized (this) {
            this.readBytesTotal = l = this.readBytesTotal + l;
            if ((l -= this.readBytesAcknowledged) < (long)(this.okHttpSettings.getInitialWindowSize() / 2)) return;
            this.writeWindowUpdateLater$okhttp(0, l);
            this.readBytesAcknowledged += l;
            return;
        }
    }

    /*
     * Exception decompiling
     */
    public final void writeData(int var1_1, boolean var2_2, Buffer var3_3, long var4_7) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    public final void writeHeaders$okhttp(int n, boolean bl, List<Header> list) throws IOException {
        Intrinsics.checkParameterIsNotNull(list, "alternating");
        this.writer.headers(bl, n, list);
    }

    public final void writePing() throws InterruptedException {
        synchronized (this) {
            ++this.awaitPingsSent;
            // MONITOREXIT [0, 1] lbl4 : MonitorExitStatement: MONITOREXIT : this
            this.writePing(false, 3, 1330343787);
            return;
        }
    }

    public final void writePing(boolean bl, int n, int n2) {
        try {
            this.writer.ping(bl, n, n2);
            return;
        }
        catch (IOException iOException) {
            this.failConnection(iOException);
        }
    }

    public final void writePingAndAwaitPong() throws InterruptedException {
        this.writePing();
        this.awaitPong();
    }

    public final void writeSynReset$okhttp(int n, ErrorCode errorCode) throws IOException {
        Intrinsics.checkParameterIsNotNull((Object)errorCode, "statusCode");
        this.writer.rstStream(n, errorCode);
    }

    public final void writeSynResetLater$okhttp(int n, ErrorCode errorCode) {
        Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
        TaskQueue taskQueue = this.writerQueue;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.connectionName);
        charSequence.append('[');
        charSequence.append(n);
        charSequence.append("] writeSynReset");
        charSequence = charSequence.toString();
        taskQueue.schedule(new Task((String)charSequence, true, (String)charSequence, true, this, n, errorCode){
            final /* synthetic */ boolean $cancelable;
            final /* synthetic */ ErrorCode $errorCode$inlined;
            final /* synthetic */ String $name;
            final /* synthetic */ int $streamId$inlined;
            final /* synthetic */ Http2Connection this$0;
            {
                this.$name = string2;
                this.$cancelable = bl;
                this.this$0 = http2Connection;
                this.$streamId$inlined = n;
                this.$errorCode$inlined = errorCode;
                super(string3, bl2);
            }

            public long runOnce() {
                try {
                    this.this$0.writeSynReset$okhttp(this.$streamId$inlined, this.$errorCode$inlined);
                    return -1L;
                }
                catch (IOException iOException) {
                    Http2Connection.access$failConnection(this.this$0, iOException);
                }
                return -1L;
            }
        }, 0L);
    }

    public final void writeWindowUpdateLater$okhttp(int n, long l) {
        TaskQueue taskQueue = this.writerQueue;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(this.connectionName);
        charSequence.append('[');
        charSequence.append(n);
        charSequence.append("] windowUpdate");
        charSequence = charSequence.toString();
        taskQueue.schedule(new Task((String)charSequence, true, (String)charSequence, true, this, n, l){
            final /* synthetic */ boolean $cancelable;
            final /* synthetic */ String $name;
            final /* synthetic */ int $streamId$inlined;
            final /* synthetic */ long $unacknowledgedBytesRead$inlined;
            final /* synthetic */ Http2Connection this$0;
            {
                this.$name = string2;
                this.$cancelable = bl;
                this.this$0 = http2Connection;
                this.$streamId$inlined = n;
                this.$unacknowledgedBytesRead$inlined = l;
                super(string3, bl2);
            }

            public long runOnce() {
                try {
                    this.this$0.getWriter().windowUpdate(this.$streamId$inlined, this.$unacknowledgedBytesRead$inlined);
                    return -1L;
                }
                catch (IOException iOException) {
                    Http2Connection.access$failConnection(this.this$0, iOException);
                }
                return -1L;
            }
        }, 0L);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u00107\u001a\u000208J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u001eJ.\u0010)\u001a\u00020\u00002\u0006\u0010)\u001a\u00020*2\b\b\u0002\u00109\u001a\u00020\f2\b\b\u0002\u0010/\u001a\u0002002\b\b\u0002\u0010#\u001a\u00020$H\u0007R\u001a\u0010\u0002\u001a\u00020\u0003X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u001eX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020$X.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020*X.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010/\u001a\u000200X.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u0014\u0010\u0004\u001a\u00020\u0005X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00106\u00a8\u0006:"}, d2={"Lokhttp3/internal/http2/Http2Connection$Builder;", "", "client", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "(ZLokhttp3/internal/concurrent/TaskRunner;)V", "getClient$okhttp", "()Z", "setClient$okhttp", "(Z)V", "connectionName", "", "getConnectionName$okhttp", "()Ljava/lang/String;", "setConnectionName$okhttp", "(Ljava/lang/String;)V", "listener", "Lokhttp3/internal/http2/Http2Connection$Listener;", "getListener$okhttp", "()Lokhttp3/internal/http2/Http2Connection$Listener;", "setListener$okhttp", "(Lokhttp3/internal/http2/Http2Connection$Listener;)V", "pingIntervalMillis", "", "getPingIntervalMillis$okhttp", "()I", "setPingIntervalMillis$okhttp", "(I)V", "pushObserver", "Lokhttp3/internal/http2/PushObserver;", "getPushObserver$okhttp", "()Lokhttp3/internal/http2/PushObserver;", "setPushObserver$okhttp", "(Lokhttp3/internal/http2/PushObserver;)V", "sink", "Lokio/BufferedSink;", "getSink$okhttp", "()Lokio/BufferedSink;", "setSink$okhttp", "(Lokio/BufferedSink;)V", "socket", "Ljava/net/Socket;", "getSocket$okhttp", "()Ljava/net/Socket;", "setSocket$okhttp", "(Ljava/net/Socket;)V", "source", "Lokio/BufferedSource;", "getSource$okhttp", "()Lokio/BufferedSource;", "setSource$okhttp", "(Lokio/BufferedSource;)V", "getTaskRunner$okhttp", "()Lokhttp3/internal/concurrent/TaskRunner;", "build", "Lokhttp3/internal/http2/Http2Connection;", "peerName", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Builder {
        private boolean client;
        public String connectionName;
        private Listener listener;
        private int pingIntervalMillis;
        private PushObserver pushObserver;
        public BufferedSink sink;
        public Socket socket;
        public BufferedSource source;
        private final TaskRunner taskRunner;

        public Builder(boolean bl, TaskRunner taskRunner) {
            Intrinsics.checkParameterIsNotNull(taskRunner, "taskRunner");
            this.client = bl;
            this.taskRunner = taskRunner;
            this.listener = Listener.REFUSE_INCOMING_STREAMS;
            this.pushObserver = PushObserver.CANCEL;
        }

        public static /* synthetic */ Builder socket$default(Builder builder, Socket socket, String string2, BufferedSource bufferedSource, BufferedSink bufferedSink, int n, Object object) throws IOException {
            if ((n & 2) != 0) {
                string2 = Util.peerName(socket);
            }
            if ((n & 4) != 0) {
                bufferedSource = Okio.buffer(Okio.source(socket));
            }
            if ((n & 8) == 0) return builder.socket(socket, string2, bufferedSource, bufferedSink);
            bufferedSink = Okio.buffer(Okio.sink(socket));
            return builder.socket(socket, string2, bufferedSource, bufferedSink);
        }

        public final Http2Connection build() {
            return new Http2Connection(this);
        }

        public final boolean getClient$okhttp() {
            return this.client;
        }

        public final String getConnectionName$okhttp() {
            String string2 = this.connectionName;
            if (string2 != null) return string2;
            Intrinsics.throwUninitializedPropertyAccessException("connectionName");
            return string2;
        }

        public final Listener getListener$okhttp() {
            return this.listener;
        }

        public final int getPingIntervalMillis$okhttp() {
            return this.pingIntervalMillis;
        }

        public final PushObserver getPushObserver$okhttp() {
            return this.pushObserver;
        }

        public final BufferedSink getSink$okhttp() {
            BufferedSink bufferedSink = this.sink;
            if (bufferedSink != null) return bufferedSink;
            Intrinsics.throwUninitializedPropertyAccessException("sink");
            return bufferedSink;
        }

        public final Socket getSocket$okhttp() {
            Socket socket = this.socket;
            if (socket != null) return socket;
            Intrinsics.throwUninitializedPropertyAccessException("socket");
            return socket;
        }

        public final BufferedSource getSource$okhttp() {
            BufferedSource bufferedSource = this.source;
            if (bufferedSource != null) return bufferedSource;
            Intrinsics.throwUninitializedPropertyAccessException("source");
            return bufferedSource;
        }

        public final TaskRunner getTaskRunner$okhttp() {
            return this.taskRunner;
        }

        public final Builder listener(Listener listener) {
            Intrinsics.checkParameterIsNotNull(listener, "listener");
            Builder builder = this;
            builder.listener = listener;
            return builder;
        }

        public final Builder pingIntervalMillis(int n) {
            Builder builder = this;
            builder.pingIntervalMillis = n;
            return builder;
        }

        public final Builder pushObserver(PushObserver pushObserver) {
            Intrinsics.checkParameterIsNotNull(pushObserver, "pushObserver");
            Builder builder = this;
            builder.pushObserver = pushObserver;
            return builder;
        }

        public final void setClient$okhttp(boolean bl) {
            this.client = bl;
        }

        public final void setConnectionName$okhttp(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "<set-?>");
            this.connectionName = string2;
        }

        public final void setListener$okhttp(Listener listener) {
            Intrinsics.checkParameterIsNotNull(listener, "<set-?>");
            this.listener = listener;
        }

        public final void setPingIntervalMillis$okhttp(int n) {
            this.pingIntervalMillis = n;
        }

        public final void setPushObserver$okhttp(PushObserver pushObserver) {
            Intrinsics.checkParameterIsNotNull(pushObserver, "<set-?>");
            this.pushObserver = pushObserver;
        }

        public final void setSink$okhttp(BufferedSink bufferedSink) {
            Intrinsics.checkParameterIsNotNull(bufferedSink, "<set-?>");
            this.sink = bufferedSink;
        }

        public final void setSocket$okhttp(Socket socket) {
            Intrinsics.checkParameterIsNotNull(socket, "<set-?>");
            this.socket = socket;
        }

        public final void setSource$okhttp(BufferedSource bufferedSource) {
            Intrinsics.checkParameterIsNotNull(bufferedSource, "<set-?>");
            this.source = bufferedSource;
        }

        public final Builder socket(Socket socket) throws IOException {
            return Builder.socket$default(this, socket, null, null, null, 14, null);
        }

        public final Builder socket(Socket socket, String string2) throws IOException {
            return Builder.socket$default(this, socket, string2, null, null, 12, null);
        }

        public final Builder socket(Socket socket, String string2, BufferedSource bufferedSource) throws IOException {
            return Builder.socket$default(this, socket, string2, bufferedSource, null, 8, null);
        }

        public final Builder socket(Socket object, String string2, BufferedSource bufferedSource, BufferedSink bufferedSink) throws IOException {
            Intrinsics.checkParameterIsNotNull(object, "socket");
            Intrinsics.checkParameterIsNotNull(string2, "peerName");
            Intrinsics.checkParameterIsNotNull(bufferedSource, "source");
            Intrinsics.checkParameterIsNotNull(bufferedSink, "sink");
            Builder builder = this;
            builder.socket = object;
            if (builder.client) {
                object = new StringBuilder();
                ((StringBuilder)object).append(Util.okHttpName);
                ((StringBuilder)object).append(' ');
                ((StringBuilder)object).append(string2);
                object = ((StringBuilder)object).toString();
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("MockWebServer ");
                ((StringBuilder)object).append(string2);
                object = ((StringBuilder)object).toString();
            }
            builder.connectionName = object;
            builder.source = bufferedSource;
            builder.sink = bufferedSink;
            return builder;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lokhttp3/internal/http2/Http2Connection$Companion;", "", "()V", "AWAIT_PING", "", "DEFAULT_SETTINGS", "Lokhttp3/internal/http2/Settings;", "getDEFAULT_SETTINGS", "()Lokhttp3/internal/http2/Settings;", "DEGRADED_PING", "DEGRADED_PONG_TIMEOUT_NS", "INTERVAL_PING", "OKHTTP_CLIENT_WINDOW_SIZE", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Settings getDEFAULT_SETTINGS() {
            return DEFAULT_SETTINGS;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH&\u00a8\u0006\r"}, d2={"Lokhttp3/internal/http2/Http2Connection$Listener;", "", "()V", "onSettings", "", "connection", "Lokhttp3/internal/http2/Http2Connection;", "settings", "Lokhttp3/internal/http2/Settings;", "onStream", "stream", "Lokhttp3/internal/http2/Http2Stream;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
    public static abstract class Listener {
        public static final Companion Companion = new Companion(null);
        public static final Listener REFUSE_INCOMING_STREAMS = new Listener(){

            public void onStream(Http2Stream http2Stream) throws IOException {
                Intrinsics.checkParameterIsNotNull(http2Stream, "stream");
                http2Stream.close(ErrorCode.REFUSED_STREAM, null);
            }
        };

        public void onSettings(Http2Connection http2Connection, Settings settings) {
            Intrinsics.checkParameterIsNotNull(http2Connection, "connection");
            Intrinsics.checkParameterIsNotNull(settings, "settings");
        }

        public abstract void onStream(Http2Stream var1) throws IOException;

        @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lokhttp3/internal/http2/Http2Connection$Listener$Companion;", "", "()V", "REFUSE_INCOMING_STREAMS", "Lokhttp3/internal/http2/Http2Connection$Listener;", "okhttp"}, k=1, mv={1, 1, 16})
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0086\u0004\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\u0003H\u0016J8\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0016\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J(\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\fH\u0016J \u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u0010H\u0016J.\u0010$\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010%\u001a\u00020\f2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'H\u0016J\t\u0010)\u001a\u00020\u0003H\u0096\u0002J \u0010*\u001a\u00020\u00032\u0006\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\f2\u0006\u0010-\u001a\u00020\fH\u0016J(\u0010.\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\f2\u0006\u00101\u001a\u00020\u0017H\u0016J&\u00102\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u00103\u001a\u00020\f2\f\u00104\u001a\b\u0012\u0004\u0012\u00020(0'H\u0016J\u0018\u00105\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010!\u001a\u00020\"H\u0016J\u0018\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0018\u00106\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u00107\u001a\u00020\u0014H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u00068"}, d2={"Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "Lokhttp3/internal/http2/Http2Reader$Handler;", "Lkotlin/Function0;", "", "reader", "Lokhttp3/internal/http2/Http2Reader;", "(Lokhttp3/internal/http2/Http2Connection;Lokhttp3/internal/http2/Http2Reader;)V", "getReader$okhttp", "()Lokhttp3/internal/http2/Http2Reader;", "ackSettings", "alternateService", "streamId", "", "origin", "", "protocol", "Lokio/ByteString;", "host", "port", "maxAge", "", "applyAndAckSettings", "clearPrevious", "", "settings", "Lokhttp3/internal/http2/Settings;", "data", "inFinished", "source", "Lokio/BufferedSource;", "length", "goAway", "lastGoodStreamId", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "debugData", "headers", "associatedStreamId", "headerBlock", "", "Lokhttp3/internal/http2/Header;", "invoke", "ping", "ack", "payload1", "payload2", "priority", "streamDependency", "weight", "exclusive", "pushPromise", "promisedStreamId", "requestHeaders", "rstStream", "windowUpdate", "windowSizeIncrement", "okhttp"}, k=1, mv={1, 1, 16})
    public final class ReaderRunnable
    implements Http2Reader.Handler,
    Function0<Unit> {
        private final Http2Reader reader;

        public ReaderRunnable(Http2Reader http2Reader) {
            Intrinsics.checkParameterIsNotNull(http2Reader, "reader");
            this.reader = http2Reader;
        }

        @Override
        public void ackSettings() {
        }

        @Override
        public void alternateService(int n, String string2, ByteString byteString, String string3, int n2, long l) {
            Intrinsics.checkParameterIsNotNull(string2, "origin");
            Intrinsics.checkParameterIsNotNull(byteString, "protocol");
            Intrinsics.checkParameterIsNotNull(string3, "host");
        }

        /*
         * Loose catch block
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        public final void applyAndAckSettings(boolean bl, Settings object) {
            Task task;
            Intrinsics.checkParameterIsNotNull(object, "settings");
            Ref.LongRef longRef = new Ref.LongRef();
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            Object object2 = Http2Connection.this.getWriter();
            // MONITORENTER : object2
            Http2Connection http2Connection = Http2Connection.this;
            // MONITORENTER : http2Connection
            Object object3 = Http2Connection.this.getPeerSettings();
            if (bl) {
                objectRef2.element = object;
            } else {
                task = new Settings();
                ((Settings)((Object)task)).merge((Settings)object3);
                ((Settings)((Object)task)).merge((Settings)object);
                objectRef2.element = task;
            }
            longRef.element = (long)((Settings)objectRef2.element).getInitialWindowSize() - (long)((Settings)object3).getInitialWindowSize();
            if (longRef.element != 0L && !Http2Connection.this.getStreams$okhttp().isEmpty()) {
                object3 = Http2Connection.this.getStreams$okhttp().values().toArray(new Http2Stream[0]);
                if (object3 == null) {
                    object = new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    throw object;
                }
            } else {
                object3 = null;
            }
            objectRef.element = object3;
            Http2Connection.this.setPeerSettings((Settings)objectRef2.element);
            object3 = Http2Connection.this.settingsListenerQueue;
            task = new StringBuilder();
            ((StringBuilder)((Object)task)).append(Http2Connection.this.getConnectionName$okhttp());
            ((StringBuilder)((Object)task)).append(" onSettings");
            String string2 = ((StringBuilder)((Object)task)).toString();
            task = new Task(string2, true, string2, true, this, bl, objectRef2, (Settings)object, longRef, objectRef){
                final /* synthetic */ boolean $cancelable;
                final /* synthetic */ boolean $clearPrevious$inlined;
                final /* synthetic */ Ref.LongRef $delta$inlined;
                final /* synthetic */ String $name;
                final /* synthetic */ Ref.ObjectRef $newPeerSettings$inlined;
                final /* synthetic */ Settings $settings$inlined;
                final /* synthetic */ Ref.ObjectRef $streamsToNotify$inlined;
                final /* synthetic */ ReaderRunnable this$0;
                {
                    this.$name = string2;
                    this.$cancelable = bl;
                    this.this$0 = readerRunnable;
                    this.$clearPrevious$inlined = bl3;
                    this.$newPeerSettings$inlined = objectRef;
                    this.$settings$inlined = settings;
                    this.$delta$inlined = longRef;
                    this.$streamsToNotify$inlined = objectRef2;
                    super(string3, bl2);
                }

                public long runOnce() {
                    this.this$0.Http2Connection.this.getListener$okhttp().onSettings(this.this$0.Http2Connection.this, (Settings)this.$newPeerSettings$inlined.element);
                    return -1L;
                }
            };
            ((TaskQueue)object3).schedule(task, 0L);
            object = Unit.INSTANCE;
            // MONITOREXIT : http2Connection
            try {
                Http2Connection.this.getWriter().applyAndAckSettings((Settings)objectRef2.element);
            }
            catch (IOException iOException) {
                Http2Connection.this.failConnection(iOException);
            }
            object = Unit.INSTANCE;
            // MONITOREXIT : object2
            if ((Http2Stream[])objectRef.element == null) return;
            object3 = (Http2Stream[])objectRef.element;
            if (object3 == null) {
                Intrinsics.throwNpe();
            }
            int n = ((Object)object3).length;
            int n2 = 0;
            while (n2 < n) {
                object = object3[n2];
                // MONITORENTER : object
                ((Http2Stream)object).addBytesToWriteWindow(longRef.element);
                object2 = Unit.INSTANCE;
                // MONITOREXIT : object
                ++n2;
            }
            return;
            catch (Throwable throwable) {
                // MONITOREXIT : http2Connection
                throw throwable;
            }
        }

        @Override
        public void data(boolean bl, int n, BufferedSource bufferedSource, int n2) throws IOException {
            Intrinsics.checkParameterIsNotNull(bufferedSource, "source");
            if (Http2Connection.this.pushedStream$okhttp(n)) {
                Http2Connection.this.pushDataLater$okhttp(n, bufferedSource, n2, bl);
                return;
            }
            Object object = Http2Connection.this.getStream(n);
            if (object == null) {
                Http2Connection.this.writeSynResetLater$okhttp(n, ErrorCode.PROTOCOL_ERROR);
                object = Http2Connection.this;
                long l = n2;
                ((Http2Connection)object).updateConnectionFlowControl$okhttp(l);
                bufferedSource.skip(l);
                return;
            }
            ((Http2Stream)object).receiveData(bufferedSource, n2);
            if (!bl) return;
            ((Http2Stream)object).receiveHeaders(Util.EMPTY_HEADERS, true);
        }

        public final Http2Reader getReader$okhttp() {
            return this.reader;
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public void goAway(int n, ErrorCode object, ByteString object2) {
            Http2Stream[] arrhttp2Stream;
            int n2;
            Intrinsics.checkParameterIsNotNull(object, "errorCode");
            Intrinsics.checkParameterIsNotNull(object2, "debugData");
            ((ByteString)object2).size();
            object = Http2Connection.this;
            synchronized (object) {
                Collection<Http2Stream> collection = Http2Connection.this.getStreams$okhttp().values();
                n2 = 0;
                Http2Stream[] arrhttp2Stream2 = collection.toArray(new Http2Stream[0]);
                if (arrhttp2Stream2 == null) {
                    TypeCastException typeCastException = new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    throw typeCastException;
                }
                arrhttp2Stream = arrhttp2Stream2;
                Http2Connection.access$setShutdown$p(Http2Connection.this, true);
                Unit unit = Unit.INSTANCE;
            }
            int n3 = arrhttp2Stream.length;
            while (n2 < n3) {
                object = arrhttp2Stream[n2];
                if (((Http2Stream)object).getId() > n && ((Http2Stream)object).isLocallyInitiated()) {
                    ((Http2Stream)object).receiveRstStream(ErrorCode.REFUSED_STREAM);
                    Http2Connection.this.removeStream$okhttp(((Http2Stream)object).getId());
                }
                ++n2;
            }
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public void headers(boolean bl, int n, int n2, List<Header> list) {
            Intrinsics.checkParameterIsNotNull(list, "headerBlock");
            if (Http2Connection.this.pushedStream$okhttp(n)) {
                Http2Connection.this.pushHeadersLater$okhttp(n, list, bl);
                return;
            }
            Http2Connection http2Connection = Http2Connection.this;
            synchronized (http2Connection) {
                Http2Stream http2Stream = Http2Connection.this.getStream(n);
                if (http2Stream != null) {
                    Unit unit = Unit.INSTANCE;
                    // MONITOREXIT [4, 8] lbl10 : MonitorExitStatement: MONITOREXIT : var5_5
                    http2Stream.receiveHeaders(Util.toHeaders(list), bl);
                    return;
                }
                boolean bl2 = Http2Connection.this.isShutdown;
                if (bl2) {
                    return;
                }
                n2 = Http2Connection.this.getLastGoodStreamId$okhttp();
                if (n <= n2) {
                    return;
                }
                n2 = Http2Connection.this.getNextStreamId$okhttp();
                if (n % 2 == n2 % 2) {
                    return;
                }
                Object object = Util.toHeaders(list);
                Http2Stream http2Stream2 = new Http2Stream(n, Http2Connection.this, false, bl, (Headers)object);
                Http2Connection.this.setLastGoodStreamId$okhttp(n);
                Http2Connection.this.getStreams$okhttp().put(n, http2Stream2);
                object = Http2Connection.this.taskRunner.newQueue();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Http2Connection.this.getConnectionName$okhttp());
                stringBuilder.append('[');
                stringBuilder.append(n);
                stringBuilder.append("] onStream");
                String string2 = stringBuilder.toString();
                Task task = new Task(string2, true, string2, true, http2Stream2, this, http2Stream, n, list, bl){
                    final /* synthetic */ boolean $cancelable;
                    final /* synthetic */ List $headerBlock$inlined;
                    final /* synthetic */ boolean $inFinished$inlined;
                    final /* synthetic */ String $name;
                    final /* synthetic */ Http2Stream $newStream$inlined;
                    final /* synthetic */ Http2Stream $stream$inlined;
                    final /* synthetic */ int $streamId$inlined;
                    final /* synthetic */ ReaderRunnable this$0;
                    {
                        this.$name = string2;
                        this.$cancelable = bl;
                        this.$newStream$inlined = http2Stream;
                        this.this$0 = readerRunnable;
                        this.$stream$inlined = http2Stream2;
                        this.$streamId$inlined = n;
                        this.$headerBlock$inlined = list;
                        this.$inFinished$inlined = bl3;
                        super(string3, bl2);
                    }

                    public long runOnce() {
                        try {
                            this.this$0.Http2Connection.this.getListener$okhttp().onStream(this.$newStream$inlined);
                            return -1L;
                        }
                        catch (IOException iOException) {
                            okhttp3.internal.platform.Platform platform = okhttp3.internal.platform.Platform.Companion.get();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Http2Connection.Listener failure for ");
                            stringBuilder.append(this.this$0.Http2Connection.this.getConnectionName$okhttp());
                            platform.log(stringBuilder.toString(), 4, iOException);
                            try {
                                this.$newStream$inlined.close(ErrorCode.PROTOCOL_ERROR, iOException);
                                return -1L;
                            }
                            catch (IOException iOException2) {
                                return -1L;
                            }
                        }
                    }
                };
                ((TaskQueue)object).schedule(task, 0L);
                return;
            }
        }

        @Override
        public void invoke() {
            Throwable throwable2;
            Object object;
            ErrorCode errorCode;
            IOException iOException;
            block5 : {
                Object object2 = ErrorCode.INTERNAL_ERROR;
                errorCode = ErrorCode.INTERNAL_ERROR;
                Object object3 = null;
                object = object2;
                iOException = object3;
                ErrorCode errorCode2 = object2;
                try {
                    try {
                        this.reader.readConnectionPreface(this);
                        do {
                            object = object2;
                            iOException = object3;
                            errorCode2 = object2;
                        } while (this.reader.nextFrame(false, this));
                        object = object2;
                        iOException = object3;
                        errorCode2 = object2;
                        object = object2 = ErrorCode.NO_ERROR;
                        iOException = object3;
                        errorCode2 = object2;
                        ErrorCode errorCode3 = ErrorCode.CANCEL;
                        errorCode2 = object2;
                        object = errorCode3;
                        object2 = object3;
                    }
                    catch (IOException iOException2) {
                        object = errorCode2;
                        iOException = iOException2;
                        object = errorCode2 = ErrorCode.PROTOCOL_ERROR;
                        iOException = iOException2;
                        object3 = ErrorCode.PROTOCOL_ERROR;
                        object = object3;
                    }
                    Http2Connection.this.close$okhttp(errorCode2, (ErrorCode)((Object)object), (IOException)object2);
                }
                catch (Throwable throwable2) {
                    break block5;
                }
                Util.closeQuietly(this.reader);
                return;
            }
            Http2Connection.this.close$okhttp((ErrorCode)((Object)object), errorCode, iOException);
            Util.closeQuietly(this.reader);
            throw throwable2;
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public void ping(boolean bl, int n, int n2) {
            if (!bl) {
                TaskQueue taskQueue = Http2Connection.this.writerQueue;
                CharSequence charSequence = new StringBuilder();
                charSequence.append(Http2Connection.this.getConnectionName$okhttp());
                charSequence.append(" ping");
                charSequence = charSequence.toString();
                taskQueue.schedule(new Task((String)charSequence, true, (String)charSequence, true, this, n, n2){
                    final /* synthetic */ boolean $cancelable;
                    final /* synthetic */ String $name;
                    final /* synthetic */ int $payload1$inlined;
                    final /* synthetic */ int $payload2$inlined;
                    final /* synthetic */ ReaderRunnable this$0;
                    {
                        this.$name = string2;
                        this.$cancelable = bl;
                        this.this$0 = readerRunnable;
                        this.$payload1$inlined = n;
                        this.$payload2$inlined = n2;
                        super(string3, bl2);
                    }

                    public long runOnce() {
                        this.this$0.Http2Connection.this.writePing(true, this.$payload1$inlined, this.$payload2$inlined);
                        return -1L;
                    }
                }, 0L);
                return;
            }
            Http2Connection http2Connection = Http2Connection.this;
            synchronized (http2Connection) {
                if (n != 1) {
                    if (n != 2) {
                        Object object;
                        if (n == 3) {
                            object = Http2Connection.this;
                            Http2Connection.access$setAwaitPongsReceived$p((Http2Connection)object, ((Http2Connection)object).awaitPongsReceived + 1L);
                            object = Http2Connection.this;
                            if (object == null) {
                                object = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                                throw object;
                            }
                            object.notifyAll();
                        }
                        object = Unit.INSTANCE;
                    } else {
                        Http2Connection http2Connection2 = Http2Connection.this;
                        long l = http2Connection2.degradedPongsReceived;
                        Http2Connection.access$setDegradedPongsReceived$p(http2Connection2, 1L + l);
                    }
                } else {
                    Http2Connection http2Connection3 = Http2Connection.this;
                    long l = http2Connection3.intervalPongsReceived;
                    Http2Connection.access$setIntervalPongsReceived$p(http2Connection3, 1L + l);
                }
                return;
            }
        }

        @Override
        public void priority(int n, int n2, int n3, boolean bl) {
        }

        @Override
        public void pushPromise(int n, int n2, List<Header> list) {
            Intrinsics.checkParameterIsNotNull(list, "requestHeaders");
            Http2Connection.this.pushRequestLater$okhttp(n2, list);
        }

        @Override
        public void rstStream(int n, ErrorCode errorCode) {
            Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
            if (Http2Connection.this.pushedStream$okhttp(n)) {
                Http2Connection.this.pushResetLater$okhttp(n, errorCode);
                return;
            }
            Http2Stream http2Stream = Http2Connection.this.removeStream$okhttp(n);
            if (http2Stream == null) return;
            http2Stream.receiveRstStream(errorCode);
        }

        @Override
        public void settings(boolean bl, Settings settings) {
            Intrinsics.checkParameterIsNotNull(settings, "settings");
            TaskQueue taskQueue = Http2Connection.this.writerQueue;
            CharSequence charSequence = new StringBuilder();
            charSequence.append(Http2Connection.this.getConnectionName$okhttp());
            charSequence.append(" applyAndAckSettings");
            charSequence = charSequence.toString();
            taskQueue.schedule(new Task((String)charSequence, true, (String)charSequence, true, this, bl, settings){
                final /* synthetic */ boolean $cancelable;
                final /* synthetic */ boolean $clearPrevious$inlined;
                final /* synthetic */ String $name;
                final /* synthetic */ Settings $settings$inlined;
                final /* synthetic */ ReaderRunnable this$0;
                {
                    this.$name = string2;
                    this.$cancelable = bl;
                    this.this$0 = readerRunnable;
                    this.$clearPrevious$inlined = bl3;
                    this.$settings$inlined = settings;
                    super(string3, bl2);
                }

                public long runOnce() {
                    this.this$0.applyAndAckSettings(this.$clearPrevious$inlined, this.$settings$inlined);
                    return -1L;
                }
            }, 0L);
        }

        @Override
        public void windowUpdate(int n, long l) {
            if (n == 0) {
                Http2Connection http2Connection = Http2Connection.this;
                synchronized (http2Connection) {
                    Object object = Http2Connection.this;
                    Http2Connection.access$setWriteBytesMaximum$p((Http2Connection)object, ((Http2Connection)object).getWriteBytesMaximum() + l);
                    object = Http2Connection.this;
                    if (object != null) {
                        ((Object)object).notifyAll();
                        object = Unit.INSTANCE;
                        return;
                    }
                    object = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    throw object;
                }
            }
            Http2Stream http2Stream = Http2Connection.this.getStream(n);
            if (http2Stream == null) return;
            synchronized (http2Stream) {
                http2Stream.addBytesToWriteWindow(l);
                Unit unit = Unit.INSTANCE;
                return;
            }
        }
    }

}

