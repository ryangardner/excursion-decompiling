/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;
import com.google.android.gms.common.zzd;
import com.google.android.gms.common.zzn;
import com.google.android.gms.common.zzo;
import java.util.concurrent.Callable;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
class zzl {
    private static final zzl zzb = new zzl(true, null, null);
    final boolean zza;
    @Nullable
    private final String zzc;
    @Nullable
    private final Throwable zzd;

    zzl(boolean bl, @Nullable String string2, @Nullable Throwable throwable) {
        this.zza = bl;
        this.zzc = string2;
        this.zzd = throwable;
    }

    static zzl zza() {
        return zzb;
    }

    static zzl zza(String string2) {
        return new zzl(false, string2, null);
    }

    static zzl zza(String string2, Throwable throwable) {
        return new zzl(false, string2, throwable);
    }

    static zzl zza(Callable<String> callable) {
        return new zzn(callable, null);
    }

    static String zza(String string2, zzd object, boolean bl, boolean bl2) {
        String string3 = bl2 ? "debug cert rejected" : "not allowed";
        String string4 = Hex.bytesToStringLowercase(Preconditions.checkNotNull(AndroidUtilsLight.zza("SHA-1")).digest(((zzd)object).zza()));
        object = new StringBuilder(14);
        ((StringBuilder)object).append("12451009.false");
        return String.format("%s: pkg=%s, sha1=%s, atk=%s, ver=%s", string3, string2, string4, bl, ((StringBuilder)object).toString());
    }

    @Nullable
    String zzb() {
        return this.zzc;
    }

    final void zzc() {
        if (this.zza) return;
        if (!Log.isLoggable((String)"GoogleCertificatesRslt", (int)3)) return;
        if (this.zzd != null) {
            Log.d((String)"GoogleCertificatesRslt", (String)this.zzb(), (Throwable)this.zzd);
            return;
        }
        Log.d((String)"GoogleCertificatesRslt", (String)this.zzb());
    }
}

