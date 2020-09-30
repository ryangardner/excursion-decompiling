/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.formatter.ValueFormatter;
import java.util.Collection;

public class IndexAxisValueFormatter
extends ValueFormatter {
    private int mValueCount = 0;
    private String[] mValues = new String[0];

    public IndexAxisValueFormatter() {
    }

    public IndexAxisValueFormatter(Collection<String> collection) {
        if (collection == null) return;
        this.setValues(collection.toArray(new String[collection.size()]));
    }

    public IndexAxisValueFormatter(String[] arrstring) {
        if (arrstring == null) return;
        this.setValues(arrstring);
    }

    @Override
    public String getFormattedValue(float f) {
        int n = Math.round(f);
        if (n < 0) return "";
        if (n >= this.mValueCount) return "";
        if (n == (int)f) return this.mValues[n];
        return "";
    }

    public String[] getValues() {
        return this.mValues;
    }

    public void setValues(String[] arrstring) {
        String[] arrstring2 = arrstring;
        if (arrstring == null) {
            arrstring2 = new String[]{};
        }
        this.mValues = arrstring2;
        this.mValueCount = arrstring2.length;
    }
}

