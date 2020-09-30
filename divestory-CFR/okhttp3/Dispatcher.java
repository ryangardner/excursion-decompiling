/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealCall;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0019\u0010 \u001a\u00020\u001f2\n\u0010!\u001a\u00060\u001aR\u00020\u001bH\u0000\u00a2\u0006\u0002\b\"J\u0015\u0010#\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001bH\u0000\u00a2\u0006\u0002\b$J\r\u0010\u0002\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b%J\u0016\u0010&\u001a\b\u0018\u00010\u001aR\u00020\u001b2\u0006\u0010'\u001a\u00020(H\u0002J)\u0010)\u001a\u00020\u001f\"\u0004\b\u0000\u0010*2\f\u0010+\u001a\b\u0012\u0004\u0012\u0002H*0,2\u0006\u0010!\u001a\u0002H*H\u0002\u00a2\u0006\u0002\u0010-J\u0015\u0010)\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001bH\u0000\u00a2\u0006\u0002\b.J\u0019\u0010)\u001a\u00020\u001f2\n\u0010!\u001a\u00060\u001aR\u00020\u001bH\u0000\u00a2\u0006\u0002\b.J\b\u0010/\u001a\u000200H\u0002J\f\u00101\u001a\b\u0012\u0004\u0012\u00020302J\u0006\u00104\u001a\u00020\u0010J\f\u00105\u001a\b\u0012\u0004\u0012\u00020302J\u0006\u00106\u001a\u00020\u0010R\u0011\u0010\u0002\u001a\u00020\u00038G\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R*\u0010\n\u001a\u0004\u0018\u00010\t2\b\u0010\b\u001a\u0004\u0018\u00010\t8F@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR&\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R&\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00108F@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u0018\u0010\u0018\u001a\f\u0012\b\u0012\u00060\u001aR\u00020\u001b0\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u001c\u001a\f\u0012\b\u0012\u00060\u001aR\u00020\u001b0\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00067"}, d2={"Lokhttp3/Dispatcher;", "", "executorService", "Ljava/util/concurrent/ExecutorService;", "(Ljava/util/concurrent/ExecutorService;)V", "()V", "()Ljava/util/concurrent/ExecutorService;", "executorServiceOrNull", "<set-?>", "Ljava/lang/Runnable;", "idleCallback", "getIdleCallback", "()Ljava/lang/Runnable;", "setIdleCallback", "(Ljava/lang/Runnable;)V", "maxRequests", "", "getMaxRequests", "()I", "setMaxRequests", "(I)V", "maxRequestsPerHost", "getMaxRequestsPerHost", "setMaxRequestsPerHost", "readyAsyncCalls", "Ljava/util/ArrayDeque;", "Lokhttp3/internal/connection/RealCall$AsyncCall;", "Lokhttp3/internal/connection/RealCall;", "runningAsyncCalls", "runningSyncCalls", "cancelAll", "", "enqueue", "call", "enqueue$okhttp", "executed", "executed$okhttp", "-deprecated_executorService", "findExistingCallWithHost", "host", "", "finished", "T", "calls", "Ljava/util/Deque;", "(Ljava/util/Deque;Ljava/lang/Object;)V", "finished$okhttp", "promoteAndExecute", "", "queuedCalls", "", "Lokhttp3/Call;", "queuedCallsCount", "runningCalls", "runningCallsCount", "okhttp"}, k=1, mv={1, 1, 16})
public final class Dispatcher {
    private ExecutorService executorServiceOrNull;
    private Runnable idleCallback;
    private int maxRequests;
    private int maxRequestsPerHost;
    private final ArrayDeque<RealCall.AsyncCall> readyAsyncCalls;
    private final ArrayDeque<RealCall.AsyncCall> runningAsyncCalls;
    private final ArrayDeque<RealCall> runningSyncCalls;

    public Dispatcher() {
        this.maxRequests = 64;
        this.maxRequestsPerHost = 5;
        this.readyAsyncCalls = new ArrayDeque();
        this.runningAsyncCalls = new ArrayDeque();
        this.runningSyncCalls = new ArrayDeque();
    }

    public Dispatcher(ExecutorService executorService) {
        Intrinsics.checkParameterIsNotNull(executorService, "executorService");
        this();
        this.executorServiceOrNull = executorService;
    }

    private final RealCall.AsyncCall findExistingCallWithHost(String string2) {
        Object object;
        Object object2 = this.runningAsyncCalls.iterator();
        while (object2.hasNext()) {
            object = object2.next();
            if (!Intrinsics.areEqual(((RealCall.AsyncCall)object).getHost(), string2)) continue;
            return object;
        }
        object = this.readyAsyncCalls.iterator();
        do {
            if (!object.hasNext()) return null;
        } while (!Intrinsics.areEqual(((RealCall.AsyncCall)(object2 = (RealCall.AsyncCall)object.next())).getHost(), string2));
        return object2;
    }

    private final <T> void finished(Deque<T> object, T object2) {
        synchronized (this) {
            if (object.remove(object2)) {
                object = this.idleCallback;
                object2 = Unit.INSTANCE;
                // MONITOREXIT [0, 2, 4] lbl6 : MonitorExitStatement: MONITOREXIT : this
                if (this.promoteAndExecute()) return;
                if (object == null) return;
                object.run();
                return;
            }
            object = new AssertionError((Object)"Call wasn't in-flight!");
            throw (Throwable)object;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private final boolean promoteAndExecute() {
        int n;
        int n2;
        boolean bl;
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
        List list = new ArrayList();
        synchronized (this) {
            Object thread2;
            Iterator<RealCall.AsyncCall> iterator2 = this.readyAsyncCalls.iterator();
            Intrinsics.checkExpressionValueIsNotNull(iterator2, "readyAsyncCalls.iterator()");
            while (iterator2.hasNext()) {
                thread2 = iterator2.next();
                if (this.runningAsyncCalls.size() >= this.maxRequests) break;
                if (((RealCall.AsyncCall)thread2).getCallsPerHost().get() >= this.maxRequestsPerHost) continue;
                iterator2.remove();
                ((RealCall.AsyncCall)thread2).getCallsPerHost().incrementAndGet();
                Intrinsics.checkExpressionValueIsNotNull(thread2, "asyncCall");
                list.add(thread2);
                this.runningAsyncCalls.add((RealCall.AsyncCall)thread2);
            }
            n2 = this.runningCallsCount();
            n = 0;
            bl = n2 > 0;
            thread2 = Unit.INSTANCE;
        }
        n2 = list.size();
        while (n < n2) {
            ((RealCall.AsyncCall)list.get(n)).executeOn(this.executorService());
            ++n;
        }
        return bl;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="executorService", imports={}))
    public final ExecutorService -deprecated_executorService() {
        return this.executorService();
    }

    public final void cancelAll() {
        synchronized (this) {
            Iterator<Object> iterator2 = this.readyAsyncCalls.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().getCall().cancel();
            }
            iterator2 = this.runningAsyncCalls.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().getCall().cancel();
            }
            iterator2 = this.runningSyncCalls.iterator();
            while (iterator2.hasNext()) {
                ((RealCall)iterator2.next()).cancel();
            }
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void enqueue$okhttp(RealCall.AsyncCall object) {
        Intrinsics.checkParameterIsNotNull(object, "call");
        synchronized (this) {
            RealCall.AsyncCall asyncCall;
            this.readyAsyncCalls.add((RealCall.AsyncCall)object);
            if (!((RealCall.AsyncCall)object).getCall().getForWebSocket() && (asyncCall = this.findExistingCallWithHost(((RealCall.AsyncCall)object).getHost())) != null) {
                ((RealCall.AsyncCall)object).reuseCallsPerHostFrom(asyncCall);
            }
            object = Unit.INSTANCE;
        }
        this.promoteAndExecute();
    }

    public final void executed$okhttp(RealCall realCall) {
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull(realCall, "call");
            this.runningSyncCalls.add(realCall);
            return;
        }
    }

    public final ExecutorService executorService() {
        synchronized (this) {
            ExecutorService executorService;
            if (this.executorServiceOrNull == null) {
                TimeUnit timeUnit = TimeUnit.SECONDS;
                SynchronousQueue<Runnable> synchronousQueue = new SynchronousQueue<Runnable>();
                synchronousQueue = synchronousQueue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Util.okHttpName);
                stringBuilder.append(" Dispatcher");
                executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, timeUnit, synchronousQueue, Util.threadFactory(stringBuilder.toString(), false));
                this.executorServiceOrNull = executorService;
            }
            if ((executorService = this.executorServiceOrNull) != null) return executorService;
            Intrinsics.throwNpe();
            return executorService;
        }
    }

    public final void finished$okhttp(RealCall.AsyncCall asyncCall) {
        Intrinsics.checkParameterIsNotNull(asyncCall, "call");
        asyncCall.getCallsPerHost().decrementAndGet();
        this.finished((Deque)this.runningAsyncCalls, asyncCall);
    }

    public final void finished$okhttp(RealCall realCall) {
        Intrinsics.checkParameterIsNotNull(realCall, "call");
        this.finished((Deque)this.runningSyncCalls, realCall);
    }

    public final Runnable getIdleCallback() {
        synchronized (this) {
            return this.idleCallback;
        }
    }

    public final int getMaxRequests() {
        synchronized (this) {
            return this.maxRequests;
        }
    }

    public final int getMaxRequestsPerHost() {
        synchronized (this) {
            return this.maxRequestsPerHost;
        }
    }

    public final List<Call> queuedCalls() {
        synchronized (this) {
            List<Call> list = this.readyAsyncCalls;
            Collection<RealCall> collection = new Collection<RealCall>(CollectionsKt.collectionSizeOrDefault(list, 10));
            collection = collection;
            list = list.iterator();
            do {
                if (!list.hasNext()) {
                    list = Collections.unmodifiableList((List)collection);
                    Intrinsics.checkExpressionValueIsNotNull(list, "Collections.unmodifiable\u2026yncCalls.map { it.call })");
                    return list;
                }
                collection.add(((RealCall.AsyncCall)list.next()).getCall());
            } while (true);
        }
    }

    public final int queuedCallsCount() {
        synchronized (this) {
            return this.readyAsyncCalls.size();
        }
    }

    public final List<Call> runningCalls() {
        synchronized (this) {
            List<Iterable> list = (List<Iterable>)((Object)this.runningSyncCalls);
            Object object = this.runningAsyncCalls;
            Collection<RealCall> collection = new Collection<RealCall>(CollectionsKt.collectionSizeOrDefault(object, 10));
            collection = collection;
            object = object.iterator();
            do {
                if (!object.hasNext()) {
                    list = Collections.unmodifiableList(CollectionsKt.plus(list, (Iterable)((List)collection)));
                    Intrinsics.checkExpressionValueIsNotNull(list, "Collections.unmodifiable\u2026yncCalls.map { it.call })");
                    return list;
                }
                collection.add(((RealCall.AsyncCall)object.next()).getCall());
            } while (true);
        }
    }

    public final int runningCallsCount() {
        synchronized (this) {
            int n = this.runningAsyncCalls.size();
            int n2 = this.runningSyncCalls.size();
            return n + n2;
        }
    }

    public final void setIdleCallback(Runnable runnable2) {
        synchronized (this) {
            this.idleCallback = runnable2;
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void setMaxRequests(int n) {
        boolean bl = true;
        if (n < 1) {
            bl = false;
        }
        if (bl) {
            synchronized (this) {
                this.maxRequests = n;
                Unit unit = Unit.INSTANCE;
            }
            this.promoteAndExecute();
            return;
        }
        StringBuilder throwable = new StringBuilder();
        throwable.append("max < 1: ");
        throwable.append(n);
        throw (Throwable)new IllegalArgumentException(throwable.toString().toString());
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void setMaxRequestsPerHost(int n) {
        boolean bl = true;
        if (n < 1) {
            bl = false;
        }
        if (bl) {
            synchronized (this) {
                this.maxRequestsPerHost = n;
                Unit unit = Unit.INSTANCE;
            }
            this.promoteAndExecute();
            return;
        }
        StringBuilder throwable = new StringBuilder();
        throwable.append("max < 1: ");
        throwable.append(n);
        throw (Throwable)new IllegalArgumentException(throwable.toString().toString());
    }
}

