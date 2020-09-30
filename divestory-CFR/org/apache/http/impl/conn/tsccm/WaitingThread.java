/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn.tsccm;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import org.apache.http.impl.conn.tsccm.RouteSpecificPool;

public class WaitingThread {
    private boolean aborted;
    private final Condition cond;
    private final RouteSpecificPool pool;
    private Thread waiter;

    public WaitingThread(Condition condition, RouteSpecificPool routeSpecificPool) {
        if (condition == null) throw new IllegalArgumentException("Condition must not be null.");
        this.cond = condition;
        this.pool = routeSpecificPool;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public boolean await(Date var1_1) throws InterruptedException {
        if (this.waiter != null) {
            var1_1 = new StringBuilder();
            var1_1.append("A thread is already waiting on this object.\ncaller: ");
            var1_1.append(Thread.currentThread());
            var1_1.append("\nwaiter: ");
            var1_1.append(this.waiter);
            throw new IllegalStateException(var1_1.toString());
        }
        if (this.aborted != false) throw new InterruptedException("Operation interrupted");
        this.waiter = Thread.currentThread();
        if (var1_1 == null) ** GOTO lbl18
        try {
            block5 : {
                var2_3 = this.cond.awaitUntil((Date)var1_1);
                break block5;
lbl18: // 1 sources:
                this.cond.await();
                var2_3 = true;
            }
            var3_4 = this.aborted;
            if (!var3_4) {
                this.waiter = null;
                return var2_3;
            }
            var1_1 = new InterruptedException("Operation interrupted");
            throw var1_1;
        }
        catch (Throwable var1_2) {
            this.waiter = null;
            throw var1_2;
        }
    }

    public final Condition getCondition() {
        return this.cond;
    }

    public final RouteSpecificPool getPool() {
        return this.pool;
    }

    public final Thread getThread() {
        return this.waiter;
    }

    public void interrupt() {
        this.aborted = true;
        this.cond.signalAll();
    }

    public void wakeup() {
        if (this.waiter == null) throw new IllegalStateException("Nobody waiting on this object.");
        this.cond.signalAll();
    }
}

