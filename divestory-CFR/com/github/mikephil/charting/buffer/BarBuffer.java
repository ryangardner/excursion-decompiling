/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.buffer.AbstractBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class BarBuffer
extends AbstractBuffer<IBarDataSet> {
    protected float mBarWidth = 1.0f;
    protected boolean mContainsStacks = false;
    protected int mDataSetCount = 1;
    protected int mDataSetIndex = 0;
    protected boolean mInverted = false;

    public BarBuffer(int n, int n2, boolean bl) {
        super(n);
        this.mDataSetCount = n2;
        this.mContainsStacks = bl;
    }

    protected void addBar(float f, float f2, float f3, float f4) {
        float[] arrf = this.buffer;
        int n = this.index;
        this.index = n + 1;
        arrf[n] = f;
        arrf = this.buffer;
        n = this.index;
        this.index = n + 1;
        arrf[n] = f2;
        arrf = this.buffer;
        n = this.index;
        this.index = n + 1;
        arrf[n] = f3;
        arrf = this.buffer;
        n = this.index;
        this.index = n + 1;
        arrf[n] = f4;
    }

    @Override
    public void feed(IBarDataSet iBarDataSet) {
        float f = iBarDataSet.getEntryCount();
        float f2 = this.phaseX;
        float f3 = this.mBarWidth / 2.0f;
        int n = 0;
        do {
            if (!((float)n < f * f2)) {
                this.reset();
                return;
            }
            BarEntry barEntry = (BarEntry)iBarDataSet.getEntryForIndex(n);
            if (barEntry != null) {
                float f4;
                float f5;
                float f6 = barEntry.getX();
                float f7 = barEntry.getY();
                float[] arrf = barEntry.getYVals();
                if (!this.mContainsStacks || arrf == null) {
                    if (this.mInverted) {
                        f4 = f7 >= 0.0f ? f7 : 0.0f;
                        if (!(f7 <= 0.0f)) {
                            f7 = 0.0f;
                        }
                    } else {
                        f4 = f7 >= 0.0f ? f7 : 0.0f;
                        if (!(f7 <= 0.0f)) {
                            f7 = 0.0f;
                        }
                        f5 = f4;
                        f4 = f7;
                        f7 = f5;
                    }
                    if (f7 > 0.0f) {
                        f7 *= this.phaseY;
                    } else {
                        f4 *= this.phaseY;
                    }
                    this.addBar(f6 - f3, f7, f6 + f3, f4);
                } else {
                    f7 = -barEntry.getNegativeSum();
                    float f8 = 0.0f;
                    for (int i = 0; i < arrf.length; ++i) {
                        float f9;
                        f5 = arrf[i];
                        float f10 = f5 FCMPL 0.0f;
                        if (f10 == false && (f8 == 0.0f || f7 == 0.0f)) {
                            f4 = f5;
                            f5 = f7;
                            f7 = f4;
                            f9 = f8;
                        } else if (f10 >= 0) {
                            f4 = f5 + f8;
                            f5 = f7;
                            f7 = f8;
                            f9 = f4;
                        } else {
                            f4 = Math.abs(f5) + f7;
                            f5 = Math.abs(f5) + f7;
                            f9 = f8;
                        }
                        if (this.mInverted) {
                            f8 = f7 >= f4 ? f7 : f4;
                            if (!(f7 <= f4)) {
                                f7 = f4;
                            }
                        } else {
                            f8 = f7 >= f4 ? f7 : f4;
                            if (!(f7 <= f4)) {
                                f7 = f4;
                            }
                            f4 = f8;
                            f8 = f7;
                            f7 = f4;
                        }
                        this.addBar(f6 - f3, f7 * this.phaseY, f6 + f3, f8 * this.phaseY);
                        f7 = f5;
                        f8 = f9;
                    }
                }
            }
            ++n;
        } while (true);
    }

    public void setBarWidth(float f) {
        this.mBarWidth = f;
    }

    public void setDataSet(int n) {
        this.mDataSetIndex = n;
    }

    public void setInverted(boolean bl) {
        this.mInverted = bl;
    }
}

