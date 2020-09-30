/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.signin.internal.zaa;

public final class zab
extends AbstractSafeParcelable
implements Result {
    public static final Parcelable.Creator<zab> CREATOR = new zaa();
    private final int zaa;
    private int zab;
    private Intent zac;

    public zab() {
        this(0, null);
    }

    zab(int n, int n2, Intent intent) {
        this.zaa = n;
        this.zab = n2;
        this.zac = intent;
    }

    private zab(int n, Intent intent) {
        this(2, 0, null);
    }

    @Override
    public final Status getStatus() {
        if (this.zab != 0) return Status.RESULT_CANCELED;
        return Status.RESULT_SUCCESS;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        SafeParcelWriter.writeInt(parcel, 2, this.zab);
        SafeParcelWriter.writeParcelable(parcel, 3, (Parcelable)this.zac, n, false);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

