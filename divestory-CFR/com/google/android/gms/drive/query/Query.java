/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveSpace;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.internal.zzr;
import com.google.android.gms.drive.query.internal.zzt;
import com.google.android.gms.drive.query.internal.zzx;
import com.google.android.gms.drive.query.zza;
import com.google.android.gms.drive.query.zzb;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Query
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<Query> CREATOR = new zzb();
    private final List<DriveSpace> zzby;
    private final zzr zzlm;
    private final String zzln;
    private final SortOrder zzlo;
    final List<String> zzlp;
    final boolean zzlq;
    final boolean zzlr;

    Query(zzr zzr2, String string2, SortOrder sortOrder, List<String> list, boolean bl, List<DriveSpace> list2, boolean bl2) {
        this.zzlm = zzr2;
        this.zzln = string2;
        this.zzlo = sortOrder;
        this.zzlp = list;
        this.zzlq = bl;
        this.zzby = list2;
        this.zzlr = bl2;
    }

    private Query(zzr zzr2, String string2, SortOrder sortOrder, List<String> list, boolean bl, Set<DriveSpace> set, boolean bl2) {
        this(zzr2, string2, sortOrder, list, bl, new ArrayList<DriveSpace>(set), bl2);
    }

    /* synthetic */ Query(zzr zzr2, String string2, SortOrder sortOrder, List list, boolean bl, Set set, boolean bl2, zza zza2) {
        this(zzr2, string2, sortOrder, (List<String>)list, bl, set, bl2);
    }

    public Filter getFilter() {
        return this.zzlm;
    }

    @Deprecated
    public String getPageToken() {
        return this.zzln;
    }

    public SortOrder getSortOrder() {
        return this.zzlo;
    }

    public String toString() {
        return String.format(Locale.US, "Query[%s,%s,PageToken=%s,Spaces=%s]", this.zzlm, this.zzlo, this.zzln, this.zzby);
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzlm, n, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzln, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzlo, n, false);
        SafeParcelWriter.writeStringList(parcel, 5, this.zzlp, false);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzlq);
        SafeParcelWriter.writeTypedList(parcel, 7, this.zzby, false);
        SafeParcelWriter.writeBoolean(parcel, 8, this.zzlr);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final Set<DriveSpace> zzbi() {
        if (this.zzby != null) return new HashSet<DriveSpace>(this.zzby);
        return new HashSet<DriveSpace>();
    }

    public static class Builder {
        private String zzln;
        private SortOrder zzlo;
        private List<String> zzlp = Collections.emptyList();
        private boolean zzlq;
        private boolean zzlr;
        private final List<Filter> zzls = new ArrayList<Filter>();
        private Set<DriveSpace> zzlt = Collections.emptySet();

        public Builder() {
        }

        public Builder(Query query) {
            this.zzls.add(query.getFilter());
            this.zzln = query.getPageToken();
            this.zzlo = query.getSortOrder();
            this.zzlp = query.zzlp;
            this.zzlq = query.zzlq;
            query.zzbi();
            this.zzlt = query.zzbi();
            this.zzlr = query.zzlr;
        }

        public Builder addFilter(Filter filter) {
            Preconditions.checkNotNull(filter, "Filter may not be null.");
            if (filter instanceof zzt) return this;
            this.zzls.add(filter);
            return this;
        }

        public Query build() {
            return new Query(new zzr(zzx.zzmv, (Iterable<Filter>)this.zzls), this.zzln, this.zzlo, this.zzlp, this.zzlq, this.zzlt, this.zzlr, null);
        }

        @Deprecated
        public Builder setPageToken(String string2) {
            this.zzln = string2;
            return this;
        }

        public Builder setSortOrder(SortOrder sortOrder) {
            this.zzlo = sortOrder;
            return this;
        }
    }

}

