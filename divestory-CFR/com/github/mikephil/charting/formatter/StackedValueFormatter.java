/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import java.text.DecimalFormat;

public class StackedValueFormatter
extends ValueFormatter {
    private boolean mDrawWholeStack;
    private DecimalFormat mFormat;
    private String mSuffix;

    public StackedValueFormatter(boolean bl, String charSequence, int n) {
        this.mDrawWholeStack = bl;
        this.mSuffix = charSequence;
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        do {
            if (n2 >= n) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("###,###,###,##0");
                ((StringBuilder)charSequence).append(stringBuffer.toString());
                this.mFormat = new DecimalFormat(((StringBuilder)charSequence).toString());
                return;
            }
            if (n2 == 0) {
                stringBuffer.append(".");
            }
            stringBuffer.append("0");
            ++n2;
        } while (true);
    }

    @Override
    public String getBarStackedLabel(float f, BarEntry object) {
        Object object2;
        if (!this.mDrawWholeStack && (object2 = ((BarEntry)object).getYVals()) != null) {
            if (object2[((float[])object2).length - 1] != f) return "";
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(this.mFormat.format(((BarEntry)object).getY()));
            ((StringBuilder)object2).append(this.mSuffix);
            return ((StringBuilder)object2).toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.mFormat.format(f));
        ((StringBuilder)object).append(this.mSuffix);
        return ((StringBuilder)object).toString();
    }
}

