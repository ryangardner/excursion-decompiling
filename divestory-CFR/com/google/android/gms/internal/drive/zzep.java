/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.IntentSender
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.content.IntentSender;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.drive.zza;
import com.google.android.gms.internal.drive.zzab;
import com.google.android.gms.internal.drive.zzad;
import com.google.android.gms.internal.drive.zzc;
import com.google.android.gms.internal.drive.zzec;
import com.google.android.gms.internal.drive.zzek;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzes;
import com.google.android.gms.internal.drive.zzex;
import com.google.android.gms.internal.drive.zzgj;
import com.google.android.gms.internal.drive.zzgm;
import com.google.android.gms.internal.drive.zzgq;
import com.google.android.gms.internal.drive.zzgs;
import com.google.android.gms.internal.drive.zzgu;
import com.google.android.gms.internal.drive.zzgw;
import com.google.android.gms.internal.drive.zzhb;
import com.google.android.gms.internal.drive.zzhd;
import com.google.android.gms.internal.drive.zzhf;
import com.google.android.gms.internal.drive.zzj;
import com.google.android.gms.internal.drive.zzm;
import com.google.android.gms.internal.drive.zzo;
import com.google.android.gms.internal.drive.zzr;
import com.google.android.gms.internal.drive.zzu;
import com.google.android.gms.internal.drive.zzw;
import com.google.android.gms.internal.drive.zzy;

public final class zzep
extends zza
implements zzeo {
    zzep(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.drive.internal.IDriveService");
    }

    @Override
    public final IntentSender zza(zzgm zzgm2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzgm2);
        parcel = this.zza(10, parcel);
        zzgm2 = (IntentSender)zzc.zza(parcel, IntentSender.CREATOR);
        parcel.recycle();
        return zzgm2;
    }

    @Override
    public final IntentSender zza(zzu zzu2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzu2);
        parcel = this.zza(11, parcel);
        zzu2 = (IntentSender)zzc.zza(parcel, IntentSender.CREATOR);
        parcel.recycle();
        return zzu2;
    }

    @Override
    public final zzec zza(zzgj zzgj2, zzeq object) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzgj2);
        zzc.zza(parcel, (IInterface)object);
        zzgj2 = this.zza(7, parcel);
        object = zzc.zza((Parcel)zzgj2, zzec.CREATOR);
        zzgj2.recycle();
        return object;
    }

    @Override
    public final void zza(zzab zzab2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzab2);
        zzc.zza(parcel, zzeq2);
        this.zzb(24, parcel);
    }

    @Override
    public final void zza(zzad zzad2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzad2);
        this.zzb(16, parcel);
    }

    @Override
    public final void zza(zzek zzek2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzek2);
        zzc.zza(parcel, zzeq2);
        this.zzb(1, parcel);
    }

    @Override
    public final void zza(zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzeq2);
        this.zzb(9, parcel);
    }

    @Override
    public final void zza(zzex zzex2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzex2);
        zzc.zza(parcel, zzeq2);
        this.zzb(13, parcel);
    }

    @Override
    public final void zza(zzgq zzgq2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzgq2);
        zzc.zza(parcel, zzeq2);
        this.zzb(2, parcel);
    }

    @Override
    public final void zza(zzgs zzgs2, zzes zzes2, String string2, zzeq zzeq2) throws RemoteException {
        string2 = this.zza();
        zzc.zza((Parcel)string2, zzgs2);
        zzc.zza((Parcel)string2, zzes2);
        string2.writeString(null);
        zzc.zza((Parcel)string2, zzeq2);
        this.zzb(15, (Parcel)string2);
    }

    @Override
    public final void zza(zzgu zzgu2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzgu2);
        zzc.zza(parcel, zzeq2);
        this.zzb(36, parcel);
    }

    @Override
    public final void zza(zzgw zzgw2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzgw2);
        zzc.zza(parcel, zzeq2);
        this.zzb(28, parcel);
    }

    @Override
    public final void zza(zzhb zzhb2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzhb2);
        zzc.zza(parcel, zzeq2);
        this.zzb(17, parcel);
    }

    @Override
    public final void zza(zzhd zzhd2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzhd2);
        zzc.zza(parcel, zzeq2);
        this.zzb(38, parcel);
    }

    @Override
    public final void zza(zzhf zzhf2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzhf2);
        zzc.zza(parcel, zzeq2);
        this.zzb(3, parcel);
    }

    @Override
    public final void zza(zzj zzj2, zzes zzes2, String string2, zzeq zzeq2) throws RemoteException {
        string2 = this.zza();
        zzc.zza((Parcel)string2, zzj2);
        zzc.zza((Parcel)string2, zzes2);
        string2.writeString(null);
        zzc.zza((Parcel)string2, zzeq2);
        this.zzb(14, (Parcel)string2);
    }

    @Override
    public final void zza(zzm zzm2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzm2);
        zzc.zza(parcel, zzeq2);
        this.zzb(18, parcel);
    }

    @Override
    public final void zza(zzo zzo2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzo2);
        zzc.zza(parcel, zzeq2);
        this.zzb(8, parcel);
    }

    @Override
    public final void zza(zzr zzr2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzr2);
        zzc.zza(parcel, zzeq2);
        this.zzb(4, parcel);
    }

    @Override
    public final void zza(zzw zzw2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzw2);
        zzc.zza(parcel, zzeq2);
        this.zzb(5, parcel);
    }

    @Override
    public final void zza(zzy zzy2, zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzy2);
        zzc.zza(parcel, zzeq2);
        this.zzb(6, parcel);
    }

    @Override
    public final void zzb(zzeq zzeq2) throws RemoteException {
        Parcel parcel = this.zza();
        zzc.zza(parcel, zzeq2);
        this.zzb(35, parcel);
    }
}

