/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.query.internal.zzg;
import java.util.Locale;

public final class zzf
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzf> CREATOR = new zzg();
    private final String fieldName;
    private final boolean zzmc;

    public zzf(String string2, boolean bl) {
        this.fieldName = string2;
        this.zzmc = bl;
    }

    public final String toString() {
        Locale locale = Locale.US;
        String string2 = this.fieldName;
        String string3 = this.zzmc ? "ASC" : "DESC";
        return String.format(locale, "FieldWithSortOrder[%s %s]", string2, string3);
    }

    public final void writeToParcel(Parcel parcel, int n) {
        n = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.fieldName, false);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzmc);
        SafeParcelWriter.finishObjectHeader(parcel, n);
    }
}

