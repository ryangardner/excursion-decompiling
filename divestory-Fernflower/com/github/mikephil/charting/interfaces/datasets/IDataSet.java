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
   boolean addEntry(T var1);

   void addEntryOrdered(T var1);

   void calcMinMax();

   void calcMinMaxY(float var1, float var2);

   void clear();

   boolean contains(T var1);

   YAxis.AxisDependency getAxisDependency();

   int getColor();

   int getColor(int var1);

   List<Integer> getColors();

   List<T> getEntriesForXValue(float var1);

   int getEntryCount();

   T getEntryForIndex(int var1);

   T getEntryForXValue(float var1, float var2);

   T getEntryForXValue(float var1, float var2, DataSet.Rounding var3);

   int getEntryIndex(float var1, float var2, DataSet.Rounding var3);

   int getEntryIndex(T var1);

   Legend.LegendForm getForm();

   DashPathEffect getFormLineDashEffect();

   float getFormLineWidth();

   float getFormSize();

   GradientColor getGradientColor();

   GradientColor getGradientColor(int var1);

   List<GradientColor> getGradientColors();

   MPPointF getIconsOffset();

   int getIndexInEntries(int var1);

   String getLabel();

   ValueFormatter getValueFormatter();

   int getValueTextColor();

   int getValueTextColor(int var1);

   float getValueTextSize();

   Typeface getValueTypeface();

   float getXMax();

   float getXMin();

   float getYMax();

   float getYMin();

   boolean isDrawIconsEnabled();

   boolean isDrawValuesEnabled();

   boolean isHighlightEnabled();

   boolean isVisible();

   boolean needsFormatter();

   boolean removeEntry(int var1);

   boolean removeEntry(T var1);

   boolean removeEntryByXValue(float var1);

   boolean removeFirst();

   boolean removeLast();

   void setAxisDependency(YAxis.AxisDependency var1);

   void setDrawIcons(boolean var1);

   void setDrawValues(boolean var1);

   void setHighlightEnabled(boolean var1);

   void setIconsOffset(MPPointF var1);

   void setLabel(String var1);

   void setValueFormatter(ValueFormatter var1);

   void setValueTextColor(int var1);

   void setValueTextColors(List<Integer> var1);

   void setValueTextSize(float var1);

   void setValueTypeface(Typeface var1);

   void setVisible(boolean var1);
}
