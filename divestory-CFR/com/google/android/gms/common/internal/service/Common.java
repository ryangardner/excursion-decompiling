/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.service.zab;
import com.google.android.gms.common.internal.service.zac;
import com.google.android.gms.common.internal.service.zad;
import com.google.android.gms.common.internal.service.zaj;

public final class Common {
    public static final Api<Api.ApiOptions.NoOptions> API;
    public static final Api.ClientKey<zaj> CLIENT_KEY;
    public static final zad zaa;
    private static final Api.AbstractClientBuilder<zaj, Api.ApiOptions.NoOptions> zab;

    static {
        CLIENT_KEY = new Api.ClientKey();
        zab = new zab();
        API = new Api<Api.ApiOptions.NoOptions>("Common.API", zab, CLIENT_KEY);
        zaa = new zac();
    }
}

