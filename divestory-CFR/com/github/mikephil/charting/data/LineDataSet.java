/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.graphics.DashPathEffect
 *  android.util.Log
 */
package com.github.mikephil.charting.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.util.Log;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineRadarDataSet;
import com.github.mikephil.charting.formatter.DefaultFillFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class LineDataSet
extends LineRadarDataSet<Entry>
implements ILineDataSet {
    private List<Integer> mCircleColors = null;
    private int mCircleHoleColor = -1;
    private float mCircleHoleRadius = 4.0f;
    private float mCircleRadius = 8.0f;
    private float mCubicIntensity = 0.2f;
    private DashPathEffect mDashPathEffect = null;
    private boolean mDrawCircleHole = true;
    private boolean mDrawCircles = true;
    private IFillFormatter mFillFormatter = new DefaultFillFormatter();
    private Mode mMode = Mode.LINEAR;

    public LineDataSet(List<Entry> list, String string2) {
        super(list, string2);
        if (this.mCircleColors == null) {
            this.mCircleColors = new ArrayList<Integer>();
        }
        this.mCircleColors.clear();
        this.mCircleColors.add(Color.rgb((int)140, (int)234, (int)255));
    }

    @Override
    public DataSet<Entry> copy() {
        Object object = new ArrayList<Entry>();
        int n = 0;
        do {
            if (n >= this.mValues.size()) {
                object = new LineDataSet((List<Entry>)object, this.getLabel());
                this.copy((LineDataSet)object);
                return object;
            }
            object.add((Entry)((Entry)this.mValues.get(n)).copy());
            ++n;
        } while (true);
    }

    protected void copy(LineDataSet lineDataSet) {
        super.copy(lineDataSet);
        lineDataSet.mCircleColors = this.mCircleColors;
        lineDataSet.mCircleHoleColor = this.mCircleHoleColor;
        lineDataSet.mCircleHoleRadius = this.mCircleHoleRadius;
        lineDataSet.mCircleRadius = this.mCircleRadius;
        lineDataSet.mCubicIntensity = this.mCubicIntensity;
        lineDataSet.mDashPathEffect = this.mDashPathEffect;
        lineDataSet.mDrawCircleHole = this.mDrawCircleHole;
        lineDataSet.mDrawCircles = this.mDrawCircleHole;
        lineDataSet.mFillFormatter = this.mFillFormatter;
        lineDataSet.mMode = this.mMode;
    }

    public void disableDashedLine() {
        this.mDashPathEffect = null;
    }

    public void enableDashedLine(float f, float f2, float f3) {
        this.mDashPathEffect = new DashPathEffect(new float[]{f, f2}, f3);
    }

    @Override
    public int getCircleColor(int n) {
        return this.mCircleColors.get(n);
    }

    @Override
    public int getCircleColorCount() {
        return this.mCircleColors.size();
    }

    public List<Integer> getCircleColors() {
        return this.mCircleColors;
    }

    @Override
    public int getCircleHoleColor() {
        return this.mCircleHoleColor;
    }

    @Override
    public float getCircleHoleRadius() {
        return this.mCircleHoleRadius;
    }

    @Override
    public float getCircleRadius() {
        return this.mCircleRadius;
    }

    @Deprecated
    public float getCircleSize() {
        return this.getCircleRadius();
    }

    @Override
    public float getCubicIntensity() {
        return this.mCubicIntensity;
    }

    @Override
    public DashPathEffect getDashPathEffect() {
        return this.mDashPathEffect;
    }

    @Override
    public IFillFormatter getFillFormatter() {
        return this.mFillFormatter;
    }

    @Override
    public Mode getMode() {
        return this.mMode;
    }

    @Override
    public boolean isDashedLineEnabled() {
        if (this.mDashPathEffect != null) return true;
        return false;
    }

    @Override
    public boolean isDrawCircleHoleEnabled() {
        return this.mDrawCircleHole;
    }

    @Override
    public boolean isDrawCirclesEnabled() {
        return this.mDrawCircles;
    }

    @Deprecated
    @Override
    public boolean isDrawCubicEnabled() {
        if (this.mMode != Mode.CUBIC_BEZIER) return false;
        return true;
    }

    @Deprecated
    @Override
    public boolean isDrawSteppedEnabled() {
        if (this.mMode != Mode.STEPPED) return false;
        return true;
    }

    public void resetCircleColors() {
        if (this.mCircleColors == null) {
            this.mCircleColors = new ArrayList<Integer>();
        }
        this.mCircleColors.clear();
    }

    public void setCircleColor(int n) {
        this.resetCircleColors();
        this.mCircleColors.add(n);
    }

    public void setCircleColors(List<Integer> list) {
        this.mCircleColors = list;
    }

    public void setCircleColors(int ... arrn) {
        this.mCircleColors = ColorTemplate.createColors(arrn);
    }

    public void setCircleColors(int[] arrn, Context context) {
        List<Integer> list;
        List<Integer> list2 = list = this.mCircleColors;
        if (list == null) {
            list2 = new ArrayList<Integer>();
        }
        list2.clear();
        int n = arrn.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mCircleColors = list2;
                return;
            }
            int n3 = arrn[n2];
            list2.add(context.getResources().getColor(n3));
            ++n2;
        } while (true);
    }

    public void setCircleHoleColor(int n) {
        this.mCircleHoleColor = n;
    }

    public void setCircleHoleRadius(float f) {
        if (f >= 0.5f) {
            this.mCircleHoleRadius = Utils.convertDpToPixel(f);
            return;
        }
        Log.e((String)"LineDataSet", (String)"Circle radius cannot be < 0.5");
    }

    public void setCircleRadius(float f) {
        if (f >= 1.0f) {
            this.mCircleRadius = Utils.convertDpToPixel(f);
            return;
        }
        Log.e((String)"LineDataSet", (String)"Circle radius cannot be < 1");
    }

    @Deprecated
    public void setCircleSize(float f) {
        this.setCircleRadius(f);
    }

    public void setCubicIntensity(float f) {
        float f2 = f;
        if (f > 1.0f) {
            f2 = 1.0f;
        }
        f = f2;
        if (f2 < 0.05f) {
            f = 0.05f;
        }
        this.mCubicIntensity = f;
    }

    public void setDrawCircleHole(boolean bl) {
        this.mDrawCircleHole = bl;
    }

    public void setDrawCircles(boolean bl) {
        this.mDrawCircles = bl;
    }

    public void setFillFormatter(IFillFormatter iFillFormatter) {
        if (iFillFormatter == null) {
            this.mFillFormatter = new DefaultFillFormatter();
            return;
        }
        this.mFillFormatter = iFillFormatter;
    }

    public void setMode(Mode mode) {
        this.mMode = mode;
    }

    public static final class Mode
    extends Enum<Mode> {
        private static final /* synthetic */ Mode[] $VALUES;
        public static final /* enum */ Mode CUBIC_BEZIER;
        public static final /* enum */ Mode HORIZONTAL_BEZIER;
        public static final /* enum */ Mode LINEAR;
        public static final /* enum */ Mode STEPPED;

        static {
            Mode mode;
            LINEAR = new Mode();
            STEPPED = new Mode();
            CUBIC_BEZIER = new Mode();
            HORIZONTAL_BEZIER = mode = new Mode();
            $VALUES = new Mode[]{LINEAR, STEPPED, CUBIC_BEZIER, mode};
        }

        public static Mode valueOf(String string2) {
            return Enum.valueOf(Mode.class, string2);
        }

        public static Mode[] values() {
            return (Mode[])$VALUES.clone();
        }
    }

}

