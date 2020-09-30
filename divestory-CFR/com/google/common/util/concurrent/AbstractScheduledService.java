/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.ForwardingFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractScheduledService
implements Service {
    private static final Logger logger = Logger.getLogger(AbstractScheduledService.class.getName());
    private final AbstractService delegate = new ServiceDelegate();

    protected AbstractScheduledService() {
    }

    static /* synthetic */ Logger access$400() {
        return logger;
    }

    @Override
    public final void addListener(Service.Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    @Override
    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    @Override
    public final void awaitRunning(long l, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitRunning(l, timeUnit);
    }

    @Override
    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    @Override
    public final void awaitTerminated(long l, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitTerminated(l, timeUnit);
    }

    protected ScheduledExecutorService executor() {
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new 1ThreadFactoryImpl());
        this.addListener(new Service.Listener(){

            @Override
            public void failed(Service.State state, Throwable throwable) {
                scheduledExecutorService.shutdown();
            }

            @Override
            public void terminated(Service.State state) {
                scheduledExecutorService.shutdown();
            }
        }, MoreExecutors.directExecutor());
        return scheduledExecutorService;
    }

    @Override
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @Override
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    protected abstract void runOneIteration() throws Exception;

    protected abstract Scheduler scheduler();

    protected String serviceName() {
        return this.getClass().getSimpleName();
    }

    protected void shutDown() throws Exception {
    }

    @Override
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    protected void startUp() throws Exception {
    }

    @Override
    public final Service.State state() {
        return this.delegate.state();
    }

    @Override
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.serviceName());
        stringBuilder.append(" [");
        stringBuilder.append((Object)((Object)this.state()));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    class 1ThreadFactoryImpl
    implements ThreadFactory {
        1ThreadFactoryImpl() {
        }

        @Override
        public Thread newThread(Runnable runnable2) {
            return MoreExecutors.newThread(AbstractScheduledService.this.serviceName(), runnable2);
        }
    }

    public static abstract class CustomScheduler
    extends Scheduler {
        protected abstract Schedule getNextSchedule() throws Exception;

        @Override
        final Future<?> schedule(AbstractService object, ScheduledExecutorService scheduledExecutorService, Runnable runnable2) {
            object = new ReschedulableCallable((AbstractService)object, scheduledExecutorService, runnable2);
            ((ReschedulableCallable)object).reschedule();
            return object;
        }

        private class ReschedulableCallable
        extends ForwardingFuture<Void>
        implements Callable<Void> {
            @NullableDecl
            private Future<Void> currentFuture;
            private final ScheduledExecutorService executor;
            private final ReentrantLock lock = new ReentrantLock();
            private final AbstractService service;
            private final Runnable wrappedRunnable;

            ReschedulableCallable(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable2) {
                this.wrappedRunnable = runnable2;
                this.executor = scheduledExecutorService;
                this.service = abstractService;
            }

            @Override
            public Void call() throws Exception {
                this.wrappedRunnable.run();
                this.reschedule();
                return null;
            }

            @Override
            public boolean cancel(boolean bl) {
                this.lock.lock();
                try {
                    bl = this.currentFuture.cancel(bl);
                    return bl;
                }
                finally {
                    this.lock.unlock();
                }
            }

            @Override
            protected Future<Void> delegate() {
                throw new UnsupportedOperationException("Only cancel and isCancelled is supported by this future");
            }

            @Override
            public boolean isCancelled() {
                this.lock.lock();
                try {
                    boolean bl = this.currentFuture.isCancelled();
                    return bl;
                }
                finally {
                    this.lock.unlock();
                }
            }

            public void reschedule() {
                Throwable throwable;
                block6 : {
                    Schedule schedule2;
                    Throwable throwable2;
                    try {
                        schedule2 = CustomScheduler.this.getNextSchedule();
                        throwable2 = null;
                        this.lock.lock();
                    }
                    catch (Throwable throwable3) {
                        this.service.notifyFailed(throwable3);
                        return;
                    }
                    try {
                        if (this.currentFuture != null) {
                            throwable = throwable2;
                            if (this.currentFuture.isCancelled()) break block6;
                        }
                        this.currentFuture = this.executor.schedule(this, schedule2.delay, schedule2.unit);
                        throwable = throwable2;
                    }
                    catch (Throwable throwable4) {
                        // empty catch block
                    }
                }
                this.lock.unlock();
                if (throwable == null) return;
                this.service.notifyFailed(throwable);
            }
        }

        protected static final class Schedule {
            private final long delay;
            private final TimeUnit unit;

            public Schedule(long l, TimeUnit timeUnit) {
                this.delay = l;
                this.unit = Preconditions.checkNotNull(timeUnit);
            }
        }

    }

    public static abstract class Scheduler {
        private Scheduler() {
        }

        public static Scheduler newFixedDelaySchedule(final long l, final long l2, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            boolean bl = l2 > 0L;
            Preconditions.checkArgument(bl, "delay must be > 0, found %s", l2);
            return new Scheduler(){

                @Override
                public Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable2) {
                    return scheduledExecutorService.scheduleWithFixedDelay(runnable2, l, l2, timeUnit);
                }
            };
        }

        public static Scheduler newFixedRateSchedule(final long l, final long l2, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            boolean bl = l2 > 0L;
            Preconditions.checkArgument(bl, "period must be > 0, found %s", l2);
            return new Scheduler(){

                @Override
                public Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable2) {
                    return scheduledExecutorService.scheduleAtFixedRate(runnable2, l, l2, timeUnit);
                }
            };
        }

        abstract Future<?> schedule(AbstractService var1, ScheduledExecutorService var2, Runnable var3);

    }

    private final class ServiceDelegate
    extends AbstractService {
        @MonotonicNonNullDecl
        private volatile ScheduledExecutorService executorService;
        private final ReentrantLock lock = new ReentrantLock();
        @MonotonicNonNullDecl
        private volatile Future<?> runningTask;
        private final Runnable task = new Task();

        private ServiceDelegate() {
        }

        @Override
        protected final void doStart() {
            this.executorService = MoreExecutors.renamingDecorator(AbstractScheduledService.this.executor(), new Supplier<String>(){

                @Override
                public String get() {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(AbstractScheduledService.this.serviceName());
                    stringBuilder.append(" ");
                    stringBuilder.append((Object)ServiceDelegate.this.state());
                    return stringBuilder.toString();
                }
            });
            this.executorService.execute(new Runnable(){

                @Override
                public void run() {
                    ServiceDelegate.this.lock.lock();
                    try {
                        AbstractScheduledService.this.startUp();
                        ServiceDelegate.this.runningTask = AbstractScheduledService.this.scheduler().schedule(AbstractScheduledService.this.delegate, ServiceDelegate.this.executorService, ServiceDelegate.this.task);
                        ServiceDelegate.this.notifyStarted();
                        return;
                    }
                    catch (Throwable throwable) {
                        try {
                            ServiceDelegate.this.notifyFailed(throwable);
                            if (ServiceDelegate.this.runningTask == null) return;
                            ServiceDelegate.this.runningTask.cancel(false);
                            return;
                        }
                        finally {
                            ServiceDelegate.this.lock.unlock();
                        }
                    }
                }
            });
        }

        @Override
        protected final void doStop() {
            this.runningTask.cancel(false);
            this.executorService.execute(new Runnable(){

                /*
                 * Loose catch block
                 * Enabled unnecessary exception pruning
                 */
                @Override
                public void run() {
                    block7 : {
                        ServiceDelegate.this.lock.lock();
                        Service.State state = ServiceDelegate.this.state();
                        Service.State state2 = Service.State.STOPPING;
                        if (state == state2) break block7;
                        {
                            catch (Throwable throwable) {
                                ServiceDelegate.this.lock.unlock();
                                throw throwable;
                            }
                        }
                        ServiceDelegate.this.lock.unlock();
                        return;
                    }
                    AbstractScheduledService.this.shutDown();
                    try {
                        ServiceDelegate.this.lock.unlock();
                        ServiceDelegate.this.notifyStopped();
                        return;
                    }
                    catch (Throwable throwable) {
                        ServiceDelegate.this.notifyFailed(throwable);
                    }
                }
            });
        }

        @Override
        public String toString() {
            return AbstractScheduledService.this.toString();
        }

        class Task
        implements Runnable {
            Task() {
            }

            /*
             * Exception decompiling
             */
            @Override
            public void run() {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Nonsensical loop would be emitted - failure
                // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LoopIdentifier.considerAsDoLoopStart(LoopIdentifier.java:401)
                // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LoopIdentifier.identifyLoops1(LoopIdentifier.java:62)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:541)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
                // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
                // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
                // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
                // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
                // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
                // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
                // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
                // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                // org.benf.cfr.reader.Main.main(Main.java:48)
                // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
                // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
                throw new IllegalStateException("Decompilation failed");
            }
        }

    }

}

