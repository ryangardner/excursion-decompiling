/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.List;

public final class zab
implements Parcelable.Creator<GoogleSignInAccount> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String string2;
        String string3;
        String string4;
        String string5;
        String string6;
        String string7;
        String string8;
        int n = SafeParcelReader.validateObjectHeader(parcel);
        String string9 = string7 = (string5 = (string2 = (string8 = (string4 = (string3 = null)))));
        Object object = string9;
        String string10 = string6 = object;
        long l = 0L;
        int n2 = 0;
        block14 : do {
            if (parcel.dataPosition() >= n) {
                SafeParcelReader.ensureAtEnd(parcel, n);
                return new GoogleSignInAccount(n2, string3, string4, string8, string2, (Uri)string5, string7, l, string9, (List<Scope>)object, string6, string10);
            }
            int n3 = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(n3)) {
                default: {
                    SafeParcelReader.skipUnknownField(parcel, n3);
                    continue block14;
                }
                case 12: {
                    string10 = SafeParcelReader.createString(parcel, n3);
                    continue block14;
                }
                case 11: {
                    string6 = SafeParcelReader.createString(parcel, n3);
                    continue block14;
                }
                case 10: {
                    object = SafeParcelReader.createTypedList(parcel, n3, Scope.CREATOR);
                    continue block14;
                }
                case 9: {
                    string9 = SafeParcelReader.createString(parcel, n3);
                    continue block14;
                }
                case 8: {
                    l = SafeParcelReader.readLong(parcel, n3);
                    continue block14;
                }
                case 7: {
                    string7 = SafeParcelReader.createString(parcel, n3);
                    continue block14;
                }
                case 6: {
                    string5 = (Uri)SafeParcelReader.createParcelable(parcel, n3, Uri.CREATOR);
                    continue block14;
                }
                case 5: {
                    string2 = SafeParcelReader.createString(parcel, n3);
                    continue block14;
                }
                case 4: {
                    string8 = SafeParcelReader.createString(parcel, n3);
                    continue block14;
                }
                case 3: {
                    string4 = SafeParcelReader.createString(parcel, n3);
                    continue block14;
                }
                case 2: {
                    string3 = SafeParcelReader.createString(parcel, n3);
                    continue block14;
                }
                case 1: 
            }
            n2 = SafeParcelReader.readInt(parcel, n3);
        } while (true);
    }

    public final /* synthetic */ Object[] newArray(int n) {
        return new GoogleSignInAccount[n];
    }
}

