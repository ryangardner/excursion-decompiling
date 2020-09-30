/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;

public abstract class EntityBuffer<T>
extends AbstractDataBuffer<T> {
    private boolean zaa = false;
    private ArrayList<Integer> zab;

    protected EntityBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    private final int zaa(int n) {
        if (n >= 0 && n < this.zab.size()) {
            return this.zab.get(n);
        }
        StringBuilder stringBuilder = new StringBuilder(53);
        stringBuilder.append("Position ");
        stringBuilder.append(n);
        stringBuilder.append(" is out of bounds for this buffer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private final void zaa() {
        synchronized (this) {
            if (this.zaa) return;
            int n = Preconditions.checkNotNull(this.mDataHolder).getCount();
            Object object = new ArrayList();
            this.zab = object;
            if (n > 0) {
                ((ArrayList)object).add(0);
                String string2 = this.getPrimaryDataMarkerColumn();
                int n2 = this.mDataHolder.getWindowIndex(0);
                object = this.mDataHolder.getString(string2, 0, n2);
                for (n2 = 1; n2 < n; ++n2) {
                    Object object2;
                    int n3 = this.mDataHolder.getWindowIndex(n2);
                    String string3 = this.mDataHolder.getString(string2, n2, n3);
                    if (string3 != null) {
                        object2 = object;
                        if (!string3.equals(object)) {
                            this.zab.add(n2);
                            object2 = string3;
                        }
                        object = object2;
                        continue;
                    }
                    n = String.valueOf(string2).length();
                    object2 = new StringBuilder(n + 78);
                    ((StringBuilder)object2).append("Missing value for markerColumn: ");
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append(", at row: ");
                    ((StringBuilder)object2).append(n2);
                    ((StringBuilder)object2).append(", for window: ");
                    ((StringBuilder)object2).append(n3);
                    object = new NullPointerException(((StringBuilder)object2).toString());
                    throw object;
                }
            }
            this.zaa = true;
            return;
        }
    }

    @Override
    public final T get(int n) {
        int n2;
        int n3;
        this.zaa();
        int n4 = this.zaa(n);
        int n5 = n3 = 0;
        if (n < 0) return this.getEntry(n4, n5);
        if (n == this.zab.size()) {
            n5 = n3;
            return this.getEntry(n4, n5);
        }
        if (n == this.zab.size() - 1) {
            n5 = Preconditions.checkNotNull(this.mDataHolder).getCount();
            n2 = this.zab.get(n);
        } else {
            n5 = this.zab.get(n + 1);
            n2 = this.zab.get(n);
        }
        if ((n5 -= n2) != 1) return this.getEntry(n4, n5);
        n = this.zaa(n);
        n2 = Preconditions.checkNotNull(this.mDataHolder).getWindowIndex(n);
        String string2 = this.getChildDataMarkerColumn();
        if (string2 == null) return this.getEntry(n4, n5);
        if (this.mDataHolder.getString(string2, n, n2) != null) return this.getEntry(n4, n5);
        n5 = n3;
        return this.getEntry(n4, n5);
    }

    protected String getChildDataMarkerColumn() {
        return null;
    }

    @Override
    public int getCount() {
        this.zaa();
        return this.zab.size();
    }

    protected abstract T getEntry(int var1, int var2);

    protected abstract String getPrimaryDataMarkerColumn();
}

