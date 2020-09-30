/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.lifecycle;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

@Deprecated
public interface LifecycleRegistryOwner
extends LifecycleOwner {
    @Override
    public LifecycleRegistry getLifecycle();
}

