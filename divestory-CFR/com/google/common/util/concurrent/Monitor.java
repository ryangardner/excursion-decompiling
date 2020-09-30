/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Monitor {
    private Guard activeGuards = null;
    private final boolean fair;
    private final ReentrantLock lock;

    public Monitor() {
        this(false);
    }

    public Monitor(boolean bl) {
        this.fair = bl;
        this.lock = new ReentrantLock(bl);
    }

    private void await(Guard guard, boolean bl) throws InterruptedException {
        if (bl) {
            this.signalNextWaiter();
        }
        this.beginWaitingFor(guard);
        try {
            do {
                guard.condition.await();
            } while (!(bl = guard.isSatisfied()));
            this.endWaitingFor(guard);
            return;
        }
        catch (Throwable throwable) {
            this.endWaitingFor(guard);
            throw throwable;
        }
    }

    /*
     * Exception decompiling
     */
    private boolean awaitNanos(Guard var1_1, long var2_2, boolean var4_3) throws InterruptedException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[DOLOOP]], but top level block is 0[TRYBLOCK]
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

    private void awaitUninterruptibly(Guard guard, boolean bl) {
        if (bl) {
            this.signalNextWaiter();
        }
        this.beginWaitingFor(guard);
        try {
            do {
                guard.condition.awaitUninterruptibly();
            } while (!(bl = guard.isSatisfied()));
            this.endWaitingFor(guard);
            return;
        }
        catch (Throwable throwable) {
            this.endWaitingFor(guard);
            throw throwable;
        }
    }

    private void beginWaitingFor(Guard guard) {
        int n = guard.waiterCount;
        guard.waiterCount = n + 1;
        if (n != 0) return;
        guard.next = this.activeGuards;
        this.activeGuards = guard;
    }

    private void endWaitingFor(Guard guard) {
        int n;
        guard.waiterCount = n = guard.waiterCount - 1;
        if (n != 0) return;
        Guard guard2 = this.activeGuards;
        Guard guard3 = null;
        do {
            if (guard2 == guard) {
                if (guard3 == null) {
                    this.activeGuards = guard2.next;
                    break;
                }
                guard3.next = guard2.next;
                break;
            }
            Guard guard4 = guard2.next;
            guard3 = guard2;
            guard2 = guard4;
        } while (true);
        guard2.next = null;
    }

    private static long initNanoTime(long l) {
        long l2;
        if (l <= 0L) {
            return 0L;
        }
        l = l2 = System.nanoTime();
        if (l2 != 0L) return l;
        return 1L;
    }

    private boolean isSatisfied(Guard guard) {
        try {
            return guard.isSatisfied();
        }
        catch (Throwable throwable) {
            this.signalAllWaiters();
            throw throwable;
        }
    }

    private static long remainingNanos(long l, long l2) {
        long l3 = 0L;
        if (l2 > 0L) return l2 - (System.nanoTime() - l);
        return l3;
    }

    private void signalAllWaiters() {
        Guard guard = this.activeGuards;
        while (guard != null) {
            guard.condition.signalAll();
            guard = guard.next;
        }
    }

    private void signalNextWaiter() {
        Guard guard = this.activeGuards;
        while (guard != null) {
            if (this.isSatisfied(guard)) {
                guard.condition.signal();
                return;
            }
            guard = guard.next;
        }
    }

    private static long toSafeNanos(long l, TimeUnit timeUnit) {
        return Longs.constrainToRange(timeUnit.toNanos(l), 0L, 6917529027641081853L);
    }

    public void enter() {
        this.lock.lock();
    }

    /*
     * Exception decompiling
     */
    public boolean enter(long var1_1, TimeUnit var3_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[UNCONDITIONALDOLOOP]
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

    public boolean enterIf(Guard guard) {
        if (guard.monitor != this) throw new IllegalMonitorStateException();
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            boolean bl = guard.isSatisfied();
            if (bl) return bl;
            reentrantLock.unlock();
            return bl;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
    }

    public boolean enterIf(Guard guard, long l, TimeUnit timeUnit) {
        if (guard.monitor != this) throw new IllegalMonitorStateException();
        if (!this.enter(l, timeUnit)) {
            return false;
        }
        try {
            boolean bl = guard.isSatisfied();
            if (bl) return bl;
            this.lock.unlock();
            return bl;
        }
        catch (Throwable throwable) {
            this.lock.unlock();
            throw throwable;
        }
    }

    public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
        if (guard.monitor != this) throw new IllegalMonitorStateException();
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        try {
            boolean bl = guard.isSatisfied();
            if (bl) return bl;
            reentrantLock.unlock();
            return bl;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
    }

    public boolean enterIfInterruptibly(Guard guard, long l, TimeUnit timeUnit) throws InterruptedException {
        if (guard.monitor != this) throw new IllegalMonitorStateException();
        ReentrantLock reentrantLock = this.lock;
        if (!reentrantLock.tryLock(l, timeUnit)) {
            return false;
        }
        try {
            boolean bl = guard.isSatisfied();
            if (bl) return bl;
            reentrantLock.unlock();
            return bl;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
    }

    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    public boolean enterInterruptibly(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.lock.tryLock(l, timeUnit);
    }

    public void enterWhen(Guard guard) throws InterruptedException {
        if (guard.monitor != this) throw new IllegalMonitorStateException();
        ReentrantLock reentrantLock = this.lock;
        boolean bl = reentrantLock.isHeldByCurrentThread();
        reentrantLock.lockInterruptibly();
        try {
            if (guard.isSatisfied()) return;
            this.await(guard, bl);
            return;
        }
        catch (Throwable throwable) {
            this.leave();
            throw throwable;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public boolean enterWhen(Guard var1_1, long var2_4, TimeUnit var4_5) throws InterruptedException {
        var5_6 = Monitor.toSafeNanos(var2_4, var4_5);
        if (var1_1.monitor != this) throw new IllegalMonitorStateException();
        var7_7 = this.lock;
        var8_8 = var7_7.isHeldByCurrentThread();
        var9_9 = this.fair;
        var10_10 = false;
        if (var9_9) ** GOTO lbl-1000
        if (Thread.interrupted() != false) throw new InterruptedException();
        if (var7_7.tryLock()) {
            var11_11 = 0L;
        } else lbl-1000: // 2 sources:
        {
            var11_11 = Monitor.initNanoTime(var5_6);
            if (!var7_7.tryLock(var2_4, var4_5)) {
                return false;
            }
        }
        try {
            if (var1_1.isSatisfied() || (var9_9 = this.awaitNanos(var1_1, var2_4 = var11_11 == 0L ? var5_6 : Monitor.remainingNanos(var11_11, var5_6), var8_8))) {
                var10_10 = true;
            }
            if (var10_10 != false) return var10_10;
            var7_7.unlock();
            return var10_10;
        }
        catch (Throwable var1_2) {
            if (var8_8 != false) throw var1_2;
            try {
                this.signalNextWaiter();
                throw var1_2;
            }
            finally {
                var7_7.unlock();
            }
        }
    }

    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.monitor != this) throw new IllegalMonitorStateException();
        ReentrantLock reentrantLock = this.lock;
        boolean bl = reentrantLock.isHeldByCurrentThread();
        reentrantLock.lock();
        try {
            if (guard.isSatisfied()) return;
            this.awaitUninterruptibly(guard, bl);
            return;
        }
        catch (Throwable throwable) {
            this.leave();
            throw throwable;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public boolean enterWhenUninterruptibly(Guard guard, long l, TimeUnit object) {
        boolean bl2;
        long l3;
        long l2;
        void var1_5;
        boolean bl3;
        boolean bl;
        block20 : {
            l2 = Monitor.toSafeNanos(l, (TimeUnit)((Object)object));
            if (guard.monitor != this) throw new IllegalMonitorStateException();
            object = this.lock;
            boolean bl4 = ((ReentrantLock)object).isHeldByCurrentThread();
            bl3 = Thread.interrupted();
            bl = true;
            bl2 = bl3;
            if (!this.fair) {
                bl2 = bl3;
                if (((ReentrantLock)object).tryLock()) {
                    l = 0L;
                    bl2 = bl4;
                    break block20;
                }
            }
            bl2 = bl3;
            l3 = Monitor.initNanoTime(l2);
            l = l2;
            do {
                bl2 = bl3;
                try {
                    boolean bl5 = ((ReentrantLock)object).tryLock(l, TimeUnit.NANOSECONDS);
                    if (bl5) {
                        l = l3;
                        bl2 = bl4;
                        break;
                    }
                    if (!bl3) return false;
                    Thread.currentThread().interrupt();
                    return false;
                }
                catch (InterruptedException interruptedException) {
                    try {
                        l = Monitor.remainingNanos(l3, l2);
                        return (boolean)l;
                    }
                    finally {
                        bl3 = true;
                        continue;
                    }
                }
                break;
            } while (true);
        }
        do {
            block21 : {
                l3 = l;
                if (!guard.isSatisfied()) {
                    long l4;
                    if (l == 0L) {
                        l3 = l;
                        l = Monitor.initNanoTime(l2);
                        l4 = l2;
                    } else {
                        l3 = l;
                        l4 = Monitor.remainingNanos(l, l2);
                    }
                    l3 = l;
                    bl = bl2 = this.awaitNanos(guard, l4, bl2);
                }
                if (bl) break block21;
                bl2 = bl3;
                ((ReentrantLock)object).unlock();
            }
            if (!bl3) return bl;
            Thread.currentThread().interrupt();
            return bl;
            catch (Throwable throwable) {
                bl2 = bl3;
                ((ReentrantLock)object).unlock();
                bl2 = bl3;
                throw throwable;
                catch (InterruptedException interruptedException) {
                    bl2 = false;
                    bl3 = true;
                    l = l3;
                    continue;
                }
                catch (Throwable throwable2) {
                    // empty catch block
                }
            }
            break;
        } while (true);
        if (!bl2) throw var1_5;
        Thread.currentThread().interrupt();
        throw var1_5;
    }

    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }

    public int getQueueLength() {
        return this.lock.getQueueLength();
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.monitor != this) throw new IllegalMonitorStateException();
        this.lock.lock();
        try {
            int n = guard.waiterCount;
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    public boolean hasQueuedThread(Thread thread2) {
        return this.lock.hasQueuedThread(thread2);
    }

    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }

    public boolean hasWaiters(Guard guard) {
        if (this.getWaitQueueLength(guard) <= 0) return false;
        return true;
    }

    public boolean isFair() {
        return this.fair;
    }

    public boolean isOccupied() {
        return this.lock.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }

    public void leave() {
        ReentrantLock reentrantLock = this.lock;
        try {
            if (reentrantLock.getHoldCount() != 1) return;
            this.signalNextWaiter();
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public boolean tryEnter() {
        return this.lock.tryLock();
    }

    public boolean tryEnterIf(Guard guard) {
        if (guard.monitor != this) throw new IllegalMonitorStateException();
        ReentrantLock reentrantLock = this.lock;
        if (!reentrantLock.tryLock()) {
            return false;
        }
        try {
            boolean bl = guard.isSatisfied();
            if (bl) return bl;
            reentrantLock.unlock();
            return bl;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
    }

    public void waitFor(Guard guard) throws InterruptedException {
        boolean bl = guard.monitor == this;
        if (!(bl & this.lock.isHeldByCurrentThread())) throw new IllegalMonitorStateException();
        if (guard.isSatisfied()) return;
        this.await(guard, true);
    }

    public boolean waitFor(Guard guard, long l, TimeUnit timeUnit) throws InterruptedException {
        l = Monitor.toSafeNanos(l, timeUnit);
        boolean bl = guard.monitor == this;
        if (!(bl & this.lock.isHeldByCurrentThread())) throw new IllegalMonitorStateException();
        if (guard.isSatisfied()) {
            return true;
        }
        if (Thread.interrupted()) throw new InterruptedException();
        return this.awaitNanos(guard, l, true);
    }

    public void waitForUninterruptibly(Guard guard) {
        boolean bl = guard.monitor == this;
        if (!(bl & this.lock.isHeldByCurrentThread())) throw new IllegalMonitorStateException();
        if (guard.isSatisfied()) return;
        this.awaitUninterruptibly(guard, true);
    }

    /*
     * WARNING - void declaration
     */
    public boolean waitForUninterruptibly(Guard guard, long l, TimeUnit object) {
        void var1_4;
        long l2 = Monitor.toSafeNanos(l, object);
        object = guard.monitor;
        boolean bl = true;
        boolean bl2 = object == this;
        if (!(bl2 & this.lock.isHeldByCurrentThread())) throw new IllegalMonitorStateException();
        if (guard.isSatisfied()) {
            return true;
        }
        long l3 = Monitor.initNanoTime(l2);
        boolean bl3 = Thread.interrupted();
        l = l2;
        boolean bl4 = true;
        do {
            try {
                bl4 = this.awaitNanos(guard, l, bl4);
                if (!bl3) return bl4;
                Thread.currentThread().interrupt();
                return bl4;
            }
            catch (Throwable throwable) {
            }
            catch (InterruptedException interruptedException) {
                try {
                    bl3 = guard.isSatisfied();
                    if (bl3) {
                        Thread.currentThread().interrupt();
                        return true;
                    }
                    l = Monitor.remainingNanos(l3, l2);
                    bl3 = true;
                    bl4 = false;
                    continue;
                }
                catch (Throwable throwable) {
                    bl3 = bl;
                }
            }
            break;
        } while (true);
        if (!bl3) throw var1_4;
        Thread.currentThread().interrupt();
        throw var1_4;
    }

    public static abstract class Guard {
        final Condition condition;
        final Monitor monitor;
        @NullableDecl
        Guard next;
        int waiterCount = 0;

        protected Guard(Monitor monitor) {
            this.monitor = Preconditions.checkNotNull(monitor, "monitor");
            this.condition = monitor.lock.newCondition();
        }

        public abstract boolean isSatisfied();
    }

}

