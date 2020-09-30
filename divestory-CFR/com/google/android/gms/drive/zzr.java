/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.zzs;

public final class zzr
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzr> CREATOR = new zzs();
    private int accountType;
    private String zzbg;
    private String zzbh;
    private String zzbi;
    private int zzbj;
    private boolean zzbk;

    public zzr(String string2, int n, String string3, String string4, int n2, boolean bl) {
        this.zzbg = string2;
        this.accountType = n;
        this.zzbh = string3;
        this.zzbi = string4;
        this.zzbj = n2;
        this.zzbk = bl;
    }

    private static boolean zzb(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 256: 
            case 257: 
            case 258: 
        }
        return true;
    }

    public final boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        if (object == this) {
            return true;
        }
        object = (zzr)object;
        if (!Objects.equal(this.zzbg, ((zzr)object).zzbg)) return false;
        if (this.accountType != ((zzr)object).accountType) return false;
        if (this.zzbj != ((zzr)object).zzbj) return false;
        if (this.zzbk != ((zzr)object).zzbk) return false;
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzbg, this.accountType, this.zzbj, this.zzbk);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        String string2 = !zzr.zzb(this.accountType) ? null : this.zzbg;
        int n3 = 0;
        SafeParcelWriter.writeString(parcel, 2, string2, false);
        boolean bl = zzr.zzb(this.accountType);
        int n4 = -1;
        n = !bl ? -1 : this.accountType;
        SafeParcelWriter.writeInt(parcel, 3, n);
        SafeParcelWriter.writeString(parcel, 4, this.zzbh, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzbi, false);
        n = this.zzbj;
        n = n != 0 && n != 1 && n != 2 && n != 3 ? n3 : 1;
        n = n == 0 ? n4 : this.zzbj;
        SafeParcelWriter.writeInt(parcel, 6, n);
        SafeParcelWriter.writeBoolean(parcel, 7, this.zzbk);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
    }
}

