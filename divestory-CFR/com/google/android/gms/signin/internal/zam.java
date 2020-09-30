/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.signin.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.zas;
import com.google.android.gms.signin.internal.zal;

public final class zam
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zam> CREATOR = new zal();
    private final int zaa;
    private final ConnectionResult zab;
    private final zas zac;

    public zam(int n) {
        this(new ConnectionResult(8, null), null);
    }

    zam(int n, ConnectionResult connectionResult, zas zas2) {
        this.zaa = n;
        this.zab = connectionResult;
        this.zac = zas2;
    }

    private zam(ConnectionResult connectionResult, zas zas2) {
        this(1, connectionResult, null);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zab, n, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zac, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final ConnectionResult zaa() {
        return this.zab;
    }

    public final zas zab() {
        return this.zac;
    }
}

