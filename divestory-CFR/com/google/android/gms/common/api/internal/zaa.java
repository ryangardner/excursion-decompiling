/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.google.android.gms.common.api.internal;

import android.app.Activity;
import com.google.android.gms.common.api.internal.ActivityLifecycleObserver;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class zaa
extends ActivityLifecycleObserver {
    private final WeakReference<zaa> zaa;

    public zaa(Activity activity) {
        this(zaa.zab(activity));
    }

    private zaa(zaa zaa2) {
        this.zaa = new WeakReference<zaa>(zaa2);
    }

    @Override
    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable2) {
        zaa zaa2 = (zaa)this.zaa.get();
        if (zaa2 == null) throw new IllegalStateException("The target activity has already been GC'd");
        zaa2.zaa(runnable2);
        return this;
    }

    static class zaa
    extends LifecycleCallback {
        private List<Runnable> zaa = new ArrayList<Runnable>();

        private zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        private final void zaa(Runnable runnable2) {
            synchronized (this) {
                this.zaa.add(runnable2);
                return;
            }
        }

        private static zaa zab(Activity activity) {
            synchronized (activity) {
                zaa zaa2;
                LifecycleFragment lifecycleFragment = zaa.getFragment(activity);
                zaa zaa3 = zaa2 = lifecycleFragment.getCallbackOrNull("LifecycleObserverOnStop", zaa.class);
                if (zaa2 != null) return zaa3;
                return new zaa(lifecycleFragment);
            }
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public void onStop() {
            Object object;
            synchronized (this) {
                object = this.zaa;
                ArrayList<Runnable> arrayList = new ArrayList<Runnable>();
                this.zaa = arrayList;
            }
            object = object.iterator();
            while (object.hasNext()) {
                ((Runnable)object.next()).run();
            }
        }
    }

}

