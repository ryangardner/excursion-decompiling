/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.GetServiceRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zze
implements Parcelable.Creator<GetServiceRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Object object;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        Scope[] arrscope = object = null;
        Scope[] arrscope2 = arrscope;
        Scope[] arrscope3 = arrscope2;
        Scope[] arrscope4 = arrscope3;
        Scope[] arrscope5 = arrscope4;
        Scope[] arrscope6 = arrscope5;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        boolean bl = false;
        int n5 = 0;
        boolean bl2 = false;
        block15 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new GetServiceRequest(n2, n3, n4, (String)object, (IBinder)arrscope, arrscope2, (Bundle)arrscope3, (Account)arrscope4, (Feature[])arrscope5, (Feature[])arrscope6, bl, n5, bl2);
            }
            int n6 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n6)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n6);
                    continue block15;
                }
                case 14: {
                    bl2 = SafeParcelReader.readBoolean(parcel, n6);
                    continue block15;
                }
                case 13: {
                    n5 = SafeParcelReader.readInt(parcel, n6);
                    continue block15;
                }
                case 12: {
                    bl = SafeParcelReader.readBoolean(parcel, n6);
                    continue block15;
                }
                case 11: {
                    arrscope6 = SafeParcelReader.createTypedArray(parcel, n6, Feature.CREATOR);
                    continue block15;
                }
                case 10: {
                    arrscope5 = SafeParcelReader.createTypedArray(parcel, n6, Feature.CREATOR);
                    continue block15;
                }
                case 8: {
                    arrscope4 = (Scope[])SafeParcelReader.createParcelable(parcel, n6, Account.CREATOR);
                    continue block15;
                }
                case 7: {
                    arrscope3 = SafeParcelReader.createBundle(parcel, n6);
                    continue block15;
                }
                case 6: {
                    arrscope2 = SafeParcelReader.createTypedArray(parcel, n6, Scope.CREATOR);
                    continue block15;
                }
                case 5: {
                    arrscope = SafeParcelReader.readIBinder(parcel, n6);
                    continue block15;
                }
                case 4: {
                    object = SafeParcelReader.createString(parcel, n6);
                    continue block15;
                }
                case 3: {
                    n4 = SafeParcelReader.readInt(parcel, n6);
                    continue block15;
                }
                case 2: {
                    n3 = SafeParcelReader.readInt(parcel, n6);
                    continue block15;
                }
                case 1: 
            }
            n2 = SafeParcelReader.readInt(parcel, n6);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new GetServiceRequest[n];
    }
}

