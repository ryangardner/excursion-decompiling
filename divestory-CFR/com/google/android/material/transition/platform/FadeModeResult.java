/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.transition.platform;

class FadeModeResult {
    final int endAlpha;
    final boolean endOnTop;
    final int startAlpha;

    private FadeModeResult(int n, int n2, boolean bl) {
        this.startAlpha = n;
        this.endAlpha = n2;
        this.endOnTop = bl;
    }

    static FadeModeResult endOnTop(int n, int n2) {
        return new FadeModeResult(n, n2, true);
    }

    static FadeModeResult startOnTop(int n, int n2) {
        return new FadeModeResult(n, n2, false);
    }
}

