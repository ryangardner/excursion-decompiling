/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class InterruptibleTask<T>
extends AtomicReference<Runnable>
implements Runnable {
    private static final Runnable DONE = new DoNothingRunnable();
    private static final Runnable INTERRUPTING = new DoNothingRunnable();
    private static final int MAX_BUSY_WAIT_SPINS = 1000;
    private static final Runnable PARKED = new DoNothingRunnable();

    InterruptibleTask() {
    }

    abstract void afterRanInterruptibly(@NullableDecl T var1, @NullableDecl Throwable var2);

    final void interruptTask() {
        Runnable runnable2 = (Runnable)this.get();
        if (!(runnable2 instanceof Thread)) return;
        if (!this.compareAndSet(runnable2, INTERRUPTING)) return;
        try {
            ((Thread)runnable2).interrupt();
            return;
        }
        finally {
            if (this.getAndSet(DONE) == PARKED) {
                LockSupport.unpark((Thread)runnable2);
            }
        }
    }

    abstract boolean isDone();

    @Override
    public final void run() {
        boolean bl;
        T t;
        block16 : {
            Thread thread2;
            block11 : {
                thread2 = Thread.currentThread();
                if (!this.compareAndSet(null, thread2)) {
                    return;
                }
                bl = this.isDone() ^ true;
                if (!bl) break block11;
                try {
                    t = this.runInterruptibly();
                }
                catch (Throwable throwable) {
                    block12 : {
                        if (this.compareAndSet(thread2, DONE)) break block12;
                        Runnable runnable2 = (Runnable)this.get();
                        int n = 0;
                        int n2 = 0;
                        do {
                            int n3;
                            block15 : {
                                block13 : {
                                    block14 : {
                                        if (runnable2 != INTERRUPTING && runnable2 != PARKED) {
                                            if (n == 0) break;
                                            thread2.interrupt();
                                            break;
                                        }
                                        n3 = n2 + 1;
                                        if (n3 <= 1000) break block13;
                                        Runnable runnable3 = PARKED;
                                        if (runnable2 == runnable3) break block14;
                                        n2 = n;
                                        if (!this.compareAndSet(INTERRUPTING, runnable3)) break block15;
                                    }
                                    n = !Thread.interrupted() && n == 0 ? 0 : 1;
                                    LockSupport.park(this);
                                    n2 = n;
                                    break block15;
                                }
                                Thread.yield();
                                n2 = n;
                            }
                            runnable2 = (Runnable)this.get();
                            n = n2;
                            n2 = n3;
                        } while (true);
                    }
                    if (!bl) return;
                    this.afterRanInterruptibly(null, throwable);
                    return;
                }
            }
            t = null;
            if (this.compareAndSet(thread2, DONE)) break block16;
            Runnable runnable4 = (Runnable)this.get();
            int n = 0;
            int n4 = 0;
            do {
                int n5;
                block19 : {
                    block17 : {
                        block18 : {
                            if (runnable4 != INTERRUPTING && runnable4 != PARKED) {
                                if (n == 0) break;
                                thread2.interrupt();
                                break;
                            }
                            n5 = n4 + 1;
                            if (n5 <= 1000) break block17;
                            Runnable runnable5 = PARKED;
                            if (runnable4 == runnable5) break block18;
                            n4 = n;
                            if (!this.compareAndSet(INTERRUPTING, runnable5)) break block19;
                        }
                        n = !Thread.interrupted() && n == 0 ? 0 : 1;
                        LockSupport.park(this);
                        n4 = n;
                        break block19;
                    }
                    Thread.yield();
                    n4 = n;
                }
                runnable4 = (Runnable)this.get();
                n = n4;
                n4 = n5;
            } while (true);
        }
        if (!bl) return;
        this.afterRanInterruptibly(t, null);
    }

    abstract T runInterruptibly() throws Exception;

    abstract String toPendingString();

    @Override
    public final String toString() {
        CharSequence charSequence;
        Object object = (Runnable)this.get();
        if (object == DONE) {
            charSequence = "running=[DONE]";
        } else if (object == INTERRUPTING) {
            charSequence = "running=[INTERRUPTED]";
        } else if (object instanceof Thread) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("running=[RUNNING ON ");
            ((StringBuilder)charSequence).append(((Thread)object).getName());
            ((StringBuilder)charSequence).append("]");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "running=[NOT STARTED YET]";
        }
        object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(this.toPendingString());
        return ((StringBuilder)object).toString();
    }

    private static final class DoNothingRunnable
    implements Runnable {
        private DoNothingRunnable() {
        }

        @Override
        public void run() {
        }
    }

}

