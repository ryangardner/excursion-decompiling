/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.zza;
import com.google.android.gms.internal.drive.zzb;
import com.google.android.gms.internal.drive.zzc;
import com.google.android.gms.internal.drive.zzem;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzff;
import com.google.android.gms.internal.drive.zzfh;
import com.google.android.gms.internal.drive.zzfj;
import com.google.android.gms.internal.drive.zzfl;
import com.google.android.gms.internal.drive.zzfn;
import com.google.android.gms.internal.drive.zzfr;
import com.google.android.gms.internal.drive.zzft;
import com.google.android.gms.internal.drive.zzfv;
import com.google.android.gms.internal.drive.zzfx;
import com.google.android.gms.internal.drive.zzfy;
import com.google.android.gms.internal.drive.zzga;
import com.google.android.gms.internal.drive.zzgd;
import com.google.android.gms.internal.drive.zzgf;
import com.google.android.gms.internal.drive.zzgh;
import com.google.android.gms.internal.drive.zzgz;
import com.google.android.gms.internal.drive.zzio;
import com.google.android.gms.internal.drive.zzip;

public abstract class zzer
extends zzb
implements zzeq {
    public zzer() {
        super("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
    }

    /*
     * Unable to fully structure code
     */
    @Override
    protected final boolean dispatchTransaction(int var1_1, Parcel var2_2, Parcel var3_3, int var4_4) throws RemoteException {
        switch (var1_1) {
            default: {
                return false;
            }
            case 22: {
                this.zza(zzc.zza(var2_2, zzgf.CREATOR));
                ** break;
            }
            case 21: {
                this.zza(zzc.zza(var2_2, zzgz.CREATOR));
                ** break;
            }
            case 20: {
                this.zza(zzc.zza(var2_2, zzem.CREATOR));
                ** break;
            }
            case 18: {
                this.zza(zzc.zza(var2_2, zzff.CREATOR));
                ** break;
            }
            case 17: {
                this.zza(zzc.zza(var2_2, zza.CREATOR));
                ** break;
            }
            case 16: {
                this.zza(zzc.zza(var2_2, zzfr.CREATOR));
                ** break;
            }
            case 15: {
                this.zzb(zzc.zza(var2_2));
                ** break;
            }
            case 14: {
                this.zza(zzc.zza(var2_2, zzfj.CREATOR));
                ** break;
            }
            case 13: {
                this.zza(zzc.zza(var2_2, zzga.CREATOR));
                ** break;
            }
            case 12: {
                this.zza(zzc.zza(var2_2, zzgd.CREATOR));
                ** break;
            }
            case 11: {
                this.zza(zzc.zza(var2_2, zzfx.CREATOR), zzip.zzb(var2_2.readStrongBinder()));
                ** break;
            }
            case 9: {
                this.zza(zzc.zza(var2_2, zzgh.CREATOR));
                ** break;
            }
            case 8: {
                this.zza(zzc.zza(var2_2, zzfv.CREATOR));
                ** break;
            }
            case 7: {
                this.onSuccess();
                ** break;
            }
            case 6: {
                this.zza(zzc.zza(var2_2, Status.CREATOR));
                ** break;
            }
            case 5: {
                this.zza(zzc.zza(var2_2, zzfh.CREATOR));
                ** break;
            }
            case 4: {
                this.zza(zzc.zza(var2_2, zzfy.CREATOR));
                ** break;
            }
            case 3: {
                this.zza(zzc.zza(var2_2, zzfn.CREATOR));
                ** break;
            }
            case 2: {
                this.zza(zzc.zza(var2_2, zzft.CREATOR));
                ** break;
            }
            case 1: 
        }
        this.zza(zzc.zza(var2_2, zzfl.CREATOR));
lbl63: // 20 sources:
        var3_3.writeNoException();
        return true;
    }
}

