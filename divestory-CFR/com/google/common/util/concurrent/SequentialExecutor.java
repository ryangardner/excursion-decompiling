/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

final class SequentialExecutor
implements Executor {
    private static final Logger log = Logger.getLogger(SequentialExecutor.class.getName());
    private final Executor executor;
    private final Deque<Runnable> queue = new ArrayDeque<Runnable>();
    private final QueueWorker worker = new QueueWorker();
    private long workerRunCount = 0L;
    private WorkerRunningState workerRunningState = WorkerRunningState.IDLE;

    SequentialExecutor(Executor executor) {
        this.executor = Preconditions.checkNotNull(executor);
    }

    static /* synthetic */ WorkerRunningState access$200(SequentialExecutor sequentialExecutor) {
        return sequentialExecutor.workerRunningState;
    }

    static /* synthetic */ long access$308(SequentialExecutor sequentialExecutor) {
        long l = sequentialExecutor.workerRunCount;
        sequentialExecutor.workerRunCount = 1L + l;
        return l;
    }

    static /* synthetic */ Logger access$400() {
        return log;
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    @Override
    public void execute(final Runnable runnable2) {
        Preconditions.checkNotNull(runnable2);
        Deque<Runnable> deque = this.queue;
        synchronized (deque) {
            if (this.workerRunningState != WorkerRunningState.RUNNING && this.workerRunningState != WorkerRunningState.QUEUED) {
                long l = this.workerRunCount;
                Runnable runnable3 = new Runnable(){

                    @Override
                    public void run() {
                        runnable2.run();
                    }
                };
                this.queue.add(runnable3);
                this.workerRunningState = WorkerRunningState.QUEUING;
                // MONITOREXIT [5, 12] lbl11 : MonitorExitStatement: MONITOREXIT : var2_5
                boolean bl = true;
                boolean bl2 = true;
                try {
                    this.executor.execute(this.worker);
                    if (this.workerRunningState == WorkerRunningState.QUEUING) {
                        bl2 = false;
                    }
                    if (bl2) {
                        return;
                    }
                    runnable3 = this.queue;
                }
                catch (Error error) {
                }
                catch (RuntimeException runtimeException) {
                    // empty catch block
                }
                synchronized (runnable3) {
                    if (this.workerRunCount != l) return;
                    if (this.workerRunningState != WorkerRunningState.QUEUING) return;
                    this.workerRunningState = WorkerRunningState.QUEUED;
                    return;
                }
                deque = this.queue;
                synchronized (deque) {
                    void var1_4;
                    bl2 = (this.workerRunningState == WorkerRunningState.IDLE || this.workerRunningState == WorkerRunningState.QUEUING) && this.queue.removeLastOccurrence(runnable3) ? bl : false;
                    if (!(var1_4 instanceof RejectedExecutionException)) throw var1_4;
                    if (bl2) throw var1_4;
                    return;
                }
            }
            this.queue.add(runnable2);
            return;
        }
    }

    private final class QueueWorker
    implements Runnable {
        private QueueWorker() {
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        private void workOnQueue() {
            var1_1 = false;
            var2_2 = false;
            do {
                var3_3 = var2_2;
                var4_4 = SequentialExecutor.access$100(SequentialExecutor.this);
                var3_3 = var2_2;
                // MONITORENTER : var4_4
                var5_6 = var1_1;
                if (var1_1) ** GOTO lbl22
                if (SequentialExecutor.access$200(SequentialExecutor.this) == WorkerRunningState.RUNNING) {
                    // MONITOREXIT : var4_4
                    if (var2_2 == false) return;
                    Thread.currentThread().interrupt();
                    return;
                }
                SequentialExecutor.access$308(SequentialExecutor.this);
                SequentialExecutor.access$202(SequentialExecutor.this, WorkerRunningState.RUNNING);
                var5_6 = true;
lbl22: // 2 sources:
                if ((var6_7 = (Runnable)SequentialExecutor.access$100(SequentialExecutor.this).poll()) == null) {
                    SequentialExecutor.access$202(SequentialExecutor.this, WorkerRunningState.IDLE);
                    // MONITOREXIT : var4_4
                    if (var2_2 == false) return;
                    Thread.currentThread().interrupt();
                    return;
                }
                var3_3 = var2_2;
                break;
            } while (true);
            {
                catch (Throwable var6_8) {}
                var3_3 = var2_2;
                try {
                    throw var6_8;
                }
                catch (Throwable var6_9) {
                    if (var3_3 == false) throw var6_9;
                    Thread.currentThread().interrupt();
                    throw var6_9;
                }
            }
            {
                var7_10 = Thread.interrupted();
                var3_3 = var2_2 |= var7_10;
                try {
                    var6_7.run();
                    var1_1 = var5_6;
                }
                catch (RuntimeException var4_5) {
                    var3_3 = var2_2;
                    var8_11 = SequentialExecutor.access$400();
                    var3_3 = var2_2;
                    var9_12 = Level.SEVERE;
                    var3_3 = var2_2;
                    var3_3 = var2_2;
                    var10_13 = new StringBuilder();
                    var3_3 = var2_2;
                    var10_13.append("Exception while executing runnable ");
                    var3_3 = var2_2;
                    var10_13.append(var6_7);
                    var3_3 = var2_2;
                    var8_11.log(var9_12, var10_13.toString(), var4_5);
                    var1_1 = var5_6;
                }
                continue;
            }
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public void run() {
            try {
                this.workOnQueue();
                return;
            }
            catch (Error error) {
                Deque deque = SequentialExecutor.this.queue;
                synchronized (deque) {
                    SequentialExecutor.this.workerRunningState = WorkerRunningState.IDLE;
                    throw error;
                }
            }
        }
    }

    static final class WorkerRunningState
    extends Enum<WorkerRunningState> {
        private static final /* synthetic */ WorkerRunningState[] $VALUES;
        public static final /* enum */ WorkerRunningState IDLE;
        public static final /* enum */ WorkerRunningState QUEUED;
        public static final /* enum */ WorkerRunningState QUEUING;
        public static final /* enum */ WorkerRunningState RUNNING;

        static {
            WorkerRunningState workerRunningState;
            IDLE = new WorkerRunningState();
            QUEUING = new WorkerRunningState();
            QUEUED = new WorkerRunningState();
            RUNNING = workerRunningState = new WorkerRunningState();
            $VALUES = new WorkerRunningState[]{IDLE, QUEUING, QUEUED, workerRunningState};
        }

        public static WorkerRunningState valueOf(String string2) {
            return Enum.valueOf(WorkerRunningState.class, string2);
        }

        public static WorkerRunningState[] values() {
            return (WorkerRunningState[])$VALUES.clone();
        }
    }

}

