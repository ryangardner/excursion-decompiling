/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.hash.LongAddable;
import com.google.common.hash.Striped64;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

final class LongAdder
extends Striped64
implements Serializable,
LongAddable {
    private static final long serialVersionUID = 7249069246863182397L;

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.busy = 0;
        this.cells = null;
        this.base = objectInputStream.readLong();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeLong(this.sum());
    }

    @Override
    public void add(long l) {
        boolean bl;
        long l2;
        Object object = this.cells;
        if (object == null) {
            l2 = this.base;
            if (this.casBase(l2, l2 + l)) return;
        }
        int[] arrn = (int[])threadHashCode.get();
        boolean bl2 = bl = true;
        if (arrn != null) {
            bl2 = bl;
            if (object != null) {
                int n = ((Striped64.Cell[])object).length;
                bl2 = bl;
                if (n >= 1) {
                    object = object[n - 1 & arrn[0]];
                    bl2 = bl;
                    if (object != null) {
                        l2 = ((Striped64.Cell)object).value;
                        bl2 = ((Striped64.Cell)object).cas(l2, l2 + l);
                        if (bl2) return;
                    }
                }
            }
        }
        this.retryUpdate(l, arrn, bl2);
    }

    public void decrement() {
        this.add(-1L);
    }

    @Override
    public double doubleValue() {
        return this.sum();
    }

    @Override
    public float floatValue() {
        return this.sum();
    }

    @Override
    final long fn(long l, long l2) {
        return l + l2;
    }

    @Override
    public void increment() {
        this.add(1L);
    }

    @Override
    public int intValue() {
        return (int)this.sum();
    }

    @Override
    public long longValue() {
        return this.sum();
    }

    public void reset() {
        this.internalReset(0L);
    }

    @Override
    public long sum() {
        long l = this.base;
        Striped64.Cell[] arrcell = this.cells;
        long l2 = l;
        if (arrcell == null) return l2;
        int n = arrcell.length;
        int n2 = 0;
        do {
            l2 = l;
            if (n2 >= n) return l2;
            Striped64.Cell cell = arrcell[n2];
            l2 = l;
            if (cell != null) {
                l2 = l + cell.value;
            }
            ++n2;
            l = l2;
        } while (true);
    }

    public long sumThenReset() {
        long l = this.base;
        Striped64.Cell[] arrcell = this.cells;
        this.base = 0L;
        long l2 = l;
        if (arrcell == null) return l2;
        int n = arrcell.length;
        int n2 = 0;
        do {
            l2 = l;
            if (n2 >= n) return l2;
            Striped64.Cell cell = arrcell[n2];
            l2 = l;
            if (cell != null) {
                l2 = l + cell.value;
                cell.value = 0L;
            }
            ++n2;
            l = l2;
        } while (true);
    }

    public String toString() {
        return Long.toString(this.sum());
    }
}

