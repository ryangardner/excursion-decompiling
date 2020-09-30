/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import okhttp3.internal.Util;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Hpack;
import okhttp3.internal.http2.Http2;
import okhttp3.internal.http2.Settings;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 #2\u00020\u0001:\u0003#$%B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0016\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010J(\u0010\u0012\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\u0017\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J.\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\u001c\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\u001d\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J\u0018\u0010\u001e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\u001e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\u001f\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010 \u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010!\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002J(\u0010\"\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2={"Lokhttp3/internal/http2/Http2Reader;", "Ljava/io/Closeable;", "source", "Lokio/BufferedSource;", "client", "", "(Lokio/BufferedSource;Z)V", "continuation", "Lokhttp3/internal/http2/Http2Reader$ContinuationSource;", "hpackReader", "Lokhttp3/internal/http2/Hpack$Reader;", "close", "", "nextFrame", "requireSettings", "handler", "Lokhttp3/internal/http2/Http2Reader$Handler;", "readConnectionPreface", "readData", "length", "", "flags", "streamId", "readGoAway", "readHeaderBlock", "", "Lokhttp3/internal/http2/Header;", "padding", "readHeaders", "readPing", "readPriority", "readPushPromise", "readRstStream", "readSettings", "readWindowUpdate", "Companion", "ContinuationSource", "Handler", "okhttp"}, k=1, mv={1, 1, 16})
public final class Http2Reader
implements Closeable {
    public static final Companion Companion = new Companion(null);
    private static final Logger logger;
    private final boolean client;
    private final ContinuationSource continuation;
    private final Hpack.Reader hpackReader;
    private final BufferedSource source;

    static {
        Logger logger = Logger.getLogger(Http2.class.getName());
        Intrinsics.checkExpressionValueIsNotNull(logger, "Logger.getLogger(Http2::class.java.name)");
        Http2Reader.logger = logger;
    }

    public Http2Reader(BufferedSource bufferedSource, boolean bl) {
        Intrinsics.checkParameterIsNotNull(bufferedSource, "source");
        this.source = bufferedSource;
        this.client = bl;
        this.continuation = new ContinuationSource(this.source);
        this.hpackReader = new Hpack.Reader(this.continuation, 4096, 0, 4, null);
    }

    private final void readData(Handler handler, int n, int n2, int n3) throws IOException {
        if (n3 == 0) throw (Throwable)new IOException("PROTOCOL_ERROR: TYPE_DATA streamId == 0");
        int n4 = 0;
        int n5 = 1;
        boolean bl = (n2 & 1) != 0;
        if ((n2 & 32) == 0) {
            n5 = 0;
        }
        if (n5 != 0) throw (Throwable)new IOException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA");
        n5 = n4;
        if ((n2 & 8) != 0) {
            n5 = Util.and(this.source.readByte(), 255);
        }
        n = Companion.lengthWithoutPadding(n, n2, n5);
        handler.data(bl, n3, this.source, n);
        this.source.skip(n5);
    }

    private final void readGoAway(Handler object, int n, int n2, int n3) throws IOException {
        if (n < 8) {
            object = new StringBuilder();
            ((StringBuilder)object).append("TYPE_GOAWAY length < 8: ");
            ((StringBuilder)object).append(n);
            throw (Throwable)new IOException(((StringBuilder)object).toString());
        }
        if (n3 != 0) throw (Throwable)new IOException("TYPE_GOAWAY streamId != 0");
        n2 = this.source.readInt();
        n3 = this.source.readInt();
        n -= 8;
        ErrorCode errorCode = ErrorCode.Companion.fromHttp2(n3);
        if (errorCode == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("TYPE_GOAWAY unexpected error code: ");
            ((StringBuilder)object).append(n3);
            throw (Throwable)new IOException(((StringBuilder)object).toString());
        }
        ByteString byteString = ByteString.EMPTY;
        if (n > 0) {
            byteString = this.source.readByteString(n);
        }
        object.goAway(n2, errorCode, byteString);
    }

    private final List<Header> readHeaderBlock(int n, int n2, int n3, int n4) throws IOException {
        this.continuation.setLeft(n);
        ContinuationSource continuationSource = this.continuation;
        continuationSource.setLength(continuationSource.getLeft());
        this.continuation.setPadding(n2);
        this.continuation.setFlags(n3);
        this.continuation.setStreamId(n4);
        this.hpackReader.readHeaders();
        return this.hpackReader.getAndResetHeaderList();
    }

    private final void readHeaders(Handler handler, int n, int n2, int n3) throws IOException {
        if (n3 == 0) throw (Throwable)new IOException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0");
        int n4 = 0;
        boolean bl = (n2 & 1) != 0;
        if ((n2 & 8) != 0) {
            n4 = Util.and(this.source.readByte(), 255);
        }
        int n5 = n;
        if ((n2 & 32) != 0) {
            this.readPriority(handler, n3);
            n5 = n - 5;
        }
        handler.headers(bl, n3, -1, this.readHeaderBlock(Companion.lengthWithoutPadding(n5, n2, n4), n4, n2, n3));
    }

    private final void readPing(Handler object, int n, int n2, int n3) throws IOException {
        if (n != 8) {
            object = new StringBuilder();
            ((StringBuilder)object).append("TYPE_PING length != 8: ");
            ((StringBuilder)object).append(n);
            throw (Throwable)new IOException(((StringBuilder)object).toString());
        }
        if (n3 != 0) throw (Throwable)new IOException("TYPE_PING streamId != 0");
        n3 = this.source.readInt();
        n = this.source.readInt();
        boolean bl = true;
        if ((n2 & 1) == 0) {
            bl = false;
        }
        object.ping(bl, n3, n);
    }

    private final void readPriority(Handler handler, int n) throws IOException {
        int n2 = this.source.readInt();
        boolean bl = (n2 & (int)0x80000000L) != 0;
        handler.priority(n, n2 & Integer.MAX_VALUE, Util.and(this.source.readByte(), 255) + 1, bl);
    }

    private final void readPriority(Handler object, int n, int n2, int n3) throws IOException {
        if (n == 5) {
            if (n3 == 0) throw (Throwable)new IOException("TYPE_PRIORITY streamId == 0");
            this.readPriority((Handler)object, n3);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("TYPE_PRIORITY length: ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" != 5");
        throw (Throwable)new IOException(((StringBuilder)object).toString());
    }

    private final void readPushPromise(Handler handler, int n, int n2, int n3) throws IOException {
        if (n3 == 0) throw (Throwable)new IOException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0");
        int n4 = (n2 & 8) != 0 ? Util.and(this.source.readByte(), 255) : 0;
        handler.pushPromise(n3, this.source.readInt() & Integer.MAX_VALUE, this.readHeaderBlock(Companion.lengthWithoutPadding(n - 4, n2, n4), n4, n2, n3));
    }

    private final void readRstStream(Handler object, int n, int n2, int n3) throws IOException {
        if (n != 4) {
            object = new StringBuilder();
            ((StringBuilder)object).append("TYPE_RST_STREAM length: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" != 4");
            throw (Throwable)new IOException(((StringBuilder)object).toString());
        }
        if (n3 == 0) throw (Throwable)new IOException("TYPE_RST_STREAM streamId == 0");
        n = this.source.readInt();
        ErrorCode errorCode = ErrorCode.Companion.fromHttp2(n);
        if (errorCode != null) {
            object.rstStream(n3, errorCode);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("TYPE_RST_STREAM unexpected error code: ");
        ((StringBuilder)object).append(n);
        throw (Throwable)new IOException(((StringBuilder)object).toString());
    }

    /*
     * Unable to fully structure code
     */
    private final void readSettings(Handler var1_1, int var2_2, int var3_3, int var4_4) throws IOException {
        block8 : {
            if (var4_4 != 0) throw (Throwable)new IOException("TYPE_SETTINGS streamId != 0");
            if ((var3_3 & 1) != 0) {
                if (var2_2 != 0) throw (Throwable)new IOException("FRAME_SIZE_ERROR ack frame should be empty!");
                var1_1.ackSettings();
                return;
            }
            if (var2_2 % 6 != 0) {
                var1_1 = new StringBuilder();
                var1_1.append("TYPE_SETTINGS length % 6 != 0: ");
                var1_1.append(var2_2);
                throw (Throwable)new IOException(var1_1.toString());
            }
            var5_5 = new Settings();
            var6_6 = RangesKt.step(RangesKt.until(0, var2_2), 6);
            var3_3 = var6_6.getFirst();
            var7_7 = var6_6.getLast();
            var8_8 = var6_6.getStep();
            if (!(var8_8 >= 0 ? var3_3 <= var7_7 : var3_3 >= var7_7)) break block8;
            do {
                block11 : {
                    block9 : {
                        block10 : {
                            var4_4 = Util.and(this.source.readShort(), 65535);
                            var9_9 = this.source.readInt();
                            if (var4_4 == 2) break block9;
                            if (var4_4 == 3) break block10;
                            if (var4_4 == 4) ** GOTO lbl37
                            if (var4_4 != 5) {
                                var2_2 = var4_4;
                            } else if (var9_9 >= 16384 && var9_9 <= 16777215) {
                                var2_2 = var4_4;
                            } else {
                                var1_1 = new StringBuilder();
                                var1_1.append("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: ");
                                var1_1.append(var9_9);
                                throw (Throwable)new IOException(var1_1.toString());
lbl37: // 1 sources:
                                var2_2 = 7;
                                if (var9_9 < 0) throw (Throwable)new IOException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1");
                            }
                            break block11;
                        }
                        var2_2 = 4;
                        break block11;
                    }
                    var2_2 = var4_4;
                    if (var9_9 != 0) {
                        if (var9_9 != 1) throw (Throwable)new IOException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1");
                        var2_2 = var4_4;
                    }
                }
                var5_5.set(var2_2, var9_9);
                if (var3_3 == var7_7) break;
                var3_3 += var8_8;
            } while (true);
        }
        var1_1.settings(false, var5_5);
    }

    private final void readWindowUpdate(Handler object, int n, int n2, int n3) throws IOException {
        if (n == 4) {
            long l = Util.and(this.source.readInt(), Integer.MAX_VALUE);
            if (l == 0L) throw (Throwable)new IOException("windowSizeIncrement was 0");
            object.windowUpdate(n3, l);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("TYPE_WINDOW_UPDATE length !=4: ");
        ((StringBuilder)object).append(n);
        throw (Throwable)new IOException(((StringBuilder)object).toString());
    }

    @Override
    public void close() throws IOException {
        this.source.close();
    }

    public final boolean nextFrame(boolean bl, Handler object) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "handler");
        try {
            this.source.require(9L);
        }
        catch (EOFException eOFException) {
            return false;
        }
        int n = Util.readMedium(this.source);
        if (n > 16384) {
            object = new StringBuilder();
            ((StringBuilder)object).append("FRAME_SIZE_ERROR: ");
            ((StringBuilder)object).append(n);
            throw (Throwable)new IOException(((StringBuilder)object).toString());
        }
        int n2 = Util.and(this.source.readByte(), 255);
        int n3 = Util.and(this.source.readByte(), 255);
        int n4 = this.source.readInt() & Integer.MAX_VALUE;
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(Http2.INSTANCE.frameLog(true, n4, n, n2, n3));
        }
        if (bl && n2 != 4) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Expected a SETTINGS frame but was ");
            ((StringBuilder)object).append(Http2.INSTANCE.formattedType$okhttp(n2));
            throw (Throwable)new IOException(((StringBuilder)object).toString());
        }
        switch (n2) {
            default: {
                this.source.skip(n);
                return true;
            }
            case 8: {
                this.readWindowUpdate((Handler)object, n, n3, n4);
                return true;
            }
            case 7: {
                this.readGoAway((Handler)object, n, n3, n4);
                return true;
            }
            case 6: {
                this.readPing((Handler)object, n, n3, n4);
                return true;
            }
            case 5: {
                this.readPushPromise((Handler)object, n, n3, n4);
                return true;
            }
            case 4: {
                this.readSettings((Handler)object, n, n3, n4);
                return true;
            }
            case 3: {
                this.readRstStream((Handler)object, n, n3, n4);
                return true;
            }
            case 2: {
                this.readPriority((Handler)object, n, n3, n4);
                return true;
            }
            case 1: {
                this.readHeaders((Handler)object, n, n3, n4);
                return true;
            }
            case 0: 
        }
        this.readData((Handler)object, n, n3, n4);
        return true;
    }

    public final void readConnectionPreface(Handler object) throws IOException {
        Object object2;
        Intrinsics.checkParameterIsNotNull(object, "handler");
        if (this.client) {
            if (!this.nextFrame(true, (Handler)object)) throw (Throwable)new IOException("Required SETTINGS preface not received");
            return;
        }
        object = this.source.readByteString(Http2.CONNECTION_PREFACE.size());
        if (logger.isLoggable(Level.FINE)) {
            object2 = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<< CONNECTION ");
            stringBuilder.append(((ByteString)object).hex());
            ((Logger)object2).fine(Util.format(stringBuilder.toString(), new Object[0]));
        }
        if (!(Intrinsics.areEqual(Http2.CONNECTION_PREFACE, object) ^ true)) {
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Expected a connection header but was ");
        ((StringBuilder)object2).append(((ByteString)object).utf8());
        throw (Throwable)new IOException(((StringBuilder)object2).toString());
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\f"}, d2={"Lokhttp3/internal/http2/Http2Reader$Companion;", "", "()V", "logger", "Ljava/util/logging/Logger;", "getLogger", "()Ljava/util/logging/Logger;", "lengthWithoutPadding", "", "length", "flags", "padding", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Logger getLogger() {
            return logger;
        }

        public final int lengthWithoutPadding(int n, int n2, int n3) throws IOException {
            int n4 = n;
            if ((n2 & 8) != 0) {
                n4 = n - 1;
            }
            if (n3 <= n4) {
                return n4 - n3;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PROTOCOL_ERROR padding ");
            stringBuilder.append(n3);
            stringBuilder.append(" > remaining length ");
            stringBuilder.append(n4);
            throw (Throwable)new IOException(stringBuilder.toString());
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001aH\u0016J\b\u0010\u001e\u001a\u00020\u0018H\u0002J\b\u0010\u001f\u001a\u00020 H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\nR\u001a\u0010\u0011\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\n\u00a8\u0006!"}, d2={"Lokhttp3/internal/http2/Http2Reader$ContinuationSource;", "Lokio/Source;", "source", "Lokio/BufferedSource;", "(Lokio/BufferedSource;)V", "flags", "", "getFlags", "()I", "setFlags", "(I)V", "left", "getLeft", "setLeft", "length", "getLength", "setLength", "padding", "getPadding", "setPadding", "streamId", "getStreamId", "setStreamId", "close", "", "read", "", "sink", "Lokio/Buffer;", "byteCount", "readContinuationHeader", "timeout", "Lokio/Timeout;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class ContinuationSource
    implements Source {
        private int flags;
        private int left;
        private int length;
        private int padding;
        private final BufferedSource source;
        private int streamId;

        public ContinuationSource(BufferedSource bufferedSource) {
            Intrinsics.checkParameterIsNotNull(bufferedSource, "source");
            this.source = bufferedSource;
        }

        private final void readContinuationHeader() throws IOException {
            int n;
            int n2;
            int n3 = this.streamId;
            this.left = n = Util.readMedium(this.source);
            this.length = n;
            n = Util.and(this.source.readByte(), 255);
            this.flags = Util.and(this.source.readByte(), 255);
            if (Companion.getLogger().isLoggable(Level.FINE)) {
                Companion.getLogger().fine(Http2.INSTANCE.frameLog(true, this.streamId, this.length, n, this.flags));
            }
            this.streamId = n2 = this.source.readInt() & Integer.MAX_VALUE;
            if (n == 9) {
                if (n2 != n3) throw (Throwable)new IOException("TYPE_CONTINUATION streamId changed");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append(" != TYPE_CONTINUATION");
            throw (Throwable)new IOException(stringBuilder.toString());
        }

        @Override
        public void close() throws IOException {
        }

        public final int getFlags() {
            return this.flags;
        }

        public final int getLeft() {
            return this.left;
        }

        public final int getLength() {
            return this.length;
        }

        public final int getPadding() {
            return this.padding;
        }

        public final int getStreamId() {
            return this.streamId;
        }

        @Override
        public long read(Buffer buffer, long l) throws IOException {
            int n;
            Intrinsics.checkParameterIsNotNull(buffer, "sink");
            while ((n = this.left) == 0) {
                this.source.skip(this.padding);
                this.padding = 0;
                if ((this.flags & 4) != 0) {
                    return -1L;
                }
                this.readContinuationHeader();
            }
            if ((l = this.source.read(buffer, Math.min(l, (long)n))) == -1L) {
                return -1L;
            }
            this.left -= (int)l;
            return l;
        }

        public final void setFlags(int n) {
            this.flags = n;
        }

        public final void setLeft(int n) {
            this.left = n;
        }

        public final void setLength(int n) {
            this.length = n;
        }

        public final void setPadding(int n) {
            this.padding = n;
        }

        public final void setStreamId(int n) {
            this.streamId = n;
        }

        @Override
        public Timeout timeout() {
            return this.source.timeout();
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J8\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH&J(\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0006H&J \u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\nH&J.\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00062\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH&J \u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006H&J(\u0010#\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\u0011H&J&\u0010'\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH&J\u0018\u0010*\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H&J\u0018\u0010+\u001a\u00020\u00032\u0006\u0010,\u001a\u00020\u00112\u0006\u0010+\u001a\u00020-H&J\u0018\u0010.\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u000eH&\u00a8\u00060"}, d2={"Lokhttp3/internal/http2/Http2Reader$Handler;", "", "ackSettings", "", "alternateService", "streamId", "", "origin", "", "protocol", "Lokio/ByteString;", "host", "port", "maxAge", "", "data", "inFinished", "", "source", "Lokio/BufferedSource;", "length", "goAway", "lastGoodStreamId", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "debugData", "headers", "associatedStreamId", "headerBlock", "", "Lokhttp3/internal/http2/Header;", "ping", "ack", "payload1", "payload2", "priority", "streamDependency", "weight", "exclusive", "pushPromise", "promisedStreamId", "requestHeaders", "rstStream", "settings", "clearPrevious", "Lokhttp3/internal/http2/Settings;", "windowUpdate", "windowSizeIncrement", "okhttp"}, k=1, mv={1, 1, 16})
    public static interface Handler {
        public void ackSettings();

        public void alternateService(int var1, String var2, ByteString var3, String var4, int var5, long var6);

        public void data(boolean var1, int var2, BufferedSource var3, int var4) throws IOException;

        public void goAway(int var1, ErrorCode var2, ByteString var3);

        public void headers(boolean var1, int var2, int var3, List<Header> var4);

        public void ping(boolean var1, int var2, int var3);

        public void priority(int var1, int var2, int var3, boolean var4);

        public void pushPromise(int var1, int var2, List<Header> var3) throws IOException;

        public void rstStream(int var1, ErrorCode var2);

        public void settings(boolean var1, Settings var2);

        public void windowUpdate(int var1, long var2);
    }

}

