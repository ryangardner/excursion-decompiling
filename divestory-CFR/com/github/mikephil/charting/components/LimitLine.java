/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 */
package com.github.mikephil.charting.components;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.utils.Utils;

public class LimitLine
extends ComponentBase {
    private DashPathEffect mDashPathEffect = null;
    private String mLabel = "";
    private LimitLabelPosition mLabelPosition = LimitLabelPosition.RIGHT_TOP;
    private float mLimit = 0.0f;
    private int mLineColor = Color.rgb((int)237, (int)91, (int)91);
    private float mLineWidth = 2.0f;
    private Paint.Style mTextStyle = Paint.Style.FILL_AND_STROKE;

    public LimitLine(float f) {
        this.mLimit = f;
    }

    public LimitLine(float f, String string2) {
        this.mLimit = f;
        this.mLabel = string2;
    }

    public void disableDashedLine() {
        this.mDashPathEffect = null;
    }

    public void enableDashedLine(float f, float f2, float f3) {
        this.mDashPathEffect = new DashPathEffect(new float[]{f, f2}, f3);
    }

    public DashPathEffect getDashPathEffect() {
        return this.mDashPathEffect;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public LimitLabelPosition getLabelPosition() {
        return this.mLabelPosition;
    }

    public float getLimit() {
        return this.mLimit;
    }

    public int getLineColor() {
        return this.mLineColor;
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public Paint.Style getTextStyle() {
        return this.mTextStyle;
    }

    public boolean isDashedLineEnabled() {
        if (this.mDashPathEffect != null) return true;
        return false;
    }

    public void setLabel(String string2) {
        this.mLabel = string2;
    }

    public void setLabelPosition(LimitLabelPosition limitLabelPosition) {
        this.mLabelPosition = limitLabelPosition;
    }

    public void setLineColor(int n) {
        this.mLineColor = n;
    }

    public void setLineWidth(float f) {
        float f2 = f;
        if (f < 0.2f) {
            f2 = 0.2f;
        }
        f = f2;
        if (f2 > 12.0f) {
            f = 12.0f;
        }
        this.mLineWidth = Utils.convertDpToPixel(f);
    }

    public void setTextStyle(Paint.Style style2) {
        this.mTextStyle = style2;
    }

    public static final class LimitLabelPosition
    extends Enum<LimitLabelPosition> {
        private static final /* synthetic */ LimitLabelPosition[] $VALUES;
        public static final /* enum */ LimitLabelPosition LEFT_BOTTOM;
        public static final /* enum */ LimitLabelPosition LEFT_TOP;
        public static final /* enum */ LimitLabelPosition RIGHT_BOTTOM;
        public static final /* enum */ LimitLabelPosition RIGHT_TOP;

        static {
            LimitLabelPosition limitLabelPosition;
            LEFT_TOP = new LimitLabelPosition();
            LEFT_BOTTOM = new LimitLabelPosition();
            RIGHT_TOP = new LimitLabelPosition();
            RIGHT_BOTTOM = limitLabelPosition = new LimitLabelPosition();
            $VALUES = new LimitLabelPosition[]{LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, limitLabelPosition};
        }

        public static LimitLabelPosition valueOf(String string2) {
            return Enum.valueOf(LimitLabelPosition.class, string2);
        }

        public static LimitLabelPosition[] values() {
            return (LimitLabelPosition[])$VALUES.clone();
        }
    }

}

