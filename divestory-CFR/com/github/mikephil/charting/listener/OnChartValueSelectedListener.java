/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.listener;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

public interface OnChartValueSelectedListener {
    public void onNothingSelected();

    public void onValueSelected(Entry var1, Highlight var2);
}

