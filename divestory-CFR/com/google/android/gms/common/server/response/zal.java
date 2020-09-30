/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.zak;
import com.google.android.gms.common.server.response.zam;
import com.google.android.gms.common.server.response.zan;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zal
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zal> CREATOR = new zam();
    private final int zaa;
    private final HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> zab;
    private final ArrayList<zak> zac;
    private final String zad;

    zal(int n, ArrayList<zak> arrayList, String string2) {
        this.zaa = n;
        this.zac = null;
        HashMap hashMap = new HashMap();
        int n2 = arrayList.size();
        n = 0;
        do {
            if (n >= n2) {
                this.zab = hashMap;
                this.zad = Preconditions.checkNotNull(string2);
                this.zaa();
                return;
            }
            zak zak2 = arrayList.get(n);
            String string3 = zak2.zaa;
            HashMap hashMap2 = new HashMap();
            int n3 = Preconditions.checkNotNull(zak2.zab).size();
            for (int i = 0; i < n3; ++i) {
                zan zan2 = zak2.zab.get(i);
                hashMap2.put(zan2.zaa, zan2.zab);
            }
            hashMap.put(string3, hashMap2);
            ++n;
        } while (true);
    }

    public zal(Class<? extends FastJsonResponse> class_) {
        this.zaa = 1;
        this.zac = null;
        this.zab = new HashMap();
        this.zad = Preconditions.checkNotNull(class_.getCanonicalName());
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> iterator2 = this.zab.keySet().iterator();
        block0 : while (iterator2.hasNext()) {
            Object object = iterator2.next();
            stringBuilder.append((String)object);
            stringBuilder.append(":\n");
            Map<String, FastJsonResponse.Field<?, ?>> map = this.zab.get(object);
            object = map.keySet().iterator();
            do {
                if (!object.hasNext()) continue block0;
                String string2 = (String)object.next();
                stringBuilder.append("  ");
                stringBuilder.append(string2);
                stringBuilder.append(": ");
                stringBuilder.append(map.get(string2));
            } while (true);
            break;
        }
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        ArrayList<zak> arrayList = new ArrayList<zak>();
        Iterator<String> iterator2 = this.zab.keySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                SafeParcelWriter.writeTypedList(parcel, 2, arrayList, false);
                SafeParcelWriter.writeString(parcel, 3, this.zad, false);
                SafeParcelWriter.finishObjectHeader(parcel, n);
                return;
            }
            String string2 = iterator2.next();
            arrayList.add(new zak(string2, this.zab.get(string2)));
        } while (true);
    }

    public final Map<String, FastJsonResponse.Field<?, ?>> zaa(String string2) {
        return this.zab.get(string2);
    }

    public final void zaa() {
        Iterator<String> iterator2 = this.zab.keySet().iterator();
        block0 : while (iterator2.hasNext()) {
            Object object = iterator2.next();
            Map<String, FastJsonResponse.Field<?, ?>> map = this.zab.get(object);
            object = map.keySet().iterator();
            do {
                if (!object.hasNext()) continue block0;
                map.get((String)object.next()).zaa(this);
            } while (true);
            break;
        }
        return;
    }

    public final void zaa(Class<? extends FastJsonResponse> class_, Map<String, FastJsonResponse.Field<?, ?>> map) {
        this.zab.put(Preconditions.checkNotNull(class_.getCanonicalName()), map);
    }

    public final boolean zaa(Class<? extends FastJsonResponse> class_) {
        return this.zab.containsKey(Preconditions.checkNotNull(class_.getCanonicalName()));
    }

    public final void zab() {
        Iterator<String> iterator2 = this.zab.keySet().iterator();
        while (iterator2.hasNext()) {
            String string2 = iterator2.next();
            Map<String, FastJsonResponse.Field<?, ?>> map = this.zab.get(string2);
            HashMap hashMap = new HashMap();
            for (String string3 : map.keySet()) {
                hashMap.put(string3, map.get(string3).zaa());
            }
            this.zab.put(string2, hashMap);
        }
    }

    public final String zac() {
        return this.zad;
    }
}

