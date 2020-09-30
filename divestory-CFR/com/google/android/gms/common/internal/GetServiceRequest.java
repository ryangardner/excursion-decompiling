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
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.AccountAccessor;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.zze;

public class GetServiceRequest
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GetServiceRequest> CREATOR = new zze();
    String zza;
    IBinder zzb;
    Scope[] zzc;
    Bundle zzd;
    Account zze;
    Feature[] zzf;
    Feature[] zzg;
    boolean zzh;
    private final int zzi;
    private final int zzj;
    private int zzk;
    private boolean zzl;
    private int zzm;

    public GetServiceRequest(int n) {
        this.zzi = 5;
        this.zzk = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzj = n;
        this.zzl = true;
    }

    GetServiceRequest(int n, int n2, int n3, String string2, IBinder iBinder, Scope[] arrscope, Bundle bundle, Account account, Feature[] arrfeature, Feature[] arrfeature2, boolean bl, int n4, boolean bl2) {
        this.zzi = n;
        this.zzj = n2;
        this.zzk = n3;
        this.zza = "com.google.android.gms".equals(string2) ? "com.google.android.gms" : string2;
        if (n < 2) {
            string2 = null;
            if (iBinder != null) {
                string2 = AccountAccessor.getAccountBinderSafe(IAccountAccessor.Stub.asInterface(iBinder));
            }
            this.zze = string2;
        } else {
            this.zzb = iBinder;
            this.zze = account;
        }
        this.zzc = arrscope;
        this.zzd = bundle;
        this.zzf = arrfeature;
        this.zzg = arrfeature2;
        this.zzl = bl;
        this.zzm = n4;
        this.zzh = bl2;
    }

    public Bundle getExtraArgs() {
        return this.zzd;
    }

    public void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzi);
        SafeParcelWriter.writeInt(parcel, 2, this.zzj);
        SafeParcelWriter.writeInt(parcel, 3, this.zzk);
        SafeParcelWriter.writeString(parcel, 4, this.zza, false);
        SafeParcelWriter.writeIBinder(parcel, 5, this.zzb, false);
        SafeParcelWriter.writeTypedArray((Parcel)parcel, (int)6, (Parcelable[])this.zzc, (int)n, (boolean)false);
        SafeParcelWriter.writeBundle(parcel, 7, this.zzd, false);
        SafeParcelWriter.writeParcelable(parcel, 8, (Parcelable)this.zze, n, false);
        SafeParcelWriter.writeTypedArray((Parcel)parcel, (int)10, (Parcelable[])this.zzf, (int)n, (boolean)false);
        SafeParcelWriter.writeTypedArray((Parcel)parcel, (int)11, (Parcelable[])this.zzg, (int)n, (boolean)false);
        SafeParcelWriter.writeBoolean(parcel, 12, this.zzl);
        SafeParcelWriter.writeInt(parcel, 13, this.zzm);
        SafeParcelWriter.writeBoolean(parcel, 14, this.zzh);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

