/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zaad;
import com.google.android.gms.common.api.internal.zaag;
import com.google.android.gms.common.api.internal.zaan;
import com.google.android.gms.common.api.internal.zaap;
import com.google.android.gms.common.api.internal.zaax;
import com.google.android.gms.common.internal.IAccountAccessor;
import java.util.ArrayList;
import java.util.Set;

final class zaaj
extends zaan {
    private final ArrayList<Api.Client> zaa;
    private final /* synthetic */ zaad zab;

    public zaaj(ArrayList<Api.Client> arrayList) {
        this.zab = var1_1;
        super(var1_1, null);
        this.zaa = arrayList;
    }

    @Override
    public final void zaa() {
        zaad.zad((zaad)this.zab).zad.zac = zaad.zag(this.zab);
        ArrayList<Api.Client> arrayList = this.zaa;
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            Api.Client client = arrayList.get(n2);
            ++n2;
            client.getRemoteService(zaad.zah(this.zab), zaad.zad((zaad)this.zab).zad.zac);
        }
    }
}

