/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 */
package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class SquareShapeRenderer
implements IShapeRenderer {
    @Override
    public void renderShape(Canvas canvas, IScatterDataSet iScatterDataSet, ViewPortHandler viewPortHandler, float f, float f2, Paint paint) {
        float f3 = iScatterDataSet.getScatterShapeSize();
        float f4 = f3 / 2.0f;
        float f5 = Utils.convertDpToPixel(iScatterDataSet.getScatterShapeHoleRadius());
        float f6 = (f3 - f5 * 2.0f) / 2.0f;
        float f7 = f6 / 2.0f;
        int n = iScatterDataSet.getScatterShapeHoleColor();
        if ((double)f3 > 0.0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(f6);
            f3 = f - f5;
            f4 = f2 - f5;
            canvas.drawRect(f3 - f7, f4 - f7, (f += f5) + f7, (f2 += f5) + f7, paint);
            if (n == 1122867) return;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(n);
            canvas.drawRect(f3, f4, f, f2, paint);
            return;
        }
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(f - f4, f2 - f4, f + f4, f2 + f4, paint);
    }
}

