/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.components;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.Utils;

public class XAxis
extends AxisBase {
    private boolean mAvoidFirstLastClipping = false;
    public int mLabelHeight = 1;
    public int mLabelRotatedHeight = 1;
    public int mLabelRotatedWidth = 1;
    protected float mLabelRotationAngle = 0.0f;
    public int mLabelWidth = 1;
    private XAxisPosition mPosition = XAxisPosition.TOP;

    public XAxis() {
        this.mYOffset = Utils.convertDpToPixel(4.0f);
    }

    public float getLabelRotationAngle() {
        return this.mLabelRotationAngle;
    }

    public XAxisPosition getPosition() {
        return this.mPosition;
    }

    public boolean isAvoidFirstLastClippingEnabled() {
        return this.mAvoidFirstLastClipping;
    }

    public void setAvoidFirstLastClipping(boolean bl) {
        this.mAvoidFirstLastClipping = bl;
    }

    public void setLabelRotationAngle(float f) {
        this.mLabelRotationAngle = f;
    }

    public void setPosition(XAxisPosition xAxisPosition) {
        this.mPosition = xAxisPosition;
    }

    public static final class XAxisPosition
    extends Enum<XAxisPosition> {
        private static final /* synthetic */ XAxisPosition[] $VALUES;
        public static final /* enum */ XAxisPosition BOTH_SIDED;
        public static final /* enum */ XAxisPosition BOTTOM;
        public static final /* enum */ XAxisPosition BOTTOM_INSIDE;
        public static final /* enum */ XAxisPosition TOP;
        public static final /* enum */ XAxisPosition TOP_INSIDE;

        static {
            XAxisPosition xAxisPosition;
            TOP = new XAxisPosition();
            BOTTOM = new XAxisPosition();
            BOTH_SIDED = new XAxisPosition();
            TOP_INSIDE = new XAxisPosition();
            BOTTOM_INSIDE = xAxisPosition = new XAxisPosition();
            $VALUES = new XAxisPosition[]{TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE, xAxisPosition};
        }

        public static XAxisPosition valueOf(String string2) {
            return Enum.valueOf(XAxisPosition.class, string2);
        }

        public static XAxisPosition[] values() {
            return (XAxisPosition[])$VALUES.clone();
        }
    }

}

