/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.interfaces.dataprovider.ScatterDataProvider;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.ScatterChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class ScatterChart
extends BarLineChartBase<ScatterData>
implements ScatterDataProvider {
    public ScatterChart(Context context) {
        super(context);
    }

    public ScatterChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ScatterChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    public ScatterData getScatterData() {
        return (ScatterData)this.mData;
    }

    @Override
    protected void init() {
        super.init();
        this.mRenderer = new ScatterChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.getXAxis().setSpaceMin(0.5f);
        this.getXAxis().setSpaceMax(0.5f);
    }

    public static final class ScatterShape
    extends Enum<ScatterShape> {
        private static final /* synthetic */ ScatterShape[] $VALUES;
        public static final /* enum */ ScatterShape CHEVRON_DOWN;
        public static final /* enum */ ScatterShape CHEVRON_UP;
        public static final /* enum */ ScatterShape CIRCLE;
        public static final /* enum */ ScatterShape CROSS;
        public static final /* enum */ ScatterShape SQUARE;
        public static final /* enum */ ScatterShape TRIANGLE;
        public static final /* enum */ ScatterShape X;
        private final String shapeIdentifier;

        static {
            ScatterShape scatterShape;
            SQUARE = new ScatterShape("SQUARE");
            CIRCLE = new ScatterShape("CIRCLE");
            TRIANGLE = new ScatterShape("TRIANGLE");
            CROSS = new ScatterShape("CROSS");
            X = new ScatterShape("X");
            CHEVRON_UP = new ScatterShape("CHEVRON_UP");
            CHEVRON_DOWN = scatterShape = new ScatterShape("CHEVRON_DOWN");
            $VALUES = new ScatterShape[]{SQUARE, CIRCLE, TRIANGLE, CROSS, X, CHEVRON_UP, scatterShape};
        }

        private ScatterShape(String string3) {
            this.shapeIdentifier = string3;
        }

        public static ScatterShape[] getAllDefaultShapes() {
            return new ScatterShape[]{SQUARE, CIRCLE, TRIANGLE, CROSS, X, CHEVRON_UP, CHEVRON_DOWN};
        }

        public static ScatterShape valueOf(String string2) {
            return Enum.valueOf(ScatterShape.class, string2);
        }

        public static ScatterShape[] values() {
            return (ScatterShape[])$VALUES.clone();
        }

        public String toString() {
            return this.shapeIdentifier;
        }
    }

}

