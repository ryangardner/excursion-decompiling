/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.concurrent.TaskRunner$runnable
 */
package okhttp3.internal.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;

@Metadata(bv={1, 0, 3}, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 #2\u00020\u0001:\u0003\"#$B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u0014J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\rH\u0002J\b\u0010\u001a\u001a\u0004\u0018\u00010\u0018J\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0006\u0010\u001c\u001a\u00020\u0016J\u0015\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u001fJ\u0006\u0010 \u001a\u00020\tJ\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2={"Lokhttp3/internal/concurrent/TaskRunner;", "", "backend", "Lokhttp3/internal/concurrent/TaskRunner$Backend;", "(Lokhttp3/internal/concurrent/TaskRunner$Backend;)V", "getBackend", "()Lokhttp3/internal/concurrent/TaskRunner$Backend;", "busyQueues", "", "Lokhttp3/internal/concurrent/TaskQueue;", "coordinatorWaiting", "", "coordinatorWakeUpAt", "", "nextQueueName", "", "readyQueues", "runnable", "Ljava/lang/Runnable;", "activeQueues", "", "afterRun", "", "task", "Lokhttp3/internal/concurrent/Task;", "delayNanos", "awaitTaskToRun", "beforeRun", "cancelAll", "kickCoordinator", "taskQueue", "kickCoordinator$okhttp", "newQueue", "runTask", "Backend", "Companion", "RealBackend", "okhttp"}, k=1, mv={1, 1, 16})
public final class TaskRunner {
    public static final Companion Companion = new Companion(null);
    public static final TaskRunner INSTANCE;
    private static final Logger logger;
    private final Backend backend;
    private final List<TaskQueue> busyQueues;
    private boolean coordinatorWaiting;
    private long coordinatorWakeUpAt;
    private int nextQueueName;
    private final List<TaskQueue> readyQueues;
    private final Runnable runnable;

    static {
        Object object = new StringBuilder();
        ((StringBuilder)object).append(Util.okHttpName);
        ((StringBuilder)object).append(" TaskRunner");
        INSTANCE = new TaskRunner(new RealBackend(Util.threadFactory(((StringBuilder)object).toString(), true)));
        object = Logger.getLogger(TaskRunner.class.getName());
        Intrinsics.checkExpressionValueIsNotNull(object, "Logger.getLogger(TaskRunner::class.java.name)");
        logger = object;
    }

    public TaskRunner(Backend backend) {
        Intrinsics.checkParameterIsNotNull(backend, "backend");
        this.backend = backend;
        this.nextQueueName = 10000;
        this.busyQueues = new ArrayList();
        this.readyQueues = new ArrayList();
        this.runnable = new Runnable(this){
            final /* synthetic */ TaskRunner this$0;
            {
                this.this$0 = taskRunner;
            }

            /*
             * Unable to fully structure code
             * Enabled unnecessary exception pruning
             * Converted monitor instructions to comments
             */
            public void run() {
                do lbl-1000: // 3 sources:
                {
                    var1_1 = this.this$0;
                    // MONITORENTER : var1_1
                    var2_2 = this.this$0.awaitTaskToRun();
                    // MONITOREXIT : var1_1
                    if (var2_2 == null) return;
                    var1_1 = var2_2.getQueue$okhttp();
                    if (var1_1 == null) {
                        Intrinsics.throwNpe();
                    }
                    var3_3 = -1L;
                    var5_4 = TaskRunner.Companion.getLogger().isLoggable(java.util.logging.Level.FINE);
                    if (var5_4) {
                        var3_3 = var1_1.getTaskRunner$okhttp().getBackend().nanoTime();
                        okhttp3.internal.concurrent.TaskLoggerKt.access$log(var2_2, (TaskQueue)var1_1, "starting");
                    }
                    TaskRunner.access$runTask(this.this$0, var2_2);
                    break;
                } while (true);
                catch (Throwable var6_6) {
                    try {
                        this.this$0.getBackend().execute(this);
                        throw var6_6;
                    }
                    catch (Throwable var6_7) {
                        if (var5_4 == false) throw var6_7;
                        var7_8 = var1_1.getTaskRunner$okhttp().getBackend().nanoTime();
                        var9_9 = new StringBuilder();
                        var9_9.append("failed a run in ");
                        var9_9.append(okhttp3.internal.concurrent.TaskLoggerKt.formatDuration(var7_8 - var3_3));
                        okhttp3.internal.concurrent.TaskLoggerKt.access$log(var2_2, (TaskQueue)var1_1, var9_9.toString());
                        throw var6_7;
                    }
                }
                {
                    var6_5 = Unit.INSTANCE;
                    if (!var5_4) continue;
                    var7_8 = var1_1.getTaskRunner$okhttp().getBackend().nanoTime();
                    var6_5 = new StringBuilder();
                    var6_5.append("finished run in ");
                    var6_5.append(okhttp3.internal.concurrent.TaskLoggerKt.formatDuration(var7_8 - var3_3));
                    okhttp3.internal.concurrent.TaskLoggerKt.access$log(var2_2, (TaskQueue)var1_1, var6_5.toString());
                    ** while (true)
                }
            }
        };
    }

    public static final /* synthetic */ void access$runTask(TaskRunner taskRunner, Task task) {
        taskRunner.runTask(task);
    }

    private final void afterRun(Task object, long l) {
        if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Thread ");
            Thread thread2 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
            ((StringBuilder)object).append(thread2.getName());
            ((StringBuilder)object).append(" MUST hold lock on ");
            ((StringBuilder)object).append(this);
            throw (Throwable)((Object)new AssertionError((Object)((StringBuilder)object).toString()));
        }
        TaskQueue taskQueue = ((Task)object).getQueue$okhttp();
        if (taskQueue == null) {
            Intrinsics.throwNpe();
        }
        boolean bl = taskQueue.getActiveTask$okhttp() == object;
        if (!bl) throw (Throwable)new IllegalStateException("Check failed.".toString());
        boolean bl2 = taskQueue.getCancelActiveTask$okhttp();
        taskQueue.setCancelActiveTask$okhttp(false);
        taskQueue.setActiveTask$okhttp(null);
        this.busyQueues.remove(taskQueue);
        if (l != -1L && !bl2 && !taskQueue.getShutdown$okhttp()) {
            taskQueue.scheduleAndDecide$okhttp((Task)object, l, true);
        }
        if (!(((Collection)taskQueue.getFutureTasks$okhttp()).isEmpty() ^ true)) return;
        this.readyQueues.add(taskQueue);
    }

    private final void beforeRun(Task object) {
        if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Thread ");
            object = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(object, "Thread.currentThread()");
            stringBuilder.append(((Thread)object).getName());
            stringBuilder.append(" MUST hold lock on ");
            stringBuilder.append(this);
            throw (Throwable)((Object)new AssertionError((Object)stringBuilder.toString()));
        }
        ((Task)object).setNextExecuteNanoTime$okhttp(-1L);
        TaskQueue taskQueue = ((Task)object).getQueue$okhttp();
        if (taskQueue == null) {
            Intrinsics.throwNpe();
        }
        taskQueue.getFutureTasks$okhttp().remove(object);
        this.readyQueues.remove(taskQueue);
        taskQueue.setActiveTask$okhttp((Task)object);
        this.busyQueues.add(taskQueue);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private final void runTask(Task object) {
        long l;
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
        Thread thread2 = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(thread2, "currentThread");
        String string2 = thread2.getName();
        thread2.setName(((Task)object).getName());
        try {
            l = ((Task)object).runOnce();
        }
        catch (Throwable throwable) {
            synchronized (this) {
                this.afterRun((Task)object, -1L);
                object = Unit.INSTANCE;
            }
            thread2.setName(string2);
            throw throwable;
        }
        synchronized (this) {
            this.afterRun((Task)object, l);
            object = Unit.INSTANCE;
        }
        thread2.setName(string2);
    }

    public final List<TaskQueue> activeQueues() {
        synchronized (this) {
            return CollectionsKt.plus((Collection)this.busyQueues, (Iterable)this.readyQueues);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public final Task awaitTaskToRun() {
        if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
            var1_1 = new StringBuilder();
            var1_1.append("Thread ");
            var2_3 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(var2_3, "Thread.currentThread()");
            var1_1.append(var2_3.getName());
            var1_1.append(" MUST hold lock on ");
            var1_1.append(this);
            throw (Throwable)new AssertionError((Object)var1_1.toString());
        }
        block4 : do {
            block14 : {
                if (this.readyQueues.isEmpty()) {
                    return null;
                }
                var3_7 = this.backend.nanoTime();
                var5_8 = Long.MAX_VALUE;
                var2_4 = null;
                var7_9 = this.readyQueues.iterator();
                while (var7_9.hasNext()) {
                    var1_2 = var7_9.next().getFutureTasks$okhttp().get(0);
                    var8_10 = Math.max(0L, var1_2.getNextExecuteNanoTime$okhttp() - var3_7);
                    if (var8_10 > 0L) {
                        var5_8 = Math.min(var8_10, var5_8);
                        continue;
                    }
                    if (var2_4 != null) {
                        var10_11 = true;
                        break block14;
                    }
                    var2_4 = var1_2;
                }
                var10_11 = false;
            }
            if (var2_4 != null) {
                this.beforeRun(var2_4);
                if (!var10_11) {
                    if (this.coordinatorWaiting != false) return var2_4;
                    if ((((Collection)this.readyQueues).isEmpty() ^ true) == false) return var2_4;
                }
                this.backend.execute(this.runnable);
                return var2_4;
            }
            if (this.coordinatorWaiting) {
                if (var5_8 >= this.coordinatorWakeUpAt - var3_7) return null;
                this.backend.coordinatorNotify(this);
                return null;
            }
            this.coordinatorWaiting = true;
            this.coordinatorWakeUpAt = var3_7 + var5_8;
            this.backend.coordinatorWait(this, var5_8);
lbl49: // 2 sources:
            do {
                this.coordinatorWaiting = false;
                continue block4;
                break;
            } while (true);
            {
                catch (Throwable var2_5) {
                    break;
                }
                catch (InterruptedException var2_6) {}
                {
                    this.cancelAll();
                    ** continue;
                }
            }
        } while (true);
        this.coordinatorWaiting = false;
        throw var2_5;
    }

    public final void cancelAll() {
        int n;
        for (n = this.busyQueues.size() - 1; n >= 0; --n) {
            this.busyQueues.get(n).cancelAllAndDecide$okhttp();
        }
        n = this.readyQueues.size() - 1;
        while (n >= 0) {
            TaskQueue taskQueue = this.readyQueues.get(n);
            taskQueue.cancelAllAndDecide$okhttp();
            if (taskQueue.getFutureTasks$okhttp().isEmpty()) {
                this.readyQueues.remove(n);
            }
            --n;
        }
    }

    public final Backend getBackend() {
        return this.backend;
    }

    public final void kickCoordinator$okhttp(TaskQueue object) {
        Intrinsics.checkParameterIsNotNull(object, "taskQueue");
        if (Util.assertionsEnabled && !Thread.holdsLock(this)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Thread ");
            Thread thread2 = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(thread2, "Thread.currentThread()");
            ((StringBuilder)object).append(thread2.getName());
            ((StringBuilder)object).append(" MUST hold lock on ");
            ((StringBuilder)object).append(this);
            throw (Throwable)((Object)new AssertionError((Object)((StringBuilder)object).toString()));
        }
        if (((TaskQueue)object).getActiveTask$okhttp() == null) {
            if (((Collection)((TaskQueue)object).getFutureTasks$okhttp()).isEmpty() ^ true) {
                Util.addIfAbsent(this.readyQueues, object);
            } else {
                this.readyQueues.remove(object);
            }
        }
        if (this.coordinatorWaiting) {
            this.backend.coordinatorNotify(this);
            return;
        }
        this.backend.execute(this.runnable);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final TaskQueue newQueue() {
        int n;
        synchronized (this) {
            n = this.nextQueueName;
            this.nextQueueName = n + 1;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('Q');
        stringBuilder.append(n);
        return new TaskQueue(this, stringBuilder.toString());
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\tH&\u00a8\u0006\u000e"}, d2={"Lokhttp3/internal/concurrent/TaskRunner$Backend;", "", "beforeTask", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "coordinatorNotify", "coordinatorWait", "nanos", "", "execute", "runnable", "Ljava/lang/Runnable;", "nanoTime", "okhttp"}, k=1, mv={1, 1, 16})
    public static interface Backend {
        public void beforeTask(TaskRunner var1);

        public void coordinatorNotify(TaskRunner var1);

        public void coordinatorWait(TaskRunner var1, long var2);

        public void execute(Runnable var1);

        public long nanoTime();
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lokhttp3/internal/concurrent/TaskRunner$Companion;", "", "()V", "INSTANCE", "Lokhttp3/internal/concurrent/TaskRunner;", "logger", "Ljava/util/logging/Logger;", "getLogger", "()Ljava/util/logging/Logger;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Logger getLogger() {
            return logger;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u000eH\u0016J\u0006\u0010\u0013\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lokhttp3/internal/concurrent/TaskRunner$RealBackend;", "Lokhttp3/internal/concurrent/TaskRunner$Backend;", "threadFactory", "Ljava/util/concurrent/ThreadFactory;", "(Ljava/util/concurrent/ThreadFactory;)V", "executor", "Ljava/util/concurrent/ThreadPoolExecutor;", "beforeTask", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "coordinatorNotify", "coordinatorWait", "nanos", "", "execute", "runnable", "Ljava/lang/Runnable;", "nanoTime", "shutdown", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class RealBackend
    implements Backend {
        private final ThreadPoolExecutor executor;

        public RealBackend(ThreadFactory threadFactory2) {
            Intrinsics.checkParameterIsNotNull(threadFactory2, "threadFactory");
            this.executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, (BlockingQueue<Runnable>)new SynchronousQueue(), threadFactory2);
        }

        @Override
        public void beforeTask(TaskRunner taskRunner) {
            Intrinsics.checkParameterIsNotNull(taskRunner, "taskRunner");
        }

        @Override
        public void coordinatorNotify(TaskRunner taskRunner) {
            Intrinsics.checkParameterIsNotNull(taskRunner, "taskRunner");
            ((Object)taskRunner).notify();
        }

        @Override
        public void coordinatorWait(TaskRunner taskRunner, long l) throws InterruptedException {
            Intrinsics.checkParameterIsNotNull(taskRunner, "taskRunner");
            long l2 = l / 1000000L;
            if (l2 <= 0L) {
                if (l <= 0L) return;
            }
            ((Object)taskRunner).wait(l2, (int)(l - 1000000L * l2));
        }

        @Override
        public void execute(Runnable runnable2) {
            Intrinsics.checkParameterIsNotNull(runnable2, "runnable");
            this.executor.execute(runnable2);
        }

        @Override
        public long nanoTime() {
            return System.nanoTime();
        }

        public final void shutdown() {
            this.executor.shutdown();
        }
    }

}

