/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataBufferIterator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.SingleRefDataBufferIterator;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T>
implements DataBuffer<T> {
    protected final DataHolder mDataHolder;

    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.mDataHolder = dataHolder;
    }

    @Override
    public final void close() {
        this.release();
    }

    @Override
    public abstract T get(int var1);

    @Override
    public int getCount() {
        DataHolder dataHolder = this.mDataHolder;
        if (dataHolder != null) return dataHolder.getCount();
        return 0;
    }

    @Override
    public Bundle getMetadata() {
        DataHolder dataHolder = this.mDataHolder;
        if (dataHolder != null) return dataHolder.getMetadata();
        return null;
    }

    @Deprecated
    @Override
    public boolean isClosed() {
        DataHolder dataHolder = this.mDataHolder;
        if (dataHolder == null) return true;
        if (!dataHolder.isClosed()) return false;
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new DataBufferIterator<T>(this);
    }

    @Override
    public void release() {
        DataHolder dataHolder = this.mDataHolder;
        if (dataHolder == null) return;
        dataHolder.close();
    }

    @Override
    public Iterator<T> singleRefIterator() {
        return new SingleRefDataBufferIterator<T>(this);
    }
}

