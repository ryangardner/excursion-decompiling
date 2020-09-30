/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 */
package com.google.android.material.resources;

import android.graphics.Typeface;
import com.google.android.material.resources.TextAppearanceFontCallback;

public final class CancelableFontCallback
extends TextAppearanceFontCallback {
    private final ApplyFont applyFont;
    private boolean cancelled;
    private final Typeface fallbackFont;

    public CancelableFontCallback(ApplyFont applyFont, Typeface typeface) {
        this.fallbackFont = typeface;
        this.applyFont = applyFont;
    }

    private void updateIfNotCancelled(Typeface typeface) {
        if (this.cancelled) return;
        this.applyFont.apply(typeface);
    }

    public void cancel() {
        this.cancelled = true;
    }

    @Override
    public void onFontRetrievalFailed(int n) {
        this.updateIfNotCancelled(this.fallbackFont);
    }

    @Override
    public void onFontRetrieved(Typeface typeface, boolean bl) {
        this.updateIfNotCancelled(typeface);
    }

    public static interface ApplyFont {
        public void apply(Typeface var1);
    }

}

