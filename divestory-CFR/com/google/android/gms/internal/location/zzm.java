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
import com.google.android.gms.internal.location.zzn;
import com.google.android.gms.location.zzj;
import java.util.Collections;
import java.util.List;

public final class zzm
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzm> CREATOR;
    static final List<ClientIdentity> zzcd;
    static final zzj zzce;
    private String tag;
    private zzj zzcf;
    private List<ClientIdentity> zzm;

    static {
        zzcd = Collections.emptyList();
        zzce = new zzj();
        CREATOR = new zzn();
    }

    zzm(zzj zzj2, List<ClientIdentity> list, String string2) {
        this.zzcf = zzj2;
        this.zzm = list;
        this.tag = string2;
    }

    public final boolean equals(Object object) {
        if (!(object instanceof zzm)) {
            return false;
        }
        object = (zzm)object;
        if (!Objects.equal(this.zzcf, ((zzm)object).zzcf)) return false;
        if (!Objects.equal(this.zzm, ((zzm)object).zzm)) return false;
        if (!Objects.equal(this.tag, ((zzm)object).tag)) return false;
        return true;
    }

    public final int hashCode() {
        return this.zzcf.hashCode();
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzcf, n, false);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzm, false);
        SafeParcelWriter.writeString(parcel, 3, this.tag, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

