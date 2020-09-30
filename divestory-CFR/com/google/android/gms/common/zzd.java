/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzo;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

abstract class zzd
extends zzo {
    private int zza;

    protected zzd(byte[] arrby) {
        boolean bl = arrby.length == 25;
        Preconditions.checkArgument(bl);
        this.zza = Arrays.hashCode(arrby);
    }

    protected static byte[] zza(String arrby) {
        try {
            return arrby.getBytes("ISO-8859-1");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError(unsupportedEncodingException);
        }
    }

    public boolean equals(Object arrby) {
        if (arrby == null) return false;
        if (!(arrby instanceof zzm)) {
            return false;
        }
        try {
            arrby = (zzm)arrby;
            if (arrby.zzc() != this.hashCode()) {
                return false;
            }
            if ((arrby = arrby.zzb()) == null) {
                return false;
            }
            arrby = (byte[])ObjectWrapper.unwrap((IObjectWrapper)arrby);
            return Arrays.equals(this.zza(), arrby);
        }
        catch (RemoteException remoteException) {
            Log.e((String)"GoogleCertificates", (String)"Failed to get Google certificates from remote", (Throwable)remoteException);
        }
        return false;
    }

    public int hashCode() {
        return this.zza;
    }

    abstract byte[] zza();

    @Override
    public final IObjectWrapper zzb() {
        return ObjectWrapper.wrap(this.zza());
    }

    @Override
    public final int zzc() {
        return this.hashCode();
    }
}

