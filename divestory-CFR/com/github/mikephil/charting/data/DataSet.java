/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BaseDataSet;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class DataSet<T extends Entry>
extends BaseDataSet<T> {
    protected List<T> mValues = null;
    protected float mXMax = -3.4028235E38f;
    protected float mXMin = Float.MAX_VALUE;
    protected float mYMax = -3.4028235E38f;
    protected float mYMin = Float.MAX_VALUE;

    public DataSet(List<T> list, String string2) {
        super(string2);
        this.mValues = list;
        if (list == null) {
            this.mValues = new ArrayList<T>();
        }
        this.calcMinMax();
    }

    @Override
    public boolean addEntry(T t) {
        List<T> list;
        if (t == null) {
            return false;
        }
        List<T> list2 = list = this.getValues();
        if (list == null) {
            list2 = new ArrayList<T>();
        }
        this.calcMinMax(t);
        return list2.add(t);
    }

    @Override
    public void addEntryOrdered(T t) {
        List<T> list;
        if (t == null) {
            return;
        }
        if (this.mValues == null) {
            this.mValues = new ArrayList<T>();
        }
        this.calcMinMax(t);
        if (this.mValues.size() > 0 && ((Entry)(list = this.mValues).get(list.size() - 1)).getX() > ((Entry)t).getX()) {
            int n = this.getEntryIndex(((Entry)t).getX(), ((BaseEntry)t).getY(), Rounding.UP);
            this.mValues.add(n, t);
            return;
        }
        this.mValues.add(t);
    }

    @Override
    public void calcMinMax() {
        List<T> list = this.mValues;
        if (list == null) return;
        if (list.isEmpty()) {
            return;
        }
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        list = this.mValues.iterator();
        while (list.hasNext()) {
            this.calcMinMax((Entry)list.next());
        }
    }

    protected void calcMinMax(T t) {
        if (t == null) {
            return;
        }
        this.calcMinMaxX(t);
        this.calcMinMaxY(t);
    }

    protected void calcMinMaxX(T t) {
        if (((Entry)t).getX() < this.mXMin) {
            this.mXMin = ((Entry)t).getX();
        }
        if (!(((Entry)t).getX() > this.mXMax)) return;
        this.mXMax = ((Entry)t).getX();
    }

    @Override
    public void calcMinMaxY(float f, float f2) {
        List<T> list = this.mValues;
        if (list == null) return;
        if (list.isEmpty()) {
            return;
        }
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        int n = this.getEntryIndex(f, Float.NaN, Rounding.DOWN);
        int n2 = this.getEntryIndex(f2, Float.NaN, Rounding.UP);
        while (n <= n2) {
            this.calcMinMaxY((Entry)this.mValues.get(n));
            ++n;
        }
    }

    protected void calcMinMaxY(T t) {
        if (((BaseEntry)t).getY() < this.mYMin) {
            this.mYMin = ((BaseEntry)t).getY();
        }
        if (!(((BaseEntry)t).getY() > this.mYMax)) return;
        this.mYMax = ((BaseEntry)t).getY();
    }

    @Override
    public void clear() {
        this.mValues.clear();
        this.notifyDataSetChanged();
    }

    public abstract DataSet<T> copy();

    protected void copy(DataSet dataSet) {
        super.copy(dataSet);
    }

    @Override
    public List<T> getEntriesForXValue(float f) {
        int n;
        ArrayList<Entry> arrayList;
        Entry entry;
        int n2;
        block5 : {
            arrayList = new ArrayList<Entry>();
            n2 = this.mValues.size() - 1;
            n = 0;
            while (n <= n2) {
                int n3 = (n2 + n) / 2;
                entry = (Entry)this.mValues.get(n3);
                if (f == entry.getX()) {
                    for (n = n3; n > 0 && ((Entry)this.mValues.get(n - 1)).getX() == f; --n) {
                    }
                    break block5;
                }
                if (f > entry.getX()) {
                    n = n3 + 1;
                    continue;
                }
                n2 = n3 - 1;
            }
            return arrayList;
        }
        n2 = this.mValues.size();
        while (n < n2) {
            entry = (Entry)this.mValues.get(n);
            if (entry.getX() != f) return arrayList;
            arrayList.add(entry);
            ++n;
        }
        return arrayList;
    }

    @Override
    public int getEntryCount() {
        return this.mValues.size();
    }

    @Override
    public T getEntryForIndex(int n) {
        return (T)((Entry)this.mValues.get(n));
    }

    @Override
    public T getEntryForXValue(float f, float f2) {
        return this.getEntryForXValue(f, f2, Rounding.CLOSEST);
    }

    @Override
    public T getEntryForXValue(float f, float f2, Rounding rounding) {
        int n = this.getEntryIndex(f, f2, rounding);
        if (n <= -1) return null;
        return (T)((Entry)this.mValues.get(n));
    }

    @Override
    public int getEntryIndex(float f, float f2, Rounding rounding) {
        throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: fail exe a35 = a12\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.Ir2JRegAssignTransformer.transform(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Unknown Source)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Unknown Source)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(Unknown Source)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Unknown Source)\n\tat the.bytecode.club.bytecodeviewer.util.Dex2Jar.dex2Jar(Dex2Jar.java:54)\n\tat the.bytecode.club.bytecodeviewer.BytecodeViewer$8.run(BytecodeViewer.java:957)\nCaused by: java.lang.NullPointerException\n\tat com.googlecode.dex2jar.ir.ts.an.SimpleLiveAnalyze.onUseLocal(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.SimpleLiveAnalyze.onUseLocal(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Unknown Source)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Unknown Source)\n\t... 20 more\n");
    }

    @Override
    public int getEntryIndex(Entry entry) {
        return this.mValues.indexOf(entry);
    }

    public List<T> getValues() {
        return this.mValues;
    }

    @Override
    public float getXMax() {
        return this.mXMax;
    }

    @Override
    public float getXMin() {
        return this.mXMin;
    }

    @Override
    public float getYMax() {
        return this.mYMax;
    }

    @Override
    public float getYMin() {
        return this.mYMin;
    }

    @Override
    public boolean removeEntry(T t) {
        if (t == null) {
            return false;
        }
        List<T> list = this.mValues;
        if (list == null) {
            return false;
        }
        boolean bl = list.remove(t);
        if (!bl) return bl;
        this.calcMinMax();
        return bl;
    }

    public void setValues(List<T> list) {
        this.mValues = list;
        this.notifyDataSetChanged();
    }

    public String toSimpleString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataSet, label: ");
        String string2 = this.getLabel() == null ? "" : this.getLabel();
        stringBuilder.append(string2);
        stringBuilder.append(", entries: ");
        stringBuilder.append(this.mValues.size());
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        return stringBuffer.toString();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.toSimpleString());
        int n = 0;
        while (n < this.mValues.size()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((Entry)this.mValues.get(n)).toString());
            stringBuilder.append(" ");
            stringBuffer.append(stringBuilder.toString());
            ++n;
        }
        return stringBuffer.toString();
    }

    public static final class Rounding
    extends Enum<Rounding> {
        private static final /* synthetic */ Rounding[] $VALUES;
        public static final /* enum */ Rounding CLOSEST;
        public static final /* enum */ Rounding DOWN;
        public static final /* enum */ Rounding UP;

        static {
            Rounding rounding;
            UP = new Rounding();
            DOWN = new Rounding();
            CLOSEST = rounding = new Rounding();
            $VALUES = new Rounding[]{UP, DOWN, rounding};
        }

        public static Rounding valueOf(String string2) {
            return Enum.valueOf(Rounding.class, string2);
        }

        public static Rounding[] values() {
            return (Rounding[])$VALUES.clone();
        }
    }

}

