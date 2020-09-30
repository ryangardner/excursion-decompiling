/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 */
package com.github.mikephil.charting.components;

import android.graphics.Canvas;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public interface IMarker {
    public void draw(Canvas var1, float var2, float var3);

    public MPPointF getOffset();

    public MPPointF getOffsetForDrawingAtPoint(float var1, float var2);

    public void refreshContent(Entry var1, Highlight var2);
}

