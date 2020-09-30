/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class BubbleDataSet
extends BarLineScatterCandleBubbleDataSet<BubbleEntry>
implements IBubbleDataSet {
    private float mHighlightCircleWidth = 2.5f;
    protected float mMaxSize;
    protected boolean mNormalizeSize = true;

    public BubbleDataSet(List<BubbleEntry> list, String string2) {
        super(list, string2);
    }

    @Override
    protected void calcMinMax(BubbleEntry bubbleEntry) {
        super.calcMinMax(bubbleEntry);
        float f = bubbleEntry.getSize();
        if (!(f > this.mMaxSize)) return;
        this.mMaxSize = f;
    }

    @Override
    public DataSet<BubbleEntry> copy() {
        Object object = new ArrayList<BubbleEntry>();
        int n = 0;
        do {
            if (n >= this.mValues.size()) {
                object = new BubbleDataSet((List<BubbleEntry>)object, this.getLabel());
                this.copy((BubbleDataSet)object);
                return object;
            }
            object.add((BubbleEntry)((BubbleEntry)this.mValues.get(n)).copy());
            ++n;
        } while (true);
    }

    protected void copy(BubbleDataSet bubbleDataSet) {
        bubbleDataSet.mHighlightCircleWidth = this.mHighlightCircleWidth;
        bubbleDataSet.mNormalizeSize = this.mNormalizeSize;
    }

    @Override
    public float getHighlightCircleWidth() {
        return this.mHighlightCircleWidth;
    }

    @Override
    public float getMaxSize() {
        return this.mMaxSize;
    }

    @Override
    public boolean isNormalizeSizeEnabled() {
        return this.mNormalizeSize;
    }

    @Override
    public void setHighlightCircleWidth(float f) {
        this.mHighlightCircleWidth = Utils.convertDpToPixel(f);
    }

    public void setNormalizeSizeEnabled(boolean bl) {
        this.mNormalizeSize = bl;
    }
}

