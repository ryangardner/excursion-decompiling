/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

final class zaci
implements Continuation<Boolean, Void> {
    zaci() {
    }

    @Override
    public final /* synthetic */ Object then(Task task) throws Exception {
        if ((Boolean)task.getResult() == false) throw new ApiException(new Status(13, "listener already unregistered"));
        return null;
    }
}

