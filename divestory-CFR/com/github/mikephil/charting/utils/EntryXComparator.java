/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.data.Entry;
import java.util.Comparator;

public class EntryXComparator
implements Comparator<Entry> {
    @Override
    public int compare(Entry entry, Entry entry2) {
        float f = entry.getX() - entry2.getX() FCMPL 0.0f;
        if (f == false) {
            return 0;
        }
        if (f <= 0) return -1;
        return 1;
    }
}

