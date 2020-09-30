/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.DirectExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import sun.misc.Unsafe;

public abstract class AbstractFuture<V>
extends InternalFutureFailureAccess
implements ListenableFuture<V> {
    private static final AtomicHelper ATOMIC_HELPER;
    private static final boolean GENERATE_CANCELLATION_CAUSES;
    private static final Object NULL;
    private static final long SPIN_THRESHOLD_NANOS = 1000L;
    private static final Logger log;
    @NullableDecl
    private volatile Listener listeners;
    @NullableDecl
    private volatile Object value;
    @NullableDecl
    private volatile Waiter waiters;

    static {
        Throwable throwable;
        AtomicHelper atomicHelper;
        GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
        log = Logger.getLogger(AbstractFuture.class.getName());
        Throwable throwable2 = null;
        try {
            atomicHelper = new UnsafeAtomicHelper();
            throwable = null;
        }
        catch (Throwable throwable3) {
            try {
                atomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Thread.class, "thread"), AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Waiter.class, "next"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Waiter.class, "waiters"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Listener.class, "listeners"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Object.class, "value"));
            }
            catch (Throwable throwable4) {
                atomicHelper = new SynchronizedHelper();
            }
        }
        ATOMIC_HELPER = atomicHelper;
        if (throwable2 != null) {
            log.log(Level.SEVERE, "UnsafeAtomicHelper is broken!", throwable);
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", throwable2);
        }
        NULL = new Object();
    }

    protected AbstractFuture() {
    }

    private void addDoneString(StringBuilder stringBuilder) {
        try {
            V v = AbstractFuture.getUninterruptibly(this);
            stringBuilder.append("SUCCESS, result=[");
            this.appendUserObject(stringBuilder, v);
            stringBuilder.append("]");
            return;
        }
        catch (RuntimeException runtimeException) {
            stringBuilder.append("UNKNOWN, cause=[");
            stringBuilder.append(runtimeException.getClass());
            stringBuilder.append(" thrown from get()]");
            return;
        }
        catch (CancellationException cancellationException) {
            stringBuilder.append("CANCELLED");
            return;
        }
        catch (ExecutionException executionException) {
            stringBuilder.append("FAILURE, cause=[");
            stringBuilder.append(executionException.getCause());
            stringBuilder.append("]");
        }
    }

    private void addPendingString(StringBuilder stringBuilder) {
        int n = stringBuilder.length();
        stringBuilder.append("PENDING");
        Object object = this.value;
        if (object instanceof SetFuture) {
            stringBuilder.append(", setFuture=[");
            this.appendUserObject(stringBuilder, ((SetFuture)object).future);
            stringBuilder.append("]");
        } else {
            block6 : {
                try {
                    object = Strings.emptyToNull(this.pendingToString());
                    break block6;
                }
                catch (StackOverflowError stackOverflowError) {
                }
                catch (RuntimeException runtimeException) {
                    // empty catch block
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Exception thrown from implementation: ");
                stringBuilder2.append(object.getClass());
                object = stringBuilder2.toString();
            }
            if (object != null) {
                stringBuilder.append(", info=[");
                stringBuilder.append((String)object);
                stringBuilder.append("]");
            }
        }
        if (!this.isDone()) return;
        stringBuilder.delete(n, stringBuilder.length());
        this.addDoneString(stringBuilder);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void appendUserObject(StringBuilder var1_1, Object var2_2) {
        if (var2_2 != this) ** GOTO lbl6
        try {
            var1_1.append("this future");
            return;
lbl6: // 1 sources:
            var1_1.append(var2_2);
            return;
        }
        catch (StackOverflowError var2_3) {
        }
        catch (RuntimeException var2_4) {
            // empty catch block
        }
        var1_1.append("Exception thrown from implementation: ");
        var1_1.append(var2_5.getClass());
    }

    private static CancellationException cancellationExceptionWithCause(@NullableDecl String object, @NullableDecl Throwable throwable) {
        object = new CancellationException((String)object);
        ((Throwable)object).initCause(throwable);
        return object;
    }

    private Listener clearListeners(Listener listener) {
        Listener listener2;
        while (!ATOMIC_HELPER.casListeners(this, listener2 = this.listeners, Listener.TOMBSTONE)) {
        }
        Listener listener3 = listener;
        listener = listener2;
        while (listener != null) {
            listener2 = listener.next;
            listener.next = listener3;
            listener3 = listener;
            listener = listener2;
        }
        return listener3;
    }

    private static void complete(AbstractFuture<?> object) {
        Object object2 = null;
        block0 : do {
            AbstractFuture.super.releaseWaiters();
            ((AbstractFuture)object).afterDone();
            object2 = AbstractFuture.super.clearListeners((Listener)object2);
            while (object2 != null) {
                object = ((Listener)object2).next;
                Object object3 = ((Listener)object2).task;
                if (object3 instanceof SetFuture) {
                    Object object4;
                    object2 = (SetFuture)object3;
                    object3 = ((SetFuture)object2).owner;
                    if (((AbstractFuture)object3).value == object2 && ATOMIC_HELPER.casValue((AbstractFuture<?>)object3, object2, object4 = AbstractFuture.getFutureValue(((SetFuture)object2).future))) {
                        object2 = object;
                        object = object3;
                        continue block0;
                    }
                } else {
                    AbstractFuture.executeListener((Runnable)object3, ((Listener)object2).executor);
                }
                object2 = object;
            }
            return;
            break;
        } while (true);
    }

    private static void executeListener(Runnable runnable2, Executor executor) {
        try {
            executor.execute(runnable2);
            return;
        }
        catch (RuntimeException runtimeException) {
            Logger logger = log;
            Level level = Level.SEVERE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RuntimeException while executing runnable ");
            stringBuilder.append(runnable2);
            stringBuilder.append(" with executor ");
            stringBuilder.append(executor);
            logger.log(level, stringBuilder.toString(), runtimeException);
        }
    }

    private V getDoneValue(Object object) throws ExecutionException {
        if (object instanceof Cancellation) throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", ((Cancellation)object).cause);
        if (object instanceof Failure) throw new ExecutionException(((Failure)object).exception);
        Object object2 = object;
        if (object != NULL) return (V)object2;
        object2 = null;
        return (V)object2;
    }

    private static Object getFutureValue(ListenableFuture<?> object) {
        Object object2;
        if (object instanceof Trusted) {
            Object object3;
            object = object3 = ((AbstractFuture)object).value;
            if (!(object3 instanceof Cancellation)) return object;
            Cancellation cancellation = (Cancellation)object3;
            object = object3;
            if (!cancellation.wasInterrupted) return object;
            if (cancellation.cause == null) return Cancellation.CAUSELESS_CANCELLED;
            return new Cancellation(false, cancellation.cause);
        }
        if (object instanceof InternalFutureFailureAccess && (object2 = InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess)object)) != null) {
            return new Failure((Throwable)object2);
        }
        boolean bl = object.isCancelled();
        if ((GENERATE_CANCELLATION_CAUSES ^ true) & bl) {
            return Cancellation.CAUSELESS_CANCELLED;
        }
        try {
            Object object4 = AbstractFuture.getUninterruptibly(object);
            if (bl) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("get() did not throw CancellationException, despite reporting isCancelled() == true: ");
                ((StringBuilder)object2).append(object);
                object4 = new Object(((StringBuilder)object2).toString());
                return new Cancellation(false, (Throwable)object4);
            }
            object2 = object4;
            if (object4 != null) return object2;
            return NULL;
        }
        catch (Throwable throwable) {
            return new Failure(throwable);
        }
        catch (CancellationException cancellationException) {
            if (bl) return new Cancellation(false, cancellationException);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("get() threw CancellationException, despite reporting isCancelled() == false: ");
            ((StringBuilder)object2).append(object);
            return new Failure(new IllegalArgumentException(((StringBuilder)object2).toString(), cancellationException));
        }
        catch (ExecutionException executionException) {
            if (!bl) return new Failure(executionException.getCause());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("get() did not throw CancellationException, despite reporting isCancelled() == true: ");
            ((StringBuilder)object2).append(object);
            return new Cancellation(false, new IllegalArgumentException(((StringBuilder)object2).toString(), executionException));
        }
    }

    private static <V> V getUninterruptibly(Future<V> future) throws ExecutionException {
        boolean bl = false;
        do {
            V v;
            try {
                v = future.get();
                if (!bl) return v;
                Thread.currentThread().interrupt();
            }
            catch (Throwable throwable) {
                if (!bl) throw throwable;
                Thread.currentThread().interrupt();
                throw throwable;
            }
            catch (InterruptedException interruptedException) {
                bl = true;
                continue;
            }
            return v;
            break;
        } while (true);
    }

    private void releaseWaiters() {
        Waiter waiter;
        while (!ATOMIC_HELPER.casWaiters(this, waiter = this.waiters, Waiter.TOMBSTONE)) {
        }
        while (waiter != null) {
            waiter.unpark();
            waiter = waiter.next;
        }
    }

    /*
     * Unable to fully structure code
     */
    private void removeWaiter(Waiter var1_1) {
        var1_1.thread = null;
        block0 : do {
            if ((var1_1 = this.waiters) == Waiter.TOMBSTONE) {
                return;
            }
            var2_2 = null;
            while (var1_1 != null) {
                var3_3 = var1_1.next;
                if (var1_1.thread != null) {
                    var4_4 = var1_1;
                } else if (var2_2 != null) {
                    var2_2.next = var3_3;
                    var4_4 = var2_2;
                    if (var2_2.thread == null) {
                        continue block0;
                    }
                } else {
                    var4_4 = var2_2;
                    if (AbstractFuture.ATOMIC_HELPER.casWaiters(this, var1_1, var3_3)) ** break;
                    continue block0;
                }
                var1_1 = var3_3;
                var2_2 = var4_4;
            }
            return;
            break;
        } while (true);
    }

    @Override
    public void addListener(Runnable runnable2, Executor executor) {
        Listener listener;
        Preconditions.checkNotNull(runnable2, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        if (!this.isDone() && (listener = this.listeners) != Listener.TOMBSTONE) {
            Listener listener2;
            Listener listener3 = new Listener(runnable2, executor);
            do {
                listener3.next = listener;
                if (ATOMIC_HELPER.casListeners(this, listener, listener3)) {
                    return;
                }
                listener = listener2 = this.listeners;
            } while (listener2 != Listener.TOMBSTONE);
        }
        AbstractFuture.executeListener(runnable2, executor);
    }

    protected void afterDone() {
    }

    @Override
    public boolean cancel(boolean bl) {
        Object object = this.value;
        boolean bl2 = true;
        boolean bl3 = object == null;
        if (!(bl3 | object instanceof SetFuture)) {
            return false;
        }
        Cancellation cancellation = GENERATE_CANCELLATION_CAUSES ? new Cancellation(bl, new CancellationException("Future.cancel() was called.")) : (bl ? Cancellation.CAUSELESS_INTERRUPTED : Cancellation.CAUSELESS_CANCELLED);
        boolean bl4 = false;
        AbstractFuture abstractFuture = this;
        do {
            Object object2;
            if (ATOMIC_HELPER.casValue(abstractFuture, object, cancellation)) {
                if (bl) {
                    abstractFuture.interruptTask();
                }
                AbstractFuture.complete(abstractFuture);
                bl4 = bl2;
                if (!(object instanceof SetFuture)) return bl4;
                object = ((SetFuture)object).future;
                if (!(object instanceof Trusted)) {
                    object.cancel(bl);
                    return bl2;
                }
                abstractFuture = (AbstractFuture)object;
                object = abstractFuture.value;
                bl3 = object == null;
                bl4 = bl2;
                if (!(bl3 | object instanceof SetFuture)) return bl4;
                bl4 = true;
                continue;
            }
            object = object2 = abstractFuture.value;
            if (!(object2 instanceof SetFuture)) return bl4;
        } while (true);
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        Object object;
        Waiter waiter;
        boolean bl;
        block3 : {
            Waiter waiter2;
            if (Thread.interrupted()) throw new InterruptedException();
            object = this.value;
            bl = object != null;
            if (bl & (object instanceof SetFuture ^ true)) {
                return this.getDoneValue(object);
            }
            object = this.waiters;
            if (object == Waiter.TOMBSTONE) return this.getDoneValue(this.value);
            waiter = new Waiter();
            do {
                waiter.setNext((Waiter)object);
                if (ATOMIC_HELPER.casWaiters(this, (Waiter)object, waiter)) break block3;
                waiter2 = this.waiters;
                object = waiter2;
            } while (waiter2 != Waiter.TOMBSTONE);
            return this.getDoneValue(this.value);
        }
        do {
            LockSupport.park(this);
            if (!Thread.interrupted()) continue;
            this.removeWaiter(waiter);
            throw new InterruptedException();
        } while (!((bl = (object = this.value) != null) & (object instanceof SetFuture ^ true)));
        return this.getDoneValue(object);
    }

    @Override
    public V get(long l, TimeUnit object) throws InterruptedException, TimeoutException, ExecutionException {
        Object object2;
        Object object3;
        long l2 = ((TimeUnit)((Object)object)).toNanos(l);
        if (Thread.interrupted()) throw new InterruptedException();
        Object object4 = this.value;
        boolean bl = object4 != null;
        if (bl & (object4 instanceof SetFuture ^ true)) {
            return this.getDoneValue(object4);
        }
        long l3 = l2 > 0L ? System.nanoTime() + l2 : 0L;
        long l4 = l2;
        if (l2 >= 1000L) {
            block13 : {
                object4 = this.waiters;
                if (object4 == Waiter.TOMBSTONE) return this.getDoneValue(this.value);
                object2 = new Waiter();
                do {
                    ((Waiter)object2).setNext((Waiter)object4);
                    if (ATOMIC_HELPER.casWaiters(this, (Waiter)object4, (Waiter)object2)) break block13;
                    object4 = object3 = this.waiters;
                } while (object3 != Waiter.TOMBSTONE);
                return this.getDoneValue(this.value);
            }
            do {
                LockSupport.parkNanos(this, l2);
                if (Thread.interrupted()) {
                    this.removeWaiter((Waiter)object2);
                    throw new InterruptedException();
                }
                object4 = this.value;
                bl = object4 != null;
                if (bl & (object4 instanceof SetFuture ^ true)) {
                    return this.getDoneValue(object4);
                }
                l2 = l4 = l3 - System.nanoTime();
            } while (l4 >= 1000L);
            this.removeWaiter((Waiter)object2);
        }
        while (l4 > 0L) {
            object4 = this.value;
            bl = object4 != null;
            if (bl & (object4 instanceof SetFuture ^ true)) {
                return this.getDoneValue(object4);
            }
            if (Thread.interrupted()) throw new InterruptedException();
            l4 = l3 - System.nanoTime();
        }
        object2 = this.toString();
        String string2 = ((Enum)object).toString().toLowerCase(Locale.ROOT);
        object4 = new StringBuilder();
        ((StringBuilder)object4).append("Waited ");
        ((StringBuilder)object4).append(l);
        ((StringBuilder)object4).append(" ");
        ((StringBuilder)object4).append(((Enum)object).toString().toLowerCase(Locale.ROOT));
        object4 = object3 = ((StringBuilder)object4).toString();
        if (l4 + 1000L < 0L) {
            object4 = new StringBuilder();
            ((StringBuilder)object4).append((String)object3);
            ((StringBuilder)object4).append(" (plus ");
            object4 = ((StringBuilder)object4).toString();
            l4 = -l4;
            l = ((TimeUnit)((Object)object)).convert(l4, TimeUnit.NANOSECONDS);
            long l5 = l LCMP 0L;
            bl = l5 == false || (l4 -= ((TimeUnit)((Object)object)).toNanos(l)) > 1000L;
            object = object4;
            if (l5 > 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object4);
                ((StringBuilder)object).append(l);
                ((StringBuilder)object).append(" ");
                ((StringBuilder)object).append(string2);
                object = object4 = ((StringBuilder)object).toString();
                if (bl) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append((String)object4);
                    ((StringBuilder)object).append(",");
                    object = ((StringBuilder)object).toString();
                }
                object4 = new StringBuilder();
                ((StringBuilder)object4).append((String)object);
                ((StringBuilder)object4).append(" ");
                object = ((StringBuilder)object4).toString();
            }
            object4 = object;
            if (bl) {
                object4 = new StringBuilder();
                ((StringBuilder)object4).append((String)object);
                ((StringBuilder)object4).append(l4);
                ((StringBuilder)object4).append(" nanoseconds ");
                object4 = ((StringBuilder)object4).toString();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object4);
            ((StringBuilder)object).append("delay)");
            object4 = ((StringBuilder)object).toString();
        }
        if (this.isDone()) {
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object4);
            ((StringBuilder)object).append(" but future completed as timeout expired");
            throw new TimeoutException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append((String)object4);
        ((StringBuilder)object).append(" for ");
        ((StringBuilder)object).append((String)object2);
        throw new TimeoutException(((StringBuilder)object).toString());
    }

    protected void interruptTask() {
    }

    @Override
    public boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    @Override
    public boolean isDone() {
        boolean bl;
        Object object = this.value;
        if (object != null) {
            bl = true;
            return (object instanceof SetFuture ^ true) & bl;
        }
        bl = false;
        return (object instanceof SetFuture ^ true) & bl;
    }

    final void maybePropagateCancellationTo(@NullableDecl Future<?> future) {
        boolean bl = future != null;
        if (!(bl & this.isCancelled())) return;
        future.cancel(this.wasInterrupted());
    }

    @NullableDecl
    protected String pendingToString() {
        if (!(this instanceof ScheduledFuture)) return null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("remaining delay=[");
        stringBuilder.append(((ScheduledFuture)((Object)this)).getDelay(TimeUnit.MILLISECONDS));
        stringBuilder.append(" ms]");
        return stringBuilder.toString();
    }

    protected boolean set(@NullableDecl V v) {
        Object object = v;
        if (v == null) {
            object = NULL;
        }
        if (!ATOMIC_HELPER.casValue(this, null, object)) return false;
        AbstractFuture.complete(this);
        return true;
    }

    protected boolean setException(Throwable object) {
        if (!ATOMIC_HELPER.casValue(this, null, object = new Failure(Preconditions.checkNotNull(object)))) return false;
        AbstractFuture.complete(this);
        return true;
    }

    protected boolean setFuture(ListenableFuture<? extends V> object) {
        Preconditions.checkNotNull(object);
        SetFuture setFuture = this.value;
        SetFuture setFuture2 = setFuture;
        if (setFuture == null) {
            if (object.isDone()) {
                if (!ATOMIC_HELPER.casValue(this, null, object = AbstractFuture.getFutureValue(object))) return false;
                AbstractFuture.complete(this);
                return true;
            }
            setFuture2 = new SetFuture(this, object);
            if (ATOMIC_HELPER.casValue(this, null, setFuture2)) {
                try {
                    object.addListener(setFuture2, DirectExecutor.INSTANCE);
                    return true;
                }
                catch (Throwable throwable) {
                    try {
                        object = new Failure(throwable);
                    }
                    catch (Throwable throwable2) {
                        object = Failure.FALLBACK_INSTANCE;
                    }
                    ATOMIC_HELPER.casValue(this, setFuture2, object);
                }
                return true;
            }
            setFuture2 = this.value;
        }
        if (!(setFuture2 instanceof Cancellation)) return false;
        object.cancel(((Cancellation)setFuture2).wasInterrupted);
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Object.super.toString());
        stringBuilder.append("[status=");
        if (this.isCancelled()) {
            stringBuilder.append("CANCELLED");
        } else if (this.isDone()) {
            this.addDoneString(stringBuilder);
        } else {
            this.addPendingString(stringBuilder);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @NullableDecl
    @Override
    protected final Throwable tryInternalFastPathGetFailure() {
        if (!(this instanceof Trusted)) return null;
        Object object = this.value;
        if (!(object instanceof Failure)) return null;
        return ((Failure)object).exception;
    }

    protected final boolean wasInterrupted() {
        Object object = this.value;
        if (!(object instanceof Cancellation)) return false;
        if (!((Cancellation)object).wasInterrupted) return false;
        return true;
    }

    private static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        abstract boolean casListeners(AbstractFuture<?> var1, Listener var2, Listener var3);

        abstract boolean casValue(AbstractFuture<?> var1, Object var2, Object var3);

        abstract boolean casWaiters(AbstractFuture<?> var1, Waiter var2, Waiter var3);

        abstract void putNext(Waiter var1, Waiter var2);

        abstract void putThread(Waiter var1, Thread var2);
    }

    private static final class Cancellation {
        static final Cancellation CAUSELESS_CANCELLED;
        static final Cancellation CAUSELESS_INTERRUPTED;
        @NullableDecl
        final Throwable cause;
        final boolean wasInterrupted;

        static {
            if (GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
                return;
            }
            CAUSELESS_CANCELLED = new Cancellation(false, null);
            CAUSELESS_INTERRUPTED = new Cancellation(true, null);
        }

        Cancellation(boolean bl, @NullableDecl Throwable throwable) {
            this.wasInterrupted = bl;
            this.cause = throwable;
        }
    }

    private static final class Failure {
        static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future."){

            /*
             * Converted monitor instructions to comments
             */
            @Override
            public Throwable fillInStackTrace() {
                // MONITORENTER : this
                // MONITOREXIT : this
                return this;
            }
        });
        final Throwable exception;

        Failure(Throwable throwable) {
            this.exception = Preconditions.checkNotNull(throwable);
        }

    }

    private static final class Listener {
        static final Listener TOMBSTONE = new Listener(null, null);
        final Executor executor;
        @NullableDecl
        Listener next;
        final Runnable task;

        Listener(Runnable runnable2, Executor executor) {
            this.task = runnable2;
            this.executor = executor;
        }
    }

    private static final class SafeAtomicHelper
    extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AbstractFuture, Listener> listenersUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, Object> valueUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, Waiter> waitersUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<Waiter, Waiter> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractFuture, Waiter> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractFuture, Listener> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<AbstractFuture, Object> atomicReferenceFieldUpdater5) {
            this.waiterThreadUpdater = atomicReferenceFieldUpdater;
            this.waiterNextUpdater = atomicReferenceFieldUpdater2;
            this.waitersUpdater = atomicReferenceFieldUpdater3;
            this.listenersUpdater = atomicReferenceFieldUpdater4;
            this.valueUpdater = atomicReferenceFieldUpdater5;
        }

        @Override
        boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            return this.listenersUpdater.compareAndSet(abstractFuture, listener, listener2);
        }

        @Override
        boolean casValue(AbstractFuture<?> abstractFuture, Object object, Object object2) {
            return this.valueUpdater.compareAndSet(abstractFuture, object, object2);
        }

        @Override
        boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            return this.waitersUpdater.compareAndSet(abstractFuture, waiter, waiter2);
        }

        @Override
        void putNext(Waiter waiter, Waiter waiter2) {
            this.waiterNextUpdater.lazySet(waiter, waiter2);
        }

        @Override
        void putThread(Waiter waiter, Thread thread2) {
            this.waiterThreadUpdater.lazySet(waiter, thread2);
        }
    }

    private static final class SetFuture<V>
    implements Runnable {
        final ListenableFuture<? extends V> future;
        final AbstractFuture<V> owner;

        SetFuture(AbstractFuture<V> abstractFuture, ListenableFuture<? extends V> listenableFuture) {
            this.owner = abstractFuture;
            this.future = listenableFuture;
        }

        @Override
        public void run() {
            if (this.owner.value != this) {
                return;
            }
            Object object = AbstractFuture.getFutureValue(this.future);
            if (!ATOMIC_HELPER.casValue(this.owner, this, object)) return;
            AbstractFuture.complete(this.owner);
        }
    }

    private static final class SynchronizedHelper
    extends AtomicHelper {
        private SynchronizedHelper() {
        }

        @Override
        boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            synchronized (abstractFuture) {
                if (abstractFuture.listeners != listener) return false;
                abstractFuture.listeners = listener2;
                return true;
            }
        }

        @Override
        boolean casValue(AbstractFuture<?> abstractFuture, Object object, Object object2) {
            synchronized (abstractFuture) {
                if (abstractFuture.value != object) return false;
                abstractFuture.value = object2;
                return true;
            }
        }

        @Override
        boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractFuture) {
                if (abstractFuture.waiters != waiter) return false;
                abstractFuture.waiters = waiter2;
                return true;
            }
        }

        @Override
        void putNext(Waiter waiter, Waiter waiter2) {
            waiter.next = waiter2;
        }

        @Override
        void putThread(Waiter waiter, Thread thread2) {
            waiter.thread = thread2;
        }
    }

    static interface Trusted<V>
    extends ListenableFuture<V> {
    }

    static abstract class TrustedFuture<V>
    extends AbstractFuture<V>
    implements Trusted<V> {
        TrustedFuture() {
        }

        @Override
        public final void addListener(Runnable runnable2, Executor executor) {
            super.addListener(runnable2, executor);
        }

        @Override
        public final boolean cancel(boolean bl) {
            return super.cancel(bl);
        }

        @Override
        public final V get() throws InterruptedException, ExecutionException {
            return super.get();
        }

        @Override
        public final V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return super.get(l, timeUnit);
        }

        @Override
        public final boolean isCancelled() {
            return super.isCancelled();
        }

        @Override
        public final boolean isDone() {
            return super.isDone();
        }
    }

    private static final class UnsafeAtomicHelper
    extends AtomicHelper {
        static final long LISTENERS_OFFSET;
        static final Unsafe UNSAFE;
        static final long VALUE_OFFSET;
        static final long WAITERS_OFFSET;
        static final long WAITER_NEXT_OFFSET;
        static final long WAITER_THREAD_OFFSET;

        static {
            PrivilegedExceptionAction<Unsafe> privilegedExceptionAction;
            try {
                privilegedExceptionAction = Unsafe.getUnsafe();
            }
            catch (SecurityException securityException) {
                try {
                    privilegedExceptionAction = new PrivilegedExceptionAction<Unsafe>(){

                        @Override
                        public Unsafe run() throws Exception {
                            Field[] arrfield = Unsafe.class.getDeclaredFields();
                            int n = arrfield.length;
                            int n2 = 0;
                            while (n2 < n) {
                                Object object = arrfield[n2];
                                ((Field)object).setAccessible(true);
                                object = ((Field)object).get(null);
                                if (Unsafe.class.isInstance(object)) {
                                    return (Unsafe)Unsafe.class.cast(object);
                                }
                                ++n2;
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }
                    };
                    privilegedExceptionAction = (Unsafe)AccessController.doPrivileged(privilegedExceptionAction);
                }
                catch (PrivilegedActionException privilegedActionException) {
                    throw new RuntimeException("Could not initialize intrinsics", privilegedActionException.getCause());
                }
            }
            try {
                WAITERS_OFFSET = ((Unsafe)((Object)privilegedExceptionAction)).objectFieldOffset(AbstractFuture.class.getDeclaredField("waiters"));
                LISTENERS_OFFSET = ((Unsafe)((Object)privilegedExceptionAction)).objectFieldOffset(AbstractFuture.class.getDeclaredField("listeners"));
                VALUE_OFFSET = ((Unsafe)((Object)privilegedExceptionAction)).objectFieldOffset(AbstractFuture.class.getDeclaredField("value"));
                WAITER_THREAD_OFFSET = ((Unsafe)((Object)privilegedExceptionAction)).objectFieldOffset(Waiter.class.getDeclaredField("thread"));
                WAITER_NEXT_OFFSET = ((Unsafe)((Object)privilegedExceptionAction)).objectFieldOffset(Waiter.class.getDeclaredField("next"));
                UNSAFE = privilegedExceptionAction;
                return;
            }
            catch (Exception exception) {
                Throwables.throwIfUnchecked(exception);
                throw new RuntimeException(exception);
            }
        }

        private UnsafeAtomicHelper() {
        }

        @Override
        boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, LISTENERS_OFFSET, listener, listener2);
        }

        @Override
        boolean casValue(AbstractFuture<?> abstractFuture, Object object, Object object2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, VALUE_OFFSET, object, object2);
        }

        @Override
        boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, WAITERS_OFFSET, waiter, waiter2);
        }

        @Override
        void putNext(Waiter waiter, Waiter waiter2) {
            UNSAFE.putObject(waiter, WAITER_NEXT_OFFSET, waiter2);
        }

        @Override
        void putThread(Waiter waiter, Thread thread2) {
            UNSAFE.putObject(waiter, WAITER_THREAD_OFFSET, thread2);
        }

    }

    private static final class Waiter {
        static final Waiter TOMBSTONE = new Waiter(false);
        @NullableDecl
        volatile Waiter next;
        @NullableDecl
        volatile Thread thread;

        Waiter() {
            ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }

        Waiter(boolean bl) {
        }

        void setNext(Waiter waiter) {
            ATOMIC_HELPER.putNext(this, waiter);
        }

        void unpark() {
            Thread thread2 = this.thread;
            if (thread2 == null) return;
            this.thread = null;
            LockSupport.unpark(thread2);
        }
    }

}

