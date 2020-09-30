/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okio.AsyncTimeout$sink
 *  okio.AsyncTimeout$source
 */
package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import okio.AsyncTimeout;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 \u001b2\u00020\u0001:\u0002\u001b\u001cB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0001J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\u0004J\u0012\u0010\u000e\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\tH\u0014J\u0010\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007H\u0002J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u00020\fH\u0014J\"\u0010\u0016\u001a\u0002H\u0017\"\u0004\b\u0000\u0010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0019H\u0086\b\u00a2\u0006\u0002\u0010\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lokio/AsyncTimeout;", "Lokio/Timeout;", "()V", "inQueue", "", "next", "timeoutAt", "", "access$newTimeoutException", "Ljava/io/IOException;", "cause", "enter", "", "exit", "newTimeoutException", "remainingNanos", "now", "sink", "Lokio/Sink;", "source", "Lokio/Source;", "timedOut", "withTimeout", "T", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "Companion", "Watchdog", "okio"}, k=1, mv={1, 1, 16})
public class AsyncTimeout
extends Timeout {
    public static final Companion Companion = new Companion(null);
    private static final long IDLE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60L);
    private static final long IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(IDLE_TIMEOUT_MILLIS);
    private static final int TIMEOUT_WRITE_SIZE = 65536;
    private static AsyncTimeout head;
    private boolean inQueue;
    private AsyncTimeout next;
    private long timeoutAt;

    public static final /* synthetic */ long access$getTimeoutAt$p(AsyncTimeout asyncTimeout) {
        return asyncTimeout.timeoutAt;
    }

    public static final /* synthetic */ void access$setHead$cp(AsyncTimeout asyncTimeout) {
        head = asyncTimeout;
    }

    public static final /* synthetic */ void access$setNext$p(AsyncTimeout asyncTimeout, AsyncTimeout asyncTimeout2) {
        asyncTimeout.next = asyncTimeout2;
    }

    public static final /* synthetic */ void access$setTimeoutAt$p(AsyncTimeout asyncTimeout, long l) {
        asyncTimeout.timeoutAt = l;
    }

    private final long remainingNanos(long l) {
        return this.timeoutAt - l;
    }

    public final IOException access$newTimeoutException(IOException iOException) {
        return this.newTimeoutException(iOException);
    }

    public final void enter() {
        if (!(this.inQueue ^ true)) throw (Throwable)new IllegalStateException("Unbalanced enter/exit".toString());
        long l = this.timeoutNanos();
        boolean bl = this.hasDeadline();
        if (l == 0L && !bl) {
            return;
        }
        this.inQueue = true;
        AsyncTimeout.Companion.scheduleTimeout(this, l, bl);
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return AsyncTimeout.Companion.cancelScheduledTimeout(this);
    }

    protected IOException newTimeoutException(IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException == null) return interruptedIOException;
        interruptedIOException.initCause(iOException);
        return interruptedIOException;
    }

    public final Sink sink(Sink sink2) {
        Intrinsics.checkParameterIsNotNull(sink2, "sink");
        return new Sink(this, sink2){
            final /* synthetic */ Sink $sink;
            final /* synthetic */ AsyncTimeout this$0;
            {
                this.this$0 = asyncTimeout;
                this.$sink = sink2;
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled unnecessary exception pruning
             */
            public void close() {
                Throwable throwable2222;
                AsyncTimeout asyncTimeout = this.this$0;
                asyncTimeout.enter();
                this.$sink.close();
                Unit unit = Unit.INSTANCE;
                if (asyncTimeout.exit()) throw (Throwable)asyncTimeout.access$newTimeoutException(null);
                return;
                {
                    catch (Throwable throwable2222) {
                    }
                    catch (IOException iOException) {}
                    {
                        Throwable throwable3;
                        if (asyncTimeout.exit()) {
                            throwable3 = asyncTimeout.access$newTimeoutException(iOException);
                        }
                        throwable3 = throwable3;
                        throw throwable3;
                    }
                }
                asyncTimeout.exit();
                throw throwable2222;
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled unnecessary exception pruning
             */
            public void flush() {
                Throwable throwable2222;
                AsyncTimeout asyncTimeout = this.this$0;
                asyncTimeout.enter();
                this.$sink.flush();
                Unit unit = Unit.INSTANCE;
                if (asyncTimeout.exit()) throw (Throwable)asyncTimeout.access$newTimeoutException(null);
                return;
                {
                    catch (Throwable throwable2222) {
                    }
                    catch (IOException iOException) {}
                    {
                        Throwable throwable3;
                        if (asyncTimeout.exit()) {
                            throwable3 = asyncTimeout.access$newTimeoutException(iOException);
                        }
                        throwable3 = throwable3;
                        throw throwable3;
                    }
                }
                asyncTimeout.exit();
                throw throwable2222;
            }

            public AsyncTimeout timeout() {
                return this.this$0;
            }

            public String toString() {
                java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();
                stringBuilder.append("AsyncTimeout.sink(");
                stringBuilder.append(this.$sink);
                stringBuilder.append(')');
                return stringBuilder.toString();
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled unnecessary exception pruning
             */
            public void write(okio.Buffer buffer, long l) {
                Throwable throwable2222;
                Object object;
                Intrinsics.checkParameterIsNotNull(buffer, "source");
                okio._Util.checkOffsetAndCount(buffer.size(), 0L, l);
                do {
                    long l2 = 0L;
                    if (l <= 0L) return;
                    Object object2 = buffer.head;
                    long l3 = l2;
                    object = object2;
                    if (object2 == null) {
                        Intrinsics.throwNpe();
                        object = object2;
                        l3 = l2;
                    }
                    do {
                        l2 = l3;
                        if (l3 >= (long)65536) break;
                        l2 = l3 + (long)(((okio.Segment)object).limit - ((okio.Segment)object).pos);
                        if (l2 >= l) {
                            l2 = l;
                            break;
                        }
                        object2 = ((okio.Segment)object).next;
                        l3 = l2;
                        object = object2;
                        if (object2 != null) continue;
                        Intrinsics.throwNpe();
                        l3 = l2;
                        object = object2;
                    } while (true);
                    object = this.this$0;
                    ((AsyncTimeout)object).enter();
                    this.$sink.write(buffer, l2);
                    object2 = Unit.INSTANCE;
                    if (((AsyncTimeout)object).exit()) throw (Throwable)((AsyncTimeout)object).access$newTimeoutException(null);
                    l -= l2;
                    continue;
                    {
                        catch (Throwable throwable2222) {
                            break;
                        }
                        catch (IOException iOException) {}
                        {
                            Throwable throwable3;
                            if (((AsyncTimeout)object).exit()) {
                                throwable3 = ((AsyncTimeout)object).access$newTimeoutException(iOException);
                            }
                            throwable3 = throwable3;
                            throw throwable3;
                        }
                    }
                } while (true);
                ((AsyncTimeout)object).exit();
                throw throwable2222;
            }
        };
    }

    public final Source source(Source source2) {
        Intrinsics.checkParameterIsNotNull(source2, "source");
        return new Source(this, source2){
            final /* synthetic */ Source $source;
            final /* synthetic */ AsyncTimeout this$0;
            {
                this.this$0 = asyncTimeout;
                this.$source = source2;
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled unnecessary exception pruning
             */
            public void close() {
                Throwable throwable2222;
                AsyncTimeout asyncTimeout = this.this$0;
                asyncTimeout.enter();
                this.$source.close();
                Unit unit = Unit.INSTANCE;
                if (asyncTimeout.exit()) throw (Throwable)asyncTimeout.access$newTimeoutException(null);
                return;
                {
                    catch (Throwable throwable2222) {
                    }
                    catch (IOException iOException) {}
                    {
                        Throwable throwable3;
                        if (asyncTimeout.exit()) {
                            throwable3 = asyncTimeout.access$newTimeoutException(iOException);
                        }
                        throwable3 = throwable3;
                        throw throwable3;
                    }
                }
                asyncTimeout.exit();
                throw throwable2222;
            }

            /*
             * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
             * Loose catch block
             * Enabled unnecessary exception pruning
             */
            public long read(okio.Buffer buffer, long l) {
                Throwable throwable2222;
                Intrinsics.checkParameterIsNotNull(buffer, "sink");
                AsyncTimeout asyncTimeout = this.this$0;
                asyncTimeout.enter();
                l = this.$source.read(buffer, l);
                if (asyncTimeout.exit()) throw (Throwable)asyncTimeout.access$newTimeoutException(null);
                return l;
                {
                    catch (Throwable throwable2222) {
                    }
                    catch (IOException iOException) {}
                    {
                        Throwable throwable3;
                        if (asyncTimeout.exit()) {
                            throwable3 = asyncTimeout.access$newTimeoutException(iOException);
                        }
                        throwable3 = throwable3;
                        throw throwable3;
                    }
                }
                asyncTimeout.exit();
                throw throwable2222;
            }

            public AsyncTimeout timeout() {
                return this.this$0;
            }

            public String toString() {
                java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();
                stringBuilder.append("AsyncTimeout.source(");
                stringBuilder.append(this.$source);
                stringBuilder.append(')');
                return stringBuilder.toString();
            }
        };
    }

    protected void timedOut() {
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public final <T> T withTimeout(Function0<? extends T> function0) {
        Throwable throwable2222;
        Intrinsics.checkParameterIsNotNull(function0, "block");
        this.enter();
        function0 = function0.invoke();
        InlineMarker.finallyStart(1);
        if (this.exit()) throw (Throwable)this.access$newTimeoutException(null);
        InlineMarker.finallyEnd(1);
        return (T)function0;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                Throwable throwable3;
                if (this.exit()) {
                    throwable3 = this.access$newTimeoutException(iOException);
                }
                throwable3 = throwable3;
                throw throwable3;
            }
        }
        InlineMarker.finallyStart(1);
        this.exit();
        InlineMarker.finallyEnd(1);
        throw throwable2222;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\n\u001a\u0004\u0018\u00010\tH\u0000\u00a2\u0006\u0002\b\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\tH\u0002J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lokio/AsyncTimeout$Companion;", "", "()V", "IDLE_TIMEOUT_MILLIS", "", "IDLE_TIMEOUT_NANOS", "TIMEOUT_WRITE_SIZE", "", "head", "Lokio/AsyncTimeout;", "awaitTimeout", "awaitTimeout$okio", "cancelScheduledTimeout", "", "node", "scheduleTimeout", "", "timeoutNanos", "hasDeadline", "okio"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final boolean cancelScheduledTimeout(AsyncTimeout asyncTimeout) {
            synchronized (AsyncTimeout.class) {
                AsyncTimeout asyncTimeout2 = head;
                while (asyncTimeout2 != null) {
                    if (asyncTimeout2.next == asyncTimeout) {
                        AsyncTimeout.access$setNext$p(asyncTimeout2, asyncTimeout.next);
                        AsyncTimeout.access$setNext$p(asyncTimeout, null);
                        return false;
                    }
                    asyncTimeout2 = asyncTimeout2.next;
                }
                return true;
            }
        }

        /*
         * WARNING - void declaration
         * Enabled unnecessary exception pruning
         */
        private final void scheduleTimeout(AsyncTimeout object, long l, boolean bl) {
            synchronized (AsyncTimeout.class) {
                void var4_3;
                long l2;
                Object object2;
                if (head == null) {
                    object2 = new AsyncTimeout();
                    AsyncTimeout.access$setHead$cp((AsyncTimeout)object2);
                    object2 = new Watchdog();
                    ((Thread)object2).start();
                }
                long l3 = System.nanoTime();
                void var8_6 = l2 LCMP 0L;
                if (var8_6 != false && var4_3 != false) {
                    AsyncTimeout.access$setTimeoutAt$p((AsyncTimeout)object, Math.min(l2, ((Timeout)object).deadlineNanoTime() - l3) + l3);
                } else if (var8_6 != false) {
                    AsyncTimeout.access$setTimeoutAt$p((AsyncTimeout)object, (long)(l2 + l3));
                } else {
                    if (var4_3 == false) {
                        object = new AssertionError();
                        throw (Throwable)object;
                    }
                    AsyncTimeout.access$setTimeoutAt$p((AsyncTimeout)object, ((Timeout)object).deadlineNanoTime());
                }
                l2 = ((AsyncTimeout)object).remainingNanos(l3);
                AsyncTimeout asyncTimeout = head;
                object2 = asyncTimeout;
                if (asyncTimeout == null) {
                    Intrinsics.throwNpe();
                    object2 = asyncTimeout;
                }
                while (((AsyncTimeout)object2).next != null) {
                    asyncTimeout = ((AsyncTimeout)object2).next;
                    if (asyncTimeout == null) {
                        Intrinsics.throwNpe();
                    }
                    if (l2 < asyncTimeout.remainingNanos(l3)) break;
                    asyncTimeout = ((AsyncTimeout)object2).next;
                    object2 = asyncTimeout;
                    if (asyncTimeout != null) continue;
                    Intrinsics.throwNpe();
                    object2 = asyncTimeout;
                }
                AsyncTimeout.access$setNext$p((AsyncTimeout)object, ((AsyncTimeout)object2).next);
                AsyncTimeout.access$setNext$p((AsyncTimeout)object2, (AsyncTimeout)object);
                if (object2 == head) {
                    ((Object)AsyncTimeout.class).notify();
                }
                object = Unit.INSTANCE;
                return;
            }
        }

        public final AsyncTimeout awaitTimeout$okio() throws InterruptedException {
            AsyncTimeout asyncTimeout = head;
            if (asyncTimeout == null) {
                Intrinsics.throwNpe();
            }
            asyncTimeout = asyncTimeout.next;
            AsyncTimeout asyncTimeout2 = null;
            if (asyncTimeout == null) {
                long l = System.nanoTime();
                ((Object)AsyncTimeout.class).wait(IDLE_TIMEOUT_MILLIS);
                AsyncTimeout asyncTimeout3 = head;
                if (asyncTimeout3 == null) {
                    Intrinsics.throwNpe();
                }
                asyncTimeout = asyncTimeout2;
                if (asyncTimeout3.next != null) return asyncTimeout;
                asyncTimeout = asyncTimeout2;
                if (System.nanoTime() - l < IDLE_TIMEOUT_NANOS) return asyncTimeout;
                return head;
            }
            long l = asyncTimeout.remainingNanos(System.nanoTime());
            if (l > 0L) {
                long l2 = l / 1000000L;
                ((Object)AsyncTimeout.class).wait(l2, (int)(l - 1000000L * l2));
                return null;
            }
            asyncTimeout2 = head;
            if (asyncTimeout2 == null) {
                Intrinsics.throwNpe();
            }
            AsyncTimeout.access$setNext$p(asyncTimeout2, asyncTimeout.next);
            AsyncTimeout.access$setNext$p(asyncTimeout, null);
            return asyncTimeout;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0000\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016\u00a8\u0006\u0005"}, d2={"Lokio/AsyncTimeout$Watchdog;", "Ljava/lang/Thread;", "()V", "run", "", "okio"}, k=1, mv={1, 1, 16})
    private static final class Watchdog
    extends Thread {
        public Watchdog() {
            super("Okio Watchdog");
            this.setDaemon(true);
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        @Override
        public void run() {
            do {
                var1_1 = null;
                // MONITORENTER : okio.AsyncTimeout.class
                var2_3 = AsyncTimeout.Companion.awaitTimeout$okio();
                if (var2_3 != AsyncTimeout.access$getHead$cp()) ** GOTO lbl11
                AsyncTimeout.access$setHead$cp(null);
                try {
                    // MONITOREXIT : okio.AsyncTimeout.class
                    return;
lbl11: // 1 sources:
                    var1_1 = Unit.INSTANCE;
                    // MONITOREXIT : okio.AsyncTimeout.class
                    if (var2_3 == null) continue;
                    var2_3.timedOut();
                }
                catch (InterruptedException var1_2) {
                }
            } while (true);
        }
    }

}

