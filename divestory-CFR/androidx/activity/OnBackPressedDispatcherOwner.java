/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.activity;

import androidx.activity.OnBackPressedDispatcher;
import androidx.lifecycle.LifecycleOwner;

public interface OnBackPressedDispatcherOwner
extends LifecycleOwner {
    public OnBackPressedDispatcher getOnBackPressedDispatcher();
}

