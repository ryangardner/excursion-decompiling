/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.fragment.app;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

class FragmentViewLifecycleOwner
implements LifecycleOwner {
    private LifecycleRegistry mLifecycleRegistry = null;

    FragmentViewLifecycleOwner() {
    }

    @Override
    public Lifecycle getLifecycle() {
        this.initialize();
        return this.mLifecycleRegistry;
    }

    void handleLifecycleEvent(Lifecycle.Event event) {
        this.mLifecycleRegistry.handleLifecycleEvent(event);
    }

    void initialize() {
        if (this.mLifecycleRegistry != null) return;
        this.mLifecycleRegistry = new LifecycleRegistry(this);
    }

    boolean isInitialized() {
        if (this.mLifecycleRegistry == null) return false;
        return true;
    }
}

