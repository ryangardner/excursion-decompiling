/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.listener;

import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;

public interface OnDrawListener {
    public void onDrawFinished(DataSet<?> var1);

    public void onEntryAdded(Entry var1);

    public void onEntryMoved(Entry var1);
}

