/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicDouble
extends Number
implements Serializable {
    private static final long serialVersionUID = 0L;
    private transient AtomicLong value;

    public AtomicDouble() {
        this(0.0);
    }

    public AtomicDouble(double d) {
        this.value = new AtomicLong(Double.doubleToRawLongBits(d));
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.value = new AtomicLong();
        this.set(objectInputStream.readDouble());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeDouble(this.get());
    }

    public final double addAndGet(double d) {
        double d2;
        long l;
        long l2;
        while (!this.value.compareAndSet(l = this.value.get(), l2 = Double.doubleToRawLongBits(d2 = Double.longBitsToDouble(l) + d))) {
        }
        return d2;
    }

    public final boolean compareAndSet(double d, double d2) {
        return this.value.compareAndSet(Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }

    @Override
    public double doubleValue() {
        return this.get();
    }

    @Override
    public float floatValue() {
        return (float)this.get();
    }

    public final double get() {
        return Double.longBitsToDouble(this.value.get());
    }

    public final double getAndAdd(double d) {
        double d2;
        long l;
        long l2;
        while (!this.value.compareAndSet(l = this.value.get(), l2 = Double.doubleToRawLongBits((d2 = Double.longBitsToDouble(l)) + d))) {
        }
        return d2;
    }

    public final double getAndSet(double d) {
        long l = Double.doubleToRawLongBits(d);
        return Double.longBitsToDouble(this.value.getAndSet(l));
    }

    @Override
    public int intValue() {
        return (int)this.get();
    }

    public final void lazySet(double d) {
        long l = Double.doubleToRawLongBits(d);
        this.value.lazySet(l);
    }

    @Override
    public long longValue() {
        return (long)this.get();
    }

    public final void set(double d) {
        long l = Double.doubleToRawLongBits(d);
        this.value.set(l);
    }

    public String toString() {
        return Double.toString(this.get());
    }

    public final boolean weakCompareAndSet(double d, double d2) {
        return this.value.weakCompareAndSet(Double.doubleToRawLongBits(d), Double.doubleToRawLongBits(d2));
    }
}

