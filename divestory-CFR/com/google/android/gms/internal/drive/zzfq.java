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
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.zzb;
import com.google.android.gms.drive.events.zzo;
import com.google.android.gms.drive.events.zzr;
import com.google.android.gms.drive.events.zzv;
import com.google.android.gms.internal.drive.zzfp;

public final class zzfq
implements Parcelable.Creator<zzfp> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        AbstractSafeParcelable abstractSafeParcelable;
        AbstractSafeParcelable abstractSafeParcelable2;
        AbstractSafeParcelable abstractSafeParcelable3;
        AbstractSafeParcelable abstractSafeParcelable4;
        ChangeEvent changeEvent;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        AbstractSafeParcelable abstractSafeParcelable5 = abstractSafeParcelable2 = (abstractSafeParcelable3 = (abstractSafeParcelable4 = (abstractSafeParcelable = (changeEvent = null))));
        int n2 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zzfp(n2, changeEvent, (CompletionEvent)abstractSafeParcelable, (zzo)abstractSafeParcelable4, (zzb)abstractSafeParcelable3, (zzv)abstractSafeParcelable2, (zzr)abstractSafeParcelable5);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            int n4 = SafeParcelReader.getFieldId(n3);
            if (n4 != 2) {
                if (n4 != 3) {
                    if (n4 != 5) {
                        if (n4 != 6) {
                            if (n4 != 7) {
                                if (n4 != 9) {
                                    if (n4 != 10) {
                                        SafeParcelReader.skipUnknownField(parcel, n3);
                                        continue;
                                    }
                                    abstractSafeParcelable5 = SafeParcelReader.createParcelable(parcel, n3, zzr.CREATOR);
                                    continue;
                                }
                                abstractSafeParcelable2 = SafeParcelReader.createParcelable(parcel, n3, zzv.CREATOR);
                                continue;
                            }
                            abstractSafeParcelable3 = SafeParcelReader.createParcelable(parcel, n3, zzb.CREATOR);
                            continue;
                        }
                        abstractSafeParcelable4 = SafeParcelReader.createParcelable(parcel, n3, zzo.CREATOR);
                        continue;
                    }
                    abstractSafeParcelable = SafeParcelReader.createParcelable(parcel, n3, CompletionEvent.CREATOR);
                    continue;
                }
                changeEvent = SafeParcelReader.createParcelable(parcel, n3, ChangeEvent.CREATOR);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zzfp[n];
    }
}

