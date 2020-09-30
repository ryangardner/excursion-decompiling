/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.graphics.DashPathEffect
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDataSet<T extends Entry>
implements IDataSet<T> {
    protected YAxis.AxisDependency mAxisDependency = YAxis.AxisDependency.LEFT;
    protected List<Integer> mColors = new ArrayList<Integer>();
    protected boolean mDrawIcons = true;
    protected boolean mDrawValues = true;
    private Legend.LegendForm mForm = Legend.LegendForm.DEFAULT;
    private DashPathEffect mFormLineDashEffect = null;
    private float mFormLineWidth = Float.NaN;
    private float mFormSize = Float.NaN;
    protected GradientColor mGradientColor = null;
    protected List<GradientColor> mGradientColors = null;
    protected boolean mHighlightEnabled = true;
    protected MPPointF mIconsOffset = new MPPointF();
    private String mLabel = "DataSet";
    protected List<Integer> mValueColors = new ArrayList<Integer>();
    protected transient ValueFormatter mValueFormatter;
    protected float mValueTextSize = 17.0f;
    protected Typeface mValueTypeface;
    protected boolean mVisible = true;

    public BaseDataSet() {
        this.mColors.add(Color.rgb((int)140, (int)234, (int)255));
        this.mValueColors.add(-16777216);
    }

    public BaseDataSet(String string2) {
        this();
        this.mLabel = string2;
    }

    public void addColor(int n) {
        if (this.mColors == null) {
            this.mColors = new ArrayList<Integer>();
        }
        this.mColors.add(n);
    }

    @Override
    public boolean contains(T t) {
        int n = 0;
        while (n < this.getEntryCount()) {
            if (this.getEntryForIndex(n).equals(t)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    protected void copy(BaseDataSet baseDataSet) {
        baseDataSet.mAxisDependency = this.mAxisDependency;
        baseDataSet.mColors = this.mColors;
        baseDataSet.mDrawIcons = this.mDrawIcons;
        baseDataSet.mDrawValues = this.mDrawValues;
        baseDataSet.mForm = this.mForm;
        baseDataSet.mFormLineDashEffect = this.mFormLineDashEffect;
        baseDataSet.mFormLineWidth = this.mFormLineWidth;
        baseDataSet.mFormSize = this.mFormSize;
        baseDataSet.mGradientColor = this.mGradientColor;
        baseDataSet.mGradientColors = this.mGradientColors;
        baseDataSet.mHighlightEnabled = this.mHighlightEnabled;
        baseDataSet.mIconsOffset = this.mIconsOffset;
        baseDataSet.mValueColors = this.mValueColors;
        baseDataSet.mValueFormatter = this.mValueFormatter;
        baseDataSet.mValueColors = this.mValueColors;
        baseDataSet.mValueTextSize = this.mValueTextSize;
        baseDataSet.mVisible = this.mVisible;
    }

    @Override
    public YAxis.AxisDependency getAxisDependency() {
        return this.mAxisDependency;
    }

    @Override
    public int getColor() {
        return this.mColors.get(0);
    }

    @Override
    public int getColor(int n) {
        List<Integer> list = this.mColors;
        return list.get(n % list.size());
    }

    @Override
    public List<Integer> getColors() {
        return this.mColors;
    }

    @Override
    public Legend.LegendForm getForm() {
        return this.mForm;
    }

    @Override
    public DashPathEffect getFormLineDashEffect() {
        return this.mFormLineDashEffect;
    }

    @Override
    public float getFormLineWidth() {
        return this.mFormLineWidth;
    }

    @Override
    public float getFormSize() {
        return this.mFormSize;
    }

    @Override
    public GradientColor getGradientColor() {
        return this.mGradientColor;
    }

    @Override
    public GradientColor getGradientColor(int n) {
        List<GradientColor> list = this.mGradientColors;
        return list.get(n % list.size());
    }

    @Override
    public List<GradientColor> getGradientColors() {
        return this.mGradientColors;
    }

    @Override
    public MPPointF getIconsOffset() {
        return this.mIconsOffset;
    }

    @Override
    public int getIndexInEntries(int n) {
        int n2 = 0;
        while (n2 < this.getEntryCount()) {
            if ((float)n == ((Entry)this.getEntryForIndex(n2)).getX()) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    @Override
    public String getLabel() {
        return this.mLabel;
    }

    public List<Integer> getValueColors() {
        return this.mValueColors;
    }

    @Override
    public ValueFormatter getValueFormatter() {
        if (!this.needsFormatter()) return this.mValueFormatter;
        return Utils.getDefaultValueFormatter();
    }

    @Override
    public int getValueTextColor() {
        return this.mValueColors.get(0);
    }

    @Override
    public int getValueTextColor(int n) {
        List<Integer> list = this.mValueColors;
        return list.get(n % list.size());
    }

    @Override
    public float getValueTextSize() {
        return this.mValueTextSize;
    }

    @Override
    public Typeface getValueTypeface() {
        return this.mValueTypeface;
    }

    @Override
    public boolean isDrawIconsEnabled() {
        return this.mDrawIcons;
    }

    @Override
    public boolean isDrawValuesEnabled() {
        return this.mDrawValues;
    }

    @Override
    public boolean isHighlightEnabled() {
        return this.mHighlightEnabled;
    }

    @Override
    public boolean isVisible() {
        return this.mVisible;
    }

    @Override
    public boolean needsFormatter() {
        if (this.mValueFormatter != null) return false;
        return true;
    }

    public void notifyDataSetChanged() {
        this.calcMinMax();
    }

    @Override
    public boolean removeEntry(int n) {
        return this.removeEntry(this.getEntryForIndex(n));
    }

    @Override
    public boolean removeEntryByXValue(float f) {
        return this.removeEntry(this.getEntryForXValue(f, Float.NaN));
    }

    @Override
    public boolean removeFirst() {
        if (this.getEntryCount() <= 0) return false;
        return this.removeEntry(this.getEntryForIndex(0));
    }

    @Override
    public boolean removeLast() {
        if (this.getEntryCount() <= 0) return false;
        return this.removeEntry(this.getEntryForIndex(this.getEntryCount() - 1));
    }

    public void resetColors() {
        if (this.mColors == null) {
            this.mColors = new ArrayList<Integer>();
        }
        this.mColors.clear();
    }

    @Override
    public void setAxisDependency(YAxis.AxisDependency axisDependency) {
        this.mAxisDependency = axisDependency;
    }

    public void setColor(int n) {
        this.resetColors();
        this.mColors.add(n);
    }

    public void setColor(int n, int n2) {
        this.setColor(Color.argb((int)n2, (int)Color.red((int)n), (int)Color.green((int)n), (int)Color.blue((int)n)));
    }

    public void setColors(List<Integer> list) {
        this.mColors = list;
    }

    public void setColors(int ... arrn) {
        this.mColors = ColorTemplate.createColors(arrn);
    }

    public void setColors(int[] arrn, int n) {
        this.resetColors();
        int n2 = arrn.length;
        int n3 = 0;
        while (n3 < n2) {
            int n4 = arrn[n3];
            this.addColor(Color.argb((int)n, (int)Color.red((int)n4), (int)Color.green((int)n4), (int)Color.blue((int)n4)));
            ++n3;
        }
    }

    public void setColors(int[] arrn, Context context) {
        if (this.mColors == null) {
            this.mColors = new ArrayList<Integer>();
        }
        this.mColors.clear();
        int n = arrn.length;
        int n2 = 0;
        while (n2 < n) {
            int n3 = arrn[n2];
            this.mColors.add(context.getResources().getColor(n3));
            ++n2;
        }
    }

    @Override
    public void setDrawIcons(boolean bl) {
        this.mDrawIcons = bl;
    }

    @Override
    public void setDrawValues(boolean bl) {
        this.mDrawValues = bl;
    }

    public void setForm(Legend.LegendForm legendForm) {
        this.mForm = legendForm;
    }

    public void setFormLineDashEffect(DashPathEffect dashPathEffect) {
        this.mFormLineDashEffect = dashPathEffect;
    }

    public void setFormLineWidth(float f) {
        this.mFormLineWidth = f;
    }

    public void setFormSize(float f) {
        this.mFormSize = f;
    }

    public void setGradientColor(int n, int n2) {
        this.mGradientColor = new GradientColor(n, n2);
    }

    public void setGradientColors(List<GradientColor> list) {
        this.mGradientColors = list;
    }

    @Override
    public void setHighlightEnabled(boolean bl) {
        this.mHighlightEnabled = bl;
    }

    @Override
    public void setIconsOffset(MPPointF mPPointF) {
        this.mIconsOffset.x = mPPointF.x;
        this.mIconsOffset.y = mPPointF.y;
    }

    @Override
    public void setLabel(String string2) {
        this.mLabel = string2;
    }

    @Override
    public void setValueFormatter(ValueFormatter valueFormatter) {
        if (valueFormatter == null) {
            return;
        }
        this.mValueFormatter = valueFormatter;
    }

    @Override
    public void setValueTextColor(int n) {
        this.mValueColors.clear();
        this.mValueColors.add(n);
    }

    @Override
    public void setValueTextColors(List<Integer> list) {
        this.mValueColors = list;
    }

    @Override
    public void setValueTextSize(float f) {
        this.mValueTextSize = Utils.convertDpToPixel(f);
    }

    @Override
    public void setValueTypeface(Typeface typeface) {
        this.mValueTypeface = typeface;
    }

    @Override
    public void setVisible(boolean bl) {
        this.mVisible = bl;
    }
}

