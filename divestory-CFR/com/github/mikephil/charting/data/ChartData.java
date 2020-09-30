/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 *  android.util.Log
 */
package com.github.mikephil.charting.data;

import android.graphics.Typeface;
import android.util.Log;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ChartData<T extends IDataSet<? extends Entry>> {
    protected List<T> mDataSets;
    protected float mLeftAxisMax = -3.4028235E38f;
    protected float mLeftAxisMin = Float.MAX_VALUE;
    protected float mRightAxisMax = -3.4028235E38f;
    protected float mRightAxisMin = Float.MAX_VALUE;
    protected float mXMax = -3.4028235E38f;
    protected float mXMin = Float.MAX_VALUE;
    protected float mYMax = -3.4028235E38f;
    protected float mYMin = Float.MAX_VALUE;

    public ChartData() {
        this.mDataSets = new ArrayList<T>();
    }

    public ChartData(List<T> list) {
        this.mDataSets = list;
        this.notifyDataChanged();
    }

    public ChartData(T ... arrT) {
        this.mDataSets = this.arrayToList(arrT);
        this.notifyDataChanged();
    }

    private List<T> arrayToList(T[] arrT) {
        ArrayList<T> arrayList = new ArrayList<T>();
        int n = arrT.length;
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(arrT[n2]);
            ++n2;
        }
        return arrayList;
    }

    public void addDataSet(T t) {
        if (t == null) {
            return;
        }
        this.calcMinMax(t);
        this.mDataSets.add(t);
    }

    public void addEntry(Entry entry, int n) {
        if (this.mDataSets.size() > n && n >= 0) {
            IDataSet iDataSet = (IDataSet)this.mDataSets.get(n);
            if (!iDataSet.addEntry(entry)) {
                return;
            }
            this.calcMinMax(entry, iDataSet.getAxisDependency());
            return;
        }
        Log.e((String)"addEntry", (String)"Cannot add Entry because dataSetIndex too high or too low.");
    }

    protected void calcMinMax() {
        Object object;
        Object object2 = this.mDataSets;
        if (object2 == null) {
            return;
        }
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        object2 = object2.iterator();
        while (object2.hasNext()) {
            this.calcMinMax((IDataSet)object2.next());
        }
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = Float.MAX_VALUE;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = Float.MAX_VALUE;
        object2 = this.getFirstLeft(this.mDataSets);
        if (object2 != null) {
            this.mLeftAxisMax = object2.getYMax();
            this.mLeftAxisMin = object2.getYMin();
            object2 = this.mDataSets.iterator();
            while (object2.hasNext()) {
                object = (IDataSet)object2.next();
                if (object.getAxisDependency() != YAxis.AxisDependency.LEFT) continue;
                if (object.getYMin() < this.mLeftAxisMin) {
                    this.mLeftAxisMin = object.getYMin();
                }
                if (!(object.getYMax() > this.mLeftAxisMax)) continue;
                this.mLeftAxisMax = object.getYMax();
            }
        }
        if ((object2 = this.getFirstRight(this.mDataSets)) == null) return;
        this.mRightAxisMax = object2.getYMax();
        this.mRightAxisMin = object2.getYMin();
        object = this.mDataSets.iterator();
        while (object.hasNext()) {
            object2 = (IDataSet)object.next();
            if (object2.getAxisDependency() != YAxis.AxisDependency.RIGHT) continue;
            if (object2.getYMin() < this.mRightAxisMin) {
                this.mRightAxisMin = object2.getYMin();
            }
            if (!(object2.getYMax() > this.mRightAxisMax)) continue;
            this.mRightAxisMax = object2.getYMax();
        }
    }

    protected void calcMinMax(Entry entry, YAxis.AxisDependency axisDependency) {
        if (this.mYMax < entry.getY()) {
            this.mYMax = entry.getY();
        }
        if (this.mYMin > entry.getY()) {
            this.mYMin = entry.getY();
        }
        if (this.mXMax < entry.getX()) {
            this.mXMax = entry.getX();
        }
        if (this.mXMin > entry.getX()) {
            this.mXMin = entry.getX();
        }
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            if (this.mLeftAxisMax < entry.getY()) {
                this.mLeftAxisMax = entry.getY();
            }
            if (!(this.mLeftAxisMin > entry.getY())) return;
            this.mLeftAxisMin = entry.getY();
            return;
        }
        if (this.mRightAxisMax < entry.getY()) {
            this.mRightAxisMax = entry.getY();
        }
        if (!(this.mRightAxisMin > entry.getY())) return;
        this.mRightAxisMin = entry.getY();
    }

    protected void calcMinMax(T t) {
        if (this.mYMax < t.getYMax()) {
            this.mYMax = t.getYMax();
        }
        if (this.mYMin > t.getYMin()) {
            this.mYMin = t.getYMin();
        }
        if (this.mXMax < t.getXMax()) {
            this.mXMax = t.getXMax();
        }
        if (this.mXMin > t.getXMin()) {
            this.mXMin = t.getXMin();
        }
        if (t.getAxisDependency() == YAxis.AxisDependency.LEFT) {
            if (this.mLeftAxisMax < t.getYMax()) {
                this.mLeftAxisMax = t.getYMax();
            }
            if (!(this.mLeftAxisMin > t.getYMin())) return;
            this.mLeftAxisMin = t.getYMin();
            return;
        }
        if (this.mRightAxisMax < t.getYMax()) {
            this.mRightAxisMax = t.getYMax();
        }
        if (!(this.mRightAxisMin > t.getYMin())) return;
        this.mRightAxisMin = t.getYMin();
    }

    public void calcMinMaxY(float f, float f2) {
        Iterator<T> iterator2 = this.mDataSets.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.calcMinMax();
                return;
            }
            ((IDataSet)iterator2.next()).calcMinMaxY(f, f2);
        } while (true);
    }

    public void clearValues() {
        List<T> list = this.mDataSets;
        if (list != null) {
            list.clear();
        }
        this.notifyDataChanged();
    }

    public boolean contains(T t) {
        Iterator<T> iterator2 = this.mDataSets.iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while (!((IDataSet)iterator2.next()).equals(t));
        return true;
    }

    public int[] getColors() {
        int n;
        if (this.mDataSets == null) {
            return null;
        }
        int n2 = 0;
        int n3 = 0;
        for (n = 0; n < this.mDataSets.size(); n3 += ((IDataSet)this.mDataSets.get((int)n)).getColors().size(), ++n) {
        }
        int[] arrn = new int[n3];
        n3 = 0;
        n = n2;
        while (n < this.mDataSets.size()) {
            for (int arrn[n3] : ((IDataSet)this.mDataSets.get(n)).getColors()) {
                ++n3;
            }
            ++n;
        }
        return arrn;
    }

    public T getDataSetByIndex(int n) {
        List<T> list = this.mDataSets;
        if (list == null) return null;
        if (n < 0) return null;
        if (n < list.size()) return (T)((IDataSet)this.mDataSets.get(n));
        return null;
    }

    public T getDataSetByLabel(String string2, boolean bl) {
        int n = this.getDataSetIndexByLabel(this.mDataSets, string2, bl);
        if (n < 0) return null;
        if (n < this.mDataSets.size()) return (T)((IDataSet)this.mDataSets.get(n));
        return null;
    }

    public int getDataSetCount() {
        List<T> list = this.mDataSets;
        if (list != null) return list.size();
        return 0;
    }

    public T getDataSetForEntry(Entry entry) {
        if (entry == null) {
            return null;
        }
        int n = 0;
        while (n < this.mDataSets.size()) {
            IDataSet iDataSet = (IDataSet)this.mDataSets.get(n);
            for (int i = 0; i < iDataSet.getEntryCount(); ++i) {
                if (!entry.equalTo((Entry)iDataSet.getEntryForXValue(entry.getX(), entry.getY()))) continue;
                return (T)iDataSet;
            }
            ++n;
        }
        return null;
    }

    protected int getDataSetIndexByLabel(List<T> list, String string2, boolean bl) {
        int n = 0;
        int n2 = 0;
        if (bl) {
            n = n2;
            while (n < list.size()) {
                if (string2.equalsIgnoreCase(((IDataSet)list.get(n)).getLabel())) {
                    return n;
                }
                ++n;
            }
            return -1;
        }
        while (n < list.size()) {
            if (string2.equals(((IDataSet)list.get(n)).getLabel())) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public String[] getDataSetLabels() {
        String[] arrstring = new String[this.mDataSets.size()];
        int n = 0;
        while (n < this.mDataSets.size()) {
            arrstring[n] = ((IDataSet)this.mDataSets.get(n)).getLabel();
            ++n;
        }
        return arrstring;
    }

    public List<T> getDataSets() {
        return this.mDataSets;
    }

    public int getEntryCount() {
        Iterator<T> iterator2 = this.mDataSets.iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            n += ((IDataSet)iterator2.next()).getEntryCount();
        }
        return n;
    }

    public Entry getEntryForHighlight(Highlight highlight) {
        if (highlight.getDataSetIndex() < this.mDataSets.size()) return ((IDataSet)this.mDataSets.get(highlight.getDataSetIndex())).getEntryForXValue(highlight.getX(), highlight.getY());
        return null;
    }

    protected T getFirstLeft(List<T> object) {
        IDataSet iDataSet;
        object = object.iterator();
        do {
            if (!object.hasNext()) return null;
        } while ((iDataSet = (IDataSet)object.next()).getAxisDependency() != YAxis.AxisDependency.LEFT);
        return (T)iDataSet;
    }

    public T getFirstRight(List<T> object) {
        IDataSet iDataSet;
        object = object.iterator();
        do {
            if (!object.hasNext()) return null;
        } while ((iDataSet = (IDataSet)object.next()).getAxisDependency() != YAxis.AxisDependency.RIGHT);
        return (T)iDataSet;
    }

    public int getIndexOfDataSet(T t) {
        return this.mDataSets.indexOf(t);
    }

    public T getMaxEntryCountSet() {
        List<T> list = this.mDataSets;
        if (list == null) return null;
        if (list.isEmpty()) {
            return null;
        }
        list = (IDataSet)this.mDataSets.get(0);
        Iterator<T> iterator2 = this.mDataSets.iterator();
        while (iterator2.hasNext()) {
            IDataSet iDataSet = (IDataSet)iterator2.next();
            if (iDataSet.getEntryCount() <= list.getEntryCount()) continue;
            list = iDataSet;
        }
        return (T)list;
    }

    public float getXMax() {
        return this.mXMax;
    }

    public float getXMin() {
        return this.mXMin;
    }

    public float getYMax() {
        return this.mYMax;
    }

    public float getYMax(YAxis.AxisDependency axisDependency) {
        float f;
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            float f2;
            float f3 = f2 = this.mLeftAxisMax;
            if (f2 != -3.4028235E38f) return f3;
            return this.mRightAxisMax;
        }
        float f4 = f = this.mRightAxisMax;
        if (f != -3.4028235E38f) return f4;
        return this.mLeftAxisMax;
    }

    public float getYMin() {
        return this.mYMin;
    }

    public float getYMin(YAxis.AxisDependency axisDependency) {
        float f;
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            float f2;
            float f3 = f2 = this.mLeftAxisMin;
            if (f2 != Float.MAX_VALUE) return f3;
            return this.mRightAxisMin;
        }
        float f4 = f = this.mRightAxisMin;
        if (f != Float.MAX_VALUE) return f4;
        return this.mLeftAxisMin;
    }

    public boolean isHighlightEnabled() {
        Iterator<T> iterator2 = this.mDataSets.iterator();
        do {
            if (!iterator2.hasNext()) return true;
        } while (((IDataSet)iterator2.next()).isHighlightEnabled());
        return false;
    }

    public void notifyDataChanged() {
        this.calcMinMax();
    }

    public boolean removeDataSet(int n) {
        if (n >= this.mDataSets.size()) return false;
        if (n >= 0) return this.removeDataSet((IDataSet)this.mDataSets.get(n));
        return false;
    }

    public boolean removeDataSet(T t) {
        if (t == null) {
            return false;
        }
        boolean bl = this.mDataSets.remove(t);
        if (!bl) return bl;
        this.calcMinMax();
        return bl;
    }

    public boolean removeEntry(float f, int n) {
        if (n >= this.mDataSets.size()) {
            return false;
        }
        Object t = ((IDataSet)this.mDataSets.get(n)).getEntryForXValue(f, Float.NaN);
        if (t != null) return this.removeEntry((Entry)t, n);
        return false;
    }

    public boolean removeEntry(Entry entry, int n) {
        if (entry == null) return false;
        if (n >= this.mDataSets.size()) {
            return false;
        }
        IDataSet iDataSet = (IDataSet)this.mDataSets.get(n);
        if (iDataSet == null) return false;
        boolean bl = iDataSet.removeEntry(entry);
        if (!bl) return bl;
        this.calcMinMax();
        return bl;
    }

    public void setDrawValues(boolean bl) {
        Iterator<T> iterator2 = this.mDataSets.iterator();
        while (iterator2.hasNext()) {
            ((IDataSet)iterator2.next()).setDrawValues(bl);
        }
    }

    public void setHighlightEnabled(boolean bl) {
        Iterator<T> iterator2 = this.mDataSets.iterator();
        while (iterator2.hasNext()) {
            ((IDataSet)iterator2.next()).setHighlightEnabled(bl);
        }
    }

    public void setValueFormatter(ValueFormatter valueFormatter) {
        if (valueFormatter == null) {
            return;
        }
        Iterator<T> iterator2 = this.mDataSets.iterator();
        while (iterator2.hasNext()) {
            ((IDataSet)iterator2.next()).setValueFormatter(valueFormatter);
        }
    }

    public void setValueTextColor(int n) {
        Iterator<T> iterator2 = this.mDataSets.iterator();
        while (iterator2.hasNext()) {
            ((IDataSet)iterator2.next()).setValueTextColor(n);
        }
    }

    public void setValueTextColors(List<Integer> list) {
        Iterator<T> iterator2 = this.mDataSets.iterator();
        while (iterator2.hasNext()) {
            ((IDataSet)iterator2.next()).setValueTextColors(list);
        }
    }

    public void setValueTextSize(float f) {
        Iterator<T> iterator2 = this.mDataSets.iterator();
        while (iterator2.hasNext()) {
            ((IDataSet)iterator2.next()).setValueTextSize(f);
        }
    }

    public void setValueTypeface(Typeface typeface) {
        Iterator<T> iterator2 = this.mDataSets.iterator();
        while (iterator2.hasNext()) {
            ((IDataSet)iterator2.next()).setValueTypeface(typeface);
        }
    }
}

