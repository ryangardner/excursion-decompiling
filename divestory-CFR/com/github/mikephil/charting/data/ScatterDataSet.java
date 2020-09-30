/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineScatterCandleRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.renderer.scatter.ChevronDownShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.ChevronUpShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.CircleShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.CrossShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.SquareShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.TriangleShapeRenderer;
import com.github.mikephil.charting.renderer.scatter.XShapeRenderer;
import java.util.ArrayList;
import java.util.List;

public class ScatterDataSet
extends LineScatterCandleRadarDataSet<Entry>
implements IScatterDataSet {
    private int mScatterShapeHoleColor = 1122867;
    private float mScatterShapeHoleRadius = 0.0f;
    protected IShapeRenderer mShapeRenderer = new SquareShapeRenderer();
    private float mShapeSize = 15.0f;

    public ScatterDataSet(List<Entry> list, String string2) {
        super(list, string2);
    }

    public static IShapeRenderer getRendererForShape(ScatterChart.ScatterShape scatterShape) {
        switch (1.$SwitchMap$com$github$mikephil$charting$charts$ScatterChart$ScatterShape[scatterShape.ordinal()]) {
            default: {
                return null;
            }
            case 7: {
                return new ChevronDownShapeRenderer();
            }
            case 6: {
                return new ChevronUpShapeRenderer();
            }
            case 5: {
                return new XShapeRenderer();
            }
            case 4: {
                return new CrossShapeRenderer();
            }
            case 3: {
                return new TriangleShapeRenderer();
            }
            case 2: {
                return new CircleShapeRenderer();
            }
            case 1: 
        }
        return new SquareShapeRenderer();
    }

    @Override
    public DataSet<Entry> copy() {
        Object object = new ArrayList<Entry>();
        int n = 0;
        do {
            if (n >= this.mValues.size()) {
                object = new ScatterDataSet((List<Entry>)object, this.getLabel());
                this.copy((ScatterDataSet)object);
                return object;
            }
            object.add((Entry)((Entry)this.mValues.get(n)).copy());
            ++n;
        } while (true);
    }

    protected void copy(ScatterDataSet scatterDataSet) {
        super.copy(scatterDataSet);
        scatterDataSet.mShapeSize = this.mShapeSize;
        scatterDataSet.mShapeRenderer = this.mShapeRenderer;
        scatterDataSet.mScatterShapeHoleRadius = this.mScatterShapeHoleRadius;
        scatterDataSet.mScatterShapeHoleColor = this.mScatterShapeHoleColor;
    }

    @Override
    public int getScatterShapeHoleColor() {
        return this.mScatterShapeHoleColor;
    }

    @Override
    public float getScatterShapeHoleRadius() {
        return this.mScatterShapeHoleRadius;
    }

    @Override
    public float getScatterShapeSize() {
        return this.mShapeSize;
    }

    @Override
    public IShapeRenderer getShapeRenderer() {
        return this.mShapeRenderer;
    }

    public void setScatterShape(ScatterChart.ScatterShape scatterShape) {
        this.mShapeRenderer = ScatterDataSet.getRendererForShape(scatterShape);
    }

    public void setScatterShapeHoleColor(int n) {
        this.mScatterShapeHoleColor = n;
    }

    public void setScatterShapeHoleRadius(float f) {
        this.mScatterShapeHoleRadius = f;
    }

    public void setScatterShapeSize(float f) {
        this.mShapeSize = f;
    }

    public void setShapeRenderer(IShapeRenderer iShapeRenderer) {
        this.mShapeRenderer = iShapeRenderer;
    }

}

