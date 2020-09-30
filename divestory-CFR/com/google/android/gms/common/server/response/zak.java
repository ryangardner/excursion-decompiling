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
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.zan;
import com.google.android.gms.common.server.response.zao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zak
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zak> CREATOR = new zao();
    final String zaa;
    final ArrayList<zan> zab;
    private final int zac;

    zak(int n, String string2, ArrayList<zan> arrayList) {
        this.zac = n;
        this.zaa = string2;
        this.zab = arrayList;
    }

    zak(String object, Map<String, FastJsonResponse.Field<?, ?>> map) {
        this.zac = 1;
        this.zaa = object;
        if (map == null) {
            object = null;
        } else {
            ArrayList<zan> arrayList = new ArrayList<zan>();
            Iterator<String> iterator2 = map.keySet().iterator();
            do {
                object = arrayList;
                if (!iterator2.hasNext()) break;
                object = iterator2.next();
                arrayList.add(new zan((String)object, map.get(object)));
            } while (true);
        }
        this.zab = object;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zac);
        SafeParcelWriter.writeString(parcel, 2, this.zaa, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zab, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

