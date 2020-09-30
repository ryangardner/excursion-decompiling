/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.connection.RealConnectionPool$cleanupTask
 */
package okhttp3.internal.connection;

import java.lang.ref.Reference;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.RealConnectionPool;
import okhttp3.internal.platform.Platform;

@Metadata(bv={1, 0, 3}, d1={"\u0000c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005*\u0001\u000e\u0018\u0000 (2\u00020\u0001:\u0001(B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ.\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b2\u0006\u0010\u001d\u001a\u00020\u0015J\u000e\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\u0007J\u000e\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u0012J\u0006\u0010\"\u001a\u00020\u0005J\u0006\u0010#\u001a\u00020$J\u0006\u0010%\u001a\u00020\u0005J\u0018\u0010&\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0007H\u0002J\u000e\u0010'\u001a\u00020$2\u0006\u0010!\u001a\u00020\u0012R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2={"Lokhttp3/internal/connection/RealConnectionPool;", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "maxIdleConnections", "", "keepAliveDuration", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "(Lokhttp3/internal/concurrent/TaskRunner;IJLjava/util/concurrent/TimeUnit;)V", "cleanupQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "cleanupTask", "okhttp3/internal/connection/RealConnectionPool$cleanupTask$1", "Lokhttp3/internal/connection/RealConnectionPool$cleanupTask$1;", "connections", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lokhttp3/internal/connection/RealConnection;", "keepAliveDurationNs", "callAcquirePooledConnection", "", "address", "Lokhttp3/Address;", "call", "Lokhttp3/internal/connection/RealCall;", "routes", "", "Lokhttp3/Route;", "requireMultiplexed", "cleanup", "now", "connectionBecameIdle", "connection", "connectionCount", "evictAll", "", "idleConnectionCount", "pruneAndGetAllocationCount", "put", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class RealConnectionPool {
    public static final Companion Companion = new Companion(null);
    private final TaskQueue cleanupQueue;
    private final cleanupTask.1 cleanupTask;
    private final ConcurrentLinkedQueue<RealConnection> connections;
    private final long keepAliveDurationNs;
    private final int maxIdleConnections;

    public RealConnectionPool(TaskRunner object, int n, long l, TimeUnit timeUnit) {
        Intrinsics.checkParameterIsNotNull(object, "taskRunner");
        Intrinsics.checkParameterIsNotNull((Object)timeUnit, "timeUnit");
        this.maxIdleConnections = n;
        this.keepAliveDurationNs = timeUnit.toNanos(l);
        this.cleanupQueue = ((TaskRunner)object).newQueue();
        object = new StringBuilder();
        ((StringBuilder)object).append(Util.okHttpName);
        ((StringBuilder)object).append(" ConnectionPool");
        this.cleanupTask = new Task(this, ((StringBuilder)object).toString()){
            final /* synthetic */ RealConnectionPool this$0;
            {
                this.this$0 = realConnectionPool;
                super(string2, false, 2, null);
            }

            public long runOnce() {
                return this.this$0.cleanup(java.lang.System.nanoTime());
            }
        };
        this.connections = new ConcurrentLinkedQueue();
        if (l > 0L) {
            return;
        }
        n = 0;
        if (n != 0) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("keepAliveDuration <= 0: ");
        ((StringBuilder)object).append(l);
        throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
    }

    private final int pruneAndGetAllocationCount(RealConnection realConnection, long l) {
        block4 : {
            if (Util.assertionsEnabled && !Thread.holdsLock(realConnection)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Thread ");
                Thread thread2 = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
                stringBuilder.append(thread2.getName());
                stringBuilder.append(" MUST hold lock on ");
                stringBuilder.append(realConnection);
                throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
            }
            List<Reference<RealCall>> list = realConnection.getCalls();
            int n = 0;
            while (n < list.size()) {
                RealCall.CallReference callReference = list.get(n);
                if (callReference.get() != null) {
                    ++n;
                    continue;
                }
                if (callReference == null) throw new TypeCastException("null cannot be cast to non-null type okhttp3.internal.connection.RealCall.CallReference");
                callReference = callReference;
                CharSequence charSequence = new StringBuilder();
                charSequence.append("A connection to ");
                charSequence.append(realConnection.route().address().url());
                charSequence.append(" was leaked. ");
                charSequence.append("Did you forget to close a response body?");
                charSequence = charSequence.toString();
                Platform.Companion.get().logCloseableLeak((String)charSequence, callReference.getCallStackTrace());
                list.remove(n);
                realConnection.setNoNewExchanges(true);
                if (!list.isEmpty()) {
                    continue;
                }
                break block4;
            }
            return list.size();
        }
        realConnection.setIdleAtNs$okhttp(l - this.keepAliveDurationNs);
        return 0;
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public final boolean callAcquirePooledConnection(Address address, RealCall realCall, List<Route> list, boolean bl) {
        void var2_2;
        Intrinsics.checkParameterIsNotNull(address, "address");
        Intrinsics.checkParameterIsNotNull(var2_2, "call");
        Iterator<RealConnection> iterator2 = this.connections.iterator();
        while (iterator2.hasNext()) {
            RealConnection realConnection = iterator2.next();
            Intrinsics.checkExpressionValueIsNotNull(realConnection, "connection");
            synchronized (realConnection) {
                void var4_4;
                void var3_3;
                if ((var4_4 == false || realConnection.isMultiplexed$okhttp()) && realConnection.isEligible$okhttp(address, (List<Route>)var3_3)) {
                    var2_2.acquireConnectionNoEvents(realConnection);
                    return true;
                }
                Unit unit = Unit.INSTANCE;
            }
        }
        return false;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final long cleanup(long l) {
        long l2;
        Object object = null;
        Iterator<RealConnection> iterator2 = this.connections.iterator();
        int n = 0;
        long l3 = Long.MIN_VALUE;
        int n2 = 0;
        while (iterator2.hasNext()) {
            RealConnection realConnection = iterator2.next();
            Intrinsics.checkExpressionValueIsNotNull(realConnection, "connection");
            synchronized (realConnection) {
                if (this.pruneAndGetAllocationCount(realConnection, l) > 0) {
                    ++n2;
                } else {
                    ++n;
                    l2 = l - realConnection.getIdleAtNs$okhttp();
                    if (l2 > l3) {
                        object = Unit.INSTANCE;
                        object = realConnection;
                        l3 = l2;
                    } else {
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        }
        l2 = this.keepAliveDurationNs;
        if (l3 < l2 && n <= this.maxIdleConnections) {
            if (n > 0) {
                return l2 - l3;
            }
            if (n2 <= 0) return -1L;
            return l2;
        }
        if (object == null) {
            Intrinsics.throwNpe();
        }
        synchronized (object) {
            boolean bl = ((Collection)((RealConnection)object).getCalls()).isEmpty();
            if (bl ^ true) {
                return 0L;
            }
            l2 = ((RealConnection)object).getIdleAtNs$okhttp();
            if (l2 + l3 != l) {
                return 0L;
            }
            ((RealConnection)object).setNoNewExchanges(true);
            this.connections.remove(object);
        }
        Util.closeQuietly(((RealConnection)object).socket());
        if (!this.connections.isEmpty()) return 0L;
        this.cleanupQueue.cancelAll();
        return 0L;
    }

    public final boolean connectionBecameIdle(RealConnection realConnection) {
        Intrinsics.checkParameterIsNotNull(realConnection, "connection");
        if (Util.assertionsEnabled && !Thread.holdsLock(realConnection)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            Thread thread2 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
            stringBuilder.append(thread2.getName());
            stringBuilder.append(" MUST hold lock on ");
            stringBuilder.append(realConnection);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        boolean bl = realConnection.getNoNewExchanges();
        boolean bl2 = true;
        if (!bl && this.maxIdleConnections != 0) {
            TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
            return false;
        }
        realConnection.setNoNewExchanges(true);
        this.connections.remove(realConnection);
        bl = bl2;
        if (!this.connections.isEmpty()) return bl;
        this.cleanupQueue.cancelAll();
        return bl2;
    }

    public final int connectionCount() {
        return this.connections.size();
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void evictAll() {
        Iterator<RealConnection> iterator2 = this.connections.iterator();
        Intrinsics.checkExpressionValueIsNotNull(iterator2, "connections.iterator()");
        do {
            Socket socket;
            if (!iterator2.hasNext()) {
                if (!this.connections.isEmpty()) return;
                this.cleanupQueue.cancelAll();
                return;
            }
            RealConnection realConnection = iterator2.next();
            Intrinsics.checkExpressionValueIsNotNull(realConnection, "connection");
            synchronized (realConnection) {
                if (realConnection.getCalls().isEmpty()) {
                    iterator2.remove();
                    realConnection.setNoNewExchanges(true);
                    socket = realConnection.socket();
                } else {
                    socket = null;
                }
                if (socket == null) continue;
            }
            Util.closeQuietly(socket);
        } while (true);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final int idleConnectionCount() {
        Object object = this.connections;
        boolean bl = object instanceof Collection;
        int n = 0;
        int n2 = 0;
        if (bl && ((Collection)object).isEmpty()) {
            return n;
        }
        Iterator iterator2 = object.iterator();
        do {
            n = n2;
            if (!iterator2.hasNext()) return n;
            object = (RealConnection)iterator2.next();
            Intrinsics.checkExpressionValueIsNotNull(object, "it");
            synchronized (object) {
                bl = ((RealConnection)object).getCalls().isEmpty();
                if (!bl) continue;
            }
            n2 = n = n2 + 1;
            if (n >= 0) continue;
            CollectionsKt.throwCountOverflow();
            n2 = n;
        } while (true);
    }

    public final void put(RealConnection realConnection) {
        Intrinsics.checkParameterIsNotNull(realConnection, "connection");
        if (Util.assertionsEnabled && !Thread.holdsLock(realConnection)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            Thread thread2 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
            stringBuilder.append(thread2.getName());
            stringBuilder.append(" MUST hold lock on ");
            stringBuilder.append(realConnection);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        this.connections.add(realConnection);
        TaskQueue.schedule$default(this.cleanupQueue, this.cleanupTask, 0L, 2, null);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2={"Lokhttp3/internal/connection/RealConnectionPool$Companion;", "", "()V", "get", "Lokhttp3/internal/connection/RealConnectionPool;", "connectionPool", "Lokhttp3/ConnectionPool;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final RealConnectionPool get(ConnectionPool connectionPool) {
            Intrinsics.checkParameterIsNotNull(connectionPool, "connectionPool");
            return connectionPool.getDelegate$okhttp();
        }
    }

}

