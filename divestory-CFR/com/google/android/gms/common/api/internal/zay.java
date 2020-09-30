/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zaw;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zay
implements OnCompleteListener<TResult> {
    private final /* synthetic */ TaskCompletionSource zaa;
    private final /* synthetic */ zaw zab;

    zay(zaw zaw2, TaskCompletionSource taskCompletionSource) {
        this.zab = zaw2;
        this.zaa = taskCompletionSource;
    }

    @Override
    public final void onComplete(Task<TResult> task) {
        zaw.zab(this.zab).remove(this.zaa);
    }
}

