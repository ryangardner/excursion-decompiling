/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.model;

public class GradientColor {
    private int endColor;
    private int startColor;

    public GradientColor(int n, int n2) {
        this.startColor = n;
        this.endColor = n2;
    }

    public int getEndColor() {
        return this.endColor;
    }

    public int getStartColor() {
        return this.startColor;
    }

    public void setEndColor(int n) {
        this.endColor = n;
    }

    public void setStartColor(int n) {
        this.startColor = n;
    }
}

