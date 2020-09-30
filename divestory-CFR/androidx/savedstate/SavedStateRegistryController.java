/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.savedstate;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.savedstate.Recreator;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;

public final class SavedStateRegistryController {
    private final SavedStateRegistryOwner mOwner;
    private final SavedStateRegistry mRegistry;

    private SavedStateRegistryController(SavedStateRegistryOwner savedStateRegistryOwner) {
        this.mOwner = savedStateRegistryOwner;
        this.mRegistry = new SavedStateRegistry();
    }

    public static SavedStateRegistryController create(SavedStateRegistryOwner savedStateRegistryOwner) {
        return new SavedStateRegistryController(savedStateRegistryOwner);
    }

    public SavedStateRegistry getSavedStateRegistry() {
        return this.mRegistry;
    }

    public void performRestore(Bundle bundle) {
        Lifecycle lifecycle = this.mOwner.getLifecycle();
        if (lifecycle.getCurrentState() != Lifecycle.State.INITIALIZED) throw new IllegalStateException("Restarter must be created only during owner's initialization stage");
        lifecycle.addObserver(new Recreator(this.mOwner));
        this.mRegistry.performRestore(lifecycle, bundle);
    }

    public void performSave(Bundle bundle) {
        this.mRegistry.performSave(bundle);
    }
}

