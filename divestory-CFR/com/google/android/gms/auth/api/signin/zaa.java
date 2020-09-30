/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.auth.api.signin;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Scope;
import java.util.Comparator;

final class zaa
implements Comparator {
    static final Comparator zaa = new zaa();

    private zaa() {
    }

    public final int compare(Object object, Object object2) {
        return GoogleSignInAccount.zaa((Scope)object, (Scope)object2);
    }
}

