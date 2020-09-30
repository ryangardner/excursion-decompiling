/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class HorizontalBarBuffer
extends BarBuffer {
    public HorizontalBarBuffer(int n, int n2, boolean bl) {
        super(n, n2, bl);
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
                        f5 = f7;
                        f7 = f4;
                        f4 = f5;
                    }
                    if (f7 > 0.0f) {
                        f7 *= this.phaseY;
                    } else {
                        f4 *= this.phaseY;
                    }
                    this.addBar(f4, f6 + f3, f7, f6 - f3);
                } else {
                    f7 = -barEntry.getNegativeSum();
                    float f8 = 0.0f;
                    for (int i = 0; i < arrf.length; ++i) {
                        float f9;
                        f5 = arrf[i];
                        if (f5 >= 0.0f) {
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
                            f4 = f7;
                            f7 = f8;
                            f8 = f4;
                        }
                        f4 = this.phaseY;
                        this.addBar(f8 * this.phaseY, f6 + f3, f7 * f4, f6 - f3);
                        f7 = f5;
                        f8 = f9;
                    }
                }
            }
            ++n;
        } while (true);
    }
}

