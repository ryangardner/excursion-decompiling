/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.interfaces.datasets;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;

public interface IScatterDataSet
extends ILineScatterCandleRadarDataSet<Entry> {
    public int getScatterShapeHoleColor();

    public float getScatterShapeHoleRadius();

    public float getScatterShapeSize();

    public IShapeRenderer getShapeRenderer();
}

