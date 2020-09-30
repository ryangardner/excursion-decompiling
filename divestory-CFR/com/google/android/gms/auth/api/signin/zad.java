/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

public final class zad
implements Parcelable.Creator<GoogleSignInOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2;
        String string3;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        Object object = null;
        String string4 = string3 = (string2 = object);
        Object object2 = string4;
        String string5 = object2;
        int n2 = 0;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        block12 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new GoogleSignInOptions(n2, (ArrayList<Scope>)object, (Account)string2, bl, bl2, bl3, string3, string4, (ArrayList<GoogleSignInOptionsExtensionParcelable>)object2, string5);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n3)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n3);
                    continue block12;
                }
                case 10: {
                    string5 = SafeParcelReader.createString(parcel, n3);
                    continue block12;
                }
                case 9: {
                    object2 = SafeParcelReader.createTypedList(parcel, n3, GoogleSignInOptionsExtensionParcelable.CREATOR);
                    continue block12;
                }
                case 8: {
                    string4 = SafeParcelReader.createString(parcel, n3);
                    continue block12;
                }
                case 7: {
                    string3 = SafeParcelReader.createString(parcel, n3);
                    continue block12;
                }
                case 6: {
                    bl3 = SafeParcelReader.readBoolean(parcel, n3);
                    continue block12;
                }
                case 5: {
                    bl2 = SafeParcelReader.readBoolean(parcel, n3);
                    continue block12;
                }
                case 4: {
                    bl = SafeParcelReader.readBoolean(parcel, n3);
                    continue block12;
                }
                case 3: {
                    string2 = (Account)SafeParcelReader.createParcelable(parcel, n3, Account.CREATOR);
                    continue block12;
                }
                case 2: {
                    object = SafeParcelReader.createTypedList(parcel, n3, Scope.CREATOR);
                    continue block12;
                }
                case 1: 
            }
            n2 = SafeParcelReader.readInt(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new GoogleSignInOptions[n];
    }
}

