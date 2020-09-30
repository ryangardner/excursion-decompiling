/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.utils.ObjectPool;
import java.util.List;

public class MPPointD
extends ObjectPool.Poolable {
    private static ObjectPool<MPPointD> pool;
    public double x;
    public double y;

    static {
        ObjectPool objectPool;
        pool = objectPool = ObjectPool.create(64, new MPPointD(0.0, 0.0));
        objectPool.setReplenishPercentage(0.5f);
    }

    private MPPointD(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public static MPPointD getInstance(double d, double d2) {
        MPPointD mPPointD = pool.get();
        mPPointD.x = d;
        mPPointD.y = d2;
        return mPPointD;
    }

    public static void recycleInstance(MPPointD mPPointD) {
        pool.recycle(mPPointD);
    }

    public static void recycleInstances(List<MPPointD> list) {
        pool.recycle(list);
    }

    @Override
    protected ObjectPool.Poolable instantiate() {
        return new MPPointD(0.0, 0.0);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MPPointD, x: ");
        stringBuilder.append(this.x);
        stringBuilder.append(", y: ");
        stringBuilder.append(this.y);
        return stringBuilder.toString();
    }
}

