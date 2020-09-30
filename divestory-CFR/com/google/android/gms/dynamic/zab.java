/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.dynamic;

import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import java.util.Iterator;

final class zab
implements OnDelegateCreatedListener<T> {
    private final /* synthetic */ DeferredLifecycleHelper zaa;

    zab(DeferredLifecycleHelper deferredLifecycleHelper) {
        this.zaa = deferredLifecycleHelper;
    }

    @Override
    public final void onDelegateCreated(T object) {
        DeferredLifecycleHelper.zaa(this.zaa, object);
        object = DeferredLifecycleHelper.zaa(this.zaa).iterator();
        do {
            if (!object.hasNext()) {
                DeferredLifecycleHelper.zaa(this.zaa).clear();
                DeferredLifecycleHelper.zaa(this.zaa, null);
                return;
            }
            ((DeferredLifecycleHelper.zaa)object.next()).zaa(DeferredLifecycleHelper.zab(this.zaa));
        } while (true);
    }
}

