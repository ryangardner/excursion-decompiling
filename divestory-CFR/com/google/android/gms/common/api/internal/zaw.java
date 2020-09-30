/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zav;
import com.google.android.gms.common.api.internal.zay;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class zaw {
    private final Map<BasePendingResult<?>, Boolean> zaa = Collections.synchronizedMap(new WeakHashMap());
    private final Map<TaskCompletionSource<?>, Boolean> zab = Collections.synchronizedMap(new WeakHashMap());

    static /* synthetic */ Map zaa(zaw zaw2) {
        return zaw2.zaa;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    private final void zaa(boolean bl, Status status) {
        Object object;
        Object object2 = this.zaa;
        synchronized (object2) {
            object = new HashMap(this.zaa);
        }
        Iterator iterator22 = this.zab;
        synchronized (iterator22) {
            object2 = new HashMap(this.zab);
        }
        for (Iterator iterator22 : object.entrySet()) {
            if (!bl && !((Boolean)iterator22.getValue()).booleanValue()) continue;
            ((BasePendingResult)iterator22.getKey()).forceFailureUnlessReady(status);
        }
        iterator22 = object2.entrySet().iterator();
        while (iterator22.hasNext()) {
            object2 = iterator22.next();
            if (!bl && !((Boolean)object2.getValue()).booleanValue()) continue;
            ((TaskCompletionSource)object2.getKey()).trySetException(new ApiException(status));
        }
    }

    static /* synthetic */ Map zab(zaw zaw2) {
        return zaw2.zab;
    }

    final void zaa(int n, String string2) {
        StringBuilder stringBuilder = new StringBuilder("The connection to Google Play services was lost");
        if (n == 1) {
            stringBuilder.append(" due to service disconnection.");
        } else if (n == 3) {
            stringBuilder.append(" due to dead object exception.");
        }
        if (string2 != null) {
            stringBuilder.append(" Last reason for disconnect: ");
            stringBuilder.append(string2);
        }
        this.zaa(true, new Status(20, stringBuilder.toString()));
    }

    final void zaa(BasePendingResult<? extends Result> basePendingResult, boolean bl) {
        this.zaa.put(basePendingResult, bl);
        ((PendingResult)basePendingResult).addStatusListener(new zav(this, basePendingResult));
    }

    final <TResult> void zaa(TaskCompletionSource<TResult> taskCompletionSource, boolean bl) {
        this.zab.put(taskCompletionSource, bl);
        taskCompletionSource.getTask().addOnCompleteListener(new zay(this, taskCompletionSource));
    }

    final boolean zaa() {
        if (!this.zaa.isEmpty()) return true;
        if (this.zab.isEmpty()) return false;
        return true;
    }

    public final void zab() {
        this.zaa(false, GoogleApiManager.zaa);
    }
}

