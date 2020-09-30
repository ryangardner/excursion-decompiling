/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataBufferIterator;
import com.google.android.gms.common.data.DataBufferRef;
import com.google.android.gms.common.internal.Preconditions;
import java.util.NoSuchElementException;

public class SingleRefDataBufferIterator<T>
extends DataBufferIterator<T> {
    private T zac;

    public SingleRefDataBufferIterator(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            int n = this.zab;
            StringBuilder stringBuilder = new StringBuilder(46);
            stringBuilder.append("Cannot advance the iterator beyond ");
            stringBuilder.append(n);
            throw new NoSuchElementException(stringBuilder.toString());
        }
        ++this.zab;
        if (this.zab != 0) {
            ((DataBufferRef)Preconditions.checkNotNull(this.zac)).zaa(this.zab);
            return this.zac;
        }
        Object object = Preconditions.checkNotNull(this.zaa.get(0));
        this.zac = object;
        if (object instanceof DataBufferRef) {
            return this.zac;
        }
        String string2 = String.valueOf(this.zac.getClass());
        object = new StringBuilder(String.valueOf(string2).length() + 44);
        ((StringBuilder)object).append("DataBuffer reference of type ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" is not movable");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }
}

