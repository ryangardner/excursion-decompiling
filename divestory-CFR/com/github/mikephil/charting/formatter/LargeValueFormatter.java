/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.formatter.ValueFormatter;
import java.text.DecimalFormat;

public class LargeValueFormatter
extends ValueFormatter {
    private DecimalFormat mFormat = new DecimalFormat("###E00");
    private int mMaxLength = 5;
    private String[] mSuffix = new String[]{"", "k", "m", "b", "t"};
    private String mText = "";

    public LargeValueFormatter() {
    }

    public LargeValueFormatter(String string2) {
        this();
        this.mText = string2;
    }

    private String makePretty(double d) {
        String string2 = this.mFormat.format(d);
        int n = Character.getNumericValue(string2.charAt(string2.length() - 1));
        int n2 = Character.getNumericValue(string2.charAt(string2.length() - 2));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n2);
        stringBuilder.append("");
        stringBuilder.append(n);
        n2 = Integer.valueOf(stringBuilder.toString());
        string2 = string2.replaceAll("E[0-9][0-9]", this.mSuffix[n2 / 3]);
        do {
            if (string2.length() <= this.mMaxLength) {
                if (!string2.matches("[0-9]+\\.[a-z]")) return string2;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2.substring(0, string2.length() - 2));
            stringBuilder.append(string2.substring(string2.length() - 1));
            string2 = stringBuilder.toString();
        } while (true);
    }

    public int getDecimalDigits() {
        return 0;
    }

    @Override
    public String getFormattedValue(float f) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.makePretty(f));
        stringBuilder.append(this.mText);
        return stringBuilder.toString();
    }

    public void setAppendix(String string2) {
        this.mText = string2;
    }

    public void setMaxLength(int n) {
        this.mMaxLength = n;
    }

    public void setSuffix(String[] arrstring) {
        this.mSuffix = arrstring;
    }
}

