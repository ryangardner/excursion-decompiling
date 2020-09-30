/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.savedstate;

import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.SavedStateRegistry;

public interface SavedStateRegistryOwner
extends LifecycleOwner {
    public SavedStateRegistry getSavedStateRegistry();
}

