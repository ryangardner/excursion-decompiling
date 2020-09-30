/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Settings;
import okhttp3.internal.http2.StreamResetException;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 _2\u00020\u0001:\u0004_`abB1\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020#J\r\u0010C\u001a\u00020AH\u0000\u00a2\u0006\u0002\bDJ\r\u0010E\u001a\u00020AH\u0000\u00a2\u0006\u0002\bFJ\u0018\u0010G\u001a\u00020A2\u0006\u0010H\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u001a\u0010I\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J\u000e\u0010J\u001a\u00020A2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010K\u001a\u00020A2\u0006\u0010L\u001a\u00020\nJ\u0006\u0010M\u001a\u00020NJ\u0006\u0010O\u001a\u00020PJ\u0006\u0010,\u001a\u00020QJ\u0016\u0010R\u001a\u00020A2\u0006\u00104\u001a\u00020S2\u0006\u0010T\u001a\u00020\u0003J\u0016\u0010U\u001a\u00020A2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010V\u001a\u00020A2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010W\u001a\u00020\nJ\u0006\u0010L\u001a\u00020\nJ\r\u0010X\u001a\u00020AH\u0000\u00a2\u0006\u0002\bYJ$\u0010Z\u001a\u00020A2\f\u0010[\u001a\b\u0012\u0004\u0012\u00020]0\\2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010^\u001a\u00020\u0007J\u0006\u0010>\u001a\u00020QR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\u0004\u0018\u00010\u000f8@X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\n0\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b!\u0010 R$\u0010$\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R$\u0010)\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(R\u0018\u0010,\u001a\u00060-R\u00020\u0000X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0018\u00100\u001a\u000601R\u00020\u0000X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0018\u00104\u001a\u000605R\u00020\u0000X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u00107R$\u00108\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u0010&\"\u0004\b:\u0010(R$\u0010;\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@@X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010&\"\u0004\b=\u0010(R\u0018\u0010>\u001a\u00060-R\u00020\u0000X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010/\u00a8\u0006c"}, d2={"Lokhttp3/internal/http2/Http2Stream;", "", "id", "", "connection", "Lokhttp3/internal/http2/Http2Connection;", "outFinished", "", "inFinished", "headers", "Lokhttp3/Headers;", "(ILokhttp3/internal/http2/Http2Connection;ZZLokhttp3/Headers;)V", "getConnection", "()Lokhttp3/internal/http2/Http2Connection;", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "getErrorCode$okhttp", "()Lokhttp3/internal/http2/ErrorCode;", "setErrorCode$okhttp", "(Lokhttp3/internal/http2/ErrorCode;)V", "errorException", "Ljava/io/IOException;", "getErrorException$okhttp", "()Ljava/io/IOException;", "setErrorException$okhttp", "(Ljava/io/IOException;)V", "hasResponseHeaders", "headersQueue", "Ljava/util/ArrayDeque;", "getId", "()I", "isLocallyInitiated", "()Z", "isOpen", "<set-?>", "", "readBytesAcknowledged", "getReadBytesAcknowledged", "()J", "setReadBytesAcknowledged$okhttp", "(J)V", "readBytesTotal", "getReadBytesTotal", "setReadBytesTotal$okhttp", "readTimeout", "Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "getReadTimeout$okhttp", "()Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "sink", "Lokhttp3/internal/http2/Http2Stream$FramingSink;", "getSink$okhttp", "()Lokhttp3/internal/http2/Http2Stream$FramingSink;", "source", "Lokhttp3/internal/http2/Http2Stream$FramingSource;", "getSource$okhttp", "()Lokhttp3/internal/http2/Http2Stream$FramingSource;", "writeBytesMaximum", "getWriteBytesMaximum", "setWriteBytesMaximum$okhttp", "writeBytesTotal", "getWriteBytesTotal", "setWriteBytesTotal$okhttp", "writeTimeout", "getWriteTimeout$okhttp", "addBytesToWriteWindow", "", "delta", "cancelStreamIfNecessary", "cancelStreamIfNecessary$okhttp", "checkOutNotClosed", "checkOutNotClosed$okhttp", "close", "rstStatusCode", "closeInternal", "closeLater", "enqueueTrailers", "trailers", "getSink", "Lokio/Sink;", "getSource", "Lokio/Source;", "Lokio/Timeout;", "receiveData", "Lokio/BufferedSource;", "length", "receiveHeaders", "receiveRstStream", "takeHeaders", "waitForIo", "waitForIo$okhttp", "writeHeaders", "responseHeaders", "", "Lokhttp3/internal/http2/Header;", "flushHeaders", "Companion", "FramingSink", "FramingSource", "StreamTimeout", "okhttp"}, k=1, mv={1, 1, 16})
public final class Http2Stream {
    public static final Companion Companion = new Companion(null);
    public static final long EMIT_BUFFER_SIZE = 16384L;
    private final Http2Connection connection;
    private ErrorCode errorCode;
    private IOException errorException;
    private boolean hasResponseHeaders;
    private final ArrayDeque<Headers> headersQueue;
    private final int id;
    private long readBytesAcknowledged;
    private long readBytesTotal;
    private final StreamTimeout readTimeout;
    private final FramingSink sink;
    private final FramingSource source;
    private long writeBytesMaximum;
    private long writeBytesTotal;
    private final StreamTimeout writeTimeout;

    public Http2Stream(int n, Http2Connection http2Connection, boolean bl, boolean bl2, Headers headers) {
        Intrinsics.checkParameterIsNotNull(http2Connection, "connection");
        this.id = n;
        this.connection = http2Connection;
        this.writeBytesMaximum = http2Connection.getPeerSettings().getInitialWindowSize();
        this.headersQueue = new ArrayDeque();
        this.source = new FramingSource(this.connection.getOkHttpSettings().getInitialWindowSize(), bl2);
        this.sink = new FramingSink(this, bl);
        this.readTimeout = new StreamTimeout();
        this.writeTimeout = new StreamTimeout();
        if (headers != null) {
            if (!(this.isLocallyInitiated() ^ true)) throw (Throwable)new IllegalStateException("locally-initiated streams shouldn't have headers yet".toString());
            ((Collection)this.headersQueue).add(headers);
            return;
        }
        if (!this.isLocallyInitiated()) throw (Throwable)new IllegalStateException("remotely-initiated streams should have headers".toString());
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private final boolean closeInternal(ErrorCode object, IOException object2) {
        Thread thread2;
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Thread ");
            thread2 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
            ((StringBuilder)object).append(thread2.getName());
            ((StringBuilder)object).append(" MUST NOT hold lock on ");
            ((StringBuilder)object).append(this);
            throw (Throwable)((Object)new AssertionError((Object)((StringBuilder)object).toString()));
        }
        synchronized (this) {
            boolean bl;
            ErrorCode errorCode = this.errorCode;
            if (errorCode != null) {
                return false;
            }
            if (this.source.getFinished$okhttp() && (bl = this.sink.getFinished())) {
                return false;
            }
            this.errorCode = object;
            this.errorException = thread2;
            ((Object)this).notifyAll();
            object = Unit.INSTANCE;
        }
        this.connection.removeStream$okhttp(this.id);
        return true;
    }

    public final void addBytesToWriteWindow(long l) {
        this.writeBytesMaximum += l;
        if (l <= 0L) return;
        ((Object)this).notifyAll();
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public final void cancelStreamIfNecessary$okhttp() throws IOException {
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            Thread thread2 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
            stringBuilder.append(thread2.getName());
            stringBuilder.append(" MUST NOT hold lock on ");
            stringBuilder.append(this);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        // MONITORENTER : this
        boolean bl = !this.source.getFinished$okhttp() && this.source.getClosed$okhttp() && (this.sink.getFinished() || this.sink.getClosed());
        boolean bl2 = this.isOpen();
        Unit unit = Unit.INSTANCE;
        // MONITOREXIT : this
        if (bl) {
            this.close(ErrorCode.CANCEL, null);
            return;
        }
        if (bl2) return;
        this.connection.removeStream$okhttp(this.id);
    }

    public final void checkOutNotClosed$okhttp() throws IOException {
        IOException iOException;
        if (this.sink.getClosed()) throw (Throwable)new IOException("stream closed");
        if (this.sink.getFinished()) throw (Throwable)new IOException("stream finished");
        if (this.errorCode == null) return;
        Object object = iOException = this.errorException;
        if (iOException != null) throw (Throwable)object;
        object = this.errorCode;
        if (object == null) {
            Intrinsics.throwNpe();
        }
        object = new StreamResetException((ErrorCode)((Object)object));
        throw (Throwable)object;
    }

    public final void close(ErrorCode errorCode, IOException iOException) throws IOException {
        Intrinsics.checkParameterIsNotNull((Object)errorCode, "rstStatusCode");
        if (!this.closeInternal(errorCode, iOException)) {
            return;
        }
        this.connection.writeSynReset$okhttp(this.id, errorCode);
    }

    public final void closeLater(ErrorCode errorCode) {
        Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
        if (!this.closeInternal(errorCode, null)) {
            return;
        }
        this.connection.writeSynResetLater$okhttp(this.id, errorCode);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void enqueueTrailers(Headers object) {
        Intrinsics.checkParameterIsNotNull(object, "trailers");
        synchronized (this) {
            boolean bl = this.sink.getFinished();
            boolean bl2 = true;
            if (!(bl ^ true)) {
                object = new IllegalStateException("already finished".toString());
                throw (Throwable)object;
            }
            if (((Headers)object).size() == 0) {
                bl2 = false;
            }
            if (bl2) {
                this.sink.setTrailers((Headers)object);
                object = Unit.INSTANCE;
                return;
            }
            object = new IllegalArgumentException("trailers.size() == 0".toString());
            throw (Throwable)object;
        }
    }

    public final Http2Connection getConnection() {
        return this.connection;
    }

    public final ErrorCode getErrorCode$okhttp() {
        synchronized (this) {
            return this.errorCode;
        }
    }

    public final IOException getErrorException$okhttp() {
        return this.errorException;
    }

    public final int getId() {
        return this.id;
    }

    public final long getReadBytesAcknowledged() {
        return this.readBytesAcknowledged;
    }

    public final long getReadBytesTotal() {
        return this.readBytesTotal;
    }

    public final StreamTimeout getReadTimeout$okhttp() {
        return this.readTimeout;
    }

    public final Sink getSink() {
        synchronized (this) {
            block4 : {
                boolean bl = this.hasResponseHeaders || this.isLocallyInitiated();
                if (!bl) break block4;
                Unit unit = Unit.INSTANCE;
                return this.sink;
            }
            IllegalStateException illegalStateException = new IllegalStateException("reply before requesting the sink".toString());
            throw (Throwable)illegalStateException;
        }
    }

    public final FramingSink getSink$okhttp() {
        return this.sink;
    }

    public final Source getSource() {
        return this.source;
    }

    public final FramingSource getSource$okhttp() {
        return this.source;
    }

    public final long getWriteBytesMaximum() {
        return this.writeBytesMaximum;
    }

    public final long getWriteBytesTotal() {
        return this.writeBytesTotal;
    }

    public final StreamTimeout getWriteTimeout$okhttp() {
        return this.writeTimeout;
    }

    public final boolean isLocallyInitiated() {
        int n = this.id;
        boolean bl = true;
        boolean bl2 = (n & 1) == 1;
        if (this.connection.getClient$okhttp() != bl2) return false;
        return bl;
    }

    public final boolean isOpen() {
        synchronized (this) {
            boolean bl;
            ErrorCode errorCode = this.errorCode;
            if (errorCode != null) {
                return false;
            }
            if (!this.source.getFinished$okhttp()) {
                if (!this.source.getClosed$okhttp()) return true;
            }
            if (!this.sink.getFinished()) {
                if (!this.sink.getClosed()) return true;
            }
            if (!(bl = this.hasResponseHeaders)) return true;
            return false;
        }
    }

    public final Timeout readTimeout() {
        return this.readTimeout;
    }

    public final void receiveData(BufferedSource object, int n) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "source");
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            object = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(object, "Thread.currentThread()");
            stringBuilder.append(((Thread)object).getName());
            stringBuilder.append(" MUST NOT hold lock on ");
            stringBuilder.append(this);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        this.source.receive$okhttp((BufferedSource)object, n);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void receiveHeaders(Headers object, boolean bl) {
        boolean bl2;
        Intrinsics.checkParameterIsNotNull(object, "headers");
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            object = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(object, "Thread.currentThread()");
            stringBuilder.append(((Thread)object).getName());
            stringBuilder.append(" MUST NOT hold lock on ");
            stringBuilder.append(this);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        synchronized (this) {
            if (this.hasResponseHeaders && bl2) {
                this.source.setTrailers((Headers)object);
            } else {
                this.hasResponseHeaders = true;
                ((Collection)this.headersQueue).add(object);
            }
            if (bl2) {
                this.source.setFinished$okhttp(true);
            }
            bl2 = this.isOpen();
            ((Object)this).notifyAll();
            object = Unit.INSTANCE;
        }
        if (bl2) return;
        this.connection.removeStream$okhttp(this.id);
    }

    public final void receiveRstStream(ErrorCode errorCode) {
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull((Object)errorCode, "errorCode");
            if (this.errorCode != null) return;
            this.errorCode = errorCode;
            ((Object)this).notifyAll();
            return;
        }
    }

    public final void setErrorCode$okhttp(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public final void setErrorException$okhttp(IOException iOException) {
        this.errorException = iOException;
    }

    public final void setReadBytesAcknowledged$okhttp(long l) {
        this.readBytesAcknowledged = l;
    }

    public final void setReadBytesTotal$okhttp(long l) {
        this.readBytesTotal = l;
    }

    public final void setWriteBytesMaximum$okhttp(long l) {
        this.writeBytesMaximum = l;
    }

    public final void setWriteBytesTotal$okhttp(long l) {
        this.writeBytesTotal = l;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final Headers takeHeaders() throws IOException {
        synchronized (this) {
            Object object;
            block8 : {
                this.readTimeout.enter();
                while (this.headersQueue.isEmpty() && this.errorCode == null) {
                    this.waitForIo$okhttp();
                }
                if (!(((Collection)this.headersQueue).isEmpty() ^ true)) break block8;
                Headers headers = this.headersQueue.removeFirst();
                Intrinsics.checkExpressionValueIsNotNull(headers, "headersQueue.removeFirst()");
                return headers;
            }
            IOException iOException = object = this.errorException;
            if (object != null) throw (Throwable)iOException;
            object = this.errorCode;
            if (object == null) {
                Intrinsics.throwNpe();
            }
            iOException = new StreamResetException((ErrorCode)((Object)object));
            throw (Throwable)iOException;
            finally {
                this.readTimeout.exitAndThrowIfTimedOut();
            }
        }
    }

    public final Headers trailers() throws IOException {
        synchronized (this) {
            if (this.errorCode != null) {
                Object object;
                IOException iOException = object = this.errorException;
                if (object != null) throw (Throwable)iOException;
                object = this.errorCode;
                if (object == null) {
                    Intrinsics.throwNpe();
                }
                iOException = new StreamResetException((ErrorCode)((Object)object));
                throw (Throwable)iOException;
            }
            boolean bl = this.source.getFinished$okhttp() && this.source.getReceiveBuffer().exhausted() && this.source.getReadBuffer().exhausted();
            if (!bl) {
                IllegalStateException illegalStateException = new IllegalStateException("too early; can't read the trailers yet".toString());
                throw (Throwable)illegalStateException;
            }
            Headers headers = this.source.getTrailers();
            if (headers == null) return Util.EMPTY_HEADERS;
            return headers;
        }
    }

    public final void waitForIo$okhttp() throws InterruptedIOException {
        try {
            ((Object)this).wait();
            return;
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw (Throwable)new InterruptedIOException();
        }
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public final void writeHeaders(List<Header> object, boolean bl, boolean bl2) throws IOException {
        Object object2;
        void var2_2;
        boolean bl3;
        boolean bl4;
        Intrinsics.checkParameterIsNotNull(object, "responseHeaders");
        if (Util.assertionsEnabled && Thread.holdsLock(this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            object = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(object, "Thread.currentThread()");
            stringBuilder.append(((Thread)object).getName());
            stringBuilder.append(" MUST NOT hold lock on ");
            stringBuilder.append(this);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        synchronized (this) {
            bl4 = true;
            this.hasResponseHeaders = true;
            if (var2_2 != false) {
                this.sink.setFinished(true);
            }
            object2 = Unit.INSTANCE;
        }
        boolean bl5 = bl3;
        if (!bl3) {
            object2 = this.connection;
            synchronized (object2) {
                bl3 = this.connection.getWriteBytesTotal() >= this.connection.getWriteBytesMaximum() ? bl4 : false;
                Unit unit = Unit.INSTANCE;
            }
            bl5 = bl3;
        }
        this.connection.writeHeaders$okhttp(this.id, (boolean)var2_2, (List<Header>)object);
        if (!bl5) return;
        this.connection.flush();
    }

    public final Timeout writeTimeout() {
        return this.writeTimeout;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"Lokhttp3/internal/http2/Http2Stream$Companion;", "", "()V", "EMIT_BUFFER_SIZE", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0003H\u0002J\b\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0018\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001f"}, d2={"Lokhttp3/internal/http2/Http2Stream$FramingSink;", "Lokio/Sink;", "finished", "", "(Lokhttp3/internal/http2/Http2Stream;Z)V", "closed", "getClosed", "()Z", "setClosed", "(Z)V", "getFinished", "setFinished", "sendBuffer", "Lokio/Buffer;", "trailers", "Lokhttp3/Headers;", "getTrailers", "()Lokhttp3/Headers;", "setTrailers", "(Lokhttp3/Headers;)V", "close", "", "emitFrame", "outFinishedOnLastFrame", "flush", "timeout", "Lokio/Timeout;", "write", "source", "byteCount", "", "okhttp"}, k=1, mv={1, 1, 16})
    public final class FramingSink
    implements Sink {
        private boolean closed;
        private boolean finished;
        private final Buffer sendBuffer;
        final /* synthetic */ Http2Stream this$0;
        private Headers trailers;

        public FramingSink(Http2Stream http2Stream, boolean bl) {
            this.this$0 = http2Stream;
            this.finished = bl;
            this.sendBuffer = new Buffer();
        }

        public /* synthetic */ FramingSink(Http2Stream http2Stream, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 1) != 0) {
                bl = false;
            }
            this(http2Stream, bl);
        }

        /*
         * Enabled unnecessary exception pruning
         */
        private final void emitFrame(boolean bl) throws IOException {
            long l;
            Http2Stream http2Stream = this.this$0;
            synchronized (http2Stream) {
                this.this$0.getWriteTimeout$okhttp().enter();
                while (this.this$0.getWriteBytesTotal() >= this.this$0.getWriteBytesMaximum() && !this.finished && !this.closed && this.this$0.getErrorCode$okhttp() == null) {
                    this.this$0.waitForIo$okhttp();
                }
                this.this$0.checkOutNotClosed$okhttp();
                l = Math.min(this.this$0.getWriteBytesMaximum() - this.this$0.getWriteBytesTotal(), this.sendBuffer.size());
                Object object = this.this$0;
                ((Http2Stream)object).setWriteBytesTotal$okhttp(((Http2Stream)object).getWriteBytesTotal() + l);
                bl = bl && l == this.sendBuffer.size() && this.this$0.getErrorCode$okhttp() == null;
                object = Unit.INSTANCE;
            }
            this.this$0.getWriteTimeout$okhttp().enter();
            try {
                this.this$0.getConnection().writeData(this.this$0.getId(), bl, this.sendBuffer, l);
                return;
            }
            finally {
                this.this$0.getWriteTimeout$okhttp().exitAndThrowIfTimedOut();
            }
            {
                finally {
                    this.this$0.getWriteTimeout$okhttp().exitAndThrowIfTimedOut();
                }
            }
        }

        /*
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        @Override
        public void close() throws IOException {
            Http2Stream http2Stream = this.this$0;
            if (Util.assertionsEnabled && Thread.holdsLock(http2Stream)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Thread ");
                Thread thread2 = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
                stringBuilder.append(thread2.getName());
                stringBuilder.append(" MUST NOT hold lock on ");
                stringBuilder.append(http2Stream);
                throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
            }
            Object object = this.this$0;
            // MONITORENTER : object
            boolean bl = this.closed;
            if (bl) {
                // MONITOREXIT : object
                return;
            }
            bl = this.this$0.getErrorCode$okhttp() == null;
            Object object2 = Unit.INSTANCE;
            // MONITOREXIT : object
            if (!this.this$0.getSink$okhttp().finished) {
                int n = this.sendBuffer.size() > 0L ? 1 : 0;
                boolean bl2 = this.trailers != null;
                if (bl2) {
                    while (this.sendBuffer.size() > 0L) {
                        this.emitFrame(false);
                    }
                    object = this.this$0.getConnection();
                    n = this.this$0.getId();
                    object2 = this.trailers;
                    if (object2 == null) {
                        Intrinsics.throwNpe();
                    }
                    ((Http2Connection)object).writeHeaders$okhttp(n, bl, Util.toHeaderList((Headers)object2));
                } else if (n != 0) {
                    while (this.sendBuffer.size() > 0L) {
                        this.emitFrame(true);
                    }
                } else if (bl) {
                    this.this$0.getConnection().writeData(this.this$0.getId(), true, null, 0L);
                }
            }
            object = this.this$0;
            // MONITORENTER : object
            this.closed = true;
            object2 = Unit.INSTANCE;
            // MONITOREXIT : object
            this.this$0.getConnection().flush();
            this.this$0.cancelStreamIfNecessary$okhttp();
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public void flush() throws IOException {
            Object object = this.this$0;
            if (Util.assertionsEnabled && Thread.holdsLock(object)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Thread ");
                Thread thread2 = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
                stringBuilder.append(thread2.getName());
                stringBuilder.append(" MUST NOT hold lock on ");
                stringBuilder.append(object);
                throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
            }
            Http2Stream thread2 = this.this$0;
            synchronized (thread2) {
                this.this$0.checkOutNotClosed$okhttp();
                object = Unit.INSTANCE;
            }
            while (this.sendBuffer.size() > 0L) {
                this.emitFrame(false);
                this.this$0.getConnection().flush();
            }
        }

        public final boolean getClosed() {
            return this.closed;
        }

        public final boolean getFinished() {
            return this.finished;
        }

        public final Headers getTrailers() {
            return this.trailers;
        }

        public final void setClosed(boolean bl) {
            this.closed = bl;
        }

        public final void setFinished(boolean bl) {
            this.finished = bl;
        }

        public final void setTrailers(Headers headers) {
            this.trailers = headers;
        }

        @Override
        public Timeout timeout() {
            return this.this$0.getWriteTimeout$okhttp();
        }

        @Override
        public void write(Buffer object, long l) throws IOException {
            Intrinsics.checkParameterIsNotNull(object, "source");
            Http2Stream http2Stream = this.this$0;
            if (Util.assertionsEnabled && Thread.holdsLock(http2Stream)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Thread ");
                Thread thread2 = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
                ((StringBuilder)object).append(thread2.getName());
                ((StringBuilder)object).append(" MUST NOT hold lock on ");
                ((StringBuilder)object).append(http2Stream);
                throw (Throwable)((Object)new AssertionError((Object)((StringBuilder)object).toString()));
            }
            this.sendBuffer.write((Buffer)object, l);
            while (this.sendBuffer.size() >= 16384L) {
                this.emitFrame(false);
            }
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u0003H\u0016J\u001d\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u0003H\u0000\u00a2\u0006\u0002\b\"J\b\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0003H\u0002R\u001a\u0010\u0007\u001a\u00020\u0005X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019\u00a8\u0006&"}, d2={"Lokhttp3/internal/http2/Http2Stream$FramingSource;", "Lokio/Source;", "maxByteCount", "", "finished", "", "(Lokhttp3/internal/http2/Http2Stream;JZ)V", "closed", "getClosed$okhttp", "()Z", "setClosed$okhttp", "(Z)V", "getFinished$okhttp", "setFinished$okhttp", "readBuffer", "Lokio/Buffer;", "getReadBuffer", "()Lokio/Buffer;", "receiveBuffer", "getReceiveBuffer", "trailers", "Lokhttp3/Headers;", "getTrailers", "()Lokhttp3/Headers;", "setTrailers", "(Lokhttp3/Headers;)V", "close", "", "read", "sink", "byteCount", "receive", "source", "Lokio/BufferedSource;", "receive$okhttp", "timeout", "Lokio/Timeout;", "updateConnectionFlowControl", "okhttp"}, k=1, mv={1, 1, 16})
    public final class FramingSource
    implements Source {
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;
        private final Buffer readBuffer;
        private final Buffer receiveBuffer;
        private Headers trailers;

        public FramingSource(long l, boolean bl) {
            this.maxByteCount = l;
            this.finished = bl;
            this.receiveBuffer = new Buffer();
            this.readBuffer = new Buffer();
        }

        private final void updateConnectionFlowControl(long l) {
            Http2Stream http2Stream = Http2Stream.this;
            if (Util.assertionsEnabled && Thread.holdsLock(http2Stream)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Thread ");
                Thread thread2 = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
                stringBuilder.append(thread2.getName());
                stringBuilder.append(" MUST NOT hold lock on ");
                stringBuilder.append(http2Stream);
                throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
            }
            Http2Stream.this.getConnection().updateConnectionFlowControl$okhttp(l);
        }

        /*
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        @Override
        public void close() throws IOException {
            Http2Stream http2Stream = Http2Stream.this;
            // MONITORENTER : http2Stream
            this.closed = true;
            long l = this.readBuffer.size();
            this.readBuffer.clear();
            Object object = Http2Stream.this;
            if (object == null) {
                object = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                throw object;
            }
            ((Object)object).notifyAll();
            object = Unit.INSTANCE;
            // MONITOREXIT : http2Stream
            if (l > 0L) {
                this.updateConnectionFlowControl(l);
            }
            Http2Stream.this.cancelStreamIfNecessary$okhttp();
        }

        public final boolean getClosed$okhttp() {
            return this.closed;
        }

        public final boolean getFinished$okhttp() {
            return this.finished;
        }

        public final Buffer getReadBuffer() {
            return this.readBuffer;
        }

        public final Buffer getReceiveBuffer() {
            return this.receiveBuffer;
        }

        public final Headers getTrailers() {
            return this.trailers;
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        @Override
        public long read(Buffer var1_1, long var2_4) throws IOException {
            Intrinsics.checkParameterIsNotNull(var1_1, "sink");
            var4_4 = var2_3 >= 0L;
            if (!var4_4) {
                var1_1 = new StringBuilder();
                var1_1.append("byteCount < 0: ");
                var1_1.append((long)var2_3);
                throw (Throwable)new IllegalArgumentException(var1_1.toString().toString());
            }
            do {
                block18 : {
                    var5_5 = null;
                    var6_6 = Http2Stream.this;
                    // MONITORENTER : var6_6
                    Http2Stream.this.getReadTimeout$okhttp().enter();
                    if (Http2Stream.this.getErrorCode$okhttp() != null && (var5_5 = Http2Stream.this.getErrorException$okhttp()) == null) {
                        var7_7 /* !! */  = Http2Stream.this.getErrorCode$okhttp();
                        if (var7_7 /* !! */  == null) {
                            Intrinsics.throwNpe();
                        }
                        var5_5 = new StreamResetException(var7_7 /* !! */ );
                    }
                    if (this.closed) ** break block17
                    if (this.readBuffer.size() <= 0L) break block18;
                    var8_8 = this.readBuffer.read((Buffer)var1_1, Math.min((long)var2_3, this.readBuffer.size()));
                    var7_7 /* !! */  = Http2Stream.this;
                    var7_7 /* !! */ .setReadBytesTotal$okhttp(var7_7 /* !! */ .getReadBytesTotal() + var8_8);
                    var10_9 = Http2Stream.this.getReadBytesTotal() - Http2Stream.this.getReadBytesAcknowledged();
                    var12_10 = var8_8;
                    if (var5_5 == null) {
                        var12_10 = var8_8;
                        if (var10_9 >= (long)(Http2Stream.this.getConnection().getOkHttpSettings().getInitialWindowSize() / 2)) {
                            Http2Stream.this.getConnection().writeWindowUpdateLater$okhttp(Http2Stream.this.getId(), var10_9);
                            Http2Stream.this.setReadBytesAcknowledged$okhttp(Http2Stream.this.getReadBytesTotal());
                            var12_10 = var8_8;
                        }
                    }
                    ** GOTO lbl42
                }
                if (!this.finished && var5_5 == null) {
                    Http2Stream.this.waitForIo$okhttp();
                    var12_10 = -1L;
                    var4_4 = true;
                } else {
                    var12_10 = -1L;
lbl42: // 2 sources:
                    var4_4 = false;
                }
                var7_7 /* !! */  = Unit.INSTANCE;
            } while (var4_4);
            if (var12_10 != -1L) {
                this.updateConnectionFlowControl(var12_10);
                return var12_10;
            }
            if (var5_5 == null) return -1L;
            if (var5_5 != null) throw (Throwable)var5_5;
            Intrinsics.throwNpe();
            throw (Throwable)var5_5;
            {
                var1_1 = new IOException("stream closed");
                throw (Throwable)var1_1;
            }
            finally {
                Http2Stream.this.getReadTimeout$okhttp().exitAndThrowIfTimedOut();
            }
        }

        /*
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        public final void receive$okhttp(BufferedSource object, long l) throws IOException {
            long l2;
            Intrinsics.checkParameterIsNotNull(object, "source");
            Http2Stream http2Stream = Http2Stream.this;
            long l3 = l2;
            if (Util.assertionsEnabled) {
                if (Thread.holdsLock(http2Stream)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Thread ");
                    object = Thread.currentThread();
                    Intrinsics.checkExpressionValueIsNotNull(object, "Thread.currentThread()");
                    stringBuilder.append(((Thread)object).getName());
                    stringBuilder.append(" MUST NOT hold lock on ");
                    stringBuilder.append(http2Stream);
                    throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
                }
                l3 = l2;
            }
            while (l3 > 0L) {
                http2Stream = Http2Stream.this;
                // MONITORENTER : http2Stream
                boolean bl = this.finished;
                long l4 = this.readBuffer.size();
                l2 = this.maxByteCount;
                boolean bl2 = true;
                boolean bl3 = l4 + l3 > l2;
                Object object2 = Unit.INSTANCE;
                // MONITOREXIT : http2Stream
                if (bl3) {
                    object.skip(l3);
                    Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                }
                if (bl) {
                    object.skip(l3);
                    return;
                }
                l2 = object.read(this.receiveBuffer, l3);
                if (l2 == -1L) throw (Throwable)new EOFException();
                l4 = l3 - l2;
                http2Stream = Http2Stream.this;
                // MONITORENTER : http2Stream
                if (this.closed) {
                    l2 = this.receiveBuffer.size();
                    this.receiveBuffer.clear();
                } else {
                    bl3 = this.readBuffer.size() == 0L ? bl2 : false;
                    this.readBuffer.writeAll(this.receiveBuffer);
                    if (bl3) {
                        object2 = Http2Stream.this;
                        if (object2 == null) {
                            object = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                            throw object;
                        }
                        object2.notifyAll();
                    }
                    l2 = 0L;
                }
                object2 = Unit.INSTANCE;
                // MONITOREXIT : http2Stream
                l3 = l4;
                if (l2 <= 0L) continue;
                this.updateConnectionFlowControl(l2);
                l3 = l4;
            }
        }

        public final void setClosed$okhttp(boolean bl) {
            this.closed = bl;
        }

        public final void setFinished$okhttp(boolean bl) {
            this.finished = bl;
        }

        public final void setTrailers(Headers headers) {
            this.trailers = headers;
        }

        @Override
        public Timeout timeout() {
            return Http2Stream.this.getReadTimeout$okhttp();
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0014J\b\u0010\b\u001a\u00020\u0004H\u0014\u00a8\u0006\t"}, d2={"Lokhttp3/internal/http2/Http2Stream$StreamTimeout;", "Lokio/AsyncTimeout;", "(Lokhttp3/internal/http2/Http2Stream;)V", "exitAndThrowIfTimedOut", "", "newTimeoutException", "Ljava/io/IOException;", "cause", "timedOut", "okhttp"}, k=1, mv={1, 1, 16})
    public final class StreamTimeout
    extends AsyncTimeout {
        public final void exitAndThrowIfTimedOut() throws IOException {
            if (this.exit()) throw (Throwable)this.newTimeoutException(null);
        }

        @Override
        protected IOException newTimeoutException(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException == null) return socketTimeoutException;
            socketTimeoutException.initCause(iOException);
            return socketTimeoutException;
        }

        @Override
        protected void timedOut() {
            Http2Stream.this.closeLater(ErrorCode.CANCEL);
            Http2Stream.this.getConnection().sendDegradedPingLater$okhttp();
        }
    }

}

