/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.base.zaa;
import com.google.android.gms.internal.base.zad;
import com.google.android.gms.signin.internal.zab;
import com.google.android.gms.signin.internal.zac;
import com.google.android.gms.signin.internal.zag;
import com.google.android.gms.signin.internal.zam;

public abstract class zaf
extends zaa
implements zac {
    public zaf() {
        super("com.google.android.gms.signin.internal.ISignInCallbacks");
    }

    /*
     * Unable to fully structure code
     */
    @Override
    protected final boolean zaa(int var1_1, Parcel var2_2, Parcel var3_3, int var4_4) throws RemoteException {
        switch (var1_1) {
            default: {
                return false;
            }
            case 9: {
                this.zaa(zad.zaa(var2_2, zag.CREATOR));
                ** break;
            }
            case 8: {
                this.zaa(zad.zaa(var2_2, zam.CREATOR));
                ** break;
            }
            case 7: {
                this.zaa(zad.zaa(var2_2, Status.CREATOR), zad.zaa(var2_2, GoogleSignInAccount.CREATOR));
                ** break;
            }
            case 6: {
                this.zab(zad.zaa(var2_2, Status.CREATOR));
                ** break;
            }
            case 4: {
                this.zaa(zad.zaa(var2_2, Status.CREATOR));
                ** break;
            }
            case 3: 
        }
        this.zaa(zad.zaa(var2_2, ConnectionResult.CREATOR), zad.zaa(var2_2, zab.CREATOR));
lbl21: // 6 sources:
        var3_3.writeNoException();
        return true;
    }
}

