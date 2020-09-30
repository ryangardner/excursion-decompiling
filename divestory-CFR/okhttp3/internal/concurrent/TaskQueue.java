/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.concurrent.TaskQueue$execute
 *  okhttp3.internal.concurrent.TaskQueue$schedule
 */
package okhttp3.internal.concurrent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskLoggerKt;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;

@Metadata(bv={1, 0, 3}, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u00013B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010!\u001a\u00020\"J\r\u0010#\u001a\u00020\u000eH\u0000\u00a2\u0006\u0002\b$J5\u0010%\u001a\u00020\"2\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010&\u001a\u00020'2\b\b\u0002\u0010(\u001a\u00020\u000e2\u000e\b\u0004\u0010)\u001a\b\u0012\u0004\u0012\u00020\"0*H\u0086\bJ\u0006\u0010+\u001a\u00020,J+\u0010-\u001a\u00020\"2\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010&\u001a\u00020'2\u000e\b\u0004\u0010)\u001a\b\u0012\u0004\u0012\u00020'0*H\u0086\bJ\u0018\u0010-\u001a\u00020\"2\u0006\u0010.\u001a\u00020\b2\b\b\u0002\u0010&\u001a\u00020'J%\u0010/\u001a\u00020\u000e2\u0006\u0010.\u001a\u00020\b2\u0006\u0010&\u001a\u00020'2\u0006\u00100\u001a\u00020\u000eH\u0000\u00a2\u0006\u0002\b1J\u0006\u0010\u001c\u001a\u00020\"J\b\u00102\u001a\u00020\u0005H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u0014X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001a8F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0016R\u001a\u0010\u001c\u001a\u00020\u000eX\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0010\"\u0004\b\u001e\u0010\u0012R\u0014\u0010\u0002\u001a\u00020\u0003X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 \u00a8\u00064"}, d2={"Lokhttp3/internal/concurrent/TaskQueue;", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "name", "", "(Lokhttp3/internal/concurrent/TaskRunner;Ljava/lang/String;)V", "activeTask", "Lokhttp3/internal/concurrent/Task;", "getActiveTask$okhttp", "()Lokhttp3/internal/concurrent/Task;", "setActiveTask$okhttp", "(Lokhttp3/internal/concurrent/Task;)V", "cancelActiveTask", "", "getCancelActiveTask$okhttp", "()Z", "setCancelActiveTask$okhttp", "(Z)V", "futureTasks", "", "getFutureTasks$okhttp", "()Ljava/util/List;", "getName$okhttp", "()Ljava/lang/String;", "scheduledTasks", "", "getScheduledTasks", "shutdown", "getShutdown$okhttp", "setShutdown$okhttp", "getTaskRunner$okhttp", "()Lokhttp3/internal/concurrent/TaskRunner;", "cancelAll", "", "cancelAllAndDecide", "cancelAllAndDecide$okhttp", "execute", "delayNanos", "", "cancelable", "block", "Lkotlin/Function0;", "idleLatch", "Ljava/util/concurrent/CountDownLatch;", "schedule", "task", "scheduleAndDecide", "recurrence", "scheduleAndDecide$okhttp", "toString", "AwaitIdleTask", "okhttp"}, k=1, mv={1, 1, 16})
public final class TaskQueue {
    private Task activeTask;
    private boolean cancelActiveTask;
    private final List<Task> futureTasks;
    private final String name;
    private boolean shutdown;
    private final TaskRunner taskRunner;

    public TaskQueue(TaskRunner taskRunner, String string2) {
        Intrinsics.checkParameterIsNotNull(taskRunner, "taskRunner");
        Intrinsics.checkParameterIsNotNull(string2, "name");
        this.taskRunner = taskRunner;
        this.name = string2;
        this.futureTasks = new ArrayList();
    }

    public static /* synthetic */ void execute$default(TaskQueue taskQueue, String string2, long l, boolean bl, Function0 function0, int n, Object object) {
        if ((n & 2) != 0) {
            l = 0L;
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        Intrinsics.checkParameterIsNotNull(string2, "name");
        Intrinsics.checkParameterIsNotNull(function0, "block");
        taskQueue.schedule(new Task(function0, string2, bl, string2, bl){
            final /* synthetic */ Function0 $block;
            final /* synthetic */ boolean $cancelable;
            final /* synthetic */ String $name;
            {
                this.$block = function0;
                this.$name = string2;
                this.$cancelable = bl;
                super(string3, bl2);
            }

            public long runOnce() {
                this.$block.invoke();
                return -1L;
            }
        }, l);
    }

    public static /* synthetic */ void schedule$default(TaskQueue taskQueue, String string2, long l, Function0 function0, int n, Object object) {
        if ((n & 2) != 0) {
            l = 0L;
        }
        Intrinsics.checkParameterIsNotNull(string2, "name");
        Intrinsics.checkParameterIsNotNull(function0, "block");
        taskQueue.schedule(new Task(function0, string2, string2){
            final /* synthetic */ Function0 $block;
            final /* synthetic */ String $name;
            {
                this.$block = function0;
                this.$name = string2;
                super(string3, false, 2, null);
            }

            public long runOnce() {
                return ((java.lang.Number)this.$block.invoke()).longValue();
            }
        }, l);
    }

    public static /* synthetic */ void schedule$default(TaskQueue taskQueue, Task task, long l, int n, Object object) {
        if ((n & 2) != 0) {
            l = 0L;
        }
        taskQueue.schedule(task, l);
    }

    public final void cancelAll() {
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
        TaskRunner taskRunner = this.taskRunner;
        synchronized (taskRunner) {
            if (this.cancelAllAndDecide$okhttp()) {
                this.taskRunner.kickCoordinator$okhttp(this);
            }
            Unit unit = Unit.INSTANCE;
            return;
        }
    }

    public final boolean cancelAllAndDecide$okhttp() {
        Task task = this.activeTask;
        if (task != null) {
            if (task == null) {
                Intrinsics.throwNpe();
            }
            if (task.getCancelable()) {
                this.cancelActiveTask = true;
            }
        }
        boolean bl = false;
        int n = this.futureTasks.size() - 1;
        while (n >= 0) {
            if (this.futureTasks.get(n).getCancelable()) {
                task = this.futureTasks.get(n);
                if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
                    TaskLoggerKt.access$log(task, this, "canceled");
                }
                this.futureTasks.remove(n);
                bl = true;
            }
            --n;
        }
        return bl;
    }

    public final void execute(String string2, long l, boolean bl, Function0<Unit> function0) {
        Intrinsics.checkParameterIsNotNull(string2, "name");
        Intrinsics.checkParameterIsNotNull(function0, "block");
        this.schedule(new /* invalid duplicate definition of identical inner class */, l);
    }

    public final Task getActiveTask$okhttp() {
        return this.activeTask;
    }

    public final boolean getCancelActiveTask$okhttp() {
        return this.cancelActiveTask;
    }

    public final List<Task> getFutureTasks$okhttp() {
        return this.futureTasks;
    }

    public final String getName$okhttp() {
        return this.name;
    }

    public final List<Task> getScheduledTasks() {
        TaskRunner taskRunner = this.taskRunner;
        synchronized (taskRunner) {
            return CollectionsKt.toList((Iterable)this.futureTasks);
        }
    }

    public final boolean getShutdown$okhttp() {
        return this.shutdown;
    }

    public final TaskRunner getTaskRunner$okhttp() {
        return this.taskRunner;
    }

    public final CountDownLatch idleLatch() {
        TaskRunner taskRunner = this.taskRunner;
        synchronized (taskRunner) {
            if (this.activeTask == null && this.futureTasks.isEmpty()) {
                return new CountDownLatch(0);
            }
            Task object2 = this.activeTask;
            if (object2 instanceof AwaitIdleTask) {
                return ((AwaitIdleTask)object2).getLatch();
            }
            for (Task task : this.futureTasks) {
                if (!(task instanceof AwaitIdleTask)) continue;
                return ((AwaitIdleTask)task).getLatch();
            }
            AwaitIdleTask awaitIdleTask = new AwaitIdleTask();
            if (!this.scheduleAndDecide$okhttp(awaitIdleTask, 0L, false)) return awaitIdleTask.getLatch();
            this.taskRunner.kickCoordinator$okhttp(this);
            return awaitIdleTask.getLatch();
        }
    }

    public final void schedule(String string2, long l, Function0<Long> function0) {
        Intrinsics.checkParameterIsNotNull(string2, "name");
        Intrinsics.checkParameterIsNotNull(function0, "block");
        this.schedule(new /* invalid duplicate definition of identical inner class */, l);
    }

    public final void schedule(Task object, long l) {
        Intrinsics.checkParameterIsNotNull(object, "task");
        TaskRunner taskRunner = this.taskRunner;
        synchronized (taskRunner) {
            if (this.shutdown) {
                if (((Task)object).getCancelable()) {
                    if (!TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) return;
                    TaskLoggerKt.access$log((Task)object, this, "schedule canceled (queue is shutdown)");
                    return;
                }
                if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
                    TaskLoggerKt.access$log((Task)object, this, "schedule failed (queue is shutdown)");
                }
                object = new RejectedExecutionException();
                throw (Throwable)object;
            }
            if (this.scheduleAndDecide$okhttp((Task)object, l, false)) {
                this.taskRunner.kickCoordinator$okhttp(this);
            }
            object = Unit.INSTANCE;
            return;
        }
    }

    public final boolean scheduleAndDecide$okhttp(Task task, long l, boolean bl) {
        int n;
        boolean bl2;
        int n2;
        block8 : {
            Object object;
            Intrinsics.checkParameterIsNotNull(task, "task");
            task.initQueue$okhttp(this);
            long l2 = this.taskRunner.getBackend().nanoTime();
            long l3 = l2 + l;
            n2 = this.futureTasks.indexOf(task);
            bl2 = false;
            if (n2 != -1) {
                if (task.getNextExecuteNanoTime$okhttp() <= l3) {
                    if (!TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) return false;
                    TaskLoggerKt.access$log(task, this, "already scheduled");
                    return false;
                }
                this.futureTasks.remove(n2);
            }
            task.setNextExecuteNanoTime$okhttp(l3);
            if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
                if (bl) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("run again after ");
                    ((StringBuilder)object).append(TaskLoggerKt.formatDuration(l3 - l2));
                    object = ((StringBuilder)object).toString();
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("scheduled after ");
                    ((StringBuilder)object).append(TaskLoggerKt.formatDuration(l3 - l2));
                    object = ((StringBuilder)object).toString();
                }
                TaskLoggerKt.access$log(task, this, (String)object);
            }
            object = this.futureTasks.iterator();
            n2 = 0;
            while (object.hasNext()) {
                n = ((Task)object.next()).getNextExecuteNanoTime$okhttp() - l2 > l ? 1 : 0;
                if (n == 0) {
                    ++n2;
                    continue;
                }
                break block8;
            }
            n2 = -1;
        }
        n = n2;
        if (n2 == -1) {
            n = this.futureTasks.size();
        }
        this.futureTasks.add(n, task);
        bl = bl2;
        if (n != 0) return bl;
        return true;
    }

    public final void setActiveTask$okhttp(Task task) {
        this.activeTask = task;
    }

    public final void setCancelActiveTask$okhttp(boolean bl) {
        this.cancelActiveTask = bl;
    }

    public final void setShutdown$okhttp(boolean bl) {
        this.shutdown = bl;
    }

    public final void shutdown() {
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
        TaskRunner taskRunner = this.taskRunner;
        synchronized (taskRunner) {
            this.shutdown = true;
            if (this.cancelAllAndDecide$okhttp()) {
                this.taskRunner.kickCoordinator$okhttp(this);
            }
            Unit unit = Unit.INSTANCE;
            return;
        }
    }

    public String toString() {
        return this.name;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lokhttp3/internal/concurrent/TaskQueue$AwaitIdleTask;", "Lokhttp3/internal/concurrent/Task;", "()V", "latch", "Ljava/util/concurrent/CountDownLatch;", "getLatch", "()Ljava/util/concurrent/CountDownLatch;", "runOnce", "", "okhttp"}, k=1, mv={1, 1, 16})
    private static final class AwaitIdleTask
    extends Task {
        private final CountDownLatch latch;

        public AwaitIdleTask() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Util.okHttpName);
            stringBuilder.append(" awaitIdle");
            super(stringBuilder.toString(), false);
            this.latch = new CountDownLatch(1);
        }

        public final CountDownLatch getLatch() {
            return this.latch;
        }

        @Override
        public long runOnce() {
            this.latch.countDown();
            return -1L;
        }
    }

}

