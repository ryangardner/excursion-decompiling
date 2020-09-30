/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.DashPathEffect
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.interfaces.datasets;

import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.MPPointF;
import java.util.List;

public interface IDataSet<T extends Entry> {
    public boolean addEntry(T var1);

    public void addEntryOrdered(T var1);

    public void calcMinMax();

    public void calcMinMaxY(float var1, float var2);

    public void clear();

    public boolean contains(T var1);

    public YAxis.AxisDependency getAxisDependency();

    public int getColor();

    public int getColor(int var1);

    public List<Integer> getColors();

    public List<T> getEntriesForXValue(float var1);

    public int getEntryCount();

    public T getEntryForIndex(int var1);

    public T getEntryForXValue(float var1, float var2);

    public T getEntryForXValue(float var1, float var2, DataSet.Rounding var3);

    public int getEntryIndex(float var1, float var2, DataSet.Rounding var3);

    public int getEntryIndex(T var1);

    public Legend.LegendForm getForm();

    public DashPathEffect getFormLineDashEffect();

    public float getFormLineWidth();

    public float getFormSize();

    public GradientColor getGradientColor();

    public GradientColor getGradientColor(int var1);

    public List<GradientColor> getGradientColors();

    public MPPointF getIconsOffset();

    public int getIndexInEntries(int var1);

    public String getLabel();

    public ValueFormatter getValueFormatter();

    public int getValueTextColor();

    public int getValueTextColor(int var1);

    public float getValueTextSize();

    public Typeface getValueTypeface();

    public float getXMax();

    public float getXMin();

    public float getYMax();

    public float getYMin();

    public boolean isDrawIconsEnabled();

    public boolean isDrawValuesEnabled();

    public boolean isHighlightEnabled();

    public boolean isVisible();

    public boolean needsFormatter();

    public boolean removeEntry(int var1);

    public boolean removeEntry(T var1);

    public boolean removeEntryByXValue(float var1);

    public boolean removeFirst();

    public boolean removeLast();

    public void setAxisDependency(YAxis.AxisDependency var1);

    public void setDrawIcons(boolean var1);

    public void setDrawValues(boolean var1);

    public void setHighlightEnabled(boolean var1);

    public void setIconsOffset(MPPointF var1);

    public void setLabel(String var1);

    public void setValueFormatter(ValueFormatter var1);

    public void setValueTextColor(int var1);

    public void setValueTextColors(List<Integer> var1);

    public void setValueTextSize(float var1);

    public void setValueTypeface(Typeface var1);

    public void setVisible(boolean var1);
}

