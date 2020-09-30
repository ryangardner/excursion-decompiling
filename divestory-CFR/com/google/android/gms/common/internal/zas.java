/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.zav;

public final class zas
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zas> CREATOR = new zav();
    private final int zaa;
    private IBinder zab;
    private ConnectionResult zac;
    private boolean zad;
    private boolean zae;

    zas(int n, IBinder iBinder, ConnectionResult connectionResult, boolean bl, boolean bl2) {
        this.zaa = n;
        this.zab = iBinder;
        this.zac = connectionResult;
        this.zad = bl;
        this.zae = bl2;
    }

    public final boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof zas)) {
            return false;
        }
        object = (zas)object;
        if (!this.zac.equals(((zas)object).zac)) return false;
        if (!Objects.equal(this.zaa(), ((zas)object).zaa())) return false;
        return true;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        SafeParcelWriter.writeIBinder(parcel, 2, this.zab, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zac, n, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zad);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zae);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final IAccountAccessor zaa() {
        IBinder iBinder = this.zab;
        if (iBinder != null) return IAccountAccessor.Stub.asInterface(iBinder);
        return null;
    }

    public final ConnectionResult zab() {
        return this.zac;
    }

    public final boolean zac() {
        return this.zad;
    }

    public final boolean zad() {
        return this.zae;
    }
}

