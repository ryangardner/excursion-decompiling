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
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.drive.query.internal.zza;
import com.google.android.gms.drive.query.internal.zzb;
import com.google.android.gms.drive.query.internal.zzc;
import com.google.android.gms.drive.query.internal.zzd;
import com.google.android.gms.drive.query.internal.zzl;
import com.google.android.gms.drive.query.internal.zzn;
import com.google.android.gms.drive.query.internal.zzo;
import com.google.android.gms.drive.query.internal.zzp;
import com.google.android.gms.drive.query.internal.zzq;
import com.google.android.gms.drive.query.internal.zzr;
import com.google.android.gms.drive.query.internal.zzt;
import com.google.android.gms.drive.query.internal.zzv;
import com.google.android.gms.drive.query.internal.zzz;

public final class zzh
implements Parcelable.Creator<FilterHolder> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        zza zza2;
        zza zza3;
        zza zza4;
        zza zza5;
        zza zza6;
        zza zza7;
        zza zza8;
        zza zza9;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        zzz zzz2 = zza7 = (zza3 = (zza9 = (zza4 = (zza8 = (zza2 = (zza6 = (zza5 = null)))))));
        block11 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new FilterHolder((zzb<?>)zza5, (zzd)zza6, (zzr)zza2, (zzv)zza8, (zzp<?>)zza4, (zzt)zza9, (zzn<?>)zza3, (zzl)zza7, zzz2);
            }
            int n2 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n2)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n2);
                    continue block11;
                }
                case 9: {
                    zzz2 = SafeParcelReader.createParcelable(parcel, n2, zzz.CREATOR);
                    continue block11;
                }
                case 8: {
                    zza7 = SafeParcelReader.createParcelable(parcel, n2, zzl.CREATOR);
                    continue block11;
                }
                case 7: {
                    zza3 = SafeParcelReader.createParcelable(parcel, n2, zzn.CREATOR);
                    continue block11;
                }
                case 6: {
                    zza9 = SafeParcelReader.createParcelable(parcel, n2, zzt.CREATOR);
                    continue block11;
                }
                case 5: {
                    zza4 = SafeParcelReader.createParcelable(parcel, n2, zzp.CREATOR);
                    continue block11;
                }
                case 4: {
                    zza8 = SafeParcelReader.createParcelable(parcel, n2, zzv.CREATOR);
                    continue block11;
                }
                case 3: {
                    zza2 = SafeParcelReader.createParcelable(parcel, n2, zzr.CREATOR);
                    continue block11;
                }
                case 2: {
                    zza6 = SafeParcelReader.createParcelable(parcel, n2, zzd.CREATOR);
                    continue block11;
                }
                case 1: 
            }
            zza5 = SafeParcelReader.createParcelable(parcel, n2, zzb.CREATOR);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new FilterHolder[n];
    }
}

