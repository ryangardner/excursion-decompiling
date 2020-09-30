/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Random;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import sun.misc.Unsafe;

abstract class Striped64
extends Number {
    static final int NCPU;
    private static final Unsafe UNSAFE;
    private static final long baseOffset;
    private static final long busyOffset;
    static final Random rng;
    static final ThreadLocal<int[]> threadHashCode;
    volatile transient long base;
    volatile transient int busy;
    @NullableDecl
    volatile transient Cell[] cells;

    static {
        threadHashCode = new ThreadLocal();
        rng = new Random();
        NCPU = Runtime.getRuntime().availableProcessors();
        try {
            Unsafe unsafe;
            UNSAFE = unsafe = Striped64.getUnsafe();
            baseOffset = unsafe.objectFieldOffset(Striped64.class.getDeclaredField("base"));
            busyOffset = UNSAFE.objectFieldOffset(Striped64.class.getDeclaredField("busy"));
            return;
        }
        catch (Exception exception) {
            throw new Error(exception);
        }
    }

    Striped64() {
    }

    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException securityException) {
            try {
                PrivilegedExceptionAction<Unsafe> privilegedExceptionAction = new PrivilegedExceptionAction<Unsafe>(){

                    @Override
                    public Unsafe run() throws Exception {
                        Field[] arrfield = Unsafe.class.getDeclaredFields();
                        int n = arrfield.length;
                        int n2 = 0;
                        while (n2 < n) {
                            Object object = arrfield[n2];
                            ((Field)object).setAccessible(true);
                            object = ((Field)object).get(null);
                            if (Unsafe.class.isInstance(object)) {
                                return (Unsafe)Unsafe.class.cast(object);
                            }
                            ++n2;
                        }
                        throw new NoSuchFieldError("the Unsafe");
                    }
                };
                return AccessController.doPrivileged(privilegedExceptionAction);
            }
            catch (PrivilegedActionException privilegedActionException) {
                throw new RuntimeException("Could not initialize intrinsics", privilegedActionException.getCause());
            }
        }
    }

    final boolean casBase(long l, long l2) {
        return UNSAFE.compareAndSwapLong(this, baseOffset, l, l2);
    }

    final boolean casBusy() {
        return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
    }

    abstract long fn(long var1, long var3);

    final void internalReset(long l) {
        Cell[] arrcell = this.cells;
        this.base = l;
        if (arrcell == null) return;
        int n = arrcell.length;
        int n2 = 0;
        while (n2 < n) {
            Cell cell = arrcell[n2];
            if (cell != null) {
                cell.value = l;
            }
            ++n2;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    final void retryUpdate(long var1_1, @NullableDecl int[] var3_2, boolean var4_6) {
        if (var3_2 == null) {
            var5_7 = Striped64.threadHashCode;
            var3_2 = new int[1];
            var5_7.set(var3_2);
            var7_15 = var6_14 = Striped64.rng.nextInt();
            if (var6_14 == 0) {
                var7_15 = 1;
            }
            var3_2[0] = var7_15;
        } else {
            var7_15 = var3_2[0];
        }
        var8_16 = var7_15;
        var7_15 = 0;
        var9_17 = var4_6;
        do {
            block30 : {
                block31 : {
                    if ((var5_9 = this.cells) == null || (var10_18 = var5_9.length) <= 0) break block30;
                    var11_19 = var5_9[var10_18 - 1 & var8_16];
                    if (var11_19 != null) break block31;
                    if (this.busy == 0) {
                        var5_10 = new Cell(var1_1);
                        if (this.busy == 0 && this.casBusy()) {
                            try {
                                var11_19 = this.cells;
                            }
                            catch (Throwable var3_3) {
                                this.busy = 0;
                                throw var3_3;
                            }
                            if (var11_19 != null && (var6_14 = var11_19.length) > 0 && var11_19[var6_14 = var6_14 - 1 & var8_16] == null) {
                                var11_19[var6_14] = var5_10;
                                var6_14 = 1;
                            } else {
                                var6_14 = 0;
                            }
                            this.busy = 0;
                            if (var6_14 == 0) continue;
                            return;
                        }
                    }
                    ** GOTO lbl-1000
                }
                if (!var9_17) {
                    var4_6 = true;
                    var6_14 = var7_15;
                } else {
                    var12_20 = var11_19.value;
                    if (var11_19.cas(var12_20, this.fn(var12_20, var1_1))) {
                        return;
                    }
                    if (var10_18 >= Striped64.NCPU || this.cells != var5_9) lbl-1000: // 2 sources:
                    {
                        var6_14 = 0;
                        var4_6 = var9_17;
                    } else if (var7_15 == 0) {
                        var6_14 = 1;
                        var4_6 = var9_17;
                    } else {
                        var4_6 = var9_17;
                        var6_14 = var7_15;
                        if (this.busy == 0) {
                            var4_6 = var9_17;
                            var6_14 = var7_15;
                            if (this.casBusy()) {
                                block29 : {
                                    if (this.cells != var5_9) break block29;
                                    var11_19 = new Cell[var10_18 << 1];
                                    for (var7_15 = 0; var7_15 < var10_18; ++var7_15) {
                                        var11_19[var7_15] = var5_9[var7_15];
                                    }
                                    try {
                                        this.cells = var11_19;
                                    }
                                    catch (Throwable var3_4) {
                                        throw var3_4;
                                    }
                                    finally {
                                        this.busy = 0;
                                    }
                                }
                                var7_15 = 0;
                                continue;
                            }
                        }
                    }
                }
                var7_15 = var8_16 ^ var8_16 << 13;
                var7_15 ^= var7_15 >>> 17;
                var3_2[0] = var8_16 = var7_15 ^ var7_15 << 5;
                var9_17 = var4_6;
                var7_15 = var6_14;
                continue;
            }
            if (this.busy == 0 && this.cells == var5_9 && this.casBusy()) {
                try {
                    if (this.cells == var5_9) {
                        var11_19 = new Cell[2];
                        var11_19[var8_16 & 1] = var5_12 = new Cell(var1_1);
                        this.cells = var11_19;
                        var6_14 = 1;
                    } else {
                        var6_14 = 0;
                    }
                    this.busy = 0;
                    if (var6_14 == 0) continue;
                    return;
                }
                catch (Throwable var3_5) {
                    this.busy = 0;
                    throw var3_5;
                }
            }
            var12_20 = this.base;
            if (this.casBase(var12_20, this.fn(var12_20, var1_1))) return;
        } while (true);
    }

    static final class Cell {
        private static final Unsafe UNSAFE;
        private static final long valueOffset;
        volatile long p0;
        volatile long p1;
        volatile long p2;
        volatile long p3;
        volatile long p4;
        volatile long p5;
        volatile long p6;
        volatile long q0;
        volatile long q1;
        volatile long q2;
        volatile long q3;
        volatile long q4;
        volatile long q5;
        volatile long q6;
        volatile long value;

        static {
            try {
                Unsafe unsafe;
                UNSAFE = unsafe = Striped64.getUnsafe();
                valueOffset = unsafe.objectFieldOffset(Cell.class.getDeclaredField("value"));
                return;
            }
            catch (Exception exception) {
                throw new Error(exception);
            }
        }

        Cell(long l) {
            this.value = l;
        }

        final boolean cas(long l, long l2) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, l, l2);
        }
    }

}

