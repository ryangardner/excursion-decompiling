/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.formatter.ValueFormatter;
import java.text.DecimalFormat;

public class DefaultValueFormatter
extends ValueFormatter {
    protected int mDecimalDigits;
    protected DecimalFormat mFormat;

    public DefaultValueFormatter(int n) {
        this.setup(n);
    }

    public int getDecimalDigits() {
        return this.mDecimalDigits;
    }

    @Override
    public String getFormattedValue(float f) {
        return this.mFormat.format(f);
    }

    public void setup(int n) {
        this.mDecimalDigits = n;
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
}

