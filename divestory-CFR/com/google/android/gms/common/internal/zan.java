/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.common.internal.PendingResultUtil;

final class zan
implements PendingResultUtil.zaa {
    zan() {
    }

    @Override
    public final ApiException zaa(Status status) {
        return ApiExceptionUtil.fromStatus(status);
    }
}

