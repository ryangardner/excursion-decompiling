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
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.zzh;
import com.google.android.gms.internal.drive.zzfm;
import java.util.Collections;
import java.util.List;

public final class zzfl
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzfl> CREATOR;
    private static final List<zzh> zzhx;
    private final int status;
    final long zzhy;
    final long zzhz;
    private final List<zzh> zzia;

    static {
        zzhx = Collections.emptyList();
        CREATOR = new zzfm();
    }

    public zzfl(long l, long l2, int n, List<zzh> list) {
        this.zzhy = l;
        this.zzhz = l2;
        this.status = n;
        this.zzia = list;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 2, this.zzhy);
        SafeParcelWriter.writeLong(parcel, 3, this.zzhz);
        SafeParcelWriter.writeInt(parcel, 4, this.status);
        SafeParcelWriter.writeTypedList(parcel, 5, this.zzia, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

