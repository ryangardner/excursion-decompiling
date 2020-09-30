/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.common.util.concurrent.Callables;
import com.google.common.util.concurrent.DirectExecutor;
import com.google.common.util.concurrent.ForwardingListenableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableScheduledFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.SequentialExecutor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.TrustedListenableFutureTask;
import com.google.common.util.concurrent.WrappingExecutorService;
import com.google.common.util.concurrent.WrappingScheduledExecutorService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class MoreExecutors {
    private MoreExecutors() {
    }

    public static void addDelayedShutdownHook(ExecutorService executorService, long l, TimeUnit timeUnit) {
        new Application().addDelayedShutdownHook(executorService, l, timeUnit);
    }

    public static Executor directExecutor() {
        return DirectExecutor.INSTANCE;
    }

    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor) {
        return new Application().getExitingExecutorService(threadPoolExecutor);
    }

    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor, long l, TimeUnit timeUnit) {
        return new Application().getExitingExecutorService(threadPoolExecutor, l, timeUnit);
    }

    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        return new Application().getExitingScheduledExecutorService(scheduledThreadPoolExecutor);
    }

    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, long l, TimeUnit timeUnit) {
        return new Application().getExitingScheduledExecutorService(scheduledThreadPoolExecutor, l, timeUnit);
    }

    /*
     * Exception decompiling
     */
    static <T> T invokeAnyImpl(ListeningExecutorService var0, Collection<? extends Callable<T>> var1_2, boolean var2_5, long var3_6, TimeUnit var5_7) throws InterruptedException, ExecutionException, TimeoutException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 10[UNCONDITIONALDOLOOP]
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

    private static boolean isAppEngineWithApiClasses() {
        Object object = System.getProperty("com.google.appengine.runtime.environment");
        boolean bl = false;
        if (object == null) {
            return false;
        }
        try {
            Class.forName("com.google.appengine.api.utils.SystemProperty");
            object = Class.forName("com.google.apphosting.api.ApiProxy").getMethod("getCurrentEnvironment", new Class[0]).invoke(null, new Object[0]);
            if (object == null) return bl;
            return true;
        }
        catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            return bl;
        }
    }

    public static ListeningExecutorService listeningDecorator(ExecutorService executorService) {
        if (executorService instanceof ListeningExecutorService) {
            return (ListeningExecutorService)executorService;
        }
        if (!(executorService instanceof ScheduledExecutorService)) return new ListeningDecorator(executorService);
        return new ScheduledListeningDecorator((ScheduledExecutorService)executorService);
    }

    public static ListeningScheduledExecutorService listeningDecorator(ScheduledExecutorService scheduledExecutorService) {
        if (!(scheduledExecutorService instanceof ListeningScheduledExecutorService)) return new ScheduledListeningDecorator(scheduledExecutorService);
        return (ListeningScheduledExecutorService)scheduledExecutorService;
    }

    public static ListeningExecutorService newDirectExecutorService() {
        return new DirectExecutorService();
    }

    public static Executor newSequentialExecutor(Executor executor) {
        return new SequentialExecutor(executor);
    }

    static Thread newThread(String string2, Runnable runnable2) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(runnable2);
        runnable2 = MoreExecutors.platformThreadFactory().newThread(runnable2);
        try {
            ((Thread)runnable2).setName(string2);
            return runnable2;
        }
        catch (SecurityException securityException) {
            return runnable2;
        }
    }

    public static ThreadFactory platformThreadFactory() {
        if (!MoreExecutors.isAppEngineWithApiClasses()) {
            return Executors.defaultThreadFactory();
        }
        try {
            return (ThreadFactory)Class.forName("com.google.appengine.api.ThreadManager").getMethod("currentRequestThreadFactory", new Class[0]).invoke(null, new Object[0]);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw Throwables.propagate(invocationTargetException.getCause());
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", noSuchMethodException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", classNotFoundException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", illegalAccessException);
        }
    }

    static Executor rejectionPropagatingExecutor(final Executor executor, final AbstractFuture<?> abstractFuture) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(abstractFuture);
        if (executor != MoreExecutors.directExecutor()) return new Executor(){
            boolean thrownFromDelegate = true;

            @Override
            public void execute(final Runnable runnable2) {
                try {
                    Executor executor2 = executor;
                    Runnable runnable3 = new Runnable(){

                        @Override
                        public void run() {
                            thrownFromDelegate = false;
                            runnable2.run();
                        }
                    };
                    executor2.execute(runnable3);
                    return;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    if (!this.thrownFromDelegate) return;
                    abstractFuture.setException(rejectedExecutionException);
                }
            }

        };
        return executor;
    }

    static Executor renamingDecorator(final Executor executor, final Supplier<String> supplier) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(supplier);
        return new Executor(){

            @Override
            public void execute(Runnable runnable2) {
                executor.execute(Callables.threadRenaming(runnable2, (Supplier<String>)supplier));
            }
        };
    }

    static ExecutorService renamingDecorator(ExecutorService executorService, final Supplier<String> supplier) {
        Preconditions.checkNotNull(executorService);
        Preconditions.checkNotNull(supplier);
        return new WrappingExecutorService(executorService){

            @Override
            protected Runnable wrapTask(Runnable runnable2) {
                return Callables.threadRenaming(runnable2, (Supplier<String>)supplier);
            }

            @Override
            protected <T> Callable<T> wrapTask(Callable<T> callable) {
                return Callables.threadRenaming(callable, (Supplier<String>)supplier);
            }
        };
    }

    static ScheduledExecutorService renamingDecorator(ScheduledExecutorService scheduledExecutorService, final Supplier<String> supplier) {
        Preconditions.checkNotNull(scheduledExecutorService);
        Preconditions.checkNotNull(supplier);
        return new WrappingScheduledExecutorService(scheduledExecutorService){

            @Override
            protected Runnable wrapTask(Runnable runnable2) {
                return Callables.threadRenaming(runnable2, (Supplier<String>)supplier);
            }

            @Override
            protected <T> Callable<T> wrapTask(Callable<T> callable) {
                return Callables.threadRenaming(callable, (Supplier<String>)supplier);
            }
        };
    }

    public static boolean shutdownAndAwaitTermination(ExecutorService executorService, long l, TimeUnit timeUnit) {
        l = timeUnit.toNanos(l) / 2L;
        executorService.shutdown();
        try {
            if (executorService.awaitTermination(l, TimeUnit.NANOSECONDS)) return executorService.isTerminated();
            executorService.shutdownNow();
            executorService.awaitTermination(l, TimeUnit.NANOSECONDS);
            return executorService.isTerminated();
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }
        return executorService.isTerminated();
    }

    private static <T> ListenableFuture<T> submitAndAddQueueListener(ListeningExecutorService object, Callable<T> callable, final BlockingQueue<Future<T>> blockingQueue) {
        object = object.submit(callable);
        object.addListener(new Runnable((ListenableFuture)object){
            final /* synthetic */ ListenableFuture val$future;
            {
                this.val$future = listenableFuture;
            }

            @Override
            public void run() {
                blockingQueue.add(this.val$future);
            }
        }, MoreExecutors.directExecutor());
        return object;
    }

    private static void useDaemonThreadFactory(ThreadPoolExecutor threadPoolExecutor) {
        threadPoolExecutor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(true).setThreadFactory(threadPoolExecutor.getThreadFactory()).build());
    }

    static class Application {
        Application() {
        }

        final void addDelayedShutdownHook(final ExecutorService executorService, final long l, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(executorService);
            Preconditions.checkNotNull(timeUnit);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DelayedShutdownHook-for-");
            stringBuilder.append(executorService);
            this.addShutdownHook(MoreExecutors.newThread(stringBuilder.toString(), new Runnable(){

                @Override
                public void run() {
                    try {
                        executorService.shutdown();
                        executorService.awaitTermination(l, timeUnit);
                        return;
                    }
                    catch (InterruptedException interruptedException) {
                        return;
                    }
                }
            }));
        }

        void addShutdownHook(Thread thread2) {
            Runtime.getRuntime().addShutdownHook(thread2);
        }

        final ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor) {
            return this.getExitingExecutorService(threadPoolExecutor, 120L, TimeUnit.SECONDS);
        }

        final ExecutorService getExitingExecutorService(ThreadPoolExecutor threadPoolExecutor, long l, TimeUnit timeUnit) {
            MoreExecutors.useDaemonThreadFactory(threadPoolExecutor);
            ExecutorService executorService = Executors.unconfigurableExecutorService(threadPoolExecutor);
            this.addDelayedShutdownHook(threadPoolExecutor, l, timeUnit);
            return executorService;
        }

        final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
            return this.getExitingScheduledExecutorService(scheduledThreadPoolExecutor, 120L, TimeUnit.SECONDS);
        }

        final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, long l, TimeUnit timeUnit) {
            MoreExecutors.useDaemonThreadFactory(scheduledThreadPoolExecutor);
            ScheduledExecutorService scheduledExecutorService = Executors.unconfigurableScheduledExecutorService(scheduledThreadPoolExecutor);
            this.addDelayedShutdownHook(scheduledThreadPoolExecutor, l, timeUnit);
            return scheduledExecutorService;
        }

    }

    private static final class DirectExecutorService
    extends AbstractListeningExecutorService {
        private final Object lock = new Object();
        private int runningTasks = 0;
        private boolean shutdown = false;

        private DirectExecutorService() {
        }

        private void endTask() {
            Object object = this.lock;
            synchronized (object) {
                int n;
                this.runningTasks = n = this.runningTasks - 1;
                if (n != 0) return;
                this.lock.notifyAll();
                return;
            }
        }

        private void startTask() {
            Object object = this.lock;
            synchronized (object) {
                if (!this.shutdown) {
                    ++this.runningTasks;
                    return;
                }
                RejectedExecutionException rejectedExecutionException = new RejectedExecutionException("Executor already shutdown");
                throw rejectedExecutionException;
            }
        }

        @Override
        public boolean awaitTermination(long l, TimeUnit object) throws InterruptedException {
            l = object.toNanos(l);
            object = this.lock;
            synchronized (object) {
                do {
                    if (this.shutdown && this.runningTasks == 0) {
                        return true;
                    }
                    if (l <= 0L) {
                        return false;
                    }
                    long l2 = System.nanoTime();
                    TimeUnit.NANOSECONDS.timedWait(this.lock, l);
                    l -= System.nanoTime() - l2;
                } while (true);
            }
        }

        @Override
        public void execute(Runnable runnable2) {
            this.startTask();
            try {
                runnable2.run();
                return;
            }
            finally {
                this.endTask();
            }
        }

        @Override
        public boolean isShutdown() {
            Object object = this.lock;
            synchronized (object) {
                return this.shutdown;
            }
        }

        @Override
        public boolean isTerminated() {
            Object object = this.lock;
            synchronized (object) {
                if (!this.shutdown) return false;
                if (this.runningTasks != 0) return false;
                return true;
            }
        }

        @Override
        public void shutdown() {
            Object object = this.lock;
            synchronized (object) {
                this.shutdown = true;
                if (this.runningTasks != 0) return;
                this.lock.notifyAll();
                return;
            }
        }

        @Override
        public List<Runnable> shutdownNow() {
            this.shutdown();
            return Collections.emptyList();
        }
    }

    private static class ListeningDecorator
    extends AbstractListeningExecutorService {
        private final ExecutorService delegate;

        ListeningDecorator(ExecutorService executorService) {
            this.delegate = Preconditions.checkNotNull(executorService);
        }

        @Override
        public final boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
            return this.delegate.awaitTermination(l, timeUnit);
        }

        @Override
        public final void execute(Runnable runnable2) {
            this.delegate.execute(runnable2);
        }

        @Override
        public final boolean isShutdown() {
            return this.delegate.isShutdown();
        }

        @Override
        public final boolean isTerminated() {
            return this.delegate.isTerminated();
        }

        @Override
        public final void shutdown() {
            this.delegate.shutdown();
        }

        @Override
        public final List<Runnable> shutdownNow() {
            return this.delegate.shutdownNow();
        }
    }

    private static final class ScheduledListeningDecorator
    extends ListeningDecorator
    implements ListeningScheduledExecutorService {
        final ScheduledExecutorService delegate;

        ScheduledListeningDecorator(ScheduledExecutorService scheduledExecutorService) {
            super(scheduledExecutorService);
            this.delegate = Preconditions.checkNotNull(scheduledExecutorService);
        }

        @Override
        public ListenableScheduledFuture<?> schedule(Runnable trustedListenableFutureTask, long l, TimeUnit timeUnit) {
            trustedListenableFutureTask = TrustedListenableFutureTask.create(trustedListenableFutureTask, null);
            return new ListenableScheduledTask<Object>(trustedListenableFutureTask, this.delegate.schedule(trustedListenableFutureTask, l, timeUnit));
        }

        @Override
        public <V> ListenableScheduledFuture<V> schedule(Callable<V> object, long l, TimeUnit timeUnit) {
            object = TrustedListenableFutureTask.create(object);
            return new ListenableScheduledTask(object, this.delegate.schedule((Runnable)object, l, timeUnit));
        }

        @Override
        public ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable runnable2, long l, long l2, TimeUnit timeUnit) {
            runnable2 = new NeverSuccessfulListenableFutureTask(runnable2);
            return new ListenableScheduledTask(runnable2, this.delegate.scheduleAtFixedRate(runnable2, l, l2, timeUnit));
        }

        @Override
        public ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable2, long l, long l2, TimeUnit timeUnit) {
            runnable2 = new NeverSuccessfulListenableFutureTask(runnable2);
            return new ListenableScheduledTask(runnable2, this.delegate.scheduleWithFixedDelay(runnable2, l, l2, timeUnit));
        }

        private static final class ListenableScheduledTask<V>
        extends ForwardingListenableFuture.SimpleForwardingListenableFuture<V>
        implements ListenableScheduledFuture<V> {
            private final ScheduledFuture<?> scheduledDelegate;

            public ListenableScheduledTask(ListenableFuture<V> listenableFuture, ScheduledFuture<?> scheduledFuture) {
                super(listenableFuture);
                this.scheduledDelegate = scheduledFuture;
            }

            @Override
            public boolean cancel(boolean bl) {
                boolean bl2 = super.cancel(bl);
                if (!bl2) return bl2;
                this.scheduledDelegate.cancel(bl);
                return bl2;
            }

            @Override
            public int compareTo(Delayed delayed) {
                return this.scheduledDelegate.compareTo(delayed);
            }

            @Override
            public long getDelay(TimeUnit timeUnit) {
                return this.scheduledDelegate.getDelay(timeUnit);
            }
        }

        private static final class NeverSuccessfulListenableFutureTask
        extends AbstractFuture.TrustedFuture<Void>
        implements Runnable {
            private final Runnable delegate;

            public NeverSuccessfulListenableFutureTask(Runnable runnable2) {
                this.delegate = Preconditions.checkNotNull(runnable2);
            }

            @Override
            public void run() {
                try {
                    this.delegate.run();
                    return;
                }
                catch (Throwable throwable) {
                    this.setException(throwable);
                    throw Throwables.propagate(throwable);
                }
            }
        }

    }

}

