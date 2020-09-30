/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.zar;

public final class zat
implements Parcelable.Creator<zar> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int n = SafeParcelReader.validateObjectHeader(parcel);
        Account account = null;
        GoogleSignInAccount googleSignInAccount = null;
        int n2 = 0;
        int n3 = 0;
        do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new zar(n2, account, n3, googleSignInAccount);
            }
            int n4 = SafeParcelReader.readHeader(parcel);
            int n5 = SafeParcelReader.getFieldId(n4);
            if (n5 != 1) {
                if (n5 != 2) {
                    if (n5 != 3) {
                        if (n5 != 4) {
                            SafeParcelReader.skipUnknownField(parcel, n4);
                            continue;
                        }
                        googleSignInAccount = SafeParcelReader.createParcelable(parcel, n4, GoogleSignInAccount.CREATOR);
                        continue;
                    }
                    n3 = SafeParcelReader.readInt(parcel, n4);
                    continue;
                }
                account = (Account)SafeParcelReader.createParcelable(parcel, n4, Account.CREATOR);
                continue;
            }
            n2 = SafeParcelReader.readInt(parcel, n4);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new zar[n];
    }
}

