/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api;

import android.os.Bundle;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataBuffer;
import java.util.Iterator;

public class DataBufferResponse<T, R extends AbstractDataBuffer<T>>
extends Response<R>
implements DataBuffer<T> {
    public DataBufferResponse() {
    }

    public DataBufferResponse(R r) {
        super(r);
    }

    @Override
    public void close() {
        ((AbstractDataBuffer)this.getResult()).close();
    }

    @Override
    public T get(int n) {
        return ((AbstractDataBuffer)this.getResult()).get(n);
    }

    @Override
    public int getCount() {
        return ((AbstractDataBuffer)this.getResult()).getCount();
    }

    @Override
    public Bundle getMetadata() {
        return ((AbstractDataBuffer)this.getResult()).getMetadata();
    }

    @Override
    public boolean isClosed() {
        return ((AbstractDataBuffer)this.getResult()).isClosed();
    }

    @Override
    public Iterator<T> iterator() {
        return ((AbstractDataBuffer)this.getResult()).iterator();
    }

    @Override
    public void release() {
        ((AbstractDataBuffer)this.getResult()).release();
    }

    @Override
    public Iterator<T> singleRefIterator() {
        return ((AbstractDataBuffer)this.getResult()).singleRefIterator();
    }
}

