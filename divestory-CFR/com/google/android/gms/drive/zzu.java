/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.drive;

import android.os.Parcel;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public abstract class zzu
extends AbstractSafeParcelable {
    private volatile transient boolean zzbt = false;

    public void writeToParcel(Parcel parcel, int n) {
        Preconditions.checkState(this.zzbt ^ true);
        this.zzbt = true;
        this.zza(parcel, n);
    }

    protected abstract void zza(Parcel var1, int var2);
}

