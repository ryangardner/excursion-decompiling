/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.auth.api.signin;

import com.google.android.gms.common.api.Scope;
import java.util.Comparator;

final class zac
implements Comparator<Scope> {
    zac() {
    }

    @Override
    public final /* synthetic */ int compare(Object object, Object object2) {
        object = (Scope)object;
        object2 = (Scope)object2;
        return ((Scope)object).getScopeUri().compareTo(((Scope)object2).getScopeUri());
    }
}

