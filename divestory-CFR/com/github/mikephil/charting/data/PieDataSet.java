/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class PieDataSet
extends DataSet<PieEntry>
implements IPieDataSet {
    private boolean mAutomaticallyDisableSliceSpacing;
    private float mShift = 18.0f;
    private float mSliceSpace = 0.0f;
    private boolean mUsingSliceColorAsValueLineColor = false;
    private int mValueLineColor = -16777216;
    private float mValueLinePart1Length = 0.3f;
    private float mValueLinePart1OffsetPercentage = 75.0f;
    private float mValueLinePart2Length = 0.4f;
    private boolean mValueLineVariableLength = true;
    private float mValueLineWidth = 1.0f;
    private ValuePosition mXValuePosition = ValuePosition.INSIDE_SLICE;
    private ValuePosition mYValuePosition = ValuePosition.INSIDE_SLICE;

    public PieDataSet(List<PieEntry> list, String string2) {
        super(list, string2);
    }

    @Override
    protected void calcMinMax(PieEntry pieEntry) {
        if (pieEntry == null) {
            return;
        }
        this.calcMinMaxY(pieEntry);
    }

    @Override
    public DataSet<PieEntry> copy() {
        Object object = new ArrayList<PieEntry>();
        int n = 0;
        do {
            if (n >= this.mValues.size()) {
                object = new PieDataSet((List<PieEntry>)object, this.getLabel());
                this.copy((PieDataSet)object);
                return object;
            }
            object.add((PieEntry)((PieEntry)this.mValues.get(n)).copy());
            ++n;
        } while (true);
    }

    protected void copy(PieDataSet pieDataSet) {
        super.copy(pieDataSet);
    }

    @Override
    public float getSelectionShift() {
        return this.mShift;
    }

    @Override
    public float getSliceSpace() {
        return this.mSliceSpace;
    }

    @Override
    public int getValueLineColor() {
        return this.mValueLineColor;
    }

    @Override
    public float getValueLinePart1Length() {
        return this.mValueLinePart1Length;
    }

    @Override
    public float getValueLinePart1OffsetPercentage() {
        return this.mValueLinePart1OffsetPercentage;
    }

    @Override
    public float getValueLinePart2Length() {
        return this.mValueLinePart2Length;
    }

    @Override
    public float getValueLineWidth() {
        return this.mValueLineWidth;
    }

    @Override
    public ValuePosition getXValuePosition() {
        return this.mXValuePosition;
    }

    @Override
    public ValuePosition getYValuePosition() {
        return this.mYValuePosition;
    }

    @Override
    public boolean isAutomaticallyDisableSliceSpacingEnabled() {
        return this.mAutomaticallyDisableSliceSpacing;
    }

    @Override
    public boolean isUsingSliceColorAsValueLineColor() {
        return this.mUsingSliceColorAsValueLineColor;
    }

    @Override
    public boolean isValueLineVariableLength() {
        return this.mValueLineVariableLength;
    }

    public void setAutomaticallyDisableSliceSpacing(boolean bl) {
        this.mAutomaticallyDisableSliceSpacing = bl;
    }

    public void setSelectionShift(float f) {
        this.mShift = Utils.convertDpToPixel(f);
    }

    public void setSliceSpace(float f) {
        float f2 = f;
        if (f > 20.0f) {
            f2 = 20.0f;
        }
        f = f2;
        if (f2 < 0.0f) {
            f = 0.0f;
        }
        this.mSliceSpace = Utils.convertDpToPixel(f);
    }

    public void setUsingSliceColorAsValueLineColor(boolean bl) {
        this.mUsingSliceColorAsValueLineColor = bl;
    }

    public void setValueLineColor(int n) {
        this.mValueLineColor = n;
    }

    public void setValueLinePart1Length(float f) {
        this.mValueLinePart1Length = f;
    }

    public void setValueLinePart1OffsetPercentage(float f) {
        this.mValueLinePart1OffsetPercentage = f;
    }

    public void setValueLinePart2Length(float f) {
        this.mValueLinePart2Length = f;
    }

    public void setValueLineVariableLength(boolean bl) {
        this.mValueLineVariableLength = bl;
    }

    public void setValueLineWidth(float f) {
        this.mValueLineWidth = f;
    }

    public void setXValuePosition(ValuePosition valuePosition) {
        this.mXValuePosition = valuePosition;
    }

    public void setYValuePosition(ValuePosition valuePosition) {
        this.mYValuePosition = valuePosition;
    }

    public static final class ValuePosition
    extends Enum<ValuePosition> {
        private static final /* synthetic */ ValuePosition[] $VALUES;
        public static final /* enum */ ValuePosition INSIDE_SLICE;
        public static final /* enum */ ValuePosition OUTSIDE_SLICE;

        static {
            ValuePosition valuePosition;
            INSIDE_SLICE = new ValuePosition();
            OUTSIDE_SLICE = valuePosition = new ValuePosition();
            $VALUES = new ValuePosition[]{INSIDE_SLICE, valuePosition};
        }

        public static ValuePosition valueOf(String string2) {
            return Enum.valueOf(ValuePosition.class, string2);
        }

        public static ValuePosition[] values() {
            return (ValuePosition[])$VALUES.clone();
        }
    }

}

