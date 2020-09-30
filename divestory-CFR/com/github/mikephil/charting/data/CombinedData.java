/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.github.mikephil.charting.data;

import android.util.Log;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CombinedData
extends BarLineScatterCandleBubbleData<IBarLineScatterCandleBubbleDataSet<? extends Entry>> {
    private BarData mBarData;
    private BubbleData mBubbleData;
    private CandleData mCandleData;
    private LineData mLineData;
    private ScatterData mScatterData;

    @Override
    public void calcMinMax() {
        if (this.mDataSets == null) {
            this.mDataSets = new ArrayList();
        }
        this.mDataSets.clear();
        this.mYMax = -3.4028235E38f;
        this.mYMin = Float.MAX_VALUE;
        this.mXMax = -3.4028235E38f;
        this.mXMin = Float.MAX_VALUE;
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = Float.MAX_VALUE;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = Float.MAX_VALUE;
        Iterator<BarLineScatterCandleBubbleData> iterator2 = this.getAllData().iterator();
        while (iterator2.hasNext()) {
            ChartData chartData = iterator2.next();
            chartData.calcMinMax();
            List list = chartData.getDataSets();
            this.mDataSets.addAll(list);
            if (chartData.getYMax() > this.mYMax) {
                this.mYMax = chartData.getYMax();
            }
            if (chartData.getYMin() < this.mYMin) {
                this.mYMin = chartData.getYMin();
            }
            if (chartData.getXMax() > this.mXMax) {
                this.mXMax = chartData.getXMax();
            }
            if (chartData.getXMin() < this.mXMin) {
                this.mXMin = chartData.getXMin();
            }
            if (chartData.mLeftAxisMax > this.mLeftAxisMax) {
                this.mLeftAxisMax = chartData.mLeftAxisMax;
            }
            if (chartData.mLeftAxisMin < this.mLeftAxisMin) {
                this.mLeftAxisMin = chartData.mLeftAxisMin;
            }
            if (chartData.mRightAxisMax > this.mRightAxisMax) {
                this.mRightAxisMax = chartData.mRightAxisMax;
            }
            if (!(chartData.mRightAxisMin < this.mRightAxisMin)) continue;
            this.mRightAxisMin = chartData.mRightAxisMin;
        }
    }

    public List<BarLineScatterCandleBubbleData> getAllData() {
        ArrayList<BarLineScatterCandleBubbleData> arrayList = new ArrayList<BarLineScatterCandleBubbleData>();
        BarLineScatterCandleBubbleData barLineScatterCandleBubbleData = this.mLineData;
        if (barLineScatterCandleBubbleData != null) {
            arrayList.add(barLineScatterCandleBubbleData);
        }
        if ((barLineScatterCandleBubbleData = this.mBarData) != null) {
            arrayList.add(barLineScatterCandleBubbleData);
        }
        if ((barLineScatterCandleBubbleData = this.mScatterData) != null) {
            arrayList.add(barLineScatterCandleBubbleData);
        }
        if ((barLineScatterCandleBubbleData = this.mCandleData) != null) {
            arrayList.add(barLineScatterCandleBubbleData);
        }
        if ((barLineScatterCandleBubbleData = this.mBubbleData) == null) return arrayList;
        arrayList.add(barLineScatterCandleBubbleData);
        return arrayList;
    }

    public BarData getBarData() {
        return this.mBarData;
    }

    public BubbleData getBubbleData() {
        return this.mBubbleData;
    }

    public CandleData getCandleData() {
        return this.mCandleData;
    }

    public BarLineScatterCandleBubbleData getDataByIndex(int n) {
        return this.getAllData().get(n);
    }

    public int getDataIndex(ChartData chartData) {
        return this.getAllData().indexOf(chartData);
    }

    public IBarLineScatterCandleBubbleDataSet<? extends Entry> getDataSetByHighlight(Highlight highlight) {
        if (highlight.getDataIndex() >= this.getAllData().size()) {
            return null;
        }
        BarLineScatterCandleBubbleData barLineScatterCandleBubbleData = this.getDataByIndex(highlight.getDataIndex());
        if (highlight.getDataSetIndex() < barLineScatterCandleBubbleData.getDataSetCount()) return (IBarLineScatterCandleBubbleDataSet)barLineScatterCandleBubbleData.getDataSets().get(highlight.getDataSetIndex());
        return null;
    }

    @Override
    public Entry getEntryForHighlight(Highlight highlight) {
        if (highlight.getDataIndex() >= this.getAllData().size()) {
            return null;
        }
        Object object = this.getDataByIndex(highlight.getDataIndex());
        if (highlight.getDataSetIndex() >= ((ChartData)object).getDataSetCount()) {
            return null;
        }
        Iterator iterator2 = ((ChartData)object).getDataSetByIndex(highlight.getDataSetIndex()).getEntriesForXValue(highlight.getX()).iterator();
        do {
            if (!iterator2.hasNext()) return null;
            object = (Entry)iterator2.next();
            if (((BaseEntry)object).getY() == highlight.getY()) return object;
        } while (!Float.isNaN(highlight.getY()));
        return object;
    }

    public LineData getLineData() {
        return this.mLineData;
    }

    public ScatterData getScatterData() {
        return this.mScatterData;
    }

    @Override
    public void notifyDataChanged() {
        BarLineScatterCandleBubbleData barLineScatterCandleBubbleData = this.mLineData;
        if (barLineScatterCandleBubbleData != null) {
            barLineScatterCandleBubbleData.notifyDataChanged();
        }
        if ((barLineScatterCandleBubbleData = this.mBarData) != null) {
            barLineScatterCandleBubbleData.notifyDataChanged();
        }
        if ((barLineScatterCandleBubbleData = this.mCandleData) != null) {
            barLineScatterCandleBubbleData.notifyDataChanged();
        }
        if ((barLineScatterCandleBubbleData = this.mScatterData) != null) {
            barLineScatterCandleBubbleData.notifyDataChanged();
        }
        if ((barLineScatterCandleBubbleData = this.mBubbleData) != null) {
            barLineScatterCandleBubbleData.notifyDataChanged();
        }
        this.calcMinMax();
    }

    @Deprecated
    @Override
    public boolean removeDataSet(int n) {
        Log.e((String)"MPAndroidChart", (String)"removeDataSet(int index) not supported for CombinedData");
        return false;
    }

    @Override
    public boolean removeDataSet(IBarLineScatterCandleBubbleDataSet<? extends Entry> iBarLineScatterCandleBubbleDataSet) {
        boolean bl;
        Iterator<BarLineScatterCandleBubbleData> iterator2 = this.getAllData().iterator();
        boolean bl2 = false;
        do {
            if (!iterator2.hasNext()) return bl2;
            bl2 = bl = ((ChartData)iterator2.next()).removeDataSet(iBarLineScatterCandleBubbleDataSet);
        } while (!bl);
        return bl;
    }

    @Deprecated
    @Override
    public boolean removeEntry(float f, int n) {
        Log.e((String)"MPAndroidChart", (String)"removeEntry(...) not supported for CombinedData");
        return false;
    }

    @Deprecated
    @Override
    public boolean removeEntry(Entry entry, int n) {
        Log.e((String)"MPAndroidChart", (String)"removeEntry(...) not supported for CombinedData");
        return false;
    }

    public void setData(BarData barData) {
        this.mBarData = barData;
        this.notifyDataChanged();
    }

    public void setData(BubbleData bubbleData) {
        this.mBubbleData = bubbleData;
        this.notifyDataChanged();
    }

    public void setData(CandleData candleData) {
        this.mCandleData = candleData;
        this.notifyDataChanged();
    }

    public void setData(LineData lineData) {
        this.mLineData = lineData;
        this.notifyDataChanged();
    }

    public void setData(ScatterData scatterData) {
        this.mScatterData = scatterData;
        this.notifyDataChanged();
    }
}

