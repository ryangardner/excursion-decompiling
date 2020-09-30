/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zziz;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;

interface zzmf<T> {
    public boolean equals(T var1, T var2);

    public int hashCode(T var1);

    public T newInstance();

    public void zza(T var1, zzns var2) throws IOException;

    public void zza(T var1, byte[] var2, int var3, int var4, zziz var5) throws IOException;

    public void zzc(T var1, T var2);

    public void zzd(T var1);

    public int zzn(T var1);

    public boolean zzp(T var1);
}

