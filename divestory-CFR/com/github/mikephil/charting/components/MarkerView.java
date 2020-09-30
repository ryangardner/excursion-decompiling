/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 */
package com.github.mikephil.charting.components;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import java.lang.ref.WeakReference;

public class MarkerView
extends RelativeLayout
implements IMarker {
    private MPPointF mOffset = new MPPointF();
    private MPPointF mOffset2 = new MPPointF();
    private WeakReference<Chart> mWeakChart;

    public MarkerView(Context context, int n) {
        super(context);
        this.setupLayoutResource(n);
    }

    private void setupLayoutResource(int n) {
        View view = LayoutInflater.from((Context)this.getContext()).inflate(n, (ViewGroup)this);
        view.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-2, -2));
        view.measure(View.MeasureSpec.makeMeasureSpec((int)0, (int)0), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    @Override
    public void draw(Canvas canvas, float f, float f2) {
        MPPointF mPPointF = this.getOffsetForDrawingAtPoint(f, f2);
        int n = canvas.save();
        canvas.translate(f + mPPointF.x, f2 + mPPointF.y);
        this.draw(canvas);
        canvas.restoreToCount(n);
    }

    public Chart getChartView() {
        WeakReference<Chart> weakReference = this.mWeakChart;
        if (weakReference != null) return (Chart)weakReference.get();
        return null;
    }

    @Override
    public MPPointF getOffset() {
        return this.mOffset;
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float f, float f2) {
        Object object = this.getOffset();
        this.mOffset2.x = ((MPPointF)object).x;
        this.mOffset2.y = ((MPPointF)object).y;
        object = this.getChartView();
        float f3 = this.getWidth();
        float f4 = this.getHeight();
        if (this.mOffset2.x + f < 0.0f) {
            this.mOffset2.x = -f;
        } else if (object != null && f + f3 + this.mOffset2.x > (float)object.getWidth()) {
            this.mOffset2.x = (float)object.getWidth() - f - f3;
        }
        if (this.mOffset2.y + f2 < 0.0f) {
            this.mOffset2.y = -f2;
            return this.mOffset2;
        }
        if (object == null) return this.mOffset2;
        if (!(f2 + f4 + this.mOffset2.y > (float)object.getHeight())) return this.mOffset2;
        this.mOffset2.y = (float)object.getHeight() - f2 - f4;
        return this.mOffset2;
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
        this.measure(View.MeasureSpec.makeMeasureSpec((int)0, (int)0), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
        this.layout(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    public void setChartView(Chart chart) {
        this.mWeakChart = new WeakReference<Chart>(chart);
    }

    public void setOffset(float f, float f2) {
        this.mOffset.x = f;
        this.mOffset.y = f2;
    }

    public void setOffset(MPPointF mPPointF) {
        this.mOffset = mPPointF;
        if (mPPointF != null) return;
        this.mOffset = new MPPointF();
    }
}

