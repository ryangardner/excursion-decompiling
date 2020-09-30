/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ExecutionList {
    private static final Logger log = Logger.getLogger(ExecutionList.class.getName());
    private boolean executed;
    @NullableDecl
    private RunnableExecutorPair runnables;

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

    /*
     * Enabled unnecessary exception pruning
     */
    public void add(Runnable runnable2, Executor executor) {
        Preconditions.checkNotNull(runnable2, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        synchronized (this) {
            if (!this.executed) {
                RunnableExecutorPair runnableExecutorPair;
                this.runnables = runnableExecutorPair = new RunnableExecutorPair(runnable2, executor, this.runnables);
                return;
            }
        }
        ExecutionList.executeListener(runnable2, executor);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public void execute() {
        RunnableExecutorPair runnableExecutorPair;
        RunnableExecutorPair runnableExecutorPair2;
        synchronized (this) {
            if (this.executed) {
                return;
            }
            this.executed = true;
            runnableExecutorPair = this.runnables;
            runnableExecutorPair2 = null;
            this.runnables = null;
        }
        do {
            RunnableExecutorPair runnableExecutorPair3 = runnableExecutorPair2;
            if (runnableExecutorPair == null) {
                while (runnableExecutorPair3 != null) {
                    ExecutionList.executeListener(runnableExecutorPair3.runnable, runnableExecutorPair3.executor);
                    runnableExecutorPair3 = runnableExecutorPair3.next;
                }
                return;
            }
            runnableExecutorPair3 = runnableExecutorPair.next;
            runnableExecutorPair.next = runnableExecutorPair2;
            runnableExecutorPair2 = runnableExecutorPair;
            runnableExecutorPair = runnableExecutorPair3;
        } while (true);
    }

    private static final class RunnableExecutorPair {
        final Executor executor;
        @NullableDecl
        RunnableExecutorPair next;
        final Runnable runnable;

        RunnableExecutorPair(Runnable runnable2, Executor executor, RunnableExecutorPair runnableExecutorPair) {
            this.runnable = runnable2;
            this.executor = executor;
            this.next = runnableExecutorPair;
        }
    }

}

