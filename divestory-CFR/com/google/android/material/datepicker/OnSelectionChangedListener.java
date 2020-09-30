/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.datepicker;

abstract class OnSelectionChangedListener<S> {
    OnSelectionChangedListener() {
    }

    void onIncompleteSelectionChanged() {
    }

    abstract void onSelectionChanged(S var1);
}

