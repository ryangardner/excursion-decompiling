/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.signin;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.gms.signin.zab;
import com.google.android.gms.signin.zac;
import com.google.android.gms.signin.zae;

public final class zaa {
    public static final Api.AbstractClientBuilder<SignInClientImpl, SignInOptions> zaa;
    public static final Api<SignInOptions> zab;
    private static final Api.ClientKey<SignInClientImpl> zac;
    private static final Api.ClientKey<SignInClientImpl> zad;
    private static final Api.AbstractClientBuilder<SignInClientImpl, zae> zae;
    private static final Scope zaf;
    private static final Scope zag;
    private static final Api<zae> zah;

    static {
        zac = new Api.ClientKey();
        zad = new Api.ClientKey();
        zaa = new zac();
        zae = new zab();
        zaf = new Scope("profile");
        zag = new Scope("email");
        zab = new Api<SignInOptions>("SignIn.API", zaa, zac);
        zah = new Api<zae>("SignIn.INTERNAL_API", zae, zad);
    }
}

