/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.interfaces.datasets;

import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

public interface IPieDataSet
extends IDataSet<PieEntry> {
    public float getSelectionShift();

    public float getSliceSpace();

    public int getValueLineColor();

    public float getValueLinePart1Length();

    public float getValueLinePart1OffsetPercentage();

    public float getValueLinePart2Length();

    public float getValueLineWidth();

    public PieDataSet.ValuePosition getXValuePosition();

    public PieDataSet.ValuePosition getYValuePosition();

    public boolean isAutomaticallyDisableSliceSpacingEnabled();

    public boolean isUsingSliceColorAsValueLineColor();

    public boolean isValueLineVariableLength();
}

