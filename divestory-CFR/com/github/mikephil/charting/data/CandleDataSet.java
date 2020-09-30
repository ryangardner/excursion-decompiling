/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 */
package com.github.mikephil.charting.data;

import android.graphics.Paint;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineScatterCandleRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class CandleDataSet
extends LineScatterCandleRadarDataSet<CandleEntry>
implements ICandleDataSet {
    private float mBarSpace = 0.1f;
    protected int mDecreasingColor = 1122868;
    protected Paint.Style mDecreasingPaintStyle = Paint.Style.FILL;
    protected int mIncreasingColor = 1122868;
    protected Paint.Style mIncreasingPaintStyle = Paint.Style.STROKE;
    protected int mNeutralColor = 1122868;
    protected int mShadowColor = 1122868;
    private boolean mShadowColorSameAsCandle = false;
    private float mShadowWidth = 3.0f;
    private boolean mShowCandleBar = true;

    public CandleDataSet(List<CandleEntry> list, String string2) {
        super(list, string2);
    }

    @Override
    protected void calcMinMax(CandleEntry candleEntry) {
        if (candleEntry.getLow() < this.mYMin) {
            this.mYMin = candleEntry.getLow();
        }
        if (candleEntry.getHigh() > this.mYMax) {
            this.mYMax = candleEntry.getHigh();
        }
        this.calcMinMaxX(candleEntry);
    }

    @Override
    protected void calcMinMaxY(CandleEntry candleEntry) {
        if (candleEntry.getHigh() < this.mYMin) {
            this.mYMin = candleEntry.getHigh();
        }
        if (candleEntry.getHigh() > this.mYMax) {
            this.mYMax = candleEntry.getHigh();
        }
        if (candleEntry.getLow() < this.mYMin) {
            this.mYMin = candleEntry.getLow();
        }
        if (!(candleEntry.getLow() > this.mYMax)) return;
        this.mYMax = candleEntry.getLow();
    }

    @Override
    public DataSet<CandleEntry> copy() {
        Object object = new ArrayList<CandleEntry>();
        int n = 0;
        do {
            if (n >= this.mValues.size()) {
                object = new CandleDataSet((List<CandleEntry>)object, this.getLabel());
                this.copy((CandleDataSet)object);
                return object;
            }
            object.add((CandleEntry)((CandleEntry)this.mValues.get(n)).copy());
            ++n;
        } while (true);
    }

    protected void copy(CandleDataSet candleDataSet) {
        super.copy(candleDataSet);
        candleDataSet.mShadowWidth = this.mShadowWidth;
        candleDataSet.mShowCandleBar = this.mShowCandleBar;
        candleDataSet.mBarSpace = this.mBarSpace;
        candleDataSet.mShadowColorSameAsCandle = this.mShadowColorSameAsCandle;
        candleDataSet.mHighLightColor = this.mHighLightColor;
        candleDataSet.mIncreasingPaintStyle = this.mIncreasingPaintStyle;
        candleDataSet.mDecreasingPaintStyle = this.mDecreasingPaintStyle;
        candleDataSet.mNeutralColor = this.mNeutralColor;
        candleDataSet.mIncreasingColor = this.mIncreasingColor;
        candleDataSet.mDecreasingColor = this.mDecreasingColor;
        candleDataSet.mShadowColor = this.mShadowColor;
    }

    @Override
    public float getBarSpace() {
        return this.mBarSpace;
    }

    @Override
    public int getDecreasingColor() {
        return this.mDecreasingColor;
    }

    @Override
    public Paint.Style getDecreasingPaintStyle() {
        return this.mDecreasingPaintStyle;
    }

    @Override
    public int getIncreasingColor() {
        return this.mIncreasingColor;
    }

    @Override
    public Paint.Style getIncreasingPaintStyle() {
        return this.mIncreasingPaintStyle;
    }

    @Override
    public int getNeutralColor() {
        return this.mNeutralColor;
    }

    @Override
    public int getShadowColor() {
        return this.mShadowColor;
    }

    @Override
    public boolean getShadowColorSameAsCandle() {
        return this.mShadowColorSameAsCandle;
    }

    @Override
    public float getShadowWidth() {
        return this.mShadowWidth;
    }

    @Override
    public boolean getShowCandleBar() {
        return this.mShowCandleBar;
    }

    public void setBarSpace(float f) {
        float f2 = f;
        if (f < 0.0f) {
            f2 = 0.0f;
        }
        f = f2;
        if (f2 > 0.45f) {
            f = 0.45f;
        }
        this.mBarSpace = f;
    }

    public void setDecreasingColor(int n) {
        this.mDecreasingColor = n;
    }

    public void setDecreasingPaintStyle(Paint.Style style2) {
        this.mDecreasingPaintStyle = style2;
    }

    public void setIncreasingColor(int n) {
        this.mIncreasingColor = n;
    }

    public void setIncreasingPaintStyle(Paint.Style style2) {
        this.mIncreasingPaintStyle = style2;
    }

    public void setNeutralColor(int n) {
        this.mNeutralColor = n;
    }

    public void setShadowColor(int n) {
        this.mShadowColor = n;
    }

    public void setShadowColorSameAsCandle(boolean bl) {
        this.mShadowColorSameAsCandle = bl;
    }

    public void setShadowWidth(float f) {
        this.mShadowWidth = Utils.convertDpToPixel(f);
    }

    public void setShowCandleBar(boolean bl) {
        this.mShowCandleBar = bl;
    }
}

