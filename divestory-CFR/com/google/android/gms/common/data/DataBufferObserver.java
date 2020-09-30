/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.data;

public interface DataBufferObserver {
    public void onDataChanged();

    public void onDataRangeChanged(int var1, int var2);

    public void onDataRangeInserted(int var1, int var2);

    public void onDataRangeMoved(int var1, int var2, int var3);

    public void onDataRangeRemoved(int var1, int var2);

    public static interface Observable {
        public void addObserver(DataBufferObserver var1);

        public void removeObserver(DataBufferObserver var1);
    }

}

