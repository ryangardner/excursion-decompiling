/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zaj {
    private final ArrayMap<ApiKey<?>, ConnectionResult> zaa = new ArrayMap();
    private final ArrayMap<ApiKey<?>, String> zab = new ArrayMap();
    private final TaskCompletionSource<Map<ApiKey<?>, String>> zac = new TaskCompletionSource();
    private int zad;
    private boolean zae = false;

    public zaj(Iterable<? extends HasApiKey<?>> object) {
        object = object.iterator();
        do {
            if (!object.hasNext()) {
                this.zad = this.zaa.keySet().size();
                return;
            }
            HasApiKey hasApiKey = (HasApiKey)object.next();
            this.zaa.put(hasApiKey.getApiKey(), null);
        } while (true);
    }

    public final Set<ApiKey<?>> zaa() {
        return this.zaa.keySet();
    }

    public final void zaa(ApiKey<?> object, ConnectionResult connectionResult, String string2) {
        this.zaa.put((ApiKey<?>)object, connectionResult);
        this.zab.put((ApiKey<?>)object, string2);
        --this.zad;
        if (!connectionResult.isSuccess()) {
            this.zae = true;
        }
        if (this.zad != 0) return;
        if (this.zae) {
            object = new AvailabilityException(this.zaa);
            this.zac.setException((Exception)object);
            return;
        }
        this.zac.setResult(this.zab);
    }

    public final Task<Map<ApiKey<?>, String>> zab() {
        return this.zac.getTask();
    }
}

