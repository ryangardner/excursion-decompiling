/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.internal.location.zzbe;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public final class zzbd
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzbd> CREATOR;
    static final List<ClientIdentity> zzcd;
    private String moduleId;
    private String tag;
    private LocationRequest zzdg;
    private boolean zzdh;
    private boolean zzdi;
    private boolean zzdj;
    private boolean zzdk = true;
    private List<ClientIdentity> zzm;

    static {
        zzcd = Collections.emptyList();
        CREATOR = new zzbe();
    }

    zzbd(LocationRequest locationRequest, List<ClientIdentity> list, String string2, boolean bl, boolean bl2, boolean bl3, String string3) {
        this.zzdg = locationRequest;
        this.zzm = list;
        this.tag = string2;
        this.zzdh = bl;
        this.zzdi = bl2;
        this.zzdj = bl3;
        this.moduleId = string3;
    }

    @Deprecated
    public static zzbd zza(LocationRequest locationRequest) {
        return new zzbd(locationRequest, zzcd, null, false, false, false, null);
    }

    public final boolean equals(Object object) {
        if (!(object instanceof zzbd)) {
            return false;
        }
        object = (zzbd)object;
        if (!Objects.equal(this.zzdg, ((zzbd)object).zzdg)) return false;
        if (!Objects.equal(this.zzm, ((zzbd)object).zzm)) return false;
        if (!Objects.equal(this.tag, ((zzbd)object).tag)) return false;
        if (this.zzdh != ((zzbd)object).zzdh) return false;
        if (this.zzdi != ((zzbd)object).zzdi) return false;
        if (this.zzdj != ((zzbd)object).zzdj) return false;
        if (!Objects.equal(this.moduleId, ((zzbd)object).moduleId)) return false;
        return true;
    }

    public final int hashCode() {
        return this.zzdg.hashCode();
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.zzdg);
        if (this.tag != null) {
            stringBuilder.append(" tag=");
            stringBuilder.append(this.tag);
        }
        if (this.moduleId != null) {
            stringBuilder.append(" moduleId=");
            stringBuilder.append(this.moduleId);
        }
        stringBuilder.append(" hideAppOps=");
        stringBuilder.append(this.zzdh);
        stringBuilder.append(" clients=");
        stringBuilder.append(this.zzm);
        stringBuilder.append(" forceCoarseLocation=");
        stringBuilder.append(this.zzdi);
        if (!this.zzdj) return stringBuilder.toString();
        stringBuilder.append(" exemptFromBackgroundThrottle");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzdg, n, false);
        SafeParcelWriter.writeTypedList(parcel, 5, this.zzm, false);
        SafeParcelWriter.writeString(parcel, 6, this.tag, false);
        SafeParcelWriter.writeBoolean(parcel, 7, this.zzdh);
        SafeParcelWriter.writeBoolean(parcel, 8, this.zzdi);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzdj);
        SafeParcelWriter.writeString(parcel, 10, this.moduleId, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

