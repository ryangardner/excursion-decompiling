/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.view.View
 */
package com.github.mikephil.charting.jobs;

import android.graphics.Matrix;
import android.view.View;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.jobs.ViewPortJob;
import com.github.mikephil.charting.utils.ObjectPool;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class ZoomJob
extends ViewPortJob {
    private static ObjectPool<ZoomJob> pool;
    protected YAxis.AxisDependency axisDependency;
    protected Matrix mRunMatrixBuffer = new Matrix();
    protected float scaleX;
    protected float scaleY;

    static {
        ObjectPool objectPool;
        pool = objectPool = ObjectPool.create(1, new ZoomJob(null, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null));
        objectPool.setReplenishPercentage(0.5f);
    }

    public ZoomJob(ViewPortHandler viewPortHandler, float f, float f2, float f3, float f4, Transformer transformer, YAxis.AxisDependency axisDependency, View view) {
        super(viewPortHandler, f3, f4, transformer, view);
        this.scaleX = f;
        this.scaleY = f2;
        this.axisDependency = axisDependency;
    }

    public static ZoomJob getInstance(ViewPortHandler viewPortHandler, float f, float f2, float f3, float f4, Transformer transformer, YAxis.AxisDependency axisDependency, View view) {
        ZoomJob zoomJob = pool.get();
        zoomJob.xValue = f3;
        zoomJob.yValue = f4;
        zoomJob.scaleX = f;
        zoomJob.scaleY = f2;
        zoomJob.mViewPortHandler = viewPortHandler;
        zoomJob.mTrans = transformer;
        zoomJob.axisDependency = axisDependency;
        zoomJob.view = view;
        return zoomJob;
    }

    public static void recycleInstance(ZoomJob zoomJob) {
        pool.recycle(zoomJob);
    }

    @Override
    protected ObjectPool.Poolable instantiate() {
        return new ZoomJob(null, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null);
    }

    @Override
    public void run() {
        Matrix matrix = this.mRunMatrixBuffer;
        this.mViewPortHandler.zoom(this.scaleX, this.scaleY, matrix);
        this.mViewPortHandler.refresh(matrix, this.view, false);
        float f = ((BarLineChartBase)this.view).getAxis((YAxis.AxisDependency)this.axisDependency).mAxisRange / this.mViewPortHandler.getScaleY();
        float f2 = ((BarLineChartBase)this.view).getXAxis().mAxisRange / this.mViewPortHandler.getScaleX();
        this.pts[0] = this.xValue - f2 / 2.0f;
        this.pts[1] = this.yValue + f / 2.0f;
        this.mTrans.pointValuesToPixel(this.pts);
        this.mViewPortHandler.translate(this.pts, matrix);
        this.mViewPortHandler.refresh(matrix, this.view, false);
        ((BarLineChartBase)this.view).calculateOffsets();
        this.view.postInvalidate();
        ZoomJob.recycleInstance(this);
    }
}

