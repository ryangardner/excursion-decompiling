/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.concurrent;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;

@Metadata(bv={1, 0, 3}, d1={"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0001H\u0002\u001a2\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\f0\u000eH\b\u00a2\u0006\u0002\u0010\u000f\u001a'\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\b\u00a8\u0006\u0012"}, d2={"formatDuration", "", "ns", "", "log", "", "task", "Lokhttp3/internal/concurrent/Task;", "queue", "Lokhttp3/internal/concurrent/TaskQueue;", "message", "logElapsed", "T", "block", "Lkotlin/Function0;", "(Lokhttp3/internal/concurrent/Task;Lokhttp3/internal/concurrent/TaskQueue;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "taskLog", "messageBlock", "okhttp"}, k=2, mv={1, 1, 16})
public final class TaskLoggerKt {
    public static final String formatDuration(long l) {
        CharSequence charSequence;
        if (l <= (long)-999500000) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((l - (long)500000000) / (long)1000000000);
            ((StringBuilder)charSequence).append(" s ");
            charSequence = ((StringBuilder)charSequence).toString();
        } else if (l <= (long)-999500) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((l - (long)500000) / (long)1000000);
            ((StringBuilder)charSequence).append(" ms");
            charSequence = ((StringBuilder)charSequence).toString();
        } else if (l <= 0L) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((l - (long)500) / (long)1000);
            ((StringBuilder)charSequence).append(" \u00b5s");
            charSequence = ((StringBuilder)charSequence).toString();
        } else if (l < (long)999500) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((l + (long)500) / (long)1000);
            ((StringBuilder)charSequence).append(" \u00b5s");
            charSequence = ((StringBuilder)charSequence).toString();
        } else if (l < (long)999500000) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((l + (long)500000) / (long)1000000);
            ((StringBuilder)charSequence).append(" ms");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((l + (long)500000000) / (long)1000000000);
            ((StringBuilder)charSequence).append(" s ");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        charSequence = String.format("%6s", Arrays.copyOf(new Object[]{charSequence}, 1));
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "java.lang.String.format(format, *args)");
        return charSequence;
    }

    private static final void log(Task task, TaskQueue object, String string2) {
        Logger logger = TaskRunner.Companion.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((TaskQueue)object).getName$okhttp());
        stringBuilder.append(' ');
        object = StringCompanionObject.INSTANCE;
        object = String.format("%-22s", Arrays.copyOf(new Object[]{string2}, 1));
        Intrinsics.checkExpressionValueIsNotNull(object, "java.lang.String.format(format, *args)");
        stringBuilder.append((String)object);
        stringBuilder.append(": ");
        stringBuilder.append(task.getName());
        logger.fine(stringBuilder.toString());
    }

    public static final <T> T logElapsed(Task task, TaskQueue taskQueue, Function0<? extends T> object) {
        T t;
        long l;
        Intrinsics.checkParameterIsNotNull(task, "task");
        Intrinsics.checkParameterIsNotNull(taskQueue, "queue");
        Intrinsics.checkParameterIsNotNull(object, "block");
        boolean bl = TaskRunner.Companion.getLogger().isLoggable(Level.FINE);
        if (bl) {
            l = taskQueue.getTaskRunner$okhttp().getBackend().nanoTime();
            TaskLoggerKt.log(task, taskQueue, "starting");
        } else {
            l = -1L;
        }
        try {
            t = object.invoke();
        }
        catch (Throwable throwable) {
            InlineMarker.finallyStart(1);
            if (bl) {
                long l2 = taskQueue.getTaskRunner$okhttp().getBackend().nanoTime();
                object = new StringBuilder();
                ((StringBuilder)object).append("failed a run in ");
                ((StringBuilder)object).append(TaskLoggerKt.formatDuration(l2 - l));
                TaskLoggerKt.log(task, taskQueue, ((StringBuilder)object).toString());
            }
            InlineMarker.finallyEnd(1);
            throw throwable;
        }
        InlineMarker.finallyStart(1);
        if (bl) {
            long l3 = taskQueue.getTaskRunner$okhttp().getBackend().nanoTime();
            object = new StringBuilder();
            ((StringBuilder)object).append("finished run in ");
            ((StringBuilder)object).append(TaskLoggerKt.formatDuration(l3 - l));
            TaskLoggerKt.log(task, taskQueue, ((StringBuilder)object).toString());
        }
        InlineMarker.finallyEnd(1);
        return t;
    }

    public static final void taskLog(Task task, TaskQueue taskQueue, Function0<String> function0) {
        Intrinsics.checkParameterIsNotNull(task, "task");
        Intrinsics.checkParameterIsNotNull(taskQueue, "queue");
        Intrinsics.checkParameterIsNotNull(function0, "messageBlock");
        if (!TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) return;
        TaskLoggerKt.log(task, taskQueue, function0.invoke());
    }
}

