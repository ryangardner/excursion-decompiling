/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package com.github.mikephil.charting.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartGestureListener;

public abstract class ChartTouchListener<T extends Chart<?>>
extends GestureDetector.SimpleOnGestureListener
implements View.OnTouchListener {
    protected static final int DRAG = 1;
    protected static final int NONE = 0;
    protected static final int PINCH_ZOOM = 4;
    protected static final int POST_ZOOM = 5;
    protected static final int ROTATE = 6;
    protected static final int X_ZOOM = 2;
    protected static final int Y_ZOOM = 3;
    protected T mChart;
    protected GestureDetector mGestureDetector;
    protected ChartGesture mLastGesture = ChartGesture.NONE;
    protected Highlight mLastHighlighted;
    protected int mTouchMode = 0;

    public ChartTouchListener(T t) {
        this.mChart = t;
        this.mGestureDetector = new GestureDetector(t.getContext(), (GestureDetector.OnGestureListener)this);
    }

    protected static float distance(float f, float f2, float f3, float f4) {
        f -= f2;
        f2 = f3 - f4;
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    public void endAction(MotionEvent motionEvent) {
        OnChartGestureListener onChartGestureListener = ((Chart)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener == null) return;
        onChartGestureListener.onChartGestureEnd(motionEvent, this.mLastGesture);
    }

    public ChartGesture getLastGesture() {
        return this.mLastGesture;
    }

    public int getTouchMode() {
        return this.mTouchMode;
    }

    protected void performHighlight(Highlight highlight, MotionEvent motionEvent) {
        if (highlight != null && !highlight.equalTo(this.mLastHighlighted)) {
            ((Chart)this.mChart).highlightValue(highlight, true);
            this.mLastHighlighted = highlight;
            return;
        }
        ((Chart)this.mChart).highlightValue(null, true);
        this.mLastHighlighted = null;
    }

    public void setLastHighlighted(Highlight highlight) {
        this.mLastHighlighted = highlight;
    }

    public void startAction(MotionEvent motionEvent) {
        OnChartGestureListener onChartGestureListener = ((Chart)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener == null) return;
        onChartGestureListener.onChartGestureStart(motionEvent, this.mLastGesture);
    }

    public static final class ChartGesture
    extends Enum<ChartGesture> {
        private static final /* synthetic */ ChartGesture[] $VALUES;
        public static final /* enum */ ChartGesture DOUBLE_TAP;
        public static final /* enum */ ChartGesture DRAG;
        public static final /* enum */ ChartGesture FLING;
        public static final /* enum */ ChartGesture LONG_PRESS;
        public static final /* enum */ ChartGesture NONE;
        public static final /* enum */ ChartGesture PINCH_ZOOM;
        public static final /* enum */ ChartGesture ROTATE;
        public static final /* enum */ ChartGesture SINGLE_TAP;
        public static final /* enum */ ChartGesture X_ZOOM;
        public static final /* enum */ ChartGesture Y_ZOOM;

        static {
            ChartGesture chartGesture;
            NONE = new ChartGesture();
            DRAG = new ChartGesture();
            X_ZOOM = new ChartGesture();
            Y_ZOOM = new ChartGesture();
            PINCH_ZOOM = new ChartGesture();
            ROTATE = new ChartGesture();
            SINGLE_TAP = new ChartGesture();
            DOUBLE_TAP = new ChartGesture();
            LONG_PRESS = new ChartGesture();
            FLING = chartGesture = new ChartGesture();
            $VALUES = new ChartGesture[]{NONE, DRAG, X_ZOOM, Y_ZOOM, PINCH_ZOOM, ROTATE, SINGLE_TAP, DOUBLE_TAP, LONG_PRESS, chartGesture};
        }

        public static ChartGesture valueOf(String string2) {
            return Enum.valueOf(ChartGesture.class, string2);
        }

        public static ChartGesture[] values() {
            return (ChartGesture[])$VALUES.clone();
        }
    }

}

