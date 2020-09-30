/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 */
package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class TriangleShapeRenderer
implements IShapeRenderer {
    protected Path mTrianglePathBuffer = new Path();

    @Override
    public void renderShape(Canvas canvas, IScatterDataSet iScatterDataSet, ViewPortHandler viewPortHandler, float f, float f2, Paint paint) {
        float f3 = iScatterDataSet.getScatterShapeSize();
        float f4 = f3 / 2.0f;
        float f5 = (f3 - Utils.convertDpToPixel(iScatterDataSet.getScatterShapeHoleRadius()) * 2.0f) / 2.0f;
        int n = iScatterDataSet.getScatterShapeHoleColor();
        paint.setStyle(Paint.Style.FILL);
        iScatterDataSet = this.mTrianglePathBuffer;
        iScatterDataSet.reset();
        float f6 = f2 - f4;
        iScatterDataSet.moveTo(f, f6);
        float f7 = f + f4;
        float f8 = f2 + f4;
        iScatterDataSet.lineTo(f7, f8);
        f2 = f - f4;
        iScatterDataSet.lineTo(f2, f8);
        double d = (double)f3 DCMPL 0.0;
        if (d > 0) {
            iScatterDataSet.lineTo(f, f6);
            f4 = f2 + f5;
            f3 = f8 - f5;
            iScatterDataSet.moveTo(f4, f3);
            iScatterDataSet.lineTo(f7 - f5, f3);
            iScatterDataSet.lineTo(f, f6 + f5);
            iScatterDataSet.lineTo(f4, f3);
        }
        iScatterDataSet.close();
        canvas.drawPath((Path)iScatterDataSet, paint);
        iScatterDataSet.reset();
        if (d <= 0) return;
        if (n == 1122867) return;
        paint.setColor(n);
        iScatterDataSet.moveTo(f, f6 + f5);
        f = f8 - f5;
        iScatterDataSet.lineTo(f7 - f5, f);
        iScatterDataSet.lineTo(f2 + f5, f);
        iScatterDataSet.close();
        canvas.drawPath((Path)iScatterDataSet, paint);
        iScatterDataSet.reset();
    }
}

