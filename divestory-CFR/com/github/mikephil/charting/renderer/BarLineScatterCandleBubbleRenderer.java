/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.renderer;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class BarLineScatterCandleBubbleRenderer
extends DataRenderer {
    protected XBounds mXBounds = new XBounds();

    public BarLineScatterCandleBubbleRenderer(ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
    }

    protected boolean isInBoundsX(Entry entry, IBarLineScatterCandleBubbleDataSet iBarLineScatterCandleBubbleDataSet) {
        if (entry == null) {
            return false;
        }
        float f = iBarLineScatterCandleBubbleDataSet.getEntryIndex(entry);
        if (entry == null) return false;
        if (!(f >= (float)iBarLineScatterCandleBubbleDataSet.getEntryCount() * this.mAnimator.getPhaseX())) return true;
        return false;
    }

    protected boolean shouldDrawValues(IDataSet iDataSet) {
        if (!iDataSet.isVisible()) return false;
        if (iDataSet.isDrawValuesEnabled()) return true;
        if (!iDataSet.isDrawIconsEnabled()) return false;
        return true;
    }

    protected class XBounds {
        public int max;
        public int min;
        public int range;

        protected XBounds() {
        }

        public void set(BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider, IBarLineScatterCandleBubbleDataSet iBarLineScatterCandleBubbleDataSet) {
            float f = Math.max(0.0f, Math.min(1.0f, BarLineScatterCandleBubbleRenderer.this.mAnimator.getPhaseX()));
            float f2 = barLineScatterCandleBubbleDataProvider.getLowestVisibleX();
            float f3 = barLineScatterCandleBubbleDataProvider.getHighestVisibleX();
            barLineScatterCandleBubbleDataProvider = iBarLineScatterCandleBubbleDataSet.getEntryForXValue(f2, Float.NaN, DataSet.Rounding.DOWN);
            Object t = iBarLineScatterCandleBubbleDataSet.getEntryForXValue(f3, Float.NaN, DataSet.Rounding.UP);
            int n = 0;
            int n2 = barLineScatterCandleBubbleDataProvider == null ? 0 : iBarLineScatterCandleBubbleDataSet.getEntryIndex(barLineScatterCandleBubbleDataProvider);
            this.min = n2;
            n2 = t == null ? n : iBarLineScatterCandleBubbleDataSet.getEntryIndex(t);
            this.max = n2;
            this.range = (int)((float)(n2 - this.min) * f);
        }
    }

}

