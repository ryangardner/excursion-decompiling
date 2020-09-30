/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.events;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.zzp;
import com.google.android.gms.drive.zzu;

public final class zzo
extends zzu
implements DriveEvent {
    public static final Parcelable.Creator<zzo> CREATOR = new zzp();
    private final DataHolder zzav;
    private final boolean zzcq;
    private final int zzcr;

    public zzo(DataHolder dataHolder, boolean bl, int n) {
        this.zzav = dataHolder;
        this.zzcq = bl;
        this.zzcr = n;
    }

    @Override
    public final int getType() {
        return 3;
    }

    @Override
    public final void zza(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzav, n, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzcq);
        SafeParcelWriter.writeInt(parcel, 4, this.zzcr);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }

    public final boolean zzaa() {
        return this.zzcq;
    }

    public final int zzab() {
        return this.zzcr;
    }

    public final DataHolder zzz() {
        return this.zzav;
    }
}

