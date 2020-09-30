/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.util.AttributeSet
 */
package com.syntak.library.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

public class LinedEditText
extends AppCompatEditText {
    private Paint mPaint = new Paint();

    public LinedEditText(Context context) {
        super(context);
        this.initPaint();
    }

    public LinedEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initPaint();
    }

    public LinedEditText(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initPaint();
    }

    private void initPaint() {
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(Integer.MIN_VALUE);
    }

    protected void onDraw(Canvas canvas) {
        int n = this.getLeft();
        int n2 = this.getRight();
        int n3 = this.getPaddingTop();
        int n4 = this.getPaddingBottom();
        int n5 = this.getPaddingLeft();
        int n6 = this.getPaddingRight();
        int n7 = this.getHeight();
        int n8 = this.getLineHeight();
        n7 = (n7 - n3 - n4) / n8;
        n4 = 0;
        do {
            if (n4 >= n7) {
                super.onDraw(canvas);
                return;
            }
            float f = n + n5;
            float f2 = n8 * ++n4 + n3;
            canvas.drawLine(f, f2, (float)(n2 - n6), f2, this.mPaint);
        } while (true);
    }
}

