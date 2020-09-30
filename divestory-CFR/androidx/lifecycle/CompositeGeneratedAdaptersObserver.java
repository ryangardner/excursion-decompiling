/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.lifecycle;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;

class CompositeGeneratedAdaptersObserver
implements LifecycleEventObserver {
    private final GeneratedAdapter[] mGeneratedAdapters;

    CompositeGeneratedAdaptersObserver(GeneratedAdapter[] arrgeneratedAdapter) {
        this.mGeneratedAdapters = arrgeneratedAdapter;
    }

    @Override
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        int n;
        MethodCallsLogger methodCallsLogger = new MethodCallsLogger();
        GeneratedAdapter[] arrgeneratedAdapter = this.mGeneratedAdapters;
        int n2 = arrgeneratedAdapter.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            arrgeneratedAdapter[n].callMethods(lifecycleOwner, event, false, methodCallsLogger);
        }
        arrgeneratedAdapter = this.mGeneratedAdapters;
        n2 = arrgeneratedAdapter.length;
        n = n3;
        while (n < n2) {
            arrgeneratedAdapter[n].callMethods(lifecycleOwner, event, true, methodCallsLogger);
            ++n;
        }
    }
}

