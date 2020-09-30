/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.drive.query.internal.zza;
import com.google.android.gms.drive.query.internal.zzj;
import com.google.android.gms.drive.query.internal.zzs;
import com.google.android.gms.drive.query.internal.zzx;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class zzr
extends zza {
    public static final Parcelable.Creator<zzr> CREATOR = new zzs();
    private List<Filter> zzls;
    private final zzx zzlz;
    private final List<FilterHolder> zzmo;

    public zzr(zzx object, Filter filter, Filter ... arrfilter) {
        this.zzlz = object;
        this.zzmo = object = new ArrayList(arrfilter.length + 1);
        object.add(new FilterHolder(filter));
        object = new ArrayList<Filter>(arrfilter.length + 1);
        this.zzls = object;
        object.add((Filter)filter);
        int n = arrfilter.length;
        int n2 = 0;
        while (n2 < n) {
            object = arrfilter[n2];
            this.zzmo.add(new FilterHolder((Filter)object));
            this.zzls.add((Filter)object);
            ++n2;
        }
    }

    public zzr(zzx object, Iterable<Filter> object2) {
        this.zzlz = object;
        this.zzls = new ArrayList<Filter>();
        this.zzmo = new ArrayList<FilterHolder>();
        object = object2.iterator();
        while (object.hasNext()) {
            object2 = (Filter)object.next();
            this.zzls.add((Filter)object2);
            this.zzmo.add(new FilterHolder((Filter)object2));
        }
    }

    zzr(zzx zzx2, List<FilterHolder> list) {
        this.zzlz = zzx2;
        this.zzmo = list;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzlz, n, false);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzmo, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    @Override
    public final <T> T zza(zzj<T> zzj2) {
        ArrayList<T> arrayList = new ArrayList<T>();
        Iterator<FilterHolder> iterator2 = this.zzmo.iterator();
        while (iterator2.hasNext()) {
            arrayList.add(iterator2.next().getFilter().zza(zzj2));
        }
        return zzj2.zza(this.zzlz, arrayList);
    }
}

