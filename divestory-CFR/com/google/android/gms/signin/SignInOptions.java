/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.signin;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.Objects;

public final class SignInOptions
implements Api.ApiOptions.Optional {
    public static final SignInOptions zaa;
    private final boolean zab;
    private final boolean zac;
    private final String zad;
    private final boolean zae;
    private final String zaf;
    private final String zag;
    private final boolean zah;
    private final Long zai = null;
    private final Long zaj = null;

    static {
        new zaa();
        zaa = new SignInOptions(false, false, null, false, null, null, false, null, null);
    }

    private SignInOptions(boolean bl, boolean bl2, String string2, boolean bl3, String string3, String string4, boolean bl4, Long l, Long l2) {
        this.zab = false;
        this.zac = false;
        this.zad = null;
        this.zae = false;
        this.zah = false;
        this.zaf = null;
        this.zag = null;
    }

    public final boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SignInOptions)) {
            return false;
        }
        object = (SignInOptions)object;
        if (!Objects.equal(null, null)) return false;
        if (!Objects.equal(null, null)) return false;
        if (!Objects.equal(null, null)) return false;
        if (!Objects.equal(null, null)) return false;
        if (!Objects.equal(null, null)) return false;
        return true;
    }

    public final int hashCode() {
        Boolean bl = false;
        return Objects.hashCode(bl, bl, null, bl, bl, null, null, null, null);
    }

    public static final class zaa {
    }

}

