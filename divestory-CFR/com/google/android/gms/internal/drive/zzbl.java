/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.drive.zzbi;

final class zzbl
implements ResultCallback<Status> {
    zzbl(zzbi zzbi2) {
    }

    @Override
    public final /* synthetic */ void onResult(Result result) {
        if (((Status)(result = (Status)result)).isSuccess()) return;
        zzbi.zzx().efmt("DriveContentsImpl", "Error discarding contents, status: %s", result);
    }
}

