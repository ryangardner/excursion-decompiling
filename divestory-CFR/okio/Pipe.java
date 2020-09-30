/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okio.Pipe$sink
 *  okio.Pipe$source
 */
package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.Pipe;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\"2\u0006\u0010\u0017\u001a\u00020\u0010J\r\u0010\u0017\u001a\u00020\u0010H\u0007\u00a2\u0006\u0002\b$J\r\u0010\u001b\u001a\u00020\u001cH\u0007\u00a2\u0006\u0002\b%J&\u0010&\u001a\u00020\"*\u00020\u00102\u0017\u0010'\u001a\u0013\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\"0(\u00a2\u0006\u0002\b)H\u0082\bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0017\u001a\u00020\u00108G\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u001a\u0010\u0018\u001a\u00020\nX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u0013\u0010\u001b\u001a\u00020\u001c8G\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\nX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\f\"\u0004\b \u0010\u000e\u00a8\u0006*"}, d2={"Lokio/Pipe;", "", "maxBufferSize", "", "(J)V", "buffer", "Lokio/Buffer;", "getBuffer$okio", "()Lokio/Buffer;", "canceled", "", "getCanceled$okio", "()Z", "setCanceled$okio", "(Z)V", "foldedSink", "Lokio/Sink;", "getFoldedSink$okio", "()Lokio/Sink;", "setFoldedSink$okio", "(Lokio/Sink;)V", "getMaxBufferSize$okio", "()J", "sink", "sinkClosed", "getSinkClosed$okio", "setSinkClosed$okio", "source", "Lokio/Source;", "()Lokio/Source;", "sourceClosed", "getSourceClosed$okio", "setSourceClosed$okio", "cancel", "", "fold", "-deprecated_sink", "-deprecated_source", "forward", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "okio"}, k=1, mv={1, 1, 16})
public final class Pipe {
    private final Buffer buffer;
    private boolean canceled;
    private Sink foldedSink;
    private final long maxBufferSize;
    private final Sink sink;
    private boolean sinkClosed;
    private final Source source;
    private boolean sourceClosed;

    public Pipe(long l) {
        this.maxBufferSize = l;
        this.buffer = new Buffer();
        boolean bl = this.maxBufferSize >= 1L;
        if (bl) {
            this.sink = new Sink(this){
                final /* synthetic */ Pipe this$0;
                private final Timeout timeout;
                {
                    this.this$0 = pipe;
                    this.timeout = new Timeout();
                }

                /*
                 * Enabled unnecessary exception pruning
                 */
                public void close() {
                    Object object;
                    Object object2 = null;
                    Object object3 = this.this$0.getBuffer$okio();
                    synchronized (object3) {
                        boolean bl = this.this$0.getSinkClosed$okio();
                        if (bl) {
                            return;
                        }
                        object = this.this$0.getFoldedSink$okio();
                        if (object != null) {
                            object2 = object;
                        } else {
                            if (this.this$0.getSourceClosed$okio() && this.this$0.getBuffer$okio().size() > 0L) {
                                object2 = new IOException("source is closed");
                                throw (Throwable)object2;
                            }
                            this.this$0.setSinkClosed$okio(true);
                            object = this.this$0.getBuffer$okio();
                            if (object == null) {
                                object2 = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                                throw object2;
                            }
                            object.notifyAll();
                        }
                        object = Unit.INSTANCE;
                    }
                    if (object2 == null) return;
                    object3 = this.this$0;
                    object = object2.timeout();
                    object3 = ((Pipe)object3).sink().timeout();
                    long l = ((Timeout)object).timeoutNanos();
                    ((Timeout)object).timeout(Timeout.Companion.minTimeout(((Timeout)object3).timeoutNanos(), ((Timeout)object).timeoutNanos()), TimeUnit.NANOSECONDS);
                    if (((Timeout)object).hasDeadline()) {
                        long l2 = ((Timeout)object).deadlineNanoTime();
                        if (((Timeout)object3).hasDeadline()) {
                            ((Timeout)object).deadlineNanoTime(Math.min(((Timeout)object).deadlineNanoTime(), ((Timeout)object3).deadlineNanoTime()));
                        }
                        try {
                            object2.close();
                            return;
                        }
                        finally {
                            ((Timeout)object).timeout(l, TimeUnit.NANOSECONDS);
                            if (((Timeout)object3).hasDeadline()) {
                                ((Timeout)object).deadlineNanoTime(l2);
                            }
                        }
                    }
                    if (((Timeout)object3).hasDeadline()) {
                        ((Timeout)object).deadlineNanoTime(((Timeout)object3).deadlineNanoTime());
                    }
                    try {
                        object2.close();
                        return;
                    }
                    finally {
                        ((Timeout)object).timeout(l, TimeUnit.NANOSECONDS);
                        if (((Timeout)object3).hasDeadline()) {
                            ((Timeout)object).clearDeadline();
                        }
                    }
                }

                /*
                 * Enabled unnecessary exception pruning
                 */
                public void flush() {
                    Object object;
                    Object object2 = null;
                    Object object3 = this.this$0.getBuffer$okio();
                    synchronized (object3) {
                        if (!(this.this$0.getSinkClosed$okio() ^ true)) {
                            IllegalStateException illegalStateException = new IllegalStateException("closed".toString());
                            throw (Throwable)illegalStateException;
                        }
                        if (this.this$0.getCanceled$okio()) {
                            IOException iOException = new IOException("canceled");
                            throw (Throwable)iOException;
                        }
                        object = this.this$0.getFoldedSink$okio();
                        if (object == null) {
                            object = object2;
                            if (this.this$0.getSourceClosed$okio()) {
                                if (this.this$0.getBuffer$okio().size() > 0L) {
                                    object = new IOException("source is closed");
                                    throw (Throwable)object;
                                }
                                object = object2;
                            }
                        }
                        object2 = Unit.INSTANCE;
                    }
                    if (object == null) return;
                    object3 = this.this$0;
                    object2 = object.timeout();
                    object3 = ((Pipe)object3).sink().timeout();
                    long l = ((Timeout)object2).timeoutNanos();
                    ((Timeout)object2).timeout(Timeout.Companion.minTimeout(((Timeout)object3).timeoutNanos(), ((Timeout)object2).timeoutNanos()), TimeUnit.NANOSECONDS);
                    if (((Timeout)object2).hasDeadline()) {
                        long l2 = ((Timeout)object2).deadlineNanoTime();
                        if (((Timeout)object3).hasDeadline()) {
                            ((Timeout)object2).deadlineNanoTime(Math.min(((Timeout)object2).deadlineNanoTime(), ((Timeout)object3).deadlineNanoTime()));
                        }
                        try {
                            object.flush();
                            return;
                        }
                        finally {
                            ((Timeout)object2).timeout(l, TimeUnit.NANOSECONDS);
                            if (((Timeout)object3).hasDeadline()) {
                                ((Timeout)object2).deadlineNanoTime(l2);
                            }
                        }
                    }
                    if (((Timeout)object3).hasDeadline()) {
                        ((Timeout)object2).deadlineNanoTime(((Timeout)object3).deadlineNanoTime());
                    }
                    try {
                        object.flush();
                        return;
                    }
                    finally {
                        ((Timeout)object2).timeout(l, TimeUnit.NANOSECONDS);
                        if (((Timeout)object3).hasDeadline()) {
                            ((Timeout)object2).clearDeadline();
                        }
                    }
                }

                public Timeout timeout() {
                    return this.timeout;
                }

                /*
                 * WARNING - void declaration
                 * Enabled unnecessary exception pruning
                 */
                public void write(Buffer object, long l) {
                    long l2;
                    void var2_4;
                    Sink sink2;
                    Intrinsics.checkParameterIsNotNull(object, "source");
                    Object object2 = null;
                    Object object3 = this.this$0.getBuffer$okio();
                    synchronized (object3) {
                        if (!(this.this$0.getSinkClosed$okio() ^ true)) {
                            object = new IllegalStateException("closed".toString());
                            throw (Throwable)object;
                        }
                        if (this.this$0.getCanceled$okio()) {
                            object = new IOException("canceled");
                            throw (Throwable)object;
                        }
                        do {
                            sink2 = object2;
                            if (var2_4 <= 0L || (sink2 = this.this$0.getFoldedSink$okio()) != null) break;
                            if (this.this$0.getSourceClosed$okio()) {
                                object = new IOException("source is closed");
                                throw (Throwable)object;
                            }
                            l2 = this.this$0.getMaxBufferSize$okio() - this.this$0.getBuffer$okio().size();
                            if (l2 == 0L) {
                                this.timeout.waitUntilNotified(this.this$0.getBuffer$okio());
                                if (!this.this$0.getCanceled$okio()) continue;
                                object = new IOException("canceled");
                                throw (Throwable)object;
                            }
                            l2 = Math.min(l2, (long)var2_4);
                            this.this$0.getBuffer$okio().write((Buffer)object, l2);
                            var2_4 -= l2;
                            sink2 = this.this$0.getBuffer$okio();
                            if (sink2 == null) {
                                object = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                                throw object;
                            }
                            ((Object)sink2).notifyAll();
                        } while (true);
                        object2 = Unit.INSTANCE;
                    }
                    if (sink2 == null) return;
                    object3 = this.this$0;
                    object2 = sink2.timeout();
                    object3 = ((Pipe)object3).sink().timeout();
                    l2 = ((Timeout)object2).timeoutNanos();
                    ((Timeout)object2).timeout(Timeout.Companion.minTimeout(((Timeout)object3).timeoutNanos(), ((Timeout)object2).timeoutNanos()), TimeUnit.NANOSECONDS);
                    if (((Timeout)object2).hasDeadline()) {
                        long l3 = ((Timeout)object2).deadlineNanoTime();
                        if (((Timeout)object3).hasDeadline()) {
                            ((Timeout)object2).deadlineNanoTime(Math.min(((Timeout)object2).deadlineNanoTime(), ((Timeout)object3).deadlineNanoTime()));
                        }
                        try {
                            sink2.write((Buffer)object, (long)var2_4);
                            return;
                        }
                        finally {
                            ((Timeout)object2).timeout(l2, TimeUnit.NANOSECONDS);
                            if (((Timeout)object3).hasDeadline()) {
                                ((Timeout)object2).deadlineNanoTime(l3);
                            }
                        }
                    }
                    if (((Timeout)object3).hasDeadline()) {
                        ((Timeout)object2).deadlineNanoTime(((Timeout)object3).deadlineNanoTime());
                    }
                    try {
                        sink2.write((Buffer)object, (long)var2_4);
                        return;
                    }
                    finally {
                        ((Timeout)object2).timeout(l2, TimeUnit.NANOSECONDS);
                        if (((Timeout)object3).hasDeadline()) {
                            ((Timeout)object2).clearDeadline();
                        }
                    }
                }
            };
            this.source = new Source(this){
                final /* synthetic */ Pipe this$0;
                private final Timeout timeout;
                {
                    this.this$0 = pipe;
                    this.timeout = new Timeout();
                }

                public void close() {
                    Buffer buffer = this.this$0.getBuffer$okio();
                    synchronized (buffer) {
                        this.this$0.setSourceClosed$okio(true);
                        Object object = this.this$0.getBuffer$okio();
                        if (object != null) {
                            ((Object)object).notifyAll();
                            object = Unit.INSTANCE;
                            return;
                        }
                        object = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                        throw object;
                    }
                }

                public long read(Buffer object, long l) {
                    Intrinsics.checkParameterIsNotNull(object, "sink");
                    Buffer buffer = this.this$0.getBuffer$okio();
                    synchronized (buffer) {
                        if (this.this$0.getSourceClosed$okio() ^ true) {
                            if (this.this$0.getCanceled$okio()) {
                                object = new IOException("canceled");
                                throw (Throwable)object;
                            }
                        } else {
                            object = new IllegalStateException("closed".toString());
                            throw (Throwable)object;
                        }
                        while (this.this$0.getBuffer$okio().size() == 0L) {
                            boolean bl = this.this$0.getSinkClosed$okio();
                            if (bl) return -1L;
                            this.timeout.waitUntilNotified(this.this$0.getBuffer$okio());
                            if (!this.this$0.getCanceled$okio()) continue;
                            object = new IOException("canceled");
                            throw (Throwable)object;
                        }
                        l = this.this$0.getBuffer$okio().read((Buffer)object, l);
                        object = this.this$0.getBuffer$okio();
                        if (object != null) {
                            object.notifyAll();
                            return l;
                        }
                        object = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                        throw object;
                    }
                }

                public Timeout timeout() {
                    return this.timeout;
                }
            };
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("maxBufferSize < 1: ");
        stringBuilder.append(this.maxBufferSize);
        throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
    }

    public static final /* synthetic */ void access$forward(Pipe pipe, Sink sink2, Function1 function1) {
        pipe.forward(sink2, function1);
    }

    private final void forward(Sink sink2, Function1<? super Sink, Unit> function1) {
        Timeout timeout2 = sink2.timeout();
        Timeout timeout3 = this.sink().timeout();
        long l = timeout2.timeoutNanos();
        timeout2.timeout(Timeout.Companion.minTimeout(timeout3.timeoutNanos(), timeout2.timeoutNanos()), TimeUnit.NANOSECONDS);
        if (timeout2.hasDeadline()) {
            long l2 = timeout2.deadlineNanoTime();
            if (timeout3.hasDeadline()) {
                timeout2.deadlineNanoTime(Math.min(timeout2.deadlineNanoTime(), timeout3.deadlineNanoTime()));
            }
            try {
                function1.invoke(sink2);
                return;
            }
            finally {
                InlineMarker.finallyStart(1);
                timeout2.timeout(l, TimeUnit.NANOSECONDS);
                if (timeout3.hasDeadline()) {
                    timeout2.deadlineNanoTime(l2);
                }
                InlineMarker.finallyEnd(1);
            }
        }
        if (timeout3.hasDeadline()) {
            timeout2.deadlineNanoTime(timeout3.deadlineNanoTime());
        }
        try {
            function1.invoke(sink2);
            return;
        }
        finally {
            InlineMarker.finallyStart(1);
            timeout2.timeout(l, TimeUnit.NANOSECONDS);
            if (timeout3.hasDeadline()) {
                timeout2.clearDeadline();
            }
            InlineMarker.finallyEnd(1);
        }
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="sink", imports={}))
    public final Sink -deprecated_sink() {
        return this.sink;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="source", imports={}))
    public final Source -deprecated_source() {
        return this.source;
    }

    public final void cancel() {
        Buffer buffer = this.buffer;
        synchronized (buffer) {
            this.canceled = true;
            this.buffer.clear();
            Object object = this.buffer;
            if (object != null) {
                ((Object)object).notifyAll();
                object = Unit.INSTANCE;
                return;
            }
            object = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
            throw object;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void fold(Sink object) throws IOException {
        Intrinsics.checkParameterIsNotNull(object, "sink");
        do {
            Object object2;
            boolean bl;
            Buffer buffer = this.buffer;
            synchronized (buffer) {
                boolean bl2 = this.foldedSink == null;
                if (!bl2) {
                    object = new IllegalStateException("sink already folded".toString());
                    throw (Throwable)object;
                }
                if (this.canceled) {
                    this.foldedSink = object;
                    object = new IOException("canceled");
                    throw (Throwable)object;
                }
                if (this.buffer.exhausted()) {
                    this.sourceClosed = true;
                    this.foldedSink = object;
                    return;
                }
                bl = this.sinkClosed;
                object2 = new Buffer();
                ((Buffer)object2).write(this.buffer, this.buffer.size());
                Object object3 = this.buffer;
                if (object3 == null) {
                    object = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    throw object;
                }
                ((Object)object3).notifyAll();
                object3 = Unit.INSTANCE;
            }
            try {
                object.write((Buffer)object2, ((Buffer)object2).size());
                if (bl) {
                    object.close();
                    continue;
                }
                object.flush();
            }
            catch (Throwable throwable) {
                object = this.buffer;
                synchronized (object) {
                    this.sourceClosed = true;
                    object2 = this.buffer;
                    if (object2 == null) {
                        TypeCastException typeCastException = new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                        throw typeCastException;
                    }
                    ((Object)object2).notifyAll();
                    object2 = Unit.INSTANCE;
                    throw throwable;
                }
            }
        } while (true);
    }

    public final Buffer getBuffer$okio() {
        return this.buffer;
    }

    public final boolean getCanceled$okio() {
        return this.canceled;
    }

    public final Sink getFoldedSink$okio() {
        return this.foldedSink;
    }

    public final long getMaxBufferSize$okio() {
        return this.maxBufferSize;
    }

    public final boolean getSinkClosed$okio() {
        return this.sinkClosed;
    }

    public final boolean getSourceClosed$okio() {
        return this.sourceClosed;
    }

    public final void setCanceled$okio(boolean bl) {
        this.canceled = bl;
    }

    public final void setFoldedSink$okio(Sink sink2) {
        this.foldedSink = sink2;
    }

    public final void setSinkClosed$okio(boolean bl) {
        this.sinkClosed = bl;
    }

    public final void setSourceClosed$okio(boolean bl) {
        this.sourceClosed = bl;
    }

    public final Sink sink() {
        return this.sink;
    }

    public final Source source() {
        return this.source;
    }
}

