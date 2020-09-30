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
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzn;

public final class zzm
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzm> CREATOR = new zzn();
    private final String zzan;
    private final boolean zzao;
    private final boolean zzat;
    private final DriveId zzdd;
    private final MetadataBundle zzde;
    private final Contents zzdf;
    private final int zzdg;
    private final int zzdh;
    private final boolean zzdi;

    public zzm(DriveId driveId, MetadataBundle metadataBundle, int n, boolean bl, com.google.android.gms.drive.zzn zzn2) {
        this(driveId, metadataBundle, null, zzn2.zzm(), zzn2.zzl(), zzn2.zzn(), n, bl, zzn2.zzp());
    }

    zzm(DriveId driveId, MetadataBundle metadataBundle, Contents contents, boolean bl, String string2, int n, int n2, boolean bl2, boolean bl3) {
        this.zzdd = driveId;
        this.zzde = metadataBundle;
        this.zzdf = contents;
        this.zzao = bl;
        this.zzan = string2;
        this.zzdg = n;
        this.zzdh = n2;
        this.zzdi = bl2;
        this.zzat = bl3;
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdd, n, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzde, n, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzdf, n, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzao);
        SafeParcelWriter.writeString(parcel, 6, this.zzan, false);
        SafeParcelWriter.writeInt(parcel, 7, this.zzdg);
        SafeParcelWriter.writeInt(parcel, 8, this.zzdh);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzdi);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzat);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

