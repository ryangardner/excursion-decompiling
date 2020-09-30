/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.zaad;
import com.google.android.gms.common.api.internal.zaaf;
import com.google.android.gms.common.api.internal.zaag;
import com.google.android.gms.common.api.internal.zaah;
import com.google.android.gms.common.api.internal.zaak;
import com.google.android.gms.common.api.internal.zaan;
import com.google.android.gms.common.api.internal.zaay;
import com.google.android.gms.common.api.internal.zaba;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.zaj;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class zaai
extends zaan {
    final /* synthetic */ zaad zaa;
    private final Map<Api.Client, zaaf> zab;

    public zaai(Map<Api.Client, zaaf> map) {
        this.zaa = var1_1;
        super(var1_1, null);
        this.zab = map;
    }

    @Override
    public final void zaa() {
        int n;
        Object object;
        int n2;
        Object object3 = new zaj(zaad.zab(this.zaa));
        Object object4 = new ArrayList<Api.Client>();
        ArrayList<Api.Client> arrayList = new ArrayList<Api.Client>();
        for (Api.Client object22 : this.zab.keySet()) {
            if (object22.requiresGooglePlayServices() && !zaaf.zaa(this.zab.get(object22))) {
                object4.add(object22);
                continue;
            }
            arrayList.add(object22);
        }
        int n3 = -1;
        boolean bl = object4.isEmpty();
        int n4 = 0;
        if (bl) {
            object = arrayList;
            n = ((ArrayList)object).size();
            for (n2 = 0; n2 < n; ++n2) {
                object4 = ((ArrayList)object).get(n2);
                object4 = (Api.Client)object4;
                n3 = n4 = ((zaj)object3).zaa(zaad.zaa(this.zaa), (Api.Client)object4);
                if (n4 != 0) continue;
                n3 = n4;
                break;
            }
        } else {
            object = object4;
            n = ((ArrayList)object).size();
            for (n2 = n4; n2 < n; ++n2) {
                object4 = ((ArrayList)object).get(n2);
                object4 = (Api.Client)object4;
                n3 = n4 = ((zaj)object3).zaa(zaad.zaa(this.zaa), (Api.Client)object4);
                if (n4 == 0) continue;
                n3 = n4;
                break;
            }
        }
        if (n3 != 0) {
            object3 = new ConnectionResult(n3, null);
            zaad.zad(this.zaa).zaa(new zaah(this, this.zaa, (ConnectionResult)object3));
            return;
        }
        if (zaad.zae(this.zaa) && zaad.zaf(this.zaa) != null) {
            zaad.zaf(this.zaa).zab();
        }
        Iterator<Api.Client> iterator2 = this.zab.keySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            object4 = this.zab.get(object);
            if (object.requiresGooglePlayServices() && ((zaj)object3).zaa(zaad.zaa(this.zaa), (Api.Client)object) != 0) {
                zaad.zad(this.zaa).zaa(new zaak(this, this.zaa, (BaseGmsClient.ConnectionProgressReportCallbacks)object4));
                continue;
            }
            object.connect((BaseGmsClient.ConnectionProgressReportCallbacks)object4);
        }
    }
}

