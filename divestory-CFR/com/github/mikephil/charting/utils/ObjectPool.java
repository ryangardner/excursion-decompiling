/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.utils;

import java.util.List;

public class ObjectPool<T extends Poolable> {
    private static int ids;
    private int desiredCapacity;
    private T modelObject;
    private Object[] objects;
    private int objectsPointer;
    private int poolId;
    private float replenishPercentage;

    private ObjectPool(int n, T t) {
        if (n <= 0) throw new IllegalArgumentException("Object Pool must be instantiated with a capacity greater than 0!");
        this.desiredCapacity = n;
        this.objects = new Object[n];
        this.objectsPointer = 0;
        this.modelObject = t;
        this.replenishPercentage = 1.0f;
        this.refillPool();
    }

    public static ObjectPool create(int n, Poolable poolable) {
        synchronized (ObjectPool.class) {
            ObjectPool<Poolable> objectPool = new ObjectPool<Poolable>(n, poolable);
            objectPool.poolId = ids++;
            return objectPool;
        }
    }

    private void refillPool() {
        this.refillPool(this.replenishPercentage);
    }

    private void refillPool(float f) {
        int n = this.desiredCapacity;
        int n2 = (int)((float)n * f);
        if (n2 < 1) {
            n2 = 1;
        } else if (n2 > n) {
            n2 = n;
        }
        n = 0;
        do {
            if (n >= n2) {
                this.objectsPointer = n2 - 1;
                return;
            }
            this.objects[n] = ((Poolable)this.modelObject).instantiate();
            ++n;
        } while (true);
    }

    private void resizePool() {
        int n;
        int n2 = this.desiredCapacity;
        this.desiredCapacity = n = n2 * 2;
        Object[] arrobject = new Object[n];
        n = 0;
        do {
            if (n >= n2) {
                this.objects = arrobject;
                return;
            }
            arrobject[n] = this.objects[n];
            ++n;
        } while (true);
    }

    public T get() {
        synchronized (this) {
            if (this.objectsPointer == -1 && this.replenishPercentage > 0.0f) {
                this.refillPool();
            }
            Poolable poolable = (Poolable)this.objects[this.objectsPointer];
            poolable.currentOwnerId = Poolable.NO_OWNER;
            --this.objectsPointer;
            return (T)poolable;
        }
    }

    public int getPoolCapacity() {
        return this.objects.length;
    }

    public int getPoolCount() {
        return this.objectsPointer + 1;
    }

    public int getPoolId() {
        return this.poolId;
    }

    public float getReplenishPercentage() {
        return this.replenishPercentage;
    }

    public void recycle(T object) {
        synchronized (this) {
            int n;
            if (((Poolable)object).currentOwnerId != Poolable.NO_OWNER) {
                if (((Poolable)object).currentOwnerId == this.poolId) {
                    object = new Object("The object passed is already stored in this pool!");
                    throw object;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The object to recycle already belongs to poolId ");
                stringBuilder.append(((Poolable)object).currentOwnerId);
                stringBuilder.append(".  Object cannot belong to two different pool instances simultaneously!");
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
                throw illegalArgumentException;
            }
            this.objectsPointer = n = this.objectsPointer + 1;
            if (n >= this.objects.length) {
                this.resizePool();
            }
            ((Poolable)object).currentOwnerId = this.poolId;
            this.objects[this.objectsPointer] = object;
            return;
        }
    }

    public void recycle(List<T> object) {
        synchronized (this) {
            while (object.size() + this.objectsPointer + 1 > this.desiredCapacity) {
                this.resizePool();
            }
            int n = object.size();
            for (int i = 0; i < n; ++i) {
                Poolable poolable = (Poolable)object.get(i);
                if (poolable.currentOwnerId != Poolable.NO_OWNER) {
                    if (poolable.currentOwnerId == this.poolId) {
                        object = new IllegalArgumentException("The object passed is already stored in this pool!");
                        throw object;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("The object to recycle already belongs to poolId ");
                    ((StringBuilder)object).append(poolable.currentOwnerId);
                    ((StringBuilder)object).append(".  Object cannot belong to two different pool instances simultaneously!");
                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)object).toString());
                    throw illegalArgumentException;
                }
                poolable.currentOwnerId = this.poolId;
                this.objects[this.objectsPointer + 1 + i] = poolable;
            }
            this.objectsPointer += n;
            return;
        }
    }

    public void setReplenishPercentage(float f) {
        float f2;
        if (f > 1.0f) {
            f2 = 1.0f;
        } else {
            f2 = f;
            if (f < 0.0f) {
                f2 = 0.0f;
            }
        }
        this.replenishPercentage = f2;
    }

    public static abstract class Poolable {
        public static int NO_OWNER = -1;
        int currentOwnerId = NO_OWNER;

        protected abstract Poolable instantiate();
    }

}

