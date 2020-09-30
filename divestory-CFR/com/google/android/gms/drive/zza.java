/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Base64
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.zzb;
import com.google.android.gms.internal.drive.zzez;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzlq;

public class zza
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zza> CREATOR = new zzb();
    private final long zze;
    private final long zzf;
    private final long zzg;
    private volatile String zzh = null;

    public zza(long l, long l2, long l3) {
        boolean bl = true;
        boolean bl2 = l != -1L;
        Preconditions.checkArgument(bl2);
        bl2 = l2 != -1L;
        Preconditions.checkArgument(bl2);
        bl2 = l3 != -1L ? bl : false;
        Preconditions.checkArgument(bl2);
        this.zze = l;
        this.zzf = l2;
        this.zzg = l3;
    }

    public boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != zza.class) {
            return false;
        }
        object = (zza)object;
        if (((zza)object).zzf != this.zzf) return false;
        if (((zza)object).zzg != this.zzg) return false;
        if (((zza)object).zze != this.zze) return false;
        return true;
    }

    public int hashCode() {
        String string2 = String.valueOf(this.zze);
        String string3 = String.valueOf(this.zzf);
        String string4 = String.valueOf(this.zzg);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + String.valueOf(string3).length() + String.valueOf(string4).length());
        stringBuilder.append(string2);
        stringBuilder.append(string3);
        stringBuilder.append(string4);
        return stringBuilder.toString().hashCode();
    }

    public String toString() {
        if (this.zzh != null) return this.zzh;
        String string2 = String.valueOf(Base64.encodeToString((byte[])((zzez)((zzkk)zzez.zzaj().zzk(1).zzc(this.zze).zzd(this.zzf).zze(this.zzg).zzdf())).toByteArray(), (int)10));
        string2 = string2.length() != 0 ? "ChangeSequenceNumber:".concat(string2) : new String("ChangeSequenceNumber:");
        this.zzh = string2;
        return this.zzh;
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 2, this.zze);
        SafeParcelWriter.writeLong(parcel, 3, this.zzf);
        SafeParcelWriter.writeLong(parcel, 4, this.zzg);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

