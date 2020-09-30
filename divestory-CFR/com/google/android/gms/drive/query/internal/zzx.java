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
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.internal.zzy;

public final class zzx
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzx> CREATOR = new zzy();
    public static final zzx zzmq = new zzx("=");
    public static final zzx zzmr = new zzx("<");
    public static final zzx zzms = new zzx("<=");
    public static final zzx zzmt = new zzx(">");
    public static final zzx zzmu = new zzx(">=");
    public static final zzx zzmv = new zzx("and");
    public static final zzx zzmw = new zzx("or");
    private static final zzx zzmx = new zzx("not");
    public static final zzx zzmy = new zzx("contains");
    private final String tag;

    zzx(String string2) {
        this.tag = string2;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        zzx zzx2 = (zzx)object;
        object = this.tag;
        if (object == null) {
            if (zzx2.tag == null) return true;
            return false;
        }
        if (((String)object).equals(zzx2.tag)) return true;
        return false;
    }

    public final String getTag() {
        return this.tag;
    }

    public final int hashCode() {
        int n;
        String string2 = this.tag;
        if (string2 == null) {
            n = 0;
            return n + 31;
        }
        n = string2.hashCode();
        return n + 31;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.tag, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

