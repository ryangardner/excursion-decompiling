/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import java.util.Map;

final class zab
implements SuccessContinuation {
    static final SuccessContinuation zaa = new zab();

    private zab() {
    }

    public final Task then(Object object) {
        return GoogleApiAvailability.zab((Map)object);
    }
}

