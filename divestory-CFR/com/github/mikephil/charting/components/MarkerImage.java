/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.github.mikephil.charting.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointF;
import java.lang.ref.WeakReference;

public class MarkerImage
implements IMarker {
    private Context mContext;
    private Drawable mDrawable;
    private Rect mDrawableBoundsCache = new Rect();
    private MPPointF mOffset = new MPPointF();
    private MPPointF mOffset2 = new MPPointF();
    private FSize mSize = new FSize();
    private WeakReference<Chart> mWeakChart;

    public MarkerImage(Context context, int n) {
        this.mContext = context;
        if (Build.VERSION.SDK_INT >= 21) {
            this.mDrawable = this.mContext.getResources().getDrawable(n, null);
            return;
        }
        this.mDrawable = this.mContext.getResources().getDrawable(n);
    }

    @Override
    public void draw(Canvas canvas, float f, float f2) {
        if (this.mDrawable == null) {
            return;
        }
        MPPointF mPPointF = this.getOffsetForDrawingAtPoint(f, f2);
        float f3 = this.mSize.width;
        float f4 = this.mSize.height;
        float f5 = f3;
        if (f3 == 0.0f) {
            f5 = this.mDrawable.getIntrinsicWidth();
        }
        f3 = f4;
        if (f4 == 0.0f) {
            f3 = this.mDrawable.getIntrinsicHeight();
        }
        this.mDrawable.copyBounds(this.mDrawableBoundsCache);
        this.mDrawable.setBounds(this.mDrawableBoundsCache.left, this.mDrawableBoundsCache.top, this.mDrawableBoundsCache.left + (int)f5, this.mDrawableBoundsCache.top + (int)f3);
        int n = canvas.save();
        canvas.translate(f + mPPointF.x, f2 + mPPointF.y);
        this.mDrawable.draw(canvas);
        canvas.restoreToCount(n);
        this.mDrawable.setBounds(this.mDrawableBoundsCache);
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
        Drawable drawable2;
        Object object = this.getOffset();
        this.mOffset2.x = ((MPPointF)object).x;
        this.mOffset2.y = ((MPPointF)object).y;
        object = this.getChartView();
        float f3 = this.mSize.width;
        float f4 = this.mSize.height;
        float f5 = f3;
        if (f3 == 0.0f) {
            drawable2 = this.mDrawable;
            f5 = f3;
            if (drawable2 != null) {
                f5 = drawable2.getIntrinsicWidth();
            }
        }
        f3 = f4;
        if (f4 == 0.0f) {
            drawable2 = this.mDrawable;
            f3 = f4;
            if (drawable2 != null) {
                f3 = drawable2.getIntrinsicHeight();
            }
        }
        if (this.mOffset2.x + f < 0.0f) {
            this.mOffset2.x = -f;
        } else if (object != null && f + f5 + this.mOffset2.x > (float)object.getWidth()) {
            this.mOffset2.x = (float)object.getWidth() - f - f5;
        }
        if (this.mOffset2.y + f2 < 0.0f) {
            this.mOffset2.y = -f2;
            return this.mOffset2;
        }
        if (object == null) return this.mOffset2;
        if (!(f2 + f3 + this.mOffset2.y > (float)object.getHeight())) return this.mOffset2;
        this.mOffset2.y = (float)object.getHeight() - f2 - f3;
        return this.mOffset2;
    }

    public FSize getSize() {
        return this.mSize;
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
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

    public void setSize(FSize fSize) {
        this.mSize = fSize;
        if (fSize != null) return;
        this.mSize = new FSize();
    }
}

