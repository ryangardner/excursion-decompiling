/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.zaa;

public class ClientIdentity
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ClientIdentity> CREATOR = new zaa();
    private final int zaa;
    private final String zab;

    public ClientIdentity(int n, String string2) {
        this.zaa = n;
        this.zab = string2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ClientIdentity)) {
            return false;
        }
        object = (ClientIdentity)object;
        if (((ClientIdentity)object).zaa != this.zaa) return false;
        if (!Objects.equal(((ClientIdentity)object).zab, this.zab)) return false;
        return true;
    }

    public int hashCode() {
        return this.zaa;
    }

    public String toString() {
        int n = this.zaa;
        String string2 = this.zab;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 12);
        stringBuilder.append(n);
        stringBuilder.append(":");
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zaa);
        SafeParcelWriter.writeString(parcel, 2, this.zab, false);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

