/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBufferObserver;
import java.util.HashSet;
import java.util.Iterator;

public final class DataBufferObserverSet
implements DataBufferObserver,
DataBufferObserver.Observable {
    private HashSet<DataBufferObserver> zaa = new HashSet();

    @Override
    public final void addObserver(DataBufferObserver dataBufferObserver) {
        this.zaa.add(dataBufferObserver);
    }

    public final void clear() {
        this.zaa.clear();
    }

    public final boolean hasObservers() {
        if (this.zaa.isEmpty()) return false;
        return true;
    }

    @Override
    public final void onDataChanged() {
        Iterator<DataBufferObserver> iterator2 = this.zaa.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onDataChanged();
        }
    }

    @Override
    public final void onDataRangeChanged(int n, int n2) {
        Iterator<DataBufferObserver> iterator2 = this.zaa.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onDataRangeChanged(n, n2);
        }
    }

    @Override
    public final void onDataRangeInserted(int n, int n2) {
        Iterator<DataBufferObserver> iterator2 = this.zaa.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onDataRangeInserted(n, n2);
        }
    }

    @Override
    public final void onDataRangeMoved(int n, int n2, int n3) {
        Iterator<DataBufferObserver> iterator2 = this.zaa.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onDataRangeMoved(n, n2, n3);
        }
    }

    @Override
    public final void onDataRangeRemoved(int n, int n2) {
        Iterator<DataBufferObserver> iterator2 = this.zaa.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onDataRangeRemoved(n, n2);
        }
    }

    @Override
    public final void removeObserver(DataBufferObserver dataBufferObserver) {
        this.zaa.remove(dataBufferObserver);
    }
}

