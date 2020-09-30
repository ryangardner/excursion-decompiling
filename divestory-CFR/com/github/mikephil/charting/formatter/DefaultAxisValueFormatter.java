/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.formatter.ValueFormatter;
import java.text.DecimalFormat;

public class DefaultAxisValueFormatter
extends ValueFormatter {
    protected int digits;
    protected DecimalFormat mFormat;

    public DefaultAxisValueFormatter(int n) {
        this.digits = n;
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        do {
            if (n2 >= n) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("###,###,###,##0");
                stringBuilder.append(stringBuffer.toString());
                this.mFormat = new DecimalFormat(stringBuilder.toString());
                return;
            }
            if (n2 == 0) {
                stringBuffer.append(".");
            }
            stringBuffer.append("0");
            ++n2;
        } while (true);
    }

    public int getDecimalDigits() {
        return this.digits;
    }

    @Override
    public String getFormattedValue(float f) {
        return this.mFormat.format(f);
    }
}

