/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zabx
implements RemoteCall {
    private final RegistrationMethods.Builder zaa;

    zabx(RegistrationMethods.Builder builder) {
        this.zaa = builder;
    }

    public final void accept(Object object, Object object2) {
        this.zaa.zaa((Api.AnyClient)object, (TaskCompletionSource)object2);
    }
}

