/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.zza;
import com.google.android.gms.drive.zzu;
import com.google.android.gms.internal.drive.zzfg;
import java.util.List;

public final class zzff
extends zzu {
    public static final Parcelable.Creator<zzff> CREATOR = new zzfg();
    private final DataHolder zzhr;
    private final List<DriveId> zzhs;
    private final zza zzht;
    private final boolean zzhu;

    public zzff(DataHolder dataHolder, List<DriveId> list, zza zza2, boolean bl) {
        this.zzhr = dataHolder;
        this.zzhs = list;
        this.zzht = zza2;
        this.zzhu = bl;
    }

    @Override
    protected final void zza(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzhr, n |= 1, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzhs, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzht, n, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzhu);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

