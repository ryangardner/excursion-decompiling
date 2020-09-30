/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.grpc;

import io.grpc.Deadline;
import io.grpc.PersistentHashArrayMappedTrie;
import io.grpc.ThreadLocalContextStorage;
import java.io.Closeable;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Context {
    static final int CONTEXT_DEPTH_WARN_THRESH = 1000;
    private static final PersistentHashArrayMappedTrie<Key<?>, Object> EMPTY_ENTRIES;
    public static final Context ROOT;
    static final Logger log;
    final CancellableContext cancellableAncestor;
    final int generation;
    final PersistentHashArrayMappedTrie<Key<?>, Object> keyValueEntries;
    private ArrayList<ExecutableListener> listeners;
    private CancellationListener parentListener = new ParentListener();

    static {
        log = Logger.getLogger(Context.class.getName());
        EMPTY_ENTRIES = new PersistentHashArrayMappedTrie();
        ROOT = new Context(null, EMPTY_ENTRIES);
    }

    private Context(Context context, PersistentHashArrayMappedTrie<Key<?>, Object> persistentHashArrayMappedTrie) {
        this.cancellableAncestor = Context.cancellableAncestor(context);
        this.keyValueEntries = persistentHashArrayMappedTrie;
        int n = context == null ? 0 : context.generation + 1;
        this.generation = n;
        Context.validateGeneration(n);
    }

    private Context(PersistentHashArrayMappedTrie<Key<?>, Object> persistentHashArrayMappedTrie, int n) {
        this.cancellableAncestor = null;
        this.keyValueEntries = persistentHashArrayMappedTrie;
        this.generation = n;
        Context.validateGeneration(n);
    }

    static CancellableContext cancellableAncestor(Context context) {
        if (context == null) {
            return null;
        }
        if (!(context instanceof CancellableContext)) return context.cancellableAncestor;
        return (CancellableContext)context;
    }

    static <T> T checkNotNull(T t, Object object) {
        if (t == null) throw new NullPointerException(String.valueOf(object));
        return t;
    }

    public static Context current() {
        Context context;
        Context context2 = context = Context.storage().current();
        if (context != null) return context2;
        return ROOT;
    }

    public static Executor currentContextExecutor(Executor executor) {
        return executor.new 1CurrentContextExecutor();
    }

    public static <T> Key<T> key(String string2) {
        return new Key(string2);
    }

    public static <T> Key<T> keyWithDefault(String string2, T t) {
        return new Key<T>(string2, t);
    }

    static Storage storage() {
        return LazyStorage.storage;
    }

    private static void validateGeneration(int n) {
        if (n != 1000) return;
        log.log(Level.SEVERE, "Context ancestry chain length is abnormally long. This suggests an error in application code. Length exceeded: 1000", new Exception());
    }

    public void addListener(CancellationListener arrayList, Executor object) {
        Context.checkNotNull(arrayList, "cancellationListener");
        Context.checkNotNull(object, "executor");
        if (!this.canBeCancelled()) return;
        object = new ExecutableListener((Executor)object, (CancellationListener)((Object)arrayList));
        synchronized (this) {
            if (this.isCancelled()) {
                ((ExecutableListener)object).deliver();
            } else if (this.listeners == null) {
                arrayList = new ArrayList<Object>();
                this.listeners = arrayList;
                arrayList.add(object);
                if (this.cancellableAncestor == null) return;
                this.cancellableAncestor.addListener(this.parentListener, DirectExecutor.INSTANCE);
            } else {
                this.listeners.add((ExecutableListener)object);
            }
            return;
        }
    }

    public Context attach() {
        Context context;
        Context context2 = context = Context.storage().doAttach(this);
        if (context != null) return context2;
        return ROOT;
    }

    public <V> V call(Callable<V> callable) throws Exception {
        Context context = this.attach();
        try {
            callable = callable.call();
            return (V)callable;
        }
        finally {
            this.detach(context);
        }
    }

    boolean canBeCancelled() {
        if (this.cancellableAncestor == null) return false;
        return true;
    }

    public Throwable cancellationCause() {
        CancellableContext cancellableContext = this.cancellableAncestor;
        if (cancellableContext != null) return cancellableContext.cancellationCause();
        return null;
    }

    public void detach(Context context) {
        Context.checkNotNull(context, "toAttach");
        Context.storage().detach(this, context);
    }

    public Executor fixedContextExecutor(Executor executor) {
        return new 1FixedContextExecutor(this, executor);
    }

    public Context fork() {
        return new Context(this.keyValueEntries, this.generation + 1);
    }

    public Deadline getDeadline() {
        CancellableContext cancellableContext = this.cancellableAncestor;
        if (cancellableContext != null) return cancellableContext.getDeadline();
        return null;
    }

    public boolean isCancelled() {
        CancellableContext cancellableContext = this.cancellableAncestor;
        if (cancellableContext != null) return cancellableContext.isCancelled();
        return false;
    }

    boolean isCurrent() {
        if (Context.current() != this) return false;
        return true;
    }

    int listenerCount() {
        synchronized (this) {
            if (this.listeners != null) return this.listeners.size();
            return 0;
        }
    }

    Object lookup(Key<?> key) {
        return this.keyValueEntries.get(key);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    void notifyAndClearListeners() {
        int n;
        Object object;
        if (!this.canBeCancelled()) {
            return;
        }
        synchronized (this) {
            if (this.listeners == null) {
                return;
            }
            object = this.listeners;
            this.listeners = null;
        }
        int n2 = 0;
        int n3 = 0;
        do {
            n = n2;
            if (n3 >= ((ArrayList)object).size()) break;
            if (!(object.get((int)n3).listener instanceof ParentListener)) {
                ((ExecutableListener)((ArrayList)object).get(n3)).deliver();
            }
            ++n3;
        } while (true);
        do {
            if (n >= ((ArrayList)object).size()) {
                object = this.cancellableAncestor;
                if (object == null) return;
                ((Context)object).removeListener(this.parentListener);
                return;
            }
            if (object.get((int)n).listener instanceof ParentListener) {
                ((ExecutableListener)((ArrayList)object).get(n)).deliver();
            }
            ++n;
        } while (true);
    }

    public void removeListener(CancellationListener cancellationListener) {
        if (!this.canBeCancelled()) {
            return;
        }
        synchronized (this) {
            if (this.listeners == null) return;
            for (int i = this.listeners.size() - 1; i >= 0; --i) {
                if (this.listeners.get((int)i).listener != cancellationListener) continue;
                this.listeners.remove(i);
                break;
            }
            if (!this.listeners.isEmpty()) return;
            if (this.cancellableAncestor != null) {
                this.cancellableAncestor.removeListener(this.parentListener);
            }
            this.listeners = null;
            return;
        }
    }

    public void run(Runnable runnable2) {
        Context context = this.attach();
        try {
            runnable2.run();
            return;
        }
        finally {
            this.detach(context);
        }
    }

    public CancellableContext withCancellation() {
        return new CancellableContext(this);
    }

    public CancellableContext withDeadline(Deadline deadline, ScheduledExecutorService scheduledExecutorService) {
        Context.checkNotNull(deadline, "deadline");
        Context.checkNotNull(scheduledExecutorService, "scheduler");
        return new CancellableContext(this, deadline, scheduledExecutorService);
    }

    public CancellableContext withDeadlineAfter(long l, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        return this.withDeadline(Deadline.after(l, timeUnit), scheduledExecutorService);
    }

    public <V> Context withValue(Key<V> key, V v) {
        return new Context(this, this.keyValueEntries.put(key, v));
    }

    public <V1, V2> Context withValues(Key<V1> key, V1 V1, Key<V2> key2, V2 V2) {
        return new Context(this, this.keyValueEntries.put(key, V1).put(key2, V2));
    }

    public <V1, V2, V3> Context withValues(Key<V1> key, V1 V1, Key<V2> key2, V2 V2, Key<V3> key3, V3 V3) {
        return new Context(this, this.keyValueEntries.put(key, V1).put(key2, V2).put(key3, V3));
    }

    public <V1, V2, V3, V4> Context withValues(Key<V1> key, V1 V1, Key<V2> key2, V2 V2, Key<V3> key3, V3 V3, Key<V4> key4, V4 V4) {
        return new Context(this, this.keyValueEntries.put(key, V1).put(key2, V2).put(key3, V3).put(key4, V4));
    }

    public Runnable wrap(final Runnable runnable2) {
        return new Runnable(){

            @Override
            public void run() {
                Context context = Context.this.attach();
                try {
                    runnable2.run();
                    return;
                }
                finally {
                    Context.this.detach(context);
                }
            }
        };
    }

    public <C> Callable<C> wrap(final Callable<C> callable) {
        return new Callable<C>(){

            @Override
            public C call() throws Exception {
                Context context = Context.this.attach();
                try {
                    Object v = callable.call();
                    return (C)v;
                }
                finally {
                    Context.this.detach(context);
                }
            }
        };
    }

    final class 1CurrentContextExecutor
    implements Executor {
        1CurrentContextExecutor() {
        }

        @Override
        public void execute(Runnable runnable2) {
            Executor.this.execute(Context.current().wrap(runnable2));
        }
    }

    final class 1FixedContextExecutor
    implements Executor {
        final /* synthetic */ Executor val$e;

        1FixedContextExecutor() {
            this.val$e = var2_2;
        }

        @Override
        public void execute(Runnable runnable2) {
            this.val$e.execute(this$0.wrap(runnable2));
        }
    }

    static @interface CanIgnoreReturnValue {
    }

    public static final class CancellableContext
    extends Context
    implements Closeable {
        private Throwable cancellationCause;
        private boolean cancelled;
        private final Deadline deadline;
        private ScheduledFuture<?> pendingDeadline;
        private final Context uncancellableSurrogate;

        private CancellableContext(Context context) {
            super(context, context.keyValueEntries);
            this.deadline = context.getDeadline();
            this.uncancellableSurrogate = new Context(this, this.keyValueEntries);
        }

        private CancellableContext(Context object, Deadline deadline, ScheduledExecutorService scheduledExecutorService) {
            super((Context)object, ((Context)object).keyValueEntries);
            object = ((Context)object).getDeadline();
            if (object == null || ((Deadline)object).compareTo(deadline) > 0) {
                if (!deadline.isExpired()) {
                    this.pendingDeadline = deadline.runOnExpiration(new Runnable(){

                        @Override
                        public void run() {
                            try {
                                CancellableContext cancellableContext = CancellableContext.this;
                                TimeoutException timeoutException = new TimeoutException("context timed out");
                                cancellableContext.cancel(timeoutException);
                                return;
                            }
                            catch (Throwable throwable) {
                                log.log(Level.SEVERE, "Cancel threw an exception, which should not happen", throwable);
                            }
                        }
                    }, scheduledExecutorService);
                    object = deadline;
                } else {
                    this.cancel(new TimeoutException("context timed out"));
                    object = deadline;
                }
            }
            this.deadline = object;
            this.uncancellableSurrogate = new Context(this, this.keyValueEntries);
        }

        @Override
        public Context attach() {
            return this.uncancellableSurrogate.attach();
        }

        @Override
        boolean canBeCancelled() {
            return true;
        }

        /*
         * Enabled unnecessary exception pruning
         */
        public boolean cancel(Throwable throwable) {
            boolean bl;
            synchronized (this) {
                boolean bl2 = this.cancelled;
                bl = true;
                if (!bl2) {
                    this.cancelled = true;
                    if (this.pendingDeadline != null) {
                        this.pendingDeadline.cancel(false);
                        this.pendingDeadline = null;
                    }
                    this.cancellationCause = throwable;
                } else {
                    bl = false;
                }
            }
            if (!bl) return bl;
            this.notifyAndClearListeners();
            return bl;
        }

        @Override
        public Throwable cancellationCause() {
            if (!this.isCancelled()) return null;
            return this.cancellationCause;
        }

        @Override
        public void close() {
            this.cancel(null);
        }

        @Override
        public void detach(Context context) {
            this.uncancellableSurrogate.detach(context);
        }

        public void detachAndCancel(Context context, Throwable throwable) {
            try {
                this.detach(context);
                return;
            }
            finally {
                this.cancel(throwable);
            }
        }

        @Override
        public Deadline getDeadline() {
            return this.deadline;
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public boolean isCancelled() {
            synchronized (this) {
                if (this.cancelled) {
                    return true;
                }
            }
            if (!super.isCancelled()) return false;
            this.cancel(super.cancellationCause());
            return true;
        }

        @Deprecated
        @Override
        public boolean isCurrent() {
            return this.uncancellableSurrogate.isCurrent();
        }

    }

    public static interface CancellationListener {
        public void cancelled(Context var1);
    }

    static @interface CheckReturnValue {
    }

    private static final class DirectExecutor
    extends Enum<DirectExecutor>
    implements Executor {
        private static final /* synthetic */ DirectExecutor[] $VALUES;
        public static final /* enum */ DirectExecutor INSTANCE;

        static {
            DirectExecutor directExecutor;
            INSTANCE = directExecutor = new DirectExecutor();
            $VALUES = new DirectExecutor[]{directExecutor};
        }

        public static DirectExecutor valueOf(String string2) {
            return Enum.valueOf(DirectExecutor.class, string2);
        }

        public static DirectExecutor[] values() {
            return (DirectExecutor[])$VALUES.clone();
        }

        @Override
        public void execute(Runnable runnable2) {
            runnable2.run();
        }

        public String toString() {
            return "Context.DirectExecutor";
        }
    }

    private final class ExecutableListener
    implements Runnable {
        private final Executor executor;
        final CancellationListener listener;

        ExecutableListener(Executor executor, CancellationListener cancellationListener) {
            this.executor = executor;
            this.listener = cancellationListener;
        }

        void deliver() {
            try {
                this.executor.execute(this);
                return;
            }
            catch (Throwable throwable) {
                log.log(Level.INFO, "Exception notifying context listener", throwable);
            }
        }

        @Override
        public void run() {
            this.listener.cancelled(Context.this);
        }
    }

    public static final class Key<T> {
        private final T defaultValue;
        private final String name;

        Key(String string2) {
            this(string2, null);
        }

        Key(String string2, T t) {
            this.name = Context.checkNotNull(string2, "name");
            this.defaultValue = t;
        }

        public T get() {
            return this.get(Context.current());
        }

        public T get(Context object) {
            Object object2;
            object = object2 = ((Context)object).lookup(this);
            if (object2 != null) return (T)object;
            object = this.defaultValue;
            return (T)object;
        }

        public String toString() {
            return this.name;
        }
    }

    private static final class LazyStorage {
        static final Storage storage;

        static {
            Serializable serializable = new AtomicReference();
            storage = LazyStorage.createStorage(serializable);
            if ((serializable = (Throwable)serializable.get()) == null) return;
            log.log(Level.FINE, "Storage override doesn't exist. Using default", (Throwable)serializable);
        }

        private LazyStorage() {
        }

        private static Storage createStorage(AtomicReference<? super ClassNotFoundException> atomicReference) {
            try {
                return Class.forName("io.grpc.override.ContextStorageOverride").asSubclass(Storage.class).getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception exception) {
                throw new RuntimeException("Storage override failed to initialize", exception);
            }
            catch (ClassNotFoundException classNotFoundException) {
                atomicReference.set(classNotFoundException);
                return new ThreadLocalContextStorage();
            }
        }
    }

    private final class ParentListener
    implements CancellationListener {
        private ParentListener() {
        }

        @Override
        public void cancelled(Context context) {
            Context context2 = Context.this;
            if (context2 instanceof CancellableContext) {
                ((CancellableContext)context2).cancel(context.cancellationCause());
                return;
            }
            context2.notifyAndClearListeners();
        }
    }

    public static abstract class Storage {
        @Deprecated
        public void attach(Context context) {
            throw new UnsupportedOperationException("Deprecated. Do not call.");
        }

        public abstract Context current();

        public abstract void detach(Context var1, Context var2);

        public Context doAttach(Context context) {
            Context context2 = this.current();
            this.attach(context);
            return context2;
        }
    }

}

