/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import java.util.Map;

final class zaa
implements SuccessContinuation {
    static final SuccessContinuation zaa = new zaa();

    private zaa() {
    }

    public final Task then(Object object) {
        return GoogleApiAvailability.zaa((Map)object);
    }
}

