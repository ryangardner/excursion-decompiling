/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseArray
 */
package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.converter.zac;
import com.google.android.gms.common.server.converter.zad;
import com.google.android.gms.common.server.response.FastJsonResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class StringToIntConverter
extends AbstractSafeParcelable
implements FastJsonResponse.FieldConverter<String, Integer> {
    public static final Parcelable.Creator<StringToIntConverter> CREATOR = new zac();
    private final int zaa;
    private final HashMap<String, Integer> zab;
    private final SparseArray<String> zac;
    private final ArrayList<zaa> zad;

    public StringToIntConverter() {
        this.zaa = 1;
        this.zab = new HashMap();
        this.zac = new SparseArray();
        this.zad = null;
    }

    StringToIntConverter(int n, ArrayList<zaa> arrayList) {
        this.zaa = n;
        this.zab = new HashMap();
        this.zac = new SparseArray();
        this.zad = null;
        int n2 = arrayList.size();
        n = 0;
        while (n < n2) {
            zaa zaa2 = arrayList.get(n);
            ++n;
            this.add(zaa2.zaa, zaa2.zab);
        }
    }

    public final StringToIntConverter add(String string2, int n) {
        this.zab.put(string2, n);
        this.zac.put(n, (Object)string2);
        return this;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        ArrayList<zaa> arrayList = new ArrayList<zaa>();
        Iterator<String> iterator2 = this.zab.keySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                SafeParcelWriter.writeTypedList(parcel, 2, arrayList, false);
                SafeParcelWriter.finishObjectHeader(parcel, n);
                return;
            }
            String string2 = iterator2.next();
            arrayList.add(new zaa(string2, this.zab.get(string2)));
        } while (true);
    }

    @Override
    public final int zaa() {
        return 7;
    }

    @Override
    public final /* synthetic */ Object zaa(Object object) {
        object = (Integer)object;
        if ((object = (String)this.zac.get(((Integer)object).intValue())) != null) return object;
        if (!this.zab.containsKey("gms_unknown")) return object;
        return "gms_unknown";
    }

    @Override
    public final int zab() {
        return 0;
    }

    @Override
    public final /* synthetic */ Object zab(Object object) {
        object = (String)object;
        Integer n = this.zab.get(object);
        object = n;
        if (n != null) return object;
        return this.zab.get("gms_unknown");
    }

    public static final class zaa
    extends AbstractSafeParcelable {
        public static final Parcelable.Creator<zaa> CREATOR = new zad();
        final String zaa;
        final int zab;
        private final int zac;

        zaa(int n, String string2, int n2) {
            this.zac = n;
            this.zaa = string2;
            this.zab = n2;
        }

        zaa(String string2, int n) {
            this.zac = 1;
            this.zaa = string2;
            this.zab = n;
        }

        public final void writeToParcel(Parcel parcel, int n) {
            n = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, this.zac);
            SafeParcelWriter.writeString(parcel, 2, this.zaa, false);
            SafeParcelWriter.writeInt(parcel, 3, this.zab);
            SafeParcelWriter.finishObjectHeader(parcel, n);
        }
    }

}

