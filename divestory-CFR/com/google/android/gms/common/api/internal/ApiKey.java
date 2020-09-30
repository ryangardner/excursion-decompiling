/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;

public final class ApiKey<O extends Api.ApiOptions> {
    private final boolean zaa;
    private final int zab;
    private final Api<O> zac;
    private final O zad;

    private ApiKey(Api<O> api, O o) {
        this.zaa = false;
        this.zac = api;
        this.zad = o;
        this.zab = Objects.hashCode(api, o);
    }

    public static <O extends Api.ApiOptions> ApiKey<O> getSharedApiKey(Api<O> api, O o) {
        return new ApiKey<O>(api, o);
    }

    public final boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof ApiKey)) {
            return false;
        }
        object = (ApiKey)object;
        if (!Objects.equal(this.zac, ((ApiKey)object).zac)) return false;
        if (!Objects.equal(this.zad, ((ApiKey)object).zad)) return false;
        return true;
    }

    public final String getApiName() {
        return this.zac.zad();
    }

    public final Api.AnyClientKey<?> getClientKey() {
        return this.zac.zac();
    }

    public final int hashCode() {
        return this.zab;
    }

    public final boolean isUnique() {
        return false;
    }
}

