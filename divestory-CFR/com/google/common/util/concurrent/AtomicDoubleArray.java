/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.primitives.ImmutableLongArray;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLongArray;

public class AtomicDoubleArray
implements Serializable {
    private static final long serialVersionUID = 0L;
    private transient AtomicLongArray longs;

    public AtomicDoubleArray(int n) {
        this.longs = new AtomicLongArray(n);
    }

    public AtomicDoubleArray(double[] arrd) {
        int n = arrd.length;
        long[] arrl = new long[n];
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.longs = new AtomicLongArray(arrl);
                return;
            }
            arrl[n2] = Double.doubleToRawLongBits(arrd[n2]);
            ++n2;
        } while (true);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int n = objectInputStream.readInt();
        ImmutableLongArray.Builder builder = ImmutableLongArray.builder();
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.longs = new AtomicLongArray(builder.build().toArray());
                return;
            }
            builder.add(Double.doubleToRawLongBits(objectInputStream.readDouble()));
            ++n2;
        } while (true);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int n = this.length();
        objectOutputStream.writeInt(n);
        int n2 = 0;
        while (n2 < n) {
            objectOutputStream.writeDouble(this.get(n2));
            ++n2;
        }
    }

    public double addAndGet(int n, double d) {
        double d2;
        long l;
        long l2;
        while (!this.longs.compareAndSet(n, l2 = this.longs.get(n), l = Double.doubleToRawLongBits(d2 = Double.longBitsToDouble(l2) + d))) {
        }
        return d2;
    }

    public final boolean compareAndSet(int n, double d, double d2) {
        return this.longs.compareAndSet(n, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    public final double get(int n) {
        return Double.longBitsToDouble(this.longs.get(n));
    }

    public final double getAndAdd(int n, double d) {
        double d2;
        long l;
        long l2;
        while (!this.longs.compareAndSet(n, l2 = this.longs.get(n), l = Double.doubleToRawLongBits((d2 = Double.longBitsToDouble(l2)) + d))) {
        }
        return d2;
    }

    public final double getAndSet(int n, double d) {
        long l = Double.doubleToRawLongBits(d);
        return Double.longBitsToDouble(this.longs.getAndSet(n, l));
    }

    public final void lazySet(int n, double d) {
        long l = Double.doubleToRawLongBits(d);
        this.longs.lazySet(n, l);
    }

    public final int length() {
        return this.longs.length();
    }

    public final void set(int n, double d) {
        long l = Double.doubleToRawLongBits(d);
        this.longs.set(n, l);
    }

    public String toString() {
        int n = this.length() - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder((n + 1) * 19);
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(Double.longBitsToDouble(this.longs.get(n2)));
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(',');
            stringBuilder.append(' ');
            ++n2;
        } while (true);
    }

    public final boolean weakCompareAndSet(int n, double d, double d2) {
        return this.longs.weakCompareAndSet(n, Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }
}

